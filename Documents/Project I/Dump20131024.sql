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
-- Table structure for table `soowner`
--

DROP TABLE IF EXISTS `soowner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `soowner` (
  `idowner` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `lastname` varchar(45) DEFAULT NULL,
  `user` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idowner`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `soowner`
--

LOCK TABLES `soowner` WRITE;
/*!40000 ALTER TABLE `soowner` DISABLE KEYS */;
/*!40000 ALTER TABLE `soowner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `soservices`
--

DROP TABLE IF EXISTS `soservices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `soservices` (
  `idso` int(11) NOT NULL,
  `idservice` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `returntype` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idservice`,`idso`),
  KEY `fkidso` (`idso`),
  CONSTRAINT `fkidso` FOREIGN KEY (`idso`) REFERENCES `so` (`idso`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `soservices`
--

LOCK TABLES `soservices` WRITE;
/*!40000 ALTER TABLE `soservices` DISABLE KEYS */;
/*!40000 ALTER TABLE `soservices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servers`
--

DROP TABLE IF EXISTS `servers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `servers` (
  `idserver` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `url` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idserver`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servers`
--

LOCK TABLES `servers` WRITE;
/*!40000 ALTER TABLE `servers` DISABLE KEYS */;
/*!40000 ALTER TABLE `servers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `so`
--

DROP TABLE IF EXISTS `so`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `so` (
  `idserver` int(11) NOT NULL,
  `idowner` int(11) NOT NULL,
  `idso` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idso`,`idowner`,`idserver`),
  KEY `fkidserver` (`idserver`),
  KEY `fkidowner` (`idowner`),
  CONSTRAINT `fkidowner` FOREIGN KEY (`idowner`) REFERENCES `soowner` (`idowner`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fkidserver` FOREIGN KEY (`idserver`) REFERENCES `servers` (`idserver`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `so`
--

LOCK TABLES `so` WRITE;
/*!40000 ALTER TABLE `so` DISABLE KEYS */;
/*!40000 ALTER TABLE `so` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servicesparams`
--

DROP TABLE IF EXISTS `servicesparams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `servicesparams` (
  `idservice` int(11) NOT NULL,
  `idparam` int(11) NOT NULL,
  `param1` varchar(45) DEFAULT NULL,
  `param2` varchar(45) DEFAULT NULL,
  `param3` varchar(45) DEFAULT NULL,
  `param4` varchar(45) DEFAULT NULL,
  `param5` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idparam`,`idservice`),
  KEY `fkidservice` (`idservice`),
  CONSTRAINT `fkidservice` FOREIGN KEY (`idservice`) REFERENCES `soservices` (`idservice`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servicesparams`
--

LOCK TABLES `servicesparams` WRITE;
/*!40000 ALTER TABLE `servicesparams` DISABLE KEYS */;
/*!40000 ALTER TABLE `servicesparams` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-10-24 10:23:08
