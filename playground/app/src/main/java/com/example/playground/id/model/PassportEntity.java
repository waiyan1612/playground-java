package com.example.playground.id.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(schema = "id", name = "passport")
@DiscriminatorValue("PASSPORT")
public class PassportEntity extends DocumentEntity {

    private String issuedBy;

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PassportEntity that)) return false;
        return Objects.equals(id, that.id);
    }
}
