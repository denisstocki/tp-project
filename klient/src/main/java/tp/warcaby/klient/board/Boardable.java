package tp.warcaby.klient.board;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * Abstract class representing base Board View informations
 * */
public abstract class Boardable {
    /**
     * Enum holding state of the Board
     * */
    public BoardState boardState;
    /**
     * Reference to stage when Board will be displayed
     * */
    private final Stage stage;
    /**
     * Title of Board view
     * */
    private final String title;
    /**
     * Reference to JavaFX Label holding messages to user
     * */
    public Label infoLabel;
    /**
     * Fields of the board view
     * */
    public Field[][] fields;
    /**
     * Clickable buttons for checkers fields
     * */
    public Button[][] buttons;
    /**
     * Previously chosen Field on Board view
     * */
    public final int[] prevCoords = new int[2];
    /**
     * Currently chosen Field on Board view
     * */
    public final int[] nextCoords = new int[2];
    /**
     * Holds color name to be displayed
     * */
    private final String color;
    /**
     * Latch to moderate access to fields
     * */
    public CountDownLatch latch;
    /**
     * Size of pawn in pixels
     * */
    public final double pawnSize;
    /**
     * Field size in pixels
     * */
    public final double fieldSize;
    /**
     * Was a piece moved
     * */
    public boolean moved;
    /**
     * Reference to JavaFX scene
     * */
    private final Scene scene;
    /**
     * Reference to JavaFX border pane
     * */
    private final BorderPane pane;
    /**
     * Reference to JavaFX grid pane holding board fields
     * */
    public GridPane gridPane;
    /**
     * Reference to JavaFX group of top elements on screen
     * */
    private final Pane topPaneGroup;
    /**
     * Reference to JavaFX top pane
     * */
    private final BorderPane topPane;
    /**
     * Holds current mouse state
     * */
    public MouseState mouseState;
    /**
     * Size of Board view displayed in client
     * */
    public final int size;

    /**
     * Holds previous color info
     * */
    public String prevColor;

    public final Character[] letters;
    public final int[] numbers;
    private final GridPane upperLettersPane;
    private final GridPane lowerLettersPane;
    private final GridPane leftNumbersPane;
    private final GridPane rightNumbersPane;
    private final GridPane topGridPane;
    private boolean reverse;

    private ArrayList<String> currentBestMoves;
    private ArrayList<Field> possibleFields;

