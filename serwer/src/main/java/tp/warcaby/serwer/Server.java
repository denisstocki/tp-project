package tp.warcaby.serwer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Base class of connection from server to clients
 * */
public class Server {
    /**
     * Main server loop
     * */
    public static void main(String[] args) throws IOException {

        try(ServerSocket serverSocket = new ServerSocket(4444)){

            System.out.println("[SERVER IS WORKING ON PORT 4444]\n");

            String gameType;
            Scanner readA, readB;
            PrintWriter writeA, writeB;
            Gameable gameable;
            BotThread bot;
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

                String playerType = readMessageFrom(readA);

                if("bot".equals(playerType)){
                    System.out.println("[PLAYER NUMBER (BOT): "+ ++playerNumber + "JOINED THE GAME...] (black pawns)\n" +
                            "[THE GAME NUMBER: " + gameNumber + " WILL SOON START...]\n");
                    bot = new BotThread(readA, writeA);
                    bot.start();
                    continue;
                }

                sendMessageTo(writeA, "choose");
                System.out.println("[SENT MESSAGE TO WHITE PAWN PLAYER]: 'choose'\n");

                gameType = readMessageFrom(readA);
                System.out.println("[RECEIVED MESSAGE FROM WHITE PAWN PLAYER]: '" + gameType + "'\n");

                switch (gameType) {
                    case "classic" -> gameable = new ClassicCheckers();
                    case "english" -> gameable = new EnglishCheckers();
                    case "overtaking" -> gameable = new OvertakingCheckers();
                    case "polish" -> gameable = new PolishCheckers();
                    default -> {
                        sendMessageTo(writeA, "error");
                        System.out.println("[SENT MESSAGE TO WHITE PAWN PLAYER]: 'error'\n");
                        serverSocket.close();
                    }
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

                assert gameable != null;

                game_loop : while (!isFinished) {
                    while (true) {
                        sendMessageTo(writeA, gameable.getCurrentBestMoves());
                        System.out.println("server = 1");
                        if(!readA.hasNext()){
                            System.out.println("server = 2");
                            sendMessageTo(writeB, "error");
                            break game_loop;
                        }
                        String move = readA.nextLine();
                        System.out.println("server = 3");

                        System.out.println("[MOVE NUMBER: " + ++moveCounter + "] [WHITE PAWN PLAYER MOVE IS: " + move + "]\n");


                        gameable.considerMove(move);
                        System.out.println("server = 4");

                        String whiteRespond = gameable.getWhiteRespond();
                        String blackRespond = gameable.getBlackRespond();
                        System.out.println("server = 5");

                        if(whiteRespond != null){
                            System.out.println("server = 6");

                            sendMessageTo(writeA, whiteRespond);
                            System.out.println("[SENT MESSAGE TO WHITE PLAYER]: " + whiteRespond);
                        }
                        if(blackRespond != null){
                            System.out.println("server = 7");

                            sendMessageTo(writeB, blackRespond);
                            System.out.println("[SENT MESSAGE TO BLACK PLAYER]: " + blackRespond);
                        }

                        if(gameable.isFinished()){
                            System.out.println("server = 8");

                            isFinished = true;
                            break;
                        } else if (gameable.moveEnded()) {
                            System.out.println("server = 9");

                            break;
                        }
                        System.out.println("server = 10");

                    }
                    while (!isFinished){
                        sendMessageTo(writeB, gameable.getCurrentBestMoves());

                        if(!readB.hasNext()){
                            sendMessageTo(writeA, "error");
                            break game_loop;
                        }

                        String move = readB.nextLine();
                        System.out.println("[MOVE NUMBER: " + ++moveCounter + "] [BLACK PAWN PLAYER MOVE IS: " + move + "]\n");

                        gameable.considerMove(move);

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
                            break;
                        } else if (gameable.moveEnded()) {
                            break;
                        }
                    }
                }
            }
        }
    }
    /**
     * Read message from client to server
     * */
    private static String readMessageFrom(Scanner playerReader) {
        return playerReader.nextLine();
    }

    /**
     * Send message via sockets to client
     * */
    private static void sendMessageTo(PrintWriter playerWriter, String messageContent) {
        playerWriter.println(messageContent);
    }
}
