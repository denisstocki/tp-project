package tp.warcaby.serwer;

import java.util.ArrayList;

/**
 * Code holding logic for English checkers variant
 * */
public class EnglishCheckers extends ClassicLogic {

    public EnglishCheckers() {
        super(8, 12, 12);
    }

    @Override
    protected void getPossibleMovesFor(ArrayList<ArrayList<String>> board, int x, int y) {
        System.out.println("getPossibleMovesFor");
        String pawn = board.get(x).get(y);

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
            if(pawn.contains("Q")){
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
            if(pawn.contains("Q")){
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
            }
        }
    }

    @Override
    public void findMovesRecursive(ArrayList<ArrayList<String>> board, int row, int col, String ownCode, String enemyCode, String pawn, StringBuilder combination) {
        System.out.println("findMovesRecursive");
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

    @Override
    public boolean hasKill(ArrayList<ArrayList<String>> board, int row, int col, String enemyCode, String pawn, int dx, int dy) {
        System.out.println("hasKill");
        row += dx;
        col += dy;

        if (row + dx >= 0 && row + dx < size && col + dy >= 0 && col + dy < size &&
                row >= 0 && row < size && col >= 0 && col < size){
            return board.get(row).get(col).contains(enemyCode) &&
                    board.get(row + dx).get(col + dy).equals("E");
        } else {
            return false;
        }
    }
}
