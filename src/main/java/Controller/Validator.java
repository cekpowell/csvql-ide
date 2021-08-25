package Controller;

import java.io.File;

import Model.FileType;
import View.Editor.EditorTab;
import View.Editor.ProgramTab;
import View.Editor.TableTab;
import View.TableStore.StoredTable;

/**
 * Static class that defines methods to validate input information (e.g.,
 * validating filenames are suitable before adding them into the system).
 */
public class Validator {
    

    //////////////////
    // INITIALIZING //
    //////////////////

    /**
     * Class constructor.
     * 
     * Private so cannot be instantiated.
     */
    private Validator(){}

    ///////////////
    // NEW FILES //
    ///////////////

    /**
     * Validates that the provided filename is valid for the system.
     * 
     * @param filename The name of the new file being added into the system.
     * @throws Exception If the filename is not valid for the system.
     */
    public static void validateNewFile(String filename) throws Exception{
        // var to hold errors
        String errors = "";

        // CHECKING INPUT //

        // The filetype must be supported
        boolean fileTypeCorrect = (FileType.getFileType(filename) != null);

        // filename cannot be in use in the system
        boolean fileNameIsCorrect = !Validator.fileNameIsInUse(filename);

        // CHECKING RESULTS //

        if(!fileTypeCorrect){
            // adding error and skipping to next file
            errors += "The file '" + filename + "' is not supported by the system.\n";
        }
        if(!fileNameIsCorrect){
            // adding error and skipping to next file
            errors += "The filename '" + filename + "' is already in use in the system.\n";
        }

        // THROWINNG EXCEPTION //

        if(errors != ""){
            throw new Exception(errors);
        }
    }

    /**
     * Validates that the provided filename is valid for the system.
     * 
     * @param file The new file being added into the system.
     * @throws Exception If the filename is not valid for the system.
     */
    public static void validateNewFile(File file) throws Exception{
        // running thee validator method on the filename
        Validator.validateNewFile(file.getName());
    }

    ///////////////////
    // STORED TABLES //
    ///////////////////

    /**
     * Validates that the provided file can be loaded into the TableStore.
     * 
     * @param file The file being loaded into the TableStore
     * @throws Exception If the new file cannot be opened into the TableStore.
     */
    public static void validateNewTableStoreFile(File file) throws Exception{
        // var to hold errors
        String errors = "";

        // CHECKING INPUT //

        // The filetype must be correct
        boolean fileTypeCorrect = FileType.TABLE.fileHasSameFileType(file);
        // filename cannot be in use in the system
        boolean fileNameIsCorrect = !Validator.fileNameIsInUse(file.getName());

        // CHECKING RESULTS //
        
        if(!fileTypeCorrect){
            // adding error and skipping to next file
            errors += "The file '" + file.getName() + "' is not supported by the TableStore.\n";
        }
        if(!fileNameIsCorrect){
            // adding error and skipping to next file
            errors += "The filename '" + file.getName() + "' is already in use in the system.\n";
        }

        // THROWING EXCEPTION //

        if(errors != ""){
            throw new Exception(errors);
        }
    }

    /**
     * Validates that the provided StoredTable can be loaded into the Editor.
     * 
     * @param storedTable The StoredTable being loaded into thee Editor.
     * @throws Exception If the StoredTable cannot be opened in the Editor.
     */
    public static void validateOpenStoredTableInEditor(StoredTable storedTable) throws Exception{
        if(Validator.fileNameIsInUseInEditor(storedTable.getName())){
            throw new Exception("There is already a file with this name in the editor!");
        }
    }

    ////////////////////
    // RENAMING FILES //
    ////////////////////

    /**
     * Validates that the provided file name is a valid for renaming the provided
     * EditorTab.
     * 
     * @param editorTab The EditorTab being renamed.
     * @param newFilename The new filename for the EditorTab.
     * @throws Exception Thrown if the provided EditorTab cannot be renamed to the given filename
     * because either the FileType is not supported, or the name is already in use.
     */
    public static void validateRenameFile(EditorTab editorTab, String newFilename) throws Exception{
        // var to hold errors
        String errors = "";

        // CHECKING INPUT //

        // The filetype must be supported
        boolean fileTypeCorrect = (FileType.getFileType(newFilename) != null);

        // filename must be a valid name for renaming
        boolean fileNameIsCorrect = Validator.renameFileNameIsValid(editorTab, newFilename);

        // CHECKING RESULTS //

        if(!fileTypeCorrect){
            // adding error and skipping to next file
            errors += "The file '" + newFilename + "' is not supported by the system.\n";
        }
        if(!fileNameIsCorrect){
            // adding error and skipping to next file
            errors += "The filename '" + newFilename + "' is already in use in the system.\n";
        }

        // THROWINNG EXCEPTION //

        if(errors != ""){
            throw new Exception(errors);
        }
    }

