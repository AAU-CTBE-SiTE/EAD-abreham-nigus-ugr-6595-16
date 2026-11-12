package com.craft;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Objects;

// @Order(1): the lowest number has the highest priority, so this aspect is the OUTERMOST one.
// It runs before LoggingAspect, so an invalid student is stopped before anything else happens.
@Aspect
@Component
@Order(1)
public class ValidationAspect {

    // @Around can CHANGE THE ARGUMENTS: it cleans the name ("  sara  " -> "Sara")
    // and calls proceed() with the new arguments.
    // args(student) binds the method argument to the "student" parameter.
    @Around("execution(* com.craft.StudentService.addStudent(..)) && args(student)")
    public Object normalizeName(ProceedingJoinPoint joinPoint, Student student) throws Throwable {
        Student cleaned = new Student(student.getId(), clean(student.getName()), student.getAge());

        if (!Objects.equals(student.getName(), cleaned.getName())) {
            System.out.println("[Validation] @Around      changed name '" + student.getName()
                    + "' to '" + cleaned.getName() + "'");
        }
        return joinPoint.proceed(new Object[]{cleaned});
    }

    // @Before can BLOCK the call: throwing an exception means the method never runs
    @Before("execution(* com.craft.StudentService.addStudent(..)) && args(student)")
    public void validate(Student student) {
        if (student.getName() == null || student.getName().isBlank()) {
            System.out.println("[Validation] @Before      rejected student " + student.getId() + ": empty name");
            throw new IllegalArgumentException("Student name must not be empty");
        }
        System.out.println("[Validation] @Before      student " + student.getId() + " is valid");
    }

    private String clean(String name) {
        if (name == null || name.isBlank()) {
            return name;
        }
        String trimmed = name.trim();
        return Character.toUpperCase(trimmed.charAt(0)) + trimmed.substring(1).toLowerCase();
    }
}
