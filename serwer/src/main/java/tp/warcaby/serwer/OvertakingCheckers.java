package tp.warcaby.serwer;

import java.util.List;

/**
 * Logic holding rules for overtaking checkers varaint
 * */
public class OvertakingCheckers extends ClassicLogic {

    public OvertakingCheckers() {
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

    /**
     * Check for overtaking variant win
     * */
    @Override
    public String getWinner() {
        if(finishState == FinishState.WHITE){
            return FinishState.BLACK.toString();
        } else if (finishState == FinishState.BLACK) {
            return FinishState.WHITE.toString();
        } else {
            return FinishState.TIE.toString();
        }
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
