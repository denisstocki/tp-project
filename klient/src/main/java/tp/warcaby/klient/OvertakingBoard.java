package tp.warcaby.klient;

import tp.warcaby.klient.board.BoardState;

/**
 * View for displaying overtaking variant board
 * */
public class OvertakingBoard extends ClassicallyBoardable {

    private BoardState boardState;

    public OvertakingBoard(BoardState boardState, String color, boolean reverse) {
        super(boardState, color, 8, "Overtaking checkers", reverse);
    }
}
