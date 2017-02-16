package com.renemoise.routerrmk.network.table;

import android.app.ActionBar;
import android.app.ExpandableListActivity;
import android.util.Log;

import com.renemoise.routerrmk.UI.UIManager;
import com.renemoise.routerrmk.network.Constants;
import com.renemoise.routerrmk.network.tablerecord.TableRecord;
import com.renemoise.routerrmk.support.LabException;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Rene Moise on 2/1/2017.
 */

public class Table extends Observable implements TableInterface{

    protected ArrayList<TableRecord> table;

    public Table() {
        table = new ArrayList<TableRecord>();
    }

    //return any list.
    @Override
    public List<TableRecord> getTableArrayList() {
        return table;
    }

    //return tablerecord for a successful addition.
    @Override
    public TableRecord addItem(TableRecord tableRecord) {
        try{
            //Try to see if there is an item.
            getItem(tableRecord);

            //If it is, remove it and update it.
            removeItem(tableRecord.getKey());
            table.add(tableRecord);
        }
        catch (LabException e)
        {
            table.add(tableRecord);
            updateObservers();
        }

        finally {
            return tableRecord;
        }
    }

    //I could have used table.contains to get an item.
    @Override
    public TableRecord getItem( TableRecord tableRecord) throws LabException {
        if(table.contains(tableRecord))
            UIManager.getInstance().disPlayMessage("Record already exist.");

        for(TableRecord record : table)
        {
            if(record.equals(tableRecord))
                return record;
        }
        throw  new LabException("RECORD NOT PRESENT");
    }

    @Override
    public TableRecord removeItem(int key) throws LabException{

        try{
            TableRecord record = getItem(key);
            table.remove(record);
            updateObservers();
            return record;
        }
        catch (LabException e){
            e.getMessage();
        }
        throw new LabException("Item Not Removed!");
    }

    @Override
    public TableRecord getItem(int key) throws LabException {
        for(TableRecord record : table){
            if (record.getKey().compareTo(key) == 0){
                return record;
            }
        }
        throw new LabException("Record with key: " + key + " Was not found");
    }

    @Override
    public void clear() {
        updateObservers();
        table.clear();
    }

    //Notify all observers that something has changed.
    public void updateObservers(){
        setChanged();
        notifyObservers();
    }
}
