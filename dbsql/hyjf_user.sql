/*
Navicat MySQL Data Transfer

Source Server         : beta2_47.105.206.3
Source Server Version : 50725
Source Host           : 47.105.206.3:33306
Source Database       : hyjf_user

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2019-07-24 14:57:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ht_account_bank
-- ----------------------------
DROP TABLE IF EXISTS `ht_account_bank`;
CREATE TABLE `ht_account_bank` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) unsigned NOT NULL COMMENT '用户ID',
  `bank_status` tinyint(2) DEFAULT '0' COMMENT '禁用状态',
  `account` varchar(30) DEFAULT '' COMMENT '银行账号',
  `bank` varchar(50) DEFAULT NULL COMMENT '所属银行',
  `branch` varchar(100) DEFAULT NULL COMMENT '支行',
  `province` char(4) DEFAULT '0' COMMENT '省份',
  `city` char(4) DEFAULT '0' COMMENT '城市',
  `area` int(5) DEFAULT '0' COMMENT '区',
  `card_type` varchar(1) DEFAULT '0' COMMENT '卡类型 0普通提现卡1默认卡2快捷支付卡',
  `respcode` varchar(10) DEFAULT NULL,
  `respdesc` varchar(100) DEFAULT NULL,
  `create_ip` varchar(15) DEFAULT '',
  `update_ip` varchar(15) DEFAULT '',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52336098 DEFAULT CHARSET=utf8 COMMENT='用户银行卡表';

-- ----------------------------
-- Table structure for ht_account_chinapnr
-- ----------------------------
DROP TABLE IF EXISTS `ht_account_chinapnr`;
CREATE TABLE `ht_account_chinapnr` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `chinapnr_usrid` varchar(50) NOT NULL COMMENT '汇付帐号',
  `chinapnr_usrcustid` bigint(16) NOT NULL COMMENT '客户号',
  `isok` tinyint(1) DEFAULT '0',
  `create_ip` varchar(15) DEFAULT '',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`) USING BTREE,
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户汇付账户信息表';

-- ----------------------------
-- Table structure for ht_account_mobile_synch
-- ----------------------------
DROP TABLE IF EXISTS `ht_account_mobile_synch`;
CREATE TABLE `ht_account_mobile_synch` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `username` varchar(30) NOT NULL COMMENT '用户名',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `accountId` varchar(20) DEFAULT NULL COMMENT '电子账号',
  `account` varchar(20) DEFAULT NULL COMMENT '银行卡号',
  `new_account` varchar(20) DEFAULT NULL COMMENT '新银行卡号',
  `mobile` char(11) DEFAULT NULL COMMENT '手机号',
  `new_mobile` char(11) DEFAULT NULL COMMENT '新手机号',
  `searchtime` int(11) NOT NULL DEFAULT '0' COMMENT '查询次数',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '同步状态：0：未同步1：已同步',
  `flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '同步标识：1：手机号同步，2：银行卡同步',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='银行卡号手机号同步表';

-- ----------------------------
-- Table structure for ht_app_utm_reg
-- ----------------------------
DROP TABLE IF EXISTS `ht_app_utm_reg`;
CREATE TABLE `ht_app_utm_reg` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `source_id` int(10) NOT NULL COMMENT '渠道编号',
  `source_name` varchar(256) NOT NULL COMMENT '渠道名称',
  `user_id` int(11) NOT NULL,
  `user_name` varchar(256) NOT NULL COMMENT '用户名',
  `register_time` datetime NOT NULL COMMENT '注册时间',
  `open_account_time` datetime DEFAULT NULL COMMENT '开户时间',
  `first_invest_time` int(11) DEFAULT '0' COMMENT '首次投资时间',
  `invest_amount` decimal(13,2) DEFAULT '0.00' COMMENT '首投金额',
  `invest_project_type` varchar(10) DEFAULT NULL COMMENT '首次投资标的的项目类型',
  `invest_project_period` varchar(10) DEFAULT NULL COMMENT '首次投资标的的项目期限',
  `cumulative_invest` decimal(20,2) NOT NULL COMMENT '累积投资金额',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=366 DEFAULT CHARSET=utf8 COMMENT='app渠道统计详情表';

-- ----------------------------
-- Table structure for ht_appointment_auth_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_appointment_auth_log`;
CREATE TABLE `ht_appointment_auth_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `auth_type` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '授权方式：0完全授权，1部分授权',
  `auth_status` tinyint(1) unsigned NOT NULL COMMENT '操作状态：0：取消授权1：授权',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='授权记录表';

-- ----------------------------
-- Table structure for ht_appointment_recod_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_appointment_recod_log`;
CREATE TABLE `ht_appointment_recod_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `recod` int(3) DEFAULT '0' COMMENT '违约分值',
  `recod_total` int(3) DEFAULT '0' COMMENT '违约总计分值',
  `recod_nid` varchar(30) DEFAULT NULL COMMENT '违约标号',
  `recod_money` decimal(20,0) DEFAULT NULL COMMENT '违约金额',
  `apoint_order_id` varchar(30) DEFAULT NULL COMMENT '订单号',
  `recod_remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `recod_type` int(1) DEFAULT NULL COMMENT '违约类型：0取消预约，1金额不足',
  `user_id` int(10) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(20) DEFAULT NULL COMMENT '用户名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_bank_cancellation_account
-- ----------------------------
DROP TABLE IF EXISTS `ht_bank_cancellation_account`;
CREATE TABLE `ht_bank_cancellation_account` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL COMMENT '用户ID',
  `username` varchar(30) DEFAULT '' COMMENT '用户名',
  `mobile` char(11) DEFAULT '' COMMENT '销户前手机号',
  `bank_open_account` tinyint(1) unsigned DEFAULT '0' COMMENT '是否银行开户,0未开户,1已开户',
  `truename` varchar(30) DEFAULT '' COMMENT '真实姓名',
  `idcard` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `card_no` varchar(30) DEFAULT '' COMMENT '销户前银行账号',
  `bank_account` varchar(20) NOT NULL COMMENT '用户客户号',
  `reg_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `create_user_id` int(11) DEFAULT NULL COMMENT '创建者用户ID',
  `create_user_name` varchar(30) DEFAULT NULL COMMENT '创建者',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新者用户ID',
  `update_user_name` varchar(30) DEFAULT NULL COMMENT '更新者',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='银行销户记录表';

-- ----------------------------
-- Table structure for ht_bank_card
-- ----------------------------
DROP TABLE IF EXISTS `ht_bank_card`;
CREATE TABLE `ht_bank_card` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户userId',
  `user_name` varchar(30) NOT NULL COMMENT '用户名',
  `card_no` varchar(30) DEFAULT '' COMMENT '银行账号',
  `bank_id` int(10) DEFAULT '0' COMMENT '银行ID',
  `mobile` char(11) DEFAULT NULL COMMENT '银行绑定的手机号码',
  `pay_alliance_code` varchar(30) DEFAULT NULL COMMENT '开户行联行行号',
  `bank` varchar(50) DEFAULT NULL COMMENT '所属银行',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '银行卡是否有效 0无效 1有效',
  `create_user_id` int(11) NOT NULL COMMENT '创建用户id',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=968 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for ht_bank_card_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_bank_card_log`;
CREATE TABLE `ht_bank_card_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(30) DEFAULT NULL,
  `card_no` varchar(30) DEFAULT '' COMMENT '银行账号',
  `bank_code` varchar(50) DEFAULT NULL COMMENT '银行缩写',
  `bank_name` varchar(100) DEFAULT NULL COMMENT '银行名称',
  `card_type` tinyint(1) DEFAULT NULL COMMENT '卡类型 0普通提现卡1默认卡2快捷支付卡',
  `operation_type` tinyint(1) DEFAULT NULL COMMENT '操作类型 0绑定 1删除',
  `status` tinyint(1) DEFAULT NULL COMMENT '操作状态  0 成功 1失败',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=168 DEFAULT CHARSET=utf8 COMMENT='用户银行卡操作记录表';

-- ----------------------------
-- Table structure for ht_bank_mobile_modify
-- ----------------------------
DROP TABLE IF EXISTS `ht_bank_mobile_modify`;
CREATE TABLE `ht_bank_mobile_modify` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `account` varchar(20) DEFAULT '' COMMENT '用户客户号',
  `bank_mobile_old` char(11) DEFAULT '' COMMENT '修改前银行预留手机号',
  `bank_mobile_new` char(11) DEFAULT '' COMMENT '修改后银行预留手机号',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0初始 1成功 ',
  `create_by` varchar(30) NOT NULL COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(30) NOT NULL COMMENT '修改人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `idx_account` (`account`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户修改银行预留手机号记录表';

-- ----------------------------
-- Table structure for ht_bank_open_account
-- ----------------------------
DROP TABLE IF EXISTS `ht_bank_open_account`;
CREATE TABLE `ht_bank_open_account` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `user_name` varchar(30) NOT NULL COMMENT '用户名',
  `account` varchar(20) NOT NULL COMMENT '用户客户号',
  `create_user_id` int(11) NOT NULL COMMENT '创建用户id',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`) USING BTREE,
  KEY `idx_account` (`account`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=949 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for ht_bank_open_account_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_bank_open_account_log`;
CREATE TABLE `ht_bank_open_account_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '开户用户userId',
  `user_name` varchar(30) NOT NULL COMMENT '开户用户名',
  `order_id` varchar(30) NOT NULL COMMENT '用户开户订单号',
  `mobile` char(11) DEFAULT NULL COMMENT '用户手机号',
  `id_type` varchar(3) DEFAULT NULL COMMENT '证件类型',
  `id_no` varchar(20) DEFAULT NULL COMMENT '身份证号码',
  `name` varchar(10) DEFAULT NULL COMMENT '真实姓名',
  `card_no` varchar(30) DEFAULT NULL COMMENT '银行卡号',
  `acct_use` varchar(5) DEFAULT NULL COMMENT '账户用途',
  `last_srv_auth_code` varchar(60) DEFAULT NULL COMMENT '前导业务授权码',
  `user_ip` varchar(32) DEFAULT NULL COMMENT '用户ip',
  `acq_res` varchar(200) DEFAULT NULL COMMENT '请求方保留字段',
  `client` tinyint(1) NOT NULL DEFAULT '0' COMMENT '开户平台 0PC 1微官网 2Android 3IOS 4其他',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '开户状态 0初始 1验证码发送成功 2开户中 3开户失败',
  `ret_code` varchar(20) DEFAULT NULL,
  `ret_msg` varchar(200) DEFAULT NULL,
  `create_user_id` int(11) NOT NULL COMMENT '创建用户id',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `order_id` (`order_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1760 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Table structure for ht_bank_sms_auth_code
-- ----------------------------
DROP TABLE IF EXISTS `ht_bank_sms_auth_code`;
CREATE TABLE `ht_bank_sms_auth_code` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户userId',
  `srv_tx_code` varchar(40) NOT NULL COMMENT '请求类型',
  `srv_auth_code` varchar(30) NOT NULL COMMENT '银行卡号',
  `sms_seq` varchar(10) DEFAULT NULL COMMENT '短信序列号',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效 0无效 1有效',
  `create_user_id` int(11) NOT NULL COMMENT '创建用户id',
  `create_user_name` varchar(20) NOT NULL COMMENT '创建用户名',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户名',
  `update_user_name` varchar(20) DEFAULT NULL COMMENT '更新用户名',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=237 DEFAULT CHARSET=utf8 COMMENT='银行验证码授权表';

-- ----------------------------
-- Table structure for ht_bind_user
-- ----------------------------
DROP TABLE IF EXISTS `ht_bind_user`;
CREATE TABLE `ht_bind_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `bind_unique_id` varchar(20) NOT NULL COMMENT '绑定唯一id（第三方提供）',
  `bind_platform_id` int(11) NOT NULL COMMENT '绑定用户第三方平台编号 汇晶社：2000000011',
  `del_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标识',
  `create_user_id` int(11) DEFAULT NULL COMMENT '创建用户',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_unique` (`bind_unique_id`,`bind_platform_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=230 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='第三方平台用户绑定表';

-- ----------------------------
-- Table structure for ht_borrow_appoint
-- ----------------------------
DROP TABLE IF EXISTS `ht_borrow_appoint`;
CREATE TABLE `ht_borrow_appoint` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) unsigned DEFAULT '0' COMMENT '预约用户id',
  `order_id` varchar(100) DEFAULT NULL COMMENT '预约订单号',
  `tender_nid` varchar(100) DEFAULT NULL COMMENT '投资订单号',
  `borrow_nid` varchar(50) DEFAULT NULL COMMENT '项目编号',
  `borrow_period` int(10) DEFAULT NULL COMMENT '项目期限',
  `borrow_apr` decimal(10,2) DEFAULT NULL COMMENT '年华收益',
  `borrow_account` decimal(11,2) DEFAULT '0.00' COMMENT '借款金额',
  `account` decimal(11,2) DEFAULT '0.00' COMMENT '预约金额',
  `appoint_status` tinyint(1) unsigned DEFAULT '0' COMMENT '预约状态 0 预约失败 1预约成功 2取消预约 ',
  `appoint_time` datetime DEFAULT NULL COMMENT '预约时间',
  `appoint_remark` varchar(255) DEFAULT NULL COMMENT '预约备注',
  `cancel_time` datetime DEFAULT NULL COMMENT '撤销时间',
  `tender_status` tinyint(1) DEFAULT NULL COMMENT '投资状态 0投资中 1投资成功 2投资失败',
  `tender_time` datetime DEFAULT NULL COMMENT '投资时间',
  `tender_remark` varchar(255) DEFAULT NULL COMMENT '投资备注',
  `create_user_id` int(11) unsigned DEFAULT NULL COMMENT '创建者id',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_id` (`order_id`),
  UNIQUE KEY `tender_nid` (`tender_nid`),
  KEY `user_id` (`user_id`),
  KEY `borrow_nid` (`borrow_nid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户预约表';

-- ----------------------------
-- Table structure for ht_callcenter_service_users
-- ----------------------------
DROP TABLE IF EXISTS `ht_callcenter_service_users`;
CREATE TABLE `ht_callcenter_service_users` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL DEFAULT '',
  `assigned` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0:呼叫中心未分配客服,1呼叫中心已分配客服',
  `insdate` datetime DEFAULT NULL COMMENT '登陆时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='上海呼叫中心用用户客服分配表';

-- ----------------------------
-- Table structure for ht_cert_user
-- ----------------------------
DROP TABLE IF EXISTS `ht_cert_user`;
CREATE TABLE `ht_cert_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `log_ord_id` varchar(100) NOT NULL COMMENT 'mongo里面的订单号',
  `user_id` int(11) unsigned NOT NULL COMMENT '用户ID',
  `user_name` varchar(100) DEFAULT '' COMMENT '用户名',
  `user_id_card_hash` varchar(100) DEFAULT NULL COMMENT '用户标示哈希',
  `hash_value` varchar(100) DEFAULT NULL COMMENT '哈希值',
  `borrow_nid` varchar(100) DEFAULT NULL COMMENT '上送关联的BorrowNid',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`) USING BTREE,
  KEY `idx_user_id_card_hash` (`user_id_card_hash`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=851 DEFAULT CHARSET=utf8 COMMENT='国家互联网应急中心已上送用户表';

-- ----------------------------
-- Table structure for ht_certificate_authority
-- ----------------------------
DROP TABLE IF EXISTS `ht_certificate_authority`;
CREATE TABLE `ht_certificate_authority` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户Id',
  `user_name` varchar(30) NOT NULL DEFAULT '' COMMENT '用户名',
  `mobile` char(11) NOT NULL DEFAULT '' COMMENT '手机号',
  `true_name` varchar(30) DEFAULT NULL COMMENT '姓名',
  `id_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '证件类型:0:身份证,1:企业证件号码',
  `id_no` varchar(20) NOT NULL DEFAULT '' COMMENT '证件号码',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `status` varchar(20) DEFAULT NULL COMMENT '状态:success:成功,error:失败',
  `code` varchar(10) NOT NULL DEFAULT '' COMMENT '认证状态:1000：操作成功 2001：参数缺失或者不合法 2002：业务异常，失败原因见 msg 2003：其他错误，请联系法大大',
  `customer_id` varchar(32) NOT NULL DEFAULT '' COMMENT '客户编号',
  `remark` varchar(50) NOT NULL DEFAULT '' COMMENT '备注',
  `create_user_id` int(11) NOT NULL COMMENT '创建用户id',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `user_name` (`user_name`) USING BTREE,
  KEY `mobile` (`mobile`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=941 DEFAULT CHARSET=utf8 COMMENT='用户CA认证记录表';

-- ----------------------------
-- Table structure for ht_corp_open_account_record
-- ----------------------------
DROP TABLE IF EXISTS `ht_corp_open_account_record`;
CREATE TABLE `ht_corp_open_account_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(11) NOT NULL COMMENT '用户编号',
  `username` varchar(256) NOT NULL COMMENT '用户名',
  `busi_code` char(30) NOT NULL COMMENT '组织机构代码/社会信用号',
  `busi_name` varchar(256) DEFAULT NULL COMMENT '企业名称',
  `guar_type` char(6) DEFAULT NULL COMMENT '是否担保类型，Y：是 N：否',
  `open_bank_id` char(8) DEFAULT '' COMMENT '开户银行编号',
  `account` varchar(30) DEFAULT '' COMMENT '银行账号',
  `status` tinyint(1) unsigned DEFAULT '0' COMMENT '企业开户状态  0初始 1提交 2审核中 3 审核拒绝 4开户失败 5开户中 6开户成功',
  `is_bank` tinyint(1) DEFAULT '0' COMMENT '是否银行 0汇付 1江西银行',
  `card_type` int(11) DEFAULT NULL COMMENT '证件类型 20：其他证件（组织机构代码）25：社会信用号',
  `tax_registration_code` char(30) DEFAULT NULL COMMENT '税务登记证',
  `buse_no` char(30) DEFAULT NULL COMMENT '营业执照编号',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注（失败原因）',
  `create_user` int(11) DEFAULT NULL COMMENT '创建者',
  `update_user` int(11) DEFAULT NULL COMMENT '更新者',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='企业用户开户记录';

-- ----------------------------
-- Table structure for ht_customer_task_config
-- ----------------------------
DROP TABLE IF EXISTS `ht_customer_task_config`;
CREATE TABLE `ht_customer_task_config` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `task_time` varchar(7) DEFAULT NULL COMMENT '任务时间,精确到月 yyyy-mm',
  `customer_name` varchar(20) DEFAULT NULL COMMENT '坐席姓名',
  `customer_group` tinyint(1) DEFAULT '0' COMMENT '坐席分组 1:新客组,2:老客组',
  `month_goal` decimal(16,2) DEFAULT NULL COMMENT '月目标（元）',
  `status` tinyint(1) DEFAULT '0' COMMENT '是否有效 1:有效,2:无效',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标识 0：未删除，1：已删除',
  `create_user_id` int(11) DEFAULT NULL COMMENT '创建用户ID',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_customer_name` (`customer_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='坐席信息表';

-- ----------------------------
-- Table structure for ht_evalation
-- ----------------------------
DROP TABLE IF EXISTS `ht_evalation`;
CREATE TABLE `ht_evalation` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `score_up` smallint(3) unsigned NOT NULL COMMENT '评分上限',
  `score_down` smallint(3) unsigned NOT NULL COMMENT '评分下线',
  `eval_type` varchar(20) NOT NULL COMMENT '测评类型',
  `summary` varchar(250) NOT NULL COMMENT '测评分析',
  `remarks` varchar(100) DEFAULT NULL COMMENT '备注',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '状态0是可用，1是不可用',
  `create_user` varchar(20) DEFAULT NULL COMMENT '创建人',
  `update_user` varchar(20) DEFAULT NULL COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='测评结果表';

-- ----------------------------
-- Table structure for ht_hjh_user_auth
-- ----------------------------
DROP TABLE IF EXISTS `ht_hjh_user_auth`;
CREATE TABLE `ht_hjh_user_auth` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `user_name` varchar(30) NOT NULL COMMENT '用户名',
  `auto_inves_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '自动投标授权状态 0: 未授权    1:已授权',
  `auto_credit_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '自动债转授权状态 0: 未授权    1:已授权',
  `auto_withdraw_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '自动预约取现状态 0: 未授权    1:已授权',
  `auto_consume_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '自动无密消费状态 0: 未授权    1:已授权',
  `auto_create_time` int(10) NOT NULL DEFAULT '0' COMMENT '授权时间',
  `auto_order_id` varchar(50) NOT NULL DEFAULT ' ' COMMENT '订单号',
  `auto_credit_order_id` varchar(50) NOT NULL DEFAULT '' COMMENT '自动债转订单号',
  `auto_credit_time` int(10) NOT NULL DEFAULT '0' COMMENT '自动债转授权时间',
  `auto_credit_end_time` varchar(8) NOT NULL DEFAULT '' COMMENT '自动债转授权过期时间',
  `auto_bid_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '自动投资授权时间',
  `auto_bid_end_time` varchar(8) DEFAULT '0' COMMENT '自动投资授权过期时间',
  `auto_payment_status` tinyint(1) DEFAULT '0' COMMENT '缴费授权状态 0: 未授权    1:已授权',
  `auto_payment_time` int(10) DEFAULT NULL COMMENT '缴费授权时间',
  `auto_payment_end_time` varchar(8) DEFAULT NULL COMMENT '缴费授权结束时间',
  `auto_repay_status` tinyint(1) DEFAULT '0' COMMENT '还款授权状态 0: 未授权    1:已授权',
  `auto_repay_time` int(10) DEFAULT NULL COMMENT '还款授权时间',
  `auto_repay_end_time` varchar(8) DEFAULT NULL COMMENT '还款授权结束时间',
  `inves_cancel_time` varchar(8) DEFAULT NULL COMMENT '投资授权解约时间',
  `credit_cancel_time` varchar(8) DEFAULT NULL COMMENT '债转授权解约时间',
  `payment_cancel_time` varchar(8) DEFAULT NULL COMMENT '缴费授权解约时间',
  `repay_cancel_time` varchar(8) DEFAULT NULL COMMENT '还款授权解约时间',
  `inves_max_amt` varchar(10) DEFAULT NULL COMMENT '自动投资单笔最大金额',
  `credit_max_amt` varchar(10) DEFAULT NULL COMMENT '自动债转单笔最大金额',
  `payment_max_amt` varchar(10) DEFAULT NULL COMMENT '缴费授权单笔最大金额',
  `repay_max_amt` varchar(10) DEFAULT NULL COMMENT '还款授权单笔最大金额',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0: 未删除    1:已删除',
  `tx_amount` int(11) DEFAULT NULL COMMENT '投资交易上限',
  `tot_amount` int(11) DEFAULT NULL COMMENT '投资交易总额上限',
  `create_user_id` int(11) DEFAULT NULL COMMENT '创建者',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新者',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=51749 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_hjh_user_auth_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_hjh_user_auth_log`;
CREATE TABLE `ht_hjh_user_auth_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `user_name` varchar(30) NOT NULL COMMENT '用户名',
  `order_id` varchar(30) NOT NULL COMMENT '订单号',
  `order_status` tinyint(1) NOT NULL COMMENT '订单状态 1：完成  2：未完成',
  `auth_type` tinyint(2) NOT NULL COMMENT '签约类型 0全部开通 1:自动投资签约 2:预约取现 3:无密消费 4:自动债转授权 5:缴费授权 6:还款授权 11:自动投资授权、自动债转授权 12:自动投资授权、缴费授权 13:自动债转授权、缴费授权 14:自动投资授权、自动债转授权、缴费授权',
  `operate_esb` tinyint(1) NOT NULL COMMENT '签约操作平台 0:pc  1:微信  2:安卓  3:IOS  4:其他',
  `auth_create_time` int(10) DEFAULT NULL COMMENT '签约授权时间',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0: 未删除    1:已删除',
  `create_user_id` int(11) DEFAULT NULL COMMENT '创建者',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新者',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`) USING BTREE,
  KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=109120 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_lab_platform
-- ----------------------------
DROP TABLE IF EXISTS `ht_lab_platform`;
CREATE TABLE `ht_lab_platform` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '平台ID',
  `pid` int(11) NOT NULL COMMENT '平台id',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '关联用户',
  `username` varchar(20) NOT NULL COMMENT '平台用户名',
  `password` varchar(32) NOT NULL COMMENT '平台密码',
  `ip` varchar(20) NOT NULL COMMENT '固定IP地址',
  `p_name` varchar(50) NOT NULL COMMENT '平台名称',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '接口开放状态：0为不开放，1为开放',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='第三方平台接入表';

-- ----------------------------
-- Table structure for ht_loan_subject_certificate_authority
-- ----------------------------
DROP TABLE IF EXISTS `ht_loan_subject_certificate_authority`;
CREATE TABLE `ht_loan_subject_certificate_authority` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL COMMENT '姓名或企业名称',
  `mobile` char(11) NOT NULL COMMENT '手机号',
  `id_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '证件类型:0:身份证,1:企业证件号码',
  `id_no` varchar(20) NOT NULL DEFAULT '' COMMENT '证件号码',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `status` varchar(20) DEFAULT NULL COMMENT '状态:success:成功,error:失败',
  `code` varchar(10) NOT NULL DEFAULT '' COMMENT '认证状态:1000：操作成功 2001：参数缺失或者不合法 2002：业务异常，失败原因见 msg 2003：其他错误，请联系法大大',
  `customer_id` varchar(32) NOT NULL DEFAULT '' COMMENT '客户编号',
  `remark` varchar(50) NOT NULL DEFAULT '' COMMENT '备注',
  `create_user_id` int(11) NOT NULL COMMENT '创建用户id',
  `create_user_name` varchar(20) NOT NULL COMMENT '创建用户名',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户名',
  `update_user_name` varchar(20) DEFAULT NULL COMMENT '更新用户名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `name` (`name`) USING BTREE,
  KEY `mobile` (`mobile`) USING BTREE,
  KEY `id_no` (`id_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='借款主体CA认证记录表';

-- ----------------------------
-- Table structure for ht_msp_abnormalcredit
-- ----------------------------
DROP TABLE IF EXISTS `ht_msp_abnormalcredit`;
CREATE TABLE `ht_msp_abnormalcredit` (
  `id` varchar(40) NOT NULL,
  `apply_id` varchar(50) DEFAULT NULL COMMENT '申请编号',
  `creditStartDate` varchar(10) DEFAULT NULL COMMENT '借款日期',
  `creditEndDate` varchar(10) DEFAULT NULL COMMENT '到期日期',
  `loanType` varchar(10) DEFAULT NULL COMMENT '借款类型',
  `loanMoney` varchar(20) DEFAULT NULL COMMENT '借款金额',
  `assureType` varchar(10) DEFAULT NULL COMMENT '担保方式',
  `loanPeriods` varchar(10) DEFAULT NULL COMMENT '借款日期',
  `num` varchar(10) DEFAULT NULL COMMENT '编号',
  PRIMARY KEY (`id`),
  KEY `apply_id` (`apply_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_msp_abnormalcreditdetail
-- ----------------------------
DROP TABLE IF EXISTS `ht_msp_abnormalcreditdetail`;
CREATE TABLE `ht_msp_abnormalcreditdetail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `abcd_id` varchar(50) DEFAULT NULL COMMENT '异常还款记录明细',
  `checkOverdueDate` varchar(10) DEFAULT NULL COMMENT '到期日期',
  `overdueDays` varchar(10) DEFAULT NULL COMMENT '借款类型',
  `overdueReason` varchar(10) DEFAULT NULL COMMENT '借款金额',
  `overdueState` varchar(10) DEFAULT NULL COMMENT '担保方式',
  `operTime` varchar(10) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_msp_anliinfos
-- ----------------------------
DROP TABLE IF EXISTS `ht_msp_anliinfos`;
CREATE TABLE `ht_msp_anliinfos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `apply_id` varchar(50) DEFAULT NULL COMMENT '申请编号',
  `anli_id` varchar(100) DEFAULT NULL COMMENT 'ID号',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `paperNum` varchar(20) DEFAULT NULL COMMENT '身份证',
  `dangshirenType` varchar(100) DEFAULT NULL COMMENT '当事人类型',
  `sex` varchar(10) DEFAULT NULL COMMENT '性别',
  `birthday` varchar(20) DEFAULT NULL COMMENT '生日',
  `anjianTitle` varchar(100) DEFAULT NULL COMMENT '案例标题',
  `endDate` varchar(100) DEFAULT NULL COMMENT '审结日期',
  `anjianType` varchar(100) DEFAULT NULL COMMENT '案件类型',
  `anjianNum` varchar(100) DEFAULT NULL COMMENT '案件字号',
  PRIMARY KEY (`id`),
  KEY `apply_id` (`apply_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_msp_apply
-- ----------------------------
DROP TABLE IF EXISTS `ht_msp_apply`;
CREATE TABLE `ht_msp_apply` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键 id',
  `apply_id` varchar(50) NOT NULL COMMENT '申请编号',
  `name` varchar(20) DEFAULT NULL COMMENT '借款人姓名',
  `identity_card` varchar(50) DEFAULT NULL COMMENT '身份证',
  `mobile_no` varchar(20) DEFAULT NULL COMMENT '手机号',
  `apply_date` varchar(20) DEFAULT NULL COMMENT '申请日期',
  `loan_type` varchar(2) DEFAULT NULL COMMENT '‘01’代表经营，‘02’代表消费，‘99’代表其他',
  `loan_money` decimal(22,2) DEFAULT NULL COMMENT '借款金额',
  `loan_time_limit` int(10) DEFAULT NULL COMMENT '借款期数',
  `credit_address` varchar(30) DEFAULT NULL COMMENT '借款城市（字典）',
  `share_identification` tinyint(1) unsigned DEFAULT '0' COMMENT '共享标识0为未共享,1为已共享',
  `service_type` varchar(2) DEFAULT NULL COMMENT '业务类型',
  `unredeemed_money` decimal(22,2) DEFAULT '0.00' COMMENT '未偿还本金',
  `repayment_status` varchar(2) DEFAULT NULL COMMENT '还款状态 01 正常（借款人已按时归还该月还款金额的全部）。提前归还该月应还款金额的全部（但尚未结清），也归入“01 正常”；02 逾期中；03 逾期核销；04 正常结清',
  `overdue_amount` decimal(22,2) DEFAULT '0.00' COMMENT '逾期总金额',
  `overdue_date` varchar(10) DEFAULT NULL COMMENT '逾期开始日期',
  `overdue_length` varchar(10) DEFAULT NULL COMMENT '逾期时长',
  `overdue_reason` varchar(2) DEFAULT NULL COMMENT '逾期原因 01 能力下降；02 恶意拖欠；03 身份欺诈；04 逃逸；05 犯罪入狱；06 疾病；07 死亡；99 其他',
  `approval_result` varchar(2) DEFAULT NULL COMMENT '审批结果 01 审批通过；02 审批拒绝；04 重新审批；05 客户取消',
  `approval_date` varchar(10) DEFAULT NULL COMMENT '审批日期',
  `contract_begin` varchar(10) DEFAULT NULL COMMENT '合同开始日期',
  `contract_end` varchar(10) DEFAULT NULL COMMENT '合同结束日期',
  `guarantee_type` varchar(2) DEFAULT NULL COMMENT '担保类型A 抵押；B 质押；C 担保；D 信用；E 保证；Y 其他',
  `create_user` varchar(30) DEFAULT NULL COMMENT '创建人',
  `create_time` int(10) DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(30) DEFAULT NULL COMMENT '修改人',
  `update_time` int(10) DEFAULT NULL COMMENT '修改时间',
  `del_flg` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `configure_id` int(10) DEFAULT NULL COMMENT '配置id',
  PRIMARY KEY (`id`),
  KEY `apply_id` (`apply_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='新建借款人查询表';

-- ----------------------------
-- Table structure for ht_msp_applydetails
-- ----------------------------
DROP TABLE IF EXISTS `ht_msp_applydetails`;
CREATE TABLE `ht_msp_applydetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `apply_id` varchar(50) DEFAULT NULL COMMENT '申请编号',
  `applyDate` varchar(100) DEFAULT NULL COMMENT '申请日期',
  `memberType` varchar(100) DEFAULT NULL COMMENT '会员类型',
  `creditAddress` varchar(100) DEFAULT NULL COMMENT '申请地点',
  `loanType` varchar(100) DEFAULT NULL COMMENT '借款类型',
  `loanMoney` varchar(100) DEFAULT NULL COMMENT '借款金额',
  `applyResult` varchar(100) DEFAULT NULL COMMENT '审批结果',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `apply_id` (`apply_id`)
) ENGINE=InnoDB AUTO_INCREMENT=523 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_msp_blackdata
-- ----------------------------
DROP TABLE IF EXISTS `ht_msp_blackdata`;
CREATE TABLE `ht_msp_blackdata` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `apply_id` varchar(50) DEFAULT NULL COMMENT '申请编号',
  `createDate` varchar(100) DEFAULT NULL COMMENT '报送/公开日期',
  `lastOverdueDate` varchar(100) DEFAULT NULL COMMENT '最近逾期开始日期',
  `creditAddress` varchar(100) DEFAULT NULL COMMENT '借款地点',
  `arrears` varchar(100) DEFAULT NULL COMMENT '欠款总额',
  `overdueDays` varchar(100) DEFAULT NULL COMMENT '逾期天数',
  `phone` varchar(100) DEFAULT NULL COMMENT '电话',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `residenceAddress` varchar(100) DEFAULT NULL COMMENT '户籍地址',
  `currentAddress` varchar(100) DEFAULT NULL COMMENT '现居地址',
  PRIMARY KEY (`id`),
  KEY `apply_id` (`apply_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_msp_configure
-- ----------------------------
DROP TABLE IF EXISTS `ht_msp_configure`;
CREATE TABLE `ht_msp_configure` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键 id',
  `configure_name` varchar(50) NOT NULL COMMENT '标的名称',
  `loan_type` varchar(2) DEFAULT NULL COMMENT '借款用途 ‘01’代表经营，‘02’代表消费，‘99’代表其他',
  `service_type` varchar(2) DEFAULT NULL COMMENT '业务类型 01 信用额度(不设置该选项)；02 一般借贷；03消费信贷；04 循环贷；05其他',
  `approval_result` varchar(2) DEFAULT NULL COMMENT '审批结果 01 审批通过；02 审批拒绝；04 重新审批；05 客户取消',
  `loan_money` decimal(22,2) DEFAULT NULL COMMENT '借款金额',
  `loan_time_limit` int(10) DEFAULT NULL COMMENT '借款期数',
  `credit_address` varchar(30) DEFAULT NULL COMMENT '借款城市（字典）',
  `guarantee_type` varchar(2) DEFAULT NULL COMMENT '担保类型 A 抵押；B 质押；C 担保；D 信用；E 保证；Y 其他',
  `unredeemed_money` decimal(22,2) DEFAULT '0.00' COMMENT '未偿还本金',
  `repayment_status` varchar(2) DEFAULT NULL COMMENT '当前还款状态 01 正常（借款人已按时归还该月还款金额的全部）。提前归还该月应还款金额的全部（但尚未结清），也归入“01 正常”；02 逾期中；03 逾期核销；04 正常结清',
  `overdue_amount` decimal(22,2) DEFAULT NULL COMMENT '逾期总金额 本金、利息、滞纳金等总和',
  `overdue_date` varchar(20) DEFAULT NULL COMMENT '逾期开始日期',
  `overdue_length` int(10) DEFAULT NULL COMMENT '逾期时长',
  `overdue_reason` varchar(2) DEFAULT NULL COMMENT '逾期原因 01 能力下降；02 恶意拖欠；03 身份欺诈；04 逃逸；05 犯罪入狱；06 疾病；07 死亡；99 其他',
  `del_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标识',
  `create_user` int(11) DEFAULT NULL COMMENT '创建人',
  `update_user` int(11) DEFAULT NULL COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='配置表';

-- ----------------------------
-- Table structure for ht_msp_degreeresult
-- ----------------------------
DROP TABLE IF EXISTS `ht_msp_degreeresult`;
CREATE TABLE `ht_msp_degreeresult` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `apply_id` varchar(50) DEFAULT NULL COMMENT '申请编号',
  `status` varchar(100) DEFAULT NULL COMMENT '学历查询结果状态',
  `school` varchar(100) DEFAULT NULL COMMENT '毕业院校',
  `degree` varchar(100) DEFAULT NULL COMMENT '学历',
  `admissionYear` varchar(100) DEFAULT NULL COMMENT '入学年份',
  `major` varchar(100) DEFAULT NULL COMMENT '专业',
  `graduationTime` varchar(100) DEFAULT NULL COMMENT '毕业时间',
  `graduationConclusion` varchar(100) DEFAULT NULL COMMENT '毕业结论',
  `degreeType` varchar(100) DEFAULT NULL COMMENT '学历类型',
  `schoolNature` varchar(100) DEFAULT NULL COMMENT '学校性质',
  `photoBase64Code` longtext COMMENT '照片的64位串，可以通过编译转换成图片',
  PRIMARY KEY (`id`),
  KEY `apply_id` (`apply_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_msp_fqz
-- ----------------------------
DROP TABLE IF EXISTS `ht_msp_fqz`;
CREATE TABLE `ht_msp_fqz` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `apply_id` varchar(50) DEFAULT NULL COMMENT '申请编号',
  `identityAuth` varchar(1) DEFAULT NULL COMMENT '姓名+身份证号认证查询结果',
  `identityAndPhotoAuth` varchar(1) DEFAULT NULL COMMENT '姓名+身份证号+照片认证查询结果',
  `identityPhoto` longtext COMMENT '身份证照片的base64位码（如果有）',
  `nameWithCardAuth` varchar(1) DEFAULT NULL COMMENT '姓名+银行卡认证查询结果',
  `nameIdentityCardAuth` varchar(1) DEFAULT NULL COMMENT '姓名+身份证号+银行卡认证查询结果',
  `degreeCode` varchar(1) DEFAULT NULL COMMENT '学历查询状态',
  `mspApply` varchar(100) DEFAULT NULL COMMENT '多重申请风险信息(一年内)，示例：1笔,总金额222元',
  `mspContract` varchar(100) DEFAULT NULL COMMENT '多重借贷风险信息(未结清)\r\n示例：1笔,总金额222元',
  `mspEndContract` varchar(100) DEFAULT NULL COMMENT '多重借贷风险信息（已结清）',
  `mspBlacklist` varchar(100) DEFAULT NULL COMMENT '违约风险信息(五年内)\r\n示例：1笔,总金额222元',
  `mspScore` varchar(100) DEFAULT NULL COMMENT '小额信贷交易行为评分，示例60',
  `countRiskBlack` varchar(100) DEFAULT NULL COMMENT '身份证号关联风险数（例：2个风险则返回“2”）',
  `mspBlack` varchar(2000) DEFAULT NULL COMMENT '姓名身份证号匹配的msp黑名单',
  `internetBlack` varchar(100) DEFAULT NULL COMMENT '姓名身份证号匹配的互联网黑名单',
  `allwinBlack` varchar(100) DEFAULT NULL COMMENT '合作机构风险库黑名单',
  `identityRisk1` varchar(2000) DEFAULT NULL,
  `identityRisk2` varchar(2000) DEFAULT NULL,
  `identityRisk3` varchar(2000) DEFAULT NULL,
  `countIdentityRisk` varchar(100) DEFAULT NULL,
  `phoneRisk1` varchar(100) DEFAULT NULL,
  `phoneRisk2` varchar(100) DEFAULT NULL,
  `phoneRisk3` varchar(100) DEFAULT NULL,
  `phoneRisk4` varchar(100) DEFAULT NULL,
  `phoneRisk5` varchar(100) DEFAULT NULL,
  `phoneRisk6` varchar(100) DEFAULT NULL,
  `phoneRisk7` varchar(100) DEFAULT NULL,
  `countPhoneRisk` varchar(100) DEFAULT NULL,
  `emailRisk1` varchar(100) DEFAULT NULL,
  `emailRisk2` varchar(100) DEFAULT NULL,
  `emailRisk3` varchar(100) DEFAULT NULL,
  `emailRisk4` varchar(100) DEFAULT NULL,
  `emailRisk5` varchar(100) DEFAULT NULL,
  `emailRisk6` varchar(100) DEFAULT NULL,
  `countEmailRisk` varchar(100) DEFAULT NULL,
  `qqRisk1` varchar(100) DEFAULT NULL,
  `qqRisk2` varchar(100) DEFAULT NULL,
  `qqRisk3` varchar(100) DEFAULT NULL,
  `qqRisk4` varchar(100) DEFAULT NULL,
  `qqRisk5` varchar(100) DEFAULT NULL,
  `countQqRisk` varchar(100) DEFAULT NULL,
  `companyRisk1` varchar(100) DEFAULT NULL,
  `companyRisk2` varchar(100) DEFAULT NULL,
  `countCompanyRisk` varchar(100) DEFAULT NULL,
  `applyAddressRisk1` varchar(100) DEFAULT NULL,
  `countAddressRisk` varchar(100) DEFAULT NULL,
  `queryTimesCode` varchar(100) DEFAULT NULL,
  `queryTimesInOneYea` varchar(100) DEFAULT NULL,
  `queryTimesInSix` varchar(100) DEFAULT NULL,
  `queryTimesInThree` varchar(100) DEFAULT NULL,
  `queryTimesInTwoYear` varchar(100) DEFAULT NULL,
  `haveSifa` varchar(100) DEFAULT NULL,
  `sifaCode` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `apply_id` (`apply_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_msp_normalcreditdetail
-- ----------------------------
DROP TABLE IF EXISTS `ht_msp_normalcreditdetail`;
CREATE TABLE `ht_msp_normalcreditdetail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `apply_id` varchar(50) DEFAULT NULL COMMENT '申请编号',
  `creditStartDate` varchar(100) DEFAULT NULL COMMENT '借款日期',
  `creditEndDate` varchar(100) DEFAULT NULL COMMENT '到期日期',
  `creditAddress` varchar(100) DEFAULT NULL COMMENT '借款地点',
  `loanType` varchar(100) DEFAULT NULL COMMENT '借款类型',
  `assureType` varchar(100) DEFAULT NULL COMMENT '担保方式',
  `loanMoney` varchar(100) DEFAULT NULL COMMENT '合同金额',
  `loanPeriods` varchar(100) DEFAULT NULL COMMENT '还款期数',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `num` varchar(100) DEFAULT NULL COMMENT '借款编号',
  PRIMARY KEY (`id`),
  KEY `apply_id` (`apply_id`)
) ENGINE=InnoDB AUTO_INCREMENT=208 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_msp_querydetail
-- ----------------------------
DROP TABLE IF EXISTS `ht_msp_querydetail`;
CREATE TABLE `ht_msp_querydetail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `apply_id` varchar(50) DEFAULT NULL COMMENT '申请编号',
  `queryDate` varchar(100) DEFAULT NULL COMMENT '查询日期',
  `memberType` varchar(100) DEFAULT NULL COMMENT '会员类型',
  `queryType` varchar(100) DEFAULT NULL COMMENT '查询类别',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `apply_id` (`apply_id`)
) ENGINE=InnoDB AUTO_INCREMENT=733 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_msp_region
-- ----------------------------
DROP TABLE IF EXISTS `ht_msp_region`;
CREATE TABLE `ht_msp_region` (
  `region_id` varchar(10) NOT NULL COMMENT '城市区号',
  `region_name` varchar(100) DEFAULT NULL COMMENT '城市名称',
  PRIMARY KEY (`region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_msp_shixininfos
-- ----------------------------
DROP TABLE IF EXISTS `ht_msp_shixininfos`;
CREATE TABLE `ht_msp_shixininfos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `apply_id` varchar(50) DEFAULT NULL COMMENT '申请编号',
  `shixin_id` varchar(20) DEFAULT NULL COMMENT 'ID号',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `paperNum` varchar(100) DEFAULT NULL COMMENT '身份证',
  `anliNum` varchar(100) DEFAULT NULL COMMENT '案号',
  `beizhixingrenlvxingStatus` varchar(100) DEFAULT NULL COMMENT '被执行人的履行情况',
  `jutiStatus` varchar(100) DEFAULT NULL COMMENT '失信被执行人行为具体情形',
  `lianTime` varchar(100) DEFAULT NULL COMMENT '立案时间',
  `province` varchar(100) DEFAULT NULL COMMENT '省份',
  `publicTime` varchar(100) DEFAULT NULL COMMENT '发布时间',
  `zhixingCourt` varchar(100) DEFAULT NULL COMMENT '执行法院',
  PRIMARY KEY (`id`),
  KEY `apply_id` (`apply_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_msp_title
-- ----------------------------
DROP TABLE IF EXISTS `ht_msp_title`;
CREATE TABLE `ht_msp_title` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `apply_id` varchar(50) DEFAULT NULL COMMENT '申请编号',
  `customerName` varchar(100) DEFAULT NULL COMMENT '姓名',
  `paperNumber` varchar(100) DEFAULT NULL COMMENT '身份证号',
  `reportTime` varchar(100) DEFAULT NULL COMMENT '报告生成时间',
  `wjqCount` varchar(100) DEFAULT NULL COMMENT '未结清',
  `jqCount` varchar(100) DEFAULT NULL COMMENT '已结清',
  `totalCount` varchar(100) DEFAULT NULL COMMENT '小计',
  `ewjqCount` varchar(100) DEFAULT NULL COMMENT '未结清',
  `ejqCount` varchar(100) DEFAULT NULL COMMENT '已结清',
  `etotalCount` varchar(100) DEFAULT NULL COMMENT '小计',
  `applyingCount` varchar(100) DEFAULT NULL COMMENT '申请中笔数',
  `applyPassedCount` varchar(100) DEFAULT NULL COMMENT '通过笔数',
  `applyRejectCount` varchar(100) DEFAULT NULL COMMENT '拒绝笔数',
  `applyTotalCount` varchar(100) DEFAULT NULL COMMENT '小计',
  `queryCount` varchar(100) DEFAULT NULL COMMENT '申请/债权/逾期/补录/行业不良记录',
  PRIMARY KEY (`id`),
  KEY `apply_id` (`apply_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_msp_zhixinginfos
-- ----------------------------
DROP TABLE IF EXISTS `ht_msp_zhixinginfos`;
CREATE TABLE `ht_msp_zhixinginfos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `apply_id` varchar(50) DEFAULT NULL COMMENT '申请编号',
  `zguxubg_id` varchar(20) DEFAULT NULL COMMENT 'ID号',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `paperNum` varchar(100) DEFAULT NULL COMMENT '身份证',
  `zhixingCourt` varchar(100) DEFAULT NULL COMMENT '执行法院',
  `anliNum` varchar(100) DEFAULT NULL COMMENT '案例号',
  `anjianState` varchar(100) DEFAULT NULL COMMENT '案件状态',
  `zhixingTaget` varchar(100) DEFAULT NULL COMMENT '执行标的',
  `lianTime` varchar(100) DEFAULT NULL COMMENT '立案时间',
  PRIMARY KEY (`id`),
  KEY `apply_id` (`apply_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_pre_regist
-- ----------------------------
DROP TABLE IF EXISTS `ht_pre_regist`;
CREATE TABLE `ht_pre_regist` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `mobile` varchar(11) NOT NULL COMMENT '手机号',
  `referrer` varchar(10) DEFAULT NULL COMMENT '推荐人',
  `utm_id` int(10) DEFAULT NULL COMMENT '关键词ID',
  `source_id` int(10) DEFAULT NULL COMMENT '渠道ID',
  `pre_regist_time` int(10) NOT NULL COMMENT '预注册时间',
  `regist_flag` tinyint(2) DEFAULT NULL COMMENT '是否已注册 0:否,1:是',
  `regist_time` int(10) DEFAULT NULL COMMENT '注册时间',
  `platform_id` varchar(20) DEFAULT NULL COMMENT '平台ID',
  `platform_name` varchar(20) DEFAULT NULL COMMENT '平台名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_user` int(11) DEFAULT NULL COMMENT '创建者',
  `update_user` int(11) DEFAULT NULL COMMENT '更新者',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_mobile` (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='预注册用户';

-- ----------------------------
-- Table structure for ht_r_oa_department
-- ----------------------------
DROP TABLE IF EXISTS `ht_r_oa_department`;
CREATE TABLE `ht_r_oa_department` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parentid` int(10) NOT NULL COMMENT '父类部门id',
  `name` varchar(50) NOT NULL COMMENT '部门名',
  `description` varchar(200) NOT NULL COMMENT '部门描述',
  `ishead` tinyint(4) NOT NULL COMMENT '是否为总部(1,0)',
  `ishr` tinyint(4) NOT NULL COMMENT '是否人力资源部(1,0)',
  `isfinance` tinyint(4) NOT NULL COMMENT '是否财务部(1,0)',
  `cuttype` tinyint(4) NOT NULL COMMENT '提成发放方式（1线上2线下）',
  `provinceid` varchar(20) NOT NULL COMMENT '所在省份',
  `cityid` varchar(20) NOT NULL COMMENT '所在城市',
  `header` int(10) DEFAULT '0' COMMENT '负责人：员工ID',
  `manager` int(10) DEFAULT '0' COMMENT '督导：用户ID',
  `position_category` int(4) NOT NULL COMMENT '岗位类别',
  `listorder` tinyint(3) unsigned NOT NULL COMMENT '排序',
  `sort` int(10) DEFAULT NULL COMMENT '排序',
  `flag` int(1) DEFAULT NULL COMMENT '删除标记 0：已删除  1：未删除',
  `parent_name` varchar(50) NOT NULL DEFAULT '' COMMENT '父类部门名',
  `grdfather_id` int(10) NOT NULL DEFAULT '0' COMMENT '祖父级部门id',
  `grdfather_name` varchar(50) NOT NULL DEFAULT '' COMMENT '祖父级部门名',
  PRIMARY KEY (`id`),
  KEY `index_name` (`parentid`),
  KEY `idx_heaer` (`header`),
  KEY `idx_manager` (`manager`)
) ENGINE=InnoDB AUTO_INCREMENT=544 DEFAULT CHARSET=utf8 COMMENT='本表存放部门信息';

-- ----------------------------
-- Table structure for ht_r_oa_users
-- ----------------------------
DROP TABLE IF EXISTS `ht_r_oa_users`;
CREATE TABLE `ht_r_oa_users` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_login` varchar(60) NOT NULL DEFAULT '' COMMENT '用户名',
  `user_pass` varchar(64) NOT NULL DEFAULT '' COMMENT '登录密码；oa_password加密',
  `user_realname` varchar(50) NOT NULL DEFAULT '' COMMENT '姓名',
  `user_email` varchar(100) NOT NULL DEFAULT '' COMMENT '登录邮箱',
  `idcard` varchar(20) NOT NULL DEFAULT '' COMMENT '身份证号',
  `avatar` varchar(255) DEFAULT NULL COMMENT '用户头像，相对于upload/avatar目录',
  `sex` smallint(1) DEFAULT '0' COMMENT '性别；0：保密，1：男；2：女',
  `acc_province` varchar(120) NOT NULL COMMENT '户口省份',
  `acc_city` varchar(20) NOT NULL COMMENT '户口城市',
  `acc_address` varchar(120) NOT NULL COMMENT '户口所在地址',
  `departmentid` int(8) NOT NULL COMMENT '部门',
  `positionid` int(8) NOT NULL COMMENT '岗位名称',
  `level` smallint(1) NOT NULL COMMENT '角色等级: 2-员工/1-leader',
  `temporary` int(2) NOT NULL DEFAULT '2' COMMENT '1,兼职/2,正式员工',
  `rework` int(1) DEFAULT NULL COMMENT '是否转正 0：试用期 1：转正',
  `rework_time` varchar(20) DEFAULT NULL COMMENT '入职时间或角色（正式/兼职）修改时间',
  `ispart` char(1) NOT NULL COMMENT 'Y/N',
  `payroll_try` int(8) DEFAULT NULL COMMENT '试用期月薪',
  `payroll` int(8) DEFAULT NULL COMMENT '转正月薪',
  `entrydate` date NOT NULL COMMENT '入职时间',
  `reference` varchar(20) NOT NULL COMMENT '入职推荐人',
  `education` tinyint(2) NOT NULL COMMENT '学历',
  `school` varchar(30) NOT NULL COMMENT '毕业院校',
  `specialty` varchar(30) NOT NULL COMMENT '专业',
  `mobile` char(11) NOT NULL COMMENT '手机',
  `last_login_ip` varchar(16) NOT NULL COMMENT '最后登录ip',
  `last_login_time` char(11) NOT NULL COMMENT '最后登录时间',
  `create_time` char(11) NOT NULL COMMENT '注册时间',
  `bank_address` varchar(80) NOT NULL COMMENT '开户行地址',
  `bank_user` varchar(30) DEFAULT NULL COMMENT '开户人姓名',
  `bank_num` varchar(30) DEFAULT NULL COMMENT '银行卡账号',
  `user_status` char(3) NOT NULL DEFAULT 'E' COMMENT '用户状态 E1:一级未审核;E2二级未审核 ；E已入职；E11一级审核未通过；E21二级审核未通过;E3取消入职；Q已离职；Q1离职一级未审核；Q2离职一级审核未通过；Q2离职二级未审核；Q22离职二级审核未通过；Q3取消离职',
  `age` int(3) NOT NULL COMMENT '年龄',
  `hyd_name` varchar(20) NOT NULL COMMENT '平台账号',
  `hyd_id` int(8) NOT NULL COMMENT '//汇盈贷账号ID',
  `user_type` smallint(1) DEFAULT '2' COMMENT '用户类型，1:admin ;2:会员',
  `entry_success_time` int(11) DEFAULT NULL COMMENT '入职终审通过时间',
  `leave_success_time` int(11) DEFAULT NULL COMMENT '离职终审通过时间',
  `safety_high` tinyint(1) NOT NULL DEFAULT '0' COMMENT '安全性是否高？',
  PRIMARY KEY (`id`),
  KEY `idx_user_login_` (`user_login`),
  KEY `idx_user_nicename` (`user_realname`),
  KEY `idx_hyd_id` (`hyd_id`),
  KEY `idx_departmentid` (`departmentid`),
  KEY `idx_hyd_name` (`hyd_name`),
  KEY `idx_user_status` (`user_status`),
  KEY `idx_idcard` (`idcard`)
) ENGINE=InnoDB AUTO_INCREMENT=11437 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Table structure for ht_screen_config
-- ----------------------------
DROP TABLE IF EXISTS `ht_screen_config`;
CREATE TABLE `ht_screen_config` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `task_time` varchar(7) DEFAULT NULL COMMENT '任务时间,精确到月  yyyy-mm',
  `new_passenger_goal` decimal(16,2) DEFAULT NULL COMMENT '新客组月目标（元）',
  `old_passenger_goal` decimal(16,2) DEFAULT NULL COMMENT '老客组月目标（元）',
  `operational_goal` decimal(16,2) DEFAULT NULL COMMENT '运营部月目标（元）',
  `status` tinyint(1) DEFAULT '0' COMMENT '是否有效 1:有效,2:无效',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标识 0：未删除，1：已删除',
  `create_user_id` int(11) DEFAULT NULL COMMENT '创建用户ID',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='大屏运营部目标表';

-- ----------------------------
-- Table structure for ht_sms_count
-- ----------------------------
DROP TABLE IF EXISTS `ht_sms_count`;
CREATE TABLE `ht_sms_count` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `department_id` int(11) DEFAULT '0' COMMENT '分公司ID',
  `department_name` varchar(40) DEFAULT NULL COMMENT '分公司名字',
  `sms_number` int(11) DEFAULT NULL COMMENT '短信发送数量',
  `posttime` date DEFAULT NULL COMMENT '提交日期',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=868 DEFAULT CHARSET=utf8 COMMENT='分公司短信统计';

-- ----------------------------
-- Table structure for ht_smscode
-- ----------------------------
DROP TABLE IF EXISTS `ht_smscode`;
CREATE TABLE `ht_smscode` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL COMMENT '用户id',
  `mobile` char(11) DEFAULT '' COMMENT '手机号码',
  `checkfor` varchar(32) NOT NULL COMMENT '加密验证码',
  `checkcode` varchar(6) NOT NULL COMMENT '验证码',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '不详',
  `posttime` int(11) unsigned NOT NULL COMMENT '发送时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_mobile` (`mobile`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=3109 DEFAULT CHARSET=utf8 COMMENT='短信验证码表';

-- ----------------------------
-- Table structure for ht_spreads_user
-- ----------------------------
DROP TABLE IF EXISTS `ht_spreads_user`;
CREATE TABLE `ht_spreads_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned DEFAULT '0',
  `spreads_user_id` int(11) unsigned DEFAULT '0',
  `set` varchar(1000) DEFAULT NULL COMMENT '设置',
  `type` varchar(100) DEFAULT NULL,
  `opernote` varchar(50) NOT NULL COMMENT '操作说明',
  `operation` varchar(20) NOT NULL COMMENT '操作人 ',
  `create_ip` varchar(15) NOT NULL DEFAULT '',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_spreads_user_id` (`spreads_user_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=394 DEFAULT CHARSET=utf8 COMMENT='用户推荐人表';

-- ----------------------------
-- Table structure for ht_spreads_user_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_spreads_user_log`;
CREATE TABLE `ht_spreads_user_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned DEFAULT '0',
  `old_spreads_user_id` int(11) unsigned DEFAULT '0',
  `spreads_user_id` int(11) unsigned DEFAULT '0',
  `type` varchar(100) DEFAULT NULL,
  `opernote` varchar(50) NOT NULL COMMENT '操作说明',
  `operation` varchar(20) NOT NULL COMMENT '操作人 ',
  `create_ip` varchar(15) NOT NULL DEFAULT '',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='用户推荐人日志表';

-- ----------------------------
-- Table structure for ht_user
-- ----------------------------
DROP TABLE IF EXISTS `ht_user`;
CREATE TABLE `ht_user` (
  `user_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(30) DEFAULT '' COMMENT '用户名',
  `mobile` char(11) DEFAULT '' COMMENT '手机号',
  `bank_mobile` char(11) DEFAULT '' COMMENT '银行预留手机号',
  `email` varchar(32) DEFAULT '' COMMENT '邮箱',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `salt` char(6) NOT NULL COMMENT '加密验证字符',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '用户是否锁定,0未锁定,1锁定',
  `open_account` tinyint(10) NOT NULL DEFAULT '0' COMMENT '是否开户,0未开户,1已开户',
  `recharge_sms` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '充值成功短信 0发送 1不发送',
  `withdraw_sms` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '提现成功短信 0发送 1不发送',
  `icon_url` varchar(255) DEFAULT NULL COMMENT 'app头像的url',
  `if_receive_notice` tinyint(1) unsigned DEFAULT '0' COMMENT '手机端是否接收推送通知 0：不接收，1：接收',
  `invest_sms` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '投资成功短信 0发送 1不发送',
  `recieve_sms` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '回收成功短信 0发送 1不发送',
  `reg_esb` tinyint(1) unsigned DEFAULT NULL COMMENT '账户开通平台 0pc 1微信 2安卓 3IOS 4其他',
  `eprovince` varchar(60) DEFAULT '0',
  `send_sms` tinyint(1) unsigned DEFAULT '0',
  `is_inst_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '是否是第三方推送用户(0:否,1:是)',
  `inst_code` varchar(30) DEFAULT NULL COMMENT '第三方机构编号',
  `account_esb` tinyint(1) DEFAULT NULL COMMENT '账户开通平台 0pc 1微信 2安卓 3IOS 4其他',
  `user_type` tinyint(1) unsigned DEFAULT '0' COMMENT '用户类型 0普通用户 1企业用户',
  `payment_auth_status` tinyint(1) unsigned DEFAULT '0' COMMENT '缴费授权状态  0：未授权1：已授权',
  `is_set_password` tinyint(1) unsigned DEFAULT '0' COMMENT '是否设置了交易密码 0未设置 1已设置',
  `bank_open_account` tinyint(1) unsigned DEFAULT '0' COMMENT '是否银行开户,0未开户,1已开户',
  `bank_account_esb` tinyint(1) unsigned DEFAULT NULL COMMENT '银行开户平台',
  `is_evaluation_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '是否已进行风险测评(0:未测评,1:已测评)',
  `evaluation_expired_time` datetime DEFAULT '1900-01-01 00:00:00' COMMENT '测评到期时间',
  `is_ca_flag` tinyint(1) unsigned DEFAULT '0' COMMENT 'CA认证标识位(0:未认证,1:已认证)',
  `is_smtp` tinyint(1) unsigned DEFAULT '0' COMMENT '是否发送投资协议邮件 0发送 1不发送',
  `reg_ip` varchar(15) NOT NULL COMMENT '注册ip',
  `reg_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `points_total` int(11) DEFAULT '0' COMMENT '用户总获取积分',
  `points_current` int(11) DEFAULT '0' COMMENT '用户当前积分',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`),
  KEY `IDX_REG_TIME` (`reg_time`),
  KEY `idx_mobile` (`mobile`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7311 DEFAULT CHARSET=utf8 COMMENT='平台用户表';

-- ----------------------------
-- Table structure for ht_user_alias
-- ----------------------------
DROP TABLE IF EXISTS `ht_user_alias`;
CREATE TABLE `ht_user_alias` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `sign` varchar(50) DEFAULT NULL,
  `alias` varchar(200) DEFAULT NULL,
  `client` varchar(1) DEFAULT NULL COMMENT '所属平台（2 安卓 3 ios）',
  `package_code` varchar(50) DEFAULT NULL COMMENT '属于哪个平台（用于推送时选用不同极光账号）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=414 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_user_bind_email_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_user_bind_email_log`;
CREATE TABLE `ht_user_bind_email_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(11) unsigned NOT NULL COMMENT '用户id',
  `user_email` varchar(45) DEFAULT NULL COMMENT '绑定的邮箱',
  `user_email_status` int(10) unsigned NOT NULL DEFAULT '1' COMMENT '账号邮箱1未验证 2已验证 3过期(已发送新的邮件)',
  `email_active_code` varchar(32) DEFAULT NULL COMMENT 'email激活码',
  `email_active_url_deadtime` timestamp NULL DEFAULT NULL COMMENT 'email激活链接过期时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送邮件时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8 COMMENT='用户绑定邮箱记录表';

-- ----------------------------
-- Table structure for ht_user_change_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_user_change_log`;
CREATE TABLE `ht_user_change_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) unsigned DEFAULT NULL COMMENT '用户id',
  `username` varchar(30) DEFAULT NULL COMMENT '用户登录名',
  `real_name` varchar(30) DEFAULT NULL COMMENT '真实姓名',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号',
  `idcard` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `role` tinyint(1) unsigned DEFAULT NULL COMMENT '用户角色1投资人2借款人',
  `attribute` tinyint(1) unsigned DEFAULT NULL COMMENT '用户属性 0=>无主单 1=>有主单 2=>线下员工 3=>线上员工',
  `recommend_user` varchar(30) DEFAULT NULL COMMENT '推荐人',
  `is51` tinyint(1) unsigned DEFAULT NULL COMMENT '是否51老用户，1：是',
  `status` tinyint(1) unsigned DEFAULT NULL COMMENT '用户是否锁定,0未锁定,1锁定',
  `remark` varchar(255) DEFAULT NULL COMMENT '修改说明',
  `borrower_type` tinyint(1) DEFAULT NULL COMMENT '借款人类型 1：内部机构 2：外部机构',
  `update_type` tinyint(1) unsigned DEFAULT NULL COMMENT '修改类型 2用户信息修改  1推荐人修改',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `update_user` varchar(30) DEFAULT NULL COMMENT '修改人名字',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `email` varchar(32) DEFAULT NULL COMMENT '邮箱',
  `utm_name` varchar(255) DEFAULT NULL COMMENT '注册渠道',
  `utm_type` varchar(50) DEFAULT NULL COMMENT '注册渠道数据来源（0：ht_utm_reg，1：ht_app_utm_reg）',
  `utm_source_id` varchar(50) DEFAULT NULL COMMENT '注册渠道sourceId',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=572 DEFAULT CHARSET=utf8 COMMENT='用户信息修改日志表';

-- ----------------------------
-- Table structure for ht_user_contact
-- ----------------------------
DROP TABLE IF EXISTS `ht_user_contact`;
CREATE TABLE `ht_user_contact` (
  `user_id` int(11) unsigned NOT NULL,
  `relation` int(11) NOT NULL,
  `rl_name` varchar(20) NOT NULL DEFAULT '''',
  `rl_phone` varchar(11) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户联系日志表';

-- ----------------------------
-- Table structure for ht_user_evalation
-- ----------------------------
DROP TABLE IF EXISTS `ht_user_evalation`;
CREATE TABLE `ht_user_evalation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `er_id` int(11) NOT NULL COMMENT '用户测评总结id',
  `question_id` int(11) NOT NULL COMMENT '问题id',
  `answer_id` int(11) DEFAULT NULL COMMENT '答案id',
  `sort` tinyint(3) unsigned DEFAULT NULL COMMENT '排序',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_er_id` (`er_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1171296 DEFAULT CHARSET=utf8 COMMENT='用户测评问卷结果表';

-- ----------------------------
-- Table structure for ht_user_evalation_behavior
-- ----------------------------
DROP TABLE IF EXISTS `ht_user_evalation_behavior`;
CREATE TABLE `ht_user_evalation_behavior` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(10) unsigned NOT NULL COMMENT '测试用户id',
  `behavior` varchar(5000) NOT NULL COMMENT '用户行为',
  `statr_time` datetime DEFAULT NULL COMMENT '测评开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '测试结束时间',
  `remarks` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2698 DEFAULT CHARSET=utf8 COMMENT='用户测评行为表';

-- ----------------------------
-- Table structure for ht_user_evalation_result
-- ----------------------------
DROP TABLE IF EXISTS `ht_user_evalation_result`;
CREATE TABLE `ht_user_evalation_result` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) unsigned NOT NULL,
  `eval_type` varchar(20) NOT NULL COMMENT '测评类型',
  `summary` varchar(250) NOT NULL COMMENT '总结',
  `score_count` int(3) NOT NULL COMMENT '问卷总分',
  `inst_code` varchar(20) DEFAULT '10000000' COMMENT '测评机构编号',
  `inst_name` varchar(20) DEFAULT '汇盈金服' COMMENT '测评机构名称',
  `lasttime` datetime DEFAULT NULL,
  `plat_form` tinyint(1) DEFAULT NULL COMMENT '测评平台 0pc 1微信 2安卓 3IOS 4其他',
  `remarks` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=702 DEFAULT CHARSET=utf8 COMMENT='用户测评总结表';

-- ----------------------------
-- Table structure for ht_user_info
-- ----------------------------
DROP TABLE IF EXISTS `ht_user_info`;
CREATE TABLE `ht_user_info` (
  `user_id` int(11) unsigned NOT NULL COMMENT '用户id',
  `role_id` tinyint(4) unsigned DEFAULT '1' COMMENT '用户角色1投资人2借款人3担保机构',
  `birthday` varchar(20) DEFAULT NULL COMMENT '生日',
  `sex` tinyint(1) unsigned DEFAULT '0' COMMENT '性别:0未知,1男,2女',
  `truename` varchar(30) DEFAULT '' COMMENT '真实姓名',
  `idcard` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `truename_isApprove` tinyint(1) NOT NULL DEFAULT '0' COMMENT '实名是已否认证',
  `mobile_isApprove` tinyint(1) NOT NULL DEFAULT '0' COMMENT '手机是已否认证',
  `email_isApprove` tinyint(1) NOT NULL DEFAULT '0' COMMENT '邮件是已否认证',
  `province` varchar(20) DEFAULT NULL COMMENT '省份',
  `city` varchar(20) DEFAULT NULL COMMENT '城市',
  `area` varchar(20) DEFAULT NULL COMMENT '区域',
  `address` varchar(100) DEFAULT NULL COMMENT '地址',
  `attribute` int(11) NOT NULL DEFAULT '0' COMMENT '用户属性 0=>无主单 1=>有主单 2=>线下员工 3=>线上员工',
  `is_contact` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `borrower_type` tinyint(1) DEFAULT NULL COMMENT '借款人类型 1：内部机构 2：外部机构',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`user_id`),
  KEY `idx_idcard` (`idcard`) USING BTREE,
  KEY `idx_truename` (`truename`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='平台用户信息表';

-- ----------------------------
-- Table structure for ht_user_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_user_log`;
CREATE TABLE `ht_user_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT '0' COMMENT '用户ID',
  `event` varchar(20) DEFAULT NULL COMMENT '事件',
  `content` varchar(5000) DEFAULT NULL COMMENT '日志内容',
  `ip` varchar(20) DEFAULT NULL COMMENT 'IP地址',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1947 DEFAULT CHARSET=utf8 COMMENT='用户日志表';

-- ----------------------------
-- Table structure for ht_user_login_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_user_login_log`;
CREATE TABLE `ht_user_login_log` (
  `user_id` int(10) unsigned NOT NULL COMMENT '用户ID',
  `login_times` int(11) NOT NULL DEFAULT '0' COMMENT '登录次数',
  `login_ip` varchar(20) DEFAULT NULL COMMENT 'IP地址',
  `login_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后登录时间',
  `last_ip` varchar(20) DEFAULT NULL COMMENT 'IP地址',
  `last_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上一次登录时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`user_id`),
  KEY `idx_login_time` (`login_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户登录日志,所有字段从user 表中拆出来的';

-- ----------------------------
-- Table structure for ht_user_loginerror_lock
-- ----------------------------
DROP TABLE IF EXISTS `ht_user_loginerror_lock`;
CREATE TABLE `ht_user_loginerror_lock` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL COMMENT '锁定用户ID',
  `username` varchar(30) NOT NULL COMMENT '锁定用户用户名',
  `mobile` varchar(11) NOT NULL COMMENT '用户手机号',
  `front` tinyint(1) NOT NULL COMMENT '是否是前端锁定用户1：前端 0：后台',
  `unlocked` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否被解锁 1：已解锁 0：锁定',
  `lock_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '被锁定时间（最后一次登录失败时间）',
  `unlock_time` timestamp NULL DEFAULT NULL COMMENT '解锁时间(自动解锁是redis过期时间)',
  `operator` int(11) DEFAULT NULL COMMENT '解锁用户ID',
  PRIMARY KEY (`id`),
  KEY `INDEX_USERNAME` (`username`) USING BTREE,
  KEY `INDEX_MOBILE` (`mobile`) USING BTREE,
  KEY `INDEX_LOCKTIME` (`lock_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1118 DEFAULT CHARSET=utf8 COMMENT='用户登录失败锁定表';

-- ----------------------------
-- Table structure for ht_user_plat
-- ----------------------------
DROP TABLE IF EXISTS `ht_user_plat`;
CREATE TABLE `ht_user_plat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `pid` int(11) NOT NULL COMMENT '第三方平台ID',
  `usernamep` varchar(30) NOT NULL COMMENT '平台用户名',
  `ptype` tinyint(1) unsigned NOT NULL COMMENT '是否新注册 0为新 1为关联',
  `addip` varchar(20) NOT NULL COMMENT '添加IP',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_usernamep` (`usernamep`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_pid_addtime` (`pid`,`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户第三方平台关联表';

-- ----------------------------
-- Table structure for ht_user_portrait
-- ----------------------------
DROP TABLE IF EXISTS `ht_user_portrait`;
CREATE TABLE `ht_user_portrait` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL COMMENT '用户id',
  `user_name` varchar(30) DEFAULT NULL COMMENT '用户名',
  `age` int(11) unsigned DEFAULT NULL COMMENT '年龄',
  `sex` varchar(2) DEFAULT '' COMMENT '性别：男,女',
  `education` varchar(100) DEFAULT NULL COMMENT '学历',
  `occupation` varchar(100) DEFAULT NULL COMMENT '职业',
  `city` varchar(30) DEFAULT NULL COMMENT '城市',
  `interest` varchar(200) DEFAULT NULL COMMENT '爱好',
  `interest_sum` decimal(12,2) DEFAULT '0.00' COMMENT '累计收益',
  `invest_sum` decimal(12,2) DEFAULT '0.00' COMMENT '累计年化投资金额',
  `recharge_sum` decimal(12,2) DEFAULT '0.00' COMMENT '累计充值金额',
  `withdraw_sum` decimal(12,2) DEFAULT '0.00' COMMENT '累计提取金额',
  `login_active` varchar(10) DEFAULT NULL COMMENT '登陆活跃',
  `customer_source` varchar(10) DEFAULT NULL COMMENT '客户来源',
  `last_login_time` int(10) DEFAULT NULL COMMENT '最后一次登陆时间',
  `last_recharge_time` int(10) DEFAULT NULL COMMENT '最后一次充值时间',
  `last_withdraw_time` int(10) DEFAULT NULL COMMENT '最后一次提现时间',
  `invest_platform` int(11) DEFAULT '0' COMMENT '同时投资平台数',
  `invest_age` tinyint(1) unsigned DEFAULT '0' COMMENT '投龄',
  `trade_number` int(11) DEFAULT '0' COMMENT '交易笔数',
  `current_owner` varchar(20) DEFAULT NULL COMMENT '当前拥有人',
  `add_wechat` varchar(2) DEFAULT '' COMMENT '是否加微信：否,是',
  `invest_process` varchar(20) DEFAULT NULL COMMENT '投资进程',
  `customer_complaint` varchar(20) DEFAULT NULL COMMENT '客户投诉',
  `invite_customer` int(11) DEFAULT '0' COMMENT '邀约客户数',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `mobile` char(11) DEFAULT NULL COMMENT '手机号',
  `bank_total` decimal(21,2) DEFAULT '0.00' COMMENT '江西银行总资产',
  `fund_retention` decimal(12,2) DEFAULT '0.00' COMMENT '资金存留比',
  `last_repay_time` int(10) DEFAULT NULL COMMENT '最后一笔回款时间',
  `invite_regist` int(10) DEFAULT '0' COMMENT '邀约注册客户数',
  `invite_recharge` int(10) DEFAULT '0' COMMENT '邀约充值客户数',
  `invite_tender` int(10) DEFAULT '0' COMMENT '邀约投资客户数',
  `yield` decimal(12,2) DEFAULT '0.00' COMMENT '客均收益率',
  `attribute` tinyint(1) DEFAULT '0' COMMENT '是否有主单',
  `bank_balance` decimal(21,2) DEFAULT '0.00' COMMENT '江西银行可用余额',
  `account_await` decimal(21,2) DEFAULT '0.00' COMMENT '银行账户待还金额',
  `bank_frost` decimal(21,2) DEFAULT '0.00' COMMENT '银行冻结金额',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `unique_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9434 DEFAULT CHARSET=utf8 COMMENT='用户画像表';

-- ----------------------------
-- Table structure for ht_utm
-- ----------------------------
DROP TABLE IF EXISTS `ht_utm`;
CREATE TABLE `ht_utm` (
  `utm_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `utm_source` varchar(50) DEFAULT NULL COMMENT '平台',
  `source_id` int(10) DEFAULT NULL COMMENT '账户推广平台',
  `utm_medium` varchar(50) DEFAULT NULL COMMENT '媒介',
  `utm_term` varchar(50) NOT NULL COMMENT '关键词',
  `utm_content` varchar(50) DEFAULT NULL COMMENT '广告系列内容',
  `utm_campaign` varchar(50) DEFAULT NULL COMMENT '广告系列名称',
  `utm_referrer` int(11) unsigned DEFAULT NULL COMMENT '推荐人id',
  `link_address` varchar(100) NOT NULL DEFAULT 'www.hyjf.com' COMMENT '链接地址',
  `remark` varchar(200) DEFAULT NULL,
  `status` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '状态 1启用 2禁用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`utm_id`),
  UNIQUE KEY `utm_source_term` (`utm_source`,`utm_term`),
  KEY `idx_source_id` (`source_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11200024 DEFAULT CHARSET=utf8 COMMENT='推广链接';

-- ----------------------------
-- Table structure for ht_utm_plat
-- ----------------------------
DROP TABLE IF EXISTS `ht_utm_plat`;
CREATE TABLE `ht_utm_plat` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `source_id` int(10) DEFAULT NULL,
  `source_name` varchar(50) DEFAULT NULL,
  `source_type` tinyint(4) unsigned DEFAULT '0' COMMENT '0 pc渠道 1 app渠道',
  `del_flag` tinyint(1) unsigned DEFAULT NULL COMMENT '删除标识',
  `attorn_flag` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '债转标识: 1-默认可转让  0-不可转让',
  `remark` varchar(255) DEFAULT NULL,
  `create_group_cd` varchar(50) DEFAULT NULL COMMENT '创建者部门',
  `update_group_cd` int(10) DEFAULT '0' COMMENT '更新部门id',
  `create_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '创建用户id',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ID` (`source_id`) USING BTREE,
  KEY `idx_source_id` (`source_id`)
) ENGINE=InnoDB AUTO_INCREMENT=171 DEFAULT CHARSET=utf8 COMMENT='推广平台表';

-- ----------------------------
-- Table structure for ht_utm_reg
-- ----------------------------
DROP TABLE IF EXISTS `ht_utm_reg`;
CREATE TABLE `ht_utm_reg` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `utm_id` int(10) unsigned NOT NULL COMMENT '推广链接id',
  `user_id` int(10) unsigned NOT NULL COMMENT '注册用户id',
  `invest_time` int(10) unsigned DEFAULT NULL COMMENT '首次投资时间',
  `invest_amount` decimal(13,2) DEFAULT '0.00' COMMENT '首次投标的投资金额',
  `invest_project_type` varchar(10) DEFAULT NULL COMMENT '首次投资标的的项目类型',
  `invest_project_period` varchar(10) DEFAULT NULL COMMENT '首次投资标的的项目期限',
  `open_account` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '开户返回',
  `bind_card` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '绑卡返回',
  `hxyid` int(11) unsigned DEFAULT '0' COMMENT '惠享游用户id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `idx_utm_id` (`utm_id`)
) ENGINE=InnoDB AUTO_INCREMENT=297017 DEFAULT CHARSET=utf8 COMMENT='推广链接注册';

-- ----------------------------
-- Table structure for ht_utm_source
-- ----------------------------
DROP TABLE IF EXISTS `ht_utm_source`;
CREATE TABLE `ht_utm_source` (
  `source_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '渠道平台ID',
  `utm_source` varchar(50) NOT NULL COMMENT '渠道平台名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`source_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_utm_visit
-- ----------------------------
DROP TABLE IF EXISTS `ht_utm_visit`;
CREATE TABLE `ht_utm_visit` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `utm_id` int(10) unsigned NOT NULL COMMENT '推广链接id',
  `ip` varchar(15) DEFAULT NULL COMMENT '访问ip',
  `os` varchar(10) DEFAULT NULL COMMENT '操作系统',
  `browser` varchar(10) DEFAULT NULL COMMENT '浏览器',
  `country` varchar(30) DEFAULT NULL COMMENT '国家',
  `province` varchar(30) DEFAULT NULL COMMENT '省份',
  `city` varchar(30) DEFAULT NULL COMMENT '城市',
  `login` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否登录 0未登录 1登录',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `utm_id` (`utm_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='推广链接访问';

-- ----------------------------
-- Table structure for ht_vip_auth
-- ----------------------------
DROP TABLE IF EXISTS `ht_vip_auth`;
CREATE TABLE `ht_vip_auth` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vip_id` int(11) DEFAULT NULL COMMENT 'vip编号',
  `coupon_code` varchar(50) DEFAULT NULL COMMENT '优惠券编号',
  `coupon_quantity` int(3) DEFAULT NULL COMMENT '优惠券数量',
  `remark` varchar(300) DEFAULT NULL COMMENT '备注',
  `del_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标识 0：未删除，1：已删除',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='vip权限';

-- ----------------------------
-- Table structure for ht_vip_info
-- ----------------------------
DROP TABLE IF EXISTS `ht_vip_info`;
CREATE TABLE `ht_vip_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vip_name` varchar(50) DEFAULT NULL COMMENT 'vip名称',
  `vip_value` int(11) DEFAULT NULL COMMENT 'vip的v值',
  `vip_level` int(3) DEFAULT NULL COMMENT 'vip级别',
  `remark` varchar(300) DEFAULT NULL COMMENT '备注',
  `del_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标识',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='vip信息';

-- ----------------------------
-- Table structure for ht_vip_transfer_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_vip_transfer_log`;
CREATE TABLE `ht_vip_transfer_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户编号',
  `vip_id` int(11) DEFAULT NULL COMMENT 'vip编号',
  `ord_id` varchar(100) DEFAULT NULL COMMENT '订单编号',
  `transfer_in_custid` varchar(100) DEFAULT NULL COMMENT '转入账户',
  `transfer_out_custid` varchar(100) DEFAULT NULL COMMENT '转出账户',
  `trans_amt` decimal(11,0) DEFAULT NULL COMMENT '转账金额',
  `trans_type` int(1) DEFAULT NULL COMMENT '转账类别 1:转入，2：转出',
  `trans_status` int(1) DEFAULT NULL COMMENT '转账状态，0：成功，1：失败',
  `trans_time` int(10) DEFAULT NULL COMMENT '转账时间',
  `remark` varchar(300) DEFAULT NULL COMMENT '备注',
  `del_flag` int(1) DEFAULT NULL COMMENT '删除标识 0：未删除，1：已删除',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='vip转账记录';

-- ----------------------------
-- Table structure for ht_vip_user_tender
-- ----------------------------
DROP TABLE IF EXISTS `ht_vip_user_tender`;
CREATE TABLE `ht_vip_user_tender` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户编号',
  `vip_id` int(11) DEFAULT NULL COMMENT 'vip编号',
  `tender_nid` varchar(100) DEFAULT NULL COMMENT '投资订单编号',
  `tender_vip_value` int(11) DEFAULT '0' COMMENT '投资V值',
  `sum_vip_value` int(11) DEFAULT '0' COMMENT '账户V值',
  `remark` varchar(300) DEFAULT NULL COMMENT '备注',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除，1：已删除',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `userId` (`user_id`),
  KEY `tenderNid` (`tender_nid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='vip投资';

-- ----------------------------
-- Table structure for ht_vip_user_upgrade
-- ----------------------------
DROP TABLE IF EXISTS `ht_vip_user_upgrade`;
CREATE TABLE `ht_vip_user_upgrade` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户编号',
  `vip_id` int(11) DEFAULT NULL COMMENT 'vip编号',
  `upgrade_vip_value` int(11) DEFAULT NULL COMMENT 'vip升级时的V值',
  `upgrade_vip_type` int(1) DEFAULT NULL COMMENT 'vip升级的类别，1：购买，2：V值升级',
  `gift_flg` int(1) DEFAULT NULL COMMENT '礼包发放状态，0：未发放，1：已发放',
  `remark` varchar(300) DEFAULT NULL COMMENT '备注',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除，1：已删除',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `userId` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='vip成长';

-- ----------------------------
-- Table structure for ht_whereabouts_page_config
-- ----------------------------
DROP TABLE IF EXISTS `ht_whereabouts_page_config`;
CREATE TABLE `ht_whereabouts_page_config` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '页面id',
  `title` varchar(50) NOT NULL COMMENT '页面title',
  `utm_id` int(11) DEFAULT NULL COMMENT '渠道编号',
  `referrer` int(11) DEFAULT NULL COMMENT '推荐人id',
  `top_button` varchar(30) NOT NULL COMMENT '注册按钮名称',
  `jump_path` varchar(100) NOT NULL COMMENT '注册成功跳转地址',
  `bottom_button_status` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '下载按钮启用状态  0：启用   1：不启用 ',
  `bottom_button` varchar(30) NOT NULL COMMENT '按钮名称  ',
  `download_path` varchar(100) DEFAULT NULL COMMENT '下载按钮跳转路径',
  `describe` varchar(500) NOT NULL COMMENT '描述',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `style` int(11) NOT NULL DEFAULT '0' COMMENT '样式 0:通用模板 1:大转盘',
  `status_on` tinyint(1) unsigned DEFAULT '1' COMMENT '开启状态：0 未开启  1 开启',
  `del_flag` tinyint(1) unsigned NOT NULL COMMENT '删除状态  0：未删除   1：已删除 ',
  `create_user_id` varchar(11) DEFAULT NULL COMMENT '创建人',
  `update_user_id` varchar(11) DEFAULT NULL COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='着陆页配置';

-- ----------------------------
-- Table structure for ht_whereabouts_page_picture
-- ----------------------------
DROP TABLE IF EXISTS `ht_whereabouts_page_picture`;
CREATE TABLE `ht_whereabouts_page_picture` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `picture_name` varchar(50) DEFAULT NULL COMMENT '图片名称',
  `picture_type` int(3) DEFAULT NULL COMMENT '图片位置类别  1：区域1banner   2：区域2banner  3：区域3banner',
  `whereabouts_id` int(11) DEFAULT NULL COMMENT '着落页id  hyjf_whereabouts_page_config表id',
  `picture_url` varchar(200) DEFAULT NULL COMMENT '图片路径',
  `sort` int(3) DEFAULT NULL COMMENT '排序',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `del_flag` tinyint(1) unsigned NOT NULL COMMENT '删除状态  0：未删除   1：已删除 ',
  `create_user_id` varchar(11) DEFAULT NULL COMMENT '创建人',
  `update_user_id` varchar(11) DEFAULT NULL COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='着陆页配置图片';

-- ----------------------------
-- Procedure structure for test
-- ----------------------------
DROP PROCEDURE IF EXISTS `test`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `test`()
begin
    declare i int default 0;                      
    set i = 0;                          
    lp : loop                           
    INSERT INTO `ht_utm_reg` (`utm_id`,`user_id`,`invest_time`,`invest_amount`,`invest_project_type`,`invest_project_period`,`open_account`,`bind_card`,`hxyid`,`create_time`,`update_time` )
			VALUES  ( '11200018','6879','1554361856','100.00','汇计划','1个月','1','1','0',now(),now() );    
     set i = i + 1;                  
     if i > 1000000 then                  
        leave lp;
     end if; 
    end loop;
		commit;
    select count(*) from ht_utm_reg where utm_id='11200018';                 
end
;;
DELIMITER ;
