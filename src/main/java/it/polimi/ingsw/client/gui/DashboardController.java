package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.constant.enumeration.ColorDevCard;
import it.polimi.ingsw.constant.enumeration.Level;
import it.polimi.ingsw.constant.model.DevelopmentCard;
import it.polimi.ingsw.constant.move.MoveBuyDevCard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class DashboardController extends ControllerGuiInterface{

    public static String className = "dashboard";
    int indexToBuy;

    @FXML
    public AnchorPane anchorPane;

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

    private final ArrayList<ImageView> imageViews = new ArrayList<>();

    @Override
    public String getName() {
        return className;
    }

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
        indexToBuy = gui.getModel().getDashboard().getTopDevCard(ColorDevCard.values()[index%4],Level.values()[index/4]).getId();

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

    //TODO:  devo rivederla tutta

}
