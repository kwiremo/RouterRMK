package com.renemoise.routerrmk.support;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Rene Moise on 1/18/2017.
 */
public class UIManager implements Observer {
    private static UIManager ourInstance = new UIManager();
    public static UIManager getInstance() {
        return ourInstance;
    }
    private Activity parentActivity;    //It is assigned in the update method. The update method is
                                        //called when the observable notifies it that it had set up
                                        //the parent activity.
    private Context context;            //context for the UI's sake.


    private UIManager() {
    }

    //To display on screen. Traditinally called a toast.
    public  void disPlayMessage(String message, int displayTime){
        Toast.makeText(context, message, displayTime).show();
    }

    //To display on screen. Traditinally called a toast.
    public  void disPlayMessage(String message){
        disPlayMessage(message, Toast.LENGTH_LONG); // default is long time
    }

    private void setUpWidgets(){

    }

    //This class observes the bootloader. It is notified when something changes in the bootloader.
    @Override
    public void update(Observable observable, Object o) {
        parentActivity = ParentActivity.getParentActivity();
        context = parentActivity.getBaseContext();
    }
}
