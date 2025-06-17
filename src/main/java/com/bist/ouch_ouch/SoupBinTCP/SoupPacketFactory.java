package com.bist.ouch_ouch.SoupBinTCP;

import java.nio.ByteBuffer;

public enum SoupPacketFactory {
    INSTANCE;


    private final SequencedData   sequencedData   = new SequencedData();
    private final UnsequencedData unsequencedData = new UnsequencedData();
    private final LoginRequest    loginRequest     = new LoginRequest();
    private final LogOutRequest   logOutRequest    = new LogOutRequest();
    private final EndOfSession   endOfSession   = new EndOfSession();
    private final LoginAccepted   loginAccepted   = new LoginAccepted();
    private final LoginRejected   loginRejected   = new LoginRejected();
    private final ServerHeartbeat serverHeartbeat = new ServerHeartbeat();
    private final ClientHeartbeat clientHeartbeat = new ClientHeartbeat();


    /** This way of doing this may be unsafe. Check later */
    public SoupPacket wrap(ByteBuffer buf, int pos, char type) {
        return switch (type) {

            case 'Z' -> endOfSession.wrap(buf, pos);
            case 'L' -> loginRequest.wrap(buf, pos);
            case 'O' -> logOutRequest.wrap(buf, pos);
            case 'A' -> loginAccepted.wrap(buf, pos);
            case 'J' -> loginRejected.wrap(buf, pos);
            case 'H' -> serverHeartbeat.wrap(buf, pos);
            case 'R' -> clientHeartbeat.wrap(buf, pos);
            case 'S' -> sequencedData.wrap(buf, pos);
            case 'U' -> unsequencedData.wrap(buf, pos);
            default  -> throw new IllegalArgumentException(
                    "Unknown SoupBinTCP type '" + type + '\'');
        };
    }
}