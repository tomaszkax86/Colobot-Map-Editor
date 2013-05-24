/*
 * Copyright (c) 2013 Tomasz Kapuściński
 * All rights reserved.
 */
package colobot.editor.map;

import colobot.editor.Language;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 * Objects of this class represent Colobot map.
 * @author Tomasz Kapuściński tomaszkax86@gmail.com
 */
public final class Map extends ArrayList<ColobotObject>
{
    private static final EmptyTableModel emptyTableModel = new EmptyTableModel();
    
    private final MyTableModel myTableModel = new MyTableModel();
    
    private final ArrayList<Element> preCreate = new ArrayList<>();
    private final ArrayList<Element> postCreate = new ArrayList<>();
    
    private Terrain terrain = null;
    
    
    public TableModel getTableModel()
    {
        return myTableModel;
    }
    
    public void load(ParsedMap map)
    {
        boolean pre = true;
        
        for(Element element : map)
        {
            if(element.getName().equals("BeginObject"))
                pre = false;
            else if(element.getName().equals("CreateObject"))
                add(ColobotObject.valueOf(element));
            else
            {
                if(pre)
                    preCreate.add(element);
                else
                    postCreate.add(element);
            }
        }
    }
    
    public void store(ParsedMap map)
    {
        map.clear();
        
        for(Element element : preCreate)
            map.add(element);
        
        map.add(new Element("BeginObject"));
        
        for(ColobotObject object : this)
            map.add(object);
        
        for(Element element : postCreate)
            map.add(element);
    }
    
    public Terrain getTerrain()
    {
        return terrain;
    }
    
    public void setRelief(BufferedImage relief, float water, float factor)
    {
        this.terrain = Terrain.create(relief, water, factor);
    }
    
    
    public static TableModel getEmptyTableModel()
    {
        return emptyTableModel;
    }
    
    private final class MyTableModel extends AbstractTableModel
    {
        @Override
        public String getColumnName(int columnIndex)
        {
            return columnIndex == 0 ? Language.getText("map.id") : Language.getText("map.object");
        }
        
        @Override
        public int getRowCount()
        {
            return Map.this.size();
        }
        
        @Override
        public int getColumnCount()
        {
            return 2;
        }
        
        @Override
        public Object getValueAt(int rowIndex, int columnIndex)
        {
            if(columnIndex == 0)
                return rowIndex + 1;
            else
                return Map.this.get(rowIndex).getType();
        }
    }
    
    private static final class EmptyTableModel extends AbstractTableModel
    {
        @Override
        public int getRowCount()
        {
            return 0;
        }

        @Override
        public int getColumnCount()
        {
            return 2;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex)
        {
            return null;
        }
        
        @Override
        public String getColumnName(int columnIndex)
        {
            return columnIndex == 0 ? Language.getText("map.id") : Language.getText("map.object");
        }
    }
}
