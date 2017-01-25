package com.renemoise.routerrmk.network.datagram_fields;

import com.renemoise.routerrmk.support.LabException;
import com.renemoise.routerrmk.support.Utilities;

/**
 * Created by Rene Moise on 1/22/2017.
 */

public class LL2PAddressField implements DatagramHeaderField {

    private int address; //this contains the integer value of the address.

    private boolean isSourceAddress; //true if the contained address is a source address.


    //This is a String containing the explanation of the contents of this field. For example,
    // it might contain the string “Source LL2P Address: 0x314159”.
    private String explanation;

    public LL2PAddressField(Integer address, boolean isSourceAddress) {
        this.address = address;
        this.isSourceAddress = isSourceAddress;
    }

    public LL2PAddressField(String address, boolean isSourceAddress) {
        try
        {
            new LL2PAddressField(Integer.parseInt(address),  isSourceAddress);
        }
        catch (NumberFormatException e)
        {
            System.err.println("LL2PAddressField: address could not be parsed");
        }
    }

    @Override
    public String toString() {
        return explainSelf();
    }

    @Override
    public String toHexString() {
        String addTemp = Integer.toHexString(address);
        return Utilities.padHexString(addTemp, 3);
    }

    @Override
    public String explainSelf() {
        setExplanation();
        return explanation;
    }

    //This is not supposed to be implemented in this class.
    @Override
    public String toAsciiString() {
       return null;
    }

    public void setExplanation() {
        explanation = "LL2P ";
        if(isSourceAddressField())
            explanation = explanation + " Source Address";
        else
            explanation = explanation + " Destination Address";

        explanation = explanation + "Value = " + address;
    }

    //Still needs implementation
    public boolean isSourceAddressField()
    {
        return isSourceAddress;
    }
}
