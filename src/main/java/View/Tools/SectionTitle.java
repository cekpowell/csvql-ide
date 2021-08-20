package View.Tools;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Convenience class for the title of a section within the application.
 */
public class SectionTitle extends Label{

    // static variables
    private static final String font = "Verdana";
    private static final FontWeight fontWeight = FontWeight.BOLD;
    private static final int fontSize = 15;

    /**
     * Class constructor.
     * 
     * @param title The title.
     */
    public SectionTitle(String title){
        // initializing
        super(title);

        /////////////////
        // CONFIGURING //
        ///////////////// 
        
        // formatting the title
        this.setFont(Font.font(font, fontWeight, fontSize)); // TODO should be done with CSS
    }
}
