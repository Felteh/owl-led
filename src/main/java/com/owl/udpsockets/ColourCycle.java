package com.owl.udpsockets;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ColourCycle extends UdpRunnable {

    public ColourCycle(byte[] onCommand, byte[] offCommand) throws SocketException, UnknownHostException {
        super(onCommand, offCommand);
    }    

    @Override
    public void run() {
        try {
            Sender s = new Sender();
            int i = 0;
            while (i < 256) {
                s.sendString(onCommand);
                Thread.sleep(100);
                s.sendString(Colours.toByteArray(i));
                Thread.sleep(200);
                i++;
            }
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(ColourCycle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
