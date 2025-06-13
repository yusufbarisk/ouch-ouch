package com.bist.ouch_ouch.SoupBinTCP;

import java.nio.ByteBuffer;

import static com.bist.ouch_ouch.SoupBinTCP.utils.StringUtils.readAsciiString;

public interface SoupPacket {char type();}

class LoginAccepted implements SoupPacket{

    private ByteBuffer byteBuffer;
    private int off;

    public LoginAccepted wrap(ByteBuffer src, int off){
        this.byteBuffer = src;
        this.off = off;
        return this;
    }

    // TODO offsetleri gozden gecir, BIST spec icin 3 olacak sanirim
    public String session() {return  readAsciiString(byteBuffer, off + 1, 10);}
    public long nextSeqNumber() { return Long.parseLong(readAsciiString(byteBuffer, off + 1, 20));}

    @Override
    public char type() {
        return 'A';
    }
}

class LoginRejected implements SoupPacket{

    private ByteBuffer byteBuffer;
    private int off;

    public LoginRejected wrap(ByteBuffer src, int off){
        this.byteBuffer = src;
        this.off = off;
        return this;
    }

    // TODO offsetleri gozden gecir, BIST spec icin 3 olacak sanirim
    public String session() {return  readAsciiString(byteBuffer, off + 1, 10);}
    public long nextSeqNumber() { return Long.parseLong(readAsciiString(byteBuffer, off + 1, 20));}

    @Override
    public char type() {
        return 'J';
    }
}

