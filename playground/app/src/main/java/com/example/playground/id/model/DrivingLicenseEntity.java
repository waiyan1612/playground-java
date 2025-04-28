package com.example.playground.id.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(schema = "id", name = "driving_license")
@DiscriminatorValue("DRIVING_LICENSE")
public class DrivingLicenseEntity extends DocumentEntity {

    private String classType;

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DrivingLicenseEntity that)) return false;
        return Objects.equals(id, that.id);
    }
}
