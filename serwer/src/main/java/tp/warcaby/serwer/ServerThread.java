package tp.warcaby.serwer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread {

    public static void main(String[] args) throws IOException {


        try(ServerSocket serverSocket = new ServerSocket(4444)){

            System.out.println("Server is working on port number 4444");

            String game_type, winner, msgin, msgout;
            BufferedReader readA, readB;
            OutputStream outA, outB;
            InputStream inA, inB;
            PrintWriter writeA, writeB;
            GameLogic game_logic;
            Socket socketA, socketB;
            boolean finished;
            int game_number, player_number, move_counter;

            game_number = 0;
            player_number = 0;

            while (true){

                System.out.println("\nWaiting to start game nr.: " + ++game_number);

                game_logic = null;
                finished = false;
                socketA = serverSocket.accept();

                playerJoinedInfo(++player_number);

                inA = socketA.getInputStream();
                outA = socketA.getOutputStream();
                readA = new BufferedReader(new InputStreamReader(inA));
                writeA = new PrintWriter(outA, true);

                sendMsgTo(writeA, "choose");

                game_type = getMsgFrom(readA);

                switch (game_type){
                    case "classic":
                        game_logic = new ClassicCheckers();
                        break;
                    case "english":
                        game_logic = new EnglishCheckers();
                        break;
                    case "overtaking":
                        game_logic = new OvertakingCheckers();
                        break;
                    default:
                        sendMsgTo(writeA, "error");
                        serverSocket.close();
                }

                socketB = serverSocket.accept();

                playerJoinedInfo(++player_number);

                inB = socketB.getInputStream();
                outB = socketB.getOutputStream();
                readB = new BufferedReader(new InputStreamReader(inB));
                writeB = new PrintWriter(outB, true);

                sendMsgTo(writeB, game_type);
                sendMsgTo(writeA, "joined");

                move_counter = 1;

                while (!finished){
                    while (true) {
                        msgin = readA.readLine();
                        System.out.println("Ruch gracza A! Numer ruchu: " +
                                move_counter +
                                " Ruch: (" +
                                msgin.charAt(0) + ", " +
                                msgin.charAt(1) + ") -> (" +
                                msgin.charAt(2) + ", " +
                                msgin.charAt(3) + ")");

                        if (game_logic.isLegal(msgin)) {
                            game_logic.movePawn(msgin);
                            move_counter++;
                            if (game_logic.isFinished()) {
                                finished = true;
                            } else {
                                msgout = "block";
                                sendMsgTo(writeA, msgout);
                                sendMsgTo(writeB, "unblocked" + msgin);
                            }
                            break;
                        } else {
                            msgout = "illegal";
                            sendMsgTo(writeA, msgout);
                        }
                    }
                    while (!finished){
                        msgin = readB.readLine();
                        System.out.println("Ruch gracza B! Numer ruchu: " +
                                move_counter +
                                " Ruch: (" +
                                msgin.charAt(0) + ", " +
                                msgin.charAt(1) + ") -> (" +
                                msgin.charAt(2) + ", " +
                                msgin.charAt(3) + ")");

                        if (game_logic.isLegal(msgin)) {
                            game_logic.movePawn(msgin);
                            if (game_logic.isFinished()) {
                                finished = true;
                            } else {
                                msgout = "block";
                                sendMsgTo(writeB, msgout);
                                sendMsgTo(writeA, "unblocked" + msgin);
                            }
                            break;
                        } else {
                            msgout = "illegal";
                            sendMsgTo(writeB, msgout);
                        }
                    }
                }

                msgout = game_logic.getWinner();

                sendMsgTo(writeA, msgout);
                sendMsgTo(writeB, msgout);
            }
        }
    }

    private static void playerJoinedInfo(int i) {
        System.out.println("Player joined! Player number: " + i);
    }

    private static String getMsgFrom(BufferedReader readA) throws IOException {
        return readA.readLine();
    }

    private static void sendMsgTo(PrintWriter writeA, String choose) {
        writeA.print(choose);
    }
}
