package com.renemoise.routerrmk.network.datagrams;

import com.renemoise.routerrmk.network.datagram_fields.TextPayload;
import com.renemoise.routerrmk.support.Factory;
import com.renemoise.routerrmk.support.Utilities;

/**
 * Created by Rene Moise on 1/31/2017.
 */

public class TextDatagram implements Datagram {

    //	This is a DatagramHeaderField object that contains only text.
    //TODO: Ask How to implement this.
    //private TextPayload payload;
    private String payload;

    public TextDatagram(String payload) {
        //TODO: Uncomment if the problem above is resolved.
        //this.payload = new TextPayload(payload);
        this.payload = payload;
    }

    @Override
    public String toHexString() {
        return Utilities.toHexString(payload);
    }

    @Override
    public String toProtocolExplanationString() {
        return String.format("Text datagram. Value: {0}", payload);
    }

    @Override
    public String toSummaryString() {
        return String.format("Text Payload");
    }

    @Override
    public String toString() {
        return toHexString();
    }
}
