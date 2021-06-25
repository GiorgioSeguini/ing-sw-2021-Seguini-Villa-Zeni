package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.constant.enumeration.MarbleColor;
import it.polimi.ingsw.constant.move.MoveTypeMarket;
import javafx.event.ActionEvent;
import javafx.event.Event;
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

public class MarketController extends ControllerGuiInterface implements EventHandler<Event>{

    public static String className = "market";
    private static int nCol=4;
    private static int nRow=3;
    private ImageView[][] marbleImages=new ImageView[nRow][nCol];
    private Image[] marblesColor;
    private static final String white1= "Hai l'abilità biglia bianca attiva. Le biglie bianche saranno convertite automaticamente nel seguente colore.";
    private static final String white2= "Hai l'abilità biglia bianca attiva. Potrai sceglie come convertire le biglie nei seguenti colori.";
    private int index;
    private boolean checkAlert=true;
    private static final double marketH=2522;
    private static final double marketL=1951;
    private static final double boardH=1000;
    private static final double boardL=1000;
    private static final double spaceH=530;
    private static final double spaceL=510;
    private final Double[] x = {580.0, 800.0, 1020.0,1240.0,1600.0};
    private final Double[] y = {570.0,790.0,1010.0,1450.0};
    private ImageView[] row = new ImageView[3];
    private ImageView[] col = new ImageView[4];
    private ImageView imageViewExtMarble= new ImageView();
    private Image arrowUp;
    private Image arrowLeft;

    @Override
    public String getName() {
        return className;
    }
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private AnchorPane colorPane1;
    @FXML
    private AnchorPane colorPane2;
    @FXML
    private GridPane gridPane;
    @FXML
    private GridPane colorGridPane;
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
    private ImageView market;
    @FXML
    private Label whites;
    @FXML
    private ImageView color1;
    @FXML
    private ImageView color2;



    public MarketController(){
        super();
        marblesColor=new Image[MarbleColor.values().length];
        for(MarbleColor color: MarbleColor.values()){
            marblesColor[color.ordinal()]= new Image(getImagePath(color));
        }
        arrowUp= new Image("/images/punchboard/arrowUp.png");
        arrowLeft=new Image("/images/punchboard/arrowLeft.png");
    }


    @FXML
    public void initialize(){
        market.fitHeightProperty().bind(gridPane.heightProperty().divide(1.7));
        anchorPane.getChildren().add(imageViewExtMarble);

        for (int i=0; i<nRow; i++){
            row[i]=new ImageView(arrowLeft);
            anchorPane.getChildren().add(row[i]);
            row[i].setOnMouseClicked(this);
        }
        for (int i=0; i<nCol; i++){
            col[i]=new ImageView(arrowUp);
            anchorPane.getChildren().add(col[i]);
            col[i].setOnMouseClicked(this);
        }


        for(int i=0; i<nRow; i++){
            for (int j=0; j<nCol; j++){
                marbleImages[i][j]= new ImageView();
                anchorPane.getChildren().add(marbleImages[i][j]);
            }
        }
        for (int i=0; i<nRow; i++){
            GUI.fixImages(market, marketH, marbleImages[i],new Double[]{x[0],x[1],x[2],x[3]}, new Double[]{y[i],y[i],y[i],y[i]}, 175.0);
            //GUI.fixImages(market, marketH, marbleImages[i],new Double[4], new Double[4], 175.0);
        }

        GUI.fixImages(market, marketH, col, new Double[]{x[0],x[1],x[2],x[3]}, new Double[]{y[3],y[3],y[3],y[3]},380.0);
        GUI.fixImages(market, marketH, row, new Double[]{x[4],x[4],x[4]}, new Double[]{y[0],y[1],y[2]},180.0);
        GUI.fixImages(market,marketH,new ImageView[]{imageViewExtMarble}, new Double[]{1400.0},new Double[]{320.0},150.0);

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

        if(gui.getModel().getMe().getConverter().isWhiteAbilityActive()){
            if(gui.getModel().getMe().getConverter().getToconvert().size()==2){
                MarbleColor color= gui.getModel().getMe().getConverter().getToconvert().get(1).getColor();
                color2.setImage(marblesColor[color.ordinal()]);
            }
            MarbleColor color= gui.getModel().getMe().getConverter().getToconvert().get(0).getColor();
            color1.setImage(marblesColor[color.ordinal()]);
            color1.fitHeightProperty().bind(colorPane1.heightProperty().divide(5));
            color2.fitHeightProperty().bind(colorPane2.heightProperty().divide(5));
        }

        market.setVisible(true);

        MarbleColor extMarble=gui.getModel().getMarketTray().getExternalMarble();
        imageViewExtMarble.setImage(marblesColor[extMarble.ordinal()]);
        imageViewExtMarble.setVisible(true);

    }

    public void MarketMoveConfirm(ActionEvent actionEvent) {
        if(new MoveTypeMarket(gui.getModel().getMyID()).canPerform(gui.getModel())){
            if (checkAlert) {
                checkAlert=false;
                AlertBox box = new AlertBox("Mossa Market", "Stai scegliendo di comprare risorse dal mercato. Se decidi di continuare non potrai tornare indietro");
                Button button = new Button("Ok");
                EventHandler<ActionEvent> event = new
                        EventHandler<ActionEvent>() {
                            public void handle(ActionEvent e) {
                                HideFirstScreen(true);
                                box.closeBox();
                            }
                        };
                button.setOnAction(event);
                box.addButton(button);
                box.display();
            }
            else{
                HideFirstScreen(true);
            }
        }
        else{
            AlertBox box= new AlertBox("Mossa Market", "Stai scegliendo di comprare risorse dal mercato, ma al momento questa mossa non è disponibile");
            box.display();
        }
    }

    public void returnBack(ActionEvent actionEvent) {
        gui.activate(BaseMeController.className);
    }

    public void confirm(ActionEvent actionEvent) {
        MoveTypeMarket move = new MoveTypeMarket(gui.getModel().getMyID());
        move.setIndexToBuy(index);
        gui.sendMove(move);
    }

    private String getImagePath(MarbleColor color){
        String path="/images/marbles/"+color+".png";
        return path;
    }

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
        confirm.setDisable(true);
        confirm.setVisible(!b);
        whites.setDisable(b);
        whites.setVisible(!b);
        if(gui.getModel().getMe().getConverter().isWhiteAbilityActive()){
            if(gui.getModel().getMe().getConverter().getToconvert().size()==1){
                whites.setText(white1);
            }
            else{
                whites.setText(white2);
            }
            color1.setVisible(!b);
            color2.setVisible(!b);
        }else{
            whites.setText("");
        }

        for(ImageView image: row){
            image.setDisable(b);
            image.setVisible(!b);
        }
        for(ImageView image: col){
            image.setDisable(b);
            image.setVisible(!b);
        }

    }


    @Override
    public void handle(Event event) {
        ImageView selected=((ImageView) event.getSource());

        boolean isRow=false;
        for(int i=0; i<nRow && isRow==false; i++){
            if(selected.equals(row[i])){
                index=i;
                isRow=true;
            }
        }
        for(int i=0; i<nCol && isRow==false; i++){
            if(selected.equals(col[i])){
                index=i;
            }
        }

        if(isRow){
            chose.setText("\nHai scelto la "+ (index+1)+"riga.");
            index+=4;
        }else {
            chose.setText("\n Hai scelto la "+ (index+1)+ "colonna.");
        }
        confirm.setDisable(false);
    }
}