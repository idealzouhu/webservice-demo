/*
 Navicat Premium Data Transfer

 Source Server         : test
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : customer

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 22/10/2022 21:19:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for administrator
-- ----------------------------
DROP TABLE IF EXISTS `administrator`;
CREATE TABLE `administrator`  (
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `email` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `administratorId` int(0) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`administratorId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '管理员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of administrator
-- ----------------------------
INSERT INTO `administrator` VALUES ('123', '123@qq.com', 1);

-- ----------------------------
-- Table structure for complaint
-- ----------------------------
DROP TABLE IF EXISTS `complaint`;
CREATE TABLE `complaint`  (
  `complaintId` int(0) NOT NULL AUTO_INCREMENT,
  `reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发出投诉的客户姓名',
  `state` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表示当前投诉处理状态，只有两种投诉状态：\r\n“完成” 、“未完成” ',
  `date` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '投诉时间',
  `customerId` int(0) NULL DEFAULT NULL,
  `operation` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '处理操作，只能由administrator中的客服进行操作',
  PRIMARY KEY (`complaintId`) USING BTREE,
  INDEX `customerId`(`customerId`) USING BTREE,
  CONSTRAINT `customerId` FOREIGN KEY (`customerId`) REFERENCES `customer` (`customerId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of complaint
-- ----------------------------
INSERT INTO `complaint` VALUES (3, '服务器炸了', 'Userex1@qq.com', '等待处理', '2022-10-16 21:33:17', 4, '服务器好了');
INSERT INTO `complaint` VALUES (4, '服务器崩了', 'Userex1@qq.com', '等待处理', '2022-10-16 21:34:21', 4, NULL);
INSERT INTO `complaint` VALUES (5, '软件崩了', 'Userex2@qq.com', '等待处理', '2022-10-16 21:35:07', 5, '软件好了');
INSERT INTO `complaint` VALUES (6, '软件炸了', 'Userex2@qq.com', '已处理', '2022-10-16 21:35:21', 5, '处理完毕');

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer`  (
  `customerId` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户的邮箱',
  `age` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`customerId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES (1, NULL, '123', '123@qq.com', NULL);
INSERT INTO `customer` VALUES (2, NULL, '123456', '125@qq.com', NULL);
INSERT INTO `customer` VALUES (3, NULL, '123', '1234@qq.com', NULL);
INSERT INTO `customer` VALUES (4, NULL, '123', 'Userex1@qq.com', NULL);
INSERT INTO `customer` VALUES (5, NULL, '123', 'Userex2@qq.com', NULL);

SET FOREIGN_KEY_CHECKS = 1;
