package View.Editor;

import java.io.File;

import Model.FileType;
import View.TableStore.StoredTable;

/**
 * View that represents an individual table being edited in the editor.
 */
public class TableTab extends EditorTab{

    // member variables
    private StoredTable storedTable; /** The stored table associated with this TableTab */

    //////////////////
    // INITIALIZING //
    //////////////////

    /**
     * Class constructor
     * 
     * @param editorTabContainer The container associated with the ProgramTab.
     * @param name The name of the program.
     * @param file The file associated with the program.
     */
    public TableTab(EditorTabContainer editorTabContainer, String name, File file, StoredTable storedTable){
        // initializing
        super(editorTabContainer, name, file, FileType.TABLE);
        this.storedTable = storedTable;
    }

    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////

    public StoredTable getStoredTable(){
        return this.storedTable;
    }

    public void setStoredTable(StoredTable storedTable){
        this.storedTable = storedTable;
    }
}