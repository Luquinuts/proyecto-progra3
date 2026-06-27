-- phpMyAdmin SQL Dump
-- version 5.2.3
-- https://www.phpmyadmin.net/
--
-- Host: db
-- Generation Time: Jun 17, 2026 at 09:34 PM
-- Server version: 9.7.0
-- PHP Version: 8.3.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `Subastas`
--

-- --------------------------------------------------------

--
-- Table structure for table `Oferta`
--

CREATE TABLE `Oferta` (
  `id` int NOT NULL,
  `dniPostulante` int NOT NULL,
  `idProducto` int NOT NULL,
  `fechaCreacion` varchar(100) DEFAULT NULL,
  `monto` decimal(10,2) DEFAULT NULL,
  `estado` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `Oferta`
--

INSERT INTO `Oferta` (`id`, `dniPostulante`, `idProducto`, `fechaCreacion`, `monto`, `estado`) VALUES
(1, 789, 1, '2026-06-17 18:34:24', 2051.62, 'EXITO'),
(2, 456, 1, '2026-06-17 18:34:25', 2457.91, 'EXITO'),
(3, 123, 1, '2026-06-17 18:34:25', 1643.30, 'RECHAZADO');

-- --------------------------------------------------------

--
-- Table structure for table `Postulante`
--

CREATE TABLE `Postulante` (
  `dni` int NOT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `apellido` varchar(100) DEFAULT NULL,
  `correoElectronico` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `Postulante`
--

INSERT INTO `Postulante` (`dni`, `nombre`, `apellido`, `correoElectronico`) VALUES
(123, 'Juan', 'Perez', 'jperez@subastas.com'),
(456, 'Pedro', 'Ramirez', 'pramirez@subastas.com'),
(789, 'Ernesto', 'Rodriguez', 'erodriguez@subastas.com');

-- --------------------------------------------------------

--
-- Table structure for table `Producto`
--

CREATE TABLE `Producto` (
  `id` int NOT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `precioBase` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `Producto`
--

INSERT INTO `Producto` (`id`, `nombre`, `precioBase`) VALUES
(1, 'Reloj de mesa', 1234.56),
(2, 'Cuchillo antiguo', 300.00);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Oferta`
--
ALTER TABLE `Oferta`
  ADD PRIMARY KEY (`id`),
  ADD KEY `dniPostulante` (`dniPostulante`),
  ADD KEY `idProducto` (`idProducto`);

--
-- Indexes for table `Postulante`
--
ALTER TABLE `Postulante`
  ADD PRIMARY KEY (`dni`);

--
-- Indexes for table `Producto`
--
ALTER TABLE `Producto`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Oferta`
--
ALTER TABLE `Oferta`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `Producto`
--
ALTER TABLE `Producto`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Oferta`
--
ALTER TABLE `Oferta`
  ADD CONSTRAINT `Oferta_ibfk_1` FOREIGN KEY (`dniPostulante`) REFERENCES `Postulante` (`dni`),
  ADD CONSTRAINT `Oferta_ibfk_2` FOREIGN KEY (`idProducto`) REFERENCES `Producto` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
