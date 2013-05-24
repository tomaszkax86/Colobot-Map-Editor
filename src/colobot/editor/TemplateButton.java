/*
 * Copyright (c) 2013 Tomasz Kapuściński
 * All rights reserved.
 */
package colobot.editor;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JButton;

/**
 * Button that displays image.
 * 
 * @author Tomasz Kapuściński tomaszkax86@gmail.com
 */
public final class TemplateButton extends JButton
{
    private BufferedImage image = null;
    
    public TemplateButton()
    {
        initComponents();
    }
    
    public BufferedImage getImage()
    {
        return image;
    }
    
    public void setImage(BufferedImage image)
    {
        this.image = image;
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        if(image == null) return;
        
        g.drawImage(image, 4, 4, getWidth() - 4, getHeight() - 4,
                0, 0, image.getWidth(), image.getHeight(), null);
    }
    
    @SuppressWarnings("unchecked")
    private void initComponents()
    {
        setMaximumSize(new java.awt.Dimension(128, 128));
        setMinimumSize(new java.awt.Dimension(48, 48));
        setPreferredSize(new java.awt.Dimension(72, 72));
    }
}
