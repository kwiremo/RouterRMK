package com.renemoise.routerrmk.support;

import android.app.Activity;

/**
 * Created by Rene Moise on 1/7/2017.
 */
public class ParentActivity {

    //Singleton classes are instantiated once.
    private static ParentActivity ourInstance = new ParentActivity();
    public static ParentActivity getInstance() {
        return ourInstance;
    }
    private static Activity parentActivity;
    //private constructor insures no other class instantiate it.
    private ParentActivity() {}


    //Get the activity parent
    public static Activity getParentActivity() {
        return parentActivity;
    }

    //Set the activity parent
    public static void setParentActivity(Activity myParentActivity) {
        parentActivity = myParentActivity;
    }
}
