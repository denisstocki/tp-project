package tp.warcaby.serwer;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Hello world!
 *
 */
public class SeverThread
{
    /**
     * FUNKCJA GL0WNA MAIN
     * @param args
     */
    public static void main(String[] args) {

        /**
         * PROBUJEMY UTWORZY SOCKETA
         */
        try (ServerSocket serverSocket = new ServerSocket(4444)) {

            /**
             * INFORMACJA SYSTEMOWA
             */
            System.out.println("Server is working on port 4444 ...");

            /**
             * DEKLARACJA ZMIENNYCH
             */
            int client_count = 0;



            /**
             * PETLA DOLACZAJACA NOWYCH UZYTKOWNIKOW
             */
            while (client_count>0) {

                /**
                 * AKCEPTACJA POLACZENIA
                 */
                Socket socket = serverSocket.accept();
                DataInputStream input = new DataInputStream(socket.getInputStream());

                /**
                 * INFORMACJA SYSTEMOWA
                 */
                System.out.println("New client connected ... Client number: " + client_count++);
            }
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
