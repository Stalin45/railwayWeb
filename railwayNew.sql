CREATE DATABASE  IF NOT EXISTS `railwayapp` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `railwayapp`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: localhost    Database: railwayapp
-- ------------------------------------------------------
-- Server version	5.6.16

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
-- Table structure for table `destinations`
--

DROP TABLE IF EXISTS `destinations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `destinations` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `number` int(11) DEFAULT NULL,
  `time` time DEFAULT NULL,
  `path_number` bigint(20) DEFAULT NULL,
  `station_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_l1ussdyafsjprru7ky8rfn9cy` (`path_number`),
  KEY `FK_l7d6btn7ox5t11xnr3kboaqv6` (`station_id`),
  CONSTRAINT `FK_l1ussdyafsjprru7ky8rfn9cy` FOREIGN KEY (`path_number`) REFERENCES `paths` (`number`),
  CONSTRAINT `FK_l7d6btn7ox5t11xnr3kboaqv6` FOREIGN KEY (`station_id`) REFERENCES `stations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `destinations`
--

LOCK TABLES `destinations` WRITE;
/*!40000 ALTER TABLE `destinations` DISABLE KEYS */;
INSERT INTO `destinations` VALUES (1,1,'01:10:00',1,1),(2,2,'03:54:00',1,2),(3,3,'04:51:00',1,3),(4,1,'01:10:00',2,3),(5,2,'03:54:00',2,2),(6,3,'04:51:00',2,3),(7,4,'06:18:00',2,4);
/*!40000 ALTER TABLE `destinations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `passengers`
--

DROP TABLE IF EXISTS `passengers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `passengers` (
  `passport` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `second_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`passport`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `passengers`
--

LOCK TABLES `passengers` WRITE;
/*!40000 ALTER TABLE `passengers` DISABLE KEYS */;
INSERT INTO `passengers` VALUES (12345,'Ilya','Kapralov'),(566754,'fsef','ssfse');
/*!40000 ALTER TABLE `passengers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pathmap`
--

DROP TABLE IF EXISTS `pathmap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pathmap` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cost` float DEFAULT NULL,
  `current_station` bigint(20) DEFAULT NULL,
  `next_station` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_l3xmi31ed3qlfy17yqq8mx4sm` (`current_station`),
  KEY `FK_l1skne3xw9nov0bjeslbuppuh` (`next_station`),
  CONSTRAINT `FK_l1skne3xw9nov0bjeslbuppuh` FOREIGN KEY (`next_station`) REFERENCES `stations` (`id`),
  CONSTRAINT `FK_l3xmi31ed3qlfy17yqq8mx4sm` FOREIGN KEY (`current_station`) REFERENCES `stations` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pathmap`
--

LOCK TABLES `pathmap` WRITE;
/*!40000 ALTER TABLE `pathmap` DISABLE KEYS */;
INSERT INTO `pathmap` VALUES (1,500,1,2),(2,500,2,1),(3,100,2,3),(4,200,2,4),(5,100,3,2),(6,50,3,4),(7,200,4,2),(8,50,5,1),(9,0,5,2),(10,50,1,5);
/*!40000 ALTER TABLE `pathmap` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paths`
--

DROP TABLE IF EXISTS `paths`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `paths` (
  `number` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`number`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paths`
--

LOCK TABLES `paths` WRITE;
/*!40000 ALTER TABLE `paths` DISABLE KEYS */;
INSERT INTO `paths` VALUES (1),(2);
/*!40000 ALTER TABLE `paths` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stations`
--

DROP TABLE IF EXISTS `stations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stations` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stations`
--

LOCK TABLES `stations` WRITE;
/*!40000 ALTER TABLE `stations` DISABLE KEYS */;
INSERT INTO `stations` VALUES (1,'moscow'),(2,'petersburg'),(3,'gatchina'),(4,'pushkin'),(5,'st2');
/*!40000 ALTER TABLE `stations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `superuser`
--

DROP TABLE IF EXISTS `superuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `superuser` (
  `role` varchar(31) NOT NULL,
  `login` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `superuser`
--

LOCK TABLES `superuser` WRITE;
/*!40000 ALTER TABLE `superuser` DISABLE KEYS */;
INSERT INTO `superuser` VALUES ('S','spec1','pass2'),('U','user1','pass1');
/*!40000 ALTER TABLE `superuser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tickets`
--

DROP TABLE IF EXISTS `tickets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tickets` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `total_cost` bigint(20) DEFAULT NULL,
  `passenger_id` bigint(20) DEFAULT NULL,
  `timetable_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_fxds3oay8p6ccc069ype4a0p4` (`passenger_id`),
  KEY `FK_o7kinaw7xwheynwhqp2goy353` (`timetable_id`),
  CONSTRAINT `FK_fxds3oay8p6ccc069ype4a0p4` FOREIGN KEY (`passenger_id`) REFERENCES `passengers` (`passport`),
  CONSTRAINT `FK_o7kinaw7xwheynwhqp2goy353` FOREIGN KEY (`timetable_id`) REFERENCES `time_table` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tickets`
--

LOCK TABLES `tickets` WRITE;
/*!40000 ALTER TABLE `tickets` DISABLE KEYS */;
INSERT INTO `tickets` VALUES (1,NULL,12345,2),(2,NULL,566754,3);
/*!40000 ALTER TABLE `tickets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `time_table`
--

DROP TABLE IF EXISTS `time_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `time_table` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `free_seats` int(11) DEFAULT NULL,
  `path` bigint(20) DEFAULT NULL,
  `train_number` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_s0wbiyu3mvebogo77tj4y3h8j` (`path`),
  KEY `FK_m0ql5rwjdt0p210x6s53jhli0` (`train_number`),
  CONSTRAINT `FK_m0ql5rwjdt0p210x6s53jhli0` FOREIGN KEY (`train_number`) REFERENCES `trains` (`number`),
  CONSTRAINT `FK_s0wbiyu3mvebogo77tj4y3h8j` FOREIGN KEY (`path`) REFERENCES `paths` (`number`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `time_table`
--

LOCK TABLES `time_table` WRITE;
/*!40000 ALTER TABLE `time_table` DISABLE KEYS */;
INSERT INTO `time_table` VALUES (1,'2014-04-20',100,1,1),(2,'2014-04-22',99,1,1),(3,'2014-04-22',199,2,2),(4,'2014-04-21',200,2,2);
/*!40000 ALTER TABLE `time_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `train_types`
--

DROP TABLE IF EXISTS `train_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `train_types` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cost_multiplier` float DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `train_types`
--

LOCK TABLES `train_types` WRITE;
/*!40000 ALTER TABLE `train_types` DISABLE KEYS */;
INSERT INTO `train_types` VALUES (1,1,'normal'),(2,1.4,'fast');
/*!40000 ALTER TABLE `train_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trains`
--

DROP TABLE IF EXISTS `trains`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trains` (
  `number` bigint(20) NOT NULL AUTO_INCREMENT,
  `max_seat` int(11) DEFAULT NULL,
  `type` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`number`),
  KEY `FK_5cctbtwr2nkf5gd0g4fosd7jy` (`type`),
  CONSTRAINT `FK_5cctbtwr2nkf5gd0g4fosd7jy` FOREIGN KEY (`type`) REFERENCES `train_types` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trains`
--

LOCK TABLES `trains` WRITE;
/*!40000 ALTER TABLE `trains` DISABLE KEYS */;
INSERT INTO `trains` VALUES (1,100,1),(2,200,2);
/*!40000 ALTER TABLE `trains` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-04-11  1:10:06
