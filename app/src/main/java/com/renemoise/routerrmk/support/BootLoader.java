package com.renemoise.routerrmk.support;
import android.app.Activity;

import com.renemoise.routerrmk.UI.UIManager;
import com.renemoise.routerrmk.network.Constants;
import java.util.Observable;

/**
 * Created by Rene Moise on 1/7/2017.
 */

/*
    The booloader will be called by the mainActivity when the app starts to initialize all the
    constants. It will notify the observers that it completed doing its work.
 */
public class BootLoader extends Observable {

    public BootLoader(Activity parenttActivity)
    {
        bootRouter(parenttActivity);
    }

    private void bootRouter(Activity parentActivity)
    {
        ParentActivity.setParentActivity(parentActivity);
        addObserver(Constants.getInstance());
        addObserver(UIManager.getInstance());
        setChanged();       //setChanged marks this observer as has been changed.
        notifyObservers();  // Notify Observers things have changed. Automatically calls the
                            //clearchanged that set the observer as not changed.
        UIManager.getInstance().disPlayMessage("Now let's roll! RouterRMK is app and running!");
    }

    //THe update method is called by an observable to which this obsever is registered when
    // the observable changes.
    @Override
    public String toString() {
        return "BootLoader";
    }
}
