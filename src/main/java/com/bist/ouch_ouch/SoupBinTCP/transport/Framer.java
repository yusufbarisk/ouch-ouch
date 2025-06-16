package com.bist.ouch_ouch.SoupBinTCP.transport;

import jakarta.websocket.Session;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class Framer {

    @Autowired
    Session session;

    ByteBuffer byteBuffer;

    public Framer(ByteBuffer recvBuffer) {
        this.byteBuffer = recvBuffer;
    }

    public void process() {
        byteBuffer.flip();

        while (byteBuffer.remaining() >= 2) {
            byteBuffer.mark();

            int length = byteBuffer.getShort() & 0xFFFF; // Read unsigned 2-byte length

            if (byteBuffer.remaining() < length) {
                byteBuffer.reset();
                break;
            }

            byte[] message = new byte[length];
            byteBuffer.get(message);

            handleMessage(message);
        }

        byteBuffer.compact();
    }

    private void handleMessage(byte[] message) {
        session.
        System.out.println("Received message: " + Arrays.toString(message));
    }

}
