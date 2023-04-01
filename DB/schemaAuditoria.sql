CREATE DATABASE  IF NOT EXISTS `2023Auditoria` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `2023Auditoria`;
-- MySQL dump 10.13  Distrib 8.0.24, for Win64 (x86_64)
--
-- Host: localhost    Database: 2021Auditoria
-- ------------------------------------------------------
-- Server version	8.0.28-0ubuntu0.20.04.3

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `JEActas`
--


--
-- Table structure for table `RegistrosProveedor`
--

DROP TABLE IF EXISTS `RegistrosProveedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `RegistrosProveedor` (
  `idRegistrosProveedor` int NOT NULL AUTO_INCREMENT,
  `NombreArchivo` varchar(45) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `Hash` varchar(64) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `HashFirmado` varchar(172) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `TimestampFirma` datetime NOT NULL,
  `TimestampAlmacenaje` datetime NOT NULL,
  `NombrePrueba` varchar(45) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `NombreCiudad` varchar(45) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  PRIMARY KEY (`idRegistrosProveedor`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

CREATE USER 'AuditPHP'@'localhost' IDENTIFIED WITH mysql_native_password BY '@udi20215963H';
GRANT ALL PRIVILEGES ON 2023Auditoria.RegistrosProveedor TO 'AuditPHP'@'localhost';
FLUSH PRIVILEGES;

/*!40101 SET character_set_client = @saved_cs_client */;


/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-03-28 16:31:07
