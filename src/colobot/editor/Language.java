/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colobot.editor;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Tomek
 */
public final class Language
{
    private static ResourceBundle resources = null;
    
    private Language() {}
    
    static void init()
    {
        resources = ResourceBundle.getBundle("colobot.editor.lang.Language");
    }
    
    static void init(Locale locale)
    {
        resources = ResourceBundle.getBundle("colobot.editor.lang.Language", locale);
    }
    
    public static String getText(String key)
    {
        return resources.getString(key);
    }
}
