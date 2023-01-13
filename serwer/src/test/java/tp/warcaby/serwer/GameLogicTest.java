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

        gl.showBoard();
        String x= outputStreamCaptor.toString().trim();
        assertEquals(x.replaceAll("\r\n|\r|\n", ""), "E B E B E B E B B E B E B E B E E B E B E B E B E E E E E E E E E E E E E E E E W E W E W E W E E W E W E W E W W E W E W E W E");


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
    public void ChangeTurnTest(){
        MockGameLogic gl = new MockGameLogic(8, 12,12);
        gl.changeTurn();
        assertEquals(gl.getTurn(), "BLACK");
        assertTrue(gl.turned());
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
