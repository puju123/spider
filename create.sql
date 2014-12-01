CREATE TABLE `spider_doc` (
  `id` varchar(100) NOT NULL,
  `title` text,
  `fetchtime` varchar(128) DEFAULT NULL,
  `html` longblob,
  `content` longtext,
  `pubdate` varchar(45) DEFAULT NULL,
  `author` varchar(45) DEFAULT NULL,
  `original` varchar(512) DEFAULT NULL,
  `status` varchar(32) DEFAULT NULL,
  `url` varchar(2048) NOT NULL,
  `charset` varchar(45) DEFAULT NULL,
  `fetchcode` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
