CREATE TABLE public.carro
(
    codigo    bigserial NOT NULL,
    marca     text,
    modelo    text,
    cor       text,
    ano       integer,
    placa     text,
    enabled   boolean DEFAULT true,
    status    text DEFAULT 'DISPONIVEL',
    PRIMARY KEY (codigo)
);

-- Inserção de dados na tabela 'carro'
INSERT INTO public.carro (marca, modelo, cor, ano, placa, enabled, status)
VALUES
    ('Toyota', 'Corolla', 'Prata', 2021, 'ABC-1234', true, 'DISPONIVEL'),
    ('Honda', 'Civic', 'Preto', 2020, 'DEF-5678', true, 'DISPONIVEL'),
    ('Ford', 'Focus', 'Branco', 2019, 'GHI-9101', true, 'DISPONIVEL'),
    ('Chevrolet', 'Onix', 'Azul', 2022, 'JKL-2345', true, 'DISPONIVEL'),
    ('Fiat', 'Uno', 'Vermelho', 2018, 'MNO-6789', true, 'DISPONIVEL'),
    ('Volkswagen', 'Gol', 'Cinza', 2023, 'PQR-3456', true, 'DISPONIVEL');

-- Criação da tabela de aluguel (rental)
CREATE TABLE public.rental (
    id bigserial NOT NULL,
    carro_id bigint NOT NULL,
    nome_funcionario text NOT NULL,
    data_inicial timestamp NOT NULL,
    data_final timestamp,
    PRIMARY KEY (id),
    FOREIGN KEY (carro_id) REFERENCES public.carro (codigo)
);