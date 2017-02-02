package com.renemoise.routerrmk.support;
import android.app.Activity;
import android.util.Log;

import com.renemoise.routerrmk.UI.UIManager;
import com.renemoise.routerrmk.network.Constants;
import com.renemoise.routerrmk.network.daemon.LL1Daemon;
import com.renemoise.routerrmk.network.datagram_fields.CRC;
import com.renemoise.routerrmk.network.datagram_fields.DatagramPayloadField;
import com.renemoise.routerrmk.network.datagram_fields.LL2PAddressField;
import com.renemoise.routerrmk.network.datagram_fields.LL2PTypeField;
import com.renemoise.routerrmk.network.datagrams.LL2PFrame;

import java.util.Observable;

/**
 * Created by Rene Moise on 1/7/2017.
 */

/*
    The booloader will be called by the mainActivity when the app starts to initialize all the
    constants. It will notify the observers that it completed doing its work.
 */
public class BootLoader extends Observable {

    public BootLoader(Activity parenttActivity)
    {
        bootRouter(parenttActivity);
    }

    private void bootRouter(Activity parentActivity)
    {
        ParentActivity.setParentActivity(parentActivity);
        addObserver(Constants.getInstance());
        addObserver(UIManager.getInstance());
        addObserver(FrameLogger.getInstance());
        addObserver(LL1Daemon.getInstance());
        setChanged();       //setChanged marks this observer as has been changed.
        notifyObservers();  // Notify Observers things have changed. Automatically calls the
                            //clearchanged that set the observer as not changed.
        UIManager.getInstance().disPlayMessage("Now let's roll! RouterRMK is app and running!");

        //TEST COMPONENTS
        testRouterComponents();
    }

    //THe update method is called by an observable to which this obsever is registered when
    // the observable changes.
    @Override
    public String toString() {
        return "BootLoader";
    }

    private void testRouterComponents()
    {
        LL2PAddressField dest = new LL2PAddressField("CAFCAF",false);
        LL2PAddressField source = new LL2PAddressField("261995",false);
        LL2PTypeField type = new LL2PTypeField("8001");
        DatagramPayloadField payload = new DatagramPayloadField("Dummy Data");
        CRC crc = new CRC("1234");

        //LL2PFrame frame = new LL2PFrame(dest, source, type, payload, crc);
        //byte[] frame =
        LL2PFrame frame = new LL2PFrame("CAFCAF2619958001Dummy Data1234".getBytes());
        Log.e("HEX_STRING",frame.toHexString());
        Log.e("TO_STRING",frame.toString());
        Log.e("PROTOCOL_INFO",frame.toProtocolExplanationString());
        Log.e("SUMMARY", frame.toSummaryString());
        //byte[] specs = "CAFCAF".getBytes();
        //LL2PFrame frame = new LL2PFrame(specs);
    }
}
