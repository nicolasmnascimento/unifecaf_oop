package com.concessionaria.veiculos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.concessionaria.veiculos.model.Modelo;
import com.concessionaria.veiculos.service.ModeloService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/modelos")
public class ModeloController {

    private final ModeloService service;

    public ModeloController(ModeloService service) {
        this.service = service;
    }

    @GetMapping
    public List<Modelo> listar(@RequestParam(required = false) Long marcaId) {
        return service.listar(marcaId);
    }

    @GetMapping("/{id}")
    public Modelo buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Modelo criar(@Valid @RequestBody Modelo modelo) {
        return service.criar(modelo);
    }

    @PutMapping("/{id}")
    public Modelo atualizar(@PathVariable Long id, @Valid @RequestBody Modelo modelo) {
        return service.atualizar(id, modelo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        service.remover(id);
    }
}
