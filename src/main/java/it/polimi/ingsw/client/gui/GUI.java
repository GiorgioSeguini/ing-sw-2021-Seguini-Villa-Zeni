package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.UI;
import it.polimi.ingsw.client.modelClient.GameClient;
import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.enumeration.GameStatus;
import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.message.ConnectionMessage;
import it.polimi.ingsw.constant.message.DisconnectMessage;
import it.polimi.ingsw.constant.message.ReconnectMessage;
import it.polimi.ingsw.constant.model.Depots;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.move.MoveType;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Gui class
 * Extends Application
 * Implements UI
 * There are some static function used in Gui Controllers to avoid code duplicate
 */
public class GUI extends Application implements UI {

    /**
     * The constant client.
     */
    protected static Client client;

    private final HashMap<String, FXMLLoader> loaderMap = new HashMap<>();
    private Scene main;
    private ControllerGuiInterface current;
    private ConnectionMessage connectionMex= null;
    private boolean active = false;
    private final Image[] resImage = new Image[ResourceType.values().length];
    private final Image[] numberImage = new Image[10];
    private final static String errorConnectionMessage="Ops! Qualcosa è andato storto! Verifica i seguenti problemi:\n" +
            "\t1. La stanza nella quale vuoi accedere ha una partita già in corso.\n" +
            "\t2. La stanza che stai cercando è inesistente.\n" +
            "\t3. Il nome con la quale stai provando ad accedere è già stato usato da un altro utente in questa stanza";

    /**
     * Entry point for launch GUI
     *
     * @param client the client
     */
    public static void entry(Client client) {
        GUI.client = client;
        launch("");
    }

    /**
     * @see UI#printConnectionMessage(ConnectionMessage)
     */
    @Override
    public void printConnectionMessage(ConnectionMessage message) {
        connectionMex=message;
        if((message instanceof DisconnectMessage || message instanceof ReconnectMessage) && getModel()!=null ){
            Platform.runLater(this::showAlertBoxConnection);
        }
        else{
            update();
        }
    }

    /**
     * show an allert box containing the network notification
     */
    private void showAlertBoxConnection() {
        AlertBox box= new AlertBox("Notifica di Rete", connectionMex.toString());
        box.display();
        connectionMex=null;
    }

    /**
     * Gets connection message. For lobby
     *
     * @return the connection message - of type String
     */
    public ConnectionMessage getConnectionMex() {
        return connectionMex;
    }

