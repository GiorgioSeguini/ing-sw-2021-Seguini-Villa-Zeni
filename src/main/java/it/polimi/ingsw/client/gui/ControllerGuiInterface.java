package it.polimi.ingsw.client.gui;

/**
 * Controller Gui interface
 * Abstract class, each JavaFx controller inherits from this class in order to be managed by GUI class
 * Each class that inherits must have an unique static attribute className of type String
 *  *
 */
public abstract class ControllerGuiInterface {

    /**
     * A reference to the GUi
     */
    protected GUI gui;

    /**
     * Set gui. call by the GUI itself
     *
     * @param gui the gui
     */
    public void setGUI(GUI gui){
        this.gui=gui;
    }

    /**
     * Gets the unique className
     *
     * @return the className of type String
     */
    public abstract String getName();

    /**
     * function called by the GUI before showing the scene
     * must reset everything
     */
    public abstract void update();

}
