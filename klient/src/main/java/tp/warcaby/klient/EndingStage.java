package tp.warcaby.klient;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

/**
 * FX Stage at the end of the game
 * */
public class EndingStage extends Stage {
    /**
     * FX LastStage label
     * */
    private final Label msgLabel;
    /**
     * coords of the ending stage
     * */
    private final double xCoord, yCoord;
    /**
     * constructor of the ending stage
     * */
    public EndingStage(String message, double xCoord, double yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;

        msgLabel = new Label(message);
        msgLabel.setPrefSize(250, 250);
        msgLabel.setAlignment(Pos.CENTER);

        switch (message){
            case "white":
                whiteTemplate();
                break;
            case "black":
                blackTemplate();
                break;
            case "tie":
                tieTemplate();
                break;
            case "error":
                errorTemplate();
                break;
        }

        setX(xCoord - 125);
        setY(yCoord - 125);

        Scene scene = new Scene(msgLabel);

        setScene(scene);
        setResizable(false);
        setTitle("Game message");
    }
    /**
     * FX tie of the template
     * */
    private void tieTemplate() {
        msgLabel.setText("Game tied!");
        msgLabel.setStyle(
                "-fx-background-color: orange;"+
                "-fx-font-family: Arial;" +
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold");
        msgLabel.setTextFill(Paint.valueOf("brown"));
    }
    /**
     * FX Black is winner template of stage
     * */
    private void blackTemplate() {
        msgLabel.setText("Black won the game!");
        msgLabel.setStyle(
                "-fx-background-color: white;"+
                "-fx-font-family: Arial;" +
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold");
        msgLabel.setTextFill(Paint.valueOf("black"));
    }
    /**
     * FX Last stage error happend
     * */
    private void errorTemplate() {
        msgLabel.setText("An error occured!\nRestart the game");
        msgLabel.setStyle(
                "-fx-background-color: grey;"+
                "-fx-font-family: Arial;" +
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold");
        msgLabel.setTextFill(Paint.valueOf("red"));
    }
    /**
     * white won hurra template
     * */
    private void whiteTemplate() {
        msgLabel.setText("White won the game!");
        msgLabel.setStyle(
                "-fx-background-color: black;"+
                "-fx-font-family: Arial;" +
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold;");
        msgLabel.setTextFill(Paint.valueOf("white"));
    }
}
