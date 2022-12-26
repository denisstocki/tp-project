package tp.warcaby.serwer;

public interface GameLogic {
    void initializeBoard();
    boolean isLegal(String move);
    void movePawn(String move);

    boolean isFinished();

    String getWinner();

    String getPossibleMovesFor(int i, int j);
    int quantityOf(Character color);

    boolean hasMoves(Character color);
}
