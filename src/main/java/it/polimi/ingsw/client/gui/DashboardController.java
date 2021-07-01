package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.constant.enumeration.ColorDevCard;
import it.polimi.ingsw.constant.enumeration.Level;
import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.model.DevelopmentCard;
import it.polimi.ingsw.constant.move.MoveBuyDevCard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

/**
 * Dashboard controller, controller of the Dashboard, show all card on sell and your depots on the right
 * Accessible even if it's not player's turn
 */
public class DashboardController extends ControllerGuiInterface{

    public static final String className = "dashboard";

    private static final Double[] IMAGE_X = {38.0, 250.0, 462.0, 674.0, 38.0, 250.0, 462.0, 674.0, 38.0, 250.0, 462.0, 674.0};
    private static final Double[] IMAGE_Y = {40.0, 40.0, 40.0, 40.0, 256.0, 256.0, 256.0, 256.0, 472.0, 472.0, 472.0, 472.0};
    private static final Double IMAGE_XDepots = 900.0;
    private static final Double IMAGE_YDepots = 100.0;
    private static final Double BOARD_DEPOTS_HEIGHT = 1017.0;
    private static final Double RES_SIZE = 80.0;
    private static final Double[] RES_X = {289.0, 231.0, 320.0, 187.0, 274.0, 365.0};
    private static final Double[] RES_Y = {173.0, 315.0, 315.0, 467.0, 467.0, 467.0};
    private static final Double[] STRONGBOX_X = {182.0, 388.0, 182.0, 388.0};
    private static final Double[] STRONGBOX_X2 = {70.0, 110.0,  290.0, 330.0, 70.0, 110.0,  290.0, 330.0};
    private static final Double[] STRONGBOX_Y = {769.0, 769.0, 883.0, 883.0};
    private static final Double[] STRONGBOX_Y2 = {769.0, 769.0, 769.0, 769.0, 883.0, 883.0, 883.0, 883.0};

    private static final Double[] BUTTON_X = {100.0, 300.0, 500.0};
    private static final Double[] BUTTON_Y = {600.0, 600.0, 600.0};

    private static final Double IMAGE_REAL = 200.0;
    private static final Double IMAGE_REAL_DEPOTS = 400.0;
    private static final Double BOARD_X = 50.0;
    private static final Double BOARD_Y = 50.0;
    private static final Double BOARD_HEIGHT_REAL = 450.0;
    private static final Double BOARD_HEIGHT = 1150.0;
    private static final Double[] DEV_CARD_X = {10.0, 490.0, 970.0};
    private static final Double[] DEV_CARD_Y = {250.0, 250.0, 250.0};
    private static final Double DEV_CARD_HEIGHT = 700.0;

    //discount ability
    private static final Double[] DISCOUNT_Y = {1169.0, 1169.0, 1283.0, 1283.0};
    private static final Double[] DISCOUNT_Y2 = {1169.0, 1169.0, 1169.0, 1169.0, 1283.0, 1283.0, 1283.0, 1283.0};
    private static final Double[] LABEL_X = {20.0};
    private static final Double[] LABEL_Y = {1100.0};


    private final ImageView[] resources = new ImageView[6];
    private final ImageView[] numberStrongbox = new ImageView[ResourceType.values().length*2];
    private final ImageView[] imageStrongbox = new ImageView[ResourceType.values().length];
    private final Image[] resImage = new Image[ResourceType.values().length];
    //discount
    private final ImageView[] numberDiscount = new ImageView[ResourceType.values().length*2];
    private final ImageView[] imageDiscount = new ImageView[ResourceType.values().length];
    private final Label labelDiscount = new Label();

    private boolean[] chosen =new boolean[12];
    private final ArrayList<Integer> choice = new ArrayList<>();

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button confirmButton;
    @FXML
    private Button baseButton;


    @FXML
    private ImageView imageViewDepots;
    @FXML
    private ImageView imageViewBoard ;
    @FXML
    private ImageView devcardBuyed;


    private final ImageView[] imageViews = new ImageView[12];
    private final ImageView[] devCards = new ImageView[3];
    private final Button[] buttons = new Button[3];


    /**
     * Instantiates a new Dashboard controller.
     * Image caching for the resources of the depots
     */
    public DashboardController() {
        for(ResourceType type : ResourceType.values()){
            resImage[type.ordinal()]= new Image("/images/punchboard/" + type + ".png");
        }
    }

