package org.pensatocode.loadtester.util;

import org.pensatocode.loadtester.domain.EventConfig;

import java.util.SplittableRandom;

public final class EventConfigUtil {

    private static final SplittableRandom splittableRandom = new SplittableRandom();

    private EventConfigUtil() {
        // Util
    }

    public static EventConfig createRandomEventConfig(String experimentName, String currentStrategy) {
        Short configMaxEvents = (short) splittableRandom.nextInt(1, 8);
        Integer configMaxTime = splittableRandom.nextInt(1, 50) * 100; // 100ms to 5000 ms
        Short configPageReads = (short) splittableRandom.nextInt(1, 10);
        Short configPageSize = (short) splittableRandom.nextInt(20, 40);
        Short configLoopSkips  = (short) splittableRandom.nextInt(1, 4);
        return new EventConfig(0L, currentStrategy, experimentName, configMaxEvents, configMaxTime,
                configPageReads, configPageSize, configLoopSkips, (short) 0, 0L);
    }
}
