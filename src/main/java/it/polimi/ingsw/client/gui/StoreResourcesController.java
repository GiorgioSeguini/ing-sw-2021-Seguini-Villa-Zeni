package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.move.MoveChoseResources;
import it.polimi.ingsw.constant.move.MoveDiscardResources;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;

public class StoreResourcesController extends ControllerGuiInterface{
    public static final String className = "store";

    @FXML
    private ChoiceBox<Integer> coins;
    @FXML
    private ChoiceBox<Integer> servants;
    @FXML
    private ChoiceBox<Integer> shields;
    @FXML
    private ChoiceBox<Integer> stones;

    private final ChoiceBox<Integer>[] boxes = new ChoiceBox[4];

    @FXML
    public void initialize(){
        boxes[ResourceType.Coins.ordinal()] = coins;
        boxes[ResourceType.Servants.ordinal()] = servants;
        boxes[ResourceType.Shields.ordinal()] = shields;
        boxes[ResourceType.Stones.ordinal()] = stones;
    }

    @Override
    public void update() {
        NumberOfResources res = gui.getModel().getMe().getConverter().getResources();
        for(ResourceType type : ResourceType.values()){
            ObservableList<Integer> array = FXCollections.observableList(new ArrayList<>());
            for(int i=0; i<=res.getAmountOf(type); i++){
                array.addAll(i);
            }
            boxes[type.ordinal()].setItems(array);
            boxes[type.ordinal()].setValue(0);
        }
    }

    @Override
    public String getName() {
        return className;
    }

    public void onAction(ActionEvent actionEvent) {
        NumberOfResources resources = new NumberOfResources();
        for(ResourceType type : ResourceType.values()){
            Integer v = boxes[type.ordinal()].getValue();
            if(v!=null) {     //TODO
                resources = resources.add(type, v);
            }
        }
        MoveDiscardResources move = new MoveDiscardResources(gui.getModel().getMyID());
        move.setToDiscard(resources);
        gui.sendMove(move);
    }
}
