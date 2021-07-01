package it.polimi.ingsw.client.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * The type Alert box. A costume alert Box with a default button that simply close the box
 * Another button can be added
 * The default look will be setted according to default StyleSheet
 */
public class AlertBox {
    private Stage window;
    private final String boxTitle;
    private final String boxMessage;
    private final VBox layout;
    private Button closeButton;

    /**
     * Instantiates a new Alert box, with just one default exit button
     *
     * @param boxTitle   of type String - the box title
     * @param boxMessage of type String - the box message
     */
    public AlertBox(String boxTitle, String boxMessage){
        window= new Stage();
        this.boxTitle= boxTitle;
        this.boxMessage= boxMessage;
        layout=createAlertBox();
    }

    /**
     * Instantiates a new Alert box, with the default exit button and another button passed as parameter
     *
     *
     * @param boxTitle   of type String - the box title
     * @param boxMessage of type String - the box message
     * @param button     of type Button - the button
     */
    public AlertBox(String boxTitle, String boxMessage, Button button){
        window= new Stage();
        this.boxTitle= boxTitle;
        this.boxMessage= boxMessage;
        layout=createAlertBox();
        addButton(button);
    }

    /**
     * Add button to the alert box and set default stylesheet
     *
     * @param button the button
     */
    public void addButton(Button button){
        button.getStyleClass().add("baseButton");
        layout.getChildren().addAll(button);
    }

    /**
     * Show the Alert Box and wait until a button is pressed
     */
    public void display(){
        Scene scene= new Scene(layout);
        scene.getStylesheets().add(getClass().getResource("/fxml/Style.css").toString());
        window.setScene(scene);
        window.showAndWait();
    }

    /**
     * Forse the alert box to close
     */
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
        this.closeButton= new Button("Cancel");
        closeButton.setOnAction(e->window.close());
        closeButton.getStyleClass().add("baseButton");

        VBox layout= new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        return layout;
    }

    /**
     * Get close default button.
     *
     * @return the button - the default close button
     */
    public Button getCloseButton(){
        return closeButton;
    }
}
