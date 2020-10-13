-- MySQL dump 10.13  Distrib 8.0.21, for Linux (x86_64)
--
-- Host: localhost    Database: Shop
-- ------------------------------------------------------
-- Server version	8.0.21-0ubuntu0.20.04.4

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `Shop`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `Shop` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `Shop`;

--
-- Table structure for table `articles`
--

DROP TABLE IF EXISTS `articles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `articles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` text,
  `price` mediumtext,
  `disabled` tinyint(1) DEFAULT NULL,
  `description` text,
  `image` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articles`
--

LOCK TABLES `articles` WRITE;
/*!40000 ALTER TABLE `articles` DISABLE KEYS */;
INSERT INTO `articles` VALUES (1,'34343Computer','10.02',0,'HighTech Computer ;D423424<br>',3),(2,'Computer','10.00',0,'HighTech Computer ;D<br>',1),(16,'new Art','10001.00',0,'new Artwadwadawdawawdw241256214',1),(17,'new Art','0.00',0,'new Art',1),(18,'new Art','0.00',0,'new Art',1);
/*!40000 ALTER TABLE `articles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bills`
--

DROP TABLE IF EXISTS `bills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bills` (
  `id` int NOT NULL AUTO_INCREMENT,
  `text` text,
  `customerId` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bills`
--

LOCK TABLES `bills` WRITE;
/*!40000 ALTER TABLE `bills` DISABLE KEYS */;
INSERT INTO `bills` VALUES (1,'test',44);
/*!40000 ALTER TABLE `bills` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `images`
--

DROP TABLE IF EXISTS `images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `images` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` text,
  `data` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `images`
--

LOCK TABLES `images` WRITE;
/*!40000 ALTER TABLE `images` DISABLE KEYS */;
INSERT INTO `images` VALUES (1,'OSKF','data:image/png;base64,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'),(2,'Server 1HE INTEL','https://external-content.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.intel.com%2Fcontent%2Fdam%2Fwww%2Fpublic%2Fus%2Fen%2Fimages%2Fproduct%2Fserver-system-r2312sc2shgr-front-lg.jpg&f=1&nofb=1'),(3,'Server 1HE','https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.brentford.com%2Fmedia%2Fcatalog%2Fproduct%2Fcache%2F1%2Fimage%2F650x%2F9df78eab33525d08d6e5fb8d27136e95%2Fa%2Fs%2Fas_-1114s-wtrt_front_1.gif&f=1&nofb=1'),(11,'Iron Ingot','https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.jAdeOsatris6TYjb88cdgQAAAA%26pid%3DApi&f=1'),(12,'new','content');
/*!40000 ALTER TABLE `images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userid` int DEFAULT NULL,
  `shoppingCart` text,
  `createdate` date DEFAULT NULL,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (12,53,'{\"shoppingCartItems\":[{\"article\":{\"id\":1,\"name\":\"test\",\"price\":12.22,\"description\":\"erster Artikel\\u003cbr\\u003e\",\"disabled\":false},\"amount\":2}],\"orderSendShown\":false,\"orderSend\":-1}','2020-09-17',2),(13,52,'{\"shoppingCartItems\":[{\"article\":{\"id\":4,\"name\":\"new Art\",\"price\":10.0,\"description\":\"test\",\"disabled\":false},\"amount\":2}],\"orderSendShown\":false,\"orderSend\":-1}','2020-09-17',2),(14,52,'{\"shoppingCartItems\":[{\"article\":{\"id\":4,\"name\":\"new Art\",\"price\":10.0,\"description\":\"test\",\"disabled\":false},\"amount\":5}],\"orderSendShown\":false,\"orderSend\":-1}','2020-09-17',1),(15,2,'{\"shoppingCartItems\":[{\"article\":{\"id\":4,\"name\":\"new Art\",\"price\":10.0,\"description\":\"test\",\"disabled\":false},\"amount\":3},{\"article\":{\"id\":2,\"name\":\"Computer\",\"price\":12.02,\"description\":\"HighTech Computer ;D\\u003cbr\\u003e\",\"disabled\":false},\"amount\":2},{\"article\":{\"id\":1,\"name\":\"test\",\"price\":12.22,\"description\":\"erster Artikel\\u003cbr\\u003e\",\"disabled\":false},\"amount\":1}],\"orderSend\":-1}','2020-09-17',1),(16,1,'{\"shoppingCartItems\":[{\"article\":{\"id\":16,\"name\":\"new Art\",\"price\":10001,\"description\":\"new Art\",\"disabled\":false},\"amount\":1},{\"article\":{\"id\":2,\"name\":\"Computer\",\"price\":10.0,\"description\":\"HighTech Computer ;D\\u003cbr\\u003e\",\"disabled\":false},\"amount\":1},{\"article\":{\"id\":1,\"name\":\"Computer\",\"price\":10.029,\"description\":\"HighTech Computer ;D\\u003cbr\\u003e\",\"disabled\":false},\"amount\":1}]}','2020-09-17',1),(17,1,'{\"shoppingCartItems\":[{\"article\":{\"id\":16,\"name\":\"new Art\",\"price\":10001,\"description\":\"new Art\",\"disabled\":false},\"amount\":1},{\"article\":{\"id\":2,\"name\":\"Computer\",\"price\":10.0,\"description\":\"HighTech Computer ;D\\u003cbr\\u003e\",\"disabled\":false},\"amount\":1},{\"article\":{\"id\":1,\"name\":\"Computer\",\"price\":10.029,\"description\":\"HighTech Computer ;D\\u003cbr\\u003e\",\"disabled\":false},\"amount\":1}],\"orderSend\":0}','2020-09-17',1),(18,1,'{\"shoppingCartItems\":[{\"article\":{\"id\":1,\"name\":\"Computer\",\"price\":10.029,\"description\":\"HighTech Computer ;D\\u003cbr\\u003e\",\"disabled\":false},\"amount\":9},{\"article\":{\"id\":16,\"name\":\"new Art\",\"price\":10001,\"description\":\"new Art\",\"disabled\":false},\"amount\":5}],\"orderSend\":-1}','2020-09-17',1),(19,1,'{\"shoppingCartItems\":[{\"article\":{\"id\":2,\"name\":\"Computer\",\"price\":10.0,\"description\":\"HighTech Computer ;D\\u003cbr\\u003e\",\"disabled\":false},\"amount\":3}],\"orderSend\":-1}','2020-09-17',1),(20,1,'{\"shoppingCartItems\":[{\"article\":{\"id\":1,\"name\":\"Computert23232\",\"price\":10.03,\"description\":\"HighTech Computer ;D\\u003cbr\\u003e\",\"disabled\":false},\"amount\":2}],\"orderSend\":-1}','2020-09-20',1),(21,1,'{\"shoppingCartItems\":[{\"article\":{\"id\":2,\"name\":\"Computer\",\"price\":10.0,\"description\":\"HighTech Computer ;D\\u003cbr\\u003e\",\"disabled\":false},\"amount\":1}],\"orderSend\":-1}','2020-09-20',4),(22,2,'{\"shoppingCartItems\":[{\"article\":{\"id\":1,\"name\":\"34343Computer\",\"price\":10.02,\"description\":\"HighTech Computer ;D423424\\u003cbr\\u003e\",\"disabled\":false},\"amount\":1}],\"orderSend\":-1}','2020-09-20',1),(23,2,'{\"shoppingCartItems\":[{\"article\":{\"id\":2,\"name\":\"Computer\",\"price\":10.0,\"description\":\"HighTech Computer ;D\\u003cbr\\u003e\",\"disabled\":false},\"amount\":2}],\"orderSend\":1}','2020-09-20',1),(24,2,'{\"shoppingCartItems\":[{\"article\":{\"id\":1,\"name\":\"34343Computer\",\"price\":10.02,\"description\":\"HighTech Computer ;D423424\\u003cbr\\u003e\",\"disabled\":false},\"amount\":2}],\"orderSend\":1}','2020-09-20',1),(25,2,'{\"shoppingCartItems\":[{\"article\":{\"id\":1,\"name\":\"34343Computer\",\"price\":10.02,\"description\":\"HighTech Computer ;D423424\\u003cbr\\u003e\",\"disabled\":false},\"amount\":2}],\"orderSend\":1}','2020-09-20',1),(26,2,'{\"shoppingCartItems\":[{\"article\":{\"id\":1,\"name\":\"34343Computer\",\"price\":10.02,\"description\":\"HighTech Computer ;D423424\\u003cbr\\u003e\",\"disabled\":false},\"amount\":2}],\"orderSend\":-1}','2020-09-20',1),(27,2,'{\"shoppingCartItems\":[{\"article\":{\"id\":1,\"name\":\"34343Computer\",\"price\":10.02,\"description\":\"HighTech Computer ;D423424\\u003cbr\\u003e\",\"disabled\":false},\"amount\":3}],\"orderSendShown\":false,\"orderSend\":-1}','2020-09-20',1),(28,2,'{\"shoppingCartItems\":[{\"article\":{\"id\":1,\"name\":\"34343Computer\",\"price\":10.02,\"description\":\"HighTech Computer ;D423424\\u003cbr\\u003e\",\"disabled\":false},\"amount\":3}],\"orderSendShown\":false,\"orderSend\":-1}','2020-09-20',1),(29,2,'{\"shoppingCartItems\":[{\"article\":{\"id\":1,\"name\":\"34343Computer\",\"price\":10.02,\"description\":\"HighTech Computer ;D423424\\u003cbr\\u003e\",\"disabled\":false},\"amount\":3}],\"orderSendShown\":false,\"orderSend\":-1}','2020-09-20',1),(30,2,'{\"shoppingCartItems\":[{\"article\":{\"id\":1,\"name\":\"34343Computer\",\"price\":10.02,\"description\":\"HighTech Computer ;D423424\\u003cbr\\u003e\",\"disabled\":false},\"amount\":3}],\"orderSendShown\":false,\"orderSend\":-1}','2020-09-20',1),(31,2,'{\"shoppingCartItems\":[{\"article\":{\"id\":1,\"name\":\"34343Computer\",\"price\":10.02,\"description\":\"HighTech Computer ;D423424\\u003cbr\\u003e\",\"disabled\":false},\"amount\":1}],\"orderSendShown\":false,\"orderSend\":-1}','2020-09-20',1),(32,2,'{\"shoppingCartItems\":[{\"article\":{\"id\":16,\"name\":\"new Art\",\"price\":10001.0,\"description\":\"new Artwadwadawdawawdw241256214\",\"disabled\":false},\"amount\":1}],\"orderSendShown\":false,\"orderSend\":-1}','2020-09-20',1),(33,2,'{\"shoppingCartItems\":[{\"article\":{\"id\":1,\"name\":\"34343Computer\",\"price\":10.02,\"description\":\"HighTech Computer ;D423424\\u003cbr\\u003e\",\"disabled\":false},\"amount\":1}],\"orderSendShown\":false,\"orderSend\":-1}','2020-09-20',1),(34,2,'{\"shoppingCartItems\":[{\"article\":{\"id\":1,\"name\":\"34343Computer\",\"price\":10.02,\"description\":\"HighTech Computer ;D423424\\u003cbr\\u003e\",\"disabled\":false},\"amount\":3}],\"orderSendShown\":false,\"orderSend\":-1}','2020-09-20',1),(35,2,'{\"shoppingCartItems\":[{\"article\":{\"id\":1,\"name\":\"34343Computer\",\"price\":10.02,\"description\":\"HighTech Computer ;D423424\\u003cbr\\u003e\",\"disabled\":false},\"amount\":2}],\"orderSendShown\":false,\"orderSend\":-1}','2020-09-20',1),(36,2,'{\"shoppingCartItems\":[{\"article\":{\"id\":2,\"name\":\"Computer\",\"price\":10.0,\"description\":\"HighTech Computer ;D\\u003cbr\\u003e\",\"disabled\":false},\"amount\":1}],\"orderSendShown\":false,\"orderSend\":-1}','2020-09-20',1),(37,2,'{\"shoppingCartItems\":[{\"article\":{\"id\":2,\"name\":\"Computer\",\"price\":10.0,\"description\":\"HighTech Computer ;D\\u003cbr\\u003e\",\"disabled\":false},\"amount\":1}],\"orderSendShown\":false,\"orderSend\":-1}','2020-09-20',1),(38,2,'{\"shoppingCartItems\":[{\"article\":{\"id\":16,\"name\":\"new Art\",\"price\":10001.0,\"description\":\"new Artwadwadawdawawdw241256214\",\"disabled\":false},\"amount\":1}],\"orderSendShown\":false,\"orderSend\":-1}','2020-09-20',1),(39,2,'{\"shoppingCartItems\":[{\"article\":{\"id\":17,\"name\":\"new Art\",\"price\":0.0,\"description\":\"new Art\",\"disabled\":false},\"amount\":1},{\"article\":{\"id\":16,\"name\":\"new Art\",\"price\":10001.0,\"description\":\"new Artwadwadawdawawdw241256214\",\"disabled\":false},\"amount\":1}],\"orderSendShown\":false,\"orderSend\":-1}','2020-09-20',1),(40,2,'{\"shoppingCartItems\":[{\"article\":{\"id\":1,\"name\":\"34343Computer\",\"price\":10.02,\"description\":\"HighTech Computer ;D423424\\u003cbr\\u003e\",\"disabled\":false},\"amount\":1}],\"orderSendShown\":false,\"orderSend\":-1}','2020-09-20',1);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shopsettings`
--

DROP TABLE IF EXISTS `shopsettings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shopsettings` (
  `id` int NOT NULL AUTO_INCREMENT,
  `settingname` text,
  `settingcontent` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shopsettings`
--

LOCK TABLES `shopsettings` WRITE;
/*!40000 ALTER TABLE `shopsettings` DISABLE KEYS */;
INSERT INTO `shopsettings` VALUES (1,'name','Karims Shop');
/*!40000 ALTER TABLE `shopsettings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` text,
  `password` text,
  `isadmin` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (52,'admin','$2a$12$JeTAdk6xlLGbxK.h3VLHq.sHH7GGSDO/g4E4BGnivj5vlPdefzD7e',1),(53,'test','$2a$12$TCqe8iyPHx0.c2Pbs2OnG.Mrxt.5DkcF298ZnWO2/dlHhGoAmuOzq',0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-09-27  1:36:08
