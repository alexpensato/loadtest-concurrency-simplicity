package org.pensatocode.loadtester.services;

import lombok.extern.slf4j.Slf4j;
import org.pensatocode.loadtester.bean.CounterBean;
import org.pensatocode.loadtester.domain.Product;
import org.pensatocode.loadtester.repository.EventConfigRepository;
import org.pensatocode.loadtester.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Slf4j
@Service
public class GeneratorService {

    private final ProductRepository productRepository;
    private final EventConfigRepository eventConfigRepository;

    public GeneratorService(@Autowired ProductRepository productRepository,
                            @Autowired EventConfigRepository eventConfigRepository) {
        this.productRepository = productRepository;
        this.eventConfigRepository = eventConfigRepository;
    }

    public CounterBean countAllRepositories() {
        Long products = productRepository.count();
        Long eventConfig = eventConfigRepository.count();
        return new CounterBean(products, eventConfig);
    }

    public CounterBean getAllAtomicCounters() {
        Long products = productRepository.getAtomicCount();
        Long eventConfig = eventConfigRepository.getAtomicCount();
        return new CounterBean(products, eventConfig);
    }

    public CounterBean generateData() {
        Map<String,Long> companies = new HashMap<>();
        Map<String,Long> categories = new HashMap<>();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("kaggle_ecommerce_redux.csv");
        if (inputStream == null) {
            log.error("CSV not found!");
            return new CounterBean();
        }
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] properties = line.split(",");
                if (properties.length >= 7) {
                    // props
                    Long id = 0L;
                    String desc = properties[3];
                    String search = properties[0];
                    Double price = extractPrice(properties[2]);
                    Integer rank = extractRank(properties[5]);
                    Long catId = extractLong(properties[4]);
                    Long compId = extractLong(properties[6]);
                    // new product
                    Product product = new Product(id, desc, search, price, rank, catId, compId, true);
                    try {
                        productRepository.save(product);
                    } catch (DataAccessException e) {
                        log.warn(String.format("Unable to save product [%s]: %s", product.toString(), e.getMessage()));
                    }
                }
            }

        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return countAllRepositories();
    }

    private Long extractLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            log.warn(String.format("CSV value `%s` is invalid: %s", value, e.getMessage()));
        }
        return 0L;
    }

    private Integer extractRank(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            log.warn(String.format("Rank value `%s` is invalid: %s", value, e.getMessage()));
        }
        return 0;
    }

    private Double extractPrice(String value) {
        try {
            String strippedValue = value.substring(1);
            int dashIdx = strippedValue.indexOf('-');
            if (dashIdx > 0) {
                strippedValue = strippedValue.substring(0, dashIdx);
            }
            return Double.parseDouble(strippedValue);

        } catch (StringIndexOutOfBoundsException | NumberFormatException e) {
            log.warn(String.format("Price `%s` is invalid: %s", value, e.getMessage()));
        }
        return 0.0;
    }
}
