package Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import View.App.Dashboard;
import View.App.NewProgramForm;
import View.App.Toolbar;
import View.Editor.EditorFile;
import View.Editor.Table;
import View.Tools.ErrorAlert;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * Uses static methods to manage the system.
 */
public class SystemController {

    // constants
    private static final String interpreter = "csvql-no-colour";
    private static final String cqlExtension = "cql";
    
    // local variables
    private static Toolbar toolbar;
    private static Dashboard dashboard;

    /**
     * Singleton class - no construction possible.
     */
    private SystemController(){
    }

    ////////////////////////////
    // CONFIGURING CONTROLLER //
    ////////////////////////////

    /**
     * Sets the Toolbar for the system controller.
     * 
     * @param toolbar The Toolbar for the system controller.
     */
    public static void setToolbar(Toolbar toolbar){
        SystemController.toolbar = toolbar;
    }

    /**
     * Sets the Dashboord for the system contoller.
     * 
     * @param dashboard The Dashboard for the system controller.
     */
    public static void setDashboard(Dashboard dashboard){
        SystemController.dashboard = dashboard;
    }

    ////////////////////////
    // CREATING NEW FILES //
    ////////////////////////

    /**
     * Displays a pop-up that allows the user to create a new CQL program or table.
     */
    public static void createNewFile(){
        // creating input form
        NewProgramForm newProgramForm = new NewProgramForm();
        newProgramForm.initOwner(SystemController.dashboard.getScene().getWindow());
        newProgramForm.show();
    }

    ///////////////////
    // OPENING FILES //
    ///////////////////

    /**
     * Displays a pop-up that allows the user to load CQL programs or tables into the
     * system.
     */
    public static void openFile(){
        // configuring the file chooser to load a new file into the system
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CSVQL Program", "*.cql"),
                                                 new ExtensionFilter("Table", "*.txt", "*.csv", "*.tsv"));

        // showing the open dialog
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(SystemController.dashboard.getScene().getWindow());

        // checking if files were opened
        if (selectedFiles != null) {
            // iterating through selected files
            for(File file : selectedFiles){
                try{
                    // CQL Program opened
                    if(FilenameUtils.getExtension(file.getName()).equals(SystemController.cqlExtension)){
                        // adding the program to the system
                        SystemController.loadProgram(file);
                    }

                    // CSV, TSV or Textfile opened
                    else{
                        // adding the table into the system
                        SystemController.loadTable(file);
                    }
                }
                // handling error
                catch(Exception ex){
                    ErrorAlert.showErrorAlert(SystemController.dashboard.getScene().getWindow(), ex);
                }
            }
        }
    }

    /////////////////////
    // ADDING PROGRAMS //
    /////////////////////

    /**
     * Adds a new program into the editor with the provided name.
     * 
     * @param name The name of the new program
     */
    public static void createNewProgram(String name) throws Exception{
        // adding the program into the editor
        SystemController.dashboard.getEditor().createNewProgram(name);
    }

    /**
     * Loads a program into the system from within an existing file.
     * 
     * @param file The File associated with the CQL program.
     */
    public static void loadProgram(File file) throws Exception{
        // adding the program into the editor
        SystemController.dashboard.getEditor().loadProgram(file);
    }

    ///////////////////
    // ADDING TABLES //
    ///////////////////

    /**
     * Adds a new table into the editor with the provided name.
     * 
     * @param name The name of the new table.
     */
    public static void createNewTable(String name) throws Exception{
        // adding the program into the editor
        SystemController.dashboard.getEditor().createNewTable(name);
    }

    /**
     * Loads the provided table into the system using it's File.
     * 
     * @param file The File associated with the table.
     */
    public static void loadTable(File file) throws Exception{
        // adding the table into the editor
        SystemController.dashboard.getEditor().loadTable(file);

        // adding the table into the tablestore
        SystemController.dashboard.getTableStore().addTable(file);
    }

    /**
     * Adds the provided table to the system table store.
     * 
     * @param table The table being added into the table store.
     * @throws Exception If the the table could not be added.
     */
    public static void addTableToStore(Table table) throws Exception{
        // adding the table into the tablestore
        SystemController.dashboard.getTableStore().addTable(table.getFile());
    }

    /**
     * Adds the provided table into the Editor.
     * 
     * @param file The table to be added to the editor.
     * @throws Exception If the the table could not be added.
     */
    public static void addTableToEditor(File file) throws Exception{
        // adding the table into the editor
        SystemController.dashboard.getEditor().loadTable(file);
    }

    /////////////////////
    // REMOVING TABLES //
    /////////////////////

    public static void removeTableFromEditor(File file){
        // removing the table from the editor
        SystemController.dashboard.getEditor().removeTable(file);
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
    public static void runProgram(EditorFile program) throws Exception{
        // GATHERINNG NEEDED FILES //

        // list to store needed files
        ArrayList<File> neededFiles = new ArrayList<File>();

        // interpreter file
        
        neededFiles.add(FileUtils.toFile(SystemController.class.getClassLoader().getResource(SystemController.interpreter)));

        // program file
        if(program.getFile() != null){
            neededFiles.add(program.getFile());
        }
        else{
            throw new Exception("The program has no saved data - it cannot be run.");
        }

        // CSV files
        for(File file : SystemController.dashboard.getTableStore().getFiles()){
            neededFiles.add(file);
        }

        // WRITING NEEDED FILES INTO TEMP DIRECTORY //

        // making temp directory
        File tmpDir = new File("tmp");
        if(!tmpDir.exists()){
            tmpDir.mkdir();
        }

        // iteratig through needed files
        for(File file : neededFiles) {
            // copying each file into the local directory (resources)
            Files.copy(file.toPath(), 
                       (new File("tmp/" + file.getName())).toPath(), 
                       StandardCopyOption.REPLACE_EXISTING);
        }

        // RUNNING INTERPRETER //

        // try block needed so that can still delete the tmp dir if it fails
        try{
            // gathering output
            String output = SystemController.getProgramOutput(program.getName());

            // displaying output into terminal
            SystemController.dashboard.getTerminal().displayProgramOutput(output);
        }
        catch(Exception e){
            throw new Exception();
        }

        // removing temporary directory
        FileUtils.deleteDirectory(tmpDir);
    }

    /**
     * @param command the command to run
     * @return the output of the command
     * @throws IOException if an I/O error occurs
     */
    public static String runCommand(String command) throws IOException
    {
        ProcessBuilder pb = new ProcessBuilder(command).redirectErrorStream(true);
        Process process = pb.start();
        StringBuilder result = new StringBuilder(80);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream())))
        {
            while (true)
            {
                String line = in.readLine();
                if (line == null)
                    break;
                result.append(line).append("\n");
            }
        }
        return result.toString();
    }

    /**
     * Gathers the output of executing a provided CSVQL program.
     * 
     * @param programName The name of the CSVQL program to be executed.
     */
    private static String getProgramOutput(String programName) throws Exception{

        // command to be run
        String[] command = {"bash", "-c", 
                            "chmod +x " + SystemController.interpreter +  
                            " && ./" + SystemController.interpreter + " " + programName};

        // process builder to run the command
        ProcessBuilder pb = new ProcessBuilder(command).redirectErrorStream(true);
        pb.directory(new File("tmp/"));

        // starting the process
        Process p = pb.start();

        // gathering the process output
        String response = SystemController.readProcessOutput(p);
            
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
    private static String readProcessOutput(Process p) throws Exception{
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
}
