package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.move.MoveChoseInitialResources;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.util.ArrayList;

/**
 * Controller of the pane to chose initial Resources
 */
public class InitialResController extends ControllerGuiInterface{

    public static final String className = "initialRes";

    @FXML
    private ChoiceBox<Integer> coins;
    @FXML
    private ChoiceBox<Integer> servants;
    @FXML
    private ChoiceBox<Integer> shields;
    @FXML
    private ChoiceBox<Integer> stones;
    @FXML
    private Label label;
    @FXML
    private Button confirm;

    private final ChoiceBox<Integer>[] boxes = new ChoiceBox[4];
    private int max;

    /**
     * Initialize the pane and its elements, making it resizable
     */
    @FXML
    public void initialize(){
        boxes[ResourceType.Coins.ordinal()] = coins;
        boxes[ResourceType.Servants.ordinal()] = servants;
        boxes[ResourceType.Shields.ordinal()] = shields;
        boxes[ResourceType.Stones.ordinal()] = stones;
    }

    /**
     * @see ControllerGuiInterface#update()
     */
    @Override
    public void update() {
        this.max = gui.getModel().getInitialResources(gui.getModel().getMyID());

        for(ResourceType type : ResourceType.values()){
             fillBox(type, max);
        }

        label.setText("Scegli "+ max + " risorse iniziali");
    }

    /**
     * @see ControllerGuiInterface#update()
     */
    @Override
    public String getName() {
        return className;
    }

    /**
     * Send a MoveChoseInitialRes to server with the resources in the buffer
     *
     * @param actionEvent the action event
     */
    public void onAction(ActionEvent actionEvent) {
        NumberOfResources resources = new NumberOfResources();
        for(ResourceType type : ResourceType.values()){
            Integer v = boxes[type.ordinal()].getValue();
            if(v!=null) {
                resources = resources.add(type, v);
            }
        }
        MoveChoseInitialResources move = new MoveChoseInitialResources(gui.getModel().getMyID());
        move.setResources(resources);
        gui.sendMove(move);
    }

    /**
     * Fill ChoiceBox with number from o to max
     * Set 0 as default value
     *
     * @param type the type of Resources of the corresponding box
     * @param max  the max possible integer value
     */
    private void fillBox(ResourceType type, int max) {
        ObservableList<Integer> array = FXCollections.observableList(new ArrayList<>());
        for(int i=0; i<=max; i++){
            array.addAll(i);
        }
        boxes[type.ordinal()].setItems(array);
        boxes[type.ordinal()].setValue(0);
    }

    /**
     * Check buttons and set disable and not visible according to other pane parameters
     */
    private void checkConfirm(){
        int total =0;
        for(ChoiceBox<Integer> box : boxes){
            Integer v = box.getValue();
            if(v!=null)
                total += v;
        }
        confirm.setDisable(total!=max);
    }

    /**
     * Update buttons on ChoiceBoxEvent
     *
     * @param actionEvent the action event
     */
    public void boxAction(ActionEvent actionEvent) {
        checkConfirm();
    }
}
