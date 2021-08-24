package Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javafx.stage.FileChooser;

import Model.FileType;
import View.App.Dashboard;
import View.App.DashboardToolbar;
import View.Editor.EditorTab;
import View.Editor.ProgramTab;
import View.Editor.TableTab;
import View.Forms.NewFileForm;
import View.Forms.RenameFileForm;
import View.TableStore.StoredTable;
import View.Tools.PopUpWindow;

/**
 * Serves as the main controller for the application. Adopts the 
 * singleton pattern and uses instance methods to control the system's
 * data flow.
 */
public class SystemController {

    // constants
    private static final String interpreterExe = "csvql-no-colour";
    private static final String cqlExtension = "cql";
    
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

    //////////////////////////
    // CREATING EDITOR TABS //
    //////////////////////////

    /**
     * Displays a pop-up that allows the user to create a new CQL program or table.
     */
    public void createNewFile(){
        // creating input form
        NewFileForm newProgramForm = new NewFileForm();  
        newProgramForm.initOwner(this.dashboard.getScene().getWindow());

        // displaying input form
        newProgramForm.show();
    }

    /**
     * Displays a pop-up that allows the user to load files into the system
     * editor.
     */
    public void openFile(){
        // configuring the file chooser to load a new file into the system
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().addAll(FileType.PROGRAM.getExtensionFilters());
        fileChooser.getExtensionFilters().addAll(FileType.TABLE.getExtensionFilters());

        // showing the open dialog
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(this.dashboard.getScene().getWindow());

        // checking if files were opened
        if (selectedFiles != null) {
            // iterating through selected files
            for(File file : selectedFiles){
                try{
                    // CQL Program opened
                    if(FilenameUtils.getExtension(file.getName()).equals(SystemController.cqlExtension)){
                        // adding the program to the system
                        this.createNewEditorTab(file, FileType.PROGRAM); 
                    }

                    // CSV, TSV or Textfile opened
                    else{
                        // adding the table into the system
                        this.createNewEditorTab(file, FileType.TABLE);
                    }
                }
                // handling error
                catch(Exception ex){
                    PopUpWindow.showErrorWindow(this.dashboard.getScene().getWindow(), ex);
                }
            }
        }
    }

    /**
     * Creates a new EditorTab instance using the provided name and
     * type.
     * 
     * @param name The name of the new EditorTab
     * @param type
     */
    public void createNewEditorTab(String name, FileType type) throws Exception{
        // checks
        if(this.fileNameIsInUse(name)){
            throw new Exception("This filename is already in use in the system!");
        }

        // Checks Complete //

        // editortab object
        EditorTab editorTab;

        // New Tab is Program
        if(type == FileType.PROGRAM){
            // creating new program tab
            editorTab = new ProgramTab(this.dashboard.getEditor().getEditorTabContainer(), name);
        }
        // New Tab is Table
        else{
            // creating new table tab
            editorTab = new TableTab(this.dashboard.getEditor().getEditorTabContainer(), name, null); // null as no stored table yet
        }

        // adding the editor tab into the system
        this.dashboard.getEditor().getEditorTabContainer().addEditorTab(editorTab);
    }

    /**
     * Creates a new EditorTab instance using the provided file and
     * type
     * 
     * @param name The file that will be associated with the editor tab.
     * @param type The Type of editor tab being createed.
     */
    public void createNewEditorTab(File file, FileType type) throws Exception{
        // checks
        if(this.fileNameIsInUse(file.getName())){
            throw new Exception("This filename is already in use in the system!");
        }

        // Checks Complete

        EditorTab editorTab;

        // New Tab is Program
        if(type == FileType.PROGRAM){
            // creating new ProgramTab
            editorTab = new ProgramTab(this.dashboard.getEditor().getEditorTabContainer(), file);
        }
        // New Tab is Table (need to create StoredTable object for the TableStore)
        else{
            // creating and storing new stored table
            StoredTable storedTable = this.createAndStoreNewStoredTable(file);

            // creating new TableTab with the file and StoredTable
            editorTab = new TableTab(this.dashboard.getEditor().getEditorTabContainer(), file, storedTable);
        }

        // adding the generated EditorTab into the system
        this.dashboard.getEditor().getEditorTabContainer().addEditorTab(editorTab);
    }

    /**
     * Creates a new EditorTab using the content from a stored table.
     * 
     * @param storedTable The StoredTable the EditorTab is being loaded from.
     */
    public void createNewEditorTab(StoredTable storedTable) throws Exception{
        // checks
        if(this.fileNameIsInUseInEditor(storedTable.getName())){
            throw new Exception("There is already a file with this name in the editor!");
        }
        
        // Checks Complete //

        // creating new TableTab
        TableTab tableTab = new TableTab(this.dashboard.getEditor().getEditorTabContainer(), storedTable.getFile(), storedTable);

        // adding TableTab to editor
        this.dashboard.getEditor().getEditorTabContainer().addEditorTab(tableTab);
    }

    ///////////////////
    // STORED TABLES //
    ///////////////////

