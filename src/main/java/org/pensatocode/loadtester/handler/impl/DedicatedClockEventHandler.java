package org.pensatocode.loadtester.handler.impl;

import lombok.extern.slf4j.Slf4j;
import org.pensatocode.loadtester.domain.EventConfig;
import org.pensatocode.loadtester.domain.dedicated.EventTimer;
import org.pensatocode.loadtester.handler.AbstractEventHandler;
import org.pensatocode.loadtester.handler.impl.dedicated.WatchMaker;
import org.pensatocode.loadtester.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class DedicatedClockEventHandler extends AbstractEventHandler {

    public DedicatedClockEventHandler(@Autowired ProductRepository productRepository) {
        super(productRepository);
    }

    @Override
    public Short processEvents(EventConfig eventConfig) {
        String key = UUID.randomUUID().toString();
        WatchMaker.getWatches().put(key, true);
        WatchMaker.getEvents().offer(new EventTimer(key, eventConfig.getConfigMaxTime()));
        short eventsCounter = 0;
        short loopCounter = 0;
        while (WatchMaker.getWatches().get(key) && eventsCounter < eventConfig.getConfigMaxEvents()) {
            eventsCounter += super.doSomeWork(eventConfig, loopCounter);
            if (loopCounter < 10) loopCounter++;
        }
        return eventsCounter;
    }

}
