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

public class StoreResourcesController extends ControllerGuiInterface{
    public static final String className = "store";

    private static final Double[] RES_X = {289.0, 231.0, 320.0, 187.0, 274.0, 365.0};
    private static final Double[] RES_Y = {173.0, 315.0, 315.0, 467.0, 467.0, 467.0};
    private static final Double RES_SIZE = 80.0;
    private static final Double DEPOTS_HEIGHT = 1017.0;
    private static final Double[] DEPOTS_X = {220.0};
    private static final Double[] DEPOTS_Y = {146.0};
    private static final Double DEPOTS_REAL = 400.0;

    @FXML
    private ChoiceBox<Integer> coins;
    @FXML
    private ChoiceBox<Integer> servants;
    @FXML
    private ChoiceBox<Integer> shields;
    @FXML
    private ChoiceBox<Integer> stones;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView depots;
    @FXML
    private GridPane gridPane;

    private final ChoiceBox<Integer>[] boxes = new ChoiceBox[4];
    private final ImageView[] resources = new ImageView[6];

    @FXML
    public void initialize(){
        boxes[ResourceType.Coins.ordinal()] = coins;
        boxes[ResourceType.Servants.ordinal()] = servants;
        boxes[ResourceType.Shields.ordinal()] = shields;
        boxes[ResourceType.Stones.ordinal()] = stones;


        for(int i=0; i< resources.length; i++){
            resources[i] = new ImageView();
            anchorPane.getChildren().add(resources[i]);
            /*GridPane.setColumnIndex(resources[i], GridPane.getColumnIndex(depots));
            GridPane.setRowIndex(resources[i], GridPane.getRowIndex(depots));*/
        }

        GUI.fixImages(anchorPane, 692.0, new ImageView[]{depots}, DEPOTS_X, DEPOTS_Y, DEPOTS_REAL);
        anchorPane.widthProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                    gridPane.setLayoutX((Double)newValue / 2.0);
                    gridPane.setPrefWidth((Double)newValue / 2.0);
                });
        gridPane.prefHeightProperty().bind(anchorPane.heightProperty());
        GUI.fixImages(depots, DEPOTS_HEIGHT, resources, RES_X, RES_Y, RES_SIZE);
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

        gui.printDepots(resources, gui.getModel().getMe().getDepots());
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
