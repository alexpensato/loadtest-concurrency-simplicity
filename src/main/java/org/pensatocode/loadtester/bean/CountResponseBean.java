package org.pensatocode.loadtester.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountResponseBean {
    private CounterBean counts;
    private CounterBean atomicCounters;
}
