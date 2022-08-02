create database SistemaVeiculos;
use SistemaVeiculos;
create table Cliente(id bigint not null auto_increment, email varchar(512) not null unique, pass varchar(1200) not null, cpf char(11) not null, nome varchar(256) not null, telefone varchar(14) not null, sexo varchar(1) not null, nascimento date not null, primary key(id));
create table Loja(id bigint not null auto_increment, email varchar(512) not null unique, pass varchar(1200) not null, cnpj char(14) not null, nome varchar(256) not null, descricao varchar(1024) not null, primary key(id));
create table Carros(placa char(7) not null, modelo varchar(128) not null, chassi varchar(64) not null, ano bigint not null, quilometragem bigint not null, descricao varchar(1024) not null, valor float not null, loja_id bigint not null, primary key(placa), foreign key(loja_id) references Loja(id));
create table Imagens(id bigint not null auto_increment, carro_id char(7) not null, primary key(id), foreign key(carro_id) references Carros(placa));
create table Proposta(id bigint not null auto_increment, loja_id bigint not null, cliente_id bigint not null, carro_id char(7) not null, valor float not null, condicoes varchar(1200) not null, estado varchar(11) not null, contraproposta varchar(1200), data_proposta date not null, primary key(id, cliente_id, carro_id), foreign key(cliente_id) references Cliente(id), foreign key(loja_id) references Loja(id), foreign key(carro_id) references Carros(placa));

-- testes
insert into Loja(email, pass, cnpj, nome, descricao) values ('loja', md5('loja'), '12523', 'Loja Topster', 'Super mega hyper loja');
insert into Carros (placa, modelo, chassi, ano, quilometragem, descricao, valor, loja_id) values ('abc1234', 'Ferrari', 'ahuyshuia', 2021, 10, 'Que carrao em', 12674.70, 1);
insert into Cliente(email, pass, cpf, nome, telefone, sexo, nascimento) values ('cliente', md5('cliente'), '1345', 'Jorge', '40028922', 'M', '2001-07-09');
insert into Proposta(loja_id, cliente_id, carro_id, valor, condicoes, estado, data_proposta) values (1, 1, 'abc1234', 12000, 'tem que ter glub', 'ABERTO', curtime());

create table Editora(id bigint not null auto_increment, cnpj varchar(18) not null, nome varchar(256) not null, primary key (id));
create table Livro(id bigint not null auto_increment, titulo varchar(256) not null, autor varchar(256) not null, ano integer not null, preco float not null, editora_id bigint not null, primary key (id), foreign key (editora_id) references Editora(id));
insert into Editora(cnpj, nome) values  ('55.789.390/0008-99', 'Companhia das Letras');
insert into Editora(cnpj, nome) values ('71.150.470/0001-40', 'Record');
insert into Editora(cnpj, nome) values ('32.106.536/0001-82', 'Objetiva');
insert into Livro(titulo, autor, ano, preco, editora_id) values ('Ensaio sobre a Cegueira', 'José Saramago', 1995, 54.9, 1);
insert into Livro(titulo, autor, ano, preco, editora_id) values  ('Cem anos de Solidão', 'Gabriel Garcia Márquez', 1977, 59.9, 2);
insert into Livro(titulo, autor, ano, preco, editora_id) values ('Diálogos Impossíveis', 'Luis Fernando Verissimo', 2012, 22.9, 3);
