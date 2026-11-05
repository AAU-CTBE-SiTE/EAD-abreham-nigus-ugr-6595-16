package com.craft;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

// @Component: Spring finds this class with @ComponentScan and creates the "student" bean
// @Scope("singleton"): the default, one shared Student for the whole container
@Component
@Scope("singleton")
@Data
@ToString
public class Student {
    private int id;
    private String name;
    private int age;

    // Literal dependency injection used city and country as plain fields,
    // object dependency injection moved them into the Address class.
   //   private String city;
    //    private String country;
    private Address address;

    private List<Subject> subjects;

    // Used by the tight coupling demo (new Student())
    public Student() {
        System.out.println("Empty Constructor...");
    }

    // Constructor-based injection: Spring calls this constructor because of @Autowired.
    // @Value injects the literal values, and Spring injects the Address bean (object injection).
    @Autowired
    public Student(@Value("101") int id,
                   @Value("John Doe") String name,
                   @Value("20") int age,
                   Address address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
        System.out.println("Student Parameterized Constructor Called");
    }

    // Setter-based injection: Spring calls this setter because of @Autowired.
    // It injects every Subject bean as a List (collection injection).
    @Autowired
    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
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
