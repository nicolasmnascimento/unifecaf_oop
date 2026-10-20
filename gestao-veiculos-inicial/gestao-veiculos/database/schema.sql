CREATE DATABASE IF NOT EXISTS gestao_veiculos
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE gestao_veiculos;

CREATE TABLE IF NOT EXISTS marcas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(80) NOT NULL,
    pais_origem VARCHAR(60),
    PRIMARY KEY (id),
    CONSTRAINT uk_marcas_nome UNIQUE (nome)
);

CREATE TABLE IF NOT EXISTS modelos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    tipo VARCHAR(40) NOT NULL,
    marca_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_modelo_marca UNIQUE (nome, marca_id),
    CONSTRAINT fk_modelos_marca FOREIGN KEY (marca_id) REFERENCES marcas(id)
);

CREATE TABLE IF NOT EXISTS veiculos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    modelo_id BIGINT NOT NULL,
    ano_fabricacao INT NOT NULL,
    cor VARCHAR(40) NOT NULL,
    preco DECIMAL(12,2) NOT NULL,
    quilometragem BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'DISPONIVEL',
    PRIMARY KEY (id),
    CONSTRAINT fk_veiculos_modelo FOREIGN KEY (modelo_id) REFERENCES modelos(id),
    CONSTRAINT chk_veiculos_preco CHECK (preco > 0),
    CONSTRAINT chk_veiculos_quilometragem CHECK (quilometragem >= 0)
);
