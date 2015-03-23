package com.owl.udpsockets;

public class Brightness {

    public static byte[] toByteArray(Integer brightness) {
        byte[] byteArray = {0x4E , brightness.byteValue(), 0x55};
        return byteArray;
    }
}
