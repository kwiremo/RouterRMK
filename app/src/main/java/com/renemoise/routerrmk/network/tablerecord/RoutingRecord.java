package com.renemoise.routerrmk.network.tablerecord;

import android.content.IntentFilter;

import com.renemoise.routerrmk.network.datagram_fields.NetworkDistancePair;

/**
 * Created by Rene Moise on 2/1/2017.
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
        //TODO: Make it cleaner.
        return networkDistancePair.toString() + " " + nextHop;
    }
}
