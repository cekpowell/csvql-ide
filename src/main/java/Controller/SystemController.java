package Controller;

import java.io.File;

import Model.FileType;
import View.App.Dashboard;
import View.App.DashboardToolbar;
import View.Editor.EditorTab;
import View.Editor.EditorTabContainer;
import View.Editor.ProgramTab;
import View.Editor.TableTab;
import View.TableStore.StoredTable;

/**
 * Serves as the main controller for the application. Adopts the 
 * singleton pattern and uses instance methods to control the system's
 * data flow.
 */
public class SystemController {
        
    // local variables
    private static SystemController instance = null;
    private DashboardToolbar toolbar;
    private Dashboard dashboard;

    //////////////////
    // INITIALIZING //
    //////////////////

    /**
     * Class constructor - private so can't be initialized.
     * 
     * @param toolbar The application toolbar.
     * @param dashboard The application dashboard.
     */
    private SystemController(DashboardToolbar toolbar, Dashboard dashboard){
        // initializing
        this.toolbar = toolbar;
        this.dashboard = dashboard;
    }

    /**
     * Initializer method - initializes the SystemController instance provided that
     * it has not been initialized yet (Singleton Pattern).
     * 
     * @param toolbar The Dashboard Toolbar within the application.
     * @param dashboard The Dashboard within the application.
     */
    public static void init(DashboardToolbar toolbar, Dashboard dashboard){
        // initializing only if instance does not exist
        if(SystemController.instance == null){
            SystemController.instance = new SystemController(toolbar, dashboard);
        }
        else{
            // TODO - what should happen here - throw Exception?? Just doing nothing for now
        }
    }

    /**
     * Returns the System Controller instance. If one has not been initialized,
     * returns null.
     * 
     * @return The SystemController instance.
     */
    public static SystemController getInstance(){
        // returning the instance provided it exists.
        if(instance != null){
            return instance;
        }
        else{
            // TODO - what should happen here - throw exception? just returning null for now.
            return null;
        }
    }

    ////////////////////////
    // CREATING NEW FILES //
    //////////////////////// 

    /**
     * Attempts to crate a new file within the system using the provided name.
     * 
     * @param filename The name of the new file within the system.
     */
    public void createNewFile(String filename) throws Exception{
        // VALIDATING //

        Validator.validateNewFile(filename);

        // VALIDATED //

        // gathering file type
        FileType fileType = FileType.getFileType(filename);

        // LOADING FILE //

        // File is Table
        if(fileType == FileType.TABLE){
            // creating new TableTab
            TableTab tableTab = new TableTab(this.dashboard.getEditor().getEditorTabContainer(), filename, null, null);

            // adding table tab to editor
            this.dashboard.getEditor().getEditorTabContainer().addEditorTab(tableTab);
        }
        // File is Program
        else{
            // creating new program tab
            ProgramTab programTab = new ProgramTab(this.dashboard.getEditor().getEditorTabContainer(), filename, null, fileType);

            // adding program tab to editor
            this.dashboard.getEditor().getEditorTabContainer().addEditorTab(programTab);
        }
    }

    ///////////////////////////////////
    // LOADING FILES INTO THE SYSTEM //
    ///////////////////////////////////

    /**
     * Attempts to load a given number of files into the system.
     * 
     * @param file The File being loaded into the system
     * @param fileType The type(s) of the file being loaded into the system.
     */
    public void loadFile(File file, FileType... fileTypes) throws Exception{
        // VALIDATING //

        Validator.validateNewFile(file);

        // VALIDATED //

        // gathering file type
        FileType fileType = FileType.getFileType(file);

        // LOADING FILE //

        // File is Table
        if(fileType == FileType.TABLE){
            // creating and storing new stored table
            StoredTable storedTable = this.createAndStoreNewStoredTable(file);

            // creating new TableTab with the file and StoredTable
            TableTab tableTab = new TableTab(this.dashboard.getEditor().getEditorTabContainer(), file.getName(), file, storedTable);

            // adding program tab to editor
            this.dashboard.getEditor().getEditorTabContainer().addEditorTab(tableTab);
        }
        // File is Program
        else{
            // creating new program tab
            ProgramTab programTab = new ProgramTab(this.dashboard.getEditor().getEditorTabContainer(), file.getName(), file, fileType);

            // adding program tab to editor
            this.dashboard.getEditor().getEditorTabContainer().addEditorTab(programTab);
        }
    }

    ///////////////////
    // STORED TABLES //
    ///////////////////

    /**
     * Attempts to load a given number of files into the TableStore.
     * 
     * @param file The Files being loaded into the TableStore
     */
    public void loadFileIntoTableStore(File file) throws Exception{
        // VALIDATING //

        Validator.validateNewTableStoreFile(file);

        // VALIDATED //

        // Creating new StoredTable and loading it into the TableStore
        this.createAndStoreNewStoredTable(file);
    }

