package tictactoe;

import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * @author Tyler
 * Received some assistance per conversation from Todd Froemling, Ryan Hilsabeck, Jason Kryst, and Travis Williams. 
 * Project turned in 2/17/2013
 * CSC 469 
 * Professor Muganda
 * Project Built in NetBeans IDE 7.2.1
 */
public class TicTacToe {

    public static void main(String[] args) throws IOException {
        JFrame frame = new TTTFrame();
        // Find out if should be a server or  a client
        String[] possibleValues = {
            "Tic Tac Toe Server", "Tic Tac Toe Client"
        };
        String selectedValue = (String) JOptionPane.showInputDialog(frame,
                "Select a Tic Tac Toe Role", "CSC 469/569 Tic Tac Toe",
                JOptionPane.INFORMATION_MESSAGE, null,
                possibleValues, possibleValues[0]);
        System.out.println("You selected " + selectedValue);
        //System.out.println(selectedValue.contains("Server"));
        if (selectedValue.contains("Server")) {
            frame.setTitle(frame.getTitle() + " " + "Server");
            NetComm.setUpServer();
        } else {
            frame.setTitle(frame.getTitle() + " " + "Client");
            NetComm.setUpClient();
        }
        GameGlobals.init();
    }
}
