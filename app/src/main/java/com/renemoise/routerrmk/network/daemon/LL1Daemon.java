package com.renemoise.routerrmk.network.daemon;

import android.os.AsyncTask;
import android.util.Log;

import com.renemoise.routerrmk.UI.UIManager;
import com.renemoise.routerrmk.network.Constants;
import com.renemoise.routerrmk.network.datagrams.Datagram;
import com.renemoise.routerrmk.network.datagrams.LL2PFrame;
import com.renemoise.routerrmk.network.table.Table;
import com.renemoise.routerrmk.network.tablerecord.AdjacencyRecord;
import com.renemoise.routerrmk.support.Factory;
import com.renemoise.routerrmk.support.FrameLogger;
import com.renemoise.routerrmk.support.GetIPAddress;
import com.renemoise.routerrmk.support.LabException;
import com.renemoise.routerrmk.support.PacketInformation;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Rene Moise on 1/26/2017.
 *
 * LL1Daemon class is responsible of creating LL1 frames. It implements the observable. It is
 * observed by FrameLogger instances. however the Framelogger is adding itself to a list of
 * observers. Check the frameLogger class.
 */
public class LL1Daemon extends Observable implements Observer{
    private static LL1Daemon ourInstance = new LL1Daemon();
    //This returns the ourInstance and allows other objects access to this Singleton class.
    public static LL1Daemon getInstance() {
        return ourInstance;
    }
    private DatagramSocket receiveSocket;   //This is the UDP socket used to receive frames
    private DatagramSocket sendSocket;      //This is the UDP socket used to send frames.
    //this is the table object that maintains adjacency
    // relationships between LL2P addresses and their matching InetAddress (IP address) objects.
    private Table adjacencyTable;

    //this is the local object that can translate IP address string objects to valid InetAddress
    // objects.
    private GetIPAddress nameServer;

    //this is a reference to the high level UI Manager. When we transmit frames or have
    // problems transmitting frames we may send messages to be displayed on the screen.
    private UIManager uiManager;

    //we are overloading our Factory class here. It would be great to have a separate Factory
    // classes for each interface.
    Factory factory;

    //this is a reference to the singleton daemon class that handles layer 2 processing.
    private LL2Daemon ll2Daemon;

    //LL1 Daemon constructor
    private LL1Daemon() {
    }

