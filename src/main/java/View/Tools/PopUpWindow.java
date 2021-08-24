package View.Tools;

import java.util.Optional;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.stage.Window;

/**
 * Defines methods for generating PopUp Windows.
 * 
 * ConfirmationWindow - Used to confirm an action (e.g., remove a file).
 * 
 * ErrorWindow - Used to display an error message to the screen.
 */
public class PopUpWindow {
    
    // static variables
    // CONFIRMATION WINDOW
    private static final ButtonType confirm = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
    private static final ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
    private static final int width = 300;
    private static final int height = 100;

    /////////////////////////
    // CONFIRMATION WINDOW //
    /////////////////////////

    /**
     * Displays a window asking the user to confirm an action and returns the result
     * of this confirmation.
     * 
     * @param owner The window the confirmation window will be displayed onto.
     * @param title The title of the confirmation window.
     * @param message The message displayed in the confirmastion window.
     * @return True if the user confirmed the window, false otherwise.
     */
    public static boolean showConfirmationWindow(Window owner, String title, String message){
        // initializing the alert
        Alert confirmationWindow = new Alert(AlertType.CONFIRMATION, "", confirm, cancel);
        confirmationWindow.setTitle(title);
        confirmationWindow.setHeaderText(message);
        confirmationWindow.getDialogPane().setPrefSize(width, height);
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

    //////////////////
    // ERROR WINDOW //
    //////////////////

    /**
     * Shows an error window using the provided exception.
     * 
     * @param owner The window the error alert will be displayed onto.
     * @param exception The exception that threw the error.
     */
    public static void showErrorWindow(Window owner, Exception exception){
        // initializing error window
        Alert errorWindow = new Alert(AlertType.ERROR, exception.getMessage());
        errorWindow.setTitle("Error");
        errorWindow.setHeaderText("Error");
        errorWindow.initOwner(owner);

        // showing the error window
        errorWindow.showAndWait();
    }

    /**
     * Shows an error window using the provided error message.
     * 
     * @param owner The window the error alert will be displayed onto.
     * @param message The exception that threw the error.
     */
    public static void showErrorWindow(Window owner, String message){
        // initializing error window
        Alert errorWindow = new Alert(AlertType.ERROR, message);
        errorWindow.setTitle("Error");
        errorWindow.setHeaderText("Error");
        errorWindow.initOwner(owner);

        // showing the error window
        errorWindow.showAndWait();
    }
}
