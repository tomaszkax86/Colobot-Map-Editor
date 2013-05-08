/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colobot.editor.map;

import java.util.LinkedHashMap;

/**
 *
 * @author Tomek
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
        
        builder.append("CreateObject");
        
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
