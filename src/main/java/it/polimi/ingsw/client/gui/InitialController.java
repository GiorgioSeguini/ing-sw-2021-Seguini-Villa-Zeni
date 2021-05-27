package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class InitialController extends ControllerGuiInterface{

    public  static String className = "initial";
    @FXML
    public ImageView imageView;


    @Override
    public void update() {
        int id = gui.getModel().getLeaderCards().get(0).getId() +1;
        //Image image = new Image("/images/board/Masters of Renaissance_PlayerBoard (11_2020)-1.png");
        Image image = new Image("/images/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-"+ id +"-1.png");
        imageView.setImage(image);

    }

    @Override
    public String getName() {
        return className;
    }
}
