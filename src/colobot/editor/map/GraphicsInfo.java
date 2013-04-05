/*
 * Copyright (c) 2013 Tomasz Kapuściński
 */
package colobot.editor.map;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Objects of this class represent graphics information about Colobot map.
 * @author Tomasz Kapuściński tomaszkax86@gmail.com
 */
public class GraphicsInfo
{
    public static final int TYPE_AIR = 0;
    public static final int TYPE_WATER = 1;
    
    // ambient colors - air and water
    private final Color[] ambientColor = new Color[] { new Color(120, 90, 0, 0), new Color(20, 20, 20, 20) };
    
    // fog color, null if none - air and water
    private final Color[] fogColor = new Color[] { null, null };
    
    // color of vehicles
    private Color vehicleColor = new Color(168, 158, 118, 0);
    
    // color of plants
    private Color greeneryColor = new Color(161, 151, 41, 0);
    
    // color of insects
    private Color insectColor = null;
    
    // maximum field of view - air and water
    private final double[] deepView = new double[] { 100.0, 25.0 };
    
    // lowest level where fog starts - air and water
    private final double[] fogStart = new double[] { 0.1, 0.1 };
    
    // background settings
    private String backgroundImage = null;      // image name
    private Color backgroundUp = null;          // upper color of the sky
    private Color backgroundDown = null;        // lower color of the sky
    private Color backgroundCloudUp = null;     // upper color behind clouds
    private Color backgroundCloudDown = null;   // lower color behind clouds
    
    // texture of dirt, value from 1 to 8
    private int secondTexture = 1;
    
    // light effects - file name
    private String frontsizeName = null;
    
    // planets
    private final ArrayList<Planet> planets = new ArrayList<>();
    
    
    GraphicsInfo()
    {
        // NOP
    }
    
    public Color getAmbientColor(int type)
    {
        return ambientColor[type];
    }
    
    public void setAmbientColor(int type, Color color)
    {
        ambientColor[type] = color;
    }
    
    public Color getFogColor(int type)
    {
        return fogColor[type];
    }
    
    public void setFogColor(int type, Color color)
    {
        fogColor[type] = color;
    }
    
    public Color getVehicleColor()
    {
        return vehicleColor;
    }
    
    public void setVehicleColor(Color color)
    {
        vehicleColor = color;
    }
    
    public Color getGreeneryColor()
    {
        return greeneryColor;
    }
    
    public void setGreeneryColor(Color color)
    {
        greeneryColor = color;
    }
    
    public Color getInsectColor()
    {
        return insectColor;
    }
    
    public void setInsectColor(Color color)
    {
        insectColor = color;
    }
    
    public double getDeepView(int type)
    {
        return deepView[type];
    }
    
    public void setDeepView(int type, double value)
    {
        deepView[type] = value;
    }
    
    public double getFogStart(int type)
    {
        return fogStart[type];
    }
    
    public void setFogStart(int type, double value)
    {
        fogStart[type] = value;
    }
    
    public int getSecondTexture()
    {
        return secondTexture;
    }
    
    public void setSecondTexture(int value)
    {
        secondTexture = value;
    }
    
    public String getBackgroundImage()
    {
        return backgroundImage;
    }
    
    public void setBackground(String image)
    {
        backgroundImage = image;
    }
    
    public Color getBackgroundUp()
    {
        return backgroundUp;
    }
    
    public void setBackgroundUp(Color color)
    {
        backgroundUp = color;
    }
    
    public Color getBackgroundDown()
    {
        return backgroundDown;
    }
    
    public void setBackgroundDown(Color color)
    {
        backgroundDown = color;
    }
    
    public Color getBackgroundCloudUp()
    {
        return backgroundCloudUp;
    }
    
    public void setBackgroundCloudUp(Color color)
    {
        backgroundCloudUp = color;
    }
    
    public Color getBackgroundCloudDown()
    {
        return backgroundCloudDown;
    }
    
    public void setBackgroundCloudDown(Color color)
    {
        backgroundCloudDown = color;
    }
    
    public String getFrontsizeName()
    {
        return frontsizeName;
    }
    
    public void setFrontsizeName(String name)
    {
        frontsizeName = name;
    }
    
    public int getPlanetCount()
    {
        return planets.size();
    }
    
    public void addPlanet(Planet planet)
    {
        planets.add(planet);
    }
    
    public Planet getPlanet(int index)
    {
        return planets.get(index);
    }
    
    public Planet removePlanet(int index)
    {
        return planets.remove(index);
    }
    
    
    /**
     * Objects of this class represent planets.
     */
    public static final class Planet
    {
        // true - only visible during interplanetary travel
        private boolean travelMode = false;
        
        // position of the planet on the sky
        private final double[] pos = new double[] { 0.0, 0.0 };
        
        // dimension multiplier - value between 0.002 (small stars) and 0.5 (large planets)
        private double dim = 0.05;
        
        // speed of the planet, generally small value around 0.001
        private double speed = 0.0;
        
        // movement direction - usually around 0.1
        private double dir = 0.1;
        
        // image file name
        private String image = null;
        
        // texture coordinates
        private final double[][] uv = new double[][] { {0.0, 0.0}, {1.0, 1.0} };
        
        
        public Planet()
        {
            // NOP
        }
        
        public boolean isTravelMode()
        {
            return travelMode;
        }
        
        public void setTravelMode(boolean mode)
        {
            travelMode = mode;
        }
        
        public double getX()
        {
            return pos[0];
        }
        
        public void setX(double x)
        {
            pos[0] = x;
        }
        
        public double getY()
        {
            return pos[1];
        }
        
        public void setY(double y)
        {
            pos[1] = y;
        }
        
        public void setPosition(double x, double y)
        {
            pos[0] = x;
            pos[1] = y;
        }
        
        public double getDimension()
        {
            return dim;
        }
        
        public void setDimension(double value)
        {
            dim = value;
        }
        
        public double getSpeed()
        {
            return speed;
        }
        
        public void setSpeed(double value)
        {
            speed = value;
        }
        
        public double getDirection()
        {
            return dir;
        }
        
        public void setDirection(double value)
        {
            dir = value;
        }
        
        public String getImage()
        {
            return image;
        }
        
        public void setImage(String image)
        {
            this.image = image;
        }
        
        public double getUV(int t, int c)
        {
            return uv[t][c];
        }
        
        public void setUV(int t, int c, double value)
        {
            uv[t][c] = value;
        }
    }
}
