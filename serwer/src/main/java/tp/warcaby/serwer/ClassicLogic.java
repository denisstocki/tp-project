package tp.warcaby.serwer;

public interface ClassicLogic extends GameLogic{
    @Override
    void initializeBoard();

    @Override
    boolean isLegal(String move);

    @Override
    void movePawn(String move);

    @Override
    boolean isFinished();

    @Override
    String getWinner();

    @Override
    String getPossibleMovesFor(int i, int j);

    @Override
    int quantityOf(Character color);

    @Override
    boolean hasMoves(Character color);
}
