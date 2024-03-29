package com.renemoise.routerrmk.support;
import android.app.Activity;
import android.util.Log;

import com.renemoise.routerrmk.UI.UIManager;
import com.renemoise.routerrmk.network.Constants;
import com.renemoise.routerrmk.network.daemons.ARPDaemon;
import com.renemoise.routerrmk.network.daemons.LL1Daemon;
import com.renemoise.routerrmk.network.daemons.LL2Daemon;
import com.renemoise.routerrmk.network.daemons.LL3PDaemon;
import com.renemoise.routerrmk.network.daemons.LRPDaemon;
import com.renemoise.routerrmk.network.daemons.Scheduler;
import com.renemoise.routerrmk.network.datagram_fields.CRC;
import com.renemoise.routerrmk.network.datagram_fields.LL2PAddressField;
import com.renemoise.routerrmk.network.datagram_fields.LL2PTypeField;
import com.renemoise.routerrmk.network.datagram_fields.TextPayload;
import com.renemoise.routerrmk.network.datagrams.LL2PFrame;
import com.renemoise.routerrmk.network.table.Table;
import com.renemoise.routerrmk.network.tablerecord.AdjacencyRecord;

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

        //testTableProperties();

        //After the UIManager is added to the observer list for the BootLoader, the BootLoader get
        // the TableUI from the UIManager and add it to the BootLoader’s observer list. This ensures
        //That every instance of table UI that is instantiated is observing the bootloader as well.
        addObserver(UIManager.getInstance().getTableUI());
        addObserver(UIManager.getInstance().getSnifferUI());
        addObserver(FrameLogger.getInstance());
        addObserver(LL1Daemon.getInstance());
        addObserver(LL2Daemon.getInstance());
        addObserver(Scheduler.getInstance());
        addObserver(LL3PDaemon.getInstance());
        //testTableProperties();
        addObserver(LRPDaemon.getInstance());
        addObserver(ARPDaemon.getInstance());
        setChanged();       //setChanged marks this observer as has been changed.
        notifyObservers();  // Notify Observers things have changed. Automatically calls the
                            //clearchanged that set the observer as not changed.
        UIManager.getInstance().displayMessage("Now let's roll! RouterRMK is app and running!");
        //TEST COMPONENTS
        //testRouterComponents();

        //testLl1daemon();
    }

    private void testLl1daemon() {

        LL1Daemon daemon = LL1Daemon.getInstance();

        //daemon.addAdjacency("112233", "10.30.27.160");
        //daemon.sendFrame(new LL2PFrame("1122332619958001Dummy Data1234".getBytes()));
    }

    //THe update method is called by an observable to which this obsever is registered when
    // the observable changes.
    @Override
    public String toString() {
        return "BootLoader";
    }

    public void testTableProperties()
    {
        //create adjacency records.
        AdjacencyRecord record1 = new AdjacencyRecord(new LL2PAddressField
                ("112233",false).getAddress(),GetIPAddress.getInstance().getInetAddress
                ("192.168.209.1"));

        //Create a table
        Table adjacencyTable = new Table();
        adjacencyTable.addItem(record1);
    }

    private void testRouterComponents()
    {
        LL2PAddressField dest = new LL2PAddressField("CAFCAF",false);
        LL2PAddressField source = new LL2PAddressField("261995",false);
        LL2PTypeField type = new LL2PTypeField("8001");
        TextPayload payload = new TextPayload("Dummy Data");
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
