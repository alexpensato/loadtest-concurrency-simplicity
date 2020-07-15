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

    public StrategyService(@Autowired BasicEventHandler basicEventHandler,
                           @Autowired ForkJoinEventHandler forkJoinEventHandler,
                           @Autowired DedicatedClockEventHandler dedicatedClockEventHandler) {
        this.basicEventHandler = basicEventHandler;
        this.forkJoinEventHandler = forkJoinEventHandler;
        this.dedicatedClockEventHandler = dedicatedClockEventHandler;
    }

    public Strategy retrieveByKey(String key) {
        Strategy result = Strategy.find(key);
        if (result == null) {
            throw new IllegalArgumentException("Strategy not found!");
        }
        return result;
    }

    public AbstractEventHandler createEventHandlerByStrategy(Strategy strategy) {
        switch(strategy) {
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
