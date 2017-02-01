package com.renemoise.routerrmk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.renemoise.routerrmk.network.Constants;
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
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Boot up the router.
        BootLoader bootRouter = new BootLoader(this);
        testRouterComponents();
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
        return super.onOptionsItemSelected(item);
    }


    private void testRouterComponents()
    {
        LL2PAddressField dest = new LL2PAddressField("CAFCAF",false);
        LL2PAddressField source = new LL2PAddressField("261995",false);
        LL2PTypeField type = new LL2PTypeField("8001");
        DatagramPayloadField payload = new DatagramPayloadField("Dummy Data");
        CRC crc = new CRC("1234");

        LL2PFrame frame = new LL2PFrame(dest, source, type, payload, crc);
        Log.e("HEX_STRING",frame.toHexString());
        Log.e("TO_STRING",frame.toString());
        Log.e("PROTOCOL_INFO",frame.toProtocolExplanationString());
        Log.e("SUMMARY", frame.toSummaryString());
        //byte[] specs = "CAFCAF".getBytes();
        //LL2PFrame frame = new LL2PFrame(specs);
    }
}