    /**
     * Initialize the pane and its elements, making it resizable
     */
    @FXML
    public void initialize(){

        //first screen
        imageArrayInitializer(anchorPane, imageViews);
        for(ImageView imageView: imageViews){
            imageView.setOnMouseClicked(this::onMouseClicked);
        }
        GUI.fixImagesToPane(anchorPane,692.0,1280.0,imageViews,IMAGE_X,IMAGE_Y,IMAGE_REAL);
        //depots
        imageArrayInitializer(anchorPane, resources);
        imageArrayInitializer(anchorPane, imageStrongbox);
        imageArrayInitializer(anchorPane, numberStrongbox);
        GUI.fixImagesToPane(anchorPane, 692.0, 1280.0, imageViewDepots, IMAGE_XDepots, IMAGE_YDepots, IMAGE_REAL_DEPOTS);
        GUI.fixImages(imageViewDepots, BOARD_DEPOTS_HEIGHT, resources, RES_X, RES_Y, RES_SIZE);
        GUI.fixImages(imageViewDepots, BOARD_DEPOTS_HEIGHT, imageStrongbox, STRONGBOX_X, STRONGBOX_Y, RES_SIZE);
        for(ResourceType type : ResourceType.values()){
            imageStrongbox[type.ordinal()].setImage(resImage[type.ordinal()]);
        }
        GUI.fixImages(imageViewDepots, BOARD_DEPOTS_HEIGHT, numberStrongbox, STRONGBOX_X2, STRONGBOX_Y2, RES_SIZE);
        //discount
        imageArrayInitializer(anchorPane, imageDiscount);
        imageArrayInitializer(anchorPane, numberDiscount);
        GUI.fixImages(imageViewDepots, BOARD_DEPOTS_HEIGHT, imageDiscount, STRONGBOX_X, DISCOUNT_Y, RES_SIZE);
        GUI.fixImages(imageViewDepots, BOARD_DEPOTS_HEIGHT, numberDiscount, STRONGBOX_X2, DISCOUNT_Y2, RES_SIZE);
        GUI.fixLabels(imageViewDepots, BOARD_DEPOTS_HEIGHT, new Label[]{labelDiscount}, LABEL_X, LABEL_Y);
        anchorPane.getChildren().add(labelDiscount);
        labelDiscount.getStyleClass().clear();
        labelDiscount.getStyleClass().add("baseLabel");

        //second screen
        imageArrayInitializer(anchorPane, devCards);
        GUI.fixImagesToPane(anchorPane, 692.0, 1280.0, imageViewBoard, BOARD_X, BOARD_Y, BOARD_HEIGHT_REAL);
        GUI.fixImagesToPane(anchorPane,devcardBuyed);
        for(int i=0; i< buttons.length; i++){
            buttons[i] = new Button();
            buttons[i].setOnAction(this::choseNumber);
            buttons[i].setText("QUI");
            buttons[i].getStyleClass().clear();
            buttons[i].getStyleClass().add("baseButton");
            anchorPane.getChildren().add(buttons[i]);
        }
        GUI.fixControlToPane(anchorPane, 692.0, 1280.0, buttons, BUTTON_X, BUTTON_Y);
        GUI.fixImages(imageViewBoard, BOARD_HEIGHT, devCards, DEV_CARD_X, DEV_CARD_Y, DEV_CARD_HEIGHT);

    }

    /**
     * @see ControllerGuiInterface#update()
     */
    @Override
    public String getName() {
        return className;
    }

    /**
     * @see ControllerGuiInterface#update()
     */
    @Override
    public void update() {
        resetChoice();
        hideSecondScreen(true);
        hideFirstScreen(false);
        checkButton();
        Image[] image = new Image[12];
        int i=0;
        for(Level l: Level.values()) {
            for (ColorDevCard c : ColorDevCard.values()) {
                if(gui.getModel().getDashboard().getTopDevCard(c,l) == null){
                    image[i] = new Image("/images/back/RETRO-"+l+"-"+c+".png");
                    imageViews[i].setOnMouseClicked(null);
                }else {
                    int id = gui.getModel().getDashboard().getTopDevCard(c, l).getId() + 1;
                    image[i] = new Image("/images/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-" + id + "-1.png");
                }
                imageViews[i].setImage(image[i]);
                i++;
            }
        }

        for(i=0; i<3; i++){
            if(gui.getModel().getMe().getPersonalBoard().getPos(i).size()>0) {
                DevelopmentCard devCard= gui.getModel().getMe().getPersonalBoard().getPos(i).get(0);
                devCards[i].setImage(new Image("images/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-" + (devCard.getId() + 1) + "-1.png"));
            }
        }
        gui.printDepots(resources, gui.getModel().getMe().getDepots());
        gui.printResources(numberStrongbox,gui.getModel().getMe().getDepots().getStrongBox().getResources());
        //discount
        if(!gui.getModel().getMe().getDiscounted().isEmpty()){
            for(ResourceType type: ResourceType.values()){
                imageDiscount[type.ordinal()].setImage(resImage[type.ordinal()]);
            }
            gui.printResources(numberDiscount, gui.getModel().getMe().getDiscounted());
            labelDiscount.setText("Hai attivi i seguenti sconti");
        }
    }

