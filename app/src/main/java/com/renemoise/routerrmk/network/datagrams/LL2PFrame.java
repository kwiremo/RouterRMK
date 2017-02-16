package com.renemoise.routerrmk.network.datagrams;

import android.util.Log;

import com.renemoise.routerrmk.network.Constants;
import com.renemoise.routerrmk.network.datagram_fields.CRC;
import com.renemoise.routerrmk.network.datagram_fields.DatagramPayloadField;
import com.renemoise.routerrmk.network.datagram_fields.LL2PAddressField;
import com.renemoise.routerrmk.network.datagram_fields.LL2PTypeField;
import com.renemoise.routerrmk.support.Factory;

/**
 * Created by Rene Moise on 1/22/2017.
 *
 * This class is responsible of building a Lab Layer 2 frame (LL2 Frame).
 * It implements datagram interface.
 *
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

    /**A byte array is provided and this method must extract the correct sets of bytes and
     convert them to the fields, which are retrieved from the factory.
     */
    public LL2PFrame(byte[] LL2FrameSpecs){
        StringBuilder tempHolder = new StringBuilder();

        String frameString = new String(LL2FrameSpecs);
        Log.e("FRAME", frameString);
        //Extract Destination Field
        this.destinationAddress = (LL2PAddressField) Factory.getInstance().getDatagramHeaderField
                (Constants.LL2P_DESTINATION_ADDRESS, frameString.substring
                        (Constants.LL2P_DEST_ADDRESS_OFFSET*2,
                                Constants.LL2P_DEST_ADDRESS_OFFSET*2 + Constants.LL2P_DEST_ADD_FIELD_LENGTH*2));

        Log.d("DEST", frameString.substring(0,6));
        Log.d("SOURCE", frameString.substring(6,12));
        //Extract Source Field
        this.sourceAddress = (LL2PAddressField) Factory.getInstance().getDatagramHeaderField
                (Constants.LL2P_SOURCE_ADDRESS, frameString.substring
                        (Constants.LL2P_SOURCE_ADDRESS_OFFSET*2,
                                Constants.LL2P_SOURCE_ADDRESS_OFFSET*2+Constants.LL2P_SOURCE_ADDRESS_FIELD_LENGTH*2));

        //Extract Type Field
        this.type = (LL2PTypeField) Factory.getInstance().getDatagramHeaderField
                (Constants.LL2P_TYPE_FELD, frameString.substring(Constants.LL2P_TYPE_FIELD_OFFSET*2,
                        Constants.LL2P_TYPE_FIELD_OFFSET*2 + Constants.LL2P_TYPE_FIELD_LENGTH*2));


        //Extract Payload Field
        int paylodLength = LL2FrameSpecs.length - Constants.LL2P_PAYLOAD_OFFSET*2
                - Constants.LL2P_CRC_FIELD_LENGTH*2;
        Log.e("LEN", frameString.length()+"");
        Log.e("PAYloadLEn", paylodLength+"");

        this.payload = (DatagramPayloadField) Factory.getInstance().getDatagramHeaderField
                (Constants.LL2P_PAYLOAD_FIELD, frameString.substring(Constants.LL2P_PAYLOAD_OFFSET*2,
                        Constants.LL2P_PAYLOAD_OFFSET*2 + paylodLength));

        //Get CRC Value.
        int CRC_Offset = Constants.LL2P_PAYLOAD_OFFSET*2 + paylodLength;

        this.crc = (CRC) Factory.getInstance().getDatagramHeaderField
                (Constants.LL2P_CRC_FIELD, frameString.substring(CRC_Offset,
                        CRC_Offset + Constants.LL2P_CRC_FIELD_LENGTH*2));
    }

    //TODO: Ask the professor
    private void makePayloadField()
    {

    }

    @Override
    public String toString() {
        return String.format(getDestinationAddress().toString()+
                getSourceAddress().toString()+ getType().toString()+
                getPayload().toString()+ getCrc().toString());
    }

    @Override
    public String toHexString() {

        return String.format(getDestinationAddress().toHexString()+
                getSourceAddress().toHexString()+ getType().toHexString()+
                getPayload().toHexString() + getCrc().toHexString());
    }

    public String toHexStringFormatted() {

        return String.format("{0}\t{1}", getDestinationAddress().toHexString()+
                getSourceAddress().toHexString()+ getType().toHexString()+
                getPayload().toHexString() + getCrc().toHexString());
    }

    @Override
    public String toProtocolExplanationString() {
        return String.format(getDestinationAddress().explainSelf()+"\n"+
                getSourceAddress().explainSelf()+"\n"+ getType().explainSelf()+"\n"+
                getPayload().explainSelf()+"\n"+ getCrc().explainSelf());
    }

    @Override
    public String toSummaryString() {
        return String.format("LL2P Frame");
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

