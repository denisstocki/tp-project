package tp.warcaby.serwer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EnglishLogicTest{


    @Test
    public void considerMoveTest(){
        MockGameable mock = new MockEnglishLogic(4, 2, 2);
        mock.considerMove("3021");
        mock.considerMove("0312");
        mock.considerMove("2103");
        mock.considerMove("0110");
        mock.considerMove("3221");
        mock.considerMove("1032");
        mock.considerMove("0312");
        mock.considerMove("3210");
        assertEquals("[[E, E, E, E], [BQ, E, WQ, E], [E, E, E, E], [E, E, E, E]]", mock.getBoard().toString());
    }
}
