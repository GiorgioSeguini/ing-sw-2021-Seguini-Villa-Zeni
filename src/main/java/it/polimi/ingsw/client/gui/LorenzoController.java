package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class LorenzoController extends ControllerGuiInterface{
    public static final String className = "lorenzo";
    private static final Double[] IMAGE_X = {200.0, 800.0};
    private static final Double[] IMAGE_Y = {300.0, 300.0};
    private static final Double IMAGE_HEIGTH = 200.0;


    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView retro;
    @FXML
    private ImageView faithTrack;
    private final ImageView front = new ImageView();
    private final Image[] images;

    public LorenzoController(){
        images = new Image[6];
        for(int i=1; i<=6; i++){
            images[i-1]=new Image("/images/punchboard/cerchio"+i+".png");
        }
    }

    @FXML
    public void initialize(){
        anchorPane.getChildren().add(front);
        GUI.fixImagesToPane(anchorPane, 629.0, 1280.0, new ImageView[]{front, retro}, IMAGE_X, IMAGE_Y, IMAGE_HEIGTH);
        //TODO
        GUI.fixImagesToPane(anchorPane, 692.0, 1280.0, new ImageView[]{faithTrack}, new Double[]{0.0}, new Double[]{0.0}, 300.0);
    }

    @Override
    public void update() {
        front.setImage(images[gui.getModel().getSoloGame().getRevealed().ordinal()]);
        //TODO
        // gui.printFaithTrack();
    }

    @Override
    public String getName() {
        return className;
    }
}
