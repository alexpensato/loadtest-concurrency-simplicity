package org.pensatocode.loadtester.handler.impl;

import org.pensatocode.loadtester.domain.EventConfig;
import org.pensatocode.loadtester.handler.AbstractEventHandler;
import org.pensatocode.loadtester.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class BasicEventHandler extends AbstractEventHandler {

    public BasicEventHandler(@Autowired ProductRepository productRepository) {
        super(productRepository);
    }

    @Override
    public Short processEvents(EventConfig eventConfig) {
        short eventsCounter = 0;
        short loopCounter = 0;
        long endTime = System.nanoTime() + Duration.ofMillis(eventConfig.getConfigMaxTime()).toNanos();
        while (System.nanoTime() < endTime && eventsCounter < eventConfig.getConfigMaxEvents()) {
            eventsCounter += super.doSomeWork(eventConfig, loopCounter);
            if (loopCounter < 10) loopCounter++;
        }
        return eventsCounter;
    }
}
