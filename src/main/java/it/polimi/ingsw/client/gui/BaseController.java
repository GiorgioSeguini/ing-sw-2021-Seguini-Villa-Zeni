package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.modelClient.AbilityType;
import it.polimi.ingsw.client.modelClient.LeaderCardClient;
import it.polimi.ingsw.constant.enumeration.LeaderStatus;
import it.polimi.ingsw.constant.enumeration.PopesFavorStates;
import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.model.LeaderCard;
import it.polimi.ingsw.constant.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * Controller of the generic base scene, show a the Player Board with all the info updates about the player
 */
public class BaseController  extends ControllerGuiInterface{

    public static final String className = "other";

    static final double BOARD_HEIGHT = 1717;
    private static final double RES_SIZE = 80;
    private static final double POPE_SIZE = 145;
    private static final double CARD_HEIGHT = 700;
    private static final Double[] RES_X = {289.0, 231.0, 320.0, 187.0, 274.0, 365.0};
    private static final Double[] RES_Y = {763.0, 905.0, 905.0, 1057.0, 1057.0, 1057.0};
    private static final Double[] STRONGBOX_X = {182.0, 388.0, 182.0, 388.0};
    private static final Double[] STRONGBOX_X2 = {70.0, 110.0,  290.0, 330.0, 70.0, 110.0,  290.0, 330.0};
    private static final Double[] STRONGBOX_Y = {1357.0, 1357.0, 1471.0, 1471.0};
    private static final Double[] STRONGBOX_Y2 = {1357.0, 1357.0, 1357.0, 1357.0, 1471.0, 1471.0, 1471.0, 1471.0};
    private static final Double[] POPES_X = {607.0, 1186.0, 1884.0};
    private static final Double[] POPES_Y = {250.0, 132.0, 250.0};
    private static final Double[] DEV_X = {900.0, 900.0, 900.0, 1375.0, 1375.0, 1375.0, 1850.0, 1850.0, 1850.0};
    private static final Double[] DEV_Y = {950.0, 820.0, 690.0, 950.0, 820.0, 690.0, 950.0, 820.0, 690.0};
    private static final Double[] LEAD_X = {2420.0, 2420.0};
    private static final Double[] LEAD_Y = {100.0, 980.0};
    private static final Double[] LABEL_Y = {45.0, 900.0};
    private static final Double LABEL_SIZE = 45.0 ;
    private static final Double[] FAITH_X = { 95.0, 212.0, 329.0, 329.0, 329.0, 446.0, 563.0, 680.0, 797.0, 914.0, 914.0, 914.0, 1031.0, 1148.0, 1265.0, 1382.0, 1499.0, 1499.0, 1499.0, 1616.0, 1733.0, 1850.0, 1967.0, 2084.0, 2201.0};
    private static final Double[] FAITH_Y = {338.0, 338.0, 338.0, 221.0, 104.0, 104.0, 104.0, 104.0, 104.0, 104.0, 221.0, 338.0,  338.0,  338.0,  338.0,  338.0,  338.0,  221.0,  104.0,  104.0,  104.0,  104.0,  104.0,  104.0,  104.0};
    private static final Double FAITH_HEIGHT = 100.0;
    private static final Double LEAD_ORIGINAL_HEIGHT = 698.0;
    private static final Double[] LEAD_DEPOTS_X = {2519.0, 2682.0};
    private static final Double[] LEAD_DEPOTS_Y = {680.0, 680.0};
    private static final Double[] LEAD_DEPOTS_Y2 = {1560.0, 1560.0};
    private static final Double[] GAME_STATUS_X = {3000.0};
    private static final Double[] GAME_STATUS_Y = {10.0};
    private static final Double GAME_STATUS_HEIGHT = 100.0;
    private static final Double GAME_STATUS_WIDTH = 500.0;


    private Player player;

    @FXML
    ImageView board;
    @FXML
    AnchorPane anchorPane;
    @FXML
    private Button previous;
    @FXML
    private Button next;
    @FXML
    private Button exitOthers;

    //depots
    private final ImageView[] resources = new ImageView[6];
    //strongbox
    private final ImageView[] imageStrongbox = new ImageView[ResourceType.values().length];
    private final ImageView[] numberStrongbox = new ImageView[ResourceType.values().length*2];
    //devcard
    final ImageView[] devCards = new ImageView[9];
    //leadercard
    final ImageView[] leaderCards = new ImageView[2];
    final Label[] leaderCardsLabels = new Label[2];
    //leder card depots
    private final ImageView[][] leaderDepots = new ImageView[2][2];
    //faithtrack
    final ImageView[] faithTrack = new ImageView[25];
    private final ImageView[] popes = new ImageView[3];
    //gamestatus
    final Label gameStatusLabel = new Label();

    //image caching
    private final Image[][] popesImage = new Image[3][2];
    private final Image faith;
    private final Image[] resImage = new Image[ResourceType.values().length];

