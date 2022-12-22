package tp.warcaby.serwer;

public interface GameLogic {
    void initializeBoard();
    boolean isLegal(String move);
    void movePawn(String move);

    boolean gameFinished();

    String getWinner();

    String getPossibleMovesFor(int i, int j);


}
