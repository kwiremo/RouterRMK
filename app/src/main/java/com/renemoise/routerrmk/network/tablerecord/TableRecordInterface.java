package com.renemoise.routerrmk.network.tablerecord;

/**
 * Created by Rene Moise on 2/1/2017.
 */

/**
 * An interface for all tables.
 */
public interface TableRecordInterface {
    /**
     * This method returns the key in the record.  Keys will be Integers for all record types.
     */
    Integer getKey();

    /**
     * This will return the time passed, in seconds, since this record was last referenced.
     * Not all records will have ages, but all records will return a value if requested.
     * If no age variable is present the function will return “0”
     */
    int getAgeInSeconds();
}





