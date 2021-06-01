package it.polimi.ingsw.client.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class DevCardPositionController extends ControllerGuiInterface{

    public  static String className = "devCardPosition";
    private ArrayList<ImageView> imageViews = new ArrayList<>();

    @FXML
    public GridPane grid;
    @FXML
    public ImageView PersonalBoardDevCard;
    @FXML
    public ImageView imageView1;
    @FXML
    public ImageView imageView2;
    @FXML
    public ImageView imageView3;
    @FXML
    public Button button1;
    @FXML
    public Button button2;
    @FXML
    public Button button3;

    @FXML
    public void initialize(){
        imageViews.add(imageView1);
        imageViews.add(imageView2);
        imageViews.add(imageView3);

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
        for(int i=0; i<imageViews.size(); i++){
            int id = gui.getModel().getLeaderCards().get(i).getId() +1;
            imageViews.get(i).setImage(new Image("/images/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-"+ gui.getModel().getMe().getPersonalBoard().getOwnedDevCards().get(i).getId() +"-1.png"));
        }
    }

    public void onButtonClick(ActionEvent actionEvent){

    }
}
