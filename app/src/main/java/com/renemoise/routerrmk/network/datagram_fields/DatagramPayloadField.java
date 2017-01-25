package com.renemoise.routerrmk.network.datagram_fields;

import com.renemoise.routerrmk.network.datagram_fields.DatagramHeaderField;
import com.renemoise.routerrmk.network.datagrams.Datagram;

/**
 * Created by Rene Moise on 1/22/2017.
 */

// Adapter – this class adapts a Datagram object to a DatagramHeaderField object.
public class DatagramPayloadField implements DatagramHeaderField {

    Datagram packet;

    public DatagramPayloadField(Datagram packet) {
        this.packet = packet;
    }

    public DatagramPayloadField(String text)
    {
        // This constructor would create a TextDatagram object using the passed in String.
        // It would make it possible for other classes to easily create payloads when a simple
        // string is to be contained here.
    }

    public Datagram getPacket() {
        return packet;
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
