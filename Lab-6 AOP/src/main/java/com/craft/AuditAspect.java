package com.craft;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.stereotype.Component;

// INTRODUCTION: an aspect can add a new interface (and its methods) to an existing class
// without changing that class. Here StudentService becomes an Auditable,
// and the Auditable methods are handled by AuditableImpl.
@Aspect
@Component
public class AuditAspect {

    @DeclareParents(value = "com.craft.StudentService", defaultImpl = AuditableImpl.class)
    public static Auditable auditable;
}
