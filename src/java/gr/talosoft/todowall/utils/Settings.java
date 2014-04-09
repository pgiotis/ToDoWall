package gr.talosoft.todowall.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Properties;
import javax.servlet.ServletContext;

/**
 * Save and get all todo wall settings
 *
 * @author Panagiotis Giotis <giotis.p@gmail.com>
 */
public class Settings {

    private final Properties properties;
    private final String SettingsFileName;

    /**
     * Settings constractor. Load properties file
     */
    public Settings(String workingDir) {
       
        SettingsFileName = workingDir+"data/settings.properties";
        File file = new File(SettingsFileName);
        this.properties = new Properties();
        try {
            this.properties.load(new FileInputStream(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get database username
     *
     * @return a string with username
     */
    public String getDBUsername() {

        return this.properties.getProperty("DBusername");

    }

    /**
     * Get database password
     *
     * @return a string with password
     */
    public String getDBPassword() {

        return this.properties.getProperty("DBpassword");

    }

    /**
     * Get database URL
     *
     * @return a string with URL
     */
    public String getDBurl() {

        return this.properties.getProperty("DBurl");

    }



    /**
     * Set new Setting values
     *
     * @param updateSettings A map with all settings that i want to update
     */
    public void setSettings(HashMap<String, String> updateSettings) {

        for (String cSetting : updateSettings.keySet()) {

            this.properties.setProperty(cSetting, updateSettings.get(cSetting));

        }

        try {
            this.properties.store(new PrintWriter(new FileWriter(SettingsFileName)), null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get all  settings
     *
     * @return a map with all settings
     */
    public HashMap<String, String> getSettings() {
        HashMap<String, String> Settings = new HashMap<String, String>();

        for (Object tmp : this.properties.keySet()) {
            Settings.put((String) tmp, this.properties.getProperty((String) tmp));
        }

        return Settings;
    }

}
