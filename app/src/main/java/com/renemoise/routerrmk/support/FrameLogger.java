package com.renemoise.routerrmk.support;

import com.renemoise.routerrmk.network.datagrams.LL1Daemon;
import com.renemoise.routerrmk.network.datagrams.LL2PFrame;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Rene Moise on 1/22/2017.
 */
public class FrameLogger extends Observable implements Observer  {
    private static FrameLogger ourInstance = new FrameLogger();
    private ArrayList<LL2PFrame> frameList;


    private FrameLogger() {
        frameList = new ArrayList<LL2PFrame>();
    }

    public static FrameLogger getInstance() {
        return ourInstance;
    }

    //this method returns the current frameList.
    public ArrayList<LL2PFrame> getFrameList()
    {
        return frameList;
    }

    //When this class is notified of a change from the LL1Daemon it is also passed an LL2P Frame
    // in the Object.  In this case the object must be recast into an LL2PFrame object and added
    // to the frameList.
    @Override
    public void update(Observable observable, Object o) {
        if(observable.getClass() == BootLoader.class){
            //add Sniffer UI to the list of observers.
        }
        else if(observable.getClass() == LL1Daemon.class){
            frameList.add((LL2PFrame) o);
            setChanged();
            notifyObservers();
        }
    }
}
