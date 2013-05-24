/*
 * Copyright (c) 2013 Tomasz Kapuściński
 * All rights reserved.
 */
package colobot.editor.file;

import colobot.editor.map.Element;
import colobot.editor.map.ParsedMap;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Tomasz Kapuściński tomaszkax86@gmail.com
 */
public final class OriginalMapFormat extends MapFormat
{
    public OriginalMapFormat()
    {
        super("original");
    }

    @Override
    public void load(ParsedMap map, File file) throws IOException
    {
        map.clear();
        
        try(BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            while(true)
            {
                String line = reader.readLine();
                if(line == null) break;
                
                line = trimLine(line);
                
                Element element = parse(line);
                if(element == null) continue;
                
                map.add(element);
            }
        }
    }

    @Override
    public void store(ParsedMap map, File file) throws IOException
    {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file)))
        {
            writer.write("# Colobot map\n");
            
            for(Element element : map)
            {
                writer.write(element.getName());
                
                for(String key : element.keySet())
                {
                    String value = element.get(key);
                    
                    writer.write(' ');
                    writer.write(key);
                    writer.write('=');
                    writer.write(value);
                }
                
                writer.write('\n');
            }
        }
    }
    
    // removes comment and white characters
    private String trimLine(String line)
    {
        int index = line.indexOf("//");
        
        if(index != -1)
            line = line.substring(0, index);
        
        return line.trim();
    }
    
    public static Element parse(String line)
    {
        // removes comment
        int index = line.indexOf('#');
        if(index != -1)
            line = line.substring(0, index);
        
        // removes leading and traling white characters
        line = line.trim();
        
        // if nothing left - return nothing
        if(line.isEmpty()) return null;
        
        String type;
        
        index = line.indexOf(' ');
        
        if(index != -1)
            type = line.substring(0, index);
        else
            type = line;
        
        Element element = new Element(type);
        
        String[] parts = line.split("=");
        
        String name, value;
        
        for(int i=1; i<parts.length; i++)
        {
            String part1 = parts[i-1].trim();
            String part2 = parts[i].trim();
            
            // extracting name
            index = part1.lastIndexOf(' ');
            name = part1.substring(index + 1);
            
            // extracting value
            if(i != parts.length - 1)       // not a last element
            {
                index = part2.lastIndexOf(' ');
                if(index != -1)
                    value = part2.substring(0, index);
                else
                    value = part2;
            }
            else                            // last value
                value = part2;
            
            // removing spaces
            value = removeSpaces(value);
            
            element.put(name, value);
        }
        
        return element;
    }
    
    private static String removeSpaces(String value)
    {
        if(value.charAt(0) == '\"') return value;
        
        StringBuilder builder = new StringBuilder(value.length());
        
        for(int i=0; i<value.length(); i++)
        {
            char c = value.charAt(i);
            if(!Character.isWhitespace(c))
                builder.append(c);
        }
        
        return builder.toString();
    }
}
