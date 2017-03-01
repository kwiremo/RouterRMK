package com.renemoise.routerrmk.network.daemon;

/**
 * Created by Rene Moise on 2/23/2017.
 */
public class LRPDaemon {
    private static LRPDaemon ourInstance = new LRPDaemon();

    public static LRPDaemon getInstance() {
        return ourInstance;
    }

    private LRPDaemon() {
    }
}
