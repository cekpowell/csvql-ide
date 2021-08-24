package Model;

import javafx.scene.image.Image;

/**
 * Defines static image instances to be used by other system components.
 */
public class Images {

    // types 
    
    // FILE //
    public static final Image FILE = new Image("img/file.png");
    public static final Image OPEN = new Image("img/open.png");
    public static final Image NEW_FILE = new Image("img/new.png");

    // EDITOR //
    public static final Image EDITOR = new Image("img/editor.png");
    public static final Image PROGRAM = new Image("img/program.png");
    public static final Image PROGRAM_UNSAVED = new Image("img/program-unsaved.png");
    public static final Image TABLE = new Image("img/table.png");
    public static final Image TABLE_UNSAVED = new Image("img/table-unsaved.png");

    // EDITOR TOOLBAR //
    public static final Image SAVE_AS = new Image("img/save-as.png");
    public static final Image SAVE = new Image("img/save.png");
    public static final Image RENAME = new Image("img/rename.png");
    public static final Image UNDO = new Image("img/undo.png");
    public static final Image REDO = new Image("img/redo.png");
    public static final Image ZOOM_IN = new Image("img/zoom-in.png");
    public static final Image ZOOM_OUT = new Image("img/zoom-out.png");
    public static final Image RUN = new Image("img/run.png");

    // TABLE STORE //
    public static final Image TABLE_STORE = new Image("img/table-store.png");
    public static final Image CLOSE = new Image("img/remove.png");

    // STORED TABLE //
    public static final Image OPEN_IN_EDITOR = new Image("img/openInEditor.png");
    public static final Image REMOVE = new Image("img/remove.png");

    // TERMINAL //
    public static final Image TERMINAL = new Image("img/terminal.png");
    public static final Image MESSAGE = new Image("img/message.png");
    public static final Image ERROR = new Image("img/error.png");

    // TERMINAL TOOLBAR
    public static final Image COPY = new Image("img/copy.png");

    /**
     * Class constructor.
     * 
     * Private - cannot be constructed.
     */
    private Images(){}
}