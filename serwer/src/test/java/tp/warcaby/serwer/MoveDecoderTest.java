package tp.warcaby.serwer;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MoveDecoderTest {

    @Test
    public void ContructorTest() {
        MoveDecoder md = new MoveDecoder("1234");
        assertTrue(true);
    }

    @Test
    public void GetMessageTest() {
        MoveDecoder md = new MoveDecoder("9631");
        assertEquals(md.getMessage(), "9631");
    }

    @Test
    public void GetOldCoordsTest() {
        MoveDecoder md = new MoveDecoder("4157");
        assertEquals('4', md.getOldCoords().get(0).charValue());
        assertEquals('1', md.getOldCoords().get(1).charValue());
    }

    @Test
    public void GetNewCoordsTest() {
        MoveDecoder md = new MoveDecoder("DUPA");
        assertEquals('P', md.getNewCoords().get(0).charValue());
        assertEquals('A', md.getNewCoords().get(1).charValue());
    }
}
