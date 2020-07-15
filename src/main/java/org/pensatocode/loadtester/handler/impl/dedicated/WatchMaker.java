package org.pensatocode.loadtester.handler.impl.dedicated;

import lombok.extern.slf4j.Slf4j;
import org.pensatocode.loadtester.domain.dedicated.EventTimer;

import java.util.Map;
import java.util.concurrent.*;

@Slf4j
public class WatchMaker implements AutoCloseable {

    private final Map<String, Boolean> watches =  new ConcurrentHashMap<>();
    private final DelayQueue<EventTimer> events = new DelayQueue<>();
    private final ExecutorService executorService;

    private static volatile boolean keepWorkerRunning = true;
    private static final WatchMaker instance = new WatchMaker();

    public static Map<String, Boolean> getWatches() {
        return instance.watches;
    }

    public static DelayQueue<EventTimer> getEvents() {
        return instance.events;
    }

    public WatchMaker() {
        executorService = Executors.newSingleThreadExecutor();
        Runnable clockWork = new ClockWork();
        executorService.execute(clockWork);
    }

    private static class ClockWork implements Runnable {
        @Override
        public void run() {
            while (keepWorkerRunning) {
                try {
                    EventTimer expiredEvent = WatchMaker.getEvents().take();
                    WatchMaker.getWatches().replace(expiredEvent.getKey(), false);
                } catch (InterruptedException e) {
                    log.warn("ClockWork failed to take an event");
                }
            }
        }
    }

    @Override
    public void close() {
        keepWorkerRunning = false;
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}
