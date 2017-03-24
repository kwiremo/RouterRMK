package com.renemoise.routerrmk.UI;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.renemoise.routerrmk.R;
import com.renemoise.routerrmk.network.Constants;
import com.renemoise.routerrmk.network.datagram_fields.CRC;
import com.renemoise.routerrmk.network.datagram_fields.DatagramPayloadField;
import com.renemoise.routerrmk.network.datagram_fields.LL2PAddressField;
import com.renemoise.routerrmk.network.datagram_fields.LL2PTypeField;
import com.renemoise.routerrmk.network.datagrams.LL2PFrame;
import com.renemoise.routerrmk.network.tablerecord.AdjacencyRecord;
import com.renemoise.routerrmk.support.BootLoader;
import com.renemoise.routerrmk.support.FrameLogger;
import com.renemoise.routerrmk.support.ParentActivity;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Rene Moise on 1/26/2017.
 *
 * The snifferUI class will access the UIManager classes and displays all the packets to the user.
 */
public class SnifferUI implements Observer {

    private Activity parentActivity;
    private Context context;  //object taken from the Activity’s getBaseContext()
    private FrameLogger frameLogger;  //– refers to the singleton frameLogger instance

    //Local implementation of the local private class to provide custom adapter for displaying
    //text lines on the screen of the SnifferUI
    private SnifferFrameListAdapter frameListAdapter;


    private ListView frameListView; // – matches with ListView in SnifferUI relative Layout
    private TextView protocolBreakoutTextView; //object – points to middle window in SnifferUI
    private TextView frameBytesTextView; //  object – points to lower window in SnifferUI


    public SnifferUI() {
    }

    // This method sets up the adapter, ListView, TextView objects, and also declares the
    // onClickListener for handling events on the ListView widget.
    private void connectWidgets(){
        //Connect the local ListView object to the widget on the screen. This will look something like
        frameListView = (ListView) parentActivity.findViewById(R.id.idSnifferListView);

        //Instantiate the frameListAdapter.  Pass it the context and a frameList reference using the
        //FrameLogger’s getFrameList() method.
        frameListAdapter = new SnifferFrameListAdapter(context, frameLogger.getFrameList());

        //Connect the ListView object to the adapter with the ListView’s “setAdapter(Adapter)” method.
        // You did this in the SingleTableUI.
        frameListView.setAdapter(frameListAdapter);

        //Connect the Protocol breakout object to the TextView for the middle window.
        protocolBreakoutTextView = (TextView) parentActivity.findViewById(R.id.idLL2PInformationTextView);

        //Connect the Hex detail object to the TextView for the lower window.
        frameBytesTextView = (TextView) parentActivity.findViewById(R.id.idHexContentsTextView);

        //Inform the ListView object where its OnClickListener method is with the ListView’s
        frameListView.setOnItemClickListener(showUpdatedFrameList);
    }

    // This method handles the click events on the ListView.  It takes the selected frame and
    // assigns that frame’s protocol explanation and hex bytes strings to the lower windows for
    // display on-screen.

    private AdapterView.OnItemClickListener showUpdatedFrameList = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            //Retrieve the frame that was clicked.
            LL2PFrame frameSelected = frameLogger.getFrameList().get(i);

            //Set the TextView in the middle window’s text to that string.
            protocolBreakoutTextView.setText(frameSelected.toProtocolExplanationString());

            //Set the TextView in the middle window’s text to that string.
            String hexStringExplanation = frameSelected.toHexString();

