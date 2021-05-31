package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.UI;
import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.client.parser.StarterClient;
import it.polimi.ingsw.constant.enumeration.GameStatus;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.constant.move.MoveType;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class GUI extends Application implements UI {

    protected static Client client;

    private final HashMap<Pane, String> screenMap = new HashMap<>();
    private final HashMap<String, FXMLLoader> loaderMap = new HashMap<>();
    private Scene main;
    private ControllerGuiInterface current;
    private boolean myTurn=false;
    private boolean active = false;

    public static void entry(Client client) {

        GUI.client = client;
        launch((String) null);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.getIcons().add(new Image("/images/punchboard/calamaio.png"));
        client.setGui(this);
        primaryStage.setTitle("Maestri del Rinascimento");
        Pane root = new Pane();
        this.main = new Scene(root, 600, 400);
        primaryStage.setScene(this.main);

        ArrayList<FXMLLoader> loaders = new ArrayList<>();
        loaders.add(new FXMLLoader(getClass().getResource("sample.fxml")));
        loaders.add(new FXMLLoader(getClass().getResource("initial.fxml")));
        loaders.add(new FXMLLoader(getClass().getResource("waiting.fxml")));
        loaders.add(new FXMLLoader(getClass().getResource("initialRes.fxml")));
        loaders.add(new FXMLLoader(getClass().getResource("base.fxml")));
        loaders.add(new FXMLLoader(getClass().getResource("market.fxml")));
        loaders.add(new FXMLLoader((getClass().getResource("lobby.fxml"))));
        loaders.add(new FXMLLoader((getClass().getResource("dashboard.fxml"))));
        loaders.add(new FXMLLoader((getClass().getResource("store.fxml"))));


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
        if(getModel()==null) {
            if(active)
                this.activate("lobby");
            return;
        }
        if(!this.getModel().isMyTurn()){
            this.activate("waiting");
            myTurn=false;
        }else {
            if(this.getModel().getStatus()== GameStatus.Initial){
                if(!this.getModel().getMe().getPersonalBoard().isReady()){
                    activate("initial");
                }else{
                    activate("initialRes");
                }
            }else{
                if(myTurn){
                    current.update();
                }else{
                    if(this.getModel().getMe().getStatus()== PlayerStatus.NeedToStore){
                        activate(StoreResourcesController.className);
                    }
                    activate("base");
                    myTurn=true;
                }
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

    @Override
    public void setActive() {
        active=true;
    }
}