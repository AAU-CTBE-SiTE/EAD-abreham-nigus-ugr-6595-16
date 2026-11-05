package com.craft.step05_constructorinjection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

// Constructor-based Injection: Spring calls Student(int id, String name, int age).
// The three config files show the three ways to match the constructor arguments.
public class App {
    public static void main(String[] args) {
        String[] configs = {
                "step05_constructorinjection/beansConfig-name.xml",   // Constructor argument name
                "step05_constructorinjection/beansConfig-order.xml",  // Arguments in order, no attribute
                "step05_constructorinjection/beansConfig-index.xml"   // Constructor argument index
        };

        for (String config : configs) {
            System.out.println("-- " + config);
            ApplicationContext context = new ClassPathXmlApplicationContext(config);

            Student student = context.getBean("student", Student.class);

            System.out.println(student);
        }
    }
}
