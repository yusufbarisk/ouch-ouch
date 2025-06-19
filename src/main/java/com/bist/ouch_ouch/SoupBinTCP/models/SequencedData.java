package com.bist.ouch_ouch.SoupBinTCP.models;

import java.nio.ByteBuffer;

import static com.bist.ouch_ouch.SoupBinTCP.utils.AsciiUtils.readAsciiString;

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
