/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 50730
Source Host           : localhost:3306
Source Database       : netty

Target Server Type    : MYSQL
Target Server Version : 50730
File Encoding         : 65001

Date: 2020-11-13 15:57:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for curve_message
-- ----------------------------
DROP TABLE IF EXISTS `curve_message`;
CREATE TABLE `curve_message` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `receive_byte_stream` varchar(4096) DEFAULT NULL,
  `reply_byte_stream` varchar(4096) DEFAULT NULL,
  `protocol_version` varchar(255) DEFAULT NULL,
  `protocol_type` varchar(255) DEFAULT NULL,
  `curve_date` varchar(6) DEFAULT NULL,
  `create_time` datetime(3) DEFAULT NULL,
  `update_time` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of curve_message
-- ----------------------------
INSERT INTO `curve_message` VALUES ('1', '68 3F 00 00 00 00 89 09 06 00 01 00 01 67 00 00 00 02 67 00 4D 09 0E 67 00 97 09 65 67 00 20 0A B0 67 00 F4 07 CE 67 00 10 08 DC 67 00 9C 0A E4 67 00 2A 08 21 68 00 9A 0A 00 00 00 00 00 0A 0B 14 ', '[B@3aae945a', 'PROTOCOL_NW_104', 'PROTOCOL_NW_104_PLAN', '41', '2020-11-13 10:10:00.662', '2020-11-13 10:10:00.662');
INSERT INTO `curve_message` VALUES ('2', '68 3F 00 00 00 00 89 09 06 00 01 00 01 67 00 00 00 02 67 00 4D 09 0E 67 00 97 09 65 67 00 20 0A B0 67 00 F4 07 CE 67 00 10 08 DC 67 00 9C 0A E4 67 00 2A 08 21 68 00 9A 0A 00 00 00 00 00 0A 0B 14 ', '68 3F 00 00 00 00 89 09 07 00 01 00 01 67 00 00 00 02 67 00 4D 09 0E 67 00 97 09 65 67 00 20 0A B0 67 00 F4 07 CE 67 00 10 08 DC 67 00 9C 0A E4 67 00 2A 08 21 68 00 9A 0A 00 00 00 00 00 0A 0B 14 ', 'PROTOCOL_NW_104', 'PROTOCOL_NW_104_PLAN', '41', '2020-11-13 10:12:32.154', '2020-11-13 10:12:32.154');
INSERT INTO `curve_message` VALUES ('3', '68 3F 00 00 00 00 89 09 06 00 01 00 01 67 00 00 00 02 67 00 4D 09 0E 67 00 97 09 65 67 00 20 0A B0 67 00 F4 07 CE 67 00 10 08 DC 67 00 9C 0A E4 67 00 2A 08 21 68 00 9A 0A 00 00 00 00 00 0A 0B 14 ', '68 3F 00 00 00 00 89 09 07 00 01 00 01 67 00 00 00 02 67 00 4D 09 0E 67 00 97 09 65 67 00 20 0A B0 67 00 F4 07 CE 67 00 10 08 DC 67 00 9C 0A E4 67 00 2A 08 21 68 00 9A 0A 00 00 00 00 00 0A 0B 14 ', 'PROTOCOL_NW_104', 'PROTOCOL_NW_104_PLAN', '201110', '2020-11-13 10:14:31.252', '2020-11-13 10:14:31.252');
