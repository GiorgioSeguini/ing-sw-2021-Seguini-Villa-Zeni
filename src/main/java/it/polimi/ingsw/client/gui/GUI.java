package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.UI;
import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.client.parser.StarterClient;
import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.constant.move.MoveType;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class GUI extends Application implements UI {

    protected static Client client;

    private final HashMap<Pane, String> screenMap = new HashMap<>();
    private final HashMap<String, FXMLLoader> loaderMap = new HashMap<>();
    private Scene main;
    private ControllerGuiInterface current;
    private boolean myTurn=false;

    public static void entry(String[] args, Client client) {

        GUI.client = client;
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        client.setGui(this);
        primaryStage.setTitle("Maestri del Rinascimento");
        Pane root = new Pane();
        this.main = new Scene(root, 600, 400);
        primaryStage.setScene(this.main);

        ArrayList<FXMLLoader> loaders = new ArrayList<>();
        loaders.add(new FXMLLoader(getClass().getResource("sample.fxml")));
        loaders.add(new FXMLLoader(getClass().getResource("initial.fxml")));
        loaders.add(new FXMLLoader(getClass().getResource("waiting.fxml")));

        for (FXMLLoader loader : loaders) {
            Pane pane = loader.load();
            ControllerGuiInterface controller = loader.getController();
            controller.setGUI(this);
            this.loaderMap.put(controller.getName(), loader);
            this.screenMap.put(pane, controller.getName());
        }
        this.activate("sample");

        primaryStage.show();
    }

    @Override
    public void update(){
        if(!this.getModel().isMyTurn()){
            this.activate("waiting");
            myTurn=false;
        }else {
            if(myTurn) {
                current.update();
            }
            else{
                myTurn=true;
                activate("initial");
            }
        }
    }
    public void activate(String name) {
        FXMLLoader loader = this.loaderMap.get(name);
        this.current = loader.getController();
        current.update();
        main.setRoot(loader.getRoot());
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