package com.renemoise.routerrmk.network.datagram_fields;

import com.renemoise.routerrmk.network.Constants;
import com.renemoise.routerrmk.network.datagram_fields.DatagramHeaderField;

/**
 * Created by Rene Moise on 1/22/2017.
 *
 * This class calculates the CRC value that will be used for error checking purposes.
 */

public class CRC implements DatagramHeaderField {

    private String crcValue; // type – this contains the fake string value of the address.


    //This is a constructor that works with a passed String. Truncate the string to 2 bytes
    // (4 hex characters) and store it.
    public CRC(String crcValue) {
        this.crcValue = crcValue.substring(0, Constants.LL2P_CRC_FIELD_LENGTH*2);

    }

    @Override
    public String toHexString() {
        return crcValue;
    }

    @Override
    public String explainSelf() {
        return String.format("CRC Field. Value: " + toAsciiString());
    }

    @Override
    public String toAsciiString() {

        StringBuilder crcAscii = new StringBuilder();
        char [] crcValueChar = crcValue.toCharArray();

        for(int i = 0; i < crcValue.length(); i++)
        {
            crcAscii.append((int)crcValueChar[i]);
        }
        return crcAscii.toString();
    }

    @Override
    public String toString() {
        return toHexString();
    }
}
