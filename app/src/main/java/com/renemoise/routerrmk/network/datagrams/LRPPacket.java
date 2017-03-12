package com.renemoise.routerrmk.network.datagrams;

import com.renemoise.routerrmk.network.Constants;
import com.renemoise.routerrmk.network.datagram_fields.LL3PAddressField;
import com.renemoise.routerrmk.network.datagram_fields.LRPRouteCount;
import com.renemoise.routerrmk.network.datagram_fields.LRPSequenceNumber;
import com.renemoise.routerrmk.network.datagram_fields.NetworkDistancePair;
import com.renemoise.routerrmk.support.Factory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.zip.CheckedOutputStream;

/**
 * Created by Rene Moise on 3/7/2017.
 */

public class LRPPacket implements Datagram {


    //This contains the LL3P address of the router that originated this update
    private LL3PAddressField sourceLL3P;

    private LRPSequenceNumber sequenceNumber;
    private LRPRouteCount count;

    // A List object containing all the network distance pairs in the routing update.
    private List<NetworkDistancePair> routes;

    Factory factory = Factory.getInstance();

    /**
     * 	The constructor must parse the byte array and create all the appropriate fields.
     *  This is  used when deconstructing packets that are received.
     * @param lrpPacket
     */
    public LRPPacket(byte[]  lrpPacket) {
        routes = new ArrayList<>();
        String lrppacket = new String(lrpPacket);

        sourceLL3P = (LL3PAddressField) factory.getDatagramHeaderField(
                Constants.LL3P_SOURCE_ADDRESS,lrppacket.substring(
                        Constants.SOURCE_LL3P_OFFSET,Constants.SOURCE_LL3P_LENGTH));

        sequenceNumber = (LRPSequenceNumber) factory.getDatagramHeaderField(
                Constants.SEQUENCE_NUMBER,lrppacket.substring(Constants.SEQUENCE_NUMBER_OFFSET,
                        Constants.SEQUENCE_NUMBER_OFFSET + Constants.SEQUENCE_NUMBER_LENGTH));

        count = (LRPRouteCount) factory.getDatagramHeaderField(
                Constants.COUNT, lrppacket.substring(
                        Constants.COUNT_OFFSET,Constants.COUNT_OFFSET + Constants.COUNT_LENGTH));

        String routesString = lrppacket.substring(Constants.PAIR_OFFSET,lrpPacket.length);

        for(int i = 0; i<routesString.length(); i = i+4) {
            NetworkDistancePair temp = (NetworkDistancePair) factory.getDatagramHeaderField(
                    Constants.NETWORK_DISTANCE, routesString.substring(i, i + Constants.PAIR_LENGTH));
            routes.add(temp);
        }
    }

    //	Constructor to create LRP Packets for transmission from this router to adjacent routers.
    public LRPPacket(LL3PAddressField sourceLL3P, LRPSequenceNumber sequenceNumber,
                     LRPRouteCount count, List<NetworkDistancePair> routes) {
        this.sourceLL3P = sourceLL3P;
        this.sequenceNumber = sequenceNumber;
        this.count = count;
        this.routes = routes;
    }


    @Override
    public String toHexString() {
        StringBuilder netDistHex = new StringBuilder();
        for(NetworkDistancePair pair : routes){
            netDistHex.append(pair.toHexString());
        }
        return sourceLL3P.toHexString() + sequenceNumber.toHexString() +
                count.toHexString() + netDistHex;
    }

    @Override
    public String toProtocolExplanationString() {
        StringBuilder netDistExpla = new StringBuilder();
        for(NetworkDistancePair pair : routes){
            netDistExpla.append(pair.explainSelf() + '\n');
        }
        return sourceLL3P.explainSelf() + '\n' + sequenceNumber.explainSelf() + '\n' +
                count.explainSelf() + '\n' + netDistExpla;
    }

    @Override
    public String toSummaryString() {
        return "LRP Packet";
    }

    @Override
    public String toString() {
        StringBuilder netDistStri = new StringBuilder();
        for(NetworkDistancePair pair : routes){
            netDistStri.append(pair.toString());
        }
        return sourceLL3P.toString() + sequenceNumber.toString() +
                count.toString()  + netDistStri;
    }

    public LL3PAddressField getSourceLL3P() {
        return sourceLL3P;
    }

    public LRPSequenceNumber getSequenceNumber() {
        return sequenceNumber;
    }

    public LRPRouteCount getCount() {
        return count;
    }

    public List<NetworkDistancePair> getRoutes() {
        return routes;
    }

    /**
     * This returns a byte array of ASCII characters ordered for transmission.
     * @return
     */
    //TODO: How to return getbytes.
    public byte[] getBytes(){
        StringBuilder netDistAscii = new StringBuilder();
        for(NetworkDistancePair pair : routes){
            netDistAscii.append(pair.toAsciiString());
        }
        netDistAscii.append(sourceLL3P.toAsciiString());
        netDistAscii.append(sequenceNumber.toAsciiString());
        netDistAscii.append(count.toAsciiString());
        return netDistAscii.toString().getBytes();
    }

    /**
     * This returns the number of routes in this update packet.
     * @return
     */
    public int getRouteCount(){
        return routes.size();
    }
}
