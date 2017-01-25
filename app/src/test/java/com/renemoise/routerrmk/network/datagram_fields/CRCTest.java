package com.renemoise.routerrmk.network.datagram_fields;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Rene Moise on 1/25/2017.
 */
public class CRCTest {
    @Test
    public void toAsciiString() throws Exception {
        CRC test = new CRC("abc");
        String result = test.toAsciiString();
        String expected = "979899";
        assertEquals(expected, result);
    }

}