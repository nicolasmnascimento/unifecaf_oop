package com.concessionaria.veiculos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.concessionaria.veiculos.model.Marca;

public interface MarcaRepository extends JpaRepository<Marca, Long> {
    boolean existsByNomeIgnoreCase(String nome);
}
