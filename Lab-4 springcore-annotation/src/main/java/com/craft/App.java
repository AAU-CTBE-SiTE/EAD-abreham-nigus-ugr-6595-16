package com.craft;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// @Configuration + @ComponentScan: Spring scans com.craft and creates a bean
// for every class marked @Component (Student and Address)
@Configuration
@ComponentScan("com.craft")
public class App {

    // @Component makes only one bean per class, so the two Subject beans are defined here
    @Bean
    public Subject math() {
        Subject subject = new Subject();
        subject.setSubjectName("Maths 101");
        return subject;
    }

    @Bean("Java")
    public Subject java() {
        Subject subject = new Subject();
        subject.setSubjectName("Java EE");
        return subject;
    }

    public static void main(String[] args) {
        tightCouplingDemo();

        // Start the IoC container from the annotations instead of an XML file
        title("Spring creates the beans");
        ApplicationContext context = new AnnotationConfigApplicationContext(App.class);

        injectionDemo(context);
        singletonScopeDemo(context);
        prototypeScopeDemo(context);
    }

    // Problem: Tight Coupling. App creates Student itself with "new".
    private static void tightCouplingDemo() {
        title("Tight coupling");
        Student student = new Student();

        student.getHello();
    }

    // Constructor injection (literals + Address object) and setter injection (List of subjects)
    private static void injectionDemo(ApplicationContext context) {
        title("Dependency injection");
        Student student = context.getBean("student", Student.class);

        System.out.println(student);
        student.getDetails();
    }

    // Singleton scope: Student is @Scope("singleton"), so both getBean() calls return the same instance
    private static void singletonScopeDemo(ApplicationContext context) {
        title("Singleton scope (Student)");
        Student student1 = context.getBean("student", Student.class);
        Student student2 = context.getBean("student", Student.class);

        // Check if both references point to the SAME instance
        System.out.println(student1 == student2);

        //Modify student1's name
        student1.setName("Abebe");

        // Check student2 (should reflect changes)
        System.out.println(student2.getName());
    }

    // Prototype scope: Address is @Scope("prototype"), so every getBean() call creates a new instance
    private static void prototypeScopeDemo(ApplicationContext context) {
        title("Prototype scope (Address)");
        Address address1 = context.getBean("address", Address.class);
        Address address2 = context.getBean("address", Address.class);

        System.out.println(address1 == address2);

        //Modify address1's city
        address1.setCity("Bahir Dar");

        // Check address2 (it is a different object, so it does NOT change)
        System.out.println(address2.getCity());
    }

    private static void title(String text) {
        System.out.println();
        System.out.println("=== " + text + " ===");
    }
}