    /**
     * Displays a pop-up that allows the user to load tables into the table store.
     * Tables that are loaded using this method are not loaded directly into the editor.
     */
    public void openFileIntoTableStore(){
        // configuring the file chooser to load a new file into the system
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Table");
        fileChooser.getExtensionFilters().addAll(FileType.TABLE.getExtensionFilters());

        // showing the open dialog
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(this.dashboard.getScene().getWindow());

        // checking if files were opened
        if (selectedFiles != null) {
            // iterating through selected files
            for(File file : selectedFiles){
                try{
                    // checking file is not present in system already
                    if(this.fileNameIsInUse(file.getName())){
                        throw new Exception("The filename '" + file.getName() + "' is already in use in the system!");
                    }

                    // creating and storing new stored table
                    this.createAndStoreNewStoredTable(file);
                }
                // handling error
                catch(Exception ex){
                    PopUpWindow.showErrorWindow(this.dashboard.getScene().getWindow(), ex);
                }
            }
        }
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

    /**
     * Creates a new StoredTable instance and adds it to the application
     * TableStore.
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
            // checking savefile name is valid
            if(!this.newFileNameIsValid(editorTab, chosenFile.getName())){
                throw new Exception("This filename is already in use in the system!");
            }

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
     * Renames the provided EditorTab. Displays a pop-up that allows the user to
     * enter in the new name for the associated file.
     * 
     * @param editorTab The editorTab being renamed.
     */
    public void renameEditorTab(EditorTab editorTab){
        // configuring rename window
        RenameFileForm renameFileForm = new RenameFileForm(editorTab);
        renameFileForm.initOwner(this.dashboard.getScene().getWindow());

        // displaying rename window
        renameFileForm.show();
    }

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
        // checking name is suitable
        if(!this.newFileNameIsValid(editorTab, newFilename)){
            throw new Exception("This name is already in use in the system!");
        }

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
        // EditorTab has no file
        else{
            // updating tab information
            editorTab.setName(newFilename);
        }
    }

    //////////////////////
    // RUNNING PROGRAMS //
    //////////////////////

    /**
     * Runs the provided program and passes the result onto the Terminal.
     * 
     * @param program The program being run.
     * 
     * @throws Exception Thrown if the program could not be run.
     */
    public void runProgram(ProgramTab program) throws Exception{
        // GATHERINNG NEEDED FILES //

        // list to store needed files
        ArrayList<File> neededFiles = new ArrayList<File>();

        // interpreter file
        neededFiles.add(FileUtils.toFile(SystemController.class.getClassLoader().getResource("interpreter/" + SystemController.interpreterExe)));

        // program file
        if(program.getFile() != null){
            neededFiles.add(program.getFile());
        }
        else{
            throw new Exception("The program has no saved data - it cannot be run.");
        }

        // Stored Tables
        for(StoredTable storedTable : this.dashboard.getTableStore().getStoredTables()){
            neededFiles.add(storedTable.getFile());
        }

        // WRITING NEEDED FILES INTO TEMP DIRECTORY //

        // making tmp directory
        File tmpDir = new File("tmp");
        if(!tmpDir.exists()){
            tmpDir.mkdir();
        }

        // iterating through list
        for(File file : neededFiles) {
            // copying each file into the tmp directory
            Files.copy(file.toPath(), 
                       (new File("tmp/" + file.getName())).toPath(), 
                       StandardCopyOption.REPLACE_EXISTING);
        }

        // RUNNING INTERPRETER //

        /**
         * Try block neeeded so that can still delete the tmp directory
         * if the interpreter execution fails.
         */
        try{
            // gathering output
            String output = this.getProgramOutput(program.getName());

            // displaying output into terminal
            this.dashboard.getTerminal().displayProgramOutput(output);
        }
        catch(Exception e){
            throw new Exception();
        }

        // removing temporary directory
        FileUtils.deleteDirectory(tmpDir);
    }

    /**
     * Gathers the output of executing a provided CSVQL program.
     * 
     * @param programName The name of the CSVQL program to be executed.
     */
    private String getProgramOutput(String programName) throws Exception{

        // command to be run
        String[] command = {"bash", "-c", 
                            "chmod +x " + SystemController.interpreterExe +  
                            " && ./" + SystemController.interpreterExe + " " + programName};

        // process builder to run the command
        ProcessBuilder pb = new ProcessBuilder(command).redirectErrorStream(true);
        pb.directory(new File("tmp/"));

        // starting the process
        Process p = pb.start();

        // gathering the process output
        String response = this.readProcessOutput(p);
            
        return response;
    }

    /**
     * Reads the response from the command. Please note that this works only
     * if the process returns immediately.
     * 
     * @param p The process for whiich the output is being read.
     * 
     * @return The output from the process.
     * 
     * @throws Exception If the process output could not be gathered.
     */
    private String readProcessOutput(Process p) throws Exception{
        // string to hold response
        ArrayList<String> responseLines = new ArrayList<String>();

        // reader for response
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

        // iterating through response
        String line;
        while ((line = reader.readLine()) != null) {
            responseLines.add(line);
        }
        reader.close();

        // concatinating response
        String response = String.join("\n", responseLines);

        // returning the response
        return response;
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
    private boolean fileNameIsInUse(String filename){
        return (this.fileNameIsInUseInEditor(filename) || this.fileNameIsInUseInTableStore(filename));
    }

    /**
     * Determines if there are any tabs open in the Editor that have the 
     * provided filename.
     * 
     * @param filename The filename being checked for.
     * @return True if at least one tab has the filename, false otherwise.
     */
    private boolean fileNameIsInUseInEditor(String filename){
        // iterating through editor tabs
        for(EditorTab editorTab : this.dashboard.getEditor().getEditorTabContainer().getEditorTabs()){
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
    private boolean fileNameIsInUseInTableStore(String filename){
        // iterating through stored tables
        for(StoredTable storedTable : this.dashboard.getTableStore().getStoredTables()){
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
    private boolean newFileNameIsValid(EditorTab editortab, String newFilename){
        // Editor Tabs
        for(EditorTab eTab : this.dashboard.getEditor().getEditorTabContainer().getEditorTabs()){
            // checking if tab is a different tab but with the same name
            if(eTab != editortab && eTab.getName().equals(newFilename)){
                return false;
            }
        }

        // Stored Tables
        for(StoredTable storedTable : this.dashboard.getTableStore().getStoredTables()){
            // Type is Program
            if(editortab.getFileType() == FileType.PROGRAM){
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