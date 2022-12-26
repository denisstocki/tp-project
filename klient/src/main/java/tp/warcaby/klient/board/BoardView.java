package tp.warcaby.klient.board;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.Group;
import javafx.scene.control.Button;

//CLASS RESPONSIBLE ONLY FOR JAVAFX SITE OF GAME
//JUST CLASSIFY WHAT WILL BE DISPLAYED WHEN CALLED

public class BoardView extends GridPane {
    private final double screenX = 800;
    private final double screenY = 800;
    int size = 8;
    private final Button[][] buttonFields;
    private final BoardController boardController;

    BoardView(Pawn[][] pieces, BoardController boardController) {

        this.boardController = boardController;

        double buttonSize = (screenX /  size) * 0.9;   //Offset for upper bar
        double pieceSize = 0.75 * buttonSize / 2;

        buttonFields = new Button[size][size];

        for(int row = 0; row < size; row++) {

            for(int col = 0; col< size; col++) {

                Button button = new Button();
                button.setPrefHeight(buttonSize);
                button.setPrefWidth(buttonSize);
                buttonFields[row][col] = button;
                button.setOnAction((EventHandler<ActionEvent>) boardController);

                String backgroundColor;
                if((row+col) % 2 == 0) {backgroundColor = "#E3C193";}
                else {backgroundColor = "#BD7A44";}

                button.setStyle(String.format("-fx-background-color: %s; -fx-background-radius: 0; -fx-background-insets: 0 0 -1 0, 0, 1, 2;", backgroundColor));

                if(pieces[row][col] != null)
                {
                    Circle circle = new Circle(pieceSize);
                    circle.setCenterX(buttonSize / 2.0);
                    circle.setCenterY(buttonSize / 2.0);

                    if(pieces[row][col].get_color() == PawnColor.BLACK)
                        circle.setFill(Paint.valueOf("#000"));
                    else
                        circle.setFill(Paint.valueOf("#FFF"));

                    circle.setOnMouseClicked(mouseEvent -> button.fire());
                    Group group = new Group(button, circle);
                    add(group, row, col);
                }
                else {
                    add(button, row, col);
                }
            }
        }
    }

    Button[][] getButtons() {
        return buttonFields;
    }
}
