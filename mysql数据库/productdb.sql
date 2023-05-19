/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : productdb

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2019-08-05 14:05:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL,
  `password` varchar(20) default NULL,
  PRIMARY KEY  (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for `t_evaluate`
-- ----------------------------
DROP TABLE IF EXISTS `t_evaluate`;
CREATE TABLE `t_evaluate` (
  `evaluateId` int(11) NOT NULL auto_increment,
  `productObj` varchar(20) default NULL,
  `memberObj` varchar(20) default NULL,
  `content` varchar(50) default NULL,
  `evaluateTime` varchar(20) default NULL,
  PRIMARY KEY  (`evaluateId`),
  KEY `FKC42B8424844F6F91` (`memberObj`),
  KEY `FKC42B84246778E291` (`productObj`),
  KEY `FKC42B842413CC3859` (`productObj`),
  KEY `FKC42B84244A0F684F` (`memberObj`),
  KEY `FKC42B842460B575B` (`productObj`),
  CONSTRAINT `FKC42B842460B575B` FOREIGN KEY (`productObj`) REFERENCES `t_productinfo` (`productNo`),
  CONSTRAINT `FKC42B842413CC3859` FOREIGN KEY (`productObj`) REFERENCES `t_productinfo` (`productNo`),
  CONSTRAINT `FKC42B84244A0F684F` FOREIGN KEY (`memberObj`) REFERENCES `t_memberinfo` (`memberUserName`),
  CONSTRAINT `FKC42B84246778E291` FOREIGN KEY (`productObj`) REFERENCES `t_productinfo` (`productNo`),
  CONSTRAINT `FKC42B8424844F6F91` FOREIGN KEY (`memberObj`) REFERENCES `t_memberinfo` (`memberUserName`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_evaluate
-- ----------------------------
INSERT INTO `t_evaluate` VALUES ('1', 'cp001', 'linlin', '非常好', '2019-02-15 20:05:46');
INSERT INTO `t_evaluate` VALUES ('2', 'cp006', 'usertest', '不错哦都', '2019-02-24 11:10:15');

-- ----------------------------
-- Table structure for `t_memberinfo`
-- ----------------------------
DROP TABLE IF EXISTS `t_memberinfo`;
CREATE TABLE `t_memberinfo` (
  `memberUserName` varchar(20) NOT NULL,
  `password` varchar(20) default NULL,
  `realName` varchar(20) default NULL,
  `sex` varchar(2) default NULL,
  `birthday` varchar(10) default NULL,
  `telephone` varchar(20) default NULL,
  `email` varchar(40) default NULL,
  `qq` varchar(20) default NULL,
  `address` varchar(60) default NULL,
  `photo` varchar(50) default NULL,
  PRIMARY KEY  (`memberUserName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_memberinfo
-- ----------------------------
INSERT INTO `t_memberinfo` VALUES ('linlin', '123456', '林林', '男', '2019-02-12', '13938124234', 'ceshi@126.com', '25234532', '四川达州f ', 'upload/fe0453cf-c721-42fd-ba1f-eb3a40af5514.jpg');
INSERT INTO `t_memberinfo` VALUES ('usertest', '123456', '测试用户', '男', '1997-02-12', '13598919031', 'ceshiuser@126.com', '2834951', '测试家庭地址', 'upload/ea1ec834-f5f3-4397-80bf-d60181e78935.jpg');
INSERT INTO `t_memberinfo` VALUES ('xiaozhang', '123456', '小张', '女', '1982-02-09', '13959349561', 'xiaozhang@126.com', '510561141', '四川成都', 'upload/897aa38b-fb50-405e-8c0d-d0d7b394d5ef.jpg');

-- ----------------------------
-- Table structure for `t_notice`
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice` (
  `noticeId` int(11) NOT NULL auto_increment,
  `title` varchar(30) default NULL,
  `content` varchar(80) default NULL,
  `publishDate` varchar(10) default NULL,
  PRIMARY KEY  (`noticeId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_notice
-- ----------------------------
INSERT INTO `t_notice` VALUES ('1', '公告标题', '公告内容', '2019-02-15');

-- ----------------------------
-- Table structure for `t_orderdetail`
-- ----------------------------
DROP TABLE IF EXISTS `t_orderdetail`;
CREATE TABLE `t_orderdetail` (
  `detailId` int(11) NOT NULL auto_increment,
  `orderObj` varchar(30) default NULL,
  `productObj` varchar(20) default NULL,
  `price` float default NULL,
  `count` int(11) default NULL,
  PRIMARY KEY  (`detailId`),
  KEY `FK987A51D46778E291` (`productObj`),
  KEY `FK987A51D4AA176D79` (`orderObj`),
  KEY `FK987A51D413CC3859` (`productObj`),
  KEY `FK987A51D4A83664FB` (`orderObj`),
  KEY `FK987A51D460B575B` (`productObj`),
  CONSTRAINT `FK987A51D460B575B` FOREIGN KEY (`productObj`) REFERENCES `t_productinfo` (`productNo`),
  CONSTRAINT `FK987A51D413CC3859` FOREIGN KEY (`productObj`) REFERENCES `t_productinfo` (`productNo`),
  CONSTRAINT `FK987A51D46778E291` FOREIGN KEY (`productObj`) REFERENCES `t_productinfo` (`productNo`),
  CONSTRAINT `FK987A51D4A83664FB` FOREIGN KEY (`orderObj`) REFERENCES `t_orderinfo` (`orderNo`),
  CONSTRAINT `FK987A51D4AA176D79` FOREIGN KEY (`orderObj`) REFERENCES `t_orderinfo` (`orderNo`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_orderdetail
-- ----------------------------
INSERT INTO `t_orderdetail` VALUES ('1', 'linlin20190412035132', 'cp002', '57.8', '1');
INSERT INTO `t_orderdetail` VALUES ('2', 'linlin20190412035132', 'cp004', '48.88', '2');
INSERT INTO `t_orderdetail` VALUES ('3', 'linlin20190816234818', 'cp005', '21', '1');
INSERT INTO `t_orderdetail` VALUES ('4', 'linlin20190816234818', 'cp003', '25.99', '2');

-- ----------------------------
-- Table structure for `t_orderinfo`
-- ----------------------------
DROP TABLE IF EXISTS `t_orderinfo`;
CREATE TABLE `t_orderinfo` (
  `orderNo` varchar(30) NOT NULL,
  `memberObj` varchar(20) default NULL,
  `orderTime` varchar(20) default NULL,
  `totalMoney` float default NULL,
  `orderStateObj` int(11) default NULL,
  `memo` varchar(50) default NULL,
  `buyWay` varchar(20) default NULL,
  `realName` varchar(20) default NULL,
  `telphone` varchar(20) default NULL,
  `postcode` varchar(20) default NULL,
  `address` varchar(80) default NULL,
  PRIMARY KEY  (`orderNo`),
  KEY `FK7EF822F1844F6F91` (`memberObj`),
  KEY `FK7EF822F167F75F03` (`orderStateObj`),
  KEY `FK7EF822F14A0F684F` (`memberObj`),
  KEY `FK7EF822F12DB757C1` (`orderStateObj`),
  CONSTRAINT `FK7EF822F12DB757C1` FOREIGN KEY (`orderStateObj`) REFERENCES `t_orderstate` (`stateId`),
  CONSTRAINT `FK7EF822F14A0F684F` FOREIGN KEY (`memberObj`) REFERENCES `t_memberinfo` (`memberUserName`),
  CONSTRAINT `FK7EF822F167F75F03` FOREIGN KEY (`orderStateObj`) REFERENCES `t_orderstate` (`stateId`),
  CONSTRAINT `FK7EF822F1844F6F91` FOREIGN KEY (`memberObj`) REFERENCES `t_memberinfo` (`memberUserName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_orderinfo
-- ----------------------------
INSERT INTO `t_orderinfo` VALUES ('linlin20190412035132', 'linlin', '2019-04-12 03:51:32', '15556', '1', '测试', '邮局汇款', '林林', '13938124234', '625314', '四川达州f ');
INSERT INTO `t_orderinfo` VALUES ('linlin20190816234818', 'linlin', '2019-07-16 23:48:18', '7298', '3', 'test', '邮局汇款', '林林', '13938124234', '610054', '四川达州f ');

-- ----------------------------
-- Table structure for `t_orderstate`
-- ----------------------------
DROP TABLE IF EXISTS `t_orderstate`;
CREATE TABLE `t_orderstate` (
  `stateId` int(11) NOT NULL auto_increment,
  `stateName` varchar(20) default NULL,
  PRIMARY KEY  (`stateId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_orderstate
-- ----------------------------
INSERT INTO `t_orderstate` VALUES ('1', '未付款');
INSERT INTO `t_orderstate` VALUES ('2', '已付款');
INSERT INTO `t_orderstate` VALUES ('3', '已发货');
INSERT INTO `t_orderstate` VALUES ('4', '交易完成');

-- ----------------------------
-- Table structure for `t_productcart`
-- ----------------------------
DROP TABLE IF EXISTS `t_productcart`;
CREATE TABLE `t_productcart` (
  `cartId` int(11) NOT NULL auto_increment,
  `memberObj` varchar(20) default NULL,
  `productObj` varchar(20) default NULL,
  `price` float default NULL,
  `count` int(11) default NULL,
  PRIMARY KEY  (`cartId`),
  KEY `FKCB017765844F6F91` (`memberObj`),
  KEY `FKCB0177656778E291` (`productObj`),
  KEY `FKF8C4A544844F6F91` (`memberObj`),
  KEY `FKF8C4A54413CC3859` (`productObj`),
  KEY `FKF8C4A5444A0F684F` (`memberObj`),
  KEY `FKF8C4A54460B575B` (`productObj`),
  CONSTRAINT `FKF8C4A54460B575B` FOREIGN KEY (`productObj`) REFERENCES `t_productinfo` (`productNo`),
  CONSTRAINT `FKCB0177656778E291` FOREIGN KEY (`productObj`) REFERENCES `t_productinfo` (`productNo`),
  CONSTRAINT `FKCB017765844F6F91` FOREIGN KEY (`memberObj`) REFERENCES `t_memberinfo` (`memberUserName`),
  CONSTRAINT `FKF8C4A54413CC3859` FOREIGN KEY (`productObj`) REFERENCES `t_productinfo` (`productNo`),
  CONSTRAINT `FKF8C4A5444A0F684F` FOREIGN KEY (`memberObj`) REFERENCES `t_memberinfo` (`memberUserName`),
  CONSTRAINT `FKF8C4A544844F6F91` FOREIGN KEY (`memberObj`) REFERENCES `t_memberinfo` (`memberUserName`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_productcart
-- ----------------------------

-- ----------------------------
-- Table structure for `t_productclass`
-- ----------------------------
DROP TABLE IF EXISTS `t_productclass`;
CREATE TABLE `t_productclass` (
  `classId` int(11) NOT NULL auto_increment,
  `className` varchar(20) default NULL,
  PRIMARY KEY  (`classId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_productclass
-- ----------------------------
INSERT INTO `t_productclass` VALUES ('1', '电脑类');
INSERT INTO `t_productclass` VALUES ('2', '手机类');

-- ----------------------------
-- Table structure for `t_productinfo`
-- ----------------------------
DROP TABLE IF EXISTS `t_productinfo`;
CREATE TABLE `t_productinfo` (
  `productNo` varchar(20) NOT NULL,
  `productClassObj` int(11) default NULL,
  `productName` varchar(20) default NULL,
  `productPhoto` varchar(50) default NULL,
  `productPrice` float default NULL,
  `productCount` int(11) default NULL,
  `recommendFlag` int(11) default NULL,
  `hotNum` int(11) default NULL,
  `onlineDate` varchar(10) default NULL,
  PRIMARY KEY  (`productNo`),
  KEY `FKCB0460F3AC02098B` (`productClassObj`),
  KEY `FKCB0460F324E1EF27` (`recommendFlag`),
  KEY `FKF8C78ED224E1EF27` (`recommendFlag`),
  KEY `FKF8C78ED2EC6CFA43` (`productClassObj`),
  KEY `FKF8C78ED2FDFCDF29` (`recommendFlag`),
  KEY `FKF8C78ED24211BB81` (`productClassObj`),
  CONSTRAINT `FKF8C78ED24211BB81` FOREIGN KEY (`productClassObj`) REFERENCES `t_productclass` (`classId`),
  CONSTRAINT `FKCB0460F324E1EF27` FOREIGN KEY (`recommendFlag`) REFERENCES `t_yesorno` (`id`),
  CONSTRAINT `FKCB0460F3AC02098B` FOREIGN KEY (`productClassObj`) REFERENCES `t_productclass` (`classId`),
  CONSTRAINT `FKF8C78ED224E1EF27` FOREIGN KEY (`recommendFlag`) REFERENCES `t_yesorno` (`id`),
  CONSTRAINT `FKF8C78ED2EC6CFA43` FOREIGN KEY (`productClassObj`) REFERENCES `t_productclass` (`classId`),
  CONSTRAINT `FKF8C78ED2FDFCDF29` FOREIGN KEY (`recommendFlag`) REFERENCES `t_yesorno` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_productinfo
-- ----------------------------
INSERT INTO `t_productinfo` VALUES ('cp001', '1', 'Dell/戴尔 N4050四核笔记本', 'upload/71895989-35ce-49e4-8ea1-9fcc1f848acb.jpg', '24', '92', '1', '3', '2019-02-13');
INSERT INTO `t_productinfo` VALUES ('cp002', '1', '联想Y510PT笔记本', 'upload/0d168523-80a5-4a93-9b05-916bf1537eaa.jpg', '57.8', '91', '1', '6', '2019-02-11');
INSERT INTO `t_productinfo` VALUES ('cp003', '1', 'Acer/宏基 E1-471游戏本', 'upload/e8f21700-f164-4c55-bfcd-bad2fcdc09fb.jpg', '25.99', '92', '1', '1', '2019-02-16');
INSERT INTO `t_productinfo` VALUES ('cp004', '2', '苹果5S 移动4G版', 'upload/de9ba624-a649-41ae-b992-b2fcb9b0c0d3.jpg', '48.88', '76', '1', '2', '2019-02-14');
INSERT INTO `t_productinfo` VALUES ('cp005', '2', '小米手机3代', 'upload/54f54ebf-ff26-4dde-98e2-4f713e925915.jpg', '21', '61', '1', '3', '2019-02-12');
INSERT INTO `t_productinfo` VALUES ('cp006', '2', 'HTC 608T手机', 'upload/95d61fa4-ad44-4173-b1c3-b1d46232c203.jpg', '9.88', '85', '1', '3', '2019-02-24');

-- ----------------------------
-- Table structure for `t_yesorno`
-- ----------------------------
DROP TABLE IF EXISTS `t_yesorno`;
CREATE TABLE `t_yesorno` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(20) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_yesorno
-- ----------------------------
INSERT INTO `t_yesorno` VALUES ('1', '是');
INSERT INTO `t_yesorno` VALUES ('2', '否');
