package com.renemoise.routerrmk.UI;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Rene Moise on 1/26/2017.
 *
 * The snifferUI class will access the UIManager classes and displays all the packets to the user.
 */
public class SnifferUI implements Observer {
    private static SnifferUI ourInstance = new SnifferUI();

    public static SnifferUI getInstance() {
        return ourInstance;
    }

    private SnifferUI() {
    }


    @Override
    public void update(Observable observable, Object o) {

    }
}
