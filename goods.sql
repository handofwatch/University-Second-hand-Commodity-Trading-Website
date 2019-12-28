# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.28)
# Database: goods
# Generation Time: 2019-12-28 16:34:38 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table t_admin
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_admin`;

CREATE TABLE `t_admin` (
  `adminId` char(32) NOT NULL,
  `adminname` varchar(50) NOT NULL DEFAULT '',
  `adminpwd` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`adminId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `t_admin` WRITE;
/*!40000 ALTER TABLE `t_admin` DISABLE KEYS */;

INSERT INTO `t_admin` (`adminId`, `adminname`, `adminpwd`)
VALUES
	('a1','liuBei','123'),
	('a2','guanYu','123'),
	('a3','zhangSanFei','123');

/*!40000 ALTER TABLE `t_admin` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_goods
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_goods`;

CREATE TABLE `t_goods` (
  `gid` char(32) NOT NULL DEFAULT '',
  `gname` varchar(200) NOT NULL DEFAULT '',
  `price` decimal(8,2) NOT NULL,
  `cid` char(32) NOT NULL DEFAULT '',
  `image_w` varchar(100) DEFAULT NULL,
  `image_w2` varchar(100) DEFAULT NULL,
  `image_b` varchar(100) DEFAULT NULL,
  `gdesc` varchar(400) DEFAULT NULL,
  `orderBy` int(11) NOT NULL AUTO_INCREMENT,
  `gstatus` int(11) NOT NULL,
  PRIMARY KEY (`gid`),
  KEY `orderBy` (`orderBy`),
  KEY `FK_t_book_t_category` (`cid`),
  CONSTRAINT `FK_t_book_t_category` FOREIGN KEY (`cid`) REFERENCES `t_category` (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `t_goods` WRITE;
/*!40000 ALTER TABLE `t_goods` DISABLE KEYS */;

INSERT INTO `t_goods` (`gid`, `gname`, `price`, `cid`, `image_w`, `image_w2`, `image_b`, `gdesc`, `orderBy`, `gstatus`)
VALUES
	('5315DA60D24042889400AD4C93A37501','Spring 3.x企业应用开发实战(含CD光盘1张)',90.00,'5F79D0D246AD4216AC04E9C5FAB3199E','book_img/22605701-1_w.jpg','book_img/22605701-1_w.jpg','book_img/22605701-1_b.jpg','spring',5,1),
	('CB0AB3654945411EA69F368D0EA91A00','JavaScript语言精粹（修订版）',49.00,'5F79D0D246AD4216AC04E9C5FAB3199E','book_img/22872884-1_w.jpg',NULL,'book_img/22872884-1_b.jpg','java',4,1),
	('CD913617EE964D0DBAF20C60076D32FB','名师讲坛——Java开发实战经典（配光盘）（60小时全真课堂培训，视频超级给力！790项实例及分析，北京魔乐科技培训中心Java全部精华）',79.80,'5F79D0D246AD4216AC04E9C5FAB3199E','book_img/20637368-1_w_2.jpg',NULL,'book_img/20637368-1_b_2.jpg','web',3,1),
	('CE01F15D435A4C51B0AD8202A318DCA7','Java编程思想（第4版）',108.00,'5F79D0D246AD4216AC04E9C5FAB3199E','book_img/9317290-1_w.jpg',NULL,'book_img/9317290-1_b.jpg',NULL,1,1),
	('DF4E74EEE89B43229BB8212F0B858C38','精通Hibernate：Java对象持久化技术详解（第2版）(含光盘1张)',75.00,'5F79D0D246AD4216AC04E9C5FAB3199E','book_img/20773347-1_w_1.jpg',NULL,'book_img/20773347-1_b.jpg',NULL,2,1);

/*!40000 ALTER TABLE `t_goods` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_cartitem
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_cartitem`;

CREATE TABLE `t_cartitem` (
  `cartItemId` char(32) NOT NULL,
  `gid` char(32) DEFAULT NULL,
  `uid` char(32) NOT NULL DEFAULT '',
  `orderBy` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`cartItemId`),
  KEY `orderBy` (`orderBy`),
  KEY `FK_t_cartitem_t_user` (`uid`),
  KEY `FK_t_cartitem_t_book` (`gid`),
  CONSTRAINT `FK_t_cartitem_t_book` FOREIGN KEY (`gid`) REFERENCES `t_goods` (`gid`),
  CONSTRAINT `FK_t_cartitem_t_user` FOREIGN KEY (`uid`) REFERENCES `t_user` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table t_category
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_category`;

CREATE TABLE `t_category` (
  `cid` char(32) NOT NULL,
  `cname` varchar(50) NOT NULL DEFAULT '',
  `cdesc` varchar(100) DEFAULT NULL,
  `orderBy` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`cid`),
  UNIQUE KEY `cname` (`cname`),
  KEY `orderBy` (`orderBy`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `t_category` WRITE;
/*!40000 ALTER TABLE `t_category` DISABLE KEYS */;

