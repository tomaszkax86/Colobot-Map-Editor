/*
 * Copyright (c) 2013 Tomasz Kapuściński
 */
package colobot.editor.map;

import java.util.ArrayList;
import java.util.Objects;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 * Objects of this class represent Colobot objects on the map
 * @author Tomasz Kapuściński tomaszkax86@gmail.com
 */
public final class ColobotObject implements Cloneable
{
    private static final EmptyTableModel emptyTableModel = new EmptyTableModel();
    
    private final MyTableModel myTableModel = new MyTableModel();
    private final ArrayList<Attribute> attribs = new ArrayList<>();
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
        this.type = type;
        this.x = x;
        this.y = y;
        this.dir = dir;
        
        addAttribute("type", type);
        addAttribute("pos", Double.toString(x) + ';' + Double.toString(y));
        addAttribute("dir", Double.toString(dir));
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
        // testing parameters
        String localType = attribs.get(0).getValue();
        String pos = attribs.get(1).getValue();
        int index = pos.indexOf(';');
        double localX = Double.parseDouble( pos.substring(0, index) );
        double localY = Double.parseDouble( pos.substring(index + 1) );
        double localDir = Double.parseDouble( attribs.get(2).getValue() );
        
        // copying parameters
        this.type = localType;
        this.x = localX;
        this.y = localY;
        this.dir = localDir;
    }
    
    @Override
    public ColobotObject clone()
    {
        ColobotObject object = new ColobotObject();
        
        for(int i=0; i<3; i++)
        {
            Attribute a = attribs.get(i);
            
            String name = a.getName();
            String value = a.getValue();
            
            object.setAttribute(name, value);
        }
        
        for(int i=3; i<attribs.size(); i++)
        {
            Attribute a = attribs.get(i);
            
            String name = a.getName();
            String value = a.getValue();
            
            object.addAttribute(name, value);
        }
        
        object.update();
        
        return object;
    }
    
    public boolean hasAttribute(String name)
    {
        for(Attribute a : attribs)
        {
            if(a.getName().equals(name))
                return true;
        }
        
        return false;
    }
    
    public void addAttribute(String name, String value)
    {
        for(Attribute a : attribs)
        {
            if(a.getName().equals(name))
                throw new IllegalArgumentException("Attribute already exists");
        }
        
        attribs.add(new Attribute(name, value));
    }
    
    public void setAttribute(String name, String value)
    {
        for(Attribute a : attribs)
        {
            if(a.getName().equals(name))
            {
                a.setValue(value);
                return;
            }
        }
        
        throw new IllegalArgumentException("No such attribute");
    }
    
    public String getAttribute(String name)
    {
        for(Attribute a : attribs)
        {
            if(a.getName().equals(name))
                return a.getValue();
        }
        
        throw new IllegalArgumentException("No such attribute");
    }
    
    public void removeAttribute(int index)
    {
        if(index < 3) throw new IllegalArgumentException("Cannot remove compulsory attribute");
        
        attribs.remove(index);
    }
    
    public TableModel getTableModel()
    {
        return myTableModel;
    }
    
    public static TableModel getEmptyTableModel()
    {
        return emptyTableModel;
    }
    
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        
        builder.append("CreateObject");
        
        for(Attribute key : attribs)
        {
            builder.append(' ');
            builder.append(key.getName());
            builder.append('=');
            builder.append(key.getValue());
        }
        
        return builder.toString();
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
            return columnIndex == 0 ? "Name" : "Value";
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
            return attribs.size();
        }

        @Override
        public int getColumnCount()
        {
            return 2;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex)
        {
            Attribute a = attribs.get(rowIndex);
            
            if(columnIndex == 0)
                return a.getName();
            else
                return a.getValue();
        }
        
        @Override
        public void setValueAt(Object v, int rowIndex, int columnIndex)
        {
            String value = (String) v;
            Attribute a = attribs.get(rowIndex);
            
            if(columnIndex == 0)
            {
                if(rowIndex > 2)
                    a.setName(value);
            }
            else
            {
                a.setValue(value);
            }
        }
        
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex)
        {
            if(columnIndex == 0)
                return rowIndex > 2;
            else
                return true;
        }
        
        @Override
        public String getColumnName(int columnIndex)
        {
            return columnIndex == 0 ? "Name" : "Value";
        }
    }
}
