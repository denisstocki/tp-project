package tp.warcaby.serwer;

import java.util.ArrayList;
/**
 * Logic containing most common rules and settings shared by most of variants
 * */
public abstract class ClassicLogic extends GameLogic{

    private final int size;
    public ClassicLogic(int size, int whiteCount, int blackCount) {
        super(size, whiteCount, blackCount);
        this.size = size;
    }

    /**
     * Respond for Player after making move
     * */
    @Override
    public void createRespond(String move, String color) {
        String enemy;

        if("white".equals(color)){
            enemy = "B";
        } else {
            enemy = "W";
        }

        if(isLegal(move, color, true)){

            System.out.println(mustMoves + "legal");

            updateCount();
            updateBlock(enemy);
            updateFinish();
            System.out.println(latestMoves  );
            System.out.println(repeated);
            if(isFinished()){
                if("white".equalsIgnoreCase(getWinner())){
                    whiteRespond = "white";
                    blackRespond = "white" + move;
                } else if ("black".equalsIgnoreCase(getWinner())) {
                    blackRespond = "white";
                    whiteRespond = "white" + move;
                } else {
                    if(repeated){
                        if ("white".equals(color)) {
                            whiteRespond = "repeated";
                            blackRespond = "repeated" + move;
                        } else {
                            whiteRespond = "repeated" + move;
                            blackRespond = "repeated";
                        }
                    } else if(unCaptured){
                        if ("white".equals(color)) {
                            whiteRespond = "uncaptured";
                            blackRespond = "uncaptured" + move;
                        } else {
                            whiteRespond = "uncaptured" + move;
                            blackRespond = "uncaptured";
                        }
                    } else {
                        if ("white".equals(color)) {
                            whiteRespond = "tie";
                            blackRespond = "tie" + move;
                        } else {
                            whiteRespond = "tie" + move;
                            blackRespond = "tie";
                        }
                    }
                }
            }
        }
        System.out.println("(game)[RESPOND FOR " + color.toUpperCase() + " PLAYER PREPARED]: " + move + " [WHITE]: " + whiteRespond + " [BLACK]: " + blackRespond);
    }

