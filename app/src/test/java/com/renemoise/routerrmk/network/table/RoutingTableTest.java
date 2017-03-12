package com.renemoise.routerrmk.network.table;

import com.renemoise.routerrmk.network.tablerecord.RoutingRecord;

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

        RoutingTable table = new RoutingTable();
        RoutingRecord record1 = new RoutingRecord(3,0,3333);
        table.addNewRoute(record1);

        System.err.println("Record added:" + table.table.get(0).toString());
        Thread.sleep(2000);
        table.expireRecords(1);
        System.err.println("Size after deletion:" + table.table.size());

        RoutingRecord record2 = new RoutingRecord(1,3,2222);
        RoutingRecord record3 = new RoutingRecord(2,4,1111);
        RoutingRecord record4 = new RoutingRecord(1,2,2222);
        RoutingRecord record5 = new RoutingRecord(2,2,1111);
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


        table.addRoutes(routes);

        List<RoutingRecord> bestRoutes = new ArrayList<>();

        bestRoutes = table.getBestRoutes();
        System.err.println("LIST Best Routes:");
        for(int i = 0; i < bestRoutes.size(); i++){
            System.err.println(bestRoutes.get(i).toString());
        }

        System.err.println("LIST ALL Routes:");
        for(int i = 0; i < table.table.size(); i++){
            System.err.println(table.table.get(i).toString());
        }

        Thread.sleep(4000);

        table.addNewRoute(record4);

        table.expireRecords(3);


        bestRoutes = table.getBestRoutes();
        System.err.println("LIST Best Routes:");
        for(int i = 0; i < bestRoutes.size(); i++){
            System.err.println(bestRoutes.get(i).toString());
        }

        System.err.println("LIST ALL Routes:");
        for(int i = 0; i < table.table.size(); i++){
            System.err.println(table.table.get(i).toString());
        }

        table.addRoutes(routes);

        table.removeRoutesFrom(2222);

        bestRoutes = table.getBestRoutes();
        System.err.println("LIST Best Routes after removing 2222:");
        for(int i = 0; i < bestRoutes.size(); i++){
            System.err.println(bestRoutes.get(i).toString());
        }

        System.err.println("LIST ALL Routes after removing 2222:");
        for(int i = 0; i < table.table.size(); i++){
            System.err.println(table.table.get(i).toString());
        }



        RoutingRecord record10a = new RoutingRecord(10,3,2222);
        RoutingRecord record10b= new RoutingRecord(10,4,1111);
        RoutingRecord record10c = new RoutingRecord(10,2,2222);
        RoutingRecord record11a = new RoutingRecord(11,2,1111);
        RoutingRecord record11b = new RoutingRecord(11,1,4444);
        RoutingRecord record12a = new RoutingRecord(12,3,2222);
        RoutingRecord record12b = new RoutingRecord(12,4,5555);

        routes = new ArrayList<>();

        routes.add(record1);
        routes.add(record2);
        routes.add(record3);
        routes.add(record4);
        routes.add(record5);
        routes.add(record6);
        routes.add(record7);
        routes.add(record8);

        bestRoutes = table.getBestRoutes();
        System.err.println("LIST Best Routes after removing 2222:");
        for(int i = 0; i < bestRoutes.size(); i++){
            System.err.println(bestRoutes.get(i).toString());
        }

        System.err.println("LIST ALL Routes after removing 2222:");
        for(int i = 0; i < table.table.size(); i++){
            System.err.println(table.table.get(i).toString());
        }
    }
}