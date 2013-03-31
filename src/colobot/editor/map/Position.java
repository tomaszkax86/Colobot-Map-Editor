/*
 * Copyright (c) 2013 Tomasz Kapuściński
 */
package colobot.editor.map;

/**
 * Objects of this class represent position.
 * @author Tomasz Kapuściński tomaszkax86@gmail.com
 */
public final class Position
{
    private double x, y;
    
    /**
     * Creates new position with default values (0.0, 0.0).
     */
    public Position()
    {
        this(0.0, 0.0);
    }
    
    /**
     * Creates new position with given values.
     * @param x X coordinate
     * @param y Y coordinate
     */
    public Position(double x, double y)
    {
        setX(x);
        setY(y);
    }
    
    /**
     * Returns X coordinate of this position.
     * @return X coordinate
     */
    public double getX()
    {
        return x;
    }
    
    /**
     * Returns Y coordinate of this position.
     * @return Y coordinate
     */
    public double getY()
    {
        return y;
    }
    
    /**
     * Sets X coordinate.
     * @param x new X coordinate
     */
    public void setX(double x)
    {
        this.x = x;
    }
    
    /**
     * Sets Y coordinate.
     * @param y new Y coordinate
     */
    public void setY(double y)
    {
        this.y = y;
    }
    
    /**
     * Sets new coordinates.
     * @param x new X coordinate
     * @param y new Y coordinate
     */
    public void setPosition(double x, double y)
    {
        setX(x);
        setY(y);
    }
    
    @Override
    public String toString()
    {
        return Double.toString(x) + ';' + Double.toString(y);
    }
}
