package com.concessionaria.veiculos.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.concessionaria.veiculos.model.StatusVeiculo;
import com.concessionaria.veiculos.model.Veiculo;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long>, JpaSpecificationExecutor<Veiculo> {

    List<Veiculo> findByModeloId(Long modeloId);

    List<Veiculo> findByModeloMarcaId(Long marcaId);

    List<Veiculo> findByStatus(StatusVeiculo status);

    List<Veiculo> findByPrecoBetween(BigDecimal precoMin, BigDecimal precoMax);

    List<Veiculo> findByAnoFabricacao(Integer anoFabricacao);
}
