package tp.warcaby.serwer;

import java.util.ArrayList;
import java.util.Random;

/**
 * Simplest interface representing object playable in checkers
 * */
public interface Gameable {


    /*
     * Print current state of board to standard outpu
     * */
    void createBestMoves();

    ArrayList<String> deduplicateMoves(ArrayList<String> moves);

    ArrayList<String> maximizeMoves(int max);

    int findMaxMove();

    ArrayList<ArrayList<String>> doubleCopyOf(ArrayList<ArrayList<String>> board);

    boolean isRepeated();

    String getWhiteRespond();

    String getBlackRespond();

    void pickCurrentRespond(String msg1, String msg2);

    boolean isIncludedOn(String combination, String move, int index);


    boolean isFinished();

    ArrayList<ArrayList<String>> getBoard();

    void considerMove(String move);

    boolean isEmpty(ArrayList<String> bestMoves);

    void changeTurn();

    ArrayList<String> deleteEmptiesFrom(ArrayList<String> moves);

    void countMove(ArrayList<ArrayList<String>> board, int x1, int y1, int x2, int y2);

    void updateMovesWithoutCapture(int x1, int y1, int x2, int y2);

    void updateLatestMoves(int x1, int y1, int x2, int y2);

    String getCurrentBestMoves();

    boolean moveEnded();

    void updateBlocks();

    void updateMoveEnded();

    /*
     * Initialize the board, fill it and prepare for game
     * */
    void initialize();

    /*
     * Get game finished state
     * */
    /*
     * Make a move on current board
     * */
    void fetchMove(int x1, int y1, int x2, int y2);

    /*
     * Check for three time repetitions of movements, then optionally draw game
     * */ boolean repetitionCheck();

    /*
     * Keeps track wheter turn has changed
     * */

    /*
     * Update current Game state, Draw/Win?Continue
     * */

    int getCoord(String move, int index);

    String getTurn();

    String getWinner();

    String getCurrentRandomMove(Random random);
}
