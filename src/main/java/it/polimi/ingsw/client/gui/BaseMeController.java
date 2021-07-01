package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.modelClient.AbilityType;
import it.polimi.ingsw.client.modelClient.LeaderCardClient;
import it.polimi.ingsw.constant.enumeration.LeaderStatus;
import it.polimi.ingsw.constant.model.LeaderCard;
import it.polimi.ingsw.constant.model.NumberOfResources;
import it.polimi.ingsw.constant.model.ProductionPower;
import it.polimi.ingsw.constant.move.MoveActiveProduction;
import it.polimi.ingsw.constant.move.MoveEndTurn;
import it.polimi.ingsw.constant.move.MoveLeader;
import it.polimi.ingsw.constant.move.MoveType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

/**
 * Controller of the client player base scene, show a the Player Board, LeaderCards with all the info updates about the player clietn
 * Entry point for the begin of the turn
 */
public class BaseMeController extends BaseController{

    public static final String className = "base";

    private static final Double[] BASE_PROD_X = {576.0};
    private static final Double[] BASE_PROD_Y = {1153.0};
    private static final Double BASE_PROD_HEIGHT = 312.0;
    private static final Double[] LEAD_BUTTON_X = {2550.0, 2700.0, 2550.0, 2700.0};
    private static final Double[] LEAD_BUTTON_Y = {20.0, 20.0, 900.0, 900.0};
    private static final Double LEAD_BUTTON_HEIGHT = 70.0;
    private static final Double LEAD_BUTTON_WIDTH = 120.0;
    private static final Double[] TOKEN_X = {3000.0, 3000.0};
    private static final Double[] TOKEN_Y = {250.0, 700.0};
    private static final Double TOKEN_SIZE = 300.0;


    @FXML
    private Button market;
    @FXML
    private Button dashboard;
    @FXML
    private Button endTurn;
    @FXML
    private Button production;
    @FXML
    private Button confirm;
    @FXML
    private Button exit;
    @FXML
    private Button other;

    private final Image blackCross;

    private final Button[] leaderButton = new Button[4];
    //production
    private final ImageView baseProduction = new ImageView();
    private final ArrayList<ProductionPower> chosen = new ArrayList<>();
    //single player
    private final Image[] tokens;
    private final ImageView retro = new ImageView();
    private final ImageView revealed = new ImageView();


    /**
     * Instantiates a new Base me controller.
     */
    public BaseMeController() {
        super();
        blackCross = new Image("/images/punchboard/croce.png");
        tokens = new Image[6];
        for(int i=1; i<=6; i++){
            tokens[i-1]=new Image("/images/punchboard/cerchio"+i+".png");
        }
        retro.setImage(new Image("/images/punchboard/retro_cerchi.png"));
    }

    /**
     * Initialize the pane and its elements, making it resizable
     */
    @FXML
    public void initialize(){
        super.initialize();
        //initialize imageViews
        for(int i=0; i< leaderButton.length; i++){
            leaderButton[i] = new Button();
            leaderButton[i].setOnMouseClicked(this::onLeadButton);
            leaderButton[i].setText(i%2==0 ? "active" : "scarta");
            anchorPane.getChildren().add(leaderButton[i]);
            leaderButton[i].getStyleClass().clear();
            leaderButton[i].getStyleClass().add("baseButton");
        }


        //base production
        baseProduction.setImage(new Image("/images/board/extraProd.png"));
        anchorPane.getChildren().add(baseProduction);
        GUI.fixImages(board, BOARD_HEIGHT, new ImageView[]{baseProduction}, BASE_PROD_X, BASE_PROD_Y, BASE_PROD_HEIGHT);
        baseProduction.fitWidthProperty().bind(board.fitHeightProperty().divide(BOARD_HEIGHT / BASE_PROD_HEIGHT));


        //leadercard button
        GUI.fixLabels(board, BOARD_HEIGHT, leaderButton, LEAD_BUTTON_X, LEAD_BUTTON_Y);

        //single player
        anchorPane.getChildren().add(retro);
        anchorPane.getChildren().add(revealed);
        GUI.fixImages(board, BOARD_HEIGHT, new ImageView[]{retro, revealed}, TOKEN_X, TOKEN_Y, TOKEN_SIZE);

    }

