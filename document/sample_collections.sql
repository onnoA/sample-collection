/*
 Navicat Premium Data Transfer

 Source Server         : 本地数据库
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : localhost:3306
 Source Schema         : sample_collections

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 06/07/2020 18:03:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for content_comments
-- ----------------------------
DROP TABLE IF EXISTS `content_comments`;
CREATE TABLE `content_comments`  (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '评论主键id',
  `pid` bigint(32) NULL DEFAULT NULL COMMENT '父评论id',
  `resource_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '被评论的资源id，可以是项目、资源、内容等',
  `type` tinyint(1) NOT NULL COMMENT '评论类型：1：对内容评论，2：回复评论',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '评论者id',
  `status` int(2) NOT NULL DEFAULT 1 COMMENT '状态：1：正常;2：屏蔽',
  `be_reviewer_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '被评论者id',
  `like_num` bigint(11) NOT NULL DEFAULT 0 COMMENT '点赞的数量',
  `comment_content` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '评论内容',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建者',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改者',
  `deleted` int(2) NULL DEFAULT 0 COMMENT '是否删除 0：未删除；1：删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `owner_id`(`resource_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '评论表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of content_comments
-- ----------------------------
INSERT INTO `content_comments` VALUES (7, NULL, '12345678', 1, 'onnoa', 1, NULL, 0, '这是我的第一条评论', '2020-07-03 15:45:04', '2020-07-03 15:45:32', 'onnoa', NULL, 0);
INSERT INTO `content_comments` VALUES (8, NULL, '12345678', 1, 'onnoa', 1, NULL, 0, '这是我的第二条评论。', '2020-07-03 15:45:51', NULL, 'onnoa', NULL, 0);
INSERT INTO `content_comments` VALUES (9, NULL, '12345678', 1, 'onnoa', 1, NULL, 0, '这是我的第三条评论。', '2020-07-03 15:46:08', NULL, 'onnoa', NULL, 0);
INSERT INTO `content_comments` VALUES (10, 7, '12345678', 2, 'heng', 1, 'onnoa', 0, '这是回复onnoa的第一条评论。', '2020-07-03 15:50:18', NULL, 'heng', NULL, 0);
INSERT INTO `content_comments` VALUES (11, 8, '12345678', 2, 'heng', 1, 'onnoa', 0, '这是回复onnoa的第二条评论。', '2020-07-03 15:50:52', NULL, 'heng', NULL, 0);
INSERT INTO `content_comments` VALUES (12, 10, '12345678', 2, 'zhang', 1, 'heng', 0, '这是回复heng的回复评论评论。', '2020-07-03 15:51:55', NULL, 'zhang', NULL, 0);

-- ----------------------------
-- Table structure for sys_back_end_inter_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_back_end_inter_resource`;
CREATE TABLE `sys_back_end_inter_resource`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '主键',
  `interface_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '访问路径',
  `interface_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '菜单功能名称',
  `descr` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '描述',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '后端资源菜单表' ROW_FORMAT = Compact;

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
-- Table structure for sys_front_view_resource_back_inter_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_front_view_resource_back_inter_resource`;
CREATE TABLE `sys_front_view_resource_back_inter_resource`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '主键',
  `back_end_view_url_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '后端菜单资源id',
  `front_view_path_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '前端菜单资源id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_front_view_resource_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_front_view_resource_role`;
CREATE TABLE `sys_front_view_resource_role`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '主键',
  `role_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '角色id',
  `view_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '视图菜单id',
  `select_type` int(2) NULL DEFAULT 1 COMMENT '1:选中2:半选中状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色菜单关系表' ROW_FORMAT = Compact;

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
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '主键',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户名称',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '密码MD5',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `mobile_phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后登录IP',
  `login_count` int(11) UNSIGNED NULL DEFAULT 0 COMMENT '登录次数',
  `real_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` int(1) NULL DEFAULT NULL COMMENT '用户状态1-正常，2-禁用',
  `deleted` int(1) UNSIGNED NULL DEFAULT 0 COMMENT '是否可以删除用户 0-正常，1删除',
  `creator` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_user_business
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_business`;
CREATE TABLE `sys_user_business`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `user_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `business_no` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商户编号',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户和商户关系表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `role_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色id',
  `user_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色关系表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for utopa_videostream_svc_video_info
-- ----------------------------
DROP TABLE IF EXISTS `videostream_svc_video_info`;
CREATE TABLE `utopa_videostream_svc_video_info`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `video_name` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '视频名称',
  `video_title` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '视频标题（展示给C端用户的名称，可与视频名称一致）',
  `origin_file_name` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '视频文件原来的名字',
  `from_sys` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '视频来源系统',
  `pics_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片集ID（视频流服务提取一组图片）——视频动态封面图',
  `file_url` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '视频文件存放在服务器上的URL（或key），完整URL或部分URL(未包含域名等部分)；视频文件夹及文件名都与ID一致（规则）',
  `play_url` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '视频播放地址',
  `cover_pic_url` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '视频静态封面图（视频播放前静态显示）',
  `cover_period_start` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '视频封面截取的时间区间，竖线分割，格式 [开始时间]|[结束时间]，如：1:25|1:32',
  `cover_period_end` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '视频封面截取的时间区间，竖线分割，格式 [开始时间]|[结束时间]，如：1:25|1:32',
  `file_size` decimal(10, 0) NOT NULL COMMENT '(自动)视频文件大小，MB为单位',
  `format_type` int(11) NOT NULL COMMENT '视频封装格式类型 0:其他  1:MP4',
  `video_width` int(11) NULL DEFAULT NULL COMMENT '(自动)视频分辨率宽',
  `video_height` int(11) NULL DEFAULT NULL COMMENT '(自动)视频分辨率高',
  `vd_bitrate` decimal(10, 0) NULL DEFAULT NULL COMMENT '(自动)视频码率',
  `ad_bitrate` decimal(10, 0) NULL DEFAULT NULL COMMENT '(自动)音频码率',
  `tl_bitrate` decimal(10, 0) NULL DEFAULT NULL COMMENT '(自动)总码率',
  `status` int(11) NOT NULL COMMENT '状态（0：未发布处理中 1：已发布可用   2：已发布禁用  30: 视频流服务未知异常 31: 文件上传失败 32：文件格式不正确  33:视频提取封面图失败 34:视频转码失败 ）',
  `descr` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '描述',
  `memo` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '备注',
  `create_by` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_by` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '编辑者',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '编辑时间',
  `deleted` tinyint(4) NULL DEFAULT 0 COMMENT '是否已删除（0:否 1：是）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '视频信息表（视频服务）' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
