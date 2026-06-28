package com.idotpet.dto;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para cadastro de um animal disponivel para adocao")
public class AnimalRequest {
    @NotBlank
    @Schema(description = "Nome do animal", example = "Mel")
    public String nome;

    @NotBlank
    @Schema(description = "Descricao do animal e informacoes relevantes para adocao", example = "Cadela docil para adocao")
    public String descricao;

    @Schema(description = "Idade aproximada do animal em anos", example = "2")
    public Integer idade;

    @Schema(description = "Porte do animal", example = "MEDIO")
    public String porte;

    @Schema(description = "Cidade onde o animal esta localizado", example = "Sao Paulo")
    public String cidade;

    @Schema(description = "Estado onde o animal esta localizado", example = "SP")
    public String estado;

    @Schema(description = "Imagem principal do animal. Mantido por compatibilidade; prefira imagemUrls.", example = "/uploads/foto-principal.jpg")
    public String imagemUrl;

    @Size(max = 5, message = "Um animal pode ter no máximo 5 imagens")
    @Schema(description = "Lista de URLs das imagens do animal. Limite maximo de 5 imagens.")
    public List<String> imagemUrls;
}
