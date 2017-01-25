package com.renemoise.routerrmk.support;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Rene Moise on 1/22/2017.
 */

public class UtilitiesTest {
    @Test
    public void padHexString() throws Exception {
//        try {
//            String result = Utilities.padHexString(null, 2);
//            fail("Should have thrown Exception");
//        }catch (Exception e){
//
//        }

        String result = Utilities.padHexString("FF", 2);
        String Expected = "00FF";
        assertEquals(Expected, result);
    }
}