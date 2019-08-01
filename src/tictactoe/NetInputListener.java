package tictactoe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

class NetInputListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent event) {
        try {
            //checks the boolean ready() on the reader
            //from buffered reader
            if (NetComm.netReader.ready() == true) {

                //read line then
                //passes to processInput()
                String input = NetComm.netReader.readLine();
                GameGlobals.processInput(input);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
