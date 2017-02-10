package com.renemoise.routerrmk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.renemoise.routerrmk.UI.AddAdjacencyDialog;
import com.renemoise.routerrmk.network.Constants;
import com.renemoise.routerrmk.network.daemon.LL1Daemon;
import com.renemoise.routerrmk.network.datagram_fields.CRC;
import com.renemoise.routerrmk.network.datagram_fields.DatagramHeaderField;
import com.renemoise.routerrmk.network.datagram_fields.DatagramPayloadField;
import com.renemoise.routerrmk.network.datagram_fields.LL2PAddressField;
import com.renemoise.routerrmk.network.datagram_fields.LL2PTypeField;
import com.renemoise.routerrmk.network.datagrams.Datagram;
import com.renemoise.routerrmk.network.datagrams.LL2PFrame;
import com.renemoise.routerrmk.support.BootLoader;
import com.renemoise.routerrmk.UI.UIManager;

/**
 * MainAcitvity is the starting point of the application.
 */
public class MainActivity extends AppCompatActivity implements AddAdjacencyDialog.AdjacencyPairListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Boot up the router.
        BootLoader bootRouter = new BootLoader(this);
    }

    //called when the router is started. The inflater is responsible of enabling the or creating
    //the options menu feature in the Activity and connecting it with the menu XML file you created.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Called when the item on the menu is selected.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.showIPAddress){
            UIManager.getInstance().disPlayMessage("Your IP address is: " + Constants.IP_ADDRESS);
        }
        else if(item.getItemId() == R.id.showAddAdjacencyRecordDialogue){

            //Call a dialogue.
            AddAdjacencyDialog adjacencyDialog = new AddAdjacencyDialog();
            adjacencyDialog.show(getFragmentManager(), "Get Adjaceny Addresses: From if Statem.");

        }
        return super.onOptionsItemSelected(item);
    }

    //this calls the LL1Daemon’s addAdjacency method, passing the two strings required.
    @Override
    public void onFinishedEditDialog(String ll2PAddress, String ipAddress) {
        LL1Daemon.getInstance().addAdjacency(ll2PAddress,ipAddress);
    }
}
