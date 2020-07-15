package org.pensatocode.loadtester.handler;

import org.pensatocode.loadtester.domain.EventConfig;

public interface EventHandler {
    Short processEvents(EventConfig eventConfig);
}
