package com.concessionaria.veiculos.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.concessionaria.veiculos.exception.RecursoNaoEncontradoException;
import com.concessionaria.veiculos.model.Modelo;
import com.concessionaria.veiculos.model.StatusVeiculo;
import com.concessionaria.veiculos.model.Veiculo;
import com.concessionaria.veiculos.repository.ModeloRepository;
import com.concessionaria.veiculos.repository.VeiculoRepository;


@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final ModeloRepository modeloRepository;

    public VeiculoService(VeiculoRepository veiculoRepository, ModeloRepository modeloRepository) {
        this.veiculoRepository = veiculoRepository;
        this.modeloRepository = modeloRepository;
    }

    public List<Veiculo> listar(Long marcaId, Long modeloId, BigDecimal precoMin, BigDecimal precoMax,
                                Integer ano, StatusVeiculo status, String tipo) {
        Specification<Veiculo> spec = (root, query, cb) -> cb.conjunction();

        if (marcaId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("modelo").get("marca").get("id"), marcaId));
        }
        if (modeloId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("modelo").get("id"), modeloId));
        }
        if (precoMin != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("preco"), precoMin));
        }
        if (precoMax != null) {
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("preco"), precoMax));
        }
        if (ano != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("anoFabricacao"), ano));
        }
        if (status != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), status));
        }
        if (tipo != null && !tipo.isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("modelo").get("tipo")), "%" + tipo.toLowerCase() + "%"));
        }
        return veiculoRepository.findAll(spec);
    }

    public Veiculo buscarPorId(Long id) {
        return veiculoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Veículo não encontrado: " + id));
    }

    @Transactional
    public Veiculo criar(Veiculo dados) {
        Long modeloId = dados.getModelo() == null ? null : dados.getModelo().getId();
        if (modeloId == null) {
            throw new IllegalArgumentException("Informe o id do modelo do veículo.");
        }
        Modelo modelo = modeloRepository.findById(modeloId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Modelo não encontrado: " + modeloId));
        dados.setModelo(modelo);
        if (dados.getStatus() == null) {
            dados.setStatus(StatusVeiculo.DISPONIVEL);
        }
        return veiculoRepository.save(dados);
    }

    @Transactional
    public Veiculo atualizar(Long id, Veiculo dados) {
        Veiculo veiculo = buscarPorId(id);
        veiculo.setAnoFabricacao(dados.getAnoFabricacao());
        veiculo.setCor(dados.getCor());
        veiculo.setPreco(dados.getPreco());
        veiculo.setQuilometragem(dados.getQuilometragem());
        if (dados.getStatus() != null) {
            veiculo.setStatus(dados.getStatus());
        }
        if (dados.getModelo() != null && dados.getModelo().getId() != null) {
            Modelo modelo = modeloRepository.findById(dados.getModelo().getId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException(
                            "Modelo não encontrado: " + dados.getModelo().getId()));
            veiculo.setModelo(modelo);
        }
        return veiculoRepository.save(veiculo);
    }

    @Transactional
    public Veiculo atualizarStatus(Long id, StatusVeiculo status) {
        Veiculo veiculo = buscarPorId(id);
        veiculo.setStatus(status);
        return veiculoRepository.save(veiculo);
    }

    @Transactional
    public void remover(Long id) {
        veiculoRepository.delete(buscarPorId(id));
    }
}
