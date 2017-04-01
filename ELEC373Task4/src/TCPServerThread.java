
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServerThread {

    private ServerSocket tcpServer;
    private File file;

    public TCPServerThread(int port, File file) {
        this.file = file;

        try {
            tcpServer = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {

        while (!Thread.interrupted()) {

            try {
                Socket client = tcpServer.accept();

                TCPTransferThread tcp = new TCPTransferThread(client, file);
                tcp.run();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}
