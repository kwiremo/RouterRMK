package com.renemoise.routerrmk.network.datagram_fields;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Rene Moise on 1/29/2017.
 */
public class LL2PAddressFieldTest {
    @Test
    public void toHexString() throws Exception {
        LL2PAddressField ex = new LL2PAddressField(65,false);
        System.out.println(ex.toHexString());
        System.out.print(String.format("hello \n HI"));

    }

    @Test
    public void toAsciiString() throws Exception {
        LL2PAddressField ex = new LL2PAddressField(6500,false);
        String temp = "000041";
        System.out.println(Character.toChars(Integer.valueOf(temp.substring(0,2),16)));
        System.out.println(ex.toAsciiString());
    }

}