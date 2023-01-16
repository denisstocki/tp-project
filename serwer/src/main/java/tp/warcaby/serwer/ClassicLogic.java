package tp.warcaby.serwer;

import java.util.ArrayList;

public class ClassicLogic extends GameLogic{

    

    public ClassicLogic(int size, int whiteCount, int blackCount) {
        super(size, whiteCount, blackCount);
        createBestMoves();
    }
    

    
    @Override
    protected void getPossibleMovesFor(ArrayList<ArrayList<String>> board, int x, int y) {
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

    @Override
    protected void addQueensPossibilities(ArrayList<ArrayList<String>> board, int x, int y, int tempX, int tempY, int dx, int dy) {
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

    @Override
    public void findMovesRecursive(ArrayList<ArrayList<String>> board, int row, int col, String ownCode, String enemyCode, String pawn, StringBuilder combination) {
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

    @Override
    public void findQueensEmpties(ArrayList<ArrayList<String>> board, int row, int col, String ownCode, String enemyCode, String pawn, StringBuilder combination, int tempX, int tempY, int dx, int dy) {
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

    @Override
    public void forkKill(ArrayList<ArrayList<String>> board, int row, int col, String ownCode, String enemyCode, String pawn, StringBuilder combination, int tempX, int tempY) {
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

    @Override
    public boolean hasKill(ArrayList<ArrayList<String>> board, int row, int col, String enemyCode, String pawn, int dx, int dy) {

        row += dx;
        col += dy;

        if(pawn.contains("Q")){
            while(true){
                if(row + dx >= 0 && row + dx < size && col + dy >= 0 && col + dy < size &&
                        row >= 0 && row < size && col >= 0 && col < size){
                    String unknown = board.get(row).get(col);
                    if(unknown.contains(enemyCode)){
                        return board.get(row + dx).get(col + dy).equals("E");
                    } else if (unknown.contains(String.valueOf(pawn.charAt(0)))) {
                        return false;
                    } else {
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
                return board.get(row).get(col).contains(enemyCode) &&
                        board.get(row + dx).get(col + dy).equals("E");
            } else {
                return false;
            }
        }
    }

    @Override
    public void considerMove(String msg) {

        int x1 = getCoord(msg, 0);
        int y1 = getCoord(msg, 1);
        int x2 = getCoord(msg, 2);
        int y2 = getCoord(msg, 3);


        if(index < max){
            fetchMove(x1, y1, x2, y2);
            if (index == max){
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
    }
    
    @Override
    public void fetchMove(int x1, int y1, int x2, int y2) {
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

    @Override
    public void updateWinner(String move) {
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

    @Override
    public void promoteMove(ArrayList<ArrayList<String>> board, int x2, int y2) {
        String pawn = board.get(x2).get(y2);

        if ("W".equals(pawn) && x2 == 0){
            board.get(x2).set(y2, "WQ");
        } else if ("B".equals(pawn) && x2 == size - 1) {
            board.get(x2).set(y2, "BQ");
        }
    }
    
    

    @Override
    public void deleteUntappedMoves(int x1, int y1, int x2, int y2) {
        for (String combination : bestMoves) {
            if(!isIncludedOn(combination, "" + x1 + y1 + x2 + y2, index)){
                bestMoves = deleteEmptiesFrom(bestMoves);
            }
        }
    }
    

    @Override
    public void killMove(ArrayList<ArrayList<String>> board, int x1, int y1, int x2, int y2) {
        int dx, dy;

        if(x2 > x1) dx = 1;
        else dx = -1;

        if(y2 > y1) dy = 1;
        else dy = -1;

        x1 += dx;
        y1 += dy;

        while (x1 != x2 && y1 != y2){
            board.get(x1).set(y1, "E");
            x1 += dx;
            y1 += dy;
        }
    }

    @Override
    public void makeMove(ArrayList<ArrayList<String>> board, int x1, int y1, int x2, int y2) {
        board.get(x2).set(y2, board.get(x1).get(y1));
        board.get(x1).set(y1, "E");
    }

    @Override
    public boolean isLegal(int x1, int y1, int x2, int y2, int index) {
        for (String combination : bestMoves) {
            if(isIncludedOn(combination, "" + x1 + y1 + x2 + y2, index)){
                return true;
            }
        }
        return false;
    }
}
