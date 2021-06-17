package it.polimi.ingsw.client.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StartController extends ControllerGuiInterface{

    public static String className="start";

    @FXML
    private Button privateGame;
    @FXML
    private Button publicGame;
    @FXML
    private Button SinglePlayerOffline;

    @Override
    public String getName() {
        return className;
    }

    @Override
    public void update() {
    }

    public void publicGame(ActionEvent event) {
        gui.activate(publicLoginController.className);
    }


    public void privateGame(ActionEvent event) {
        gui.activate(privateLoginController.className);
    }

    public void soloGameOff(ActionEvent event) {
        gui.activate("fuck");
    }


}
