package tp.warcaby.klient.board;

import java.util.concurrent.CountDownLatch;

public class BoardController{
    private final Boardable board;
    private final String color;
    private final int size;
    public BoardController(Boardable board) {
        this.board = board;
        this.color = board.getColor();
        this.size = board.getSize();
    }

    public void setOpponentMove(String move, boolean ended) {
        if ("black".equals(color)) move = decodeMove(move);
        board.setOpponentMove(move, ended);
    }

    private String decodeMove(String move) {
        int x1 = getCoord(move, 0);
        int y1 = getCoord(move, 1);
        int x2 = getCoord(move, 2);
        int y2 = getCoord(move, 3);

        return "" + (size - 1 - x1) + (size - 1 - y1) + (size - 1 - x2) + (size - 1 - y2);
    }

    private static int getCoord(String move, int index) {
        return Integer.parseInt(String.valueOf(move.charAt(index)));
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

    public void setOurMove(boolean ended) {
        board.setOurMove(ended);
    }

    public double getBoardX(){
        return board.getBoardX();
    }

    public double getBoardY(){
        return board.getBoardY();
    }

    public void showBoard() {
        board.showBoard();
    }

    public boolean wasMoved() {
        return board.wasMoved();
    }

    public void setMoved(boolean moved){
        board.setMoved(moved);
    }

    public String getMove() {
        if ("black".equals(color)) return decodeMove(board.getMove());
        return board.getMove();
    }

    public BoardState getBoardState(){
        return board.getBoardState();
    }

    public void setLatch(CountDownLatch latch){
        board.setLatch(latch);
    }

    public String getColor(){
        return board.getColor();
    }
}