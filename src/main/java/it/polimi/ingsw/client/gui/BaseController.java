package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.constant.enumeration.PlayerStatus;
import it.polimi.ingsw.constant.enumeration.PopesFavorStates;
import it.polimi.ingsw.constant.model.DevelopmentCard;
import it.polimi.ingsw.constant.model.ProductionPower;
import it.polimi.ingsw.constant.move.MoveActiveProduction;
import it.polimi.ingsw.constant.move.MoveEndTurn;
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
    private static final Double[] LABEL_Y = { 50.0, 930.0};

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
    private final Label[] leaderCardsLabels = new Label[2];

    private final Image[][] popesImage = new Image[3][2];
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
    }

    @FXML
    public void initialize(){
        //initialize images
        for(int i=0; i< resources.length; i++){
            resources[i] = new ImageView();
            anchorPane.getChildren().add(resources[i]);
        }
        for(int i=0; i< popes.length; i++){
            popes[i] = new ImageView();
            anchorPane.getChildren().add(popes[i]);
        }
        for(int i=0; i< devCards.length; i++){
            devCards[i] = new ImageView();
            anchorPane.getChildren().add(devCards[i]);
        }
        for(int i=0; i< leaderCards.length; i++){
            leaderCards[i] = new ImageView();
            anchorPane.getChildren().add(leaderCards[i]);
        }
        for(int i=0; i< leaderCardsLabels.length; i++){
            leaderCardsLabels[i] = new Label();
            anchorPane.getChildren().add(leaderCardsLabels[i]);
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
            int id = gui.getModel().getMe().getPersonalBoard().getLeaderCards().get(i).getId() +49;
            leaderCards[i].setImage(new Image("/images/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-"+ id +"-1.png"));
            leaderCardsLabels[i].setText( gui.getModel().getMe().getPersonalBoard().getLeaderCards().get(i).getStatus().toString());
        }
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

    @Override
    public String getName() {
        return className;
    }
}
