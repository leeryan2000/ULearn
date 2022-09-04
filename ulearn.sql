CREATE DATABASE IF NOT EXISTS ulearn;

USE ulearn;

-- ----------------------------
-- Table structure for u_user
-- ----------------------------
DROP TABLE IF EXISTS `u_user`;
CREATE TABLE `u_user` (
	`id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
	`username` VARCHAR(100) NOT NULL COMMENT '用户名, 邮箱前缀',
	`password` VARCHAR(255) NOT NULL COMMENT '用户密码',
	`email` VARCHAR(100) NOT NULL COMMENT '用户邮箱',
	`create_time` DATETIME NOT NULL COMMENT '用户创建时间',
	`key` VARCHAR(100) NOT NULL COMMENT '加密秘钥',
	PRIMARY KEY (`id`)
) ENGINE=INNODB CHARACTER SET=utf8 COMMENT='用户表';

-- ----------------------------
-- Table structure for u_question
-- ----------------------------
DROP TABLE IF EXISTS `u_question`;
CREATE TABLE `u_question` (
	`id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '问题ID',
	`u_id` BIGINT NOT NULL COMMENT '创建者ID',
	`title` VARCHAR(100) NOT NULL COMMENT '标题',
	`content` VARCHAR(100) NOT NULL COMMENT '内容',
	`create_time` DATE NOT NULL COMMENT '创建时间',
	`view` INT DEFAULT 0 COMMENT '浏览次数',
	PRIMARY KEY (`id`)
) ENGINE=INNODB CHARACTER SET=utf8 COMMENT='问题表';


-- ----------------------------
-- Table structure for u_answer
-- ----------------------------
DROP TABLE IF EXISTS `u_answer`; 
CREATE TABLE `u_answer` (
	`id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '回答ID',
	`u_id` BIGINT NOT NULL COMMENT '用户ID',
	`content` BIGINT NOT NULL COMMENT '内容',
	`create_time` DATE NOT NULL COMMENT '创建时间',
	PRIMARY KEY (`id`)
) ENGINE=INNODB CHARACTER SET=utf8 COMMENT='问题表';


-- ----------------------------
-- Table structure for u_tag
-- ----------------------------
DROP TABLE IF EXISTS `u_tag`;
CREATE TABLE `u_tag` (
	`id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '标签ID',
	`name` VARCHAR(50) NOT NULL COMMENT '标签名',
	PRIMARY KEY (`id`)
) ENGINE=INNODB CHARACTER SET=utf8 COMMENT='标签表';

INSERT INTO u_tag(`name`) VALUES('CS');
INSERT INTO u_tag(`name`) VALUES('FAM');
INSERT INTO u_tag(`name`) VALUES('IBM');
INSERT INTO u_tag(`name`) VALUES('IC');
INSERT INTO u_tag(`name`) VALUES('AEE');


-- ----------------------------
-- Table structure for u_question_tag
-- ----------------------------
DROP TABLE IF EXISTS `u_question_tag`;
CREATE TABLE `u_question_tag` (
	`q_id` BIGINT NOT NULL COMMENT '问题ID',
	`t_id` BIGINT NOT NULL COMMENT '标签ID',
	CONSTRAINT unique_q_t UNIQUE (`q_id`, `t_id`) COMMENT '确保同一个问题没有重复使用同一个标签'
) ENGINE=INNODB CHARACTER SET=utf8 COMMENT='问题及标签联表';

-- ----------------------------
-- Table structure for u_vote_question
-- ----------------------------
DROP TABLE IF EXISTS `u_vote_question`;
CREATE TABLE `u_vote_question` (
	`u_id` BIGINT NOT NULL COMMENT '用户ID',
	`q_id` BIGINT NOT NULL COMMENT '问题ID',
	`create_time` DATE NOT NULL COMMENT '创建时间',
	CONSTRAINT unique_u_q UNIQUE (`u_id`, `q_id`)
) ENGINE=INNODB CHARACTER SET=utf8 COMMENT='用户投票及问题联表';


-- ----------------------------
-- Table structure for u_vote_answer
-- ----------------------------
DROP TABLE IF EXISTS `u_vote_answer`;
CREATE TABLE `u_vote_answer` (
	`u_id` BIGINT NOT NULL COMMENT '用户ID',
	`a_id` BIGINT NOT NULL COMMENT '回答ID',
	`create_time` DATE NOT NULL COMMENT '创建时间',
	CONSTRAINT unique_u_a UNIQUE (`u_id`, `a_id`)
) ENGINE=INNODB CHARACTER SET=utf8 COMMENT='用户投票及回答联表';


