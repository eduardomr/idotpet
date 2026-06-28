package com.idotpet.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AnimalRequest {
    @NotBlank
    public String nome;

    @NotBlank
    public String descricao;
    public Integer idade;
    
    public String porte;
    public String cidade;
    public String estado;
    public String imagemUrl;

    @Size(max = 5, message = "Um animal pode ter no máximo 5 imagens")
    public List<String> imagemUrls;
}
