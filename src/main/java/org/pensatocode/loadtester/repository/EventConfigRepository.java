package org.pensatocode.loadtester.repository;

import org.pensatocode.loadtester.domain.EventConfig;

import org.pensatocode.simplicity.jdbc.JdbcRepository;
import org.springframework.data.repository.query.Param;

public interface EventConfigRepository extends JdbcRepository<EventConfig, Long> {
    EventConfig findByOffset(Integer offset);
    Integer deleteEventConfigs(@Param("id") Long id);
}