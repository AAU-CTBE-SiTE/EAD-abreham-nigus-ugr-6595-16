package com.craft.step11_prototypescope;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Student {
    private int id;
    private String name;
    private int age;

    public Student() {
        System.out.println("Empty Constructor...");
    }
}
