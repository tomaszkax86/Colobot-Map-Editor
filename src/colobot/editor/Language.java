/*
 * Copyright (c) 2013 Tomasz Kapuściński
 * All rights reserved.
 */
package colobot.editor;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Tomasz Kapuściński tomaszkax86@gmail.com
 */
public final class Language
{
    private static ResourceBundle resources = null;
    
    private Language() {}       // no instantiation
    
    // initializes language with default locale
    static void init()
    {
        resources = ResourceBundle.getBundle("lang.Language");
    }
    
    // initializes language with given locale
    static void init(Locale locale)
    {
        resources = ResourceBundle.getBundle("lang.Language", locale);
    }
    
    /**
     * Translates message
     *
     * @param key key to language properties file
     * @return localized message
     */
    public static String getText(String key)
    {
        return resources.getString(key);
    }
}
