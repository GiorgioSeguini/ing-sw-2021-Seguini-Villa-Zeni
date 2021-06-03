package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.constant.enumeration.MarbleColor;
import it.polimi.ingsw.constant.enumeration.PopesFavorStates;
import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.model.DevelopmentCard;
import it.polimi.ingsw.constant.move.MoveEndTurn;
import it.polimi.ingsw.constant.move.MoveType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class BaseController extends ControllerGuiInterface{

    public static String className = "base";
    private static final double BOARD_HEIGHT = 1717;
    private static final double RES_SIZE = 80;
    private static final double POPE_SIZE = 145;
    private static final double CARD_HEIGHT = 737;
    private static final double CARD_OFFSET = 60;
    private static final Double[] RES_X = {289.0, 231.0, 320.0, 187.0, 274.0, 365.0};
    private static final Double[] RES_Y = {763.0, 905.0, 905.0, 1057.0, 1057.0, 1057.0};
    private static final Double[] POPES_X = {607.0, 1186.0, 1814.0};
    private static final Double[] POPES_Y = {250.0, 132.0, 250.0};
    private static final Double[] DEV_X = {935.0, 935.0, 935.0, 1398.0, 1398.0, 1398.0, 1861.0, 1861.0, 1861.0};
    private static final Double[] DEV_Y = {873.0, 933.0, 993.0, 873.0, 933.0, 993.0, 873.0, 933.0, 993.0};

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

    private final ImageView[] resources = new ImageView[6];
    private final ImageView[] popes = new ImageView[3];
    private final ImageView[] devCards = new ImageView[9];

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

        board.fitHeightProperty().bind(anchorPane.heightProperty().divide(1.1));
        /*board.layoutXProperty().bind(anchorPane.heightProperty().divide(10.0));
        board.layoutYProperty().bind(anchorPane.heightProperty().divide(10.0));*/

        /*market.layoutXProperty().bind(board.layoutXProperty());
        dashboard.layoutXProperty().bind(market.layoutXProperty().add(market.widthProperty()).add(10));*/

        GUI.fixImages(board, BOARD_HEIGHT, resources, RES_X, RES_Y, RES_SIZE);
        GUI.fixImages(board, BOARD_HEIGHT, popes, POPES_X, POPES_Y, POPE_SIZE);
        GUI.fixImages(board, BOARD_HEIGHT, devCards, DEV_X, DEV_Y, CARD_HEIGHT);
    }

    @Override
    public void update() {
        int  n0 = gui.getModel().getMe().getDepots().getWareHouseDepots().getShelfs().get(0).getUsed();
        if(n0>0){
            ResourceType type = gui.getModel().getMe().getDepots().getWareHouseDepots().getShelfs().get(0).getResType();
            resources[0].setImage(new Image("/images/punchboard/" + type + ".png"));
            resources[0].setVisible(true);
        }else{
            resources[0].setVisible(false);
        }

        int n1 = gui.getModel().getMe().getDepots().getWareHouseDepots().getShelfs().get(1).getUsed();
        if(n1>0){
            ResourceType type = gui.getModel().getMe().getDepots().getWareHouseDepots().getShelfs().get(1).getResType();
            resources[1].setImage(new Image("/images/punchboard/" + type + ".png"));
            resources[1].setVisible(true);
            if(n1>1){
                resources[2].setImage(new Image("/images/punchboard/" + type + ".png"));
                resources[2].setVisible(true);
            }else{
                resources[2].setVisible(false);
            }
        }else{
            resources[1].setVisible(false);
            resources[2].setVisible(false);
        }

        int n2 = gui.getModel().getMe().getDepots().getWareHouseDepots().getShelfs().get(2).getUsed();
        ResourceType type2 = gui.getModel().getMe().getDepots().getWareHouseDepots().getShelfs().get(2).getResType();
        if(n2>0) {
            resources[3].setImage(new Image("/images/punchboard/" + type2 + ".png"));
            resources[3].setVisible(true);
        }else {
            resources[3].setVisible(false);
        }
        if(n2>1){
            resources[4].setImage(new Image("/images/punchboard/" + type2 + ".png"));
            resources[4].setVisible(true);
        }else{
            resources[4].setVisible(false);
        }
        if(n2>2){
            resources[5].setImage(new Image("/images/punchboard/" + type2 + ".png"));
            resources[5].setVisible(true);
        }else{
            resources[5].setVisible(false);
        }

        if(gui.getModel().getMe().getFaithTrack().getPopesFavor(0).equals(PopesFavorStates.Discarded)){
            popes[0].setImage(new Image("images/punchboard/miss_2"));
        }
        if(gui.getModel().getMe().getFaithTrack().getPopesFavor(1).equals(PopesFavorStates.Discarded)){
            popes[1].setImage(new Image("images/punchboard/miss_3"));
        }
        if(gui.getModel().getMe().getFaithTrack().getPopesFavor(2).equals(PopesFavorStates.Discarded)){
            popes[2].setImage(new Image("images/punchboard/miss_4"));
        }
        //TODO mancano immagini del retro e dei conquistati

        for(int i=0; i<3; i++){
            int j=0;
            for(DevelopmentCard devCard : gui.getModel().getMe().getPersonalBoard().getPos(i)){
                devCards[i*3 +j].setImage(new Image("images/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-"+devCard.getId()+"-1.png"));
                j++;
            }
        }
        checkEndTurn();
        market.setVisible(true);
        dashboard.setVisible(true);
        endTurn.setVisible(true);
    }

    public void goToMarket(ActionEvent actionEvent) {
        gui.activate(MarketController.className);
    }
    public void goToDashboard(ActionEvent actionEvent) {
        gui.activate(DashboardController.className);
    }
    public void endTurn(ActionEvent actionEvent) {
        gui.sendMove(new MoveEndTurn(gui.getModel().getMyID()));
    }

    private void checkEndTurn(){
        MoveType move = new MoveEndTurn(gui.getModel().getMyID());
        endTurn.setDisable(!move.canPerform(gui.getModel()));
    }

    @Override
    public String getName() {
        return className;
    }
}
