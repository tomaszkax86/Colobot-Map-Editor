/*
 * Copyright (c) 2013 Tomasz Kapuściński
 * All rights reserved.
 */
package colobot.editor;

import javax.swing.JApplet;
import javax.swing.UIManager;

/**
 * Applet version of Colobot Map Editor
 *
 * @author Tomasz Kapuściński tomaszkax86@gmail.com
 */
public final class ObjectEditorApplet extends JApplet
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
