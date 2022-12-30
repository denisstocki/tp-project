package tp.warcaby.klient.board;

import javafx.scene.paint.Paint;

public enum PawnLook {
    BLACK{
        @Override
        Paint getPaint() {
            return Paint.valueOf("black");
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
        PawnState getPawnState() {
            return PawnState.DISABLED;
        }
    };

    abstract Paint getPaint();
    abstract PawnState getPawnState();
}
