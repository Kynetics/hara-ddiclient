-- MySQL dump 10.17  Distrib 10.3.13-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: hawkbit
-- ------------------------------------------------------
-- Server version	10.3.13-MariaDB-1:10.3.13+maria~bionic

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `schema_version`
--

DROP TABLE IF EXISTS `schema_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schema_version` (
  `version_rank` int(11) NOT NULL,
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) NOT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int(11) DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`version`),
  KEY `schema_version_vr_idx` (`version_rank`),
  KEY `schema_version_ir_idx` (`installed_rank`),
  KEY `schema_version_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schema_version`
--

LOCK TABLES `schema_version` WRITE;
/*!40000 ALTER TABLE `schema_version` DISABLE KEYS */;
INSERT INTO `schema_version` VALUES (1,1,'1.0.1','init   ','SQL','MYSQL/V1_0_1__init___MYSQL.sql',1018732788,'hawkbit','2019-03-07 16:10:59',151,1),(13,13,'1.10.0','advanced rolloutgroup  ','SQL','MYSQL/V1_10_0__advanced_rolloutgroup__MYSQL.sql',664507985,'hawkbit','2019-03-07 16:10:59',2,1),(14,14,'1.10.1','consolidate artifact sha1  ','SQL','MYSQL/V1_10_1__consolidate_artifact_sha1__MYSQL.sql',-360463156,'hawkbit','2019-03-07 16:10:59',8,1),(15,15,'1.10.2','rollout auto start  ','SQL','MYSQL/V1_10_2__rollout_auto_start__MYSQL.sql',1887828765,'hawkbit','2019-03-07 16:10:59',1,1),(16,16,'1.10.3','add rollout deleted flag  ','SQL','MYSQL/V1_10_3__add_rollout_deleted_flag__MYSQL.sql',1292572859,'hawkbit','2019-03-07 16:10:59',31,1),(17,17,'1.11.0','drop target info  ','SQL','MYSQL/V1_11_0__drop_target_info__MYSQL.sql',413839248,'hawkbit','2019-03-07 16:10:59',14,1),(18,18,'1.11.1','target filter query UQ   ','SQL','MYSQL/V1_11_1__target_filter_query_UQ___MYSQL.sql',-115576513,'hawkbit','2019-03-07 16:10:59',2,1),(19,19,'1.11.2','remove unused idexes   ','SQL','MYSQL/V1_11_2__remove_unused_idexes___MYSQL.sql',-507567816,'hawkbit','2019-03-07 16:10:59',6,1),(20,20,'1.11.2.1','create tenant usage  ','SQL','MYSQL/V1_11_2_1__create_tenant_usage__MYSQL.sql',-206019792,'hawkbit','2019-03-07 16:10:59',2,1),(21,21,'1.11.3','add module md targetvis  ','SQL','MYSQL/V1_11_3__add_module_md_targetvis__MYSQL.sql',185590925,'hawkbit','2019-03-07 16:10:59',2,1),(22,22,'1.12.0','action performance   ','SQL','MYSQL/V1_12_0__action_performance___MYSQL.sql',603109923,'hawkbit','2019-03-07 16:10:59',20,1),(23,23,'1.12.1','missing non null   ','SQL','MYSQL/V1_12_1__missing_non_null___MYSQL.sql',193177367,'hawkbit','2019-03-07 16:10:59',12,1),(24,24,'1.12.2','missing non null enum   ','SQL','MYSQL/V1_12_2__missing_non_null_enum___MYSQL.sql',1849006417,'hawkbit','2019-03-07 16:10:59',9,1),(25,25,'1.12.3','cascade delete   ','SQL','MYSQL/V1_12_3__cascade_delete___MYSQL.sql',2033261637,'hawkbit','2019-03-07 16:10:59',1,1),(26,26,'1.12.4','add maintenance window   ','SQL','MYSQL/V1_12_4__add_maintenance_window___MYSQL.sql',120260775,'hawkbit','2019-03-07 16:10:59',2,1),(27,27,'1.12.6','add index   ','SQL','MYSQL/V1_12_6__add_index___MYSQL.sql',-859477542,'hawkbit','2019-03-07 16:10:59',3,1),(28,28,'1.12.7','add rollout approval fields   ','SQL','MYSQL/V1_12_7__add_rollout_approval_fields___MYSQL.sql',-146076213,'hawkbit','2019-03-07 16:10:59',2,1),(29,29,'1.12.8','change length of created last modified by   ','SQL','MYSQL/V1_12_8__change_length_of_created_last_modified_by___MYSQL.sql',1152232785,'hawkbit','2019-03-07 16:10:59',7,1),(2,2,'1.2.0','update target info for message   ','SQL','MYSQL/V1_2_0__update_target_info_for_message___MYSQL.sql',1912199924,'hawkbit','2019-03-07 16:10:59',5,1),(3,3,'1.4.0','cascade delete   ','SQL','MYSQL/V1_4_0__cascade_delete___MYSQL.sql',695610035,'hawkbit','2019-03-07 16:10:59',37,1),(4,4,'1.4.1','cascade delete   ','SQL','MYSQL/V1_4_1__cascade_delete___MYSQL.sql',71258789,'hawkbit','2019-03-07 16:10:59',6,1),(5,5,'1.5.0','target filter query   ','SQL','MYSQL/V1_5_0__target_filter_query___MYSQL.sql',1822904321,'hawkbit','2019-03-07 16:10:59',3,1),(6,6,'1.6.0','rollout management   ','SQL','MYSQL/V1_6_0__rollout_management___MYSQL.sql',-1974636320,'hawkbit','2019-03-07 16:10:59',36,1),(7,7,'1.7.0','swmType maxAssignment greater 0  ','SQL','MYSQL/V1_7_0__swmType_maxAssignment_greater_0__MYSQL.sql',-251836914,'hawkbit','2019-03-07 16:10:59',1,1),(8,8,'1.7.1','reduce length enums   ','SQL','MYSQL/V1_7_1__reduce_length_enums___MYSQL.sql',-593689229,'hawkbit','2019-03-07 16:10:59',14,1),(9,9,'1.8.0','auto assign ds filter  ','SQL','MYSQL/V1_8_0__auto_assign_ds_filter__MYSQL.sql',342236577,'hawkbit','2019-03-07 16:10:59',5,1),(10,10,'1.8.1','cascade delete   ','SQL','MYSQL/V1_8_1__cascade_delete___MYSQL.sql',1697028621,'hawkbit','2019-03-07 16:10:59',15,1),(11,11,'1.8.2','remove external artifact   ','SQL','MYSQL/V1_8_2__remove_external_artifact___MYSQL.sql',415836704,'hawkbit','2019-03-07 16:10:59',3,1),(12,12,'1.9.0','add rollout groups created   ','SQL','MYSQL/V1_9_0__add_rollout_groups_created___MYSQL.sql',1685327539,'hawkbit','2019-03-07 16:10:59',2,1);
/*!40000 ALTER TABLE `schema_version` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sp_action`
--

DROP TABLE IF EXISTS `sp_action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sp_action` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` bigint(20) DEFAULT NULL,
  `created_by` varchar(64) DEFAULT NULL,
  `last_modified_at` bigint(20) DEFAULT NULL,
  `last_modified_by` varchar(64) DEFAULT NULL,
  `optlock_revision` bigint(20) DEFAULT NULL,
  `tenant` varchar(40) NOT NULL,
  `active` bit(1) DEFAULT NULL,
  `forced_time` bigint(20) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `distribution_set` bigint(20) NOT NULL,
  `target` bigint(20) NOT NULL,
  `rollout` bigint(20) DEFAULT NULL,
  `rolloutgroup` bigint(20) DEFAULT NULL,
  `action_type` int(11) NOT NULL,
  `maintenance_cron_schedule` varchar(40) DEFAULT NULL,
  `maintenance_duration` varchar(40) DEFAULT NULL,
  `maintenance_time_zone` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sp_idx_action_01` (`tenant`,`distribution_set`),
  KEY `sp_idx_action_02` (`tenant`,`target`,`active`),
  KEY `sp_idx_action_prim` (`tenant`,`id`),
  KEY `fk_action_ds` (`distribution_set`),
  KEY `fk_targ_act_hist_targ` (`target`),
  KEY `fk_action_rollout` (`rollout`),
  KEY `fk_action_rolloutgroup` (`rolloutgroup`),
  CONSTRAINT `fk_action_ds` FOREIGN KEY (`distribution_set`) REFERENCES `sp_distribution_set` (`id`),
  CONSTRAINT `fk_action_rollout` FOREIGN KEY (`rollout`) REFERENCES `sp_rollout` (`id`),
  CONSTRAINT `fk_action_rolloutgroup` FOREIGN KEY (`rolloutgroup`) REFERENCES `sp_rolloutgroup` (`id`),
  CONSTRAINT `fk_targ_act_hist_targ` FOREIGN KEY (`target`) REFERENCES `sp_target` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sp_action`
--

LOCK TABLES `sp_action` WRITE;
/*!40000 ALTER TABLE `sp_action` DISABLE KEYS */;
INSERT INTO `sp_action` VALUES (1,1551975366225,'test',1551975382340,'test',2,'DEFAULT','',0,5,1,1,NULL,NULL,0,NULL,NULL,NULL),(2,1551975382333,'test',1551975382333,'test',1,'DEFAULT','',0,3,3,1,NULL,NULL,0,NULL,NULL,NULL),(3,1551975391415,'test',1551975391415,'test',1,'DEFAULT','',0,3,2,2,NULL,NULL,0,NULL,NULL,NULL),(4,1551975395587,'test',1551975395587,'test',1,'DEFAULT','',0,3,1,3,NULL,NULL,0,NULL,NULL,NULL);
/*!40000 ALTER TABLE `sp_action` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sp_action_status`
--

DROP TABLE IF EXISTS `sp_action_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sp_action_status` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` bigint(20) DEFAULT NULL,
  `created_by` varchar(64) DEFAULT NULL,
  `last_modified_at` bigint(20) DEFAULT NULL,
  `last_modified_by` varchar(64) DEFAULT NULL,
  `optlock_revision` bigint(20) DEFAULT NULL,
  `tenant` varchar(40) NOT NULL,
  `target_occurred_at` bigint(20) NOT NULL,
  `status` int(11) NOT NULL,
  `action` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `sp_idx_action_status_02` (`tenant`,`action`,`status`),
  KEY `sp_idx_action_status_prim` (`tenant`,`id`),
  KEY `fk_act_stat_action` (`action`),
  CONSTRAINT `fk_act_stat_action` FOREIGN KEY (`action`) REFERENCES `sp_action` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sp_action_status`
--

LOCK TABLES `sp_action_status` WRITE;
/*!40000 ALTER TABLE `sp_action_status` DISABLE KEYS */;
INSERT INTO `sp_action_status` VALUES (1,1551975366325,'test',1551975366325,'test',1,'DEFAULT',1551975366225,3,1),(2,1551975382335,'test',1551975382335,'test',1,'DEFAULT',1551975382333,3,2),(3,1551975382319,'test',1551975382319,'test',1,'DEFAULT',1551975382313,5,1),(4,1551975391416,'test',1551975391416,'test',1,'DEFAULT',1551975391415,3,3),(5,1551975395589,'test',1551975395589,'test',1,'DEFAULT',1551975395587,3,4);
/*!40000 ALTER TABLE `sp_action_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sp_action_status_messages`
--

DROP TABLE IF EXISTS `sp_action_status_messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sp_action_status_messages` (
  `action_status_id` bigint(20) NOT NULL,
  `detail_message` varchar(512) NOT NULL,
  KEY `sp_idx_action_status_msgs_01` (`action_status_id`),
  CONSTRAINT `fk_stat_msg_act_stat` FOREIGN KEY (`action_status_id`) REFERENCES `sp_action_status` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sp_action_status_messages`
--

LOCK TABLES `sp_action_status_messages` WRITE;
/*!40000 ALTER TABLE `sp_action_status_messages` DISABLE KEYS */;
INSERT INTO `sp_action_status_messages` VALUES (3,'Update Server: cancel obsolete action due to new update');
/*!40000 ALTER TABLE `sp_action_status_messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sp_artifact`
--

DROP TABLE IF EXISTS `sp_artifact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sp_artifact` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` bigint(20) DEFAULT NULL,
  `created_by` varchar(64) DEFAULT NULL,
  `last_modified_at` bigint(20) DEFAULT NULL,
  `last_modified_by` varchar(64) DEFAULT NULL,
  `optlock_revision` bigint(20) DEFAULT NULL,
  `tenant` varchar(40) NOT NULL,
  `md5_hash` varchar(32) DEFAULT NULL,
  `file_size` bigint(20) DEFAULT NULL,
  `provided_file_name` varchar(256) DEFAULT NULL,
  `sha1_hash` varchar(40) NOT NULL,
  `software_module` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `sp_idx_artifact_01` (`tenant`,`software_module`),
  KEY `sp_idx_artifact_prim` (`tenant`,`id`),
  KEY `fk_assigned_sm` (`software_module`),
  KEY `sp_idx_artifact_02` (`tenant`,`sha1_hash`),
  CONSTRAINT `fk_assigned_sm` FOREIGN KEY (`software_module`) REFERENCES `sp_base_software_module` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sp_artifact`
--

LOCK TABLES `sp_artifact` WRITE;
/*!40000 ALTER TABLE `sp_artifact` DISABLE KEYS */;
INSERT INTO `sp_artifact` VALUES (1,1551975265210,'test',1551975265210,'test',1,'DEFAULT','94424c5ce3f8c57a5b26d02f37dc06fc',7,'test_4','07fa4d03ac54d0b2a52f23d8e878c96db7aadd0a',3),(2,1551975273780,'test',1551975273780,'test',1,'DEFAULT','b0b3b0dbf5330e3179c6ae3e0ac524c9',7,'test_2','a096a9d3cb96fa4cf6c63bd736a84cb7a7e4b61e',2),(3,1551975277575,'test',1551975277575,'test',1,'DEFAULT','2244fbd6bee5dcbe312e387c062ce6e6',7,'test_3','cde0c01b26634f869bb876326e4fbe969792bf94',2),(4,1551975287265,'test',1551975287265,'test',1,'DEFAULT','2490a3d39b0004e4afeb517ef0ddbe2d',7,'test_1','b54e43082887d1e7cdb10b7a21fe4a1e56b44b5a',1);
/*!40000 ALTER TABLE `sp_artifact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sp_base_software_module`
--

DROP TABLE IF EXISTS `sp_base_software_module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sp_base_software_module` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` bigint(20) DEFAULT NULL,
  `created_by` varchar(64) DEFAULT NULL,
  `last_modified_at` bigint(20) DEFAULT NULL,
  `last_modified_by` varchar(64) DEFAULT NULL,
  `optlock_revision` bigint(20) DEFAULT NULL,
  `tenant` varchar(40) NOT NULL,
  `description` varchar(512) DEFAULT NULL,
  `name` varchar(64) NOT NULL,
  `version` varchar(64) NOT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `vendor` varchar(256) DEFAULT NULL,
  `module_type` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_base_sw_mod` (`module_type`,`name`,`version`,`tenant`),
  KEY `sp_idx_base_sw_module_01` (`tenant`,`deleted`,`name`,`version`),
  KEY `sp_idx_base_sw_module_02` (`tenant`,`deleted`,`module_type`),
  KEY `sp_idx_base_sw_module_prim` (`tenant`,`id`),
  CONSTRAINT `fk_module_type` FOREIGN KEY (`module_type`) REFERENCES `sp_software_module_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sp_base_software_module`
--

LOCK TABLES `sp_base_software_module` WRITE;
/*!40000 ALTER TABLE `sp_base_software_module` DISABLE KEYS */;
INSERT INTO `sp_base_software_module` VALUES (1,1551975246120,'test',1551975287270,'test',2,'DEFAULT','','app','1','\0','',1),(2,1551975253978,'test',1551975277579,'test',3,'DEFAULT','','apps','1','\0','',1),(3,1551975260304,'test',1551975265228,'test',2,'DEFAULT','','os','1','\0','',2);
/*!40000 ALTER TABLE `sp_base_software_module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sp_distribution_set`
--

DROP TABLE IF EXISTS `sp_distribution_set`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sp_distribution_set` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` bigint(20) DEFAULT NULL,
  `created_by` varchar(64) DEFAULT NULL,
  `last_modified_at` bigint(20) DEFAULT NULL,
  `last_modified_by` varchar(64) DEFAULT NULL,
  `optlock_revision` bigint(20) DEFAULT NULL,
  `tenant` varchar(40) NOT NULL,
  `description` varchar(512) DEFAULT NULL,
  `name` varchar(64) NOT NULL,
  `version` varchar(64) NOT NULL,
  `complete` bit(1) DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `required_migration_step` bit(1) DEFAULT NULL,
  `ds_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_distrib_set` (`name`,`version`,`tenant`),
  KEY `sp_idx_distribution_set_prim` (`tenant`,`id`),
  KEY `fk_ds_dstype_ds` (`ds_id`),
  KEY `sp_idx_distribution_set_01` (`tenant`,`deleted`,`complete`),
  CONSTRAINT `fk_ds_dstype_ds` FOREIGN KEY (`ds_id`) REFERENCES `sp_distribution_set_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sp_distribution_set`
--

LOCK TABLES `sp_distribution_set` WRITE;
/*!40000 ALTER TABLE `sp_distribution_set` DISABLE KEYS */;
INSERT INTO `sp_distribution_set` VALUES (1,1551975312567,'test',1551975318503,'test',2,'DEFAULT','','osWithApps','1','','\0','\0',1),(2,1551975326181,'test',1551975329294,'test',2,'DEFAULT','','os','1','','\0','\0',2),(3,1551975338674,'test',1551975342114,'test',2,'DEFAULT','','app','1','','\0','\0',3);
/*!40000 ALTER TABLE `sp_distribution_set` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sp_distribution_set_type`
--

DROP TABLE IF EXISTS `sp_distribution_set_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sp_distribution_set_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` bigint(20) DEFAULT NULL,
  `created_by` varchar(64) DEFAULT NULL,
  `last_modified_at` bigint(20) DEFAULT NULL,
  `last_modified_by` varchar(64) DEFAULT NULL,
  `optlock_revision` bigint(20) DEFAULT NULL,
  `tenant` varchar(40) NOT NULL,
  `description` varchar(512) DEFAULT NULL,
  `name` varchar(64) NOT NULL,
  `colour` varchar(16) DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `type_key` varchar(64) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_dst_name` (`name`,`tenant`),
  UNIQUE KEY `uk_dst_key` (`type_key`,`tenant`),
  KEY `sp_idx_distribution_set_type_01` (`tenant`,`deleted`),
  KEY `sp_idx_distribution_set_type_prim` (`tenant`,`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sp_distribution_set_type`
--

LOCK TABLES `sp_distribution_set_type` WRITE;
/*!40000 ALTER TABLE `sp_distribution_set_type` DISABLE KEYS */;
INSERT INTO `sp_distribution_set_type` VALUES (1,1551975231012,'system',1551975231012,'system',1,'DEFAULT','Default type with Firmware/OS and optional app(s).','OS with app(s)',NULL,'\0','os_app'),(2,1551975230999,'system',1551975230999,'system',1,'DEFAULT','Default type with Firmware/OS only.','OS only',NULL,'\0','os'),(3,1551975231009,'system',1551975231009,'system',1,'DEFAULT','Default type with app(s) only.','App(s) only',NULL,'\0','app');
/*!40000 ALTER TABLE `sp_distribution_set_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sp_distributionset_tag`
--

DROP TABLE IF EXISTS `sp_distributionset_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sp_distributionset_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` bigint(20) DEFAULT NULL,
  `created_by` varchar(64) DEFAULT NULL,
  `last_modified_at` bigint(20) DEFAULT NULL,
  `last_modified_by` varchar(64) DEFAULT NULL,
  `optlock_revision` bigint(20) DEFAULT NULL,
  `tenant` varchar(40) NOT NULL,
  `description` varchar(512) DEFAULT NULL,
  `name` varchar(64) NOT NULL,
  `colour` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_ds_tag` (`name`,`tenant`),
  KEY `sp_idx_distribution_set_tag_prim` (`tenant`,`id`),
  KEY `sp_idx_distribution_set_tag_01` (`tenant`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sp_distributionset_tag`
--

LOCK TABLES `sp_distributionset_tag` WRITE;
/*!40000 ALTER TABLE `sp_distributionset_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `sp_distributionset_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sp_ds_dstag`
--

DROP TABLE IF EXISTS `sp_ds_dstag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sp_ds_dstag` (
  `ds` bigint(20) NOT NULL,
  `TAG` bigint(20) NOT NULL,
  PRIMARY KEY (`ds`,`TAG`),
  KEY `fk_ds_dstag_tag` (`TAG`),
  CONSTRAINT `fk_ds_dstag_ds` FOREIGN KEY (`ds`) REFERENCES `sp_distribution_set` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_ds_dstag_tag` FOREIGN KEY (`TAG`) REFERENCES `sp_distributionset_tag` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sp_ds_dstag`
--

LOCK TABLES `sp_ds_dstag` WRITE;
/*!40000 ALTER TABLE `sp_ds_dstag` DISABLE KEYS */;
/*!40000 ALTER TABLE `sp_ds_dstag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sp_ds_metadata`
--

DROP TABLE IF EXISTS `sp_ds_metadata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sp_ds_metadata` (
  `meta_key` varchar(128) NOT NULL,
  `meta_value` varchar(4000) DEFAULT NULL,
  `ds_id` bigint(20) NOT NULL,
  PRIMARY KEY (`ds_id`,`meta_key`),
  CONSTRAINT `fk_metadata_ds` FOREIGN KEY (`ds_id`) REFERENCES `sp_distribution_set` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sp_ds_metadata`
--

LOCK TABLES `sp_ds_metadata` WRITE;
/*!40000 ALTER TABLE `sp_ds_metadata` DISABLE KEYS */;
/*!40000 ALTER TABLE `sp_ds_metadata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sp_ds_module`
--

DROP TABLE IF EXISTS `sp_ds_module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sp_ds_module` (
  `ds_id` bigint(20) NOT NULL,
  `module_id` bigint(20) NOT NULL,
  PRIMARY KEY (`ds_id`,`module_id`),
  KEY `fk_ds_module_module` (`module_id`),
  CONSTRAINT `fk_ds_module_ds` FOREIGN KEY (`ds_id`) REFERENCES `sp_distribution_set` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_ds_module_module` FOREIGN KEY (`module_id`) REFERENCES `sp_base_software_module` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sp_ds_module`
--

LOCK TABLES `sp_ds_module` WRITE;
/*!40000 ALTER TABLE `sp_ds_module` DISABLE KEYS */;
INSERT INTO `sp_ds_module` VALUES (1,1),(1,2),(1,3),(2,3),(3,1);
/*!40000 ALTER TABLE `sp_ds_module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sp_ds_type_element`
--

DROP TABLE IF EXISTS `sp_ds_type_element`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sp_ds_type_element` (
  `mandatory` bit(1) DEFAULT NULL,
  `distribution_set_type` bigint(20) NOT NULL,
  `software_module_type` bigint(20) NOT NULL,
  PRIMARY KEY (`distribution_set_type`,`software_module_type`),
  KEY `fk_ds_type_element_smtype` (`software_module_type`),
  CONSTRAINT `fk_ds_type_element_element` FOREIGN KEY (`distribution_set_type`) REFERENCES `sp_distribution_set_type` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_ds_type_element_smtype` FOREIGN KEY (`software_module_type`) REFERENCES `sp_software_module_type` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sp_ds_type_element`
--

LOCK TABLES `sp_ds_type_element` WRITE;
/*!40000 ALTER TABLE `sp_ds_type_element` DISABLE KEYS */;
INSERT INTO `sp_ds_type_element` VALUES ('\0',1,1),('',1,2),('',2,2),('',3,1);
/*!40000 ALTER TABLE `sp_ds_type_element` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sp_rollout`
--

DROP TABLE IF EXISTS `sp_rollout`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sp_rollout` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` bigint(20) DEFAULT NULL,
  `created_by` varchar(64) DEFAULT NULL,
  `last_modified_at` bigint(20) DEFAULT NULL,
  `last_modified_by` varchar(64) DEFAULT NULL,
  `optlock_revision` bigint(20) DEFAULT NULL,
  `tenant` varchar(40) NOT NULL,
  `description` varchar(512) DEFAULT NULL,
  `name` varchar(64) NOT NULL,
  `last_check` bigint(20) DEFAULT NULL,
  `group_theshold` float DEFAULT NULL,
  `status` int(11) NOT NULL,
  `distribution_set` bigint(20) NOT NULL,
  `target_filter` varchar(1024) DEFAULT NULL,
  `forced_time` bigint(20) DEFAULT NULL,
  `total_targets` bigint(20) DEFAULT NULL,
  `rollout_groups_created` bigint(20) DEFAULT NULL,
  `start_at` bigint(20) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  `action_type` int(11) NOT NULL,
  `approval_decided_by` varchar(64) DEFAULT NULL,
  `approval_remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_rollout` (`name`,`tenant`),
  KEY `fk_rollout_ds` (`distribution_set`),
  CONSTRAINT `fk_rollout_ds` FOREIGN KEY (`distribution_set`) REFERENCES `sp_distribution_set` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sp_rollout`
--

LOCK TABLES `sp_rollout` WRITE;
/*!40000 ALTER TABLE `sp_rollout` DISABLE KEYS */;
/*!40000 ALTER TABLE `sp_rollout` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sp_rolloutgroup`
--

DROP TABLE IF EXISTS `sp_rolloutgroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sp_rolloutgroup` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` bigint(20) DEFAULT NULL,
  `created_by` varchar(64) DEFAULT NULL,
  `last_modified_at` bigint(20) DEFAULT NULL,
  `last_modified_by` varchar(64) DEFAULT NULL,
  `optlock_revision` bigint(20) DEFAULT NULL,
  `tenant` varchar(40) NOT NULL,
  `description` varchar(512) DEFAULT NULL,
  `name` varchar(64) NOT NULL,
  `error_condition` int(11) DEFAULT NULL,
  `error_condition_exp` varchar(512) DEFAULT NULL,
  `error_action` int(11) DEFAULT NULL,
  `error_action_exp` varchar(512) DEFAULT NULL,
  `success_condition` int(11) NOT NULL,
  `success_condition_exp` varchar(512) NOT NULL,
  `success_action` int(11) NOT NULL,
  `success_action_exp` varchar(512) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `rollout` bigint(20) NOT NULL,
  `total_targets` bigint(20) DEFAULT NULL,
  `target_percentage` float DEFAULT NULL,
  `target_filter` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_rolloutgroup` (`name`,`rollout`,`tenant`),
  KEY `fk_rolloutgroup_rollout` (`rollout`),
  KEY `fk_rolloutgroup_rolloutgroup` (`parent_id`),
  CONSTRAINT `fk_rolloutgroup_rollout` FOREIGN KEY (`rollout`) REFERENCES `sp_rollout` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sp_rolloutgroup`
--

LOCK TABLES `sp_rolloutgroup` WRITE;
/*!40000 ALTER TABLE `sp_rolloutgroup` DISABLE KEYS */;
/*!40000 ALTER TABLE `sp_rolloutgroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sp_rollouttargetgroup`
--

DROP TABLE IF EXISTS `sp_rollouttargetgroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sp_rollouttargetgroup` (
  `target_Id` bigint(20) NOT NULL,
  `rolloutGroup_Id` bigint(20) NOT NULL,
  PRIMARY KEY (`rolloutGroup_Id`,`target_Id`),
  KEY `fk_rollouttargetgroup_target` (`target_Id`),
  CONSTRAINT `fk_rollouttargetgroup_rolloutgroup` FOREIGN KEY (`rolloutGroup_Id`) REFERENCES `sp_rolloutgroup` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_rollouttargetgroup_target` FOREIGN KEY (`target_Id`) REFERENCES `sp_target` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sp_rollouttargetgroup`
--

LOCK TABLES `sp_rollouttargetgroup` WRITE;
/*!40000 ALTER TABLE `sp_rollouttargetgroup` DISABLE KEYS */;
/*!40000 ALTER TABLE `sp_rollouttargetgroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sp_software_module_type`
--

DROP TABLE IF EXISTS `sp_software_module_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sp_software_module_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` bigint(20) DEFAULT NULL,
  `created_by` varchar(64) DEFAULT NULL,
  `last_modified_at` bigint(20) DEFAULT NULL,
  `last_modified_by` varchar(64) DEFAULT NULL,
  `optlock_revision` bigint(20) DEFAULT NULL,
  `tenant` varchar(40) NOT NULL,
  `description` varchar(512) DEFAULT NULL,
  `name` varchar(64) NOT NULL,
  `colour` varchar(16) DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `type_key` varchar(64) NOT NULL,
  `max_ds_assignments` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_smt_type_key` (`type_key`,`tenant`),
  UNIQUE KEY `uk_smt_name` (`name`,`tenant`),
  KEY `sp_idx_software_module_type_01` (`tenant`,`deleted`),
  KEY `sp_idx_software_module_type_prim` (`tenant`,`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sp_software_module_type`
--

LOCK TABLES `sp_software_module_type` WRITE;
/*!40000 ALTER TABLE `sp_software_module_type` DISABLE KEYS */;
INSERT INTO `sp_software_module_type` VALUES (1,1551975230809,'system',1551975230809,'system',1,'DEFAULT','Application Addons','Application',NULL,'\0','application',2147483647),(2,1551975230869,'system',1551975230869,'system',1,'DEFAULT','Core firmware or operationg system','OS',NULL,'\0','os',1);
/*!40000 ALTER TABLE `sp_software_module_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sp_sw_metadata`
--

DROP TABLE IF EXISTS `sp_sw_metadata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sp_sw_metadata` (
  `meta_key` varchar(128) NOT NULL,
  `meta_value` varchar(4000) DEFAULT NULL,
  `sw_id` bigint(20) NOT NULL,
  `target_visible` bit(1) DEFAULT NULL,
  PRIMARY KEY (`meta_key`,`sw_id`),
  KEY `fk_metadata_sw` (`sw_id`),
  CONSTRAINT `fk_metadata_sw` FOREIGN KEY (`sw_id`) REFERENCES `sp_base_software_module` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sp_sw_metadata`
--

LOCK TABLES `sp_sw_metadata` WRITE;
/*!40000 ALTER TABLE `sp_sw_metadata` DISABLE KEYS */;
/*!40000 ALTER TABLE `sp_sw_metadata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sp_target`
--

DROP TABLE IF EXISTS `sp_target`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sp_target` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` bigint(20) DEFAULT NULL,
  `created_by` varchar(64) DEFAULT NULL,
  `last_modified_at` bigint(20) DEFAULT NULL,
  `last_modified_by` varchar(64) DEFAULT NULL,
  `optlock_revision` bigint(20) DEFAULT NULL,
  `tenant` varchar(40) NOT NULL,
  `description` varchar(512) DEFAULT NULL,
  `name` varchar(64) NOT NULL,
  `controller_id` varchar(64) NOT NULL,
  `sec_token` varchar(128) NOT NULL,
  `assigned_distribution_set` bigint(20) DEFAULT NULL,
  `install_date` bigint(20) DEFAULT NULL,
  `address` varchar(512) DEFAULT NULL,
  `last_target_query` bigint(20) DEFAULT NULL,
  `request_controller_attributes` bit(1) NOT NULL,
  `installed_distribution_set` bigint(20) DEFAULT NULL,
  `update_status` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_controller_id` (`controller_id`,`tenant`),
  KEY `sp_idx_target_01` (`tenant`,`name`,`assigned_distribution_set`),
  KEY `sp_idx_target_03` (`tenant`,`controller_id`,`assigned_distribution_set`),
  KEY `sp_idx_target_04` (`tenant`,`created_at`),
  KEY `sp_idx_target_prim` (`tenant`,`id`),
  KEY `fk_target_assign_ds` (`assigned_distribution_set`),
  KEY `fk_target_inst_ds` (`installed_distribution_set`),
  CONSTRAINT `fk_target_assign_ds` FOREIGN KEY (`assigned_distribution_set`) REFERENCES `sp_distribution_set` (`id`),
  CONSTRAINT `fk_target_inst_ds` FOREIGN KEY (`installed_distribution_set`) REFERENCES `sp_distribution_set` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sp_target`
--

LOCK TABLES `sp_target` WRITE;
/*!40000 ALTER TABLE `sp_target` DISABLE KEYS */;
INSERT INTO `sp_target` VALUES (1,1551975352117,'test',1551975382326,'test',3,'DEFAULT','','target1','target1','447fb8b5b3ea156470e852b94166a673',3,NULL,NULL,NULL,'',NULL,2),(2,1551975356776,'test',1551975391405,'test',2,'DEFAULT','','target2','target2','0fe7b8c9de2102ec6bf305b6f66df5b2',2,NULL,NULL,NULL,'',NULL,2),(3,1551975361488,'test',1551975395581,'test',2,'DEFAULT','','target3','target3','4a28d893bb841def706073c789c0f3a7',1,NULL,NULL,NULL,'',NULL,2);
/*!40000 ALTER TABLE `sp_target` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sp_target_attributes`
--

DROP TABLE IF EXISTS `sp_target_attributes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sp_target_attributes` (
  `target_id` bigint(20) NOT NULL,
  `attribute_value` varchar(128) DEFAULT NULL,
  `attribute_key` varchar(32) NOT NULL,
  PRIMARY KEY (`target_id`,`attribute_key`),
  CONSTRAINT `fk_targ_attrib_target` FOREIGN KEY (`target_id`) REFERENCES `sp_target` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sp_target_attributes`
--

LOCK TABLES `sp_target_attributes` WRITE;
/*!40000 ALTER TABLE `sp_target_attributes` DISABLE KEYS */;
/*!40000 ALTER TABLE `sp_target_attributes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sp_target_filter_query`
--

DROP TABLE IF EXISTS `sp_target_filter_query`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sp_target_filter_query` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` bigint(20) DEFAULT NULL,
  `created_by` varchar(64) DEFAULT NULL,
  `last_modified_at` bigint(20) DEFAULT NULL,
  `last_modified_by` varchar(64) DEFAULT NULL,
  `optlock_revision` bigint(20) DEFAULT NULL,
  `tenant` varchar(40) NOT NULL,
  `name` varchar(64) NOT NULL,
  `query` varchar(1024) NOT NULL,
  `auto_assign_distribution_set` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_custom_filter_name` (`name`,`tenant`),
  KEY `fk_filter_auto_assign_ds` (`auto_assign_distribution_set`),
  CONSTRAINT `fk_filter_auto_assign_ds` FOREIGN KEY (`auto_assign_distribution_set`) REFERENCES `sp_distribution_set` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sp_target_filter_query`
--

LOCK TABLES `sp_target_filter_query` WRITE;
/*!40000 ALTER TABLE `sp_target_filter_query` DISABLE KEYS */;
/*!40000 ALTER TABLE `sp_target_filter_query` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sp_target_tag`
--

DROP TABLE IF EXISTS `sp_target_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sp_target_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` bigint(20) DEFAULT NULL,
  `created_by` varchar(64) DEFAULT NULL,
  `last_modified_at` bigint(20) DEFAULT NULL,
  `last_modified_by` varchar(64) DEFAULT NULL,
  `optlock_revision` bigint(20) DEFAULT NULL,
  `tenant` varchar(40) NOT NULL,
  `description` varchar(512) DEFAULT NULL,
  `name` varchar(64) NOT NULL,
  `colour` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_targ_tag` (`name`,`tenant`),
  KEY `sp_idx_target_tag_prim` (`tenant`,`id`),
  KEY `sp_idx_target_tag_01` (`tenant`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sp_target_tag`
--

LOCK TABLES `sp_target_tag` WRITE;
/*!40000 ALTER TABLE `sp_target_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `sp_target_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sp_target_target_tag`
--

DROP TABLE IF EXISTS `sp_target_target_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sp_target_target_tag` (
  `target` bigint(20) NOT NULL,
  `tag` bigint(20) NOT NULL,
  PRIMARY KEY (`target`,`tag`),
  KEY `fk_targ_targtag_tag` (`tag`),
  CONSTRAINT `fk_targ_targtag_tag` FOREIGN KEY (`tag`) REFERENCES `sp_target_tag` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_targ_targtag_target` FOREIGN KEY (`target`) REFERENCES `sp_target` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sp_target_target_tag`
--

LOCK TABLES `sp_target_target_tag` WRITE;
/*!40000 ALTER TABLE `sp_target_target_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `sp_target_target_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sp_tenant`
--

DROP TABLE IF EXISTS `sp_tenant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sp_tenant` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` bigint(20) DEFAULT NULL,
  `created_by` varchar(64) DEFAULT NULL,
  `last_modified_at` bigint(20) DEFAULT NULL,
  `last_modified_by` varchar(64) DEFAULT NULL,
  `optlock_revision` bigint(20) DEFAULT NULL,
  `tenant` varchar(40) NOT NULL,
  `default_ds_type` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenantmd_tenant` (`tenant`),
  KEY `sp_idx_tenant_prim` (`tenant`,`id`),
  KEY `fk_tenant_md_default_ds_type` (`default_ds_type`),
  CONSTRAINT `fk_tenant_md_default_ds_type` FOREIGN KEY (`default_ds_type`) REFERENCES `sp_distribution_set_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sp_tenant`
--

LOCK TABLES `sp_tenant` WRITE;
/*!40000 ALTER TABLE `sp_tenant` DISABLE KEYS */;
INSERT INTO `sp_tenant` VALUES (1,1551975231074,'system',1551975231074,'system',1,'Default',1);
/*!40000 ALTER TABLE `sp_tenant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sp_tenant_configuration`
--

DROP TABLE IF EXISTS `sp_tenant_configuration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sp_tenant_configuration` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` bigint(20) DEFAULT NULL,
  `created_by` varchar(64) DEFAULT NULL,
  `last_modified_at` bigint(20) DEFAULT NULL,
  `last_modified_by` varchar(64) DEFAULT NULL,
  `optlock_revision` bigint(20) DEFAULT NULL,
  `tenant` varchar(40) NOT NULL,
  `conf_key` varchar(128) NOT NULL,
  `conf_value` varchar(512) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_key` (`conf_key`,`tenant`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sp_tenant_configuration`
--

LOCK TABLES `sp_tenant_configuration` WRITE;
/*!40000 ALTER TABLE `sp_tenant_configuration` DISABLE KEYS */;
INSERT INTO `sp_tenant_configuration` VALUES (1,1551975294139,'test',1551975294139,'test',1,'DEFAULT','authentication.gatewaytoken.enabled','true'),(2,1551975294188,'test',1551975294188,'test',1,'DEFAULT','authentication.gatewaytoken.key','66076ab945a127dd80b15e9011995109');
/*!40000 ALTER TABLE `sp_tenant_configuration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uf_tenant_usage`
--

DROP TABLE IF EXISTS `uf_tenant_usage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `uf_tenant_usage` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cleared_at` bigint(20) DEFAULT NULL,
  `tenant` varchar(40) NOT NULL,
  `target_downloaded_bytes` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `cf_idx_tenant_usage` (`tenant`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uf_tenant_usage`
--

LOCK TABLES `uf_tenant_usage` WRITE;
/*!40000 ALTER TABLE `uf_tenant_usage` DISABLE KEYS */;
/*!40000 ALTER TABLE `uf_tenant_usage` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-03-07 16:19:21
