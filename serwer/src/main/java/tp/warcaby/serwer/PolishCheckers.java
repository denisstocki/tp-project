package tp.warcaby.serwer;

/**
 * Code representing rules for Polish varaint in Checkers
 * */
public class PolishCheckers extends LogicTest {
    public PolishCheckers() {
        super(10, 20, 20);
    }

    @Override
    public boolean isMustMove(String move) {
        return false;
    }

    @Override
    public boolean hasMustMoves() {
        return false;
    }

    @Override
    public boolean hasTurned() {
        return false;
    }

    @Override
    public void considerMove(String move) {

    }

    @Override
    public boolean moveEnded() {
        return false;
    }

    @Override
    public String getCurrentBestMoves() {
        return null;
    }
}
