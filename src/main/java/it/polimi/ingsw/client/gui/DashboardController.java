package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.constant.enumeration.ColorDevCard;
import it.polimi.ingsw.constant.enumeration.Level;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class DashboardController extends ControllerGuiInterface{

    public static String className = "dashboard";

    @FXML
    public GridPane gridPane;


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

    public ImageView[] imageViews = new ImageView[] {imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9,imageView10,imageView11,imageView12};

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
                imageViews[i].setImage(image[i]);
                i++;
            }
        }
    }
    //TODO:  devo rivederla tutta
}
