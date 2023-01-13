package tp.warcaby.klient.board;

import javafx.scene.shape.Circle;

/**
 * FX circle component pretending to be a circle, a pawn
 * */
public class Pawn extends Circle {
    /**
     * FX responsible button
     * */
    private PawnLook look;
    /**
     * FX responsible button
     * */
    private final double size;

    /**
     * Base Pawn view constructor
     * */
    public Pawn(PawnLook look, double size) {
        super(size, look.getPaint());
        this.look = look;
        this.size = size;
    }
    /**
     * make does the make
     * */
    public void make(PawnLook look) {
        this.look = look;
        setFill(look.getPaint());
    }
    /**
     * get the current look
     * */
    public PawnLook getLook() {
        return look;
    }
}
