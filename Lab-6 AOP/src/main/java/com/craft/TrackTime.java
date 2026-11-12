package com.craft;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// A custom annotation used as a pointcut: @annotation(trackTime) in PerformanceAspect
// matches every method marked with @TrackTime
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TrackTime {
    String value() default "";
}
