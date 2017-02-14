/*
Navicat MySQL Data Transfer

Source Server         : localhsot
Source Server Version : 50703
Source Host           : localhost:3306
Source Database       : caihong_cms

Target Server Type    : MYSQL
Target Server Version : 50703
File Encoding         : 65001

Date: 2017-02-14 20:02:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for jc_patient
-- ----------------------------
DROP TABLE IF EXISTS `jc_patient`;
CREATE TABLE `jc_patient` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `name` varchar(50) NOT NULL,
  `gender` tinyint(1) DEFAULT NULL COMMENT '1,男 2女',
  `telphone` varchar(50) DEFAULT NULL,
  `birthday` varchar(20) DEFAULT NULL,
  `id_no` varchar(50) DEFAULT NULL,
  `job` int(11) DEFAULT NULL COMMENT '1，普通员工\r\n2，农民\r\n3，工人\r\n4，其他',
  `user_id` int(11) DEFAULT NULL COMMENT '可以为空',
  `time` datetime DEFAULT CURRENT_TIMESTAMP,
  `work_address` varchar(200) DEFAULT NULL,
  `home_address` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jc_patient
-- ----------------------------

-- ----------------------------
-- Table structure for jc_reserve
-- ----------------------------
DROP TABLE IF EXISTS `jc_reserve`;
CREATE TABLE `jc_reserve` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `price` double DEFAULT NULL,
  `doctor_id` int(11) DEFAULT NULL,
  `expect_time` datetime DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '1，申请中\r\n2，已安排\r\n3，已诊断\r\n4，已取消',
  `reserve_user_id` int(11) NOT NULL,
  `time` datetime DEFAULT CURRENT_TIMESTAMP,
  `pay_status` tinyint(1) NOT NULL DEFAULT '2' COMMENT '1，已支付\r\n2，未支付',
  `order_num` varchar(100) DEFAULT NULL,
  `diagnosis` text COMMENT '医生诊断',
  `clinical_diagnosis` text,
  `patient_id` bigint(20) NOT NULL COMMENT 'id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `patient_id` (`patient_id`),
  CONSTRAINT `jc_reserve_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `jc_patient` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jc_reserve
-- ----------------------------

-- ----------------------------
-- Table structure for jc_reserve_attachment
-- ----------------------------
DROP TABLE IF EXISTS `jc_reserve_attachment`;
CREATE TABLE `jc_reserve_attachment` (
  `reserve_id` bigint(20) NOT NULL COMMENT 'id',
  `priority` int(11) NOT NULL COMMENT '排练顺序',
  `path` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `filename` varchar(255) DEFAULT NULL,
  KEY `reserve_id` (`reserve_id`),
  CONSTRAINT `jc_reserve_attachment_ibfk_1` FOREIGN KEY (`reserve_id`) REFERENCES `jc_reserve` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jc_reserve_attachment
-- ----------------------------
