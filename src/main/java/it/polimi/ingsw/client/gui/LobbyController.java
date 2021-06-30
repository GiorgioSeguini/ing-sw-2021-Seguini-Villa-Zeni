package it.polimi.ingsw.client.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * The type Lobby controller. THe pane showed while player is waiting for friends to join the game
 */
public class LobbyController extends ControllerGuiInterface{

    public static final String className="lobby";
    private final HashMap<ImageView, Label> users= new HashMap<>();
    private static final ArrayList<String> defaultRoomNames= new ArrayList<>();
    private static final String disconnectNotify="Stai uscendo dalla stanza. Vuoi procedere?";
    private boolean firstIN=true;

    static {
        for (int i=1; i<=4; i++){
            defaultRoomNames.add("defaultRoom"+i);
        }
    }

    @FXML
    private GridPane grid;

    @FXML
    private ImageView imageView1;
    @FXML
    private ImageView imageView2;
    @FXML
    private ImageView imageView3;
    @FXML
    private ImageView imageView4;

    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label label4;

    @FXML
    private Label labelnotify;
    @FXML
    private Label labelroom;

    @FXML
    private Button button;

    /**
     * @see ControllerGuiInterface#update()
     */
    @Override
    public String getName() {
        return className;
    }


    /**
     * Initialize the pane and its elements, making it resizable
     */
    @FXML
    public void initialize(){
        button.setDisable(false);
        Image user= new Image("/images/user.png");
        users.put(imageView1, label1);
        users.put(imageView2, label2);
        users.put(imageView3, label3);
        users.put(imageView4, label4);

        for(ImageView imageView: users.keySet()){
            imageView.setImage(user);
        }

        for(ImageView imageView : users.keySet()) {
            imageView.fitWidthProperty().bind(grid.widthProperty().divide(5));
            imageView.fitHeightProperty().bind(grid.heightProperty().divide(2));
        }
    }

    /**
     * @see ControllerGuiInterface#update()
     */
    @Override
    public void update() {
        if(gui.getConnectionMex()!=null){
            clearScreen();
            setLableNotify();
            setPlayersImages();
        }
    }

    /**
     * Clear Screen
     */
    private void clearScreen() {
        for(ImageView imageView: users.keySet()){
            imageView.setVisible(false);
            users.get(imageView).setText("");
        }
        labelnotify.setText("");
    }

    /**
     * Shows players images
     */
    private void setPlayersImages() {
        int i=0;
        ArrayList<ImageView> imageViews= new ArrayList<>(users.keySet());
        ArrayList<Label> labels= new ArrayList<>(users.values());
        for(String name: gui.getConnectionMex().getPlayersName()){
            imageViews.get(i).setVisible(true);
            labels.get(i).setText(name);
            i++;
        }
    }

    /**
     * Set label after each notification
     */
    private void setLableNotify() {
        String roomName= GUI.client.getRoomName();
        if(firstIN){
            if(roomName==null){
                labelnotify.setText("Sei connesso nella lobby, attendi giocatori da tutto il mondo!");
                labelroom.setText("Room: Lobby");
            }
            else{
                labelnotify.setText("Sei all'interno della stanza "+roomName+". Attendi gli altri giocatori. " );
                labelroom.setText("Room: "+ GUI.client.getRoomName());
            }
            firstIN=false;
        }
        else{
            labelnotify.setText(gui.getConnectionMex().getSimpleMex());
        }

    }

    /**
     * Close the program
     */
    public void Disconnect() {
        AlertBox box= new AlertBox("Disconnessione", disconnectNotify);
        Button button= new Button("Conferma");

        EventHandler<ActionEvent> event = new
                EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        System.exit(0);
                    }
                };
        button.setOnAction(event);
        box.addButton(button);
        box.display();
    }
}
