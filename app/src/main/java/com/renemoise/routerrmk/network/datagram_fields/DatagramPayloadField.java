package com.renemoise.routerrmk.network.datagram_fields;

import com.renemoise.routerrmk.network.datagram_fields.DatagramHeaderField;
import com.renemoise.routerrmk.network.datagrams.Datagram;
import com.renemoise.routerrmk.network.datagrams.TextDatagram;

/**
 * Created by Rene Moise on 1/22/2017.
 *
 * The datagramPayloadHeaderField is the the class of the datagram payloads. It implements
 * DatagramHeaderField.
 */

// Adapter – this class adapts a Datagram object to a DatagramHeaderField object.
public class DatagramPayloadField implements DatagramHeaderField {

    Datagram packet;

    public DatagramPayloadField(Datagram packet) {
        this.packet = packet;
    }

    //TODO: Ask
    public DatagramPayloadField(String text)
    {
        // This constructor would create a TextDatagram object using the passed in String.
        // It would make it possible for other classes to easily create payloads when a simple
        // string is to be contained here.

        packet = new TextDatagram(text);
    }

    public Datagram getPacket() {
        return packet;
    }

    @Override
    public String toHexString() {
        return packet.toHexString();
    }

    @Override
    public String explainSelf() {
        return String.format("LL2P Payload Field. Value: " + toAsciiString());
    }

    //TODO: ASK The professor
    @Override
    public String toAsciiString() {
        String hexValue = toHexString();
        StringBuilder asciiValue = new StringBuilder();

        for(int i = 0; i < hexValue.length()/2; i++)
        {
            int subLen = 2;
            int temp = Integer.valueOf(hexValue.substring(i*subLen, (i*subLen)+subLen),16);
            asciiValue.append(((char)temp));
        }
        return asciiValue.toString();
    }

    @Override
    public String toString() {
        return toAsciiString();
    }
}
