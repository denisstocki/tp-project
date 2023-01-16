package tp.warcaby.klient.board;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 * Class representing one checker field, and its state
 * */
public class Field extends Group {
    /**
     * Pawn object that currently lives on that field
     * */
    private Pawn pawn;
    /**
     * Visual element purely coded in FX
     * */
    private Circle outerCrown;
    /**
     * Visual element purely coded in FX
     * */
    private Circle innerCrown;
    /**
     * FX responsible button
     * */
    private Button field;
    /**
     * Size of field in pixel
     * */
    private final double size;
    /**
     * size of pawn displayed in pixel
     * */
    private final double pawnSize;
    /**
     * FX field color
     * */
    private final String color;

    private String oldColor;
    /**
     * Base Field constructor
     * */
    public Field(String color, Pawn pawn, double size, double pawnSize, Button field) {
        super();
        this.color = color;
        this.pawn = pawn;
        this.size = size;
        this.pawnSize = pawnSize;
        this.field = field;
        outerCrown = new Circle(4*pawnSize/5, Paint.valueOf("gold"));
        innerCrown = new Circle(3.5*pawnSize/5, pawn.getLook().getPaint());
        field.setStyle(String.format("-fx-background-color: %s; " +
                "-fx-background-radius: 0; " +
                "-fx-background-insets: 0 0 -1 0, 0, 1, 2;" +
                "-fx-border-insets: 0 0 -1 0, 0, 1, 2;" +
                "-fx-border-width: 0 0 0 0;" +
                "-fx-border-color: #392613;", color));
        this.getChildren().add(pawn);
        this.getChildren().add(outerCrown);
        this.getChildren().add(innerCrown);
        this.getChildren().add(field);
        pawn.setCenterX(size/2);
        pawn.setCenterY(size/2);
        outerCrown.setCenterX(size/2);
        outerCrown.setCenterY(size/2);
        innerCrown.setCenterX(size/2);
        innerCrown.setCenterY(size/2);
        if(PawnLook.GHOST.equals(pawn.getLook())){
            field.toFront();
        } else if(pawn.getLook().toString().toLowerCase().contains("queen")){
            pawn.toFront();
            outerCrown.toFront();
            innerCrown.toFront();
        } else {
            pawn.toFront();
        }
        pawn.setOnMouseClicked(mouseEvent -> this.field.fire());
        outerCrown.setOnMouseClicked(mouseEvent -> this.field.fire());
        innerCrown.setOnMouseClicked(mouseEvent -> this.field.fire());
    }

    /**
     * Hides dead pawns behind the board view
     * */
    public void hideGhost(){
        this.toFront();
    }

    /**
     * FX responsible button
     * */
    public void showPawn(){
        pawn.toFront();
    }
    /**
     * Get pawn living on that field
     * */
    public Pawn getPawn() {
        return pawn;
    }
    /**
     * FX responsible button
     * */
    public Circle getOuterCrown() {
        return outerCrown;
    }
    /**
     * FX responsible button
     * */
    public Circle getInnerCrown() {
        return innerCrown;
    }

    public void memoriseOldColor(){
        this.oldColor = color;
    }

    public void setOldFieldStyle(){
        field.setStyle(String.format("-fx-background-color: %s; " +
                "-fx-background-radius: 0; " +
                "-fx-background-insets: 0 0 -1 0, 0, 1, 2;" +
                "-fx-border-insets: 0 0 -1 0, 0, 1, 2;" +
                "-fx-border-width: 0 0 0 0;" +
                "-fx-border-color: #392613;", color));
    }
    /**
     * FX style setup
     * */
    public void setFieldStyle(String color){
        field.setStyle(String.format("-fx-background-color: %s; " +
                "-fx-background-radius: 0; " +
                "-fx-background-insets: 0 0 -1 0, 0, 1, 2;" +
                "-fx-border-insets: 0 0 -1 0, 0, 1, 2;" +
                "-fx-border-width: 0 0 0 0;" +
                "-fx-border-color: #392613;", color));

    }
    /**
     * FX gest field color
     * */
    public String getFieldColor(){
        return color;
    }
}
