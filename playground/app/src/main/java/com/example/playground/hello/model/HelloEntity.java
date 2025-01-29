package com.example.playground.hello.model;

import com.example.playground.base.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(schema = "sandbox", name = "hello")
public class HelloEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String lang;

    private String message;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
