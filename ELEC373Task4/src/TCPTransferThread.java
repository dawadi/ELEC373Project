
import com.sun.tools.doclets.internal.toolkit.util.DocFinder;

import java.io.*;
import java.net.Socket;

public class TCPTransferThread extends Thread {
    private Socket connection;
    private File file;

    public TCPTransferThread(Socket connection, File file) {
        this.connection = connection;
        this.file = file;

        System.out.println("New connection from: " + connection.getInetAddress().getHostAddress());
        System.out.println("Sending " + file.getName() + ", with a size of " + file.length() + " bytes");
    }

    public void run() {

        try {
            FileInputStream fs = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fs);
            OutputStream os = connection.getOutputStream();

            byte[] packet;
            int packetSize = 5000;
            long fileLength = file.length();
            long sent = 0;

            // Reading and writing to BufferedStream and OutputStream
            while (sent < fileLength) {

                if ((fileLength - sent) >= packetSize) {
                    sent += packetSize;
                } else {
                    packetSize = (int)(fileLength - sent);
                    sent = fileLength;
                }

                packet = new byte[packetSize];
                bis.read(packet, 0, packetSize);
                os.write(packet, 0, packetSize);
            }

            os.flush();
            bis.close();

            connection.close();
            System.out.println("File sent successfully to " + connection.getInetAddress().getHostAddress());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
