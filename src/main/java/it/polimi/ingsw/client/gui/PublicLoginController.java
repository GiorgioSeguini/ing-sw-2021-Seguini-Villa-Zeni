package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.constant.setupper.JoinWaitngListSetupper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class PublicLoginController extends ControllerGuiInterface{

    public static final String className = "publicLogin";
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

    public void setName(){
        this.name=name_lable.getText();
        this.button.setDisable(notCanActive());
    }

    public void setNumber(){
        this.number=number_lable.getValue();
        this.button.setDisable(notCanActive());
    }

    public void start() {
        if(!GUI.client.setOnline()){
            AlertBox box = new AlertBox("Errore di rete", "Oh no! Non siamo riusciti a contattare il server\n Riprova più tardi o controlla la tua connessione internet");
            box.display();
            Platform.exit();
        };
        //DataOutputStream socket = GUI.client.socketOut;
        GUI.client.sendSetupper(new JoinWaitngListSetupper(name, number));
    }

    private boolean notCanActive(){
        return name == null || number == null;
    }

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

}
