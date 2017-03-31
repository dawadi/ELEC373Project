
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.Buffer;

public class UDPServerThread extends Thread {

    private DatagramSocket udpServer;
    private File file;

    public UDPServerThread(int port, File file) {
        this.file = file;

        try {
            udpServer = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void run() {

        while (!Thread.interrupted()) {
            byte[] buffer = new byte[21000];

            // First we attempt to receive the request
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            try {
                FileInputStream fs = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fs);

                udpServer.receive(packet);
                System.out.println("New connection from: " + packet.getAddress().getHostAddress());
                System.out.println("Sending " + file.getName() + ", with a size of " + file.length() + " bytes");

                bis.read(buffer, 0, buffer.length);

                // Create the packet to send
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buffer, buffer.length, address, port);

                udpServer.send(packet);

                bis.close();
                System.out.println("File sent successfully to " + packet.getAddress().getHostAddress());


            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
