package com.renemoise.routerrmk.support;

import android.app.Activity;

/**
 * Created by Rene Moise on 1/7/2017.
 */

/**
 *The parent activity gives an easy access to mainActivity context.
 */
public class ParentActivity {
    private static Activity mainActivity;
    //Singleton classes are instantiated once.
    private static ParentActivity ourInstance = new ParentActivity();

    //private constructor insures no other class instantiate it.
    private ParentActivity() {}

    public static ParentActivity getInstance() {
        return ourInstance;
    }

    //Get the activity parent
    public static Activity getParentActivity() {
        return mainActivity;
    }

    //Set the activity parent
    public static void setParentActivity(Activity myParentActivity) {
        mainActivity = myParentActivity;
    }
}