        /**
     * Creates a new StoredTable using the provided file and adds 
     * it to the application's TableStore.
     * 
     * @param file The file associated with the stored table.
     * @return The StoredTable that was created and added to the TableStore.
     */
    private StoredTable createAndStoreNewStoredTable(File file){
        // creating new StoredTable
        StoredTable storedTable = new StoredTable(this.dashboard.getTableStore(), file);

        // adding new StoredTable into the TableStore
        this.dashboard.getTableStore().addStoredTable(storedTable);

        return storedTable;
    }

    /**
     * Creates a new EditorTab using the content from a stored table.
     * 
     * @param storedTable The StoredTable the EditorTab is being loaded from.
     */
    public void openStoredTableIntoEditor(StoredTable storedTable) throws Exception{
        // VALIDATING //

        Validator.validateOpenStoredTableInEditor(storedTable);

        // VALIDATED //

        // creating new TableTab
        TableTab tableTab = new TableTab(this.dashboard.getEditor().getEditorTabContainer(), storedTable.getFile().getName(), storedTable.getFile(), storedTable);

        // adding TableTab to editor
        this.dashboard.getEditor().getEditorTabContainer().addEditorTab(tableTab);
    }

    /**
     * Removes the open EditorTab associated with the provided StoredTable if
     * it exists.
     * 
     * @param storedTable The StoredTable being removed from the Editor.
     */
    public void removeStoredTableFromEditor(StoredTable storedTable){
        // var to store the EditorTab of the StoredTable
        EditorTab matchedEditorTab = null;

        // iterating through editor tabs to find the StoredTable
        for(EditorTab editorTab : this.dashboard.getEditor().getEditorTabContainer().getEditorTabs()){
            
            // checking if it is a table tab
            if(editorTab.getFileType() == FileType.TABLE){
                // casting
                TableTab tableTab = (TableTab) editorTab;

                // checkiing for stored table
                if(tableTab.getStoredTable() == storedTable){
                    // storing mathced editor tab
                    matchedEditorTab = editorTab;

                    break; /** Can break as there will be only one match so need to keep iterating */
                }
            }
        }

        // removing the EditorTab if a match was found
        if(matchedEditorTab != null){
            this.dashboard.getEditor().getEditorTabContainer().removeEditorTab(matchedEditorTab);
        }
    }

    ///////////////////////
    // SAVING EDITOR TAB //
    ///////////////////////

    /**
     * Saves the EditorTab into a new file.
     * 
     * @param editorTab The EditorTab being saved.
     */
    public void saveEditorTabAs(EditorTab editorTab, File file) throws Exception{
        // VALIDATING //

        Validator.validateRenameFile(editorTab, file.getName());

        // VALIDATED //

        // gathering content to be saved
        String content = editorTab.getCodeArea().getCode();

        // saving content to file
        FileManager.writeContentToFile(file, content);

        // attaching the chosen file
        editorTab.setFile(file);

        // updating tab information
        editorTab.updateAfterSave();

        // HANDLING TABLE TAB CASE //
        if(editorTab.getFileType() == FileType.TABLE){
            // casting to table tab
            TableTab tableTab = (TableTab) editorTab;

            // creating and storing new stored table
            StoredTable storedTable = this.createAndStoreNewStoredTable(file);

            // adding stored table into the table tab
            tableTab.setStoredTable(storedTable);
        }
    }

    /**
     * Saves the EditorTab into it's current file if one exists.
     * 
     * If one does not exist, a FileChooser is displayed that allows
     * the user to select one.
     * 
     * @param EditorTab The EditorTab being saved.
     */
    public void saveEditorTab(EditorTab editorTab) throws Exception{
        // Program Has File //
        if(editorTab.getFile() != null){
            // getting content to save
            String content = editorTab.getCodeArea().getCode();

            // saving content to file
            FileManager.writeContentToFile(editorTab.getFile(), content);

            // updating tab information
            editorTab.updateAfterSave();
        }

        // EditorFile Has No File (open dialog and let the user select one) //
        else{
            // getting the file to save the program to
            File chosenFile = FileManager.getNewSaveFile(this.dashboard.getScene().getWindow(), 
                                                         editorTab.getName(), 
                                                         editorTab.getFileType().getExtensionFilters());

            // making sure file was selected
            if(chosenFile != null){
                // saving file through system controller
                SystemController.getInstance().saveEditorTabAs(editorTab, chosenFile); 
            }
        }
    }

    /////////////////////////
    // RENAMING EDITOR TAB //
    /////////////////////////

