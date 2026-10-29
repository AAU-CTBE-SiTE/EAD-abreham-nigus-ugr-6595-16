package com.craft;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        setterInjectionDemo();
        constructorInjectionDemo();
        scopeDemo();
    }

    // Setter-based injection: literal values, an object (ref) and a collection (<list>)
    private static void setterInjectionDemo() {
        System.out.println("=== Setter injection (beansConfig.xml) ===");
        ApplicationContext context = new ClassPathXmlApplicationContext("beansConfig.xml");
        Student student = context.getBean("student", Student.class);

        System.out.println(student);
        student.getDetails();
    }

    // Constructor-based injection: literal values and an object (ref)
    private static void constructorInjectionDemo() {
        System.out.println();
        System.out.println("=== Constructor injection (constructorConfig.xml) ===");
        ApplicationContext context = new ClassPathXmlApplicationContext("constructorConfig.xml");
        Student student = context.getBean("student", Student.class);

        System.out.println(student);
    }

    // Bean scopes: singleton (default) vs prototype
    private static void scopeDemo() {
        System.out.println();
        System.out.println("=== Bean scopes (scopeConfig.xml) ===");
        ApplicationContext context = new ClassPathXmlApplicationContext("scopeConfig.xml");

        Student student1 = context.getBean("singletonStudent", Student.class);
        Student student2 = context.getBean("singletonStudent", Student.class);

        // Check if both references point to the SAME instance
        System.out.println("singleton: student1 == student2 -> " + (student1 == student2));

        // Modify student1's name, student2 reflects the change
        student1.setName("John Doe");
        System.out.println("singleton: student2.getName() -> " + student2.getName());

        Student student3 = context.getBean("prototypeStudent", Student.class);
        Student student4 = context.getBean("prototypeStudent", Student.class);

        System.out.println("prototype: student3 == student4 -> " + (student3 == student4));

        // Modify student3's name, student4 is a different object so it does not change
        student3.setName("John Doe");
        System.out.println("prototype: student4.getName() -> " + student4.getName());
    }
}
