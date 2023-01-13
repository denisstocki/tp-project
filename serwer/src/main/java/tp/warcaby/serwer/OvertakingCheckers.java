package tp.warcaby.serwer;

import java.util.List;

/**
 * Logic holding rules for overtaking checkers varaint
 * */
public class OvertakingCheckers extends ClassicLogic {

    public OvertakingCheckers() {
        super(8, 12, 12);
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
}
