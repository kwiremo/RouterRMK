package com.renemoise.routerrmk.network.datagrams;

import java.util.Observable;

/**
 * Created by Rene Moise on 1/26/2017.
 *
 * LL1Daemon class is responsible of creating LL1 frames. It implements the observable. It is
 * observed by FrameLogger instances. however the Framelogger is adding itself to a list of
 * observers. Check the frameLogger class.
 */
public class LL1Daemon extends Observable {
    private static LL1Daemon ourInstance = new LL1Daemon();

    public static LL1Daemon getInstance() {
        return ourInstance;
    }

    private LL1Daemon() {
    }


}
