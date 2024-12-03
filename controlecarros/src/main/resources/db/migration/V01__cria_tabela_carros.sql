-- Criação da tabela 'carro'
CREATE TABLE public.carro
(
    codigo    bigserial NOT NULL,
    marca     text,
    modelo    text,
    cor       text,
    ano       integer,
    placa     text,
    ocupado   text DEFAULT 'INATIVO',
    status    text DEFAULT 'ATIVO',
    PRIMARY KEY (codigo)
);

-- Inserção de dados na tabela 'carro'
INSERT INTO public.carro (marca, modelo, cor, ano, placa, ocupado, status)
VALUES
    ('Toyota', 'Corolla', 'Prata', 2021, 'ABC-1234', 'INATIVO', 'ATIVO'),
    ('Honda', 'Civic', 'Preto', 2020, 'DEF-5678', 'INATIVO', 'ATIVO'),
    ('Ford', 'Focus', 'Branco', 2019, 'GHI-9101', 'INATIVO', 'ATIVO'),
    ('Chevrolet', 'Onix', 'Azul', 2022, 'JKL-2345', 'INATIVO', 'ATIVO'),
    ('Fiat', 'Uno', 'Vermelho', 2018, 'MNO-6789', 'INATIVO', 'ATIVO'),
    ('Volkswagen', 'Gol', 'Cinza', 2023, 'PQR-3456', 'INATIVO', 'ATIVO');
