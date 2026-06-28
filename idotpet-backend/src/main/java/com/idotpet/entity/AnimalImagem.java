package com.idotpet.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class AnimalImagem extends PanacheEntity {
    public String url;
    public Integer ordem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id", nullable = false)
    public Animal animal;

    public AnimalImagem() {
    }

    public AnimalImagem(String url, Integer ordem, Animal animal) {
        this.url = url;
        this.ordem = ordem;
        this.animal = animal;
    }
}
