package com.renemoise.routerrmk.network.daemon;

import com.renemoise.routerrmk.UI.TableUI;
import com.renemoise.routerrmk.UI.UIManager;
import com.renemoise.routerrmk.network.Constants;
import com.renemoise.routerrmk.support.BootLoader;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Rene Moise on 2/23/2017.
 */
public class Scheduler implements Observer {
    private static Scheduler ourInstance = new Scheduler();
    public static Scheduler getInstance() {
        return ourInstance;
    }
    ScheduledThreadPoolExecutor threadManager;  //thread manager
    ARPDaemon arpDaemon; //Reference to the arp daemon
    LRPDaemon lrpDaemon; //Reference to the LRP Daamon
    TableUI tableUI;    //reference to table UI

    private Scheduler() {
    }

    @Override
    public void update(Observable observable, Object o) {
        if(observable.getClass().equals(BootLoader.class)){
            threadManager = new ScheduledThreadPoolExecutor(Constants.THREAD_COUNT);
            tableUI = UIManager.getInstance().getTableUI();
            arpDaemon = ARPDaemon.getInstance();
            lrpDaemon = LRPDaemon.getInstance();

            /**
             * To spin off a thread use the scheduleAtFixedRate(int object, int delay,
             * int interval, int units) method on the ScheduledThreadPoolExecutor class.

             For example, to set off a thread that wakes up every second in the TimedTable class
             you’d give this command:

             threadManager.scheduleAtFixedRate(tableUI,
             Constants.ROUTER_BOOT_TIME,
             Constants.UI_UPDATE_INTERVAL,
             TimeUnit.SECONDS);

             In the example above you provide the local reference to the tableUI.
             Then use the constants for the boot_time, which should be from 3-10 seconds
             (a delay to let everything settle before the first thread begins its work),
             the update interval (which should be 1 second for the table UI), and the time unit
             constant.

             */
            threadManager.scheduleAtFixedRate(tableUI,Constants.ROUTER_BOOT_TIME,
                    Constants.UI_UPDATE_INTERVAL, TimeUnit.SECONDS);
            threadManager.scheduleAtFixedRate(arpDaemon,Constants.ROUTER_BOOT_TIME,
                    Constants.ARP_DAEMON_UPDATE_INTERVAL, TimeUnit.SECONDS);
            threadManager.scheduleAtFixedRate(lrpDaemon, Constants.ROUTER_BOOT_TIME,
                    Constants.LRP_DAEMON_UPDATE_INTERVAL, TimeUnit.SECONDS);
        }
    }
}
