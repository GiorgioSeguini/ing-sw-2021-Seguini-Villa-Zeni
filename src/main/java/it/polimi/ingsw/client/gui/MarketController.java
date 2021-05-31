package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.constant.enumeration.MarbleColor;
import it.polimi.ingsw.constant.move.MoveTypeMarket;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.Collection;

public class MarketController extends ControllerGuiInterface{

    public static String className = "market";
    private static int nCol=4;
    private static int nRow=3;
    private ImageView[][] marbleImages= new ImageView[nRow][nCol];
    private ArrayList<ImageView> rowcol= new ArrayList<>();
    private int index;
    private AlertBox box;

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
    private ImageView row1;
    @FXML
    private ImageView row2;
    @FXML
    private ImageView row3;
    @FXML
    private ImageView col1;
    @FXML
    private ImageView col2;
    @FXML
    private ImageView col3;
    @FXML
    private ImageView col4;


    @FXML
    private Button marketMoveConfirm;
    @FXML
    private Button returnback;
    @FXML
    private Button confirm;

    @FXML
    private GridPane grid;

    @FXML
    private TextFlow infos;

    @FXML
    public void initialize(){
        returnback.setDisable(false);
        marketMoveConfirm.setDisable(false);
        returnback.setVisible(true);
        marketMoveConfirm.setVisible(true);
        infos.setDisable(true);
        infos.setVisible(false);
        confirm.setVisible(false);
        confirm.setDisable(true);
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
        rowcol.add(col1);
        rowcol.add(col2);
        rowcol.add(col3);
        rowcol.add(col4);
        rowcol.add(row1);
        rowcol.add(row2);
        rowcol.add(row3);

        for(ImageView image: rowcol){
            image.setDisable(true);
        }
    }

    @Override
    public void update() {
        for(int i=0; i<nRow; i++){
            for (int j=0; j<nCol; j++){
                MarbleColor color= gui.getModel().getMarketTray().gettMarble(i,j);
                marbleImages[i][j].setImage(new Image(getImagePath(color)));
            }
        }

        MarbleColor extMarble=gui.getModel().getMarketTray().getExternalMarble();
        imageViewExtMarble.setImage(new Image(getImagePath(extMarble)));

    }

    public void MarketMoveConfirm(ActionEvent actionEvent) {
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
        box.display();
    }


    public void returnBack(ActionEvent actionEvent) {
        gui.activate(BaseController.className);
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

    private void print(Text text){
        infos.getChildren().addAll(text);
    }

    private void makeMove() {
        infos.setDisable(false);
        infos.setVisible(true);
        confirm.setVisible(true);
        for(ImageView image: rowcol){
            image.setDisable(false);
        }
        print(new Text("Hai scelto di comprare al mercato!\n\nClicca su una delle frecce del mercato per fare la tua scelta\n"));
    }

    public void selectRowCol(MouseEvent mouseEvent){
        index=rowcol.indexOf((ImageView) mouseEvent.getSource());
        if(index<4){
            print(new Text("\nHai scelto la "+ (index+1)+"colonna."));
            System.out.println("colonna "+ (index+1));
        }else {
            print(new Text("\n Hai scelto la "+ (index-3)+ "riga."));
            System.out.println("riga "+ (index-3));
        }

        confirm.setDisable(false);
    }


}
