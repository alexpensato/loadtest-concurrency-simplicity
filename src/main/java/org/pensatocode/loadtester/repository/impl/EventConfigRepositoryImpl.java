package org.pensatocode.loadtester.repository.impl;

import org.pensatocode.loadtester.domain.EventConfig;
import org.pensatocode.loadtester.repository.mapper.EventConfigMapper;
import org.pensatocode.loadtester.repository.EventConfigRepository;

import org.pensatocode.simplicity.jdbc.AbstractJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;

@Repository(value = "eventConfigRepository")
public class EventConfigRepositoryImpl extends AbstractJdbcRepository<EventConfig, Long> implements EventConfigRepository {

    public EventConfigRepositoryImpl(@Autowired JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, new EventConfigMapper(), "event_config", EventConfig.class, "id");
    }

    @Override
    public EventConfig findByOffset(Integer offset) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM event_config ORDER BY id LIMIT 1 OFFSET ?",
                new Object[]{offset},
                new int[]{Types.INTEGER},
                rowMapper);
    }

    @Override
    public Integer deleteEventConfigs(@Param("id") Long id) {
        Integer lineCount = jdbcTemplate.update(
                "DELETE FROM event_config WHERE id > ?",
                new Object[]{id},
                new int[]{Types.BIGINT}
        );
        decreaseCounter(lineCount);
        return lineCount;
    }
}