package tp.warcaby.serwer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OvertakingLogicTest {

    @Test
    public void getWinnerTest(){
        MockGameable mock = new MockOvertakingLogic(4, 2, 2);
        mock.considerMove("3021");
        mock.considerMove("0312");
        mock.considerMove("2103");
        mock.considerMove("0110");
        mock.considerMove("3221");
        mock.considerMove("1032");
        mock.considerMove("0321");
        mock.considerMove("3210");


        assertEquals(mock.getWinner(), "black");
    }
}
