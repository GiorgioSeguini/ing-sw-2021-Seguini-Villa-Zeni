package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.modelClient.AbilityType;
import it.polimi.ingsw.client.modelClient.LeaderCardClient;
import it.polimi.ingsw.constant.enumeration.LeaderStatus;
import it.polimi.ingsw.constant.enumeration.PopesFavorStates;
import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.model.DevelopmentCard;
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
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

public class BaseMeController extends BaseController{

    public static String className = "base";

    private static final Double[] BASE_PROD_X = {579.0};
    private static final Double[] BASE_PROD_Y = {1156.0};
    private static final Double BASE_PROD_HEIGHT = 286.0;
    private static final Double[] LEAD_BUTTON_X = {2550.0, 2700.0, 2550.0, 2700.0};
    private static final Double[] LEAD_BUTTON_Y = {40.0, 40.0, 900.0, 900.0};
    private static final Double LEAD_BUTTON_HEIGHT = 45.0;
    private static final Double LEAD_BUTTON_WIDTH = 145.0;
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
    private final Label[] labels = new Label[3];
    private final ImageView baseProduction = new ImageView();
    private final ArrayList<ProductionPower> chosen = new ArrayList<>();
    //single player
    private final Image[] tokens;
    private final ImageView retro = new ImageView();
    private final ImageView revealed = new ImageView();


    public BaseMeController() {
        super();
        blackCross = new Image("/images/punchboard/croce.png");
        tokens = new Image[6];
        for(int i=1; i<=6; i++){
            tokens[i-1]=new Image("/images/punchboard/cerchio"+i+".png");
        }
        retro.setImage(new Image("/images/punchboard/retro_cerchi.png"));
    }

    @FXML
    public void initialize(){
        super.initialize();
        //initialize imageViews
        for(int i=0; i< leaderButton.length; i++){
            leaderButton[i] = new Button();
            leaderButton[i].setOnMouseClicked(this::onLeadButton);
            leaderButton[i].setText(i%2==0 ? "active" : "scarta");
            anchorPane.getChildren().add(leaderButton[i]);
        }


        //base production
        baseProduction.setImage(new Image("/images/board/extraProd.png"));
        anchorPane.getChildren().add(baseProduction);
        GUI.fixImages(board, BOARD_HEIGHT, new ImageView[]{baseProduction}, BASE_PROD_X, BASE_PROD_Y, BASE_PROD_HEIGHT);
        baseProduction.fitWidthProperty().bind(board.fitHeightProperty().divide(BOARD_HEIGHT / BASE_PROD_HEIGHT));

        for(int i=0; i<3; i++){
            int finalI = i;
            labels[i]= new Label();
            anchorPane.getChildren().add(labels[i]);
            anchorPane.heightProperty().addListener((observableValue, oldValue, newValue) -> labels[finalI].setLayoutY((Double)newValue - 10.0));
            board.fitHeightProperty().addListener((observableValue, oldValue, newValue) -> labels[finalI].setLayoutX((Double)newValue * BOARD_HEIGHT / DEV_X[finalI*3]));
        }

        //leadercard button
        GUI.fixLabels(board, BOARD_HEIGHT, leaderButton, LEAD_BUTTON_X, LEAD_BUTTON_Y, LEAD_BUTTON_HEIGHT, LEAD_BUTTON_WIDTH);

        //single player
        anchorPane.getChildren().add(retro);
        anchorPane.getChildren().add(revealed);
        GUI.fixImages(board, BOARD_HEIGHT, new ImageView[]{retro, revealed}, TOKEN_X, TOKEN_Y, TOKEN_SIZE);

    }

    @Override
    public void update() {
        super.setPlayer(gui.getModel().getMe());
        super.internalUpdate();
        String text = "";
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
    }

    //onAction functions
    public void goToMarket(ActionEvent actionEvent) {
        gui.activate(MarketController.className);
    }
    public void goToDashboard(ActionEvent actionEvent) {
        gui.activate(DashboardController.className);
    }
    public void goToOther(ActionEvent actionEvent) {
        gui.activate(BaseController.className);
    }
    public void endTurn(ActionEvent actionEvent) {
        gui.sendMove(new MoveEndTurn(gui.getModel().getMyID()));
    }
    public void activeProduction(ActionEvent actionEvent){
        checkButtons(false);

        for(int i=0; i<3; i++){
            if(devCards[i*3].getImage()!=null){
                int j=0;
                while(devCards[i*3 + j].getImage()!=null) j++;
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

        for(int i=0; i<3; i++){
            labels[i].setVisible(true);
        }
    }

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
                    p = new ProductionPower(0, input, new NumberOfResources(), 1, 1);
                }
            }
        }
        if(chosen.contains(p)){
            chosen.remove(p);
            if(index>=0) labels[index/3].setText("");
        }else{
            chosen.add(p);
            if(index>=0) labels[index/3].setText("selected");
        }
        checkConfirm();
    }

    public void exitProduction(ActionEvent actionEvent) {
        checkButtons(true);

        for(int i=0; i<9; i++){
            devCards[i].setOnMouseClicked(null);
        }
        for(int i=0; i<3; i++){
            labels[i].setVisible(false);
        }
        baseProduction.setOnMouseClicked(null);
        for(ImageView image : leaderCards){
            image.setOnMouseClicked(null);
        }
    }

    public void confirmProduction(ActionEvent actionEvent) {
        MoveActiveProduction activeProduction = new MoveActiveProduction(gui.getModel().getMyID());
        activeProduction.setToActive(new ArrayList<>(chosen));
        gui.sendMove(activeProduction);
        chosen.clear();
    }

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
            }
        }
    }



    @Override
    public String getName() {
        return className;
    }

}