package com.renemoise.routerrmk.network.datagram_fields;

/**
 * Created by Rene Moise on 2/15/2017.
 */

import com.renemoise.routerrmk.support.Utilities;

/**
 * This class will create a text payload.
 */
public class TextPayload implements DatagramHeaderField {

    //	This is a DatagramHeaderField object that contains only text.
    private String payload;

    //Constructor. Passed a payload value that is saved.
    public TextPayload(String payload) {
        this.payload = payload;
    }

    //returns the hexString value of the payload text.
    @Override
    public String toHexString() {
        return Utilities.toHexString(payload);
    }

    @Override
    public String explainSelf() {
        return String.format("Text datagram. Value: {0}", payload);
    }

    //Return ascii value of the payload.
    @Override
    public String toAsciiString() {
        return Utilities.toAsciiString(payload);
    }
}
