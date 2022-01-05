package resources;

import storage.Storage;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class Resources {

    private static final String configPath = FileSystems.getDefault().getPath("src", "resources", "config.properties").toString();
    public static String storagePath;
    public static int initialise;
    public static Locale locale = Locale.getDefault();
    public static ResourceBundle rb;

    public static void setLocale(String language){
        locale = new Locale(language);
        rb = ResourceBundle.getBundle("ResourceBundle", locale);
    }

    //Метод, задающий настройки, указанные в config.resources
    public static boolean startApp() {
        Properties properties = new Properties();
        try {
            System.out.println(configPath);
            properties.load(new FileInputStream(configPath));
            if(System.getProperty("os.name").contains("Windows")) {
                storagePath = properties.getProperty("storage.win.path");
            }
            else if (System.getProperty("os.name").contains("Linux")) {
                storagePath = properties.getProperty("storage.lin.path");
            }
            setLocale(properties.getProperty("language"));
            initialise = Integer.parseInt(properties.getProperty("storage.load"));
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("Ошибка! Файл с настройками не найден!");
            return false;
        }
        return true;
    }
}
