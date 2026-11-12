package com.craft;

import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

// @EnableAspectJAutoProxy: turns on annotation-based AOP. Spring finds every @Aspect bean
// and wraps the matching beans (here StudentService) in a PROXY that runs the advice.
@Configuration
@ComponentScan("com.craft")
@EnableAspectJAutoProxy
public class App {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(App.class);
        StudentService service = context.getBean(StudentService.class);

        proxyDemo(service);
        adviceDemo(service);
        changeArgumentsDemo(service);
        blockCallDemo(service);
        afterThrowingDemo(service);
        cachingDemo(service);
        customAnnotationDemo(service);
        introductionDemo(service);
        selfInvocationDemo(service);
        xmlAopDemo();
    }

    // Weaving and proxies: Spring AOP weaves aspects at runtime by putting a proxy around the target
    private static void proxyDemo(StudentService service) {
        title("1. Proxy (runtime weaving)");
        System.out.println("Class of the bean: " + service.getClass().getName());
        System.out.println("Is it an AOP proxy?   " + AopUtils.isAopProxy(service));
        System.out.println("Is it a CGLIB proxy?  " + AopUtils.isCglibProxy(service));
        System.out.println("Real target class:    " + AopUtils.getTargetClass(service).getName());
    }

    // @Before, @AfterReturning and @After on a normal call, and the order of the aspects
    private static void adviceDemo(StudentService service) {
        title("2. @Before, @AfterReturning, @After and aspect order");
        service.addStudent(new Student(1, "Abebe", 20));
    }

    // @Around changes the argument before the real method gets it
    private static void changeArgumentsDemo(StudentService service) {
        title("3. @Around changes the arguments");
        service.addStudent(new Student(2, "  sARA  ", 22));
    }

    // @Before throws an exception, so the real method never runs
    private static void blockCallDemo(StudentService service) {
        title("4. @Before blocks an invalid call");
        try {
            service.addStudent(new Student(3, "   ", 19));
        } catch (IllegalArgumentException e) {
            System.out.println("App caught: " + e.getMessage());
        }
    }

    // @AfterThrowing reads the exception, @After still runs (like finally)
    private static void afterThrowingDemo(StudentService service) {
        title("5. @AfterThrowing");
        try {
            service.deleteStudent(99);
        } catch (IllegalArgumentException e) {
            System.out.println("App caught: " + e.getMessage());
        }
    }

    // @Around skips the real method when the value is cached
    private static void cachingDemo(StudentService service) {
        title("6. @Around skips the real method (cache)");
        System.out.println("-- first call");
        service.getStudent(1);
        System.out.println("-- second call");
        service.getStudent(1);
    }

    // A custom annotation as a pointcut: @TrackTime
    private static void customAnnotationDemo(StudentService service) {
        title("7. Custom annotation pointcut (@TrackTime)");
        List<Student> all = service.getAllStudents();
        System.out.println("App got " + all.size() + " students");
    }

    // Introduction: StudentService now also implements Auditable
    private static void introductionDemo(StudentService service) {
        title("8. Introduction (@DeclareParents)");
        System.out.println("service instanceof Auditable: " + (service instanceof Auditable));
        Auditable auditable = (Auditable) service;
        System.out.println(auditable.getAuditInfo());
    }

    // Self-invocation: calls inside the same object skip the proxy, so no advice runs for them
    private static void selfInvocationDemo(StudentService service) {
        title("9. Self-invocation (advice is NOT applied to inner calls)");
        service.registerAll(List.of(new Student(4, "Kebede", 21), new Student(5, "Hana", 23)));
    }

    // The same AOP ideas configured with XML (<aop:config>) instead of annotations
    private static void xmlAopDemo() {
        title("10. XML-based AOP (aopConfig.xml)");
        ApplicationContext xmlContext = new ClassPathXmlApplicationContext("aopConfig.xml");
        StudentService xmlService = xmlContext.getBean("studentService", StudentService.class);

        xmlService.addStudent(new Student(6, "Liya", 24));
        xmlService.getStudent(6);
    }

    private static void title(String text) {
        System.out.println();
        System.out.println("=== " + text + " ===");
    }
}
