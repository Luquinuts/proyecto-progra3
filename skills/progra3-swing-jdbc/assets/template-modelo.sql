-- ============================================
-- Script SQL - Template para proyecto final Progra 3
-- Integrantes: Galende Ramiro, Palavecino Lucas
-- ============================================

CREATE DATABASE IF NOT EXISTS NombreProyecto;
USE NombreProyecto;

-- Tabla principal (ej: Producto, Cliente, etc.)
CREATE TABLE IF NOT EXISTS Producto (
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) DEFAULT NULL,
    precio DECIMAL(10,2) DEFAULT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla secundaria con FK (ej: Postulante)
CREATE TABLE IF NOT EXISTS Postulante (
    dni INT NOT NULL,
    nombre VARCHAR(100) DEFAULT NULL,
    apellido VARCHAR(100) DEFAULT NULL,
    correoElectronico VARCHAR(100) DEFAULT NULL,
    PRIMARY KEY (dni)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Tabla de transacciones/relaciones con FK (ej: Oferta)
CREATE TABLE IF NOT EXISTS Oferta (
    id INT NOT NULL AUTO_INCREMENT,
    postulante_dni INT NOT NULL,
    producto_id INT NOT NULL,
    monto DECIMAL(10,2) DEFAULT NULL,
    fechaCreacion VARCHAR(100) DEFAULT NULL,
    estado VARCHAR(50) DEFAULT NULL,
    PRIMARY KEY (id),
    KEY fk_postulante (postulante_dni),
    KEY fk_producto (producto_id),
    CONSTRAINT fk_postulante FOREIGN KEY (postulante_dni) REFERENCES Postulante (dni),
    CONSTRAINT fk_producto FOREIGN KEY (producto_id) REFERENCES Producto (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Datos de ejemplo
INSERT INTO Producto (nombre, precio) VALUES
('Producto 1', 1000.00),
('Producto 2', 500.00);

INSERT INTO Postulante (dni, nombre, apellido, correoElectronico) VALUES
(123, 'Juan', 'Perez', 'jperez@email.com'),
(456, 'Maria', 'Garcia', 'mgarcia@email.com');
