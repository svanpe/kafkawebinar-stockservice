package be.tobania.demo.kafka.stockservice.model;

import be.tobania.demo.kafka.stockservice.model.enums.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Data
@Validated
@ToString
public class Order {

    private Long id;

    private LocalDate creationDate;

    private Customer customer;

    private OrderStatus status;

    private List<OrderItem> orderItems;
}
