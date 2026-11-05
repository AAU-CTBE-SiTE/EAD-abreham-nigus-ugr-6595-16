package com.craft.step06_literalinjection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

// 1. Literal Dependency Injection: simple values (strings, numbers) injected through setters.
public class App {
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("step06_literalinjection/beansConfig.xml");

        Student student = context.getBean("student", Student.class);

        System.out.println(student);
        System.out.println(student.getName());
    }
}
