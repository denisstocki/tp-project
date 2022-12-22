package tp.warcaby.klient.board;

import tp.warcaby.klient.board.PawnColor;
import tp.warcaby.klient.board.PawnType;

public class Pawn {
    private int x_cord;
    private int y_cord;
    private PawnColor color;
    private PawnType type;

    public Pawn(int x_cord, int y_cord, PawnColor color, PawnType pawnType) {
        this.x_cord = x_cord;
        this.y_cord = y_cord;
        this.color = color;
        this.type = pawnType;
    }
    public void upgradeToQueen() {
        type = PawnType.QUEEN;
    }

    public void downgradeToNormal() {
        type = PawnType.NORMAL;
    }

    //GETTERS AND SETTERS
    public int get_x_cord() {
        return x_cord;
    }

    public int get_y_cord() {
        return y_cord;
    }
    public PawnType get_type() {
        return type;
    }
    public PawnColor get_color() {
        return color;
    }
}
