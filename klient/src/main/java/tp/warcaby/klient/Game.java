package tp.warcaby.klient;

import tp.warcaby.klient.board.BoardController;
import tp.warcaby.klient.board.BoardState;
import tp.warcaby.klient.board.ClassicBoard;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Game  {


    public static void main(String[] args) {
        BoardController controller = null;
        String command;
        ChoiceStage choiceStage;
        Socket socket;

        try {

            socket = new Socket("localhost", 3333);

            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String msgin, move;

            EndingStage endingStage;
            move = null;

            System.out.println("Here");
            ChoiceStage choiceStage1 = new ChoiceStage();
//            msgin = in.nextLine();
//            if("choose".equals(msgin)){
//                choiceStage1.start(new Stage());
//                out.println(choiceStage1.getChoice());
//            }

            game_loop: while (true){
                System.out.println("Again");
                msgin = in.nextLine();
                System.out.println("Received message: " + msgin);
                if(msgin.contains("unblocked")) move = msgin.replace("unblocked", "");
                switch (msgin){
                    case "choose":
                        System.out.println("Tutaj");
                        choiceStage1.showStage();
                        System.out.println("nie dziala");
                        break;
                    case "classic":
                        controller = new BoardController(new ClassicBoard(BoardState.LOCKED));
                        break;
                    case "english":
                        controller = new BoardController(new EnglishBoard(BoardState.LOCKED));
                        break;
                    case "overtaking":
                        controller = new BoardController(new OvertakingBoard(BoardState.LOCKED));
                        break;
                    case "illegal":
                        controller.setGameInfo("Wykonano nielegalny ruch! Spróbuj ponownie ...");
                        controller.setBoardState(BoardState.UNLOCKED);
                        break;
                    case "black":
                    case "white":
                    case "tie":
                        controller.setBoardState(BoardState.LOCKED);
                        controller.setWinnerInfo(msgin);
                        endingStage = new EndingStage(msgin);
                        endingStage.show();
                        socket.close();
                        break game_loop;
                    case "block":
                        controller.setBoardState(BoardState.LOCKED);
                        controller.setOurMove();
                        controller.setGameInfo("Oczekiwanie na ruch przeciwnika!");
                        break;
                    case "unblocked":
                        controller.setOpponentMove(move);
                        controller.setGameInfo("Twoj ruch!");
                        controller.setBoardState(BoardState.UNLOCKED);
                        break;
                    case "error":
                        controller.setBoardState(BoardState.LOCKED);
                        endingStage = new EndingStage(msgin);
                        endingStage.show();
                        socket.close();
                        break game_loop;
                }
            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}