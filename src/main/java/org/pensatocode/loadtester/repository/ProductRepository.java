package org.pensatocode.loadtester.repository;

import org.pensatocode.loadtester.domain.Product;

import org.pensatocode.simplicity.jdbc.JdbcRepository;

import java.util.List;

public interface ProductRepository extends JdbcRepository<Product, Long> {
    List<Product> findAllByDescriptionPattern(String searchPattern);
    Integer deleteWithOffset(Integer offset);
}