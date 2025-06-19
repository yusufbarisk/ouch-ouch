package com.bist.ouch_ouch.SoupBinTCP.models;

import java.nio.ByteBuffer;

import static com.bist.ouch_ouch.SoupBinTCP.utils.AsciiUtils.readAsciiString;

class LoginAccepted implements SoupPacket {

    private ByteBuffer byteBuffer;
    private int off;

    public LoginAccepted wrap(ByteBuffer src, int off) {
        this.byteBuffer = src;
        this.off = off;
        return this;
    }

    @Override
    public char type() {
        return readAsciiString(byteBuffer, off + 2, 1).charAt(0);
    }

    // offset=3, len=10, alphanumeric
    public String session() {
        return readAsciiString(byteBuffer, off + 3, 10);
    }

    // offset=13, len=20, alphanumeric
    public String sequenceNumber() {
        return readAsciiString(byteBuffer, off + 9, 10);
    }
}
