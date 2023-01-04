package tp.warcaby.klient.board;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.concurrent.CountDownLatch;

public abstract class Boardable {
    public BoardState boardState;
    private final Stage stage;
    private final String title;
    public Label infoLabel;
    public Field[][] fields;
    public Button[][] buttons;
    public final int[] prevCoords = new int[2];
    public final int[] nextCoords = new int[2];
    private final String color;
    public CountDownLatch latch;
    public final double pawnSize;
    public final double fieldSize;
    public boolean moved;
    private final Scene scene;
    private final BorderPane pane;
    public GridPane gridPane;
    private final Group topGroup;
    private final BorderPane topPane;
    public MouseState mouseState;
    public final int size;

    public Boardable(BoardState boardState, String color, int size, String title) {
        double screenX = 800;

        this.boardState = boardState;
        this.color = color;
        this.size = size;
        this.pawnSize = 0.3375 * ((screenX)/size);
        this.fieldSize = 0.9 * ((screenX)/size);
        this.title = title;

        infoLabel = new Label();
        stage = new Stage();
        fields = new Field[size][size];
        buttons = new Button[size][size];
        pane = new BorderPane();
        gridPane = new GridPane();
        topGroup = new Group();
        topPane = new BorderPane();
        scene = new Scene(pane);

        mouseState = MouseState.FIRST_CLICK;
        latch = null;
        moved = false;

        setBoard();
    }

    private void setBoard() {
        setInfoLabel();
        setTopGroup();
        setTopPane();
        setGridPane();
        setPane();
        setStage();
        initializePawns();
    }

    private void setStage() {
        stage.setTitle(title);
        stage.setResizable(false);
        stage.setScene(scene);
    }

    private void setPane() {
        pane.setTop(topPane);
        pane.setCenter(gridPane);
    }

    private void setGridPane() {
        gridPane.setHgap(0);
        gridPane.setVgap(0);
    }

    private void setTopPane() {
        topPane.setLeft(topGroup);
        topPane.setRight(infoLabel);
        topPane.setStyle("-fx-border-width: 3 0 2 0;" +
                "-fx-border-color: #392613;");
    }

    private void setTopGroup() {
        Rectangle rectangle = new Rectangle(50, 50, Paint.valueOf("#734d26"));
        Circle circle = new Circle(15, Paint.valueOf(color));

        topGroup.getChildren().add(rectangle);
        topGroup.getChildren().add(circle);

        circle.setCenterX(25);
        circle.setCenterY(25);
        rectangle.setX(0);
        rectangle.setY(0);

        rectangle.setStyle("-fx-border-width: 3 3 2 3;" +
                "-fx-border-color: #392613;");
        topGroup.setStyle("-fx-border-width: 3 0 2 0;" +
                "-fx-border-color: #392613;");
    }

    private void setInfoLabel() {
        infoLabel.setText("Your move!");
        infoLabel.setAlignment(Pos.CENTER);
        infoLabel.setPrefSize(fieldSize * size - 50, 50);
        infoLabel.setStyle("-fx-background-color: #734d26;" +
                "-fx-border-width: 0 0 0 3;" +
                "-fx-border-color: #392613;" +
                "-fx-font-family: Arial;" +
                "-fx-font-size: 25px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #000");
    }

    public void setOpponentMove(String move){
        int x1 = move.charAt(0);
        int y1 = move.charAt(1);
        int x2 = move.charAt(2);
        int y2 = move.charAt(3);

        PawnLook prevLook = fields[x1][y1].getPawn().getLook();
        Field tempField = fields[x2][y2];

        fields[x1][y1].getPawn().make(PawnLook.GHOST);
        tempField.getPawn().make(prevLook);

        buttons[x1][y1].toFront();

        if(prevLook.toString().contains("queen")){
            tempField.getPawn().toFront();
            tempField.getOuterCrown().toFront();
            tempField.getInnerCrown().toFront();
        } else tempField.getPawn().toFront();
    }
    public void setBoardState(BoardState state){
        boardState = state;
    }

    public void setGameInfo(String info){
        infoLabel.setText(info);
    }
    public void setWinnerInfo(String winner){
        if("white".equals(winner)) infoLabel.setText("Wygrały białe pionki !");
        else if ("black".equals(winner)) infoLabel.setText("Wygrały czarne pionki !");
        else infoLabel.setText("Remis !");
    }
    public void setOurMove(){
        int x1 = nextCoords[0];
        int y1 = nextCoords[1];
        int x2 = prevCoords[0];
        int y2 = prevCoords[1];

        fields[x1][y1].getPawn().make(fields[x2][y2].getPawn().getLook());

        if(fields[x1][y1].getPawn().getLook().toString().toLowerCase().contains("queen")){
            fields[x1][y1].getPawn().toFront();
            fields[x1][y1].getOuterCrown().toFront();
            fields[x1][y1].getInnerCrown().toFront();
            fields[x1][y1].getInnerCrown().setFill(fields[x1][y1].getPawn().getFill());
        } else fields[x1][y1].getPawn().toFront();

        fields[x2][y2].getPawn().make(PawnLook.GHOST);
        buttons[x2][y2].toFront();
    }
    public void showBoard(){
        stage.show();
    }
    public boolean wasMoved(){
        if(moved){
            moved = false;
            return true;
        } else return false;
    }
    public String getMove(){
        return String.valueOf(prevCoords[0]) +
                prevCoords[1] +
                nextCoords[0] +
                nextCoords[1];
    }
    public BoardState getBoardState(){
        return boardState;
    }
    public void setLatch(CountDownLatch latch){
        this.latch = latch;
    }
    public void setButton(Button button){
        button.setPrefHeight(fieldSize);
        button.setPrefWidth(fieldSize);
        button.setOnAction(actionEvent -> {
            if(boardState.toString().equals("UNLOCKED")) {
                Object source = actionEvent.getSource();
                outer_loop:
                for (int k = 0; k < size; k++) {
                    for (int l = 0; l < size; l++) {
                        if (source.equals(buttons[k][l])) {
                            if (mouseState.toString().equalsIgnoreCase("first_click")) {
                                prevCoords[0] = k;
                                prevCoords[1] = l;
                                mouseState = MouseState.SECOND_CLICK;
                            } else if (mouseState.toString().equalsIgnoreCase("second_click")) {
                                nextCoords[0] = k;
                                nextCoords[1] = l;
                                mouseState = MouseState.FIRST_CLICK;
                                moved = true;
                                latch.countDown();
                            }
                            break outer_loop;
                        }
                    }
                }
            } else infoLabel.setText("Nie możesz wykonać teraz ruchu !");
        });
    }

    public abstract void initializePawns();
}
