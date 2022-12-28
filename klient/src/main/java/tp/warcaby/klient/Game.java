package tp.warcaby.klient;

import javafx.application.Application;
import javafx.stage.Stage;
import tp.warcaby.klient.board.BoardController;

import java.io.*;
import java.net.Socket;

public class Game extends Application {

    private BoardController controller;
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

        //Commented out for now as sockets dont work for me

//        try {
//            socket = new Socket("localhost", 1);
//
//            /**
//             * ODBIERANIE Z SOCKETA
//             */
//            input = socket.getInputStream();
//            in = new BufferedReader(new InputStreamReader(input));
//
//            /**
//             * WYSYLANIE DO SOCKETA
//             */
//            output = socket.getOutputStream();
//            out = new PrintWriter(output, true);
//            System.out.println("ko");
//
//        }
//
//        /**
//         * LAPANIE WYJATKOW
//         */
//        catch (IOException ex) {
//            System.out.println("Server exception: " + ex.getMessage());
//            ex.printStackTrace();
//        }
        controller = new BoardController(stage);
        controller.showView();

        //Commented out for now as I work on Frontend

        //choiceStage = new ChoiceStage(250, 250, out);
        //choiceStage.show();

    }

}