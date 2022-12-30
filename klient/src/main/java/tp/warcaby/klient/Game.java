package tp.warcaby.klient;

import javafx.application.Application;
import javafx.stage.Stage;
import tp.warcaby.klient.board.BoardState;
import tp.warcaby.klient.board.ClassicBoard;

import java.io.*;
import java.net.Socket;

public class Game extends Application {

    private String command;
    private ChoiceStage choiceStage;
    private Socket socket;
    private InputStream input;
    private BufferedReader in;
    private OutputStream output;
    private PrintWriter out;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {

        ClassicBoard game = new ClassicBoard(BoardState.UNLOCKED);
        game.show();

//        try {
//
//            socket = new Socket("localhost", 4444);
//
//            InputStream in = socket.getInputStream();
//            BufferedReader server = new BufferedReader(new InputStreamReader(input));
//
//            OutputStream out = socket.getOutputStream();
//            PrintWriter me = new PrintWriter(output, true);
//
//            ChoiceStage choiceStage = new ChoiceStage(me);
//            String msgin, move;
//
//            EndingStage endingStage;
//
//            game_loop: while (true){
//                msgin = server.readLine();
//                if(msgin.contains("unblocked")) move = msgin.replace("unblocked", "");
//                switch (msgin){
//                    case "choose":
//                        choiceStage.show();
//                        break;
//                    case "classic":
//                        //TODO: ustawic plansze na tryb klasyczny
//                        break;
//                    case "english":
//                        //TODO: ustawic plansze na tryb angielski
//                        break;
//                    case "overtaking":
//                        //TODO: ustawic plansze na tryb wybijanka
//                        break;
//                    case "illegal":
//                        //TODO: cofnac wykonany ruch na planszy
//                        //TODO: wyswietlic informacje na temat wykonania nieprawidlowego ruchu
//                        break;
//                    case "black":
//                    case "white":
//                    case "tie":
//                        game.setBoardState(BoardState.LOCKED);
//                        endingStage = new EndingStage(msgin);
//                        endingStage.show();
//                        break game_loop;
//                    case "block":
//                        //TODO: zablokowac ruch temu graczowi, ruch na planszy zostawic
//                        game.setBoardState(BoardState.LOCKED);
//                        break;
//                    case "unblocked":
//                        //TODO: odblokuj tego gracza, wykonaj ruch zapisany w zmiennej move, wykonaj swoj ruch
//                        game.setBoardState(BoardState.LOCKED);
//                        break;
//                    case "error":
//                        //TODO: zablokowac poruszanie sie na planszy
//                        endingStage = new EndingStage(msgin);
//                        endingStage.show();
//                        socket.close();
//                        break game_loop;
//                }
//            }
//
//        } catch (IOException ex) {
//            System.out.println("Server exception: " + ex.getMessage());
//            ex.printStackTrace();
//        }
    }
}