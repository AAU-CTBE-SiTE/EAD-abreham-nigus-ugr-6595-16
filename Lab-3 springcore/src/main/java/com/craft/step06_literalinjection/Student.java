package com.craft.step06_literalinjection;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Student {
    private int id;
    private String name;
    private int age;

    private String city;
    private String country;

}
