package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.constant.enumeration.MarbleColor;
import it.polimi.ingsw.constant.enumeration.PopesFavorStates;
import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.model.DevelopmentCard;
import it.polimi.ingsw.constant.move.MoveEndTurn;
import it.polimi.ingsw.constant.move.MoveType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    @FXML
    ImageView board;
    @FXML
    AnchorPane anchorPane;
    @FXML
    ImageView marble0;
    @FXML
    ImageView marble1;
    @FXML
    ImageView marble2;
    @FXML
    ImageView marble3;
    @FXML
    ImageView marble4;
    @FXML
    ImageView marble5;
    @FXML
    ImageView pope0;
    @FXML
    ImageView pope1;
    @FXML
    ImageView pope2;
    @FXML
    ImageView devCard00;
    @FXML
    ImageView devCard01;
    @FXML
    ImageView devCard02;
    @FXML
    ImageView devCard10;
    @FXML
    ImageView devCard11;
    @FXML
    ImageView devCard12;
    @FXML
    ImageView devCard20;
    @FXML
    ImageView devCard21;
    @FXML
    ImageView devCard22;
    @FXML
    Button market;
    @FXML
    Button dashboard;
    @FXML
    Button endTurn;

    private final ImageView[][] devCards = new ImageView[3][3];
    @FXML
    public void initialize(){
        devCards[0][0]=devCard00;
        devCards[0][1]=devCard01;
        devCards[0][2]=devCard02;
        devCards[1][0]=devCard10;
        devCards[1][1]=devCard11;
        devCards[1][2]=devCard12;
        devCards[2][0]=devCard20;
        devCards[2][1]=devCard21;
        devCards[2][2]=devCard22;

        board.fitHeightProperty().bind(anchorPane.heightProperty().divide(1.1));
        /*board.layoutXProperty().bind(anchorPane.heightProperty().divide(10.0));
        board.layoutYProperty().bind(anchorPane.heightProperty().divide(10.0));*/

        /*market.layoutXProperty().bind(board.layoutXProperty());
        dashboard.layoutXProperty().bind(market.layoutXProperty().add(market.widthProperty()).add(10));*/

        marble0.layoutXProperty().bind(board.layoutXProperty().add(board.fitHeightProperty().divide(BOARD_HEIGHT/289.0)));
        marble0.layoutYProperty().bind(board.layoutYProperty().add(board.fitHeightProperty().divide(BOARD_HEIGHT/763.0)));
        marble0.fitHeightProperty().bind(board.fitHeightProperty().divide(BOARD_HEIGHT/RES_SIZE));

        marble1.layoutXProperty().bind(board.layoutXProperty().add(board.fitHeightProperty().divide(BOARD_HEIGHT/231.0)));
        marble1.layoutYProperty().bind(board.layoutYProperty().add(board.fitHeightProperty().divide(BOARD_HEIGHT/905.0)));
        marble1.fitHeightProperty().bind(board.fitHeightProperty().divide(BOARD_HEIGHT/RES_SIZE));

        marble2.layoutXProperty().bind(board.layoutXProperty().add(board.fitHeightProperty().divide(BOARD_HEIGHT/320.0)));
        marble2.layoutYProperty().bind(board.layoutYProperty().add(board.fitHeightProperty().divide(BOARD_HEIGHT/905.0)));
        marble2.fitHeightProperty().bind(board.fitHeightProperty().divide(BOARD_HEIGHT/RES_SIZE));

        marble3.layoutXProperty().bind(board.layoutXProperty().add(board.fitHeightProperty().divide(BOARD_HEIGHT/187.0)));
        marble3.layoutYProperty().bind(board.layoutYProperty().add(board.fitHeightProperty().divide(BOARD_HEIGHT/1057.0)));
        marble3.fitHeightProperty().bind(board.fitHeightProperty().divide(BOARD_HEIGHT/RES_SIZE));

        marble4.layoutXProperty().bind(board.layoutXProperty().add(board.fitHeightProperty().divide(BOARD_HEIGHT/274.0)));
        marble4.layoutYProperty().bind(board.layoutYProperty().add(board.fitHeightProperty().divide(BOARD_HEIGHT/1057)));
        marble4.fitHeightProperty().bind(board.fitHeightProperty().divide(BOARD_HEIGHT/RES_SIZE));

        marble5.layoutXProperty().bind(board.layoutXProperty().add(board.fitHeightProperty().divide(BOARD_HEIGHT/365.0)));
        marble5.layoutYProperty().bind(board.layoutYProperty().add(board.fitHeightProperty().divide(BOARD_HEIGHT/1057.0)));
        marble5.fitHeightProperty().bind(board.fitHeightProperty().divide(BOARD_HEIGHT/RES_SIZE));

        //POPES
        pope0.layoutXProperty().bind(board.layoutXProperty().add(board.fitHeightProperty().divide(BOARD_HEIGHT/607.0)));
        pope0.layoutYProperty().bind(board.layoutYProperty().add(board.fitHeightProperty().divide(BOARD_HEIGHT/250.0)));
        pope0.fitHeightProperty().bind(board.fitHeightProperty().divide(BOARD_HEIGHT/POPE_SIZE));

        pope1.layoutXProperty().bind(board.layoutXProperty().add(board.fitHeightProperty().divide(BOARD_HEIGHT/1186.0)));
        pope1.layoutYProperty().bind(board.layoutYProperty().add(board.fitHeightProperty().divide(BOARD_HEIGHT/132.0)));
        pope1.fitHeightProperty().bind(board.fitHeightProperty().divide(BOARD_HEIGHT/POPE_SIZE));

        pope2.layoutXProperty().bind(board.layoutXProperty().add(board.fitHeightProperty().divide(BOARD_HEIGHT/1184.0)));
        pope2.layoutYProperty().bind(board.layoutYProperty().add(board.fitHeightProperty().divide(BOARD_HEIGHT/250.0)));
        pope2.fitHeightProperty().bind(board.fitHeightProperty().divide(BOARD_HEIGHT/POPE_SIZE));

        for(int j=0; j<3; j++){
            devCards[0][j].layoutXProperty().bind(board.layoutXProperty().add(board.fitHeightProperty().divide(BOARD_HEIGHT/935.0)));
            devCards[0][j].fitHeightProperty().bind(board.fitHeightProperty().divide(BOARD_HEIGHT/CARD_HEIGHT));
        }
        for(int j=0; j<3; j++){
            devCards[1][j].layoutXProperty().bind(board.layoutXProperty().add(board.fitHeightProperty().divide(BOARD_HEIGHT/1398.0)));
            devCards[1][j].fitHeightProperty().bind(board.fitHeightProperty().divide(BOARD_HEIGHT/CARD_HEIGHT));
        }
        for(int j=0; j<3; j++){
            devCards[2][j].layoutXProperty().bind(board.layoutXProperty().add(board.fitHeightProperty().divide(BOARD_HEIGHT/1861.0)));
            devCards[2][j].fitHeightProperty().bind(board.fitHeightProperty().divide(BOARD_HEIGHT/CARD_HEIGHT));
        }

        for(int i=0; i<3; i++)
            devCards[i][0].layoutYProperty().bind(board.layoutYProperty().add(board.fitHeightProperty().divide(BOARD_HEIGHT/873.0)));


        for(int i=0; i<3; i++){
            for(int j=1; j<3; j++){
                devCards[i][j].layoutYProperty().bind(devCards[i][j-1].layoutYProperty().add(CARD_OFFSET));
            }
        }
    }

    @Override
    public void update() {
        int  n0 = gui.getModel().getMe().getDepots().getWareHouseDepots().getShelfs().get(0).getUsed();
        if(n0>0){
            ResourceType type = gui.getModel().getMe().getDepots().getWareHouseDepots().getShelfs().get(0).getResType();
            marble0.setImage(new Image("/images/punchboard/" + type + ".png"));
            marble0.setVisible(true);
        }else{
            marble0.setVisible(false);
        }

        int n1 = gui.getModel().getMe().getDepots().getWareHouseDepots().getShelfs().get(1).getUsed();
        if(n1>0){
            ResourceType type = gui.getModel().getMe().getDepots().getWareHouseDepots().getShelfs().get(1).getResType();
            marble1.setImage(new Image("/images/punchboard/" + type + ".png"));
            marble1.setVisible(true);
            if(n1>1){
                marble2.setImage(new Image("/images/punchboard/" + type + ".png"));
                marble2.setVisible(true);
            }else{
                marble2.setVisible(false);
            }
        }else{
            marble1.setVisible(false);
            marble2.setVisible(false);
        }

        int n2 = gui.getModel().getMe().getDepots().getWareHouseDepots().getShelfs().get(2).getUsed();
        ResourceType type2 = gui.getModel().getMe().getDepots().getWareHouseDepots().getShelfs().get(2).getResType();
        if(n2>0) {
            marble3.setImage(new Image("/images/punchboard/" + type2 + ".png"));
            marble3.setVisible(true);
        }else {
            marble3.setVisible(false);
        }
        if(n2>1){
            marble4.setImage(new Image("/images/punchboard/" + type2 + ".png"));
            marble4.setVisible(true);
        }else{
            marble4.setVisible(false);
        }
        if(n2>2){
            marble5.setImage(new Image("/images/punchboard/" + type2 + ".png"));
            marble5.setVisible(true);
        }else{
            marble5.setVisible(false);
        }

        if(gui.getModel().getMe().getFaithTrack().getPopesFavor(0).equals(PopesFavorStates.Discarded)){
            pope0.setImage(new Image("images/punchboard/miss_2"));
        }
        if(gui.getModel().getMe().getFaithTrack().getPopesFavor(1).equals(PopesFavorStates.Discarded)){
            pope1.setImage(new Image("images/punchboard/miss_3"));
        }
        if(gui.getModel().getMe().getFaithTrack().getPopesFavor(2).equals(PopesFavorStates.Discarded)){
            pope2.setImage(new Image("images/punchboard/miss_4"));
        }
        //TODO mancano immagini del retro e dei conquistati

        for(int i=0; i<3; i++){
            int j=0;
            for(DevelopmentCard devCard : gui.getModel().getMe().getPersonalBoard().getPos(i)){
                devCards[i][j].setImage(new Image("images/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-"+devCard.getId()+"-1.png"));
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
