package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.Client;
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

    public void start(){
        DataOutputStream socket = GUI.client.socketOut;
        try {
            socket.writeUTF(name);
            socket.flush();
            socket.writeInt(number);
            socket.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
        gui.update();
    }

    private boolean notCanActive(){
        return name == null || number == null;
    }

    @Override
    public void update() {
        //nothing to do here
    }

    @Override
    public String getName() {
        return className;
    }
}
