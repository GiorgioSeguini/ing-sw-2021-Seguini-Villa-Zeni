package it.polimi.ingsw.client.gui;

import com.sun.jdi.Value;
import it.polimi.ingsw.constant.enumeration.ColorDevCard;
import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.enumeration.Level;
import it.polimi.ingsw.constant.model.DevelopmentCard;
import it.polimi.ingsw.constant.move.MoveBuyDevCard;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class DashboardController extends ControllerGuiInterface{

    public static String className = "dashboard";
    private final ArrayList<Integer> choice = new ArrayList<>();
    private boolean[] chosen =new boolean[12];
    private static Double[] IMAGE_X = {38.0, 250.0, 462.0, 674.0, 38.0, 250.0, 462.0, 674.0, 38.0, 250.0, 462.0, 674.0};
    private static Double[] IMAGE_Y = {27.0, 27.0, 27.0, 27.0, 225.0, 225.0, 225.0, 225.0, 414.0, 414.0, 414.0, 414.0};
    private static final Double IMAGE_REAL = 150.0;
    private static final ImageView[] IMAGE_VIEWS = new ImageView[12];
    //MoveBuyDevCard move = new MoveBuyDevCard(gui.getModel().getMyID());

    @FXML
    public AnchorPane anchorPane;
    @FXML
    public GridPane gridSecondScreen;
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

    @FXML
    public ImageView imageViewBoard;


    @FXML
    public ImageView devcard1;
    @FXML
    public ImageView devcard2;
    @FXML
    public ImageView devcard3;
    @FXML
    public ImageView devcardBuyed;



    @FXML
    public Button button1;
    @FXML
    public Button button2;
    @FXML
    public Button button3;

    private final ArrayList<ImageView> imageViews = new ArrayList<>();
    private final ArrayList<ImageView> devCards = new ArrayList<>();
    private final ArrayList<Label> labels = new ArrayList<>();
    private final ArrayList<Button> buttons = new ArrayList<>();

    @FXML
    public void initialize(){

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
        for(int i=0; i<IMAGE_VIEWS.length; i++){
            IMAGE_VIEWS[i] = imageViews.get(i);
        }

        devCards.add(imageViewBoard);
        devCards.add(devcard1);
        devCards.add(devcard2);
        devCards.add(devcard3);
        devCards.add(devcardBuyed);

        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);

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


        GUI.fixImagesToPane(anchorPane,692.0,1280.0,IMAGE_VIEWS,IMAGE_X,IMAGE_Y,IMAGE_REAL);



        imageViewBoard.fitWidthProperty().bind(gridSecondScreen.widthProperty().divide(2));
        devcardBuyed.fitWidthProperty().bind(gridSecondScreen.widthProperty().divide(3));
        devcard1.fitWidthProperty().bind(gridSecondScreen.widthProperty().divide(4));
        devcard2.fitWidthProperty().bind(gridSecondScreen.widthProperty().divide(4));
        devcard3.fitWidthProperty().bind(gridSecondScreen.widthProperty().divide(4));
    }
    @Override
    public String getName() {
        return className;
    }

    @Override
    public void update() {
        resetChoice();
        hideSecondScreen(true);
        hideFirstScreen(false);
        checkButton();
        Image[] image = new Image[12];
        int i=0,j;
        for(Level l: Level.values()) {
            for (ColorDevCard c : ColorDevCard.values()) {
                int id = gui.getModel().getDashboard().getTopDevCard(c, l).getId() + 1;
                image[i] = new Image("/images/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-"+ id +"-1.png");
                imageViews.get(i).setImage(image[i]);
                i++;
            }
        }

        for(i=0; i<3; i++){
            if(gui.getModel().getMe().getPersonalBoard().getPos(i).size()>0) {
                DevelopmentCard devCard= gui.getModel().getMe().getPersonalBoard().getPos(i).get(0);
                devCards.get(i+1).setImage(new Image("images/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-" + (devCard.getId() + 1) + "-1.png"));
            }
        }
    }

    public void onMouseClicked(MouseEvent mouseEvent) {

        int index = imageViews.indexOf((ImageView) mouseEvent.getSource());
        if(new MoveBuyDevCard(gui.getModel().getMyID()).canPerform(gui.getModel())&&gui.getModel().getDashboard().isSomethingBuyable(gui.getModel())) {
            if (!chosen[index]) {
                choice.add(gui.getModel().getDashboard().getTopDevCard(ColorDevCard.values()[index % 4], Level.values()[index / 4]).getId());
                labels.get(index).setText("selected");
            } else {
                choice.remove((Integer) gui.getModel().getDashboard().getTopDevCard(ColorDevCard.values()[index % 4], Level.values()[index / 4]).getId());
                labels.get(index).setText("");
            }
            chosen[index] = !chosen[index];
            checkButton();
        }
    }

    public void confirm(ActionEvent actionEvent){
        hideFirstScreen(true);
        hideSecondScreen(false);
        devCards.get(4).setImage(new Image("images/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-"+(choice.get(0)+1)+"-1.png"));
    }

    public void baseButton(ActionEvent actionEvent){
        resetChoice();
        gui.activate(BaseController.className);
    }

    private void checkButton(){
        this.confirmButton.setDisable(choice.size()!=1||!(new MoveBuyDevCard(gui.getModel().getMyID()).canPerform(gui.getModel()))||!(gui.getModel().getDashboard().isSomethingBuyable(gui.getModel())));
    }

    private void hideSecondScreen(boolean b){
        gridSecondScreen.setVisible(!b);
        gridSecondScreen.setDisable(b);
        imageViewBoard.setVisible(!b);

        for(int i=0; i<devCards.size(); i++){
            devCards.get(i).setVisible(!b);

        }

        for(Button button: buttons){
            button.setVisible(!b);
            button.setDisable(b);
        }
    }

    private void hideFirstScreen(boolean b) {
        gridSecondScreen.setDisable(!b);
        gridSecondScreen.setVisible(b);
        for (ImageView imageView : imageViews) {
            imageView.setVisible(!b);
            imageView.setDisable(b);
        }
        for (Label label : labels) {
            label.setVisible(!b);
            label.setDisable(b);
        }
        checkButton();
        baseButton.setDisable(b);
        confirmButton.setVisible(!b);
        confirmButton.setDisable(b);
        baseButton.setVisible(!b);
    }

    public void choseNumber(ActionEvent actionEvent) {
        int index=-1;
        if (button1.equals(actionEvent.getSource())) {
            index=0;
            System.out.println(index);
        }
        if (button2.equals(actionEvent.getSource())) {
            index=1;
        }
        if (button3.equals(actionEvent.getSource())) {
            index=2;
        }
        System.out.println(index);
        MoveBuyDevCard move= new MoveBuyDevCard(gui.getModel().getMyID());
        move.setIndexCardToBuy(choice.get(0));
        move.setPos(index);
        gui.sendMove(move);
    }

    private void resetChoice(){
        choice.removeAll(choice);
        for(Label label: labels){
            label.setText("");
        }
        chosen=new boolean[12];
    }

}
