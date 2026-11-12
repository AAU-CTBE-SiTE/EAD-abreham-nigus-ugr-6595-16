package com.craft;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

// ASPECT: a class that holds a cross-cutting concern (here: logging).
// @Aspect marks it as an aspect, @Component makes it a Spring bean so Spring can use it.
// @Order(2): runs inside ValidationAspect (@Order(1)) and outside PerformanceAspect and CachingAspect.
@Aspect
@Component
@Order(2)
public class LoggingAspect {

    // POINTCUTS: named, reusable expressions that select JOIN POINTS (method executions)

    // execution(): every method of StudentService, any return type, any arguments
    @Pointcut("execution(* com.craft.StudentService.*(..))")
    public void serviceMethods() {}

    // execution() with a name pattern: every StudentService method whose name starts with "get"
    @Pointcut("execution(* com.craft.StudentService.get*(..))")
    public void getterMethods() {}

    // execution() for one method
    @Pointcut("execution(* com.craft.StudentService.addStudent(..))")
    public void addMethod() {}

    // within(): every join point inside the StudentService class
    @Pointcut("within(com.craft.StudentService)")
    public void inStudentService() {}

    // @Before: runs BEFORE the method. JoinPoint gives the method name and the arguments.
    @Before("serviceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("[Logging] @Before         " + joinPoint.getSignature().getName()
                + "() args=" + Arrays.toString(joinPoint.getArgs()));
    }

    // @AfterReturning: runs only when the method finishes normally, and can read the return value.
    // "||" combines two pointcuts: getters OR addStudent.
    @AfterReturning(pointcut = "getterMethods() || addMethod()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("[Logging] @AfterReturning " + joinPoint.getSignature().getName()
                + "() returned " + result);
    }

    // @AfterThrowing: runs only when the method throws an exception, and can read the exception
    @AfterThrowing(pointcut = "inStudentService()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
        System.out.println("[Logging] @AfterThrowing  " + joinPoint.getSignature().getName()
                + "() threw " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
    }

    // @After: runs after the method no matter what (like a finally block), on success or exception
    @After("serviceMethods()")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("[Logging] @After          " + joinPoint.getSignature().getName() + "() finished");
    }
}
