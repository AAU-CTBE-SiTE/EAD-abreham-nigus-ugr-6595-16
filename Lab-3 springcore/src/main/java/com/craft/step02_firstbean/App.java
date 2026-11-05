package com.craft.step02_firstbean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

// Let Spring manage object creation: the IoC container reads beansConfig.xml
// and creates the Student bean, App only asks for it with getBean().
public class App {
    public static void main(String[] args) {

        ApplicationContext context =
                new ClassPathXmlApplicationContext("step02_firstbean/beansConfig.xml");

        Student student = context.getBean("student", Student.class);

        student.getHello();
    }
}
