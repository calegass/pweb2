CREATE TABLE public.pessoa
(
    codigo          bigserial NOT NULL,
    cpf             text,
    data_nascimento date,
    nome            text,
    profissao       text,
    status          text DEFAULT 'ATIVO',
    PRIMARY KEY (codigo)
);