package tp.warcaby.serwer;

import java.io.PrintWriter;
import java.util.Scanner;

public class DBThread extends Thread {

    private final CheckersDBConnection dbConnection;
    private String course;
    private final Scanner in;
    private final PrintWriter out;

    public DBThread(CheckersDBConnection db, Scanner readA, PrintWriter writeA) {
        this.dbConnection = db;
        this.in = readA;
        this.out = writeA;
    }

    @Override
    public void start(){

        dbConnection.connect();
        course = dbConnection.readCourse(1);
        System.out.println(course);
        int max = course.length();
        int index = 0;

        out.println(dbConnection.readGameType(1));

        while (index != max){
            String msg = course.substring(index, index + 4);
            index += 4;
            if(index < max) {
                if(course.charAt(index) == 'q'){
                    out.println("dataQ" + msg);
                    index++;
                } else {
                    out.println("data" + msg);
                }
            }
            System.out.println(in.nextLine());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        dbConnection.disconnect();
    }
}
