package it.polimi.ingsw.client.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class SingleLoginCotroller extends ControllerGuiInterface{
    public static final String className = "singleLogin";
    private String name;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField name_lable;
    @FXML
    private Button button;
    @FXML
    private Button ComeBack;

    public void setName() {
        this.name = name_lable.getText();
        this.button.setDisable(notCanActive());
    }

    public void setNumber() {
        this.button.setDisable(notCanActive());
    }

    public void start() {
        GUI.client.startOffline(name);
    }

    private boolean notCanActive() {
        return name == null;
    }

    @Override
    public void update() {
        name = null;
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
