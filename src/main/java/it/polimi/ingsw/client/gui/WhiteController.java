package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.move.MoveWhiteConversion;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;

public class WhiteController extends IntermediateController{

    public static final String className = "whiteConversion";


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
        checkConfirm();
    }

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

    public void boxAction(ActionEvent actionEvent) {
        checkConfirm();
    }

    private void checkConfirm() {
        int total =0;
        for(ChoiceBox<Integer> box : boxes){
            Integer v = box.getValue();
            if(v!=null)
                total += v;
        }
        confirm.setDisable(total != gui.getModel().getMe().getConverter().getWhite());
    }


    @Override
    public String getName() {
        return className;
    }
}
