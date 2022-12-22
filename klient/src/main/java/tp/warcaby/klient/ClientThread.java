package tp.warcaby.klient;

import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread{
    /**
     * DEKLARACJA ZMIENNYCH
     */
    private Socket socket;
    int client_number;

    /**
     * KONSTRUKTOR KLASY CLIENTTHREAD
     * @param socket
     */
    public ClientThread(Socket socket, int client_number) {
        this.socket = socket;
        this.client_number = client_number;
    }

    /**
     * FUNKCJA STARTUJACA WATEK
     */
    public void run() {

        /**
         * PROBUJEMY KOMUNIKOWAC SIE Z KLIENTEM
         */
        try {

            /**
             * ODBIERANIE Z SOCKETA
             */
            InputStream input = socket.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));

            /**
             * WYSYLANIE DO SOCKETA
             */
            OutputStream output = socket.getOutputStream();
            PrintWriter out = new PrintWriter(output, true);


            /**
             * ZAMKNIECIE SOCKETA
             */
            socket.close();

        }

        /**
         * LAPANIE WYJATKOW
         */
        catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
