package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.move.MoveWhiteConversion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.util.ArrayList;

/**
 * The type White controller. Pane to select how to convert white marbles bought from the market if more than one option is available
 * If a player has active two leader card, both with the ability to convert a white marble into a resources, each type one is bought she must chose between the two option.
 * If player has just one leader card active with the ability to convert a white marble into a resources this option is forced, so this pane will not been shown
 * Extends Intermediate Controller
 */
public class WhiteController extends IntermediateController{

    public static final String className = "whiteConversion";
    private static final Double[] LABEL_X = {-300.0};
    private static final Double[] LABEL_Y = {-200.0};

    @FXML
    private Label label;

    /**
     * Initialize the pane and its elements, making it resizable
     */
    @Override
    public void initialize(){
        super.initialize();
        GUI.fixLabels(depots, DEPOTS_HEIGHT, new Label[]{label}, LABEL_X, LABEL_Y);
    }

    /**
     * @see ControllerGuiInterface#update()
     */
    @Override
    public void update() {
        super.update();
        ArrayList<ResourceType> possibleConversion = gui.getModel().getMe().getConverter().getToconvert();
        int white = gui.getModel().getMe().getConverter().getWhite();
        for(ResourceType type : ResourceType.values()){
            if(possibleConversion.contains(type)){
                fillBox(type, white);
            }else{
                fillBox(type, 0);
            }
        }
        label.setText("Scegli come convertire " + gui.getModel().getMe().getConverter().getWhite() + " biglie bianche");
        checkConfirm();
    }

    /**
     * Send a MoveWhite to server with the values taken from the choiceBoxes
     *
     * @param actionEvent the action event
     */
    @Override
    public void onAction(ActionEvent actionEvent) {
        MoveWhiteConversion move = new MoveWhiteConversion(gui.getModel().getMyID());
        ArrayList<ResourceType> whiteMarbles = new ArrayList<>();
        for(ResourceType type: ResourceType.values()){
            Integer v =boxes[type.ordinal()].getValue();
            if(v!= null){
                for(int i=0; i<v; i++){
                    whiteMarbles.add(type);
                }
            }
        }
        move.setWhiteMarbles(whiteMarbles);
        gui.sendMove(move);
    }

    /**
     * Box action. Just check if confirm button can be enable
     *
     * @param actionEvent the action event
     */
    public void boxAction(ActionEvent actionEvent) {
        checkConfirm();
    }

    /**
     * Check buttons and set disable and not visible according to other pane parameters
     */
    private void checkConfirm() {
        int total =0;
        for(ChoiceBox<Integer> box : boxes){
            Integer v = box.getValue();
            if(v!=null)
                total += v;
        }
        confirm.setDisable(total != gui.getModel().getMe().getConverter().getWhite());
    }

    /**
     * @see ControllerGuiInterface#getName()
     */
    @Override
    public String getName() {
        return className;
    }
}