    @Override
    public void update(Observable observable, Object o) {
        openSockets();
        nameServer = new GetIPAddress();

        //Initialize the adjacency table
        adjacencyTable = new Table();

        //Retrieve and save the reference to the Factory singleton class.
        factory = Factory.getInstance();

        //Add observer to observers
        addObserver(FrameLogger.getInstance());

        //	Save the reference for the UIManager singleton class so we can call its
        // displayMessage(…) method.
        uiManager = UIManager.getInstance();

        //Save the reference to the singleton LL2P daemon object so we can pass received frames
        // to it for processing at layer 2 of our router.
        ll2Daemon = LL2Daemon.getInstance();

        // spin off a new thread to listen for packets.
        new ReceiveUnicastFrame().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, receiveSocket);

    }

    protected class SendUnicastFrame extends AsyncTask<PacketInformation, Void, Void>
    {
        @Override
        protected Void doInBackground(PacketInformation... packetInformations) {
            PacketInformation pktInfo = packetInformations[0];
            try {
                pktInfo.getSocket().send(pktInfo.getPacket());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class ReceiveUnicastFrame extends AsyncTask<DatagramSocket, Void, byte[]>
    {

        @Override
        protected byte[] doInBackground(DatagramSocket... datagramSockets) {
            DatagramSocket receiveSocketTemp = datagramSockets[0];
            byte[] receiveData = new byte[1024];   // byte array to store received bytes.
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            try {
                receiveSocketTemp.receive(receivePacket); // check the socket for packet.
            } catch (IOException e) {
                e.printStackTrace();
            }
            int byteReceivedLength = receivePacket.getLength();
            byte[] frameByte = new String(receivePacket.getData()).substring(0,byteReceivedLength).getBytes();

            return frameByte;
        }

        @Override
        protected void onPostExecute(byte[] frameReceived) {
            LL2PFrame receivedFrame = new LL2PFrame(frameReceived);
            setChanged();
            notifyObservers(receivedFrame);
            // pass this LL2P Frame to the LL2PDaemon when you create one...
            LL2Daemon.processLL2PFrame(receivedFrame);

            // spin off a new thread to listen for packets.

            new ReceiveUnicastFrame().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, receiveSocket);

            Log.e("ReceivedStringFromHost", receivedFrame.toSummaryString());
        }
    }

    /**
     * This method opens the UDP sockets, preparing the router to send and receive packets.
     */
    private void openSockets()
    {

        //Try to open the send UDP Port.
        try {
            sendSocket = new DatagramSocket();
            Log.d(Constants.LOG_TAG, "SEND SOCKET OPENED SUCCESSFULLY!");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return;
        }

        //Try to set-up the receive UDP Port.
        try {
            receiveSocket = new DatagramSocket(Constants.UDP_PORT);
            Log.d(Constants.LOG_TAG, "RECEIVE SOCKET OPENED SUCCESSFULLY!");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return;
        }
    }

    public void sendFrame(LL2PFrame ll2PFrame)
    {
        String frameToSend = ll2PFrame.toString();
        InetAddress inetAddress;

        try {

            //Map the destination LL1 Address to the IP address.
            AdjacencyRecord record = (AdjacencyRecord) adjacencyTable.getItem
                    (ll2PFrame.getDestinationAddress().getAddress());
            inetAddress = record.getIpAddress();
            DatagramPacket sendPacket = new DatagramPacket(frameToSend.getBytes(),
                    frameToSend.length(),inetAddress, Constants.REMOTE_UDP_PORT);

            //Request to send frame.
            new SendUnicastFrame().execute(new PacketInformation(sendPacket, sendSocket));

            setChanged();
            notifyObservers(ll2PFrame);

        } catch (LabException e) {
            e.printStackTrace();
        }

    }

    //This is method is used when a record is to be removed.
    public void	removeAdjacency(AdjacencyRecord recordToRemove){
        try {
            adjacencyTable.removeItem(recordToRemove.getKey());
        }
        catch (LabException e)
        {
            Log.e(Constants.LOG_TAG, e.getMessage());
        }
    }

    //o	This method returns the Adjacency table. It is used by a UI object to display the list.
    public Table getAdjacencyTable()
    {
        return adjacencyTable;
    }

    // This method is passed two strings. It creates an AdjacencyRecord and has the table
    // add this record to itself.

    //TODO: When is the record added?
    public void	addAdjacency(String ll2PAddress, String ipAddress){

        int ll2pAdd = Integer.valueOf(ll2PAddress,16);      //Get integfer value of the address
        InetAddress inetAddress = nameServer.getInetAddress(ipAddress); //get inetaddress

        //The factory returns a table. We are leaving this new instance creation to the factory
        //to encapsulate what might change. The factory will return any table depending on what id
        //that was passed.
        AdjacencyRecord adjTable = (AdjacencyRecord)Factory.getInstance().getTableRecord(Constants.ADJACENCY_RECORD);

        //We are setting the LL2Paddress and the inetaddress here because the factory returns a
        //default instance that does not set them. This one way to solve a problem that the factory
        //method expects different parameter for each table record.
        adjTable.setLl2pAddress(ll2pAdd);
        adjTable.setIpAddress(inetAddress);

        Log.e("ADD_ADDED", ll2PAddress.toString());
        Log.e("IP_ADD", ipAddress);
        adjacencyTable.addItem(adjTable);
        setChanged();
        notifyObservers(adjTable);
    }
}
