package com.owl.udpsockets.sup;

import com.owl.udpsockets.sup.impl.SendPacketOperation;
import com.owl.udpsockets.sup.impl.SleepOperation;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {

    private static final Logger LOG = LoggerFactory.getLogger(Server.class);

    private final DatagramSocket socket;
    private final int PORT = 8899;
    private final String ipAddress;
    private final String macAddress;
    private final InetAddress inetAddress;

    public Server(String ipAddress, String macAddress) throws IOException {
        this.ipAddress = ipAddress;
        this.macAddress = macAddress;

        inetAddress = InetAddress.getByName(ipAddress);
        connect();
        socket = new DatagramSocket();
    }

    public void executeCommand(OperationList a) throws IOException, InterruptedException {
        for (Operation o : a.operations) {
            switch (o.getOperationType()) {
                case SLEEP:
                    LOG.debug("Sleeping");
                    sleep((SleepOperation) o);
                    break;
                case SEND_PACKET:
                    LOG.debug("Sending packet");
                    sendPacket((SendPacketOperation) o);
                    break;
                default:
                    throw new RuntimeException("No operation type known");
            }
        }
    }

    private void sendPacket(SendPacketOperation a) throws IOException {
        LOG.debug("UDP SEND={}", a.toString());
        DatagramPacket packet = new DatagramPacket(a.bytes, a.bytes.length, inetAddress, PORT);
        socket.send(packet);
    }

    private void connect() throws IOException {
        LOG.debug("Connecting to light server");
        try (DatagramSocket connectSocket = new DatagramSocket()) {
            final int connectPort = 48899;
            byte[] buf = "Link_Wi-Fi".getBytes();

            DatagramPacket packet = new DatagramPacket(buf, buf.length, inetAddress, connectPort);
            LOG.debug("UDP SEND={}", Arrays.toString(buf));
            connectSocket.send(packet);

            byte[] receiveData = new byte[32];
            DatagramPacket receivePacket = new DatagramPacket(receiveData,
                    receiveData.length);
            boolean found = false;
            while (!found) {
                connectSocket.receive(receivePacket);
                String expectedSentence = ipAddress + "," + macAddress.toUpperCase().replace(":", "") + ",";
                String sentence = new String(receivePacket.getData(), 0, receivePacket.getLength());
                LOG.debug("UDP RECEIVED={} EXPECTED={}", sentence, expectedSentence);
                if (expectedSentence.equals(sentence)) {
                    found = true;
                    LOG.debug("UDP LINKED");
                }
            }
        }
    }

    private void sleep(SleepOperation sleepOperation) throws InterruptedException {
        LOG.debug("OP: " + sleepOperation.toString());
        Thread.sleep(sleepOperation.sleepLengthMillis);
    }
}
