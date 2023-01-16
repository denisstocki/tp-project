package tp.warcaby.serwer;

/**
 * Logic holding rules for overtaking checkers varaint
 * */
public class OvertakingCheckers extends ClassicLogic {

    public OvertakingCheckers() {
        super(8, 12, 12);
    }

    @Override
    public void updateWinner(String move){
        if(whiteCount == 0 || whiteBlocked){
            winner = FinishState.WHITE;
            finished = true;
            pickCurrentRespond("black", "black" + move);
        } else if (blackCount == 0 || blackBlocked) {
            winner = FinishState.BLACK;
            finished = true;
            pickCurrentRespond("white", "white" + move);
        } else if (movesWithoutCapture == 30 || repeated) {
            winner = FinishState.TIE;
            finished = true;
            pickCurrentRespond("tie", "tie" + move);
        }
    }
}
