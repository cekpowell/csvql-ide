package View.Tools;

import javafx.scene.control.Alert;
import javafx.stage.Window;

/**
 * Alert window that displays an error message.
 */
public class ErrorAlert extends Alert {

    /**
     * Constructor for the class. Creates a new alert window with a ValidationException as the error.
     * @param exception The exception for this error.
     */
    public ErrorAlert(Exception exception){
        // initializing
        super(AlertType.ERROR, exception.getMessage());
        this.setTitle("Error");
        this.setHeaderText("Error");
    }

    /**
     * Constructor for the class. Creates a new alert window with a string as the error message.
     * @param errorMessage
     */
    public ErrorAlert(String errorMessage){
        // initializing
        super(AlertType.ERROR, errorMessage);
        this.setTitle("Error");
        this.setHeaderText("Error");
    }

    /**
     * Shows the error alert.
     * 
     * @param owner The current window.
     */
    public void showWindow(Window owner){
        this.initOwner(owner);

        this.showAndWait();
    }
}