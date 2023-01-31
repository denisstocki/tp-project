package tp.warcaby.klient.board;

import tp.warcaby.klient.ClassicallyBoardable;
/**
 * Baord view for classical variant
 * */
public class ClassicBoard extends ClassicallyBoardable {

    public ClassicBoard(BoardState boardState, String color, boolean reverse) {
        super(boardState, color, 8, "Classic checkers", reverse);
    }
}
