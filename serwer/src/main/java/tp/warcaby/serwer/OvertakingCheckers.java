package tp.warcaby.serwer;

import java.util.List;

public class OvertakingCheckers extends BaseLogic {

    public void movePawn(String move) {
        List<List<String>> board = getBoard();
        int x1 = Integer.parseInt(String.valueOf(move.charAt(0)));
        int y1 = Integer.parseInt(String.valueOf(move.charAt(1)));
        int x2 = Integer.parseInt(String.valueOf(move.charAt(2)));
        int y2 = Integer.parseInt(String.valueOf(move.charAt(3)));
        String figure = board.get(x1).get(y1);
        int dx, dy;
        if (x2 > x1) dx = 1;
        else dx = -1;
        if (y2 > y1) dy = 1;
        else dy = -1;
        if ("W".equals(figure) && x2 == 0) {
            board.get(x2).set(y2, "WQ");
        } else if ("B".equals(figure) && x2 == 7) {
            board.get(x2).set(y2, "BQ");
        } else {
            board.get(x2).set(y2, figure);
        }
        board.get(x1).set(y1, "E");
        x1 += dx;
        y1 += dy;
        while (x1 != x2 && y1 != y2) {
            board.get(x1).set(y1, "E");
            x1 += dx;
            y1 += dy;
        }
        if ('B' == figure.charAt(0)) {
            if (quantityOf('W') == 0) {
                setGameFinishState(GameFinishState.BLACK_WON);
                setFinished(true);
            } else if (!hasMoves('W')) {
                setGameFinishState(GameFinishState.BLACK_WON);
                setFinished(true);
            } else {
                setGameFinishState(GameFinishState.BLACK_WON);
                setFinished(true);
            }
        } else {
            if (quantityOf('B') == 0) {
                setGameFinishState(GameFinishState.BLACK_WON);
                setFinished(true);
            } else if (!hasMoves('B')) {
                setGameFinishState(GameFinishState.BLACK_WON);
                setFinished(true);
            } else {
                setGameFinishState(GameFinishState.BLACK_WON);
                setFinished(true);
            }
        }

        setMovesWithoutCapture(getMovesWithoutCapture() + 1);

        if (getMovesWithoutCapture() + 1 >= 15) {
            setGameFinishState(GameFinishState.BLACK_WON);
            setFinished(true);

        }
    }

}
