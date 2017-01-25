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

    //LL2P Constants
    public  final int LL2P_DESTINATION_ADDRESS = 1; //this contains the integer value of the address.
    public final int LL2P_SOURCE_ADDRESS = 2; //true if the contained address is a source address.
    public final int LL2P_TYPE_FELD = 3;
    public final int LL2P_PAYLOAD_FIELD = 4;
    public final int LL2P_CRC_FIELD = 5;
}
