package tictactoe;

import javax.swing.JButton;

/**
 *
 * @author Tyler
 */
public class TTTButton extends JButton {

    final int row;
    final int col;
    PlayerId playerId = PlayerId.NONE;

    TTTButton(int row, int col) {
        this.row = row;
        this.col = col;
    }
    
  void setPlayerId(PlayerId id) {
    this.playerId = id;
  }
}
