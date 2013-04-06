/*
 * Copyright (c) 2013 Tomasz Kapuściński
 */
package colobot.editor.file;

import colobot.editor.map.ColobotObject;
import colobot.editor.map.GeneralInfo.Language;
import colobot.editor.map.GraphicsInfo.Planet;
import colobot.editor.map.Map;
import colobot.editor.map.Objects;
import java.awt.Color;
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
    private int langCount = 0;
    
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
            
            try
            {
                addElement(element, map);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void importObjects(Reader r, Objects objects) throws IOException
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
            
            if(element.getType().equals("CreateObject"))
                addObject(element, objects);
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
    
    private void addElement(Element element, Map map)
    {
        String[] parts;
        String type = element.getType();
        
        switch(type)
        {
            case "Instructions":
                map.getGeneralInfo().setInstructions(removeQuotationMarks(element.get("name")));
                break;
            case "Satellite":
                map.getGeneralInfo().setSatellite(removeQuotationMarks(element.get("name")));
                break;
            case "Loading":
                map.getGeneralInfo().setLoadingFile(removeQuotationMarks(element.get("name")));
                break;
            case "SoluceFile":
                map.getGeneralInfo().setSolutionFile(removeQuotationMarks(element.get("name")));
                break;
            case "HelpFile":
                map.getGeneralInfo().setHelpFile(removeQuotationMarks(element.get("name")));
                break;
            case "EndingFile":
                int win = Integer.parseInt( element.get("win") );
                int lost = Integer.parseInt( element.get("lost") );
                map.getGeneralInfo().setEndingFile(win, lost);
                break;
            case "MessageDelay":
                double delay = Double.parseDouble(element.get("factor"));
                map.getGeneralInfo().setMessageDelay(delay);
                break;
            case "Audio":
                int track = Integer.parseInt(element.get("track"));
                map.getGeneralInfo().setAudioTrack(track);
                break;
            case "AmbiantColor":
                map.getGraphicsInfo().setAmbientColor(0, parseColor(element.get("air")));
                map.getGraphicsInfo().setAmbientColor(1, parseColor(element.get("water")));
                break;
            case "FogColor":
                map.getGraphicsInfo().setFogColor(0, parseColor(element.get("air")));
                map.getGraphicsInfo().setFogColor(1, parseColor(element.get("water")));
                break;
            case "VehicleColor":
                map.getGraphicsInfo().setVehicleColor(parseColor(element.get("color")));
                break;
            case "GreneeryColor":
                map.getGraphicsInfo().setGreeneryColor(parseColor(element.get("color")));
                break;
            case "InsectColor":
                map.getGraphicsInfo().setInsectColor(parseColor(element.get("color")));
                break;
            case "DeepView":
                map.getGraphicsInfo().setDeepView(0, Double.parseDouble(element.get("air")));
                map.getGraphicsInfo().setDeepView(1, Double.parseDouble(element.get("water")));
                break;
            case "SecondTexture":
                map.getGraphicsInfo().setSecondTexture(Integer.parseInt(element.get("rank")));
                break;
            case "Background":
                map.getGraphicsInfo().setBackground(removeQuotationMarks(element.get("image")));
                map.getGraphicsInfo().setBackgroundUp(parseColor(element.get("up")));
                map.getGraphicsInfo().setBackgroundDown(parseColor(element.get("down")));
                map.getGraphicsInfo().setBackgroundCloudUp(parseColor(element.get("cloudUp")));
                map.getGraphicsInfo().setBackgroundCloudDown(parseColor(element.get("cloudDown")));
                break;
            case "FrontsizeName":
                map.getGraphicsInfo().setFrontsizeName(removeQuotationMarks(element.get("image")));
                break;
            case "Planet":
                map.getGraphicsInfo().addPlanet(parsePlanet(element));
                break;
            case "TerrainGenerate":
                map.getTerrainInfo().setVision(Double.parseDouble(element.get("vision")));
                map.getTerrainInfo().setDepth(Double.parseDouble(element.get("depth")));
                map.getTerrainInfo().setHard(Double.parseDouble(element.get("hard")));
                break;
            case "TerrainWind":
                parts = element.get("speed").split(";");
                map.getTerrainInfo().setWindX(Double.parseDouble(parts[0]));
                map.getTerrainInfo().setWindY(Double.parseDouble(parts[1]));
                break;
            case "TerrainBlitz":
                map.getTerrainInfo().setBlitz(true);
                map.getTerrainInfo().setBlitz(Double.parseDouble(element.get("sleep")),
                    Double.parseDouble(element.get("delay")),
                    Double.parseDouble(element.get("magnetic")));
                break;
            case "TerrainRelief":
                map.getTerrainInfo().setRelief(removeQuotationMarks(element.get("image")));
                map.getTerrainInfo().setHeightFactor(Double.parseDouble(element.get("factor")));
                break;
            case "TerrainResource":
                map.getTerrainInfo().setResources(element.get("image"));
                break;
            case "TerrainWater":
                map.getTerrainInfo().setWaterTexture(removeQuotationMarks(element.get("image")));
                map.getTerrainInfo().setWaterLevel(Double.parseDouble(element.get("level")));
                map.getTerrainInfo().setWaterSpeedX(Double.parseDouble(element.get("moveX")));
                map.getTerrainInfo().setWaterSpeedY(Double.parseDouble(element.get("moveY")));
                map.getTerrainInfo().setWaterColor(parseColor(element.get("color")));
                if(element.containsKey("brightness"))
                    map.getTerrainInfo().setWaterBrightness(Double.parseDouble(element.get("brightness")));
                break;
            case "TerrainLava":
                map.getTerrainInfo().setLavaMode(element.get("mode").equals("1"));
                break;
            case "TerrainCloud":
                map.getTerrainInfo().setCloudImage(removeQuotationMarks(element.get("image")));
                map.getTerrainInfo().setCloudLevel(Double.parseDouble(element.get("level")));
                break;
        // TODO: materials
            case "CreateObject":
                addObject(element, map.getObjects());
                break;
            default:
                if(type.indexOf("Title.") != -1)
                    parseLanguage(element, map, true);
                else if(type.indexOf("Resume.") != -1)
                    parseLanguage(element, map, false);
        }
    }
    
    private void addObject(Element element, Objects objects)
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

        objects.add(object);
    }
    
    private String removeSpaces(String value)
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
    
    private void parseLanguage(Element element, Map map, boolean first)
    {
        if(first)
        {
            Language lang = map.getGeneralInfo().getLanguage(langCount);
            lang.setLetter(element.getType().charAt(6));
            lang.setTitle(removeQuotationMarks(element.get("text")));
        }
        else
        {
            Language lang = map.getGeneralInfo().getLanguage(langCount);
            lang.setDescription(removeQuotationMarks(element.get("text")));
            langCount++;
        }
    }
    
    private Color parseColor(String text)
    {
        if(text == null) return null;
        
        String[] rgba = text.split(";");
        
        int r = Integer.parseInt(rgba[0]);
        int g = Integer.parseInt(rgba[1]);
        int b = Integer.parseInt(rgba[2]);
        int a = Integer.parseInt(rgba[3]);
        
        return new Color(r, g, b, a);
    }
    
    private Planet parsePlanet(Element element)
    {
        Planet planet = new Planet();
        
        return planet;
        
        /*
        planet.setTravelMode(element.get("mode").equals("0"));
        
        String[] pos = element.get("pos").split(";");
        planet.setX(Double.parseDouble(pos[0]));
        planet.setY(Double.parseDouble(pos[1]));
        
        planet.setDimension(Double.parseDouble(element.get("dim")));
        planet.setDirection(Double.parseDouble(element.get("dir")));
        planet.setSpeed(Double.parseDouble(element.get("speed")));
        
        planet.setImage(element.get("image"));
        
        String[] uv1 = element.get("uv1").split(";");
        String[] uv2 = element.get("uv2").split(";");
        
        planet.setUV(0, 1, Double.parseDouble(uv1[0]));
        planet.setUV(0, 0, Double.parseDouble(uv1[1]));
        planet.setUV(1, 1, Double.parseDouble(uv2[0]));
        planet.setUV(1, 0, Double.parseDouble(uv2[1]));
        
        return planet;  // */
    }
    
    private String removeQuotationMarks(String text)
    {
        if(text == null) return text;
        if(text.isEmpty()) return text;
        if(text.charAt(0) != '\"') return text;
        if(text.charAt(text.length() - 1) != '\"') return text;
        
        return text.substring(1, text.length() - 1);
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
