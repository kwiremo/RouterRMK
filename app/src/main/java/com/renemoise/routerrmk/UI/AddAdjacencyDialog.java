package com.renemoise.routerrmk.UI;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.renemoise.routerrmk.R;
import com.renemoise.routerrmk.support.ParentActivity;

/**
 * Created by Rene Moise on 2/9/2017.
 */

/**
 * This class is a dialog fragment because it inherits from the DialogFragment. It will be used to
 * raise dialogs to the screen.
 */

public class AddAdjacencyDialog extends DialogFragment {
    //    This will represent the widget on the screen that the user can type the IP address into.
    private EditText ipAddressEditText;

    //This will represent the widget on the screen that the user can type the LL2P Address into.
    private EditText  Ll2pAddressEditText;

    //This is the button that the user clicks to close the window and add the adjacency specified.
    private Button addAdjacencyButton;

    //This button cancels the dialog without entering an adjacency
    private Button cancelButton;

    //This interface provides a connection back to the main Activity (remember that the
    // DialogFragment is actually running on a different Activity thread)
    public interface AdjacencyPairListener{
        void onFinishedEditDialog( String ll2PAddress, String ipAddress);
    }

    //Default constructor
    public AddAdjacencyDialog() {
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout xml file you built.
        View rootView = inflater.inflate(R.layout.adjacency_entries_dialogue, container,false);

        //Set the title
        getDialog().setTitle("Get Adjaceny Addresses");

        //Connect the widgets on-screen to our local fields.
        ipAddressEditText = (EditText) rootView.findViewById(R.id.IPAddrreditText);
        Ll2pAddressEditText = (EditText) rootView.findViewById(R.id.LL2P_Address_editText);
        addAdjacencyButton = (Button) rootView.findViewById(R.id.add_Adjacency_Dialogue_Button);
        cancelButton = (Button) rootView.findViewById(R.id.Cancel_Adjacency_dialogue_Button);

        //Handle addAdjacency Button
        addAdjacencyButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                //To get the Activity object in your Dialog class you use the fact that the main
                // activity is now also an AdjacencyPairListener (because it implements
                // that interface)
                AdjacencyPairListener activity =
                        (AdjacencyPairListener) ParentActivity.getParentActivity();

                activity.onFinishedEditDialog(Ll2pAddressEditText.getText().toString(),
                        ipAddressEditText.getText().toString());
                dismiss();

            }
        });

        //Handle Cancel Button.
        cancelButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return rootView;
    }

    //call the superclass’s constructor, passing the savedInstanceState.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
