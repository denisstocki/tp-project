package tp.warcaby.klient.board;

public interface Boardable {
     double screenX = 800;
    double screenY = 850;
    void initializeBoard();
    void setOpponentMove(String move);

    void setBoardState(BoardState state);
}
