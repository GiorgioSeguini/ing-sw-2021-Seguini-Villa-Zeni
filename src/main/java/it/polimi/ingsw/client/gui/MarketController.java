package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.constant.enumeration.MarbleColor;
import it.polimi.ingsw.constant.move.MoveTypeMarket;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.Collection;
/*
----------------------------------------------------------------
                                                                |
                        spaceH                                  |
                                                                |
spaceL       ---------------------------                        |
             |                          |                       |
             |                          |                       |
             |                          | boardH                |
             |                          |                       | marketH
             |                          |                       |
             ---------------------------                        |
                      boardL                                    |
                                                                |
                                                                |
                                                                |
-----------------------------------------------------------------
                    marketL
*/

public class MarketController extends ControllerGuiInterface{

    public static String className = "market";
    private static int nCol=4;
    private static int nRow=3;
    private ImageView[][] marbleImages=new ImageView[nRow][nCol];
    private ArrayList<ImageView> rowcol= new ArrayList<>();
    private Image[] marblesColor;
    private int index;
    private AlertBox box;
    private static final double marketH=2522;
    private static final double marketL=1951;
    private static final double boardH=1000;
    private static final double boardL=750;
    private static final double spaceH=530;
    private static final double spaceL=510;
    private final Double[] x;
    private final Double[] y;

    @Override
    public String getName() {
        return className;
    }


    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button marketMoveConfirm;
    @FXML
    private Button returnback;
    @FXML
    private Button confirm;
    @FXML
    private Label infos;
    @FXML
    private Label chose;
    @FXML
    private ImageView market= new ImageView();

    private ImageView imageViewExtMarble;

    public MarketController(){
        super();

        marblesColor=new Image[MarbleColor.values().length];
        for(MarbleColor color: MarbleColor.values()){
            marblesColor[color.ordinal()]= new Image(getImagePath(color));
        }

        x = new Double[nCol];
        for (int i=0; i<nCol; i++){
            x[i]=spaceL+boardL/(nCol*2)+ i*boardL/nCol;
        }

        y = new Double[nRow];
        for (int i=0; i<nRow; i++){
            x[i]=spaceH+boardL/(nRow*2)+ i*boardL/nRow;
        }
    }


    @FXML
    public void initialize(){
        market.setImage(new Image("/images/punchboard/plancia_portabiglie.png"));
        for (int i=0; i<nCol+nRow; i++){
            rowcol.add(new ImageView());
            anchorPane.getChildren().add(rowcol.get(i));
        }

        for(int i=0; i<nRow; i++){
            for (int j=0; j<nCol; j++){
                marbleImages[i][j]= new ImageView();
                anchorPane.getChildren().add(marbleImages[i][j]);
            }
        }
        for (int i=0; i<nRow; i++){
            GUI.fixImages(market, marketH, marbleImages[i],x, new Double[]{y[i],y[i],y[i],y[i]}, (boardH/nRow)-5 );
        }
    }

    @Override
    public void update() {
        HideFirstScreen(false);
        for(int i=0; i<nRow; i++){
            for (int j=0; j<nCol; j++){
                MarbleColor color= gui.getModel().getMarketTray().gettMarble(i,j);
                marbleImages[i][j].setImage(marblesColor[color.ordinal()]);
                marbleImages[i][j].setVisible(true);
            }
        }

        market.setVisible(true);

        //MarbleColor extMarble=gui.getModel().getMarketTray().getExternalMarble();
        //imageViewExtMarble=marblesColor[extMarble.ordinal()];
    }

    public void MarketMoveConfirm(ActionEvent actionEvent) {
        /*if(new MoveTypeMarket(gui.getModel().getMyID()).canPerform(gui.getModel())){
            box= new AlertBox("Mossa Market", "Stai scegliendo di comprare risorse dal mercato. Se decidi di continuare non potrai tornare indietro");
            Button button= new Button("Ok");
            EventHandler<ActionEvent> event = new
                    EventHandler<ActionEvent>() {
                        public void handle(ActionEvent e)
                        {
                            returnback.setDisable(true);
                            marketMoveConfirm.setDisable(true);
                            returnback.setVisible(false);
                            marketMoveConfirm.setVisible(false);
                            box.closeBox();
                            makeMove();
                        }
                    };
            button.setOnAction(event);
            box.addButton(button);
        }
        else{
            box= new AlertBox("Mossa Market", "Stai scegliendo di comprare risorse dal mercato, ma al momento questa mossa non è disponibile");
        }
        box.display();*/
    }


    public void returnBack(ActionEvent actionEvent) {
        gui.activate(BaseController.className);
    }

    public void confirm(ActionEvent actionEvent) {
        /*MoveTypeMarket move = new MoveTypeMarket(gui.getModel().getMyID());
        move.setIndexToBuy(index);
        gui.sendMove(move);*/
    }

    private String getImagePath(MarbleColor color){
        String path="/images/marbles/"+color+".png";
        return path;
    }

    /*public void selectRowCol(MouseEvent mouseEvent){
        index=rowcol.indexOf((ImageView) mouseEvent.getSource());
        if(index<4){
            chose.setText("\nHai scelto la "+ (index+1)+"colonna.");
        }else {
            chose.setText("\n Hai scelto la "+ (index-3)+ "riga.");
        }
        confirm.setDisable(false);
    }*/

    private void HideFirstScreen(boolean b){
        returnback.setDisable(b);
        returnback.setVisible(!b);
        marketMoveConfirm.setDisable(b);
        marketMoveConfirm.setVisible(!b);
        HideSecondScreen(!b);
    }

    private void HideSecondScreen(boolean b){
        infos.setDisable(b);
        infos.setVisible(!b);
        chose.setDisable(b);
        chose.setVisible(!b);
        chose.setText("");
        confirm.setDisable(b);
        confirm.setVisible(!b);
        for(ImageView image: rowcol){
            image.setDisable(b);
        }

    }


}