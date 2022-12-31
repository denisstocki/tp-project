package tp.warcaby.klient;

import tp.warcaby.klient.board.BoardState;
import tp.warcaby.klient.board.Boardable;

public class OvertakingBoard implements Boardable {

    private BoardState boardState;

    public OvertakingBoard(BoardState boardState) {
        this.boardState = boardState;
    }

    @Override
    public void initializeBoard() {

    }

    @Override
    public void setOpponentMove(String move) {

    }

    @Override
    public void setBoardState(BoardState state) {

    }

    @Override
    public void setGameInfo(String info) {

    }

    @Override
    public void setWinnerInfo(String winner) {

    }

    @Override
    public void setOurMove() {

    }
}
