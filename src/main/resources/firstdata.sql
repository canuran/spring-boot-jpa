/*
Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `admin_id` varchar(20) NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `account` varchar(64) DEFAULT NULL,
  `gender` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('new_admin', '新管理', 'NEW', '1', 'new', '2', '2017-08-01 18:58:12');
INSERT INTO `admin` VALUES ('normal_admin', '管理员', '123456', '1', 'admin', '1', '2017-06-22 17:04:56');
INSERT INTO `admin` VALUES ('super_admin', '总管', 'ABCDEF', '1', 'super', '0', '2017-06-18 11:05:25');

-- ----------------------------
-- Table structure for admin_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role` (
  `admin_id` varchar(20) NOT NULL,
  `role_id` varchar(20) NOT NULL,
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`admin_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_role
-- ----------------------------
INSERT INTO `admin_role` VALUES ('normal_admin', 'joke', '2017-07-26 17:37:20');
INSERT INTO `admin_role` VALUES ('normal_admin', 'news', '2017-07-26 17:37:36');
INSERT INTO `admin_role` VALUES ('super_admin', 'joke', '2017-07-26 17:38:38');
INSERT INTO `admin_role` VALUES ('super_admin', 'news', '2017-07-26 17:39:33');
INSERT INTO `admin_role` VALUES ('super_admin', 'user', '2017-07-26 17:38:26');

-- ----------------------------
-- Table structure for company
-- ----------------------------
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company` (
  `company_id` varchar(20) NOT NULL,
  `name` varchar(128) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NOW(),
  PRIMARY KEY (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of company
-- ----------------------------
INSERT INTO `company` VALUES ('alibaba', '阿里巴巴', '杭州', '1995-08-03 15:42:01');
INSERT INTO `company` VALUES ('tencent', '腾讯', '深圳', '2000-06-01 15:40:45');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_id` varchar(20) NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('joke', '笑话管理', '笑话管理', '2017-06-19 17:18:18');
INSERT INTO `role` VALUES ('news', '新闻管理', '新闻管理', '2017-06-20 10:55:03');
INSERT INTO `role` VALUES ('user', '用户管理', '用户管理', '2017-06-19 18:16:56');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` varchar(20) NOT NULL,
  `name` varchar(32) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `gender` int(11) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `company_id` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('ewing', '元宝', 'yb', '1', '2000-01-01 00:00:00', '2017-07-27 18:19:35', 'tencent');
