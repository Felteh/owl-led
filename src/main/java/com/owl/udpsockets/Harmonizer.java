package com.owl.udpsockets;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Harmonizer extends UdpRunnable {

    public Harmonizer(byte[] onCommand, byte[] offCommand) throws SocketException, UnknownHostException {
        super(onCommand, offCommand);
    }

    private final static int INTER_COMMAND_SLEEP=150;
    
    @Override
    public void run() {
        try {
            Sender s = new Sender();
            while (true) {
                //Choose a random for light 1
                Integer i = randomColour();
                send(s, onCommand, Colours.toByteArray(i));

                Thread.sleep(INTER_COMMAND_SLEEP);
                //Choose harmonizing colour for light 2
                Integer j = complementary(i, 180, 255);
                send(s, offCommand, Colours.toByteArray(j));

//                Thread.sleep(INTER_COMMAND_SLEEP);
//                //Choose a random brightness for light 1
//                Integer r = randomBright();
//                send(s, onCommand, Brightness.toByteArray(r));
//
//                Thread.sleep(INTER_COMMAND_SLEEP);
//                Integer t = complementary(r, 12, 255);
//                send(s, offCommand, Brightness.toByteArray(r));

                Thread.sleep(2*60*1000);
            }
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(ColourCycle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Integer randomColour() {
        return UUID.randomUUID().hashCode() % 255;
//        return (int)Math.floor(Math.random()*255d);
    }

    private Integer complementary(Integer h, Integer s, Integer max) {
        h += s;
        while (h >= max) {
            h -= 255;
        }
        while (h < 0.0) {
            h += 255;
        }
        return h;
    }

    private void send(Sender s, byte[] onCommand, byte[] toByteArray) throws InterruptedException, IOException {
        s.sendString(onCommand);
        Thread.sleep(100);
        s.sendString(toByteArray);
    }

    private Integer randomBright() {
        return UUID.randomUUID().hashCode() % 27;
    }
}
