package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.UI;
import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.client.parser.StarterClient;
import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.enumeration.GameStatus;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.constant.move.MoveType;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        //check initialization
        if(getModel()==null) {
            if(active)
                this.activate("lobby");
            return;
        }
        //check error
        if(getModel().getMe().getErrorMessage()!= ErrorMessage.NoError){
            AlertBox box = new AlertBox("Errore", getModel().getMe().getErrorMessage().toString());
            Button button= new Button("Ok");
            EventHandler<ActionEvent> event = e -> box.closeBox();
            button.setOnAction(event);
            box.addButton(button);
            box.display();
        }

        //show correct scene
        if(!this.getModel().isMyTurn()){
            this.activate("waiting");
            myTurn=false;
        }else {
            if(!this.getModel().getMe().getErrorMessage().equals(ErrorMessage.NoError)){
                AlertBox outOfResourcesBox = new AlertBox("ATTENTO: Mancano delle risorse!", "Non hai abbastanza risorse per comprare quella carta.\n Scegliene un'altra, compra nuove risorse dal mercato o attiva una produzione!");
                outOfResourcesBox.display();
                current.update();
                return;
            }
            if(this.getModel().getStatus()== GameStatus.Initial){
                if(!this.getModel().getMe().getPersonalBoard().isReady()){
                    activate("initial");
                }else{
                    activate("initialRes");
                }
            }else{
                if(this.getModel().getMe().getStatus()== PlayerStatus.NeedToStore){
                    activate(StoreResourcesController.className);
                }else {
                    activate("base");
                }
                myTurn=true;
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

    /**
     *  For a given image view ad a given set of images fix their relative position in a resizable scene
     *  On al images will be set preserve ratio
     * @param back - of type ImageView reference to the background image
     * @param backHeight - of type Double height of the original image in background
     * @param images - of type ImageView[] array containing all the image ypu want to fix
     * @param x - of type Double[] array containing coordinate x of the wanted position
     * @param y- of type Double[] array containing coordinate y of the wanted position
     * @param imageHeight - of type Double[] height of all the image with the respect to the background original image
     */
    public static void fixImages(final ImageView back, final Double backHeight, final ImageView[] images, final Double[] x, final Double[] y, final Double imageHeight){
        if(images.length!=x.length)
            return;
        if(images.length!=y.length)
            return;
        for(int i=0; i<images.length; i++){
            int finalI = i;
            back.fitHeightProperty().addListener((observableValue, oldValue, newValue) -> images[finalI].setLayoutX(back.getLayoutX() + (Double)newValue * x[finalI]/ backHeight));
            back.fitHeightProperty().addListener((observableValue, oldValue, newValue) -> images[finalI].setLayoutY(back.getLayoutY() + (Double)newValue * y[finalI]/ backHeight));
            images[finalI].fitHeightProperty().bind(back.fitHeightProperty().divide(backHeight / imageHeight));
            images[finalI].setPreserveRatio(true);
        }
    }
}