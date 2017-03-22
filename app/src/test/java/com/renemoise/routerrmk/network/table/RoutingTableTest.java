package com.renemoise.routerrmk.network.table;

import com.renemoise.routerrmk.network.tablerecord.RoutingRecord;
import com.renemoise.routerrmk.support.ForwardingRecord;

import org.junit.Test;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Rene Moise on 3/9/2017.
 */
public class RoutingTableTest {
    @Test
    public void getBestRoutes() throws Exception {

        RoutingTable routingTable = new RoutingTable();
        RoutingTable forwardingTable = new RoutingTable();

        RoutingRecord record1 = new RoutingRecord(3,0,3333);
        routingTable.addNewRoute(record1);

        System.err.println("Record added:" + routingTable.table.get(0).toString());
        Thread.sleep(2000);
        routingTable.expireRecords(1);
        System.err.println("Size after deletion:" + routingTable.table.size());

        RoutingRecord record2 = new RoutingRecord(1,3,2222);
        RoutingRecord record3 = new RoutingRecord(2,4,1111);
        RoutingRecord record4 = new RoutingRecord(1,2,2222);
        RoutingRecord record5 = new RoutingRecord(2,5,1111);
        RoutingRecord record6 = new RoutingRecord(4,1,4444);
        RoutingRecord record7 = new RoutingRecord(5,3,2222);
        RoutingRecord record8 = new RoutingRecord(6,4,5555);

        List<RoutingRecord> routes = new ArrayList<>();

        routes.add(record1);
        routes.add(record2);
        routes.add(record3);
        routes.add(record4);
        routes.add(record5);
        routes.add(record6);
        routes.add(record7);
        routes.add(record8);


        routingTable.addRoutes(routes);

        List<RoutingRecord> bestRoutes = new ArrayList<>();

        RoutingRecord existingForwardingRecord = new RoutingRecord(1,10,222);

        forwardingTable.table.add(existingForwardingRecord);


        bestRoutes = routingTable.getBestRoutes();

        //Add a list to forwarding table.
        forwardingTable.addRoutesToForwarding(bestRoutes);

        System.err.println("LIST Best Routes:");
        for(int i = 0; i < bestRoutes.size(); i++){
            System.err.println(bestRoutes.get(i).toString());
        }

        System.err.println("LIST ALL Routes:");
        for(int i = 0; i < routingTable.table.size(); i++){
            System.err.println(routingTable.table.get(i).toString());
        }

        System.err.println("Forwading table");
        for(int i = 0; i < forwardingTable.table.size(); i++){
            System.err.println(forwardingTable.table.get(i).toString());
        }


        Thread.sleep(4000);

        routingTable.addNewRoute(record4);

        routingTable.expireRecords(3);


        bestRoutes = routingTable.getBestRoutes();
        System.err.println("LIST Best Routes:");
        for(int i = 0; i < bestRoutes.size(); i++){
            System.err.println(bestRoutes.get(i).toString());
        }

        System.err.println("LIST ALL Routes:");
        for(int i = 0; i < routingTable.table.size(); i++){
            System.err.println(routingTable.table.get(i).toString());
        }

        routingTable.addRoutes(routes);

        routingTable.removeRoutesFrom(2222);

        bestRoutes = routingTable.getBestRoutes();
        System.err.println("LIST Best Routes after removing 2222:");
        for(int i = 0; i < bestRoutes.size(); i++){
            System.err.println(bestRoutes.get(i).toString());
        }

        System.err.println("LIST ALL Routes after removing 2222:");
        for(int i = 0; i < routingTable.table.size(); i++){
            System.err.println(routingTable.table.get(i).toString());
        }



        RoutingRecord record10a = new RoutingRecord(10,3,2222);
        RoutingRecord record10c = new RoutingRecord(10,2,2222);
        RoutingRecord record10b = new RoutingRecord(10,4,1111);
        RoutingRecord record12a = new RoutingRecord(12,3,2222);
        RoutingRecord record12b = new RoutingRecord(12,4,5555);
        RoutingRecord record11a = new RoutingRecord(11,2,1111);
        RoutingRecord record11b = new RoutingRecord(11,1,4444);

        routes = new ArrayList<>();

        routes.add(record10a);
        routes.add(record10b);
        routes.add(record10c);
        routes.add(record11a);
        routes.add(record11b);
        routes.add(record12a);
        routes.add(record12b);

        routingTable.addRoutes(routes);
        bestRoutes = routingTable.getBestRoutes();
        System.err.println("LIST Best Routes after adding 10,11,12:");
        for(int i = 0; i < bestRoutes.size(); i++){
            System.err.println(bestRoutes.get(i).toString());
        }

        System.err.println("LIST ALL Routes after adding 10,11,12:");
        for(int i = 0; i < routingTable.table.size(); i++){
            System.err.println(routingTable.table.get(i).toString());
        }
    }
}