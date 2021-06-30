package it.polimi.ingsw.client.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * The type Single login cotroler.Show a pane that allows user to join a public room
 */
public class SingleLoginController extends ControllerGuiInterface{

    public static final String className = "singleLogin";
    private String name;

    @FXML
    private TextField name_lable;
    @FXML
    private Button button;
    @FXML
    private Button comeBack;

    /**
     * Sets player name
     */
    public void setName() {
        this.name = name_lable.getText();
        this.button.setDisable(notCanActive());
    }

    /**
     * Start a new singlePlayer Game offline
     */
    public void start() {
        GUI.client.startOffline(name);
    }

    private boolean notCanActive() {
        return name == null;
    }

    /**
     * @see ControllerGuiInterface#update()
     */
    @Override
    public void update() {
        name = null;
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
