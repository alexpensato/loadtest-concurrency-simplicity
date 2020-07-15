package org.pensatocode.loadtester.domain;

import javax.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id private Long id;
    private String description;
    private String searchPattern;
    private Double price;
    private Integer categoryRank;
    private Long categoryId;
    private Long companyId;
    private Boolean original;
}
