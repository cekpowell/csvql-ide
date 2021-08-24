package Model;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

/**
 * Defines static KeyCombination instances to be used by other system components.
 */
public class KeyCodes {
    
    // SAVE
    public static final KeyCombination CTRL_S = new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN);

    // RUN
    public static final KeyCombination CTRL_R = new KeyCodeCombination(KeyCode.R, KeyCombination.SHORTCUT_DOWN);

    // UNDO
    public static final KeyCombination CTRL_Z = new KeyCodeCombination(KeyCode.Z, KeyCombination.SHORTCUT_DOWN);

    //REDO
    public static final KeyCombination CTRL_SHIFT_Z = new KeyCodeCombination(KeyCode.Z, KeyCombination.SHIFT_ANY, KeyCombination.SHORTCUT_DOWN);
    
    // ZOOM-IN
    public static final KeyCombination CTRL_PLUS = new KeyCodeCombination(KeyCode.EQUALS, KeyCombination.SHORTCUT_DOWN);

    // ZOOM-OUT
    public static final KeyCombination CTRL_MINUS = new KeyCodeCombination(KeyCode.MINUS, KeyCombination.SHORTCUT_DOWN);

    /**
     * Class constructor.
     * 
     * Private - cannot be constructed.
     */
    private KeyCodes(){}
}
