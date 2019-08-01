package tictactoe;

import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Tyler
 */
class NetComm {

    public static ServerSocket serverSocket;
    public static Socket clientSocket;
    public static BufferedReader netReader; // Used to read data from socket
    public static PrintWriter netWriter;    // Used to write data to socket

    /**
     * Set up network reader and writer streams after client sockets are
     * connected.
     */
    private static void setUp() throws IOException {
        // read in
        netReader = new BufferedReader(new InputStreamReader(
                clientSocket.getInputStream()));
        // write out
        netWriter = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    public static void setUpServer() throws IOException {

        serverSocket = new ServerSocket(50000);

        // wait for incoming connection
        clientSocket = serverSocket.accept();
        System.out.println("Connected");
        GameGlobals.isServer = true;
        setUp();

    }

    public static void setUpClient() throws UnknownHostException, IOException {
        String ip = JOptionPane.showInputDialog("Please enter a server "
            + "IP address", "localhost");
        //String ip = "localhost"; //for testing
        clientSocket = new Socket(ip, 50000);
        GameGlobals.isServer = false;
        setUp();
    }
}
