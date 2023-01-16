package tp.warcaby.serwer;

/**
 * Class representing Rules for Classical variant of checkers
 * */
public class ClassicCheckers extends LogicTest {
    public ClassicCheckers() {
        super(8, 12, 12);
    }

    @Override
    public boolean isMustMove(String move) {
        return false;
    }

    @Override
    public boolean hasMustMoves() {
        return false;
    }
}
