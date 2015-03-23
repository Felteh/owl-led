package com.owl.udpsockets.sup;

import com.owl.udpsockets.sup.impl.SendPacketOperation;
import com.owl.udpsockets.sup.impl.SleepOperation;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class Server {

    private final DatagramSocket socket;
    private final int PORT = 8899;
    private final String MULTICAST_GROUP_ID = "192.168.1.114";
    private final InetAddress INET_ADDRESS;

    public Server() throws IOException {
        INET_ADDRESS = InetAddress.getByName(MULTICAST_GROUP_ID);
        connect();
        socket = new DatagramSocket();
    }

    public void executeCommand(OperationList a) throws IOException, InterruptedException {
        for (Operation o : a.operations) {
            switch (o.getOperationType()) {
                case SLEEP:
                    sleep((SleepOperation) o);
                    break;
                case SEND_PACKET:
                    sendPacket((SendPacketOperation) o);
                    break;
                default:
                    throw new RuntimeException("No operation type known");
            }
        }
    }

    private void sendPacket(SendPacketOperation a) throws IOException {
        System.out.println("UDP SEND: " + a.toString());
        DatagramPacket packet = new DatagramPacket(a.bytes, a.bytes.length, INET_ADDRESS, PORT);
        socket.send(packet);
    }

    private void connect() throws IOException {
        try (DatagramSocket connectSocket = new DatagramSocket()) {
            final int connectPort = 48899;
            byte[] buf = "Link_Wi-Fi".getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, INET_ADDRESS, connectPort);

            System.out.println("UDP SEND: " + Arrays.toString(buf));
            connectSocket.send(packet);

            byte[] receiveData = new byte[32];
            DatagramPacket receivePacket = new DatagramPacket(receiveData,
                    receiveData.length);
            boolean found = false;
            while (!found) {
                connectSocket.receive(receivePacket);
                String sentence = new String(receivePacket.getData(), 0,
                        receivePacket.getLength());
                System.out.println("UDP RECEIVED: " + sentence);
                if ("192.168.1.114,ACCF233F9260,".equals(sentence)) {
                    found = true;
                    System.out.println("UDP LINKED");
                }
            }
        }
    }

    private void sleep(SleepOperation sleepOperation) throws InterruptedException {
        System.out.println("OP: "+sleepOperation.toString());
        Thread.sleep(sleepOperation.sleepLengthMillis);
    }
}
