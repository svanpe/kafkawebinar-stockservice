package be.tobania.demo.kafka.stockservice.repository;

import be.tobania.demo.kafka.stockservice.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, Long> {


    Optional<ProductEntity> findProductEntityByName(String productName);

}
