package com.craft.step03_manualsetters;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

// Managing beans via Spring's IoC container: Spring creates the bean,
// but the values are still set by hand in App.
public class App {
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("step03_manualsetters/beansConfig.xml");
        Student student = context.getBean("student", Student.class);

        student.setId(101);
        student.setName("John");
        student.setAge(34);

        student.getDetails();
    }
}
