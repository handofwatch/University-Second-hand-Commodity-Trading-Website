# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.28)
# Database: goods
# Generation Time: 2020-01-04 22:04:44 +0000
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


# Dump of table t_goods
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_goods`;

CREATE TABLE `t_goods` (
  `gid` char(32) NOT NULL DEFAULT '',
  `userId` char(32) NOT NULL DEFAULT '',
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
  KEY `t_goods_t_user` (`userId`),
  CONSTRAINT `t_goods_t_category` FOREIGN KEY (`cid`) REFERENCES `t_category` (`cid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_goods_t_user` FOREIGN KEY (`userId`) REFERENCES `t_user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `t_goods` WRITE;
/*!40000 ALTER TABLE `t_goods` DISABLE KEYS */;

INSERT INTO `t_goods` (`gid`, `userId`, `gname`, `price`, `cid`, `image_w`, `image_w2`, `image_b`, `gdesc`, `orderBy`, `gstatus`)
VALUES
	('5582A4D0A7D240CB996CCA6C1E5A0340','531D8A16D524478D86F8A115FE95D93F','请输入您的商品名',59.00,'56AD72718C524147A2485E5F4A95A062','goods_img/7102DF1795274599BED6A04278D8073D.jpg','goods_img/A26076E1EB4C40D39EE6130B1275F950.jpg','goods_img/AF3B9B2A213A4E8AAD92F828A557B6C0.jpg','请您添加商品描述',112,4);

/*!40000 ALTER TABLE `t_goods` ENABLE KEYS */;
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

LOCK TABLES `t_order` WRITE;
/*!40000 ALTER TABLE `t_order` DISABLE KEYS */;

INSERT INTO `t_order` (`oid`, `ordertime`, `total`, `address`, `uid`, `buyername`, `phone`)
VALUES
	('CA6A93E1AAD145E185DDD5FDD72E2251','2020-01-05 05:51:30',59.00,'上海市同济大学嘉定校区','32DB3700D2564254982BC58B0E4D95BC','回守涛','13999999999');

/*!40000 ALTER TABLE `t_order` ENABLE KEYS */;
UNLOCK TABLES;


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

LOCK TABLES `t_orderitem` WRITE;
/*!40000 ALTER TABLE `t_orderitem` DISABLE KEYS */;

INSERT INTO `t_orderitem` (`orderItemId`, `price`, `gid`, `gname`, `image_b`, `oid`, `orderstatus`)
VALUES
	('8C8B40D647A749FAAB38CE636392A570',59.00,'5582A4D0A7D240CB996CCA6C1E5A0340','请输入您的商品名','goods_img/AF3B9B2A213A4E8AAD92F828A557B6C0.jpg','CA6A93E1AAD145E185DDD5FDD72E2251',4);

/*!40000 ALTER TABLE `t_orderitem` ENABLE KEYS */;
UNLOCK TABLES;


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
	('32DB3700D2564254982BC58B0E4D95BC','liSi','1234','itcast_cxf@126.com',1,'15682E9C4D2849E2AB5D8AFF55D2F7BA87126B0EF55A45C6A136E3CAA90D60F2'),
	('4DE7E4D829A54D4FAB150B7451407198','def','def','itcast_cxf@soh.com',0,'D00FAA82457748FF8C1B912042E615B39F3602053E154181B98CDD48D9ECFC96'),
	('531D8A16D524478D86F8A115FE95D93F','zhangSan','123','itcast_cxf@163.com',1,'FCF142D04C4A420992FF4E7BAC92C1E58AF905F1A46B4818BB455BD925E52DDD'),
	('55790D9C1A1845738E6D93866A148C7E','wangWu','123','itcast_cxf@sina.com',1,'659903B3D5FF4576B82425A593962DFE64B6137EBE934AE5AE19F614E71F4549'),
	('608E7DB1DA5D4FF9BF00586129506A3A','cirtuslimon','123','1589826173@qq.com',0,'4D573D12924F4BCB8B4C60AA6CD2F822C5F95C1E9DC74638A8EEA502D71CE73C'),
	('9CC972DFA2D4481F89841A46FD1B3E7B','abc','abc','itcast_cxf@qq.com',0,'D7CEB3DE44364749A4807D98F8B2F63017FDFED9FFC842B6BBC64E20698FED5F'),
	('B0D4BDF9CB684302A84ABD80485FA833','cirtus','123','cirtuslimon@163.com',0,'E28E5D84EFD84723B3FF789590EF29479FA2A56E12424CB0AE5685CE075E072F'),
	('B50ADE921BF14F6EB5331777B1874763','aabb','aaa','abc@abc.cn',0,'10032D0DFD2B49DC98CA9739F929656B6819FA1C10EC44F8A95206D0C3D62094'),
	('C3F9448C30954F8E9FB88E96E2886EE3','123','123','13764688@qq.com',0,'60F5AFAC00AD40DCBC2BAC28DE946A892C23CB57BB49407BA3C23DA1BB4950E2'),
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
