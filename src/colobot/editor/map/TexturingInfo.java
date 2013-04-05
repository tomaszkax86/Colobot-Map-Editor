/*
 * Copyright (c) 2013 Tomasz Kapuściński
 */
package colobot.editor.map;

import java.util.ArrayList;

/**
 *
 * @author Tomasz Kapuściński tomaszkax86@gmail.com
 */
public final class TexturingInfo
{
    // texturing mode - simple or complex
    // if simple - uses TerrainInitTextures
    // if complex - uses TerrainMaterial, TerrainInit, and TerrainLevel
    private boolean texturingMode = false;
    
    // simple texturing mode
    private String initTexturesImage = null;    // partial file name
    private int initTexturesDX = 1;             // horizontal tiles count
    private int initTexturesDY = 1;             // vertical tiles count
    private String initTexturesTable = "1";     // texture table
    
    // complex texturing mode
    private final ArrayList<Material> materials = new ArrayList<>(); // materials
    private int terrainInit = 0;                // initial material
    private final ArrayList<Level> levels = new ArrayList<>();      // poziomy
    
    
    TexturingInfo()
    {
        // NOP
    }
    
    // texturing modes
    public boolean isSimpleTexturingMode()
    {
        return !texturingMode;
    }
    
    public boolean isComplexTexturingMode()
    {
        return texturingMode;
    }
    
    public void setTexturingMode(boolean complexMode)
    {
        texturingMode = complexMode;
    }
    
    // simple texturing mode
    public String getSimpleModeImage()
    {
        return initTexturesImage;
    }
    
    public int getSimpleModeHorizontalTiles()
    {
        return initTexturesDX;
    }
    
    public int getSimpleModeVerticalTiles()
    {
        return initTexturesDY;
    }
    
    public String getSimpleModeTable()
    {
        return initTexturesTable;
    }
    
    // complex texturing mode
    public int getComplexModeMaterialCount()
    {
        return materials.size();
    }
    
    public Material getComplexModeMaterial(int index)
    {
        return materials.get(index);
    }
    
    public void addComplexModeMaterial(Material material)
    {
        materials.add(material);
    }
    
    public Material removeComplexModeMaterial(int index)
    {
        return materials.remove(index);
    }
    
    public int getComplexModeDefaultMaterial()
    {
        return terrainInit;
    }
    
    public int getComplexModeLevelCount()
    {
        return levels.size();
    }
    
    public void addComplexModeLevel(Level level)
    {
        levels.add(level);
    }
    
    public Level getComplexModeLevel(int index)
    {
        return levels.get(index);
    }

    public Level removeComplexModeLevel(int index)
    {
        return levels.remove(index);
    }
    
    /**
     * Material settings
     */
    public static final class Material
    {
        public static final int NEXT_UP = 0;
        public static final int NEXT_DOWN = 1;
        public static final int NEXT_LEFT = 2;
        public static final int NEXT_RIGHT = 3;
        
        private int id;                     // material ID
        private String image;               // material texture image file name
        private double u = 0.0, v = 0.0;    // texture UV coordinates
        private int[] next = new int[4];    // materials on neighbor tiles
        
        
        public Material(int id, String image)
        {
            this.id = id;
            this.image = image;
            
            for(int i=0; i<4; i++)
                next[i] = id;
        }
        
        public int getID()
        {
            return id;
        }
        
        public void setID(int id)
        {
            this.id = id;
        }
        
        public String getImage()
        {
            return image;
        }
        
        public void setImage(String image)
        {
            this.image = image;
        }
        
        public double getTextureU()
        {
            return u;
        }
        
        public void setTextureU(double u)
        {
            this.u = u;
        }
        
        public double getTextureV()
        {
            return v;
        }
        
        public void setTextureV(double v)
        {
            this.v = v;
        }
        
        public void setTextureUV(double u, double v)
        {
            this.u = u;
            this.v = v;
        }
        
        public int getNeightbor(int n)
        {
            return next[n];
        }
        
        public void setNeighbor(int n, int id)
        {
            next[n] = id;
        }
    }

    
    /**
     * Material level settings
     */
    public static final class Level
    {
        private int id;                     // material ID
        private double min, max;            // min and max height
        private double slope;               // more slope - more flat terrain
        private double freq = 0.8;          // frequency
        
        public Level(int id, double min, double max)
        {
            this.id = id;
            this.min = min;
            this.max = max;
            this.slope = 0.0;
        }
        
        public int getID()
        {
            return id;
        }
        
        public void setID(int id)
        {
            this.id = id;
        }
        
        public double getMinimum()
        {
            return min;
        }
        
        public void setMinimum(double min)
        {
            this.min = min;
        }
        
        public double getMaximum()
        {
            return max;
        }
        
        public void setMaximum(double max)
        {
            this.max = max;
        }
        
        public double getSlope()
        {
            return slope;
        }
        
        public void setSloper(double slope)
        {
            this.slope = slope;
        }
        
        public double getFrequency()
        {
            return freq;
        }
        
        public void setFrequency(double freq)
        {
            this.freq = freq;
        }
    }
}
