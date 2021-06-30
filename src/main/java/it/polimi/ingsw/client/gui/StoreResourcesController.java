package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.move.MoveDiscardResources;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The type Store resources controller. This pane allows player to discard resources.
 * After player buy something at the market she must declare if she wants to discard some of the new Resources or she keeps everything(if possible)
 * This move type is mandatory after player buy something at the market
 */
public class StoreResourcesController extends IntermediateController{
    public static final String className = "store";

    private static final Double[] RES_Y = {-200.0, -200.0, -100.0, -100.0};
    private static final Double[] RES_Y2 = {-200.0, -200.0, -200.0, -200.0, -100.0, -100.0, -100.0, -100.0};
    private static final Double[] LABEL_X = {-300.0};
    private static final Double[] LABEL_Y = {-200.0};

    private final ImageView[] imageRes = new ImageView[ResourceType.values().length];
    private final ImageView[] numberRes = new ImageView[ResourceType.values().length*2];
    @FXML
    private Label label;

    /**
     * Initialize the pane and its elements, making it resizable
     */
    @Override
    @FXML
    public void initialize(){
        super.initialize();
        for(int i=0; i< numberRes.length; i++){
            numberRes[i]= new ImageView();
            anchorPane.getChildren().add(numberRes[i]);
        }
        for(int i=0; i< imageRes.length; i++){
            imageRes[i] = new ImageView();
            anchorPane.getChildren().add(imageRes[i]);
        }
        GUI.fixImages(depots, DEPOTS_HEIGHT, imageRes, STRONGBOX_X, RES_Y, RES_SIZE);
        GUI.fixImages(depots, DEPOTS_HEIGHT, numberRes, STRONGBOX_X2, RES_Y2, RES_SIZE);
        for(ResourceType type : ResourceType.values()){
            imageRes[type.ordinal()].setImage(new Image("/images/punchboard/" + type + ".png"));
        }
        GUI.fixLabels(depots, DEPOTS_HEIGHT, new Label[]{label}, LABEL_X, LABEL_Y);
        label.setText("Hai comprato \nle seguenti risorse: ");
    }

    /**
     * @see ControllerGuiInterface#update()
     */
    @Override
    public void update() {
        super.update();
        NumberOfResources res = gui.getModel().getMe().getConverter().getResources();
        for(ResourceType type : ResourceType.values()){
            fillBox(type, res.getAmountOf(type));
        }
        gui.printResources(numberRes, res);
    }

    /**
     * @see ControllerGuiInterface#getName()
     */
    @Override
    public String getName() {
        return className;
    }

    /**
     * Send a MoveDiscardResources to server with the values taken from the choiceBoxes
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
        MoveDiscardResources move = new MoveDiscardResources(gui.getModel().getMyID());
        move.setToDiscard(resources);
        gui.sendMove(move);
    }
}
