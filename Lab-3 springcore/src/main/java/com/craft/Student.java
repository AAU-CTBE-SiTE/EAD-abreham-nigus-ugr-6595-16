package com.craft;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class Student {
    private int id;
    private String name;
    private int age;

    // Literal dependency injection used city and country as plain fields,
    // object dependency injection moved them into the Address class.
//    private String city;
//    private String country;
    private Address address;

    private List<Subject> subjects;

    // Used by setter-based injection (<property>)
    public Student() {
        System.out.println("Empty Constructor...");
    }

    // Used by constructor-based injection (<constructor-arg>)
    public Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
        System.out.println("Student Parameterized Constructor Called");
    }

    // Used by constructor-based object injection (<constructor-arg ... ref="address"/>)
    public Student(int id, String name, int age, Address address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public void getHello() {
        System.out.println("Hello Springers");
    }

    public void getDetails() {
        if (subjects == null) {
            System.out.println("Student Details " + this);
            return;
        }

        System.out.println("Student Name: " + this.getName());

        int index = 1;
        for (Subject subject : subjects) {
            System.out.println("Subject " + index + ": " +
                    subject.getSubjectName());
            index++;
        }
    }
}
