package be.tobania.demo.kafka.stockservice.service;

import be.tobania.demo.kafka.stockservice.entity.ProductEntity;
import be.tobania.demo.kafka.stockservice.model.Order;
import be.tobania.demo.kafka.stockservice.model.OrderItem;
import be.tobania.demo.kafka.stockservice.model.Product;
import be.tobania.demo.kafka.stockservice.model.enums.OrderStatus;
import be.tobania.demo.kafka.stockservice.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class StockProcessor {

    private final ObjectMapper objectMapper;
    private final ProductRepository productRepository;


    //@Scheduled(fixedDelay = 5000)
    @Bean
    public KStream<String, String> startProcessing(@Qualifier("productStreamBuilder") StreamsBuilder builder) {
        log.info(" start streaming to update product stock");

        builder.stream("orders", Consumed
                .with(Serdes.String(), Serdes.String())).
                foreach((v, k) -> {

                    try {

                        Order order = objectMapper.readValue(k, Order.class);

                        if (order.getStatus() == OrderStatus.SHIPPED) {
                            ProductEntity productEntity = new ProductEntity();
                            List<OrderItem> orderItemList = order.getOrderItems();
                            orderItemList.forEach(o -> {
                                Product product = o.getProduct();
                                Optional<ProductEntity> productEntity1 = productRepository.findProductEntityByName(product.getName());

                                if (productEntity1.isEmpty()) {
                                    ProductEntity newProduct = new ProductEntity();

                                    newProduct.setName(product.getName());
                                    newProduct.setDescription(product.getDescription());

                                    ProductEntity saveProduct = productRepository.save(newProduct);

                                    log.info(String.format("*********** The current total product is : %d", productEntity.getTotalQuantity()));

                                    saveProduct.setTotalQuantity(saveProduct.getTotalQuantity() - o.getQuantity());

                                    ProductEntity savedProductEntity2 = productRepository.save(saveProduct);

                                    log.info(String.format("************* The update total product is : -> %d", savedProductEntity2.getTotalQuantity()));
                                } else {

                                    log.info(String.format("*********** The current total product is : %d", productEntity.getTotalQuantity()));

                                    productEntity1.get().setTotalQuantity(productEntity1.get().getTotalQuantity() - o.getQuantity());

                                    ProductEntity savedProductEntity2 = productRepository.save(productEntity1.get());

                                    log.info(String.format("************* The update total product is : -> %d", savedProductEntity2.getTotalQuantity()));
                                }


                            });
                        }

                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    log.info(k);
                });

        return null;
    }


}
