/*
 * Copyright (c) 2013 Tomasz Kapuściński
 * All rights reserved.
 */
package colobot.editor.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

public final class VBOModel extends CompiledModel
{
    private int id = 0;
    
    public VBOModel(Model model)
    {
        super(model);
    }
    
    @Override
    public void create()
    {
        if(id != 0) return;
        
        ByteBuffer buffer = ByteBuffer.allocateDirect(model.size() * 3 * 4 * (3 + 3 + 2));
        buffer.order(ByteOrder.nativeOrder());
        
        for(Triangle triangle : model)
        {
            for(int i=0; i<3; i++)
            {
                Vertex vertex = triangle.getVertex(i);
                
                // puts UV
                buffer.putFloat(vertex.getU());
                buffer.putFloat(vertex.getV());
                
                // puts normal
                buffer.putFloat(vertex.getNormalX());
                buffer.putFloat(vertex.getNormalY());
                buffer.putFloat(vertex.getNormalZ());
                
                // puts position
                buffer.putFloat(vertex.getX());
                buffer.putFloat(vertex.getY());
                buffer.putFloat(vertex.getZ());
                // */
            }
        }
        
        buffer.flip();
        
        id = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, id);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }
    
    @Override
    public void destroy()
    {
        if(id == 0) return;
        
        GL15.glDeleteBuffers(id);
        
        id = 0;
    }
    
    public void render()
    {
        if(id == 0) return;
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, id);
        
        GL11.glInterleavedArrays(GL11.GL_T2F_N3F_V3F, 0, 0);
        
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 3 * model.size());
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }
}
