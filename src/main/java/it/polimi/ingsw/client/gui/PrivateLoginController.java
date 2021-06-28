package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.constant.setupper.CreateRoomSetupper;
import it.polimi.ingsw.constant.setupper.LinkToRoomSetupper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PrivateLoginController extends ControllerGuiInterface{

    public static final String className = "privateLogin";
    private String name;
    private Integer number=0;
    private String roomName;
    private boolean addinroom=false;

    private static final String addInroom= "Accedi ad una stanza";
    private static final String createroom="Crea una stanza";

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
        name_lable.setText("");
        roomName=null;
        roomName_lable.setText("");
        addinroom=false;
        name_lable.setVisible(false);
        roomName_lable.setVisible(false);
        number_lable.setVisible(false);
        button.setDisable(true);

        menuChoice.setText("Seleziona l'azione");
        addInRoom.setText(addInroom);
        createRoom.setText(createroom);
    }

    public void setName(){
        this.name=name_lable.getText();
        CheckValues();
    }

    public void setNumber(){
        this.number=number_lable.getValue();
        CheckValues();
    }

    public void setRoomName() {
        this.roomName=roomName_lable.getText();
        CheckValues();
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


    public void needNumber(ActionEvent event) {
        name_lable.setVisible(true);
        roomName_lable.setVisible(true);
        if(event.getSource().equals(addInRoom)) {
            number_lable.setVisible(false);
            addinroom = true;
            menuChoice.setText(addInRoom.getText());
        }
        else{
            number_lable.setVisible(true);
            addinroom = false;
            menuChoice.setText(createRoom.getText());
        }
    }

    private void CheckValues(){
        if(addinroom){
            if(name!=null && roomName!=null){
                button.setDisable(false);
            }else{
                button.setDisable(true);
            }
        }else{
            if(name!=null && roomName!=null && number!=0){
                button.setDisable(false);
            }
            else{
                button.setDisable(true);
            }
        }
    }
}
