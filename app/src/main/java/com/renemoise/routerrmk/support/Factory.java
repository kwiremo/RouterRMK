package com.renemoise.routerrmk.support;

import com.renemoise.routerrmk.network.datagram_fields.DatagramHeaderField;

import java.util.Observable;

/**
 * Created by Rene Moise on 1/22/2017.
 */
public class Factory extends Observable{
    private static Factory ourInstance = new Factory();

    public static Factory getInstance() {
        return ourInstance;
    }

    private Factory() {
    }

    //This method returns a datagram field. This can be any field for any layer in the router.
    public DatagramHeaderField getDatagramHeaderField(int FieldValue, String contents){
        return null;
    }


}
