package com.owl.udpsockets;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public abstract class UdpRunnable implements Runnable {
 
    final DatagramSocket socket;
    final String MULTICAST_GROUP_ID = "192.168.1.114";
    final int PORT = 8899;
    final InetAddress group;
    final byte[] onCommand;
    final byte[] offCommand;

    public UdpRunnable(byte[] onCommand, byte[] offCommand) throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        group = InetAddress.getByName(MULTICAST_GROUP_ID);
        this.onCommand = onCommand;
        this.offCommand = offCommand;
    }
    
}
