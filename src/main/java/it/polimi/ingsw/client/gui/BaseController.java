package it.polimi.ingsw.client.gui;

import javafx.event.ActionEvent;

public class BaseController extends ControllerGuiInterface{

    public static String className = "base";

    @Override
    public String getName() {
        return className;
    }

    @Override
    public void update() {
        //TODO
    }

    public void goToMarket(ActionEvent actionEvent) {
        gui.activate(MarketController.className);
    }
}
