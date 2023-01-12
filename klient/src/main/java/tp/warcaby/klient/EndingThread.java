package tp.warcaby.klient;

import javafx.application.Platform;
import javafx.stage.Stage;

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
