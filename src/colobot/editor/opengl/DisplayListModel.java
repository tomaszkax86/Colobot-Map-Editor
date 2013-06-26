/*
 * Copyright (c) 2013 Tomasz Kapuściński
 * All rights reserved.
 */
package colobot.editor.opengl;

import org.lwjgl.opengl.GL11;

public final class DisplayListModel extends CompiledModel
{
    private int id = 0;
    
    public DisplayListModel(Model model)
    {
        super(model);
    }
    
    @Override
    public void create()
    {
        if(id != 0) return;
        
        id = GL11.glGenLists(1);
        GL11.glNewList(id, GL11.GL_COMPILE);
        
        model.render();
        
        GL11.glEndList();
    }
    
    @Override
    public void destroy()
    {
        if(id == 0) return;
        
        GL11.glDeleteLists(id, 1);
        
        id = 0;
    }
    
    public void render()
    {
        if(id == 0) return;
        
        GL11.glCallList(id);
    }
}
