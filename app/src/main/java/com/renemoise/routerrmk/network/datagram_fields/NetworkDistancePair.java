package com.renemoise.routerrmk.network.datagram_fields;

/**
 * Created by Rene Moise on 3/7/2017.
 */

import com.renemoise.routerrmk.support.Utilities;

/**
 * This class contains the address/distance pair fields. This is how far is a given address from
 * this router.
 */
public class NetworkDistancePair implements DatagramHeaderField {

    /**
     *     This is the network number of the remote network a route is available to.
     */
    private int network;

    /**
     * This is the distance to the remote network.
     */
    private int distance;

    /**
     * The constructor takes a 4 character Hex string. The first two characters are the network
     * number and the second two are the distance to that network.
     * @param networkDistance
     */
    public NetworkDistancePair(String networkDistance) {
        this.network = Integer.valueOf(networkDistance.substring(0,2),16);
        this.distance = Integer.valueOf(networkDistance.substring(2,4),16);
    }

    public NetworkDistancePair(int network, int distance){
        this.network = network;
        this.distance = distance;
    }

    public int getNetwork() {
        return network;
    }

    //TODO: Added it later (Not in the lab).
    public int getDistance() {
        return distance;
    }

    @Override
    public String toHexString() {
        return Utilities.padHexString(Integer.toHexString(network),1) +
                Utilities.padHexString(Integer.toHexString(distance),1);
    }

    @Override
    public String explainSelf() {
        return "Net/Dist: " + Utilities.padHexString(Integer.toHexString(network),1) + "/" +
                Utilities.padHexString(Integer.toHexString(distance),1);
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
