package tp.warcaby.klient;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

/**
 * FX responsibility how the pawn look like
 * */
public class PlayerChoiceGrid extends GridPane {
    /**
     * FX Labels
     * */
    private final Label chooseText, playerChoice, botChoice, dbChoice;
    private final TextField chooseDB;
    /**
     * User wybór
     * */
    private String choice;
    /**
     * reference to current stage
     * */
    private final Stage stage;
    /**
     * Constructor for game typ choice grif
     * */
    public PlayerChoiceGrid(Stage stage) {
        this.stage = stage;

        chooseText = new Label("Wybierz rodzaj gracza:");
        playerChoice = new Label("1. Gracz internetowy");
        botChoice = new Label("2. Bot");
        dbChoice = new Label("3. Powtorka");
        chooseDB = new TextField("ID gry ...");

        add(chooseText, 0, 0);
        add(playerChoice, 0, 1);
        add(botChoice, 0, 2);
        add(dbChoice, 0, 3);
        add(chooseDB, 0, 4);

        setGridStyle();

        setGridLayout();

        setLabelBehaviour(playerChoice);
        setLabelBehaviour(botChoice);
        setLabelBehaviour(dbChoice);

        playerChoice.setOnMouseClicked(mouseEvent -> {
            choice = "player";
            this.stage.hide();
        });
        botChoice.setOnMouseClicked(mouseEvent -> {
            choice = "bot";
            this.stage.hide();
        });
        dbChoice.setOnMouseClicked(mouseEvent -> {
            choice = "db" + chooseDB.getText();
            this.stage.hide();
        });
    }

    /**
     * FX set label behaviour
     * */
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
    /**
     * FX set current grid layout
     * */
    private void setGridLayout() {
        chooseText.setPrefSize(270, 50);
        playerChoice.setPrefSize(270, 50);
        botChoice.setPrefSize(270, 50);
        dbChoice.setPrefSize(270, 50);
        chooseDB.setPrefSize(270, 50);

        setAlignment(Pos.CENTER);

        chooseText.setAlignment(Pos.CENTER);
        playerChoice.setAlignment(Pos.CENTER);
        botChoice.setAlignment(Pos.CENTER);
        dbChoice.setAlignment(Pos.CENTER);
        chooseDB.setAlignment(Pos.CENTER);

        setVgap(8);
    }
    /**
     * FX set Grid style
     * */
    private void setGridStyle() {
        chooseText.setBackground(Background.fill(Paint.valueOf("#BD7A44")));
        playerChoice.setBackground(Background.fill(Paint.valueOf("BD7A44")));
        botChoice.setBackground(Background.fill(Paint.valueOf("BD7A44")));
        dbChoice.setBackground(Background.fill(Paint.valueOf("BD7A44")));
        chooseDB.setBackground(Background.fill(Paint.valueOf("BD7A44")));
        setBackground(Background.fill(Paint.valueOf("E3C193")));
        chooseText.setStyle("-fx-background-radius: 20 20 20 20;" +
                "-fx-background-color: BD7A44;"+
                "-fx-font-family: Arial;" +
                "-fx-font-size: 18px;" +
                "-fx-font-weight: bold");
        playerChoice.setStyle("-fx-background-radius: 20 20 20 20;" +
                "-fx-background-color: BD7A44;"+
                "-fx-font-family: Arial;" +
                "-fx-font-size: 17px");
        botChoice.setStyle("-fx-background-radius: 20 20 20 20;" +
                "-fx-background-color: BD7A44;"+
                "-fx-font-family: Arial;" +
                "-fx-font-size: 17px");
        dbChoice.setStyle("-fx-background-radius: 20 20 20 20;" +
                "-fx-background-color: BD7A44;"+
                "-fx-font-family: Arial;" +
                "-fx-font-size: 17px");
        chooseDB.setStyle("-fx-background-radius: 20 20 20 20;" +
                "-fx-background-color: BD7A44;"+
                "-fx-font-family: Arial;" +
                "-fx-font-size: 17px");
    }
    /**
     * FX get choice of current user
     * */
    public String getChoice() {
        return choice;
    }
}
