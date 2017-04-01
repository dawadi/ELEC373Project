import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Router start = networkSetup();

    }

    public static Router networkSetup(){
        Router source = new Router(1);
        source.routerName = "Source";
        source.numberOfRouters = 1;

        Router b = new Router(20.5);
        Router c = new Router(2);
        Router d = new Router(1);
        source.addRouters(b,c,d);

        Router dest = new Router(0);
        dest.routerName = "Destination";
        dest.addToRouters(b,c,d);


        /*System.out.println(source);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);
        System.out.println(dest);*/

        LinkStateRouting lsr = new LinkStateRouting(source, b, c, d, dest);
        //System.out.println(lsr);
        return source;
    }

}
