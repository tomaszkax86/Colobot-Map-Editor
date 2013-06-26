/*
 * Copyright (c) 2013 Tomasz Kapuściński
 * All rights reserved.
 */
package colobot.editor.opengl;

public abstract class CompiledModel
{
    protected final Model model;
    
    protected CompiledModel(Model model)
    {
        if(model == null) throw new NullPointerException("Model must not be null");
        
        this.model = model;
    }
    
    public final Model getModel()
    {
        return model;
    }
    
    public abstract void create();
    
    public abstract void destroy();
}
