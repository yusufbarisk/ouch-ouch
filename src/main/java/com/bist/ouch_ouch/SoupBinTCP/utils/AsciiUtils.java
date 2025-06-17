package com.bist.ouch_ouch.SoupBinTCP.utils;

import java.nio.ByteBuffer;

public class AsciiUtils {


    public static String readAsciiString(ByteBuffer buffer, int index, int length) {
        int originalPos = buffer.position();
        buffer.position(index);
        byte[] bytes = new byte[length];

        buffer.get(bytes, 0, length);
        buffer.position(originalPos);

        return new String(bytes, java.nio.charset.StandardCharsets.US_ASCII);
    }
    public static int readAsciiInt(ByteBuffer buffer, int index, int length) {
        return Integer.parseInt(readAsciiString(buffer, index, length));
    }


}