package com.craft.step04_setterinjection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

// Setter-based Injection: Spring calls setId, setName and setAge
// with the values from the <property> tags in beansConfig.xml.
public class App {
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("step04_setterinjection/beansConfig.xml");
        Student student = context.getBean("student", Student.class);

        student.getDetails();

        // Let's getName and print the bean
        System.out.println("Student Name: " + student.getName());
        System.out.println(student);
    }
}
