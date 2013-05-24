package colobot.editor.map;

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
            Terrain terrain = map.getTerrain();
            if(terrain == null) return;
            
            int width = 640;
            int height = 480;
            float aspect = (float) width / (float) height;
            
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create();
            
            // create and load texture
            URL url = MapViewer.class.getResource("/images/texture.png");
            BufferedImage image = ImageIO.read(url);
            ByteBuffer buffer = ByteBuffer.allocateDirect(image.getWidth() * image.getHeight() * 4);
            
            for(int i=0; i<image.getHeight(); i++)
            {
                for(int j=0; j<image.getWidth(); j++)
                {
                    java.awt.Color color = new java.awt.Color(image.getRGB(j, i));
                    
                    buffer.put((byte) color.getRed());
                    buffer.put((byte) color.getGreen());
                    buffer.put((byte) color.getBlue());
                    buffer.put((byte) color.getAlpha());
                }
            }
            
            buffer.flip();
            
            final int textureID = GL11.glGenTextures();
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
            
            //GL11.glTexEnvf(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_DECAL);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
            
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, image.getWidth(), image.getHeight(), 0,
                GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
            
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
            
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
            
            while(!Display.isCloseRequested())
            {
                // camera setup
                GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
                GL11.glMatrixMode(GL11.GL_MODELVIEW);
                GL11.glLoadIdentity();
                
                float dx = (float) Math.cos(Math.toRadians(orientation));
                float dy = (float) Math.tan(Math.toRadians(pitch));
                float dz = (float) Math.sin(Math.toRadians(orientation));
                
                float x = cx + dx;
                float y = cy + dy;
                float z = cz + dz;
                
                GLU.gluLookAt(cx, cy, cz, x, y, z, 0, 1, 0);
                
                // drawing scene
                int w = terrain.getWidth();
                int h = terrain.getHeight();
                
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
                
                float darken = 0.6f;
                
                GL11.glColor3f(darken, darken, darken);
                
                GL11.glBegin(GL11.GL_TRIANGLES);
                
                // map
                for(int i=1; i<w; i++)
                {
                    for(int j=1; j<h; j++)
                    {
                        texture(0, 0); vertex(i-1, j-1, terrain);
                        texture(1, 0); vertex(i-1, j, terrain);
                        texture(0, 1); vertex(i, j-1, terrain);
                        
                        texture(0, 1); vertex(i, j-1, terrain);
                        texture(1, 0); vertex(i-1, j, terrain);
                        texture(1, 1); vertex(i, j, terrain);
                    }
                }
                
                GL11.glEnd();
                
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
                
                // objects
                for(ColobotObject object : map)
                {
                    float xo = (float) object.getX();
                    float zo = (float) object.getY();
                    int xi = nearestCoordinate(xo);
                    int yi = nearestCoordinate(zo);
                    float yo = terrain.get(xi, yi);
                    
                    GL11.glPushMatrix();
                    
                    GL11.glTranslatef(xo, yo, -zo);
                    
                    drawObject(object);
                    
                    GL11.glPopMatrix();
                }
                
                // water
                GL11.glColor3f(0.0f, 0.1f, 0.6f);
                GL11.glBegin(GL11.GL_QUADS);
                
                GL11.glVertex3f(-400.0f, 0.0f, -400.0f);
                GL11.glVertex3f( 400.0f, 0.0f, -400.0f);
                GL11.glVertex3f( 400.0f, 0.0f,  400.0f);
                GL11.glVertex3f(-400.0f, 0.0f,  400.0f);
                
                GL11.glEnd();
                
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
                
                Display.update();
                Display.sync(60);
            }
            
            Display.destroy();
        }
        catch(Exception e)
        {
            System.err.println("Error has occured");
            e.printStackTrace();
        }
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
}
