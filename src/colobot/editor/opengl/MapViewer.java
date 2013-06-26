/*
 * Copyright (c) 2013 Tomasz Kapuściński
 * All rights reserved.
 */
package colobot.editor.opengl;

import colobot.editor.map.ColobotObject;
import colobot.editor.map.Map;
import colobot.editor.map.Terrain;
import static colobot.editor.opengl.Vertex.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.*;
import java.util.*;
import javax.imageio.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;

/**
 * 3D map viewer.
 * @author Tomasz Kapuściński tomaszkax86@gmail.com
 */
public final class MapViewer implements Runnable
{
    private Map map = null;
    
    public void view(Map map)
    {
        if(map == null) return;
        this.map = map;
        
        new Thread(this).start();
    }
    
    @Override
    public void run()
    {
        try
        {
            loop();
        }
        catch(Exception e)
        {
            System.err.println("Error has occured");
            e.printStackTrace();
        }
    }
    
    private void loop() throws Exception
    {
        Terrain terrain = map.getTerrain();
        if(terrain == null) return;
        
        int width = 640;
        int height = 480;
        float aspect = (float) width / (float) height;
        
        Display.setDisplayMode(new DisplayMode(width, height));
        Display.create();
        
        // setup 3D view
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluPerspective(45.0f, aspect, 0.01f, 1000.0f);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
        GL11.glEnable(GL11.GL_CULL_FACE);
        
        float cx = -45, cy = 100, cz = -45;
        float orientation = 0.0f, pitch = 0.0f;
        
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        
        // create and load textures
        URL url = MapViewer.class.getResource("/images/grass.png");
        BufferedImage image = ImageIO.read(url);
        Texture grass = new Texture(image);
        grass.create();
        
        url = MapViewer.class.getResource("/images/water.png");
        image = ImageIO.read(url);
        Texture water = new Texture(image);
        water.create();
        
        Model terrainModel = new Model();
        terrainModel.setTexture(grass);
        
        TexCoord tex11 = new TexCoord(0.0f, 0.0f);
        TexCoord tex12 = new TexCoord(0.0f, 1.0f);
        TexCoord tex22 = new TexCoord(1.0f, 1.0f);
        TexCoord tex21 = new TexCoord(1.0f, 0.0f);
        
        // compiling map
        int w = terrain.getWidth();
        int h = terrain.getHeight();
        
        float[] v1 = new float[3];
        float[] v2 = new float[3];
        float[] v3 = new float[3];
        
        Normal[][] normals = new Normal[w][h];
        VertexCoord[][] points = new VertexCoord[w][h];
        
        for(int i=0; i<w; i++)
        {
            for(int j=0; j<h; j++)
            {
                if((i == 0) || (i == w-1) || (j == 0) || (j == h-1))
                {
                    normals[i][j] = new Normal(0.0f, 1.0f, 0.0f);
                }
                else
                {
                    float dx1 = terrain.get(i-1, j) - terrain.get(i, j);
                    float dx2 = terrain.get(i, j) - terrain.get(i+1, j);
                    float dx = 0.5f * (dx1 + dx2);
                    float angleX = (float) Math.atan2(dx, 5.0);
                    float x = (float) Math.sin(angleX);
                    
                    float dy1 = terrain.get(i, j-1) - terrain.get(i, j);
                    float dy2 = terrain.get(i, j) - terrain.get(i, j+1);
                    float dy = 0.5f * (dy1 + dy2);
                    float angleY = (float) Math.atan2(dy, 5.0);
                    float y = (float) Math.sin(angleY);
                    
                    normals[i][j] = new Normal(x, 1.0f, y);
                }
                
                points[i][j] = new VertexCoord(i * 5.0f - 400.0f, terrain.get(i, j), j * 5.0f - 400.0f);
            }
        }
        
        Vertex vt1 = null, vt2 = null, vt3 = null;
        Triangle triangle = null;
        
        for(int i=1; i<w; i++)
        {
            for(int j=1; j<h; j++)
            {
                vt1 = new Vertex(points[i-1][j-1], normals[i-1][j-1], tex11);
                vt2 = new Vertex(points[i-1][ j ], normals[i-1][ j ], tex12);
                vt3 = new Vertex(points[ i ][ j ], normals[ i ][ j ], tex22);
                
                triangle = new Triangle(vt1, vt2, vt3);
                terrainModel.add(triangle);
                
                // triangle 2
                vt1 = new Vertex(points[ i ][ j ], normals[ i ][ j ], tex22);
                vt2 = new Vertex(points[ i ][j-1], normals[ i ][j-1], tex21);
                vt3 = new Vertex(points[i-1][j-1], normals[i-1][j-1], tex11);
                
                triangle = new Triangle(vt1, vt2, vt3);
                terrainModel.add(triangle);
            }
        }
        
        // water model
        Model waterModel = new Model();
        waterModel.setTexture(water);
        
        tex11 = new TexCoord(  0.0f,   0.0f);
        tex12 = new TexCoord(  0.0f, 800.0f);
        tex22 = new TexCoord(800.0f, 800.0f);
        tex21 = new TexCoord(800.0f,   0.0f);
        
        VertexCoord vc11 = new VertexCoord(-400.0f, 0.0f, -400.0f);
        VertexCoord vc12 = new VertexCoord(-400.0f, 0.0f,  400.0f);
        VertexCoord vc21 = new VertexCoord( 400.0f, 0.0f, -400.0f);
        VertexCoord vc22 = new VertexCoord( 400.0f, 0.0f,  400.0f);
        
        Normal normal = new Normal(0.0f, 1.0f, 0.0f);
        
        vt1 = new Vertex(vc11, normal, tex11);
        vt2 = new Vertex(vc21, normal, tex21);
        vt3 = new Vertex(vc22, normal, tex22);
        
        waterModel.add(new Triangle(vt3, vt2, vt1));
        
        vt1 = new Vertex(vc22, normal, tex22);
        vt2 = new Vertex(vc12, normal, tex12);
        vt3 = new Vertex(vc11, normal, tex11);
        
        waterModel.add(new Triangle(vt3, vt2, vt1));
        
        
        // shader setup
        int shaderProgram = GL20.glCreateProgram();
        int vertexShader = loadVertexShader(MapViewer.class.getResourceAsStream("/shaders/vertex.glsl"));
        int fragmentShader = loadFragmentShader(MapViewer.class.getResourceAsStream("/shaders/fragment.glsl"));
        
        GL20.glAttachShader(shaderProgram, vertexShader);
        GL20.glAttachShader(shaderProgram, fragmentShader);
        GL20.glLinkProgram(shaderProgram);
        GL20.glValidateProgram(shaderProgram);
        
        // rendering
        while(!Display.isCloseRequested())
        {
            // camera setup
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glLoadIdentity();
            
            GL20.glUseProgram(shaderProgram);
            
            float dx = (float) Math.cos(Math.toRadians(orientation));
            float dy = (float) Math.tan(Math.toRadians(pitch));
            float dz = (float) Math.sin(Math.toRadians(orientation));
            
            float x = cx + dx;
            float y = cy + dy;
            float z = cz + dz;
            
            GLU.gluLookAt(cx, cy, cz, x, y, z, 0, 1, 0);
            
            float darken = 0.6f;
            
            GL11.glColor3f(darken, darken, darken);
            
            int lightID = GL20.glGetUniformLocation(shaderProgram, "light");
            GL20.glUniform3f(lightID, 1.0f, 1.0f, 1.0f);
            
            terrainModel.render();
            
            waterModel.render();
            
            GL20.glUseProgram(0);
            
            // objects
            for(ColobotObject object : map)
            {
                float xo = (float) object.getX();
                float zo = (float) object.getY();
                float angle = (float) (Math.PI * object.getDirection() / 2.0);
                int xi = nearestCoordinate(xo);
                int yi = nearestCoordinate(-zo);
                float yo = terrain.get(xi, yi);
                
                GL11.glPushMatrix();
                
                GL11.glRotatef(angle, 0.0f, 1.0f, 0.0f);
                GL11.glTranslatef(xo, yo, -zo);
                
                drawObject(object);
                
                GL11.glPopMatrix();
            }
            
            // water
            /*
            GL11.glColor4f(0.0f, 0.1f, 0.6f, 0.25f);
            GL11.glBegin(GL11.GL_QUADS);
            
            GL11.glVertex3f(-400.0f, 0.0f, -400.0f);
            GL11.glVertex3f(-400.0f, 0.0f,  400.0f);
            GL11.glVertex3f( 400.0f, 0.0f,  400.0f);
            GL11.glVertex3f( 400.0f, 0.0f, -400.0f);
            
            GL11.glEnd();
            // */
            
            // keyboard input
            if(Keyboard.isKeyDown(Keyboard.KEY_UP))
                pitch += 2.0f;
            if(Keyboard.isKeyDown(Keyboard.KEY_DOWN))
                pitch -= 2.0f;
            if(Keyboard.isKeyDown(Keyboard.KEY_LEFT))
                orientation -= 2.0f;
            if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
                orientation += 2.0f;
            if(Keyboard.isKeyDown(Keyboard.KEY_SPACE))
            {
                float speed = 2.0f;
                cx += speed * dx;
                cy += speed * dy;
                cz += speed * dz;
            }
            if(Keyboard.isKeyDown(Keyboard.KEY_F))
            {
                float speed = 2.0f;
                cx -= speed * dx;
                cy -= speed * dy;
                cz -= speed * dz;
            }
            if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
                break;
            
            Display.update();
            Display.sync(60);
        }
        
        Display.destroy();
    }
    
