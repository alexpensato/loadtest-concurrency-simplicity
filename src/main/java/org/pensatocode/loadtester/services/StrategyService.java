package org.pensatocode.loadtester.services;

import lombok.extern.slf4j.Slf4j;
import org.pensatocode.loadtester.domain.EventConfig;
import org.pensatocode.loadtester.handler.impl.BasicEventHandler;
import org.pensatocode.loadtester.handler.AbstractEventHandler;
import org.pensatocode.loadtester.handler.impl.ForkJoinEventHandler;
import org.pensatocode.loadtester.handler.impl.DedicatedClockEventHandler;
import org.pensatocode.loadtester.model.Strategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.MissingResourceException;
import java.util.SplittableRandom;

@Service
@Slf4j
public class StrategyService {

    private final BasicEventHandler basicEventHandler;
    private final ForkJoinEventHandler forkJoinEventHandler;
    private final DedicatedClockEventHandler dedicatedClockEventHandler;

    private final SplittableRandom splittableRandom = new SplittableRandom();

    private Strategy currentStrategy = Strategy.BASIC;

    public StrategyService(@Autowired BasicEventHandler basicEventHandler,
                           @Autowired ForkJoinEventHandler forkJoinEventHandler,
                           @Autowired DedicatedClockEventHandler dedicatedClockEventHandler) {
        this.basicEventHandler = basicEventHandler;
        this.forkJoinEventHandler = forkJoinEventHandler;
        this.dedicatedClockEventHandler = dedicatedClockEventHandler;
    }

    public Strategy getCurrent() {
        return currentStrategy;
    }

    public Strategy update(String value) {
        Strategy result = Strategy.find(value);
        if (result != null) {
            this.currentStrategy = result;
        }
        log.debug(String.format("Changing strategies: %s is %s", value, result));
        return this.currentStrategy;
    }

    public EventConfig createRandomEventConfig(String experimentName) {
        Short configMaxEvents = (short) splittableRandom.nextInt(1, 8);
        Integer configMaxTime = splittableRandom.nextInt(1, 50) * 100; // 100ms to 5000 ms
        Short configPageReads = (short) splittableRandom.nextInt(1, 10);
        Short configPageSize = (short) splittableRandom.nextInt(20, 40);
        Short configLoopSkips  = (short) splittableRandom.nextInt(1, 4);
        return new EventConfig(0L, currentStrategy.getKey(), experimentName, configMaxEvents, configMaxTime,
                configPageReads, configPageSize, configLoopSkips, (short) 0, 0L);
    }

    public AbstractEventHandler createEventHandlerByCurrentStrategy() {
        switch(currentStrategy) {
            case BASIC:
                return basicEventHandler;
            case FORK_JOIN:
                return forkJoinEventHandler;
            case DEDICATED_CLOCK:
                return dedicatedClockEventHandler;
        }
        throw new MissingResourceException(
                "Current Strategy not found",
                this.getClass().getName(),
                AbstractEventHandler.class.getName());
    }
}
