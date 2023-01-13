package tp.warcaby.klient;

import tp.warcaby.klient.board.BoardState;

/**
 * class representing View of EnglishBoard
 * */
public class EnglishBoard extends ClassicallyBoardable {

    public EnglishBoard(BoardState boardState, String color) {
        super(boardState, color, 8, "English checkers");
    }
}
