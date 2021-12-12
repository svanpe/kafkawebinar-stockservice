package be.tobania.demo.kafka.stockservice.model;

import be.tobania.demo.kafka.stockservice.model.enums.ParcelStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;


@Validated
@Getter
@Setter
@NoArgsConstructor
public class ParcelForPatch {

    private ParcelStatus status;

}
