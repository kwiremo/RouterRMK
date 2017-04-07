package com.renemoise.routerrmk.UI;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;

import com.renemoise.routerrmk.network.daemons.LL1Daemon;
import com.renemoise.routerrmk.network.daemons.LL2Daemon;
import com.renemoise.routerrmk.network.table.Table;
import com.renemoise.routerrmk.network.tablerecord.AdjacencyRecord;

import java.util.Observer;

/**
 * Created by Rene Moise on 2/9/2017.
 */

public class AdjacencyTableUI extends SingleTableUI implements Observer {
    //o	this holds the reference to the ll1Daemon

    private LL1Daemon ll1Daemon;
    private LL2Daemon ll2Daemon;

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

            UIManager.getInstance().displayMessage(i+"");
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

            tableToDisplay.removeItem(key);

            //Update the display
            updateView();

            //return true if removed.
            return true;
        }
    };
}
