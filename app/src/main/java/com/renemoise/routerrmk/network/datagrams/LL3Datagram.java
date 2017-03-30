package com.renemoise.routerrmk.network.datagrams;

import android.util.Log;

import com.renemoise.routerrmk.UI.UIManager;
import com.renemoise.routerrmk.network.Constants;
import com.renemoise.routerrmk.network.datagram_fields.DatagramPayloadField;
import com.renemoise.routerrmk.network.datagram_fields.LL3PAddressField;
import com.renemoise.routerrmk.network.datagram_fields.LL3PChecksum;
import com.renemoise.routerrmk.network.datagram_fields.LL3PIdentifierField;
import com.renemoise.routerrmk.network.datagram_fields.LL3PTTLField;
import com.renemoise.routerrmk.network.datagram_fields.LL3TypeField;
import com.renemoise.routerrmk.support.Factory;

/**
 * Created by Rene Moise on 3/30/2017.
 */

/**
 * This class is responsible of creating an LL3P datagram. The LL3P datagram consist of 2 bytes of
 * source LL3P address field, 2 bytes of destination LL3P address field, 2 bytes of type, 2 bytes
 * of identifier field, 1 byte of time to live, the variable length of the payload field, and
 * finally the checksum.
 */

public class LL3Datagram implements Datagram {

    //A private field for the source LL3P Address value.
    private LL3PAddressField sourceLl3PAddressField;

    //A private field for the source LL3P Address value.
    private LL3PAddressField destLL3PAddressField;

    //A private field for the field type. The only valid type for now is 0x8001 – text payload.
    LL3TypeField ll3TypeField;

    // A private field for the Identifier. (2 bytes)
    LL3PIdentifierField identifierFieldValue;

    //A private field for the time to live. (1 byte). It has a max value
    // of 15. It contains the time to live of the packet.
    LL3PTTLField timeToLive;

    //A private field for the Payload
    DatagramPayloadField payloadFieldValue;

    //A private field for the checksum
    LL3PChecksum checksum;

    public LL3Datagram(LL3PAddressField sourceLl3PAddressField,
                       LL3PAddressField destLL3PAddressField, LL3TypeField ll3TypeField,
                       LL3PIdentifierField identifierFieldValue, LL3PTTLField timeToLive,
                       DatagramPayloadField payloadFieldValue, LL3PChecksum checksum) {
        this.sourceLl3PAddressField = sourceLl3PAddressField;
        this.destLL3PAddressField = destLL3PAddressField;
        this.ll3TypeField = ll3TypeField;
        this.identifierFieldValue = identifierFieldValue;
        this.timeToLive = timeToLive;
        this.payloadFieldValue = payloadFieldValue;
        this.checksum = checksum;
    }

    public LL3Datagram(byte[] LL3FrameString) {
        try {
            StringBuilder tempHolder = new StringBuilder();

            String frameString = new String(LL3FrameString);
            Log.e("FRAME", frameString);
            //Extract Destination Field
            this.sourceLl3PAddressField = (LL3PAddressField) Factory.getInstance().getDatagramHeaderField
                    (Constants.LL3P_SOURCE_ADDRESS, frameString.substring
                            (Constants.LL3P_SOURCE_ADDRESS_OFFSET * 2,
                                    Constants.LL3P_SOURCE_ADDRESS_OFFSET * 2 + Constants.LL3P_SOURCE_ADDRESS_FIELD_LENGTH * 2));

            Log.d("SOURCE", frameString.substring(0, 6));
            Log.d("DEST", frameString.substring(6, 12));
            //Extract Source Field
            this.destLL3PAddressField = (LL3PAddressField) Factory.getInstance().getDatagramHeaderField
                    (Constants.LL3P_DESTINATION_ADDRESS, frameString.substring
                            (Constants.LL3P_DEST_ADDRESS_OFFSET * 2,
                                    Constants.LL3P_DEST_ADDRESS_OFFSET * 2 + Constants.LL3P_DEST_ADD_FIELD_LENGTH * 2));

            //Extract Type Field
            this.ll3TypeField = (LL3TypeField) Factory.getInstance().getDatagramHeaderField
                    (Constants.LL3P_TYPE_FIELD, frameString.substring(Constants.LL3P_TYPE_FIELD_OFFSET * 2,
                            Constants.LL3P_TYPE_FIELD_OFFSET * 2 + Constants.LL3P_TYPE_FIELD_LENGTH * 2));

            //Extract Identifier Field
            this.identifierFieldValue = (LL3PIdentifierField) Factory.getInstance().getDatagramHeaderField
                    (Constants.LL3P_IDENTIFIER_FIELD, frameString.substring(Constants.LL3P_IDENTIFIER_FIELD_OFFSET * 2,
                            Constants.LL3P_IDENTIFIER_FIELD_OFFSET * 2 + Constants.LL3P_IDENTIFIER_FIELD_LENGTH * 2));

            //Extract Time to Live Field
            this.timeToLive = (LL3PTTLField) Factory.getInstance().getDatagramHeaderField
                    (Constants.LL3P_TTL_FIELD, frameString.substring(Constants.LL3P_TTL_FIELD_OFFSET * 2,
                            Constants.LL3P_TTL_FIELD_OFFSET * 2 + Constants.LL3P_TTL_FIELD_LENGTH * 2));


            //Extract Payload Field
            int paylodLength = LL3FrameString.length - Constants.LL3P_PAYLOAD_OFFSET * 2
                    - Constants.LL3P_CHECKSUM_FIELD_LENGTH * 2;

            //Make a payloadField passing datagrams.
            String payloadString = frameString.substring(Constants.LL3P_PAYLOAD_OFFSET*2,
                    Constants.LL3P_PAYLOAD_OFFSET*2 + paylodLength);
            TextDatagram datagramPacket = (TextDatagram) Factory.getInstance().getDatagram
                    (Constants.TEXT_DATAGRAM, payloadString);
            this.payloadFieldValue = new DatagramPayloadField(datagramPacket);

            //Get CRC Value.
            int CRC_Offset = Constants.LL3P_PAYLOAD_OFFSET * 2 + paylodLength;

            this.checksum = (LL3PChecksum) Factory.getInstance().getDatagramHeaderField
                    (Constants.LL3P_CHECKSUM_FIELD, frameString.substring(CRC_Offset,
                            CRC_Offset + Constants.LL3P_CHECKSUM_FIELD_LENGTH * 2));
        }
        catch (Exception e){
            UIManager.getInstance().disPlayMessage("Could not process the received packet.");
        }
    }

