package com.renemoise.routerrmk.network.daemon;

import com.renemoise.routerrmk.UI.UIManager;
import com.renemoise.routerrmk.network.Constants;
import com.renemoise.routerrmk.network.datagram_fields.CRC;
import com.renemoise.routerrmk.network.datagram_fields.DatagramPayloadField;
import com.renemoise.routerrmk.network.datagram_fields.LL2PAddressField;
import com.renemoise.routerrmk.network.datagram_fields.LL2PTypeField;
import com.renemoise.routerrmk.network.datagram_fields.TextPayload;
import com.renemoise.routerrmk.network.datagrams.ARPDatagram;
import com.renemoise.routerrmk.network.datagrams.Datagram;
import com.renemoise.routerrmk.network.datagrams.LL2PFrame;
import com.renemoise.routerrmk.support.BootLoader;
import com.renemoise.routerrmk.support.Factory;
import com.renemoise.routerrmk.support.LabException;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Rene Moise on 2/1/2017.
 */

/**
 * The LL2Daemon class is responsible of making LL2Frames and requesting the LL1Daemon to send it.
 */
public class LL2Daemon implements Observer {
    private static LL2Daemon ourInstance = new LL2Daemon();
    public static LL2Daemon getInstance() {
        return ourInstance;
    }
    public ARPDaemon arpDaemon;

    // this holds a reference for the UIManager. Primarily used for displaying messages on screen.
    private static UIManager uiManager;

    // This holds a reference for the LL1Daemon.  This is used when we need to request
    // transmission services from that daemon.
    private LL1Daemon ll1Daemon;


    private LL2Daemon() {
    }

    //o	This method is passed an LL2PFrame object. It processes the frame.
    public void processLL2PFrame(LL2PFrame receivedFrame) {

        String dest = receivedFrame.getDestinationAddress().toHexString();
        if(!receivedFrame.getDestinationAddress().toHexString().equals(Constants.LL2P_ROUTER_ADDRESS_VALUE)){
            uiManager.disPlayMessage("Discarded packet from " +
                    receivedFrame.getSourceAddress().toHexString());
        }
        else
        {
            switch (receivedFrame.getType().getType()){
                case Constants.LL2P_TYPE_IS_ECHO_REPLY:
                    uiManager.disPlayMessage("Received Echo Request + "+
                            receivedFrame.getSourceAddress().toString());
                    break;
                case Constants.LL2P_TYPE_IS_ECHO_REQUEST:
                    answerEchoRequest(receivedFrame);
                    break;
                case Constants.LL2P_TYPE_IS_LL3P:
                    uiManager.disPlayMessage("Unsupported frame Received. Stay tuned for updates");
                    break;
                case Constants.LL2P_TYPE_IS_RESERVED:
                    uiManager.disPlayMessage("Unsupported frame Received. Stay tuned for updates");
                    break;
                case Constants.LL2P_TYPE_IS_LRP:
                    uiManager.disPlayMessage("Unsupported frame Received. Stay tuned for updates");
                    break;
                case Constants.LL2P_TYPE_IS_ARP_REQUEST:
                    arpDaemon.processARPRequest(receivedFrame.getSourceAddress().getAddress(),
                            (ARPDatagram) receivedFrame.getPayload().getPacket());
                    uiManager.disPlayMessage("received an ARP Request");
                    break;

                case Constants.LL2P_TYPE_IS_ARP_REPLY:
                    arpDaemon.processARPReply(receivedFrame.getSourceAddress().getAddress(),
                            (ARPDatagram) receivedFrame.getPayload().getPacket());
                    uiManager.disPlayMessage("Received an ARP Reply");
                    break;

                case Constants.LL2P_TYPE_IS_TEXT:
                    uiManager.disPlayMessage("Received Text packet + "+
                            receivedFrame.getSourceAddress().toString());
                    break;
                default:
                    uiManager.disPlayMessage("The packet was not recognized!");
                    break;
            }
        }
    }

    //When the adjacency table UI element is clicked, an echo request is sent to the
    //extracted destination address.
    public void sendEchoRequest(Integer LL2PAddress){


        // Create a new LL2PFrame.
        LL2PFrame frame = new LL2PFrame(new LL2PAddressField(LL2PAddress,false),
                new LL2PAddressField(Constants.LL2P_ROUTER_ADDRESS_VALUE, true),
                new LL2PTypeField(Constants.LL2P_TYPE_IS_ECHO_REQUEST),
                new DatagramPayloadField("THIS IS AN ECHO REQUEST"),new CRC("1995"));

        //Get instance
        ll1Daemon = LL1Daemon.getInstance();

        //Send frame.
        ll1Daemon.sendFrame(frame);

        //Say you just attempted to send a frame.
        UIManager.getInstance().disPlayMessage("Just sent a clicked frame!");

    }

    // This method is passed an LL2PFrame object. Using this object it builds an echo reply frame
    // and requests transmission from the LL1Daemon.
    private void answerEchoRequest(LL2PFrame frame){

        //The new echo reply frame uses the source LL2P address in the frame you just received as
        // the destination, your LL2P Address for the source LL2P address, the Echo Reply Type value, and any CRC Value.
        LL2PFrame toSendFrame = new LL2PFrame(frame.getSourceAddress(),
                new LL2PAddressField(Constants.LL2P_ROUTER_ADDRESS_VALUE,true),
                new LL2PTypeField(Constants.LL2P_TYPE_IS_ECHO_REPLY),
                new DatagramPayloadField("THIS IS AN ECHO REPLY"), new CRC("1995"));

        //Send an echoReply frame.
        ll1Daemon.sendFrame(toSendFrame);
    }

    void sendLL2PFrame(Datagram datagram, int destinationAddress, int datagramType) {
        LL2PFrame frame;
        switch (datagramType)
        {
            case Constants.LL2P_TYPE_IS_LL3P:
                //for now frame is null
                frame = null;
                break;
            case Constants.LL2P_TYPE_IS_ARP_REQUEST:
                frame = new LL2PFrame(new LL2PAddressField(destinationAddress,false), new LL2PAddressField(
                        Constants.LL2P_ROUTER_ADDRESS_VALUE, true), new LL2PTypeField(
                        Constants.LL2P_TYPE_IS_ARP_REQUEST), new DatagramPayloadField(
                        datagram), new CRC("1995"));
                break;
            case Constants.LL2P_TYPE_IS_ARP_REPLY:
                frame = new LL2PFrame(new LL2PAddressField(destinationAddress,false), new LL2PAddressField(
                        Constants.LL2P_ROUTER_ADDRESS_VALUE, true), new LL2PTypeField(
                        Constants.LL2P_TYPE_IS_ARP_REPLY), new DatagramPayloadField(
                        datagram), new CRC("1995"));
                break;
            default:
                frame = null;
        }

        //send a LL3P Frame.
        ll1Daemon.sendFrame(frame);
    }

    @Override
    public void update(Observable observable, Object o) {

        //If from bootloader, save reference to uiManager and ll1Daemon.
        if(observable.getClass() == BootLoader.class){
            uiManager = UIManager.getInstance();
            ll1Daemon = LL1Daemon.getInstance();
            arpDaemon = ARPDaemon.getInstance();
        }
    }
}
