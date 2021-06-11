package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.Client;
//<<<<<<< DisconnectionHandler
import it.polimi.ingsw.constant.setupper.JoinWaitngListSetupper;
//=======
import javafx.event.ActionEvent;
//>>>>>>> master
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.DataOutputStream;
import java.io.IOException;

public class SampleController extends ControllerGuiInterface{

    public static String className = "sample";
    private String name;
    private Integer number;

    @FXML
    TextField name_lable;
    @FXML
    ChoiceBox<Integer> number_lable;
    @FXML
    Button button;
    @FXML
    Button offline;

    @FXML
    public void initialize(){

    }
    public void setName(){
        this.name=name_lable.getText();
        this.button.setDisable(notCanActive());
        checkOffline();
    }

    public void setNumber(){
        this.number=number_lable.getValue();
        this.button.setDisable(notCanActive());
        checkOffline();
    }

    public void start() throws IOException {
        GUI.client.setOnline();
        DataOutputStream socket = GUI.client.socketOut;
        GUI.client.sendSetupper(new JoinWaitngListSetupper(name, number));
        //gui.activate("lobby");
    }

    private boolean notCanActive(){
        return name == null || number == null;
    }

    private void checkOffline(){
        if(number==null) return;
        offline.setVisible(number==1);
        offline.setDisable(notCanActive());
    }
    @Override
    public void update() {
        offline.setDisable(true);
        offline.setVisible(false);
    }

    @Override
    public String getName() {
        return className;
    }

    public void offline(ActionEvent actionEvent) {
        GUI.client.startOffline(name);
    }
}
