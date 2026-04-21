package com.idotpet.repository;

import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import com.idotpet.entity.Animal;

@ApplicationScoped
public class AnimalRepository implements PanacheRepository<Animal> {

}
