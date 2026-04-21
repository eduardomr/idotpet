package com.idotpet.entity;

import java.time.LocalDateTime;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

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
}
