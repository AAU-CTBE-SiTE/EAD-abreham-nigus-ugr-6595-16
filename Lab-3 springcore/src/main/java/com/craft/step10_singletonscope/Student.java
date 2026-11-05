package com.craft.step10_singletonscope;

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
