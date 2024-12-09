CREATE TABLE public.carro
(
    codigo       bigserial NOT NULL,
    marca        text,
    modelo       text,
    cor          text,
    ano          integer,
    placa        text,
    kilometragem float DEFAULT 0,
    kmRodados   float DEFAULT 0,
    enabled      boolean DEFAULT true,
    status       text DEFAULT 'DISPONIVEL',
    PRIMARY KEY (codigo)
);

-- Inserção de dados na tabela 'carro'
INSERT INTO public.carro (marca, modelo, cor, ano, placa, kilometragem, kmRodados, enabled, status)
VALUES
    ('Toyota', 'Corolla', 'Prata', 2021, 'ABC-1234', 15000.5, 0, true, 'DISPONIVEL'),
    ('Honda', 'Civic', 'Preto', 2020, 'DEF-5678', 22000.0, 0, true, 'DISPONIVEL'),
    ('Ford', 'Focus', 'Branco', 2019, 'GHI-9101', 18000.3, 0, true, 'DISPONIVEL'),
    ('Chevrolet', 'Onix', 'Azul', 2022, 'JKL-2345', 5000.7, 0, true, 'DISPONIVEL'),
    ('Fiat', 'Uno', 'Vermelho', 2018, 'MNO-6789', 30000.2, 0, true, 'DISPONIVEL'),
    ('Volkswagen', 'Gol', 'Cinza', 2023, 'PQR-3456', 1000.0, 0,  true, 'DISPONIVEL');

-- Criação da tabela de aluguel (rental)
CREATE TABLE public.rental (
    id              bigserial NOT NULL,
    carro_id        bigint NOT NULL,
    nome_funcionario text NOT NULL,
    data_inicial    timestamp NOT NULL,
    data_final      timestamp,
    PRIMARY KEY (id),
    FOREIGN KEY (carro_id) REFERENCES public.carro (codigo)
);