    /**
     * Base Boardable constructor
     * */
    public Boardable(BoardState boardState, String color, int size, String title, boolean reverse) {
        double screenX = 800 - (double)480/size;

        this.boardState = boardState;
        this.color = color;
        this.size = size;
        this.pawnSize = 0.3375 * ((screenX)/size);
        this.fieldSize = (double) 640 / size;
        this.title = title;
        this.reverse = reverse;

        letters = new Character[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
        numbers = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        infoLabel = new Label();
        upperLettersPane = new GridPane();
        lowerLettersPane = new GridPane();
        leftNumbersPane = new GridPane();
        rightNumbersPane = new GridPane();
        topGridPane = new GridPane();
        stage = new Stage();
        fields = new Field[size][size];
        possibleFields = new ArrayList<>();
        buttons = new Button[size][size];
        pane = new BorderPane();
        gridPane = new GridPane();
        topPaneGroup = new Pane();
        topPane = new BorderPane();
        scene = new Scene(pane);

        mouseState = MouseState.FIRST_CLICK;
        latch = null;
        prevColor = null;

        setBoard();

        System.out.println("x = " + scene.getWidth());
        System.out.println("y = " + scene.getHeight());
    }

    /**
     * Set up of JavaFX visuasl
     * */
    private void setBoard() {
        setInfoLabel();
        setTopGroup();
        setTopGridPane();
        setLettersGrid(upperLettersPane);
        setLettersGrid(lowerLettersPane);
        setNumbersGrid(leftNumbersPane);
        setNumbersGrid(rightNumbersPane);
        setTopPane();
        setGridPane();
        setPane();
        setStage();
        initializePawns(color);
    }

    private void setTopGridPane() {
        topGridPane.add(topPaneGroup, 0, 0);
        topGridPane.add(infoLabel, 1, 0);
        topGridPane.setStyle("-fx-border-width: 3 0 3 0;" +
                "-fx-border-color: #392613;");
    }

    private void setNumbersGrid(GridPane gridPane) {
        Label label;
        for (int i = 0; i < size; i++) {
            if(reverse){
                label = new Label(String.valueOf(numbers[size - 1 - i]));
            } else {
                label = new Label(String.valueOf(numbers[i]));
            }
            label.setAlignment(Pos.CENTER);
            label.setPrefSize(25, (double) 650 / size);
            label.setBackground(Background.fill(Paint.valueOf("#E3C193")));
            gridPane.add(label, 0, i);
        }
//        gridPane.setStyle("-fx-border-width: 3 3 3 3;" +
//                "-fx-border-color: #392613;");
    }

    private void setLettersGrid(GridPane gridPane) {
        Label label;
        label = new Label();
        label.setPrefSize(25, 30);
        label.setBackground(Background.fill(Paint.valueOf("#E3C193")));
        gridPane.add(label, 0, 0);
        for (int i = 1; i <= size; i++) {
            if(reverse){
                label = new Label(String.valueOf(letters[size - i]));
            } else {
                label = new Label(String.valueOf(letters[i - 1]));
            }
            label.setAlignment(Pos.CENTER);
            label.setPrefSize((double) 644/size, 30);
            label.setBackground(Background.fill(Paint.valueOf("#E3C193")));
            gridPane.add(label, i, 0);
        }
        label = new Label();
        label.setPrefSize(25, 30);
        label.setBackground(Background.fill(Paint.valueOf("#E3C193")));
        gridPane.add(label, size + 1, 0);
//        gridPane.setStyle("-fx-border-width: 3 3 3 3;" +
//                "-fx-border-color: #392613;");
    }

    /**
     * Setting up main stage
     * */
    private void setStage() {
        stage.setTitle(title);
        stage.setResizable(false);
        stage.setScene(scene);
    }
    /**
     * Setting up main pane
     * */
    private void setPane() {
        pane.setLeft(leftNumbersPane);
        pane.setCenter(gridPane);
        pane.setRight(rightNumbersPane);
        pane.setTop(topPane);
        pane.setBottom(lowerLettersPane);
    }
    /**
     * Setting up main grid pane
     * */
    private void setGridPane() {
        gridPane.setHgap(0);
        gridPane.setVgap(0);
        gridPane.setStyle("-fx-border-width: 2 2 2 2;" +
                "-fx-border-color: #392613;");
        gridPane.setPrefSize(644, 644);
    }
    /**
     * Setting up main top pane
     * */
    private void setTopPane() {
        topPane.setTop(topGridPane);
        topPane.setBottom(upperLettersPane);
//        topPane.setStyle("-fx-border-width: 3 0 2 0;" +
//                "-fx-border-color: #392613;");
    }
    /**
     * Setting up main top group
     * */
    private void setTopGroup() {
        Rectangle rectangle = new Rectangle(50, 50, Paint.valueOf("#734d26"));
        Circle circle = new Circle(15, Paint.valueOf(color));

        topPaneGroup.getChildren().add(rectangle);
        topPaneGroup.getChildren().add(circle);

        circle.setCenterX(25);
        circle.setCenterY(25);
        rectangle.setX(0);
        rectangle.setY(0);
    }
    /**
     * Sets message on label to user
     * */
    private void setInfoLabel() {
        infoLabel.setText("Your move!");
        infoLabel.setAlignment(Pos.CENTER);
        infoLabel.setPrefSize(644, 50);
        infoLabel.setStyle("-fx-background-color: #734d26;" +
                "-fx-border-width: 0 0 0 3;" +
                "-fx-border-color: #392613;" +
                "-fx-font-family: Arial;" +
                "-fx-font-size: 25px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #000");
    }
    /**
     * Set move and update board
     * */
    public void setOpponentMove(String move, boolean ended){
        int x1 = Integer.parseInt(String.valueOf(move.charAt(0)));
        int y1 = Integer.parseInt(String.valueOf(move.charAt(1)));
        int x2 = Integer.parseInt(String.valueOf(move.charAt(2)));
        int y2 = Integer.parseInt(String.valueOf(move.charAt(3)));

        System.out.println("KUr " + x1 + y1 + x2 + y2 );

        String enemy;

        if("white".equals(color)){
            enemy = "BLACK";
        } else {
            enemy = "WHITE";
        }

        if(fields[x1][y1].getPawn().getLook().toString().toLowerCase().contains("queen")){
            fields[x2][y2].getPawn().make(fields[x1][y1].getPawn().getLook());
            fields[x2][y2].getPawn().toFront();
            fields[x2][y2].getOuterCrown().toFront();
            fields[x2][y2].getInnerCrown().setFill(fields[x2][y2].getPawn().getLook().getPaint());
            fields[x2][y2].getInnerCrown().toFront();
        } else if(ended & x2 == size - 1 & fields[x1][y1].getPawn().getLook().toString().equals(enemy)){
            if("white".equals(color)){
                fields[x2][y2].getPawn().make(PawnLook.BLACK_QUEEN);
            } else {
                fields[x2][y2].getPawn().make(PawnLook.WHITE_QUEEN);
            }
            fields[x2][y2].getPawn().toFront();
            fields[x2][y2].getOuterCrown().toFront();
            fields[x2][y2].getInnerCrown().setFill(fields[x2][y2].getPawn().getLook().getPaint());
            fields[x2][y2].getInnerCrown().toFront();
        } else if (ended & x2 == 0 & fields[x1][y1].getPawn().getLook().toString().equals(color.toUpperCase())) {
            if("white".equals(color)){
                fields[x2][y2].getPawn().make(PawnLook.WHITE_QUEEN);
            } else {
                fields[x2][y2].getPawn().make(PawnLook.BLACK_QUEEN);
            }
            fields[x2][y2].getPawn().toFront();
            fields[x2][y2].getOuterCrown().toFront();
            fields[x2][y2].getInnerCrown().setFill(fields[x2][y2].getPawn().getLook().getPaint());
            fields[x2][y2].getInnerCrown().toFront();
        } else {
            fields[x2][y2].getPawn().make(fields[x1][y1].getPawn().getLook());
            fields[x2][y2].getPawn().toFront();
        }

        fields[x1][y1].getPawn().make(PawnLook.GHOST);
        buttons[x1][y1].toFront();

        int tempX, tempY, dx, dy;

        if(x2 > x1) dx = 1;
        else dx = -1;
        if(y2 > y1) dy = 1;
        else dy = -1;

        tempX = x1 + dx;
        tempY = y1 + dy;

        while (tempX != x2 && tempY != y2){
            fields[tempX][tempY].getPawn().make(PawnLook.GHOST);
            buttons[tempX][tempY].toFront();
            tempX += dx;
            tempY += dy;
        }
    }
    /**
     * Set board state
     * */
    public void setBoardState(BoardState state){
        boardState = state;
    }

    /**
     * Set game info
     * */
    public void setGameInfo(String info){
        infoLabel.setText(info);
    }
    /**
     * Display winner info
     * */
    public void setWinnerInfo(String winner){
        if("white".equals(winner)) infoLabel.setText("Wygrały białe pionki !");
        else if ("black".equals(winner)) infoLabel.setText("Wygrały czarne pionki !");
        else infoLabel.setText("Remis !");
    }
    /**
     * Set our move on board
     * */
    public void setOurMove(boolean ended){
        int x1 = nextCoords[0];
        int y1 = nextCoords[1];
        int x2 = prevCoords[0];
        int y2 = prevCoords[1];

        System.out.println("KUr " + x1 + y1 + x2 + y2 );

        String enemy;

        if("white".equals(color)){
            enemy = "BLACK";
        } else {
            enemy = "WHITE";
        }

        if(fields[x2][y2].getPawn().getLook().toString().toLowerCase().contains("queen")){
            fields[x1][y1].getPawn().make(fields[x2][y2].getPawn().getLook());
            fields[x1][y1].getPawn().toFront();
            fields[x1][y1].getOuterCrown().toFront();
            fields[x1][y1].getInnerCrown().setFill(fields[x2][y2].getPawn().getLook().getPaint());
            fields[x1][y1].getInnerCrown().toFront();
        } else if(ended & x1 == size - 1 & fields[x2][y2].getPawn().getLook().toString().equals(enemy)){
            if("white".equals(color)){
                fields[x1][y1].getPawn().make(PawnLook.BLACK_QUEEN);
            } else {
                fields[x1][y1].getPawn().make(PawnLook.WHITE_QUEEN);
            }
            fields[x1][y1].getPawn().toFront();
            fields[x1][y1].getOuterCrown().toFront();
            fields[x1][y1].getInnerCrown().setFill(fields[x1][y1].getPawn().getLook().getPaint());
            fields[x1][y1].getInnerCrown().toFront();
        } else if (ended & x1 == 0 & fields[x2][y2].getPawn().getLook().toString().equals(color.toUpperCase())) {
            if("white".equals(color)){
                fields[x1][y1].getPawn().make(PawnLook.WHITE_QUEEN);
            } else {
                fields[x1][y1].getPawn().make(PawnLook.BLACK_QUEEN);
            }
            fields[x1][y1].getPawn().toFront();
            fields[x1][y1].getOuterCrown().toFront();
            fields[x1][y1].getInnerCrown().setFill(fields[x1][y1].getPawn().getLook().getPaint());
            fields[x1][y1].getInnerCrown().toFront();
        } else {
            fields[x1][y1].getPawn().make(fields[x2][y2].getPawn().getLook());
            fields[x1][y1].getPawn().toFront();
        }

        fields[x2][y2].getPawn().make(PawnLook.GHOST);
        buttons[x2][y2].toFront();

        int tempX, tempY, dx, dy;

        if(x1 > x2) dx = 1;
        else dx = -1;
        if(y1 > y2) dy = 1;
        else dy = -1;

        tempX = x2;
        tempY = y2;

        tempX += dx;
        tempY += dy;

        while (tempX != x1 && tempY != y1){
            fields[tempX][tempY].getPawn().make(PawnLook.GHOST);
            buttons[tempX][tempY].toFront();
            tempX += dx;
            tempY += dy;
        }
    }
    /**
     * Shows board view on clients screen
     * */
    public void showBoard(){
        stage.show();
    }
    /**
     * Return if sth was moved
     * */
    public boolean wasMoved(){
        return moved;
    }
    /**
     * Set if sth was moved
     * */

    public void setMoved(boolean moved) {
        this.moved = moved;
    }
    /**
     * get move operation requesting it from stored coords
     * */

    public String getMove(){
        return String.valueOf(prevCoords[0]) +
                prevCoords[1] +
                nextCoords[0] +
                nextCoords[1];
    }
    /**
     * get the state of board
     * */
    public BoardState getBoardState(){
        return boardState;
    }
    public void setLatch(CountDownLatch latch){
        this.latch = latch;
    }
    /**
     * Set one of the fields to clicked and processes
     * */
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
                            if (mouseState.toString().equalsIgnoreCase("first_click") && fields[k][l].getPawn().getLook().getColor().equals(color)) {
                                if(prevColor != null){
                                    fields[prevCoords[0]][prevCoords[1]].setFieldStyle(prevColor);
                                    getPreviousFields();
                                    System.out.println("1");
                                }
                                System.out.println("2");
                                prevCoords[0] = k;
                                prevCoords[1] = l;
                                mouseState = MouseState.SECOND_CLICK;
                                prevColor = fields[k][l].getFieldColor();
                                fields[k][l].setFieldStyle("#FFB31A");
                                savePossibleFields(k, l);
                                showPossibleFields();
                                System.out.println("pierwszy ruch " + fields[prevCoords[0]][prevCoords[1]].getPawn().getLook().getColor());
                            } else if (mouseState.toString().equalsIgnoreCase("second_click")) {
                                if(fields[k][l].getPawn().getLook().getColor().equals(color)){
                                    System.out.println("3");
                                    if(k != prevCoords[0] || l != prevCoords[1]){
                                        fields[k][l].setFieldStyle("#FFB31A");
                                        getPreviousFields();
                                        savePossibleFields(k, l);
                                        showPossibleFields();
                                        fields[prevCoords[0]][prevCoords[1]].setFieldStyle(prevColor);
                                        prevCoords[0] = k;
                                        prevCoords[1] = l;
                                    }
                                } else {
                                    System.out.println("5");
                                    nextCoords[0] = k;
                                    nextCoords[1] = l;
                                    mouseState = MouseState.FIRST_CLICK;
                                    if(fields[prevCoords[0]][prevCoords[1]].getPawn().getLook().getColor().equals(color) &&
                                            fields[nextCoords[0]][nextCoords[1]].getPawn().getLook().getColor().equals("none")){
                                        System.out.println("6");
                                        moved = true;
                                        System.out.println("dobry ruch");
                                    }
                                    fields[prevCoords[0]][prevCoords[1]].setFieldStyle(prevColor);
                                    getPreviousFields();
                                    latch.countDown();
                                }
                            }
                            break outer_loop;
                        }
                    }
                }
            } else infoLabel.setText("Nie możesz wykonać teraz ruchu !");
        });
    }

    private void getPreviousFields() {
        for (Field field : possibleFields) {
            field.setOldFieldStyle();
        }
    }

    private void savePossibleFields(int x, int y) {
        possibleFields = new ArrayList<>();

        for (String move : currentBestMoves) {
            if(String.valueOf(x).equals(move.substring(0, 1)) && String.valueOf(y).equals(move.substring(1, 2))){
                possibleFields.add(fields[Integer.parseInt(String.valueOf(move.charAt(2)))][Integer.parseInt(String.valueOf(move.charAt(3)))]);
            }
        }
    }

    private void showPossibleFields() {
        for (Field field : possibleFields) {
            field.setFieldStyle("green");
        }
    }

    public String getColor(){
        return color;
    }
    /**
     * get board width
     * */
    public double getBoardX(){
        return stage.getX() + stage.getWidth()/2;
    }
    /**
     * get board height
     * */
    public double getBoardY(){
        return stage.getY() + stage.getHeight()/2;
    }
    /**
     * Get board size
     * */
    public int getSize() {
        return size;
    }

    /**
     * Instructions to run on game exit
     * */
    public void setOnExit(Socket socket, Thread game){
        stage.setOnCloseRequest(windowEvent -> {
            try {
                socket.close();
                game.interrupt();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Initialize pawns on the board
     * */
    public abstract void initializePawns(String color);

    public void setBestMoves(String moves) {
        currentBestMoves = new ArrayList<>();

        for (int i = 0; i < moves.length(); i += 4) {
            currentBestMoves.add(moves.substring(i, i + 4));
        }

        System.out.println("currentBestMoves = " + currentBestMoves);
    }

    public boolean isReversed() {
        return reverse;
    }
}
