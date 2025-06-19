package com.bist.ouch_ouch.SoupBinTCP.models;

import java.nio.ByteBuffer;

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
