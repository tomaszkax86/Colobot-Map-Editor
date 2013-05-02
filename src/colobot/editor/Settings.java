/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colobot.editor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 *
 * @author Tomek
 */
final class Settings
{
    private static final Properties settings = new Properties();
    
    static void loadDefault()
    {
        settings.clear();
        
        settings.setProperty("lookandfeel", "Nimbus");
        
        settings.setProperty("window.width", "1000");
        settings.setProperty("window.height", "600");
        
        settings.setProperty("toolbox.x", "0");
        settings.setProperty("toolbox.y", "0");
        settings.setProperty("toolbox.width", "200");
        settings.setProperty("toolbox.height", "600");
        
        settings.setProperty("mapdisplay.x", "200");
        settings.setProperty("mapdisplay.y", "0");
        settings.setProperty("mapdisplay.width", "600");
        settings.setProperty("mapdisplay.height", "600");
        
        settings.setProperty("objectlist.x", "800");
        settings.setProperty("objectlist.y", "0");
        settings.setProperty("objectlist.width", "200");
        settings.setProperty("objectlist.height", "300");
        
        settings.setProperty("objectattributes.x", "800");
        settings.setProperty("objectattributes.y", "300");
        settings.setProperty("objectattributes.width", "200");
        settings.setProperty("objectattributes.height", "300");
    }
    
    static void load(File file) throws IOException
    {
        try(BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            settings.clear();
            settings.load(reader);
        }
    }
    
    static void save(File file) throws IOException
    {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file)))
        {
            settings.store(writer, "Colobot map editor settings");
        }
    }
    
    static String getString(String key)
    {
        return settings.getProperty(key);
    }
    
    static void setString(String key, String value)
    {
        settings.setProperty(key, value);
    }
    
    static int getInteger(String key)
    {
        try
        {
            return Integer.parseInt(settings.getProperty(key));
        }
        catch(Exception e)
        {
            return 0;
        }
    }
    
    static void setInteger(String key, int value)
    {
        settings.setProperty(key, Integer.toString(value));
    }
    
    static double getDouble(String key)
    {
        try
        {
            return Double.parseDouble(settings.getProperty(key));
        }
        catch(Exception e)
        {
            return Double.NaN;
        }
    }
    
    static void setDouble(String key, double value)
    {
        settings.setProperty(key, Double.toString(value));
    }
    
    static void initLookAndFeel()
    {
        try
        {
            String type = settings.getProperty("lookandfeel", "default");

            if(type.equals("default")) return;
            
            if(type.equals("system"))
            {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
            else
            {
                LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();
                
                for(LookAndFeelInfo info : infos)
                {
                    if(info.getName().equals(type))
                    {
                        UIManager.setLookAndFeel(info.getClassName());
                        return;
                    }
                }
            }
        }
        catch(Exception e)
        {
            
        }
    }
    
    private Settings() {}
}
