-- ============================================
-- CineApp - Sistema de Compra de Boletos de Cine
-- Proyecto Final Programacion 3
-- Integrantes: Galende Ramiro, Palavecino Lucas
-- ============================================

CREATE DATABASE IF NOT EXISTS cine;
USE cine;

-- Tabla de peliculas
CREATE TABLE peliculas (
    id_pelicula INT PRIMARY KEY,
    titulo VARCHAR(100),
    genero VARCHAR(50),
    duracion INT,
    clasificacion VARCHAR(10)
);

-- Tabla de salas
CREATE TABLE salas (
    id_sala INT PRIMARY KEY,
    nombre VARCHAR(30),
    capacidad INT
);

-- Tabla de butacas
CREATE TABLE butacas (
    id_butaca INT PRIMARY KEY,
    id_sala INT,
    fila CHAR(1),
    numero INT,
    FOREIGN KEY (id_sala) REFERENCES salas(id_sala)
);

-- Tabla de funciones (pelicula + sala + horario)
CREATE TABLE funciones (
    id_funcion INT PRIMARY KEY,
    id_pelicula INT,
    id_sala INT,
    fecha DATE,
    hora TIME,
    precio DECIMAL(8,2),
    FOREIGN KEY (id_pelicula) REFERENCES peliculas(id_pelicula),
    FOREIGN KEY (id_sala) REFERENCES salas(id_sala)
);

-- Tabla de clientes
CREATE TABLE clientes (
    id_cliente INT PRIMARY KEY,
    nombre VARCHAR(50),
    apellido VARCHAR(50),
    email VARCHAR(100),
    telefono VARCHAR(20)
);

-- Tabla de reservas (cabecera)
CREATE TABLE reservas (
    id_reserva INT PRIMARY KEY,
    id_cliente INT,
    fecha_reserva DATE,
    FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente)
);

-- Tabla de detalle de reserva (butacas reservadas por funcion)
CREATE TABLE detalle_reserva (
    id_detalle INT PRIMARY KEY,
    id_reserva INT,
    id_funcion INT,
    id_butaca INT,
    FOREIGN KEY (id_reserva) REFERENCES reservas(id_reserva),
    FOREIGN KEY (id_funcion) REFERENCES funciones(id_funcion),
    FOREIGN KEY (id_butaca) REFERENCES butacas(id_butaca),
    UNIQUE (id_funcion, id_butaca)
);

-- ============================================
-- DATOS DE EJEMPLO
-- ============================================

-- Peliculas
INSERT INTO peliculas VALUES
(1, 'El Padrino', 'Drama', 175, '+16'),
(2, 'Inception', 'Ciencia Ficcion', 148, '+13'),
(3, 'Toy Story', 'Animacion', 81, 'ATP'),
(4, 'Jurassic Park', 'Aventura', 127, '+13');

-- Salas
INSERT INTO salas VALUES
(1, 'Sala 1 - 2D', 40),
(2, 'Sala 2 - 3D', 30),
(3, 'Sala 3 - VIP', 20);

-- Butacas Sala 1 (5 filas x 8 butacas)
INSERT INTO butacas VALUES
(1, 1, 'A', 1), (2, 1, 'A', 2), (3, 1, 'A', 3), (4, 1, 'A', 4),
(5, 1, 'A', 5), (6, 1, 'A', 6), (7, 1, 'A', 7), (8, 1, 'A', 8),
(9, 1, 'B', 1), (10, 1, 'B', 2), (11, 1, 'B', 3), (12, 1, 'B', 4),
(13, 1, 'B', 5), (14, 1, 'B', 6), (15, 1, 'B', 7), (16, 1, 'B', 8),
(17, 1, 'C', 1), (18, 1, 'C', 2), (19, 1, 'C', 3), (20, 1, 'C', 4),
(21, 1, 'C', 5), (22, 1, 'C', 6), (23, 1, 'C', 7), (24, 1, 'C', 8),
(25, 1, 'D', 1), (26, 1, 'D', 2), (27, 1, 'D', 3), (28, 1, 'D', 4),
(29, 1, 'D', 5), (30, 1, 'D', 6), (31, 1, 'D', 7), (32, 1, 'D', 8),
(33, 1, 'E', 1), (34, 1, 'E', 2), (35, 1, 'E', 3), (36, 1, 'E', 4),
(37, 1, 'E', 5), (38, 1, 'E', 6), (39, 1, 'E', 7), (40, 1, 'E', 8);

-- Butacas Sala 2 (5 filas x 6 butacas)
INSERT INTO butacas VALUES
(41, 2, 'A', 1), (42, 2, 'A', 2), (43, 2, 'A', 3), (44, 2, 'A', 4), (45, 2, 'A', 5), (46, 2, 'A', 6),
(47, 2, 'B', 1), (48, 2, 'B', 2), (49, 2, 'B', 3), (50, 2, 'B', 4), (51, 2, 'B', 5), (52, 2, 'B', 6),
(53, 2, 'C', 1), (54, 2, 'C', 2), (55, 2, 'C', 3), (56, 2, 'C', 4), (57, 2, 'C', 5), (58, 2, 'C', 6),
(59, 2, 'D', 1), (60, 2, 'D', 2), (61, 2, 'D', 3), (62, 2, 'D', 4), (63, 2, 'D', 5), (64, 2, 'D', 6),
(65, 2, 'E', 1), (66, 2, 'E', 2), (67, 2, 'E', 3), (68, 2, 'E', 4), (69, 2, 'E', 5), (70, 2, 'E', 6);

-- Butacas Sala 3 (4 filas x 5 butacas)
INSERT INTO butacas VALUES
(71, 3, 'A', 1), (72, 3, 'A', 2), (73, 3, 'A', 3), (74, 3, 'A', 4), (75, 3, 'A', 5),
(76, 3, 'B', 1), (77, 3, 'B', 2), (78, 3, 'B', 3), (79, 3, 'B', 4), (80, 3, 'B', 5),
(81, 3, 'C', 1), (82, 3, 'C', 2), (83, 3, 'C', 3), (84, 3, 'C', 4), (85, 3, 'C', 5),
(86, 3, 'D', 1), (87, 3, 'D', 2), (88, 3, 'D', 3), (89, 3, 'D', 4), (90, 3, 'D', 5);

-- Funciones
INSERT INTO funciones VALUES
(1, 1, 1, '2026-07-15', '18:00:00', 1500.00),
(2, 1, 1, '2026-07-15', '21:00:00', 1800.00),
(3, 2, 2, '2026-07-15', '19:00:00', 2000.00),
(4, 3, 3, '2026-07-16', '16:00:00', 2500.00),
(5, 4, 1, '2026-07-16', '20:00:00', 1600.00),
(6, 2, 2, '2026-07-16', '22:00:00', 2000.00);
