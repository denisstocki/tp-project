package tp.warcaby.serwer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerThread {

    public static void main(String[] args) throws IOException {

        //TODO consider adding setup file to avoid hardcoded data
        try(ServerSocket serverSocket = new ServerSocket(4444)){

            System.out.println("[SERVER IS WORKING ON PORT 4444]\n");

            String gameType, messageOut;
            MoveDecoder currentMove;
            Scanner readA, readB;
            OutputStream outA, outB;
            InputStream inA, inB;
            PrintWriter writeA, writeB;
            GameLogic gameLogic;
            Socket socketA, socketB;
            boolean isFinished;
            int gameNumber, playerNumber, moveCounter;

            gameNumber = 0;
            playerNumber = 0;

            while (true) {

                System.out.println("[WAITING TO START GAME NUMBER]: " + (++gameNumber) + "\n");

                gameLogic = null;
                isFinished = false;
                socketA = serverSocket.accept();

                playerJoinedInfo(++playerNumber);

                readA = new Scanner(socketA.getInputStream());
                writeA = new PrintWriter(socketA.getOutputStream(), true);

                sendMessageTo(writeA, "choose");
                System.out.println("[Sent message]: choose (Player 1)");

                gameType = readMessageFrom(readA);

                switch (gameType){
                    case "classic":
                        gameLogic = new ClassicCheckers();
                        break;
                    case "english":
                        gameLogic = new EnglishCheckers();
                        break;
                    case "overtaking":
                        gameLogic = new OvertakingCheckers();
                        break;
                    case "polsih":
                        gameLogic = new PolishCheckers();
                        break;
                    default:
                        sendMessageTo(writeA, "error");
                        serverSocket.close();
                }

                socketB = serverSocket.accept();

                playerJoinedInfo(++playerNumber);

                readB = new Scanner(socketB.getInputStream());
                writeB = new PrintWriter(socketB.getOutputStream(), true);

                sendMessageTo(writeB, gameType);
                sendMessageTo(writeA, "joined");

                moveCounter = 1;

                while (!isFinished) {
                    while (true) {
                        currentMove = new MoveDecoder(readA.nextLine());
                        System.out.println("Ruch gracza A! Numer ruchu:" + moveCounter + "\n");
                        currentMove.printMove();

                        if (gameLogic.isLegal(currentMove.getMessage())) {
                            gameLogic.movePawn(currentMove.getMessage());
                            moveCounter++;
                            if (gameLogic.isFinished()) {
                                isFinished = true;
                            } else {
                                messageOut = "accepted";
                                sendMessageTo(writeA, messageOut);
                                sendMessageTo(writeB, "unblocked" + currentMove.getMessage());
                            }
                            break;
                        } else {
                            messageOut = "illegal";
                            sendMessageTo(writeA, messageOut);
                        }
                    }
                    while (!isFinished){
                        currentMove = new MoveDecoder(readB.nextLine());
                        System.out.println("Ruch gracza B! Numer ruchu:" + moveCounter + "\n");
                        currentMove.printMove();

                        if (gameLogic.isLegal(currentMove.getMessage())) {
                            gameLogic.movePawn(currentMove.getMessage());
                            if (gameLogic.isFinished()) {
                                isFinished = true;
                            } else {
                                messageOut = "block";
                                sendMessageTo(writeB, messageOut);
                                sendMessageTo(writeA, "unblocked" + currentMove.getMessage());
                            }
                            break;
                        } else {
                            messageOut = "illegal";
                            sendMessageTo(writeB, messageOut);
                        }
                    }
                }

                messageOut = gameLogic.getWinner();

                sendMessageTo(writeA, messageOut);
                sendMessageTo(writeB, messageOut);
            }
        }
    }

    private static void playerJoinedInfo(int playerID) {
        System.out.println("[PLAYER NUMBER: "+ playerID + "JOINED THE GAME]\n");
    }

    private static String readMessageFrom(Scanner playerReader) throws IOException {
        return playerReader.nextLine();
    }

    private static void sendMessageTo(PrintWriter playerWriter, String messageContent) {
        playerWriter.println(messageContent);
    }
}