            frameBytesTextView.setText(getFormattedExplanation(hexStringExplanation));

        }
    };

    private String getFormattedExplanation(String input) {
        //String input = "101010546f7272656e745c436f6d706c657465645c6e667375635f6f73745f62795f6d757374616e675c50656e64756c756d2d392c303030204d696c65732e6d7033006d7033006d7033004472756d202620426173730050656e64756c756d00496e2053696c69636f00496e2053696c69636f2a3b2a0050656e64756c756d0050656e64756c756d496e2053696c69636f303038004472756d2026204261737350656e64756c756d496e2053696c69636f30303800392c303030204d696c6573203c4d757374616e673e50656e64756c756d496e2053696c69636f3030380050656e64756c756d50656e64756c756d496e2053696c69636f303038004d50330000";
        int len = input.length()/16 + 1;
        StringBuilder formattedHex;
        StringBuilder formattedChar;
        String hexPortion;
        StringBuilder output = new StringBuilder();
        for(int i=0; i < len; i++)
        {
            if(i<len-1)
                hexPortion = input.substring(i*16,(i*16)+16);
            else
                hexPortion = input.substring(i * 16, (i * 16) + (input.length() % 16));

            formattedHex = new StringBuilder();
            formattedChar = new StringBuilder();
            for(int j = 0; j < hexPortion.length()/2; j++)
            {
                formattedHex.append(hexPortion.substring(j*2,(j*2)+2)+" ");
                int asciiValue = Integer.parseInt(hexPortion.substring(j*2,(j*2)+2),16);
                if(asciiValue >=32 && asciiValue <=127)
                    formattedChar.append((char)asciiValue);
                else
                    formattedChar.append(".");
            }

            output = output.append(String.format("%04x\t\t\t%16s\t\t\t%s\n",(i*16),
                    formattedHex.toString(),
                    formattedChar.toString()));
        }
        return output.toString();
    }

    /**
     * This method takes in concatenated ascii hex values and returns printable characters.
     */

    /*
    *     This class is a holder. It holds widgets (views) that make
    *     up a single row in the sniffer top window.
    */
    private static class ViewHolder {
        TextView packetNumber;
        TextView packetSummaryString;
    }

    /**
     * SnifferFrameListAdapter is a private adapter to display numbered rows from a List
     * object which contains all frames transmitted or received.
     *
     * It is instantiated above and note that the constructor passes the context as well as
     * the frameList.
     */
    /**
     * SnifferFrameListAdapter is a private adapter to display numbered rows from a List
     * object which contains all frames transmitted or received.
     *
     * It is instantiated above and note that the constructor passes the context as well as
     * the frameList.
     */
    private class SnifferFrameListAdapter extends ArrayAdapter<LL2PFrame> {
        // this is the ArrayList that will be displayed in the rows on the ListView.
        private ArrayList<LL2PFrame> frameList;

        /*
        *  The constructor is passed the context and the arrayList.
        *  the arrayList is assigned to the local variable so its contents can be
        *  adapted to the listView.
        */
        public SnifferFrameListAdapter(Context context, ArrayList<LL2PFrame> frames) {
            super(context, 0, frames);
            frameList = frames;
        }

        /**
         * Here is where the work is performed to adapt a specific row in the arrayList to
         * a row on the screen.
         *
         * @param position    - position in the array we're working with
         * @param convertView - a row View that passed in – has a view to use or a null object
         * @param parent      - the main view that contains the rows.  Note that is is the ListView object.
         * @return
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // First retrieve a frame object from the arrayList at the position we're working on
            LL2PFrame ll2PFrame = getItem(position);
            // declare a viewHolder - this simply is a single object to hold a two widgets
            ViewHolder viewHolder;

            /**
             * If convertView is null then we didn't get a recycled View, we have to create from scratch.
             * We do that here.
             */
            if (convertView == null) {
                // inflate the view defined in the layout xml file using an inflater we create here.
                LayoutInflater inflator = LayoutInflater.from(context);
                convertView = inflator.inflate(R.layout.sniffer_layout, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.packetNumber = (TextView) convertView.findViewById(R.id.snifferFrameNumberTextView);
                viewHolder.packetSummaryString = (TextView) convertView.findViewById(R.id.snifferItemTextView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.packetNumber.setText(Integer.toString(position));

            viewHolder.packetSummaryString.setText(frameList.get(position).toSummaryString());

            return convertView;
        }

    }

        /*This method handles observer updates from both the BootLoader and the FrameLogger.
    When the bootloader notifies this method of change the SnifferUI sets up all widgets via
    the connectWidgets() method.
    When the FrameLogger class notifies the SnifferUI of changes, the SnifferUI calls the
    adapter’s notifyDataSetChanged() method, which updates the screen with the latest list of
    Frames.
    */
    @Override
    public void update(Observable observable, Object o) {
        if(observable.getClass().equals(BootLoader.class)){

            //Save the parent activity reference.
            parentActivity = ParentActivity.getParentActivity();

            //Save the context
            context = parentActivity.getBaseContext();

            //Reference to framelogger
            frameLogger = FrameLogger.getInstance();

            //Add the snifferUI instance to the list of the framelogger observers.
            frameLogger.addObserver(this);

            //Connect all widgets.
            connectWidgets();
        }
        else if (observable.getClass().equals(FrameLogger.class))
        {
            frameListAdapter.notifyDataSetChanged();
        }
    }
}
