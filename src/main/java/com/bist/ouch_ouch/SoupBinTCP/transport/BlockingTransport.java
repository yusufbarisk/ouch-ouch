package com.bist.ouch_ouch.SoupBinTCP.transport;

import com.bist.ouch_ouch.SoupBinTCP.session.SoupBinTCPSession;

import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;

public class BlockingTransport implements Runnable {

    Socket socket;
    SoupBinTCPSession session;
    ByteBuffer recvBuffer = ByteBuffer.allocate(64 * 1024); // 64K buffer for now
    Framer framer = new Framer(recvBuffer);

    public BlockingTransport(Socket socket, SoupBinTCPSession session) {
        this.socket = socket;
        this.session = session;
    }

    @Override
    public void run() {
        // GLOBAL RETRY HANDLING


        try {

            while (!socket.isClosed()){
                int readSize = socket.getInputStream().read(recvBuffer.array());
                if (readSize > 0) {
                    recvBuffer.limit(readSize);
                    framer.process();
                    recvBuffer.clear();
                }
            }
        } catch (IOException e) {
            System.err.println("Connection lost: " + e.getMessage());
        }


    }
}
