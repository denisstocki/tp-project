package tp.warcaby.serwer;

import java.util.*;
/*
 * class holding base of every checkers logic
 * */
abstract class GameLogic implements Gameable {
    /*
     * It holds server copy of checkers board
     * */
    public ArrayList<ArrayList<String>> board;

    /*
     * Keeps track of latest made moves
     * */
    public LinkedList<String> latestMoves;
    /*
     * Tells which player is currently eligible to maek a move
     * */
    /*
     * Holds state of game, if it is finished or already ended
     * */
    public boolean finished;
    /*
     * Keeps tracks if there was at least one round played
     * */
    /*
     * Holds info how game ended
     * */
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
     * Repetition of moves forcing draw in game
     * */
    public boolean repeated;
    /*
     * Keep track of movements without captures of any pawn
     * */
    public int movesWithoutCapture;
    public final int size;
    public ArrayList<String> bestMoves;
    public ArrayList<ArrayList<String>> boardClone;
    public StringBuilder combinationClone;
    public int index;
    public int max;
    public TurnState turn;
    public boolean turned;
    public FinishState winner;
    public int whiteDeaths;
    public int blackDeaths;
    public boolean moveEnded;

    public String course = "";



    /*
     * Base constructor
     * */
    public GameLogic(int size, int whiteCount, int blackCount) {
        this.size = size;
        this.whiteCount = whiteCount;
        this.blackCount = blackCount;

        board = new ArrayList<>(size);
        latestMoves = new LinkedList<>();

        turn = TurnState.WHITE;
        winner = FinishState.NOONE;

        index = 0;
        max = 0;

        whiteRespond = "";
        blackRespond = "";

        turned = false;
        finished = false;
        repeated = false;
        whiteBlocked = false;
        blackBlocked = false;
        moveEnded = false;

        movesWithoutCapture = 0;

        initialize();
    }
    @Override
    public void createBestMoves() {
        bestMoves = new ArrayList<>();
        ArrayList<Integer> xCoords = new ArrayList<>();
        ArrayList<Integer> yCoords = new ArrayList<>();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if(board.get(row).get(col).contains(turn.getOwnCode())){
                    xCoords.add(row);
                    yCoords.add(col);
                    ArrayList<ArrayList<String>> copyOfBoard = doubleCopyOf(board);
                    StringBuilder combination = new StringBuilder();
                    findMovesRecursive(
                            copyOfBoard,
                            row,
                            col,
                            turn.getOwnCode(),
                            turn.getEnemyCode(),
                            copyOfBoard.get(row).get(col),
                            combination);
                }
            }
        }

        if(isEmpty(bestMoves)){
            for (int i = 0; i < xCoords.size(); i++) {
                getPossibleMovesFor(board, xCoords.get(i), yCoords.get(i));
            }
        } else {
            bestMoves = deduplicateMoves(maximizeMoves(findMaxMove()));
        }
    }
    @Override
    public ArrayList<String> deduplicateMoves(ArrayList<String> moves) {
        ArrayList<String> temp = new ArrayList<>();
        for (String string : moves) {
            if(!temp.contains(string)){
                temp.add(string);
            }
        }
        return temp;
    }
    @Override
    public ArrayList<String> maximizeMoves(int max) {
        ArrayList<String> tempList = new ArrayList<>();

        for (String string : bestMoves) {
            if(string.length() == max * 4){
                tempList.add(string);
            }
        }
        return tempList;
    }
    @Override
    public int findMaxMove() {
        int result = 0;

        for (String string : bestMoves) {
            if(string.length() > result){
                result = string.length();
            }
        }
        return result/4;
    }
    @Override
    public ArrayList<ArrayList<String>> doubleCopyOf(ArrayList<ArrayList<String>> board) {
        ArrayList<ArrayList<String>> tempBoard = new ArrayList<>();

        for (int row = 0; row < size; row++) {
            ArrayList<String> tempRowList = new ArrayList<>();
            for (int col = 0; col < size; col++) {
                tempRowList.add(board.get(row).get(col));
            }
            tempBoard.add(tempRowList);
        }

        return tempBoard;
    }

    @Override
    public boolean isRepeated() {
        for (int i = 0; i < 4; i++) {
            if(!latestMoves.get(i).equals(latestMoves.get(i + 4)) || !latestMoves.get(i).equals(latestMoves.get(i + 8))){
                return false;
            }
        }
        return true;
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
    public void pickCurrentRespond(String msg1, String msg2) {
        if (turn == TurnState.WHITE){
            whiteRespond = msg1;
            blackRespond = msg2;
        } else {
            blackRespond = msg1;
            whiteRespond = msg2;
        }
    }

    @Override
    public boolean isIncludedOn(String combination, String move, int index) {
        return combination.substring(4 * index, 4 * index + 4).equals(move);
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public boolean isEmpty(ArrayList<String> bestMoves) {
        for (String string : bestMoves) {
            if(string.length() > 0){
                return false;
            }
        }
        return true;
    }

    @Override
    public void changeTurn() {
        if(turn == TurnState.WHITE) turn = TurnState.BLACK;
        else turn = TurnState.WHITE;
        turned = true;
    }

    @Override
    public ArrayList<String> deleteEmptiesFrom(ArrayList<String> moves) {
        ArrayList<String> temp = new ArrayList<>();
        for (String combination : moves) {
            if(!combination.equals("")) temp.add(combination);
        }
        return temp;
    }

    @Override
    public void countMove(ArrayList<ArrayList<String>> board, int x1, int y1, int x2, int y2) {
        int dx, dy;

        if(x2 > x1) dx = 1;
        else dx = -1;

        if(y2 > y1) dy = 1;
        else dy = -1;

        x1 += dx;
        y1 += dy;

        whiteDeaths = 0;
        blackDeaths = 0;

        while (x1 != x2 && y1 != y2){
            if (board.get(x1).get(y1).contains("W")){
                whiteCount--;
                whiteDeaths++;
                break;
            } else if (board.get(x1).get(y1).contains("B")) {
                blackCount--;
                blackDeaths++;
                break;
            } else {
                x1 += dx;
                y1 += dy;
            }
        }
    }

    @Override
    public void updateMovesWithoutCapture(int x1, int y1, int x2, int y2) {
        String pawn = board.get(x2).get(y2);

        if(pawn.contains("Q") && whiteDeaths == 0 && blackDeaths == 0){
            movesWithoutCapture++;
        } else if (whiteDeaths == 1 || blackDeaths == 1) {
            movesWithoutCapture = 0;
        }
    }
    
    @Override
    public void updateLatestMoves(int x1, int y1, int x2, int y2) {
        if(latestMoves.size() == 12){
            latestMoves.remove();
            latestMoves.add("" + x1 + y1 + x2 + y2);
            if (isRepeated()){
                repeated = true;
            }
        } else {
            latestMoves.add("" + x1 + y1 + x2 + y2);
        }
    }

    @Override
    public String getCurrentRandomMove(Random random) {
        int rand = random.nextInt(bestMoves.size());
        return bestMoves.get(rand).substring(4 * index, 4 * index + 4);
    }

    @Override
    public String getCurrentBestMoves() {
        StringBuilder result = new StringBuilder("possible");
        for (String move : bestMoves) {
            result.append(move, 4 * index, 4 * index + 4);
        }
        return result.toString();
    }
    @Override
    public boolean moveEnded(){
        if(moveEnded){
            moveEnded = false;
            return true;
        }
        return false;
    }
    @Override
    public void updateBlocks() {
        if(isEmpty(bestMoves)){
            if (turn == TurnState.WHITE){
                whiteBlocked = true;
            } else {
                blackBlocked = true;
            }
        }
    }
    @Override
    public void updateMoveEnded() {
        if(index == max){
            moveEnded = true;
        }
    }
    /*
     * Initialize the board, fill it and prepare for game
     * */
    @Override
    public void initialize() {
        ArrayList<String> rowList;
        for (int row = 0; row < size; row++) {
            rowList = new ArrayList<>(size);
            board.add(rowList);
            if(row < (size/2 - 1)){
                for (int col = 0; col < size; col++) {
                    if((row + col) % 2 == 1) rowList.add("B");
                    else rowList.add("E");
                }
            } else if (row > (size/2)) {
                for (int col = 0; col < size; col++) {
                    if((row + col) % 2 == 1) rowList.add("W");
                    else rowList.add("E");
                }
            } else {
                for (int col = 0; col < size; col++) {
                    rowList.add("E");
                }
            }
        }
    }
    /*
     * Get game finished state
     * */
    /*
     * Make a move on current board
     * */
    /*
     * Check for three time repetitions of movements, then optionally draw game
     * */@Override
    public boolean repetitionCheck() {
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

    @Override
    public int getCoord(String  move, int index) {
        return Integer.parseInt(String.valueOf(move.charAt(index)));
    }

    @Override
    public String getTurn() {
        return turn.toString();
    }

    @Override
    public String getWinner() {
        return winner.toString().toLowerCase();
    }

    public String getCourse(){return course;}

    @Override
    public ArrayList<ArrayList<String>> getBoard(){
        return board;
    }

    protected abstract void getPossibleMovesFor(ArrayList<ArrayList<String>> board, int x, int y);

    protected abstract void addQueensPossibilities(ArrayList<ArrayList<String>> board, int x, int y, int tempX, int tempY, int dx, int dy);

    public abstract void findMovesRecursive(ArrayList<ArrayList<String>> board, int row, int col, String ownCode, String enemyCode, String pawn, StringBuilder combination);

    public abstract void findQueensEmpties(ArrayList<ArrayList<String>> board, int row, int col, String ownCode, String enemyCode, String pawn, StringBuilder combination, int tempX, int tempY, int dx, int dy);

    public abstract void forkKill(ArrayList<ArrayList<String>> board, int row, int col, String ownCode, String enemyCode, String pawn, StringBuilder combination, int tempX, int tempY);

    public abstract boolean hasKill(ArrayList<ArrayList<String>> board, int row, int col, String enemyCode, String pawn, int dx, int dy);

    public abstract void considerMove(String msg);

    public abstract void fetchMove(int x1, int y1, int x2, int y2);

    public abstract void updateWinner(String move);

    public abstract void promoteMove(ArrayList<ArrayList<String>> board, int x2, int y2);

    public abstract void deleteUntappedMoves(int x1, int y1, int x2, int y2);

    public abstract void killMove(ArrayList<ArrayList<String>> board, int x1, int y1, int x2, int y2);

    public abstract void makeMove(ArrayList<ArrayList<String>> board, int x1, int y1, int x2, int y2);

    public abstract boolean isLegal(int x1, int y1, int x2, int y2, int index);
}
