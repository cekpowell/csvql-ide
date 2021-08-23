package View.Tools;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.stage.Window;

/**
 * Button that asks for confirmation before carrying out the action.
 */
public class ConfirmationButton extends Button {

    // member variables
    private String confirmationTitle;
    private String confirmationMessage;

    /**
     * Class constructor.
     * 
     * @param label The label for the button.
     * @param confirmationTitle The title of the confirmation window.
     * @param confirmationMessage The message displayed in the confirmation window.
     */
    public ConfirmationButton(String label, String confirmationTitle, String confirmationMessage){
        // initializing
        super(label);
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
        // initializing
        super(label, image);
        this.init(confirmationTitle, confirmationMessage);
    }

    /**
     * Init method - used as multiple constructors.
     * 
     * @param confirmationTitle The title for the confirmation window.
     * @param confirmationMessage The message displayed in the confirmation window.
     */
    private void init(String confirmationTitle, String confirmationMessage){
        // setting up confirmation window
        this.confirmationTitle = confirmationTitle;
        this.confirmationMessage = confirmationMessage;
    }

    /**
     * Dsplays the confirmation window to the screen and returns the result.
     * @return True if the user selected confirm, false if selected cancel.
     */
    public boolean showConfirmationWindow(Window owner){
        return ConfirmationWindow.showConfirmationWindow(owner, this.confirmationTitle, this.confirmationMessage);
    }
}