    /**
     * @see ControllerGuiInterface#update()
     */
    @Override
    public void update() {
        super.setPlayer(gui.getModel().getMe());
        super.internalUpdate();
        String text = "Stanza:" + GUI.client.getRoomName() + "\n";
        if(gui.getModel().isMyTurn()){
            text += "E' il tuo turno\n";
            text += gui.getModel().getMe().getStatus().getTextForMe();
        }else{
            text += "E' il turno del giocatore: " + gui.getModel().getCurrPlayer().getUserName() +"\n";
            text += gui.getModel().getMe().getStatus().getTextForOther();
        }
        gameStatusLabel.setText(text);
        gameStatusLabel.setVisible(true);


        if(gui.getModel().isSinglePlayer()){
            faithTrack[gui.getModel().getSoloGame().getFaithTrack().getFaithPoints()].setImage(blackCross);
            if(gui.getModel().getSoloGame().getRevealed()!=null) {
                revealed.setImage(tokens[gui.getModel().getSoloGame().getRevealed().ordinal()]);
                revealed.setVisible(true);
            }else {
                revealed.setVisible(false);
            }
            other.setDisable(true);
            other.setVisible(false);
        }else{
            retro.setVisible(false);
            revealed.setVisible(false);
        }


        chosen.clear();
        checkButtons(true);
        exitProduction(new ActionEvent());
    }

    /**
     * Exit from the scene
     * Active MarketController
     *
     * @param actionEvent the action event
     */
//onAction functions
    public void goToMarket(ActionEvent actionEvent) {
        gui.activate(MarketController.className);
    }

    /**
     * Exit from the scene
     * Active DashBoardController
     *
     * @param actionEvent the action event
     */
    public void goToDashboard(ActionEvent actionEvent) {
        gui.activate(DashboardController.className);
    }

    /**
     * Exit from the scene
     * Active BaseController
     *
     * @param actionEvent the action event
     */
    public void goToOther(ActionEvent actionEvent) {
        gui.activate(BaseController.className);
    }

    /**
     * Send an EndTurnMove to server
     *
     * @param actionEvent the action event
     */
    public void endTurn(ActionEvent actionEvent) {
        gui.sendMove(new MoveEndTurn(gui.getModel().getMyID()));
    }

    /**
     * Change Scene, making possible to select available production
     *
     * @param actionEvent the action event
     */
    public void activeProduction(ActionEvent actionEvent){
        checkButtons(false);

        for(int i=0; i<3; i++){
            if(devCards[i*3].getImage()!=null){
                int j=0;
                while(devCards[i*3 + j].getImage()!=null && j<3) j++;
                j--;
                devCards[i*3 + j].setOnMouseClicked(this::selectCard);
            }
        }
        baseProduction.setOnMouseClicked(this::selectCard);
        int j=0;
        for(LeaderCard card : gui.getModel().getMe().getPersonalBoard().getLeaderCards()){
            if(((LeaderCardClient) card).getAbility().getAbilityType() == AbilityType.ProductionPowerPlusAbility && card.getStatus() == LeaderStatus.Played){
                leaderCards[j].setOnMouseClicked(this::selectCard);
            }
            j++;
        }

    }

    /**
     * Select a production, either from a Development Card, Leader Card or Base Production
     * if production not yet selected Highlight the imageview and add to selection buffer the production
     * if production already selected make imageview standard and remove the production from the selection buffer
     * @param actionEvent the action event
     */
    //onActionProductions
    public void selectCard(MouseEvent actionEvent){
        int index =-1;
        for(int i=0; i<9; i++){
            if(actionEvent.getSource().equals(devCards[i])){
                index = i;
            }
        }
        ProductionPower p;
        if(index>=0){
            p = gui.getModel().getMe().getPersonalBoard().getTopDevCard(index/3).getProductionPower();
        }else{
            p = gui.getModel().getMe().getPersonalBoard().getProduction().get(0);
            for(int i=0; i<2; i++){
                if(actionEvent.getSource().equals(leaderCards[i])){
                    NumberOfResources input = new NumberOfResources().add(((LeaderCardClient)gui.getModel().getMe().getPersonalBoard().getLeaderCards().get(i)).getAbility().getResource(), 1);
                    p = new ProductionPower(1, new NumberOfResources(), input, 0, 1);
                }
            }
        }
        if(chosen.contains(p)){
            chosen.remove(p);
            ((ImageView) actionEvent.getSource()).setId("imageView1");
        }else{
            chosen.add(p);
            ((ImageView) actionEvent.getSource()).setId("imageViewClicked");
        }
        checkConfirm();
    }

