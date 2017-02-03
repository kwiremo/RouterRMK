package com.renemoise.routerrmk.network.datagram_fields;

import com.renemoise.routerrmk.network.Constants;
import com.renemoise.routerrmk.network.datagram_fields.DatagramHeaderField;
import com.renemoise.routerrmk.support.Utilities;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Rene Moise on 1/22/2017.
 *
 * The class is for the type field of every datagram. It implements DatagramHeaderField.
 */

public class LL2PTypeField implements DatagramHeaderField {

    private int type; //contains the integer value of the typeValue of the payload.
    //this is a String containing the explanation of the content of this field. For example,
    // it might contain the string “LL3P typeValue (0x8001)”
    private String explanation;

    //This is a constructor that works with a passed integer to fill in all the fields.
    public LL2PTypeField(int typeValue) {
        this.type = typeValue;
    }

    //this method uses the internal fields to create an explanation string.
    public LL2PTypeField(String typeValueStringHex) {

        try
        {
            this.type = (Integer.valueOf(typeValueStringHex,16));
        }
        catch (NumberFormatException e)
        {
            System.err.println("LL2PTypeField: Type field value could not be parsed");
        }
    }

    @Override
    public String toHexString() {
        String addTemp = Integer.toHexString(type);
        return Utilities.padHexString(addTemp, Constants.LL2P_TYPE_FIELD_LENGTH);
    }

    @Override
    public String explainSelf() {
        setExplanation();
        return explanation;
    }

    @Override
    public String toAsciiString() {
        String hexValue = toHexString();
        StringBuilder asciiValue = new StringBuilder();

        //I am changing every 2 characters to ascii character.
        for(int i = 0; i < Constants.LL2P_TYPE_FIELD_LENGTH; i++)
        {
            int subLength = 2;
            int temp = Integer.valueOf(hexValue.substring
                    (i*subLength, (i*subLength)+subLength),16);
            asciiValue.append(((char)temp));
        }
        return asciiValue.toString();
    }

    //this method uses the internal fields to create an explanation string.
    private void setExplanation()
    {
        String contentsType;
        switch (type){
            case Constants.LL2P_TYPE_IS_TEXT:
                contentsType = "TEXT";
                break;
            case Constants.LL2P_TYPE_IS_ARP_REPLY:
                contentsType = "ARP REPLY";
                break;
            case Constants.LL2P_TYPE_IS_ARP_REQUEST:
                contentsType = "ARP REQUEST";
                break;
            case Constants.LL2P_TYPE_IS_ECHO_REPLY:
                contentsType = "ECHO REPLY";
                break;
            case Constants.LL2P_TYPE_IS_ECHO_REQUEST:
                contentsType = "ECHO REQUEST";
                break;
            case Constants.LL2P_TYPE_IS_LL3P:
                contentsType = "LL3 Frame";
                break;
            case Constants.LL2P_TYPE_IS_LRP:
                contentsType = "LRP";
                break;
            case Constants.LL2P_TYPE_IS_RESERVED:
                contentsType = "RESERVED";
                break;
            default:
                return;
        }
        explanation = "Value: " + toHexString() + "Packet Type: " + contentsType;
    };

    @Override
    public String toString() {
        return toHexString();
    }
}
