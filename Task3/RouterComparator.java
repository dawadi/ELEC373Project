import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by evan on 31/03/17.
 */

public class RouterComparator implements Comparator<Router>
{

    private HashMap<Router, Double> dist;

    public RouterComparator(HashMap<Router, Double> dist) {
        this.dist = dist;
    }

    public int compare( Router x, Router y )
    {
        if(this.dist.get(x) > this.dist.get(y)) return 1;
        if(this.dist.get(x) > this.dist.get(y)) return -1;
        return 0;
    }
}