package com.renemoise.routerrmk.network.datagrams;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Rene Moise on 3/7/2017.
 */
public class LRPPacketTest {
    @Test
    public void toHexString() throws Exception {
        String test = "0310fd030502040103";
        LRPPacket packet = new LRPPacket(test.getBytes());
        System.err.println(packet.toProtocolExplanationString());
        System.err.println(packet.getBytes());
        System.err.println(packet.toString());
        System.err.println(packet.toHexString());
    }
}