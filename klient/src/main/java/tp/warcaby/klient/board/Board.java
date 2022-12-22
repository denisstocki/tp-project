package tp.warcaby.klient.board;

//Class implementing asbtract Board object.
//We dont care about look, view or handling changes, just internals.

//Add asserts chececking if we have sth to delete or

import tp.warcaby.klient.Pawn;

public class Board {
    //TODO check for better encapsulation here
    private Pawn[][] pieces;
    private int size;

    public Board(int size) {
        this.size = size;
        pieces = new Pawn[size][size];
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
        pieces = new Pawn[size][size];
    }

    public Pawn[][] getPieces() {
        return pieces;
    }

    public void setPieces(Pawn[][] newPieces) {
        pieces = newPieces;
    }

    public void removePawn(int x_cord, int y_cord)
    {
        pieces[x_cord][y_cord] = null;
    }

    public void promoteToQueen(int x_cord, int y_cord)
    {
        pieces[x_cord][y_cord].upgradeToQueen();
    }

    public void addPawn(int x_cord, int y_cord, PawnType type, PawnColor color)
    {
        //bUilder design Pattern here maybe?
        pieces[x_cord][y_cord] = new Pawn(x_cord, y_cord, color, type);
    }
}
