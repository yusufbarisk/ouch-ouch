package com.bist.ouch_ouch.SoupBinTCP.models;

import java.nio.ByteBuffer;

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
