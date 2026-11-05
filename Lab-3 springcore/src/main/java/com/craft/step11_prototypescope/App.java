package com.craft.step11_prototypescope;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

// Prototype Scope: a new Student instance is created each time getBean() is called.
public class App {
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("step11_prototypescope/beansConfig.xml");
        Student student1 = context.getBean("student", Student.class);
        Student student2 = context.getBean("student", Student.class);

        System.out.println(student1 == student2);

        //Modify student1's name
        student1.setName("John Doe");

        // Check student2 (it is a different object, so it does NOT change)
        System.out.println(student2.getName());
    }
}
