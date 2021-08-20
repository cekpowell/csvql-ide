package View.Tools;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;
import javafx.stage.Window;

/**
 * Alert window that displays an error message.
 */
public class ConfirmationWindow extends Alert {

    // static variables
    private static final ButtonType confirm = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
    private static final ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
    private static final int width = 300;
    private static final int height = 100;

    /**
     * Constructor for the class. Creates a new alert window with a ValidationException as the error.
     * @param exception The exception for this error.
     */
    public ConfirmationWindow(String title, String message){
        // initializing
        super(AlertType.CONFIRMATION, "", confirm, cancel);
        this.setTitle(title);
        this.setHeaderText(message);
        this.getDialogPane().setPrefSize(width, height);
    }

    /**
     * Shows an error alert using the provided exception.
     * 
     * @param owner The window the error alert will be displayed onto.
     * @param exception The exception that threw the error.
     */
    public static boolean showConfirmationWindow(Window owner, String title, String message){
        // creating the error alert
        ConfirmationWindow confirmationWindow = new ConfirmationWindow(title, message);
        confirmationWindow.initOwner(owner);

        // displaying the window and getting the result
        Optional<ButtonType> result = confirmationWindow.showAndWait();

        // user presses confirm
        if(result.get() == confirm){
            return true;
        }
        // user presses cancel (or closes window)
        else{
            return false;
        }
    }
}