package tp.warcaby.serwer;

/**
 * Simplest interface representing object playable in checkers
 * */
public interface Gameable {

    void createRespond(String move, String color);
    boolean isLegal(String move, String color, boolean create);

    void initialize();

    boolean isFinished();
    void fetchMove(String move);
    void createMustMoves(String move);
    void createKillCount(String move);
    boolean isBetrayalMove(String move);
    boolean isFairMove(String move);
    boolean isMustMove(String move);
    boolean isCorrectDiagonal(String move);
    boolean hasMustMoves();

    String getWinner();

    void updateFinish();

    int getCoord(String move, int index);

    String getWhiteRespond();
    String getBlackRespond();

    String getTurn();
    void changeTurn();
    boolean turned();
    void showBoard();
    void checkDiagonalForQueen(int x2, int y2, int dx, int dy, int maxX, int maxY, String enemy);
}
