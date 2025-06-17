package com.bist.ouch_ouch.SoupBinTCP;

import java.nio.ByteBuffer;

import static com.bist.ouch_ouch.SoupBinTCP.utils.AsciiUtils.readAsciiInt;
import static com.bist.ouch_ouch.SoupBinTCP.utils.AsciiUtils.readAsciiString;

public interface SoupPacket {
    char type();
}

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

class LogOutRequest implements SoupPacket {

    private ByteBuffer byteBuffer;
    private int off;

    public LogOutRequest wrap(ByteBuffer src, int off) {
        this.byteBuffer = src;
        this.off = off;
        return this;
    }

    @Override
    public char type() {
        return 'O';
    }
}

class EndOfSession implements SoupPacket {

    private ByteBuffer byteBuffer;
    private int off;

    public EndOfSession wrap(ByteBuffer src, int off) {
        this.byteBuffer = src;
        this.off = off;
        return this;
    }

    @Override
    public char type() {
        return 'Z';
    }

}

class SequencedData implements SoupPacket {

    private ByteBuffer byteBuffer;
    private int off;

    public SequencedData wrap(ByteBuffer src, int off) {
        this.byteBuffer = src;
        this.off = off;
        return this;
    }

    @Override
    public char type() {
        return readAsciiString(byteBuffer, off + 2, 1).charAt(0);
    }

    // offset=3, len=variable, any alphanumeric data
    public String message() {
        return readAsciiString(byteBuffer, off + 3, byteBuffer.remaining() - off - 3); //TODO kontrol et
    }
}

class UnsequencedData implements SoupPacket {

    private ByteBuffer byteBuffer;
    private int off;

    public UnsequencedData wrap(ByteBuffer src, int off) {
        this.byteBuffer = src;
        this.off = off;
        return this;
    }

    @Override
    public char type() {
        return 'U';
    }

    // offset=3, len=variable, any alphanumeric data
    public String message() {
        return readAsciiString(byteBuffer, off + 3, byteBuffer.remaining() - off - 3); //TODO kontrol et
    }
}

class ServerHeartbeat implements SoupPacket {

    private ByteBuffer byteBuffer;
    private int off;

    public ServerHeartbeat wrap(ByteBuffer src, int off) {
        this.byteBuffer = src;
        this.off = off;
        return this;
    }

    @Override
    public char type() {
        return readAsciiString(byteBuffer, off + 2, 1).charAt(0);
    }

}

class ClientHeartbeat implements SoupPacket {

    private ByteBuffer byteBuffer;
    private int off;

    public ClientHeartbeat wrap(ByteBuffer src, int off) {
        this.byteBuffer = src;
        this.off = off;
        return this;
    }

    @Override
    public char type() {
        return readAsciiString(byteBuffer, off + 2, 1).charAt(0);
    }
}