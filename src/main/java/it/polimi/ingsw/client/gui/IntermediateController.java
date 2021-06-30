package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.constant.enumeration.ResourceType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

/**
 * Generic controller for a Pane with the player's depots on the left and a set of choiceBox for the resources on the right
 * When overiding update methods ensure to call super in order to guarantee Good updating of default elements of the pane
 */
public abstract class IntermediateController extends ControllerGuiInterface {
    private static final Double[] RES_X = {289.0, 231.0, 320.0, 187.0, 274.0, 365.0};
    private static final Double[] RES_Y = {173.0, 315.0, 315.0, 467.0, 467.0, 467.0};
    static final Double RES_SIZE = 80.0;
    static final Double DEPOTS_HEIGHT = 1017.0;
    private static final Double[] DEPOTS_X = {220.0};
    private static final Double[] DEPOTS_Y = {146.0};
    private static final Double DEPOTS_REAL = 400.0;
    static final Double[] STRONGBOX_X = {182.0, 388.0, 182.0, 388.0};
    static final Double[] STRONGBOX_X2 = {70.0, 110.0,  290.0, 330.0, 70.0, 110.0,  290.0, 330.0};
    private static final Double[] STRONGBOX_Y = {769.0, 769.0, 883.0, 883.0};
    private static final Double[] STRONGBOX_Y2 = {769.0, 769.0, 769.0, 769.0, 883.0, 883.0, 883.0, 883.0};

    @FXML
    private ChoiceBox<Integer> coins;
    @FXML
    private ChoiceBox<Integer> servants;
    @FXML
    private ChoiceBox<Integer> shields;
    @FXML
    private ChoiceBox<Integer> stones;
    @FXML
    private ImageView coinsImage;
    @FXML
    private ImageView servantsImage;
    @FXML
    private ImageView stonesImage;
    @FXML
    private ImageView shieldsImage;
    @FXML
    AnchorPane anchorPane;
    @FXML
    ImageView depots;
    @FXML
    private GridPane gridPane;
    @FXML
    Button confirm;

    //strongbox
    private final ImageView[] imageStrongbox = new ImageView[ResourceType.values().length];
    private final ImageView[] numberStrongbox = new ImageView[ResourceType.values().length*2];
    final ChoiceBox<Integer>[] boxes = new ChoiceBox[4];
    private final ImageView[] resources = new ImageView[6];

    /**
     * Initialize the pane and its elements, making it resizable
     */
    @FXML
    public void initialize() {
        boxes[ResourceType.Coins.ordinal()] = coins;
        boxes[ResourceType.Servants.ordinal()] = servants;
        boxes[ResourceType.Shields.ordinal()] = shields;
        boxes[ResourceType.Stones.ordinal()] = stones;

        for(int i=0; i<imageStrongbox.length; i++){
            imageStrongbox[i]= new ImageView();
            anchorPane.getChildren().add(imageStrongbox[i]);
        }
        for(int i=0; i<numberStrongbox.length; i++){
            numberStrongbox[i]= new ImageView();
            anchorPane.getChildren().add(numberStrongbox[i]);
        }

        for (int i = 0; i < resources.length; i++) {
            resources[i] = new ImageView();
            anchorPane.getChildren().add(resources[i]);
        }

        GUI.fixImagesToPane(anchorPane, 692.0, 1280.0, new ImageView[]{depots}, DEPOTS_X, DEPOTS_Y, DEPOTS_REAL);
        anchorPane.widthProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                    gridPane.setLayoutX((Double) newValue / 2.0);
                    gridPane.setPrefWidth((Double) newValue / 2.0);
                });
        gridPane.prefHeightProperty().bind(anchorPane.heightProperty());
        GUI.fixImages(depots, DEPOTS_HEIGHT, resources, RES_X, RES_Y, RES_SIZE);

        GUI.fixImages(depots, DEPOTS_HEIGHT, imageStrongbox, STRONGBOX_X, STRONGBOX_Y, RES_SIZE);
        for(ResourceType type : ResourceType.values()){
            imageStrongbox[type.ordinal()].setImage(new Image("/images/punchboard/" + type + ".png"));
        }
        GUI.fixImages(depots, DEPOTS_HEIGHT, numberStrongbox, STRONGBOX_X2, STRONGBOX_Y2, RES_SIZE);
        GUI.fixImagesToPane(anchorPane, coinsImage);
        GUI.fixImagesToPane(anchorPane, stonesImage);
        GUI.fixImagesToPane(anchorPane, servantsImage);
        GUI.fixImagesToPane(anchorPane, shieldsImage);
    }

    /**
     * @see ControllerGuiInterface#update()
     */
    @Override
    public void update() {
        gui.printDepots(resources, gui.getModel().getMe().getDepots());
        gui.printResources(numberStrongbox, gui.getModel().getMe().getDepots().getStrongBox().getResources());
    }

    /**
     * Abstract methods set on the button at bottom right
     *
     * @param actionEvent the action event
     */
    public abstract void onAction(ActionEvent actionEvent);

    /**
     * Fill ChoiceBox with number from o to max
     * Set 0 as default value
     *
     * @param type the type of Resources of the corresponding box
     * @param max  the max possible integer value
     */
    void fillBox(ResourceType type, int max) {
        ObservableList<Integer> array = FXCollections.observableList(new ArrayList<>());
        for(int i=0; i<=max; i++){
            array.addAll(i);
        }
        boxes[type.ordinal()].setItems(array);
        boxes[type.ordinal()].setValue(0);
    }
}