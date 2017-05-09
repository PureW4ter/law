INSERT INTO `jf_tag` (`id`, `name`, `weight`)
VALUES
	(1,'律师',1),
	(2,'房屋纠纷',2),
	(3,'房产',3),
	(4,'离婚',3),
	(5,'税费承担',1),
	(6,'户口问题',2),
	(7,'财产',1),
	(8,'合同',2),
	(9,'政策变动',3),
	(10,'户籍',1);
	
INSERT INTO `jf_oss_role` (`id`, `name`, `desc`, `status`)
VALUES
	(1,'super_admin','特别管理员',0),
	(2,'admin','一般管理员',0),
	(3,'order_admin','订单管理员',0),
	(4,'lawyer','律师',0);