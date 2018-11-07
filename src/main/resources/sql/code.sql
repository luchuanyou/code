/*
Navicat MySQL Data Transfer

Source Server         : root
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : code

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-11-07 11:53:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sequence
-- ----------------------------
DROP TABLE IF EXISTS `sequence`;
CREATE TABLE `sequence` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `seq_name` varchar(50) NOT NULL COMMENT '序列名称',
  `current_val` bigint(11) NOT NULL DEFAULT '0' COMMENT '当前值',
  `increment_val` int(2) NOT NULL DEFAULT '1' COMMENT '步长（单次递增）',
  `cycle` int(2) DEFAULT '0' COMMENT '是否循环 0：不循环，1：循环',
  `last_date` date DEFAULT NULL COMMENT '上次更新时间',
  `cache_size` int(6) DEFAULT '0' COMMENT '缓存大小',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_seq_name` (`seq_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='序列信息表';

-- ----------------------------
-- Records of sequence
-- ----------------------------
INSERT INTO `sequence` VALUES ('1', 'seq_user', '100', '1', '0', '2018-11-07', '10', '2018-11-07 11:52:23', '2018-06-11 17:52:28');

-- ----------------------------
-- Table structure for sequence_log
-- ----------------------------
DROP TABLE IF EXISTS `sequence_log`;
CREATE TABLE `sequence_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=40190 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sequence_log
-- ----------------------------

-- ----------------------------
-- Table structure for sequence_rule_list
-- ----------------------------
DROP TABLE IF EXISTS `sequence_rule_list`;
CREATE TABLE `sequence_rule_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `seq_name` varchar(50) NOT NULL COMMENT '序列名称',
  `type` int(3) DEFAULT '0' COMMENT '类型 0：序列，1：常量，2：日期',
  `content` varchar(255) DEFAULT NULL COMMENT '内容',
  `status` int(2) DEFAULT '0' COMMENT '状态 0：不可用，1：可用',
  `sort` int(5) DEFAULT '0' COMMENT '排序',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='序列规则信息表';

-- ----------------------------
-- Records of sequence_rule_list
-- ----------------------------
INSERT INTO `sequence_rule_list` VALUES ('1', 'seq_user', '0', '7', '1', '5', '2018-05-28 01:08:37', '2018-05-28 01:06:02');
INSERT INTO `sequence_rule_list` VALUES ('2', 'seq_user', '1', 'abc', '1', '4', '2018-05-31 14:14:59', '2018-05-28 01:07:02');
INSERT INTO `sequence_rule_list` VALUES ('3', 'seq_user', '2', 'yyyyMMdd', '1', '2', '2018-05-29 19:22:42', '2018-05-28 01:07:47');

-- ----------------------------
-- Function structure for currentVal
-- ----------------------------
DROP FUNCTION IF EXISTS `currentVal`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `currentVal`(v_seq_name VARCHAR(50)) RETURNS int(11)
begin
    declare value integer;
    set value = 0;
    select current_val into value  from sequence where seq_name = v_seq_name;
   return value;
end
;;
DELIMITER ;

-- ----------------------------
-- Function structure for nextVal
-- ----------------------------
DROP FUNCTION IF EXISTS `nextVal`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `nextVal`(v_seq_name VARCHAR(50)) RETURNS int(11)
begin
    update sequence set current_val = current_val + increment_val  where seq_name = v_seq_name;
    return currentVal(v_seq_name);
end
;;
DELIMITER ;
