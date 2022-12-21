package tp.warcaby.klient;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class BoardView extends Application {

    private GridPane gridPane;
    private Scene scene;
    private GameType gameType;
    final List<List<Field>> fields = new ArrayList<>(8);

    private List<Field> currentFields;

    private final double screenX = 800;
    private final double screenY = 800;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        gameType = GameType.CLASSIC;
        int size = gameType.getSize();
        for (int i = 0; i < size; i++) {
            fields.add(new ArrayList<Field>(8));
        }
        createBoard(gameType, size);
        for (int row = 0; row < size; row++) {
            currentFields = fields.get(row);
            if(row == 3 || row == 4){
                for (int col = 0; col < size; col++) {
                    currentFields.get(col).setFieldType(FieldType.EMPTY);
                }
            }
            else if(row < 3){
                for (int col = 0; col < size; col++) {
                    if(col + row % 2 == 0) currentFields.get(col).setFieldType(FieldType.WHITE_PLAYER);
                }
            }
            else {
                for (int col = 0; col < size; col++) {
                    if(col + row % 2 == 0) currentFields.get(col).setFieldType(FieldType.BLACK_PLAYER);
                }
            }
        }
        gridPane = new GridPane();
        scene = new Scene(gridPane, size * 100, size * 100);
        stage.setScene(scene);
        stage.show();
    }

    private void createBoard(GameType gameType, int size) {
        for (int i = 0; i < size; i++) {
            currentFields = fields.get(i);
            for (int j = 0; j < size; j++) {
                Field field = new Field(100, 100);
                if((i + j) % 2==0) {
                    field.setFill(Paint.valueOf("beige"));
                    gridPane.add(field, i, j);
                } else{
                    field.setFill(Paint.valueOf("#5C4033"));
                    gridPane.add(field, i, j);
                }
                switch (currentFields.get(j).getFieldType()){
                    case EMPTY:
                        break;
                    case WHITE_PLAYER:
                        //TODO change that to pawn
                        gridPane.add(new Circle(30, Paint.valueOf("white")), i, j);
                    case BLACK_PLAYER:
                        //TODO change that to pawn
                        gridPane.add(new Circle(30, Paint.valueOf("black")), i, j);
                }
            }
        }
    }
}
