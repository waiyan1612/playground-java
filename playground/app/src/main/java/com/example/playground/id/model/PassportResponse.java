package com.example.playground.id.model;

import java.util.Date;

public class PassportResponse extends DocumentResponse {

    private String issuedBy;

    public PassportResponse(String documentNumber, String documentType, String heldBy, Date issuedAt, Date expiresAt, String issuedBy) {
        super(documentNumber, documentType, heldBy, issuedAt, expiresAt);
        this.issuedBy = issuedBy;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }
}
