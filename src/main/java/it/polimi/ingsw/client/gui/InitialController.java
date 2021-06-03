package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.constant.move.MoveChoseLeaderCards;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

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
    public GridPane grid;

    private ArrayList<ImageView> imageViews = new ArrayList<>();
    private ArrayList<Label> labels = new ArrayList<>();

    @FXML
    public void initialize(){
        confirmButton.setDisable(true);

        imageViews.add(imageView1);
        imageViews.add(imageView2);
        imageViews.add(imageView3);
        imageViews.add(imageView4);

        labels.add(lable1);
        labels.add(lable2);
        labels.add(lable3);
        labels.add(lable4);

        for(ImageView imageView : imageViews) {
            imageView.fitWidthProperty().bind(grid.widthProperty().divide(5));
            imageView.fitHeightProperty().bind(grid.heightProperty().divide(2));
        }
    }

    @Override
    public void update() {
        for(int i=0; i<imageViews.size(); i++){
            int id = gui.getModel().getLeaderCards().get(i).getId() +1;
            imageViews.get(i).setImage(new Image("/images/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-"+ id +"-1.png"));
        }
    }

    @Override
    public String getName() {
        return className;
    }

    public void selectCard(MouseEvent mouseEvent){
        int index = imageViews.indexOf((ImageView) mouseEvent.getSource());
        if(!chosen[index]) {
            choice.add(gui.getModel().getLeaderCards().get(index).getId());
            labels.get(index).setText("selected");
        }else{
            choice.remove((Integer) gui.getModel().getLeaderCards().get(index).getId());
            labels.get(index).setText("");
        }
        chosen[index]=!chosen[index];
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
