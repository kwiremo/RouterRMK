package com.renemoise.routerrmk.network.datagram_fields;

import com.renemoise.routerrmk.network.Constants;
import com.renemoise.routerrmk.network.datagram_fields.DatagramHeaderField;
import com.renemoise.routerrmk.network.datagrams.Datagram;
import com.renemoise.routerrmk.network.datagrams.TextDatagram;
import com.renemoise.routerrmk.support.Factory;

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

    // This constructor would create a TextDatagram object using the passed in String.
    // It would make it possible for other classes to easily create payloads when a simple
    // string is to be contained here. This was public before we created a dedicated
    // datagramfield for the text payload field. Now it is private so that we restrict access
    //to other classes. Only this class can see it and can be used for debugging reasons.

    //TODO: Make it private after asking Prof.
    public DatagramPayloadField(String text)
    {
        packet = Factory.getInstance().getPayloadDatagram(Constants.LL2P_TYPE_IS_TEXT, text);
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
