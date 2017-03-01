package com.renemoise.routerrmk.network.tablerecord;

/**
 * Created by Rene Moise on 2/1/2017.
 */

import com.renemoise.routerrmk.network.datagram_fields.LL2PAddressField;

/**
 * Record type held by ARP Table. Arp record has 3 fields an LL2P address, an LL3P address, and an
 * age.
 */
public class ARPRecord  extends TableRecord {

    //This Integer contains the LL2P address of the neighbor.
    private int ll2PAddress;

    /**This contains the integer of the LL3P address of the neighbor.
     This field is the key for this record and should be returned by getKey.
     This field is therefore also used in the already written compareTo() method of the parent class.
     The age field is already contained in the parent class.
     */
    private int ll3pAddress;


    /**
     *  The constructor is passed the LL2P and LL3P address. It calls the superclass’s
     *  constructor to take care of the age value.
     */
    public ARPRecord(int ll2PAddress, int ll3pAddress ){
        super();//calls the superclass to update age. (Superclass constructor calls the updateTime()
        this.ll2PAddress = ll2PAddress;
        this.ll3pAddress = ll3pAddress;
    }

    /**
     * This constructor initializes a record with values of zero in the address fields.
     * This is used when returning an empty record. The process that receives and empty record will
     * use the setter methods to set the layer 2 and layer 3 address fields.
     */
    public ARPRecord(){
        //Super class called automatically. it sets the time for this record.
        this.ll2PAddress = 0;
        this.ll2PAddress = 0;
    }

    //Overrides the super getkey.
    @Override
    public Integer getKey() {
        return ll3pAddress;
    }

    public int getLl2PAddress() {
        return ll2PAddress;
    }

    public void setLl2PAddress(int ll2PAddress) {
        this.ll2PAddress = ll2PAddress;
    }

    public int getLl3pAddress() {
        return ll3pAddress;
    }

    public void setLl3pAddress(int ll3pAddress) {
        this.ll3pAddress = ll3pAddress;
    }

    @Override
    public String toString() {
        return "ARP Record. key: " + Integer.toHexString(getKey()) + " age: " + getAgeInSeconds();
    }
}
