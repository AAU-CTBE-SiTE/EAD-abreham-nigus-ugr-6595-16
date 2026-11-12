package com.craft;

// The implementation the introduced Auditable methods are delegated to
public class AuditableImpl implements Auditable {

    @Override
    public String getAuditInfo() {
        return "Audit: this object became Auditable through AuditAspect (@DeclareParents)";
    }
}
