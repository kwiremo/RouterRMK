package com.renemoise.routerrmk.network.datagram_fields;

/**
 * Created by Rene Moise on 3/30/2017.
 */

import com.renemoise.routerrmk.support.Utilities;

/**
 * //This is a 2 byte field that contains a value which cycles from 0 to 65, 535.
 */
public class LL3PIdentifierField implements DatagramHeaderField {

    //This is the identifier value tha cycles between 0 and 65,535.
    private int identifier;

    public LL3PIdentifierField(String identifier) {
        this.identifier = Integer.valueOf(identifier, 16);
    }

    public LL3PIdentifierField(int identifier) {
        this.identifier = identifier;
    }


    @Override
    public String toHexString() {
        return Utilities.padHexString(Integer.toHexString(identifier),2);
    }

    @Override
    public String explainSelf() {
        return "Identifier: " + identifier;
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
