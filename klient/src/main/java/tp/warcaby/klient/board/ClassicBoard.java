package tp.warcaby.klient.board;

//Class implementing asbtract Board object.
//We dont care about look, view or handling changes, just internal behaviours of the board

//Add asserts chececking if we have sth to delete or not and throwe errors respectively.

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class ClassicBoard extends Stage implements Boardable{
    //TODO check for better encapsulation here
    private Field[][] fields;
    private Button[][] buttons;
    private final double pawnSize;
    private final double fieldSize;
    private GridPane gridPane;
    private BoardState boardState;
    private MouseState mouseState;
    private Label infoLabel;
    private int currX, currY;
    private Scene scene;
    private Pawn pawn;
    private BorderPane pane;
    private int size;



    public ClassicBoard(BoardState boardState) {
        this.boardState = boardState;
        this.size = 8;
        this.pawnSize = 0.3375 * ((screenX)/size);
        this.fieldSize = 0.9 * ((screenX)/size);
        infoLabel = new Label();
        infoLabel.setPrefSize(fieldSize*size, 50);
        infoLabel.setStyle("-fx-background-color: #734d26;" +
                "-fx-border-width: 3 0 2 0;" +
                "-fx-border-color: #392613;" +
                "-fx-font-family: Arial;" +
                "-fx-font-size: 25px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #000");
        infoLabel.setText("Your move!");
        infoLabel.setAlignment(Pos.CENTER);
        mouseState = MouseState.FIRST_CLICK;
        gridPane = new GridPane();
        gridPane.setHgap(0);
        gridPane.setVgap(0);
        pane = new BorderPane();
        pane.setTop(infoLabel);
        pane.setCenter(gridPane);
        scene = new Scene(pane);
        fields = new Field[size][size];
        buttons = new Button[size][size];
        setTitle("Classic Checkers");
        setResizable(false);
        setScene(scene);
        initializeBoard();
    }

    @Override
    public void initializeBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(i<3 && (i+j)%2==1){
                    pawn = new Pawn(PawnLook.BLACK_QUEEN, pawnSize);
                } else if (i > 4 && (i+j)%2==1) {
                    pawn = new Pawn(PawnLook.WHITE, pawnSize);
                } else {
                    pawn = new Pawn(PawnLook.GHOST, pawnSize);
                }
                buttons[i][j] = new Button();
                if((i+j)%2==1){
                    fields[i][j] = new Field("#BD7A44", pawn, fieldSize, pawnSize, buttons[i][j]);
                } else {
                    fields[i][j] = new Field("#E3C193", pawn, fieldSize, pawnSize, buttons[i][j]);
                }
                buttons[i][j].setPrefHeight(fieldSize);
                buttons[i][j].setPrefWidth(fieldSize);
                gridPane.add(fields[i][j], j, i);
                buttons[i][j].setOnAction(actionEvent -> {
                    Object source = actionEvent.getSource();
                    outer_loop: for (int k = 0; k < size; k++) {
                        for (int l = 0; l < size; l++) {
                            if(source.equals(buttons[k][l])){
                                if(mouseState.toString().equalsIgnoreCase("first_click")){
                                    currX = k;
                                    currY = l;
                                    mouseState = MouseState.SECOND_CLICK;
                                } else if (mouseState.toString().equalsIgnoreCase("second_click")) {
                                    fields[k][l].getPawn().make(fields[currX][currY].getPawn().getLook());
                                    if(fields[k][l].getPawn().getLook().toString().toLowerCase().contains("queen")){
                                        fields[k][l].getPawn().toFront();
                                        fields[k][l].getOuterCrown().toFront();
                                        fields[k][l].getInnerCrown().toFront();
                                        fields[k][l].getInnerCrown().setFill(fields[k][l].getPawn().getFill());
                                    } else {
                                        fields[k][l].getPawn().toFront();
                                    }
                                    fields[currX][currY].getPawn().make(PawnLook.GHOST);
                                    buttons[currX][currY].toFront();
                                    mouseState = MouseState.FIRST_CLICK;
                                }
                                break outer_loop;
                            }
                        }
                    }
                });
            }
        }
    }

    @Override
    public void setOpponentMove(String move)
    {
        int x1 = move.charAt(0);
        int y1 = move.charAt(1);
        int x2 = move.charAt(2);
        int y2 = move.charAt(3);
        Field tempField = fields[x1][y1];
        PawnLook prevLook = tempField.getPawn().getLook();
        tempField.getPawn().make(PawnLook.GHOST);
        buttons[x1][y1].toFront();
        tempField = fields[x2][y2];
        tempField.getPawn().make(prevLook);
        if(prevLook.toString().contains("queen")){
            tempField.getPawn().toFront();
            tempField.getOuterCrown().toFront();
            tempField.getInnerCrown().toFront();
        } else {

        }
    }

    @Override
    public void setBoardState(BoardState state) {
        this.boardState = state;
    }
}
