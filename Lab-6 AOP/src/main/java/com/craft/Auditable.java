package com.craft;

// An interface that StudentService does NOT implement in its code.
// AuditAspect adds it to StudentService at runtime (an INTRODUCTION).
public interface Auditable {
    String getAuditInfo();
}
