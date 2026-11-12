package com.craft;

import org.aspectj.lang.JoinPoint;

// XML-based AOP: a plain class with no annotations.
// aopConfig.xml makes it an aspect and says which methods its advice runs on.
public class XmlLoggingAspect {

    public void logBefore(JoinPoint joinPoint) {
        System.out.println("[XML aspect] <aop:before>          " + joinPoint.getSignature().getName() + "()");
    }

    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("[XML aspect] <aop:after-returning> " + joinPoint.getSignature().getName()
                + "() returned " + result);
    }
}
