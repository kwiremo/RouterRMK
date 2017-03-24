package com.renemoise.routerrmk.network.datagrams;

/**
 * Created by Rene Moise on 2/23/2017.
 */

import com.renemoise.routerrmk.network.Constants;
import com.renemoise.routerrmk.network.datagram_fields.LL3PAddressField;
import com.renemoise.routerrmk.support.Factory;

/**
 * This class is an ARP Datagram that contains only an LL3P Address. This class is decorator.
 */
public class ARPDatagram implements Datagram {

    /**
     * This is a DatagramHeaderField that contains an LL3P Address.
     */
    private LL3PAddressField ll3PAddressField;
    int payloadValue;

    //
    public ARPDatagram(String ll3PAddressField) {
        this.ll3PAddressField = (LL3PAddressField )Factory.getInstance().getDatagramHeaderField(
                Constants.LL3P_SOURCE_ADDRESS,ll3PAddressField);

        payloadValue = this.ll3PAddressField.getAddress();
    }

    public int getPayloadValue() {
        return payloadValue;
    }

    /**
     *
     * @return
     */


    @Override
    public String toHexString() {
        return ll3PAddressField.toHexString();
    }

    @Override
    public String toProtocolExplanationString() {
        return "ARP Datagram. Value: " + toHexString();
    }

    @Override
    public String toSummaryString() {
        return "ARP Payload";
    }

    @Override
    public String toString() {
        return toHexString();
    }
}
