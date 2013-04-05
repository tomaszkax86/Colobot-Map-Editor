/*
 * Copyright (c) 2013 Tomasz Kapuściński
 */
package colobot.editor.map;

/**
 * Objects of this class represent Colobot map.
 * @author Tomasz Kapuściński tomaszkax86@gmail.com
 */
public final class Map
{
    private final GeneralInfo generalInfo = new GeneralInfo();
    private final GraphicsInfo graphicsInfo = new GraphicsInfo();
    private final TerrainInfo terrainInfo = new TerrainInfo();
    private final Objects objects = new Objects();
    private final TexturingInfo texturingInfo = new TexturingInfo();
    private final Lights lights = new Lights();
    private final ResearchInfo researchInfo = new ResearchInfo();
    
    
    /**
     * Creates new empty map with default values.
     */
    public Map()
    {
        // NOP
    }
    
    /**
     * Returns general info associated with this map.
     * @return general info of this map
     */
    public GeneralInfo getGeneralInfo()
    {
        return generalInfo;
    }
    
    /**
     * Returns graphics info associated with this map.
     * @return graphics info of this map
     */
    public GraphicsInfo getGraphicsInfo()
    {
        return graphicsInfo;
    }
    
    /**
     * Returns terrain info associated with this map.
     * @return terrain info of this map
     */
    public TerrainInfo getTerrainInfo()
    {
        return terrainInfo;
    }
    
    /**
     * Returns objects on this map.
     * @return objects on this map
     */
    public Objects getObjects()
    {
        return objects;
    }
    
    /**
     * Returns terrain texturing info of this map.
     * @return terrain texturing info of this map
     */
    public TexturingInfo getTexturingInfo()
    {
        return texturingInfo;
    }
    
    /**
     * Returns lights on this map.
     * @return lights on this map
     */
    public Lights getLights()
    {
        return lights;
    }
    
    /**
     * Returns research info on this map.
     * @return research info on this map
     */
    public ResearchInfo getResearchInfo()
    {
        return researchInfo;
    }
}
