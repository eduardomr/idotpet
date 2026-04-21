package com.idotpet.service;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import io.quarkus.panache.common.Page;
import com.idotpet.entity.Animal;
import com.idotpet.repository.AnimalRepository;

@ApplicationScoped
public class AnimalService {

    @Inject
    AnimalRepository repository;

    @Transactional
    public Animal criar(Animal animal) {
        animal.criadoEm = LocalDateTime.now();
        animal.status = "DISPONIVEL";
        repository.persist(animal);
        return animal;
    }

    public List<Animal> listar(int page, int size) {
        return repository.findAll()
                .page(Page.of(page, size))
                .list();
    }

    public Animal buscarPorId(Long id) {
        return repository.findByIdOptional(id)
                .orElseThrow(() -> new RuntimeException("Animal não encontrado"));
    }

    @Transactional
    public void deletar(Long id) {
        if (!repository.deleteById(id)) {
            throw new RuntimeException("Animal não encontrado");
        }
    }
}