INSERT INTO `t_category` (`cid`, `cname`, `cdesc`, `orderBy`)
VALUES
	('56AD72718C524147A2485E5F4A95A062','3DS MAX','3DS MAX分类',1),
	('57DE3C2DDA784B81844029A28217698C','Dreamweaver','Dreamweaver分类',2),
	('5F79D0D246AD4216AC04E9C5FAB3199E','书籍','书的分类',3),
	('FAB7B7F7084F4D57A0808ADC61117683','Windows','Windows分类',4);

/*!40000 ALTER TABLE `t_category` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_order
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_order`;

CREATE TABLE `t_order` (
  `oid` char(32) NOT NULL,
  `ordertime` char(19) DEFAULT NULL,
  `total` decimal(10,2) NOT NULL,
  `address` varchar(1000) NOT NULL DEFAULT '',
  `uid` char(32) NOT NULL DEFAULT '',
  `buyername` varchar(100) NOT NULL DEFAULT '',
  `phone` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`oid`),
  KEY `FK_t_order_t_user` (`uid`),
  CONSTRAINT `FK_t_order_t_user` FOREIGN KEY (`uid`) REFERENCES `t_user` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table t_orderitem
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_orderitem`;

CREATE TABLE `t_orderitem` (
  `orderItemId` char(32) NOT NULL,
  `price` decimal(8,2) NOT NULL,
  `gid` char(32) NOT NULL DEFAULT '',
  `gname` varchar(200) NOT NULL DEFAULT '',
  `image_b` varchar(100) DEFAULT NULL,
  `oid` char(32) NOT NULL DEFAULT '',
  `orderstatus` int(11) NOT NULL,
  PRIMARY KEY (`orderItemId`),
  KEY `FK_t_orderitem_t_order` (`oid`),
  CONSTRAINT `FK_t_orderitem_t_order` FOREIGN KEY (`oid`) REFERENCES `t_order` (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table t_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `uid` char(32) NOT NULL,
  `loginname` varchar(50) NOT NULL DEFAULT '',
  `loginpass` varchar(50) NOT NULL DEFAULT '',
  `email` varchar(50) NOT NULL DEFAULT '',
  `status` tinyint(1) NOT NULL,
  `activationCode` char(64) NOT NULL DEFAULT '',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `loginname` (`loginname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;

INSERT INTO `t_user` (`uid`, `loginname`, `loginpass`, `email`, `status`, `activationCode`)
VALUES
	('32DB3700D2564254982BC58B0E4D95BC','liSi','12','itcast_cxf@126.com',1,'15682E9C4D2849E2AB5D8AFF55D2F7BA87126B0EF55A45C6A136E3CAA90D60F2'),
	('4DE7E4D829A54D4FAB150B7451407198','def','def','itcast_cxf@soh.com',0,'D00FAA82457748FF8C1B912042E615B39F3602053E154181B98CDD48D9ECFC96'),
	('531D8A16D524478D86F8A115FE95D93F','zhangSan','1234','itcast_cxf@163.com',1,'FCF142D04C4A420992FF4E7BAC92C1E58AF905F1A46B4818BB455BD925E52DDD'),
	('55790D9C1A1845738E6D93866A148C7E','wangWu','123','itcast_cxf@sina.com',1,'659903B3D5FF4576B82425A593962DFE64B6137EBE934AE5AE19F614E71F4549'),
	('608E7DB1DA5D4FF9BF00586129506A3A','cirtuslimon','123','1589826173@qq.com',0,'4D573D12924F4BCB8B4C60AA6CD2F822C5F95C1E9DC74638A8EEA502D71CE73C'),
	('9CC972DFA2D4481F89841A46FD1B3E7B','abc','abc','itcast_cxf@qq.com',0,'D7CEB3DE44364749A4807D98F8B2F63017FDFED9FFC842B6BBC64E20698FED5F'),
	('B0D4BDF9CB684302A84ABD80485FA833','cirtus','123','cirtuslimon@163.com',0,'E28E5D84EFD84723B3FF789590EF29479FA2A56E12424CB0AE5685CE075E072F'),
	('B50ADE921BF14F6EB5331777B1874763','aabb','aaa','abc@abc.cn',0,'10032D0DFD2B49DC98CA9739F929656B6819FA1C10EC44F8A95206D0C3D62094'),
	('DF214F9440534B6B90FA898F989C9E5B','knight','123','3415858760@qq.com',0,'907C16E460D3484094DABB18511138AB41E6469D16BA436394414B301402F8C7'),
	('x','刘备','123','liuBei@163.com',1,'x'),
	('xx','关羽','123','guanYu@163.com',1,'xx'),
	('xxx','张三','123','zhangFei@163.com',1,'xxx'),
	('xxxx','赵云','123','zhaoYun@163.com',1,'xxxx');

/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
