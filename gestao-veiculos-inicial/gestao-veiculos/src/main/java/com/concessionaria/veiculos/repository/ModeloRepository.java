package com.concessionaria.veiculos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.concessionaria.veiculos.model.Modelo;

public interface ModeloRepository extends JpaRepository<Modelo, Long> {
    List<Modelo> findByMarcaId(Long marcaId);
}