    /**
     * Exit from the possibility to select production
     * DO NOT send anything
     *
     * @param actionEvent the action event
     */
    public void exitProduction(ActionEvent actionEvent) {
        checkButtons(true);

        for(int i=0; i<9; i++){
            devCards[i].setOnMouseClicked(null);
            devCards[i].setId("imageView1");
        }
        baseProduction.setOnMouseClicked(null);
        baseProduction.setId("imageView1");
        for(ImageView image : leaderCards){
            image.setOnMouseClicked(null);
            image.setId("imageView1");
        }
    }

    /**
     * Send a MoveActive Production with the production in the selection buffer
     *
     * @param actionEvent the action event
     */
    public void confirmProduction(ActionEvent actionEvent) {
        MoveActiveProduction activeProduction = new MoveActiveProduction(gui.getModel().getMyID());
        activeProduction.setToActive(new ArrayList<>(chosen));
        gui.sendMove(activeProduction);
        chosen.clear();
    }

    /**
     * Send a LeaderMove, either active or discard depending on the button pressed
     *
     * @param mouseEvent the mouse event
     */
    public void onLeadButton(MouseEvent mouseEvent){
        MoveLeader move = new MoveLeader(gui.getModel().getMyID());
        for(int i=0; i< leaderButton.length; i++){
            if(leaderButton[i].equals(mouseEvent.getSource())){
                move.setIdLeaderCard(gui.getModel().getMe().getPersonalBoard().getLeaderCards().get(i/2).getId());
                move.setMove(i%2);
            }
        }
        gui.sendMove(move);
    }

    //private checkButton

    /**
     * Check buttons and set disable and not visible according to other pane parameters
     *
     * @param base of type boolean, true if in base, false if in active production
     */
    private void checkButtons(boolean base){
        dashboard.setVisible(base);
        dashboard.setDisable(!base);
        market.setVisible(base);
        market.setDisable(!base);
        production.setVisible(base);
        if(base) {
            checkProduction();
        }else{
            production.setDisable(true);
        }
        endTurn.setVisible(base);
        if(base){
            checkEndTurn();
        }else {
            endTurn.setDisable(true);
        }

        exit.setVisible(!base);
        exit.setDisable(base);
        confirm.setVisible(!base);
        if(!base) {
            checkConfirm();
        }else{
            confirm.setDisable(true);
        }
        if(base) {
            checkLeaderButton();
        }else{
            for(Button b : leaderButton) {
                b.setDisable(true);
                b.setVisible(false);
            }
            for(Label l : leaderCardsLabels){
                l.setVisible(false);
            }
        }
        gameStatusLabel.setVisible(base);
        if(!gui.getModel().isSinglePlayer()) {
            other.setVisible(base);
            other.setDisable(!base);
        }
    }

    private void checkEndTurn(){
        MoveType move = new MoveEndTurn(gui.getModel().getMyID());
        endTurn.setDisable(!move.canPerform(gui.getModel()));
    }

    private void checkConfirm(){
        confirm.setDisable(chosen.size()==0);
    }

    private void checkProduction(){
        production.setDisable(!new MoveActiveProduction(gui.getModel().getMyID()).canPerform(gui.getModel()));
    }

    private void checkLeaderButton(){
        for(Button b : leaderButton) {
            b.setVisible(true);
        }
        for(int i=0; i<2; i++){
            if(i<gui.getModel().getMe().getPersonalBoard().getLeaderCards().size()) {
                switch (gui.getModel().getMe().getPersonalBoard().getLeaderCards().get(i).getStatus()) {
                    case Dead, Played -> {
                        leaderButton[i * 2].setDisable(true);
                        leaderButton[i * 2 + 1].setDisable(true);
                        leaderButton[i * 2].setVisible(false);
                        leaderButton[i * 2 + 1].setVisible(false);
                    }
                    case onHand -> {
                        leaderButton[i * 2].setDisable(false);
                        leaderButton[i * 2 + 1].setDisable(false);
                    }
                }
                leaderCardsLabels[i].setVisible(true);
            }
            else{
                leaderButton[i * 2].setDisable(true);
                leaderButton[i * 2 + 1].setDisable(true);
                leaderButton[i * 2].setVisible(false);
                leaderButton[i * 2 + 1].setVisible(false);
                leaderCardsLabels[i].setVisible(false);
            }
        }
        if(!gui.getModel().isMyTurn()){
            for(Button b : leaderButton) {
                b.setDisable(true);
                b.setVisible(false);
            }
        }
    }


    /**
     * @see ControllerGuiInterface#getName()
     */
    @Override
    public String getName() {
        return className;
    }

}
