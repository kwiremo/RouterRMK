package com.renemoise.routerrmk.UI;

/**
 * Created by Rene Moise on 2/9/2017.
 */

import android.provider.ContactsContract;

import com.renemoise.routerrmk.R;
import com.renemoise.routerrmk.network.daemon.ARPDaemon;
import com.renemoise.routerrmk.network.daemon.LL1Daemon;
import com.renemoise.routerrmk.support.ParentActivity;

import java.util.Observable;
import java.util.Observer;

/**
 * The TableUI Class has the job of creating and managing the 4 objects, each one of which will
 * manage a tableToDisplay on the screen. Thus it has four objects, each of which is a singleTableUI
 * object.
 */
public class TableUI implements Runnable,Observer {

    //   Note that this will actually implement the AdjacencyTableUI.
    private AdjacencyTableUI adjacencyUI;
    private SingleTableUI arpTableUI;
    private SingleTableUI routingTableUI;
    private SingleTableUI forwardingUI;

    //Public constructor
    public TableUI() {
    }

    //runs once every second to keep the displays current
    //It is called when a scheduler calls it.
    @Override
    public void run() {

        //TODO: Both needs to call updateview.
        adjacencyUI.updateView();
        arpTableUI.updateView();
    }

    @Override
    public void update(Observable observable, Object o) {
        //Create an object of the adjacency UI.
        adjacencyUI = new AdjacencyTableUI(ParentActivity.getParentActivity(),
                R.id.idAdjacencyTableListView,
                LL1Daemon.getInstance().getAdjacencyTable(),
                LL1Daemon.getInstance());

        arpTableUI = new SingleTableUI(ParentActivity.getParentActivity(),
                R.id.idARPListView,ARPDaemon.getInstance().getArpTable());
    }


}
