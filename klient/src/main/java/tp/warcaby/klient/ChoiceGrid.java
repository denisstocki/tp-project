package tp.warcaby.klient;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class ChoiceGrid extends GridPane {
    private final Label chooseText, classicChoice, englishChoice, overtakingChoice, polishChoice;

    private String choice;
    private Stage stage;

    public ChoiceGrid(Stage stage) {
        this.stage = stage;

        chooseText = new Label("Wybierz rodzaj gry:");
        classicChoice = new Label("1. Warcaby klasyczne");
        englishChoice = new Label("2. Warcaby angielskie");
        overtakingChoice = new Label("3. Warcaby wybijanka");
        polishChoice = new Label("4. Warcaby polskie");

        add(chooseText, 0, 0);
        add(classicChoice, 0, 1);
        add(englishChoice, 0, 2);
        add(overtakingChoice, 0, 3);
        add(polishChoice, 0, 4);

        setGridStyle();

        setGridLayout();

        setLabelBehaviour(classicChoice);
        setLabelBehaviour(englishChoice);
        setLabelBehaviour(overtakingChoice);
        setLabelBehaviour(polishChoice);

        classicChoice.setOnMouseClicked(mouseEvent -> {
            choice = "classic";
            this.stage.hide();
        });
        englishChoice.setOnMouseClicked(mouseEvent -> {
            choice = "english";
            this.stage.hide();
        });
        overtakingChoice.setOnMouseClicked(mouseEvent -> {
            choice = "overtaking";
            this.stage.hide();
        });
        polishChoice.setOnMouseClicked(mouseEvent -> {
            choice = "polish";
            this.stage.hide();
        });
    }

    private void setLabelBehaviour(Label label){
        label.setOnMouseEntered(mouseDragEvent -> label.setStyle("-fx-background-radius: 20 20 20 20;" +
                "-fx-background-color: BD7A44;"+
                "-fx-font-family: Arial;" +
                "-fx-font-size: 17px;" +
                "-fx-border-width: 2;" +
                "-fx-border-color: black;"+
                "-fx-border-style: solid;" +
                "-fx-border-radius: 20 20 20 20"));
        label.setOnMouseExited(mouseDragEvent -> label.setStyle("-fx-background-radius: 20 20 20 20;" +
                "-fx-background-color: BD7A44;"+
                "-fx-font-family: Arial;" +
                "-fx-font-size: 17px;" +
                "-fx-border-width: 0;" +
                "-fx-border-color: none;" +
                "-fx-border-style: solid"));
    }

    private void setGridLayout() {
        chooseText.setPrefSize(220, 50);
        classicChoice.setPrefSize(220, 50);
        englishChoice.setPrefSize(220, 50);
        overtakingChoice.setPrefSize(220, 50);
        polishChoice.setPrefSize(220, 50);

        setAlignment(Pos.CENTER);

        chooseText.setAlignment(Pos.CENTER);
        classicChoice.setAlignment(Pos.CENTER);
        englishChoice.setAlignment(Pos.CENTER);
        overtakingChoice.setAlignment(Pos.CENTER);
        polishChoice.setAlignment(Pos.CENTER);

        setVgap(8);
    }

    private void setGridStyle() {
        chooseText.setBackground(Background.fill(Paint.valueOf("#BD7A44")));
        classicChoice.setBackground(Background.fill(Paint.valueOf("BD7A44")));
        englishChoice.setBackground(Background.fill(Paint.valueOf("BD7A44")));
        overtakingChoice.setBackground(Background.fill(Paint.valueOf("BD7A44")));
        polishChoice.setBackground(Background.fill(Paint.valueOf("BD7A44")));

        setBackground(Background.fill(Paint.valueOf("E3C193")));

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
        polishChoice.setStyle("-fx-background-radius: 20 20 20 20;" +
                "-fx-background-color: BD7A44;" +
                "-fx-font-family: Arial;" +
                "-fx-font-size: 17px");
    }

    public String getChoice() {
        return choice;
    }
}
