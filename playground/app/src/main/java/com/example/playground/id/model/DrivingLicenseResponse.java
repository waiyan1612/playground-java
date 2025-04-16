package com.example.playground.id.model;

import java.util.Date;

public class DrivingLicenseResponse extends DocumentResponse {

    private String classType;

    public DrivingLicenseResponse(String documentNumber, String documentType, String heldBy, Date issuedAt, Date expiresAt, String classType) {
        super(documentNumber, documentType, heldBy, issuedAt, expiresAt);
        this.classType = classType;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }
}
