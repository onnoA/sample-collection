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

 Date: 09/07/2020 00:29:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_front_view_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_front_view_resource`;
CREATE TABLE `sys_front_view_resource`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '主键',
  `parent_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '父级id',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '菜单路径',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '菜单名称',
  `descr` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '菜单描述',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '图标',
  `type` int(2) NULL DEFAULT NULL COMMENT '类型：1菜单文件夹2菜单文件3按钮功能',
  `level` int(2) NULL DEFAULT NULL COMMENT '层级',
  `sort` int(3) NULL DEFAULT 1 COMMENT '排序',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '前端菜单资源表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_front_view_resource
-- ----------------------------
INSERT INTO `sys_front_view_resource` VALUES ('4ab5fee87d0a45539edf20b425149b68', '894154feaa89472db9e307ecc539a380', '/operationPlatform/authorityStstem/roleController.html', '角色权限管理', '角色权限管理', '', 2, 3, 1, '2020-07-08 23:57:04', NULL);
INSERT INTO `sys_front_view_resource` VALUES ('5eec363ac8134594a39b904986e5ee78', '894154feaa89472db9e307ecc539a380', '/operationPlatform/authorityStstem/menuController.html', '菜单管理', '菜单管理', '', 2, 3, 1, '2020-07-08 23:57:06', NULL);
INSERT INTO `sys_front_view_resource` VALUES ('66b8afcdedd24114abf698c933777514', '706f4504ee5045b787ed3a4064e8d992', '/', '系统管理', '系统管理', '', 1, 1, 1, '2020-07-08 23:49:02', NULL);
INSERT INTO `sys_front_view_resource` VALUES ('706f4504ee5045b787ed3a4064e8d992', '', '/', '运营平台', '运营平台', '', 1, 0, 1, '2020-07-08 23:44:53', NULL);
INSERT INTO `sys_front_view_resource` VALUES ('894154feaa89472db9e307ecc539a380', '66b8afcdedd24114abf698c933777514', '/', '权限管理', '权限管理', '', 1, 2, 1, '2020-07-08 23:51:59', NULL);
INSERT INTO `sys_front_view_resource` VALUES ('b58370c3a5cc4de8ac09aa87d0e4ff74', '894154feaa89472db9e307ecc539a380', '/operationPlatform/authorityStstem/userController.html', '用户管理', '用户管理', '', 2, 3, 1, '2020-07-08 23:57:01', NULL);

SET FOREIGN_KEY_CHECKS = 1;
