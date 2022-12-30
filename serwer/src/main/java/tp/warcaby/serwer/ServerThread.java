package tp.warcaby.serwer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread {

    public static void main(String[] args) throws IOException {

        //TODO consider adding setup file to avoid hardcoded data
        try(ServerSocket serverSocket = new ServerSocket(4444)){

            System.out.println("[SERVER IS WORKING ON PORT 4444]\n");
            
            //TODO consider eplacing the A/B naming convention, for Example First/Second
            String gameType, messageIn, messageOut;
            MoveDecoder currentMove;
            BufferedReader readA, readB;
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

                System.out.println("[WAITING TO START GAME NUMBER: " + (++gameNumber) + "]\n");

                gameLogic = null;
                isFinished = false;
                socketA = serverSocket.accept();

                playerJoinedInfo(++playerNumber);

                inA = socketA.getInputStream();
                outA = socketA.getOutputStream();
                readA = new BufferedReader(new InputStreamReader(inA));
                writeA = new PrintWriter(outA, true);

                sendMessageTo(writeA, "choose");

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
                    default:
                        sendMessageTo(writeA, "error");
                        serverSocket.close();
                }

                socketB = serverSocket.accept();

                playerJoinedInfo(++playerNumber);

                inB = socketB.getInputStream();
                outB = socketB.getOutputStream();
                readB = new BufferedReader(new InputStreamReader(inB));
                writeB = new PrintWriter(outB, true);

                sendMessageTo(writeB, gameType);
                sendMessageTo(writeA, "joined");

                moveCounter = 1;

                while (!isFinished) {
                    while (true) {
                        messageIn = readA.readLine();
                        currentMove = new MoveDecoder(messageIn);
                        System.out.println("Ruch gracza A! Numer ruchu:" + moveCounter + "\n");
                        currentMove.printMove();

                        if (gameLogic.isLegal(messageIn)) {
                            gameLogic.movePawn(messageIn);
                            moveCounter++;
                            if (gameLogic.isFinished()) {
                                isFinished = true;
                            } else {
                                messageOut = "block";
                                sendMessageTo(writeA, messageOut);
                                sendMessageTo(writeB, "unblocked" + messageIn);
                            }
                            break;
                        } else {
                            messageOut = "illegal";
                            sendMessageTo(writeA, messageOut);
                        }
                    }
                    while (!isFinished){
                        messageIn = readB.readLine();
                        currentMove = new MoveDecoder(messageIn);
                        System.out.println("Ruch gracza B! Numer ruchu:" + moveCounter + "\n");
                        currentMove.printMove();

                        if (gameLogic.isLegal(messageIn)) {
                            gameLogic.movePawn(messageIn);
                            if (gameLogic.isFinished()) {
                                isFinished = true;
                            } else {
                                messageOut = "block";
                                sendMessageTo(writeB, messageOut);
                                sendMessageTo(writeA, "unblocked" + messageIn);
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

    private static String readMessageFrom(BufferedReader playerReader) throws IOException {
        return playerReader.readLine();
    }

    private static void sendMessageTo(PrintWriter playerWriter, String messageContent) {
        playerWriter.print(messageContent);
    }
}
