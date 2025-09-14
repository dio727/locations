CREATE TABLE locations (
    id_location BIGINT AUTO_INCREMENT PRIMARY KEY,
    cep VARCHAR(20) NOT NULL,
    logradouro VARCHAR(255),
    complemento VARCHAR(255),
    unidade VARCHAR(255),
    bairro VARCHAR(255),
    localidade VARCHAR(255) NOT NULL,
    uf VARCHAR(10) NOT NULL,
    estado VARCHAR(100),
    regiao VARCHAR(100),
    ibge VARCHAR(50),
    gia VARCHAR(50),
    ddd VARCHAR(5),
    siafi VARCHAR(50)
);
