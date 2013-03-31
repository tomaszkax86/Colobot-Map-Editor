/*
 * Copyright (c) 2013 Tomasz Kapuściński
 */
package colobot.editor.map;

/**
 * Objects of this class represent terrain information of Colobot map.
 * @author Tomasz Kapuściński tomaszkax86@gmail.com
 */
public final class TerrainInfo
{
    // general info
    private double vision = 250.0;
    private double depth = 1.0;
    private double hard = 0.6;
    
    // wind - speed
    private Position wind = new Position();
    
    // lightnings on the map
    private boolean blitz = false;
    private double blitzSleep = 60.0;
    private double blitzDelay = 5.0;
    private double blitzMagnetic = 100.0;
    
    protected TerrainInfo()
    {
        // NOP
    }
}
