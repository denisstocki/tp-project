package tp.warcaby.serwer;

import java.util.*;
/*
 * class holding base of every checkers logic
 * */
abstract class GameLogic implements Gameable {
    /*
     * It holds server copy of checkers board
     * */
    public List<List<String>> board;
    /*
     * Keeps track of latest made moves
     * */
    public LinkedList<String> latestMoves;
    /*
     * Tells which player is currently eligible to maek a move
     * */
    private TurnState turn;
    /*
     * Holds state of game, if it is finished or already ended
     * */
    public boolean finished;
    /*
     * Keeps tracks if there was at least one round played
     * */
    private boolean turnChanged;
    /*
     * Holds info how game ended
     * */
    public FinishState finishState;
    /*
     * Response for White Player
     * */
    public String whiteRespond;
    /*
     * Response for White Player
     * */
    public String blackRespond;
    /*
     * Keeps track of all possible forced moves
     * */
    public List<String> mustMoves;
    /*
     * Are white pawns blocked and cant make a move?
     * */
    public boolean whiteBlocked;
    /*
     * Are black pawns blocked and cant make a move?
     * */
    public boolean blackBlocked;
    /*
     * Number of white pawns alive
     * */
    public int whiteCount;
    /*
     * Number of black pawns alive
     * */
    public int blackCount;
    /*
     * Number of taken black pawns
     * */
    public int whiteKills;
    /*
     * Number of taken white pawns
     * */
    public int blackKills;
    /*
     * Size of current board played, specifically exact number of rows and columns
     * */
    private final int boardSize;
    /*
     * Keeps track if any pawn was captured yet
     * */
    public boolean unCaptured;
    /*
     * Repetition of moves forcing draw in game
     * */
    public boolean repeated;
    /*
     * Keep track of movements without captures of any pawn
     * */
    public int movesWithoutCapture;
    /*
     * Base constructor
     * */
    public GameLogic(int boardSize, int whiteCount, int blackCount) {
        this.boardSize = boardSize;
        this.whiteCount = whiteCount;
        this.blackCount = blackCount;

        board = new ArrayList<>(boardSize);
        mustMoves = new ArrayList<>();
        latestMoves = new LinkedList<>();

        turn = TurnState.WHITE;
        finishState = FinishState.DEFAULT;

        turnChanged = false;
        whiteBlocked = false;
        blackBlocked = false;
        finished = false;
        unCaptured = false;
        repeated = false;

        movesWithoutCapture = 0;

        initialize();
    }
    /*
     * Print current state of board to standard outpu
     * */
    @Override
    public void showBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                System.out.print(board.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }
    /*
     * Initialize the board, fill it and prepare for game
     * */
    @Override
    public void initialize() {
        ArrayList<String> rowList;
        for (int row = 0; row < boardSize; row++) {
            rowList = new ArrayList<>(boardSize);
            board.add(rowList);
            if(row < (boardSize/2 - 1)){
                for (int col = 0; col < boardSize; col++) {
                    if((row + col) % 2 == 1) rowList.add("B");
                    else rowList.add("E");
                }
            } else if (row > (boardSize/2)) {
                for (int col = 0; col < boardSize; col++) {
                    if((row + col) % 2 == 1) rowList.add("W");
                    else rowList.add("E");
                }
            } else {
                for (int col = 0; col < boardSize; col++) {
                    rowList.add("E");
                }
            }
        }
    }
    /*
     * Get game finished state
     * */
    @Override
    public boolean isFinished() {
        return finished;
    }
    /*
     * Make a move on current board
     * */
    @Override
    public void fetchMove(String move) {
        int x1 = getCoord(move, 0);
        int y1 = getCoord(move, 1);
        int x2 = getCoord(move, 2);
        int y2 = getCoord(move, 3);

        String pawn = board.get(x1).get(y1);

        board.get(x2).set(y2, pawn);

        int dx, dy;

        if(x2 > x1) dx = 1;
        else dx = -1;

        if(y2 > y1) dy = 1;
        else dy = -1;

        while(x1 != x2 && y1 != y2){
            board.get(x1).set(y1, "E");
            x1 += dx;
            y1 += dy;
        }

        if(whiteKills == 0 && blackKills == 0){
            if(pawn.contains("Q")){
                movesWithoutCapture++;
            }
        } else {
            movesWithoutCapture = 0;
        }

        if(latestMoves.size() < 12){
            latestMoves.add(move);
        } else {
            latestMoves.remove();
            latestMoves.add(move);
            if(repetitionCheck()){
                repeated = true;
            }
        }
    }
    /*
     * Check for three time repetitions of movements, then optionally draw game
     * */
    private boolean repetitionCheck() {
        for (int i = 0; i < 4; i++) {
            System.out.println(latestMoves.get(i));
            System.out.println(latestMoves.get(i + 4));
            System.out.println(latestMoves.get(i + 8));
            if(!latestMoves.get(i).equals(latestMoves.get(i + 4)) || !latestMoves.get(i).equals(latestMoves.get(i + 8))){
                return false;
            }
        }
        return true;
    }
    /*
     * Change whose is current turn
     * */
    @Override
    public void changeTurn() {
        if(turn == TurnState.WHITE){
            turn = TurnState.BLACK;
        } else {
            turn = TurnState.WHITE;
        }
        turnChanged = true;
    }
    /*
     * Keeps track wheter turn has changed
     * */
    @Override
    public boolean turned() {
        if(turnChanged){
            turnChanged = false;
            return true;
        } else {
            return false;
        }
    }
    /*
     * Update current Game state, Draw/Win?Continue
     * */
    @Override
    public void updateFinish() {
        if(whiteCount == 0 || whiteBlocked){
            finishState = FinishState.BLACK;
            finished = true;
        } else if (blackCount == 0 || blackBlocked) {
            finishState = FinishState.WHITE;
            finished = true;
        } else if (repeated) {
            finishState = FinishState.TIE;
            finished = true;
        } else if (unCaptured) {
            finishState = FinishState.TIE;
            finished = true;
        }
    }
    @Override
    public int getCoord(String  move, int index) {
        return Integer.parseInt(String.valueOf(move.charAt(index)));
    }

    @Override
    public String getWhiteRespond() {
        return whiteRespond;
    }

    @Override
    public String getBlackRespond() {
        return blackRespond;
    }

    @Override
    public String getTurn() {
        return turn.toString();
    }

    @Override
    public boolean hasMustMoves() {
        return !mustMoves.isEmpty();
    }

    @Override
    public String getWinner() {
        return finishState.toString();
    }
}
