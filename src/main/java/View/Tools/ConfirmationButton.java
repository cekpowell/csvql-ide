package View.Tools;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.stage.Window;

import java.util.Optional;

/**
 * Button that asks for confirmation before carrying out the action.
 */
public class ConfirmationButton extends Button {

    // static variables
    private static final ButtonType confirm = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
    private static final ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
    private static final int width = 300;
    private static final int height = 100;

    // member variables
    private String label;
    private Alert confirmationWindow;

    /**
     * Class constructor.
     * 
     * @param label The label for the button.
     * @param confirmationTitle The title of the confirmation window.
     * @param confirmationMessage The message displayed in the confirmation window.
     */
    public ConfirmationButton(String label, String confirmationTitle, String confirmationMessage){
        super(label);

        // initializing 
        this.init(confirmationTitle, confirmationMessage);
    }

    /**
     * Class constructor.
     * 
     * @param label The label for the button.
     * @param image The button image.
     * @param confirmationTitle The title of the confirmation window.
     * @param confirmationMessage The message displayed in the confirmation window.
     */
    public ConfirmationButton(String label, ImageView image, String confirmationTitle, String confirmationMessage){
        super(label, image);

        // initializing
       this.init(confirmationTitle, confirmationMessage);
    }

    private void init(String confirmationTitle, String confirmationMessage){
        // setting up confirmation window
        this.confirmationWindow = new Alert(Alert.AlertType.CONFIRMATION, "", confirm, cancel);
        this.confirmationWindow.setTitle(confirmationTitle);
        this.confirmationWindow.setHeaderText(confirmationMessage);
        this.confirmationWindow.getDialogPane().setPrefSize(width, height);
    }

    /**
     * Dsplays the confirmation window to the screen and returns the result.
     * @return True if the user selected confirm, false if selected cancel.
     */
    public boolean showConfirmationWindow(Window owner){
        this.confirmationWindow.initOwner(owner);
        // displaying the window and getting the result
        Optional<ButtonType> result = this.confirmationWindow.showAndWait();

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