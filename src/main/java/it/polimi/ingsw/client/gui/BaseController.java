package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.enumeration.PopesFavorStates;
import it.polimi.ingsw.constant.model.DevelopmentCard;
import it.polimi.ingsw.constant.model.LeaderCard;
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

public class BaseController extends ControllerGuiInterface{

    public static String className = "base";
    private static final double BOARD_HEIGHT = 1717;
    private static final double RES_SIZE = 80;
    private static final double POPE_SIZE = 145;
    private static final double CARD_HEIGHT = 737;
    private static final Double[] RES_X = {289.0, 231.0, 320.0, 187.0, 274.0, 365.0};
    private static final Double[] RES_Y = {763.0, 905.0, 905.0, 1057.0, 1057.0, 1057.0};
    private static final Double[] POPES_X = {607.0, 1186.0, 1884.0};
    private static final Double[] POPES_Y = {250.0, 132.0, 250.0};
    private static final Double[] DEV_X = {935.0, 935.0, 935.0, 1398.0, 1398.0, 1398.0, 1861.0, 1861.0, 1861.0};
    private static final Double[] DEV_Y = {873.0, 933.0, 993.0, 873.0, 933.0, 993.0, 873.0, 933.0, 993.0};
    private static final Double[] BASE_PROD_X = {579.0};
    private static final Double[] BASE_PROD_Y = {1156.0};
    private static final Double BASE_PROD_HEIGHT = 286.0;
    private static final Double[] LEAD_X = {2420.0, 2420.0};
    private static final Double[] LEAD_Y = {100.0, 980.0};
    private static final Double[] LABEL_Y = {45.0, 900.0};
    private static final Double LABEL_SIZE = 45.0 ;
    private static final Double[] LEAD_BUTTON_X = {2480.0, 2600.0, 2480.0, 2600.0};
    private static final Double[] LEAD_BUTTON_Y = {45.0, 45.0, 900.0, 900.0};
    private static final Double LEAD_BUTTON_SIZE = 45.0;
    private static final Double[] FAITH_X = { 95.0, 212.0, 329.0, 329.0, 329.0, 446.0, 563.0, 680.0, 797.0, 914.0, 914.0, 914.0, 1031.0, 1148.0, 1265.0, 1382.0, 1499.0, 1499.0, 1499.0, 1616.0, 1733.0, 1850.0, 1967.0, 2084.0, 2201.0};
    private static final Double[] FAITH_Y = {338.0, 338.0, 338.0, 221.0, 104.0, 104.0, 104.0, 104.0, 104.0, 104.0, 221.0, 338.0,  338.0,  338.0,  338.0,  338.0,  338.0,  221.0,  104.0,  104.0,  104.0,  104.0,  104.0,  104.0,  104.0};
    private static final Double FAITH_HEIGHT = 100.0;

    @FXML
    ImageView board;
    @FXML
    AnchorPane anchorPane;
    @FXML
    Button market;
    @FXML
    Button dashboard;
    @FXML
    Button endTurn;
    @FXML
    private Button production;
    @FXML
    private Button confirm;
    @FXML
    private Button exit;

    private final ImageView[] resources = new ImageView[6];
    private final ImageView[] popes = new ImageView[3];
    private final ImageView[] devCards = new ImageView[9];
    private final ImageView[] leaderCards = new ImageView[2];
    private final ImageView[] faithTrack = new ImageView[25];
    private final Label[] leaderCardsLabels = new Label[2];
    private final Button[] leaderButton = new Button[4];

