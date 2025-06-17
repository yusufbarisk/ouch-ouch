package com.bist.ouch_ouch.SoupBinTCP.transport;

import com.bist.ouch_ouch.SoupBinTCP.config.Config;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SocketConnector {
    // Turn into socketchannel later

    public Socket initiateServerConnection() throws IOException {
        try {
            Socket socket = new Socket();
            socket.setTcpNoDelay(true);
            socket.setSoTimeout(Config.READ_TIMEOUT_MILLIS);
            socket.connect(new InetSocketAddress(Config.HOST_ADDR, Config.HOST_PORT), Config.CONNECT_TIMEOUT_MILLIS);
            return socket;
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

}

