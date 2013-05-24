/*
 * Copyright (c) 2013 Tomasz Kapuściński
 * All rights reserved.
 */
package colobot.editor;

import colobot.editor.map.ColobotObject;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * Panel that displays and maintaines toolbox content.
 * 
 * @author Tomasz Kapuściński tomaszkax86@gmail.com
 */
public final class ToolBoxPanel extends JTabbedPane
{
    private final MyListener listener = new MyListener();
    
    // tools
    private final JButton nullButton = new JButton();
    private final JButton cloneButton = new JButton();
    private final JButton templateButton = new JButton();
    
    // template object
    private ColobotObject template = null;
    
    
    public ToolBoxPanel()
    {
        super();
        
        setTabPlacement(LEFT);
        
        initComponent();
    }
    
    public ColobotObject getTemplate()
    {
        return template;
    }
    
    public void setTemplate(ColobotObject template)
    {
        this.template = template;
    }
    
    // inits components
    private void initComponent()
    {
        initLanguage();
        initListeners();
        
        addTools();
        addItems();
        addBots();
        addInsects();
        addBuildings();
        addRuins();
        addPlants();
    }
    
    // inits text on components
    private void initLanguage()
    {
        // tools
        nullButton.setText(Language.getText("toolbox.null"));
        cloneButton.setText(Language.getText("toolbox.clone"));
        templateButton.setText(Language.getText("toolbox.template"));
    }
    
    // inits all listeners
    private void initListeners()
    {
        // tools
        nullButton.addActionListener(listener);
        cloneButton.addActionListener(listener);
        templateButton.addActionListener(listener);
    }
    
    private void addTools()
    {
        JPanel panel = new JPanel();
        String text = Language.getText("toolbox.tools");
        panel.add(nullButton);
        panel.add(cloneButton);
        panel.add(templateButton);
        addTab(text, panel);
    }
    
    private void addItems()
    {
        JPanel panel = new JPanel();
        String text = Language.getText("toolbox.items");
        
        addTab(text, panel);
    }
    
    private void addBots()
    {
        JPanel panel = new JPanel();
        String text = Language.getText("toolbox.bots");
        
        addTab(text, panel);
    }
    
    private void addInsects()
    {
        JPanel panel = new JPanel();
        String text = Language.getText("toolbox.insects");
        
        addTab(text, panel);
    }
    
    private void addBuildings()
    {
        JPanel panel = new JPanel();
        String text = Language.getText("toolbox.buildings");
        
        addTab(text, panel);
    }
    
    private void addRuins()
    {
        JPanel panel = new JPanel();
        String text = Language.getText("toolbox.ruins");
        
        addTab(text, panel);
    }
    
    private void addPlants()
    {
        JPanel panel = new JPanel();
        String text = Language.getText("toolbox.plants");
        
        addTab(text, panel);
    }
    
    private class MyListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            Object cmd = e.getSource();
            
            if(cmd == nullButton)
                template = null;
            else if(cmd == cloneButton)
                template = null;        // TODO: cloning
            else if(cmd == templateButton)
                template = new ColobotObject("template", 0, 0, 0);        // TODO: template
            
            // debug
            // JOptionPane.showMessageDialog(null, ((JButton) cmd).getText());
        }
    }
}
