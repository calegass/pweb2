CREATE TABLE public.usuario
(
    codigo          bigserial NOT NULL,
    nome            text,
    email           text,
    nome_usuario    text,
    senha           text,
    data_nascimento date,
    ativo           boolean,
    PRIMARY KEY (codigo)
);

CREATE TABLE public.papel
(
    codigo bigserial NOT NULL,
    nome   text,
    PRIMARY KEY (codigo)
);

CREATE TABLE public.usuario_papel
(
    codigo_usuario bigint NOT NULL,
    codigo_papel   bigint NOT NULL
);

ALTER TABLE public.usuario_papel
    ADD FOREIGN KEY (codigo_usuario)
        REFERENCES public.usuario (codigo)
        NOT VALID;


ALTER TABLE public.usuario_papel
    ADD FOREIGN KEY (codigo_papel)
        REFERENCES public.papel (codigo)
        NOT VALID;

INSERT INTO usuario (nome, email, senha, nome_usuario, ativo, data_nascimento)
VALUES ('Grosbilda', 'grosbilda@seilah.com', '{noop}12345', 'grosbilda', true, '2000-06-06'),
       ('Estrobilobaldo', 'estrobilobaldo@seilah.com', '{noop}12345', 'estrobilobaldo', true, '2001-06-12'),
       ('Zirgonisvaldo', 'zirgonisvaldo@seilah.com', '{noop}12345', 'zirgonisvaldo', true, '2002-06-11');

INSERT INTO papel (codigo, nome)
VALUES (1, 'ROLE_ADMIN'),
       (2, 'ROLE_USUARIO');

INSERT INTO usuario_papel (codigo_usuario, codigo_papel)
VALUES (1, 1),
       (2, 2),
       (3, 1),
       (3, 2);
