package org.pensatocode.loadtester.controllers;

import org.pensatocode.loadtester.services.StrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/strategy")
public class StrategyRestController {

    private final StrategyService strategyService;

    public StrategyRestController(@Autowired StrategyService strategyService) {
        this.strategyService = strategyService;
    }

    @GetMapping
    public String getStrategy() {
        return strategyService.getCurrent().getKey();
    }

    @GetMapping("/{name}")
    public String updateStrategy(@PathVariable("name") String name) {
        return strategyService.update(name).getKey();
    }

}
