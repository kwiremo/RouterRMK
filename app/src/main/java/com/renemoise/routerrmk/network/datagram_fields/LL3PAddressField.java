package com.renemoise.routerrmk.network.datagram_fields;

/**
 * Created by Rene Moise on 2/23/2017.
 */

import com.renemoise.routerrmk.support.Utilities;

/**
 * This class is a decorator that implements datagramHeaderField. The LL3P Address is a two byte
 * Integer.  The high order 8 bits are an unsigned integer which specify the network number.
 * The low order 8 bits are an unsigned integer which specify the host number.
 * Thus the LL3P address 12.14 would be stored in hex as 0x0C0E.
 * The LL3P address field class must support the need of other classes to get this information.
 */
public class LL3PAddressField implements DatagramHeaderField {


    private int address;    //To store the address as an integer.

    /**
     *     the network part of the address is saved here.
     */
    private int	networkNumber;
    private int	hostNumber; // The host portion of the address is saved here.
    private Boolean isSourceAddress; //This is true if this object stores a source address.

    //This contains an explanation string, used when this field should be described in detail.
    // The string should be something like: “LL3P Source Address 14.12 (0x0E0C)”.
    private String explanationString;

    /**
     *
     * @param address
     * @param isSourceAddress
     *
     * The constructor is passed the String of hex characters describing the integer
     * value of the address and whether this is a source or destination address. All local fields
     * (objects) should be created and filled using this String.
     */
    public LL3PAddressField(String address, Boolean isSourceAddress) {
        try
        {
            this.address = (Integer.valueOf(address,16));
        }
        catch (NumberFormatException e)
        {
            this.address = -1;
            System.err.println("LL3PAddressField: address could not be parsed");
        }

        this.isSourceAddress = isSourceAddress;
    }

    @Override
    public String toHexString() {
        String addTemp = Integer.toHexString(address);
        return Utilities.padHexString(addTemp, 2);
    }

    @Override
    public String explainSelf() {
        setExplanationString();
        return explanationString;
    }

    @Override
    public String toAsciiString() {
        return Utilities.toAscciiCharactersOfEachHexByte(toHexString());
    }

    private void setExplanationString() {
        this.explanationString = "LL3P ";
        if(isSourceAddressField())
            this.explanationString = explanationString + " Source Address";
        else
            this.explanationString = explanationString  + " Destination Address";

        this.explanationString = explanationString  + "Value = " + toString();
    }

    public Boolean isSourceAddressField() {
        return isSourceAddress;
    }

    public int getNetworkNumber() {
        return Integer.valueOf(toHexString().substring(0,2),16);
    }

    public int getHostNumber() {
        return Integer.valueOf(toHexString().substring(2,4),16);
    }

    public int getAddress() {
        return address;
    }

    //public String toString() {
        //return "" + getNetworkNumber() + "." + getHostNumber();
    //}

    @Override
    public String toString() {
        return toHexString();
    }
}
