package com.craft.step09_collectioninjection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

// Collections Dependency Injection: the subjects are injected with <list>, <array> or <set>.
public class App {

    public static void main(String[] args) {
        String[] configs = {
                "step09_collectioninjection/beansConfig.xml",        // <list>
                "step09_collectioninjection/beansConfig-array.xml",  // <array>
                "step09_collectioninjection/beansConfig-set.xml"     // <set>
        };

        for (String config : configs) {
            System.out.println("-- " + config);
            ApplicationContext context = new ClassPathXmlApplicationContext(config);
            Student student = context.getBean("student", Student.class);

            System.out.println(student);

            student.getDetails();
        }
    }
}
