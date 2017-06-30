CREATE DATABASE `jfzy` DEFAULT CHARSET=utf8mb4;

USE `jfzy`;

DROP TABLE IF EXISTS `jf_article`;
CREATE TABLE `jf_article` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(500) NOT NULL DEFAULT '',
  `city_id` int(11) NOT NULL DEFAULT '0',
  `content` longtext,
  `tags` varchar(1000) DEFAULT '',
  `summary` varchar(1000) NOT NULL,
  `type` tinyint(1) NOT NULL DEFAULT '0',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `title_img_url` varchar(2000) NOT NULL,
  `share_icon_url` varchar(2000) DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `jf_lawyer`;
CREATE TABLE `jf_lawyer` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `city_id` int(11) NOT NULL DEFAULT '0',
  `status` int(11) NOT NULL DEFAULT '0',
  `name` varchar(50) NOT NULL DEFAULT '',
  `phone_num` varchar(50) NOT NULL DEFAULT '',
  `memo` varchar(200) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  `score` double DEFAULT '0',
  `on_process_task` int(4) DEFAULT '0',
  `finished_task` int(4) DEFAULT '0',
  `total_money` double DEFAULT '0',
  `finished_money` double DEFAULT '0',
  `open_id` varchar(45) DEFAULT NULL,
  `login_name` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `scored_tasks` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `jf_order`;
CREATE TABLE `jf_order` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `order_sn` varchar(50) DEFAULT '',
  `order_num` varchar(50) NOT NULL DEFAULT '',
  `city_id` int(11) DEFAULT '0',
  `user_id` int(11) NOT NULL,
  `user_name` varchar(50) NOT NULL DEFAULT '',
  `user_phone_num` varchar(50) NOT NULL DEFAULT '',
  `lawyer_id` int(11) NOT NULL DEFAULT '0',
  `lawyer_name` varchar(50) DEFAULT NULL,
  `lawyer_phone_num` varchar(50) DEFAULT NULL,
  `processor_id` int(11) NOT NULL DEFAULT '0',
  `processor_name` varchar(200) DEFAULT NULL,
  `product_id` int(11) NOT NULL,
  `product_name` varchar(200) NOT NULL DEFAULT '',
  `product_code` varchar(45) DEFAULT NULL,
  `origin_price` double NOT NULL,
  `real_price` double NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `start_time` timestamp NULL DEFAULT NULL,
  `end_time` timestamp NULL DEFAULT NULL,
  `phone_end_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `status` int(1) NOT NULL DEFAULT '0',
  `pay_way` int(1) DEFAULT NULL,
  `pay_status` int(1) NOT NULL DEFAULT '0',
  `type` int(1) DEFAULT NULL,
  `trade_phase` varchar(45) DEFAULT NULL,
  `trade_subphase` varchar(45) DEFAULT NULL,
  `has_signed` varchar(45) DEFAULT NULL,
  `role` varchar(45) DEFAULT NULL,
  `memo` varchar(2000) DEFAULT NULL,
  `address` varchar(2000) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `owner_name` varchar(45) DEFAULT NULL,
  `owner_phone` varchar(45) DEFAULT NULL,
  `notify_status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `jf_order_photo`;
CREATE TABLE `jf_order_photo` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `type` tinyint(1) NOT NULL DEFAULT '0',
  `photo_path` varchar(2000) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `jf_oss_role`;
CREATE TABLE `jf_oss_role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL DEFAULT '',
  `desc` varchar(2000) NOT NULL DEFAULT '',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0:启用,1:禁用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `jf_oss_user`;
CREATE TABLE `jf_oss_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `city_id` int(11) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `phone_num` varchar(45) DEFAULT NULL,
  `role` varchar(45) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  `status` tinyint(1) DEFAULT '0',
  `login_name` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `jf_phase`;
CREATE TABLE `jf_phase` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `phase` varchar(200) NOT NULL DEFAULT '',
  `parent_phase_id` int(11) NOT NULL DEFAULT '0',
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `jf_product`;
CREATE TABLE `jf_product` (
  `id` int(11) unsigned NOT NULL,
  `name` varchar(45) NOT NULL,
  `price` double NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  `is_deleted` tinyint(1) DEFAULT NULL,
  `code` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `jf_property`;
CREATE TABLE `jf_property` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `type_id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `jf_tag`;
CREATE TABLE `jf_tag` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL DEFAULT '',
  `weight` int(11) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `jf_user`;
CREATE TABLE `jf_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `real_name` varchar(100) DEFAULT NULL,
  `memo` varchar(2000) DEFAULT NULL,
  `address` varchar(2000) DEFAULT NULL,
  `postcode` varchar(10) DEFAULT NULL,
  `status` tinyint(2) NOT NULL,
  `head_img` varchar(2000) DEFAULT NULL,
  `money` double DEFAULT '0',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `city` varchar(45) DEFAULT NULL,
  `gender` tinyint(1) DEFAULT NULL,
  `level` tinyint(2) DEFAULT NULL,
  `email` varchar(225) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `jf_user_account`;
CREATE TABLE `jf_user_account` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `status` int(1) NOT NULL,
  `type` int(2) DEFAULT NULL,
  `value` varchar(225) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='比如两个微信号，那么就是两个user对应同一个user_account';

DROP TABLE IF EXISTS `jf_lawyer_reply`;
CREATE TABLE `jf_lawyer_reply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) DEFAULT NULL,
  `lawyer_id` int(11) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL,
  `product_code` varchar(45) DEFAULT NULL,
  `simple_reply` longtext,
  `reply_summary` longtext,
  `reply_suggests` longtext,
  `reply_rules` longtext,
  `score` double DEFAULT '0',
  `has_hukou` tinyint(1) DEFAULT '0',
  `status` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `jf_oss_permission`;
CREATE TABLE `jf_oss_permission` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `role` varchar(50) NOT NULL DEFAULT '',
  `permission` varchar(200) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `jf_id`;
CREATE TABLE `jf_id` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `type` int(11) NOT NULL DEFAULT '0',
  `page` int(11) NOT NULL DEFAULT '0',
  `current_id` int(11) NOT NULL DEFAULT '0',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uidx_type_page` (`type`,`page`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;