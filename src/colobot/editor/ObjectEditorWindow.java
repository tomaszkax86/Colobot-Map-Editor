/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colobot.editor;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;
import org.lwjgl.LWJGLUtil;

/**
 *
 * @author Tomek
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
