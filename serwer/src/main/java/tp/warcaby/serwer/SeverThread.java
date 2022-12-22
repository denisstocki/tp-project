package tp.warcaby.serwer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SeverThread {
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
            GameLogic logic;
            boolean finished;
            int game_number = 1;
            String input_mess, output_mess, game_logic, winner;
            Socket socket1, socket2;
            InputStream player1in, player2in;
            BufferedReader in1, in2;
            OutputStream player1out, player2out;
            PrintWriter out1, out2;

            while (true) {

                logic = null;
                finished = false;
                socket1 = serverSocket.accept();
                player1in = socket1.getInputStream();
                in1 = new BufferedReader(new InputStreamReader(player1in));
                player1out = socket1.getOutputStream();
                out1 = new PrintWriter(player1out, true);

                //1 - wyświetl okienko z przywitaniem gracza i zapytac o rodzaj gry
                output_mess = "choose";
                out1.print(output_mess);

                //1 - odczytanie odpoweidzi na temat rodzaju gry
                game_logic = in1.readLine();

                //ustawienie zmiennej GameLogic
                switch (game_logic) {
                    case "classic":
                        logic = new ClassicCheckers();
                        break;
                    case "overtaking":
                        logic = new OvertakingCheckers();
                        break;
                    case "english":
                        logic = new EnglishCheckers();
                        break;
                }

                //2 - wyswietlenie napisu u gracza 1 o oczekiwaniu na gracza 2
                output_mess = "wait";
                out1.print(output_mess);

                socket2 = serverSocket.accept();
                player2in = socket2.getInputStream();
                in2 = new BufferedReader(new InputStreamReader(player2in));
                player2out = new DataOutputStream(socket2.getOutputStream());
                out2 = new PrintWriter(player2out, true);

                //3 - wyslanie graczowi 2 rodzaju gry
                output_mess = game_logic;
                out2.print(output_mess);

                //4 - wyslanie graczowi 1 aby wyswietlil plansze i zrobil pierwszy ruch
                output_mess = "start";
                out1.print(output_mess);

                logic.initializeBoard();

                System.out.println("Game nr: " + game_number++ + " started ...");

                while (true) {
                    turnOf(in1, out1, logic);
                    if (isFinished(logic, out1, out2)) break;
                    turnOf(in2, out2, logic);
                    if (isFinished(logic, out1, out2)) break;
                }

                System.out.println("Game nr: " + game_number++ + " finished ...");
            }

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static void turnOf(BufferedReader in, PrintWriter out, GameLogic logic) throws IOException {
        String move = in.readLine();
        while (!logic.isLegal(move)){
            out.print("illegal");
            move = in.readLine();
        }
        out.print("allowed");
        logic.movePawn(move);
    }

    private static boolean isFinished(GameLogic logic, PrintWriter out1, PrintWriter out2) {
        String output_mess;
        String winner;
        if(logic.gameFinished()){
            winner = logic.getWinner();
            output_mess = winner;
            out1.print(output_mess);
            out2.print(output_mess);
            return true;
        }
        return false;
    }
}