    /**
     * Select a card on sell
     * if production not yet selected Highlight the imageview and add to selection buffer the card
     * if production already selected make imageview standard and remove the production from the selection buffer
     * Update buttons
     * @param mouseEvent the mouse event
     */
    public void  onMouseClicked(MouseEvent mouseEvent) {
        int index = 0;
        for(int i=0; i<imageViews.length; i++) {
            if (mouseEvent.getSource().equals(imageViews[i]))
                index = i;
        }
        if(new MoveBuyDevCard(gui.getModel().getMyID()).canPerform(gui.getModel()) && gui.getModel().getDashboard().isSomethingBuyable(gui.getModel())) {
            if (!chosen[index]) {
                choice.add(gui.getModel().getDashboard().getTopDevCard(ColorDevCard.values()[index % 4], Level.values()[index / 4]).getId());
                ((ImageView) mouseEvent.getSource()).setId("imageViewClicked");
            } else {
                choice.remove((Integer) gui.getModel().getDashboard().getTopDevCard(ColorDevCard.values()[index % 4], Level.values()[index / 4]).getId());
                ((ImageView) mouseEvent.getSource()).setId("imageView1");
            }
            chosen[index] = !chosen[index];
            checkButton();
        }
    }

    /**
     * Send a MoveBuyDevCard to server with the ids of the card in the buffer
     *
     * @param actionEvent the action event
     */
    public void confirm(ActionEvent actionEvent){
        hideFirstScreen(true);
        hideSecondScreen(false);
        devcardBuyed.setImage(new Image("images/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-"+(choice.get(0)+1)+"-1.png"));
    }

    /**
     * Exit from the scene
     * Active BaseMeController
     *
     * @param actionEvent the action event
     */
    public void baseButton(ActionEvent actionEvent){
        resetChoice();
        gui.activate(BaseMeController.className);
    }

    /**
     * Check buttons and set disable and not visible according to other pane parameters
     */
    private void checkButton(){
        this.confirmButton.setDisable(choice.size()!=1||!(new MoveBuyDevCard(gui.getModel().getMyID()).canPerform(gui.getModel()))||!(gui.getModel().getDashboard().isSomethingBuyable(gui.getModel())));
    }

    /**
     * Hide screen to select the position of the bought card
     * @param b true to hide, false to show
     */
    private void hideSecondScreen(boolean b){
        imageViewBoard.setVisible(!b);

        for (ImageView devCard : devCards) {
            devCard.setVisible(!b);

        }
        devcardBuyed.setVisible(!b);
        for(Button button: buttons){
            button.setVisible(!b);
            button.setDisable(b);
        }
    }

    /**
     * Hide first screen, the one showing the cards on sell and th depots
     * @param b true to hide, false to show
     */
    private void hideFirstScreen(boolean b) {
        for (ImageView imageView : imageViews) {
            imageView.setVisible(!b);
            imageView.setDisable(b);
        }
        for(ImageView number : numberStrongbox){
            number.setVisible(!b);
        }
        for(ImageView res : resources){
            res.setVisible(!b);
        }
        for(ImageView image: imageStrongbox){
            image.setVisible(!b);
        }
        imageViewDepots.setVisible(!b);
        checkButton();
        baseButton.setDisable(b);
        confirmButton.setVisible(!b);
        confirmButton.setDisable(b);
        baseButton.setVisible(!b);
        //discount
        for(ImageView view: imageDiscount){
            view.setVisible(!b);
        }
        for(ImageView view: numberDiscount){
            view.setVisible(!b);
        }
        labelDiscount.setVisible(!b);
    }

    /**
     * Chose the position to store the bought card
     *
     * @param actionEvent the action event
     */
    public void choseNumber(ActionEvent actionEvent) {
        int index=-1;
        for(int i=0; i< buttons.length; i++){
            if(buttons[i].equals(actionEvent.getSource())){
                index = i;
            }
        }
        //System.out.println(index);
        MoveBuyDevCard move= new MoveBuyDevCard(gui.getModel().getMyID());
        move.setIndexCardToBuy(choice.get(0));
        move.setPos(index);
        gui.sendMove(move);
    }

    private void resetChoice(){
        choice.clear();
        for(ImageView imageView: imageViews){
            imageView.setId("imageView1");
        }
        chosen=new boolean[12];
    }

    /**
     * Initialize an array of mageView and add them to the pane.
     *
     * @param anchorPane the anchor pane
     * @param imageViews the image views array
     */
    private void imageArrayInitializer(AnchorPane anchorPane, ImageView[] imageViews){
        for(int i=0; i<imageViews.length; i++){
            imageViews[i]= new ImageView();
            anchorPane.getChildren().add(imageViews[i]);
        }
    }

}
