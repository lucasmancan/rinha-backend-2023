SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;

SET default_tablespace = '';

SET default_table_access_method = heap;

DROP TABLE IF EXISTS public.pessoas;

CREATE TABLE public.pessoas (
    id uuid not null,
    apelido varchar(32) unique not null,
    nascimento date not null,
    nome varchar(100) not null,
    stack varchar(255),
    primary key (id)
);
