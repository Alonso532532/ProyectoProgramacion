DROP DATABASE IF EXISTS Dinopolis;

CREATE DATABASE Dinopolis;

USE Dinopolis;

-- TABLAS DE DINOPOLIS

CREATE TABLE Clientes(
	DNI VARCHAR(9) PRIMARY KEY,
	edad INTEGER not null,
	nombre VARCHAR(50) not null
);

CREATE TABLE Entrada(
	numeroDeEntrada INTEGER AUTO_INCREMENT PRIMARY KEY,
	tipo VARCHAR(20),
	precio DOUBLE(5,2) NOT NULL,
	DNI VARCHAR(9) NOT NULL,
	CONSTRAINT fk_DNI_Entrada FOREIGN KEY (DNI) REFERENCES Clientes (DNI) on update cascade on delete cascade,
	CONSTRAINT ck_tipo_Entrada CHECK (tipo IN ('Oferta', 'Normal', 'Familia numerosa')),
	CONSTRAINT ck_precio_Entrada CHECK (precio >= 0)
);

CREATE TABLE Zonas(
	numeroDeZona INTEGER AUTO_INCREMENT PRIMARY KEY,
	nombre VARCHAR(50) NOT NULL
);

CREATE TABLE Visita(
	DNI VARCHAR(9),
	numeroDeZona INTEGER,
	fecha DATETIME default NOW(),
	CONSTRAINT pk_visita PRIMARY KEY (DNI, numeroDeZona, fecha),
	CONSTRAINT fk_numeroDeZona_Visita FOREIGN KEY (numeroDeZona) REFERENCES Zonas (numeroDeZona) on update cascade on delete cascade,
	CONSTRAINT fk_DNI_Visita FOREIGN KEY (DNI) REFERENCES Clientes (DNI) on update cascade on delete cascade
);

CREATE TABLE Atracciones(
	numeroDeAtraccion INTEGER AUTO_INCREMENT PRIMARY KEY,
	nombre VARCHAR(50) NOT NULL,
	numeroDeZona INTEGER not null,
	CONSTRAINT fk_numeroDeZona_Atracciones FOREIGN KEY (numeroDeZona) REFERENCES Zonas (numeroDeZona) on update cascade on delete cascade
);

insert into Clientes () 
	values 
	("19232120D", 23, "Juan Carlos"),
	("29232320G", 54, "Hugo Lozano"),
	("17352120H", 11, "Pedro");

insert into Entrada (tipo, precio, DNI) 
	values 
	('Normal', 21.30, "19232120D"),
	('Familia numerosa', 16.80, "29232320G"),
	('Familia numerosa', 16.80, "29232320G");

insert into Zonas (nombre) 
	values
	("Paleosenda"),
	("Patio exterior"),
	("Recepción");

insert into Visita ()
	values
	('29232320G', 2, '2026-04-17 18:52:43'),	
	('17352120H', 3, '2026-04-17 18:35:52'),
	('29232320G', 3, '2026-04-17 18:32:23'),
	('17352120H', 2, '2026-04-17 17:13:54'),
	('29232320G', 2, '2026-04-17 17:02:23'),
	('17352120H', 1, '2026-04-16 13:32:10');

insert into Atracciones (nombre, numeroDeZona) 
	values
	('Laberinto', 2),
	('Museo', 2),
	('Trenecito', 1),
	('Teatro', 1);

-- USUARIOS DEL PROGRAMA

create table Usuarios(
	nombre VARCHAR(20) primary key,
	contrasena VARCHAR(50) not null,
	esAdmin BOOL default false
);
	

insert into Usuarios () 
	values
	('Admin', 'Sor2425$', true),
	('User', 'Sor2425$', false);