    private static void vertex(int i, int j, Terrain terrain)
    {
        float x = i * 5.0f - 400.0f;
        float z = j * 5.0f - 400.0f;
        float y = (float) terrain.get(i, j);
        
        GL11.glVertex3f(x, y, z);
    }
    
    private static void texture(float u, float v)
    {
        GL11.glTexCoord2f(u, v);
    }
    
    private static int nearestCoordinate(float x)
    {
        return (int) Math.round(160.0f * x / 800.0f + 80.0f);
    }
    
    private static void drawObject(ColobotObject object)
    {
        Random random = new Random(object.getType().hashCode());
        
        GL11.glColor3f(random.nextFloat(), random.nextFloat(), random.nextFloat());
        
        GL11.glBegin(GL11.GL_TRIANGLES);
        
        GL11.glVertex3f(0.5f, 0.0f, 0.5f);
        GL11.glVertex3f(0.5f, 0.0f, -0.5f);
        GL11.glVertex3f(0.0f, 3.0f, 0.0f);
        
        GL11.glVertex3f(0.5f, 0.0f, -0.5f);
        GL11.glVertex3f(-0.5f, 0.0f, -0.5f);
        GL11.glVertex3f(0.0f, 3.0f, 0.0f);
        
        GL11.glVertex3f(-0.5f, 0.0f, -0.5f);
        GL11.glVertex3f(-0.5f, 0.0f, 0.5f);
        GL11.glVertex3f(0.0f, 3.0f, 0.0f);
        
        GL11.glVertex3f(-0.5f, 0.0f, 0.5f);
        GL11.glVertex3f(0.5f, 0.0f, 0.5f);
        GL11.glVertex3f(0.0f, 3.0f, 0.0f);
        
        GL11.glEnd();
    }
    
