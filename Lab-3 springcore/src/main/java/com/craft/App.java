package com.craft;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        tightCouplingDemo();
        firstBeanDemo();
        setterInjectionDemo();
        constructorInjectionDemo();
        objectInjectionDemo();
        collectionInjectionDemo();

        // One context for both scope demos: the singleton is created once when it starts
        title("Bean scopes (scopeConfig.xml)");
        ApplicationContext scopeContext = new ClassPathXmlApplicationContext("scopeConfig.xml");
        singletonScopeDemo(scopeContext);
        prototypeScopeDemo(scopeContext);
    }

    // Problem: Tight Coupling. App creates Student itself with "new".
    private static void tightCouplingDemo() {
        title("Tight coupling");
        Student student = new Student();

        student.getHello();
    }

    // Let Spring manage object creation, then set the values by hand
    private static void firstBeanDemo() {
        title("Let Spring create the bean (basicConfig.xml)");
        ApplicationContext context = new ClassPathXmlApplicationContext("basicConfig.xml");
        Student student = context.getBean("student", Student.class);

        student.getHello();

        student.setId(101);
        student.setName("John");
        student.setAge(34);

        student.getDetails();
    }

    // Setter-based injection and literal dependency injection: Spring calls setId, setName and setAge
    private static void setterInjectionDemo() {
        title("Setter injection (beansConfig.xml)");
        ApplicationContext context = new ClassPathXmlApplicationContext("beansConfig.xml");
        Student student = context.getBean("literalStudent", Student.class);

        student.getDetails();

        System.out.println("Student Name: " + student.getName());
        System.out.println(student);
    }

    // Constructor-based injection: arguments matched by name, by order and by index
    private static void constructorInjectionDemo() {
        title("Constructor injection (constructorConfig.xml)");
        ApplicationContext context = new ClassPathXmlApplicationContext("constructorConfig.xml");

        for (String id : new String[]{"studentByName", "studentByOrder", "studentByIndex"}) {
            Student student = context.getBean(id, Student.class);
            System.out.println(id + ": " + student);
        }
    }

    // Object dependency injection: ref="address" with a setter and with a constructor
    private static void objectInjectionDemo() {
        title("Object injection");
        ApplicationContext setterContext = new ClassPathXmlApplicationContext("beansConfig.xml");
        Student student = setterContext.getBean("objectStudent", Student.class);
        System.out.println("setter:      " + student);

        ApplicationContext constructorContext = new ClassPathXmlApplicationContext("constructorConfig.xml");
        Student student2 = constructorContext.getBean("studentWithAddress", Student.class);
        System.out.println("constructor: " + student2);
    }

    // Collections dependency injection: the subjects come from <list>, <array> or <set>
    private static void collectionInjectionDemo() {
        title("Collection injection (beansConfig.xml)");
        ApplicationContext context = new ClassPathXmlApplicationContext("beansConfig.xml");

        for (String id : new String[]{"student", "arrayStudent", "setStudent"}) {
            System.out.println("-- " + id);
            Student student = context.getBean(id, Student.class);

            System.out.println(student);

            student.getDetails();
        }
    }

    // Singleton scope (default): both getBean() calls return the same instance
    private static void singletonScopeDemo(ApplicationContext context) {
        System.out.println("-- singleton");
        Student student1 = context.getBean("singletonStudent", Student.class);
        Student student2 = context.getBean("singletonStudent", Student.class);

        // Check if both references point to the SAME instance
        System.out.println(student1 == student2);

        //Modify student1's name
        student1.setName("John Doe");

        // Check student2 (should reflect changes)
        System.out.println(student2.getName());
    }

    // Prototype scope: every getBean() call creates a new instance
    private static void prototypeScopeDemo(ApplicationContext context) {
        System.out.println("-- prototype");
        Student student1 = context.getBean("prototypeStudent", Student.class);
        Student student2 = context.getBean("prototypeStudent", Student.class);

        System.out.println(student1 == student2);

        //Modify student1's name
        student1.setName("John Doe");

        // Check student2 (it is a different object, so it does NOT change)
        System.out.println(student2.getName());
    }

    private static void title(String text) {
        System.out.println();
        System.out.println("=== " + text + " ===");
    }
}
