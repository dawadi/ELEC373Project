import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by evan on 30/03/17.
 */
public class Router {

    public double outgoingDelayMS;
    public ArrayList<Router> connectedRouters;
    public static int numberOfRouters;//used for naming
    public int routerNumber;
    public String routerName;
    public HashMap<Router, HashMap<String, Double>> routingTable;

    public Router(double outgoingDelayMS) {
        this.outgoingDelayMS = outgoingDelayMS;
        numberOfRouters += 1;
        this.routerNumber = numberOfRouters;
        this.routerName = generateRouterName();
        this.connectedRouters = new ArrayList<>();
    }

    public Router(double outgoingDelayMS, Router... connectedRouters) {
        this.outgoingDelayMS = outgoingDelayMS;
        this.connectedRouters = new ArrayList<>();
        for(Router router: connectedRouters) {
            this.connectedRouters.add(router);

        }
        numberOfRouters += 1;
        this.routerNumber = numberOfRouters;
        this.routerName = generateRouterName();
    }

    public Router(double outgoingDelayMS, ArrayList<Router> connectedRouters) {
        this.outgoingDelayMS = outgoingDelayMS;
        this.connectedRouters = new ArrayList<>();
        this.connectedRouters.addAll(connectedRouters);
        numberOfRouters += 1;
        this.routerNumber = numberOfRouters;
        this.routerName = generateRouterName();

    }

    public void addRouters(Router... routers) {
        for(Router router: routers) {
            this.connectedRouters.add(router);
        }
    }

    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("Router Name: " + this.routerName + "\n");
        output.append("Router Outgoing Delay: " + this.outgoingDelayMS + "\n");
        output.append("Connected Routers: ");
        for(Router router: this.connectedRouters) {
            output.append(router.routerName + " ");
        }
        output.append("\n\n");
        return output.toString();
    }

    public void addToRouters(Router... routers) {
        for(int i = 0; i < routers.length; i++) {
            routers[i].addRouters(this);
        }
    }

    public void addRouters(ArrayList<Router> routers) {
        this.connectedRouters.addAll(routers);
    }

    public void updateRoutingTable(HashMap<Router, HashMap<String, Double>> routingTable) {
        this.routingTable = routingTable;
    }

    public void shareRoutingTable() {
        for(Router neighbor: this.connectedRouters) {
            neighbor.updateRoutingTable(this.routingTable);
            neighbor.shareRoutingTable();
        }
    }

    public String generateRouterName() {
        int routerNumber = this.routerNumber + 64; //ascii offset between int 1 and char 'A'
        char routerName = (char) routerNumber;
        return new StringBuilder().append(routerName).toString();
    }
}
