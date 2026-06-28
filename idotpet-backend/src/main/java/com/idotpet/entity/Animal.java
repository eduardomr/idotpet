package com.idotpet.entity;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;

@Entity
public class Animal extends PanacheEntity{
    public String nome;
    public String descricao;
    public Integer idade;
    public String porte;
    public String status;
    public String cidade;
    public String estado;
    public String imagemUrl;
    public LocalDateTime criadoEm;

    @OrderBy("ordem ASC")
    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    public List<AnimalImagem> imagens = new ArrayList<>();
}
