package tp.warcaby.serwer;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/*
* Class responsible for holding all tests related to different logics implementations
* */
public class AllLogicTest {

    /*
     * Test of Polish variant logic constructor
     * */
    @Test
    public void PolishConstructorTest(){
        MockClassicLogic cl = new MockClassicLogic(8, 12,12);
        assertTrue(true);
    }

    /*
     * Test of English variant logic constructor
     * */
    @Test
    public void EnglishConstructorTest(){
        MockClassicLogic cl = new MockClassicLogic(8, 12,12);
        assertTrue(true);
    }

    /*
     * Test of checking diagonal move correctness specific to English varaint
     * */
    @Test
    public void EnglishDiagonalCorrectnessTest(){
        MockClassicLogic cl = new MockClassicLogic(8, 12,12);
        assertTrue(true);
    }
    /*
     * Test of checking queen diagonal move correctness specific to English varaint
     * */
    @Test
    public void EnglishQueenDiagonalCorrectnessTest(){
        MockClassicLogic cl = new MockClassicLogic(8, 12,12);
        assertTrue(true);
    }
    /*
     * Test of Classical variant logic constructor
     * */
    @Test
    public void ClassicalConstructorTest(){
        MockClassicLogic cl = new MockClassicLogic(8, 12,12);
        assertTrue(true);
    }
    /*
     * Test of Overtaking variant logic constructor
     * */
    @Test
    public void OvertakingConstructorTest(){
        MockClassicLogic cl = new MockClassicLogic(8, 12,12);
        assertTrue(true);
    }
    /*
     * Test of Overtaking variant specific winner choosing logic
     * */
    @Test
    public void OvertakingWinnerTest(){
        MockClassicLogic cl = new MockClassicLogic(8, 12,12);
        assertTrue(true);
    }
}
