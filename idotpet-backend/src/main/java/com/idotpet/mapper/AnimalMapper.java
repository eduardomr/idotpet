package com.idotpet.mapper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import com.idotpet.dto.AnimalRequest;
import com.idotpet.dto.AnimalResponse;
import com.idotpet.entity.Animal;
import com.idotpet.entity.AnimalImagem;

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

        List<String> imagemUrls = imagemUrlsFrom(dto);
        entity.imagemUrl = imagemUrls.isEmpty() ? null : imagemUrls.get(0);

        for (int index = 0; index < imagemUrls.size(); index++) {
            entity.imagens.add(new AnimalImagem(imagemUrls.get(index), index, entity));
        }

        return entity;
    }


    public AnimalResponse toResponse(Animal entity) {
        AnimalResponse response = new AnimalResponse();
        response.id = entity.id;
        response.nome = entity.nome;
        response.descricao = entity.descricao;
        response.imagemUrls = entity.imagens.stream()
                .sorted(Comparator.comparing(imagem -> imagem.ordem))
                .map(imagem -> imagem.url)
                .toList();
        response.imagemUrl = response.imagemUrls.isEmpty() ? entity.imagemUrl : response.imagemUrls.get(0);
        response.cidade = entity.cidade;
        response.estado = entity.estado;
        return response;
    }

    private List<String> imagemUrlsFrom(AnimalRequest dto) {
        if (dto.imagemUrls != null && !dto.imagemUrls.isEmpty()) {
            return cleanImageUrls(dto.imagemUrls);
        }

        if (dto.imagemUrl == null || dto.imagemUrl.isBlank()) {
            return List.of();
        }

        return List.of(dto.imagemUrl.trim());
    }

    private List<String> cleanImageUrls(List<String> imagemUrls) {
        List<String> cleanUrls = new ArrayList<>();

        for (String imagemUrl : imagemUrls) {
            if (imagemUrl != null && !imagemUrl.isBlank()) {
                cleanUrls.add(imagemUrl.trim());
            }
        }

        return cleanUrls;
    }
}
