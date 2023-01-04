package tp.warcaby.klient;

import tp.warcaby.klient.board.BoardState;

public class OvertakingBoard extends ClassicallyBoardable {

    private BoardState boardState;

    public OvertakingBoard(BoardState boardState, String color) {
        super(boardState, color, 8, "Overtaking checkers");
    }
}
