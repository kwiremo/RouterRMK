package com.renemoise.routerrmk.network.datagram_fields;

import com.renemoise.routerrmk.network.datagram_fields.DatagramHeaderField;
import com.renemoise.routerrmk.support.Utilities;

/**
 * Created by Rene Moise on 1/22/2017.
 */

public class LL2PTypeField implements DatagramHeaderField {

    private Integer type; //contains the integer value of the typeValue of the payload.
    //this is a String containing the explanation of the content of this field. For example,
    // it might contain the string “LL3P typeValue (0x8001)”
    private String explanation;

    //This is a constructor that works with a passed integer to fill in all the fields.
    public LL2PTypeField(Integer typeValue) {
        this.type = typeValue;
    }

    //this method uses the internal fields to create an explanation string.
    public LL2PTypeField(String typeValueString) {

        try
        {
            new LL2PTypeField(Integer.parseInt(typeValueString));
        }
        catch (NumberFormatException e)
        {
            System.err.println("LL2PTypeField: Type field value could not be parsed");
        }
    }

    @Override
    public String toHexString() {
        String addTemp = Integer.toHexString(type);
        return Utilities.padHexString(addTemp, 2);
    }

    @Override
    public String explainSelf() {
        setExplanation();
        return null;
    }

    @Override
    public String toAsciiString() {
        return null;
    }

    //this method uses the internal fields to create an explanation string.
    private void setExplanation()
    {
        explanation = "Value " + toHexString();
    };

    @Override
    public String toString() {

        return explainSelf();
    }
}
