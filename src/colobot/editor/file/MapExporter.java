/*
 * Copyright (c) 2013 Tomasz Kapuściński
 */
package colobot.editor.file;

import colobot.editor.map.Map;
import colobot.editor.map.Objects;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;

/**
 * This class exports Colobot maps to {@code Writer}.
 * @author Tomasz Kapuściński tomaszkax86@gmail.com
 */
public abstract class MapExporter
{
    private static final HashMap<String, MapExporter> exporters = new HashMap<>();
    
    static
    {
        exporters.put("original", new OriginalMapExporter());
    }
    
    
    /**
     * Export map to {@code Writer} object.
     * @param writer object to write map to
     * @param map map to be written
     * @throws IOException if any I/O error occures
     */
    public abstract void exportMap(Writer writer, Map map) throws IOException;
    
    /**
     * Export objects to {@code Writer} object.
     * @param writer object to write map to
     * @param map map to be written
     * @throws IOException if any I/O error occures
     */
    public abstract void exportObjects(Writer writer, Objects objects) throws IOException;
    
    
    public static MapExporter getInstance(String name)
    {
        return exporters.get(name);
    }
}
