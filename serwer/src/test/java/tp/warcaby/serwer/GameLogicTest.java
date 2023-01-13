package tp.warcaby.serwer;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameLogicTest {


    @Test
    public void ConstructorTest(){
        MockGameLogic gl = new MockGameLogic(8, 12,12);
        assertTrue(true);
    }

    @Test
    public void ShowBoardTest(){
        MockGameLogic gl = new MockGameLogic(8, 12,12);

        PrintStream standardOut = System.out;
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        assertEquals(outputStreamCaptor.toString().trim(), "EBEBEBEB" +
                                                            "BEBEBEBE" +
                                                            "EBEBEBEB" +
                                                            "EEEEEEEE" +
                                                            "EEEEEEEE" +
                                                            "EWEWEWEW" +
                                                            "WEWEWEWE" +
                                                            "EWEWEWEW");


    }
    @Test
    public void IsFinishedTest(){
        MockGameLogic gl = new MockGameLogic(8, 12,12);
        assertTrue(!gl.isFinished());

    }
    @Test
    public void FetchMoveTest(){
        MockGameLogic gl = new MockGameLogic(8, 12,12);
        gl.fetchMove("1257");
        assertTrue(true);

    }
    @Test
    public void RepetitionCheckTest(){
        Gameable game = new ClassicCheckers();
        MockGameLogic gl = new MockGameLogic(8, 12,12);
        gl.fetchMove("1257");
        gl.fetchMove("1257");
        gl.fetchMove("1257");
        gl.fetchMove("1257");
        gl.fetchMove("1257");
        gl.fetchMove("1257");
        gl.fetchMove("1257");
        gl.fetchMove("1257");
        gl.fetchMove("1257");

        assertEquals(gl.isFinished(), true);
        assertEquals(gl.getWinner(), "TIE");

    }
    @Test
    public void ChangeTurnTest(){
        MockGameLogic gl = new MockGameLogic(8, 12,12);
        gl.changeTurn();
        assertEquals(gl.getTurn(), "BLACK");
        assertTrue(gl.turned());
    }
    @Test
    public void killAllOpponnetsAndWinTest() {
        MockGameLogic gl = new MockGameLogic(8, 12, 12);

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
        
        assertEquals(gl.isFinished(), true);
        assertEquals(gl.getWinner(), "TIE");

    }

    @Test
    public void getCoordsTest() {
        MockGameLogic gl = new MockGameLogic(8, 12,12);

            assertEquals(gl.getCoord("1234", 0), 1);
            assertEquals(gl.getCoord("1234", 1), 2);
            assertEquals(gl.getCoord("1234", 2), 3);
            assertEquals(gl.getCoord("1234",3), 4);
    }
}
