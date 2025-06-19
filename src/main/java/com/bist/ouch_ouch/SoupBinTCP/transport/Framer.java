package com.bist.ouch_ouch.SoupBinTCP.transport;

import com.bist.ouch_ouch.SoupBinTCP.models.SoupPacket;
import com.bist.ouch_ouch.SoupBinTCP.models.SoupPacketFactory;

import java.nio.ByteBuffer;
import java.util.function.Consumer;

public final class Framer {
    public void process(ByteBuffer recvBuf, Consumer<SoupPacket> sink) {
        while (recvBuf.remaining() >= 2) {
            recvBuf.mark();
            int len = Short.toUnsignedInt(recvBuf.getShort());

            if (recvBuf.remaining() < len){
                recvBuf.reset();
                return;
            }

        int start = recvBuf.position();
        char type = (char) recvBuf.get(start);
        SoupPacket pkt = SoupPacketFactory.INSTANCE.wrap(recvBuf, start, type);
        sink.accept(pkt);

        recvBuf.position(start + len);
        }
    }
}

