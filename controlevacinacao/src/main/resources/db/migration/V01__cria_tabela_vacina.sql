CREATE TABLE public.vacina
(
    codigo    bigserial NOT NULL,
    nome      text,
    descricao text,
    status    text DEFAULT 'ATIVO',
    PRIMARY KEY (codigo)
);

INSERT INTO vacina (nome, descricao)
VALUES ('BCG-ID', 'Formas graves de Tuberculose'),
       ('Hepatite B', 'Hepatite B'),
       ('Pólio', 'Poliomielite (paralisia Infantil)'),
       ('Tetravalente', 'Difteria, tétano, coqueluche, meningite e outras infecções por Haemophilus influenzae tipo B'),
       ('Rotavírus Humano', 'Doenças diarréicas por Rotavírus'),
       ('Pneumococos', 'Otite, sinusite, pneumonias, meningite causadas pelo Streptococcus pneumoniae'),
       ('Meningococo C', 'Doença invasiva causada por Neisseria meningitidis do Sorogrupo C'),
       ('Febre amarela', 'Febre amarela'),
       ('Tríplice Viral', 'Sarampo, Rubéola e Caxumba'),
       ('Tríplice Bacteriana', 'Difteria, tétano, coqueluche'),
       ('Dupla Adulto', 'Difteria e Tétano'),
       ('Influenza', 'Influenza ou Gripe');