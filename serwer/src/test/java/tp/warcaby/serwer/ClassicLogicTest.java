package tp.warcaby.serwer;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class ClassicLogicTest {
    
    @Test
    public void ConstructorTest(){
        MockGameable cl = new MockClassicLogic(8, 12,12);
        assertTrue(true);
    }

    @Test
    public void RespondTest(){
        MockGameable cl = new MockClassicLogic(8, 12,12);
        //Just add it the response the Game is giving us

        cl.considerMove("5242");

        assertEquals("illegal", cl.getWhiteRespond());

        cl.considerMove("5243");

        assertEquals("accepted", cl.getWhiteRespond());

        cl.considerMove("2534");

        assertEquals("accepted", cl.getBlackRespond());

        cl.considerMove("4325");

        assertEquals("accepted", cl.getWhiteRespond());
        assertEquals("unblocked4325", cl.getBlackRespond());

        MockGameable mock = new MockClassicLogic(4, 2, 2);
        mock.considerMove("3021");
        mock.considerMove("0312");
        mock.considerMove("2103");
        mock.considerMove("0110");
        mock.considerMove("3221");
        mock.considerMove("1032");
        mock.considerMove("0312");
        mock.considerMove("3210");
        mock.considerMove("1203");
        mock.considerMove("1032");
        mock.considerMove("0312");
        mock.considerMove("3210");
        mock.considerMove("1203");
        mock.considerMove("1032");
        mock.considerMove("0312");
        mock.considerMove("3210");
        mock.considerMove("1203");
        mock.considerMove("1032");

        assertEquals(mock.getBlackRespond(), "tie1032");
        assertEquals(mock.getWhiteRespond(), "tie");
    }

    @Test
    public void blackRespondTest(){
        assertTrue(true);
    }

    @Test
    public void whiteRespondTest(){
        assertTrue(true);
    }

    @Test
    public void moveEndedTest(){
        MockGameable mock = new MockClassicLogic(8, 12, 12);
        mock.considerMove("5243");
        assertTrue(mock.moveEnded());
    }

    @Test
    public void getTurnTest(){
        MockGameable mock = new MockClassicLogic(8, 12, 12);
        assertEquals(TurnState.WHITE.toString(), mock.getTurn());
    }

    @Test
    public void fetchMoveTest(){
        MockGameable mock = new MockClassicLogic(8, 12, 12);
        mock.fetchMove(5, 2, 4, 2);
        ArrayList<ArrayList<String>> mockBoard = mock.getBoard();
        assertEquals("[[E, B, E, B, E, B, E, B], [B, E, B, E, B, E, B, E], [E, B, E, B, E, B, E, B], [E, E, E, E, E, E, E, E], [E, E, E, E, E, E, E, E], [W, E, W, E, W, E, W, E], [E, W, E, W, E, W, E, W], [W, E, W, E, W, E, W, E]]" , mockBoard.toString());
    }


    @Test
    public void currentBestMovesTest(){
        MockGameable cl = new MockClassicLogic(8, 12,12);
        String bestMoves = cl.getCurrentBestMoves();
        assertTrue(bestMoves.contains("5241"));
        assertTrue(bestMoves.contains("5243"));
        assertTrue(bestMoves.contains("5443"));
        assertTrue(bestMoves.contains("5445"));
        assertTrue(bestMoves.contains("5645"));
        assertTrue(bestMoves.contains("5647"));
        assertTrue(bestMoves.contains("5041"));
    }

    @Test
    public void isFinishedTest(){
        MockGameable mock = new MockClassicLogic(8, 12,12);
        assertFalse(mock.isFinished());

        mock = new MockClassicLogic(4, 2, 2);
        mock.considerMove("3021");
        mock.considerMove("0312");
        mock.considerMove("2103");
        mock.considerMove("0110");
        mock.considerMove("3221");
        mock.considerMove("1032");
        mock.considerMove("0312");
        mock.considerMove("3210");
        mock.considerMove("1203");
        mock.considerMove("1032");
        mock.considerMove("0312");
        mock.considerMove("3210");
        mock.considerMove("1203");
        mock.considerMove("1032");
        mock.considerMove("0312");
        mock.considerMove("3210");
        mock.considerMove("1203");
        mock.considerMove("1032");
        assertTrue(mock.isFinished());
    }

    @Test
    public void isEmptyTest(){
        MockGameable mock = new MockClassicLogic(8, 12,12);
        assertTrue(mock.isEmpty(new ArrayList<>()));
    }

    @Test
    public void deDuplicateMovesTest(){
        MockGameable mock = new MockClassicLogic(8, 12,12);
        ArrayList<String> moves = new ArrayList<>();
        moves.add("1243");
        moves.add("1243");
        moves.add("4323");
        ArrayList<String> expected = new ArrayList<>();
        expected.add("1243");
        expected.add("4323");
        assertEquals(mock.deduplicateMoves(moves), expected);
    }

    @Test
    public void createBestMovesTest(){
        MockGameable mock = new MockClassicLogic(8, 12,12);

        mock.considerMove("5243");
        mock.considerMove("2534");
        mock.createBestMoves();

        assertEquals(mock.getCurrentBestMoves(), "possible4325");

        mock.considerMove("4325");
        mock.createBestMoves();

        assertEquals(mock.getCurrentBestMoves(), "possible14361634");
    }

    @Test
    public void repetitionCheckTest(){
        MockGameable mock = new MockClassicLogic(4, 2, 2);
        mock.considerMove("3021");
        mock.considerMove("0312");
        mock.considerMove("2103");
        mock.considerMove("0110");
        mock.considerMove("3221");
        mock.considerMove("1032");
        mock.considerMove("0312");
        mock.considerMove("3210");
        mock.considerMove("1203");
        mock.considerMove("1032");
        mock.considerMove("0312");
        mock.considerMove("3210");
        mock.considerMove("1203");
        mock.considerMove("1032");
        mock.considerMove("0312");
        mock.considerMove("3210");
        mock.considerMove("1203");
        mock.considerMove("1032");
        assertTrue(mock.repetitionCheck());
    }


    @Test
    public void getWinnerTest(){
        MockGameable mock = new MockClassicLogic(4, 2, 2);
        mock.considerMove("3021");
        mock.considerMove("0312");
        mock.considerMove("2103");
        mock.considerMove("0110");
        mock.considerMove("3221");
        mock.considerMove("1032");
        mock.considerMove("0312");
        mock.considerMove("3210");
        mock.considerMove("1203");
        mock.considerMove("1032");
        mock.considerMove("0312");
        mock.considerMove("3210");
        mock.considerMove("1203");
        mock.considerMove("1032");
        mock.considerMove("0312");
        mock.considerMove("3210");
        mock.considerMove("1203");
        mock.considerMove("1032");

        assertEquals(mock.getWinner(), "tie");
    }
}
