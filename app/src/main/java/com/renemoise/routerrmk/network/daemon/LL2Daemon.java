package com.renemoise.routerrmk.network.daemon;

import com.renemoise.routerrmk.network.datagrams.LL2PFrame;

/**
 * Created by Rene Moise on 2/1/2017.
 */
public class LL2Daemon {
    private static LL2Daemon ourInstance = new LL2Daemon();

    public static LL2Daemon getInstance() {
        return ourInstance;
    }

    private LL2Daemon() {
    }

    public static void processLL2PFrame(LL2PFrame receivedFrame) {
    }
}
