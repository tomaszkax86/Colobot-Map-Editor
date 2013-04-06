/*
 * Copyright (c) 2013 Tomasz Kapuściński
 */
package colobot.editor.file;

import colobot.editor.map.Map;
import colobot.editor.map.Objects;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;

/**
 * This class imports Colobot maps from {@code Reader}.
 * @author Tomasz Kapuściński tomaszkax86@gmail.com
 */
public abstract class MapImporter
{
    private static final HashMap<String, MapImporter> importers = new HashMap<>();
    
    static
    {
        importers.put("original", new OriginalMapImporter());
    }
    
    /**
     * Import Colobot map from {@code Reader} to {@code Map}
     * @param reader object to read map from
     * @param map where to put map
     * @throws IOException if any I/O error occures
     */
    public abstract void importMap(Reader reader, Map map) throws IOException;
    
    /**
     * Import objects from {@code Reader} to {@code Objects}
     * @param reader object to read map from
     * @param objects where to put objects
     * @throws IOException if any I/O error occures
     */
    public abstract void importObjects(Reader reader, Objects objects) throws IOException;
    
    
    public static MapImporter getInstance(String name)
    {
        return importers.get(name);
    }
}
