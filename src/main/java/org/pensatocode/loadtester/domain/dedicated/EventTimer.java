package org.pensatocode.loadtester.domain.dedicated;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * EventTimer to indicate timeout limit when processing events
 */
public final class EventTimer implements Delayed {

    private final String key;
    private final long startTime;

    public EventTimer(String key, long delayInMilliseconds) {
        this.key = key;
        this.startTime = System.currentTimeMillis() + delayInMilliseconds;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long diff = startTime - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return saturatedCast(this.startTime - ((EventTimer) o).startTime);
    }

    private int saturatedCast(long value) {
        if (value > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        if (value < Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }
        return (int) value;
    }

    public String getKey() {
        return key;
    }
}
