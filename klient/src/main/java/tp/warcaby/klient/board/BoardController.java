package tp.warcaby.klient.board;

public class BoardController implements Boardable{
    private final Boardable board;
    public BoardController(Boardable board) {
        this.board = board;
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
        board.setGameInfo(info);
    }

    @Override
    public void setWinnerInfo(String winner) {

    }

    @Override
    public void setOurMove() {

    }
}