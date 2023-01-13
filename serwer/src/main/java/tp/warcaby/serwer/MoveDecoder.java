package tp.warcaby.serwer;


import java.util.ArrayList;

/***
 * Claass holding and using moves reeived from sockets connection
 * */
public class MoveDecoder {
    /**
     * Original position of pawn
     * */
    private final ArrayList<Character> previousCoords;
    /**
     * New position of pawn
     * */
    private final ArrayList<Character> newCoords;
    /**
     * Get old position of pawn
     * */
    public ArrayList<Character> getOldCoords() {
        return previousCoords;
    }
    /**
     * Get New position of pawn
     * */
    public ArrayList<Character> getNewCoords() {
        return newCoords;
    }

    /**
     * Get the move message as string
     * */
    public String getMessage() {
        return String.valueOf(previousCoords.get(0)) + previousCoords.get(1)+ newCoords.get(0) + newCoords.get(1);
    }

    /**
     * Base move decoder constructor
     * */
    public MoveDecoder(String messageIn) {
        previousCoords = new ArrayList<>();
        previousCoords.add(messageIn.charAt(0));
        previousCoords.add(messageIn.charAt(1));
        newCoords = new ArrayList<>();
        newCoords.add(messageIn.charAt(2));
        newCoords.add(messageIn.charAt(3));
    }
}
