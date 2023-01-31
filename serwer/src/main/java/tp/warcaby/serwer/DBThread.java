package tp.warcaby.serwer;

import java.io.PrintWriter;
import java.util.Scanner;

public class DBThread extends Thread {

    private final CheckersDBConnection dbConnection;
    private String course;
    private final Scanner in;
    private final PrintWriter out;
    private final int id;

    public DBThread(CheckersDBConnection db, Scanner readA, PrintWriter writeA, int id) {
        this.dbConnection = db;
        this.in = readA;
        this.out = writeA;
        this.id = id;
    }

    @Override
    public void start(){

        dbConnection.connect();
        course = dbConnection.readCourse(id);
        System.out.println(course);
        int max = course.length();
        int index = 0;

        out.println(dbConnection.readGameType(id));

        while (index != max){
            String msg = course.substring(index, index + 4);
            index += 4;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(index == max) {
                out.println("data" + msg);
            } else {
                if(course.charAt(index) == 'q'){
                    out.println("dataQ" + msg);
                    index++;
                } else {
                    out.println("data" + msg);
                }
            }
        }
        out.println("dataW" + dbConnection.readWinner(id).toUpperCase());
        dbConnection.disconnect();
    }
}
