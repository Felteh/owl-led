package com.owl.udpsockets;

public class Colours {

    public static byte[] toByteArray(Integer colour) {
        byte[] byteArray = {0x40, colour.byteValue(), 0x55};
        return byteArray;
    }
}
