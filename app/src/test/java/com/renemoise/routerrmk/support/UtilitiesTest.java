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

//        String result = Utilities.padHexString("FF", 2);
//        String Expected = "00FF";
//        assertEquals(Expected, result);

        String input = "101010546f7272656e745c436f6d706c657465645c6e667375635f6f73745f62795f6d757374616e675c50656e64756c756d2d392c303030204d696c65732e6d7033006d7033006d7033004472756d202620426173730050656e64756c756d00496e2053696c69636f00496e2053696c69636f2a3b2a0050656e64756c756d0050656e64756c756d496e2053696c69636f303038004472756d2026204261737350656e64756c756d496e2053696c69636f30303800392c303030204d696c6573203c4d757374616e673e50656e64756c756d496e2053696c69636f3030380050656e64756c756d50656e64756c756d496e2053696c69636f303038004d50330000";
        int len = input.length()/16 + 1;
        StringBuilder formattedHex;
        StringBuilder formattedChar;
        String hexPortion;
        for(int i=0; i < len; i++)
        {
            if(i<len-1)
                hexPortion = input.substring(i*16,(i*16)+16);
            else
                hexPortion = input.substring(i*16,(i*16)+ (input.length()%16));

             formattedHex = new StringBuilder();
             formattedChar = new StringBuilder();
            for(int j = 0; j < hexPortion.length()/2; j++)
            {
                formattedHex.append(hexPortion.substring(j*2,(j*2)+2)+" ");
                int asciiValue = Integer.parseInt(hexPortion.substring(j*2,(j*2)+2),16);
                if(asciiValue >=32 && asciiValue <=127)
                    formattedChar.append((char)asciiValue);
                else
                    formattedChar.append(".");
            }

            System.out.format("%04x\t%s\t%s\n",(i*16),
                    formattedHex.toString(),
                    formattedChar.toString());
        }
    }
}