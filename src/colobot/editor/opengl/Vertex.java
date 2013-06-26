/*
 * Copyright (c) 2013 Tomasz Kapuściński
 * All rights reserved.
 */
package colobot.editor.opengl;

public final class Vertex
{
    private final float x, y, z;
    private final float nx, ny, nz;
    private final float u, v;
    
    
    public Vertex(VertexCoord position, Normal normal, TexCoord uv)
    {
        this.x = position.getX();
        this.y = position.getY();
        this.z = position.getZ();
        
        this.nx = normal.getX();
        this.ny = normal.getY();
        this.nz = normal.getZ();
        
        this.u = uv.getU();
        this.v = uv.getV();
    }
    
    public float getX() { return x; }
    public float getY() { return y; }
    public float getZ() { return z; }
    
    public float getNormalX() { return nx; }
    public float getNormalY() { return ny; }
    public float getNormalZ() { return nz; }
    
    public float getU() { return u; }
    public float getV() { return v; }
}
