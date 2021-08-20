package Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import View.App.Dashboard;
import View.App.Toolbar;
import View.Editor.Program;

/**
 * Uses static methods to manage the system.
 */
public class SystemController {

    // constants
    private static final String interpreter = "csvql";
    
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

    /////////////////////
    // ADDING PROGRAMS //
    /////////////////////

    /**
     * Adds a new program into the editor with the provided name.
     * 
     * @param name The name of the new program
     */
    public static void addProgram(String name) throws Exception{
        SystemController.dashboard.getEditor().addProgram(name);
    }

    /**
     * Loads a program into the system from within an existing file.
     * 
     * @param file The file the program is being loaded from.
     */
    public static void addProgram(File file) throws Exception{
        SystemController.dashboard.getEditor().addProgram(file);
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
    public static void runProgram(Program program) throws Exception{
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
        for(File file : SystemController.dashboard.getFileStore().getFiles()){
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
    public static String run(String command) throws IOException
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
