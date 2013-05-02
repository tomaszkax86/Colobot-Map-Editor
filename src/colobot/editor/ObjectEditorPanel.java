/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package colobot.editor;

import colobot.editor.file.MapExporter;
import colobot.editor.file.MapImporter;
import colobot.editor.map.ColobotObject;
import colobot.editor.map.Map;
import colobot.editor.map.MapSource;
import colobot.editor.map.Objects;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import javax.imageio.ImageIO;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author Tomek
 */
public class ObjectEditorPanel extends JDesktopPane implements MapSource
{
    private final MyListener listener = new MyListener();
    
    private final MapDisplay mapDisplay = new MapDisplay();
    private final ToolBoxPanel toolbox = new ToolBoxPanel();
    private final JTable objectListTable = new JTable();
    private final JTable objectAttributesTable = new JTable();
    
    // menu bar
    private final JMenuBar menubar = new JMenuBar();
    private final JMenuItem newMenuItem = new JMenuItem();
    private final JMenuItem openMenuItem = new JMenuItem();
    private final JMenuItem saveMenuItem = new JMenuItem();
    private final JMenuItem saveAsMenuItem = new JMenuItem();
    private final JMenuItem exitMenuItem = new JMenuItem();
    
    private final JMenuItem reliefMenuItem = new JMenuItem();
    private final JMenuItem configMenuItem = new JMenuItem();
    
    private Map map = null;
    private File currentFile = null;
    private ColobotObject selectedObject = null;
    
    
    public ObjectEditorPanel()
    {
        int width = Settings.getInteger("window.width");
        int height = Settings.getInteger("window.height");
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        
        initLanguage();
        initListeners();
        initComponents();
    }
    
    @Override
    public Map getMap()
    {
        return map;
    }
    
    public void setMap(Map map)
    {
        this.map = map;
    }
    
    public JMenuBar getMenubar()
    {
        return menubar;
    }
    
    // private elements
    
    private void initLanguage()
    {
        newMenuItem.setText(Language.getText("menu.file.new"));
        openMenuItem.setText(Language.getText("menu.file.open"));
        saveMenuItem.setText(Language.getText("menu.file.save"));
        saveAsMenuItem.setText(Language.getText("menu.file.saveas"));
        exitMenuItem.setText(Language.getText("menu.file.exit"));
        
        reliefMenuItem.setText(Language.getText("menu.edit.relief"));
        configMenuItem.setText(Language.getText("menu.edit.config"));
    }
    
    private void initListeners()
    {
        mapDisplay.addMouseListener(listener);
        mapDisplay.addMouseMotionListener(listener);
        mapDisplay.addMouseWheelListener(listener);
        
        newMenuItem.addActionListener(listener);
        openMenuItem.addActionListener(listener);
        saveMenuItem.addActionListener(listener);
        saveAsMenuItem.addActionListener(listener);
        exitMenuItem.addActionListener(listener);
        
        reliefMenuItem.addActionListener(listener);
        configMenuItem.addActionListener(listener);
    }
    
    private void initComponents()
    {
        objectAttributesTable.setModel(ColobotObject.getEmptyTableModel());
        objectListTable.setModel(Objects.getEmptyTableModel());
        
        createMenubar();
        
        JInternalFrame frame;
        
        String text = Language.getText("editor.toolbox");
        frame = new JInternalFrame(text);
        frame.add(toolbox);
        frame.setLocation(Settings.getInteger("toolbox.x"), Settings.getInteger("toolbox.y"));
        frame.setSize(Settings.getInteger("toolbox.width"), Settings.getInteger("toolbox.height"));
        frame.setResizable(true);
        frame.setIconifiable(true);
        frame.show();
        this.add(frame);
        
        text = Language.getText("editor.mapdisplay");
        frame = new JInternalFrame(text);
        mapDisplay.setMapSource(this);
        frame.add(mapDisplay);
        frame.setLocation(Settings.getInteger("mapdisplay.x"), Settings.getInteger("mapdisplay.y"));
        frame.setSize(Settings.getInteger("mapdisplay.width"), Settings.getInteger("mapdisplay.height"));
        frame.setResizable(true);
        frame.setIconifiable(true);
        frame.show();
        this.add(frame);
        
        text = Language.getText("editor.objectlist");
        frame = new JInternalFrame(text);
        frame.add(new JScrollPane(objectListTable));
        frame.setLocation(Settings.getInteger("objectlist.x"), Settings.getInteger("objectlist.y"));
        frame.setSize(Settings.getInteger("objectlist.width"), Settings.getInteger("objectlist.height"));
        frame.setResizable(true);
        frame.setIconifiable(true);
        frame.show();
        this.add(frame);
        
        text = Language.getText("editor.objectattributes");
        frame = new JInternalFrame(text);
        frame.add(new JScrollPane(objectAttributesTable));
        frame.setLocation(Settings.getInteger("objectattributes.x"), Settings.getInteger("objectattributes.y"));
        frame.setSize(Settings.getInteger("objectattributes.width"), Settings.getInteger("objectattributes.height"));
        frame.setResizable(true);
        frame.setIconifiable(true);
        frame.show();
        this.add(frame);
    }
    
    private void createMenubar()
    {
        JMenu menu;
        
        menu = new JMenu(Language.getText("menu.file"));
        menu.add(newMenuItem);
        menu.add(openMenuItem);
        menu.add(saveMenuItem);
        menu.add(saveAsMenuItem);
        menu.addSeparator();
        menu.add(exitMenuItem);
        menubar.add(menu);
        
        menu = new JMenu(Language.getText("menu.edit"));
        menu.add(reliefMenuItem);
        menu.add(configMenuItem);
        menubar.add(menu);
    }
    
