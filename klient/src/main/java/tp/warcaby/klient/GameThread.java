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
        String[] command = new String[2];
        boolean lost = false;

        Platform.runLater(gameController::showBoard);
        if("white".equals(gameController.getColor())){
            Platform.runLater(()->gameController.setGameInfo("Oczekiwanie na dolaczenie drugiego gracza ..."));
        } else {
            Platform.runLater(()->gameController.setGameInfo("Ruch przeciwnika !"));
        }

        game_loop: while (true) {

            command[0] = in.nextLine();
            System.out.println("[Received message]: " + command[0]);

            if (command[0].contains("unblocked")){
                command[1] = command[0].replace("unblocked", "");
                command[0] = "unblocked";
                System.out.println(command[1]);
            } else if (command[0].contains("moved")){
                command[1] = command[0].replace("moved", "");
                command[0] = "moved";
            } else if (command[0].contains("white") && !command[0].equals("white")){
                command[1] = command[0].replace("white", "");
                command[0] = "white";
                lost = true;
            } else if (command[0].contains("black") && !command[0].equals("black")){
                command[1] = command[0].replace("black", "");
                command[0] = "black";
                lost = true;
            } else if (command[0].contains("tie") && !command[0].equals("tie")){
                command[1] = command[0].replace("tie", "");
                command[0] = "tie";
                lost = true;
            } else if (command[0].contains("uncaptured") && !command[0].equals("uncaptured")) {
                command[1] = command[0].replace("uncaptured", "");
                lost = true;
            } else if (command[0].contains("repeated") && !command[0].equals("repeated")){
                command[1] = command[0].replace("repeated", "");
                lost = true;
            }

            switch (command[0]) {

                case "illegal":
                    Platform.runLater(() -> gameController.setGameInfo("Wykonano nielegalny ruch! Spróbuj ponownie ..."));
                    waitAndSendMove();
                    break;
                case "joined":
                    Platform.runLater(()-> gameController.setGameInfo("Dolaczyl drugi gracz ... Twoj ruch !"));
                    waitAndSendMove();
                    break;
                case "another":
                    Platform.runLater(()->gameController.setGameInfo("Wykonaj kolejny ruch !"));
                    Platform.runLater(()->gameController.setOurMove(false));
                    waitAndSendMove();
                    break;
                case "cheat":
                    Platform.runLater(()->gameController.setGameInfo("Nie uzywaj wspomagaczy do grania!"));
                    waitAndSendMove();
                    break;
                case "duplicate":
                    Platform.runLater(()->gameController.setGameInfo("Ruch nie moze polegac na zostaniu w miejscu!"));
                    waitAndSendMove();
                    break;
                case "betrayal":
                    Platform.runLater(()->gameController.setGameInfo("Nie mozesz przeskakiwac przez swoje pionki!"));
                    waitAndSendMove();
                    break;
                case "multiple":
                    Platform.runLater(()->gameController.setGameInfo("Mozesz zbic jednego pionka przeciwnika na raz!"));
                    waitAndSendMove();
                    break;
                case "stack":
                    Platform.runLater(()->gameController.setGameInfo("Docelowe pole nie jest puste!"));
                    waitAndSendMove();
                    break;
                case "owner":
                    Platform.runLater(()->gameController.setGameInfo("Nie mozesz poruszac sie pionkami przeciwnika!"));
                    waitAndSendMove();
                    break;
                case "diagonal":
                    Platform.runLater(()->gameController.setGameInfo("Kierunek ruchu niezgodny z zasadami gry!"));
                    waitAndSendMove();
                    break;
                case "rules":
                    Platform.runLater(()->gameController.setGameInfo("Dokoncz zaczete bicie!"));
                    waitAndSendMove();
                    break;
                case "repeated":
                    closeConnection();
                    Platform.runLater(()->gameController.setGameInfo("Powtorzono te same ruchy 3 razy!"));
                    if(lost){
                        Platform.runLater(()->gameController.setOpponentMove(command[1], true));
                    } else {
                        Platform.runLater(()->gameController.setOurMove(true));
                    }
                    Platform.runLater(()->{
                        EndingStage endingStage;
                        Thread endingThread;
                        endingStage = new EndingStage("tie", gameController.getBoardX(), gameController.getBoardY());
                        endingThread = new EndingThread(endingStage);
                        endingThread.start();
                    });
                    break;
                case "uncaptured":
                    closeConnection();
                    Platform.runLater(()->gameController.setGameInfo("Zbyt duzo ruchow damkami bez bicia!"));
                    if(lost){
                        Platform.runLater(()->gameController.setOpponentMove(command[1], true));
                    } else {
                        Platform.runLater(()->gameController.setOurMove(true));
                    }
                    Platform.runLater(()->{
                        EndingStage endingStage;
                        Thread endingThread;
                        endingStage = new EndingStage("tie", gameController.getBoardX(), gameController.getBoardY());
                        endingThread = new EndingThread(endingStage);
                        endingThread.start();
                    });
                    break;
                case "black":
                case "white":
                case "tie":
                    closeConnection();
                    if(lost){
                        Platform.runLater(()->gameController.setOpponentMove(command[1], true));
                    } else {
                        Platform.runLater(()->gameController.setOurMove(true));
                    }
                    Platform.runLater(()->gameController.setWinnerInfo(command[0]));
                    Platform.runLater(()->{
                        EndingStage endingStage;
                        Thread endingThread;
                        endingStage = new EndingStage(command[0], gameController.getBoardX(), gameController.getBoardY());
                        endingThread = new EndingThread(endingStage);
                        endingThread.start();
                    });
                    break game_loop;
                case "moved":
                    Platform.runLater(()->gameController.setOpponentMove(command[1], false));
                    break;
                case "accepted":
                    Platform.runLater(()->gameController.setOurMove(true));
                    Platform.runLater(()-> gameController.setGameInfo("Ruch przeciwnika!"));
                    break;
                case "unblocked":
                    Platform.runLater(()->gameController.setOpponentMove(command[1], true));
                    Platform.runLater(()->gameController.setGameInfo("Twoj ruch!"));
                    waitAndSendMove();
                    break;
                case "error":
                    closeConnection();
                    gameController.setBoardState(BoardState.LOCKED);
                    Platform.runLater(()->{
                        EndingStage endingStage;
                        Thread endingThread;
                        endingStage = new EndingStage(command[0], gameController.getBoardX(), gameController.getBoardY());
                        endingThread = new EndingThread(endingStage);
                        endingThread.start();
                    });
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
        CountDownLatch latch;
        gameController.setBoardState(BoardState.UNLOCKED);
        gameController.setMoved(false);
        System.out.println("pierwsza proba");
        while (!gameController.wasMoved()){
            latch = new CountDownLatch(1);
            gameController.setLatch(latch);
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("druga proba");
            Platform.runLater(()->gameController.setGameInfo("Nie ruszaj pionkow przeciwnika !"));
        }
        gameController.setBoardState(BoardState.LOCKED);
        out.println(gameController.getMove());
        System.out.println("[Sent message]: " + gameController.getMove());
    }
}
