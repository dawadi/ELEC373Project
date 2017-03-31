
import java.io.*;

public class Server {

    private static final int TCP_PORT = 4242;
    private static final int UDP_PORT = 4242;
    private static File transfer = new File(Server.class.getResource("data.txt").getFile());

    private static String transferMethod = "UDP";

    public static void main(String[] args) {

        if (transferMethod.equals("TCP")) {
            TCPServerThread tcp = new TCPServerThread(TCP_PORT, transfer);
            System.out.println("TCP Server running on port " + TCP_PORT);
            tcp.run();
        } else if (transferMethod.equals("UDP")) {
            UDPServerThread udp = new UDPServerThread(UDP_PORT, transfer);
            System.out.println("UDP Server running on port " + UDP_PORT);
            udp.run();
        } else {
            throw new IllegalArgumentException("Please select either TCP or UDP for a transfer method");
        }

    }
}
