/*
 * Copyright (c) 2013 Tomasz Kapuściński
 * All rights reserved.
 */
package colobot.editor.opengl;

public final class Triangle
{
    private final Vertex[] vertices = new Vertex[3];
    
    
    public Triangle(Vertex v1, Vertex v2, Vertex v3)
    {
        vertices[0] = v1;
        vertices[1] = v2;
        vertices[2] = v3;
    }
    
    public Triangle(Vertex[] vertices)
    {
        for(int i=0; i<3; i++)
            this.vertices[i] = vertices[i];
    }
    
    public Vertex getVertex(int index)
    {
        return vertices[index];
    }
}
