package tp.warcaby.serwer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class ServerThreadTest
{
    //Do wyjebania chyba, ale sprobowac zeby to jednak dzialalo
    @Test
    public void ContructorTest() throws IOException {
        PrintStream standardOut = System.out;
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        ServerThread st = new ServerThread();
        String[] args = new String[0];
        ServerThread.main(args);
        assertTrue(true);
    }
}
