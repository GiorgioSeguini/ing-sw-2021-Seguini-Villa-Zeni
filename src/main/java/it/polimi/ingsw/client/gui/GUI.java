package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.client.parser.StarterClient;
import it.polimi.ingsw.constant.move.MoveType;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class GUI extends Application {

    protected static Client client;

    private final HashMap<String, Pane> screenMap = new HashMap<>();
    private final HashMap<String, ControllerGuiInterface> controllerMap = new HashMap<>();
    private Scene main;

    public static void main(String[] args, Client client) {
        GUI.client = client;
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Maestri del Rinascimento");
        Pane root = new Pane();
        this.main = new Scene(root, 600, 400);
        primaryStage.setScene(this.main);

        ArrayList<FXMLLoader> loaders = new ArrayList<>();
        loaders.add(new FXMLLoader(getClass().getResource("sample.fxml")));
        loaders.add(new FXMLLoader(getClass().getResource("initial.fxml")));

        for (FXMLLoader loader : loaders) {
            Pane pane = loader.load();
            ControllerGuiInterface controller = loader.getController();
            controller.setGUI(this);
            this.controllerMap.put(controller.getName(), controller);
            this.screenMap.put(controller.getName(), pane);
        }
        this.activate("sample");

        primaryStage.show();
    }

    public void activate(String name) {
        this.controllerMap.get(name).update();
        main.setRoot(screenMap.get(name));
    }

    public void asyncWriteToSocket(String s){
        client.asyncWriteToSocket(s);
    }

    public GameClient getModel(){
        GameClient model = null;
        while(model==null){
            model = client.getSimpleGame();
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return client.getSimpleGame();
    }

    public void sendMove(MoveType move){
        try {
            client.socketOut.writeUTF(StarterClient.toJson(move, MoveType.class));
            client.socketOut.flush();
        }catch (IOException e){
            e.printStackTrace();
            //TODO
        }
    }
}