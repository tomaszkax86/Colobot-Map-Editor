/*
 * Copyright (c) 2013 Tomasz Kapuściński
 */
package colobot.editor.file;

import colobot.editor.map.ColobotObject;
import colobot.editor.map.Map;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;

/**
 * This class imports original Colobot maps from {@code Reader}.
 * @author Tomasz Kapuściński tomaszkax86@gmail.com
 */
public final class OriginalMapImporter extends MapImporter
{
    @Override
    public void importMap(Reader r, Map map) throws IOException
    {
        BufferedReader reader;
        
        if(r instanceof BufferedReader)
            reader = (BufferedReader) r;
        else
            reader = new BufferedReader(r);
        
        while(true)
        {
            String line = reader.readLine();
            if(line == null) break;
            line = trimLine(line);
            if(line.isEmpty()) continue;
            
            Element element = parseElement(line);
            
            addElement(element, map);
        }
    }
    
    private String trimLine(String line)
    {
        int index = line.indexOf("//");
        
        if(index != -1)
            line = line.substring(0, index);
        
        return line.trim();
    }
    
    private Element parseElement(String line)
    {
        String type;
        
        int index = line.indexOf(' ');
        
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
            
            // wydzielanie nazwy ...blabla name=
            //                            ^
            index = part1.lastIndexOf(' ');
            name = part1.substring(index + 1);
            
            // wydzielanie wartosci  = va lu e name=
            //                       = va lu e
            //                                ^
            index = part2.lastIndexOf(' ');
            if(index != -1)
                value = part2.substring(0, index);
            else
                value = part2;
            
            value = removeSpaces(value);
            
            element.put(name, value);
        }
        
        return element;
    }
    
    private String removeSpaces(String value)
    {
        StringBuilder builder = new StringBuilder(value.length());
        
        for(int i=0; i<value.length(); i++)
        {
            char c = value.charAt(i);
            if(!Character.isWhitespace(c))
                builder.append(c);
        }
        
        return builder.toString();
    }
    
    private void addElement(Element element, Map map)
    {
        if(element.getType().equals("CreateObject"))
        {
            ColobotObject object = new ColobotObject();
            
            for(String name : element.keySet())
            {
                String value = element.get(name);
                
                if(object.hasAttribute(name))
                    object.setAttribute(name, value);
                else
                    object.addAttribute(name, value);
            }
            
            object.update();
            
            map.getObjects().add(object);
        }
    }
    
    // element mapy
    private static final class Element extends HashMap<String, String>
    {
        private String type;
        
        public Element(String type)
        {
            this.type = type;
        }
        
        public String getType()
        {
            return type;
        }
        
        @Override
        public String toString()
        {
            StringBuilder builder = new StringBuilder();

            builder.append(type);

            for(String key : this.keySet())
            {
                builder.append(' ');
                builder.append(key);
                builder.append('=');
                builder.append(this.get(key));
            }

            return builder.toString();
        }
    }
}
