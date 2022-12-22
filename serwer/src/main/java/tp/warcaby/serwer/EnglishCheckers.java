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
    public boolean gameFinished() {
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
}
