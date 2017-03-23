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

/**
 * This is a base table for all other tables such as adjacency table, arp table, routing table,etc.
 * It implements the TableInterface. Other classes will extend this base class for an advantage of
 * reusing code.
 */
public class Table extends Observable implements TableInterface{

    protected ArrayList<TableRecord> table;

    /**
     * Creates a reference to the arraylist of tableRecords.
     */
    public Table() {
        table = new ArrayList<>();
    }

    //return any list.
    @Override
    public List<TableRecord> getTableArrayList() {
        return table;
    }

    //return tablerecord for a successful addition.
    @Override
    public TableRecord addItem(TableRecord tableRecord) {
        for(int i = 0; i<table.size(); i++){
            if (table.get(i).getKey().compareTo(tableRecord.getKey())==0){
                //If it is present, update time.
                table.get(i).updateTime();
                return table.get(i);
            }
        }
        //If the record was not found, the exception is caught here.
        table.add(tableRecord);
        updateObservers();
        return tableRecord;
    }

    //I could have used table.contains to get an item.
    @Override
    public TableRecord getItem( TableRecord tableRecord) throws LabException {

        /**
         * I was testing to see if the record would be found by merely using contains.
         * I also use the for loop which is guaranteed to find the record if it exits.
         */
        for(int i = 0; i<table.size(); i++){
            if (table.get(i).getKey().compareTo(tableRecord.getKey())==0){
                return table.get(i);
            }
        }
        throw new LabException("Record with key: " + Integer.toHexString(tableRecord.getKey()) +
                " Was not found");
    }

    @Override
    public TableRecord removeItem(int key){

        for(int i = 0; i<table.size(); i++){
            if (table.get(i).getKey().compareTo(key)==0){
                TableRecord record = table.get(i);
                table.remove(i);
                updateObservers();
                return record;
            }
        }
        return null;
    }


    @Override
    public TableRecord getItem(int key) throws LabException {
        for(int i = 0; i<table.size(); i++){
            if (table.get(i).getKey().compareTo(key)==0){
                return table.get(i);
            }
        }
        throw new LabException("Record with key: " + Integer.toHexString(key)
                + " Was not found");
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
