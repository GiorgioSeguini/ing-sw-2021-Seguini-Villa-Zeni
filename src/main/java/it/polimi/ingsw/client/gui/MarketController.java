package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.constant.enumeration.MarbleColor;
import it.polimi.ingsw.constant.enumeration.ResourceType;
import it.polimi.ingsw.constant.move.MoveTypeMarket;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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

/**
 * The type Market controller. Show market with marbles and allows player to buy something
 * Activate even when it's not players turn
 */
public class MarketController extends ControllerGuiInterface implements EventHandler<Event>{

    public static final String className = "market";
    private static final int nCol=4;
    private static final int nRow=3;
    private static final int nWhites=2;
    private final ImageView[][] marbleImages=new ImageView[nRow][nCol];
    private final Image[] marblesColor;
    private final Image[] resourcetype;
    private static final String white1= "Hai l'abilità biglia bianca attiva. Le biglie bianche saranno convertite automaticamente nella seguente risorsa.";
    private static final String white2= "Hai l'abilità biglia bianca attiva. Potrai sceglie come convertire le biglie nelle seguenti risorse.";
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
    private final ImageView[] row = new ImageView[3];
    private final ImageView[] col = new ImageView[4];
    private final ImageView imageViewExtMarble= new ImageView();
    private final Image arrowUp;
    private final Image arrowLeft;
    private final ImageView[] whitesRes= new ImageView[2];

    /**
     * @see ControllerGuiInterface#update()
     */
    @Override
    public String getName() {
        return className;
    }
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private GridPane gridPane;
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


    /**
     * Instantiates a new Market controller.
     */
    public MarketController(){
        super();
        marblesColor=new Image[MarbleColor.values().length];
        resourcetype=new Image[ResourceType.values().length];
        for(MarbleColor color: MarbleColor.values()){
            marblesColor[color.ordinal()]= new Image("/images/marbles/"+color+".png");
        }
        for(ResourceType res: ResourceType.values()){
            resourcetype[res.ordinal()]=new Image("/images/punchboard/"+res+".png");
        }
        arrowUp= new Image("/images/punchboard/arrowUp.png");
        arrowLeft=new Image("/images/punchboard/arrowLeft.png");
    }


    /**
     * Initialize the pane and its elements, making it resizable
     */
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

        for (int i=0;i<nWhites; i++){
            whitesRes[i]= new ImageView();
            anchorPane.getChildren().add(whitesRes[i]);
        }

        for (int i=0; i<nRow; i++){
            GUI.fixImages(market, marketH, marbleImages[i],new Double[]{x[0],x[1],x[2],x[3]}, new Double[]{y[i],y[i],y[i],y[i]}, 175.0);
            //GUI.fixImages(market, marketH, marbleImages[i],new Double[4], new Double[4], 175.0);
        }

        GUI.fixImages(market, marketH, col, new Double[]{x[0],x[1],x[2],x[3]}, new Double[]{y[3],y[3],y[3],y[3]},380.0);
        GUI.fixImages(market, marketH, row, new Double[]{x[4],x[4],x[4]}, new Double[]{y[0],y[1],y[2]},180.0);
        GUI.fixImages(market,marketH,new ImageView[]{imageViewExtMarble}, new Double[]{1400.0},new Double[]{320.0},150.0);
        GUI.fixImages(market,marketH, whitesRes, new Double[]{1630.0,1780.0}, new Double[]{2200.0,2200.0},180.0);

    }

    /**
     * @see ControllerGuiInterface#update()
     */
    @Override
    public void update() {
        HideFirstScreen(false);
        for(int i=0; i<nRow; i++){
            for (int j=0; j<nCol; j++){
                MarbleColor color= gui.getModel().getMarketTray().getMarble(i,j);
                marbleImages[i][j].setImage(marblesColor[color.ordinal()]);
                marbleImages[i][j].setVisible(true);
            }
        }

        if(gui.getModel().getMe().getConverter().isWhiteAbilityActive()){
            if(gui.getModel().getMe().getConverter().getToconvert().size()==2){
                ResourceType type= gui.getModel().getMe().getConverter().getToconvert().get(1);
                whitesRes[1].setImage(resourcetype[type.ordinal()]);
            }
            ResourceType type= gui.getModel().getMe().getConverter().getToconvert().get(0);
            whitesRes[0].setImage(resourcetype[type.ordinal()]);
        }

        market.setVisible(true);

        MarbleColor extMarble=gui.getModel().getMarketTray().getExternalMarble();
        imageViewExtMarble.setImage(marblesColor[extMarble.ordinal()]);
        imageViewExtMarble.setVisible(true);

    }

    /**
     * Confirm market Move, after Button is pressed player can effectively buy from the market
     *
     * @param actionEvent the action event
     */
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

    /**
     * Exit from the scene
     * Active BaseMeController
     *
     * @param actionEvent the action event
     */
    public void returnBack(ActionEvent actionEvent) {
        gui.activate(BaseMeController.className);
    }

    /**
     * Send a MoveTypeMArket with the line in the selection buffer
     *
     * @param actionEvent the action event
     */
    public void confirm(ActionEvent actionEvent) {
        MoveTypeMarket move = new MoveTypeMarket(gui.getModel().getMyID());
        move.setIndexToBuy(index);
        gui.sendMove(move);
    }

    /**
     * hide some elemnts
     * @param b true to hide, false to show
     */
    private void HideFirstScreen(boolean b){
        returnback.setDisable(b);
        returnback.setVisible(!b);
        marketMoveConfirm.setDisable(b);
        marketMoveConfirm.setVisible(!b);
        HideSecondScreen(!b);
    }

    /**
     * Hide/show arrows and others elements
     * @param b true to hide, false to show
     */
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
            whitesRes[0].setVisible(!b);
            whitesRes[1].setVisible(!b);
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

    /**
     * Select either a row or a column to buy. Add it to the selection buffer
     * @param event the event
     */
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