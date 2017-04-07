package com.renemoise.routerrmk.network.daemons;

import com.renemoise.routerrmk.UI.Messenger;
import com.renemoise.routerrmk.UI.UIManager;
import com.renemoise.routerrmk.network.Constants;
import com.renemoise.routerrmk.network.datagram_fields.DatagramPayloadField;
import com.renemoise.routerrmk.network.datagram_fields.LL3PAddressField;
import com.renemoise.routerrmk.network.datagram_fields.LL3PChecksum;
import com.renemoise.routerrmk.network.datagram_fields.LL3PIdentifierField;
import com.renemoise.routerrmk.network.datagram_fields.LL3PTTLField;
import com.renemoise.routerrmk.network.datagram_fields.LL3TypeField;
import com.renemoise.routerrmk.network.datagrams.LL3Datagram;
import com.renemoise.routerrmk.network.datagrams.TextDatagram;
import com.renemoise.routerrmk.support.BootLoader;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Rene Moise on 2/23/2017.
 */

/**
 * The LL3P Daemon is responsible for forwarding layer 3 packets.  To do this it relies on the
 * routing daemon for next hop information when a packet should be forwarded.
 * There are two cases when a layer 3 packet arrives. In both cases the layer 2 daemon will pass the
 * layer 3 packet to the layer 3 daemon along with the source layer 2 address. The layer 3 daemon
 * will notify the arp daemon that
 */

public class LL3PDaemon implements Observer{
    //A private field to our instance. The only one existing.
    private static LL3PDaemon ourInstance = new LL3PDaemon();

    //A reference to the arpDaemon
    private ARPDaemon arpDaemon;

    //A reference to the layer 2 daemon. o	This is the reference to the layer2Daemon so you can
    // send layer 3 Packets to the LL2PDaemon for framing and transmission.
    private LL2Daemon ll2Daemon;

    //A reference to LRP Daemon. o	This is the reference to the LRPDaemon so you can get next
    // hop information when forwarding layer 3 packets.
    private LRPDaemon lrpDaemon;

    //This field contains the current identifier value. It cycles between 0 - 65,535;
    private int identifier;

    //This field contains the current time to live value. Whenever we create a packet, we will
    //assign it a value of 15;
    private int timeToLive;

    private LL3PDaemon() {
    }

    //Gets my router's LL3P address
    public String getMyLL3PAddress(){
        return Constants.LL3P_ROUTER_ADDRESS_VALUE;
    }

    /**
     * This static method returns the only instantiated object.
     * @return
     */
    public static LL3PDaemon getInstance() {
        return ourInstance;
    }

    @Override
    public void update(Observable observable, Object o) {
        if(observable.getClass().equals(BootLoader.class)) {
            arpDaemon = ARPDaemon.getInstance();
            ll2Daemon = LL2Daemon.getInstance();
            lrpDaemon = LRPDaemon.getInstance();
            identifier = 0;
            timeToLive = 15;
        }
    }

    /**
     * This method will create a layer 3 packet object using the string and destination
     * address. It will then ask the method ‘sendLL3PToNextHop(LL3Datagram) to transmit it to the
     * appropriate neighbor.
     * @param message
     * @param layer3Address
     */
    public void sendPayload(String message, Integer layer3Address) {
        LL3PAddressField sourceLl3PAddressField = new LL3PAddressField(getMyLL3PAddress(), true);
        LL3PAddressField destLl3PAddressField = new LL3PAddressField(layer3Address, true);
        LL3TypeField ll3TypeField = new LL3TypeField();
        LL3PIdentifierField ll3PIdentifierField = new LL3PIdentifierField(getCurrentIdentifier());
        LL3PTTLField timeToLive = new LL3PTTLField(this.timeToLive);
        DatagramPayloadField payloadField = new DatagramPayloadField(new TextDatagram(message));
        LL3PChecksum checksum = new LL3PChecksum("2619");

        //Create a Layer 3 packet
        LL3Datagram ll3Datagram = new LL3Datagram(sourceLl3PAddressField, destLl3PAddressField,
                ll3TypeField, ll3PIdentifierField, timeToLive, payloadField, checksum);

        //Request to send an LL3 Datagram.
        sendLL3PToNextHop(ll3Datagram);
    }

    private void sendLL3PToNextHop(LL3Datagram ll3Datagram) {
        int nextHop = lrpDaemon.getRouteTable().getNextHop
                (ll3Datagram.getDestLL3PAddressField().getNetworkNumber());
        int macAddress;
        try {
            macAddress = arpDaemon.getMACAddress(nextHop);
            ll2Daemon.sendLL2PFrame(ll3Datagram, macAddress,Constants.LL2P_TYPE_IS_LL3P);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void processLL3Packet(LL3Datagram packet, Integer layer2Address){
        arpDaemon.getArpTable().touch(arpDaemon.getKey(layer2Address));
        int destAdd = packet.getDestLL3PAddressField().getAddress();

        if(destAdd == Integer.valueOf(getMyLL3PAddress(),16)){
            int sourceAddress = packet.getSourceLl3PAddressField().getAddress();
            UIManager.getInstance().getMessenger().receiveMessage(sourceAddress, packet.getPayloadFieldValue().toString());
            UIManager.getInstance().displayMessage("LL3P: " + packet.getPayloadFieldValue().toString());
        }
        else
        {
            int timeToLiveLeft = packet.getTimeToLive().getTimeToLive() -1;
            packet.getTimeToLive().setTimeToLive(timeToLiveLeft);

            if(timeToLiveLeft != 0) {
                sendLL3PToNextHop(packet);
            }
            else
            {
                UIManager.getInstance().displayMessage("Killed a packet" + packet.toSummaryString());
            }
        }
    }

    private int getCurrentIdentifier(){
        identifier++;
        return identifier % 65536;
    }

}
