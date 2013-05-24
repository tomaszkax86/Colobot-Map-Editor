/*
 * Copyright (c) 2013 Tomasz Kapuściński
 * All rights reserved.
 */
package colobot.editor.map;

import java.awt.image.BufferedImage;

/**
 * This class represents 3D terrain.
 * @author Tomasz Kapuściński tomaszkax86@gmail.com
 */
public final class Terrain
{
    private final float[][] heightmap;
    private final int width, height;
    
    
    /**
     * Creates new terrain.
     * 
     * @param width width
     * @param height height
     */
    public Terrain(int width, int height)
    {
        this.heightmap = new float[width][height];
        this.width = width;
        this.height = height;
    }
    
    public int getWidth()
    {
        return width;
    }
    
    public int getHeight()
    {
        return height;
    }
    
    public float get(int x, int y)
    {
        return heightmap[x][y];
    }
    
    public void set(int x, int y, float value)
    {
        heightmap[x][y] = value;
    }
    
    public static Terrain create(BufferedImage relief, float water, float factor)
    {
        int width = relief.getWidth();
        int height = relief.getHeight();
        
        factor = factor * 0.25f;
        
        Terrain terrain = new Terrain(width, height);
        
        for(int x=0; x<width; x++)
        {
            for(int y=0; y<height; y++)
            {
                int color = 255 - (0xFF & relief.getRGB(x, y));
                
                float h = factor * color - water;
                
                terrain.set(x, y, h);
            }
        }
        
        return terrain;
    }
}
