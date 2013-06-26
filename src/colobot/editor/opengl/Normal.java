/*
 * Copyright (c) 2013 Tomasz Kapuściński
 * All rights reserved.
 */
package colobot.editor.opengl;

public final class Normal
{
    private float x, y, z;
    
    
    public Normal(float[] v)
    {
        this(v[0], v[1], v[2]);
    }
    
    public Normal(float x, float y, float z)
    {
        float d = 1.0f / (float) Math.sqrt(x * x + y * y + z * z);
        
        this.x = d * x;
        this.y = d * y;
        this.z = d * z;
    }
    
    public float getX() { return x; }
    public float getY() { return y; }
    public float getZ() { return z; }
}