-- validade, nro_doses_do_lote, nro_doses_atual, codigo_vacina

CREATE TABLE public.lote
(
    codigo            bigserial NOT NULL,
    validade          date,
    nro_doses_do_lote integer,
    nro_doses_atual   integer,
    codigo_vacina     bigint,
    status            text DEFAULT 'ATIVO',
    PRIMARY KEY (codigo),
    FOREIGN KEY (codigo_vacina) REFERENCES vacina (codigo)
);

INSERT INTO lote (validade, nro_doses_do_lote, nro_doses_atual, codigo_vacina)
VALUES ('2021-12-30', 1000, 931, 1),
       ('2021-06-30', 1000, 315, 1),
       ('2021-12-30', 1000, 931, 2),
       ('2021-06-30', 1000, 315, 2),
       ('2021-12-30', 1000, 931, 3),
       ('2021-06-30', 1000, 315, 3),
       ('2021-12-30', 1000, 931, 4),
       ('2021-06-30', 1000, 315, 4),
       ('2021-12-30', 1000, 931, 5),
       ('2021-06-30', 1000, 315, 5),
       ('2021-12-30', 1000, 931, 6),
       ('2021-06-30', 1000, 315, 6),
       ('2021-12-30', 1000, 931, 7),
       ('2021-06-30', 1000, 315, 7),
       ('2021-12-30', 1000, 931, 8),
       ('2021-06-30', 1000, 315, 8),
       ('2021-12-30', 1000, 931, 9),
       ('2021-06-30', 1000, 315, 9),
       ('2021-12-30', 1000, 931, 10),
       ('2021-06-30', 1000, 315, 10),
       ('2021-12-30', 1000, 931, 11),
       ('2021-06-30', 1000, 315, 11),
       ('2021-12-30', 1000, 931, 12),
       ('2021-06-30', 1000, 315, 12);
