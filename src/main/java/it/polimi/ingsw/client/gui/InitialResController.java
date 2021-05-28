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
    ChoiceBox<Integer> box0;
    @FXML
    ChoiceBox<Integer> box1;
    @FXML
    ChoiceBox<Integer> box2;
    @FXML
    ChoiceBox<Integer> box3;
    @FXML
    Label label;
    @FXML
    Button button;

    private final ArrayList<ChoiceBox> boxes = new ArrayList<>();
    private NumberOfResources resources = new NumberOfResources();
    @FXML
    public void initialize(){
        boxes.add(box0);
        boxes.add(box1);
        boxes.add(box2);
        boxes.add(box3);
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
        int new_value = 0;
        if(((ChoiceBox) actionEvent.getSource()).getValue()!=null){
            new_value = (Integer)((ChoiceBox) actionEvent.getSource()).getValue();
        }
        ((ChoiceBox) actionEvent.getSource()).setValue(new_value);
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
