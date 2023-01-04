package tp.warcaby.serwer;


import java.util.ArrayList;

public class MoveDecoder {
    private final ArrayList<Character> previousCoords;
    private final ArrayList<Character> newCoords;

    //ADD other funcionality if needed here
    public ArrayList<Character> getOldCoords() {
        return previousCoords;
    }

    public ArrayList<Character> getNewCoords() {
        return newCoords;
    }

    public void printMove(){
        System.out.println( " Move is: (" +
                previousCoords.get(0) + ", " +
                previousCoords.get(1) + ") -> (" +
                newCoords.get(0) + ", " +
                newCoords.get(1)  + ")\n");
    }

    public String getMessage() {
        return String.valueOf(previousCoords.get(0)) + previousCoords.get(1)+ newCoords.get(0) + newCoords.get(1);
    }

    public MoveDecoder(String messageIn) {
        previousCoords = new ArrayList<>();
        previousCoords.add(messageIn.charAt(0));
        previousCoords.add(messageIn.charAt(1));
        newCoords = new ArrayList<>();
        newCoords.add(messageIn.charAt(2));
        newCoords.add(messageIn.charAt(3));
    }
}
