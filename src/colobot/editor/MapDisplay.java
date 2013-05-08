/*
 * Copyright (c) 2013 Tomasz Kapuściński
 */
package colobot.editor;

import colobot.editor.map.ColobotObject;
import colobot.editor.map.Map;
import colobot.editor.map.MapSource;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;

/**
 *
 * @author Tomasz Kapuściński tomaszkax86@gmail.com
 */
public final class MapDisplay extends JComponent
{
    private static final Rectangle2D mapBorder = new Rectangle2D.Double(-400, -400, 800, 800);
    private static final BasicStroke stroke = new BasicStroke(0.025f, BasicStroke.CAP_ROUND, BasicStroke.CAP_ROUND);
    
    private MapSource mapSource;
    private double scale = 1.0;
    private double centerX = 0.0, centerY = 0.0;
    private double selectedX = -1e+6, selectedY = -1e+6;
    private BufferedImage heightMap = null;
    
    public void setMapSource(MapSource mapSource)
    {
        this.mapSource = mapSource;
    }
    
    public double getScale()
    {
        return scale;
    }
    
    public void setScale(double scale)
    {
        if(scale < 1e-3 || scale > 1e+6) return;
        
        this.scale = scale;
    }
    
    public double getCenterX()
    {
        return centerX;
    }
    
    public double getCenterY()
    {
        return centerY;
    }
    
    public void setCenter(double x, double y)
    {
        this.centerX = x;
        this.centerY = y;
    }
    
    public void setSelected(double x, double y)
    {
        this.selectedX = x;
        this.selectedY = y;
    }
    
    public void clearSelection()
    {
        this.selectedX = -1e+6;
        this.selectedY = -1e+6;
    }
    
    public void setHeightMap(BufferedImage image)
    {
        this.heightMap = image;
    }
    
    public double getMapX(int x)
    {
        return centerX + (x - getWidth() / 2) / scale;
    }
    
    public double getMapY(int y)
    {
        return centerY - (y - getHeight() / 2) / scale;
    }
    
    @Override
    public void paintComponent(Graphics gr)
    {
        gr.setColor(Color.BLACK);
        gr.fillRect(0, 0, getWidth(), getHeight());
        
        // no map source - quit
        if(mapSource == null) return;
        
        Map map = mapSource.getMap();
        
        // no map - quit
        if(map == null) return;
        
        Graphics2D g = (Graphics2D) gr;
        
        g.setStroke(stroke);
        
        // ustawienie skali i przesunięcia
        g.translate(getWidth() / 2, getHeight() / 2);
        g.scale(scale, scale);
        g.translate(-centerX, centerY);
        
        // rysowanie mapy wysokości (jeśli jest)
        if(heightMap != null)
        {
            g.drawImage(heightMap, -400, -400, 400, 400,
                0, 0, heightMap.getWidth(), heightMap.getHeight(), null);
        }
        
        // rysowanie obramowania mapy
        g.setColor(Color.RED);
        g.draw(mapBorder);
        
        // rysowanie obiektów na mapie
        for(ColobotObject object : map)
        {
            drawObject(g, object);
        }
        
        if(selectedX > -1000.0)
        {
            Ellipse2D ellipse = new Ellipse2D.Double(selectedX-0.25, -selectedY-0.25, 0.5, 0.5);

            g.setColor(Color.RED);
            g.draw(ellipse);
        }
    }
    
    private void drawObject(Graphics2D g, ColobotObject object)
    {
        double x = object.getX();
        double y = -object.getY();
        double dir = object.getDirection();

        dir = dir * Math.PI / 1.0;
        double dx = Math.cos(dir) * 0.5;
        double dy = Math.sin(dir) * 0.5;

        Ellipse2D ellipse = new Ellipse2D.Double(x-0.25, y-0.25, 0.5, 0.5);
        Line2D line = new Line2D.Double(x, y, x+dx, y+dy);

        g.setColor(Color.GREEN);
        g.draw(ellipse);
        g.draw(line);
    }
}
