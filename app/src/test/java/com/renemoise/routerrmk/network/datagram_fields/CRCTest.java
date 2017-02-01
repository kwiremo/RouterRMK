package com.renemoise.routerrmk.network.datagram_fields;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Rene Moise on 1/25/2017.
 */
public class CRCTest {
    @Test
    public void toHexString() throws Exception {
        CRC test = new CRC("abc");
        String result = test.toHexString();
        //System.out.println("value is " + Integer.toHexString((int)"abc".indexOf(0)));
        String expected = "6162";
        assertEquals(expected, result);
        System.out.println("abcdef".substring(0,2));

    }

    @Test
    public void toAsciiString() throws Exception {
        CRC test = new CRC("abc");
        String result = test.toAsciiString();
        String expected = "9798";
        assertEquals(expected, result);
    }


}