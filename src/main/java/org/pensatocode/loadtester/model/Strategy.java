package org.pensatocode.loadtester.model;

import java.util.HashMap;
import java.util.Map;

public enum Strategy {
    BASIC("basic"),
    FORK_JOIN("forkjoin"),
    DEDICATED_CLOCK("dedicated");

    private static class MapHolder {
        private static final Map<String, Strategy> INSTANCE = new HashMap<>();
    }

    Strategy(String key) {
        this.key = key;
        MapHolder.INSTANCE.put(key, this);
    }

    private final String key;

    public String getKey() {
        return key;
    }

    public static Strategy find(String value) {
        return Strategy.MapHolder.INSTANCE.get(value);
    }
}
