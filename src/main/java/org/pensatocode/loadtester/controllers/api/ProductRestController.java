package org.pensatocode.loadtester.controllers.api;

import org.pensatocode.loadtester.domain.Product;
import org.pensatocode.loadtester.repository.ProductRepository;

import org.pensatocode.simplicity.web.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductRestController extends AbstractController<Product, Long> {

    public ProductRestController(@Autowired ProductRepository productRepository) {
        super(productRepository);
    }
}