package com.owl.light;

public class Brightness {

    public static byte[] toByteArray(Integer brightness) {
        byte[] byteArray = {0x4E, brightness.byteValue(), 0x55};
        return byteArray;
    }
}
