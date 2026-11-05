package com.craft.step04_setterinjection;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Student {
    private int id;
    private String name;
    private int age;

    public void getDetails() {
        System.out.println("Student Details " + this);
    }
}
