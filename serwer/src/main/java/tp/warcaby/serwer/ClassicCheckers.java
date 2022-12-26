package tp.warcaby.serwer;

import java.util.ArrayList;
import java.util.List;

public class ClassicCheckers implements GameLogic{

    private List<List<String>> board;

    private GameState gameState;
    private GameFinishState gameFinishState;

    private boolean finished;

    public ClassicCheckers() {
        initializeBoard();
        gameState = GameState.WHITE_TURN;
        gameFinishState = GameFinishState.DURING;
        finished = false;
    }

    /*
    public static void main(String[] args) {
        ClassicCheckers classicCheckers = new ClassicCheckers();
        System.out.println(classicCheckers);
        classicCheckers.movePawn("2130");
        System.out.println(classicCheckers.gameFinishState);
        System.out.println(classicCheckers);
        classicCheckers.movePawn("5241");
        System.out.println(classicCheckers.gameFinishState);
        System.out.println(classicCheckers);
        classicCheckers.movePawn("3052");
        System.out.println(classicCheckers.gameFinishState);
        System.out.println(classicCheckers);
        classicCheckers.movePawn("5443");
        System.out.println(classicCheckers.gameFinishState);
        System.out.println(classicCheckers);
        classicCheckers.movePawn("5234");
        System.out.println(classicCheckers.gameFinishState);
        System.out.println(classicCheckers);
        classicCheckers.movePawn("5645");
        System.out.println(classicCheckers.gameFinishState);
        System.out.println(classicCheckers);
        classicCheckers.movePawn("3456");
        System.out.println(classicCheckers.gameFinishState);
        System.out.println(classicCheckers);
        classicCheckers.movePawn("6554");
        System.out.println(classicCheckers.gameFinishState);
        System.out.println(classicCheckers);
        classicCheckers.movePawn("7465");
        System.out.println(classicCheckers.gameFinishState);
        System.out.println(classicCheckers);
        classicCheckers.movePawn("5674");
        System.out.println(classicCheckers.gameFinishState);
        System.out.println(classicCheckers);
        classicCheckers.movePawn("6756");
        System.out.println(classicCheckers.gameFinishState);
        System.out.println(classicCheckers);
        classicCheckers.movePawn("7447");
        System.out.println(classicCheckers.gameFinishState);
        System.out.println(classicCheckers);
        classicCheckers.movePawn("7665");
        System.out.println(classicCheckers.gameFinishState);
        System.out.println(classicCheckers);
        classicCheckers.movePawn("4774");
        System.out.println(classicCheckers.gameFinishState);
        System.out.println(classicCheckers);
        classicCheckers.movePawn("7452");
        System.out.println(classicCheckers.gameFinishState);
        System.out.println(classicCheckers);
        classicCheckers.movePawn("7447");
        System.out.println(classicCheckers);
        System.out.println(classicCheckers.gameFinishState);
        classicCheckers.movePawn("7263");
        System.out.println(classicCheckers);
        System.out.println(classicCheckers.gameFinishState);
        classicCheckers.movePawn("5274");
        System.out.println(classicCheckers);
        System.out.println(classicCheckers.gameFinishState);
        classicCheckers.movePawn("5041");
        System.out.println(classicCheckers);
        System.out.println(classicCheckers.gameFinishState);
        classicCheckers.movePawn("7430");
        System.out.println(classicCheckers);
        System.out.println(classicCheckers.gameFinishState);
        classicCheckers.movePawn("6152");
        System.out.println(classicCheckers);
        System.out.println(classicCheckers.gameFinishState);
        classicCheckers.movePawn("3063");
        System.out.println(classicCheckers);
        System.out.println(classicCheckers.gameFinishState);
        classicCheckers.movePawn("6345");
        System.out.println(classicCheckers);
        System.out.println(classicCheckers.gameFinishState);
        classicCheckers.movePawn("7061");
        System.out.println(classicCheckers);
        System.out.println(classicCheckers.gameFinishState);
        classicCheckers.movePawn("6152");
        System.out.println(classicCheckers);
        System.out.println(classicCheckers.gameFinishState);
        classicCheckers.movePawn("4563");
        System.out.println(classicCheckers);
        System.out.println(classicCheckers.gameFinishState);
        classicCheckers.movePawn("6341");
        System.out.println(classicCheckers);
        System.out.println(classicCheckers.gameFinishState);
    }*/

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
        int counter = 0;
        String target;
        if(queen.charAt(0) == 'W') target = "B";
        else target = "W";
        while(x1 != x2 && y1 != y2){
            x1 += dx;
            y1 += dy;
            if(board.get(x1).get(y1).equals(target)) counter++;
        }
        return counter<=1;
    }

    private boolean isTraitor(String queen, int x1, int x2, int y1, int y2) {
        int dx, dy;
        if(x2>x1) dx=1;
        else dx=-1;
        if(y2>y1) dy=1;
        else dy=-1;
        while(x1 != x2 && y1 != y2){
            x1 += dx;
            y1 += dy;
            if(board.get(x1).get(y1).equals(String.valueOf(queen.charAt(0)))) return true;
        }
        return false;
    }

    @Override
    public void movePawn(String move) {
        int x1 = Integer.parseInt(String.valueOf(move.charAt(0)));
        int y1 = Integer.parseInt(String.valueOf(move.charAt(1)));
        int x2 = Integer.parseInt(String.valueOf(move.charAt(2)));
        int y2 = Integer.parseInt(String.valueOf(move.charAt(3)));
        String figure = board.get(x1).get(y1);
        int dx, dy;
        if(x2>x1) dx=1;
        else dx=-1;
        if(y2>y1) dy=1;
        else dy=-1;
        if("W".equals(figure) && x2 == 0){
            board.get(x2).set(y2, "WQ");
        } else if ("B".equals(figure) && x2 == 7) {
            board.get(x2).set(y2, "BQ");
        } else {
            board.get(x2).set(y2, figure);
        }
        board.get(x1).set(y1, "E");
        x1 += dx;
        y1 += dy;
        while(x1 != x2 && y1 != y2){
            board.get(x1).set(y1, "E");
            x1 += dx;
            y1 += dy;
        }
        if('B' == figure.charAt(0)){
            if(quantityOf('W')==0){
                gameFinishState = GameFinishState.BLACK_WON;
                finished = true;
            }
            else if(!hasMoves('W')){
                gameFinishState = GameFinishState.TIE;
                finished = true;
            }
            else {
                gameState = GameState.WHITE_TURN;
            }
        }
        else {
            if(quantityOf('B')==0){
                gameFinishState = GameFinishState.WHITE_WON;
                finished = true;
            }
            else if(!hasMoves('B')){
                gameFinishState = GameFinishState.TIE;
                finished = true;
            }
            else {
                gameState = GameState.BLACK_TURN;
            }
        }
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public String getWinner() {
        return gameFinishState.name().toLowerCase();
    }

    @Override
    public String getPossibleMovesFor(int x, int y) {
        StringBuilder moves = new StringBuilder("");
        int temp_x, temp_y, temp;
        String figure = board.get(x).get(y);
        if("W".equals(figure) || "B".equals(figure)){
            temp_x = x-1;
            temp_y = y-1;
            if(temp_x >= 0 && temp_y >= 0 && isLegal("" + x + y + temp_x + temp_y)){
                moves.append("|").append(temp_x).append(temp_y);
            }
            temp_x = x-1;
            temp_y = y+1;
            if(temp_x >= 0 && temp_y <= 7 && isLegal("" + x + y + temp_x + temp_y)){
                moves.append("|").append(temp_x).append(temp_y);
            }
            temp_x = x+1;
            temp_y = y-1;
            if(temp_x <= 7 && temp_y >= 0 && isLegal("" + x + y + temp_x + temp_y)){
                moves.append("|").append(temp_x).append(temp_y);
            }
            temp_x = x+1;
            temp_y = y+1;
            if(temp_x <= 7 && temp_y <= 7 && isLegal("" + x + y + temp_x + temp_y)){
                moves.append("|").append(temp_x).append(temp_y);
            }
            temp_x = x-2;
            temp_y = y-2;
            if(temp_x >= 0 && temp_y >= 0 && isLegal("" + x + y + temp_x + temp_y)){
                moves.append("|").append(temp_x).append(temp_y);
            }
            temp_x = x-2;
            temp_y = y+2;
            if(temp_x >= 0 && temp_y <= 7 && isLegal("" + x + y + temp_x + temp_y)){
                moves.append("|").append(temp_x).append(temp_y);
            }
            temp_x = x+2;
            temp_y = y-2;
            if(temp_x <= 7 && temp_y >= 0 && isLegal("" + x + y + temp_x + temp_y)){
                moves.append("|").append(temp_x).append(temp_y);
            }
            temp_x = x+2;
            temp_y = y+2;
            if(temp_x <= 7 && temp_y <= 7 && isLegal("" + x + y + temp_x + temp_y)){
                moves.append("|").append(temp_x).append(temp_y);
            }
        }
        else {
            for (int i = 1; i < 8; i++) {
                temp_x = x-i;
                temp_y = y-i;
                if(temp_x >= 0 && temp_y >= 0 && isLegal("" + x + y + temp_x + temp_y)){
                    moves.append("|").append(temp_x).append(temp_y);
                } else if (temp_x <= 0 || temp_y <= 0) {
                    break;
                }
            }
            for (int i = 1; i < 8; i++) {
                temp_x = x-i;
                temp_y = y+i;
                if(temp_x >= 0 && temp_y <= 7 && isLegal("" + x + y + temp_x + temp_y)){
                    moves.append("|").append(temp_x).append(temp_y);
                } else if (temp_x <= 0 || temp_y >= 8) {
                    break;
                }
            }
            for (int i = 1; i < 8; i++) {
                temp_x = x+i;
                temp_y = y-i;
                if(temp_x <= 7 && temp_y >= 0 && isLegal("" + x + y + temp_x + temp_y)){
                    moves.append("|").append(temp_x).append(temp_y);
                } else if (temp_x >= 8 || temp_y <= 0) {
                    break;
                }
            }
            for (int i = 1; i < 8; i++) {
                temp_x = x+i;
                temp_y = y+i;
                if(temp_x <= 7 && temp_y <= 7 && isLegal("" + x + y + temp_x + temp_y)){
                    moves.append("|").append(temp_x).append(temp_y);
                } else if (temp_x >= 8 || temp_y >= 8) {
                    break;
                }
            }
        }
        return moves.toString();
    }

    @Override
    public int quantityOf(Character color) {
        int counter=0;
        for (int i = 0; i < 8; i++) {
            List<String> rowArray = board.get(i);
            for (int j = 0; j < 8; j++) {
                if(rowArray.get(j).charAt(0) == color) counter++;
            }
        }
        return counter;
    }

    @Override
    public boolean hasMoves(Character color) {
        for (int i = 0; i < 8; i++) {
            List<String> rowArray = board.get(i);
            for (int j = 0; j < 8; j++) {
                if(rowArray.get(j).charAt(0) == color && !getPossibleMovesFor(i, j).equals("")) return true;
            }
        }
        return false;
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
