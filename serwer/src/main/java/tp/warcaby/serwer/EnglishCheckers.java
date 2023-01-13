package tp.warcaby.serwer;

/**
 * Code holding logic for English checkers variant
 * */
public class EnglishCheckers extends ClassicLogic {

    public EnglishCheckers() {
        super(8, 12, 12);
    }

    /**
     * Check diagonal move for correctness, english varaint rules
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
                if(Math.abs(x2-x1) == 1){
                    return true;
                } else if (Math.abs(x2 - x1) == 2 && (blackKills == 1 || whiteKills == 1)) {
                    return true;
                }
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
    /**
     * Check diagonal queen move for correctness, english varaint rules
     * */
    @Override
    public void checkDiagonalForQueen(int x2, int y2, int dx, int dy, int maxX, int maxY, String enemy) {
        int tempX, tempY;

        tempX = x2 + dx;
        tempY = y2 + dy;

        if(tempX + dx < 0 || tempX + dx > 7 || tempY + dy < 0 || tempY + dy > 7) return;
        if(board.get(tempX).get(tempY).contains(enemy)){
            if(board.get(tempX + dx).get(tempY + dy).equals("E")){
                mustMoves.add("" + x2 + y2 + (tempX + dx) + (tempY + dy));
            }
        }
    }
}
