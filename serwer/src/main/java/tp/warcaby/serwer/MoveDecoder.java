package tp.warcaby.serwer;

import javafx.util.Pair;

public class MoveDecoder {
    private Pair<Character, Character> previousCoords;
    private Pair<Character, Character> newCoords;

    //ADD other funcionality if needed here
    public Pair getOldCoords() {
        return previousCoords;
    }

    public Pair getNewCoords() {
        return newCoords;
    }

    public void printMove(){
        System.out.println( " Move is: (" +
                previousCoords.getKey() + ", " +
                previousCoords.getValue() + ") -> (" +
                newCoords.getKey() + ", " +
                newCoords.getValue()  + ")\n");
    }

    public MoveDecoder(String messageIn) {
        previousCoords = new Pair<Character, Character>(messageIn.charAt(0), messageIn.charAt(1));
        newCoords = new Pair<Character, Character>(messageIn.charAt(2), messageIn.charAt(3));
    }
}