    //////////////////////
    // RUNNING PROGRAMS //
    //////////////////////

    /**
     * Validates the running of a ProgramTab - ensures it can be run.
     * 
     * @param programTab The ProgramTab being run.
     * @throws Exception Thrown if the ProgramTab cannot be run.
     */
    public static void validateRunProgramTab(ProgramTab programTab) throws Exception{
        // the ProgramTab must have an associated file to be run
        if(!(programTab.getFile() != null)){
            throw new Exception("The program has no saved data - it cannot be run.");
        }
    }


    ////////////////////
    // HELPER METHODS //
    ////////////////////

    /**
     * Determines if a given file name is already in use within
     * the system. Used to determine if a filename is acceptable when
     * creating/loading a new file into the system.
     * 
     * Should not be used when determining if a save file name is 
     * acceptable, as it will return true for the file being saved.
     * 
     * @param filename The name of the file.
     * @return True if name is taken, false if not.
     */
    private static boolean fileNameIsInUse(String filename){
        return (Validator.fileNameIsInUseInEditor(filename) || Validator.fileNameIsInUseInTableStore(filename));
    }

    /**
     * Determines if there are any tabs open in the Editor that have the 
     * provided filename.
     * 
     * @param filename The filename being checked for.
     * @return True if at least one tab has the filename, false otherwise.
     */
    private static boolean fileNameIsInUseInEditor(String filename){
        // iterating through editor tabs
        for(EditorTab editorTab : SystemController.getInstance().getDashboard().getEditor().getEditorTabContainer().getEditorTabs()){
            // checking for match
            if(editorTab.getName().equals(filename)){
                return true;
            }
        }

        // no match found - filename not in use
        return false;
    }

    /**
     * Determines if there are any StoredTables in the TableStore that have
     * the provided filename.
     * 
     * @param filename The filename being checked for.
     * @return True if at least one StoredTable has the filename, false
     * otherwise.
     */
    private static boolean fileNameIsInUseInTableStore(String filename){
        // iterating through stored tables
        for(StoredTable storedTable : SystemController.getInstance().getDashboard().getTableStore().getStoredTables()){
            // checking for match
            if(storedTable.getName().equals(filename)){
                return true;
            }
        }

        // no match found - filename not in use
        return false;
    }

    /**
     * Determines if a new file name is valid within the system (saveAs and rename).
     * A name is valid if it is not in use by any other file in the system other 
     * than the one being saved/renamed.
     * 
     * @param editortab The EditorTab associated with the file being saved.
     * @param newFilename The name being checked.
     * @return True if the filename is valid for saving, false if not.
     */
    private static boolean renameFileNameIsValid(EditorTab editortab, String newFilename){
        // Editor Tabs
        for(EditorTab eTab : SystemController.getInstance().getDashboard().getEditor().getEditorTabContainer().getEditorTabs()){
            // checking if tab is a different tab but with the same name
            if(eTab != editortab && eTab.getName().equals(newFilename)){
                return false;
            }
        }

        // Stored Tables
        for(StoredTable storedTable : SystemController.getInstance().getDashboard().getTableStore().getStoredTables()){
            // Type is Program
            if(editortab.getFileType() == FileType.PROGRAM_CSVQL){
                // program being saved cant have same name as any stored table
                if(storedTable.getName().equals(newFilename)){
                    return false;
                }
            }
            
            // Type is Table
            else{
                // casting editor tab to TableTab (as that is it's type)
                TableTab tableTab = (TableTab) editortab;

                // tabletab has no stored table
                if(tableTab.getStoredTable()== null){
                    // table being saved cant have same name as any stored table
                    if(storedTable.getName().equals(newFilename)){
                        return false;
                    }
                }

                // table tab has stored table
                else{
                    // table being saved can have same name only if it is same stored table
                    if(storedTable != tableTab.getStoredTable() && storedTable.getName().equals(newFilename)){
                        return false;
                    }
                }
            }
        }

        // no matches were found - so name is valid - returning true
        return true;
    }
}
