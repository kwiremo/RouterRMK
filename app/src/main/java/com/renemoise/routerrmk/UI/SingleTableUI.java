package com.renemoise.routerrmk.UI;

/**
 * Created by Rene Moise on 2/9/2017.
 */

import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.renemoise.routerrmk.R;
import com.renemoise.routerrmk.network.table.Table;
import com.renemoise.routerrmk.network.table.TableInterface;
import com.renemoise.routerrmk.network.tablerecord.TableRecord;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * The SingleTableUI class has one purpose. It observes an underlying tableToDisplay in the model and
 * refreshes the screen any time the tableToDisplay contents change.  For the most part this is a simple
 * process. We can use default ListViewAdapter objects.  It is in this class that you will actually
 * connect the underlying List object to the screen’s ListView object.
 */


public class SingleTableUI implements Observer{

    //this holds the reference to the activity
    protected Activity parentActivity;

    //This is actually a ListView, but we’ll use the base widget View in our
    // specification so that we can receive a wider range of View objects.

    protected ListView tableListViewWidget;

    //The SingleTableUI class has to have an adapter to translate the tableToDisplay to the ListView Object
    // on the screen. Fortunately there’s nothing magical here like in the Sniffer UI (see below
    // for more fun on that!) so we can use a generic ArrayAdapter class. An ArrayAdapter is an
    // object that can connect the array to the screen widget.
    private ArrayAdapter adapter;

    //Note that we’re using the interface so we don’t have to worry about what kind of tableToDisplay we’ll
    // display ahead of time. By coding to an interface here we enable this class to display many
    // different types of tables that implement the TableInterface interface.
    Table tableToDisplay;

    //This contains a List of table records that will be displayed on the screen.
    protected List<TableRecord> tableList;


    //Constructor
    public SingleTableUI(Activity parentActivity, int viewID, Table tableToDisplay) {

        //Save the activity object.
        this.parentActivity = parentActivity;
        this.tableToDisplay = tableToDisplay;
        tableList = tableToDisplay.getTableArrayList();
        adapter = new ArrayAdapter(parentActivity.getBaseContext(),
                android.R.layout.simple_list_item_1, tableToDisplay.getTableArrayList());

        //connect the local java object to the ListView xml object passed into the constructor by
        // the TableUI class
        tableListViewWidget = (ListView) parentActivity.findViewById(viewID);

        //Tie the widget to the adaptor
        tableListViewWidget.setAdapter(adapter); // tell the widget its adapter.

        //Add this instance to the tableToDisplay
        this.tableToDisplay.addObserver(this);
    }


    //o	this method safely reloads the array and updates the screen. It ensure this is done on the
    // UI thread
    public void updateView(){
        // Force all our work here to be on the UI thread!
        parentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                //TODO: What does it really do?
                // notify the OS that the dataset has changed. It will update screen!
                //In our implementation, this causes the adapter to reload the arrayList into the
                // screen widget.
                adapter.notifyDataSetChanged();
            }
        });
    }
    @Override
    public void update(Observable observable, Object o) {
        //Whenever the tableToDisplay changes it will notify us that the data set has changed and call
        // this class’s update() method
        updateView();
    }
}