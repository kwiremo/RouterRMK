package com.renemoise.routerrmk.support;

/**
 * Created by Rene Moise on 2/1/2017.
 */

import com.renemoise.routerrmk.network.datagrams.Datagram;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * this class is a “holder” to pass two objects in one object.
 */
public class PacketInformation {

    private DatagramSocket socket; //o	This contains the socket to be used.
    private DatagramPacket packet; //This contaings the datagram to be sent.

    public PacketInformation(DatagramPacket packet, DatagramSocket socket) {
        this.packet = packet;
        this.socket = socket;
    }

    public DatagramSocket getSocket() {
        return socket;
    }

    public DatagramPacket getPacket() {
        return packet;
    }
}
