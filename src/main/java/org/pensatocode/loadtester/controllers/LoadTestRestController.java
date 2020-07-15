package org.pensatocode.loadtester.controllers;

import lombok.extern.slf4j.Slf4j;
import org.pensatocode.loadtester.domain.EventConfig;
import org.pensatocode.loadtester.handler.EventHandler;
import org.pensatocode.loadtester.repository.EventConfigRepository;
import org.pensatocode.loadtester.repository.ProductRepository;
import org.pensatocode.loadtester.services.StrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@Slf4j
@RestController
@RequestMapping("/loadtest")
public class LoadTestRestController {

    private final StrategyService strategyService;

    private final EventConfigRepository eventConfigRepository;
    private final ProductRepository productRepository;

    public LoadTestRestController(@Autowired StrategyService strategyService,
                                  @Autowired EventConfigRepository eventConfigRepository,
                                  @Autowired ProductRepository productRepository) {
        this.strategyService = strategyService;
        this.eventConfigRepository = eventConfigRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/reset")
    public String reset() {
        // Dev
        Integer productCount = productRepository.deleteWithOffset(30000);
        Integer eventCount = eventConfigRepository.deleteWithOffset(50);
        // Prod
//        Integer productCount = productRepository.deleteWithOffset(500000);
//        Integer eventCount = eventConfigRepository.deleteWithOffset(200000);
        return String.format("Deleted products = %d, deleted events = %d", productCount, eventCount);
    }

    @GetMapping("/start/{name}")
    public String loadTest(@PathVariable("name") String name) {
        EventHandler eventHandler = strategyService.createEventHandlerByCurrentStrategy();
        EventConfig eventConfig = strategyService.createRandomEventConfig(name);
        long startTime = System.nanoTime();
        Short executedEvents = eventHandler.processEvents(eventConfig);
        long duration = System.nanoTime() - startTime;
        eventConfig.setExecutedTime(Duration.ofNanos(duration).toMillis());
        eventConfig.setExecutedEvents(executedEvents);
        eventConfigRepository.save(eventConfig);
        return "OK";
    }

}
