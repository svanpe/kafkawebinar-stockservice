package be.tobania.demo.kafka.stockservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;


@Validated
@Getter
@Setter
@NoArgsConstructor
public class OrderItem {

    private Long id;

    private Product product;

    private Integer quantity;

}
