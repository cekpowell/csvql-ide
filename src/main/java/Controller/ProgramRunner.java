package Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import Model.FileType;
import View.Editor.EditorTab;
import View.Editor.ProgramTab;
import View.TableStore.StoredTable;

/**
 * Manages the running of programs loaded in the systme Editor.
 */
public class ProgramRunner {

    // constants
    private static final String TMP_DIR = "tmp";
    
    //////////////////
    // INITIALIZING //
    //////////////////

    /**
     * Class constructor.
     * 
     * Private as cannot be instantiated.
     */
    private ProgramRunner(){}

    /////////////////////////
    // RUNNING PROGRAM TAB //
    /////////////////////////

    /**
     * Runs the provided program and passes the result onto the Terminal.
     * 
     * @param programTab The ProgramTab being run.
     * 
     * @throws Exception Thrown if the program could not be run.
     */
    public static  void runProgramTab(ProgramTab programTab) throws Exception{
        // VALIDATING //

        Validator.validateRunProgramTab(programTab);

        // VALIDATED //

        ////////////////////////////
        // CREATING TMP DIRECTORY //
        ////////////////////////////

        File tmpDir = ProgramRunner.createTmpDir();

        ///////////////////////
        // EXECUTING PROGRAM //
        ///////////////////////

        // gathering type of program being run
        FileType programType = FileType.getFileType(programTab.getFile());

        // executing program based on it's type
        try{
            // string to hold program output
            String programOutput = "";


            // EXECUTING PROGRAM BASED ON TYPE //

            if(programType == FileType.PROGRAM_CSVQL){
                programOutput = ProgramRunner.getCsvqlProgramOutput(tmpDir, programTab.getName());
            }
            else if(programType == FileType.PROGRAM_PYTHON){
                programOutput = ProgramRunner.getPythonProgramOutput(tmpDir, programTab.getName());
            }
            else if(programType == FileType.PROGRAM_JAVA){
                programOutput = ProgramRunner.getJavaProgramOutput(tmpDir, programTab.getName());
            }

            ///////////////////////
            // DISPLAYING OUTPUT //
            ///////////////////////

            if(programOutput != ""){
                 // displaying output into terminal
                SystemController.getInstance().getDashboard().getTerminal().displayProgramOutput(programOutput);
            }
        }
        catch(Exception e){

            /**
             * Unable to execute program, need to delete temp dir
             * and throw exception.
             */

            // deleting temp dir
            try{
                // deleting tmp directory if the execution failed
                FileUtils.deleteDirectory(tmpDir);
            }
            catch(Exception ex){
                // temp dir could not be deleted, need to throw additional exception
                throw new Exception("Unable to exeucte file '" + programTab.getName() + "'.\n" + 
                                    "Cause : \n\t" + e.toString() + "\n" + 
                                    "Unable to delete temp directory.\n" + 
                                    "Cause : \n\t" + ex.toString());
            }

            // throwing exception for program execution
            throw new Exception("Unable to exeucte file '" + programTab.getName() + "'.\n" + 
                                "Cause : \n\t" + e.toString());
        }
    
        ////////////////////////////
        // DELETING TMP DIRECTORY //
        ////////////////////////////

        try{
            // deleting tmp directory if the execution failed
            FileUtils.deleteDirectory(tmpDir);
        }
        catch(Exception e){
            // unable to delete tepm dir - need to throw exception
            throw new Exception("Unable to delete temp directory after program execution.\n" + 
                                "Cause : \n\t" + e.toString());
        }
    }

    ///////////////////////////
    // RUNNING CSVQL PROGRAM //
    ///////////////////////////

    /**
     * Gathers the output of executing a provided CSVQL program.
     * 
     * @param tmpDir The temporary directory containing the system files in use,
     * @param programName The name of the CSVQL program to be executed.
     */
    private static String getCsvqlProgramOutput(File tmpDir, String programName) throws Exception{

        // string values of interpreter dir and executable
        String interpreterDir = "interpreter";
        String interpreterExe = "csvql-no-colour";

        // COPYING INTERPRETER INTO TMP DIRECTORY //

        try{
            File interpreterFile = FileUtils.toFile(SystemController.class.getClassLoader().getResource(interpreterDir + File.separator + interpreterExe));

            Files.copy(interpreterFile.toPath(),                                                  // SOURCE FILE
                       (new File(TMP_DIR + File.separator + interpreterFile.getName())).toPath(), // TARGET DESTINATION
                       StandardCopyOption.REPLACE_EXISTING);                                      // REPLACE PROTOCOL
        }
        catch(Exception e){
            throw new Exception("Unable to cache CSVQL interpreter in tmp directory.\n" + 
                                "Cause : \n\t" + e.toString());
        }

        // CREATING EXECUTION COMMANDS //
    
        String[] commands = {"bash", "-c",                                  // CONFIGURING BASH MODE
                            "chmod +x " + interpreterExe +                  // MAKING CSVQL INTEREPRETER EXECUTABLE
                            " && ./" + interpreterExe + " " + programName}; // EXECUTING CSVQL INTERPRETER AND PROGRAM

        // EXECUTING COMMANDS //
            
        return ProgramRunner.runProcess(commands);
    }

    ////////////////////////////
    // RUNNING PYTHON PROGRAM //
    ////////////////////////////

