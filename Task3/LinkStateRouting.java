import java.util.*;
import java.lang.reflect.Array;


/**
 * Created by evan on 30/03/17.
 */
public class LinkStateRouting {

    /***
     * 1. Discover its neighbours, learn their network address
     * 2. Measure the delay or cost to each of its neighbors
     * 3. Construct a packet telling all it has just learned
     * 4. Send this packet to all other routers
     * 5. Compute the shortest path to every other router
     */
    public HashMap<Router, HashMap<String, Double>> RoutingTable;


    public LinkStateRouting(Router... allRouters) {
        RoutingTable = new HashMap<Router, HashMap<String, Double>>();
        ArrayList<Packet> allPackets = new ArrayList<>();
        for(Router router: allRouters) {
            ArrayList<String> neighbors = discoverNeighbors(router);
            HashMap<String, Double> delays = measureDelay(router, neighbors);
            Packet packet = buildPacket(router, delays);
            allPackets.add(packet);
        }
        sendPackets(allRouters[0], allPackets);

        ArrayList<Router> routers = new ArrayList<>(Arrays.asList(allRouters));
        String shortest = shortestPath(allRouters[0], allRouters[allRouters.length - 1], routers);
        System.out.println("Shortest path: " + shortest);

    }

    public ArrayList<String> discoverNeighbors(Router router) {
        ArrayList<String> neighbors = new ArrayList<>();
        for(Router neighbor: router.connectedRouters) {
            neighbors.add(neighbor.routerName);
        }
        return neighbors;
    }

    public HashMap<String, Double> measureDelay(Router router, ArrayList<String> neighbors) {
        HashMap<String, Double> delays = new HashMap<>();

        for(String name: neighbors) {
            for(Router neighbor: router.connectedRouters) {
                if(name.equals(neighbor.routerName)) {
                    delays.put(name, neighbor.outgoingDelayMS);
                }
            }
        }

        return delays;
    }

    public Packet buildPacket(Router router, HashMap<String, Double> delays) {
        Packet packet = new Packet(router, delays);
        return packet;
    }

    public void sendPackets(Router source, ArrayList<Packet> packets) {
        for(Packet packet: packets) {
            RoutingTable.put(packet.baseRouter, packet.delays);
        }
        source.shareRoutingTable();
    }

    public String shortestPath(Router source, Router dest, ArrayList<Router> allRouters) {
        HashMap<Router, Double> dist = new HashMap<>();
        HashMap<Router, Router> prev = new HashMap<>();
        PriorityQueue<Router> queue = new PriorityQueue<>(new RouterComparator(dist));


        for(Router router : allRouters) {
            if(!router.equals(source)) {
                dist.put(router, Double.POSITIVE_INFINITY);
                prev.put(router, null);
            } else {
                dist.put(router, 0.0);
                prev.put(router, source);
            }
            queue.add(router);
        }


        while(!queue.isEmpty()) {
            Router curr = queue.poll();
            for(Router neighbor : curr.connectedRouters) {
                    double currDist = dist.get(curr) + curr.outgoingDelayMS;
                    if(currDist < dist.get(neighbor)) {
                        queue.remove(neighbor);
                        dist.put(neighbor, currDist);
                        prev.put(neighbor, curr);
                        queue.add(neighbor);
                }

            }
        }

        String route = "";
        Router r = dest;
        while(!r.equals(source)) {
            route += r.routerName + "-";
            r = prev.get(r);
        }
        route += source.routerName;
        return route;
    }

    public String toString() {
        String output = "";
        for(Router baseRouter : RoutingTable.keySet()) {
            output += "Routing Table for: " + baseRouter.routerName + "\n";
            output += "Outgoing Delay: " + baseRouter.outgoingDelayMS + "\n";
            output += "(Connected Routers, Incoming Delays):\n";
            for(String connectedRouter : RoutingTable.get(baseRouter).keySet()) {
                output += "(" + connectedRouter + ", " + RoutingTable.get(baseRouter).get(connectedRouter) + ")\n";
            }
            output += "-----------------------------------------------\n";
        }
        return output;
    }


}
