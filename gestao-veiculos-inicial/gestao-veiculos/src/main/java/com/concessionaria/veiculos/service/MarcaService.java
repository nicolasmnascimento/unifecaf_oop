package com.concessionaria.veiculos.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concessionaria.veiculos.model.Marca;
import com.concessionaria.veiculos.repository.MarcaRepository;
import com.concessionaria.veiculos.exception.RecursoNaoEncontradoException;

@Service
public class MarcaService {

    private final MarcaRepository repository;

    public MarcaService(MarcaRepository repository) {
        this.repository = repository;
    }

    public List<Marca> listar() {
        return repository.findAll();
    }

    public Marca buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Marca não encontrada: " + id));
    }

    @Transactional
    public Marca criar(Marca marca) {
        if (repository.existsByNomeIgnoreCase(marca.getNome())) {
            throw new IllegalArgumentException("Já existe uma marca com esse nome.");
        }
        return repository.save(marca);
    }

    @Transactional
    public Marca atualizar(Long id, Marca dados) {
        Marca marca = buscarPorId(id);
        marca.setNome(dados.getNome());
        marca.setPaisOrigem(dados.getPaisOrigem());
        return repository.save(marca);
    }

    @Transactional
    public void remover(Long id) {
        repository.delete(buscarPorId(id));
    }
}
