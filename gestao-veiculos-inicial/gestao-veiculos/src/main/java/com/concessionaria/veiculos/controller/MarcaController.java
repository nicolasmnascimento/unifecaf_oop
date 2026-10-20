package com.concessionaria.veiculos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.concessionaria.veiculos.model.Marca;
import com.concessionaria.veiculos.service.MarcaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/marcas")
public class MarcaController {

    private final MarcaService service;

    public MarcaController(MarcaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Marca> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Marca buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Marca criar(@Valid @RequestBody Marca marca) {
        return service.criar(marca);
    }

    @PutMapping("/{id}")
    public Marca atualizar(@PathVariable Long id, @Valid @RequestBody Marca marca) {
        return service.atualizar(id, marca);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        service.remover(id);
    }
}
