package com.renemoise.routerrmk.support;

import com.renemoise.routerrmk.network.Constants;
import com.renemoise.routerrmk.network.datagram_fields.CRC;
import com.renemoise.routerrmk.network.datagram_fields.DatagramHeaderField;
import com.renemoise.routerrmk.network.datagram_fields.DatagramPayloadField;
import com.renemoise.routerrmk.network.datagram_fields.LL2PAddressField;
import com.renemoise.routerrmk.network.datagram_fields.LL2PTypeField;
import com.renemoise.routerrmk.network.tablerecord.ARPRecord;
import com.renemoise.routerrmk.network.tablerecord.AdjacencyRecord;
import com.renemoise.routerrmk.network.tablerecord.RoutingRecord;
import com.renemoise.routerrmk.network.tablerecord.TableRecord;

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
            //TODO: This payload field needs to be better.
            case Constants.LL2P_PAYLOAD_FIELD:
                return new DatagramPayloadField(contents);
            case Constants.LL2P_CRC_FIELD:
                return  new CRC(contents);
            default:
                return null;
        }
    }

    //This method returns a TableReccord instance that is created at runtime.

    //TODO: This needs to be completed.
    public TableRecord getTableRecord(int recordType)
    {
        switch (recordType) {
            case Constants.ADJACENCY_RECORD:
                //TODO: This parameters are wrong. To fix later.
                return new AdjacencyRecord(0,null);

            case Constants.ARP_RECORD:
                return new ARPRecord();

            case Constants.ROUTING_RECORD:
                return new RoutingRecord();

            default:
                return null;
        }
    }
}