    private final Image[][] popesImage = new Image[3][2];
    private final Image faith;
    private final Label[] labels = new Label[3];
    private final ImageView baseProduction = new ImageView();
    private final ArrayList<ProductionPower> chosen = new ArrayList<>();

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
    }

    @FXML
    public void initialize(){
        //initialize imageViews
        imageArrayInitializer(anchorPane, resources);
        imageArrayInitializer(anchorPane, popes);
        imageArrayInitializer(anchorPane, devCards);
        imageArrayInitializer(anchorPane, leaderCards);
        imageArrayInitializer(anchorPane, faithTrack);
        for(int i=0; i< leaderCardsLabels.length; i++){
            leaderCardsLabels[i] = new Label();
            anchorPane.getChildren().add(leaderCardsLabels[i]);
        }
        for(int i=0; i< leaderButton.length; i++){
            leaderButton[i] = new Button();
            leaderButton[i].setOnMouseClicked(this::onLeadButton);
            leaderButton[i].setText(i%2==0 ? "active" : "scarta");
            anchorPane.getChildren().add(leaderButton[i]);
        }

        board.fitHeightProperty().bind(anchorPane.heightProperty().divide(1.1));
        //board.fitWidthProperty().bind(anchorPane.widthProperty().divide(1.4));
        /*board.layoutXProperty().bind(anchorPane.heightProperty().divide(10.0));
        board.layoutYProperty().bind(anchorPane.heightProperty().divide(10.0));*/

        /*market.layoutXProperty().bind(board.layoutXProperty());
        dashboard.layoutXProperty().bind(market.layoutXProperty().add(market.widthProperty()).add(10));*/

        GUI.fixImages(board, BOARD_HEIGHT, resources, RES_X, RES_Y, RES_SIZE);
        GUI.fixImages(board, BOARD_HEIGHT, popes, POPES_X, POPES_Y, POPE_SIZE);
        GUI.fixImages(board, BOARD_HEIGHT, devCards, DEV_X, DEV_Y, CARD_HEIGHT);
        GUI.fixImages(board, BOARD_HEIGHT, faithTrack, FAITH_X, FAITH_Y, FAITH_HEIGHT);


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

        //leadercard
        GUI.fixImages(board, BOARD_HEIGHT, leaderCards, LEAD_X, LEAD_Y, CARD_HEIGHT);
        GUI.fixLabels(board, BOARD_HEIGHT, leaderCardsLabels, LEAD_X, LABEL_Y, LABEL_SIZE);
        GUI.fixLabels(board, BOARD_HEIGHT, leaderButton, LEAD_BUTTON_X, LEAD_BUTTON_Y, LEAD_BUTTON_SIZE);
        chosen.clear();
    }

    @Override
    public void update() {
        gui.printDepots(resources, gui.getModel().getMe().getDepots());

        for(int i=0; i<3; i++) {
            if (gui.getModel().getMe().getFaithTrack().getPopesFavor(i) == PopesFavorStates.FaceDown) {
                popes[i].setImage(popesImage[i][0]);
            } else if (gui.getModel().getMe().getFaithTrack().getPopesFavor(i) == PopesFavorStates.FaceUp) {
                popes[i].setImage(popesImage[i][1]);
            } else {
                popes[i].setVisible(false);
            }
        }

        for(int i=0; i<3; i++){
            int j=0;
            for(DevelopmentCard devCard : gui.getModel().getMe().getPersonalBoard().getPos(i)){
                devCards[i*3 +j].setImage(new Image("images/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-"+(devCard.getId()+1)+"-1.png"));
                j++;
            }
        }

        for(int i=0; i< leaderCards.length; i++){
            if(i<gui.getModel().getMe().getPersonalBoard().getLeaderCards().size()){
                int id = gui.getModel().getMe().getPersonalBoard().getLeaderCards().get(i).getId() +49;
                leaderCards[i].setImage(new Image("/images/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-"+ id +"-1.png"));
                leaderCardsLabels[i].setText( gui.getModel().getMe().getPersonalBoard().getLeaderCards().get(i).getStatus().toString());
            }else{
                leaderCards[i].setImage(null);
                leaderCardsLabels[i].setVisible(false);
            }
        }

        for(ImageView cell: faithTrack){
            cell.setImage(null);
        }
        faithTrack[gui.getModel().getMe().getFaithTrack().getFaithPoints()].setImage(faith);
        checkButtons(true);
    }

    //onAction functions
    public void goToMarket(ActionEvent actionEvent) {
        gui.activate(MarketController.className);
    }
    public void goToDashboard(ActionEvent actionEvent) {
        gui.activate(DashboardController.className);
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
    }

    public void confirmProduction(ActionEvent actionEvent) {
        MoveActiveProduction activeProduction = new MoveActiveProduction(gui.getModel().getMyID());
        activeProduction.setToActive(chosen);
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
                    case Dead -> {
                        leaderButton[i * 2].setDisable(true);
                        leaderButton[i * 2 + 1].setDisable(true);
                    }
                    case onHand -> {
                        leaderButton[i * 2].setDisable(false);
                        leaderButton[i * 2 + 1].setDisable(false);
                    }
                    case Played -> {
                        leaderButton[i * 2].setDisable(true);
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
    }

    private void imageArrayInitializer(AnchorPane pane, ImageView[] imageViews){
        for(int i=0; i<imageViews.length; i++){
            imageViews[i]= new ImageView();
            anchorPane.getChildren().add(imageViews[i]);
        }
    }

    @Override
    public String getName() {
        return className;
    }
}
