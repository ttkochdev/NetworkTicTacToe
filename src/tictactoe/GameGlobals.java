package tictactoe;

import java.awt.Dimension;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

enum PlayerId {
    LOCAL, REMOTE, NONE
};

class GameGlobals {
    // Game state

    static boolean isServer;
    static boolean isLocalPlayerTurn;
    static boolean localPlayAgain;  // Does local player want to play again?
    static String localPlayerMarker = "O";
    static String remotePlayerMarker = "X";
    static int numberCellsFilled;
    static boolean isGameOver = false;
// Games played. 
// Server goes first when numberGamesCompleted is even,
// client goes first when numberGamesCompleted is odd.
    static int numberGamesCompleted;
    static int numberGamesWon = 0;
    static int numberGamesLost = 0;
// User Interface Components
    static final TTTButton[][] tttBoard;    // The 9 tic tac toe buttons
    static final JTextArea chatHistoryTextArea;
    static final JTextField chatMessageTextField;
    static final JLabel gameStatusLabel;   // Used to announce win or loss

// Use a static constructor to create the user interface components
    //used to initialiazed once
    static {
        tttBoard = new TTTButton[3][3];
        // create all the buttons and add them
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tttBoard[i][j] = new TTTButton(i, j);
                tttBoard[i][j].setPreferredSize(new Dimension(50, 50));
                tttBoard[i][j].setText("0");
                tttBoard[i][j].setPlayerId(PlayerId.NONE);
                tttBoard[i][j].addActionListener(new BoardListener());
            }

        }

// create the chat stuff and the status label
        chatHistoryTextArea = new JTextArea(14, 14);
        chatHistoryTextArea.setLineWrap(true);

        chatMessageTextField = new JTextField("Type to your opponent here");
        gameStatusLabel = new JLabel("Status label");

// add listeners
        chatMessageTextField.addActionListener(new ChatListener());

    }//end static
// This timer is used to periodically check for network input
    static Timer netInputMonitorTimer = new Timer(100, new NetInputListener());

// Does all start of game initializations and starts the
// the netInputMonitorTimer.
    static void init() {
        netInputMonitorTimer.stop();
        if (isServer == true) {
           
            if (GameGlobals.numberGamesCompleted % 2 == 0) {
                GameGlobals.localPlayerMarker = "O";
                GameGlobals.remotePlayerMarker = "X";
                GameGlobals.isLocalPlayerTurn = true;
                GameGlobals.gameStatusLabel.setText("Make your move."
                        +" Wins: "+ numberGamesWon 
                        + ". Losses: " + numberGamesLost
                        + ". Game Count: " + numberGamesCompleted);
            } else {
                GameGlobals.localPlayerMarker = "X";
                GameGlobals.remotePlayerMarker = "O";
                GameGlobals.isLocalPlayerTurn = false;
                GameGlobals.gameStatusLabel.setText("Wait your turn."
                        +" Wins: " + numberGamesWon 
                        + ". Losses: " + numberGamesLost
                        + ". Game Count: " + numberGamesCompleted);
            }
        } else {
            if (GameGlobals.numberGamesCompleted % 2 != 0) {
                GameGlobals.localPlayerMarker = "X";
                GameGlobals.remotePlayerMarker = "O";
                GameGlobals.isLocalPlayerTurn = true;
                GameGlobals.gameStatusLabel.setText("Make your move."
                        +" Wins: "+ numberGamesWon 
                        + ". Losses: " + numberGamesLost
                        + ". Game Count: " + numberGamesCompleted);
            } else {
                GameGlobals.localPlayerMarker = "O";
                GameGlobals.remotePlayerMarker = "X";
                GameGlobals.isLocalPlayerTurn = false;
                GameGlobals.gameStatusLabel.setText("Wait your turn."
                        +" Wins: " + numberGamesWon 
                        + ". Losses: " + numberGamesLost
                        + ". Game Count: " + numberGamesCompleted);
            }
        }

        numberGamesCompleted++;
        chatHistoryTextArea.setText("");
        numberCellsFilled = 0;
        isGameOver = false;
        //clear board
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tttBoard[i][j].setText("");
                tttBoard[i][j].setPlayerId(PlayerId.NONE);
            }
        }
        netInputMonitorTimer.start();
        //System.out.println(netInputMonitorTimer);
        //stop when game is  over
    }

    /**
     * Process a line of input read sent from the remote side
     *
     * @param input
     */
    static void processInput(String input) throws IOException {
        //input = NetComm.netReader.readLine();
        Scanner s = new Scanner(input);
        String string = s.next();
        //System.out.println("process input string = "+string);
        switch (string) {
            case "chat":
                //System.out.println("in case: chat. Input is: " + input);
                if (isServer == true) {
                    chatHistoryTextArea.append("Client: ");
                } else {
                    chatHistoryTextArea.append("Server: ");
                }
                while (s.hasNext()) {
                    chatHistoryTextArea.append(s.next() + " ");
                }
                chatHistoryTextArea.append("\n");
                break;
            case "move":
                //System.out.println("in case: move");
                processRemoteMove(s.nextInt(), s.nextInt());
                if (GameGlobals.isLocalPlayerTurn == true) {
                    gameStatusLabel.setText("Make your move");
                }
                break;
            case "playAgain":
                //System.out.println("in case: playAgain");
                processPlayAgainMessage(s.nextBoolean());
                break;
        }
    }

    /**
     * Process a remote user move to given row and column
     *
     * @param row
     * @param col
     */
    static void processRemoteMove(int row, int col) {
        if (isServer == true) {
            tttBoard[row][col].setText(remotePlayerMarker);
        } else {
            tttBoard[row][col].setText(localPlayerMarker);
        }
        tttBoard[row][col].setPlayerId(PlayerId.REMOTE);

        GameGlobals.numberCellsFilled++;

        if (GameGlobals.hasWon(PlayerId.REMOTE) == true) {
            GameGlobals.gameStatusLabel.setText("LOSER!!!");
            numberGamesLost++;
        } else {
            if (GameGlobals.numberCellsFilled == 9) {
                GameGlobals.gameStatusLabel.setText("DRAW");       
            } else {
                GameGlobals.isLocalPlayerTurn = true;
                if (isServer == true) {
                    tttBoard[row][col].setText(remotePlayerMarker);
                } else {
                    tttBoard[row][col].setText(localPlayerMarker);
                }
                gameStatusLabel.setText("Make your move");
            }
        }
    }

    /**
     * Check to see if a player has won.
     *
     * @param player
     * @return
     */
    static boolean hasWon(PlayerId player) {
        //Check Rows and columns
        int i;
        //horizontal
        for (i = 0; i < 3; i++) {
            if (GameGlobals.tttBoard[i][0].playerId == player
                    && GameGlobals.tttBoard[i][1].playerId == player
                    && GameGlobals.tttBoard[i][2].playerId == player) {
                return true;
            }
            //vertical
            if (GameGlobals.tttBoard[0][i].playerId == player
                    && GameGlobals.tttBoard[1][i].playerId == player
                    && GameGlobals.tttBoard[2][i].playerId == player) {
                return true;
            }
        }

        //diagonal bottom left - top right
        if (GameGlobals.tttBoard[2][0].playerId == player
   