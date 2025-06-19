package com.bist.ouch_ouch.SoupBinTCP.models;

import java.nio.ByteBuffer;

import static com.bist.ouch_ouch.SoupBinTCP.utils.AsciiUtils.readAsciiString;

class LoginRejected implements SoupPacket {

    private ByteBuffer byteBuffer;
    private int off;

    public LoginRejected wrap(ByteBuffer src, int off) {
        this.byteBuffer = src;
        this.off = off;
        return this;
    }

    @Override
    public char type() {
        return readAsciiString(byteBuffer, off + 2, 1).charAt(0);
    }

    // offset=3, len=1, alphanumeric
    public char rejectReasonCode() {
        return readAsciiString(byteBuffer, off + 3, 1).charAt(0);
    }
}
