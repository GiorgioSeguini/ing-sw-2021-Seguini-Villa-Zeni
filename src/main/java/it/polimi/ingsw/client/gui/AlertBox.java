package it.polimi.ingsw.client.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class AlertBox {
    private Stage window;
    private String boxTitle;
    private String boxMessage;
    private VBox layout;

    public AlertBox(String boxTitle, String boxMessage){
        window= new Stage();
        this.boxTitle= boxTitle;
        this.boxMessage= boxMessage;
        layout=createAlertBox();
    }

    public AlertBox(String boxTitle, String boxMessage, Button button){
        window= new Stage();
        this.boxTitle= boxTitle;
        this.boxMessage= boxMessage;
        layout=createAlertBox();
        addButton(button);
    }

    public void addButton(Button button){
        layout.getChildren().addAll(button);
    }

    public void display(){
        Scene scene= new Scene(layout);
        scene.getStylesheets().add(getClass().getResource("Style.css").toString());
        window.setScene(scene);
        window.showAndWait();
    }

    public void closeBox(){
        window.close();
    }

    private VBox createAlertBox(){
        window= new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(boxTitle);
        window.setMinWidth(600);
        window.setMaxHeight(600);

        Label label= new Label();
        label.setText(boxMessage);
        Button closeButton= new Button("Cancel");
        closeButton.setOnAction(e->window.close());
        closeButton.getStyleClass().add("baseButton");

        VBox layout= new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        return layout;
    }
}