    /**
     * Check and update info whether someone is blocked
     * */
    private void updateBlock(String color) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(board.get(i).get(j).contains(color) && possibleMovesFor(i, j) > 0){
                    return;
                }
            }
        }
        if("W".equals(color)){
            whiteBlocked = true;
        } else {
            blackBlocked = true;
        }
    }
    /**
     * Count possible moves for a pawn position
     * */
    private int possibleMovesFor(int i, int j) {
        String pawn = board.get(i).get(j);
        String color, enemy;

        int tempX, tempY, counter = 0;

        if(pawn.charAt(0) == 'W'){
            color = "white";
            enemy = "B";
        }
        else{
            color = "black";
            enemy = "W";
        }

        if(pawn.contains("Q")){
            tempX = i + 1;
            tempY = j + 1;

            while(tempX < size && tempY < size){
                createKillCount("" + i + j + tempX + tempY);
                if(isLegal("" + i + j + tempX + tempY, color, false)) counter++;
                tempX++;
                tempY++;
            }

            tempX = i + 1;
            tempY = j - 1;

            while(tempX < size && tempY > 0){
                if(isLegal("" + i + j + tempX + tempY, color, false)) counter++;
                tempX++;
                tempY--;
            }

            tempX = i - 1;
            tempY = j + 1;

            while(tempX > 0 && tempY < size){
                if(isLegal("" + i + j + tempX + tempY, color, false)) counter++;
                tempX--;
                tempY++;
            }

            tempX = i - 1;
            tempY = j - 1;

            while(tempX > 0 && tempY > 0){
                if(isLegal("" + i + j + tempX + tempY, color, false)) counter++;
                tempX--;
                tempY--;
            }
        } else {
            if(i + 1 <= size - 1 && j + 1 <= size - 1){
                if(board.get(i + 1).get(j + 1).equals("E")) counter++;
                if(i + 2 <=size && j + 2 <= size){
                    if(board.get(i + 1).get(j + 1).contains(enemy) && board.get(i + 2).get(j + 2).equals("E")) counter++;
                }
            }
            if(i + 1 <= size - 1 && j - 1 >= 0){
                if(board.get(i + 1).get(j - 1).equals("E")) counter++;
                if(i + 2 <=size - 1 && j - 2 >= 0){
                    if(board.get(i + 1).get(j - 1).contains(enemy) && board.get(i + 2).get(j - 2).equals("E")) counter++;
                }
            }
            if(i - 1 >= 0 && j + 1 <= size - 1){
                if(board.get(i - 1).get(j + 1).equals("E")) counter++;
                if(i - 2 >= 0 && j + 2 <= size - 1){
                    if(board.get(i - 1).get(j + 1).contains(enemy) && board.get(i - 2).get(j + 2).equals("E")) counter++;
                }
            }
            if(i - 1 >= 0 && j - 1 >= 0){
                if(board.get(i - 1).get(j - 1).equals("E")) counter++;
                if(i - 2 >= 0 && j - 2 >= 0){
                    if(board.get(i - 1).get(j - 1).contains(enemy) && board.get(i - 2).get(j - 2).equals("E")) counter++;
                }
            }
        }
        return counter;
    }

    /**
     * Update stats of pieces taken
     * */
    private void updateCount() {
        whiteCount -= whiteKills;
        blackCount -= blackKills;
        whiteKills = 0;
        blackKills = 0;
    }

    /**
     * Check whther sent move is legal or not
     * */
    @Override
    public boolean isLegal(String move, String color, boolean create) {

        int x1 = getCoord(move, 0);
        int y1 = getCoord(move, 1);
        int x2 = getCoord(move, 2);
        int y2 = getCoord(move, 3);

        if(create) createKillCount(move);

        if(create){
            if("white".equals(color)){
                whiteRespond = "cheat";
                blackRespond = null;
            } else {
                blackRespond = "cheat";
                whiteRespond = null;
            }
        }

        if(x1 < 0 || x1 > size - 1) return false; //8
        if(y1 < 0 || y1 > size - 1) return false;
        if(x2 < 0 || x2 > size - 1) return false;
        if(y2 < 0 || y2 > size - 1) return false;

        if(create) {
            if ("white".equals(color)) {
                whiteRespond = "duplicate";
                blackRespond = null;
            } else {
                blackRespond = "duplicate";
                whiteRespond = null;
            }
        }

        if(x1 == x2 && y1 == y2) return false; //1


        if(create) {
            if ("white".equals(color)) {
                whiteRespond = "betrayal";
                blackRespond = null;
            } else {
                blackRespond = "betrayal";
                whiteRespond = null;
            }
        }

        if(isBetrayalMove(move)) return false; //2


        if(create) {
            if ("white".equals(color)) {
                whiteRespond = "multiple";
                blackRespond = null;
            } else {
                blackRespond = "multiple";
                whiteRespond = null;
            }
        }

        if(!isFairMove(move)) return false; //3


        if(create) {
            if ("white".equals(color)) {
                whiteRespond = "stack";
                blackRespond = null;
            } else {
                blackRespond = "stack";
                whiteRespond = null;
            }
        }

        if(!"E".equals(board.get(x2).get(y2))) return false; //4
        System.out.println("siema");

        if(create) {
            if ("white".equals(color)) {
                whiteRespond = "owner";
                blackRespond = null;
            } else {
                blackRespond = "owner";
                whiteRespond = null;
            }
        }

        if(!board.get(x1).get(y1).contains(getTurn().toString().substring(0, 1))) return false; //6

        if(create) {
            if ("white".equals(color)) {
                whiteRespond = "diagonal";
                blackRespond = null;
            } else {
                blackRespond = "diagonal";
                whiteRespond = null;
            }
        }

        if(!isCorrectDiagonal(move)) return false; //5

        if(create){
            whiteRespond = "repeated";
            blackRespond = "repeated";
        }

        if(create){
            whiteRespond = "uncaptured";
            blackRespond = "uncaptured";
        }

        if(movesWithoutCapture >= 30){
            unCaptured = true;
            return false;
        }

        if(create) {
            if ("white".equals(color)) {
                whiteRespond = "rules";
                blackRespond = null;
            } else {
                blackRespond = "rules";
                whiteRespond = null;
            }
        }

        System.out.println(mustMoves + "111111");
        if(create){
            if(hasMustMoves()){
                if(!isMustMove(move)){
                    return false; //7
                }
            }
            fetchMove(move);
            createMustMoves(move);
            promoteToQueen(move);
        }
        System.out.println(mustMoves + "2222222");

        if(create) {
            if (hasMustMoves()) {
                if ("white".equals(color)) {
                    whiteRespond = "another";
                    blackRespond = "moved" + move;
                } else {
                    blackRespond = "another";
                    whiteRespond = "moved" + move;
                }
            } else {
                if ("white".equals(color)) {
                    whiteRespond = "accepted";
                    blackRespond = "unblocked" + move;
                } else {
                    blackRespond = "accepted";
                    whiteRespond = "unblocked" + move;
                }
                changeTurn();
            }
        }

        return true;
    }

    /**
     * Promotes piece to Queen, updates board
     * */
    public void promoteToQueen(String move) {
        int x1 = getCoord(move, 0);
        int y1 = getCoord(move, 1);
        int x2 = getCoord(move, 2);
        int y2 = getCoord(move, 3);

        if(board.get(x2).get(y2).contains("B") && x2 == size - 1 && !hasMustMoves()){
            board.get(x2).set(y2, "BQ");
        } else if(board.get(x2).get(y2).contains("W") && x2 == 0 && !hasMustMoves()) {
            board.get(x2).set(y2, "WQ");
        }
    }

    /**
     * Fills the list of moves forced to pawn
     * */
    @Override
    public void createMustMoves(String move) {
        int x1 = getCoord(move, 0);
        int y1 = getCoord(move, 1);
        int x2 = getCoord(move, 2);
        int y2 = getCoord(move, 3);

        mustMoves = new ArrayList<>();

        if(whiteKills > 0 || blackKills > 0){
            String pawn = board.get(x2).get(y2);
            String enemy;

            if(pawn.charAt(0) == 'W'){
                enemy = "B";
            } else {
                enemy = "W";
            }

            if(pawn.contains("Q")){
                checkDiagonalForQueen(x2, y2, 1, 1, size - 1, size - 1, enemy);
                checkDiagonalForQueen(x2, y2, 1, -1, size - 1, 0, enemy);
                checkDiagonalForQueen(x2, y2, -1, 1, 0, size - 1, enemy);
                checkDiagonalForQueen(x2, y2, -1, -1, 0, 0, enemy);
            } else {
                if(x2 - 2 >= 0 && y2 - 2 >= 0) {
                    if (board.get(x2 - 1).get(y2 - 1).contains(enemy) &&
                            board.get(x2 - 2).get(y2 - 2).equals("E")) {
                        mustMoves.add("" + x2 + y2 + (x2 - 2) + (y2 - 2));
                    }
                }
                if(x2 - 2 >= 0 && y2 + 2 <= size - 1) {
                    if (board.get(x2 - 1).get(y2 + 1).contains(enemy) &&
                            board.get(x2 - 2).get(y2 + 2).equals("E")) {
                        mustMoves.add("" + x2 + y2 + (x2 - 2) + (y2 + 2));
                    }
                }
                if(x2 + 2 <= size - 1 && y2 + 2 <= size - 1) {
                    if (board.get(x2 + 1).get(y2 + 1).contains(enemy) &&
                            board.get(x2 + 2).get(y2 + 2).equals("E")) {
                        mustMoves.add("" + x2 + y2 + (x2 + 2) + (y2 + 2));
                    }
                }
                if(x2 + 2 <= size - 1 && y2 - 2 >= 0) {
                    if (board.get(x2 + 1).get(y2 - 1).contains(enemy) &&
                            board.get(x2 + 2).get(y2 - 2).equals("E")) {
                        mustMoves.add("" + x2 + y2 + (x2 + 2) + (y2 - 2));
                    }
                }
            }
        }
    }
    /**
     * Checks diagonal queen move for correctness
     * */
    @Override
    public void checkDiagonalForQueen(int x2, int y2, int dx, int dy, int maxX, int maxY, String enemy) {
        int tempX, tempY;

        tempX = x2 + dx;
        tempY = y2 + dy;

        if(tempX + dx < 0 || tempX + dx > size - 1 || tempY + dy < 0 || tempY + dy > size - 1) return;
        while(tempX < maxX && tempY < maxY){
            if(board.get(tempX).get(tempY).contains(enemy)){
                if (board.get(tempX + dx).get(tempY + dy).equals("E")) {
                    mustMoves.add("" + x2 + y2 + (tempX + dx) + (tempY + dy));
                } else {
                    return;
                }
                break;
            }
            tempX += dx;
            tempY += dy;
        }
    }
    /**
     * creates Capture Info to start counting captured pieces
     * */
    @Override
    public void createKillCount(String move) {
        whiteKills = 0;
        blackKills = 0;

        int x1 = getCoord(move, 0);
        int y1 = getCoord(move, 1);
        int x2 = getCoord(move, 2);
        int y2 = getCoord(move, 3);

        int dx, dy;

        if(x2 > x1) dx = 1;
        else dx = -1;
        if (y2 > y1) dy = 1;
        else dy = -1;

        x1 += dx;
        y1 += dy;

        while (x1 != x2 && y1 != y2){
            if(board.get(x1).get(y1).equals("B") || board.get(x1).get(y1).equals("BQ")) blackKills++;
            if(board.get(x1).get(y1).equals("W") || board.get(x1).get(y1).equals("WQ")) whiteKills++;
            x1 += dx;
            y1 += dy;
        }
    }
    /**
     * Checks whether you jump over your own pieces
     * */
    @Override
    public boolean isBetrayalMove(String move) {
        int x1 = getCoord(move, 0);
        int y1 = getCoord(move, 1);
        int x2 = getCoord(move, 2);
        int y2 = getCoord(move, 3);

        String pawn = board.get(x1).get(y1);

        boolean result = false;

        if(("B".equals(pawn) || "BQ".equals(pawn)) && blackKills > 0) result = true;
        else if(("W".equals(pawn) || "WQ".equals(pawn)) && whiteKills > 0) result = true;

        return result;
    }

    /**
     * Check whether capture is affecting only one enemy pawn
     * */
    @Override
    public boolean isFairMove(String move) {
        int x1 = getCoord(move, 0);
        int y1 = getCoord(move, 1);
        int x2 = getCoord(move, 2);
        int y2 = getCoord(move, 3);

        String pawn = board.get(x1).get(y1);

        boolean result = false;

        if(("B".equals(pawn) || "BQ".equals(pawn)) && whiteKills <= 1) result = true;
        else if(("W".equals(pawn) || "WQ".equals(pawn)) && blackKills <= 1) result = true;

        return result;
    }

    /**
     * Check whteher move is forced for apawn
     * */
    @Override
    public boolean isMustMove(String move) {
        for (String mustMove : mustMoves) {
            if(move.equals(mustMove)){
                return true;
            }
        }
        return false;
    }

    /**
     * Check whther moves is correctly placed on diagonals, considers also queen
     * */
    @Override
    public boolean isCorrectDiagonal(String move) {
        int x1 = getCoord(move, 0);
        int y1 = getCoord(move, 1);
        int x2 = getCoord(move, 2);
        int y2 = getCoord(move, 3);

        String pawn = board.get(x1).get(y1);

        if(Math.abs(x2-x1) == Math.abs(y2-y1)) {
            if (pawn.contains("Q")) {
                return true;
            } else {
                if("W".equals(pawn)){
                    if(x2 < x1 && Math.abs(x2-x1) == 1){
                        return true;
                    } else if (x2 < x1 && Math.abs(x2-x1) == 2 && blackKills == 1) {
                        return true;
                    } else if (x2 > x1 && Math.abs(x2-x1) == 2 && blackKills == 1) {
                        return true;
                    }
                } else {
                    if(x2 > x1 && Math.abs(x2-x1) == 1){
                        return true;
                    } else if (x2 > x1 && Math.abs(x2-x1) == 2 && whiteKills == 1) {
                        return true;
                    } else if (x2 < x1 && Math.abs(x2-x1) == 2 && whiteKills == 1) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
