package com.bist.ouch_ouch.SoupBinTCP.session;

import com.bist.ouch_ouch.SoupBinTCP.models.SoupPacket;
import com.bist.ouch_ouch.SoupBinTCP.transport.SocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SoupBinTCPSession {
    private static final Logger log = LoggerFactory.getLogger(SoupBinTCPSession.class);
    SocketConnector sc;

    HeartbeatScheduler heartbeatScheduler;
    static String SESSION_ID;


    /**
     * @param soupPacket the received SoupPacket
     *                   This method is called when a SoupPacket is received.
     *                   this method handles all the SoupPackets received from the server.
     *                   If necessary the pkt will be escalated to the OUCH layer. Most other
     *                   packages will be heartbeats or session related hence will be processed here.
     */
    public void onPacket(SoupPacket soupPacket) {
        heartbeatScheduler.markRecv();

        switch (soupPacket.type()) {
            // Handled by the OUCH layer
            case 'S': // Sequenced Data
                break;
            case 'U': // Unsequenced Data
                break;

            // Handled by the heartbeat handler
//            case 'R': // Client Heartbeat
//                break;
            case 'H': // Server Heartbeat
                heartbeatScheduler.markRecv(); // cok da gerekli durmuyor sanki ama

            case 'L': // Login Request
                log.info("Login request received: {}", soupPacket);
                break;
            case 'A': // Login Accepted
                log.info("Login accepted: {}", soupPacket);
                break;
            case 'J': // Login Rejected
                log.warn("Login rejected: {}", soupPacket);
                break;
            case 'O': // Log Out Request
                log.info("Log out request received: {}", soupPacket);
                break;
            case 'Z': // End of Session
                log.info("End of session received: {}", soupPacket);
                break;
            default:
                // Handle other types of packets or escalate to OUCH layer
                log.warn("Unknown SoupBinTCP packet type Not fitting pattern: {}", soupPacket.type());
        }
    }
}
