package com.renemoise.routerrmk.network.daemon;

import com.renemoise.routerrmk.network.Constants;

/**
 * Created by Rene Moise on 2/23/2017.
 */
public class LL3Daemon {
    private static LL3Daemon ourInstance = new LL3Daemon();

    public static LL3Daemon getInstance() {
        return ourInstance;
    }

    private LL3Daemon() {
    }

    //Gets my router's LL3P address
    public String getMyLL3PAddress(){
        return Constants.LL3P_ROUTER_ADDRESS_VALUE;
    }
}
