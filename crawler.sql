/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 50635
Source Host           : localhost:3306
Source Database       : crawler

Target Server Type    : MYSQL
Target Server Version : 50635
File Encoding         : 65001

Date: 2017-09-28 16:32:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for qidian_book
-- ----------------------------
DROP TABLE IF EXISTS `qidian_book`;
CREATE TABLE `qidian_book` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) DEFAULT NULL,
  `sub_type` varchar(255) DEFAULT NULL,
  `book` varchar(255) DEFAULT NULL,
  `writer` varchar(255) DEFAULT NULL,
  `words_num` varchar(255) DEFAULT NULL,
  `finish_time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7501 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tieba_info
-- ----------------------------
DROP TABLE IF EXISTS `tieba_info`;
CREATE TABLE `tieba_info` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL,
  `tiezi_num` int(11) DEFAULT NULL,
  `follow_num` int(11) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=131 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tieba_type
-- ----------------------------
DROP TABLE IF EXISTS `tieba_type`;
CREATE TABLE `tieba_type` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tiezi_info
-- ----------------------------
DROP TABLE IF EXISTS `tiezi_info`;
CREATE TABLE `tiezi_info` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `tieba_name` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `poster` varchar(255) DEFAULT NULL,
  `reply_num` int(11) DEFAULT NULL,
  `post_time` datetime DEFAULT NULL,
  `last_reply` varchar(255) DEFAULT NULL,
  `last_time` datetime DEFAULT NULL,
  `tiezi_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8976 DEFAULT CHARSET=utf8;