    private void update()
    {
        objectAttributesTable.setModel(ColobotObject.getEmptyTableModel());
        objectListTable.setModel(Objects.getEmptyTableModel());
        
        if(map == null) return;
        objectListTable.setModel(map.getObjects().getTableModel());

        if(selectedObject == null) return;
        objectAttributesTable.setModel(selectedObject.getTableModel());
    }
    
    private void selectObject(ColobotObject object)
    {
        selectedObject = object;
        
        if(object == null)
        {
            objectAttributesTable.setModel(ColobotObject.getEmptyTableModel());
            mapDisplay.clearSelection();
        }
        else
        {
            objectAttributesTable.setModel(object.getTableModel());
            mapDisplay.setSelected(object.getX(), object.getY());
        }
        
        update();
    }
    
    private void newFile()
    {
        map = new Map();
        
        selectObject(null);
        update();
        repaint();
    }
    
    private void openFile()
    {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if(result != JFileChooser.APPROVE_OPTION) return;
        
        Map newMap = new Map();
        
        MapImporter importer = MapImporter.getInstance("original");
        File newFile = fileChooser.getSelectedFile();
        
        try(BufferedReader reader = new BufferedReader(new FileReader(newFile)))
        {
            importer.importMap(reader, newMap);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this, Language.getText("error.loading"));
            return;
        }
        
        map = newMap;
        selectObject(null);
        currentFile = newFile;
        
        update();
        repaint();
    }
    
    private void saveFile(File file)
    {
        if(map == null) return;
        
        if(file == null)
        {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(this);
            if(result != JFileChooser.APPROVE_OPTION) return;
            
            file = fileChooser.getSelectedFile();
        }
        
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file)))
        {
            MapExporter exporter = MapExporter.getInstance("original");
            
            exporter.exportMap(writer, map);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this, Language.getText("error.saving"));
        }
        
        currentFile = file;
    }
    
    private void exit()
    {
        
    }
    
    private void loadRelief()
    {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if(result != JFileChooser.APPROVE_OPTION) return;
        
        try
        {
            File file = fileChooser.getSelectedFile();
            BufferedImage image = ImageIO.read(file);
            mapDisplay.setHeightMap(image);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this, Language.getText("error.relief"));
        }
        
        mapDisplay.repaint();
    }
    
    private void config()
    {
        
    }
    
    private final class MyListener implements ActionListener, MouseListener,
            MouseMotionListener, MouseWheelListener
    {
        private int startX, startY;
        private double startCenterX, startCenterY;
        private boolean dragging = false;
        
        @Override
        public void actionPerformed(ActionEvent e)
        {
            Object source = e.getSource();
            
            if(source == newMenuItem)
                newFile();
            else if(source == openMenuItem)
                openFile();
            else if(source == saveMenuItem)
                saveFile(currentFile);
            else if(source == saveAsMenuItem)
                saveFile(null);
            else if(source == exitMenuItem)
                exit();
            else if(source == reliefMenuItem)
                loadRelief();
            else if(source == configMenuItem)
                config();
        }
        
        @Override
        public void mouseClicked(MouseEvent e)
        {
            if(map == null) return;

            if(e.getButton() == MouseEvent.BUTTON1)
            {
                if(toolbox.getTemplate() == null) return;

                double x = mapDisplay.getMapX(e.getX());
                double y = mapDisplay.getMapY(e.getY());

                x = 1e-2 * Math.ceil(x * 1e+2);
                y = 1e-2 * Math.ceil(y * 1e+2);

                String pos = Double.toString(x) + ';' + Double.toString(y);

                ColobotObject object = toolbox.getTemplate().clone();

                object.setAttribute("pos", pos);
                object.update();

                map.getObjects().add(object);
                selectObject(object);
                update();
                mapDisplay.repaint();
            }
            else if(e.getButton() == MouseEvent.BUTTON3)
            {
                ColobotObject object = null;

                double x = mapDisplay.getMapX(e.getX());
                double y = mapDisplay.getMapY(e.getY());

                for(ColobotObject o : map.getObjects())
                {
                    double dx = Math.abs(x - o.getX());
                    double dy = Math.abs(y - o.getY());
                    double dist = Math.sqrt(dx*dx+dy*dy);

                    if(dist < 0.5)
                    {
                        if(object == null)
                            object = o;
                    }
                }

                selectObject(object);
                update();
                mapDisplay.repaint();
            }
        }

        @Override
        public void mousePressed(MouseEvent e)
        {
            if(e.getButton() == MouseEvent.BUTTON1)
            {
                dragging = true;
                startX = e.getX();
                startY = e.getY();
                startCenterX = mapDisplay.getCenterX();
                startCenterY = mapDisplay.getCenterY();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e)
        {
            if(e.getButton() == MouseEvent.BUTTON1)
            {
                dragging = false;
            }
        }

        @Override
        public void mouseEntered(MouseEvent e)
        {
            
        }

        @Override
        public void mouseExited(MouseEvent e)
        {
            
        }

        @Override
        public void mouseDragged(MouseEvent e)
        {
            if(dragging)
            {
                double scale = mapDisplay.getScale();
                double dx = (e.getX() - startX) / scale;
                double dy = (e.getY() - startY) / scale;
                
                double x = startCenterX - dx;
                double y = startCenterY + dy;
                
                mapDisplay.setCenter(x, y);
                mapDisplay.repaint();
            }
        }

        @Override
        public void mouseMoved(MouseEvent e)
        {
            
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e)
        {
            double scale = mapDisplay.getScale();
            
            double steps = e.getPreciseWheelRotation() * 0.5;
            
            mapDisplay.setScale(scale + steps);
            mapDisplay.repaint();
        }
    }
}
