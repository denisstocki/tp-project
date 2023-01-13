package tp.warcaby.klient.board;

import javafx.scene.paint.Paint;


/**
 * FX responsibility how the pawn look like
 * */
public enum PawnLook {
    BLACK{
        @Override
        Paint getPaint() {
            return Paint.valueOf("black");
        }

        @Override
        String getColor() {
            return "black";
        }

        @Override
        PawnState getPawnState() {
            return PawnState.ACTIVE;
        }
    },
    WHITE {
        @Override
        Paint getPaint() {
            return Paint.valueOf("white");
        }

        @Override
        String getColor() {
            return "white";
        }

        @Override
        PawnState getPawnState() {
            return PawnState.ACTIVE;
        }
    },
    BLACK_QUEEN {
        @Override
        Paint getPaint() {
            return Paint.valueOf("black");
        }

        @Override
        String getColor() {
            return "black";
        }

        @Override
        PawnState getPawnState() {
            return PawnState.ACTIVE;
        }
    },
    WHITE_QUEEN {
        @Override
        Paint getPaint() {
            return Paint.valueOf("white");
        }

        @Override
        String getColor() {
            return "white";
        }

        @Override
        PawnState getPawnState() {
            return PawnState.ACTIVE;
        }
    },
    GHOST {
        @Override
        Paint getPaint() {
            return null;
        }

        @Override
        String getColor() {
            return "none";
        }

        @Override
        PawnState getPawnState() {
            return PawnState.DISABLED;
        }
    };
    /**
     * get the current paint
     * */
    abstract Paint getPaint();
    /**
     * get the current color
     * */
    abstract String getColor();
    /**
     * get the pawn current state
     * */
    abstract PawnState getPawnState();
}
