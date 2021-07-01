package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.constant.model.Player;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

/**
 * Controller of the pane showed at the end of the game
 * Show players rank and allow just to exit from the game
 */
public class EndGameController extends ControllerGuiInterface{

    public static final String className="EndGame";

    private static final String[] messagges = {
        "Primo classificato:\n",
        "Secondo classificato:\n",
        "Terzo classificato:\n",
        "Quarto classificato:\n"
    };


    @FXML
    private GridPane grid;

    @FXML
    private ImageView imageView1;
    @FXML
    private ImageView imageView2;
    @FXML
    private ImageView imageView3;
    @FXML
    private ImageView imageView4;

    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label label4;

    @FXML
    private Label label;

    @FXML
    private Button button;

    private final Label[] labels = new Label[4];
    private final ImageView[] imageViews = new ImageView[4];

    /**
     * Initialize the pane and its elements, making it resizable
     */
    @FXML
    public void initialize(){
        button.setDisable(false);
        Image user= new Image("/images/user.png");
        imageViews[0]=imageView1;
        imageViews[1]=imageView2;
        imageViews[2]=imageView3;
        imageViews[3]=imageView4;

        for(ImageView imageView:imageViews){
            imageView.setImage(user);
            imageView.setVisible(false);
        }

        for(ImageView imageView : imageViews) {
            imageView.fitWidthProperty().bind(grid.widthProperty().divide(5));
            imageView.fitHeightProperty().bind(grid.heightProperty().divide(2));
        }

        labels[0]= label1;
        labels[1]= label2;
        labels[2]= label3;
        labels[3]= label4;
    }

    /**
     * @see ControllerGuiInterface#update()
     */
    @Override
    public void update() {
        if(!gui.getModel().isSinglePlayer()) {
            label.setText("Il gioco è terminato.\nEcco la classifica finale:");
            ArrayList<Player> players = gui.getModel().getPlayers();
            players.sort((o1, o2) -> o2.getVictoryPoints() - o1.getVictoryPoints());
            int i = 0;
            for (Player p : players) {
                labels[i].setText(messagges[i] + p.getUserName() + "\nPunti: " + p.getVictoryPoints());
                imageViews[i].setVisible(true);
                i++;
            }
        }else{
            if(gui.getModel().getSoloGame().isWinner()){
                label.setText("Oh no, Lorenzo ha vinto");
            }else{
                label.setText("Hai vinto, hai totalizzato " + gui.getModel().getMe().getVictoryPoints() + "punti\n" +
                        "Sei riuscito a battere il tuo record?");
            }
        }

    }

    /**
     * @see ControllerGuiInterface#getName()
     */
    @Override
    public String getName() {
        return className;
    }

    /**
     * Exit from the program
     *
     * @param actionEvent the action event
     */
    public void exit(ActionEvent actionEvent) {
        Platform.exit();
    }
}
