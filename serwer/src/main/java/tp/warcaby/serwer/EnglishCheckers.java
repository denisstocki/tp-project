package tp.warcaby.serwer;

import java.util.Objects;

public class EnglishCheckers extends BaseLogic {

    public boolean isLegal(String move) {
        int x1 = Integer.parseInt(String.valueOf(move.charAt(0)));
        int y1 = Integer.parseInt(String.valueOf(move.charAt(1)));
        int x2 = Integer.parseInt(String.valueOf(move.charAt(2)));
        int y2 = Integer.parseInt(String.valueOf(move.charAt(3)));
        String pawn = getBoard().get(x1).get(y1), jumped;
        //Checks if player uses his own pawn
        if((Objects.equals(pawn, "B") && getGameState() == GameState.WHITE_TURN) || (Objects.equals(pawn, "W") && getGameState() == GameState.BLACK_TURN)) return false;
        int x0, y0;
        boolean score = false;
        String direction = getBoard().get(x2).get(y2);
        System.out.println("Checking pawn " + pawn + " moving from " + x1 +"," + y1 + " to " + x2 + "," + y2);
        if(Objects.equals(pawn, "B") || Objects.equals(pawn, "W")){
            if(Math.abs(x2-x1)==1 && Math.abs(y2-y1)==1 && Objects.equals(direction, "E")) score = true;
            else if(Math.abs(x2-x1)==2 && Math.abs(y2-y1)==2 && Objects.equals(direction, "E")){
                if(x2>x1) x0 = x1+1;
                else x0 = x1-1;
                if(y2>y1) y0 = y1+1;
                else y0 = y1-1;
                jumped = getBoard().get(x0).get(y0);
                if(pawn.equals("B") && Objects.equals(jumped, "W")) score = true;
                else if(pawn.equals("W") && Objects.equals(jumped, "B")) score =  true;
            }
        }

        //CHANGES ARE HERE

        else if(Objects.equals(pawn, "BQ") || Objects.equals(pawn, "WQ")){
            if(Math.abs(x2-x1)==Math.abs(y2-y1) && Objects.equals(direction, "E") && (x2 != x1) && (y2 != y1)){

                if(Math.abs(x2-x1) == 1) {
                    score = true;
                }
            } else if (Math.abs(x2-x1) == 2) {
                    if(pawn.equals("WQ") && !isTraitor("WQ", x1, x2, y1, y2) && isFairFight("WQ", x1, x2, y1, y2) == 1) score = true;
                    else if(pawn.equals("BQ") && !isTraitor("BQ", x1, x2, y1, y2) && isFairFight("BQ", x1, x2, y1, y2) == 1) score = true;

                }
        }
        return score;
    }
}
