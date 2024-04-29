create database bibliotecajava;
use bibliotecajava;

create table Pessoa(
	cpf varchar(11) primary key,
    nome varchar(100)
);

create table Usuario(
	cpf varchar(11),
    endereco varchar(250),
    telefone varchar(20),
    foreign key (cpf) references pessoa(cpf)
);

create table Funcionario(
	cpf varchar(11),
    funcao varchar(30),
    salario float,
    foreign key (cpf) references pessoa(cpf)
);

create table ItemBiblioteca(
	codigo int primary key,
    titulo varchar(50),
    status enum('disponivel','emprestado')
);

create table Editora(
	codigo int primary key,	
    nome varchar(100),
    contato varchar(100)
);

create table Livro(
    codigo int,
    edicao int, 
    genero varchar(30),
    anoDePublicacao int,
    codigoEditora int, 
    foreign key (codigo) references ItemBiblioteca(codigo),
    foreign key (codigoEditora) references Editora(codigo)
);

create table Autores(
	codigo int primary key,
    nome varchar(100),
    nacionalidade varchar(50)
);


alter table livro  add column cpfLivro varchar(11);
select * from pessoa;
select * from livro	;
select * from autores;
select * from editora;
select * from itembiblioteca;
select * from funcionario;
select * from usuario;

use bibliotecajava;
DELETE FROM pessoa WHERE cpf = '097';
SET SQL_SAFE_UPDATES = 0;
update pessoa set cpf = '097' where cpf = '09747553627';
