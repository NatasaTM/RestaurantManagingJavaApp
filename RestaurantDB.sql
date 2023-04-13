/*
SQLyog Community v13.1.9 (64 bit)
MySQL - 8.0.31 : Database - restaurantdb
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`restaurantdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_et_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `restaurantdb`;

/*Table structure for table `city` */

DROP TABLE IF EXISTS `city`;

CREATE TABLE `city` (
  `zipcode` bigint unsigned NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`zipcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `city` */

insert  into `city`(`zipcode`,`name`) values 
(11000,'Beograd'),
(11111,'Grad Novi'),
(18000,'Nis'),
(21000,'Novi Sad'),
(23000,'Zrenjanin'),
(23300,'Kikinda'),
(555555,'Neki grad');

/*Table structure for table `concretemenu` */

DROP TABLE IF EXISTS `concretemenu`;

CREATE TABLE `concretemenu` (
  `concreteMenuId` int unsigned NOT NULL AUTO_INCREMENT,
  `concreteMenuName` varchar(50) NOT NULL,
  `date` date NOT NULL,
  `isActive` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`concreteMenuId`),
  UNIQUE KEY `unique_concreteMenuName` (`concreteMenuName`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `concretemenu` */

insert  into `concretemenu`(`concreteMenuId`,`concreteMenuName`,`date`,`isActive`) values 
(1,'Jelovnik1','2023-03-03',0),
(3,'Jelovnik2','2022-05-05',1),
(6,'Jelovnik4','2023-03-04',1),
(19,'Jelovnik 26.03.2023.','2023-03-26',1);

/*Table structure for table `employee` */

DROP TABLE IF EXISTS `employee`;

CREATE TABLE `employee` (
  `employeeId` int unsigned NOT NULL AUTO_INCREMENT,
  `firstname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `lastname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `birthdate` date NOT NULL,
  `adress` varchar(100) NOT NULL,
  `cityZipcode` bigint unsigned NOT NULL,
  `jmbg` varchar(13) NOT NULL,
  PRIMARY KEY (`employeeId`),
  UNIQUE KEY `unique_jmbg` (`jmbg`),
  KEY `cityZipcode` (`cityZipcode`),
  CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`cityZipcode`) REFERENCES `city` (`zipcode`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `employee` */

insert  into `employee`(`employeeId`,`firstname`,`lastname`,`birthdate`,`adress`,`cityZipcode`,`jmbg`) values 
(1,'Pera','Peric','2002-02-24','Sarajevska 22',23000,'511997855028'),
(2,'Ana','Anic','1997-04-07','Bulevar Oslobodjenja 155',21000,'205999855023'),
(3,'Jelena','Jelic','1995-11-08','Brigadira Ristica 58',23000,'1212002850124'),
(4,'Ivan','Ivic','2003-05-12','Pere Dobrinovica 22',23000,'101997850326'),
(5,'Mirjana2','Milanovic','1995-11-23','BVV 111',11000,'0511980855028'),
(8,'Zika','Zikic','1998-08-11','Pere Perica 12',11000,'0255478599654');

/*Table structure for table `menucategory` */

DROP TABLE IF EXISTS `menucategory`;

CREATE TABLE `menucategory` (
  `categoryId` int unsigned NOT NULL AUTO_INCREMENT,
  `categoryName` varchar(50) NOT NULL,
  PRIMARY KEY (`categoryId`),
  UNIQUE KEY `unique_concreteCategoryName` (`categoryName`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `menucategory` */

insert  into `menucategory`(`categoryId`,`categoryName`) values 
(7,'ALKOHOLNA PICA'),
(6,'DEZERT'),
(1,'DORUCAK'),
(3,'GLAVNO JELO'),
(2,'PREDJELO'),
(5,'SOK'),
(4,'SUPE I CORBE');

/*Table structure for table `menuitem` */

DROP TABLE IF EXISTS `menuitem`;

CREATE TABLE `menuitem` (
  `menuItemId` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` varchar(300) NOT NULL,
  `price` decimal(10,0) unsigned NOT NULL,
  `categoryId` int unsigned NOT NULL,
  `menuItemType` enum('FOOD','DRINKS') NOT NULL,
  PRIMARY KEY (`menuItemId`),
  KEY `categoryId` (`categoryId`),
  CONSTRAINT `menuitem_ibfk_1` FOREIGN KEY (`categoryId`) REFERENCES `menucategory` (`categoryId`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `menuitem` */

insert  into `menuitem`(`menuItemId`,`name`,`description`,`price`,`categoryId`,`menuItemType`) values 
(1,'Palacinke sa borovnicama','Vanila krem, borovnice',500,1,'FOOD'),
(2,'Omlet','Tri jaja sa pavlakom, sirom i sunkom',600,1,'FOOD'),
(4,'Pileca Karadjordjeva Snicla','tartar sos i pomfrit',1290,3,'FOOD'),
(5,'Pecurke na zaru sa prilogom','pecurke ili pomfrit',740,2,'FOOD'),
(6,'Jaja sa sunkom','3 jaja, sunka, mladi sir,salata',380,1,'FOOD'),
(7,'Tost sendvic','sunka,sir,salata',350,1,'FOOD'),
(10,'Becka snicla1','pomfrit,salata',1250,1,'FOOD'),
(14,'Coca cola','0.33',200,5,'DRINKS'),
(15,'Neko novo jelo','neki opis jela',890,2,'FOOD');

/*Table structure for table `menuitem_concrete_menu` */

DROP TABLE IF EXISTS `menuitem_concrete_menu`;

CREATE TABLE `menuitem_concrete_menu` (
  `menuItemId` int unsigned NOT NULL,
  `concrete_menuId` int unsigned NOT NULL,
  PRIMARY KEY (`menuItemId`,`concrete_menuId`),
  KEY `menuitem_concrete_menu_ibfk_2` (`concrete_menuId`),
  CONSTRAINT `menuitem_concrete_menu_ibfk_1` FOREIGN KEY (`menuItemId`) REFERENCES `menuitem` (`menuItemId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `menuitem_concrete_menu_ibfk_2` FOREIGN KEY (`concrete_menuId`) REFERENCES `concretemenu` (`concreteMenuId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `menuitem_concrete_menu` */

insert  into `menuitem_concrete_menu`(`menuItemId`,`concrete_menuId`) values 
(1,1),
(2,1),
(5,1),
(10,1),
(2,3),
(4,3),
(5,3),
(6,3),
(7,3),
(10,3),
(1,6),
(2,6),
(6,6),
(7,6),
(1,19),
(2,19),
(4,19),
(5,19),
(6,19),
(7,19),
(10,19),
(14,19),
(15,19);

/*Table structure for table `order` */

DROP TABLE IF EXISTS `order`;

CREATE TABLE `order` (
  `orderId` bigint unsigned NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `totalAmount` decimal(10,0) unsigned NOT NULL DEFAULT '0',
  `orderReadyStatus` tinyint(1) NOT NULL DEFAULT '0',
  `orderPaidStatus` tinyint(1) NOT NULL DEFAULT '0',
  `employeeId` int unsigned DEFAULT NULL,
  `tableId` int unsigned NOT NULL,
  PRIMARY KEY (`orderId`),
  KEY `employeeId` (`employeeId`),
  KEY `c_tableId` (`tableId`),
  CONSTRAINT `c_tableId` FOREIGN KEY (`tableId`) REFERENCES `table` (`tableId`),
  CONSTRAINT `order_ibfk_1` FOREIGN KEY (`employeeId`) REFERENCES `employee` (`employeeId`)
) ENGINE=InnoDB AUTO_INCREMENT=125 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `order` */

insert  into `order`(`orderId`,`date`,`totalAmount`,`orderReadyStatus`,`orderPaidStatus`,`employeeId`,`tableId`) values 
(9,'2023-03-06 00:00:00',2800,0,0,1,1),
(10,'2023-03-06 12:44:06',2800,0,0,1,1),
(11,'2023-03-06 12:54:23',2800,0,0,1,1),
(12,'2023-03-06 21:04:50',2860,0,0,2,1),
(13,'2023-03-06 21:12:23',1760,0,0,2,1),
(14,'2023-03-06 21:12:42',1760,0,0,2,1),
(15,'2023-03-06 21:12:56',1760,0,0,2,1),
(16,'2023-03-06 21:22:05',1300,0,0,2,1),
(17,'2023-03-06 21:52:33',3400,0,0,2,1),
(18,'2023-03-06 21:52:46',3400,0,0,2,1),
(19,'2023-03-06 22:13:57',1000,0,0,2,1),
(20,'2023-03-06 22:36:29',2260,0,0,2,1),
(21,'2023-03-06 22:37:09',2260,0,0,2,1),
(22,'2023-03-06 22:38:44',500,0,0,2,1),
(23,'2023-03-06 22:38:55',500,0,0,2,1),
(24,'2023-03-06 22:51:21',740,0,0,2,1),
(25,'2023-03-06 22:51:31',740,0,0,2,2),
(26,'2023-03-06 22:51:37',740,0,0,2,2),
(27,'2023-03-06 22:54:49',500,0,0,2,2),
(28,'2023-03-06 22:54:58',500,0,0,2,2),
(29,'2023-03-06 23:00:38',500,0,0,2,2),
(30,'2023-03-06 23:00:45',500,0,0,2,2),
(31,'2023-03-06 23:02:20',350,0,0,2,5),
(32,'2023-03-06 23:57:57',740,0,0,2,5),
(33,'2023-03-06 23:59:25',500,0,0,2,5),
(34,'2023-03-07 00:01:14',1500,0,0,2,5),
(35,'2023-03-07 08:59:02',3140,0,0,2,5),
(36,'2023-03-07 15:30:21',3870,0,0,2,5),
(37,'2023-03-10 09:02:51',5000,0,0,2,5),
(38,'2023-03-11 01:25:48',2930,0,0,2,5),
(39,'2023-03-18 21:27:58',4760,0,0,2,5),
(40,'2023-03-19 00:25:06',2680,0,0,2,1),
(41,'2023-03-19 03:09:13',1200,0,0,2,1),
(42,'2023-03-20 00:11:23',3700,0,0,2,1),
(43,'2023-03-20 01:30:32',7170,0,0,2,1),
(44,'2023-03-21 19:09:12',1800,0,0,2,1),
(45,'2023-03-21 19:25:01',3700,0,0,2,2),
(46,'2023-03-21 19:40:41',2560,0,0,2,1),
(47,'2023-03-23 20:33:38',3160,0,0,2,1),
(48,'2023-03-23 20:38:55',3250,0,0,2,1),
(49,'2023-03-23 20:43:05',760,0,0,2,2),
(50,'2023-03-23 20:46:40',1200,0,0,2,1),
(51,'2023-03-23 20:58:20',5360,0,0,2,1),
(52,'2023-03-23 21:02:21',700,0,0,2,1),
(53,'2023-03-23 21:31:39',3550,0,0,2,1),
(54,'2023-03-23 21:32:55',2340,0,0,2,1),
(55,'2023-03-23 23:28:29',1200,0,0,2,1),
(56,'2023-03-24 03:38:12',1000,0,0,2,1),
(57,'2023-03-25 13:19:07',2780,0,0,2,1),
(58,'2023-03-25 13:20:01',1140,0,0,2,1),
(59,'2023-03-26 23:27:19',4510,0,0,2,6),
(60,'2023-03-29 21:48:10',3220,0,0,2,1),
(61,'2023-03-29 21:49:33',760,0,0,2,5),
(62,'2023-04-02 17:29:28',5380,1,0,2,1),
(63,'2023-04-02 17:35:24',2500,1,0,2,1),
(64,'2023-04-02 19:13:15',3980,1,0,2,1),
(65,'2023-04-02 19:13:41',6250,1,0,2,1),
(66,'2023-04-02 19:19:06',2220,1,0,2,6),
(67,'2023-04-02 19:19:37',2670,1,0,2,5),
(68,'2023-04-02 19:20:00',1480,1,0,2,1),
(69,'2023-04-02 19:20:46',1140,1,0,2,5),
(70,'2023-04-02 19:21:10',4440,1,0,2,6),
(71,'2023-04-02 21:41:49',4850,1,0,2,5),
(72,'2023-04-02 21:53:40',6520,0,0,2,2),
(73,'2023-04-02 21:53:54',1050,1,0,2,1),
(74,'2023-04-02 22:42:06',12770,1,0,2,1),
(75,'2023-04-02 22:42:42',2220,1,0,2,2),
(76,'2023-04-02 23:25:34',1480,1,0,2,1),
(77,'2023-04-02 23:44:26',1480,1,0,2,1),
(78,'2023-04-02 23:50:30',2960,1,0,2,1),
(79,'2023-04-02 23:57:11',2670,1,0,2,1),
(80,'2023-04-03 00:00:31',2960,1,0,2,1),
(81,'2023-04-03 00:00:54',2960,1,0,2,1),
(82,'2023-04-03 00:01:13',4450,1,0,2,5),
(83,'2023-04-03 00:05:59',1780,1,0,2,1),
(84,'2023-04-03 00:18:32',2500,1,0,2,1),
(85,'2023-04-03 00:18:59',2500,1,0,2,1),
(86,'2023-04-03 00:27:12',1480,1,0,2,1),
(87,'2023-04-03 00:32:29',1290,1,0,2,1),
(88,'2023-04-03 00:33:51',1480,1,0,2,5),
(89,'2023-04-03 00:36:33',2580,1,0,2,1),
(90,'2023-04-03 00:40:43',4440,1,0,2,1),
(91,'2023-04-03 00:43:06',600,1,0,2,1),
(92,'2023-04-03 00:56:59',700,1,0,2,1),
(93,'2023-04-03 00:59:53',2500,1,0,2,1),
(94,'2023-04-03 01:01:03',2580,1,0,2,1),
(95,'2023-04-03 01:10:56',1200,1,0,2,1),
(96,'2023-04-03 02:06:44',3720,1,0,2,5),
(97,'2023-04-03 02:20:35',2500,1,0,2,1),
(98,'2023-04-03 08:27:28',1000,1,0,2,1),
(99,'2023-04-03 08:29:31',3870,1,0,2,1),
(100,'2023-04-03 08:33:41',3870,1,0,2,1),
(101,'2023-04-03 08:44:05',2980,1,0,2,6),
(102,'2023-04-03 08:59:02',2750,1,0,2,2),
(103,'2023-04-03 09:02:13',4380,1,0,2,1),
(104,'2023-04-03 09:03:25',6890,0,0,2,1),
(105,'2023-04-03 09:05:13',1800,1,0,2,1),
(106,'2023-04-03 09:05:33',3870,1,0,2,6),
(107,'2023-04-03 09:16:49',1780,0,0,2,1),
(108,'2023-04-03 09:17:00',3870,1,0,2,5),
(109,'2023-04-04 00:26:23',4060,1,1,2,1),
(110,'2023-04-04 00:50:43',700,1,1,2,1),
(111,'2023-04-04 11:36:18',1960,1,1,2,1),
(112,'2023-04-04 11:36:36',3260,1,1,2,2),
(113,'2023-04-04 11:58:06',5250,1,1,2,1),
(114,'2023-04-04 15:08:09',1160,1,1,2,5),
(115,'2023-04-04 15:21:21',5480,1,1,2,5),
(116,'2023-04-06 21:47:10',2600,1,1,2,1),
(117,'2023-04-06 23:20:22',1450,1,1,2,5),
(118,'2023-04-06 23:20:45',1200,1,1,2,5),
(119,'2023-04-06 23:24:56',1290,1,0,2,1),
(120,'2023-04-06 23:25:10',2670,1,0,2,6),
(121,'2023-04-06 23:26:44',1400,1,0,2,1),
(122,'2023-04-07 17:26:43',4460,1,1,2,2),
(123,'2023-04-07 17:33:36',1100,1,0,2,1),
(124,'2023-04-07 18:38:10',2880,0,0,2,1);

/*Table structure for table `orderitems` */

DROP TABLE IF EXISTS `orderitems`;

CREATE TABLE `orderitems` (
  `orderId` bigint unsigned NOT NULL,
  `orderItemsId` int unsigned NOT NULL AUTO_INCREMENT,
  `menuItemId` int unsigned NOT NULL,
  `quantity` int unsigned NOT NULL,
  `orderItemReadyStatus` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`orderId`,`orderItemsId`),
  KEY `orderItemsId` (`orderItemsId`),
  KEY `orderitems_ibfk_2` (`menuItemId`),
  CONSTRAINT `orderitems_ibfk_1` FOREIGN KEY (`orderId`) REFERENCES `order` (`orderId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `orderitems_ibfk_2` FOREIGN KEY (`menuItemId`) REFERENCES `menuitem` (`menuItemId`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=176 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `orderitems` */

insert  into `orderitems`(`orderId`,`orderItemsId`,`menuItemId`,`quantity`,`orderItemReadyStatus`) values 
(9,9,1,2,0),
(9,10,2,3,0),
(10,11,1,2,0),
(10,12,2,3,0),
(11,13,1,2,0),
(11,14,2,3,0),
(12,15,1,2,0),
(12,16,6,1,0),
(12,17,5,2,0),
(15,18,6,2,0),
(15,19,1,2,0),
(18,20,1,4,0),
(18,21,7,4,0),
(19,22,1,2,0),
(21,23,1,3,0),
(21,24,6,2,0),
(23,25,1,1,0),
(26,26,5,1,0),
(28,27,1,1,0),
(30,28,1,1,0),
(31,29,7,1,0),
(32,30,5,1,0),
(33,31,1,1,0),
(34,32,1,3,0),
(35,33,1,4,0),
(35,34,6,3,0),
(36,35,4,3,0),
(37,36,10,4,0),
(38,37,7,1,0),
(38,38,4,2,0),
(39,39,5,4,0),
(39,40,2,3,0),
(40,41,5,2,0),
(40,42,2,2,0),
(41,43,2,2,0),
(42,44,10,2,0),
(42,45,2,2,0),
(43,46,2,2,0),
(43,47,10,3,0),
(43,48,5,3,0),
(44,49,2,3,0),
(45,50,2,2,0),
(45,51,10,2,0),
(46,52,6,2,0),
(46,53,2,3,0),
(47,54,2,4,0),
(47,55,6,2,0),
(48,56,2,2,0),
(48,57,7,3,0),
(48,58,1,2,0),
(49,59,6,2,0),
(50,60,2,2,0),
(51,61,6,7,0),
(51,62,1,3,0),
(51,63,2,2,0),
(52,64,7,2,0),
(53,65,1,5,0),
(53,66,7,3,0),
(54,67,2,2,0),
(54,68,6,3,0),
(55,69,2,2,0),
(56,70,1,2,0),
(57,71,1,2,0),
(57,72,6,1,0),
(57,73,7,4,0),
(58,74,6,3,0),
(59,75,6,2,0),
(59,76,10,3,0),
(60,77,1,2,0),
(60,78,5,3,0),
(61,79,6,2,0),
(62,80,10,4,0),
(62,81,6,1,0),
(63,82,10,2,0),
(64,83,5,2,0),
(64,84,10,2,0),
(65,85,10,5,0),
(66,86,5,3,0),
(67,87,15,3,0),
(68,88,5,2,0),
(69,89,6,3,0),
(70,90,5,6,0),
(71,91,10,2,0),
(71,92,14,3,0),
(71,93,7,5,0),
(72,94,1,2,0),
(72,95,15,3,0),
(72,96,7,3,0),
(72,97,2,3,0),
(73,98,7,3,0),
(74,99,7,7,0),
(74,100,4,8,0),
(75,101,5,3,0),
(76,102,5,2,0),
(77,103,5,2,0),
(78,104,5,4,0),
(79,105,15,3,0),
(80,106,5,4,0),
(81,107,5,4,0),
(82,108,15,5,0),
(83,109,15,2,0),
(84,110,10,2,0),
(85,111,10,2,0),
(86,112,5,2,0),
(87,113,4,1,0),
(88,114,5,2,0),
(89,115,4,2,0),
(90,116,5,6,0),
(91,117,14,3,0),
(92,118,7,2,0),
(93,119,10,2,0),
(94,120,4,2,0),
(95,121,2,2,0),
(96,122,4,2,0),
(96,123,6,3,0),
(97,124,10,2,0),
(98,125,1,2,0),
(99,126,4,3,0),
(100,127,4,3,0),
(101,128,5,2,0),
(101,129,1,3,0),
(102,130,7,5,0),
(102,131,1,2,0),
(103,132,4,2,0),
(103,133,2,3,0),
(104,134,1,2,0),
(104,135,2,2,0),
(104,136,6,3,0),
(104,137,7,3,0),
(104,138,10,2,0),
(105,139,2,3,0),
(106,140,4,3,0),
(107,141,15,2,0),
(108,142,4,3,0),
(109,143,4,2,0),
(109,144,5,2,0),
(110,145,7,2,0),
(111,146,2,2,0),
(111,147,6,2,0),
(112,148,10,2,0),
(112,149,6,2,0),
(113,150,4,2,0),
(113,151,15,3,0),
(114,152,6,2,0),
(114,153,14,2,0),
(115,154,4,2,0),
(115,155,10,2,0),
(115,156,14,2,0),
(116,157,1,2,0),
(116,158,2,2,0),
(116,159,14,2,0),
(117,160,10,1,0),
(117,161,14,1,0),
(118,162,1,2,0),
(118,163,14,1,0),
(119,164,4,1,0),
(120,165,15,3,0),
(121,166,14,2,0),
(121,167,1,2,0),
(122,168,4,2,0),
(122,169,5,2,0),
(122,170,14,2,0),
(123,171,7,2,0),
(123,172,14,2,0),
(124,173,7,2,0),
(124,174,15,2,0),
(124,175,14,2,0);

/*Table structure for table `payment` */

DROP TABLE IF EXISTS `payment`;

CREATE TABLE `payment` (
  `paymentId` bigint unsigned NOT NULL AUTO_INCREMENT,
  `receiptId` bigint unsigned NOT NULL,
  `paymentMethodId` int unsigned NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY (`paymentId`,`receiptId`),
  KEY `receiptId` (`receiptId`),
  KEY `paymentMethodId` (`paymentMethodId`),
  CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`receiptId`) REFERENCES `receipt` (`receiptId`),
  CONSTRAINT `payment_ibfk_2` FOREIGN KEY (`paymentMethodId`) REFERENCES `paymentmethod` (`paymentMethodId`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_et_0900_ai_ci;

/*Data for the table `payment` */

insert  into `payment`(`paymentId`,`receiptId`,`paymentMethodId`,`date`) values 
(1,25,1,'2023-04-04 10:20:02'),
(2,27,1,'2023-04-04 11:43:32'),
(3,28,2,'2023-04-04 11:48:16'),
(4,29,1,'2023-04-04 15:10:00'),
(5,30,2,'2023-04-04 15:10:52'),
(6,31,1,'2023-04-04 22:37:29'),
(7,32,1,'2023-04-06 23:06:09'),
(8,33,2,'2023-04-06 23:22:20'),
(9,34,2,'2023-04-07 17:27:27');

/*Table structure for table `paymentmethod` */

DROP TABLE IF EXISTS `paymentmethod`;

CREATE TABLE `paymentmethod` (
  `paymentMethodId` int unsigned NOT NULL AUTO_INCREMENT,
  `paymentMethodName` varchar(20) NOT NULL,
  PRIMARY KEY (`paymentMethodId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `paymentmethod` */

insert  into `paymentmethod`(`paymentMethodId`,`paymentMethodName`) values 
(1,'VISA'),
(2,'CASH');

/*Table structure for table `receipt` */

DROP TABLE IF EXISTS `receipt`;

CREATE TABLE `receipt` (
  `receiptId` bigint unsigned NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `totalAmount` decimal(10,0) unsigned DEFAULT NULL,
  PRIMARY KEY (`receiptId`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_et_0900_ai_ci;

/*Data for the table `receipt` */

insert  into `receipt`(`receiptId`,`date`,`totalAmount`) values 
(1,'2023-04-03 10:05:00',3000),
(2,'2023-04-03 23:25:52',6850),
(3,'2023-04-03 23:33:15',2750),
(4,'2023-04-03 23:45:13',2750),
(5,'2023-04-03 23:59:44',6850),
(6,'2023-04-04 00:26:54',4060),
(7,'2023-04-04 00:28:08',4060),
(8,'2023-04-04 00:33:06',4060),
(9,'2023-04-04 00:33:58',4060),
(10,'2023-04-04 00:35:31',4060),
(11,'2023-04-04 00:39:58',4060),
(12,'2023-04-04 00:46:17',4060),
(13,'2023-04-04 00:47:10',4060),
(14,'2023-04-04 00:47:27',4060),
(15,'2023-04-04 00:49:41',4060),
(16,'2023-04-04 00:51:08',4760),
(17,'2023-04-04 01:00:50',4760),
(18,'2023-04-04 02:01:10',4760),
(19,'2023-04-04 02:03:38',4760),
(20,'2023-04-04 02:05:10',4760),
(21,'2023-04-04 02:07:09',4760),
(22,'2023-04-04 02:10:30',4760),
(23,'2023-04-04 02:13:03',4760),
(24,'2023-04-04 09:15:30',4760),
(25,'2023-04-04 10:19:43',4760),
(26,'2023-04-04 11:42:37',1960),
(27,'2023-04-04 11:43:22',1960),
(28,'2023-04-04 11:48:10',3260),
(29,'2023-04-04 15:09:36',1160),
(30,'2023-04-04 15:10:38',5250),
(31,'2023-04-04 22:37:13',5480),
(32,'2023-04-06 23:05:55',2600),
(33,'2023-04-06 23:22:15',2650),
(34,'2023-04-07 17:27:23',4460);

/*Table structure for table `receiptorder` */

DROP TABLE IF EXISTS `receiptorder`;

CREATE TABLE `receiptorder` (
  `receiptId` bigint unsigned NOT NULL,
  `orderId` bigint unsigned NOT NULL,
  PRIMARY KEY (`receiptId`,`orderId`),
  KEY `orderId` (`orderId`),
  CONSTRAINT `receiptorder_ibfk_1` FOREIGN KEY (`receiptId`) REFERENCES `receipt` (`receiptId`),
  CONSTRAINT `receiptorder_ibfk_2` FOREIGN KEY (`orderId`) REFERENCES `order` (`orderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_et_0900_ai_ci;

/*Data for the table `receiptorder` */

insert  into `receiptorder`(`receiptId`,`orderId`) values 
(1,32),
(1,33),
(1,34),
(2,101),
(5,101),
(3,102),
(4,102),
(2,106),
(5,106),
(6,109),
(7,109),
(8,109),
(9,109),
(10,109),
(11,109),
(12,109),
(13,109),
(14,109),
(15,109),
(16,109),
(17,109),
(18,109),
(19,109),
(20,109),
(21,109),
(22,109),
(23,109),
(24,109),
(25,109),
(16,110),
(17,110),
(18,110),
(19,110),
(20,110),
(21,110),
(22,110),
(23,110),
(24,110),
(25,110),
(26,111),
(27,111),
(28,112),
(30,113),
(29,114),
(31,115),
(32,116),
(33,117),
(33,118),
(34,122);

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `roleId` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `role` */

insert  into `role`(`roleId`,`name`) values 
(1,'sef kuhinje'),
(2,'konobar'),
(3,'administrator');

/*Table structure for table `table` */

DROP TABLE IF EXISTS `table`;

CREATE TABLE `table` (
  `tableId` int unsigned NOT NULL AUTO_INCREMENT,
  `numberOfSeats` int unsigned NOT NULL,
  `isAvailable` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`tableId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `table` */

insert  into `table`(`tableId`,`numberOfSeats`,`isAvailable`) values 
(1,4,1),
(2,4,1),
(5,4,1),
(6,8,1);

/*Table structure for table `tablereservation` */

DROP TABLE IF EXISTS `tablereservation`;

CREATE TABLE `tablereservation` (
  `reservationId` bigint unsigned NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `customerName` varchar(100) NOT NULL,
  `customerPhoneNumber` varchar(30) NOT NULL,
  `tableId` int unsigned NOT NULL,
  PRIMARY KEY (`reservationId`),
  UNIQUE KEY `unique_key` (`date`,`tableId`),
  KEY `tableId` (`tableId`),
  CONSTRAINT `tablereservation_ibfk_1` FOREIGN KEY (`tableId`) REFERENCES `table` (`tableId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `tablereservation` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `employeeId` int unsigned DEFAULT NULL,
  PRIMARY KEY (`username`),
  KEY `user_ibfk_1` (`employeeId`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`employeeId`) REFERENCES `employee` (`employeeId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user` */

insert  into `user`(`username`,`password`,`employeeId`) values 
('anaAnic2','1234',2),
('ivanIvic4','1234',4),
('jelenaJelic3','1234',3),
('mirjanaMilanovic5','1234',5),
('peraPeric1','1234',1),
('zikaZikic8','1234',8);

/*Table structure for table `userrole` */

DROP TABLE IF EXISTS `userrole`;

CREATE TABLE `userrole` (
  `username` varchar(20) NOT NULL,
  `roleId` int unsigned NOT NULL,
  PRIMARY KEY (`username`,`roleId`),
  KEY `roleId` (`roleId`),
  CONSTRAINT `userrole_ibfk_1` FOREIGN KEY (`username`) REFERENCES `user` (`username`),
  CONSTRAINT `userrole_ibfk_2` FOREIGN KEY (`roleId`) REFERENCES `role` (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `userrole` */

insert  into `userrole`(`username`,`roleId`) values 
('peraPeric1',1),
('ivanIvic4',2),
('jelenaJelic3',2),
('mirjanaMilanovic5',2),
('zikaZikic8',2),
('anaAnic2',3);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