    /**
     * Instantiates a new Base controller.
     */
    public BaseController(){
        super();
        //preloading images
        popesImage[0][0] = new Image("/images/punchboard/miss_2.png");
        popesImage[0][1] = new Image("/images/punchboard/pope_favor1_front.png");
        popesImage[1][0] = new Image("/images/punchboard/miss_3.png");
        popesImage[1][1] = new Image("/images/punchboard/pope_favor2_front.png");
        popesImage[2][0] = new Image("/images/punchboard/miss_4.png");
        popesImage[2][1] = new Image("/images/punchboard/pope_favor3_front.png");

        faith = new Image("/images/punchboard/faith.png");

        for(ResourceType type : ResourceType.values()){
            resImage[type.ordinal()]= new Image("/images/punchboard/" + type + ".png");
        }
    }

    /**
     * Initialize the pane and its elements, making it resizable
     */
    @FXML
    public void initialize(){
        //initialize imageViews
        imageArrayInitializer(anchorPane, resources);
        imageArrayInitializer(anchorPane, imageStrongbox);
        imageArrayInitializer(anchorPane, numberStrongbox);
        imageArrayInitializer(anchorPane, popes);
        imageArrayInitializer(anchorPane, devCards);
        imageArrayInitializer(anchorPane, leaderCards);
        imageArrayInitializer(anchorPane, faithTrack);
        for(int i=0; i< leaderCardsLabels.length; i++){
            leaderCardsLabels[i] = new Label();
            anchorPane.getChildren().add(leaderCardsLabels[i]);
        }

        board.fitHeightProperty().bind(anchorPane.heightProperty().divide(1.1));

        GUI.fixImages(board, BOARD_HEIGHT, resources, RES_X, RES_Y, RES_SIZE);
        GUI.fixImages(board, BOARD_HEIGHT, imageStrongbox, STRONGBOX_X, STRONGBOX_Y, RES_SIZE);
        for(ResourceType type : ResourceType.values()){
            imageStrongbox[type.ordinal()].setImage(resImage[type.ordinal()]);
        }
        GUI.fixImages(board, BOARD_HEIGHT, numberStrongbox, STRONGBOX_X2, STRONGBOX_Y2, RES_SIZE);
        GUI.fixImages(board, BOARD_HEIGHT, popes, POPES_X, POPES_Y, POPE_SIZE);
        GUI.fixImages(board, BOARD_HEIGHT, devCards, DEV_X, DEV_Y, CARD_HEIGHT);
        GUI.fixImages(board, BOARD_HEIGHT, faithTrack, FAITH_X, FAITH_Y, FAITH_HEIGHT);

        //leader cards
        GUI.fixImages(board, BOARD_HEIGHT, leaderCards, LEAD_X, LEAD_Y, CARD_HEIGHT);
        GUI.fixLabels(board, BOARD_HEIGHT, leaderCardsLabels, LEAD_X, LABEL_Y);

        //leadercard depots
        for(int i=0; i<2; i++){
            for(int j=0; j<2; j++){
                leaderDepots[i][j]=new ImageView();
                anchorPane.getChildren().add(leaderDepots[i][j]);
            }
        }
        GUI.fixImages(board, BOARD_HEIGHT, leaderDepots[0], LEAD_DEPOTS_X, LEAD_DEPOTS_Y, RES_SIZE);
        GUI.fixImages(board, BOARD_HEIGHT, leaderDepots[1], LEAD_DEPOTS_X, LEAD_DEPOTS_Y2, RES_SIZE);

        //gamestatus
        anchorPane.getChildren().add(gameStatusLabel);
        GUI.fixLabels(board, BOARD_HEIGHT, new Label[]{gameStatusLabel}, GAME_STATUS_X,   GAME_STATUS_Y);
        gameStatusLabel.getStyleClass().clear();
        gameStatusLabel.getStyleClass().add("baseLabel");
    }


    /**
     * @see ControllerGuiInterface#update()
     */
    @Override
    public void update() {
        if(getPlayer()==null) {
            Player p = gui.getModel().getPlayers().get(0);
            if (gui.getModel().getMe().equals(p)) {
                p = gui.getModel().getPlayers().get(1);
            }
            setPlayer(p);
        }
        internalUpdate();
        checkButtons();
    }

