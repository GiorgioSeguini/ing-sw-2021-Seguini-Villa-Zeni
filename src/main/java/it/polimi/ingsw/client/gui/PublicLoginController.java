package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.constant.setupper.JoinWaitngListSetupper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

/**
 * The type Public login controller.Show a pane that allows user to join a public room
 */
public class PublicLoginController extends ControllerGuiInterface{

    public static final String className = "publicLogin";
    private String name;
    private Integer number;

    @FXML
    private TextField name_lable;
    @FXML
    private ChoiceBox<Integer> number_lable;
    @FXML
    private Button button;
    @FXML
    private Button ComeBack;

    /**
     * Set Players name
     */
    public void setName(){
        this.name=name_lable.getText();
        this.button.setDisable(notCanActive());
    }

    /**
     * Set number of players in the game
     */
    public void setNumber(){
        this.number=number_lable.getValue();
        this.button.setDisable(notCanActive());
    }

    /**
     * Send a JoinWaitingListSetupper to the server with the information of others fields in the pane
     */
    public void start() {
        if(!GUI.client.setOnline()){
            AlertBox box = new AlertBox("Errore di rete", "Oh no! Non siamo riusciti a contattare il server\n Riprova più tardi o controlla la tua connessione internet");
            box.display();
            Platform.exit();
        };
        //DataOutputStream socket = GUI.client.socketOut;
        GUI.client.sendSetupper(new JoinWaitngListSetupper(name, number));
    }

    /**
     * Check if confirm but must set disable
     * @return true if confirm button must be disable, false otherwise
     */
    private boolean notCanActive(){
        return name == null || number == null;
    }

    /**
     * @see ControllerGuiInterface#update()
     */
    @Override
    public void update() {
        name=null;
        name_lable.setText("");
        button.setDisable(notCanActive());
    }

    /**
     * @see ControllerGuiInterface#getName()
     */
    @Override
    public String getName() {
        return className;
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

}
