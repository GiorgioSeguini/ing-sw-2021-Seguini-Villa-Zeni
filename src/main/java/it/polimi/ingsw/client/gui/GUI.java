package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GUI extends Application {

    protected static Client client;
    private Stage stage;
    static ScreenController screenController;

    public static void main(String[] args) {
        client = new Client("127.0.0.1", 12345);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage=primaryStage;
        primaryStage.setTitle("Maestri del Rinascimento");
        Pane root = new Pane();
        primaryStage.setScene(new Scene(root, 600, 400));

        screenController = new ScreenController(primaryStage.getScene());
        screenController.addScreen("sample", FXMLLoader.load(getClass().getResource( "sample.fxml" )));
        screenController.addScreen("initial", FXMLLoader.load(getClass().getResource( "initial.fxml" )));
        screenController.activate("sample");

        primaryStage.show();
    }

    protected void setScene(String name){
        screenController.activate(name);
    }


}