    /**
     * Gathers the output of executing a provided Python program.
     * 
     * @param tmpDir The temporary directory containing the system files in use,
     * @param programName The name of the CSVQL program to be executed.
     * @return 
     */
    private static String getPythonProgramOutput(File tmpDir, String programName) throws Exception{

        String interpreterExe = "python";

        // CREATING EXECUTION COMMANDS //
    
        String[] commands = {interpreterExe, programName};

        // EXECUTING COMMANDS //
            
        return ProgramRunner.runProcess(commands);
    }

    //////////////////////////
    // RUNNING JAVA PROGRAM //
    //////////////////////////

    private static String getJavaProgramOutput(File tmpDir, String programName) throws Exception{

        String compilerExe = "javac";

        String executorExe = "java";

        // CREATING COMPILE COMMANDS //

        String[] compileCommands = {compilerExe, programName};

        // EXECUTING COMPILE COMMANDS //
            
        String compileOutput = ProgramRunner.runProcess(compileCommands);

        // Compilation successful - need to run code
        if(compileOutput == ""){
            // CREATING EXECUTING COMMANDS //

            String[] executionCommands = {executorExe, programName.split("\\.")[0]}; 

            // EXECUTING EXECUTING COMMANDS //
                
            return ProgramRunner.runProcess(executionCommands);
        }
        // compilation failed - need to return the compilation output (the error)
        else{
            return compileOutput;
        }

    }

    ////////////////////
    // HELPER METHODS //
    ////////////////////

    /**
     * Creates a tmp directory that contains all of the files open in the
     * editor and table store (all files needed to run a program).
     * 
     * The method places all files in the editor and table store into the
     * returned destination (technically only the program being run is needed
     * but copying all is more general).
     */
    private static File createTmpDir() throws Exception{
        // MAKING LIST OF NEEDED FILES //

        // list to store needed files
        ArrayList<File> neededFiles = new ArrayList<File>();

        // Program files
        for(EditorTab editorTab : SystemController.getInstance().getDashboard().getEditor().getEditorTabContainer().getEditorTabs()){
            if(editorTab.getFile() != null){
                neededFiles.add(editorTab.getFile());
            }
        }

        // Stored Tables
        for(StoredTable storedTable : SystemController.getInstance().getDashboard().getTableStore().getStoredTables()){
            // adding the stored table if it is not already added
            if(!neededFiles.contains(storedTable.getFile())){
                neededFiles.add(storedTable.getFile());
            }
        }

        // WRITING NEEDED FILES INTO TEMP DIRECTORY //

        // making tmp directory
        File tmpDir = new File(TMP_DIR);
        try{
            if(!tmpDir.exists()){
                tmpDir.mkdir();
            }
        }
        catch(Exception e){
            // throwing exception if the temp directory could not be made
            throw new Exception("Unable to create temp directory for program execution!\n" + 
                                "Cause : \n\t" + e.toString());
        }

        // copying each needed file into the tmp directory
        try{
            for(File file : neededFiles) {
                Files.copy(file.toPath(),                                                  // SOURCE FILE
                           (new File(TMP_DIR + File.separator + file.getName())).toPath(), // TARGET DESTINATION FILE
                           StandardCopyOption.REPLACE_EXISTING);                           // REPLACE PROTOCOL
            }
        }
        catch(Exception e){

            /**
             * Unable to copy files into tmp dir, need to delete 
             * temp dir and throw exception.
             */

            try{
                // deleting tmp directory
                FileUtils.deleteDirectory(tmpDir);
            }
            catch(Exception ex){
                // temp dir could not be deleted - need to throw additional exception
                throw new Exception("Unable to cache needed files in temp directory.\n" +
                                    "Cause : \n\t" + e.toString() + "\n" + 
                                    "Unable to delete temp directory after failure.\n" + 
                                    "Cause : \n\t" + ex.toString());
            }

            // throwing exception for files not being copied
            throw new Exception("Unable to cache needed files in temp directory.\n" + 
                                "Cause : \n\t" + e.toString());
        }

        // returning directory
        return tmpDir;
    }

    /**
     * Runs the provided commands in a process and returns the output of 
     * this process.
     * 
     * @param commands The commands being run in the process.
     * @return The output of the command being run in the process.
     */
    private static String runProcess(String[] commands) throws Exception{

        // process builder to run the commandS
        ProcessBuilder pb = new ProcessBuilder(commands).redirectErrorStream(true); // redirect error stream to avoid deadlock
        pb.directory(new File(ProgramRunner.TMP_DIR + File.separator));

        try{
            // starting the process
            Process p = pb.start();

            // gathering the process output
            String output = ProgramRunner.readProcessOutput(p);    
            
            // returning output
            return output;
        }
        catch(Exception e){
            throw new Exception("Unable to execute commands in process.\n" + 
                                "Commands : \n\t" + commands.toString() + "\n" + 
                                "Cause : \n\t" + e.toString());
        }
    }

    /**
     * Reads the response from the command. Please note that this works only
     * if the process returns immediately.
     * 
     * @param p The process for which the output is being read.
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

        try{
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
        catch(Exception e){
            throw new Exception("Unable to read process output.\n" + 
                                "Cause : \n\t" + e.toString());
        }
    }
}
