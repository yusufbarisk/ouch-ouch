package com.bist.ouch_ouch.SoupBinTCP.models;

import java.nio.ByteBuffer;

import static com.bist.ouch_ouch.SoupBinTCP.utils.AsciiUtils.readAsciiInt;
import static com.bist.ouch_ouch.SoupBinTCP.utils.AsciiUtils.readAsciiString;

// Possible Optimizations
// type da tekrar tekrar okumana gerek yok buraya okunmus olarak geliyor
class LoginRequest implements SoupPacket {

    private ByteBuffer byteBuffer;
    private int off;

    public LoginRequest wrap(ByteBuffer src, int off) {
        this.byteBuffer = src;
        this.off = off;
        return this;
    }


    // According to spec: offset=0, len=2, Integer (likely raw, not ASCII)
    public int packetLen() {
        // If this is ASCII-encoded, use readAsciiInt; if not, use buffer.getShort/off
        return readAsciiInt(byteBuffer, off, 2);
    }

    // offset=2, len=1, always "L"
    @Override
    public char type() {
        return readAsciiString(byteBuffer, off + 2, 1).charAt(0);
    }

    // offset=3, len=6, alphanumeric
    public String username() {
        return readAsciiString(byteBuffer, off + 3, 6);
    }

    // offset=9, len=10, alphanumeric
    public String password() {
        return readAsciiString(byteBuffer, off + 9, 10);
    }

    // offset=19, len=10, alphanumeric
    public String requestedSession() {
        return readAsciiString(byteBuffer, off + 19, 10);
    }

    // offset=29, len=20, numeric ASCII
    public long requestedSequenceNumber() {
        String seq = readAsciiString(byteBuffer, off + 29, 20).trim();
        return seq.isEmpty() ? 0 : Long.parseLong(seq);
    }
}
