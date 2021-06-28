package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WaitingController extends ControllerGuiInterface{

    @FXML
    Label label;

    public static final String className = "waiting";
    @Override
    public String getName() {
        return className;
    }

    @Override
    public void update() {
        label.setText("E' il turno del giocatore: " + gui.getModel().getCurrPlayer().getUserName());
    }
}
