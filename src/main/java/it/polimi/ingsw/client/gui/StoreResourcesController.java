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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class StoreResourcesController extends IntermediateController{
    public static final String className = "store";

    @Override
    public void update() {
        super.update();
        NumberOfResources res = gui.getModel().getMe().getConverter().getResources();
        for(ResourceType type : ResourceType.values()){
            fillBox(type, res.getAmountOf(type));
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
            if(v!=null) {
                resources = resources.add(type, v);
            }
        }
        MoveDiscardResources move = new MoveDiscardResources(gui.getModel().getMyID());
        move.setToDiscard(resources);
        gui.sendMove(move);
    }
}
