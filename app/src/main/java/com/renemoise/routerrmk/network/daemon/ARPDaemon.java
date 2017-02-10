package com.renemoise.routerrmk.network.daemon;

/**
 * Created by Rene Moise on 2/9/2017.
 */
public class ARPDaemon {
    private static ARPDaemon ourInstance = new ARPDaemon();

    public static ARPDaemon getInstance() {
        return ourInstance;
    }

    private ARPDaemon() {
    }
}
