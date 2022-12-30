package tp.warcaby.klient.board;

import javafx.scene.shape.Circle;

public class Pawn extends Circle {
    private PawnLook look;
    private final double size;

    public Pawn(PawnLook look, double size) {
        super(size, look.getPaint());
        this.look = look;
        this.size = size;
    }

    public void make(PawnLook look) {
        this.look = look;
        setFill(look.getPaint());
    }

    public PawnLook getLook() {
        return look;
    }
}
