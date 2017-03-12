package com.renemoise.routerrmk.network.daemon;

import com.renemoise.routerrmk.network.Constants;
import com.renemoise.routerrmk.network.datagram_fields.LL2PAddressField;
import com.renemoise.routerrmk.network.datagram_fields.LL3PAddressField;
import com.renemoise.routerrmk.network.datagrams.ARPDatagram;
import com.renemoise.routerrmk.network.table.TimedTable;
import com.renemoise.routerrmk.network.tablerecord.ARPRecord;
import com.renemoise.routerrmk.network.tablerecord.TableRecord;
import com.renemoise.routerrmk.support.BootLoader;
import com.renemoise.routerrmk.support.Factory;
import com.renemoise.routerrmk.support.LabException;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Rene Moise on 2/9/2017.
 */

/**
 * The ARP daemon is responsible for replying to the arp requests or sending arp requests.
 */
public class ARPDaemon extends Observable implements Observer,Runnable{
    private static ARPDaemon ourInstance = new ARPDaemon();
    public static ARPDaemon getInstance() {
        return ourInstance;
    }
    private TimedTable arpTable;    //This contains the arp table.
    private LL2Daemon ll2Daemon;

    private ARPDaemon() {
        arpTable = new TimedTable();
    }

    @Override
    public void update(Observable observable, Object o) {

        //When notified by the BootLoader the router is booted, then ARPDaemon would save a
        // reference to the LL2PDaemon.
        if(observable.getClass().equals(BootLoader.class)){
            ll2Daemon = LL2Daemon.getInstance();
            addObserver(LRPDaemon.getInstance());   //TODO: Added observer here.
            testARP();  //TODO: Remove after testing.
        }
    }

    private void addARPEntry(int ll2PAdd, int ll3PAdd){
        //The new arp record created will update time for this record (will remove the old one
        //and add a new one with a new age.
        ARPRecord arpRecord = (ARPRecord) Factory.getInstance().getTableRecord(Constants.ARP_RECORD);
        arpRecord.setLl2PAddress(ll2PAdd);
        arpRecord.setLl3pAddress(ll3PAdd);
        arpTable.addItem(arpRecord);
    }

    public void testARP()
    {
        //For testing purposes.

        addARPEntry(new LL2PAddressField("112233", false).getAddress(),
                new LL3PAddressField("26189", false).getAddress());


        addARPEntry(new LL2PAddressField("112255", false).getAddress(),
                new LL3PAddressField("26139", false).getAddress());

        addARPEntry(new LL2PAddressField("112266", false).getAddress(),
                new LL3PAddressField("26129", false).getAddress());
    }

    @Override
    public void run() {
        ArrayList<TableRecord> trashedRecords = arpTable.expireRecords(Constants.MAX_AGE_ALLOWED_ARP_RECORD);
        notifyObservers(trashedRecords);    //TODO: I passed deleted records here.
    }

    /**
     * This will return a List<Integer> of LL3P address Integers which contains all
     * LL3P addresses in the ARP table.  This list will be used by the LRP Daemon to insert routes
     * in the routing table for known, attached routers.
     * @return
     */
    List<Integer> getAttachedNodes(){
        ArrayList<Integer> attachedNoded = new ArrayList<>();

        //TODO: IS this how to do it?
        for (TableRecord record:arpTable.getTableArrayList()) {
            if(record instanceof ARPRecord)
                attachedNoded.add(((ARPRecord) record).getLl3pAddress());
        }
        return attachedNoded;
    }

    /**
     *
     * @param ll2pAddress
     * @param datagram
     *
     * This method is called when an ARP reply is received by the router. This method simply
     * updates the table record (adding or touching as required) with the LL2P-LL3P address pair.
     */
    void processARPReply(int ll2pAddress, ARPDatagram datagram){

        //Add the pair to the table and chill!
        addARPEntry(ll2pAddress,datagram.getPayloadValue());
    }

    /**
     * This method will be called by the LL2P Daemon when the LL2P daemon receives an ARP request.
     * The LL2P daemon must pass the address pair for the remote router that requested an ARP Reply.
     */
    void processARPRequest(int ll2pAddress, ARPDatagram datagram){

        //Add the pair to the table and send send a reply.
        addARPEntry(ll2pAddress,datagram.getPayloadValue());

        //Create a datagram with our LL3P address.
        ARPDatagram arpDatagram = (ARPDatagram) Factory.getInstance().getDatagram(
                Constants.ARP_DATAGRAM, Constants.LL3P_ROUTER_ADDRESS_VALUE);

        ll2Daemon.sendLL2PFrame(arpDatagram,ll2pAddress,Constants.LL2P_TYPE_IS_ARP_REPLY);
    }

    //this method creates an ARP request datagram and requests the LL2P Daemon send it.

    public void sendARPRequest(int ll2pAddress){

        ARPDatagram arpDatagram = (ARPDatagram) Factory.getInstance().getDatagram(
                Constants.ARP_DATAGRAM, Constants.LL3P_ROUTER_ADDRESS_VALUE);

        ll2Daemon.sendLL2PFrame(arpDatagram,ll2pAddress,Constants.LL2P_TYPE_IS_ARP_REQUEST);
    }

    /**
     * This method is the reason the ARP daemon exists. When the LL3P daemon needs to send an
     * LL3P Packet, it needs to know the MAC address for that packet. This method uses the LL3P
     * address as a key into the table to retrieve the ARP Record for the LL3P address. The ARP
     * record contains the matching MAC address (LL2P address). This method returns the LL2P
     * address.
     */

    public int	getMACAddress(int ll3PAddress) throws Exception{
        for (TableRecord record : arpTable.getTableArrayList()) {
            if (record instanceof ARPRecord) {
                if (((ARPRecord) record).getLl3pAddress() == ll3PAddress)
                    return ((ARPRecord) record).getLl2PAddress();
            }
        }
        throw new LabException("MAC address for LL3P Address: " + ll3PAddress + " could not be found");
    }

    /**
     * This method returns an ArrayList containing the ARP table entries.
     * Note that the TimedTable class already has a method to return it’s contents in such a
     * form needed here. This method is used by the TableUI classes to display the List on
     * the screen.
     */

    //TODO: Never used. Where should be used?
    public List<TableRecord> getARPRecordList() {
        return arpTable.getTableArrayList();
    }

    // This will be needed by the SingleTableUI.
    // Conflicts with the getArptable() that returns an arrayList.
    public TimedTable getArpTable() {
        return arpTable;
    }
}
