package tp.warcaby.klient.board;

import java.util.concurrent.CountDownLatch;

/***
 * Main board controller responsible for handling actions with board view
 * */
public class BoardController{
    /***
     * Reference to board object
     * */
    private final Boardable board;
    /**
     * Reference to chosen color
     * */
    private final String color;
    /**
     * Current board size
     * */
    private final int size;
    /**
     * Base Contructor for the operating controller
     * */
    public BoardController(Boardable board) {
        this.board = board;
        this.color = board.getColor();
        this.size = board.getSize();
    }
    /**
     * Setting up the opponent move on our board
     * */
    public void setOpponentMove(String move, boolean ended) {
        if ("black".equals(color)) move = decodeMove(move);
        board.setOpponentMove(move, ended);
    }

    /**
     * Move decoder to decouple received moves and process them
     * */
    private String decodeMove(String move) {
        int x1 = getCoord(move, 0);
        int y1 = getCoord(move, 1);
        int x2 = getCoord(move, 2);
        int y2 = getCoord(move, 3);

        return "" + (size - 1 - x1) + (size - 1 - y1) + (size - 1 - x2) + (size - 1 - y2);
    }
    /**
     * Get coords of chosen pawn from the move
     * */
    private static int getCoord(String move, int index) {
        return Integer.parseInt(String.valueOf(move.charAt(index)));
    }

    /**
     * Set the state of the board
     * */
    public void setBoardState(BoardState state) {
        board.setBoardState(state);
    }

    /**
     * Set the game info
     * */
    public void setGameInfo(String info) {
        board.setGameInfo(info);
    }

    /**
     * Set the winner info
     * */
    public void setWinnerInfo(String winner) {
        board.setWinnerInfo(winner);
    }

    /**
     * Set THis Player move of pawn
     * */
    public void setOurMove(boolean ended) {
        board.setOurMove(ended);
    }

    /**
     * Gets the Board size X
     * */
    public double getBoardX(){
        return board.getBoardX();
    }
    /**
     * Gets the Board sie Y
     * */
    public double getBoardY(){
        return board.getBoardY();
    }
    /**
     * Show the board on the screen
     * */
    public void showBoard() {
        board.showBoard();
    }
    /**
     * Check whether pawn was moved on the board
     * */
    public boolean wasMoved() {
        return board.wasMoved();
    }
    /**
     * Set that there was a pawn which was moved on the board
     * */
    public void setMoved(boolean moved){
        board.setMoved(moved);
    }
    /**
     * Get move encoded as String, needs further decoding
     * */
    public String getMove() {
        if ("black".equals(color)) return decodeMove(board.getMove());
        return board.getMove();
    }
    /**
     * Gets the Board internal state
     * */
    public BoardState getBoardState(){
        return board.getBoardState();
    }
    /**
     * Set the current Latch
     * */
    public void setLatch(CountDownLatch latch){
        board.setLatch(latch);
    }
    /**
     * Gets current chosen color
     * */
    public String getColor(){
        return board.getColor();
    }
}