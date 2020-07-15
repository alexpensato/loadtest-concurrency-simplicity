package org.pensatocode.loadtester.repository.mapper;

import org.pensatocode.loadtester.domain.EventConfig;

import org.pensatocode.simplicity.jdbc.mapper.TransactionalRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.Map;

public class EventConfigMapper extends TransactionalRowMapper<EventConfig> {

    @Override
    public EventConfig mapRow(ResultSet rs, int rowNum) {
        try {
            return new EventConfig(
                    rs.getLong("id"),
                    rs.getString("strategy"),
                    rs.getString("experiment_name"),
                    rs.getShort("config_max_events"),
                    rs.getInt("config_max_time"),
                    rs.getShort("config_page_reads"),
                    rs.getShort("config_page_size"),
                    rs.getShort("config_loop_skips"),
                    rs.getShort("executed_events"),
                    rs.getLong("executed_time")
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<String, Object> mapColumns(EventConfig entity) {
        Map<String, Object> mapping = new LinkedHashMap<>();
        mapping.put("id", entity.getId());
        mapping.put("strategy", entity.getStrategy());
        mapping.put("experiment_name", entity.getExperimentName());
        mapping.put("config_max_events", entity.getConfigMaxEvents());
        mapping.put("config_max_time", entity.getConfigMaxTime());
        mapping.put("config_page_reads", entity.getConfigPageReads());
        mapping.put("config_page_size", entity.getConfigPageSize());
        mapping.put("config_loop_skips", entity.getConfigLoopSkips());
        mapping.put("executed_events", entity.getExecutedEvents());
        mapping.put("executed_time", entity.getExecutedTime());
        return mapping;
    }

    @Override
    public Map<String, Integer> mapTypes() {
        final Map<String, Integer> mapping = new LinkedHashMap<>();
        mapping.put("id", Types.BIGINT);
        mapping.put("strategy", Types.VARCHAR);
        mapping.put("experiment_name", Types.VARCHAR);
        mapping.put("config_max_events", Types.SMALLINT);
        mapping.put("config_max_time", Types.INTEGER);
        mapping.put("config_page_reads", Types.SMALLINT);
        mapping.put("config_page_size", Types.SMALLINT);
        mapping.put("config_loop_skips", Types.SMALLINT);
        mapping.put("executed_events", Types.SMALLINT);
        mapping.put("executed_time", Types.BIGINT);
        return mapping;
    }
}