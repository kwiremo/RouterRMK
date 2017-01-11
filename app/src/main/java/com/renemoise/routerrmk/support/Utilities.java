package com.renemoise.routerrmk.support;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Rene Moise on 1/7/2017.
 */

public class Utilities {

    //Singleton classes are instantiated once.
    private static Utilities ourInstance = new Utilities();
    public static Utilities getInstance() {
        return ourInstance;
    }
    //private constructor insures no other class instantiate it.
    private Utilities() {}
}
