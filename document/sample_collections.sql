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

 Date: 03/07/2020 15:43:53
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
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '评论表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for utopa_videostream_svc_video_info
-- ----------------------------
DROP TABLE IF EXISTS `utopa_videostream_svc_video_info`;
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
