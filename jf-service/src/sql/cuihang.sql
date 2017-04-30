CREATE DATABASE  IF NOT EXISTS `jfzy` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `jfzy`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: localhost    Database: jfzy
-- ------------------------------------------------------
-- Server version	5.6.15

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
-- Table structure for table `jf_article`
--

DROP TABLE IF EXISTS `jf_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jf_article` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(500) NOT NULL DEFAULT '',
  `content` longtext,
  `tags` varchar(1000) NOT NULL DEFAULT '',
  `type` tinyint(1) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `title_img_url` varchar(2000) DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jf_article`
--

LOCK TABLES `jf_article` WRITE;
/*!40000 ALTER TABLE `jf_article` DISABLE KEYS */;
/*!40000 ALTER TABLE `jf_article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jf_order`
--

DROP TABLE IF EXISTS `jf_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jf_order` (
  `id` int(11) NOT NULL,
  `order_detai_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `lawyer_id` int(11) DEFAULT NULL,
  `processor_id` int(11) DEFAULT NULL,
  `product_id` int(11) NOT NULL,
  `origin_price` double NOT NULL,
  `real_price` double NOT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `start_time` timestamp NULL DEFAULT NULL,
  `end_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `state` int(1) DEFAULT NULL,
  `pay_way` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jf_order`
--

LOCK TABLES `jf_order` WRITE;
/*!40000 ALTER TABLE `jf_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `jf_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jf_order_detail`
--

DROP TABLE IF EXISTS `jf_order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jf_order_detail` (
  `id` int(11) NOT NULL,
  `type` tinyint(1) NOT NULL,
  `customer_role` tinyint(1) NOT NULL,
  `deal_state` tinyint(2) NOT NULL,
  `comment` varchar(45) DEFAULT NULL,
  `owner_name` varchar(45) DEFAULT NULL,
  `house_address` varchar(225) DEFAULT NULL,
  `house_city_id` int(11) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jf_order_detail`
--

LOCK TABLES `jf_order_detail` WRITE;
/*!40000 ALTER TABLE `jf_order_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `jf_order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jf_order_photos`
--

DROP TABLE IF EXISTS `jf_order_photos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jf_order_photos` (
  `id` int(11) NOT NULL,
  `order_detail_id` int(11) NOT NULL,
  `photo_path` varchar(2000) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jf_order_photos`
--

LOCK TABLES `jf_order_photos` WRITE;
/*!40000 ALTER TABLE `jf_order_photos` DISABLE KEYS */;
/*!40000 ALTER TABLE `jf_order_photos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jf_product`
--

DROP TABLE IF EXISTS `jf_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jf_product` (
  `id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `price` double NOT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jf_product`
--

LOCK TABLES `jf_product` WRITE;
/*!40000 ALTER TABLE `jf_product` DISABLE KEYS */;
/*!40000 ALTER TABLE `jf_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jf_tag`
--

DROP TABLE IF EXISTS `jf_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jf_tag` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL DEFAULT '',
  `weight` int(11) NOT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jf_tag`
--

LOCK TABLES `jf_tag` WRITE;
/*!40000 ALTER TABLE `jf_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `jf_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jf_user`
--

DROP TABLE IF EXISTS `jf_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jf_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `real_name` varchar(100) DEFAULT NULL,
  `memo` varchar(500) DEFAULT NULL,
  `address` varchar(2000) DEFAULT NULL,
  `postcode` varchar(10) DEFAULT NULL,
  `status` tinyint(2) NOT NULL,
  `head_img` varchar(2000) DEFAULT NULL,
  `open_id` varchar(45) DEFAULT NULL,
  `union_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jf_user`
--

LOCK TABLES `jf_user` WRITE;
/*!40000 ALTER TABLE `jf_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `jf_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jf_user_account`
--

DROP TABLE IF EXISTS `jf_user_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jf_user_account` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `role` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `wealth` int(11) DEFAULT '0',
  `finished_task` int(3) DEFAULT '0',
  `new_task` int(3) DEFAULT '0',
  `score` double DEFAULT '0',
  `money` double DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='比如两个微信号，那么就是两个user对应同一个user_account';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jf_user_account`
--

LOCK TABLES `jf_user_account` WRITE;
/*!40000 ALTER TABLE `jf_user_account` DISABLE KEYS */;
/*!40000 ALTER TABLE `jf_user_account` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-05-01  2:00:41
