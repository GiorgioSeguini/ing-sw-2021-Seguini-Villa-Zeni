package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class InitialController extends ControllerGuiInterface{

    public  static String className = "initial";
    @FXML
    public ImageView imageView1;
    @FXML
    public ImageView imageView2;
    @FXML
    public ImageView imageView3;
    @FXML
    public ImageView imageView4;

    @Override
    public void update() {
        int id1 = gui.getModel().getLeaderCards().get(0).getId() +1;
        Image image1 = new Image("/images/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-"+ id1 +"-1.png");
        imageView1.setImage(image1);

        int id2 = gui.getModel().getLeaderCards().get(1).getId() +1;
        Image image2 = new Image("/images/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-"+ id2 +"-1.png");
        imageView2.setImage(image2);

        int id3 = gui.getModel().getLeaderCards().get(2).getId() +1;
        Image image3 = new Image("/images/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-"+ id3 +"-1.png");
        imageView3.setImage(image3);

        int id4 = gui.getModel().getLeaderCards().get(3).getId() +1;
        Image image4 = new Image("/images/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-"+ id4 +"-1.png");
        imageView4.setImage(image4);
    }

    @Override
    public String getName() {
        return className;
    }

    public void selectCard(MouseEvent mouseEvent) {

    }
}
