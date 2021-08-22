package View.Editor;

import java.io.File;

/**
 * Represents an EditorFile that is displaying a table.
 */
public class Table extends EditorFile{
    
    /**
     * Class constructor - instantiated a new EditorFile.
     * 
     * @param fileContainer The container associated with this table.
     * @param name The name of the table.
     */
    public Table(FileContainer fileContainer, String name){
        super(EditorFileType.TABLE, fileContainer, name);
    }

    /**
     * Class constructor - instantiated a new EditorFile.
     * 
     * @param fileContainer The container associated with this table.
     * @param file The File associated with the table.
     */
    public Table(FileContainer fileContainer, File file){
        super(EditorFileType.TABLE, fileContainer, file);
    }

    /**
     * Empty method - table cannot be run so no implementation required.
     */
    public void run(){}
}
