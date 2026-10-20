package com.concessionaria.veiculos.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.concessionaria.veiculos.model.StatusVeiculo;
import com.concessionaria.veiculos.model.Veiculo;
import com.concessionaria.veiculos.service.VeiculoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/veiculos")
public class VeiculoController {

    private final VeiculoService service;

    public VeiculoController(VeiculoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Veiculo> listar(
            @RequestParam(required = false) Long marcaId,
            @RequestParam(required = false) Long modeloId,
            @RequestParam(required = false) BigDecimal precoMin,
            @RequestParam(required = false) BigDecimal precoMax,
            @RequestParam(required = false) Integer ano,
            @RequestParam(required = false) StatusVeiculo status,
            @RequestParam(required = false) String tipo) {
        return service.listar(marcaId, modeloId, precoMin, precoMax, ano, status, tipo);
    }

    @GetMapping("/{id}")
    public Veiculo buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Veiculo criar(@Valid @RequestBody Veiculo veiculo) {
        return service.criar(veiculo);
    }

    @PutMapping("/{id}")
    public Veiculo atualizar(@PathVariable Long id, @Valid @RequestBody Veiculo veiculo) {
        return service.atualizar(id, veiculo);
    }

    @PatchMapping("/{id}/status")
    public Veiculo atualizarStatus(@PathVariable Long id, @RequestParam StatusVeiculo status) {
        return service.atualizarStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        service.remover(id);
    }
}
