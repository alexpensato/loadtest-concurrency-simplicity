package org.pensatocode.loadtester.controllers;

import org.pensatocode.loadtester.bean.CounterBean;
import org.pensatocode.loadtester.bean.CountersBean;
import org.pensatocode.loadtester.services.GeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
public class DataInitRestController {

    private final GeneratorService generatorService;

    public DataInitRestController(@Autowired GeneratorService generatorService) {
        this.generatorService = generatorService;
    }

    @GetMapping("/count")
    public CountersBean countAllRepositories() {
        return new CountersBean(
                this.generatorService.countAllRepositories(),
                this.generatorService.getAllAtomicCounters()
        );
    }

    @GetMapping("/generate")
    public CounterBean generateRepositoriesData() {
        return this.generatorService.generateData();
    }
}
