package com.idotpet.dto;

import jakarta.validation.constraints.NotBlank;

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
}