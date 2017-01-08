package com.renemoise.routerrmk.support;
import android.app.Activity;
import com.renemoise.routerrmk.network.Constants;
import java.util.Observable;

/**
 * Created by Rene Moise on 1/7/2017.
 */

public class BootLoader extends Observable {

    public BootLoader(Activity parenttActivity)
    {
        bootRouter(parenttActivity);
    }

    private void bootRouter(Activity parentActivity)
    {
        ParentActivity.setParentActivity(parentActivity);
        addObserver(Utilities.getInstance());
        addObserver(Constants.getInstance());
        setChanged();       //setChanged marks this observer as has been changed.
        notifyObservers();  // Notify Observers things have changed. Automatically calls the
                            //clearchanged that set the observer as not changed.
    }

    @Override
    public String toString() {
        return "BootLoader";
    }
}
