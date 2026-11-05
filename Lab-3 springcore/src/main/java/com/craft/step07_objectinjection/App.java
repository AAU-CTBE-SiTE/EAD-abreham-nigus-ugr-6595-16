package com.craft.step07_objectinjection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

// Object Dependencies: ref="address" injects the Address bean into the Student bean.
public class App {
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("step07_objectinjection/beansConfig.xml");
        Student student = context.getBean("student", Student.class);

        System.out.println(student);

    }
}
