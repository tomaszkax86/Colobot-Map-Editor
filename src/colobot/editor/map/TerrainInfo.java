/*
 * Copyright (c) 2013 Tomasz Kapuściński
 */
package colobot.editor.map;

import java.awt.Color;
import java.util.ArrayList;

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
    private double windX = 0.0;
    private double windY = 0.0;
    
    // lightnings on the map
    private boolean blitz = false;
    private double blitzSleep = 60.0;
    private double blitzDelay = 5.0;
    private double blitzMagnetic = 100.0;
    
    // heightmap
    private String relief = null;           // relief file name
    private double reliefFactor = 1.0;      // height multiplier 1.0 = 0.25m per color change
    
    // resources
    private String resources = null;        // resources file name
    
    // water
    private String waterTexture = null;     // water texture file name
    private double waterLevel = 0.0;        // height where water starts
    private double waterSpeedX = 0.0;       // speed of water
    private double waterSpeedY = 0.0;
    private Color waterColor = new Color(0, 240, 100, 0);   // water color
    private double waterBrightness = 0.2;   // water brightness
    
    // lava mode
    private boolean lavaMode = false;
    
    // clouds
    private String cloudImage = null;       // cloud texture file name
    private double cloudLevel = 125.0;      // level where clouds are
    
    
    TerrainInfo()
    {
        // NOP
    }
    
    public double getVision()
    {
        return vision;
    }
    
    public void setVision(double vision)
    {
        this.vision = vision;
    }
    
    public double getDepth()
    {
        return depth;
    }
    
    public void setDepth(double depth)
    {
        this.depth = depth;
    }
    
    public double getHard()
    {
        return hard;
    }
    
    public void setHard(double hard)
    {
        this.hard = hard;
    }
    
    public double getWindX()
    {
        return windX;
    }
    
    public double getWindY()
    {
        return windY;
    }
    
    public void setWindX(double x)
    {
        windX = x;
    }
    
    public void setWindY(double y)
    {
        windY = y;
    }
    
    public void setWind(double x, double y)
    {
        windX = x;
        windY = y;
    }
    
    public boolean isBlitzEnabled()
    {
        return blitz;
    }
    
    public void setBlitz(boolean enabled)
    {
        this.blitz = enabled;
    }
    
    public double getBlitzSleep()
    {
        return blitzSleep;
    }
    
    public double getBlitzDelay()
    {
        return blitzDelay;
    }
    
    public double getBlitzMagnetic()
    {
        return blitzMagnetic;
    }
    
    public void setBlitz(double sleep, double delay, double magnetic)
    {
        this.blitzSleep = sleep;
        this.blitzDelay = delay;
        this.blitzMagnetic = magnetic;
    }
    
    public String getRelief()
    {
        return relief;
    }
    
    public void setRelief(String relief)
    {
        this.relief = relief;
    }

    public double getHeighFactor()
    {
        return reliefFactor;
    }
    
    public void setHeightFactor(double factor)
    {
        reliefFactor = factor;
    }
    
    public String getResources()
    {
        return resources;
    }
    
    public void setResources(String value)
    {
        resources = value;
    }
    
    public String getWaterTexture()
    {
        return waterTexture;
    }
    
    public void setWaterTexture(String texture)
    {
        waterTexture = texture;
    }
    
    public double getWaterLevel()
    {
        return waterLevel;
    }
    
    public double getWaterSpeedX()
    {
        return waterSpeedX;
    }
    
    public double getWaterSpeedY()
    {
        return waterSpeedY;
    }
    
    public Color getWaterColor()
    {
        return waterColor;
    }
    
    public double getWaterBrightness()
    {
        return waterBrightness;
    }
    
    public boolean isLavaMode()
    {
        return lavaMode;
    }
    
    public String getCloudImage()
    {
        return cloudImage;
    }
    
    public double getCloudLevel()
    {
        return cloudLevel;
    }
}
