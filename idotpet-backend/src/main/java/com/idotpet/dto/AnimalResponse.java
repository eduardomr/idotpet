package com.idotpet.dto;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "Dados retornados pela API para um animal cadastrado")
public class AnimalResponse {
    @Schema(description = "Identificador do animal", example = "1")
    public Long id;

    @Schema(description = "Nome do animal", example = "Mel")
    public String nome;

    @Schema(description = "Descricao do animal", example = "Cadela docil para adocao")
    public String descricao;

    @Schema(description = "Imagem principal do animal", example = "/uploads/foto-principal.jpg")
    public String imagemUrl;

    @Schema(description = "Galeria de imagens do animal")
    public List<String> imagemUrls;

    @Schema(description = "Cidade onde o animal esta localizado", example = "Sao Paulo")
    public String cidade;

    @Schema(description = "Estado onde o animal esta localizado", example = "SP")
    public String estado;
}
