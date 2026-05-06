package com.idotpet.mapper;

import jakarta.enterprise.context.ApplicationScoped;

import com.idotpet.dto.AnimalRequest;
import com.idotpet.dto.AnimalResponse;
import com.idotpet.entity.Animal;

@ApplicationScoped
public class AnimalMapper {
    

    public Animal toEntity(AnimalRequest dto) {
        Animal entity = new Animal();
        entity.nome = dto.nome;
        entity.descricao = dto.descricao;
        entity.idade = dto.idade;
        entity.porte = dto.porte;
        entity.cidade = dto.cidade;
        entity.estado = dto.estado;
        entity.imagemUrl = dto.imagemUrl;
        return entity;
    }


    public AnimalResponse toResponse(Animal entity) {
        AnimalResponse response = new AnimalResponse();
        response.id = entity.id;
        response.nome = entity.nome;
        response.descricao = entity.descricao;
        response.imagemUrl = entity.imagemUrl;
        response.cidade = entity.cidade;
        response.estado = entity.estado;
        return response;
    }
}
