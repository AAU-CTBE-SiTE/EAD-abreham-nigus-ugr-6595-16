package com.craft;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(3)
public class PerformanceAspect {

    // @Around wraps the whole method call: code before proceed() runs before the method,
    // code after proceed() runs after it. Here it measures how long the method takes.
    //
    // @annotation(trackTime): matches methods marked with @TrackTime and binds the annotation,
    //                         so we can read its value
    // bean(studentService):   only the bean named "studentService"
    // &&:                     both conditions must be true
    @Around("@annotation(trackTime) && bean(studentService)")
    public Object measureTime(ProceedingJoinPoint joinPoint, TrackTime trackTime) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            return joinPoint.proceed(); // runs the real method
        } finally {
            long time = System.currentTimeMillis() - start;
            System.out.println("[Performance] @Around     '" + trackTime.value() + "' ("
                    + joinPoint.getSignature().getName() + "()) took " + time + " ms");
        }
    }
}
