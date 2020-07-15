package org.pensatocode.loadtester.controllers.api;

import org.pensatocode.loadtester.domain.EventConfig;
import org.pensatocode.loadtester.repository.EventConfigRepository;

import org.pensatocode.simplicity.web.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/eventConfigs")
public class EventConfigRestController extends AbstractController<EventConfig, Long> {
    public EventConfigRestController(@Autowired EventConfigRepository eventConfigRepository) {
        super(eventConfigRepository);
    }
}