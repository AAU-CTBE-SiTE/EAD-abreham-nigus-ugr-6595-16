package com.craft.step10_singletonscope;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

// Singleton Scope (default): only one Student instance is created and shared.
public class App {

    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("step10_singletonscope/beansConfig.xml");

        Student student1 = context.getBean("student", Student.class);
        Student student2 = context.getBean("student", Student.class);

        // Check if both references point to the SAME instance
        System.out.println(student1 == student2);

        //Modify student1's name
        student1.setName("John Doe");

        // Check student2 (should reflect changes)
        System.out.println(student2.getName());
    }
}
