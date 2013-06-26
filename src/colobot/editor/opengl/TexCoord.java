/*
 * Copyright (c) 2013 Tomasz Kapuściński
 * All rights reserved.
 */
package colobot.editor.opengl;

public final class TexCoord
{
    private float u, v;
    
    
    public TexCoord(float u, float v)
    {
        this.u = u;
        this.v = v;
    }
    
    public float getU() { return u; }
    public float getV() { return v; }
}
