package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.constant.move.MoveChoseLeaderCards;
import it.polimi.ingsw.constant.move.MoveType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class InitialController extends ControllerGuiInterface{

    public  static String className = "initial";

    private final ArrayList<Integer> choice = new ArrayList<>();
    private final boolean[] chosen =new boolean[4];

    @FXML
    public ImageView imageView1;
    @FXML
    public ImageView imageView2;
    @FXML
    public ImageView imageView3;
    @FXML
    public ImageView imageView4;

    @FXML
    Button confirmButton;
    @FXML
    public Label lable1;
    @FXML
    public Label lable2;
    @FXML
    public Label lable3;
    @FXML
    public Label lable4;

    @FXML
    public void initialize(){
        confirmButton.setDisable(true);
    }

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

    public void selectCard0(MouseEvent mouseEvent) {
        if(!chosen[0]) {
            choice.add(gui.getModel().getLeaderCards().get(0).getId());
            lable1.setText("selected");
        }else{
            choice.remove((Integer) gui.getModel().getLeaderCards().get(0).getId());
            lable1.setText("");
        }
        chosen[0]=!chosen[0];
        imageView1.setSmooth(chosen[0]);
        checkButton();
    }

    public void selectCard1(MouseEvent mouseEvent) {
        if(!chosen[1]) {
            choice.add(gui.getModel().getLeaderCards().get(1).getId());
            lable2.setText("selected");
        }else{
            choice.remove((Integer) gui.getModel().getLeaderCards().get(1).getId());
            lable2.setText("");
        }
        chosen[1]=!chosen[1];
        imageView2.setSmooth(chosen[1]);
        checkButton();
    }

    public void selectCard2(MouseEvent mouseEvent) {
        if(!chosen[2]) {
            choice.add(gui.getModel().getLeaderCards().get(2).getId());
            lable3.setText("selected");
        }else{
            choice.remove((Integer) gui.getModel().getLeaderCards().get(2).getId());
            lable3.setText("");
        }
        chosen[2]=!chosen[2];
        imageView3.setSmooth(chosen[2]);
        checkButton();
    }

    public void selectCard3(MouseEvent mouseEvent) {
        if(!chosen[3]) {
            choice.add(gui.getModel().getLeaderCards().get(3).getId());
            lable4.setText("selected");
        }else{
            choice.remove((Integer) gui.getModel().getLeaderCards().get(3).getId());
            lable3.setText("");
        }
        chosen[3]=!chosen[3];
        imageView4.setSmooth(chosen[3]);
        checkButton();
    }


    public void confirm(ActionEvent actionEvent) {
        MoveChoseLeaderCards move = new MoveChoseLeaderCards(gui.getModel().getMyID());
        move.setIndexLeaderCards(choice);
        gui.sendMove(move);
    }

    private void checkButton(){
        this.confirmButton.setDisable(choice.size()!=2);
    }
}
