package tp.warcaby.klient.board;

import java.util.concurrent.CountDownLatch;

public class BoardController{
    private final Boardable board;
    public BoardController(Boardable board) {
        this.board = board;
    }

    public void initializeBoard() {
        board.initializePawns();
    }

    public void setOpponentMove(String move) {
        board.setOpponentMove(move);
    }

    public void setBoardState(BoardState state) {
        board.setBoardState(state);
    }

    public void setGameInfo(String info) {
        board.setGameInfo(info);
    }

    public void setWinnerInfo(String winner) {
        board.setWinnerInfo(winner);
    }

    public void setOurMove() {
        board.setOurMove();
    }

    public void showBoard() {
        board.showBoard();
    }

    public boolean wasMoved() {
        return board.wasMoved();
    }

    public String getMove() {
        return board.getMove();
    }

    public BoardState getBoardState(){
        return board.getBoardState();
    }

    public void setLatch(CountDownLatch latch){
        board.setLatch(latch);
    }
}