    /**
     * Attempts to change the name of the EditorTab to the provided new name.
     * 
     * The renaming process works by creating a new EditorTab instance with the new 
     * filename, File object, graphic and code template and placing the old EditorTab's 
     * content (e.g., code and savestate) inside of thjis
     * 
     * The renaming process works by placing the old EditorTab instance's content, and
     * save state into a new EditorTab instance that has the new name, updated code
     * mirror template and updated graphic.
     * 
     * Renaming will fail if there exists already a file within the system with the same
     * name as the proposed new name.
     * 
     * @param editorTab The EditorTab being renamed.
     * @param newFilename The new name of the EditorTab being renamed.
     * @throws Exception Thrown if the filename could not be renamed because either the
     * new FileType is not supported, or the filename is already in use within the system.
     */
    public void renameEditorTab(EditorTab editorTab, String newFilename) throws Exception{
        // VALIDATING //

        Validator.validateRenameFile(editorTab, newFilename);

        // VALIDATED //

        ///////////////////////////////////////////////////////
        // RECOVERING NEEDED INFORMATION FROM OLD EDITOR TAB //
        ///////////////////////////////////////////////////////

        String code = editorTab.getCodeArea().getCode();
        String textAtLastSave = editorTab.getTextAtLastSave();
        boolean unsavedChanges = editorTab.hasUnsavedChanges();

        ///////////////////////////////////////////////////
        // CREATING THE PROPERTIES OF THE NEW EDITOR TAB //
        ///////////////////////////////////////////////////

        EditorTabContainer editorTabContainer = this.dashboard.getEditor().getEditorTabContainer();
        String newName = newFilename;
        File newFile = null;
        if(editorTab.getFile() != null){
            // gathering new file object
            newFile = FileManager.renameFile(editorTab.getFile(), newName);
        }
        FileType newFileType = FileType.getFileType(newFilename);

        //////////////////////////////////
        // INSTANTIATING NEW EDITOR TAB //
        //////////////////////////////////

        // new instance
        EditorTab newEditorTab = null;

        // insantiating based on the new FileType

        // NEW TYPE IS TABLE //
        if(newFileType == FileType.TABLE){
            // setting up new StoredTable instance
            StoredTable storedTable = null;

            // dealing with StoredTable when old EditorTab was Table as well
            if(editorTab.getFileType() == FileType.TABLE){
                // casting old tab to TableTab (to get StoredTable instance)
                TableTab tableTab = (TableTab) editorTab;

                // gathering old stored table and updating the file it points to
                if(tableTab.getStoredTable() != null){
                    // gathering stored table instance
                    storedTable = tableTab.getStoredTable();
    
                    // updating StoredTable file
                    storedTable.setFile(newFile);
                }
            }

            // initializing new EditorTab as TableTab
            newEditorTab = new TableTab(editorTabContainer,
                                        newName,
                                        newFile,
                                        storedTable);
        }
        // NEW TYPE IS PROGRAM //
        else{
            // initializing new EditorTab as ProgramTab
            newEditorTab = new ProgramTab(editorTabContainer,
                                          newName,
                                          newFile,
                                          newFileType); /** Instantiate ProgramTab with the filetype (as there are multiple program FileTypes) */
        }

        /////////////////////////////////////////////////
        // CONFIGURING NEW EDITOR TAB WITH OLD CONTENT //
        /////////////////////////////////////////////////

        // confuguring new editor tab
        newEditorTab.getCodeArea().setCode(code);
        newEditorTab.setTextAtLastSave(textAtLastSave);
        newEditorTab.setUnsavedChanges(unsavedChanges);

        ///////////////////////////////////////
        // PLACING NEW EDITORTAB INTO EDITOR // 
        ///////////////////////////////////////

        // replacing old editor tab with new one
        this.dashboard.getEditor().getEditorTabContainer().replaceEditorTab(editorTab, newEditorTab);

        ////////////////////////////////////////////////////////////////
        // HANDLING CASE WHERE OLD TYPE WAS TABLE BUT NEW TYPE IS NOT //
        ////////////////////////////////////////////////////////////////

        /**
         * If old EditorTab was TableTab, but new one isnt, need to remove the
         * StoredTable associated with the TableTab (if it exists).
         */

        if(editorTab.getFileType() == FileType.TABLE && newFileType != FileType.TABLE){
            TableTab tableTab = (TableTab) editorTab;
            StoredTable storedTable = null;

            if(tableTab.getStoredTable() != null){
                // getting the old stored table
                storedTable = tableTab.getStoredTable();

                // removing old stored table from dashboard
                this.dashboard.getTableStore().removeStoredTable(storedTable);
            }
        }
    }

    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////

    public DashboardToolbar getToolbar(){
        return this.toolbar;
    }

    public Dashboard getDashboard(){
        return this.dashboard;
    }
}