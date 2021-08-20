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
        this.init();
    }

    /**
     * Constructor for the class. Creates a new alert window with a string as the error message.
     * @param errorMessage
     */
    public ErrorAlert(String errorMessage){
        // initializing
        super(AlertType.ERROR, errorMessage);
        this.init();
    }

    /**
     * Init method used to set up the ErrorAlert (because there are multiple
     * constructors).
     */
    private void init(){

        /////////////////
        // CONFIGURING //
        /////////////////

        this.setTitle("Error");
        this.setHeaderText("Error");
    }

    /**
     * Shows an error alert using the provided exception.
     * 
     * @param owner The window the error alert will be displayed onto.
     * @param exception The exception that threw the error.
     */
    public static void showErrorAlert(Window owner, Exception exception){
        // creating the error alert
        ErrorAlert errorAlert = new ErrorAlert(exception);
        errorAlert.initOwner(owner);

        // showing the error alert
        errorAlert.showAndWait();
    }

    /**
     * Shows an error alert using the provided error message.
     * 
     * @param owner The window the error alert will be displayed onto.
     * @param message The exception that threw the error.
     */
    public static void showErrorAlert(Window owner, String message){
        // creating the error alert
        ErrorAlert errorAlert = new ErrorAlert(message);
        errorAlert.initOwner(owner);

        // showing the error alert
        errorAlert.showAndWait();
    }
}