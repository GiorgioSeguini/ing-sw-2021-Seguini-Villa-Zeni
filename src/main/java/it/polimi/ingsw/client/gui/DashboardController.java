package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.constant.enumeration.ColorDevCard;
import it.polimi.ingsw.constant.enumeration.ErrorMessage;
import it.polimi.ingsw.constant.enumeration.Level;
import it.polimi.ingsw.constant.model.DevelopmentCard;
import it.polimi.ingsw.constant.move.MoveBuyDevCard;
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

import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

public class DashboardController extends ControllerGuiInterface{

    public static String className = "dashboard";
    private int  indexToBuy;
    private final ArrayList<Integer> choice = new ArrayList<>();
    private final boolean[] chosen =new boolean[12];
    MoveBuyDevCard move = new MoveBuyDevCard(gui.getModel().getMyID());


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
    public BorderPane BPane1;
    @FXML
    public BorderPane BPane2;
    @FXML
    public BorderPane BPane3;
    @FXML
    public BorderPane BPane4;
    @FXML
    public BorderPane BPane5;
    @FXML
    public BorderPane BPane6;
    @FXML
    public BorderPane BPane7;
    @FXML
    public BorderPane BPane8;
    @FXML
    public BorderPane BPane9;
    @FXML
    public BorderPane BPane10;
    @FXML
    public BorderPane BPane11;
    @FXML
    public BorderPane BPane12;
    @FXML
    public GridPane gridPane;
    @FXML
    public ImageView imageViewBoard;
    @FXML
    public ImageView imageViewBoardCard1;
    @FXML
    public ImageView imageViewBoardCard2;
    @FXML
    public ImageView imageViewBoardCard3;
    @FXML
    public ImageView imageViewCardBuyed;
    @FXML
    public Button button1;
    @FXML
    public Button button2;
    @FXML
    public Button button3;

    private final ArrayList<ImageView> imageViews = new ArrayList<>();
    private final ArrayList<ImageView> imageViewsBoard = new ArrayList<>();
    private final ArrayList<Label> labels = new ArrayList<>();
    private final ArrayList<BorderPane> borderPanes = new ArrayList<>();
    private final ArrayList<Button> buttons = new ArrayList<>();

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

        imageViewsBoard.add(imageViewBoard);
        imageViewsBoard.add(imageViewBoardCard1);
        imageViewsBoard.add(imageViewBoardCard2);
        imageViewsBoard.add(imageViewBoardCard3);
        imageViewsBoard.add(imageViewCardBuyed);

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

        borderPanes.add(BPane1);
        borderPanes.add(BPane2);
        borderPanes.add(BPane3);
        borderPanes.add(BPane4);
        borderPanes.add(BPane5);
        borderPanes.add(BPane6);
        borderPanes.add(BPane7);
        borderPanes.add(BPane8);
        borderPanes.add(BPane9);
        borderPanes.add(BPane10);
        borderPanes.add(BPane11);
        borderPanes.add(BPane12);


    }
    @Override
    public String getName() {
        return className;
    }

    @Override
    public void update() {
        hideSecondScreen(true);
        hideFirstScreen(false);
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

    public MoveBuyDevCard getMove(){
        return move;
    }

    public void onMouseClicked(MouseEvent mouseEvent) {

        int index = imageViews.indexOf((ImageView) mouseEvent.getSource());
        if(!chosen[index]) {
            choice.add(gui.getModel().getDashboard().getTopDevCard(ColorDevCard.values()[index%4],Level.values()[index/4]).getId());
            labels.get(index).setText("selected");
        }else{
            choice.remove((Integer)gui.getModel().getDashboard().getTopDevCard(ColorDevCard.values()[index%4],Level.values()[index/4]).getId());
            labels.get(index).setText("");
        }
        chosen[index]=!chosen[index];
        checkButton();
    }

    public void confirm(ActionEvent actionEvent){
        hideFirstScreen(true);
        hideSecondScreen(false);
        move.setIndexCardToBuy(choice.get(0));

    }

    public void baseButton(ActionEvent actionEvent){
            gui.activate(BaseController.className);
    }

    private void checkButton(){
        this.confirmButton.setDisable(choice.size()!=1);
    }

    private void hideSecondScreen(boolean b){
        gridPane.setVisible(!b);
        gridPane.setDisable(b);
        for(ImageView imageView: imageViewsBoard){
            imageView.setVisible(!b);
            imageView.setDisable(b);
        }
        for(Button button: buttons){
            button.setVisible(!b);
            button.setDisable(b);
        }
    }
    private void hideFirstScreen(boolean b) {
        for (BorderPane borderPane : borderPanes) {
            borderPane.setVisible(!b);
            borderPane.setDisable(b);
        }
        for (Label label : labels) {
            label.setVisible(!b);
            label.setDisable(b);
        }
        confirmButton.setDisable(b);
        baseButton.setDisable(b);
        confirmButton.setVisible(!b);
        baseButton.setVisible(!b);
    }

    public void choseNumber(ActionEvent actionEvent) {
        int index=-1;
        if (button1.equals(actionEvent.getSource())) {
            index=0;
        }
        if (button2.equals(actionEvent.getSource())) {
            index=1;
        }
        if (button3.equals(actionEvent.getSource())) {
            index=2;
        }
        move.setPos(index);
        gui.sendMove(move);
    }
    //TODO:  devo rivederla tutta

}