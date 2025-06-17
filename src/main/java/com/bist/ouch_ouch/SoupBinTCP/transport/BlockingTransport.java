package com.bist.ouch_ouch.SoupBinTCP.transport;

import com.bist.ouch_ouch.SoupBinTCP.session.SoupBinTCPSession;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class BlockingTransport implements Runnable {
    private volatile boolean running = true;

    private final Socket            socket;
    private final SoupBinTCPSession session;
    private final  ByteBuffer       recvBuffer;
    private final  Framer           framer;
    private final InputStream       inputStream;



    public BlockingTransport(Socket socket, SoupBinTCPSession session) throws IOException {
        this.socket = socket;
        this.session = session;

        this.recvBuffer = ByteBuffer.allocate(64 * 1024); // 64K buffer for now
        this.inputStream = socket.getInputStream();
        this.framer  = new Framer(recvBuffer);

    }

    @Override
    public void run() {

        try {
            while (running){
                if (!recvBuffer.hasRemaining())
                    throw new IOException("Receive buffer overflow");

                int n = inputStream.read(recvBuffer.array(),
                        recvBuffer.position(),
                        recvBuffer.remaining());

                if (n == -1) throw new EOFException("Server closed the connection");
                if (n ==  0)  continue;             // read timed out

                recvBuffer.position(recvBuffer.position() + n);
                recvBuffer.flip();                    // switch to read-mode

                framer.process(recvBuffer, session::onPacket);

                recvBuffer.compact()
            }
//            while (!socket.isClosed()){
//                int readSize = socket.getInputStream().read(recvBuffer.array());
//                if (readSize > 0) {
//                    recvBuffer.limit(readSize);
//                    framer.process();
//                    recvBuffer.clear();
//                }
//            }
        } catch (IOException e) {
            System.err.println("Connection lost: " + e.getMessage());
        }


    }
}
