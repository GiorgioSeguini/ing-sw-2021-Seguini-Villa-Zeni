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

/**
 * Controller of the chose initial leader card pane
 */
public class InitialController extends ControllerGuiInterface{

    public  static final String className = "initial";

    private final ArrayList<Integer> choice = new ArrayList<>();
    private final boolean[] chosen =new boolean[4];

    @FXML
    private ImageView imageView1;
    @FXML
    private ImageView imageView2;
    @FXML
    private ImageView imageView3;
    @FXML
    private ImageView imageView4;

    @FXML
    private Button confirmButton;
    @FXML
    private Label lable1;
    @FXML
    private Label lable2;
    @FXML
    private Label lable3;
    @FXML
    private Label lable4;

    @FXML
    private GridPane grid;

    private final ArrayList<ImageView> imageViews = new ArrayList<>();
    private final ArrayList<Label> labels = new ArrayList<>();

    /**
     * Initialize the pane and its elements, making it resizable
     */
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

    /**
     * @see ControllerGuiInterface#update()
     */
    @Override
    public void update() {
        for(int i=0; i<imageViews.size(); i++){
            int id = gui.getModel().getLeaderCards().get(i).getId() +49;
            imageViews.get(i).setImage(new Image("/images/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-"+ id +"-1.png"));
        }
    }

    /**
     * @see ControllerGuiInterface#update()
     */
    @Override
    public String getName() {
        return className;
    }

    /**
     * Select a leader card from the available ones
     * if production not yet selected Highlight the imageview and add to selection buffer the card
     * if production already selected make imageview standard and remove the production from the selection buffer
     * Update buttons
     *
     * @param mouseEvent the mouse event
     */
    public void selectCard(MouseEvent mouseEvent){
        ((ImageView) mouseEvent.getSource()).setId("imageViewClicked");
        int index = imageViews.indexOf((ImageView) mouseEvent.getSource());
        if(!chosen[index]) {
            choice.add(gui.getModel().getLeaderCards().get(index).getId());
            ((ImageView) mouseEvent.getSource()).setId("imageViewClicked");
            labels.get(index).setText("selected");
        }else{
            choice.remove((Integer) gui.getModel().getLeaderCards().get(index).getId());
            ((ImageView) mouseEvent.getSource()).setId("imageView1");
            labels.get(index).setText("");
        }
        chosen[index]=!chosen[index];
        checkButton();
    }


    /**
     * Send a MoveChoseInitialLeaderCards to server with the ids of the cards in the buffer
     *
     * @param actionEvent the action event
     */
    public void confirm(ActionEvent actionEvent) {
        MoveChoseLeaderCards move = new MoveChoseLeaderCards(gui.getModel().getMyID());
        move.setIndexLeaderCards(choice);
        gui.sendMove(move);
    }

    private void checkButton(){
        this.confirmButton.setDisable(choice.size()!=2);
    }

}
