import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import tp.warcaby.klient.board.Board;
import tp.warcaby.klient.board.BoardController;
import tp.warcaby.klient.board.PawnColor;
import tp.warcaby.klient.board.PawnType;

import java.util.ArrayList;
import java.util.Arrays;

public class WarcabyGame extends Application {

    BoardController controller;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {

        //FILL UP THE BOARD AS THE RULES SAY
        controller = new BoardController(stage);
        int size = 8;

        for (int row = 0; row < size; row++) {
            if(row < 3){
                for (int col = 0; col < size; col++) {
                    if(col + row % 2 == 0) controller.setPawn(row, col, PawnType.NORMAL, PawnColor.WHITE);
                }
            }
            else if (row >= 5){
                for (int col = 0; col < size; col++) {
                    if(col + row % 2 == 0) controller.setPawn(row, col, PawnType.NORMAL, PawnColor.Black);
                }
            }
        }
        controller.showView();
    }

}