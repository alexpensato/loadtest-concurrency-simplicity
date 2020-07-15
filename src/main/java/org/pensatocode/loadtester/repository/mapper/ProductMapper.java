package org.pensatocode.loadtester.repository.mapper;

import org.pensatocode.loadtester.domain.Product;

import org.pensatocode.simplicity.jdbc.mapper.TransactionalRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.Map;

public class ProductMapper extends TransactionalRowMapper<Product> {

    @Override
    public Product mapRow(ResultSet rs, int rowNum) {
        try {
            return new Product(
                    rs.getLong("id"),
                    rs.getString("description"),
                    rs.getString("search_pattern"),
                    rs.getDouble("price"),
                    rs.getInt("category_rank"),
                    rs.getLong("category_id"),
                    rs.getLong("company_id"),
                    rs.getBoolean("original")
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<String, Object> mapColumns(Product entity) {
        Map<String, Object> mapping = new LinkedHashMap<>();
        mapping.put("id", entity.getId());
        mapping.put("description", entity.getDescription());
        mapping.put("search_pattern", entity.getSearchPattern());
        mapping.put("price", entity.getPrice());
        mapping.put("category_rank", entity.getCategoryRank());
        mapping.put("category_id", entity.getCategoryId());
        mapping.put("company_id", entity.getCompanyId());
        mapping.put("original", entity.getOriginal());
        return mapping;
    }

    @Override
    public Map<String, Integer> mapTypes() {
        final Map<String, Integer> mapping = new LinkedHashMap<>();
        mapping.put("id", Types.BIGINT);
        mapping.put("description", Types.VARCHAR);
        mapping.put("search_pattern", Types.VARCHAR);
        mapping.put("price", Types.DOUBLE);
        mapping.put("category_rank", Types.INTEGER);
        mapping.put("category_id", Types.BIGINT);
        mapping.put("company_id", Types.BIGINT);
        mapping.put("original", Types.BOOLEAN);
        return mapping;
    }
}