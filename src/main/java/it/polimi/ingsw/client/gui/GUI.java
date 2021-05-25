package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.Client;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI extends Application {

    private String name;
    private Integer number;
    private static  Client client;

    private Stage stage;

    @FXML
    TextField name_lable;
    @FXML
    ChoiceBox<Integer> number_lable;
    @FXML
    Button button;


    public static void main(String[] args) {
        client = new Client("127.0.0.1", 12345);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage=primaryStage;
        primaryStage.setTitle("Maestri del Rinascimento");
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public void setName(){
        this.name=name_lable.getText();
        this.button.setDisable(notCanActive());
    }

    public void setNumber(){
        this.number=number_lable.getValue();
        this.button.setDisable(notCanActive());
    }

    public void start() throws IOException {
        client.asyncWriteToSocket(name);
        client.asyncWriteToSocket(number.toString());

    }

    private boolean notCanActive(){
        return name == null || number == null;
    }
}
