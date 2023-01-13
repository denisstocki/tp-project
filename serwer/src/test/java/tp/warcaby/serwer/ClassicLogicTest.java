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

        cl.createRespond("1122", "BLACK");

        assertEquals(outputStreamCaptor.toString().trim(), "(game)[RESPOND FOR BLACK PLAYER PREPARED]: 1122 [WHITE]: null [BLACK]: multiple");

    }

    @Test
    public void RepetitionCheckTest(){
        MockGameLogic gl = new MockGameLogic(4, 2,2);
        gl.showBoard();
        System.out.println("\n");
        gl.fetchMove("0312");
        gl.fetchMove("3221");
        gl.showBoard();
        System.out.println("\n");
        gl.fetchMove("1223");
        gl.fetchMove("2112");
        gl.fetchMove("2332");
        gl.promoteToQueen("2332");
        gl.fetchMove("1203");
        gl.promoteToQueen("1203");
        gl.showBoard();
        System.out.println("\n");

        //Now repetting moves

        gl.fetchMove("0312");

        gl.fetchMove("3223");

        gl.fetchMove("1203");

        gl.fetchMove("2332");


        gl.fetchMove("0312");

        gl.fetchMove("3223");

        gl.fetchMove("1203");

        gl.fetchMove("2332");


        gl.fetchMove("0312");

        gl.fetchMove("3223");

        gl.fetchMove("1203");
        gl.fetchMove("2332");

        System.out.println(gl.latestMoves);
        assertTrue(gl.repetitionCheck());


//        assertEquals(gl.isFinished(), true);
//        assertEquals(gl.getWinner(), "TIE");

    }

    @Test
    public void killAllOpponnetsAndWinTest() {
        //Kill all opponents and win test
        MockGameLogic gl = new MockGameLogic(4, 2,2);
        gl.showBoard();
        System.out.println("\n");
        gl.fetchMove("0110");
        gl.fetchMove("3223");
        gl.showBoard();
        System.out.println("\n");
        gl.fetchMove("0312");
        gl.fetchMove("3021");
        System.out.println("\n");
        gl.showBoard();
        gl.fetchMove("1032");
        gl.fetchMove("2321");
        gl.fetchMove("3210");
        gl.showBoard();

        assertEquals(gl.isFinished(), true);
        assertEquals(gl.getWinner(), "WHITE");

        //kill all opponents and win test
    }
    @Test
    public void NoCaptureDrawTest() {
        //Ruchy w trojkacie po pustych polach, po 15 u kazdego gracza czyli po 30 w sumie, BEZ POWTÓRZEŃ BEZPOŚREDNICH
        //(0,0)->(4,4)->(3,3)|   DLA BIAŁYCH
        // ^^_______________vv

        //(7,1)->(3,2)->(4,5)|   DLA CZARNYCH
        // ^^_______________vv

        MockGameLogic gl = new MockGameLogic(8, 12,12);
        gl.fetchMove("0044");//1
        gl.fetchMove("7132");//2
        gl.fetchMove("4433");//3
        gl.fetchMove("3245");//4
        gl.fetchMove("3300");//5
        gl.fetchMove("4571");//6

        gl.fetchMove("0044");//1
        gl.fetchMove("7132");//2
        gl.fetchMove("4433");//3
        gl.fetchMove("3245");//4
        gl.fetchMove("3300");//5
        gl.fetchMove("4571");//6

        gl.fetchMove("0044");//1
        gl.fetchMove("7132");//2
        gl.fetchMove("4433");//3
        gl.fetchMove("3245");//4
        gl.fetchMove("3300");//5
        gl.fetchMove("4571");//6

        gl.fetchMove("0044");//1
        gl.fetchMove("7132");//2
        gl.fetchMove("4433");//3
        gl.fetchMove("3245");//4
        gl.fetchMove("3300");//5
        gl.fetchMove("4571");//6

        gl.fetchMove("0044");//1
        gl.fetchMove("7132");//2
        gl.fetchMove("4433");//3
        gl.fetchMove("3245");//4
        gl.fetchMove("3300");//5
        gl.fetchMove("4571");//6

        gl.fetchMove("0044");//1
        gl.fetchMove("7132");//2

        gl.showBoard();

        assertEquals(gl.isFinished(), true);
        assertEquals(gl.getWinner(), "TIE");

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
        MockGameLogic gl = new MockGameLogic(4, 2,2);
        gl.showBoard();
        System.out.println("\n");
        gl.fetchMove("0312");
        gl.fetchMove("3221");
        gl.showBoard();
        System.out.println("\n");
        gl.fetchMove("1223");
        gl.fetchMove("2112");
        gl.fetchMove("2332");
        gl.promoteToQueen("2332");
        gl.fetchMove("1203");
        gl.promoteToQueen("1203");
        gl.showBoard();
        System.out.println("\n");

    }

    @Test
    public void QueenMovesTest(){
        MockGameLogic gl = new MockGameLogic(4, 2,2);
        gl.showBoard();
        System.out.println("\n");
        gl.fetchMove("0312");
        gl.fetchMove("3221");
        gl.showBoard();
        System.out.println("\n");
        gl.fetchMove("1223");
        gl.fetchMove("2112");
        gl.fetchMove("2332");
        gl.promoteToQueen("2332");
        gl.fetchMove("1203");
        gl.promoteToQueen("1203");
        gl.showBoard();
        System.out.println("\n");

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
