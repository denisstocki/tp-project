package tp.warcaby.klient.board;

import javafx.scene.paint.Paint;

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

    abstract Paint getPaint();
    abstract String getColor();
    abstract PawnState getPawnState();
}
