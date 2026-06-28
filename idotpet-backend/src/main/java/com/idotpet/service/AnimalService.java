package com.idotpet.service;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.BadRequestException;
import jakarta.transaction.Transactional;
import io.quarkus.panache.common.Page;

import com.idotpet.dto.AnimalRequest;
import com.idotpet.dto.AnimalResponse;
import com.idotpet.entity.Animal;
import com.idotpet.mapper.AnimalMapper;
import com.idotpet.repository.AnimalRepository;

@ApplicationScoped
public class AnimalService {

    private static final int MAX_IMAGENS_POR_ANIMAL = 5;

    @Inject
    AnimalRepository repository;

    @Inject
    AnimalMapper mapper;

    @Transactional
    public AnimalResponse criar(AnimalRequest dto) {
        validarLimiteDeImagens(dto);

        Animal animal = mapper.toEntity(dto);

        animal.criadoEm = LocalDateTime.now();
        animal.status = "DISPONIVEL";
        repository.persist(animal);
        return mapper.toResponse(animal);
    }

    public List<AnimalResponse> listar(int page, int size) {
        return repository.findAll()
                .page(Page.of(page, size))
                .list()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public AnimalResponse buscarPorId(Long id) {
        Animal animal = repository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Animal não encontrado"));
        return mapper.toResponse(animal);
    }

    @Transactional
    public void deletar(Long id) {
        if (!repository.deleteById(id)) {
            throw new NotFoundException("Animal não encontrado");
        }
    }

    private void validarLimiteDeImagens(AnimalRequest dto) {
        if (dto.imagemUrls != null && dto.imagemUrls.size() > MAX_IMAGENS_POR_ANIMAL) {
            throw new BadRequestException("Um animal pode ter no máximo 5 imagens");
        }
    }
}
