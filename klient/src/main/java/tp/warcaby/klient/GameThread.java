package tp.warcaby.klient;

import javafx.application.Platform;
import tp.warcaby.klient.board.BoardController;
import tp.warcaby.klient.board.BoardState;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class GameThread extends Thread{

    private final BoardController gameController;
    private final Scanner in;
    private final PrintWriter out;
    private final Socket socket;


    public GameThread(BoardController gameController, Scanner in, PrintWriter out, Socket socket) {
        this.gameController = gameController;
        this.in = in;
        this.out = out;
        this.socket = socket;
    }

    @Override
    public void run() {
        EndingStage endingStage;
        String[] command = new String[2];

        Platform.runLater(gameController::showBoard);
        Platform.runLater(()->gameController.setGameInfo("Oczekiwanie na dolaczenie drugiego gracza ..."));

        game_loop: while (true) {

            command[0] = in.nextLine();
            System.out.println("[Received message]: " + command[0]);

            if (command[0].contains("unblocked")) command[1] = command[0].replace("unblocked", "");

            switch (command[0]) {

                case "illegal":
                    Platform.runLater(() -> gameController.setGameInfo("Wykonano nielegalny ruch! Spróbuj ponownie ..."));
                    waitAndSendMove();
                    break;
                case "joined":
                    Platform.runLater(()-> gameController.setGameInfo("Dolaczyl drugi gracz ! Wykonaj swoj pierwszy ruch ..."));
                    waitAndSendMove();
                    break;
                case "another":
                    Platform.runLater(()->gameController.setGameInfo("Wykonaj kolejny ruch !"));
                    waitAndSendMove();
                    break;
                case "black":
                case "white":
                case "tie":
                    gameController.setBoardState(BoardState.LOCKED);
                    Platform.runLater(()->gameController.setWinnerInfo(command[0]));
                    endingStage = new EndingStage(command[0]);
                    Platform.runLater(endingStage::show);
                    closeConnection();
                    break game_loop;
                case "accepted":
                    gameController.setBoardState(BoardState.LOCKED);
                    Platform.runLater(gameController::setOurMove);
                    Platform.runLater(()-> gameController.setGameInfo("Oczekiwanie na ruch przeciwnika!"));
                    break;
                case "unblocked":
                    Platform.runLater(()->gameController.setOpponentMove(command[1]));
                    Platform.runLater(()->gameController.setGameInfo("Twoj ruch!"));
                    waitAndSendMove();
                    break;
                case "error":
                    gameController.setBoardState(BoardState.LOCKED);
                    endingStage = new EndingStage(command[0]);
                    Platform.runLater(endingStage::show);
                    closeConnection();
                    break game_loop;
            }
        }
    }

    private void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void waitAndSendMove() {
        CountDownLatch latch = new CountDownLatch(1);
        gameController.setBoardState(BoardState.UNLOCKED);
        gameController.setLatch(latch);
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        gameController.setBoardState(BoardState.LOCKED);
        out.println(gameController.getMove());
        System.out.println("[Sent message]: " + gameController.getMove());
    }
}
