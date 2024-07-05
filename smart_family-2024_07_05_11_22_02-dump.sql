-- MySQL dump 10.13  Distrib 8.0.25, for macos11 (x86_64)
--
-- Host: 127.0.0.1    Database: smart_family_v1
-- ------------------------------------------------------
-- Server version	8.0.25

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
-- Table structure for table `t_game`
--

DROP TABLE IF EXISTS `t_game`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_game` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(10) NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '游戏名称',
  `content` varchar(500) DEFAULT NULL COMMENT '游戏描述',
  `disable_flag` tinyint(1) DEFAULT '0',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='游戏表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_game`
--

LOCK TABLES `t_game` WRITE;
/*!40000 ALTER TABLE `t_game` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_game` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_gift`
--

DROP TABLE IF EXISTS `t_gift`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_gift` (
  `gift_id` int NOT NULL AUTO_INCREMENT COMMENT '用户礼物id',
  `user_id` int NOT NULL DEFAULT '-1' COMMENT '用户id',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `name` varchar(50) DEFAULT NULL COMMENT '礼物名称',
  `price` float DEFAULT NULL,
  `content` varchar(200) DEFAULT NULL COMMENT '内容描述',
  PRIMARY KEY (`gift_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户的礼物表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_gift`
--

LOCK TABLES `t_gift` WRITE;
/*!40000 ALTER TABLE `t_gift` DISABLE KEYS */;
INSERT INTO `t_gift` VALUES (3,1,'2024-06-28 22:11:36','2024-06-13 18:04:18','纸飞机',1,'爸爸手折的纸飞机'),(4,1,'2024-06-28 14:17:03','2024-06-28 14:17:03','贴贴纸',1,'好看的贴贴纸'),(5,1,'2024-06-28 14:20:31','2024-06-28 14:20:31','小玩具',1,'一个小玩具'),(6,1,'2024-06-28 22:10:21','2024-06-28 22:10:21','木子里贴纸',2,'很好看的贴纸，拥有木子里的贴纸'),(7,1,'2024-06-28 22:11:11','2024-06-28 22:11:11','小马宝莉卡片',2,'小马宝莉图案的卡片');
/*!40000 ALTER TABLE `t_gift` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_order_gift`
--

DROP TABLE IF EXISTS `t_order_gift`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_order_gift` (
  `order_id` int NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `user_id` int NOT NULL COMMENT '用户id',
  `status` int NOT NULL DEFAULT '0' COMMENT '订单状态 3、未发货， 4、已发货，5、交易成功，6、交易关闭',
  `gift_id` int NOT NULL COMMENT '礼物id',
  `name` varchar(50) NOT NULL COMMENT '礼物名称',
  `num` int NOT NULL COMMENT '礼物兑换数量',
  `content` varchar(200) NOT NULL COMMENT '礼物内容',
  `price` float NOT NULL COMMENT '礼物单价',
  `total_price` float NOT NULL COMMENT '礼物总价',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='礼物订单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_order_gift`
--

LOCK TABLES `t_order_gift` WRITE;
/*!40000 ALTER TABLE `t_order_gift` DISABLE KEYS */;
INSERT INTO `t_order_gift` VALUES (11,2,4,5,'小玩具',1,'一个小玩具',100,100,'2024-07-01 17:00:03','2024-06-28 21:24:10'),(12,2,4,4,'贴贴纸',1,'好看的贴贴纸',100,100,'2024-07-01 17:01:18','2024-06-28 21:25:07'),(13,2,3,5,'小玩具',1,'一个小玩具',100,100,'2024-06-28 22:06:54','2024-06-28 22:06:54');
/*!40000 ALTER TABLE `t_order_gift` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_record`
--

DROP TABLE IF EXISTS `t_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_record` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL COMMENT '用户id',
  `ref_id` int DEFAULT NULL,
  `ref_type` int NOT NULL DEFAULT '-1' COMMENT '相应记录类型',
  `content` varchar(500) DEFAULT '' COMMENT '记录json数据',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_record`
--

LOCK TABLES `t_record` WRITE;
/*!40000 ALTER TABLE `t_record` DISABLE KEYS */;
INSERT INTO `t_record` VALUES (4,2,15,100,'{\"id\":15,\"taskId\":3,\"title\":\"短文学习\",\"content\":\"完成一篇短文学习\",\"taskDate\":[2024,6,28]}','2024-06-28 11:00:37','2024-06-28 11:00:37'),(5,2,16,100,'{\"id\":16,\"taskId\":4,\"title\":\"小古文学习\",\"content\":\"完成一篇小古文学习\",\"taskDate\":[2024,6,28]}','2024-06-28 11:00:38','2024-06-28 11:00:38'),(6,2,17,100,'{\"id\":17,\"taskId\":5,\"title\":\"短文阅读\",\"content\":\"完成一篇短文阅读\",\"taskDate\":[2024,6,28]}','2024-06-28 20:44:02','2024-06-28 20:44:02'),(7,2,18,100,'{\"id\":18,\"taskId\":6,\"title\":\"学习成语\",\"content\":\"学习十个成语\",\"taskDate\":[2024,6,28]}','2024-06-28 20:44:03','2024-06-28 20:44:03'),(8,2,19,100,'{\"id\":19,\"taskId\":7,\"title\":\"作文练习\",\"content\":\"写一篇不少于500字的作文\",\"taskDate\":[2024,6,28]}','2024-06-28 20:44:04','2024-06-28 20:44:04'),(9,2,12,200,'{\"orderId\":12,\"num\":1,\"name\":\"贴贴纸\",\"content\":\"好看的贴贴纸\",\"price\":100,\"totalPrice\":100}','2024-06-28 21:25:07','2024-06-28 21:25:07'),(10,2,15,100,'{\"id\":15,\"taskId\":3,\"title\":\"短文学习1\",\"content\":\"完成一篇短文学习\",\"taskDate\":[2024,6,28]}','2024-06-28 22:03:10','2024-06-28 22:03:10'),(11,2,16,100,'{\"id\":16,\"taskId\":4,\"title\":\"小古文学习\",\"content\":\"完成一篇小古文学习\",\"taskDate\":[2024,6,28]}','2024-06-28 22:06:12','2024-06-28 22:06:12'),(12,2,17,100,'{\"id\":17,\"taskId\":5,\"title\":\"短文阅读\",\"content\":\"完成一篇短文阅读\",\"taskDate\":[2024,6,28]}','2024-06-28 22:06:13','2024-06-28 22:06:13'),(13,2,18,100,'{\"id\":18,\"taskId\":6,\"title\":\"学习成语\",\"content\":\"学习十个成语\",\"taskDate\":[2024,6,28]}','2024-06-28 22:06:14','2024-06-28 22:06:14'),(14,2,19,100,'{\"id\":19,\"taskId\":7,\"title\":\"作文练习\",\"content\":\"写一篇不少于500字的作文\",\"taskDate\":[2024,6,28]}','2024-06-28 22:06:14','2024-06-28 22:06:14'),(15,2,20,100,'{\"id\":20,\"taskId\":8,\"title\":\"小古文阅读\",\"content\":\"阅读一篇小古文\",\"taskDate\":[2024,6,28]}','2024-06-28 22:06:15','2024-06-28 22:06:15'),(16,2,13,200,'{\"orderId\":13,\"num\":1,\"name\":\"小玩具\",\"content\":\"一个小玩具\",\"price\":100,\"totalPrice\":100}','2024-06-28 22:06:54','2024-06-28 22:06:54'),(17,2,21,100,'{\"id\":21,\"taskId\":3,\"title\":\"短文学习1\",\"content\":\"完成一篇短文学习\",\"taskDate\":[2024,7,1]}','2024-07-01 16:30:39','2024-07-01 16:30:39'),(18,2,22,100,'{\"id\":22,\"taskId\":4,\"title\":\"小古文学习\",\"content\":\"完成一篇小古文学习\",\"taskDate\":[2024,7,1]}','2024-07-01 16:30:40','2024-07-01 16:30:40'),(19,2,24,100,'{\"id\":24,\"taskId\":9,\"title\":\"24点练习\",\"content\":\"每天完成几道 24 点\",\"taskType\":100,\"taskDate\":[2024,7,4]}','2024-07-04 16:37:06','2024-07-04 16:37:06'),(20,2,24,100,'{\"id\":24,\"taskId\":9,\"title\":\"24点练习\",\"content\":\"每天完成几道 24 点\",\"taskType\":100,\"taskDate\":[2024,7,4]}','2024-07-04 21:47:09','2024-07-04 21:47:09'),(21,2,24,100,'{\"id\":24,\"taskId\":9,\"title\":\"24点练习\",\"content\":\"每天完成几道 24 点\",\"taskType\":100,\"taskDate\":[2024,7,4]}','2024-07-04 21:58:04','2024-07-04 21:58:04');
/*!40000 ALTER TABLE `t_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_task`
--

DROP TABLE IF EXISTS `t_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_task` (
  `task_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `title` varchar(100) NOT NULL,
  `content` varchar(500) DEFAULT NULL,
  `task_type` int DEFAULT '0' COMMENT '任务类型',
  `verify_flag` int DEFAULT '0' COMMENT '是否需要审核',
  `disable_flag` tinyint DEFAULT '0',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`task_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_task`
--

LOCK TABLES `t_task` WRITE;
/*!40000 ALTER TABLE `t_task` DISABLE KEYS */;
INSERT INTO `t_task` VALUES (3,1,'短文学习1','完成一篇短文学习',0,1,0,'2024-06-28 20:44:28','2024-06-13 20:43:34'),(4,1,'小古文学习','完成一篇小古文学习',0,1,0,'2024-06-27 14:32:35','2024-06-13 20:43:38'),(5,1,'短文阅读','完成一篇短文阅读',0,1,0,'2024-06-27 14:46:55','2024-06-27 14:29:16'),(6,1,'学习成语','学习十个成语',0,1,0,'2024-06-27 14:46:57','2024-06-27 14:34:56'),(7,1,'作文练习','写一篇不少于500字的作文',0,1,0,'2024-06-27 15:50:11','2024-06-27 15:50:11'),(8,1,'小古文阅读','阅读一篇小古文',0,1,0,'2024-06-28 21:52:48','2024-06-28 21:52:48'),(9,1,'24点练习','每天完成几道 24 点',100,0,0,'2024-07-04 16:36:39','2024-07-04 16:36:39');
/*!40000 ALTER TABLE `t_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_task_integral`
--

DROP TABLE IF EXISTS `t_task_integral`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_task_integral` (
  `task_id` int NOT NULL COMMENT '任务id',
  `integral` int NOT NULL DEFAULT '0' COMMENT '任务积分',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='任务积分表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_task_integral`
--

LOCK TABLES `t_task_integral` WRITE;
/*!40000 ALTER TABLE `t_task_integral` DISABLE KEYS */;
INSERT INTO `t_task_integral` VALUES (3,20,'2024-06-27 16:13:13','2024-06-27 16:13:13'),(4,10,'2024-06-27 16:18:46','2024-06-27 16:18:46'),(5,10,'2024-06-27 16:18:50','2024-06-27 16:18:50'),(6,10,'2024-06-27 16:18:56','2024-06-27 16:18:56'),(7,10,'2024-06-27 16:09:59','2024-06-27 15:50:11'),(8,10,'2024-06-28 21:52:48','2024-06-28 21:52:48'),(9,10,'2024-07-04 16:36:39','2024-07-04 16:36:39');
/*!40000 ALTER TABLE `t_task_integral` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_task_record`
--

DROP TABLE IF EXISTS `t_task_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_task_record` (
  `id` int NOT NULL AUTO_INCREMENT,
  `task_id` int NOT NULL,
  `user_id` int NOT NULL,
  `task_date` date NOT NULL,
  `status` int NOT NULL,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_task_user_record` (`task_id`,`user_id`,`task_date`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_task_record`
--

LOCK TABLES `t_task_record` WRITE;
/*!40000 ALTER TABLE `t_task_record` DISABLE KEYS */;
INSERT INTO `t_task_record` VALUES (1,3,2,'2024-06-17',0,'2024-06-18 14:25:03','2024-06-17 16:26:02'),(2,3,2,'2024-06-18',200,'2024-06-18 14:25:05','2024-06-17 16:30:00'),(7,4,2,'2024-06-18',200,'2024-06-18 14:37:20','2024-06-18 13:37:12'),(8,3,2,'2024-06-26',100,'2024-06-26 20:37:55','2024-06-26 20:20:20'),(9,4,2,'2024-06-26',100,'2024-06-26 20:38:04','2024-06-26 20:20:36'),(10,3,2,'2024-06-27',200,'2024-06-27 19:31:08','2024-06-27 13:16:26'),(11,4,2,'2024-06-27',200,'2024-06-27 19:31:09','2024-06-27 13:16:27'),(12,5,2,'2024-06-27',200,'2024-06-27 19:29:54','2024-06-27 14:36:19'),(13,6,2,'2024-06-27',200,'2024-06-27 19:29:55','2024-06-27 14:36:21'),(14,7,2,'2024-06-27',200,'2024-06-27 19:31:10','2024-06-27 19:15:43'),(15,3,2,'2024-06-28',200,'2024-06-28 22:03:10','2024-06-28 10:48:51'),(16,4,2,'2024-06-28',200,'2024-06-28 22:06:12','2024-06-28 10:48:52'),(17,5,2,'2024-06-28',200,'2024-06-28 22:06:13','2024-06-28 10:48:53'),(18,6,2,'2024-06-28',200,'2024-06-28 22:06:14','2024-06-28 10:48:54'),(19,7,2,'2024-06-28',200,'2024-06-28 22:06:14','2024-06-28 10:48:55'),(20,8,2,'2024-06-28',200,'2024-06-28 22:06:15','2024-06-28 21:55:44'),(21,3,2,'2024-07-01',200,'2024-07-01 16:30:39','2024-07-01 16:30:17'),(22,4,2,'2024-07-01',200,'2024-07-01 16:30:40','2024-07-01 16:30:18'),(23,5,2,'2024-07-01',100,'2024-07-01 16:30:19','2024-07-01 16:30:19'),(24,9,2,'2024-07-04',200,'2024-07-04 21:58:04','2024-07-04 16:37:06');
/*!40000 ALTER TABLE `t_task_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `name` varchar(20) NOT NULL COMMENT '用户登录名称',
  `nickname` varchar(50) NOT NULL COMMENT '用户昵称',
  `mobile` varchar(20) DEFAULT NULL COMMENT '用户手机号',
  `password` varchar(100) NOT NULL COMMENT '用户密码',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '用户更新时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户创建时间',
  `parent_id` int DEFAULT '-1' COMMENT '用户父节点id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user`
--

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` VALUES (1,'xiejun','tunie - update','18768166224','123456','2024-06-07 15:15:31','2024-06-07 15:12:53',-1),(2,'xuan','yixuan',NULL,'123456','2024-06-13 20:51:17','2024-06-13 11:04:50',1);
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user_integral`
--

DROP TABLE IF EXISTS `t_user_integral`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user_integral` (
  `user_id` int NOT NULL COMMENT '用户id',
  `integral` int NOT NULL DEFAULT '0' COMMENT '用户积分',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户积分表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user_integral`
--

LOCK TABLES `t_user_integral` WRITE;
/*!40000 ALTER TABLE `t_user_integral` DISABLE KEYS */;
INSERT INTO `t_user_integral` VALUES (2,90,'2024-06-27 19:29:54','2024-06-27 19:29:54');
/*!40000 ALTER TABLE `t_user_integral` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user_integral_record`
--

DROP TABLE IF EXISTS `t_user_integral_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user_integral_record` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int NOT NULL COMMENT '用户id',
  `integral_change` int NOT NULL COMMENT '用户积分变化',
  `ref_id` int DEFAULT NULL COMMENT '关联事件id',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='积分变化记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user_integral_record`
--

LOCK TABLES `t_user_integral_record` WRITE;
/*!40000 ALTER TABLE `t_user_integral_record` DISABLE KEYS */;
INSERT INTO `t_user_integral_record` VALUES (12,2,20,4,'2024-06-28 11:00:37','2024-06-28 11:00:37'),(13,2,10,5,'2024-06-28 11:00:38','2024-06-28 11:00:38'),(14,2,10,6,'2024-06-28 20:44:02','2024-06-28 20:44:02'),(15,2,10,7,'2024-06-28 20:44:03','2024-06-28 20:44:03'),(16,2,10,8,'2024-06-28 20:44:04','2024-06-28 20:44:04'),(17,2,-100,9,'2024-06-28 21:25:07','2024-06-28 21:25:07'),(18,2,20,10,'2024-06-28 22:03:10','2024-06-28 22:03:10'),(19,2,10,11,'2024-06-28 22:06:12','2024-06-28 22:06:12'),(20,2,10,12,'2024-06-28 22:06:13','2024-06-28 22:06:13'),(21,2,10,13,'2024-06-28 22:06:14','2024-06-28 22:06:14'),(22,2,10,14,'2024-06-28 22:06:15','2024-06-28 22:06:15'),(23,2,10,15,'2024-06-28 22:06:15','2024-06-28 22:06:15'),(24,2,-100,16,'2024-06-28 22:06:54','2024-06-28 22:06:54'),(25,2,20,17,'2024-07-01 16:30:39','2024-07-01 16:30:39'),(26,2,10,18,'2024-07-01 16:30:40','2024-07-01 16:30:40'),(27,2,10,19,'2024-07-04 16:37:06','2024-07-04 16:37:06'),(28,2,10,20,'2024-07-04 21:47:09','2024-07-04 21:47:09'),(29,2,10,21,'2024-07-04 21:58:04','2024-07-04 21:58:04');
/*!40000 ALTER TABLE `t_user_integral_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_word`
--

DROP TABLE IF EXISTS `t_word`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_word` (
  `id` int NOT NULL AUTO_INCREMENT,
  `level` int DEFAULT '0' COMMENT '难度级别',
  `en_value` varchar(50) NOT NULL,
  `ch_value` varchar(150) NOT NULL,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='单词表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_word`
--

LOCK TABLES `t_word` WRITE;
/*!40000 ALTER TABLE `t_word` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_word` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-05 11:22:02
