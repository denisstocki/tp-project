package tp.warcaby.serwer;

import java.util.*;

abstract class GameLogic implements Gameable {

    public List<List<String>> board;
    public LinkedList<String> latestMoves;
    private TurnState turn;
    public boolean finished;
    private boolean turnChanged;
    public FinishState finishState;
    public String whiteRespond;
    public String blackRespond;
    public List<String> mustMoves;
    public boolean whiteBlocked;
    public boolean blackBlocked;
    public int whiteCount;
    public int blackCount;
    public int whiteKills;
    public int blackKills;
    private final int boardSize;
    public boolean unCaptured;
    public boolean repeated;

    public int movesWithoutCapture;

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

    @Override
    public void showBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                System.out.print(board.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }

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

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public void fetchMove(String move) {
        int x1 = getCoord(move, 0);
        int y1 = getCoord(move, 1);
        int x2 = getCoord(move, 2);
        int y2 = getCoord(move, 3);

        String pawn = board.get(x1).get(y1), enemy;

        if(pawn.contains("W")){
            enemy = "B";
        } else {
            enemy = "W";
        }

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

    private boolean repetitionCheck() {
        for (int i = 0; i < 4; i++) {
            System.out.println(latestMoves.get(i));
            System.out.println(latestMoves.get(i + 4));
            System.out.println(latestMoves.get(i + 8));
            if(!latestMoves.get(i).equals(latestMoves.get(i + 4)) || !latestMoves.get(i).equals(latestMoves.get(i + 8))){
                System.out.println("SIEMKAJKDJAKLDAJ");
                return false;
            }
        }
        return true;
    }

    @Override
    public void changeTurn() {
        if(turn == TurnState.WHITE){
            turn = TurnState.BLACK;
        } else {
            turn = TurnState.WHITE;
        }
        turnChanged = true;
    }

    @Override
    public boolean turned() {
        if(turnChanged){
            turnChanged = false;
            return true;
        } else {
            return false;
        }
    }

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
    public int getCoord(String move, int index) {
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
