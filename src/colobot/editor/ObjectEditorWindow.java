/*
 * Copyright (c) 2013 Tomasz Kapuściński
 * All rights reserved.
 */
package colobot.editor;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;
import org.lwjgl.LWJGLUtil;

/**
 * Window version of Colobot map editor.
 *
 * This class contains entry point for starting standalone editor.
 *
 * @author Tomasz Kapuściński tomaszkax86@gmail.com
 */
public class ObjectEditorWindow extends JFrame implements WindowListener
{
    private static final File configFile = new File("config.properties");
    
    public ObjectEditorWindow()
    {
        setTitle(Language.getText("editor.title"));
        
        ObjectEditorPanel panel = new ObjectEditorPanel();
        
        this.setJMenuBar(panel.getMenubar());
        
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(this);
        pack();
    }
    
    /**
     * Entry point for starting standalone window-based editor.
     *
     * @param args program arguments
     */
    public static void main(String[] args)
    {
        try
        {
            Settings.load(configFile);
        }
        catch(IOException e)
        {
            Settings.loadDefault();
        }
        
        try
        {
            System.setProperty("org.lwjgl.librarypath", System.getProperty("user.dir")
                + File.separator + "native" + File.separator + LWJGLUtil.getPlatformName());
        }
        catch(Error e)
        {
            System.err.println("3D view support disabled - no LWJGL libraries found");
        }
        
        Settings.initLookAndFeel();
        
        Language.init();
        
        ObjectEditorWindow window = new ObjectEditorWindow();
        window.setVisible(true);
    }
    
    @Override
    public void windowClosing(WindowEvent e)
    {
        try
        {
            Settings.save(configFile);
        }
        catch(IOException er)
        {
            // IGNORE
        }
        
        dispose();
    }

    @Override
    public void windowClosed(WindowEvent e)
    {
        System.exit(0);
    }

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}
}
