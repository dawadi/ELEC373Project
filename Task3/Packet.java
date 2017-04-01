import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by evan on 30/03/17.
 */
public class Packet {


    Router baseRouter;
    //from neighbor routers to baseRouter
    HashMap<String, Double> delays;

    public Packet(Router baseRouter, HashMap<String, Double> delays) {
        this.baseRouter = baseRouter;
        this.delays = new HashMap<>();
        Iterator it = delays.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            String name = (String)pair.getKey();
            Double delay = (Double)pair.getValue();

            this.delays.put(name, delay);

            it.remove();
        }
    }

    public String toString() {
        String output = "";
        output += "Packet being sent from router " + this.baseRouter.routerName + "\n";
        for(String router : delays.keySet()) {
            output += "Router " + router + ": Delay " + delays.get(router) + "\n";
        }
        output += "----------------------------------------------";
        return output;
    }

}
