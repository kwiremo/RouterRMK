package com.renemoise.routerrmk.network.tablerecord;

/**
 * Created by Rene Moise on 2/1/2017.
 */

import com.renemoise.routerrmk.support.Utilities;

/**
 * The TableRecordClass is a base class for all other record classes.  The classes which inherit
 * from this class should provide specifics for fields within the class and override any methods
 * here if specific implementation related to the fields is required
 */
public class TableRecord implements TableRecordInterface {

    /**
     *o	This field has the value in seconds of the time that the record was either last
     * referenced (touched) or installed in the table.
     */
    private int lastTimeTouched;

    // This class simply calls the method “updateTime()” to set the value of lastTimeTouched.
    public TableRecord() {
        updateTIme();
    }

    /**
     *     This method sets the lastTimeTouched to the current time.
     */
    private void updateTIme() {
        lastTimeTouched = Utilities.getTimeInSeconds();
    }

    /**
     * o	This is a base class. Each class that inherits will need to override this method to
     * return whatever object they are using for the Key. This method in the base class should
     * return null. This is the second method from the interface.
     */
    @Override
    public Integer getKey() {
        return 0;
    }


    @Override
    public int getAgeInSeconds() {
        return Utilities.getTimeInSeconds() - lastTimeTouched;
    }

    /**
     * For tableRecord comparison purposes.
     */
    public int compareTo(TableRecord tableRecord)
    {
        return this.getKey().compareTo(tableRecord.getKey());
    }

    //Implement tostring() method

    @Override
    public String toString() {
        return super.toString();
    }
}
