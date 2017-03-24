package com.renemoise.routerrmk.network.tablerecord;

import java.net.InetAddress;

/**
 * Created by Rene Moise on 2/1/2017.
 */

/**
 * The adjacency record class inherits the table recoed and it is a blueprint for all
 * adjacency tables
 */
public class AdjacencyRecord extends TableRecord {

    /**
     * This is  the key for this record.
     */
    private int ll2pAddress;

    /**
     * When we transmit data using a UDP port we need the address stored in a class called
     * “InetAddress”.  This is an object that provides other UDP functions with a data structure
     * that can be easily inserted into the actual IP packet when the UDP packet we’ll create is
     * transmitted.
     */
    private InetAddress ipAddress;

    public AdjacencyRecord(int ll2pAddress, InetAddress ipAddress) {
        super();    //To set (update) the time.

        setLl2pAddress(ll2pAddress);
        setIpAddress(ipAddress);
    }

    public AdjacencyRecord(){}

    public void setLl2pAddress(int ll2pAddress) {
        this.ll2pAddress = ll2pAddress;
    }

    public void setIpAddress(InetAddress ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public Integer getKey() {
        return ll2pAddress;
    }

    /**
     * This will return “0” because there is no age in an AdjacencyRecord
     */
    @Override
    public int getAgeInSeconds() {
        return 0;
    }

    @Override
    public String toString() {
        return String.format("LL2P Add: " + Integer.toHexString(ll2pAddress)
                + "IP Add: " + ipAddress);
    }

    public int getLl2pAddress() {
        return ll2pAddress;
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }
}