    private static float dotProduct(float[] v1, float[] v2)
    {
        return v1[0] * v2[0] + v1[1] * v2[1] + v1[2] * v2[2];
    }
    
    private static float length(float[] v)
    {
        return (float) Math.sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]);
    }
    
    private static void crossProduct(float[] v1, float[] v2, float[] v3)
    {
        v3[0] = v1[1] * v2[2] - v1[2] * v2[1];
        v3[1] = v1[0] * v2[2] - v1[2] * v2[0];
        v3[2] = v1[0] * v2[1] - v1[1] * v2[0];
    }
    
    private static void normalize(float[] v)
    {
        float len = length(v);
        
        if(Math.abs(len) < 1e-6) return;
        
        v[0] /= len;
        v[1] /= len;
        v[2] /= len;
    }
    
    private static int loadVertexShader(InputStream input)
    {
        return loadShader(input, GL20.GL_VERTEX_SHADER);
    }
    
    private static int loadFragmentShader(InputStream input)
    {
        return loadShader(input, GL20.GL_FRAGMENT_SHADER);
    }
    
    private static int loadShader(InputStream input, int type)
    {
        try
        {
            String content = readContent(input);
            
            int id = GL20.glCreateShader(type);
            GL20.glShaderSource(id, content);
            GL20.glCompileShader(id);
            
            return id;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return 0;
        }
    }
    
    private static String readContent(InputStream input) throws IOException
    {
        StringBuilder builder = new StringBuilder();
        char[] buffer = new char[256];
        
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(input)))
        {
            while(true)
            {
                int read = reader.read(buffer, 0, buffer.length);
                if(read < 0) break;
                
                builder.append(buffer, 0, read);
            }
        }
        
        return builder.toString();
    }
}
