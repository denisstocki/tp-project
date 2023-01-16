package tp.warcaby.serwer;

import java.util.ArrayList;
import java.util.LinkedList;

public class LogicTest extends GameLogic{

    private final int size;
    private ArrayList<String> bestMoves;
    private ArrayList<String> defaultMoves;
    private ArrayList<ArrayList<String>> boardClone;
    private LinkedList<String> latestMoves;
    private StringBuilder combinationClone;
    private int index;
    private int max;
    private TurnState turn;
    private String whiteRespond;
    private String blackRespond;
    private boolean turned;
    private boolean finished;
    private boolean repeated;
    private boolean whiteBlocked;
    private boolean blackBlocked;
    private FinishState winner;
    private int movesWithoutCapture;
    private int whiteDeaths;
    private int blackDeaths;
    private boolean moveEnded;

    public LogicTest(int size, int whiteCount, int blackCount) {
        super(size, whiteCount, blackCount);

        this.size = size;

        turn = TurnState.WHITE;
        latestMoves = new LinkedList<>();
        index = 0;
        max = 0;

        whiteRespond = "";
        blackRespond = "";

        winner = FinishState.NOONE;

        turned = false;
        finished = false;
        repeated = false;
        whiteBlocked = false;
        blackBlocked = false;
        moveEnded = false;

        createBestMoves();
    }

