CREATE DATABASE `spider_doc` /*!40100 DEFAULT CHARACTER SET utf8 */;
CREATE TABLE `spider_doc` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` text,
  `fetchtime` varchar(45) DEFAULT NULL,
  `html` longtext,
  `content` longtext,
  `pubdate` varchar(45) DEFAULT NULL,
  `author` varchar(45) DEFAULT NULL,
  `original` varchar(512) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `url` varchar(512) NOT NULL,
  `charset` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
