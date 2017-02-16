package com.renemoise.routerrmk.UI;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.renemoise.routerrmk.network.Constants;
import com.renemoise.routerrmk.network.daemon.LL1Daemon;
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

    private LL1Daemon ll1Daemon;

    public AdjacencyTableUI(Activity parentActivity, int viewID, Table tableToDisplay, LL1Daemon ll1Daemon) {
        super(parentActivity, viewID, tableToDisplay);

        // “TableManager”  is the name of the class that manages the table. In this case we
        // know it to be the LL1Daemon.
        this.ll1Daemon = ll1Daemon;

        //tell the ListView object where it’s onItemClickListener method is written.
        tableListViewWidget.setOnItemClickListener(sendEchoRequest);
        //tableListViewWidget

        //TODO: ADD the remove.
        tableListViewWidget.setOnItemLongClickListener(removeAdjacency);
    }

    //this is a method to handle the click on an item in the screen display.
    //TODO: Is this a method?
    private AdapterView.OnItemClickListener sendEchoRequest = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            //Retrieve the record that was clicked. //TODO: tablelist?
            AdjacencyRecord record = (AdjacencyRecord) tableList.get(i);

            // Create a new LL2PFrame.
            LL2PFrame frame = new LL2PFrame(new LL2PAddressField(record.getLl2pAddress(),false),
                    new LL2PAddressField(Constants.LL2P_ROUTER_ADDRESS_VALUE, true),
                    new LL2PTypeField(Constants.LL2P_TYPE_IS_ECHO_REQUEST),
                    new DatagramPayloadField("ECHO CONTENTES"),new CRC("1234"));

            //Get instance
            ll1Daemon = LL1Daemon.getInstance();

            //Send frame.
            ll1Daemon.sendFrame(frame);

            //Say you just attempted to send a frame.
            UIManager.getInstance().disPlayMessage("Just sent a clicked frame!");
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

            //TODO: CONFIRM HOW TO DO IT.
            //Remove a frame from the table.
            try {
                tableToDisplay.removeItem(key);
            }
            catch (LabException e)
            {
                Log.e(this.toString(), e.getMessage());
            }

            //TODO: Just call updateView.
            //Update the display
            updateView();

            //return true if removed.
            return true;
        }
    };
}