    @Override
    public String toHexString() {
        return String.format(getSourceLl3PAddressField().toHexString()+
                getDestLL3PAddressField().toHexString()+ getLl3TypeField().toHexString()+
                getIdentifierFieldValue().toHexString() + getTimeToLive().toHexString() +
                getPayloadFieldValue().toHexString()+ getChecksum().toHexString());
    }

    @Override
    public String toProtocolExplanationString() {
        return String.format(getSourceLl3PAddressField().explainSelf()+ "\n"+
                getDestLL3PAddressField().explainSelf()+ "\n"+ getLl3TypeField().explainSelf()+"\n"+
                getIdentifierFieldValue().explainSelf() + "\n"+getTimeToLive().explainSelf() +"\n"+
                getPayloadFieldValue().explainSelf()+ "\n"+getChecksum().explainSelf());
    }

    @Override
    public String toSummaryString() {
        if(payloadFieldValue != null){
            return payloadFieldValue.explainSelf();
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format(getSourceLl3PAddressField().toString()+
                getDestLL3PAddressField().toString()+ getLl3TypeField().toString()+
                getIdentifierFieldValue().toString() + getTimeToLive().toString() +
                getPayloadFieldValue().toString()+ getChecksum().toString());
    }

    public LL3PAddressField getSourceLl3PAddressField() {
        return sourceLl3PAddressField;
    }

    public void setSourceLl3PAddressField(LL3PAddressField sourceLl3PAddressField) {
        this.sourceLl3PAddressField = sourceLl3PAddressField;
    }

    public LL3PAddressField getDestLL3PAddressField() {
        return destLL3PAddressField;
    }

    public void setDestLL3PAddressField(LL3PAddressField destLL3PAddressField) {
        this.destLL3PAddressField = destLL3PAddressField;
    }

    public LL3TypeField getLl3TypeField() {
        return ll3TypeField;
    }

    public void setLl3TypeField(LL3TypeField ll3TypeField) {
        this.ll3TypeField = ll3TypeField;
    }

    public LL3PIdentifierField getIdentifierFieldValue() {
        return identifierFieldValue;
    }

    public void setIdentifierFieldValue(LL3PIdentifierField identifierFieldValue) {
        this.identifierFieldValue = identifierFieldValue;
    }

    public LL3PTTLField getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(LL3PTTLField timeToLive) {
        this.timeToLive = timeToLive;
    }

    public DatagramPayloadField getPayloadFieldValue() {
        return payloadFieldValue;
    }

    public void setPayloadFieldValue(DatagramPayloadField payloadFieldValue) {
        this.payloadFieldValue = payloadFieldValue;
    }

    public LL3PChecksum getChecksum() {
        return checksum;
    }

    public void setChecksum(LL3PChecksum checksum) {
        this.checksum = checksum;
    }
}
