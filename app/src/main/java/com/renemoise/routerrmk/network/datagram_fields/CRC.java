package com.renemoise.routerrmk.network.datagram_fields;

import com.renemoise.routerrmk.network.datagram_fields.DatagramHeaderField;

/**
 * Created by Rene Moise on 1/22/2017.
 */

public class CRC implements DatagramHeaderField {

    private String crcValue; // type – this contains the fake string value of the address.


    //This is a constructor that works with a passed String. Truncate the string to 2 bytes
    // (4 hex characters) and store it.
    public CRC(String crcValue) {
        this.crcValue = crcValue;
    }

    @Override
    public String toHexString() {
        return null;
    }

    @Override
    public String explainSelf() {
        return null;
    }

    @Override
    public String toAsciiString() {
        return null;
    }
}
