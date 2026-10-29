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

    private Address address;

    private List<Subject> subjects;

    // Used by setter-based injection (<property>)
    public Student() {
        System.out.println("Empty Constructor...");
    }

    // Used by constructor-based injection (<constructor-arg>)
    public Student(int id, String name, int age, Address address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
        System.out.println("Student Parameterized Constructor Called");
    }

    public void getDetails() {
        System.out.println("Student Name: " + this.getName());

        int index = 1;
        for (Subject subject : subjects) {
            System.out.println("Subject " + index + ": " + subject.getSubjectName());
            index++;
        }
    }
}
