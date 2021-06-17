package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.UI;
import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.client.parser.StarterClient;
import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.enumeration.GameStatus;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.message.Message;
import it.polimi.ingsw.constant.model.Depots;
import it.polimi.ingsw.constant.move.MoveType;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
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
    private final Image[] resImage = new Image[ResourceType.values().length];

    public static void entry(Client client) {
        GUI.client = client;
        launch((String) null);
    }

    public GUI(){
        super();
        //image caching
        for(ResourceType type : ResourceType.values()){
            resImage[type.ordinal()]= new Image("/images/punchboard/" + type + ".png");
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.getIcons().add(new Image("/images/punchboard/calamaio.png"));
        client.setGui(this);
        primaryStage.setTitle("Maestri del Rinascimento");
        Pane root = new Pane();
        this.main = new Scene(root, 1280, 692);                //do NOT change
        primaryStage.setScene(this.main);

        ArrayList<FXMLLoader> loaders = new ArrayList<>();
        loaders.add(new FXMLLoader(getClass().getResource("publicLogin.fxml")));
        loaders.add(new FXMLLoader(getClass().getResource("initial.fxml")));
        loaders.add(new FXMLLoader(getClass().getResource("waiting.fxml")));
        loaders.add(new FXMLLoader(getClass().getResource("initialRes.fxml")));
        loaders.add(new FXMLLoader(getClass().getResource("base.fxml")));
        loaders.add(new FXMLLoader(getClass().getResource("market.fxml")));
        loaders.add(new FXMLLoader((getClass().getResource("lobby.fxml"))));
        loaders.add(new FXMLLoader((getClass().getResource("dashboard.fxml"))));
        loaders.add(new FXMLLoader((getClass().getResource("store.fxml"))));
        loaders.add(new FXMLLoader((getClass().getResource("choseRes.fxml"))));
        loaders.add(new FXMLLoader((getClass().getResource("other.fxml"))));
        loaders.add(new FXMLLoader((getClass().getResource("start.fxml"))));
        loaders.add(new FXMLLoader((getClass().getResource("privateLogin.fxml"))));
        loaders.add(new FXMLLoader((getClass().getResource("singleLogin.fxml"))));


        for (FXMLLoader loader : loaders) {
            Pane pane = loader.load();
            ControllerGuiInterface controller = loader.getController();
            controller.setGUI(this);
            this.loaderMap.put(controller.getName(), loader);
            this.screenMap.put(pane, controller.getName());
        }
        this.activate(StartController.className);
        primaryStage.minHeightProperty().bind(primaryStage.widthProperty().multiply(9.0 / 16.0));
        primaryStage.maxHeightProperty().bind(primaryStage.widthProperty().multiply(9.0 / 16.0));
        primaryStage.setMinWidth(600.0);
        primaryStage.show();
    }

    @Override
    public void update() {
        //called by background thread - the one contained in Client class for reading socket
        Platform.runLater(this::internalUpdate);
    }

    private void internalUpdate(){
        //always executed by javaFX thread

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
        //initial status
        if(this.getModel().getStatus()== GameStatus.Initial) {
            if (!this.getModel().isMyTurn()) {
                this.activate("waiting");
            } else {
                if (!this.getModel().getMe().getPersonalBoard().isReady()) {
                    activate("initial");
                } else {
                    activate("initialRes");
                }
            }
            return;
        }
        if(this.getModel().getStatus() != GameStatus.Ended){
                if(!this.getModel().isMyTurn()) {
                    if(current instanceof BaseController)
                        current.update();
                    else
                        activate(BaseMeController.className);
                }else if(this.getModel().getMe().getStatus()== PlayerStatus.NeedToStore) {
                    activate(StoreResourcesController.className);
                }else if(this.getModel().getMe().getStatus()== PlayerStatus.NeedToChoseRes){
                    activate(ChoseResController.className);
                }else if(this.getModel().getMe().getStatus()== PlayerStatus.NeedToConvert) {
                    //TODO
                    activate("");
                }else {
                    activate(BaseMeController.className);
                }
                myTurn=true;
        }else{
            //GAME ENDED
            //TODO
            activate("");
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
        client.sendMove(move);
    }

    @Override
    public void setActive() {
        active=true;
    }

    @Override
    public void stop(){
        System.out.println("Stage is closing");
        // Save file
        synchronized (client){
            client.setActive(false);
            client.notifyAll();
        }
        System.out.println("Stage closed");
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
            throw new ArithmeticException();
        if(images.length!=y.length)
            throw new ArithmeticException();
        for(int i=0; i<images.length; i++){
            int finalI = i;
            back.fitHeightProperty().addListener((observableValue, oldValue, newValue) -> images[finalI].setLayoutX(back.getLayoutX() + (Double)newValue * x[finalI]/ backHeight));
            back.fitHeightProperty().addListener((observableValue, oldValue, newValue) -> images[finalI].setLayoutY(back.getLayoutY() + (Double)newValue * y[finalI]/ backHeight));
            images[finalI].fitHeightProperty().bind(back.fitHeightProperty().divide(backHeight / imageHeight));
            images[finalI].setPreserveRatio(true);
        }
    }


    public static void fixImagesToPane(final Pane pane, final Double paneHeight, final Double paneWidth, final ImageView[] images, final Double[] x, final Double[] y, final Double imageHeight){
        if(images.length!=x.length)
            throw new ArithmeticException();
        if(images.length!=y.length)
            throw new ArithmeticException();
        for(int i=0; i<images.length; i++){
            fixImagesToPane(pane, paneHeight, paneWidth, images[i], x[i], y[i], imageHeight);
        }
    }

    public static void fixImagesToPane(final Pane pane, final Double paneHeight, final Double paneWidth, final ImageView image, final Double x, final Double y, final Double imageHeight){
        image.fitHeightProperty().bind(pane.heightProperty().divide(paneHeight / imageHeight));
        pane.widthProperty().addListener((observableValue, oldValue, newValue) -> {
            image.setLayoutX((Double)newValue * x / paneWidth);
            image.setLayoutY((Double)newValue * y / paneWidth);
        });

        //pane.heightProperty().addListener((observableValue, oldValue, newValue) -> images[finalI].setLayoutY((Double)newValue * y[finalI]/ paneHeight));
        image.setPreserveRatio(true);
    }


    public static void fixLabels(final ImageView back, final Double backHeight, final Control[] labels, final Double[] x, final Double[] y, final Double height, final Double width){
        if(labels.length!=x.length)
            throw new RuntimeException();
        if(labels.length!=y.length)
            throw new RuntimeException();
        for(int i=0; i<labels.length; i++){
            int finalI = i;
            back.fitHeightProperty().addListener((observableValue, oldValue, newValue) -> {
                labels[finalI].setLayoutX(back.getLayoutX() + (Double)newValue * x[finalI]/ backHeight);
                labels[finalI].setLayoutY(back.getLayoutY() + (Double)newValue * y[finalI]/ backHeight);
                labels[finalI].setPrefSize((Double) newValue * width / backHeight , (Double) newValue * height / backHeight);
            });

        }
    }

    public static void fixButton(final ImageView back, final Double backHeight, final Button[] buttons, final Double[] x, final Double[] y, final Double height, final Double width){
        if(buttons.length!=x.length)
            throw new RuntimeException();
        if(buttons.length!=y.length)
            throw new RuntimeException();
        for(int i=0; i<buttons.length; i++){
            int finalI = i;
            back.fitHeightProperty().addListener((observableValue, oldValue, newValue) -> {
                buttons[finalI].setLayoutX(back.getLayoutX() + (Double)newValue * x[finalI]/ backHeight);
                buttons[finalI].setLayoutY(back.getLayoutY() + (Double)newValue * y[finalI]/ backHeight);
                buttons[finalI].setPrefWidth((Double) newValue * width / backHeight);
                buttons[finalI].setFont(new Font(buttons[finalI].getFont().getName(), height / backHeight));
            });
        }
    }

    public static void fixButtonToPane(final Pane back, final Double backHeight, final Button button, final Double x, final Double y, final Double height, final Double width) {
        back.heightProperty().addListener((observableValue, oldValue, newValue) -> {
            button.setLayoutX(back.getLayoutX() + (Double)newValue * x/ backHeight);
            button.setLayoutY(back.getLayoutY() + (Double)newValue * y/ backHeight);
            button.setPrefWidth((Double) newValue * width / backHeight);
        });

    }

    public static void fixButtonToPane(final Pane back, final Button button) {
        fixButtonToPane(back, back.getPrefHeight(), button, button.getLayoutX(), button.getLayoutY(), button.getPrefHeight(), button.getPrefWidth());

    }

    public void printDepots(ImageView[] resources, Depots depots){
        int  n0 = depots.getWareHouseDepots().getShelfs().get(0).getUsed();
        if(n0>0){
            ResourceType type = depots.getWareHouseDepots().getShelfs().get(0).getResType();
            resources[0].setImage(resImage[type.ordinal()]);
            resources[0].setVisible(true);
        }else{
            resources[0].setVisible(false);
        }

        int n1 = depots.getWareHouseDepots().getShelfs().get(1).getUsed();
        if(n1>0){
            ResourceType type = depots.getWareHouseDepots().getShelfs().get(1).getResType();
            resources[1].setImage(resImage[type.ordinal()]);
            resources[1].setVisible(true);
            if(n1>1){
                resources[2].setImage(resImage[type.ordinal()]);
                resources[2].setVisible(true);
            }else{
                resources[2].setVisible(false);
            }
        }else{
            resources[1].setVisible(false);
            resources[2].setVisible(false);
        }

        int n2 = depots.getWareHouseDepots().getShelfs().get(2).getUsed();
        ResourceType type2 = depots.getWareHouseDepots().getShelfs().get(2).getResType();
        if(n2>0) {
            resources[3].setImage(resImage[type2.ordinal()]);
            resources[3].setVisible(true);
        }else {
            resources[3].setVisible(false);
        }
        if(n2>1){
            resources[4].setImage(resImage[type2.ordinal()]);
            resources[4].setVisible(true);
        }else{
            resources[4].setVisible(false);
        }
        if(n2>2){
            resources[5].setImage(resImage[type2.ordinal()]);
            resources[5].setVisible(true);
        }else{
            resources[5].setVisible(false);
        }
    }
}