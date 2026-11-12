package com.craft;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

// @Order(4): the innermost aspect, closest to the real method
@Aspect
@Component
@Order(4)
public class CachingAspect {

    private final Map<Integer, Object> cache = new HashMap<>();

    // @Around can SKIP the real method: on a cache hit it returns the saved value
    // and never calls proceed(), so StudentService.getStudent() does not run at all
    @Around("execution(* com.craft.StudentService.getStudent(int)) && args(id)")
    public Object cacheStudent(ProceedingJoinPoint joinPoint, int id) throws Throwable {
        if (cache.containsKey(id)) {
            System.out.println("[Caching] @Around         cache HIT for id " + id + ", the real method is skipped");
            return cache.get(id);
        }

        System.out.println("[Caching] @Around         cache MISS for id " + id + ", calling the real method");
        Object result = joinPoint.proceed();
        cache.put(id, result);
        return result;
    }
}
