package com.renemoise.routerrmk.network;
import android.util.Log;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Rene Moise on 1/7/2017.
 */
/*
 * This class will contain all constants.
 */
public class Constants implements Observer {

    private static Constants ourInstance = new Constants();
    public static Constants getInstance() {
        return ourInstance;
    }

    public static String ROUTER_NAME = new String("Canna");
    public static String LOG_TAG = new String("CANNA: ");
    public static String IP_ADDRESS;	// the IP address of this system
    public static String IP_ADDRESS_PREFIX; // the prefix will be stored here
    /*
     * Constructor for Constants -- will eventually find out my IP address and do other nice
     * things that need to be set up in the constants file.
     */
    private Constants()
    {
        // call the local method to get the IP address of this device.
        IP_ADDRESS = getLocalIpAddress();
        if(IP_ADDRESS != null)
        {
            int lastDot = IP_ADDRESS.lastIndexOf(".");
            int secondDot = IP_ADDRESS.substring(0, lastDot - 1).lastIndexOf(".");
            IP_ADDRESS_PREFIX = IP_ADDRESS.substring(0, secondDot + 1);
            Log.e("IP: ", IP_ADDRESS);
        }
        else
            Log.e("IP: ", "IP address not found");
    }

    /**
     * getLocalIPAddress - this function goes through the network interfaces,
     *    looking for one that has a valid IP address.
     * Care must be taken to avoid a loopback address and IPv6 Addresses.
     * @return - a string containing the IP address in dotted decimal notation.
     */
    public String getLocalIpAddress()
    {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    //THe update method is called by an observable to which this obsever is registered when
    // the observable changes.
    @Override
    public void update(Observable observable, Object o) {
    }

    //LL2P CONSTANTS
    //LL2P Fields
    public static final int LL2P_DESTINATION_ADDRESS = 1; //this contains the integer value of the address.
    public static final int LL2P_SOURCE_ADDRESS = 2; //true if the contained address is a source address.
    public static final int LL2P_TYPE_FELD = 3;
    public static final int LL2P_PAYLOAD_FIELD = 4;
    public static final int LL2P_TEXT_PAYLOAD_FIELD = 5;
    public static final int LL2P_CRC_FIELD = 6;

    //LL3P Information
    public static final int LL3P_DESTINATION_ADDRESS = 7;
    public static final int LL3P_SOURCE_ADDRESS = 8;

    public static final String LL3P_ROUTER_ADDRESS_VALUE = "301";
    public static final String LL2P_ROUTER_ADDRESS_VALUE = "261995";

    //LL2P FIELD LENGTH IN BYTES
    public  static final int LL2P_DEST_ADDRESS_OFFSET = 0;
    public  static final int LL2P_DEST_ADD_FIELD_LENGTH = 3;
    public  static final int LL2P_SOURCE_ADDRESS_OFFSET = 3;
    public  static final int LL2P_SOURCE_ADDRESS_FIELD_LENGTH = 3;
    public  static final int LL2P_TYPE_FIELD_OFFSET = 6;
    public  static final int LL2P_TYPE_FIELD_LENGTH = 2;
    public  static final int LL2P_PAYLOAD_OFFSET = 8;
    public  static final int LL2P_CRC_FIELD_LENGTH = 2;

    //PROTOCOL INFORMATION
    public static final int LL2P_TYPE_IS_LL3P = 0x8001;
    public static final int LL2P_TYPE_IS_RESERVED = 0x8002;
    public static final int LL2P_TYPE_IS_LRP = 0x8003;
    public static final int LL2P_TYPE_IS_ECHO_REQUEST = 0x8004;
    public static final int LL2P_TYPE_IS_ECHO_REPLY = 0x8005;
    public static final int LL2P_TYPE_IS_ARP_REQUEST = 0x8006;
    public static final int LL2P_TYPE_IS_ARP_REPLY = 0x8007;
    public static final int LL2P_TYPE_IS_TEXT = 0x8008;


    //TABLE RECORDS
    public static final int ARP_RECORD = 40;
    public static final int ROUTING_RECORD = 41;
    public static final int ADJACENCY_RECORD = 42;
    public static final int FORWARDING_RECORD = 43;

    //COMMUNICATION
    public static final int UDP_PORT = 49999;
    public static final int REMOTE_UDP_PORT = 49999;

    //ARP RECORDS
    public static final int MAX_AGE_ALLOWED_ARP_RECORD = 30;

    //DATAGRAM IDENTIFICATIONS
    public static final int TEXT_DATAGRAM  = 14;
    public static final int ARP_DATAGRAM = 15;

    //SCHEDULER
    public static final int THREAD_COUNT = 25;
    public static final int ROUTER_BOOT_TIME = 3;
    public static final int UI_UPDATE_INTERVAL = 1;
    public static final int ARP_DAEMON_UPDATE_INTERVAL = 5;

    //LRP Packet Field Length
    public static final int SOURCE_LL3P_OFFSET = 0;
    public static final int SOURCE_LL3P_LENGTH = 4;
    public static final int SEQUENCE_NUMBER_OFFSET = 4;
    public static final int SEQUENCE_NUMBER_LENGTH = 1;
    public static final int COUNT_OFFSET = 5;
    public static final int COUNT_LENGTH = 1;
    public static final int PAIR_OFFSET = 6;
    public static final int PAIR_LENGTH = 4;

    //LRP PACKET HEADERS IDENTIFIERS
    //LL3 address field defined above.
    public static final int SEQUENCE_NUMBER = 61;
    public static final int COUNT = 62;
    public static final int NETWORK_DISTANCE = 63;
}
