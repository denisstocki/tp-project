package tp.warcaby.klient;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tp.warcaby.klient.board.BoardController;
import tp.warcaby.klient.board.BoardState;
import tp.warcaby.klient.board.Boardable;
import tp.warcaby.klient.board.ClassicBoard;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Current Game
 * */
public class Game extends Application {

    public static void main(String[] args) {
        launch();
    }
    /**
     * Start our game on client side
     * */
    @Override
    public void start(Stage stage) throws Exception {

        Boardable board = null;
        String message;

        try {

            final Socket socket = new Socket("172.20.10.3", 4444);
            final Scanner in = new Scanner(socket.getInputStream());
            final PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            message = in.nextLine();
            System.out.println("[Received message]: " + message);

            if ("choose".equals(message)) {

                Stage choiceStage = new Stage();
                ChoiceGrid choiceGrid = new ChoiceGrid(choiceStage);
                Scene choiceScene = new Scene(choiceGrid, 250, 298);

                choiceStage.setResizable(false);
                choiceStage.setTitle("Warcaby");
                choiceStage.setScene(choiceScene);
                choiceStage.showAndWait();

                message = choiceGrid.getChoice();
                out.println(message);
                System.out.println("[Sent message]: " + message);

                if ("classic".equals(message)) board = new ClassicBoard(BoardState.LOCKED, "white");
                else if ("english".equals(message)) board = new EnglishBoard(BoardState.LOCKED, "white");
                else if ("overtaking".equals(message)) board = new OvertakingBoard(BoardState.LOCKED, "white");
                else if ("polish".equals(message)) board = new PolishBoard(BoardState.LOCKED, "white");

            }
            else if ("classic".equals(message)) board = new ClassicBoard(BoardState.LOCKED, "black");
            else if ("english".equals(message)) board = new EnglishBoard(BoardState.LOCKED, "black");
            else if ("overtaking".equals(message)) board = new OvertakingBoard(BoardState.LOCKED, "black");
            else if ("polish".equals(message)) board = new PolishBoard(BoardState.LOCKED, "black");

            Thread game = new GameThread(new BoardController(board), in, out, socket);

            board.setOnExit(socket, game);

            game.start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}