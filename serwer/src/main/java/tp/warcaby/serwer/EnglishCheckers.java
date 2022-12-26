package tp.warcaby.serwer;

public class EnglishCheckers implements GameLogic{

    @Override
    public void initializeBoard() {

    }

    @Override
    public boolean isLegal(String move) {
        return false;
    }

    @Override
    public void movePawn(String move) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public String getWinner() {
        return null;
    }

    @Override
    public String getPossibleMovesFor(int i, int j) {
        return null;
    }

    @Override
    public int quantityOf(Character color) {
        return 0;
    }

    @Override
    public boolean hasMoves(Character color) {
        return false;
    }
}
