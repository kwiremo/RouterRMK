package com.renemoise.routerrmk.network.datagram_fields;

/**
 * Created by Rene Moise on 3/7/2017.
 */

import com.renemoise.routerrmk.support.Utilities;

/**
 * This is a class with a unique Sequence number for this routing update. The maximum number of
 * routes is 15 in any update.
 */
public class LRPRouteCount implements DatagramHeaderField {

    /**
     * This contains a unique Sequence number for this routing update. The maximum number of
     * routes is 15 in any update.
     */
    private int routeCount;

    /**
     * The constructor takes one bit character hex string.
     * @param routeCount
     */
    public LRPRouteCount(String routeCount) {
        this.routeCount = Integer.valueOf(routeCount,16);
    }

    @Override
    public String toHexString() {
        return Integer.toHexString(routeCount);
    }

    @Override
    public String explainSelf() {
        return "Route Count: " + routeCount;
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
