package tictactoe;

import javax.swing.JFrame;

public class TTTFrame extends JFrame {

  TTTFrame() {
    super("CSC 469 Tic Tac Toe");
    MainTTTPanel mainPanel = new MainTTTPanel();
    this.add(mainPanel);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.pack();
    this.setVisible(true);
  
  }
}