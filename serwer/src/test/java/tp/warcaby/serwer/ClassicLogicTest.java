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


    
}
