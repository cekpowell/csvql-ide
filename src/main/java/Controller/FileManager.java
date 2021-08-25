package Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * Convenience class to manage acces to and from files within
 * the application. 
 * 
 * Contains static methods that allow for the reading of and writing to
 * files.
 */
public class FileManager {
    
    //////////////////////////
    // READING FILE CONTENT //
    //////////////////////////

    /**
     * Attempts to read the content from the provided file.
     * 
     * @param file The File where the content is being read from.
     * @return The content from the file as a String.
     * 
     * @throws Exception Thrown if the file's content could not be 
     * read.
     */
    public static String getContentFromFile(File file) throws Exception{
        try{
            // setting up file reader 
            BufferedReader reader = new BufferedReader(new FileReader(file));

            // iterating through the file
            ArrayList<String> content = new ArrayList<String>();
            while(reader.ready()){
                // getting next line
                String line = reader.readLine();

                // buildiing the content
                content.add(line);
            }

            // closiing the reader
            reader.close();

            // returning the content seperated into lines
            return String.join("\n", content);
        }
        catch(Exception e){
            throw new Exception("Unable to read content from file '" + file.getName() + "'.\n" + 
                                "Cause : \n\t" + e.toString());
        }
    }

    //////////////////////////
    // WRITING FILE CONTENT //
    //////////////////////////

    /**
     * Attempts to write the provided content into the provided file.
     * 
     * @param file The File where the content is being written to.
     * @param content The content being written into the file.
     * 
     * @throws Exception Thrown if the content could not be written into the file.
     */
    public static void writeContentToFile(File file, String content) throws Exception{
        try{
            // writing save content to file
            OutputStream out = new FileOutputStream(file);
            out.write(content.getBytes());
            out.close();
        }
        catch(Exception e){
            throw new Exception("Unable to write content to file '" + file.getName() + "'.\n" + 
                                "Cause : \n\t" + e.toString());
        }
    }

    /**
     * Attempts to write the provided content into a new file. A FileChooser is displayed
     * that allows the user to select where the new file will be stored.
     * 
     * @param content The content being written into a file.
     * @param window The window the FileChooser dialog will be displayed onto.
     * @param initialFileName The initial filename for the new file.
     * @param extensionFilters The extension filters for the new file.
     * @throws Exception Thrown if the content could not be written into the new file.
     */
    public static void writeContentToNewFile(String content, Window window, String initialFileName, ExtensionFilter[] extensionFilters) throws Exception{
        // gathering file
        File chosenFile = FileManager.getNewSaveFile(window, initialFileName, extensionFilters);

        // making sure file was selected
        if(chosenFile != null){
            try{
                // writing save content to file
                OutputStream out = new FileOutputStream(chosenFile);
                out.write(content.getBytes());
                out.close();
            }
            catch(Exception e){
                throw new Exception("Unable to write content to file '" + chosenFile.getName()  + "'.\n" + 
                                    "Cause : \n\t" + e.toString());
            }
        }
    }

    /**
     * Helper method to open a FileChooser window and allow the user
     * to select a location to save a file to.
     * 
     * @param window The Window the FileChooser will be displayed in.
     * @param initialFilename The initial name of the file being saved.
     * @param extensionFilters The ExtensionFilters that will be applied to the displayed FileChooser.
     * @return The selected file if one was chosen, null if it was not.
     */
    public static File getNewSaveFile(Window window, String initialFileName, ExtensionFilter[] extensionFilters){
        // setting up the file choser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(initialFileName);
        fileChooser.getExtensionFilters().addAll(extensionFilters);

        // showing the saving dialog and returning the selected file.
        return fileChooser.showSaveDialog(window);
    }

    /////////////////
    // RENAME FILE //
    /////////////////

    /**
     * Renames the File to the new name.
     * 
     * @param file File being renamed.
     * @param newName New name for the file.
     * @return The File object associated with renamed file
     */
    public static File renameFile(File file, String newName) throws Exception{
        try{
            // renaming associated File object
            Path source = Paths.get(file.getAbsolutePath());
            Path target = Files.move(source, source.resolveSibling(newName));

            // gathering new file object
            return target.toFile();
        }
        catch(Exception e){
            throw new Exception("Unable to rename file '" + file.getName() + "' to '" + newName + "'.\n" + 
                                "Cause : \n\t" + e.toString());
        }
    }

    //////////////////////////
    // COPYING FILE CONTENT //
    //////////////////////////

    /**
     * Copies the provided content into the system's clipboard.
     * 
     * @param content The content being copied into the clipboard. 
     */
    public static void copyContentToClipboard(String content){
        // setting up clipboard
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent clipboardContent = new ClipboardContent();

        // getting terminal content
        clipboardContent.putString(content);

        // putting terminal content into system clipboard
        clipboard.setContent(clipboardContent);
    }


    ////////////////////
    // HELPER METHODS //
    ////////////////////

    /**
     * Removes the extension filter from a filename if it contains one.
     * 
     * @param filename The whole filename which may include an extension
     * filter.
     * @return The filename without an extensionfilter if it has one.
     */
    public static String removeFileExtension(String filename){
        if(filename.contains("\\.")){
            return filename.split("\\.")[0];
        }
        else{
            return filename;
        }
    }

    /**
     * Returns the extension associated with the given filename. 
     * 
     * Assumes the extension is the sub-string that follows the final
     * "." in the filename.
     * 
     * @param filename The name of the file who's extension is being gathered.
     * @return The extension of the provided filename.
     * @throws Exception If the file does not have an extension.
     */
    public static String getFileExtensionWith(String filename) throws Exception{
        // file must have an extension (which follows ".")
        if(!filename.contains(".")){
            throw new Exception();
        }

        // reversing the string
        StringBuffer buffer = new StringBuffer(filename);
        String reverseFilename = buffer.reverse().toString();

        // gathering reversed file extension (first sub-string in split on ".")
        String reversedExtension = reverseFilename.split("\\.")[0];

        // reversing the extension (back to normal)
        buffer = new StringBuffer(reversedExtension);
        return buffer.reverse().toString();
    }
}
