package com.renemoise.routerrmk.UI;

import android.app.Activity;
import android.provider.Contacts;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.renemoise.routerrmk.network.Constants;
import com.renemoise.routerrmk.network.daemon.LL1Daemon;
import com.renemoise.routerrmk.network.daemon.LL2Daemon;
import com.renemoise.routerrmk.network.datagram_fields.CRC;
import com.renemoise.routerrmk.network.datagram_fields.DatagramPayloadField;
import com.renemoise.routerrmk.network.datagram_fields.LL2PAddressField;
import com.renemoise.routerrmk.network.datagram_fields.LL2PTypeField;
import com.renemoise.routerrmk.network.datagrams.LL2PFrame;
import com.renemoise.routerrmk.network.table.Table;
import com.renemoise.routerrmk.network.table.TableInterface;
import com.renemoise.routerrmk.network.tablerecord.AdjacencyRecord;
import com.renemoise.routerrmk.support.LabException;
import com.renemoise.routerrmk.support.Utilities;

import java.util.Observer;

/**
 * Created by Rene Moise on 2/9/2017.
 */

public class AdjacencyTableUI extends SingleTableUI implements Observer {
    //o	this holds the reference to the ll1Daemon

    private LL1Daemon ll1Daemon;     //TODO: Do we need LL1Daemon now that we are using LL2Daemon to send frame?
    private LL2Daemon ll2Daemon;    //TODO: Should it have accees to the LL2Daemon as well?

    public AdjacencyTableUI(Activity parentActivity, int viewID, Table tableToDisplay, LL1Daemon ll1Daemon) {
        super(parentActivity, viewID, tableToDisplay);

        // “TableManager”  is the name of the class that manages the table. In this case we
        // know it to be the LL1Daemon.
        this.ll1Daemon = ll1Daemon;

        //Initialize LL2Daoemon
        ll2Daemon = LL2Daemon.getInstance();

        //tell the ListView object where it’s onItemClickListener method is written.
        tableListViewWidget.setOnItemClickListener(sendEchoRequest);

        //tell the ListView object where it’s onItemClickListener method is written.
        tableListViewWidget.setOnItemLongClickListener(removeAdjacency);
    }

    //this is a method to handle the click on an item in the screen display.
    private AdapterView.OnItemClickListener sendEchoRequest = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            UIManager.getInstance().disPlayMessage(i+"");
            //Retrieve the record that was clicked.
            AdjacencyRecord record = (AdjacencyRecord) tableList.get(i);

            //Request the LL2Daemon to send this Echo request.
            ll2Daemon.sendEchoRequest(record.getLl2pAddress());
        }
    };

    //this is a method to remove the long clicked entry from the table.
    private AdapterView.OnItemLongClickListener removeAdjacency = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            //Retrieve the record that was clicked.
            AdjacencyRecord record = (AdjacencyRecord) tableList.get(i);

            //Get ll2Paddress used as a key in the table.
            int key = record.getLl2pAddress();

            //Remove a frame from the table.
            try {
                tableToDisplay.removeItem(key);
            }
            catch (LabException e)
            {
                Log.e(this.toString(), e.getMessage());
            }

            //Update the display
            updateView();

            //return true if removed.
            return true;
        }
    };
}
