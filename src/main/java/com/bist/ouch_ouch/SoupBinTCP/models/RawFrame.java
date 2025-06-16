package com.bist.ouch_ouch.SoupBinTCP.models;

import java.nio.ByteBuffer;

public record RawFrame(
        char type,
        ByteBuffer buffer,
        int offset,
        int len
) {}
