package tp.warcaby.klient.board;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Pair;

//CLASS RESPONSIBLE FOR CONTROLLING STATE OF BOARD AND BOARDVIEW

public class BoardController implements EventHandler<ActionEvent> {
    private final Board currentBoard;
    private BoardView currentView;
    private Pair<Integer, Integer> activePawn;
    private int size = 8;

    private Stage stage;

    public BoardController(Stage stage) {
        this.stage = stage;
        currentBoard = initBoard();
        activePawn = null;
        currentView = null;
    }

    public void setSize(int n) { currentBoard.setSize(n); }

    public int getSize() { return size; }

    public void setPawn(int x, int y, PawnType type, PawnColor color) {
        currentBoard.addPawn(x, y, type, color);
    }

    public void removePawn(int x, int y) {
        currentBoard.removePawn(x, y);
    }

    public void updatePawnPosition(int oldX, int oldY, int newX, int newY)
    {
        if(currentBoard.getPawn(oldX, oldY).get_color() == PawnColor.BLACK)
        {
            currentBoard.addPawn(newX, newY, PawnType.NORMAL, PawnColor.BLACK);
        }
        else
        {
            currentBoard.addPawn(newX, newY, PawnType.NORMAL, PawnColor.WHITE);
        }
        currentBoard.removePawn(oldX, oldY);
        showView();
    }

    public void promoteToQueen(int x, int y) {
        currentBoard.promoteToQueen(x, y);
    }

    public void showView() {
        Platform.setImplicitExit(false);
        Platform.runLater(() -> {
            currentView = new BoardView(currentBoard.getPieces(),this);
            showStage();
        });
    }

    @Override
    public void handle(ActionEvent actionEvent) {

        Object source = actionEvent.getSource();
        Button[][] buttons = currentView.getButtons();

        for(int i = 0; i < currentBoard.getSize(); i++) {
            for(int j = 0; j < currentBoard.getSize(); j++) {
                if(source.equals(buttons[i][j]))
                {

                    //jesli to aktyny to skip
                    //jesli to niaktywny i to jest jedno pole do przodu (w zaleznosci od koloru0
                    // to przesun pionka na nowe pole


                    if(activePawn.getKey() == i && activePawn.getValue() == j) {
                       continue;
                    }
                    else {

                        updatePawnPosition(activePawn.getKey(), activePawn.getValue(), i, j);
                        activePawn = new Pair<>(i, j);
                    }

                    currentView = new BoardView(currentBoard.getPieces(), this);
                    showStage();
                    return;
                }
            }
        }
    }

    public Board initBoard() {
        Board initialBoard = new Board(size);
        for(int row = 0; row < size; row++) {
            for(int col = 0; col < size; col++) {
                System.out.println(row);
                if ((col + row) % 2 == 0) {
                    if (col <= 2) {
                        initialBoard.addPawn(row, col, PawnType.NORMAL, PawnColor.WHITE);
                    } else if (col >= 5) {
                        initialBoard.addPawn(row, col, PawnType.NORMAL, PawnColor.BLACK);

                    }
                }
            }
        }
        return initialBoard;
    }

    private void showStage() {
        stage.setScene(new Scene(currentView));
        stage.show();
        stage.setResizable(false);
    }
}