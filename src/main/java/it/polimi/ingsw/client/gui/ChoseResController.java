package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.move.MoveChoseResources;
import it.polimi.ingsw.server.model.exception.OutOfResourcesException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

/**
 * Controller for chose resources pane.
 * If player wants to active a production with some of choice input or output resources will use this pane
 * Extends IntermediateController
 */
public class ChoseResController extends IntermediateController{

    public static final String className = "choseRes";

    private static final Double[] LABEL_X = {50.0};
    private static final Double[] LABEL_Y = {-200.0};

    @FXML
    private Label label;

    private boolean status;

    private NumberOfResources input;
    private NumberOfResources output;

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
        NumberOfResources owned = gui.getModel().getMe().getDepots().getResources();
        try {
            owned = owned.sub(gui.getModel().getMe().getToActive().getInputRes());
        } catch (OutOfResourcesException ignored) {}
        int input = gui.getModel().getMe().getToActive().getOfYourChoiceInput();


        for(ResourceType type : ResourceType.values()){
            int max = Math.min(input, owned.getAmountOf(type));
            fillBox(type, max);
        }

        label.setText("Scegli "+ input + " risorse in input");
        status = false;
        if(input==0)
            onAction(new ActionEvent());
        checkConfirm();
    }

    /**
     * @see ControllerGuiInterface#getName()
     */
    @Override
    public String getName() {
        return className;
    }

    /**
     * @see IntermediateController#onAction(ActionEvent)
     */
    @Override
    public void onAction(ActionEvent actionEvent) {
        NumberOfResources resources = new NumberOfResources();
        for(ResourceType type : ResourceType.values()){
            Integer v = boxes[type.ordinal()].getValue();
            if(v!=null) {
                resources = resources.add(type, v);
            }
        }
        if(status==false){
            input=resources;
            status=true;
            label.setText("Scegli "+ gui.getModel().getMe().getToActive().getOfYourChoiceOutput() + " risorse in output");
            for(ResourceType type : ResourceType.values()){
                int max = gui.getModel().getMe().getToActive().getOfYourChoiceOutput();
                fillBox(type, max);
            }
        }else{
            output=resources;
            MoveChoseResources move = new MoveChoseResources(gui.getModel().getMyID());
            move.setOfYourChoiceInput(input);
            move.setOfYourChoiceOutput(output);
            gui.sendMove(move);
        }
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
        if(status==false){
            if(total==gui.getModel().getMe().getToActive().getOfYourChoiceInput()){
                confirm.setDisable(false);
            }else{
                confirm.setDisable(true);
            }
        }else {
            if (total == gui.getModel().getMe().getToActive().getOfYourChoiceOutput()) {
                confirm.setDisable(false);
            }else{
                confirm.setDisable(true);
            }
        }
    }

    /**
     * Box action. Just check if confirm button can be enable
     *
     * @param actionEvent the action event
     */
    public void boxAction(ActionEvent actionEvent) {
        checkConfirm();
    }
}
