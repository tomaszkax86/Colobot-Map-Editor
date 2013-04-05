/*
 * Copyright (c) 2013 Tomasz Kapuściński
 */
package colobot.editor.map;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 * Objects of this class contains all objects on Colobot map.
 * @author Tomasz Kapuściński tomaszkax86@gmail.com
 */
public final class Objects extends ArrayList<ColobotObject>
{
    private static final EmptyTableModel emptyTableModel = new EmptyTableModel();
    
    private final MyTableModel myTableModel = new MyTableModel();
    
    
    Objects()
    {
        // NOP
    }
    
    public TableModel getTableModel()
    {
        return myTableModel;
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
            return columnIndex == 0 ? "ID" : "Object";
        }
        
        @Override
        public int getRowCount()
        {
            return Objects.this.size();
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
                return Objects.this.get(rowIndex).getType();
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
            return columnIndex == 0 ? "ID" : "Object";
        }
    }
}
