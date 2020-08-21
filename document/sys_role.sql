/*
 Navicat Premium Data Transfer

 Source Server         : 本地数据库
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : localhost:3306
 Source Schema         : sample_collections

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 09/07/2020 00:28:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '主键id',
  `role_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '角色名称',
  `role_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '角色编号',
  `status` int(2) NULL DEFAULT 2 COMMENT '1.管理员,2普通成员,3.其他',
  `role_descr` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '角色描述',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('2296a8a4ab4d41989260ffc146216d7e', '待定2', 'undefind2', 2, '未定义角色2', '2020-07-08 22:54:54', NULL);
INSERT INTO `sys_role` VALUES ('5d9bd7b8754e4189ac2cd57794187e9f', '待定1', 'undefind1', 2, '未定义角色1', '2020-07-08 22:54:52', NULL);
INSERT INTO `sys_role` VALUES ('685d344d586545eda49c53c26944dd30', '运营', 'operation', 2, '后台运营人员', '2020-07-08 22:52:13', NULL);
INSERT INTO `sys_role` VALUES ('963591a436c5488e80a46365c5d4f11e', '待定3', 'undefind3', 2, '未定义角色3', '2020-07-08 22:54:56', NULL);
INSERT INTO `sys_role` VALUES ('a18b97480a4647b686b58849d8f060a8', '待定6', 'undefind6', 2, '未定义角色6', '2020-07-08 22:55:02', NULL);
INSERT INTO `sys_role` VALUES ('c29bce5b2c3a4f06b3a5d23a133066bf', '待定4', 'undefind4', 2, '未定义角色4', '2020-07-08 22:54:58', NULL);
INSERT INTO `sys_role` VALUES ('c998aa5727b844feb9a2d91adc110b24', '开发', 'systemDevelop', 2, '开发、测试、产品等', '2020-07-08 22:53:01', NULL);
INSERT INTO `sys_role` VALUES ('d834b3ebfcba476ea2a55fc4fa8759a4', '超级管理员', 'administrator', 1, '超级管理员', '2020-07-08 22:50:18', NULL);
INSERT INTO `sys_role` VALUES ('efe84e61cff14baa9a90ebcedc8e3f33', '待定7', 'undefind7', 2, '未定义角色7', '2020-07-08 22:55:05', NULL);
INSERT INTO `sys_role` VALUES ('f23ca14aaaee41c18910ebd08ae915b8', '待定5', 'undefind5', 2, '未定义角色5', '2020-07-08 22:55:00', NULL);

SET FOREIGN_KEY_CHECKS = 1;
