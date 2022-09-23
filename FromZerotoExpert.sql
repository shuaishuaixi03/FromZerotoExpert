USE `from_zeroto_expert`;
DROP TABLE IF EXISTS `disallow_word`;
CREATE TABLE `disallow_word` (
  `word_id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `word_value` varchar(32) NOT NULL COMMENT '敏感词值',
  `word_type` tinyint NOT NULL DEFAULT (0) COMMENT '敏感词类型',
  PRIMARY KEY (`word_id`),
  KEY `word_value_key` (`word_value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='敏感词表';
insert  into `disallow_word`(`word_id`,`word_value`,`word_type`) values (1,'尼玛',0),(2,'sb',0),(3,'草泥马',0);

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(32) NOT NULL COMMENT '用户昵称',
  `user_account` varchar(16) NOT NULL COMMENT '用户账号',
  `user_password` varchar(16) NOT NULL COMMENT '用户密码',
  `user_email` varchar(32) NOT NULL COMMENT '用户邮箱',
  PRIMARY KEY (`user_id`),
  KEY `user_name_key` (`user_name`),
  KEY `user_account_key` (`user_account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
