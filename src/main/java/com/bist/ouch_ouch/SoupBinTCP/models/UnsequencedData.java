package com.bist.ouch_ouch.SoupBinTCP.models;

import java.nio.ByteBuffer;

import static com.bist.ouch_ouch.SoupBinTCP.utils.AsciiUtils.readAsciiString;

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
