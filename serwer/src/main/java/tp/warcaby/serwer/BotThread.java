package tp.warcaby.serwer;

import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class BotThread extends Thread{

    private final Scanner in;
    private final PrintWriter out;
    private Gameable game;
    private boolean isFinished;
    private Random random;


    public BotThread(Scanner in, PrintWriter out) {
        this.in = in;
        this.out = out;

        isFinished = false;

        random = new Random();
    }

    @Override
    public void start(){

        out.println("choose");
        System.out.println("[SENT MESSAGE TO WHITE PAWN PLAYER]: 'choose'\n");

        String gameType = in.nextLine();
        System.out.println("[RECEIVED MESSAGE FROM WHITE PAWN PLAYER]: '" + gameType + "'\n");

        switch (gameType){
            case "classic" -> game = new ClassicCheckers();
            case "english" -> game = new EnglishCheckers();
            case "overtaking" -> game = new OvertakingCheckers();
            case "polish" -> game = new PolishCheckers();
            default -> {
                out.println("error");
                System.out.println("[SENT MESSAGE TO WHITE PAWN PLAYER]: 'error'\n");
            }
        }

        out.println("joined");
        System.out.println("[SENT MESSAGE TO WHITE PAWN PLAYER]: 'joined'\n");

        game_loop : while (!isFinished) {
            while (true) {
                String moves = game.getCurrentBestMoves();
                out.println(moves);
                System.out.println("[SENT MESSAGE TO WHITE PAWN PLAYER]: " + moves + "\n");

                String move = in.nextLine();
                System.out.println("[RECEIVED MESSAGE FROM WHITE PAWN PLAYER]: '" + move + "'\n");
                game.considerMove(move);
                String whiteRespond = game.getWhiteRespond();
                if(whiteRespond != null){
                    out.println(whiteRespond);
                    System.out.println("[SENT MESSAGE TO WHITE PAWN PLAYER]: " + whiteRespond + "\n");
                }

                if(game.isFinished()){
                    isFinished = true;
                    break;
                } else if (game.moveEnded()) {
                    System.out.println("[PLAYER MOVE ENDED]\n");
                    break;
                }
            }
            while (!isFinished){

                String move = game.getCurrentRandomMove(random);
                System.out.println("[BEST MOVES FOR BOT (black)]: " + game.getCurrentBestMoves() + "\n");
                System.out.println("[GENERATED MOVE FOR BOT (black)]: " + move + "\n");
                game.considerMove(move);
                String whiteRespond = game.getWhiteRespond();

                if(whiteRespond != null){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    out.println(whiteRespond);
                    System.out.println("[SENT MESSAGE TO WHITE PAWN PLAYER]: " + whiteRespond + "\n");
                }

                if(game.isFinished()){
                    isFinished = true;
                    break;
                } else if (game.moveEnded()) {
                    System.out.println("[BOT MOVE ENDED]\n");
                    break;
                }
            }
        }
    }
}
