CREATE DATABASE  IF NOT EXISTS `sodb` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `sodb`;
-- MySQL dump 10.13  Distrib 5.5.16, for Win32 (x86)
--
-- Host: localhost    Database: sodb
-- ------------------------------------------------------
-- Server version	5.5.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `idcategory` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idcategory`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Ar Condicionado'),(2,'Forno'),(3,'Lâmpada'),(4,'CO2');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `smartobject`
--

DROP TABLE IF EXISTS `smartobject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `smartobject` (
  `idsmartobject` int(11) NOT NULL AUTO_INCREMENT,
  `idcategory` int(11) DEFAULT NULL,
  `serverurl` varchar(100) NOT NULL,
  `idsomodbus` varchar(5) NOT NULL,
  PRIMARY KEY (`idsmartobject`),
  KEY `fksoidcategory` (`idcategory`),
  CONSTRAINT `fksoidcategory` FOREIGN KEY (`idcategory`) REFERENCES `category` (`idcategory`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `smartobject`
--

LOCK TABLES `smartobject` WRITE;
/*!40000 ALTER TABLE `smartobject` DISABLE KEYS */;
INSERT INTO `smartobject` VALUES (1,1,'http://192.168.43.138:8080/SOServer/soCommand.do','A1'),(2,2,'http://172.23.21.31:8080/SOServer/soCommand.do','0'),(3,3,'http://192.168.43.138:8080/SOServer/soCommand.do','A2'),(4,1,'http://192.168.43.138:8080/SOServer/soCommand.do','A1'),(5,4,'http://192.168.43.138:8080/SOServer/soCommand.do','A3');
/*!40000 ALTER TABLE `smartobject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `iduser` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`iduser`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'ercilio','batatinha'),(2,'joão','feijao'),(3,'maria','sapatao');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `service` (
  `idservice` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `idservicemodbus` varchar(5) NOT NULL,
  `idcategory` int(11) DEFAULT NULL,
  `idregistermodbus` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`idservice`),
  KEY `fkseridcategory` (`idcategory`),
  CONSTRAINT `fkseridcategory` FOREIGN KEY (`idcategory`) REFERENCES `category` (`idcategory`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service`
--

LOCK TABLES `service` WRITE;
/*!40000 ALTER TABLE `service` DISABLE KEYS */;
INSERT INTO `service` VALUES (6,'Ligar','05',1,'00'),(7,'Acender','05',3,'00'),(8,'Configurar','16',1,'00'),(9,'Cozinhar','0',2,'00'),(10,'Tipo de Função','0',1,'00'),(11,'Quantidade de CO2','03',4,'00'),(12,'Pegar Temperatura','03',1,'00');
/*!40000 ALTER TABLE `service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `souserjoin`
--

DROP TABLE IF EXISTS `souserjoin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `souserjoin` (
  `idsmartobject` int(11) DEFAULT NULL,
  `iduser` int(11) DEFAULT NULL,
  KEY `fkidsmartobject` (`idsmartobject`),
  KEY `fkiduser` (`iduser`),
  CONSTRAINT `fkidsmartobject` FOREIGN KEY (`idsmartobject`) REFERENCES `smartobject` (`idsmartobject`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fkiduser` FOREIGN KEY (`iduser`) REFERENCES `user` (`iduser`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `souserjoin`
--

LOCK TABLES `souserjoin` WRITE;
/*!40000 ALTER TABLE `souserjoin` DISABLE KEYS */;
INSERT INTO `souserjoin` VALUES (1,1),(3,1),(5,1);
/*!40000 ALTER TABLE `souserjoin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parameter`
--

DROP TABLE IF EXISTS `parameter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parameter` (
  `idparameter` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `minvalue` int(11) DEFAULT NULL,
  `maxvalue` int(11) DEFAULT NULL,
  `options` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`idparameter`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parameter`
--

LOCK TABLES `parameter` WRITE;
/*!40000 ALTER TABLE `parameter` DISABLE KEYS */;
INSERT INTO `parameter` VALUES (1,'Aceso','boolean',NULL,NULL,NULL),(2,'Ligado','boolean',NULL,NULL,NULL),(3,'Temperatura','double',18,28,NULL),(4,'Bebida','combo',NULL,NULL,'Sprite|Fanta|Coca Cola| Guaraná|Pepsi'),(5,'Comida','combo',NULL,NULL,'Frango|Arroz|Picanha|Macarrão'),(6,'Humidade','double',0,100,NULL),(7,'Velocidade','combo',NULL,NULL,'Fraco|Médio|Forte'),(8,'Timer','double',0,7,NULL),(9,'Função','combo',NULL,NULL,'Circular|Refrigerar|Aquecer'),(10,'Temperatura','get',NULL,NULL,NULL),(11,'CO2','get',NULL,NULL,NULL);
/*!40000 ALTER TABLE `parameter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `serviceparameter`
--

DROP TABLE IF EXISTS `serviceparameter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `serviceparameter` (
  `idservice` int(11) NOT NULL,
  `idparameter` int(11) NOT NULL,
  KEY `fkidservice` (`idservice`),
  KEY `fkidparameter` (`idparameter`),
  CONSTRAINT `fkidparameter` FOREIGN KEY (`idparameter`) REFERENCES `parameter` (`idparameter`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fkidservice` FOREIGN KEY (`idservice`) REFERENCES `service` (`idservice`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `serviceparameter`
--

LOCK TABLES `serviceparameter` WRITE;
/*!40000 ALTER TABLE `serviceparameter` DISABLE KEYS */;
INSERT INTO `serviceparameter` VALUES (6,2),(7,1),(8,3),(8,6),(8,7),(8,8),(9,5),(10,9),(12,10),(11,11);
/*!40000 ALTER TABLE `serviceparameter` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-06-04 23:04:05
