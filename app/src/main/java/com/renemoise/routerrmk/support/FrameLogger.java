package com.renemoise.routerrmk.support;

import com.renemoise.routerrmk.UI.SnifferUI;
import com.renemoise.routerrmk.network.datagrams.LL1Daemon;
import com.renemoise.routerrmk.network.datagrams.LL2PFrame;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Rene Moise on 1/22/2017.
 *  the FrameLogger class which is Observable by the SnifferUI and observes both the LL1Daemon
 *  and the bootloader.  It has a very simply job of keeping a list of LL2PFrames and notifying
 *  the SnifferUI whenever that list changes
 *  It adds itself to the LL1Daemon class.
 *
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
            addObserver(SnifferUI.getInstance());
            LL1Daemon.getInstance().addObserver(this);

        }
        else if(observable.getClass() == LL1Daemon.class){
            frameList.add((LL2PFrame) o);
            setChanged();
            notifyObservers();
        }
    }
}
