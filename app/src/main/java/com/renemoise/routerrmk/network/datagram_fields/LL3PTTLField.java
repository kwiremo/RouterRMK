package com.renemoise.routerrmk.network.datagram_fields;

/**
 * Created by Rene Moise on 3/30/2017.
 */

import com.renemoise.routerrmk.support.Utilities;

/**
 * This class creates a time to live field. It consists of one byte int value that represent the
 * time to live for an LL3P datagram has left alive. When this value is decremented to zero, the
 * packet is killed.
 */
public class LL3PTTLField implements DatagramHeaderField {

    //This field is a one byte
    private int timeToLive;

    //The constructor to initialize the time to live.
    public LL3PTTLField(String timeToLive) {
        this.timeToLive = Integer.valueOf(timeToLive, 16);
    }

    public LL3PTTLField(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    public int getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    @Override
    public String toHexString() {
        return Utilities.padHexString(Integer.toHexString(timeToLive),1);
    }

    @Override
    public String explainSelf() {
        return "TTL: " + timeToLive;
    }

    @Override
    public String toAsciiString() {
        return Utilities.toAscciiCharactersOfEachHexByte(toHexString());
    }

    @Override
    public String toString() {
        return toHexString();
    }
}
