package com.concessionaria.veiculos.model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "veiculos")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O modelo é obrigatório.")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "modelo_id", nullable = false)
    private Modelo modelo;

    @NotNull(message = "O ano de fabricação é obrigatório.")
    @Min(value = 1886, message = "O ano deve ser igual ou posterior a 1886.")
    @Max(value = 2100, message = "O ano informado é inválido.")
    @Column(nullable = false)
    private Integer anoFabricacao;

    @NotBlank(message = "A cor é obrigatória.")
    @Size(max = 40, message = "A cor deve ter no máximo 40 caracteres.")
    @Column(nullable = false, length = 40)
    private String cor;

    @NotNull(message = "O preço é obrigatório.")
    @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que zero.")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal preco;

    @NotNull(message = "A quilometragem é obrigatória.")
    @PositiveOrZero(message = "A quilometragem não pode ser negativa.")
    @Column(nullable = false)
    private Long quilometragem;

    @NotNull(message = "O status é obrigatório.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusVeiculo status = StatusVeiculo.DISPONIVEL;

    public Veiculo() {
    }

    public Long getId() {
        return id;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public Integer getAnoFabricacao() {
        return anoFabricacao;
    }

    public void setAnoFabricacao(Integer anoFabricacao) {
        this.anoFabricacao = anoFabricacao;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Long getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(Long quilometragem) {
        this.quilometragem = quilometragem;
    }

    public StatusVeiculo getStatus() {
        return status;
    }

    public void setStatus(StatusVeiculo status) {
        this.status = status;
    }
}
