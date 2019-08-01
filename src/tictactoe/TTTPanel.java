package tictactoe;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

class MainTTTPanel extends JPanel {

    JScrollPane scrollPane;
    static JPanel buttonPanel;
    JPanel bottomPanel;
    GridBagLayout gridBagLayout;
    GridBagConstraints gridBagConstraints;

    MainTTTPanel() {
        this.setLayout(new BorderLayout());
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 3, 5, 5));
        this.add(buttonPanel, BorderLayout.WEST);

        // add the game buttons
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttonPanel.add(GameGlobals.tttBoard[i][j]);
            }
        }

        //add the chat and status fields
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(2, 1, 5, 5));
        bottomPanel.add(GameGlobals.chatMessageTextField);
        bottomPanel.add(GameGlobals.gameStatusLabel);
        this.add(bottomPanel, BorderLayout.SOUTH);

        //scroll bar for chat when text overflows
        scrollPane = new JScrollPane(GameGlobals.chatHistoryTextArea);
        scrollPane.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scrollPane, BorderLayout.CENTER);


    }
}