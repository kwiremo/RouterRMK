package com.renemoise.routerrmk.network.datagrams;

import com.renemoise.routerrmk.network.datagram_fields.CRC;
import com.renemoise.routerrmk.network.datagram_fields.DatagramPayloadField;
import com.renemoise.routerrmk.network.datagram_fields.LL2PAddressField;
import com.renemoise.routerrmk.network.datagram_fields.LL2PTypeField;

/**
 * Created by Rene Moise on 1/22/2017.
 */

public class LL2PFrame implements Datagram {
    private LL2PAddressField destinationAddress;
    private LL2PAddressField sourceAddress;
    private LL2PTypeField type;
    private DatagramPayloadField payload ;
    private CRC crc;

    public LL2PFrame(LL2PAddressField destinationAddress, LL2PAddressField sourceAddress,
                     LL2PTypeField type, DatagramPayloadField payload, CRC crc) {
        this.destinationAddress = destinationAddress;
        this.sourceAddress = sourceAddress;
        this.type = type;
        this.payload = payload;
        this.crc = crc;
    }

    public LL2PFrame(Byte [] LL2FrameSpecs){
        //A byte array is provided and this method must extract the correct sets of bytes and
        // convert them to the fields, which are retrieved from the factory.
        // In order to make this work you have to follow the directions below for new Constants.

    }

    private void makePayloadField()
    {

    }
    @Override
    public String toHexString() {
        return null;
    }

    @Override
    public String toProtocolExplanationString() {
        return null;
    }

    @Override
    public String toSummaryString() {
        return null;
    }

    public LL2PAddressField getDestinationAddress() {
        return destinationAddress;
    }

    public LL2PAddressField getSourceAddress() {
        return sourceAddress;
    }

    public LL2PTypeField getType() {
        return type;
    }

    public DatagramPayloadField getPayload() {
        return payload;
    }

    public CRC getCrc() {
        return crc;
    }
}

