package View.Tools;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Asbtract class that represents the formatting of a window used to gather user input.
 */
public abstract class InputForm extends Stage {

    // member variables
    private BorderPane container;
    private Button finishButton;
    private Button cancelButton;

    /**
     * Class constructor.
     * @param title The title of the window.
     */
    public InputForm(String title, int width, int height, String finishText, String cancelText, boolean confirmationButtons){
        // initialising member variables
        this.container = new BorderPane();
        if(confirmationButtons){
            this.finishButton = new ConfirmationButton (finishText, "Confirmation", "Are you sure you want to " +  finishText.toLowerCase() + "?");
            this.cancelButton = new ConfirmationButton (cancelText, "Confirmation", "Are you sure you want to " +  cancelText.toLowerCase() + "?");

            /////////////
            // ACTIONS //
            /////////////

            // 'submit' button
            this.finishButton.setOnAction(((e) -> {
                // displaying confirmation window
                ConfirmationButton confirmationButton = (ConfirmationButton) finishButton;
                boolean confirmed = confirmationButton.showConfirmationWindow(this.getScene().getWindow());

                // submitting if the user confirmed the submit
                if(confirmed){
                    // running the submit method
                    this.submit();
                }
            }));

            // 'cancel' button
            this.cancelButton.setOnAction(((e) -> {
                // displaying confirmation window
                ConfirmationButton confirmationButton = (ConfirmationButton) cancelButton;
                boolean confirmed = confirmationButton.showConfirmationWindow(this.getScene().getWindow());

                // closing if the user confirmed
                if(confirmed){
                    // closing the application
                    this.close();
                }
            }));
        }
        else{
            this.finishButton = new Button(finishText);
            this.cancelButton = new Button(cancelText);

            /////////////
            // ACTIONS //
            /////////////

            // 'submit' button
            this.finishButton.setOnAction(((e) -> { this.submit(); }));

            // 'cancel' button
            this.cancelButton.setOnAction(((e) -> { this.close(); }));
        }
        

        ///////////////////////////
        // CONTAINERS AND EXTRAS //
        ///////////////////////////

        // container for submit and cancel buttons
        HBox controlsContainer = new HBox();
        controlsContainer.getChildren().addAll(this.cancelButton, this.finishButton);
        controlsContainer.setAlignment(Pos.BASELINE_RIGHT);
        controlsContainer.setSpacing(10);
        controlsContainer.setPadding(new Insets(20));

        // container for divider and controls
        VBox separatorContainer = new VBox();
        Separator seperator = new Separator();
        separatorContainer.getChildren().addAll(seperator, controlsContainer);

        /////////////////
        // CONFIGURING //
        /////////////////

        // adding control buttons to form
        this.container.setBottom(separatorContainer);

        // configuring the form
        this.initModality(Modality.APPLICATION_MODAL);
        this.setTitle(title);

        // creating new scene with container
        Scene scene = new Scene(container, width, height);

        // setting scene to stage
        this.setScene(scene);
    }

    /**
     * Sets the content within the input form.
     * 
     * @param node The node to placed in the form.
     */
    public void setContent(Node node){
        this.container.setCenter(node);
    }

    /**
     * Verifies the user's inputs are valid. Creates a new object if they are, and displays an error message if they
     * are not.
     */
    public abstract void submit();
}