    private void showBoard(ArrayList<ArrayList<String>> board) {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                System.out.print(board.get(row).get(col) + " ");
            }
            System.out.println();
        }
    }



    private void createBestMoves() {
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
            System.out.println("bestMoves przed zmianami 2 = " + bestMoves);
            bestMoves = deduplicateMoves(maximizeMoves(findMaxMove()));
        }
        System.out.println(bestMoves);
        showBoard(board);
    }

    private ArrayList<String> deduplicateMoves(ArrayList<String> maximizeMoves) {
        ArrayList<String> temp = new ArrayList<>();
        for (String string : maximizeMoves) {
            if(!temp.contains(string)){
                temp.add(string);
            }
        }
        System.out.println("temp = " + temp);
        return temp;
    }

    private ArrayList<String> maximizeMoves(int max) {
        ArrayList<String> tempList = new ArrayList<>();

        for (String string : bestMoves) {
            if(string.length() == max * 4){
                tempList.add(string);
            }
        }
        System.out.println("tempList = " + tempList);
        return tempList;
    }

    private int findMaxMove() {
        int result = 0;

        for (String string : bestMoves) {
            if(string.length() > result){
                result = string.length();
            }
        }
        System.out.println("result/4 = " + result/4);
        return result/4;
    }

    private void getPossibleMovesFor(ArrayList<ArrayList<String>> board, int x, int y) {
        String pawn = board.get(x).get(y);

        if (pawn.contains("Q")){
            addQueensPossibilities(board, x, y, x + 1, y + 1, 1, 1);
            addQueensPossibilities(board, x, y, x + 1, y - 1, 1, -1);
            addQueensPossibilities(board, x, y, x - 1, y + 1, -1, 1);
            addQueensPossibilities(board, x, y, x - 1, y - 1, -1, -1);
        } else {
            if(pawn.contains("W")){
                if(x - 1 >= 0 & y + 1 < size){
                    if(board.get(x - 1).get(y + 1).equals("E")){
                        bestMoves.add("" + x + y + (x - 1) + (y + 1));
                    }
                }
                if(x - 1 >= 0 & y - 1 >= 0){
                    if(board.get(x - 1).get(y - 1).equals("E")){
                        bestMoves.add("" + x + y + (x - 1) + (y - 1));
                    }
                }
            } else {
                if(x + 1 < size & y + 1 < size){
                    if(board.get(x + 1).get(y + 1).equals("E")){
                        bestMoves.add("" + x + y + (x + 1) + (y + 1));
                    }
                }
                if(x + 1 < size & y - 1 >= 0){
                    if(board.get(x + 1).get(y - 1).equals("E")){
                        bestMoves.add("" + x + y + (x + 1) + (y - 1));
                    }
                }
            }
        }
    }

    private void addQueensPossibilities(ArrayList<ArrayList<String>> board, int x, int y, int tempX, int tempY, int dx, int dy) {
        while (true){
            if(tempX >= 0 & tempX < size & tempY >= 0 & tempY < size){
                if(!board.get(tempX).get(tempY).equals("E")){
                    break;
                } else {
                    bestMoves.add("" + x + y + (tempX) + (tempY));
                    tempX += dx;
                    tempY += dy;
                }
            } else {
                break;
            }
        }
    }

    private void findMovesRecursive(ArrayList<ArrayList<String>> board, int row, int col, String ownCode, String enemyCode, String pawn, StringBuilder combination) {
        if (pawn.contains("Q")){
            if(hasKill(board, row, col, enemyCode, pawn, 1, 1)){
                int tempX = row + 1;
                int tempY = col + 1;
                while(!board.get(tempX).get(tempY).contains(enemyCode)){
                    tempX++;
                    tempY++;
                }
                tempX++;
                tempY++;
                findQueensEmpties(board, row, col, ownCode, enemyCode, pawn, combination, tempX, tempY, 1, 1);
            } else if(!combination.toString().equals("")){
                bestMoves.add(combination.toString());
            }
            if(hasKill(board, row, col, enemyCode, pawn, 1, -1)){
                int tempX = row + 1;
                int tempY = col - 1;
                while(!board.get(tempX).get(tempY).contains(enemyCode)){
                    tempX++;
                    tempY--;
                }
                tempX++;
                tempY--;
                findQueensEmpties(board, row, col, ownCode, enemyCode, pawn, combination, tempX, tempY, 1, -1);
            } else if(!combination.toString().equals("")){
                bestMoves.add(combination.toString());
            }
            if(hasKill(board, row, col, enemyCode, pawn, -1, 1)){
                int tempX = row - 1;
                int tempY = col + 1;
                while(!board.get(tempX).get(tempY).contains(enemyCode)){
                    tempX--;
                    tempY++;
                }
                tempX--;
                tempY++;
                findQueensEmpties(board, row, col, ownCode, enemyCode, pawn, combination, tempX, tempY, -1, 1);
            } else if(!combination.toString().equals("")){
                bestMoves.add(combination.toString());
            }
            if(hasKill(board, row, col, enemyCode, pawn, -1, -1)){
                int tempX = row - 1;
                int tempY = col - 1;
                while(!board.get(tempX).get(tempY).contains(enemyCode)){
                    tempX--;
                    tempY--;
                }
                tempX--;
                tempY--;
                findQueensEmpties(board, row, col, ownCode, enemyCode, pawn, combination, tempX, tempY, -1, -1);
            } else if(!combination.toString().equals("")){
                bestMoves.add(combination.toString());
            }
        } else {
            if(hasKill(board, row, col, enemyCode, pawn, 1, 1)){
                System.out.println("Has kill 1");
                boardClone = doubleCopyOf(board);
                forkKill(board, row, col, ownCode, enemyCode, pawn, combination, row + 2, col + 2);
            } else if(!combination.toString().equals("")){
                bestMoves.add(combination.toString());
            }
            if(hasKill(board, row, col, enemyCode, pawn, 1, -1)){
                System.out.println("Has kill 2");
                boardClone = doubleCopyOf(board);
                forkKill(board, row, col, ownCode, enemyCode, pawn, combination, row + 2, col - 2);
            } else if(!combination.toString().equals("")){
                bestMoves.add(combination.toString());
            }
            if(hasKill(board, row, col, enemyCode, pawn, -1, 1)){
                System.out.println("Has kill 3");
                boardClone = doubleCopyOf(board);
                forkKill(board, row, col, ownCode, enemyCode, pawn, combination, row - 2, col + 2);
            } else if(!combination.toString().equals("")){
                bestMoves.add(combination.toString());
            }
            if(hasKill(board, row, col, enemyCode, pawn, -1, -1)){
                System.out.println("Has kill 4");
                boardClone = doubleCopyOf(board);
                forkKill(board, row, col, ownCode, enemyCode, pawn, combination, row - 2, col  - 2);
            } else if(!combination.toString().equals("")){
                bestMoves.add(combination.toString());
            }
        }
    }

    private void findQueensEmpties(ArrayList<ArrayList<String>> board, int row, int col, String ownCode, String enemyCode, String pawn, StringBuilder combination, int tempX, int tempY, int dx, int dy) {
        while(true){
            if(tempX >= 0 && tempX < size && tempY >= 0 && tempY < size){
                if("E".equals(board.get(tempX).get(tempY))){
                    boardClone = doubleCopyOf(board);
                    forkKill(board, row, col, ownCode, enemyCode, pawn, combination, tempX, tempY);
                    tempX += dx;
                    tempY += dy;
                } else break;
            } else {
                break;
            }
        }
    }

    private void forkKill(ArrayList<ArrayList<String>> board, int row, int col, String ownCode, String enemyCode, String pawn, StringBuilder combination, int tempX, int tempY) {
        makeMove(boardClone, row, col, tempX, tempY);
        killMove(boardClone, row, col, tempX, tempY);
        combinationClone = new StringBuilder(combination);
        combinationClone.append(row).append(col).append(tempX).append(tempY);
        findMovesRecursive(
                boardClone,
                tempX,
                tempY,
                ownCode,
                enemyCode,
                pawn,
                combinationClone);
    }

    private boolean hasKill(ArrayList<ArrayList<String>> board, int row, int col, String enemyCode, String pawn, int dx, int dy) {

        row += dx;
        col += dy;

        if(pawn.contains("Q")){
            while(true){
                if(row + dx >= 0 && row + dx < size && col + dy >= 0 && col + dy < size &&
                        row >= 0 && row < size && col >= 0 && col < size){
                    System.out.println("queen dx = " + dx + " dy = " + dy + " check");
                    String unknown = board.get(row).get(col);
                    if(unknown.contains(enemyCode)){
                        System.out.println("queen1");
                        return board.get(row + dx).get(col + dy).equals("E");
                    } else if (unknown.contains(String.valueOf(pawn.charAt(0)))) {
                        System.out.println("queen2");

                        return false;
                    } else {
                        System.out.println("queen3");

                        row += dx;
                        col += dy;
                    }
                } else {
                    return false;
                }
            }
        } else {
            if (row + dx >= 0 && row + dx < size && col + dy >= 0 && col + dy < size &&
                    row >= 0 && row < size && col >= 0 && col < size){
                System.out.println("row = " + row);
                System.out.println("row + dx = " + (row +dx));
                System.out.println("col = " + col);
                System.out.println("col + dy = " + (col + dy));
                return board.get(row).get(col).contains(enemyCode) &&
                        board.get(row + dx).get(col + dy).equals("E");
            } else {
                return false;
            }
        }
    }

    private ArrayList<ArrayList<String>> doubleCopyOf(ArrayList<ArrayList<String>> board) {
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
    public void considerMove(String msg) {

        int x1 = getCoord(msg, 0);
        int y1 = getCoord(msg, 1);
        int x2 = getCoord(msg, 2);
        int y2 = getCoord(msg, 3);

        System.out.println("1index = " + index);
        System.out.println("1max = " + max);
        System.out.println("1bestMoves = " + bestMoves);
        System.out.println("1turn = " + turn);
        System.out.println("1white = " + whiteCount);
        System.out.println("1black = " + blackCount);
        System.out.println("max =");
        showBoard();

        if(index < max){
            fetchMove(x1, y1, x2, y2);
            if (index == max){
                System.out.println("index < max");
                changeTurn();
                createBestMoves();
                updateMovesWithoutCapture(x1, y1, x2, y2);
                updateLatestMoves(x1, y1, x2, y2);
                updateMoveEnded();
                updateBlocks();
                updateWinner(msg);
                max = findMaxMove();

                index = 0;
            }
        } else {
            max = findMaxMove();
            index = 0;
            fetchMove(x1, y1, x2, y2);
            if (index == max){
                System.out.println("index else max");

                changeTurn();
                createBestMoves();
                updateMovesWithoutCapture(x1, y1, x2, y2);
                updateLatestMoves(x1, y1, x2, y2);
                updateMoveEnded();
                updateBlocks();
                updateWinner(msg);
                index = 0;
            }
        }

        System.out.println("2index = " + index);
        System.out.println("2max = " + max);
        System.out.println("2bestMoves = " + bestMoves);
        System.out.println("2turn = " + turn);
        System.out.println("2white = " + whiteCount);
        System.out.println("2black = " + blackCount);
        showBoard();
    }

    private void updateMoveEnded() {
        if(index == max){
            moveEnded = true;
        }
    }

    private void updateBlocks() {
        if(isEmpty(bestMoves)){
            if (turn == TurnState.WHITE){
                whiteBlocked = true;
            } else {
                blackBlocked = true;
            }
        }
    }

    public boolean moveEnded(){
        if(moveEnded){
            moveEnded = false;
            return true;
        }
        return false;
    }

    @Override
    public String getCurrentBestMoves() {
        StringBuilder result = new StringBuilder("possible");
        for (String move : bestMoves) {
            result.append(move.substring(4 * index, 4 * index + 4));
        }
        System.out.println("getCurrentBestMoves = " + result);
        return result.toString();
    }

    private void fetchMove(int x1, int y1, int x2, int y2) {
        boolean legal = isLegal(x1, y1, x2, y2, index);
        System.out.println("legal = " + legal);
        if (legal){
            makeMove(board, x1, y1, x2, y2);
            countMove(board, x1, y1, x2, y2);
            killMove(board, x1, y1, x2, y2);
            deleteUntappedMoves(x1, y1, x2, y2);
            index++;
            if(index < max){
                pickCurrentRespond("another", "moved" + x1 + y1 + x2 + y2);
            } else{
                promoteMove(board, x2, y2);
                pickCurrentRespond("accepted", "unblocked" + x1 + y1 + x2 + y2);
            }
        } else {
            pickCurrentRespond("illegal", null);
        }
    }

    private void updateLatestMoves(int x1, int y1, int x2, int y2) {
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

    private void updateMovesWithoutCapture(int x1, int y1, int x2, int y2) {
        String pawn = board.get(x2).get(y2);
        
        if(pawn.contains("Q") && whiteDeaths == 0 && blackDeaths == 0){
            movesWithoutCapture++;
        } else if (whiteDeaths == 1 || blackDeaths == 1) {
            movesWithoutCapture = 0;
        }
    }

    private void updateWinner(String move) {
        if(whiteCount == 0 || whiteBlocked){
            winner = FinishState.BLACK;
            finished = true;
            pickCurrentRespond("black", "black" + move);
        } else if (blackCount == 0 || blackBlocked) {
            winner = FinishState.WHITE;
            finished = true;
            pickCurrentRespond("white", "white" + move);
        } else if (movesWithoutCapture == 30 || repeated) {
            winner = FinishState.TIE;
            finished = true;
            pickCurrentRespond("tie", "tie" + move);
        }
    }

    private boolean isRepeated() {
        for (int i = 0; i < 4; i++) {
            if(!latestMoves.get(i).equals(latestMoves.get(i + 4)) || !latestMoves.get(i).equals(latestMoves.get(i + 8))){
                return false;
            }
        }
        return true;
    }

    private void countMove(ArrayList<ArrayList<String>> board, int x1, int y1, int x2, int y2) {
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
                System.out.println("WHITE GOT KILLED");
                whiteCount--;
                whiteDeaths++;
                break;
            } else if (board.get(x1).get(y1).contains("B")) {
                System.out.println("BLACK GOT KILLED");
                blackCount--;
                blackDeaths++;
                break;
            } else {
                System.out.println("NOONE GOT KILLED");

                x1 += dx;
                y1 += dy;
            }
        }
    }

    private void promoteMove(ArrayList<ArrayList<String>> board, int x2, int y2) {
        String pawn = board.get(x2).get(y2);

        if ("W".equals(pawn) && x2 == 0){
            board.get(x2).set(y2, "WQ");
        } else if ("B".equals(pawn) && x2 == size - 1) {
            board.get(x2).set(y2, "BQ");
        }
    }

    @Override
    public String getWhiteRespond() {
        return whiteRespond;
    }

    @Override
    public String getBlackRespond() {
        return blackRespond;
    }

    private void pickCurrentRespond(String msg1, String msg2) {
        if (turn == TurnState.WHITE){
            whiteRespond = msg1;
            blackRespond = msg2;
        } else {
            blackRespond = msg1;
            whiteRespond = msg2;
        }
    }

    private void deleteUntappedMoves(int x1, int y1, int x2, int y2) {
        for (String combination : bestMoves) {
            if(!isIncludedOn(combination, "" + x1 + y1 + x2 + y2, index)){
                combination = "";
                bestMoves = deleteEmptiesFrom(bestMoves);
            }
        }
    }

    private ArrayList<String> deleteEmptiesFrom(ArrayList<String> bestMoves) {
        ArrayList<String> temp = new ArrayList<>();
        for (String combination : bestMoves) {
            if(!combination.equals("")) temp.add(combination);
        }
        return temp;
    }

    public void changeTurn() {
        if(turn == TurnState.WHITE) turn = TurnState.BLACK;
        else turn = TurnState.WHITE;
        turned = true;
    }

    @Override
    public void checkDiagonalForQueen(int x2, int y2, int dx, int dy, int maxX, int maxY, String enemy) {

    }

    private void killMove(ArrayList<ArrayList<String>> board, int x1, int y1, int x2, int y2) {
        int dx, dy;

        if(x2 > x1) dx = 1;
        else dx = -1;

        if(y2 > y1) dy = 1;
        else dy = -1;

        x1 += dx;
        y1 += dy;

        while (x1 != x2 && y1 != y2){
            board.get(x1).set(y1, "E");
            System.out.println("siema");
            x1 += dx;
            y1 += dy;
        }
    }

    private void makeMove(ArrayList<ArrayList<String>> board, int x1, int y1, int x2, int y2) {
        board.get(x2).set(y2, board.get(x1).get(y1));
        board.get(x1).set(y1, "E");
    }

    private boolean isLegal(int x1, int y1, int x2, int y2, int index) {
        for (String combination : bestMoves) {
            if(isIncludedOn(combination, "" + x1 + y1 + x2 + y2, index)){
                return true;
            }
        }
        return false;
    }

    private boolean isIncludedOn(String combination, String move, int index) {
        return combination.substring(4 * index, 4 * index + 4).equals(move);
    }


    private boolean isEmpty(ArrayList<String> bestMoves) {
        for (String string : bestMoves) {
            if(string.length() > 0){
                return false;
            }
        }
        return true;
    }

    public int getCoord(String move, int arg) {
        return Integer.parseInt(String.valueOf(move.charAt(arg)));
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public boolean hasTurned() {
        if(turned){
            turned = false;
            return true;
        } else return false;
    }

    @Override
    public void createRespond(String move, String color) {

    }

    @Override
    public boolean isLegal(String move, String color, boolean create) {
        return false;
    }

    @Override
    public void createMustMoves(String move) {

    }

    @Override
    public void createKillCount(String move) {

    }

    @Override
    public boolean isBetrayalMove(String move) {
        return false;
    }

    @Override
    public boolean isFairMove(String move) {
        return false;
    }

    @Override
    public boolean isMustMove(String move) {
        return false;
    }

    @Override
    public boolean isCorrectDiagonal(String move) {
        return false;
    }

    @Override
    public boolean hasMustMoves() {
        return false;
    }

    @Override
    public boolean followsBestMove(String move, int bestIndex) {
        return false;
    }
}
