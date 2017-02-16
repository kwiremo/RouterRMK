package com.renemoise.routerrmk.support;

import java.util.Calendar;

/**
 * Created by Rene Moise on 1/7/2017.
 *
 * It will function as a holding place for methods that are broadly used.
 */

public class Utilities {

    //Singleton classes are instantiated once.
    private static Utilities ourInstance = new Utilities();
    public static Utilities getInstance() {
        return ourInstance;
    }
    //private constructor insures no other class instantiate it.
    private Utilities() {}


    /*
    This method is passed a String of hex characters.  The method prepends enough leading “0”
    characters so that the resulting returned string represents exactly the number of bytes
    specified by finalLength.

    Parameters.
    String hexString: This string is the string to which you want to add leading zeros.
	Integer finalLength: This Integer specifies the length in BYTES the final string should represent.
    Note that for every byte there are two hex characters so the length of the string will be two times
    the length specified by this parameter

     */

    public static String padHexString(String hexString, Integer finalByteLength){
        if(hexString == null)
            new LabException("String can not be null");

        int finalStringLenth = finalByteLength * 2;
        int inputLenth = hexString.length();

        if(inputLenth >= finalStringLenth)
            return hexString;

        int numberOfZeroes = finalStringLenth - inputLenth;
        StringBuilder paddingZeroes = new StringBuilder();

        for(int i = 0; i<numberOfZeroes; i++)
            paddingZeroes.append("0");

        return paddingZeroes.append(hexString).toString();
    }

    /**
    * The base date in seconds since 1970:
     */
    public static long baseDateInSeconds = Calendar.getInstance().getTimeInMillis()/1000;

    /**
     * Get the time passed in seconds since the program began.
     */
    public static int getTimeInSeconds()
    {
        return (int) (Calendar.getInstance().getTimeInMillis()/1000 - baseDateInSeconds);
    }

    /**
     * @param inputToChange
     * @return
     *
     * This function receives any string value and return a string of its ascii values.
     */
    public static String toAsciiString(String inputToChange) {

        StringBuilder crcAscii = new StringBuilder();
        char [] crcValueChar = inputToChange.toCharArray();

        for(int i = 0; i < inputToChange.length(); i++)
        {
            crcAscii.append((int)crcValueChar[i]);
        }
        return crcAscii.toString();
    }

    /**
     * @param inputToChange
     * @return
     *
     * This function receives an string input value and return the hexvalue of the ascii value of
     * each character.
     */

    public static String toHexString(String inputToChange) {
        StringBuilder hexValue = new StringBuilder();

        for(int i = 0; i < inputToChange.length(); i++)
        {
            int asciiValue = (int)inputToChange.charAt(i);
            hexValue.append(Integer.toHexString(asciiValue));
        }
        return hexValue.toString();
    }


}
