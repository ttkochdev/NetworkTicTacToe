package tictactoe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 * @author Tyler
 */
public class BoardListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        TTTButton clickedButton = (TTTButton) e.getSource();
        PlayerId buttonOwner = clickedButton.playerId;

        if (GameGlobals.isLocalPlayerTurn == true) {
            if (buttonOwner == PlayerId.NONE) {
                GameGlobals.processLocalMove(clickedButton.row, clickedButton.col);
            } else {
                JOptionPane.showMessageDialog(null, "Sorry, you can't move here.");
            }
        }
    }
}
