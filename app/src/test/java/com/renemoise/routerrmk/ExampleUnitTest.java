package com.renemoise.routerrmk;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        System.out.println("abcdef".substring(0,1));
        System.out.println("abcdef".substring(1,2));
        byte[] test = "abc".getBytes();
        String str1 = new String(test);
        System.out.println(str1);

    }
}