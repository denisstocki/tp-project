package tp.warcaby.serwer;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ClassicLogicTest {
    
    @Test
    public void ConstructorTest(){
        MockClassicLogic cl = new MockClassicLogic(8, 12,12);
        assertTrue(true);
    }

    @Test
    public void RespondTest(){
        MockClassicLogic cl = new MockClassicLogic(8, 12,12);
        //Just add it the response the Game is giving us

        PrintStream standardOut = System.out;
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        cl.createRespond(1122, "BLACK");

        assertEquals(outputStreamCaptor.toString().trim(), "ACTUAL RESPOND HERE");

    }
    @Test
    public void MoveLegalityTest(){
        MockClassicLogic cl = new MockClassicLogic(8, 12,12);

        //1. Ruch nie jest w miejscu czyli na przykład 0,0 -> 0,0
        //2. Nie można przeskoczyć nad swoimi pionkami
        //3. Można przeskoczyć tylko jednego przeciwnika w jednym skoku
        //4. Miejsce za przeciwnikiem musi być E
        //5. Ruch musi być po skosie i do przodu jeśli to zwykły pionek, jeśli królowa to może być też do tyłu i dowolnej
        //długości
        //6. Gracz porusza się tylko swoimi pionkami, czyli pawn = color.charAt(0) i direction = E
        //7. Czy gracz wykonał mustMOve
        //8. Ruch nie jest poza planszą



    }
    @Test
    public void QueenPromotionTest(){
        MockClassicLogic cl = new MockClassicLogic(8, 12,12);

        //Promote one pawn to Queen here

    }

    @Test
    public void QueenMovesTest(){
        MockClassicLogic cl = new MockClassicLogic(8, 12,12);

        //Promote one pawn to Queen here

    }

    @Test
    public void MustMoveTest(){
        Gameable game = new ClassicCheckers();
        MockClassicLogic cl = new MockClassicLogic(8, 12,12);


    }
    @Test
    public void checkDiagonalForQueenTest(){
        MockClassicLogic cl = new MockClassicLogic(8, 12,12);

    }
    @Test
    public void killCountCreationTest() {

    }
    @Test
    public void IsBetrayalMoveTest() {


    }

    @Test
    public void isCorrectDiagonalTest() {
        MockClassicLogic cl = new MockClassicLogic(8, 12,12);

    }
    
}
