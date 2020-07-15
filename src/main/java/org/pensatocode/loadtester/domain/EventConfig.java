package org.pensatocode.loadtester.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventConfig {
    @Id Long id;
    String strategy;
    String experimentName;
    Short configMaxEvents;
    Integer configMaxTime;
    Short configPageReads;
    Short configPageSize;
    Short configLoopSkips;
    Short executedEvents;
    Long executedTime;
}
