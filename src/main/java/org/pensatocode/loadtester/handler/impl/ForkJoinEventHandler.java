package org.pensatocode.loadtester.handler.impl;

import org.pensatocode.loadtester.domain.EventConfig;
import org.pensatocode.loadtester.handler.AbstractEventHandler;
import org.pensatocode.loadtester.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Component
public class ForkJoinEventHandler extends AbstractEventHandler {

    private final Executor executor = Executors.newCachedThreadPool();

    public ForkJoinEventHandler(@Autowired ProductRepository productRepository) {
        super(productRepository);
    }

    @Override
    public Short processEvents(EventConfig eventConfig) {
        CompletableFuture<Boolean> cf = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(eventConfig.getConfigMaxTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;
            }, executor);
        short eventsCounter = 0;
        short loopCounter = 0;
        while (cf.getNow(true) && eventsCounter < eventConfig.getConfigMaxEvents()) {
            eventsCounter += super.doSomeWork(eventConfig, loopCounter);
            if (loopCounter < 10) loopCounter++;
        }
        return eventsCounter;
    }
}
