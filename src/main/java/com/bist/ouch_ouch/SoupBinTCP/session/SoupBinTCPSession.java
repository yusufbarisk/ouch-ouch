package com.bist.ouch_ouch.SoupBinTCP.session;

import com.bist.ouch_ouch.SoupBinTCP.SoupPacket;
import com.bist.ouch_ouch.SoupBinTCP.transport.SocketConnector;
import org.springframework.stereotype.Component;

@Component
public class SoupBinTCPSession {

    SocketConnector sc;

    /**
     * @param soupPacket the received SoupPacket
     *                   This method is called when a SoupPacket is received.
     *                   this method handles all the SoupPackets received from the server.
     *                   If necessary the pkt will be escalated to the OUCH layer. Most other
     *                   packages will be heartbeats or session related hence will be processed here.
     */
    public void onPacket(SoupPacket soupPacket) {

    }
}
