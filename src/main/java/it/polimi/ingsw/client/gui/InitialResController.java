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

    @FXML
    public void initialize(){
        boxes[ResourceType.Coins.ordinal()] = coins;
        boxes[ResourceType.Servants.ordinal()] = servants;
        boxes[ResourceType.Shields.ordinal()] = shields;
        boxes[ResourceType.Stones.ordinal()] = stones;
    }


    @Override
    public void update() {
        this.max = gui.getModel().getInitialResources(gui.getModel().getMyID());

        for(ResourceType type : ResourceType.values()){
             fillBox(type, max);
        }

        label.setText("Scegli "+ max + " risorse iniziali");
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
        MoveChoseInitialResources move = new MoveChoseInitialResources(gui.getModel().getMyID());
        move.setResources(resources);
        gui.sendMove(move);
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
        confirm.setDisable(total!=max);
    }

    public void boxAction(ActionEvent actionEvent) {
        checkConfirm();
    }
}
