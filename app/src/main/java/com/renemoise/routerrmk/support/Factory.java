package com.renemoise.routerrmk.support;

import android.provider.ContactsContract;

import com.renemoise.routerrmk.network.Constants;
import com.renemoise.routerrmk.network.datagram_fields.CRC;
import com.renemoise.routerrmk.network.datagram_fields.DatagramHeaderField;
import com.renemoise.routerrmk.network.datagram_fields.DatagramPayloadField;
import com.renemoise.routerrmk.network.datagram_fields.LL2PAddressField;
import com.renemoise.routerrmk.network.datagram_fields.LL2PTypeField;
import com.renemoise.routerrmk.network.datagram_fields.LL3PAddressField;
import com.renemoise.routerrmk.network.datagram_fields.LRPRouteCount;
import com.renemoise.routerrmk.network.datagram_fields.LRPSequenceNumber;
import com.renemoise.routerrmk.network.datagram_fields.NetworkDistancePair;
import com.renemoise.routerrmk.network.datagram_fields.TextPayload;
import com.renemoise.routerrmk.network.datagrams.ARPDatagram;
import com.renemoise.routerrmk.network.datagrams.Datagram;
import com.renemoise.routerrmk.network.datagrams.TextDatagram;
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
    public DatagramHeaderField getDatagramHeaderField(int fieldValue, String contents){
        switch (fieldValue){

            //LL2P Types
            case Constants.LL2P_DESTINATION_ADDRESS:
                return new LL2PAddressField(contents,false);
            case Constants.LL2P_SOURCE_ADDRESS:
                return new LL2PAddressField(contents, true);
            case Constants.LL2P_TYPE_FELD:
                return new LL2PTypeField(contents);
            case Constants.LL2P_PAYLOAD_FIELD:
                return new DatagramPayloadField(contents);
            case Constants.LL2P_TEXT_PAYLOAD_FIELD:
                return new TextPayload(contents);
            case Constants.LL2P_CRC_FIELD:
                return  new CRC(contents);

            //LL3 Types
            case Constants.LL3P_DESTINATION_ADDRESS:
                return new LL3PAddressField(contents,false);
            case Constants.LL3P_SOURCE_ADDRESS:
                return new LL3PAddressField(contents, true);
            case Constants.SEQUENCE_NUMBER:
                return new LRPSequenceNumber(contents);
            case Constants.COUNT:
                return new LRPRouteCount(contents);
            case Constants.NETWORK_DISTANCE:
                return new NetworkDistancePair(contents);

            default:
                return null;
        }
    }

    //This method returns a datagram payload
    public Datagram getDatagram(int DatagramType, String contents)
    {
        switch (DatagramType){
            case Constants.TEXT_DATAGRAM:
                return new TextDatagram(contents);
            case Constants.ARP_DATAGRAM:
                return new ARPDatagram(contents);
            default:
                return null;
        }
    }

    //This method returns a TableReccord instance that is created at runtime.
    //This returns the default Adjacency table. The user is expected to set the fields
    //Where when he create this instance.

    public TableRecord getTableRecord(int recordType)
    {
        switch (recordType) {
            case Constants.ADJACENCY_RECORD:

                return new AdjacencyRecord();

            case Constants.ARP_RECORD:
                return new ARPRecord();

            //case Constants.ROUTING_RECORD:
                //return new RoutingRecord();

            case  Constants.FORWARDING_RECORD:
                return new ForwardingRecord();

            default:
                return null;
        }
    }
}
