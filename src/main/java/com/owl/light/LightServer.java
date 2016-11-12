package com.owl.light;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LightServer {

    private static final Logger LOG = LoggerFactory.getLogger(LightServer.class);

    private DatagramSocket socket;
    private final int PORT = 8899;
    private final String ipAddress;
    private final String macAddress;
    private final InetAddress inetAddress;

    public LightServer(String ipAddress, String macAddress) throws IOException {
        this.ipAddress = ipAddress;
        this.macAddress = macAddress;

        inetAddress = InetAddress.getByName(ipAddress);
    }

    public CompletableFuture<Void> initialise() throws IOException {
        return connect().thenRun(() -> {
            try {
                socket = new DatagramSocket();
            } catch (SocketException ex) {
                throw new RuntimeException(ex);
            }
        });
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

    private CompletableFuture<Void> connect() throws IOException {
        return CompletableFuture.runAsync(() -> {
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
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void sleep(SleepOperation sleepOperation) throws InterruptedException {
        LOG.debug("OP: " + sleepOperation.toString());
        Thread.sleep(sleepOperation.sleepLengthMillis);
    }
}
