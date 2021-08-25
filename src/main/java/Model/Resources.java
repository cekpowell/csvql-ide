package Model;

/**
 * Defines property that contains the path to the system resources.
 */
public class Resources {

    // member variables
    public static String CODE_MIRROR_PATH = FileType.class.getClassLoader().getResource("codeMirror/").toExternalForm(); 
    public static String IMG_PATH = FileType.class.getClassLoader().getResource("img/").toString(); 
    
    /**
     * Class constructor.
     * 
     * Private - Cannot be instantiated.
     */
    private Resources(){}
}
