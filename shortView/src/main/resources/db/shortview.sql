/*
 Navicat Premium Data Transfer

 Source Server         : newMysql
 Source Server Type    : MySQL
 Source Server Version : 80034
 Source Host           : localhost:3306
 Source Schema         : shortview

 Target Server Type    : MySQL
 Target Server Version : 80034
 File Encoding         : 65001

 Date: 01/11/2023 15:28:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for class
-- ----------------------------
DROP TABLE IF EXISTS `class`;
CREATE TABLE `class`  (
  `class_id` int(0) NOT NULL AUTO_INCREMENT,
  `className` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `
describe` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`class_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of class
-- ----------------------------
INSERT INTO `class` VALUES (1, '热门视频', '');
INSERT INTO `class` VALUES (2, '体育频道', '体育类');
INSERT INTO `class` VALUES (3, '实事动态', '新闻类');
INSERT INTO `class` VALUES (4, '电影解说', NULL);

-- ----------------------------
-- Table structure for qiniuvideo
-- ----------------------------
DROP TABLE IF EXISTS `qiniuvideo`;
CREATE TABLE `qiniuvideo`  (
  `qiniu_id` int(0) NOT NULL AUTO_INCREMENT,
  `bucket` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `fileKey` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `create_time` datetime(0) NOT NULL,
  `video_id` int(0) NOT NULL,
  `originalFilename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`qiniu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qiniuvideo
-- ----------------------------
INSERT INTO `qiniuvideo` VALUES (1, 'probucket', 'fffef26b816c4f38975b3b5accd953ef', '2023/11/01/fffef26b816c4f38975b3b5accd953ef.mp4', 's35ywnxgs.bkt.clouddn.com/2023/11/01/fffef26b816c4f38975b3b5accd953ef.mp4', '2023-10-31 16:00:00', 7, '余晖.mp4');
INSERT INTO `qiniuvideo` VALUES (2, 'probucket', '4bc3619a00d24ecbbcf1a4cb3b0d04dc', '2023/11/01/4bc3619a00d24ecbbcf1a4cb3b0d04dc.mp4', 's35ywnxgs.bkt.clouddn.com/2023/11/01/4bc3619a00d24ecbbcf1a4cb3b0d04dc.mp4', '2023-10-31 16:00:00', 8, '余晖.mp4');

-- ----------------------------
-- Table structure for video
-- ----------------------------
DROP TABLE IF EXISTS `video`;
CREATE TABLE `video`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_id` int(0) NOT NULL,
  `count` int(0) NULL DEFAULT NULL,
  `class_id` int(0) NOT NULL,
  `create_time` datetime(0) NOT NULL,
  `filename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '视频文件',
  `context` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `status` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of video
-- ----------------------------
INSERT INTO `video` VALUES (1, 's35ywnxgs.bkt.clouddn.com/2023/10/31/95ec65eb2b7046b380634473ea60b1e7.mp4', 1, NULL, 1, '2023-10-30 16:00:00', 'summer.mp4', '', '', 0);
INSERT INTO `video` VALUES (2, 's35ywnxgs.bkt.clouddn.com/2023/10/31/d35cb7542c504bd0a0880d1271cfb150.mp4', 1, NULL, 4, '2023-10-30 16:00:00', 'Jay.mp4', '开不了口', '', 0);
INSERT INTO `video` VALUES (3, 's35ywnxgs.bkt.clouddn.com/2023/10/31/0a9dc366f8f44b35a18e8ff701aee4c0.mp4', 1, NULL, 4, '2023-10-30 16:00:00', '风.mp4', '树叶浮动，风便有了形状！', '', 0);
INSERT INTO `video` VALUES (4, 's35ywnxgs.bkt.clouddn.com/2023/10/31/1aee9b2125f44ef09f7b7fb675f3c509.mp4', 1, NULL, 4, '2023-10-30 16:00:00', '风.mp4', '树叶浮动，风便有了形状！', '', 0);
INSERT INTO `video` VALUES (5, 's35ywnxgs.bkt.clouddn.com/2023/10/31/8a8dd884befe42c4aae5a306dc2e94cb.mp4', 1, NULL, 4, '2023-10-30 16:00:00', '风.mp4', '树叶浮动，风便有了形状！', '', 0);
INSERT INTO `video` VALUES (6, 's35ywnxgs.bkt.clouddn.com/2023/10/31/eed351b012ba4f739dff5827f422a867.mp4', 1, NULL, 4, '2023-10-30 16:00:00', '风.mp4', '树叶浮动，风便有了形状！', '', 0);
INSERT INTO `video` VALUES (7, 's35ywnxgs.bkt.clouddn.com/2023/11/01/fffef26b816c4f38975b3b5accd953ef.mp4', 1, NULL, 4, '2023-10-31 16:00:00', '余晖.mp4', '落日飞车', '橘子海', 0);
INSERT INTO `video` VALUES (8, 's35ywnxgs.bkt.clouddn.com/2023/11/01/4bc3619a00d24ecbbcf1a4cb3b0d04dc.mp4', 1, NULL, 4, '2023-10-31 16:00:00', '余晖.mp4', '落日飞车', '橘子海', 1);

SET FOREIGN_KEY_CHECKS = 1;