    /**
     * Instantiates a new Gui.
     * Store some Image in order to avoid reloading every tume
     */
    public GUI(){
        super();
        //image caching
        for(ResourceType type : ResourceType.values()){
            resImage[type.ordinal()]= new Image("/images/punchboard/" + type + ".png");
        }
        for(int i=0; i<=9; i++){
            numberImage[i] = new Image("/images/number/" + i + ".png");
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
        loaders.add(new FXMLLoader(getClass().getResource("/fxml/publicLogin.fxml")));
        loaders.add(new FXMLLoader(getClass().getResource("/fxml/initial.fxml")));
        loaders.add(new FXMLLoader(getClass().getResource("/fxml/waiting.fxml")));
        loaders.add(new FXMLLoader(getClass().getResource("/fxml/initialRes.fxml")));
        loaders.add(new FXMLLoader(getClass().getResource("/fxml/base.fxml")));
        loaders.add(new FXMLLoader(getClass().getResource("/fxml/market.fxml")));
        loaders.add(new FXMLLoader(getClass().getResource("/fxml/lobby.fxml")));
        loaders.add(new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml")));
        loaders.add(new FXMLLoader(getClass().getResource("/fxml/store.fxml")));
        loaders.add(new FXMLLoader(getClass().getResource("/fxml/choseRes.fxml")));
        loaders.add(new FXMLLoader(getClass().getResource("/fxml/other.fxml")));
        loaders.add(new FXMLLoader(getClass().getResource("/fxml/start.fxml")));
        loaders.add(new FXMLLoader(getClass().getResource("/fxml/privateLogin.fxml")));
        loaders.add(new FXMLLoader(getClass().getResource("/fxml/singleLogin.fxml")));
        loaders.add(new FXMLLoader(getClass().getResource("/fxml/white.fxml")));
        loaders.add(new FXMLLoader(getClass().getResource("/fxml/endGame.fxml")));


        for (FXMLLoader loader : loaders) {
            Pane pane = loader.load();
            ControllerGuiInterface controller = loader.getController();
            controller.setGUI(this);
            this.loaderMap.put(controller.getName(), loader);
        }
        this.activate(StartController.className);
        primaryStage.minHeightProperty().bind(primaryStage.widthProperty().multiply(9.0 / 16.0));
        primaryStage.maxHeightProperty().bind(primaryStage.widthProperty().multiply(9.0 / 16.0));
        primaryStage.setMinWidth(600.0);
        primaryStage.setOnCloseRequest(this::confirmClose);
        primaryStage.show();
    }

    /**
     * @see UI#update()
     * make GUI thread execute internalupdate
     */
    @Override
    public void update() {
        //called by background thread - the one contained in Client class for reading socket
        Platform.runLater(this::internalUpdate);
    }

    /**
     * @see UI#update()
     * executed by GUI thread
     */
    private void internalUpdate(){
        //always executed by javaFX thread

        //check initialization
        if(getModel()==null) {
            if(active)
                this.activate(LobbyController.className);
            else{
                AlertBox box= new AlertBox("Errore",errorConnectionMessage);
                box.display();
            }
            return;
        }
        //check error
        if(getModel().getMe().getErrorMessage()!= ErrorMessage.NoError){
            AlertBox box = new AlertBox("Errore", getModel().getMe().getErrorMessage().toString());
            box.display();
        }


        //show correct scene
        //initial status
        if(this.getModel().getStatus()== GameStatus.Initial) {
            if (!this.getModel().isMyTurn()) {
                this.activate(WaitingController.className);
            } else {
                if (!this.getModel().getMe().getPersonalBoard().isReady()) {
                    activate(InitialController.className);
                } else {
                    activate(InitialResController.className);
                }
            }
            return;
        }
        if(this.getModel().getStatus() != GameStatus.Ended){
                if(!this.getModel().isMyTurn()) {
                    if(current.getName().equals(BaseController.className) || current.getName().equals(MarketController.className) || current.getName().equals(DashboardController.className)) {
                        current.update();
                    }
                    else{
                        activate(BaseMeController.className);
                    }
                }else if(this.getModel().getMe().getStatus()== PlayerStatus.NeedToStore) {
                    activate(StoreResourcesController.className);
                }else if(this.getModel().getMe().getStatus()== PlayerStatus.NeedToChoseRes){
                    activate(ChoseResController.className);
                }else if(this.getModel().getMe().getStatus()== PlayerStatus.NeedToConvert) {
                    activate(WhiteController.className);
                }else {
                    activate(BaseMeController.className);
                }
        }else{
            //GAME ENDED
            activate(EndGameController.className);
        }

    }

    /**
     * Active a Controller, make visible a new Parent
     *
     * @param name - the className of the Controller you want to active
     */
    public void activate(String name) {
        FXMLLoader loader = this.loaderMap.get(name);
        this.current = loader.getController();
        current.update();
        main.setRoot(loader.getRoot());
    }

    /**
     * Get model game client.
     *
     * @return the game client - of type GameClient
     */
    public GameClient getModel(){
        return client.getSimpleGame();
    }

    /**
     * Send moveType to server
     *
     * @param move the move - of type MoveType
     */
    public void sendMove(MoveType move){
        client.sendMove(move);
    }

    /**
     * @see UI#setActive()
     */
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
     * Confirm close.
     * Show a pop-up before let user close gui Windowds
     *
     * @param actionEvent action event
     */
    public void confirmClose(WindowEvent actionEvent){
        Button b = new Button();
        b.setText("Annulla");
        AlertBox box = new AlertBox("Esci", "Sei sicuro di voler uscire dal gioco?");
        box.getCloseButton().setText("Esci");
        b.setOnAction(e->{
            actionEvent.consume();
            box.closeBox();
        });
        box.addButton(b);
        box.display();
    }

    /**
     * For a given image view ad a given set of images fix their relative position in a resizable scene
     * On all images will be set preserve ratio
     *
     * @param back        - of type ImageView reference to the background image
     * @param backHeight  - of type Double height of the original image in background
     * @param images      - of type ImageView[] array containing all the image you want to fix
     * @param x           - of type Double[] array containing coordinate x of the wanted position
     * @param y           - of type Double[] array containing coordinate y of the wanted position
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


     /**
     * For a given pane ad a given set of images fix their relative position in a resizable scene
     * On all images will be set preserve ratio
     *
     * @param pane        - of type Pane reference to the background pane
     * @param paneHeight  - of type Double height of the pane
     * @param paneWidth   - of type Double height of the pane
     * @param images      - of type ImageView[] array containing all the image you want to fix
     * @param x           - of type Double[] array containing coordinate x of the wanted position
     * @param y           - of type Double[] array containing coordinate y of the wanted position
     * @param imageHeight - of type Double[] height of all the image with the respect to the background pane height
     *
    */
    public static void fixImagesToPane(final Pane pane, final Double paneHeight, final Double paneWidth, final ImageView[] images, final Double[] x, final Double[] y, final Double imageHeight){
        if(images.length!=x.length)
            throw new ArithmeticException();
        if(images.length!=y.length)
            throw new ArithmeticException();
        for(int i=0; i<images.length; i++){
            fixImagesToPane(pane, paneHeight, paneWidth, images[i], x[i], y[i], imageHeight);
        }
    }

    /**
     * For a given pane ad a given image fix their relative position in a resizable scene
     * On all images will be set preserve ratio
     *
     * @param pane        - of type Pane reference to the background pane
     * @param paneHeight  - of type Double height of the pane
     * @param paneWidth   - of type Double height of the pane
     * @param image       - of type ImageView image you want to fix
     * @param x           - of type Double coordinate x of the wanted position
     * @param y           - of type Double coordinate y of the wanted position
     * @param imageHeight - of type Double height of the image with the respect to the background pane height
     */
    public static void fixImagesToPane(final Pane pane, final Double paneHeight, final Double paneWidth, final ImageView image, final Double x, final Double y, final Double imageHeight){
        image.fitHeightProperty().bind(pane.heightProperty().divide(paneHeight / imageHeight));
        pane.widthProperty().addListener((observableValue, oldValue, newValue) -> {
            image.setLayoutX((Double)newValue * x / paneWidth);
            image.setLayoutY((Double)newValue * y / paneWidth);
        });

        //pane.heightProperty().addListener((observableValue, oldValue, newValue) -> images[finalI].setLayoutY((Double)newValue * y[finalI]/ paneHeight));
        image.setPreserveRatio(true);
    }

    /**
     * For a given pane ad a given image fix their relative position in a resizable scene according to their actual position
     *
     * @param pane  the pane
     * @param image the image
     */
    public static void fixImagesToPane(final Pane pane, final ImageView image){
        double paneHeight = pane.getPrefHeight();
        double paneWidth = pane.getPrefWidth();
        double x = image.getLayoutX();
        double y = image.getLayoutY();
        double imageHeight = image.getFitHeight();
        image.fitHeightProperty().bind(pane.heightProperty().divide(paneHeight / imageHeight));
        pane.widthProperty().addListener((observableValue, oldValue, newValue) -> {
            image.setLayoutX((Double)newValue * x / paneWidth);
            image.setLayoutY((Double)newValue * y / paneWidth);
        });

        image.setPreserveRatio(true);
    }

    /**
     * For a given image view ad a given set of Control fix their relative position in a resizable scene
     * On all images will be set preserve ratio
     *
     * @param back        - of type ImageView reference to the background image
     * @param backHeight  - of type Double height of the original image in background
     * @param labels      - of type Control[] array containing all the control you want to fix
     * @param x           - of type Double[] array containing coordinate x of the wanted position
     * @param y           - of type Double[] array containing coordinate y of the wanted position
     */
    public static void fixLabels(final ImageView back, final Double backHeight, final Control[] labels, final Double[] x, final Double[] y){
        if(labels.length!=x.length)
            throw new RuntimeException();
        if(labels.length!=y.length)
            throw new RuntimeException();
        for(int i=0; i<labels.length; i++){
            int finalI = i;
            back.fitHeightProperty().addListener((observableValue, oldValue, newValue) -> {
                labels[finalI].setLayoutX(back.getLayoutX() + (Double)newValue * x[finalI]/ backHeight);
                labels[finalI].setLayoutY(back.getLayoutY() + (Double)newValue * y[finalI]/ backHeight);
                //labels[finalI].setPrefSize((Double) newValue * width / backHeight , (Double) newValue * height / backHeight);
            });

        }
    }

    /**
     * For a given pane ad a given set of control fix their relative position in a resizable scene
     *
     * @param pane        - of type Pane reference to the background pane
     * @param paneHeight  - of type Double height of the pane
     * @param paneWidth   - of type Double height of the pane
     * @param controls    - of type Control[] array containing all the control you want to fix
     * @param x           - of type Double[] array containing coordinate x of the wanted position
     * @param y           - of type Double[] array containing coordinate y of the wanted position
     *
     */
    public static void fixControlToPane(final Pane pane, final Double paneHeight, final Double paneWidth, final Control[] controls, final Double[] x, final Double[] y){
        if(controls.length!=x.length)
            throw new ArithmeticException();
        if(controls.length!=y.length)
            throw new ArithmeticException();
        for(int i=0; i<controls.length; i++){
            fixControlToPane(pane, paneHeight, paneWidth, controls[i], x[i], y[i]);
        }
    }

    /**
     * For a given pane ad a given control fix their relative position in a resizable scene
     *
     * @param pane        - of type Pane reference to the background pane
     * @param paneHeight  - of type Double height of the pane
     * @param paneWidth   - of type Double height of the pane
     * @param control     - of type Control control you want to fix
     * @param x           - of type Double coordinate x of the wanted position
     * @param y           - of type Double coordinate y of the wanted position
     */
    public static void fixControlToPane(final Pane pane, final Double paneHeight, final Double paneWidth, final Control control, final Double x, final Double y){
        pane.widthProperty().addListener((observableValue, oldValue, newValue) -> {
            control.setLayoutX((Double)newValue * x / paneWidth);
            control.setLayoutY((Double)newValue * y / paneWidth);
        });

        //pane.heightProperty().addListener((observableValue, oldValue, newValue) -> images[finalI].setLayoutY((Double)newValue * y[finalI]/ paneHeight));
    }


    /**
     * Print depots given a set of ImageView properly positioned.
     * DO not change the position of the ImageView, just set a new Image,
     *
     * @param resources the resources - of type ImageView[] an array containing the imageView where resources will be printed
     * @param depots    the depots - of type Depots
     */
    public void printDepots(ImageView[] resources, Depots depots){
        int  n0 = depots.getWareHouseDepots().getShelves().get(0).getUsed();
        if(n0>0){
            ResourceType type = depots.getWareHouseDepots().getShelves().get(0).getResType();
            resources[0].setImage(resImage[type.ordinal()]);
            resources[0].setVisible(true);
        }else{
            resources[0].setVisible(false);
        }

        int n1 = depots.getWareHouseDepots().getShelves().get(1).getUsed();
        if(n1>0){
            ResourceType type = depots.getWareHouseDepots().getShelves().get(1).getResType();
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

        int n2 = depots.getWareHouseDepots().getShelves().get(2).getUsed();
        ResourceType type2 = depots.getWareHouseDepots().getShelves().get(2).getResType();
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

    /**
     * Print a generic Number of resources.
     * DO not print the resource icons, but just the numbers in the corresponding order
     *
     * @param number    the number - of type ImageView[] an array containing the imageView where number will be printed
     * @param resources the resources - of type NUmberOfResources
     */
    public void printResources(ImageView[] number, NumberOfResources resources) {
        if(number.length!= 2*ResourceType.values().length)
            throw new RuntimeException();
        for(ResourceType type: ResourceType.values()){
            int val = resources.getAmountOf(type);
            if(val<10){
                number[type.ordinal()*2].setImage(null);
            }else{
                number[type.ordinal()*2].setImage(numberImage[val/10]);
            }
            number[type.ordinal()*2 +1].setImage(numberImage[val%10]);
        }
    }
}