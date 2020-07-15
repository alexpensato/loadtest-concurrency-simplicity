package org.pensatocode.loadtester.repository.impl;

import org.pensatocode.loadtester.domain.Product;
import org.pensatocode.loadtester.repository.mapper.ProductMapper;
import org.pensatocode.loadtester.repository.ProductRepository;

import org.pensatocode.simplicity.jdbc.AbstractJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository(value = "productRepository")
public class ProductRepositoryImpl extends AbstractJdbcRepository<Product, Long> implements ProductRepository {

    public ProductRepositoryImpl(@Autowired JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, new ProductMapper(), "product", Product.class, "id");
    }

    @Override
    public List<Product> findAllByDescriptionPattern(String searchPattern) {
        Pageable pageable = PageRequest.of(0, 20, Sort.unsorted());
        String queryString = sqlGenerator.selectAll(tableDesc, "description like ?", pageable);
        return jdbcTemplate.query(
                queryString,
                new String[]{"%" + searchPattern + "%"},
                new int[]{Types.VARCHAR},
                rowMapper);
    }

    @Override
    public Integer deleteWithOffset(Integer offset) {
//        SELECT * FROM event_config ORDER BY id LIMIT 1 OFFSET ?;
        Product product = jdbcTemplate.queryForObject(
                "SELECT * FROM product ORDER BY id LIMIT 1 OFFSET ?",
                new Object[]{offset},
                new int[]{Types.INTEGER},
                rowMapper);
        if (product == null) {
            return 0;
        }
        Integer lineCount = jdbcTemplate.update(
                "DELETE FROM product WHERE id > ?",
                new Object[]{product.getId()},
                new int[]{Types.BIGINT}
        );
        decreaseCounter(lineCount);
        return lineCount;
    }
}