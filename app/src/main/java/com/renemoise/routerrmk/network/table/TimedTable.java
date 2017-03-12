package com.renemoise.routerrmk.network.table;

/**
 * Created by Rene Moise on 2/23/2017.
 */

import android.app.ActionBar;
import android.util.Log;

import com.renemoise.routerrmk.network.Constants;
import com.renemoise.routerrmk.network.tablerecord.ARPRecord;
import com.renemoise.routerrmk.network.tablerecord.TableRecord;
import com.renemoise.routerrmk.support.LabException;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class is capable of examining the age of the records and expire them if the time exceeds
 * their age.
 * When the ARPDaemon creates a table for the ARP records it will use the TimedTable class.
 */
public class TimedTable extends Table {

    //Empty constructor that calls the super.
    public TimedTable() {
        super();
    }

    /**
     * @param maxAgeAllowed
     * @return ArrayList of TableRecord
     *     This method removes all records that have exceeded the integer passed in.
     *     It returns an array list of those removed records.  This is done because later the
     *     routing protocol daemon will need to know when things expire.
     */
    //TODO: NEED TO CHECK HOW THE Size decreases with a removal of a packet.
    public ArrayList<TableRecord> expireRecords(int maxAgeAllowed){
        ArrayList<TableRecord> trash = new ArrayList<>();

        for(Iterator<TableRecord> iterator = getTableArrayList().iterator(); iterator.hasNext();){
            TableRecord record = iterator.next();
            if(record.getAgeInSeconds() >= maxAgeAllowed){
                trash.add(record);
                iterator.remove();
            }
        }

        updateObservers();
        return trash;
    }

    /**
     *
     * @param key
     *
     * This method will find the associated record specified by the key passed in.
     * It will update its age.
     */
    void touch(int key){

        try{
            TableRecord record = getItem(key);
            //record.updateTime();
        }
        catch (LabException e){
            Log.e(Constants.LOG_TAG, "RECORD NOT TOUCHED" + e.getMessage());
        }
    }
}
