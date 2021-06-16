package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.move.MoveChoseResources;
import it.polimi.ingsw.server.model.exception.OutOfResourcesException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class ChoseResController extends ControllerGuiInterface{

    public static final String className = "choseRes";

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
    private boolean status = false;

    private NumberOfResources input;
    private NumberOfResources output;


    @FXML
    public void initialize(){
        boxes[ResourceType.Coins.ordinal()] = coins;
        boxes[ResourceType.Servants.ordinal()] = servants;
        boxes[ResourceType.Shields.ordinal()] = shields;
        boxes[ResourceType.Stones.ordinal()] = stones;
    }


    @Override
    public void update() {
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

    }

    @Override
    public String getName() {
        return className;
    }

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

    private void fillBox(ResourceType type, int max) {
        ObservableList<Integer> array = FXCollections.observableList(new ArrayList<>());
        for(int i=0; i<=max; i++){
            array.addAll(i);
        }
        boxes[type.ordinal()].setItems(array);
        boxes[type.ordinal()].setValue(0);
    }

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

    public void boxAction(ActionEvent actionEvent) {
        checkConfirm();
    }
}
