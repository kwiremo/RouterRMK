package com.renemoise.routerrmk.network.table;

/**
 * Created by Rene Moise on 2/1/2017.
 */

import com.renemoise.routerrmk.network.tablerecord.TableRecord;
import com.renemoise.routerrmk.support.LabException;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Table Interface serves as an interface for all other tables.
 */

public interface TableInterface {

    /**
     * This method will return the table’s complete list of records as a List of TableRecords
     */
    List<TableRecord> getTableArrayList();

    /**
     *
     *	This method is passed a TableRecord and adds it to the table.
     *	The method returns the TableRecord added.
     */
    TableRecord addItem( TableRecord tableRecord);

    /**
     * 	The method throws a LabException if the record is not found in the table.
     */
    TableRecord getItem(TableRecord tableRecord) throws LabException;

    /**
     * 	The method removes the record containing the matching Key passed as an Integer
     *  The method returns the record if one is removed, else the method returns a null object.
     */
    TableRecord removeItem(int key);

    /**
     * 	The method returns a TableRecord with the matching key passed as an Integer.
     	The method throws a LabException if the record is not found in the table.
     */
    TableRecord getItem(int key) throws LabException;

    /**
     * 	This method clears the table, removing all records.
     *
     */
    void clear();
}
