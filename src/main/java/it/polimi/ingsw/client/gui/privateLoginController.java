package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.constant.setupper.CreateRoomSetupper;
import it.polimi.ingsw.constant.setupper.JoinWaitngListSetupper;
import it.polimi.ingsw.constant.setupper.LinkToRoomSetupper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.DataOutputStream;

public class privateLoginController extends ControllerGuiInterface{

    public static String className = "privateLogin";
    private String name;
    private Integer number;
    private String roomName;
    private boolean addinroom=false;

    @FXML
    TextField name_lable;
    @FXML
    TextField roomName_lable;
    @FXML
    ChoiceBox<Integer> number_lable;
    @FXML
    Button button;
    @FXML
    Button ComeBack;
    @FXML
    MenuButton menuChoice;
    @FXML
    MenuItem addInRoom;
    @FXML
    MenuItem createRoom;

    @Override
    public String getName() {
        return className;
    }

    @Override
    public void update() {
        name=null;
        roomName=null;
        number_lable.setVisible(notCanActiveCreate());
        addinroom=false;
    }

    public void setName(){
        this.name=name_lable.getText();
        if(addinroom){
            this.button.setDisable(notCanActive());
        }
        else {
            this.button.setDisable(notCanActiveCreate());
        }
    }

    public void setNumber(){
        this.number=number_lable.getValue();
        if(addinroom){
            this.button.setDisable(notCanActive());
        }
        else {
            this.button.setDisable(notCanActiveCreate());
        }
    }

    public void start(ActionEvent event) {
        GUI.client.setOnline();
        if (addinroom){
            GUI.client.sendSetupper(new LinkToRoomSetupper(name, roomName));
        }
        else{
            GUI.client.sendSetupper(new CreateRoomSetupper(name, roomName, number));
        }
    }

    public void comeBack(ActionEvent event) {
        gui.activate(StartController.className);
    }

    public void setRoomName() {
        this.roomName=roomName_lable.getText();
        if(addinroom){
            this.button.setDisable(notCanActive());
        }
        else {
            this.button.setDisable(notCanActiveCreate());
        }
    }

    public void needNumber(ActionEvent event) {
        if(event.getSource().equals(addInRoom)) {
            number_lable.setVisible(false);
            addinroom = true;
        }
        else{
            number_lable.setVisible(true);
            addinroom = false;
        }
    }

    private boolean notCanActive(){
        return name == null || roomName==null;
    }

    private boolean notCanActiveCreate(){
        if (notCanActive()) return number==null;
        return false;
    }
}
