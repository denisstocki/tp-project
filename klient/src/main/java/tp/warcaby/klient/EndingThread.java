package tp.warcaby.klient;

import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * FX Ending thread run after game finishes
 * */
public class EndingThread extends Thread {

    private final Stage stage;

    public EndingThread(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void run() {
        Platform.runLater(stage::show);
    }
}
