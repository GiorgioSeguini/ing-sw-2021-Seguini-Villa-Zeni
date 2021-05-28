package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.move.MoveChoseInitialResources;
import it.polimi.ingsw.server.controller.MoveChoseInitialResourcesExt;
import it.polimi.ingsw.server.model.exception.OutOfResourcesException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableIntegerArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class InitialResController extends ControllerGuiInterface{

    public static String className = "initialRes";

    @FXML
    ChoiceBox<Integer> boxCoin;
    @FXML
    ChoiceBox<Integer> boxServant;
    @FXML
    ChoiceBox<Integer> boxShield;
    @FXML
    ChoiceBox<Integer> boxStone;
    @FXML
    Label label;
    @FXML
    Button button;

    private final ArrayList<ChoiceBox> boxes = new ArrayList<>();
    private NumberOfResources resources = new NumberOfResources();
    @FXML
    public void initialize(){
        //TODO it's important to keep the same order as ResourceType
        boxes.add(boxServant);
        boxes.add(boxShield);
        boxes.add(boxCoin);
        boxes.add(boxStone);
    }

    @Override
    public String getName() {
        return className;
    }



    @Override
    public void update() {
        label.setText("Scegli "+ gui.getModel().getInitialResources(gui.getModel().getMyID())+" risorse");
        ObservableList<Integer> arary = FXCollections.observableList(new ArrayList<>());
        int missing = gui.getModel().getInitialResources(gui.getModel().getMyID()) - resources.size();
        for(int i=0; i<=missing; i++){
            arary.addAll(i);
        }
        for(ChoiceBox<Integer> box : boxes){
            box.setItems(arary);
        }
        checkButton();
    }


    private void checkButton(){
        button.setDisable(resources.size()!=gui.getModel().getInitialResources(gui.getModel().getMyID()));
    }

    public void onTextUpdate(ActionEvent actionEvent) {
        ResourceType type = ResourceType.values()[boxes.indexOf((ChoiceBox) actionEvent.getSource())];
        int new_value = (Integer)((ChoiceBox) actionEvent.getSource()).getValue();
        int delta = new_value - this.resources.getAmountOf(type);
        if(delta>0)
            this.resources = this.resources.add(type, delta);
        else {
            try {
                this.resources = this.resources.sub(type, -delta);
            } catch (OutOfResourcesException e) {
                e.printStackTrace();
            }
        }
        //update();
        checkButton();
    }

    public void onButton(){
        MoveChoseInitialResources move =new MoveChoseInitialResourcesExt(gui.getModel().getMyID());
        move.setResources(this.resources);
        gui.sendMove(move);
    }
}
