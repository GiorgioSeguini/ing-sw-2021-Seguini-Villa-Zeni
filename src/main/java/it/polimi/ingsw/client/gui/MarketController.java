package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.cli.CliMoveTypeMarket;
import it.polimi.ingsw.constant.enumeration.MarbleColor;
import it.polimi.ingsw.constant.move.MoveChoseLeaderCards;
import it.polimi.ingsw.constant.move.MoveType;
import it.polimi.ingsw.constant.move.MoveTypeMarket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class MarketController extends ControllerGuiInterface{

    public static String className = "market";
    private static int nCol=4;
    private static int nRow=3;
    private ImageView[][] marbleImages= new ImageView[nRow][nCol];
    private int index;

    @Override
    public String getName() {
        return className;
    }

    @FXML
    private ImageView imageView00;
    @FXML
    private ImageView imageView01;
    @FXML
    private ImageView imageView02;
    @FXML
    private ImageView imageView03;
    @FXML
    private ImageView imageView10;
    @FXML
    private ImageView imageView11;
    @FXML
    private ImageView imageView12;
    @FXML
    private ImageView imageView13;
    @FXML
    private ImageView imageView20;
    @FXML
    private ImageView imageView21;
    @FXML
    private ImageView imageView22;
    @FXML
    private ImageView imageView23;
    @FXML
    private ImageView imageViewExtMarble;

    @FXML
    private Button marketMove;

    @FXML
    private Button returnback;

    @FXML
    private GridPane grid;

    @FXML
    public void initialize(){
        returnback.setDisable(false);
        marketMove.setDisable(false);
        marbleImages[0][0]= imageView00;
        marbleImages[0][1]= imageView01;
        marbleImages[0][2]= imageView02;
        marbleImages[0][3]= imageView03;
        marbleImages[1][0]= imageView10;
        marbleImages[1][1]= imageView11;
        marbleImages[1][2]= imageView12;
        marbleImages[1][3]= imageView13;
        marbleImages[2][0]= imageView20;
        marbleImages[2][1]= imageView21;
        marbleImages[2][2]= imageView22;
        marbleImages[2][3]= imageView23;
    }

    @Override
    public void update() {
        for(int i=0; i<nRow; i++){
            for (int j=0; j<nCol; j++){
                MarbleColor color= gui.getModel().getMarketTray().gettMarble(i,j);
                marbleImages[i][j].setImage(new Image("/images/marbles/"+color+".png"));
            }
        }
        MarbleColor extMarble=gui.getModel().getMarketTray().getExternalMarble();
        //imageViewExtMarble.setImage(new Image("/image/marbles/"+extMarble+".png")); TODO perchè cazzo non funzioni?

    }

    public void MarketMove(ActionEvent actionEvent) {
        System.out.println("Forse ho capito come funziona");

    }


    public void returnBack(ActionEvent actionEvent) {
        gui.activate(BaseController.className);
    }

    public void confirm(ActionEvent actionEvent) {
        MoveTypeMarket move = new MoveTypeMarket(gui.getModel().getMyID());
        move.setIndexToBuy(index);// TODO: 5/29/21 raccogliere index ancora non so bene come, pensavo a dei bottoni sulle frecce 
        gui.sendMove(move);
    }
}
