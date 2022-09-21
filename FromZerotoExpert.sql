USE from_zeroto_expert;
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    user_id INT NOT NULL AUTO_INCREMENT COMMENT "主键",
    user_name VARCHAR(32) NOT NULL COMMENT "用户昵称",
    user_account VARCHAR(16) NOT NULL COMMENT "用户账号",
    user_password VARCHAR(16) NOT NULL COMMENT "用户密码",
    user_email VARCHAR(32) NOT NULL COMMENT "用户邮箱",
    PRIMARY KEY(user_id),
    UNIQUE KEY `user_name_key` (user_name),
    UNIQUE KEY `user_account_key` (user_account)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT="用户表";