package com.examples.SpringBatchSample.model.entity;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@SuperBuilder
@MappedSuperclass
public abstract class BaseEntity<T extends Number>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected T id;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public BaseEntity() {
    }
}
