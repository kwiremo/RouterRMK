package com.renemoise.routerrmk.network.table;

import com.renemoise.routerrmk.network.tablerecord.RoutingRecord;
import com.renemoise.routerrmk.network.tablerecord.TableRecord;
import com.renemoise.routerrmk.support.LabException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rene Moise on 3/8/2017.
 */

/**
 * The Routing table Class will be used to store routing tables and Forwarding tables. the
 * routing table contains all the routes the router knows. The Forwarding table contains the
 * best known route to every network known. This class is responsible of the routing and forwarding
 * table operations such as adding, removing, replacing, and expiring records.
 */
public class RoutingTable extends TimedTable {
    public RoutingTable() {
        super();
    }

    /**
     *     Given a routing record search the routing table for a route from the source of this
     *     information. If this is different then remove and replace the old record. If this is
     *     the same then touch the old one to keep it from expiring. If this is new then add it.
     */

    public void addNewRoute(TableRecord newEntry) {

        RoutingRecord newRoutingRecord = (RoutingRecord) newEntry;
        if (newRoutingRecord != null) {
            for (int i = 0; i < table.size(); i++) {
                if (table.get(i).getKey().compareTo(newRoutingRecord.getKey()) == 0) {

                    RoutingRecord temp = (RoutingRecord) table.get(i);

                    if (temp != null) {

                        if (temp.getDistance() == ((RoutingRecord) newRoutingRecord).getDistance()) {
                            //If it is present, check if it has different routes
                            table.get(i).updateTime();
                        }
                        else {
                            table.remove(i);
                            table.add(newRoutingRecord);
                            updateObservers();
                            return;
                        }
                    }
                }
            }
            //If not found in the table, add this new entry.
            table.add(newRoutingRecord);
            updateObservers();
        }
    }

    /**
     * o	Remove this record from the table if it exists.
     * @param entry
     */
    public void removeItem(TableRecord entry){
        for (int i = 0; i < table.size(); i++) {
            if(table.get(i).getKey().compareTo(entry.getKey()) == 0){
                table.remove(i);
            }
        }
    }

    /**
     *  This is given a remote network number and must return the LL3P address of the next hop.
     *  It is  used for forwarding packets by the LL3P daemon later.
     */

    public int getNextHop(int network){
        for (int i = 0; i < table.size(); i++) {
            RoutingRecord temp = (RoutingRecord) table.get(i);
            if(temp.getNetworkNumber() == network){
                return temp.getNextHop();
            }
        }
        return -1;
    }

    /**
     * This returns a list of all records in the table EXCEPT those learned from
     * the specified ll3pAddress. This is used in the forwarding table when sending LRP updates.
     */
    public List<RoutingRecord> getRouteListExcluding(int ll3pAddress){
        List<RoutingRecord> routes = new ArrayList<>();
        for (int i = 0; i < table.size(); i++) {
            RoutingRecord temp = (RoutingRecord) table.get(i);
            if(temp.getNextHop() != ll3pAddress){
                routes.add(temp);
            }
            else
            {
                //Skip it.
            }
        }
        return routes;
    }

    /**
     * Used when a neighbor dies in the ARP Table. All routes learned from this remote node must
     * be removed. Note that the routingRecord doesn’t use the LL3P address as a key, but you can
     * look at the nextHop field and if it matches then you can remove that record.
     */
    public void removeRoutesFrom(int ll3PAddress){
        for (int i = 0; i < table.size(); i++) {
            RoutingRecord temp = (RoutingRecord) table.get(i);

            if(temp.getNextHop() == ll3PAddress){
                table.remove(i);
            }
        }
    }

    /**
     * This returns the best route to each known remote network.
     */
    public List<RoutingRecord> getBestRoutes(){
        HashMap<Integer,Integer> hashmap = new HashMap<>();
        for(int i = 0; i < table.size(); i++) {
            RoutingRecord temp = (RoutingRecord) table.get(i);
            int key = temp.getNetworkNumber();
            int value = temp.getDistance();

            if(hashmap.containsKey(key)){
                if(hashmap.get(key) > value){
                    hashmap.put(key, value);
                }
            }
            else
            {
                hashmap.put(key,value);
            }
        }

        List<RoutingRecord> bestRoutes = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : hashmap.entrySet()) {
            RoutingRecord temp = getRecordNetDist(entry.getKey(), entry.getValue());
            bestRoutes.add(temp);
        }

        return bestRoutes;
    }

    private RoutingRecord getRecordNetDist(int network, int distance){
        for(int i = 0; i < table.size(); i++) {
            RoutingRecord temp = (RoutingRecord) table.get(i);

            if(temp.getDistance() == distance && temp.getNetworkNumber() == network){
                return temp;
            }
        }
        return null;
    }

    /**
     *  This returns the best route for the specified remote network.
     o	This throws a labException if there is no route to the specified network.
     */

    public RoutingRecord getBestRoute(int network) throws LabException{
        List<RoutingRecord> bestRecords =  getBestRoutes();

        for(int i = 0; i < bestRecords.size(); i++) {
            if(bestRecords.get(i).getNetworkNumber() == network){
                return bestRecords.get(i);
            }
        }
        throw new LabException("No Route Found");
    }

    public void addRoutesToForwarding(List<RoutingRecord> routes){
        for(int i = 0; i < routes.size(); i++) {
            RoutingRecord bestNewRecord = routes.get(i);
            for(int j = 0; j<table.size(); j++){
                RoutingRecord forwRecord = (RoutingRecord) table.get(j);
                if(forwRecord.getNetworkNumber() == bestNewRecord.getNetworkNumber()){
                    table.remove(forwRecord);
                    addNewRoute(bestNewRecord);
                    break;
                }
            }

            //If the new record does not exist, add it.
            table.add(bestNewRecord);
        }
    }

    // This adds all routes specified to the table, following known rules for route updating.
    public void	addRoutes(List<RoutingRecord> routes){
        for(int i = 0; i<routes.size(); i++){
            addNewRoute(routes.get(i));
        }
    }
}
