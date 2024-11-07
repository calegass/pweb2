CREATE TABLE public.pessoa
(
    codigo          serial NOT NULL,
    nome            text,
    cpf             text,
    data_nascimento date,
    profissao       text,
    status          text DEFAULT 'ATIVO',
    PRIMARY KEY (codigo)
);

INSERT INTO pessoa (nome, cpf, data_nascimento, profissao)
VALUES ('Grosbilda', 123456789, '1980-03-25', 'Dentista'),
       ('Estrobilobaldo', 987654321, '1980-05-25', 'Professor(a)'),
       ('Zirgonisvaldo', 123654789, '1980-07-25', 'Médico(a)');