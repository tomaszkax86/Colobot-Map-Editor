/*
 * Copyright (c) 2013 Tomasz Kapuściński
 * All rights reserved.
 */
package colobot.editor.map;

import java.util.LinkedHashMap;

/**
 * Named element with named attributes
 * @author Tomasz Kapuściński tomaszkax86@gmail.com
 */
public class Element extends LinkedHashMap<String, String>
{
    private String name;
    
    
    public Element(String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return name;
    }
    
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        
        builder.append(name);
        
        for(String key : this.keySet())
        {
            String value = get(key);
            
            builder.append(' ');
            builder.append(key);
            builder.append('=');
            builder.append(value);
        }
        
        return builder.toString();
    }
}
