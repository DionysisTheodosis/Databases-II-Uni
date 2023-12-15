CREATE DATABASE  IF NOT EXISTS `attractiondb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `attractiondb`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: attractiondb
-- ------------------------------------------------------
-- Server version	8.0.35

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `area`
--

DROP TABLE IF EXISTS `area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `area` (
  `areaName` varchar(60) COLLATE utf8mb4_unicode_ci NOT NULL,
  `regionName` varchar(60) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`areaName`),
  KEY `FK_Region_Area` (`regionName`),
  CONSTRAINT `FK_Region_Area` FOREIGN KEY (`regionName`) REFERENCES `region` (`regionName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `area`
--

LOCK TABLES `area` WRITE;
/*!40000 ALTER TABLE `area` DISABLE KEYS */;
INSERT INTO `area` VALUES ('Άρτα','Άρτα'),('Αθήνα','Αττική'),('Αναφωνήτρια','Ζακυνθος'),('Καρλόβασι','Σάμος'),('Ποσείδι','Χαλκιδική');
/*!40000 ALTER TABLE `area` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attraction`
--

DROP TABLE IF EXISTS `attraction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attraction` (
  `attractionName` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `entrance_fee` decimal(10,2) NOT NULL DEFAULT '0.00',
  `website` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `attractionType` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `attr_profilePic` int DEFAULT NULL,
  `areaName` varchar(60) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`attractionName`),
  KEY `FK_Attraction_Picture` (`attr_profilePic`),
  KEY `FK_Attraction_Type` (`attractionType`),
  KEY `FK_Attraction_Area` (`areaName`),
  CONSTRAINT `FK_Attraction_Area` FOREIGN KEY (`areaName`) REFERENCES `area` (`areaName`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Attraction_Picture` FOREIGN KEY (`attr_profilePic`) REFERENCES `picture` (`pictureID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Attraction_Type` FOREIGN KEY (`attractionType`) REFERENCES `attraction_type` (`typeName`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `attraction_chk_1` CHECK ((`entrance_fee` >= 0.0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attraction`
--

LOCK TABLES `attraction` WRITE;
/*!40000 ALTER TABLE `attraction` DISABLE KEYS */;
INSERT INTO `attraction` VALUES ('Γεφύρι της Άρτας',0.00,'https://discoverarta.gr/en/destinations/historical-bridge-of-arta/','Αρχαία Ερείπια - Μνημεία',14,'Άρτα'),('Μικρό Σεϊτάνι',0.00,NULL,'Παραλίες',13,'Καρλόβασι'),('Μουσείο Ακρόπολης',15.00,'https://www.theacropolismuseum.gr/','Μουσεία',16,'Αθήνα'),('Ναυάγιο',0.00,NULL,'Παραλίες',12,'Αναφωνήτρια'),('Παραλία Ποσείδι',0.00,'https://gohalkidiki.com/posidi/','Παραλίες',11,'Ποσείδι'),('Παρθενώνας',20.00,'http://odysseus.culture.gr/h/3/eh355.jsp?obj_id=2384','Αρχαία Ερείπια - Μνημεία',15,'Αθήνα');
/*!40000 ALTER TABLE `attraction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attraction_type`
--

DROP TABLE IF EXISTS `attraction_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attraction_type` (
  `typeName` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `attrTypeImage` int DEFAULT NULL,
  PRIMARY KEY (`typeName`),
  KEY `FK_AtrrType_Image` (`attrTypeImage`),
  CONSTRAINT `FK_AtrrType_Image` FOREIGN KEY (`attrTypeImage`) REFERENCES `picture` (`pictureID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attraction_type`
--

LOCK TABLES `attraction_type` WRITE;
/*!40000 ALTER TABLE `attraction_type` DISABLE KEYS */;
INSERT INTO `attraction_type` VALUES ('Κάστρα',1),('Αρχαία Ερείπια - Μνημεία',2),('Θρησκευτικές Τοποθεσίες',3),('Παραλίες',4),('Μουσεία',5);
/*!40000 ALTER TABLE `attraction_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `country` (
  `countryName` varchar(60) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`countryName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `country`
--

LOCK TABLES `country` WRITE;
/*!40000 ALTER TABLE `country` DISABLE KEYS */;
INSERT INTO `country` VALUES ('Αγγλία'),('Γαλλία'),('Ελλάδα'),('Ισπανία'),('Ιταλία');
/*!40000 ALTER TABLE `country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `day`
--

DROP TABLE IF EXISTS `day`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `day` (
  `dayName` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`dayName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `day`
--

LOCK TABLES `day` WRITE;
/*!40000 ALTER TABLE `day` DISABLE KEYS */;
INSERT INTO `day` VALUES ('Δευτέρα'),('Κυριακή'),('Παρασκευή'),('Πέμπτη'),('Σάββατο'),('Τετάρτη'),('Τρίτη');
/*!40000 ALTER TABLE `day` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `email_attraction`
--

DROP TABLE IF EXISTS `email_attraction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `email_attraction` (
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `attractionName` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`email`),
  KEY `FK_Email_Attraction` (`attractionName`),
  CONSTRAINT `FK_Email_Attraction` FOREIGN KEY (`attractionName`) REFERENCES `attraction` (`attractionName`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `email_attraction_chk_1` CHECK (regexp_like(`email`,_utf8mb4'^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,7}$'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `email_attraction`
--

LOCK TABLES `email_attraction` WRITE;
/*!40000 ALTER TABLE `email_attraction` DISABLE KEYS */;
INSERT INTO `email_attraction` VALUES ('info@theacropolismuseum.gr','Μουσείο Ακρόπολης'),('efaath@culture.gr','Παρθενώνας');
/*!40000 ALTER TABLE `email_attraction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `map`
--

DROP TABLE IF EXISTS `map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `map` (
  `mapID` int NOT NULL AUTO_INCREMENT,
  `embeded_Url` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `attractionName` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`mapID`),
  KEY `FK_Atrraction_Map` (`attractionName`),
  CONSTRAINT `FK_Atrraction_Map` FOREIGN KEY (`attractionName`) REFERENCES `attraction` (`attractionName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `map`
--

LOCK TABLES `map` WRITE;
/*!40000 ALTER TABLE `map` DISABLE KEYS */;
INSERT INTO `map` VALUES (1,'<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d12232.271429195527!2d23.364030374643736!3d39.96223554094451!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x14a7cd5639a3ffb3%3A0x9b6e699e60f9d27d!2zzqDOsc-BzrHOu86vzrEgzqDOv8-DzrXOr860zrk!5e0!3m2!1sel!2sgr!4v1702301764080!5m2!1sel!2sgr\" width=\"400\" height=\"300\"style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\" referrerpolicy=\"no-referrer-when-downgrade\"></iframe>','Παραλία Ποσείδι'),(2,'<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d1113.7105495663247!2d20.624052963392906!3d37.85914090282156!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x13676b8cc2540ae1%3A0x783ee88b75588aee!2zzqDOsc-BzrHOu86vzrEgzp3Osc-FzqzOs865zr8!5e0!3m2!1sel!2sgr!4v1702304061791!5m2!1sel!2sgr\" width=\"400\" height=\"300\" style=\"border:0;\"allowfullscreen=\"\" loading=\"lazy\" referrerpolicy=\"no-referrer-when-downgrade\"></iframe>','Ναυάγιο'),(3,'<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d12614.229464046783!2d26.627311148149516!3d37.77697575638339!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x14bc42f0f4bdc5cf%3A0xcef8bb339eea4db7!2zzqDOsc-BzrHOu86vzrEgzpzOuc66z4HPjCDOo861z4rPhM6szr3OuQ!5e0!3m2!1sel!2sgr!4v1702304448417!5m2!1sel!2sgr\" width=\"400\" height=\"300\"style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\" referrerpolicy=\"no-referrer-when-downgrade\"></iframe>','Μικρό Σεϊτάνι'),(4,'<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3094.0102749947605!2d20.972010576646873!3d39.151747931655215!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x135c1b0ccdf0ec05%3A0xb00b80b525db12cf!2zzpPOtc-Gz43Pgc65IM-EzrfPgiDOhs-Bz4TOsc-C!5e0!3m2!1sel!2sgr!4v1702302526382!5m2!1sel!2sgr\" width=\"400\" height=\"300\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\" referrerpolicy=\"no-referrer-when-downgrade\"></iframe>','Γεφύρι της Άρτας'),(5,'<iframe src=\"https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3145.2397997151447!2d23.72414167660854!3d37.971532700800495!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x14a1bd19ca39ee61%3A0x1b3fa079b878a218!2zzqDOsc-BzrjOtc69z47Ovc6xz4I!5e0!3m2!1sel!2sgr!4v1702303058409!5m2!1sel!2sgr\" width=\"400\" height=\"300\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\" referrerpolicy=\"no-referrer-when-downgrade\"></iframe>','Παρθενώνας'),(6,'<iframe src=\"https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d12581.48750617742!2d23.7285227!3d37.9684499!3m2!1i1024!2i768\n!4f13.1!3m3!1m2!1s0x14a1bd173f46e4e1%3A0xb00fb46a2c010a3c!2zzpzOv8-Fz4POtc6vzr8gzpHOus-Bz4zPgM6_zrvOt8-C!5e0!3m2!1sel!2sgr!4v170\n2303728375!5m2!1sel!2sgr\" width=\"400\" height=\"300\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\" referrerpolicy=\"no-referrer\n-when-downgrade\"></iframe>','Μουσείο Ακρόπολης');
/*!40000 ALTER TABLE `map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `operation_time`
--

DROP TABLE IF EXISTS `operation_time`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `operation_time` (
  `open` time DEFAULT NULL,
  `close` time DEFAULT NULL,
  `attractionName` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `dayName` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`dayName`,`attractionName`),
  KEY `FK_Operates_Attraction` (`attractionName`),
  CONSTRAINT `FK_Operates_Attraction` FOREIGN KEY (`attractionName`) REFERENCES `attraction` (`attractionName`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Operates_Day` FOREIGN KEY (`dayName`) REFERENCES `day` (`dayName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operation_time`
--

LOCK TABLES `operation_time` WRITE;
/*!40000 ALTER TABLE `operation_time` DISABLE KEYS */;
INSERT INTO `operation_time` VALUES ('09:00:00','17:00:00','Μουσείο Ακρόπολης','Δευτέρα'),('06:00:00','06:00:00','Παρθενώνας','Δευτέρα'),('09:00:00','20:00:00','Μουσείο Ακρόπολης','Κυριακή'),('06:00:00','06:00:00','Παρθενώνας','Κυριακή'),('09:00:00','22:00:00','Μουσείο Ακρόπολης','Παρασκευή'),('06:00:00','06:00:00','Παρθενώνας','Παρασκευή'),('09:00:00','17:00:00','Μουσείο Ακρόπολης','Πέμπτη'),('06:00:00','06:00:00','Παρθενώνας','Πέμπτη'),('09:00:00','20:00:00','Μουσείο Ακρόπολης','Σάββατο'),('06:00:00','06:00:00','Παρθενώνας','Σάββατο'),('09:00:00','17:00:00','Μουσείο Ακρόπολης','Τετάρτη'),('06:00:00','06:00:00','Παρθενώνας','Τετάρτη'),('09:00:00','17:00:00','Μουσείο Ακρόπολης','Τρίτη'),('06:00:00','06:00:00','Παρθενώνας','Τρίτη');
/*!40000 ALTER TABLE `operation_time` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `picture`
--

DROP TABLE IF EXISTS `picture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `picture` (
  `pictureID` int NOT NULL AUTO_INCREMENT,
  `picture_url` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `attractionName` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `username` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`pictureID`),
  KEY `FK_Picture_Review` (`attractionName`,`username`),
  CONSTRAINT `FK_Picture_Review` FOREIGN KEY (`attractionName`, `username`) REFERENCES `review` (`attractionName`, `username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `picture`
--

LOCK TABLES `picture` WRITE;
/*!40000 ALTER TABLE `picture` DISABLE KEYS */;
INSERT INTO `picture` VALUES (1,'Photos\\attractionType\\Κάστρα.jpg',NULL,NULL),(2,'Photos\\attractionType\\ΑρχαίαΕρείπια.jpg',NULL,NULL),(3,'Photos\\attractionType\\ΘρησκευτικέςΤοποθεσίες.jpg',NULL,NULL),(4,'Photos\\attractionType\\Παραλίες.jpg',NULL,NULL),(5,'Photos\\attractionType\\Μουσεία.jpg',NULL,NULL),(6,'Photos\\user\\user1.jpg',NULL,NULL),(7,'Photos\\user\\user3.jpg',NULL,NULL),(8,'Photos\\user\\user2jpg',NULL,NULL),(9,'Photos\\user\\user4.jpg',NULL,NULL),(10,'Photos\\user\\user5.jpg',NULL,NULL),(11,'Photos\\attraction photos\\Ποσείδι.jpg',NULL,NULL),(12,'Photos\\attraction photos\\Ναυάγιο Ζάκυνθος.jpg',NULL,NULL),(13,'Photos\\attraction photos\\Μικρό σεϊτάνι.jpg',NULL,NULL),(14,'Photos\\attraction photos\\ΓεφύριΤηςΆρτας.jpg',NULL,NULL),(15,'Photos\\attraction photos\\Παρθενώνας.jpg',NULL,NULL),(16,'Photos\\attraction photos\\Μουσείο Ακρόπολης.jpg',NULL,NULL),(17,'Photos\\reviewPhotos\\Παρθενώνας1.jpg','Παρθενώνας','user2'),(18,'Photos\\reviewPhotos\\Παρθενώνας2.jpg','Παρθενώνας','user2'),(19,'Photos\\reviewPhotos\\Γεφύρι της Άρτας1.jpg','Γεφύρι της Άρτας','user2'),(20,'Photos\\reviewPhotos\\Γεφύρι της Άρτας2.jpg','Γεφύρι της Άρτας','user2'),(21,'Photos\\reviewPhotos\\Παραλία Ποσείδι1.jpg','Παραλία Ποσείδι','user4'),(22,'Photos\\reviewPhotos\\Παραλία Ποσείδι2.jpg','Παραλία Ποσείδι','user5'),(23,'Photos\\reviewPhotos\\Παραλία Ναυάγιο1.jpg','Ναυάγιο','user4'),(24,'Photos\\reviewPhotos\\Μουσείο Ακρόπολης1.jpg','Μουσείο Ακρόπολης','user4'),(25,'Photos\\reviewPhotos\\Μουσείο Ακρόπολης2.jpg','Μουσείο Ακρόπολης','user3');
/*!40000 ALTER TABLE `picture` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `region`
--

DROP TABLE IF EXISTS `region`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `region` (
  `regionName` varchar(60) COLLATE utf8mb4_unicode_ci NOT NULL,
  `countryName` varchar(60) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`regionName`),
  KEY `FK_Region_Country` (`countryName`),
  CONSTRAINT `FK_Region_Country` FOREIGN KEY (`countryName`) REFERENCES `country` (`countryName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `region`
--

LOCK TABLES `region` WRITE;
/*!40000 ALTER TABLE `region` DISABLE KEYS */;
INSERT INTO `region` VALUES ('Άρτα','Ελλάδα'),('Αττική','Ελλάδα'),('Ζακυνθος','Ελλάδα'),('Σάμος','Ελλάδα'),('Χαλκιδική','Ελλάδα');
/*!40000 ALTER TABLE `region` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review` (
  `createdAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `comment` text COLLATE utf8mb4_unicode_ci,
  `rating` decimal(2,1) NOT NULL,
  `attractionName` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `username` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`username`,`attractionName`),
  KEY `FK_Attraction_Review` (`attractionName`),
  CONSTRAINT `FK_Attraction_Review` FOREIGN KEY (`attractionName`) REFERENCES `attraction` (`attractionName`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_User_Review` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `review_chk_1` CHECK ((`rating` in (0.5,1,1.5,2,2.5,3,3.5,4,4.5,5)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT INTO `review` VALUES ('2023-12-14 08:26:11',NULL,4.5,'Γεφύρι της Άρτας','user1'),('2023-12-14 08:26:11',NULL,4.5,'Παραλία Ποσείδι','user1'),('2023-12-14 08:26:11',NULL,4.5,'Παρθενώνας','user1'),('2023-12-14 08:26:11','Το γεφύρι της Άρτας δεσπόζει στον Άραχθο και είναι ορατό από τον περιφερειακό της\nπόλης.Είναι προσπελάσιμο και περπατήσιμο από το πλάι, μέσα από ένα πεζόδρομο. Μπορείτε\nνα βγάλετε φωτογραφία με μέτωπο 3/4 από το παρακείμενο cafe.Είναι μακρύ, εντυπωσιακό, \nκαι μπορείτε να το περπατήσετε και να νιώσετε όπως οι ταξιδιώτες του 17ου αιώνα, πάνω \nαπό τις καμάρες, στην λιθόστρωτη επιφάνεια.',5.0,'Γεφύρι της Άρτας','user2'),('2023-12-14 08:26:11',NULL,5.0,'Μουσείο Ακρόπολης','user2'),('2023-12-14 08:26:11','Ιστορία! Φανταστικό μέρος! Πρέπει να δείτε! Χαίρομαι που βλέπουμε την πρόοδο της\nανοικοδόμησης αυτών των όμορφων κτιρίων! Μια ανάμνηση ζωής για κάθε επισκέπτη! Συνιστάται \nιδιαίτερα να έρθετε και να δείτε αν βρίσκεστε στην Αθήνα. Αγοράστε το εισιτήριό σας ηλεκτρονικά\nκαι ελάτε νωρίς καθώς η ζέστη μπορεί να ανέβει ψηλά και το πλήθος του κόσμου',5.0,'Παρθενώνας','user2'),('2023-12-14 08:26:11','Έχω επισκεφτεί το μουσείο περισσότερες από 10 φορές! Κάθε φορά που φιλοξενώ κάποιον συγγενή\nθα τον πάω να του δείξω τις ομορφιές της αρχαίας Ελλάδας! Κάθε φορά γνωρίζω και κάτι που την προηγούμενη\nδεν είχα προσέξει! Είναι τόσο πλούσια η συλλογή που μια φορά δεν είναι αρκετή! Η θέα από την καφετέρια είναι \nμοναδική!',5.0,'Μουσείο Ακρόπολης','user3'),('2023-12-14 08:26:11',NULL,5.0,'Ναυάγιο','user3'),('2023-12-14 08:26:11','Ένα από τα πιο ωραία μουσεία που έχω επισκεφτεί, όχι μόνο για τα εκθέματα του,\nαλλά και ως χώρος. Απαραίτητη η επίσκεψη αφού πρώτα δει κανείς την Ακρόπολη των Αθηνών.\nΤο προσωπικό είναι πολύ εξυπηρετικό, ευγενέστατο και κατατοπιστικό.\nΟ χώρος λιτός, μοντέρνος, λειτουργικός και πολύ έξυπνα δομημένος, σου δίνει την ευκαιρία για ένα\nταξίδι στο χρόνο σχετικά με την Ακρόπολη και την Αθήνα γενικότερα.\nΑξίζει όσο τίποτα η επίσκεψη. Πραγματικά πρόκειται για ένα μουσείο αντάξιο του χώρου της Ακρόπολης\nπου εξυπηρετεί.',5.0,'Μουσείο Ακρόπολης','user4'),('2023-12-14 08:26:11','Αξίζει μια επίσκεψη με σκάφος για φωτογραφίες με background το ναυάγιο και\nκολύμπι στις μπλε σπηλιες! Προτείνω να γίνει την ώρα του ηλιοβασιλεματος αφού ο ήλιος\nδύει ακριβώς απέναντι από την παραλία του ναυαγίου',5.0,'Ναυάγιο','user4'),('2023-12-14 08:26:11',NULL,5.0,'Παραλία Ποσείδι','user4'),('2023-12-14 08:26:11',NULL,5.0,'Μουσείο Ακρόπολης','user5'),('2023-12-14 08:26:11',NULL,4.5,'Ναυάγιο','user5'),('2023-12-14 08:26:11','Πολύ όμορφη παραλία!Όποιος διαθέτει αμάξι τύπου τζιπ μπορεί να φθάσει αρκετά\nκοντά,κοντά στο φάρο. Εξοπλιστείτε με τα απαραίτητα και θα σας αποζημιώσει το περπάτημα.\n Εχει δυο πλευρές οπότε κάθεσαι ανάλογα με το που φυσάει. Βαθιά για μικρά παιδιά αλλά με\n τον απαραίτητο εξοπλισμό είναι μια ασφαλή επιλογή.',5.0,'Παραλία Ποσείδι','user5');
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `telephone_attraction`
--

DROP TABLE IF EXISTS `telephone_attraction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `telephone_attraction` (
  `telephone` char(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `attractionName` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`telephone`),
  KEY `FK_Telephone_Attraction` (`attractionName`),
  CONSTRAINT `FK_Telephone_Attraction` FOREIGN KEY (`attractionName`) REFERENCES `attraction` (`attractionName`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `telephone_attraction_chk_1` CHECK (regexp_like(`telephone`,_utf8mb4'^[0-9]+$'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `telephone_attraction`
--

LOCK TABLES `telephone_attraction` WRITE;
/*!40000 ALTER TABLE `telephone_attraction` DISABLE KEYS */;
INSERT INTO `telephone_attraction` VALUES ('2109000900','Μουσείο Ακρόπολης'),('2103214172','Παρθενώνας'),('2109238747','Παρθενώνας');
/*!40000 ALTER TABLE `telephone_attraction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `username` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `surname` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `lastname` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `userProfilePic` int DEFAULT NULL,
  PRIMARY KEY (`username`),
  KEY `FK_User_Picture` (`userProfilePic`),
  CONSTRAINT `FK_User_Picture` FOREIGN KEY (`userProfilePic`) REFERENCES `picture` (`pictureID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('user1','Αναστάσης','Πετρόπουλος','petropoulos@gmail.com',6),('user2','Μιχάλης','Γιαννακόπουλος','giannakopoulos@gmail.com',8),('user3','Βαγγέλης','Παπαδόπουλος','papadopoylos@gmail.com',7),('user4','Ελένη','Ιωάννου','ioannou@gmail.com',9),('user5','Βασιλική','Πέτρου','petrou@gmail.com',10);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'attractiondb'
--

--
-- Dumping routines for database 'attractiondb'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-15  9:32:08
