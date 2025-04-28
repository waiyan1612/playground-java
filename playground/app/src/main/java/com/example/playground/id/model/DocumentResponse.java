package com.example.playground.id.model;

import java.util.Date;

// This is not an abstract class because mapstruct cannot construct one.
public class DocumentResponse {

    String documentNumber;
    String documentType;
    String heldBy;
    Date issuedAt;
    Date expiresAt;

    public DocumentResponse(String documentNumber, String documentType, String heldBy, Date issuedAt, Date expiresAt) {
        this.documentNumber = documentNumber;
        this.documentType = documentType;
        this.heldBy = heldBy;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getHeldBy() {
        return heldBy;
    }

    public void setHeldBy(String heldBy) {
        this.heldBy = heldBy;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }
}

