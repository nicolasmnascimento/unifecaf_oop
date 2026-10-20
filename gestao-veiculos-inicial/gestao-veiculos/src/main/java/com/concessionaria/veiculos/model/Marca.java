package com.concessionaria.veiculos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "marcas")
public class Marca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da marca é obrigatório.")
    @Size(max = 80, message = "O nome da marca deve ter no máximo 80 caracteres.")
    @Column(nullable = false, unique = true, length = 80)
    private String nome;

    @Size(max = 60, message = "O país deve ter no máximo 60 caracteres.")
    @Column(length = 60)
    private String paisOrigem;

    public Marca() {
    }

    public Marca(String nome, String paisOrigem) {
        this.nome = nome;
        this.paisOrigem = paisOrigem;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPaisOrigem() {
        return paisOrigem;
    }

    public void setPaisOrigem(String paisOrigem) {
        this.paisOrigem = paisOrigem;
    }
}
