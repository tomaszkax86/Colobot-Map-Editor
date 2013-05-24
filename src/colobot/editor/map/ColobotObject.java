/*
 * Copyright (c) 2013 Tomasz Kapuściński
 * All rights reserved.
 */
package colobot.editor.map;

import colobot.editor.Language;
import java.util.Iterator;
import java.util.Objects;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 * Objects of this class represent Colobot objects on the map
 * @author Tomasz Kapuściński tomaszkax86@gmail.com
 */
public final class ColobotObject extends Element
{
    private static final EmptyTableModel emptyTableModel = new EmptyTableModel();
    
    private final MyTableModel myTableModel = new MyTableModel();
    private String type;
    private double x, y;
    private double dir;
    
    
    /**
     * Creates new Colobot object using "unknows" as type and zeroed values.
     */
    public ColobotObject()
    {
        this("unknown", 0.0, 0.0, 0.0);
    }
    
    /**
     * Creates new Colobot object.
     * @param type type of an object
     * @param x X coordinate
     * @param y Y coordinate
     * @param dir direction
     */
    public ColobotObject(String type, double x, double y, double dir)
    {
        super("CreateObject");
        
        this.put("type", type);
        this.put("pos", "0;0");
        this.put("dir", Double.toString(dir));
        
        update();
    }
    
    /**
     * Returns type of this object.
     * @return type of this object
     */
    public String getType()
    {
        return type;
    }
    
    /**
     * Returns X coordinate of this object.
     * @return X coordinate of this object
     */
    public double getX()
    {
        return x;
    }
    
    /**
     * Returns Y coordinate of this object.
     * @return Y coordinate of this object
     */
    public double getY()
    {
        return y;
    }
    
    /**
     * Returns direction of this object.
     * @return direction of this object
     */
    public double getDirection()
    {
        return dir;
    }
    
    /**
     * Updates parameters of this object from attributes.
     */
    public void update()
    {
        // parsing parameters
        String localType = get("type");
        String[] pos = get("pos").split(";");
        double localX = Double.parseDouble(pos[0]);
        double localY = Double.parseDouble(pos[1]);
        double localDir = Double.parseDouble(get("dir"));
        
        // copying parameters
        this.type = localType;
        this.x = localX;
        this.y = localY;
        this.dir = localDir;
    }
    
    public TableModel getTableModel()
    {
        return myTableModel;
    }
    
    public static ColobotObject valueOf(Element element)
    {
        if(!element.getName().equals("CreateObject")) throw new IllegalArgumentException();
        
        ColobotObject object = new ColobotObject();
        
        for(String key : element.keySet())
        {
            String value = element.get(key);
            
            object.put(key, value);
        }
        
        object.update();
        
        return object;
    }
    
    public static TableModel getEmptyTableModel()
    {
        return emptyTableModel;
    }
    
    private static final class Attribute
    {
        private String name;
        private String value;
        
        public Attribute(String name, String value)
        {
            setName(name);
            setValue(value);
        }
        
        public String getName()
        {
            return name;
        }
        
        public void setName(String name)
        {
            if(name == null) throw new NullPointerException("name");
            this.name = name;
        }
        
        public String getValue()
        {
            return value;
        }
        
        public void setValue(String value)
        {
            if(value == null) throw new NullPointerException("value");
            this.value = value;
        }
        
        @Override
        public boolean equals(Object obj)
        {
            if(this == obj) return true;
            
            if(!(obj instanceof Attribute)) return false;
            Attribute a = (Attribute) obj;
            
            return a.name.equals(this.name);
        }

        @Override
        public int hashCode()
        {
            int hash = 7;
            hash = 29 * hash + Objects.hashCode(this.name);
            return hash;
        }
        
        @Override
        public String toString()
        {
            return name + '=' + value;
        }
    }

    // empty table model
    private static final class EmptyTableModel extends AbstractTableModel
    {
        public EmptyTableModel()
        {
            // NOP
        }

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
            return columnIndex == 0 ? Language.getText("object.name") : Language.getText("object.value");
        }
    }

    // table model for Colobot object
    private final class MyTableModel extends AbstractTableModel
    {
        public MyTableModel()
        {
            // NOP
        }

        @Override
        public int getRowCount()
        {
            return size();
        }

        @Override
        public int getColumnCount()
        {
            return 2;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex)
        {
            Iterator<String> keys = keySet().iterator();
            
            String key = null;
            
            for(int i=0; i<=rowIndex; i++)
                key = keys.next();
            
            String value = get(key);
            
            if(columnIndex == 0)
                return key;
            else
                return value;
        }
        
        @Override
        public void setValueAt(Object v, int rowIndex, int columnIndex)
        {
            String value = (String) v;
            
            Iterator<String> keys = keySet().iterator();
            
            String key = null;
            
            for(int i=0; i<=rowIndex; i++)
                key = keys.next();
            
            put(key, value);
        }
        
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex)
        {
            return (columnIndex > 0);
        }
        
        @Override
        public String getColumnName(int columnIndex)
        {
            return columnIndex == 0 ? Language.getText("object.name") : Language.getText("object.value");
        }
    }
}
