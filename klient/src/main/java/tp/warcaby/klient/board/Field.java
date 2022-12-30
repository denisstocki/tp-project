package tp.warcaby.klient.board;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;


public class Field extends Group {
    private Pawn pawn;
    private Circle outerCrown;
    private Circle innerCrown;
    private Button field;
    private final double size;
    private final double pawnSize;

    public Field(String color, Pawn pawn, double size, double pawnSize, Button field) {
        super();
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
                "-fx-border-width: 1 1 1 1;" +
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

    public void hideGhost(){
        this.toFront();
    }

    public void showPawn(){
        pawn.toFront();
    }

    public Pawn getPawn() {
        return pawn;
    }

    public Circle getOuterCrown() {
        return outerCrown;
    }

    public Circle getInnerCrown() {
        return innerCrown;
    }
}
