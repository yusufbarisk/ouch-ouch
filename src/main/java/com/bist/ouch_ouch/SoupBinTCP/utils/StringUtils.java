package com.bist.ouch_ouch.SoupBinTCP.utils;

import org.agrona.DirectBuffer;
import org.agrona.MutableDirectBuffer;

import java.nio.ByteBuffer;

public class StringUtils {


    public static String readAsciiString(ByteBuffer buffer, int index, int length) {
        int originalPos = buffer.position();
        buffer.position(index);
        byte[] bytes = new byte[length];

        buffer.get(bytes, 0, length);
        buffer.position(originalPos);

        return new String(bytes, java.nio.charset.StandardCharsets.US_ASCII);
    }


}