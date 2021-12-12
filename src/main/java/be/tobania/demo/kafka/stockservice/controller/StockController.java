package be.tobania.demo.kafka.stockservice.controller;


import be.tobania.demo.kafka.stockservice.entity.ProductEntity;
import be.tobania.demo.kafka.stockservice.model.Product;
import be.tobania.demo.kafka.stockservice.repository.ProductRepository;
import be.tobania.demo.kafka.stockservice.service.StockProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.StreamsBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class StockController {


    private final StockProcessor stockProcessor;

    private final ProductRepository productRepository;

    @Qualifier("app1StreamBuilder")
    private final StreamsBuilder builder;

    @RequestMapping(produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    public Product addProduct(@RequestBody Product product) {

        ProductEntity productEntity = new ProductEntity();

        productEntity.setName(product.getName());
        productEntity.setDescription(product.getDescription());

        ProductEntity saveProduct = productRepository.save(productEntity);

        log.info(String.format("******* save a new product with total quantity %d", saveProduct.getTotalQuantity()));

        Product productResponse = new Product();

        productResponse.setId(saveProduct.getId());
        productResponse.setName(saveProduct.getName());
        productResponse.setDescription(saveProduct.getDescription());


        return productResponse;


    }


    @GetMapping("/{name}")
    public Product getProduct(@PathVariable String productName) {


        ProductEntity saveProduct = productRepository.findProductEntityByName(productName).orElseThrow(()-> new RuntimeException("No product"));

        Product productResponse = new Product();

        productResponse.setId(saveProduct.getId());
        productResponse.setName(saveProduct.getName());
        productResponse.setDescription(saveProduct.getDescription());

        return productResponse;


    }


}
