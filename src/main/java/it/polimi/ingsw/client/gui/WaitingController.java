package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * The type Waiting controller. Waiting pane, just for the initial phase of the game while other players are selecting their initial resources / leader cards
 */
public class WaitingController extends ControllerGuiInterface{

    @FXML
    private Label label;

    public static final String className = "waiting";

    /**
     * @see ControllerGuiInterface#getName()
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
        label.setText("E' il turno del giocatore: " + gui.getModel().getCurrPlayer().getUserName());
    }
}
