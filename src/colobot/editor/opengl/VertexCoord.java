/*
 * Copyright (c) 2013 Tomasz Kapuściński
 * All rights reserved.
 */
package colobot.editor.opengl;

public final class VertexCoord
{
    private float x, y, z, w;
    
    
    public VertexCoord(float x, float y, float z)
    {
        this(x, y, z, 1.0f);
    }
    
    public VertexCoord(float x, float y, float z, float w)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
    public float getX() { return x; }
    public float getY() { return y; }
    public float getZ() { return z; }
    public float getW() { return w; }
}