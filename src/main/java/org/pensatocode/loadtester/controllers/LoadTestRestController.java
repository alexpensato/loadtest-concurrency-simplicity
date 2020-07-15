package org.pensatocode.loadtester.controllers;

import lombok.extern.slf4j.Slf4j;
import org.pensatocode.loadtester.handler.EventHandler;
import org.pensatocode.loadtester.model.Strategy;
import org.pensatocode.loadtester.services.LoadTestService;
import org.pensatocode.loadtester.services.StrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@Slf4j
@RestController
@RequestMapping("/loadtest")
public class LoadTestRestController {

    private final StrategyService strategyService;
    private final LoadTestService loadTestService;

    private final int PRODUCTS_OFFSET;
    private final int EVENTS_OFFSET;

    public LoadTestRestController(@Autowired StrategyService strategyService,
                                  @Autowired LoadTestService loadTestService) {
        this.strategyService = strategyService;
        this.loadTestService = loadTestService;
        Properties properties = loadApplicationProperties();
        PRODUCTS_OFFSET = Optional.ofNullable(properties.getProperty("pensatocode.loadtest.offset.product"))
                .map(Integer::parseInt)
                .orElse(500000); // half-million items
        EVENTS_OFFSET = Optional.ofNullable(properties.getProperty("pensatocode.loadtest.offset.event"))
                .map(Integer::parseInt)
                .orElse(200000); // two hundred thousand events
    }

    /**
     * Endpoint to generate necessary amount of data to initiate load test
     */
    @GetMapping("/data/generate")
    public String generate() {
        return loadTestService.generateTestData(PRODUCTS_OFFSET, EVENTS_OFFSET);
    }

    /**
     * Endpoint to reset test data to its initial condition
     */
    @GetMapping("/data/reset")
    public String reset() {
        return loadTestService.resetTestData(PRODUCTS_OFFSET, EVENTS_OFFSET);
    }

    /**
     * Endpoint that perform a few tasks and will be used by external load testing tool
     *
     * @param strategyKey key for strategy to be used
     * @param experimentName experiment name to identify load test
     */
    @GetMapping("/run/{strategyKey}/{experimentName}")
    public String singleEvent(@PathVariable("strategyKey") String strategyKey,
                              @PathVariable("experimentName") String experimentName) {
        Strategy strategy = strategyService.retrieveByKey(strategyKey);
        EventHandler eventHandler = strategyService.createEventHandlerByStrategy(strategy);
        return loadTestService.executeSingleStep(strategy, eventHandler, experimentName);
    }

    private Properties loadApplicationProperties() {
        Properties properties = new Properties();
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");
            if (inputStream != null) {
                properties.load(inputStream);
            }
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return properties;
    }
}
