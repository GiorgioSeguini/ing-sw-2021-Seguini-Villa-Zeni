package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.constant.setupper.JoinWaitngListSetupper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;

public class PublicLoginController extends ControllerGuiInterface{

    public static String className = "publicLogin";
    private String name;
    private Integer number;

    @FXML
    TextField name_lable;
    @FXML
    ChoiceBox<Integer> number_lable;
    @FXML
    Button button;
    @FXML
    Button ComeBack;

    @FXML
    public void initialize(){

    }

    public void setName(){
        this.name=name_lable.getText();
        this.button.setDisable(notCanActive());
    }

    public void setNumber(){
        this.number=number_lable.getValue();
        this.button.setDisable(notCanActive());
    }

    public void start() throws IOException {
        GUI.client.setOnline();
        //DataOutputStream socket = GUI.client.socketOut;
        GUI.client.sendSetupper(new JoinWaitngListSetupper(name, number));
    }

    private boolean notCanActive(){
        return name == null || number == null;
    }

    /*private void checkOffline(){
        if(number==null) return;
        offline.setVisible(number==1);
        offline.setDisable(notCanActive());
    }*/
    @Override
    public void update() {
        name=null;
        name_lable.setText("");
        button.setDisable(notCanActive());
    }

    @Override
    public String getName() {
        return className;
    }

    public void comeBack(ActionEvent event) {
        gui.activate(StartController.className);
    }

   /* public void offline(ActionEvent actionEvent) {
        GUI.client.startOffline(name);
    }
    */
}
