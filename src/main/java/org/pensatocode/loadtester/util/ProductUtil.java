package org.pensatocode.loadtester.util;

import lombok.extern.slf4j.Slf4j;
import org.pensatocode.loadtester.domain.Product;

import java.util.SplittableRandom;

@Slf4j
public final class ProductUtil {

    private static final SplittableRandom splittableRandom = new SplittableRandom();

    private ProductUtil() {
        // Util
    }

    public static Product createRandomProduct() {
        try {
            String desc = WordGenerator.newWord(splittableRandom.nextInt(8,30));
            String search = WordGenerator.newWord(4);
            Double price = ((double) splittableRandom.nextInt(1000, 10000)) / 100.0;
            Integer categoryRank = splittableRandom.nextInt(1, 1000);
            Long categoryId = splittableRandom.nextLong(1, 6000);
            Long companyId = splittableRandom.nextLong(1, 680);
            return new Product(0L, desc, search, price, categoryRank, categoryId, companyId, false);
        } catch (IllegalArgumentException e) {
            log.warn("Unable to create random Product: " + e.getMessage());
            return new Product(0L, "-", "ay", 10.0, 100, 1L, 1L, false);
        }
    }
}
