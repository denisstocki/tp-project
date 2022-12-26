package tp.warcaby.serwer;

import java.util.ArrayList;
import java.util.List;

public class ClassicCheckers implements GameLogic{

    private List<List<String>> board;
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
        ArrayList<String> rowArray;
        board = new ArrayList<>(8);
        for (int row = 0; row < 8; row++) {
            rowArray = new ArrayList<String>(8);
            board.add(rowArray);
            if(row<3){
                for (int col = 0; col < 8; col++) {
                    if((row+col) % 2 == 1) rowArray.add("B");
                    else rowArray.add("E");
                }
            }else if(row>4){
                for (int col = 0; col < 8; col++) {
                    if((row+col) % 2 == 1) rowArray.add("W");
                    else rowArray.add("E");
                }
            }
            else{
                for (int col = 0; col < 8; col++) {
                    rowArray.add("E");
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
        boolean score = false;
        String pawn = board.get(x1).get(y1), jumped;
        String direction = board.get(x2).get(y2);
        System.out.println("Checking pawn " + pawn + " moving from " + x1 +"," + y1 + " to " + x2 + "," + y2);
        if(pawn == "B" || pawn == "W"){
            if(Math.abs(x2-x1)==1 && Math.abs(y2-y1)==1 && direction == "E") score = true;
            else if(Math.abs(x2-x1)==2 && Math.abs(y2-y1)==2 && direction == "E"){
                if(x2>x1) x0 = x1+1;
                else x0 = x1-1;
                if(y2>y1) y0 = y1+1;
                else y0 = y1-1;
                jumped = board.get(x0).get(y0);
                if(pawn == "B" && jumped == "W") score = true;
                else if(pawn == "W" && jumped == "B") score =  true;
                else score = false;
            }
            else score = false;
        }
        else if(pawn == "BQ" || pawn == "WQ"){
            if(Math.abs(x2-x1)==Math.abs(y2-y1) && direction == "E" && (x2 != x1) && (y2 != y1)){
                if(pawn == "WQ" && !isTraitor("WQ", x1, x2, y1, y2) && isFairFight("WQ", x1, x2, y1, y2)) score = true;
                else if(pawn == "BQ" && !isTraitor("BQ", x1, x2, y1, y2) && isFairFight("BQ", x1, x2, y1, y2)) score = true;
                else score = false;
            }
        }
        return score;
    }

    private boolean isFairFight(String queen, int x1, int x2, int y1, int y2) {
        int dx, dy;
        if(x2>x1) dx=1;
        else dx=-1;
        if(y2>y1) dy=1;
        else dy=-1;
        x1 += dx;
        y1 += dy;
        int counter = 0;
        String target;
        if(queen.charAt(0) == 'W') target = "B";
        else target = "W";
        while(x1 != x2 && y1 != y2){
            if(board.get(x1).get(y1) == target) counter++;
        }
        return counter<=1;
    }

    private boolean isTraitor(String queen, int x1, int x2, int y1, int y2) {
        int dx, dy;
        if(x2>x1) dx=1;
        else dx=-1;
        if(y2>y1) dy=1;
        else dy=-1;
        x1 += dx;
        y1 += dy;
        while(x1 != x2 && y1 != y2){
            if(board.get(x1).get(y1) == String.valueOf(queen.charAt(0))) return true;
        }
        return false;
    }

    @Override
    public void movePawn(String move) {

    }

    @Override
    public boolean gameFinished() {
        return white_count == 0 || black_count == 0 || getPossibleMovesFor();
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
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 8; i++) {
            List<String> rowArray = board.get(i);
            for (int j = 0; j < 8; j++) {
                sb.append(rowArray.get(j) + " ");
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
