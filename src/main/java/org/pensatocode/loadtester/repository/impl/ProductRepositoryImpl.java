package org.pensatocode.loadtester.repository.impl;

import org.pensatocode.loadtester.domain.Product;
import org.pensatocode.loadtester.repository.mapper.ProductMapper;
import org.pensatocode.loadtester.repository.ProductRepository;

import org.pensatocode.simplicity.jdbc.AbstractJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
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
    public List<Product> findByDescriptionLike(String description, Pageable pageable) {
        String queryString = sqlGenerator.selectAll(tableDesc, "description like ?", pageable);
        return jdbcTemplate.query(
                queryString,
                new String[]{"%" + description + "%"},
                new int[]{Types.VARCHAR},
                rowMapper);
    }

    @Override
    public Product findByOffset(Integer offset) {
        List<Product> list = jdbcTemplate.query(
                "SELECT * FROM product ORDER BY id LIMIT 1 OFFSET ?",
                new Object[]{offset},
                new int[]{Types.INTEGER},
                rowMapper);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public Integer deleteProducts(@Param("id") Long id) {
        Integer lineCount = jdbcTemplate.update(
                "DELETE FROM product WHERE id >= ?",
                new Object[]{id},
                new int[]{Types.BIGINT}
        );
        decreaseCounter(lineCount);
        return lineCount;
    }
}