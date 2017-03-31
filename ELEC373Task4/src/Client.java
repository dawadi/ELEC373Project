import java.io.*;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    private static final int TCP_PORT = 4242;
    private static final int UDP_PORT = 4242;

    private static String transferMethod = "UDP";

    public static void main(String[] args) throws UnknownHostException, IOException {

        if (transferMethod.equals("TCP")) {

            System.out.println("Now using TCP transfer method...");
            System.out.println("Retrieving file from: " + InetAddress.getLocalHost() + " on port " + TCP_PORT);

            // Send HELLO packet
            Socket tcp = new Socket(InetAddress.getLocalHost(), TCP_PORT);
            InputStream is = tcp.getInputStream();

            FileOutputStream os = new FileOutputStream("TCP.received." + System.currentTimeMillis() + ".txt");
            BufferedOutputStream bos = new BufferedOutputStream(os);

            // We want to read each byte from the input stream, and write that to file
            int byteReader = is.read();

            while (byteReader != -1) {
                byteReader = is.read();
                bos.write(byteReader);
            }

            // Close streams
            bos.close();
            tcp.close();

            System.out.println("File successfully saved in root directory");

        } else if (transferMethod.equals("UDP")) {

            System.out.println("Now using UDP transfer method...");
            System.out.println("Retrieving file from: " + InetAddress.getLocalHost() + " on port " + TCP_PORT);

            // Create UDP socket
            DatagramSocket socket = new DatagramSocket();

            // Send HELLO packet
            byte[] buffer = new byte[21000];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getLocalHost(), UDP_PORT);
            socket.send(packet);

            // Now we receive and decode the packet from the server
            packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            // Write to file
            FileOutputStream os = new FileOutputStream("UDP.received." + System.currentTimeMillis() + ".txt");
            BufferedOutputStream bos = new BufferedOutputStream(os);

            bos.write(packet.getData());

            bos.close();
            socket.close();

            System.out.println("File successfully saved in root directory");
        } else {
            throw new IllegalArgumentException("Please select either TCP or UDP for a transfer method");
        }


    }

}
