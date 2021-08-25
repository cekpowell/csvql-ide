package Controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


import Model.FileType;
import View.App.Dashboard;
import View.App.DashboardToolbar;
import View.Editor.EditorTab;
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

        // handling loading of file based on it's filetype
        switch(fileType){
            case PROGRAM:
                // creating new program tab
                ProgramTab programTab = new ProgramTab(this.dashboard.getEditor().getEditorTabContainer(), filename, null);

                // adding program tab to editor
                this.dashboard.getEditor().getEditorTabContainer().addEditorTab(programTab);

                break;
            case TABLE:
                // creating new TableTab
                TableTab tableTab = new TableTab(this.dashboard.getEditor().getEditorTabContainer(), filename, null, null);

                // adding table tab to editor
                this.dashboard.getEditor().getEditorTabContainer().addEditorTab(tableTab);

                break;
            case TERMINAL:
                break;
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

        // handling loading of file based on it's filetype
        switch(fileType){
            case PROGRAM:
                // creating new program tab
                ProgramTab programTab = new ProgramTab(this.dashboard.getEditor().getEditorTabContainer(), file);

                // adding program tab to editor
                this.dashboard.getEditor().getEditorTabContainer().addEditorTab(programTab);

                break;
                
            case TABLE:
                // creating and storing new stored table
                StoredTable storedTable = this.createAndStoreNewStoredTable(file);

                // creating new TableTab with the file and StoredTable
                TableTab tableTab = new TableTab(this.dashboard.getEditor().getEditorTabContainer(), file, storedTable);

                // adding program tab to editor
                this.dashboard.getEditor().getEditorTabContainer().addEditorTab(tableTab);

                break;

            case TERMINAL:
                break;
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
        TableTab tableTab = new TableTab(this.dashboard.getEditor().getEditorTabContainer(), storedTable.getFile(), storedTable);

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
    public void saveEditorTabAs(EditorTab editorTab) throws Exception{
        // getting the file to save the program to
        File chosenFile = FileManager.getNewSaveFile(this.dashboard.getScene().getWindow(), editorTab.getName(), editorTab.getFileType().getExtensionFilters());

        // making sure file was selected
        if(chosenFile != null){
            // VALIDATING //

            Validator.validateRenameFile(editorTab, chosenFile.getName());

            // VALIDATED //

            // gathering content to be saved
            String content = editorTab.getCodeArea().getCode();

            // saving content to file
            FileManager.writeContentToFile(chosenFile, content);

            // attaching the chosen file
            editorTab.setFile(chosenFile);

            // updating tab information
            editorTab.updateAfterSave();

            // HANDLING TABLE TAB CASE //
            if(editorTab.getFileType() == FileType.TABLE){
                // casting to table tab
                TableTab tableTab = (TableTab) editorTab;

                // creating and storing new stored table
                StoredTable storedTable = this.createAndStoreNewStoredTable(chosenFile);

                // adding stored table into the table tab
                tableTab.setStoredTable(storedTable);
            }
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

        // EditorFile Has No File //
        else{
            // running save as protocol
            this.saveEditorTabAs(editorTab);
        }
    }

    /////////////////////////
    // RENAMING EDITOR TAB //
    /////////////////////////

    /**
     * Attempts to change the name of the provided EditorTab to the
     * provided new name.
     * 
     * @param editorTab The EditorTab beign renamed.
     * @param newFilename The new name for the editor tab.
     * @throws Exception If the EditorTab could not be renanmed (e.g., the name
     * is already in use).
     */
    public void renameEditorTab(EditorTab editorTab, String newFilename) throws Exception{
        // VALIDATING //

        Validator.validateRenameFile(editorTab, newFilename);

        // VALIDATED //

        // EditorTab has Associated File 
        if(editorTab.getFile() != null){
            // renaming associated File object
            Path source = Paths.get(editorTab.getFile().getAbsolutePath());
            Path target = Files.move(source, source.resolveSibling(newFilename));

            // gathering new file object
            File newFile = target.toFile();

            // updating EditorTab information
            editorTab.setFile(newFile);

            // HANDLING TABLE TAB CASE //
            if(editorTab.getFileType() == FileType.TABLE){
                // casting to table tab
                TableTab tableTab = (TableTab) editorTab;

                /**
                 * Renaming the TableTab's StoredTable if it exists.
                 * (which it should due to the if statements - if the 
                 * EditorTab has a file, then the TableTab must have a
                 * StoredTable).
                 */
                if(tableTab.getStoredTable() != null){
                    tableTab.getStoredTable().setFile(newFile);
                }
            }
        }
        // EditorTab has no file - just need to update it's display information
        else{
            // updating tab information
            editorTab.setName(newFilename);
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