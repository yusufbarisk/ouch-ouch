package com.bist.ouch_ouch.SoupBinTCP.session;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;


public final class HeartbeatScheduler implements AutoCloseable, Runnable {

    private static final long HB_INTERVAL_NS     = TimeUnit.SECONDS.toNanos(1);
    private static final long SERVER_TIMEOUT_NS  = TimeUnit.SECONDS.toNanos(3);
    private static final long TICK_PERIOD_MS     = 100;            // 10 Hz

    private final AtomicLong lastSentNs  = new AtomicLong(System.nanoTime());
    private final AtomicLong lastRecvNs  = new AtomicLong(System.nanoTime());


    private final Runnable sendHeartbeat;
    private final Runnable handleServerTimeout;


    private final ScheduledExecutorService exec =
            Executors.newSingleThreadScheduledExecutor(r -> {
                Thread t = new Thread(r, "hb-timer");
                t.setDaemon(true);
                return t;
    });

    public HeartbeatScheduler(Runnable sendHeartbeat, Runnable handleServerTimeout) {
        this.sendHeartbeat = sendHeartbeat;
        this.handleServerTimeout = handleServerTimeout;
    }

    public void markSent()  { lastSentNs.set(System.nanoTime()); }
    public void markRecv()  { lastRecvNs.set(System.nanoTime()); }

    @Override
    public void run() {
        long now = System.nanoTime();

        if (now - lastSentNs.get() >= HB_INTERVAL_NS) {
            lastSentNs.set(now);
            sendHeartbeat.run();
        }
        if (now - lastRecvNs.get() >= SERVER_TIMEOUT_NS) {
            handleServerTimeout.run();
        }
    }

    @Override
    public void close() throws Exception {
        exec.shutdownNow();
    }
}
