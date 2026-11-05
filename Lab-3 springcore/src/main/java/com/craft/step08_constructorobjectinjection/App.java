package com.craft.step08_constructorobjectinjection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

// Using Constructor: Object injection with <constructor-arg ... ref="address"/>.
public class App {

    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("step08_constructorobjectinjection/beansConfig.xml");
        Student student = context.getBean("student", Student.class);

        System.out.println(student);
    }
}
