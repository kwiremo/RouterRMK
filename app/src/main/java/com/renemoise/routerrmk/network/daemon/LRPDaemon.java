package com.renemoise.routerrmk.network.daemon;

import android.util.Log;

import com.renemoise.routerrmk.network.Constants;
import com.renemoise.routerrmk.network.datagram_fields.LL3PAddressField;
import com.renemoise.routerrmk.network.datagram_fields.LRPRouteCount;
import com.renemoise.routerrmk.network.datagram_fields.LRPSequenceNumber;
import com.renemoise.routerrmk.network.datagram_fields.NetworkDistancePair;
import com.renemoise.routerrmk.network.datagrams.LRPDatagram;
import com.renemoise.routerrmk.network.table.RoutingTable;
import com.renemoise.routerrmk.network.table.TimedTable;
import com.renemoise.routerrmk.network.tablerecord.ARPRecord;
import com.renemoise.routerrmk.network.tablerecord.RoutingRecord;
import com.renemoise.routerrmk.network.tablerecord.TableRecord;
import com.renemoise.routerrmk.support.BootLoader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Rene Moise on 2/23/2017.
 */
public class LRPDaemon implements Runnable, Observer{
    private static LRPDaemon ourInstance = new LRPDaemon();

    /**
     * you will need the reference for the ArpDaemon.  The LRP Daemon gets information about
     * adjacent networks from the ARP table. You’ll use this when you create send and send routing
     * updates because you send a routing table to every adjacent router in the ARP table.
     */
    private ARPDaemon arpDaemon;

    /**
     *  This is the table that contains ALL known routes.
     */
    private RoutingTable routeTable;

    /**
     *  This table contains the best routes for each known network, including ours
     *  S(which will be distance zero).
     */
    private RoutingTable forwardingTable;

    /**
     *     This is the reference to the layer2Daemon so you can send LRP Packets to the
     *     LL2PDaemon for framing and transmission.
     */
    private LL2Daemon  layer2Daemon;

    /**
     *   This is a private integer that starts at zero. Each time a new LRP is transmitted this
     *   is incremented and used as the sequence number in the LRP.  The maximum sequence number
     *   in an LRP is 15 so this number is reset to zero when it reaches 16.
     */
    private int sequenceNumber;

    private LRPDaemon() {
        routeTable = new RoutingTable();
        forwardingTable = new RoutingTable();
        sequenceNumber = 0;
    }


    public static LRPDaemon getInstance() {
        return ourInstance;
    }
    /**
     * This method is called by the Scheduler.  It forces its work onto the UI thread and
     * calls the method updateRoutes.
     */
    @Override
    public void run() {
        updateRoutes();
    }

    private void updateRoutes() {
        // Expire all routes in all tables
        forwardingTable.expireRecords(Constants.MAX_AGE_ALLOWED_LRP_RECORD);
        routeTable.expireRecords(Constants.MAX_AGE_ALLOWED_LRP_RECORD);

        // Add a route to the routing table (or reset its timer) for this router’s network, at a
        // distance of zero.
        routeTable.addNewRoute(new RoutingRecord(new LL3PAddressField
                (Constants.LL3P_ROUTER_ADDRESS_VALUE, true).getNetworkNumber(),
                Constants.ROUTER_DISTANCE, new LL3PAddressField(
                        Constants.LL3P_ROUTER_ADDRESS_VALUE, true).getAddress()));

	    //Add a route to the routing table for each adjacent router’s network, at a distance of one.
        // This information is gained from the ARP table and your router is considered the source
        // for this information. The neighbor is, of course, the next hop.
	    //todo: IMPLEMENT THIS.
        for (TableRecord record:arpDaemon.getARPRecordList()) {
            LL3PAddressField adjacentHop = new LL3PAddressField(
                    Integer.toHexString(((ARPRecord)record).getLl3pAddress()), true);
            RoutingRecord temp = new RoutingRecord(adjacentHop.getNetworkNumber(),
                    Constants.ADJACENT_HOP_DISTANCE, new LL3PAddressField(
                    Constants.LL3P_ROUTER_ADDRESS_VALUE, true).getAddress());
            routeTable.addNewRoute(temp);
        }


        //Get the list of best routes from the routing table and hand it to the forwarding table.
        // The forwarding table will use this list to replace, update, or add routes.
        //todo: implement this.
        List<RoutingRecord> bestRoutes = routeTable.getBestRoutes();
        forwardingTable.addRoutes(bestRoutes);

        //Using the list of adjacent nodes from step 4 above send a routing update to every
        // known neighbor. You will exclude routes learned from that router in this update in order
        // to avoid the count-to-infinity problem that exists with Distance-Vector routing protocols.
        //todo: implement this.

        for (TableRecord record:arpDaemon.getARPRecordList()) {
            LL3PAddressField myAddress = new LL3PAddressField(
                    Constants.LL3P_ROUTER_ADDRESS_VALUE, true);
            LRPSequenceNumber lrpSequenceNumber = new LRPSequenceNumber(getCurrentSequenceNumber());

            //Exclude all the routes learned from  this node.
            List<RoutingRecord> listToSend = forwardingTable.getRouteListExcluding(record.getKey());
            LRPRouteCount lrpCount = new LRPRouteCount(Integer.toHexString(listToSend.size()));

            ArrayList<NetworkDistancePair> netDistPairs = new ArrayList<>();
            for(int i = 0; i  < listToSend.size(); i++){
                RoutingRecord temp = listToSend.get(i);
                netDistPairs.add(new NetworkDistancePair(temp.getNetworkNumber(), temp.getDistance()));
            }

            //Create an LRP datagram to send
            LRPDatagram lrpDatagram = new LRPDatagram(myAddress, lrpSequenceNumber, lrpCount,
                    netDistPairs);
            layer2Daemon.sendLL2PFrame(lrpDatagram, record.getKey(), Constants.LL2P_TYPE_IS_LRP);
        }

    }

