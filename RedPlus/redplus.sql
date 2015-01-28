/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Version : 50616
 Source Host           : localhost
 Source Database       : redplus

 Target Server Version : 50616
 File Encoding         : utf-8

 Date: 10/13/2014 07:57:58 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `gift_info`
-- ----------------------------
DROP TABLE IF EXISTS `gift_info`;
CREATE TABLE `gift_info` (
  `id` int(11) NOT NULL,
  `giftname` varchar(100) NOT NULL,
  `giftinfo` varchar(100) NOT NULL,
  `remark` varchar(220) NOT NULL,
  `resv1` varchar(20) NOT NULL,
  `resv2` varchar(100) NOT NULL,
  `resv3` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `hibernate_sequences`
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequences`;
CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) NOT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL COMMENT '下一个sequence的值=sequence_next_hi_value*实体配置的max_lo',
  PRIMARY KEY (`sequence_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `hibernate_sequences`
-- ----------------------------
BEGIN;
INSERT INTO `hibernate_sequences` VALUES ('contact_template', '2'), ('customer_company', '131'), ('customer_sig_user', '7336'), ('customer_user', '3'), ('sys_permission', '6'), ('sys_resource', '13'), ('sys_role', '2'), ('sys_role_auth', '119'), ('sys_user', '10');
COMMIT;

-- ----------------------------
--  Table structure for `sys_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `permission` varchar(200) DEFAULT NULL,
  `memo` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `sys_permission`
-- ----------------------------
BEGIN;
INSERT INTO `sys_permission` VALUES ('1', '查询', 'list', null), ('2', '编辑', 'update', null), ('3', '删除', 'del', null), ('4', '添加', 'add', null), ('5', '授权', 'auth', '1'), ('30', '导入', 'imp', ''), ('31', '导出', 'exp', '');
COMMIT;

-- ----------------------------
--  Table structure for `sys_resource`
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `code` varchar(100) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `ico` varchar(100) DEFAULT NULL,
  `is_root` int(11) DEFAULT NULL,
  `is_show` int(11) DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `parent_ids` varchar(200) DEFAULT NULL,
  `permission_ids` varchar(200) DEFAULT NULL,
  `memo` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_sys_resource_parent_id` (`parent_id`),
  CONSTRAINT `FK_sys_resource_parent_id` FOREIGN KEY (`parent_id`) REFERENCES `sys_resource` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `sys_resource`
-- ----------------------------
BEGIN;
INSERT INTO `sys_resource` VALUES ('1', null, '系统管理', 'sys', '/admin/sys/user', 'nav-supplier', '1', '1', '1', '1', null, null, null), ('2', '1', '用户与权限', '', null, '', null, null, null, '2', null, null, null), ('4', '2', '用户管理', '/admin/sys/user', '/admin/sys/user', '', null, '1', '1', '3', '', '1,2,3,4', null), ('5', '2', '权限管理', '/admin/sys/permission', '/admin/sys/permission', '', null, '1', '4', '3', null, '1,2,3,4', '1'), ('6', '2', '角色管理', '/admin/sys/role', '/admin/sys/role', '', null, null, '3', '3', null, '1,2,3,4,5', null), ('7', '2', '资源管理', '/admin/sys/resource', '/admin/sys/resource', '', null, null, '2', '3', null, '1,2,3,4', null);
COMMIT;

-- ----------------------------
--  Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `code` varchar(100) DEFAULT NULL,
  `memo` varchar(200) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `sys_role`
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES ('1', '管理员', 'Admin', '', null), ('2', '测试', 'Test', '', null);
COMMIT;

-- ----------------------------
--  Table structure for `sys_role_auth`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_auth`;
CREATE TABLE `sys_role_auth` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `resource_id` int(11) DEFAULT NULL,
  `permission_ids` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_sys_role_auth_resource_id` (`resource_id`),
  KEY `FK_sys_role_auth_role_id` (`role_id`),
  CONSTRAINT `FK_sys_role_auth_resource_id` FOREIGN KEY (`resource_id`) REFERENCES `sys_resource` (`id`),
  CONSTRAINT `FK_sys_role_auth_role_id` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=714 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `sys_role_auth`
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_auth` VALUES ('702', '2', '2', ''), ('703', '2', '4', '1,2,3,4'), ('704', '2', '7', '1,2,3,4'), ('705', '2', '6', '1,2,3,4,5'), ('706', '2', '5', '1,2,3,4'), ('707', '2', '1', ''), ('708', '1', '2', ''), ('709', '1', '4', '1,2,3,4'), ('710', '1', '7', '1,2,3,4'), ('711', '1', '6', '1,2,3,4,5'), ('712', '1', '5', '1,2,3,4'), ('713', '1', '1', '');
COMMIT;

-- ----------------------------
--  Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `creater_id` int(11) DEFAULT NULL,
  `login_name` varchar(100) NOT NULL,
  `password` varchar(40) NOT NULL,
  `salt` varchar(20) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `status` smallint(6) DEFAULT NULL COMMENT '1 正常 2 停用',
  `last_login_time` datetime DEFAULT NULL,
  `last_login_ip` varchar(15) DEFAULT NULL,
  `corp_id` bigint(9) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_sys_user_logname` (`login_name`),
  KEY `FK_sys_user_creater_id` (`creater_id`),
  CONSTRAINT `FK_sys_user_creater_id` FOREIGN KEY (`creater_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `sys_user`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES ('1', null, 'admin', '5e35bbd3f9bd9f8f86a20d9c122d117f9e2b8dbb', '9590c47563aa9525', '管理员', 'aaa@gzjp.cn', '2014-06-20 11:29:10', '1', null, '1', '21'), ('49', '1', 'test', '2722c13ff6e13be090f2551d689080dce50f12ed', '9bb0a0e2ba71f120', '测试', 'test@gzjp.cn1', '2014-07-22 00:00:00', '1', null, null, null);
COMMIT;

-- ----------------------------
--  Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `role_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`,`user_id`),
  KEY `FK_sys_user_role_user_id` (`user_id`),
  CONSTRAINT `FK_sys_user_role_role_id` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`),
  CONSTRAINT `FK_sys_user_role_user_id` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `sys_user_role`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES ('1', '1'), ('1', '49'), ('2', '49');
COMMIT;

-- ----------------------------
--  Table structure for `the_message`
-- ----------------------------
DROP TABLE IF EXISTS `the_message`;
CREATE TABLE `the_message` (
  `id` int(11) NOT NULL,
  `form_user_id` int(11) NOT NULL,
  `to_user_id` int(11) NOT NULL,
  `createtime` datetime NOT NULL,
  `info` varchar(200) NOT NULL,
  `info_type` int(1) NOT NULL,
  `resv1` varchar(20) NOT NULL,
  `resv2` varchar(100) NOT NULL,
  `resv3` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `trans_rebate`
-- ----------------------------
DROP TABLE IF EXISTS `trans_rebate`;
CREATE TABLE `trans_rebate` (
  `id` int(11) NOT NULL,
  `trans_id` int(11) NOT NULL,
  `statu` int(1) NOT NULL,
  `money` int(11) NOT NULL,
  `createtime` date NOT NULL,
  `remark` varchar(220) NOT NULL,
  `resv1` varchar(20) NOT NULL,
  `resv2` varchar(100) NOT NULL,
  `resv3` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `trans_rebate_info`
-- ----------------------------
DROP TABLE IF EXISTS `trans_rebate_info`;
CREATE TABLE `trans_rebate_info` (
  `id` int(11) NOT NULL,
  `trans_rebate_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `money` int(11) NOT NULL,
  `statu` int(1) NOT NULL,
  `resv1` varchar(20) NOT NULL,
  `resv2` varchar(100) NOT NULL,
  `resv3` varchar(200) NOT NULL,
  `ratio` float(20,0) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `user_gift`
-- ----------------------------
DROP TABLE IF EXISTS `user_gift`;
CREATE TABLE `user_gift` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `gift_id` int(11) NOT NULL,
  `order` int(10) NOT NULL,
  `price` int(20) NOT NULL,
  `resv1` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `user_info`
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` int(11) NOT NULL DEFAULT '0',
  `nickname` varchar(22) DEFAULT NULL,
  `age` int(4) NOT NULL,
  `height` int(6) NOT NULL,
  `weight` int(11) NOT NULL COMMENT 'kg',
  `hobby` varchar(200) NOT NULL,
  `iconimg` varchar(200) NOT NULL,
  `area` varchar(200) NOT NULL,
  `gender` int(1) NOT NULL,
  `telpone` int(12) NOT NULL COMMENT '1代表男 0代表女',
  `loginname` varchar(100) NOT NULL,
  `passwd` varchar(20) NOT NULL,
  `online` int(11) NOT NULL COMMENT '0不在线 1 在线 －1 忙',
  `remark` varchar(220) NOT NULL,
  `coll` varchar(220) NOT NULL,
  `refree` int(11) NOT NULL,
  `resv1` varchar(20) NOT NULL,
  `resv2` varchar(100) NOT NULL,
  `resv3` varchar(200) NOT NULL,
  `icon` blob NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `user_money`
-- ----------------------------
DROP TABLE IF EXISTS `user_money`;
CREATE TABLE `user_money` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `money` int(20) NOT NULL,
  `remark` varchar(200) NOT NULL,
  `resv1` varchar(20) NOT NULL,
  `resv2` varchar(220) NOT NULL,
  `resv3` varchar(300) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `user_trans`
-- ----------------------------
DROP TABLE IF EXISTS `user_trans`;
CREATE TABLE `user_trans` (
  `id` int(11) NOT NULL,
  `from_user_id` int(11) NOT NULL,
  `to_user_id` int(11) NOT NULL,
  `createtime` date NOT NULL,
  `statu` int(11) NOT NULL,
  `resv1` varchar(20) NOT NULL,
  `resv2` varchar(100) NOT NULL,
  `resv3` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `user_trans_info`
-- ----------------------------
DROP TABLE IF EXISTS `user_trans_info`;
CREATE TABLE `user_trans_info` (
  `id` int(11) NOT NULL,
  `trans_id` int(11) NOT NULL,
  `user_gift_id` int(11) NOT NULL,
  `count` int(11) NOT NULL,
  `type` int(1) NOT NULL,
  `resv1` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;
