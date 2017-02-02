package com.renemoise.routerrmk.network.daemon;

import android.os.AsyncTask;
import android.util.Log;

import com.renemoise.routerrmk.UI.UIManager;
import com.renemoise.routerrmk.network.Constants;
import com.renemoise.routerrmk.network.datagrams.Datagram;
import com.renemoise.routerrmk.network.datagrams.LL2PFrame;
import com.renemoise.routerrmk.network.table.Table;
import com.renemoise.routerrmk.support.Factory;
import com.renemoise.routerrmk.support.GetIPAddress;
import com.renemoise.routerrmk.support.PacketInformation;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
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
    Factory factory = Factory.getInstance();

    //this is a reference to the singleton daemon class that handles layer 2 processing.
    private LL2Daemon ll2Daemon = LL2Daemon.getInstance();

    private LL1Daemon() {
    }


    //TODO: What to do in the update.
    @Override
    public void update(Observable observable, Object o) {
        openSockets();
        nameServer = new GetIPAddress();

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
            byte[] receiveData = new byte[1024];   // byte array to store received bytes.
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            try {
                receiveSocket.receive(receivePacket); // check the socket for packet.
            } catch (IOException e) {
                e.printStackTrace();
            }
            //TODO: HOW DO WE KEEP LISTENING?
            return null;
        }

        @Override
        protected void onPostExecute(byte[] frameReceived) {
            LL2PFrame receivedFrame = new LL2PFrame(frameReceived);
            setChanged();
            notifyObservers(receivedFrame);
            // pass this LL2P Frame to the LL2PDaemon when you create one...
            LL2Daemon.processLL2PFrame(receivedFrame);

            // spin off a new thread to listen for packets.
            //TODO: RESEARCH ONEXECUTOR.
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

        //Try to open the send UDP Port.
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
        //TODO: ASK THE HOSTNAME.
        DatagramPacket sendPacket = new DatagramPacket(frameToSend.getBytes(),
                frameToSend.length(),nameServer.getInetAddress(""), Constants.REMOTE_UDP_PORT);

        //Request to send frame.
        new SendUnicastFrame().execute(new PacketInformation(sendPacket, sendSocket));

        setChanged();
        notifyObservers(ll2PFrame);
    }

}