    @Override
    public void update(Observable observable, Object o) {
        if(observable.getClass().equals(BootLoader.class)) {
            arpDaemon = ARPDaemon.getInstance();
            layer2Daemon = LL2Daemon.getInstance();

        }
        else if(observable.getClass().equals(ARPDaemon.class)) {
            List<TableRecord> deletedRecords = (ArrayList<TableRecord>) o;

            for (int i = 0; i < deletedRecords.size(); i++){
                forwardingTable.removeRoutesFrom(deletedRecords.get(i).getKey());
                routeTable.removeRoutesFrom(deletedRecords.get(i).getKey());
            }
        }
    }

    //Getter for routing table
    public RoutingTable getRoutingTable() {
        return routeTable;
    }

    /**
     *     This will provide a method to return an ArrayList containing the routes in the
     *     routing table.
     */
    public List<TableRecord> getRoutingTableAsList(){
        return routeTable.getTableArrayList();
    }

    //Getter for forwarding table.
    public RoutingTable getForwardingTable(){
        return forwardingTable;
    }

    /**
     *    This will provide a method to return an ArrayList containing the routes in the forwarding
     *    table.
     */
    public List<TableRecord> getForwardingTableAsList(){
        return forwardingTable.getTableArrayList();
    }

    /**
     * This method will be called by the Layer2Daemon when an LRP Packet is received.
     * It does two important things. First it ‘touches’ the ARP entry that contains the LL2P address
     * that sent us this LRP packet.  Second, it unpacks the routing update and adds or updates
     * every route in the routing table. If the routing table changed, it also updates the
     * forwarding table and notifies the UIManager that the screen display of the forwarding
     * table needs to be updated.
     * @param lrpPackt
     * @param ll2PSource
     */
    public void receiveNewLRP(byte[] lrpPackt, int ll2PSource){
        arpDaemon.getArpTable().touch(ll2PSource);
        LRPDatagram lrpDatagram = new LRPDatagram(lrpPackt);
        //TODO: IMplement later.
        //routeTable.addRoutes(lrpDatagram.getRo);
    }

    /**
     *  This method receives a byte array that are the bytes of a receive LRP. It creates an LRP
     *  object from these bytes and then uses the routing update to update the routing table. If
     *  the routing table changes as a result of this update then the forwarding table must be
     *  updated as well.
     * @param lrp
     */
    public void processLRP(byte[] lrp){
        processLRPPacket(new LRPDatagram(lrp));
    }

    /**
     *     This method is used when a new update is to be sent to a neighbor. The LRP Daemon must
     *     get the LL2P address of the neighbor from the ARP Daemon and use the LL2P daemon to send
     *     an LRP packet.
     * @param packet
     * @param ll3Paddress
     */

    private void sendUpdate(LRPDatagram packet, int ll3Paddress) {
        try {
            int ll2pAdd = arpDaemon.getMACAddress(ll3Paddress);
            layer2Daemon.sendLL2PFrame(packet, ll2pAdd, Constants.LL2P_TYPE_IS_LRP);
        }
        catch (Exception e)
        {
            Log.e(getClass().toString(), e.getMessage());
        }
    }

    /**
     *     This method is given an LRP datagram. It is usually called by the LL2PDaemon.
     *     You should use the information in the LRP datagram to update the routing table,
     *     and if changes were made to the routing table, you should also update the forwarding
     *     table.
     * @param lrp
     */
    public void processLRPPacket(LRPDatagram lrp){
        List<NetworkDistancePair> receivedRoutes = lrp.getRoutes();
        List<RoutingRecord> newRecords = new ArrayList<>();
        for(int i = 0; i < receivedRoutes.size(); i++){
            NetworkDistancePair aPair = receivedRoutes.get(i);
            RoutingRecord temp = new RoutingRecord(aPair.getNetwork(),
                    aPair.getDistance(), lrp.getSourceLL3P().getAddress());
            newRecords.add(temp);
        }
        routeTable.addRoutes(newRecords);
        forwardingTable.addRoutes(routeTable.getBestRoutes());
    }

    private int getCurrentSequenceNumber(){
        sequenceNumber++;
        return sequenceNumber % 16;
    }
}
