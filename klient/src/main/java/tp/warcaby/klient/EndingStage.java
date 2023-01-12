package tp.warcaby.klient;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class EndingStage extends Stage {
    private final Label msgLabel;
    private final double xCoord, yCoord;

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

    private void tieTemplate() {
        msgLabel.setText("Game tied!");
        msgLabel.setStyle(
                "-fx-background-color: orange;"+
                "-fx-font-family: Arial;" +
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold");
        msgLabel.setTextFill(Paint.valueOf("brown"));
    }

    private void blackTemplate() {
        msgLabel.setText("Black won the game!");
        msgLabel.setStyle(
                "-fx-background-color: white;"+
                "-fx-font-family: Arial;" +
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold");
        msgLabel.setTextFill(Paint.valueOf("black"));
    }

    private void errorTemplate() {
        msgLabel.setText("An error occured!\nRestart the game");
        msgLabel.setStyle(
                "-fx-background-color: grey;"+
                "-fx-font-family: Arial;" +
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold");
        msgLabel.setTextFill(Paint.valueOf("red"));
    }

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
