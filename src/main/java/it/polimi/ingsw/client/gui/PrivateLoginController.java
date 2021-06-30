package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.constant.setupper.CreateRoomSetupper;
import it.polimi.ingsw.constant.setupper.LinkToRoomSetupper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * The Private login controller. Show a pane that allows user to join or create private room online
 */
public class PrivateLoginController extends ControllerGuiInterface{

    public static final String className = "privateLogin";
    private String name;
    private Integer number=0;
    private String roomName;
    private boolean addinroom=false;

    private static final String addInroom= "Accedi ad una stanza";
    private static final String createroom="Crea una stanza";

    @FXML
    private TextField name_lable;
    @FXML
    private TextField roomName_lable;
    @FXML
    private ChoiceBox<Integer> number_lable;
    @FXML
    private Button button;
    @FXML
    private Button ComeBack;
    @FXML
    private MenuButton menuChoice;
    @FXML
    private MenuItem addInRoom;
    @FXML
    private MenuItem createRoom;

    /**
     * @see ControllerGuiInterface#update()
     */
    @Override
    public String getName() {
        return className;
    }

    /**
     * @see ControllerGuiInterface#update()
     */
    @Override
    public void update() {
        name=null;
        name_lable.setText("");
        roomName=null;
        roomName_lable.setText("");
        addinroom=false;
        name_lable.setVisible(false);
        roomName_lable.setVisible(false);
        number_lable.setVisible(false);
        button.setDisable(true);

        menuChoice.setText("Seleziona l'azione");
        addInRoom.setText(addInroom);
        createRoom.setText(createroom);
    }

    /**
     * Sets name for the player and checks for buttons
     */
    public void setName(){
        this.name=name_lable.getText();
        CheckValues();
    }

    /**
     * Sets number of player in the room and checks for button
     */
    public void setNumber(){
        this.number=number_lable.getValue();
        CheckValues();
    }

    /**
     * Sets room name and checks for button
     */
    public void setRoomName() {
        this.roomName=roomName_lable.getText();
        CheckValues();
    }

    /**
     * Send an appropriate SetUpper to server according to other values in the pane
     *
     * @param event the event
     */
    public void start(ActionEvent event) {
        if(!GUI.client.setOnline()){
            AlertBox box = new AlertBox("Errore di rete", "Oh no! Non siamo riusciti a contattare il server\n Riprova più tardi o controlla la tua connessione internet");
            box.display();
            Platform.exit();
        }
        if (addinroom){
            GUI.client.sendSetupper(new LinkToRoomSetupper(name, roomName));
        }
        else{
            GUI.client.sendSetupper(new CreateRoomSetupper(name, roomName, number));
        }
    }

    /**
     * Exit from the scene
     * Active Initial Controller
     *
     * @param event the action event
     */
    public void comeBack(ActionEvent event) {
        gui.activate(StartController.className);
    }


    /**
     * Manage player choice between joining or creating a room
     * In the second case show an appropriate choiceBox for the number of players
     *
     * @param event the event
     */
    public void needNumber(ActionEvent event) {
        name_lable.setVisible(true);
        roomName_lable.setVisible(true);
        if(event.getSource().equals(addInRoom)) {
            number_lable.setVisible(false);
            addinroom = true;
            menuChoice.setText(addInRoom.getText());
        }
        else{
            number_lable.setVisible(true);
            addinroom = false;
            menuChoice.setText(createRoom.getText());
        }
    }

    /**
     * Check if all field are fulfilled and eventually enable confirm button
     */
    private void CheckValues(){
        if(addinroom){
            if(name!=null && roomName!=null){
                button.setDisable(false);
            }else{
                button.setDisable(true);
            }
        }else{
            if(name!=null && roomName!=null && number!=0){
                button.setDisable(false);
            }
            else{
                button.setDisable(true);
            }
        }
    }
}
