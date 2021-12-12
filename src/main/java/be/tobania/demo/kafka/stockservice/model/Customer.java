package be.tobania.demo.kafka.stockservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@NoArgsConstructor
@Getter
@Setter
@Validated
public class Customer {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

}
