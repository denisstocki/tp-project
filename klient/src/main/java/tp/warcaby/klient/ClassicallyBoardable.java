package tp.warcaby.klient;

import javafx.scene.control.Button;
import tp.warcaby.klient.board.*;

public abstract class ClassicallyBoardable extends Boardable {

    public ClassicallyBoardable(BoardState boardState, String color, int size, String title) {
        super(boardState, color, size, title);
    }

    @Override
    public void initializePawns() {
        Pawn pawn;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(i<3 && (i+j)%2==1) pawn = new Pawn(PawnLook.BLACK, pawnSize);
                else if (i > 4 && (i+j)%2==1) pawn = new Pawn(PawnLook.WHITE, pawnSize);
                else pawn = new Pawn(PawnLook.GHOST, pawnSize);

                buttons[i][j] = new Button();

                if((i+j)%2==1) fields[i][j] = new Field("#BD7A44", pawn, fieldSize, pawnSize, buttons[i][j]);
                else fields[i][j] = new Field("#E3C193", pawn, fieldSize, pawnSize, buttons[i][j]);

                gridPane.add(fields[i][j], j, i);

                setButton(buttons[i][j]);
            }
        }
    }
}
