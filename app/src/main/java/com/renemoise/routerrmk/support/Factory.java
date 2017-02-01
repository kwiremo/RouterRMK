package com.renemoise.routerrmk.support;

import com.renemoise.routerrmk.network.Constants;
import com.renemoise.routerrmk.network.datagram_fields.CRC;
import com.renemoise.routerrmk.network.datagram_fields.DatagramHeaderField;
import com.renemoise.routerrmk.network.datagram_fields.DatagramPayloadField;
import com.renemoise.routerrmk.network.datagram_fields.LL2PAddressField;
import com.renemoise.routerrmk.network.datagram_fields.LL2PTypeField;

import java.util.Observable;

/**
 * Created by Rene Moise on 1/22/2017.
 *
 * The factory will instantiate all the objects we need at runtime.
 */
public class Factory extends Observable{
    private static Factory ourInstance = new Factory();

    public static Factory getInstance() {
        return ourInstance;
    }

    private Factory() {
    }

    //This method returns a datagram field. This can be any field for any layer in the router.
    public DatagramHeaderField getDatagramHeaderField(int FieldValue, String contents){
        switch (FieldValue){
            case Constants.LL2P_DESTINATION_ADDRESS:
                return new LL2PAddressField(contents,false);
            case Constants.LL2P_SOURCE_ADDRESS:
                return new LL2PAddressField(contents, true);
            case Constants.LL2P_TYPE_FELD:
                return new LL2PTypeField(contents);
            case Constants.LL2P_PAYLOAD_FIELD:
                return new DatagramPayloadField(contents);
            case Constants.LL2P_CRC_FIELD:
                return  new CRC(contents);
            default:
                return null;
        }
    }
}
