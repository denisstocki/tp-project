package tp.warcaby.serwer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/*
 * Base class of connection from server to clients
 * */
public class ServerThread {
    /*
     * Main server loop
     * */
    public static void main(String[] args) throws IOException {

        try(ServerSocket serverSocket = new ServerSocket(4444)){

            System.out.println("[SERVER IS WORKING ON PORT 4444]\n");

            String gameType;
            Scanner readA, readB;
            PrintWriter writeA, writeB;
            Gameable gameable;
            Socket socketA, socketB;
            boolean isFinished;
            int gameNumber, playerNumber, moveCounter;

            gameNumber = 0;
            playerNumber = 0;

            while (true) {

                System.out.println("[WAITING TO START GAME NUMBER " + (++gameNumber) + "]\n");

                gameable = null;
                isFinished = false;

                socketA = serverSocket.accept();
                System.out.println("[PLAYER NUMBER: "+ ++playerNumber + "JOINED THE GAME...] (white pawns)\n" +
                        "[WAITING FOR PLAYER NUMBER: " + (playerNumber + 1) + " TO JOIN THE GAME]\n");

                readA = new Scanner(socketA.getInputStream());
                writeA = new PrintWriter(socketA.getOutputStream(), true);

                sendMessageTo(writeA, "choose");
                System.out.println("[SENT MESSAGE TO WHITE PAWN PLAYER]: 'choose'\n");

                gameType = readMessageFrom(readA);
                System.out.println("[RECEIVED MESSAGE FROM WHITE PAWN PLAYER]: '" + gameType + "'\n");

                switch (gameType){
                    case "classic":
                        gameable = new ClassicCheckers();
                        break;
                    case "english":
                        gameable = new EnglishCheckers();
                        break;
                    case "overtaking":
                        gameable = new OvertakingCheckers();
                        break;
                    case "polish":
                        gameable = new PolishCheckers();
                        break;
                    default:
                        sendMessageTo(writeA, "error");
                        System.out.println("[SENT MESSAGE TO WHITE PAWN PLAYER]: 'error'\n");
                        serverSocket.close();
                }

                socketB = serverSocket.accept();
                System.out.println("[PLAYER NUMBER: "+ ++playerNumber + "JOINED THE GAME...] (black pawns)\n" +
                        "[THE GAME NUMBER: " + gameNumber + " WILL SOON START...]\n");

                readB = new Scanner(socketB.getInputStream());
                writeB = new PrintWriter(socketB.getOutputStream(), true);

                sendMessageTo(writeB, gameType);
                System.out.println("[SENT MESSAGE TO BLACK PAWN PLAYER]: '" + gameType + "'\n");
                sendMessageTo(writeA, "joined");
                System.out.println("[SENT MESSAGE TO WHITE PAWN PLAYER]: 'joined'\n");
                System.out.println("[THE GAME STARTS RIGHT NOW...]");

                moveCounter = 0;

                game_loop : while (!isFinished) {
                    while (true) {

                        if(!readA.hasNext()){
                            sendMessageTo(writeB, "error");
                            break game_loop;
                        }
                        String move = readA.nextLine();
                        System.out.println("[MOVE NUMBER: " + ++moveCounter + "] [WHITE PAWN PLAYER MOVE IS: " + move + "]\n");

                        assert gameable != null;

                        gameable.createRespond(move, "white");

                        String whiteRespond = gameable.getWhiteRespond();
                        String blackRespond = gameable.getBlackRespond();

                        if(whiteRespond != null){
                            sendMessageTo(writeA, whiteRespond);
                            System.out.println("[SENT MESSAGE TO WHITE PLAYER]: " + whiteRespond);
                        }
                        if(blackRespond != null){
                            sendMessageTo(writeB, blackRespond);
                            System.out.println("[SENT MESSAGE TO BLACK PLAYER]: " + blackRespond);
                        }

                        if(gameable.isFinished()){
                            isFinished = true;
                            gameable.showBoard();
                            break;
                        } else if (gameable.turned()) {
                            gameable.showBoard();
                            break;
                        }
                        gameable.showBoard();
                    }
                    while (!isFinished){

                        if(!readB.hasNext()){
                            sendMessageTo(writeA, "error");
                            break game_loop;
                        }

                        String move = readB.nextLine();
                        System.out.println("[MOVE NUMBER: " + ++moveCounter + "] [BLACK PAWN PLAYER MOVE IS: " + move + "]\n");

                        gameable.createRespond(move, "black");

                        String whiteRespond = gameable.getWhiteRespond();
                        String blackRespond = gameable.getBlackRespond();

                        if(whiteRespond != null){
                            sendMessageTo(writeA, whiteRespond);
                            System.out.println("[SENT MESSAGE TO WHITE PLAYER]: " + whiteRespond);
                        }
                        if(blackRespond != null){
                            sendMessageTo(writeB, blackRespond);
                            System.out.println("[SENT MESSAGE TO BLACK PLAYER]: " + blackRespond);
                        }

                        if(gameable.isFinished()){
                            isFinished = true;
                            gameable.showBoard();
                            break;
                        } else if (gameable.turned()) {
                            gameable.showBoard();
                            break;
                        }
                        gameable.showBoard();
                    }
                }
            }
        }
    }
    /*
     * Read message from client to server
     * */
    private static String readMessageFrom(Scanner playerReader) {
        return playerReader.nextLine();
    }

    /*
     * Send message via sockets to client
     * */
    private static void sendMessageTo(PrintWriter playerWriter, String messageContent) {
        playerWriter.println(messageContent);
    }
}
