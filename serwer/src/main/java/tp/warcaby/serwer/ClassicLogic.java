package tp.warcaby.serwer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

abstract class BaseLogic implements GameLogic{
    private List<List<String>> board;

    private GameState gameState;
    private GameFinishState gameFinishState;

    private boolean finished;

    private int boardSize;

    private int movesWithoutCapture = 0;

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public List<List<String>> getBoard() {
        return board;
    }

    public GameFinishState getGameFinishState() {
        return gameFinishState;
    }

    public void setGameFinishState(GameFinishState gameFinishState) {
        this.gameFinishState = gameFinishState;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int getMovesWithoutCapture() {
        return movesWithoutCapture;
    }

    public void setMovesWithoutCapture(int movesWithoutCapture) {
        this.movesWithoutCapture = movesWithoutCapture;
    }

    public BaseLogic() {
        initializeBoard();
        gameState = GameState.WHITE_TURN;
        gameFinishState = GameFinishState.DURING;
        finished = false;
        boardSize = 8;
    }

    public void initializeBoard() {
        ArrayList<String> rowArray;
        board = new ArrayList<>(boardSize);
        for (int row = 0; row < boardSize; row++) {
            rowArray = new ArrayList<String>(boardSize);
            board.add(rowArray);
            if(row<(boardSize)/2 - 1){
                for (int col = 0; col < boardSize; col++) {
                    if((row+col) % 2 == 1) rowArray.add("B");
                    else rowArray.add("E");
                }
            }else if(row>(boardSize/2)){
                for (int col = 0; col < boardSize; col++) {
                    if((row+col) % 2 == 1) rowArray.add("W");
                    else rowArray.add("E");
                }
            }
            else{
                for (int col = 0; col < boardSize; col++) {
                    rowArray.add("E");
                }
            }
        }
    }

    public boolean isLegal(String move) {
        int x1 = Integer.parseInt(String.valueOf(move.charAt(0)));
        int y1 = Integer.parseInt(String.valueOf(move.charAt(1)));
        int x2 = Integer.parseInt(String.valueOf(move.charAt(2)));
        int y2 = Integer.parseInt(String.valueOf(move.charAt(3)));
        String pawn = board.get(x1).get(y1), jumped;
        //Checks if player uses his own pawn
        if((Objects.equals(pawn, "B") && gameState == GameState.WHITE_TURN) || (Objects.equals(pawn, "W") && gameState == GameState.BLACK_TURN)) return false;
        int x0, y0;
        boolean score = false;
        String direction = board.get(x2).get(y2);
        System.out.println("Checking pawn " + pawn + " moving from " + x1 +"," + y1 + " to " + x2 + "," + y2);
        if(Objects.equals(pawn, "B") || Objects.equals(pawn, "W")){
            if(Math.abs(x2-x1)==1 && Math.abs(y2-y1)==1 && Objects.equals(direction, "E")) score = true;
            else if(Math.abs(x2-x1)==2 && Math.abs(y2-y1)==2 && Objects.equals(direction, "E")){
                if(x2>x1) x0 = x1+1;
                else x0 = x1-1;
                if(y2>y1) y0 = y1+1;
                else y0 = y1-1;
                jumped = board.get(x0).get(y0);
                if(pawn.equals("B") && Objects.equals(jumped, "W")) score = true;
                else if(pawn.equals("W") && Objects.equals(jumped, "B")) score =  true;
            }
        }
        else if(Objects.equals(pawn, "BQ") || Objects.equals(pawn, "WQ")){
            if(Math.abs(x2-x1)==Math.abs(y2-y1) && Objects.equals(direction, "E") && (x2 != x1) && (y2 != y1)){
                if(pawn.equals("WQ") && !isTraitor("WQ", x1, x2, y1, y2) && isFairFight("WQ", x1, x2, y1, y2) <= 1) score = true;
                else if(pawn.equals("BQ") && !isTraitor("BQ", x1, x2, y1, y2) && isFairFight("BQ", x1, x2, y1, y2) <= 1) score = true;
            }
        }
        return score;
    }

    public int isFairFight(String queen, int x1, int x2, int y1, int y2) {
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
        return counter;
    }

    public boolean isTraitor(String queen, int x1, int x2, int y1, int y2) {
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

        if(++movesWithoutCapture >= 15) {
            gameFinishState = GameFinishState.TIE;
            finished = true;
        }
    }

    public boolean isFinished() {
        return finished;
    }

    public String getWinner() {
        return gameFinishState.name().toLowerCase();
    }

    public String getPossibleMovesFor(int x, int y) {
        StringBuilder moves = new StringBuilder();
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
            if(temp_x >= 0 && temp_y <= boardSize - 1 && isLegal("" + x + y + temp_x + temp_y)){
                moves.append("|").append(temp_x).append(temp_y);
            }
            temp_x = x+1;
            temp_y = y-1;
            if(temp_x <= boardSize - 1 && temp_y >= 0 && isLegal("" + x + y + temp_x + temp_y)){
                moves.append("|").append(temp_x).append(temp_y);
            }
            temp_x = x+1;
            temp_y = y+1;
            if(temp_x <= boardSize - 1 && temp_y <= boardSize - 1 && isLegal("" + x + y + temp_x + temp_y)){
                moves.append("|").append(temp_x).append(temp_y);
            }
            temp_x = x-2;
            temp_y = y-2;
            if(temp_x >= 0 && temp_y >= 0 && isLegal("" + x + y + temp_x + temp_y)){
                moves.append("|").append(temp_x).append(temp_y);
            }
            temp_x = x-2;
            temp_y = y+2;
            if(temp_x >= 0 && temp_y <= boardSize - 1 && isLegal("" + x + y + temp_x + temp_y)){
                moves.append("|").append(temp_x).append(temp_y);
            }
            temp_x = x+2;
            temp_y = y-2;
            if(temp_x <= boardSize - 1 && temp_y >= 0 && isLegal("" + x + y + temp_x + temp_y)){
                moves.append("|").append(temp_x).append(temp_y);
            }
            temp_x = x+2;
            temp_y = y+2;
            if(temp_x <= boardSize - 1 && temp_y <= boardSize - 1 && isLegal("" + x + y + temp_x + temp_y)){
                moves.append("|").append(temp_x).append(temp_y);
            }
        }
        else {
            for (int i = 1; i < boardSize; i++) {
                temp_x = x-i;
                temp_y = y-i;
                if(temp_x >= 0 && temp_y >= 0 && isLegal("" + x + y + temp_x + temp_y)){
                    moves.append("|").append(temp_x).append(temp_y);
                } else if (temp_x <= 0 || temp_y <= 0) {
                    break;
                }
            }
            for (int i = 1; i < boardSize; i++) {
                temp_x = x-i;
                temp_y = y+i;
                if(temp_x >= 0 && temp_y <= boardSize - 1 && isLegal("" + x + y + temp_x + temp_y)){
                    moves.append("|").append(temp_x).append(temp_y);
                } else if (temp_x <= 0 || temp_y >= boardSize) {
                    break;
                }
            }
            for (int i = 1; i < boardSize; i++) {
                temp_x = x+i;
                temp_y = y-i;
                if(temp_x <= boardSize - 1 && temp_y >= 0 && isLegal("" + x + y + temp_x + temp_y)){
                    moves.append("|").append(temp_x).append(temp_y);
                } else if (temp_x >= boardSize || temp_y <= 0) {
                    break;
                }
            }
            for (int i = 1; i < boardSize; i++) {
                temp_x = x+i;
                temp_y = y+i;
                if(temp_x <= boardSize - 1 && temp_y <= boardSize && isLegal("" + x + y + temp_x + temp_y)){
                    moves.append("|").append(temp_x).append(temp_y);
                } else if (temp_x >= boardSize || temp_y >= boardSize) {
                    break;
                }
            }
        }
        return moves.toString();
    }

    public int quantityOf(Character color) {
        int counter=0;
        for (int i = 0; i < boardSize; i++) {
            List<String> rowArray = board.get(i);
            for (int j = 0; j < boardSize; j++) {
                if(rowArray.get(j).charAt(0) == color) counter++;
            }
        }
        return counter;
    }

    public boolean hasMoves(Character color) {
        for (int i = 0; i < boardSize; i++) {
            List<String> rowArray = board.get(i);
            for (int j = 0; j < boardSize; j++) {
                if(rowArray.get(j).charAt(0) == color && !getPossibleMovesFor(i, j).equals("")) return true;
            }
        }
        return false;
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < boardSize; i++) {
            List<String> rowArray = board.get(i);
            for (int j = 0; j < boardSize; j++) {
                sb.append(rowArray.get(j)).append(" ");
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
