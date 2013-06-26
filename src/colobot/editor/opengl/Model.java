/*
 * Copyright (c) 2013 Tomasz Kapuściński
 * All rights reserved.
 */
package colobot.editor.opengl;

import java.util.ArrayList;
import org.lwjgl.opengl.GL11;

public final class Model extends ArrayList<Triangle>
{
    private Texture texture = null;
    
    public Texture getTexture()
    {
        return texture;
    }
    
    public void setTexture(Texture texture)
    {
        this.texture = texture;
    }
    
    public void render()
    {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getID());
        GL11.glBegin(GL11.GL_TRIANGLES);
        
        for(Triangle triangle : this)
        {
            for(int i=0; i<3; i++)
            {
                Vertex vertex = triangle.getVertex(i);
                
                GL11.glNormal3f(vertex.getNormalX(), vertex.getNormalY(), vertex.getNormalZ());
                GL11.glTexCoord2f(vertex.getU(), vertex.getV());
                GL11.glVertex3f(vertex.getX(), vertex.getY(), vertex.getZ());
            }
        }
        
        GL11.glEnd();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }
}
