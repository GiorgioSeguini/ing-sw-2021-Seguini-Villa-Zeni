package it.polimi.ingsw.client.gui;

public abstract class ControllerGuiInterface {

    protected GUI gui;

    public void setGUI(GUI gui){
        this.gui=gui;
    }

    public abstract String getName();

    public abstract void update();

}
