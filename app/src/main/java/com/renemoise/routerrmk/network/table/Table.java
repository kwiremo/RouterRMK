package com.renemoise.routerrmk.network.table;

import android.app.ActionBar;

import com.renemoise.routerrmk.network.tablerecord.TableRecord;
import com.renemoise.routerrmk.support.LabException;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Rene Moise on 2/1/2017.
 */

public class Table extends Observable implements TableInterface,Observer{

    protected ArrayList<TableRecord> table;

    public Table() {
        table = new ArrayList<TableRecord>();
    }

    @Override
    public List<TableRecord> getTableArrayList() {
        return null;
    }

    //TODO: Why return the tablerecord that was passed.
    @Override
    public TableRecord addItem(TableRecord tableRecord) {
        table.add(tableRecord);
        return tableRecord;
    }

    @Override
    public TableRecord getItem( TableRecord tableRecord) throws LabException {
        return null;
    }

    @Override
    public TableRecord removeItem(int key) {
        return null;
    }

    @Override
    public TableRecord getItem(int key) throws LabException {
        return null;
    }

    @Override
    public void clear() {
        table.clear();
    }

    //TODO: Do we need to setChanges?
    //Notify all observers that something has changed.
    public void updateObservers(){
        notifyObservers();
    }

    @Override
    public void update(Observable observable, Object o) {

    }
}
