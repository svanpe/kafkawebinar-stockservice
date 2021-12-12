package be.tobania.demo.kafka.stockservice.model;

import be.tobania.demo.kafka.stockservice.model.enums.ParcelStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

@NoArgsConstructor
@Data
@Validated
@ToString
public class Parcel {
    private Long id;

    private ParcelStatus status;

    private Order order;

}
