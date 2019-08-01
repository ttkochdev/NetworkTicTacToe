package tictactoe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;

/**
 *
 * @author Tyler
 */
public class ChatListener implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
        
        JTextField textEntered = (JTextField) e.getSource();
        NetComm.netWriter.println("chat " + textEntered.getText());
        GameGlobals.chatHistoryTextArea.append("You: " +
                textEntered.getText() + "\n");
        textEntered.setText("");
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
