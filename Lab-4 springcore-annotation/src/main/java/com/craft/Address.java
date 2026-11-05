package com.craft;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

// @Component: Spring finds this class with @ComponentScan and creates the "address" bean
// @Scope("prototype"): a new Address every time one is asked for
@Component
@Scope("prototype")
@Data
@ToString
public class Address {

    // Literal dependency injection with @Value
    @Value("Addis")
    private String city;

    @Value("Ethiopia")
    private String country;

    public Address() {
        System.out.println("Address Constructor Called");
    }
}
