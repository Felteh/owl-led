package com.owl.udpsockets;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class Sender {

    public void connect() throws IOException {
        final int PORT = 48899;
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            final String MULTICAST_GROUP_ID = "	192.168.1.70";
            byte[] buf = "Link_Wi-Fi".getBytes();
            final InetAddress group = InetAddress.getByName(MULTICAST_GROUP_ID);
            DatagramPacket packet = new DatagramPacket(buf, buf.length, group, PORT);

            System.out.println("UDP SEND: " + Arrays.toString(buf));
            socket.send(packet);

            byte[] receiveData = new byte[32];
            DatagramPacket receivePacket = new DatagramPacket(receiveData,
                    receiveData.length);
            boolean found = false;
            while (!found) {
                socket.receive(receivePacket);
                String sentence = new String(receivePacket.getData(), 0,
                        receivePacket.getLength());
                System.out.println("UDP RECEIVED: " + sentence);
                if ("192.168.1.70,ACCF233F9260,".equals(sentence)) {
                    found = true;
                    System.out.println("UDP LINKED");
                }
            }
        }
    }

    void sendString(byte... command) throws IOException {
        try (DatagramSocket socket = new DatagramSocket()) {
            final String MULTICAST_GROUP_ID = "192.168.1.70";
            final int PORT = 8899;
            final InetAddress group = InetAddress.getByName(MULTICAST_GROUP_ID);
            DatagramPacket packet = new DatagramPacket(command, command.length, group, PORT);
            System.out.println("UDP SEND: " + Arrays.toString(command));
            socket.send(packet);
        }
    }
}
