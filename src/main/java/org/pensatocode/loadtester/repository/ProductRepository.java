package org.pensatocode.loadtester.repository;

import org.pensatocode.loadtester.domain.Product;

import org.pensatocode.simplicity.jdbc.JdbcRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JdbcRepository<Product, Long> {
    List<Product> findByDescriptionLike(String description, Pageable pageable);
    Product findByOffset(Integer offset);
    Integer deleteProducts(@Param("id") Long id);
}