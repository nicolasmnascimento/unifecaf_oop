package com.concessionaria.veiculos.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concessionaria.veiculos.exception.RecursoNaoEncontradoException;
import com.concessionaria.veiculos.model.Marca;
import com.concessionaria.veiculos.model.Modelo;
import com.concessionaria.veiculos.repository.MarcaRepository;
import com.concessionaria.veiculos.repository.ModeloRepository;

@Service
public class ModeloService {

    private final ModeloRepository modeloRepository;
    private final MarcaRepository marcaRepository;

    public ModeloService(ModeloRepository modeloRepository, MarcaRepository marcaRepository) {
        this.modeloRepository = modeloRepository;
        this.marcaRepository = marcaRepository;
    }

    public List<Modelo> listar(Long marcaId) {
        return marcaId == null ? modeloRepository.findAll() : modeloRepository.findByMarcaId(marcaId);
    }

    public Modelo buscarPorId(Long id) {
        return modeloRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Modelo não encontrado: " + id));
    }

    @Transactional
    public Modelo criar(Modelo dados) {
        Long marcaId = dados.getMarca() == null ? null : dados.getMarca().getId();
        if (marcaId == null) {
            throw new IllegalArgumentException("Informe o id da marca do modelo.");
        }
        Marca marca = marcaRepository.findById(marcaId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Marca não encontrada: " + marcaId));
        dados.setMarca(marca);
        return modeloRepository.save(dados);
    }

    @Transactional
    public Modelo atualizar(Long id, Modelo dados) {
        Modelo modelo = buscarPorId(id);
        modelo.setNome(dados.getNome());
        modelo.setTipo(dados.getTipo());

        if (dados.getMarca() != null && dados.getMarca().getId() != null) {
            Marca marca = marcaRepository.findById(dados.getMarca().getId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException(
                            "Marca não encontrada: " + dados.getMarca().getId()));
            modelo.setMarca(marca);
        }
        return modeloRepository.save(modelo);
    }

    @Transactional
    public void remover(Long id) {
        modeloRepository.delete(buscarPorId(id));
    }
}
