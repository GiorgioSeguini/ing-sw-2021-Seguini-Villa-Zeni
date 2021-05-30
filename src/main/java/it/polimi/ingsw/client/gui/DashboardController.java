package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.constant.enumeration.ColorDevCard;
import it.polimi.ingsw.constant.enumeration.Level;
import it.polimi.ingsw.constant.model.DevelopmentCard;
import it.polimi.ingsw.constant.move.MoveBuyDevCard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class DashboardController extends ControllerGuiInterface{

    public static String className = "dashboard";
    private int  indexToBuy;
    private final boolean[] selected = new boolean[12];

    @FXML
    public GridPane grid;

    @FXML
    public Button confirmButton;
    @FXML
    public Button baseButton;

    @FXML
    public ImageView imageView1;
    @FXML
    public ImageView imageView2;
    @FXML
    public ImageView imageView3;
    @FXML
    public ImageView imageView4;
    @FXML
    public ImageView imageView5;
    @FXML
    public ImageView imageView6;
    @FXML
    public ImageView imageView7;
    @FXML
    public ImageView imageView8;
    @FXML
    public ImageView imageView9;
    @FXML
    public ImageView imageView10;
    @FXML
    public ImageView imageView11;
    @FXML
    public ImageView imageView12;

    @FXML
    public Label label1;
    @FXML
    public Label label2;
    @FXML
    public Label label3;
    @FXML
    public Label label4;
    @FXML
    public Label label5;
    @FXML
    public Label label6;
    @FXML
    public Label label7;
    @FXML
    public Label label8;
    @FXML
    public Label label9;
    @FXML
    public Label label10;
    @FXML
    public Label label11;
    @FXML
    public Label label12;

    private final ArrayList<ImageView> imageViews = new ArrayList<>();
    private final ArrayList<Label> labels = new ArrayList<>();

    @FXML
    public void initialize(){
        confirmButton.setDisable(true);
        baseButton.setDisable(false);
        imageViews.add(imageView1);
        imageViews.add(imageView2);
        imageViews.add(imageView3);
        imageViews.add(imageView4);
        imageViews.add(imageView5);
        imageViews.add(imageView6);
        imageViews.add(imageView7);
        imageViews.add(imageView8);
        imageViews.add(imageView9);
        imageViews.add(imageView10);
        imageViews.add(imageView11);
        imageViews.add(imageView12);

        labels.add(label1);
        labels.add(label2);
        labels.add(label3);
        labels.add(label4);
        labels.add(label5);
        labels.add(label6);
        labels.add(label7);
        labels.add(label8);
        labels.add(label9);
        labels.add(label10);
        labels.add(label11);
        labels.add(label12);


        for(ImageView imageView : imageViews) {
            imageView.fitWidthProperty().bind(grid.widthProperty().divide(5));
            imageView.fitHeightProperty().bind(grid.heightProperty().divide(2));
        }

    }
    @Override
    public String getName() {
        return className;
    }

    @Override
    public void update() {
        Image[] image = new Image[12];
        int i=0;
        for(Level l: Level.values()) {
            for (ColorDevCard c : ColorDevCard.values()) {
                int id = gui.getModel().getDashboard().getTopDevCard(c, l).getId() + 1;
                image[i] = new Image("/images/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-"+ id +"-1.png");
                imageViews.get(i).setImage(image[i]);
                i++;
            }
        }
    }

    public void onMouseClicked(MouseEvent mouseEvent) {

        int index = imageViews.indexOf((ImageView) mouseEvent.getSource());
        if(check(index)) {
            indexToBuy = gui.getModel().getDashboard().getTopDevCard(ColorDevCard.values()[index%4],Level.values()[index/4]).getId();
            labels.get(index).setText("selected");
        }else{
            indexToBuy = -1;
            labels.get(index).setText("");
        }
       if(indexToBuy<0){
           confirmButton.setDisable(true);
       }

    }

    public void buttonClick(ActionEvent actionEvent){
        if(confirmButton.isPressed()){
            MoveBuyDevCard move = new MoveBuyDevCard(gui.getModel().getMyID());
            move.setIndexCardToBuy(indexToBuy);
            gui.sendMove(move);
        }
        if (baseButton.isPressed()){
            gui.activate(BaseController.className);
        }
    }

    //solo per evitare codice duplicato
    private boolean check(int pos){
        return selected[pos];
    }

    //TODO:  devo rivederla tutta

}
