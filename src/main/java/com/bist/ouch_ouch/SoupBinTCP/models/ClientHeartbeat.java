package com.bist.ouch_ouch.SoupBinTCP.models;

import java.nio.ByteBuffer;

public class ClientHeartbeat implements SoupPacket {

    private ByteBuffer byteBuffer;
    private int off;

    public ClientHeartbeat wrap(ByteBuffer src, int off) {
        this.byteBuffer = src;
        this.off = off;
        return this;
    }

    @Override
    public char type() {
        return 'R';
    }
}
