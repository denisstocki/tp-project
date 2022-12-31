package tp.warcaby.klient;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class ChoiceStage extends Stage {
    private final double xSize = 250;
    private final double ySize = 250;
    private final GridPane gridPane;
    private final Scene scene;
    private final Label chooseText, classicChoice, englishChoice, overtakingChoice;

    private String choice;
    private boolean finished;
    private Game game;


    public ChoiceStage() {
        gridPane = new GridPane();
        scene = new Scene(gridPane, xSize, ySize);
        chooseText = new Label("Wybierz rodzaj gry:");
        classicChoice = new Label("1. Warcaby klasyczne");
        englishChoice = new Label("2. Warcaby angielskie");
        overtakingChoice = new Label("3. Warcaby wybijanka");
        choice = "none";
        finished = false;
        gridPane.add(chooseText, 0, 0);
        gridPane.add(classicChoice, 0, 1);
        gridPane.add(englishChoice, 0, 2);
        gridPane.add(overtakingChoice, 0, 3);
        chooseText.setBackground(Background.fill(Paint.valueOf("#BD7A44")));
        classicChoice.setBackground(Background.fill(Paint.valueOf("BD7A44")));
        englishChoice.setBackground(Background.fill(Paint.valueOf("BD7A44")));
        overtakingChoice.setBackground(Background.fill(Paint.valueOf("BD7A44")));
        chooseText.setStyle("-fx-background-radius: 20 20 20 20;" +
                "-fx-background-color: BD7A44;"+
                "-fx-font-family: Arial;" +
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold");
        classicChoice.setStyle("-fx-background-radius: 20 20 20 20;" +
                "-fx-background-color: BD7A44;"+
                "-fx-font-family: Arial;" +
                "-fx-font-size: 17px");
        englishChoice.setStyle("-fx-background-radius: 20 20 20 20;" +
                "-fx-background-color: BD7A44;"+
                "-fx-font-family: Arial;" +
                "-fx-font-size: 17px");
        overtakingChoice.setStyle("-fx-background-radius: 20 20 20 20;" +
                "-fx-background-color: BD7A44;" +
                "-fx-font-family: Arial;" +
                "-fx-font-size: 17px");
        chooseText.setPrefSize(220, 50);
        classicChoice.setPrefSize(220, 50);
        englishChoice.setPrefSize(220, 50);
        overtakingChoice.setPrefSize(220, 50);
        chooseText.setAlignment(Pos.CENTER);
        classicChoice.setAlignment(Pos.CENTER);
        englishChoice.setAlignment(Pos.CENTER);
        overtakingChoice.setAlignment(Pos.CENTER);
        gridPane.setBackground(Background.fill(Paint.valueOf("E3C193")));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(8);
        setResizable(false);
        setScene(scene);
        setTitle("Warcaby Splash");
        classicChoice.setOnMouseEntered(mouseDragEvent -> {
            classicChoice.setStyle("-fx-background-radius: 20 20 20 20;" +
                    "-fx-background-color: BD7A44;"+
                    "-fx-font-family: Arial;" +
                    "-fx-font-size: 17px;" +
                    "-fx-border-width: 2;" +
                    "-fx-border-color: black;"+
                    "-fx-border-style: solid;" +
                    "-fx-border-radius: 20 20 20 20");
        });
        classicChoice.setOnMouseExited(mouseDragEvent -> {
            classicChoice.setStyle("-fx-background-radius: 20 20 20 20;" +
                    "-fx-background-color: BD7A44;"+
                    "-fx-font-family: Arial;" +
                    "-fx-font-size: 17px;" +
                    "-fx-border-width: 0;" +
                    "-fx-border-color: none;" +
                    "-fx-border-style: solid");
        });
        englishChoice.setOnMouseEntered(mouseDragEvent -> {
            englishChoice.setStyle("-fx-background-radius: 20 20 20 20;" +
                    "-fx-background-color: BD7A44;"+
                    "-fx-font-family: Arial;" +
                    "-fx-font-size: 17px;" +
                    "-fx-border-width: 2;" +
                    "-fx-border-color: black;"+
                    "-fx-border-style: solid;" +
                    "-fx-border-radius: 20 20 20 20");
        });
        englishChoice.setOnMouseExited(mouseDragEvent -> {
            englishChoice.setStyle("-fx-background-radius: 20 20 20 20;" +
                    "-fx-background-color: BD7A44;"+
                    "-fx-font-family: Arial;" +
                    "-fx-font-size: 17px;" +
                    "-fx-border-width: 0;" +
                    "-fx-border-color: none;" +
                    "-fx-border-style: solid");
        });
        overtakingChoice.setOnMouseEntered(mouseDragEvent -> {
            overtakingChoice.setStyle("-fx-background-radius: 20 20 20 20;" +
                    "-fx-background-color: BD7A44;"+
                    "-fx-font-family: Arial;" +
                    "-fx-font-size: 17px;" +
                    "-fx-border-width: 2;" +
                    "-fx-border-color: black;"+
                    "-fx-border-style: solid;" +
                    "-fx-border-radius: 20 20 20 20");
        });
        overtakingChoice.setOnMouseExited(mouseDragEvent -> {
            overtakingChoice.setStyle("-fx-background-radius: 20 20 20 20;" +
                    "-fx-background-color: BD7A44;"+
                    "-fx-font-family: Arial;" +
                    "-fx-font-size: 17px;" +
                    "-fx-border-width: 0;" +
                    "-fx-border-color: none;" +
                    "-fx-border-style: solid");
        });
        classicChoice.setOnMouseClicked(mouseEvent -> {
            choice = "classic";
            finished = true;
        });
        englishChoice.setOnMouseClicked(mouseEvent -> {
            choice = "english";
            finished = true;
        });
        overtakingChoice.setOnMouseClicked(mouseEvent -> {
            choice = "overtaking";
            finished = true;
        });
    }

    public String getChoice() {
        return choice;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public boolean isFinished(){
        return finished;
    }

    public void showStage(){
        show();
    }

    public void hideStage(){
        hide();
    }
}
