/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colobot.editor;

import javax.swing.JApplet;
import javax.swing.UIManager;

/**
 *
 * @author Tomek
 */
public class ObjectEditorApplet extends JApplet
{
    @Override
    public void init()
    {
        Settings.loadDefault();
        
        Settings.initLookAndFeel();
        
        Language.init();
        
        ObjectEditorPanel panel = new ObjectEditorPanel();
        this.add(panel);
        
        this.setJMenuBar(panel.getMenubar());
    }
}
