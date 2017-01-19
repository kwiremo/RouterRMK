package com.renemoise.routerrmk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.renemoise.routerrmk.network.Constants;
import com.renemoise.routerrmk.support.BootLoader;
import com.renemoise.routerrmk.support.UIManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        BootLoader bt = new BootLoader(this);
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
}
