package org.pensatocode.loadtester.repository;

import org.pensatocode.loadtester.domain.EventConfig;

import org.pensatocode.simplicity.jdbc.JdbcRepository;

public interface EventConfigRepository extends JdbcRepository<EventConfig, Long> {
    Integer deleteWithOffset(Integer offset);
}