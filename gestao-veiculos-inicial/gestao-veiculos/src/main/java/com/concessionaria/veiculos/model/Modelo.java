package com.concessionaria.veiculos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "modelos",
       uniqueConstraints = @UniqueConstraint(name = "uk_modelo_marca", columnNames = {"nome", "marca_id"}))
public class Modelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do modelo é obrigatório.")
    @Size(max = 100, message = "O nome do modelo deve ter no máximo 100 caracteres.")
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "O tipo do veículo é obrigatório.")
    @Size(max = 40, message = "O tipo deve ter no máximo 40 caracteres.")
    @Column(nullable = false, length = 40)
    private String tipo;

    @NotNull(message = "A marca é obrigatória.")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "marca_id", nullable = false)
    private Marca marca;

    public Modelo() {
    }

    public Modelo(String nome, String tipo, Marca marca) {
        this.nome = nome;
        this.tipo = tipo;
        this.marca = marca;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }
}
