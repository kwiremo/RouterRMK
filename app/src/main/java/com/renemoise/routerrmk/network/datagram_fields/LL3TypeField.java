package com.renemoise.routerrmk.network.datagram_fields;

import com.renemoise.routerrmk.network.Constants;
import com.renemoise.routerrmk.network.datagrams.Datagram;
import com.renemoise.routerrmk.support.Utilities;

/**
 * Created by Rene Moise on 3/30/2017.
 */

/**
 * This represents the type of data that LL3P Protocol is carrying. It is important to know that
 * LL2P protocol and LL3P protocol  define their field differently. For example, the lL2P_IS_LL3P
 * defines the LL3P type as 0x8001 while the LL3 protocol defines LL3P_IS_TEXT as 0x8001.
 */
public class LL3TypeField implements DatagramHeaderField {

    //the only field value is the LL3 type field which is 0x8001.
    private int ll3TypeValue;

    public LL3TypeField() {
        this.ll3TypeValue = Constants.LL3P_TYPE_IS_TEXT;
    }

    @Override
    public String toHexString() {
        return Utilities.padHexString(Integer.toHexString(ll3TypeValue),2);
    }

    @Override
    public String explainSelf() {
        return "LL3 Type Field: " + toHexString();
    }

    @Override
    public String toAsciiString() {
        return Utilities.toAscciiCharactersOfEachHexByte(toHexString());
    }


    @Override
    public String toString() {
        return toHexString();
    }
}
