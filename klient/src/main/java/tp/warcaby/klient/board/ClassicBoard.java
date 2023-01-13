package tp.warcaby.klient.board;

import tp.warcaby.klient.ClassicallyBoardable;
/**
 * Baord view for classical variant
 * */
public class ClassicBoard extends ClassicallyBoardable {

    public ClassicBoard(BoardState boardState, String color) {
        super(boardState, color, 8, "Classic checkers");
        System.out.println(color + "siema");
    }
}
