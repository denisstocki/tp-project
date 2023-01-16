package tp.warcaby.klient;

import tp.warcaby.klient.board.BoardState;

/**
 * View for displaying polish variant board
 **/
public class PolishBoard extends ClassicallyBoardable {

    private BoardState boardState;

    public PolishBoard(BoardState boardState, String color, boolean reverse) {
        super(boardState, color, 10, "Polish checkers", reverse);
    }
}
