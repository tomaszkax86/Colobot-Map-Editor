/*
 * Copyright (c) 2013 Tomasz Kapuściński
 * All rights reserved.
 */
package colobot.editor.opengl;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import org.lwjgl.opengl.GL11;

public final class Texture
{
    private final BufferedImage image;
    private int id = 0;
    
    public Texture(BufferedImage image)
    {
        if(image == null) throw new NullPointerException("Image must not be null");
        
        this.image = image;
    }
    
    public int getID()
    {
        return id;
    }
    
    public void create()
    {
        if(id != 0) return;
        
        final int width = image.getWidth();
        final int height = image.getHeight();
        
        ByteBuffer buffer = ByteBuffer.allocateDirect(4 * width * height);
        
        for(int y=0; y<height; y++)
        {
            for(int x=0; x<width; x++)
            {
                int color = image.getRGB(x, y);
                
                buffer.put((byte) (0xFF & (color >>> 16)));
                buffer.put((byte) (0xFF & (color >>>  8)));
                buffer.put((byte) (0xFF & (color >>>  0)));
                buffer.put((byte) (0xFF & (color >>> 24)));
            }
        }
        
        buffer.flip();
        
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        id = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
        
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }
    
    public void destroy()
    {
        if(id == 0) return;
        
        GL11.glDeleteTextures(id);
        
        id = 0;
    }
}