    /**
     * Update for al the common elements between BAse and BaseMe controller
     */
    public void internalUpdate(){
        //depots
        gui.printDepots(resources, getPlayer().getDepots());
        gui.printResources(numberStrongbox, getPlayer().getDepots().getStrongBox().getResources());

        //faithTrack
        for(int i=0; i<3; i++) {
            if (getPlayer().getFaithTrack().getPopesFavor(i) == PopesFavorStates.FaceDown) {
                popes[i].setImage(popesImage[i][0]);
                popes[i].setVisible(true);
            } else if (getPlayer().getFaithTrack().getPopesFavor(i) == PopesFavorStates.FaceUp) {
                popes[i].setImage(popesImage[i][1]);
                popes[i].setVisible(true);
            } else {
                popes[i].setVisible(false);
            }
        }

        //devCard
        for(ImageView devCard : devCards){
            devCard.setImage(null);
        }
        for(int i=0; i<3; i++){
            int j=getPlayer().getPersonalBoard().getPos(i).size() -1;
            int k = 0;
            while(j>=0){
                int id = getPlayer().getPersonalBoard().getPos(i).get(j).getId();
                devCards[i*3 +k].setImage(new Image("images/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-"+(id+1)+"-1.png"));
                j--;
                k++;
            }
        }

        for(ImageView cell: faithTrack){
            cell.setImage(null);
        }
        faithTrack[getPlayer().getFaithTrack().getFaithPoints()].setImage(faith);

        //leader Card
        for(int i=0; i< leaderCards.length; i++){
            if(i<getPlayer().getPersonalBoard().getLeaderCards().size()){
                int id = getPlayer().getPersonalBoard().getLeaderCards().get(i).getId() +49;
                leaderCards[i].setImage(new Image("/images/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-"+ id +"-1.png"));
                leaderCardsLabels[i].setText( getPlayer().getPersonalBoard().getLeaderCards().get(i).getStatus().toString());
            }else{
                leaderCards[i].setImage(null);
                leaderCardsLabels[i].setVisible(false);
            }
        }

        //leader depots
        for(int i=0; i<2; i++){
            for(int j=0; j<2; j++){
                leaderDepots[i][0].setImage(null);
            }
        }
        for(int k=3; k<5; k++) {
            if (getPlayer().getDepots().getWareHouseDepots().getShelves().size() > k) {
                ResourceType type = getPlayer().getDepots().getWareHouseDepots().getShelves().get(k).getResType();
                int onShelf = getPlayer().getDepots().getWareHouseDepots().getShelves().get(k).getUsed();
                int i = 0;
                for (LeaderCard card : getPlayer().getPersonalBoard().getLeaderCards()) {
                    if (card.getStatus() == LeaderStatus.Played && ((LeaderCardClient) card).getAbility().getAbilityType() == AbilityType.DepotsAbility && ((LeaderCardClient) card).getAbility().getResource() == type) {
                        leaderDepots[i][0].setImage(onShelf > 0 ? resImage[type.ordinal()] : null);
                        leaderDepots[i][1].setImage(onShelf > 1 ? resImage[type.ordinal()] : null);
                    }
                    i++;
                }
            }
        }
        gameStatusLabel.setText(getPlayer().getUserName());
    }

    /**
     * Initialize an array of mageView and add them to the pane.
     *
     * @param anchorPane the anchor pane
     * @param imageViews the image views array
     */
    void imageArrayInitializer(AnchorPane anchorPane, ImageView[] imageViews){
        for(int i=0; i<imageViews.length; i++){
            imageViews[i]= new ImageView();
            anchorPane.getChildren().add(imageViews[i]);
        }

    }


    /**
     * Gets player showed
     *
     * @return the player showed
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets player showed
     *
     * @param player the player showed
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public String getName() {
        return className;
    }

    /**
     * Move to previous player according to playing order
     * change player shown
     *
     * @param actionEvent the action event
     */
    public void previous(ActionEvent actionEvent) {
        int index = gui.getModel().getPlayerIndex(getPlayer()) -1;
        if(gui.getModel().getPlayers().get(index).equals(gui.getModel().getMe())) index--;
        setPlayer(gui.getModel().getPlayers().get(index));
        internalUpdate();
        checkButtons();
    }

    /**
     * move to next player according to playing order
     * change player shown
     *
     * @param actionEvent the action event
     */
    public void next(ActionEvent actionEvent) {
        int index = gui.getModel().getPlayerIndex(getPlayer()) +1;
        if(gui.getModel().getPlayers().get(index).equals(gui.getModel().getMe())) index++;
        setPlayer(gui.getModel().getPlayers().get(index));
        internalUpdate();
        checkButtons();
    }

    /**
     * Exit from the scene
     * Active BaseMeController
     *
     * @param actionEvent the action event
     */
    public void exitOther(ActionEvent actionEvent) {
        gui.activate(BaseMeController.className);
    }

    /**
     * Check buttons and set disable and not visible according to other pane parameters
     */
    private void checkButtons(){
        int index = gui.getModel().getPlayerIndex(getPlayer());
        if(index==0) {
            previous.setDisable(true);
        }else if(index==1 && gui.getModel().getPlayerIndex(gui.getModel().getMe())==0) {
            previous.setDisable(true);
        }else {
            previous.setDisable(false);
        }

        int last = gui.getModel().getPlayers().size() -1;
        if(index==last) {
            next.setDisable(true);
        }else if(index == last -1 && gui.getModel().getPlayerIndex(gui.getModel().getMe())==last){
            next.setDisable(true);
        }else {
            next.setDisable(false);
        }
    }
}
