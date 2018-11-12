package model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class FeedTableModel extends AbstractTableModel {

    //TODO - pole sloupcu, List  FeedItem; doplnit potrebne metody jako u TableModel; chceme videt 2 sloupce URL, ShouldShow; Should show sloupec lze upravovat

    List<FeedItem> items;
    String[] columns = new String[]{"URL","ShouldShow"};

    public FeedTableModel(){
        items = new ArrayList<>();
    }

    public  void add (FeedItem item)
    {
        items.add(item);
        fireTableDataChanged();
    }

    public void setItems(List<FeedItem> items){
        this.items = items;
    }

    public List<FeedItem> getItems()
    {
        return items;
    }

    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex)
        {
            case 0: return items.get(columnIndex).getUrl();
            case 1: return items.get(columnIndex).isShouldShow();
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        switch (columnIndex){
            case 0: items.get(rowIndex).setUrl((String) aValue);
            break;
            case 1: items.get(rowIndex).setShouldShow((Boolean) aValue);
            break;
        }
        super.setValueAt(aValue, rowIndex, columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex){
            case 0:
                return String.class;
            case 1:
                return Boolean.class;
        }
        return super.getColumnClass(columnIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }
}
