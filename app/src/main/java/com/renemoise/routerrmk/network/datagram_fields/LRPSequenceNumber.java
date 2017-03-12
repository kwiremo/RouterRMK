package com.renemoise.routerrmk.network.datagram_fields;

/**
 * Created by Rene Moise on 3/7/2017.
 */

import com.renemoise.routerrmk.support.Utilities;

/**
 * A class representing a four bit sequence number which is used to differentiate between
 * successive updates
 */
public class LRPSequenceNumber implements DatagramHeaderField {

    /**
     * This contains a unique Sequence number for this routing update. The field is only
     * 4 bits long so sequence numbers wrap from 15 to zero.
     */
    private int sequenceNumber;

    /**
     *     the constructor takes a 1 character Hex string.
     */
    public LRPSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = Integer.valueOf(sequenceNumber,16);
    }

    @Override
    public String toHexString() {
        return Integer.toHexString(sequenceNumber);
    }

    @Override
    public String explainSelf() {
        return "Sequence Number: " + sequenceNumber;
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
