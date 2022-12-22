package tp.warcaby.serwer;

import java.util.ArrayList;
import java.util.List;

public class ClassicCheckers implements GameLogic{

    private List<List<Character>> board;
    private int white_count;
    private int black_count;

    public ClassicCheckers() {
        initializeBoard();
        white_count = 12;
        black_count = 12;
    }

    public static void main(String[] args) {
        ClassicCheckers classicCheckers = new ClassicCheckers();
        System.out.println(classicCheckers);
        System.out.println("Can move: " + classicCheckers.isLegal("2112"));
    }

    @Override
    public void initializeBoard() {
        ArrayList<Character> rowArray;
        board = new ArrayList<>(8);
        for (int row = 0; row < 8; row++) {
            rowArray = new ArrayList<Character>(8);
            board.add(rowArray);
            if(row<3){
                for (int col = 0; col < 8; col++) {
                    if((row+col) % 2 == 1) rowArray.add('B');
                    else rowArray.add('E');
                }
            }else if(row>4){
                for (int col = 0; col < 8; col++) {
                    if((row+col) % 2 == 1) rowArray.add('W');
                    else rowArray.add('E');
                }
            }
            else{
                for (int col = 0; col < 8; col++) {
                    rowArray.add('E');
                }
            }
        }
    }

    @Override
    public boolean isLegal(String move) {
        int x1 = Integer.parseInt(String.valueOf(move.charAt(0)));
        int y1 = Integer.parseInt(String.valueOf(move.charAt(1)));
        int x2 = Integer.parseInt(String.valueOf(move.charAt(2)));
        int y2 = Integer.parseInt(String.valueOf(move.charAt(3)));
        int x0, y0;
        char pawn = board.get(x1).get(y1), jumped;
        char direction = board.get(x2).get(y2);
        System.out.println("Checking pawn " + pawn + " moving from " + x1 +"," + y1 + " to " + x2 + "," + y2);
        if(pawn == 'B' || pawn == 'W'){
            if(Math.abs(x2-x1)==1 && Math.abs(y2-y1)==1 && direction == 'E') return true;
            if(Math.abs(x2-x1)==2 && Math.abs(y2-y1)==2 && direction == 'E'){
                if(x2>x1) x0 = x1+1;
                else x0 = x1-1;
                if(y2>y1) y0 = y1+1;
                else y0 = y1-1;
                jumped = board.get(x0).get(y0);
                if(pawn == 'B' && jumped == 'W') return true;
                if(pawn == 'W' && jumped == 'B') return true;
            }
        }
        return false;
    }

    @Override
    public void movePawn(String move) {

    }

    @Override
    public boolean gameFinished() {
        return white_count == 0 || black_count == 0;
    }

    @Override
    public String getWinner() {
        if(white_count == 0) return "black";
        else if(black_count == 0) return "white";
        else return "tie";
    }

    @Override
    public String getPossibleMovesFor(int i, int j) {
        return null;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ClassicCheckers{\n");
        for (int i = 0; i < 8; i++) {
            List<Character> rowArray = board.get(i);
            for (int j = 0; j < 8; j++) {
                sb.append(rowArray.get(j) + " ");
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
