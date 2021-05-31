package it.polimi.ingsw.client.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;



public class AlertBox {
    private static Stage window;

    public static Stage getWindow() {
        return window;
    }

    private static VBox createAlertBox(String boxTitle, String boxMessage){
        window= new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(boxTitle);
        window.setMinWidth(250);
        window.setMaxHeight(250);

        Label label= new Label();
        label.setText(boxMessage);
        Button closeButton= new Button("Cancel");
        closeButton.setOnAction(e->window.close());

        VBox layout= new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        return layout;
    }

    public static void display(String boxTitle, String boxMessage){
        VBox layout=createAlertBox(boxTitle,boxMessage);
        Scene scene= new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    public static void display(String boxTitle, String boxMessage, Button button){
        VBox layout=createAlertBox(boxTitle,boxMessage);
        layout.getChildren().addAll(button);
        Scene scene= new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
