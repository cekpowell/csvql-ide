package View.Tools;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Convenience class for the title of a section within the application.
 */
public class SectionTitle extends HBox{

    // constants
    private static final String font = "Verdana";
    private static final FontWeight fontWeight = FontWeight.BOLD;
    private static final int fontSize = 15;

    // member variables
    private Label label;
    private ImageView image;

    /**
     * Class constructor.
     * 
     * @param title The title.
     */
    public SectionTitle(String title, ImageView image){
        // initializing
        this.label = new Label(title);
        this.image = image;

        // configuring member variables

        // formatting the label
        this.label.setFont(Font.font(font, fontWeight, fontSize)); // TODO should be done with CSS

        /////////////////
        // CONFIGURING //
        ///////////////// 
        
        this.getChildren().addAll(this.image, this.label);
        this.setSpacing(10);
        this.setAlignment(Pos.CENTER_LEFT);
    }
}