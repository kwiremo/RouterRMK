package com.renemoise.routerrmk.network.tablerecord;

import android.content.IntentFilter;

import com.renemoise.routerrmk.network.datagram_fields.NetworkDistancePair;
import com.renemoise.routerrmk.support.Utilities;

/**
 * Created by Rene Moise on 2/1/2017.
 */

/**
 * The Routing Record Class extends the TableRecordClass.  It adds supports for the unique demands
 * of the routing table. The record will contain a NetworkDistance pair object, an Integer
 * containing the LL3P address of the next hop to this remote network, and finally a unique key
 * generated from the rest of the record. The Key will be a combination of the next hop
 * (source of the information) and the remote network.  Mathematically speaking we take advantage
 * of the fact that the max LL3P address has a two byte value of 255 or less.  Thus if we  multiply
 * the network number times 256*256 and add the next Hop we’ll have a unique key for every route that
 * could be placed in the table. Note that multiplying 256*256 shifts a hex number to the left by a
 * byte. So multiplying it by 256 ^ 2 will shift it to the left by 2 bytes. As a result, a key will
 * be a good way to visualize the network number and the next hop in the key. For example, if we
 * have the network number 03 and next hop 0x401, the key will be 0x040103.
 */
public class RoutingRecord extends TableRecord {

    //    This contains the number of a remote network and a distance to that network.
    public NetworkDistancePair networkDistancePair;

    //    This contains the LL3P address of the source of this route, which is the next hop.
    public int nextHop;

    // This key is used to locate records in the routing and forwarding table.
    // It is calculate as follows: network*256*256 + nextHop.
    // Note that this uniquely identifies the destination network and source of this route update.
    // This is important!
    private Integer key;

    public RoutingRecord(int networkNumber, int distance, int nextHop) {
        super();

        //TODO: Created another constructor.
        networkDistancePair = new NetworkDistancePair(networkNumber,distance);
        this.nextHop = nextHop;
        key = (networkNumber * 256 * 256) + nextHop;
    }

    public int getNetworkNumber() {
        return networkDistancePair.getNetwork();
    }

    public int getDistance(){return networkDistancePair.getDistance();}

    public int getNextHop() {
        return nextHop;
    }

    @Override
    public Integer getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "RR. " + networkDistancePair.explainSelf() +
                " Next hop: " + Integer.toHexString(nextHop) + " age: " + getAgeInSeconds();
    }

//    @Override
//    public String toString() {
//        return "RR. " + "key: " + Integer.toHexString(getKey()) + " age: " + getAgeInSeconds();
//    }
}
