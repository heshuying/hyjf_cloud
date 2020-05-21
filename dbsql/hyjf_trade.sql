/*
Navicat MySQL Data Transfer

Source Server         : beta2_47.105.206.3
Source Server Version : 50725
Source Host           : 47.105.206.3:33306
Source Database       : hyjf_trade

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2019-07-24 14:58:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for checksums
-- ----------------------------
DROP TABLE IF EXISTS `checksums`;
CREATE TABLE `checksums` (
  `db` char(64) NOT NULL,
  `tbl` char(64) NOT NULL,
  `chunk` int(11) NOT NULL,
  `chunk_time` float DEFAULT NULL,
  `chunk_index` varchar(200) DEFAULT NULL,
  `lower_boundary` text,
  `upper_boundary` text,
  `this_crc` char(40) NOT NULL,
  `this_cnt` int(11) NOT NULL,
  `master_crc` char(40) DEFAULT NULL,
  `master_cnt` int(11) DEFAULT NULL,
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`db`,`tbl`,`chunk`),
  KEY `ts_db_tbl` (`ts`,`db`,`tbl`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_account
-- ----------------------------
DROP TABLE IF EXISTS `ht_account`;
CREATE TABLE `ht_account` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `user_name` varchar(30) NOT NULL COMMENT '用户名',
  `account_id` varchar(20) DEFAULT NULL COMMENT '用户电子账户号(开户后,维护)',
  `total` decimal(21,2) DEFAULT '0.00' COMMENT '资金总额',
  `balance` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '可用金额',
  `balance_cash` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '可提现',
  `balance_frost` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '不可提现',
  `frost` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '冻结金额',
  `await` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '待收金额',
  `repay` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '待还金额',
  `plan_repay_interest` decimal(11,2) DEFAULT '0.00' COMMENT '计划累计收益',
  `plan_balance` decimal(11,2) DEFAULT '0.00' COMMENT '计划可用余额',
  `plan_frost` decimal(11,2) DEFAULT '0.00' COMMENT '计划冻结金额',
  `plan_accede_total` decimal(11,2) DEFAULT '0.00' COMMENT '计划累计加入金额',
  `plan_accede_balance` decimal(11,2) DEFAULT '0.00' COMMENT '计划加入可用余额',
  `plan_accede_frost` decimal(11,2) DEFAULT '0.00' COMMENT '投资汇添金标的投资的未放款金额',
  `plan_account_wait` decimal(11,2) DEFAULT '0.00' COMMENT '计划累计待收总额',
  `plan_capital_wait` decimal(11,2) DEFAULT '0.00' COMMENT '计划累计待收本金',
  `plan_interest_wait` decimal(11,2) DEFAULT '0.00' COMMENT '计划待收利息',
  `bank_total` decimal(21,2) DEFAULT '0.00' COMMENT '银行总资产',
  `bank_balance` decimal(11,2) DEFAULT '0.00' COMMENT '江西银行可用余额',
  `bank_frost` decimal(11,2) DEFAULT '0.00' COMMENT '银行冻结金额',
  `bank_wait_repay` decimal(11,2) DEFAULT '0.00' COMMENT '银行待还本息',
  `bank_wait_capital` decimal(11,2) DEFAULT '0.00' COMMENT '银行待还本金',
  `bank_wait_interest` decimal(11,2) DEFAULT '0.00' COMMENT '银行待还利息',
  `bank_wait_repay_org` decimal(11,2) DEFAULT '0.00' COMMENT '待还垫付机构金额',
  `bank_interest_sum` decimal(21,2) DEFAULT '0.00' COMMENT '银行累计收益',
  `bank_invest_sum` decimal(21,2) DEFAULT '0.00' COMMENT '银行累计投资',
  `bank_await` decimal(11,2) DEFAULT '0.00' COMMENT '银行待收总额',
  `bank_await_capital` decimal(11,2) DEFAULT '0.00' COMMENT '银行待收本金',
  `bank_await_interest` decimal(11,2) DEFAULT '0.00' COMMENT '银行待收利息',
  `bank_await_org` decimal(11,2) DEFAULT '0.00' COMMENT '银行垫付机构待收垫付总额',
  `bank_balance_cash` decimal(11,2) DEFAULT '0.00' COMMENT '江西银行可提现金额(银行电子账户余额)',
  `bank_frost_cash` decimal(11,2) DEFAULT '0.00' COMMENT '江西银行冻结金额(银行电子账户冻结金额)',
  `del_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标识',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`) USING BTREE,
  KEY `idx_user_name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1937 DEFAULT CHARSET=utf8 COMMENT='账户信息管理';

-- ----------------------------
-- Table structure for ht_account_borrow
-- ----------------------------
DROP TABLE IF EXISTS `ht_account_borrow`;
CREATE TABLE `ht_account_borrow` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `nid` varchar(50) DEFAULT NULL COMMENT '订单号',
  `borrow_nid` varchar(20) DEFAULT NULL,
  `user_id` int(11) DEFAULT '0' COMMENT '用户ID',
  `status` tinyint(2) unsigned DEFAULT '0' COMMENT '状态',
  `money` decimal(10,2) DEFAULT '0.00' COMMENT '金额',
  `fee` decimal(10,2) DEFAULT '0.00' COMMENT '服务费',
  `balance` decimal(11,2) NOT NULL COMMENT '实际到账余额',
  `interest` decimal(11,2) DEFAULT '0.00' COMMENT '待还利息',
  `manage_fee` decimal(11,2) DEFAULT '0.00' COMMENT '管理费',
  `remark` varchar(250) DEFAULT '0' COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `nid` (`nid`),
  KEY `idx_borrow_nid` (`borrow_nid`)
) ENGINE=InnoDB AUTO_INCREMENT=1563 DEFAULT CHARSET=utf8 COMMENT='项目标的放款表';

-- ----------------------------
-- Table structure for ht_account_directional_transfer
-- ----------------------------
DROP TABLE IF EXISTS `ht_account_directional_transfer`;
CREATE TABLE `ht_account_directional_transfer` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `order_id` varchar(30) NOT NULL COMMENT '订单号',
  `turn_out_username` varchar(256) NOT NULL COMMENT '转出账户用户名',
  `turn_out_user_id` int(11) NOT NULL COMMENT '转出账户用户编号',
  `shift_to_username` varchar(256) NOT NULL COMMENT '转入账户用户名',
  `shift_to_user_id` int(11) NOT NULL COMMENT '转入账户用户编号',
  `transfer_accounts_money` decimal(11,2) NOT NULL COMMENT '转账金额',
  `transfer_accounts_state` tinyint(1) NOT NULL COMMENT '状态状态（0转账中 1 成功 2 失败）',
  `transfer_accounts_time` datetime NOT NULL COMMENT '转账时间',
  `remark` varchar(256) DEFAULT '' COMMENT '备注说明',
  PRIMARY KEY (`id`),
  KEY `turn_out_user_id` (`turn_out_user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定向转账记录表';

-- ----------------------------
-- Table structure for ht_account_exception
-- ----------------------------
DROP TABLE IF EXISTS `ht_account_exception`;
CREATE TABLE `ht_account_exception` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `username` char(30) DEFAULT NULL COMMENT '用户名',
  `custom_id` bigint(20) DEFAULT NULL COMMENT '客户号',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `balance_plat` decimal(11,2) DEFAULT NULL COMMENT '平台可用金额',
  `frost_plat` decimal(11,2) DEFAULT NULL COMMENT '平台冻结金额',
  `balance_huifu` decimal(11,2) DEFAULT NULL COMMENT '汇付可用金额',
  `frost_huifu` decimal(11,2) DEFAULT NULL COMMENT '汇付冻结金额',
  `create_time` int(11) DEFAULT NULL,
  `role` varchar(10) DEFAULT NULL COMMENT '账户角色',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='异常账户表（可用金额和冻结金额与汇付账户不一致）';

-- ----------------------------
-- Table structure for ht_account_list
-- ----------------------------
DROP TABLE IF EXISTS `ht_account_list`;
CREATE TABLE `ht_account_list` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `nid` varchar(100) DEFAULT NULL COMMENT '交易凭证号',
  `accede_order_id` varchar(50) DEFAULT NULL COMMENT '汇计划加入订单号',
  `is_show` tinyint(1) NOT NULL DEFAULT '0' COMMENT '该明细是否在前台展示',
  `user_id` int(11) DEFAULT '0' COMMENT '用户id',
  `amount` decimal(11,2) DEFAULT '0.00' COMMENT '操作金额',
  `type` tinyint(1) DEFAULT NULL COMMENT '收支类型1收入2支出3冻结',
  `trade` varchar(50) DEFAULT NULL COMMENT '交易类型',
  `trade_code` varchar(50) DEFAULT NULL COMMENT '操作识别码 balance余额操作 frost冻结操作 await待收操作',
  `total` decimal(21,2) DEFAULT '0.00' COMMENT '资金总额',
  `balance` decimal(11,2) DEFAULT '0.00' COMMENT '可用金额',
  `frost` decimal(11,2) DEFAULT '0.00' COMMENT '冻结金额',
  `plan_frost` decimal(11,2) DEFAULT '0.00' COMMENT '汇添金账户冻结金额',
  `await` decimal(11,2) DEFAULT '0.00' COMMENT '待收金额',
  `repay` decimal(11,2) DEFAULT '0.00' COMMENT '待还金额',
  `remark` varchar(100) DEFAULT NULL,
  `operator` varchar(20) DEFAULT NULL COMMENT '操作员',
  `ip` varchar(20) DEFAULT NULL COMMENT '操作IP',
  `web` tinyint(1) DEFAULT '0' COMMENT '网站收支计算标识',
  `plan_balance` decimal(11,2) DEFAULT '0.00' COMMENT '汇添金账户可用余额',
  `is_bank` tinyint(1) DEFAULT '0' COMMENT '是否是银行的交易记录(0:否,1:是)',
  `account_id` varchar(20) DEFAULT NULL COMMENT '电子账号',
  `tx_date` int(10) DEFAULT '0' COMMENT '银行订单日期',
  `tx_time` int(10) DEFAULT '0' COMMENT '银行订单时间',
  `seq_no` varchar(15) DEFAULT NULL COMMENT '银行交易流水号',
  `bank_seq_no` varchar(50) DEFAULT NULL COMMENT '银行交易订单号',
  `check_status` tinyint(1) unsigned DEFAULT '0' COMMENT '对账状态0：未对账 1：已对账',
  `trade_status` tinyint(1) unsigned DEFAULT '1' COMMENT '交易状态0: 失败 1：成功',
  `bank_total` decimal(21,2) DEFAULT '0.00' COMMENT '银行总资产',
  `bank_balance` decimal(16,2) DEFAULT '0.00' COMMENT '银行存管可用余额',
  `bank_frost` decimal(11,2) DEFAULT '0.00' COMMENT '银行存管冻结金额',
  `bank_wait_repay` decimal(11,2) DEFAULT '0.00' COMMENT '银行待还本息',
  `bank_wait_capital` decimal(11,2) DEFAULT '0.00' COMMENT '银行待还本金',
  `bank_wait_interest` decimal(11,2) DEFAULT '0.00' COMMENT '银行待还利息',
  `bank_interest_sum` decimal(11,2) DEFAULT '0.00' COMMENT '银行累计收益',
  `bank_invest_sum` decimal(21,2) DEFAULT '0.00' COMMENT '银行累计投资',
  `bank_await` decimal(11,2) DEFAULT '0.00' COMMENT '银行待收总额',
  `bank_await_capital` decimal(11,2) DEFAULT '0.00' COMMENT '银行待收本金',
  `bank_await_interest` decimal(11,2) DEFAULT '0.00' COMMENT '银行待收利息',
  `check_date` int(10) DEFAULT NULL COMMENT '对账时间',
  `check_balance` decimal(11,2) DEFAULT '0.00' COMMENT '到账金额',
  `account_date` int(10) DEFAULT NULL COMMENT '到账时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `nid` (`nid`),
  KEY `create_time` (`create_time`),
  KEY `idx_bank_seq_no` (`bank_seq_no`) USING BTREE,
  KEY `idx_trade` (`trade`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18527 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户资金明细';

-- ----------------------------
-- Table structure for ht_account_recharge
-- ----------------------------
DROP TABLE IF EXISTS `ht_account_recharge`;
CREATE TABLE `ht_account_recharge` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `nid` varchar(50) DEFAULT NULL COMMENT '订单号',
  `user_id` int(11) DEFAULT '0' COMMENT '用户ID',
  `username` varchar(30) DEFAULT NULL,
  `account_id` varchar(50) DEFAULT NULL COMMENT '电子账号',
  `tx_date` int(10) DEFAULT NULL COMMENT '交易日期',
  `tx_time` int(10) DEFAULT NULL COMMENT '交易时间',
  `seq_no` int(50) DEFAULT NULL COMMENT '交易流水号',
  `bank_seq_no` varchar(50) DEFAULT NULL COMMENT '交易日期+交易时间+交易流水号',
  `is_bank` tinyint(1) unsigned DEFAULT '0' COMMENT '资金托管平台(0:汇付,1:江西银行)',
  `status` tinyint(2) unsigned DEFAULT '0' COMMENT '充值状态:0:初始,1:充值中,2:充值成功,3:充值失败,4:终止.',
  `money` decimal(21,2) DEFAULT '0.00' COMMENT '金额',
  `fee` decimal(11,2) NOT NULL COMMENT '费用',
  `balance` decimal(16,2) DEFAULT NULL COMMENT '实际到账余额',
  `payment` varchar(100) DEFAULT NULL COMMENT '所属银行',
  `gate_type` varchar(10) DEFAULT NULL COMMENT '网关类型：QP快捷支付;B2C网关充值;B2B企业充值 ',
  `type` tinyint(1) DEFAULT '0' COMMENT '类型.1网上充值.0线下充值',
  `remark` varchar(250) DEFAULT '0' COMMENT '备注',
  `operator` varchar(20) DEFAULT NULL,
  `add_ip` varchar(20) DEFAULT NULL COMMENT 'create ip',
  `client` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0pc 1WX 2AND 3IOS 4other',
  `cardid` varchar(100) DEFAULT NULL COMMENT '银行卡号，导出数据关联时用到',
  `message` varchar(256) DEFAULT '' COMMENT '消息记录',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `nid` (`nid`),
  KEY `idx_bank_seq_no` (`bank_seq_no`),
  KEY `idx_create_time` (`create_time`),
  KEY `idx_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2840 DEFAULT CHARSET=utf8 COMMENT='充值记录';

-- ----------------------------
-- Table structure for ht_account_trade
-- ----------------------------
DROP TABLE IF EXISTS `ht_account_trade`;
CREATE TABLE `ht_account_trade` (
  `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `type_id` int(11) NOT NULL COMMENT '交易类型 1收入 2支出  3冻结 4解冻',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `nid` varchar(50) NOT NULL COMMENT '标识名',
  `value` varchar(200) NOT NULL COMMENT '值',
  `status` tinyint(2) unsigned DEFAULT '0' COMMENT '状态',
  `order` smallint(6) DEFAULT '0' COMMENT '排序',
  `operation` varchar(20) DEFAULT '' COMMENT '账户余额操作(ADD:余额增加,SUB:余额减少,UNCHANGED:余额不变)',
  PRIMARY KEY (`id`),
  KEY `type_id` (`type_id`),
  KEY `IDX_VALUE` (`value`)
) ENGINE=InnoDB AUTO_INCREMENT=784 DEFAULT CHARSET=utf8 COMMENT='用户交易类型';

-- ----------------------------
-- Table structure for ht_account_withdraw
-- ----------------------------
DROP TABLE IF EXISTS `ht_account_withdraw`;
CREATE TABLE `ht_account_withdraw` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT '0' COMMENT '用户ID',
  `nid` varchar(50) DEFAULT NULL COMMENT '交易凭证号',
  `status` tinyint(2) unsigned DEFAULT '0' COMMENT '提现状态:0:初始,1:提现中,2:提现成功,3:提现失败，4：银行失败',
  `account` varchar(100) DEFAULT '0' COMMENT '账号',
  `bank` varchar(302) DEFAULT NULL COMMENT '所属银行',
  `bank_id` int(30) DEFAULT NULL,
  `total` decimal(11,2) DEFAULT '0.00' COMMENT '总额',
  `credited` decimal(11,2) DEFAULT '0.00' COMMENT '到账总额',
  `fee` varchar(20) DEFAULT '0' COMMENT '手续费',
  `add_ip` varchar(20) DEFAULT NULL COMMENT 'create ip',
  `remark` varchar(100) DEFAULT NULL,
  `client` tinyint(1) unsigned DEFAULT '0' COMMENT '0pc 1WX 2AND 3IOS 4other',
  `reason` varchar(500) DEFAULT NULL COMMENT '提现原因',
  `bank_flag` tinyint(1) DEFAULT '0' COMMENT '银行存管提现标志位 1为银行存管 0非银行存管',
  `account_id` varchar(30) DEFAULT NULL COMMENT '银行电子账号',
  `tx_date` int(10) DEFAULT NULL COMMENT '交易日期',
  `tx_time` int(10) DEFAULT NULL COMMENT '交易时间',
  `seq_no` int(10) DEFAULT NULL COMMENT '交易流水号',
  `bank_seq_no` varchar(50) DEFAULT NULL COMMENT '交易日期+交易时间+交易流水号',
  `withdraw_type` tinyint(1) unsigned DEFAULT '0' COMMENT '提现类型 0主动提现  1代提现',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `nid` (`nid`),
  KEY `idx_bank_seq_no` (`bank_seq_no`),
  KEY `idx_addtime` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1958 DEFAULT CHARSET=utf8 COMMENT='用户提现';

-- ----------------------------
-- Table structure for ht_aleve_error_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_aleve_error_log`;
CREATE TABLE `ht_aleve_error_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fileline` int(11) DEFAULT NULL COMMENT '文件长度',
  `saveline` int(11) DEFAULT NULL COMMENT '读取长度',
  `filestring` varchar(255) DEFAULT NULL COMMENT '文件内容',
  `filestats` varchar(55) DEFAULT '' COMMENT '文件类型，aleve,eve',
  `del_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标识',
  `create_user_id` int(11) DEFAULT NULL COMMENT '创建用户',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_saveline` (`saveline`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='存款系统-红包账户-交易明细流水文件-错误日志';

-- ----------------------------
-- Table structure for ht_aleve_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_aleve_log`;
CREATE TABLE `ht_aleve_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '银行号',
  `bank` int(11) NOT NULL COMMENT '银行号',
  `cardnbr` varchar(20) NOT NULL COMMENT '电子账号',
  `amount` decimal(16,2) DEFAULT NULL COMMENT '交易金额',
  `cur_num` int(11) DEFAULT '156' COMMENT '货币代码(156)',
  `crflag` varchar(55) DEFAULT NULL COMMENT '交易金额符号 --小于零等于C；大于零等于D',
  `valdate` varchar(55) DEFAULT NULL COMMENT '入帐日期 --YYYYMMDD 账务日期',
  `inpdate` varchar(55) DEFAULT NULL COMMENT '交易日期 --YYYYMMDD 卡系统日期',
  `reldate` varchar(55) DEFAULT NULL COMMENT '自然日期 --YYYYMMDD 服务器日期',
  `inptime` int(11) DEFAULT NULL COMMENT '交易时间--HH24MISSTT',
  `tranno` varchar(11) DEFAULT NULL COMMENT '交易流水号',
  `ori_tranno` int(11) DEFAULT NULL COMMENT '关联交易流水号',
  `transtype` int(8) DEFAULT NULL COMMENT '交易类型',
  `desline` varchar(255) DEFAULT NULL COMMENT '交易描述',
  `curr_bal` decimal(16,2) DEFAULT NULL COMMENT '交易后余额',
  `forcardnbr` varchar(20) DEFAULT NULL COMMENT '对手交易帐号',
  `revind` int(11) DEFAULT NULL COMMENT '冲正、撤销标志 --1-已撤销/冲正空或0-正常交易',
  `accchg` varchar(255) DEFAULT NULL COMMENT '交易标识 --1-调账该字段为1时，标识该笔流水为行内调账交易',
  `seqno` int(11) DEFAULT NULL COMMENT '系统跟踪号',
  `ori_num` int(11) DEFAULT NULL COMMENT '原交易流水号',
  `resv` varchar(255) DEFAULT NULL COMMENT '保留域',
  `create_day` varchar(55) DEFAULT NULL COMMENT '日期，记录导入数据所属日期',
  `create_user_id` int(11) DEFAULT NULL COMMENT '创建用户',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标识',
  `upd_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否处理 0未处理 1 已处理',
  PRIMARY KEY (`id`),
  KEY `idx_cardnbr` (`cardnbr`) USING BTREE,
  KEY `idx_transtype` (`transtype`) USING BTREE,
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1386796 DEFAULT CHARSET=utf8 COMMENT='存管系统-手续费-交易明细全流水文件';

-- ----------------------------
-- Table structure for ht_app_push_manage
-- ----------------------------
DROP TABLE IF EXISTS `ht_app_push_manage`;
CREATE TABLE `ht_app_push_manage` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(120) NOT NULL COMMENT '标题名称',
  `jump_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '跳转类型:0:原生;1:H5;',
  `jump_content` tinyint(1) NOT NULL DEFAULT '0' COMMENT '跳转内容:原生,0;H5 URL,1;H5自定义:2;',
  `jump_url` varchar(120) DEFAULT NULL COMMENT '跳转URL',
  `order_id` mediumint(6) unsigned NOT NULL DEFAULT '0' COMMENT '排序',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '0:禁用;1:启用.',
  `time_start` date NOT NULL COMMENT '开始时间',
  `time_end` date NOT NULL COMMENT '结束时间',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `thumb` varchar(120) DEFAULT NULL COMMENT '缩略图',
  `content` varchar(10000) DEFAULT NULL COMMENT '内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='App推送管理';

-- ----------------------------
-- Table structure for ht_apply_agreement
-- ----------------------------
DROP TABLE IF EXISTS `ht_apply_agreement`;
CREATE TABLE `ht_apply_agreement` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '计划id',
  `borrow_nid` varchar(50) DEFAULT NULL COMMENT '借款编号',
  `repay_period` int(11) DEFAULT NULL COMMENT '期数',
  `apply_user_id` int(11) NOT NULL COMMENT '申请人id',
  `apply_user_name` varchar(50) NOT NULL COMMENT '申请人',
  `agreement_number` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '协议份数',
  `status` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '投资状态 0 全部；1申请中：2申请成功',
  `del_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标识',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COMMENT='垫付协议申请';

-- ----------------------------
-- Table structure for ht_apply_agreement_info
-- ----------------------------
DROP TABLE IF EXISTS `ht_apply_agreement_info`;
CREATE TABLE `ht_apply_agreement_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '计划id',
  `borrow_nid` varchar(50) DEFAULT NULL COMMENT '借款编号',
  `repay_period` int(11) DEFAULT NULL COMMENT '期数',
  `contract_id` varchar(50) NOT NULL COMMENT '合同编号',
  `user_id` int(11) NOT NULL COMMENT '投资人',
  `credit_user_id` varchar(20) NOT NULL DEFAULT '0' COMMENT '出让人用户ID',
  `status` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '交易类型,5:垫付债转投资,6:汇计划垫付债转投资 ',
  `del_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标识',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=utf8 COMMENT='垫付协议申请-协议生成详情';

-- ----------------------------
-- Table structure for ht_apply_borrow_agreement
-- ----------------------------
DROP TABLE IF EXISTS `ht_apply_borrow_agreement`;
CREATE TABLE `ht_apply_borrow_agreement` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `borrow_nid` varchar(50) DEFAULT NULL COMMENT '借款编号',
  `borrow_project_source` varchar(50) DEFAULT NULL COMMENT '资产来源',
  `borrow_period` varchar(50) DEFAULT NULL COMMENT '标的期限(带单位,个月)',
  `apply_user_id` int(11) NOT NULL COMMENT '申请人id',
  `apply_user_name` varchar(50) NOT NULL COMMENT '申请人',
  `order_number` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '订单份数(应下载协议数量=出借订单数+承接订单数)',
  `agreement_number` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '协议份数(出借订单数+承接订单数,下载成功状态的协议份数)',
  `status` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '申请状态 0 初始；1成功',
  `del_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标识',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COMMENT='根据标的号申请下载协议';

-- ----------------------------
-- Table structure for ht_bank_credit_end
-- ----------------------------
DROP TABLE IF EXISTS `ht_bank_credit_end`;
CREATE TABLE `ht_bank_credit_end` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `batch_no` char(6) DEFAULT NULL COMMENT '批次号（批次号当日必须唯一）',
  `tx_date` char(8) DEFAULT NULL COMMENT '本批次交易日期',
  `tx_time` char(6) DEFAULT NULL COMMENT '本批次交易时间',
  `seq_no` varchar(10) DEFAULT NULL COMMENT '本批次交易流水号',
  `tx_counts` int(11) unsigned DEFAULT '0' COMMENT '本批次所有交易笔数',
  `user_id` int(11) unsigned NOT NULL COMMENT '融资人用户ID',
  `username` varchar(30) DEFAULT NULL COMMENT '融资人用户名',
  `tender_user_id` int(11) unsigned NOT NULL COMMENT '投资人用户ID',
  `tender_username` varchar(30) DEFAULT NULL COMMENT '投资人用户名',
  `account_id` varchar(20) NOT NULL COMMENT '融资人电子账号',
  `tender_account_id` varchar(20) NOT NULL COMMENT '投资人电子账号',
  `order_id` varchar(50) NOT NULL COMMENT '订单号',
  `org_order_id` varchar(100) NOT NULL COMMENT '原始投资订单号',
  `borrow_nid` varchar(50) NOT NULL COMMENT '标的号',
  `auth_code` varchar(30) NOT NULL,
  `credit_end_type` tinyint(1) DEFAULT NULL COMMENT '结束债权类型（1:还款，2:散标债转，3:计划债转，5：后台发起）',
  `received` tinyint(1) DEFAULT NULL COMMENT '银行接受结果（0：fail接收失败，1：success接收成功）',
  `check_retcode` varchar(50) DEFAULT NULL COMMENT '异步检查响应代码',
  `check_retmsg` varchar(100) DEFAULT NULL COMMENT '异步检查响应描述',
  `retcode` varchar(50) DEFAULT NULL COMMENT '响应代码',
  `retmsg` varchar(100) DEFAULT NULL COMMENT '响应描述',
  `suc_counts` int(11) unsigned DEFAULT NULL COMMENT '成功笔数',
  `fail_counts` int(11) unsigned DEFAULT NULL COMMENT '失败笔数',
  `state` varchar(1) DEFAULT NULL COMMENT '银行交易状态（S-成功;F-失败;A-待处理;D-正在处理;C-撤销;）',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0初始 1待请求 2请求成功 3请求失败 4校验成功 5业务全部成功  10校验失败 11业务部分成功 12业务失败 ',
  `failmsg` varchar(100) DEFAULT NULL COMMENT '失败描述',
  `fail_times` int(11) DEFAULT '0' COMMENT '失败次数',
  `create_user` int(11) NOT NULL COMMENT '添加人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user` int(11) DEFAULT NULL COMMENT '更新人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_bank_credit_end` (`order_id`) USING BTREE,
  KEY `idx_status` (`status`),
  KEY `idx_batch_no` (`batch_no`),
  KEY `idx_tender_user_id` (`tender_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=964 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_bank_merchant_account
-- ----------------------------
DROP TABLE IF EXISTS `ht_bank_merchant_account`;
CREATE TABLE `ht_bank_merchant_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_name` varchar(20) NOT NULL COMMENT '子账户名称',
  `account_type` varchar(10) NOT NULL COMMENT '账户类型',
  `account_code` varchar(20) NOT NULL COMMENT '电子账号',
  `account_balance` decimal(20,2) DEFAULT '0.00' COMMENT '账户余额',
  `available_balance` decimal(20,2) DEFAULT '0.00' COMMENT '可用余额',
  `frost` decimal(20,2) DEFAULT '0.00' COMMENT '冻结金额',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_set_password` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '平台子账户是否设置交易密码 0未设置  1已设置',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='江西银行商户子账户配置表';

-- ----------------------------
-- Table structure for ht_bank_merchant_account_info
-- ----------------------------
DROP TABLE IF EXISTS `ht_bank_merchant_account_info`;
CREATE TABLE `ht_bank_merchant_account_info` (
  `id` int(11) NOT NULL,
  `account_code` varchar(20) DEFAULT NULL COMMENT '平台子账户电子账户',
  `account_name` varchar(50) DEFAULT NULL COMMENT '银行开户名称',
  `mobile` varchar(11) DEFAULT NULL COMMENT '银行开户手机号',
  `id_no` varchar(30) DEFAULT NULL COMMENT '证件号',
  `id_type` varchar(3) DEFAULT NULL COMMENT '证件类型',
  `bank_card` varchar(20) DEFAULT NULL COMMENT '绑定银行卡号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_bank_merchant_account_list
-- ----------------------------
DROP TABLE IF EXISTS `ht_bank_merchant_account_list`;
CREATE TABLE `ht_bank_merchant_account_list` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `order_id` varchar(100) NOT NULL COMMENT '订单号',
  `borrow_nid` varchar(20) DEFAULT NULL,
  `region_name` varchar(30) DEFAULT NULL COMMENT '分公司',
  `branch_name` varchar(30) DEFAULT NULL COMMENT '分部',
  `department_name` varchar(30) DEFAULT NULL COMMENT '团队',
  `user_id` int(11) DEFAULT NULL COMMENT '对方用户ID',
  `account_id` varchar(20) NOT NULL COMMENT '对方电子账号',
  `amount` decimal(11,2) NOT NULL COMMENT '操作金额',
  `bank_account_type` varchar(30) DEFAULT NULL COMMENT '账户类型',
  `bank_account_code` varchar(20) NOT NULL COMMENT '商户子账户电子账号',
  `bank_account_balance` decimal(11,2) DEFAULT NULL COMMENT '银行账户可用金额',
  `bank_account_frost` decimal(11,2) DEFAULT NULL COMMENT '银行账户冻结金额',
  `trans_type` tinyint(1) DEFAULT NULL COMMENT '交易类型',
  `type` tinyint(1) DEFAULT NULL COMMENT '收支类型1收入2支出',
  `status` tinyint(1) DEFAULT NULL COMMENT '交易状态0: 失败 1：成功 2:处理中',
  `seq_no` varchar(15) DEFAULT NULL COMMENT '流水号',
  `tx_date` int(10) DEFAULT NULL COMMENT '交易日期',
  `tx_time` int(10) DEFAULT NULL COMMENT '交易时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` int(11) NOT NULL COMMENT '创建用户id',
  `create_user_name` varchar(20) NOT NULL COMMENT '创建用户名',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户名',
  `update_user_name` varchar(20) DEFAULT NULL COMMENT '更新用户名',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注字段',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=456 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_bank_repay_freeze_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_bank_repay_freeze_log`;
CREATE TABLE `ht_bank_repay_freeze_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL COMMENT '用户userId',
  `account` varchar(30) NOT NULL COMMENT '客户号',
  `order_id` varchar(30) NOT NULL COMMENT '客户号',
  `amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '冻结金额',
  `user_name` varchar(30) NOT NULL COMMENT '客户号',
  `borrow_nid` varchar(50) NOT NULL COMMENT '客户号',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '是否有效 0有效 1无效记录',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` int(11) NOT NULL COMMENT '创建用户userId',
  `create_user_name` varchar(20) NOT NULL COMMENT '创建用户名',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户userid',
  `update_user_name` varchar(20) DEFAULT NULL COMMENT '更新用户名',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`) USING BTREE,
  KEY `idx_borrow_nid` (`borrow_nid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=603 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_bank_repay_org_freeze_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_bank_repay_org_freeze_log`;
CREATE TABLE `ht_bank_repay_org_freeze_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `repay_user_id` int(11) NOT NULL COMMENT '还款人用户userId',
  `repay_user_name` varchar(30) NOT NULL COMMENT '还款人用户名',
  `borrow_user_id` int(11) NOT NULL COMMENT '借款人userId',
  `borrow_user_name` varchar(30) NOT NULL COMMENT '借款人用户名',
  `account` varchar(30) NOT NULL COMMENT '电子账号',
  `order_id` varchar(30) NOT NULL COMMENT '订单号:txDate+txTime+seqNo',
  `borrow_nid` varchar(100) NOT NULL COMMENT '借款编号',
  `plan_nid` varchar(100) DEFAULT '0' COMMENT '计划编号',
  `inst_code` varchar(100) NOT NULL COMMENT '资产来源',
  `amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '借款金额',
  `repay_account` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '应还本息',
  `repay_fee` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '还款服务费',
  `amount_freeze` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '冻结金额',
  `lower_interest` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '减息金额',
  `penalty_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '违约金',
  `default_interest` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '逾期罚息',
  `borrow_period` varchar(20) NOT NULL COMMENT '借款期限',
  `total_period` int(11) NOT NULL COMMENT '总期数',
  `current_period` int(11) NOT NULL COMMENT '当前期数',
  `all_repay_flag` tinyint(1) DEFAULT '0' COMMENT '是否全部还款 0否 1是',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '是否有效 0有效 1无效记录',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` int(11) NOT NULL COMMENT '创建用户userId',
  `create_user_name` varchar(20) NOT NULL COMMENT '创建用户名',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `update_user_id` int(11) DEFAULT '0' COMMENT '更新用户userId',
  `update_user_name` varchar(20) DEFAULT '' COMMENT '更新用户名',
  `late_period` int(11) DEFAULT '0' COMMENT '逾期还款期数(单笔或者多笔的最后一期)',
  PRIMARY KEY (`id`),
  KEY `idx_borrow_nid` (`borrow_nid`),
  KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=utf8 COMMENT='垫付机构银行还款冻结表';

-- ----------------------------
-- Table structure for ht_borrow
-- ----------------------------
DROP TABLE IF EXISTS `ht_borrow`;
CREATE TABLE `ht_borrow` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `borrow_nid` varchar(50) DEFAULT NULL COMMENT '借款的识别名',
  `project_type` tinyint(1) NOT NULL COMMENT '0汇保贷 1汇典贷 2汇小贷 3汇车贷 4新手标',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `borrow_user_name` varchar(100) NOT NULL COMMENT '借款用户名称',
  `status` tinyint(2) DEFAULT NULL COMMENT '状态(0:备案中,1:初审中,2:投资中,3:复审中(满标),4:还款中,5:已还款,6:流标,7:受托,8:逾期中)',
  `account` decimal(11,2) DEFAULT '0.00' COMMENT '借贷总金额',
  `borrow_valid_time` int(20) DEFAULT NULL COMMENT '借款有效时间',
  `borrow_style` varchar(100) DEFAULT '0' COMMENT '还款方式',
  `borrow_period` int(10) DEFAULT '0' COMMENT '借款期限',
  `borrow_apr` decimal(10,2) DEFAULT '0.00' COMMENT '借款利率',
  `label_id` int(10) NOT NULL DEFAULT '0' COMMENT '标签ID',
  `is_show` tinyint(1) DEFAULT '0' COMMENT '是否展示(隐藏测试标用:0:显示,1:不显示)',
  `is_engine_used` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否调用标的分配规则引擎 0:否,1:是',
  `is_installment` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否是分期标 0:否,1:是',
  `is_month` tinyint(1) NOT NULL COMMENT '天标/月标 0:天标,1:月标',
  `regist_status` tinyint(1) DEFAULT '0' COMMENT '银行备案状态',
  `regist_time` datetime(3) DEFAULT NULL COMMENT '备案时间',
  `regist_user_id` int(11) DEFAULT NULL COMMENT '备案用户userId',
  `regist_user_name` varchar(30) DEFAULT NULL COMMENT '备案用户名',
  `verify_status` tinyint(1) DEFAULT '0' COMMENT '0未交保证金 1已交保证金 2暂不发布 3定时发标 4立即发标',
  `verify_over_time` int(11) DEFAULT '0' COMMENT '初审通过时间',
  `verify_time` int(11) DEFAULT '0' COMMENT '初审时间（发标时间）',
  `verify_userid` varchar(11) DEFAULT '0' COMMENT '审核人',
  `verify_user_name` varchar(30) DEFAULT NULL COMMENT '初审用户名（admin后台用户）',
  `verify_remark` varchar(255) DEFAULT '',
  `verify_contents` varchar(250) NOT NULL COMMENT '审核备注',
  `borrow_status` tinyint(2) unsigned DEFAULT '0' COMMENT '是否可以进行借款',
  `ontime` int(11) DEFAULT '0' COMMENT '定时发标',
  `borrow_end_time` varchar(100) NOT NULL COMMENT '借款到期时间',
  `plan_nid` varchar(30) DEFAULT NULL COMMENT '计划nid',
  `borrow_full_status` tinyint(2) unsigned DEFAULT '0' COMMENT '满标审核状态',
  `borrow_full_time` int(11) DEFAULT NULL COMMENT '满标时间',
  `tender_times` int(11) DEFAULT '0' COMMENT '投标的次数',
  `borrow_account_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已借到的金额',
  `borrow_account_wait` decimal(11,2) NOT NULL DEFAULT '0.00',
  `borrow_account_scale` decimal(11,2) DEFAULT '0.00' COMMENT '借贷的完成率',
  `borrow_service` varchar(100) DEFAULT NULL COMMENT '账户管理费',
  `reverify_status` tinyint(1) DEFAULT '0' COMMENT '0初始 1放款请求中 2放款请求成功 3放款校验成功 4放款校验失败 5放款失败 6放款成功 7放款请求失败 8放款部分成功',
  `reverify_time` int(11) DEFAULT '0' COMMENT '复审时间',
  `reverify_userid` varchar(11) DEFAULT '0' COMMENT '审核人',
  `reverify_user_name` varchar(30) DEFAULT NULL COMMENT '复审用户名（admin后台用户）',
  `reverify_remark` varchar(255) DEFAULT '',
  `reverify_contents` varchar(250) NOT NULL COMMENT '审核复审标注',
  `recover_last_time` int(11) DEFAULT NULL COMMENT '最后一笔的放款完成时间',
  `repay_last_time` int(11) DEFAULT NULL COMMENT '最后还款时间',
  `repay_next_time` int(11) DEFAULT '0' COMMENT '下一笔还款时间',
  `repay_status` tinyint(1) DEFAULT '0' COMMENT '0初始 1还款请求中 2还款请求成功 3还款校验成功 4还款校验失败 5还款失败 6还款成功 7还款请求失败 8还款部分成功',
  `repay_full_status` tinyint(2) unsigned DEFAULT '0' COMMENT '是否已经还完',
  `repay_fee_normal` decimal(11,2) NOT NULL COMMENT '正常还款费用',
  `repay_account_all` decimal(11,2) DEFAULT '0.00' COMMENT '应还款总额',
  `repay_account_interest` decimal(11,2) DEFAULT '0.00' COMMENT '总还款利息',
  `repay_account_capital` decimal(11,2) DEFAULT '0.00' COMMENT '总还款本金',
  `repay_account_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还款总额',
  `repay_account_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还款利息',
  `repay_account_capital_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还款本金',
  `repay_account_wait` decimal(11,2) DEFAULT '0.00' COMMENT '未还款总额',
  `repay_account_interest_wait` decimal(11,2) DEFAULT '0.00' COMMENT '未还款利息',
  `repay_account_capital_wait` decimal(11,2) DEFAULT '0.00' COMMENT '未还款本金',
  `borrow_manager` varchar(100) DEFAULT NULL COMMENT '融资服务费',
  `service_fee_rate` varchar(13) NOT NULL DEFAULT '0.0002' COMMENT '服务费费率',
  `manage_fee_rate` varchar(13) DEFAULT '0.0001' COMMENT '管理费费率',
  `differential_rate` varchar(13) DEFAULT '0.0000' COMMENT '收益差率',
  `late_interest_rate` varchar(13) DEFAULT NULL COMMENT '逾期利率(汇计划用)',
  `late_free_days` tinyint(2) unsigned DEFAULT '0' COMMENT '逾期免息天数(汇计划用)',
  `create_user_name` varchar(30) NOT NULL DEFAULT '' COMMENT '创建用户-添加标的人员（账户操作人）',
  `add_ip` varchar(20) DEFAULT NULL COMMENT 'create ip',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `increase_interest_flag` tinyint(1) DEFAULT '0' COMMENT '产品加息标志位(0:不加息,1:加息)',
  `repay_capital_type` tinyint(1) DEFAULT '0' COMMENT '等额本息保证金的回滚方式 0：到期回滚 1：分期回滚 2：不回滚',
  `invest_level` varchar(10) DEFAULT '稳健型' COMMENT '标的投资等级',
  `regist_bankmsg` varchar(50) DEFAULT '' COMMENT '备案/撤销备案银行回值描述信息',
  PRIMARY KEY (`id`),
  UNIQUE KEY `borrow_nid` (`borrow_nid`) USING BTREE,
  KEY `status` (`status`),
  KEY `borrow_apr` (`borrow_apr`)
) ENGINE=InnoDB AUTO_INCREMENT=2176 DEFAULT CHARSET=utf8 COMMENT='标的表(标的状态金额时间等)';

-- ----------------------------
-- Table structure for ht_borrow_apicron
-- ----------------------------
DROP TABLE IF EXISTS `ht_borrow_apicron`;
CREATE TABLE `ht_borrow_apicron` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nid` varchar(50) DEFAULT NULL COMMENT '标的编号_用户ID_期数',
  `user_id` int(11) DEFAULT NULL,
  `borrow_nid` varchar(20) DEFAULT NULL,
  `data` text COMMENT '错误信息',
  `status` tinyint(1) DEFAULT '0' COMMENT '放款状态0未完成1已完成2放款执行中9放款失败',
  `web_status` tinyint(1) DEFAULT '0' COMMENT '计算状态',
  `api_type` tinyint(1) DEFAULT '0' COMMENT '0放款1还款',
  `repay_status` tinyint(2) unsigned DEFAULT '0' COMMENT '还款状态0未完成1已完成',
  `period_now` int(11) NOT NULL DEFAULT '1' COMMENT '汇租赁当前期数',
  `credit_repay_status` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '债转还款状态',
  `extra_yield_status` tinyint(1) DEFAULT '0' COMMENT '融通宝加息利息放款状态0未完成1已完成2放款执行中9放款失败',
  `extra_yield_repay_status` tinyint(1) DEFAULT '0' COMMENT '融通宝加息利息还款状态0未完成1已完成',
  `is_repay_org_flag` tinyint(1) DEFAULT '0' COMMENT '是否是担保机构还款(0:否,1:是)',
  `user_name` varchar(30) DEFAULT NULL COMMENT '用户名',
  `borrow_account` decimal(11,2) DEFAULT '0.00' COMMENT '借款金额',
  `borrow_period` int(11) DEFAULT '1' COMMENT '总期数',
  `batch_no` varchar(10) DEFAULT NULL COMMENT '还款批次号',
  `batch_amount` decimal(11,2) DEFAULT '0.00' COMMENT '应放款本金，或者是还款金额，共用',
  `batch_counts` int(11) DEFAULT '0' COMMENT '批次放款总数或者还款总记录数',
  `batch_service_fee` decimal(11,2) DEFAULT '0.00' COMMENT '放款总服务费或还款总服务费',
  `tx_amount` decimal(11,2) DEFAULT '0.00' COMMENT '应放款金额',
  `tx_counts` int(10) DEFAULT '0' COMMENT '交易笔数',
  `fail_times` tinyint(2) unsigned DEFAULT '0' COMMENT '失败次数',
  `suc_counts` int(10) DEFAULT '0' COMMENT '成功笔数',
  `suc_amount` decimal(10,2) DEFAULT '0.00' COMMENT '成功交易金额',
  `fail_counts` int(10) DEFAULT '0' COMMENT '失败笔数',
  `fail_amount` decimal(10,2) DEFAULT '0.00' COMMENT '失败交易金额',
  `service_fee` decimal(11,2) DEFAULT '0.00' COMMENT '应收服务费',
  `tx_date` int(10) DEFAULT NULL COMMENT '交易日期',
  `tx_time` int(10) DEFAULT NULL COMMENT '交易时间',
  `seq_no` int(10) DEFAULT NULL COMMENT '交易流水号',
  `bank_seq_no` varchar(50) DEFAULT NULL COMMENT '交易日期+交易时间+交易流水号',
  `plan_nid` varchar(30) DEFAULT NULL COMMENT '计划编号',
  `ordid` varchar(50) DEFAULT NULL COMMENT '放款订单号',
  `is_allrepay` tinyint(1) DEFAULT '0' COMMENT '批次任务是否属于一次性还款 0：否 1：是',
  `agreement_status` tinyint(1) DEFAULT '0' COMMENT '是否生成合同',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `submitter` varchar(50) DEFAULT '' COMMENT '还款提交人',
  `is_late` tinyint(1) DEFAULT '0' COMMENT '批次任务是否逾期还款 0：否 1：是',
  `last_period` int(11) DEFAULT NULL COMMENT '多期还款提交的最后一期(带逾期当期还款的当期和多期逾期还款的最后一期,其余默认为0)',
  PRIMARY KEY (`id`),
  KEY `borrow_nid` (`borrow_nid`),
  KEY `idx_nid` (`nid`),
  KEY `idx_bank_seq_no` (`bank_seq_no`),
  KEY `idx_api_type` (`api_type`)
) ENGINE=InnoDB AUTO_INCREMENT=7199 DEFAULT CHARSET=utf8 COMMENT='借款API执行任务';

-- ----------------------------
-- Table structure for ht_borrow_apicron_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_borrow_apicron_log`;
CREATE TABLE `ht_borrow_apicron_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nid` varchar(50) NOT NULL COMMENT '标的编号_用户ID_期数',
  `user_id` int(11) NOT NULL COMMENT '还款人用户ID',
  `borrow_nid` varchar(20) NOT NULL COMMENT '标的编号',
  `data` text COMMENT '错误信息',
  `status` tinyint(1) DEFAULT '0' COMMENT '还款状态:0初始4校验失败5还款失败6还款成功8还款部分成功 其余状态关联获取',
  `period_now` int(11) NOT NULL DEFAULT '1' COMMENT '当前期数',
  `is_repay_org_flag` tinyint(1) DEFAULT '0' COMMENT '是否是担保机构还款(0:否,1:是)',
  `user_name` varchar(30) DEFAULT NULL COMMENT '还款人用户名',
  `borrow_account` decimal(11,2) DEFAULT '0.00' COMMENT '借款金额',
  `borrow_period` int(11) DEFAULT '1' COMMENT '总期数',
  `batch_no` varchar(10) DEFAULT NULL COMMENT '还款批次号',
  `batch_amount` decimal(11,2) DEFAULT '0.00' COMMENT '应还款金额',
  `batch_counts` int(11) DEFAULT '0' COMMENT '批次还款总记录数',
  `batch_service_fee` decimal(11,2) DEFAULT '0.00' COMMENT '还款总服务费',
  `tx_amount` decimal(11,2) DEFAULT '0.00' COMMENT '应还款金额',
  `tx_counts` int(10) DEFAULT '0' COMMENT '交易笔数',
  `suc_counts` int(10) DEFAULT '0' COMMENT '成功笔数',
  `suc_amount` decimal(10,2) DEFAULT '0.00' COMMENT '成功交易金额',
  `fail_counts` int(10) DEFAULT '0' COMMENT '失败笔数',
  `fail_amount` decimal(10,2) DEFAULT '0.00' COMMENT '失败交易金额',
  `service_fee` decimal(11,2) DEFAULT '0.00' COMMENT '应收服务费',
  `tx_date` int(10) DEFAULT NULL COMMENT '交易日期',
  `tx_time` int(10) DEFAULT NULL COMMENT '交易时间',
  `seq_no` int(10) DEFAULT NULL COMMENT '交易流水号',
  `bank_seq_no` varchar(50) DEFAULT NULL COMMENT '银行交易流水号(交易日期+交易时间+交易流水号)',
  `plan_nid` varchar(30) DEFAULT NULL COMMENT '计划编号',
  `ordid` varchar(50) DEFAULT NULL COMMENT '放款订单号',
  `is_allrepay` tinyint(1) DEFAULT '0' COMMENT '批次任务是否属于一次性还款 0：否 1：是',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_borrow_nid` (`borrow_nid`),
  KEY `idx_bank_seq_no` (`bank_seq_no`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8 COMMENT='还款API执行任务记录表';

-- ----------------------------
-- Table structure for ht_borrow_bail
-- ----------------------------
DROP TABLE IF EXISTS `ht_borrow_bail`;
CREATE TABLE `ht_borrow_bail` (
  `id` int(15) unsigned NOT NULL AUTO_INCREMENT,
  `borrow_nid` varchar(50) DEFAULT NULL,
  `borrow_uid` int(11) DEFAULT '0',
  `operater_uid` int(11) DEFAULT '0',
  `bail_num` decimal(15,2) DEFAULT '0.00',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_borrow_nid` (`borrow_nid`)
) ENGINE=InnoDB AUTO_INCREMENT=1166 DEFAULT CHARSET=utf8 COMMENT='标的保证金表';

-- ----------------------------
-- Table structure for ht_borrow_carinfo
-- ----------------------------
DROP TABLE IF EXISTS `ht_borrow_carinfo`;
CREATE TABLE `ht_borrow_carinfo` (
  `id` int(15) unsigned NOT NULL AUTO_INCREMENT,
  `brand` varchar(40) DEFAULT NULL COMMENT '品牌',
  `model` varchar(50) DEFAULT NULL COMMENT '型号',
  `carseries` varchar(50) DEFAULT NULL COMMENT '车系',
  `number` varchar(12) DEFAULT NULL COMMENT '车牌号',
  `color` varchar(16) DEFAULT NULL COMMENT '颜色',
  `year` varchar(12) DEFAULT NULL COMMENT '出厂年份',
  `place` varchar(60) DEFAULT NULL COMMENT '产地',
  `volume` varchar(16) DEFAULT NULL COMMENT '排量',
  `buytime` int(15) DEFAULT NULL COMMENT '购买日期',
  `is_safe` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '1有保险2无保险',
  `price` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '购买价',
  `toprice` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '评估价',
  `borrow_nid` varchar(50) DEFAULT NULL,
  `borrow_pre_nid` varchar(12) NOT NULL DEFAULT '0' COMMENT '标的预编号',
  `registration` varchar(15) DEFAULT NULL COMMENT '车辆登记地',
  `vin` varchar(30) DEFAULT NULL COMMENT '车架号',
  PRIMARY KEY (`id`),
  KEY `idx_borrow_nid` (`borrow_nid`)
) ENGINE=InnoDB AUTO_INCREMENT=256 DEFAULT CHARSET=utf8 COMMENT='标的车辆抵押信息表';

-- ----------------------------
-- Table structure for ht_borrow_company_authen
-- ----------------------------
DROP TABLE IF EXISTS `ht_borrow_company_authen`;
CREATE TABLE `ht_borrow_company_authen` (
  `id` int(15) unsigned NOT NULL AUTO_INCREMENT,
  `authen_name` varchar(255) DEFAULT NULL COMMENT '认证项目名称',
  `authen_time` varchar(20) DEFAULT NULL COMMENT '认证时间',
  `authen_sort_key` int(2) DEFAULT NULL COMMENT '展示顺序',
  `borrow_nid` varchar(50) DEFAULT NULL,
  `borrow_pre_nid` varchar(12) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_borrow_nid` (`borrow_nid`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='标的企业授权表';

-- ----------------------------
-- Table structure for ht_borrow_config
-- ----------------------------
DROP TABLE IF EXISTS `ht_borrow_config`;
CREATE TABLE `ht_borrow_config` (
  `config_cd` varchar(255) NOT NULL,
  `config_name` varchar(255) DEFAULT NULL,
  `config_value` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `del_flag` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '删除状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`config_cd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_borrow_consume
-- ----------------------------
DROP TABLE IF EXISTS `ht_borrow_consume`;
CREATE TABLE `ht_borrow_consume` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `consume_class` varchar(20) DEFAULT NULL,
  `name` varchar(60) DEFAULT NULL,
  `project_type` tinyint(1) unsigned DEFAULT NULL,
  `user_name` varchar(20) DEFAULT NULL COMMENT '借款人用户名',
  `borrow_contents` text COMMENT '项目描述',
  `account_contents` longtext COMMENT '财务信息',
  `files` text COMMENT '借款冻结金额',
  `borrow_measures_instit` text COMMENT '合作机构',
  `borrow_company_instruction` text COMMENT '机构介绍',
  `borrow_measures_mea` text COMMENT '风控措施',
  `company_or_personal` tinyint(1) unsigned DEFAULT NULL COMMENT '借款人信息 借款类型 1：公司 2：个人',
  `username` varchar(50) DEFAULT NULL COMMENT '用户id',
  `province` varchar(20) DEFAULT NULL,
  `city` varchar(20) DEFAULT NULL,
  `area` varchar(20) DEFAULT NULL,
  `com_reg_time` varchar(30) DEFAULT NULL COMMENT '企业注册时间',
  `reg_captial` int(11) DEFAULT NULL COMMENT '注册资本',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_borrow_credit
-- ----------------------------
DROP TABLE IF EXISTS `ht_borrow_credit`;
CREATE TABLE `ht_borrow_credit` (
  `credit_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '债转id',
  `credit_nid` int(11) NOT NULL COMMENT '债转编号',
  `credit_user_id` int(11) NOT NULL COMMENT '出让人用户ID',
  `credit_user_name` varchar(30) NOT NULL COMMENT '出让人用户名',
  `bid_nid` varchar(30) NOT NULL COMMENT '原标标的编号',
  `borrow_user_id` int(11) DEFAULT NULL COMMENT '标的借款人用户ID',
  `borrow_user_name` varchar(30) DEFAULT NULL COMMENT '标的借款人用户名',
  `bid_apr` decimal(11,2) NOT NULL COMMENT '原标年化利率',
  `bid_name` varchar(255) NOT NULL COMMENT '原标标题',
  `tender_nid` varchar(30) NOT NULL COMMENT '原始投资订单号(对应borrow_tender表的nid字段)',
  `credit_status` tinyint(1) DEFAULT '0' COMMENT '转让状态，0.承接进行中，1.承接停止，2完全承接',
  `credit_order` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `credit_period` int(11) NOT NULL DEFAULT '1' COMMENT '债转期数',
  `credit_term` int(11) NOT NULL COMMENT '债转期限',
  `credit_term_hold` int(11) DEFAULT '0' COMMENT '债券已经持有天数',
  `credit_capital` decimal(11,2) unsigned NOT NULL COMMENT '债转本金',
  `credit_account` decimal(11,2) unsigned NOT NULL COMMENT '债转总额',
  `credit_interest` decimal(11,2) unsigned NOT NULL COMMENT '债转总利息',
  `credit_interest_advance` decimal(11,2) unsigned NOT NULL COMMENT '需垫付利息',
  `credit_discount` decimal(11,1) unsigned NOT NULL DEFAULT '0.0' COMMENT '折价率',
  `credit_income` decimal(11,2) unsigned NOT NULL COMMENT '总收入，本金+垫付利息',
  `credit_fee` decimal(11,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '服务费',
  `credit_price` decimal(11,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '出让价格',
  `credit_capital_assigned` decimal(11,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '已认购本金',
  `credit_interest_assigned` decimal(11,2) unsigned DEFAULT '0.00' COMMENT '已承接利息',
  `credit_interest_advance_assigned` decimal(11,2) DEFAULT '0.00' COMMENT '已承接垫付利息',
  `credit_repay_account` decimal(11,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '已还款总额',
  `credit_repay_capital` decimal(11,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '已还本金',
  `credit_repay_interest` decimal(11,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '已还利息',
  `credit_repay_end_time` int(11) NOT NULL COMMENT '债转最后还款日',
  `credit_repay_last_time` int(11) NOT NULL DEFAULT '0' COMMENT '上次还款日',
  `credit_repay_next_time` int(11) NOT NULL DEFAULT '0' COMMENT '下次还款日',
  `credit_repay_yes_time` int(11) NOT NULL DEFAULT '0' COMMENT '最终实际还款日',
  `end_time` int(11) NOT NULL DEFAULT '0' COMMENT '结束时间',
  `assign_time` int(11) NOT NULL DEFAULT '0' COMMENT '认购时间',
  `assign_num` int(11) NOT NULL DEFAULT '0' COMMENT '投资次数',
  `recover_period` int(11) NOT NULL DEFAULT '0',
  `client` tinyint(2) DEFAULT '0' COMMENT '客户端,0pc,1微信,2android,3ios,4其他',
  `repay_status` tinyint(1) DEFAULT '0' COMMENT '债转还款状态 0 未还款 1还款中 2还款完成 3还款失败',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`credit_id`),
  UNIQUE KEY `credit_nid` (`credit_nid`),
  KEY `bid_nid` (`bid_nid`),
  KEY `IDX_CUI_CD` (`credit_user_id`,`create_time`),
  KEY `IDX_END_TIME` (`end_time`),
  KEY `tender_nid` (`tender_nid`)
) ENGINE=InnoDB AUTO_INCREMENT=90133 DEFAULT CHARSET=utf8 COMMENT='汇转让标的表';

-- ----------------------------
-- Table structure for ht_borrow_delete
-- ----------------------------
DROP TABLE IF EXISTS `ht_borrow_delete`;
CREATE TABLE `ht_borrow_delete` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `inst_code` varchar(20) DEFAULT NULL COMMENT '机构编号',
  `borrow_nid` varchar(20) DEFAULT NULL COMMENT '借款编号',
  `account` decimal(11,2) DEFAULT NULL COMMENT '借款金额',
  `exception_type` tinyint(1) DEFAULT '0' COMMENT '异常类型 0:流标 1：删标',
  `exception_remark` varchar(20) DEFAULT NULL COMMENT '异常原因',
  `status` tinyint(1) DEFAULT NULL COMMENT '项目状态',
  `exception_time` varchar(20) DEFAULT NULL COMMENT '异常时间',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='异常标的删除';

-- ----------------------------
-- Table structure for ht_borrow_finhxfman_charge
-- ----------------------------
DROP TABLE IF EXISTS `ht_borrow_finhxfman_charge`;
CREATE TABLE `ht_borrow_finhxfman_charge` (
  `man_charge_cd` varchar(50) NOT NULL,
  `man_charge_type` varchar(10) DEFAULT NULL,
  `man_charge_per` varchar(10) DEFAULT NULL,
  `man_charge_per_end` varchar(10) DEFAULT NULL,
  `charge_time` int(6) DEFAULT NULL,
  `charge_time_type` varchar(10) DEFAULT NULL,
  `status` tinyint(1) unsigned DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `create_time` int(10) DEFAULT NULL,
  `update_time` int(10) DEFAULT NULL,
  PRIMARY KEY (`man_charge_cd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='汇消费管理费配置表';

-- ----------------------------
-- Table structure for ht_borrow_finman_new_charge
-- ----------------------------
DROP TABLE IF EXISTS `ht_borrow_finman_new_charge`;
CREATE TABLE `ht_borrow_finman_new_charge` (
  `man_charge_cd` varchar(50) NOT NULL COMMENT '主键id',
  `man_charge_type` varchar(10) DEFAULT NULL COMMENT '管理费类型',
  `man_charge_time` mediumint(6) DEFAULT NULL COMMENT '管理费时间',
  `man_charge_time_type` varchar(255) DEFAULT NULL COMMENT '管理费时间类型',
  `project_type` varchar(255) DEFAULT NULL COMMENT '项目类型',
  `inst_code` varchar(20) DEFAULT NULL COMMENT '机构编号',
  `asset_type` tinyint(2) DEFAULT NULL COMMENT '资产类型',
  `auto_borrow_apr` varchar(6) DEFAULT NULL COMMENT '自动发标利率',
  `charge_mode` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '服务费收取方式:1-按比例 2-按金额',
  `charge_rate` varchar(13) DEFAULT NULL COMMENT '服务费率',
  `man_charge_rate` varchar(13) DEFAULT NULL COMMENT '管理费率',
  `return_rate` varchar(13) DEFAULT NULL COMMENT '收益差率',
  `late_interest` varchar(13) DEFAULT NULL COMMENT '逾期利率',
  `service_fee_total` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '服务费总额',
  `late_free_days` tinyint(3) unsigned DEFAULT '0' COMMENT '逾期免息天数',
  `auto_repay` tinyint(2) unsigned NOT NULL DEFAULT '2' COMMENT '是否自动还款:1-是；2-否',
  `repayer_type` tinyint(2) unsigned DEFAULT NULL COMMENT '扣款账户：1-担保账户；2-借款人账户',
  `status` tinyint(1) unsigned DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`man_charge_cd`),
  KEY `idx_project_type` (`project_type`),
  KEY `idx_inst_code` (`inst_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='新账户管理费配置表';

-- ----------------------------
-- Table structure for ht_borrow_finser_charge
-- ----------------------------
DROP TABLE IF EXISTS `ht_borrow_finser_charge`;
CREATE TABLE `ht_borrow_finser_charge` (
  `charge_cd` varchar(50) NOT NULL COMMENT '主键id',
  `charge_type` varchar(10) DEFAULT NULL,
  `charge_time` int(6) DEFAULT NULL,
  `charge_time_type` varchar(255) DEFAULT NULL,
  `charge_rate` varchar(15) DEFAULT NULL COMMENT '融资服务费率',
  `status` tinyint(1) unsigned DEFAULT NULL COMMENT '状态:0:启用,1:禁用',
  `remark` varchar(255) DEFAULT NULL,
  `project_type` tinyint(2) DEFAULT NULL COMMENT '投资类型:borrow_cd',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`charge_cd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标的服务费配置表';

-- ----------------------------
-- Table structure for ht_borrow_houses
-- ----------------------------
DROP TABLE IF EXISTS `ht_borrow_houses`;
CREATE TABLE `ht_borrow_houses` (
  `id` int(15) unsigned NOT NULL AUTO_INCREMENT,
  `houses_type` varchar(2) DEFAULT NULL COMMENT '房产类型',
  `houses_location` varchar(255) DEFAULT NULL COMMENT '房产位置',
  `houses_area` varchar(255) DEFAULT NULL COMMENT '建筑面积',
  `houses_price` varchar(20) DEFAULT NULL COMMENT '市值',
  `houses_toprice` varchar(20) DEFAULT NULL COMMENT '抵押价值（元）',
  `borrow_nid` varchar(50) DEFAULT NULL,
  `borrow_pre_nid` varchar(12) DEFAULT '0',
  `houses_belong` varchar(20) DEFAULT NULL COMMENT '资产所属',
  `houses_cnt` int(5) DEFAULT NULL COMMENT '资产数量',
  PRIMARY KEY (`id`),
  KEY `idx_borrow_nid` (`borrow_nid`)
) ENGINE=InnoDB AUTO_INCREMENT=242 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='标的房屋抵押信息';

-- ----------------------------
-- Table structure for ht_borrow_info
-- ----------------------------
DROP TABLE IF EXISTS `ht_borrow_info`;
CREATE TABLE `ht_borrow_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `borrow_nid` varchar(50) DEFAULT NULL COMMENT '借款的识别名',
  `borrow_pre_nid` varchar(12) NOT NULL DEFAULT '0',
  `borrow_pre_nid_new` varchar(12) DEFAULT NULL COMMENT '新的标的编号排序',
  `name` varchar(255) DEFAULT NULL COMMENT '标题',
  `user_id` int(11) DEFAULT '0' COMMENT '借款用户ID',
  `borrow_user_name` varchar(100) DEFAULT NULL COMMENT '借款用户名称',
  `applicant` varchar(20) DEFAULT NULL COMMENT '项目申请人',
  `repay_org_name` varchar(30) DEFAULT NULL COMMENT '担保机构用户名',
  `is_repay_org_flag` tinyint(1) DEFAULT '0' COMMENT '是否可用担保机构还款(0:否,1:是)',
  `repay_org_user_id` int(11) DEFAULT '0' COMMENT '担保机构用户ID',
  `type` varchar(50) NOT NULL DEFAULT '1' COMMENT '1房贷2车贷',
  `account_contents` longtext NOT NULL COMMENT '财务信息',
  `borrow_use` varchar(100) DEFAULT '0' COMMENT '用途',
  `borrow_contents` text COMMENT '借款的详情',
  `borrow_valid_time` int(20) DEFAULT '0' COMMENT '借款有效时间',
  `inst_code` varchar(20) DEFAULT NULL COMMENT '机构编号',
  `asset_type` tinyint(2) DEFAULT NULL COMMENT '资产类型',
  `asset_type_name` varchar(20) DEFAULT NULL COMMENT '资产类型名称',
  `entrusted_flg` tinyint(1) NOT NULL DEFAULT '0' COMMENT '受托支付标志 0 否；1是',
  `entrusted_user_name` varchar(20) DEFAULT NULL COMMENT '受托支付用户名',
  `entrusted_user_id` int(11) DEFAULT NULL COMMENT '受托支付用户id',
  `trustee_pay_time` int(10) NOT NULL DEFAULT '0' COMMENT '受托支付申请时间',
  `tender_account_min` int(11) DEFAULT '0' COMMENT '最小的投资额',
  `tender_account_max` int(11) DEFAULT '0' COMMENT '最大的投资额',
  `upfiles_id` varchar(230) NOT NULL COMMENT ' 发标上传图片',
  `borrow_running_use` text COMMENT '资金运转-用途',
  `borrow_running_soruce` text COMMENT '资金运转-来源',
  `borrow_measures_instit` text COMMENT '风险控制措施-机构',
  `borrow_measures_mort` text COMMENT '风险控制措施-抵押物',
  `borrow_measures_mea` text COMMENT '险控制措施-措施',
  `files` text COMMENT '项目资料',
  `project_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0汇保贷 1汇典贷 2汇小贷 3汇车贷 4新手标',
  `can_transaction_pc` varchar(3) DEFAULT NULL COMMENT '可投资平台_PC',
  `can_transaction_wei` varchar(3) DEFAULT NULL COMMENT '可投资平台_微网站',
  `can_transaction_ios` varchar(3) DEFAULT NULL COMMENT '可投资平台_IOS',
  `can_transaction_android` varchar(3) DEFAULT NULL COMMENT '可投资平台_Android',
  `operation_label` varchar(3) DEFAULT NULL COMMENT '运营标签',
  `borrow_company_instruction` text COMMENT '机构介绍',
  `borrow_operating_process` text COMMENT '操作流程',
  `company_or_personal` tinyint(1) unsigned DEFAULT NULL COMMENT '借款人信息 借款类型 1：公司 2：个人',
  `borrow_manager_scale_end` varchar(255) DEFAULT '' COMMENT '账户管理费率下线',
  `consume_id` varchar(20) DEFAULT NULL COMMENT '资产包编号',
  `disposal_price_estimate` varchar(50) DEFAULT '' COMMENT '售价预估    ',
  `disposal_period` varchar(50) DEFAULT '' COMMENT '处置周期    ',
  `disposal_channel` varchar(50) DEFAULT '' COMMENT '处置渠道    ',
  `disposal_result` varchar(1000) DEFAULT '' COMMENT '处置结果预案',
  `disposal_note` varchar(1000) DEFAULT '' COMMENT '备注说明',
  `disposal_project_name` varchar(100) DEFAULT '' COMMENT '项目名称    ',
  `disposal_project_type` varchar(100) DEFAULT '' COMMENT '项目类型    ',
  `disposal_area` varchar(200) DEFAULT '' COMMENT '所在地区    ',
  `disposal_predictive_value` varchar(20) DEFAULT '' COMMENT '预估价值    ',
  `disposal_ownership_category` varchar(20) DEFAULT '' COMMENT '权属类别    ',
  `disposal_asset_origin` varchar(20) DEFAULT '' COMMENT '资产成因    ',
  `disposal_attachment_info` varchar(1000) DEFAULT '' COMMENT '附件信息',
  `borrow_increase_money` decimal(10,0) DEFAULT NULL COMMENT '递增金额',
  `borrow_interest_coupon` tinyint(2) DEFAULT NULL COMMENT '优惠券',
  `borrow_taste_money` tinyint(2) DEFAULT NULL COMMENT '体验金',
  `borrow_asset_number` varchar(50) DEFAULT NULL COMMENT '资产编号（风险缓释金）',
  `borrow_project_source` varchar(10) DEFAULT NULL COMMENT '项目来源（项目详情中）',
  `borrow_interest_time` varchar(50) DEFAULT NULL COMMENT '起息时间（项目详情中）',
  `borrow_due_time` varchar(50) DEFAULT NULL COMMENT '到期时间（项目详情中）',
  `borrow_safeguard_way` varchar(500) DEFAULT NULL COMMENT '保障方式（项目详情中）',
  `borrow_income_description` varchar(500) DEFAULT NULL COMMENT '收益说明（项目详情中）',
  `borrow_publisher` varchar(30) DEFAULT NULL COMMENT '发行人（风险缓释金）',
  `borrow_extra_yield` decimal(10,2) DEFAULT '0.00' COMMENT '产品加息收益率（风险缓释金）',
  `increase_interest_flag` tinyint(1) DEFAULT '0' COMMENT '是否加息标志位(0:否,1:是)',
  `contract_period` int(11) DEFAULT NULL COMMENT '融通宝协议期数',
  `borrow_level` varchar(10) DEFAULT NULL COMMENT '借款人评级',
  `asset_attributes` tinyint(1) DEFAULT '0' COMMENT '资产属性 1:抵押标 2:质押标 3:信用标 4:债权转让标 5:净值标',
  `bank_raise_start_date` varchar(10) DEFAULT NULL COMMENT '银行募集开始时间',
  `bank_raise_end_date` varchar(10) DEFAULT NULL COMMENT '银行募集结束时间',
  `bank_regist_days` tinyint(2) unsigned DEFAULT '0' COMMENT '银行备案天数',
  `bank_borrow_days` int(4) DEFAULT '0' COMMENT '银行项目天数',
  `project_name` varchar(15) DEFAULT NULL COMMENT '项目标题',
  `finance_purpose` varchar(30) DEFAULT NULL COMMENT '融资用途',
  `monthly_income` varchar(30) DEFAULT NULL COMMENT '月薪收入',
  `payment` varchar(30) DEFAULT NULL COMMENT '还款来源',
  `first_payment` varchar(30) DEFAULT NULL COMMENT '第一还款来源',
  `second_payment` varchar(30) DEFAULT NULL COMMENT '第二还款来源',
  `cost_introdution` varchar(30) DEFAULT NULL COMMENT '费用说明',
  `fiance_condition` varchar(500) DEFAULT NULL COMMENT '财务状况',
  `is_new` tinyint(1) DEFAULT '0' COMMENT '是否为新标 0 为老标 1为新标 （网站改版后都为新标）',
  `publish_inst_code` varchar(20) NOT NULL DEFAULT '0' COMMENT '定向标: 0-全部  其他值-资金机构的编号',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '100位字符限制-备注',
  `create_user_name` varchar(30) NOT NULL DEFAULT '' COMMENT '创建用户-添加标的人员（账户操作人）',
  `add_ip` varchar(20) DEFAULT NULL COMMENT 'create ip',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `borrow_nid` (`borrow_nid`) USING BTREE,
  KEY `user_id` (`user_id`),
  KEY `name` (`name`),
  KEY `project_type` (`project_type`),
  KEY `repayorgname_index` (`repay_org_name`),
  KEY `idx_borrow_pre_nid` (`borrow_pre_nid`),
  KEY `idx_inst_code` (`inst_code`)
) ENGINE=InnoDB AUTO_INCREMENT=2175 DEFAULT CHARSET=utf8 COMMENT='标的信息表';

-- ----------------------------
-- Table structure for ht_borrow_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_borrow_log`;
CREATE TABLE `ht_borrow_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `borrow_nid` varchar(50) DEFAULT NULL COMMENT '借款编号',
  `borrow_status_cd` int(8) DEFAULT NULL COMMENT '项目状态',
  `borrow_status` varchar(50) DEFAULT NULL COMMENT '项目状态名称',
  `type` varchar(50) DEFAULT NULL COMMENT '修改类型-创建标的-新增-修改-删除',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_user_name` varchar(50) DEFAULT NULL COMMENT '创建用户Name',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建用户',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `del_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2474 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_borrow_maninfo
-- ----------------------------
DROP TABLE IF EXISTS `ht_borrow_maninfo`;
CREATE TABLE `ht_borrow_maninfo` (
  `id` int(15) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `sex` tinyint(2) unsigned DEFAULT NULL COMMENT '1男2女',
  `old` tinyint(2) unsigned DEFAULT NULL COMMENT '年龄',
  `merry` tinyint(2) unsigned DEFAULT NULL COMMENT '1已婚2未婚',
  `pro` varchar(20) DEFAULT NULL,
  `city` varchar(20) DEFAULT NULL,
  `industry` varchar(50) DEFAULT NULL,
  `position` varchar(100) DEFAULT NULL COMMENT '岗位职业',
  `credit` int(11) DEFAULT '0' COMMENT '个人授信额度',
  `size` varchar(50) DEFAULT NULL COMMENT '公司规模（人数）',
  `business` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '月营业额',
  `wtime` varchar(60) DEFAULT NULL COMMENT '在现单位工作的时间',
  `borrow_nid` varchar(50) DEFAULT NULL,
  `borrow_pre_nid` varchar(12) NOT NULL DEFAULT '0',
  `card_no` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `domicile` varchar(50) DEFAULT NULL COMMENT '户籍地',
  `overdue_times` varchar(10) DEFAULT NULL COMMENT '在平台逾期次数',
  `overdue_amount` varchar(15) DEFAULT NULL COMMENT '在平台逾期金额',
  `litigation` varchar(100) DEFAULT '' COMMENT '涉诉情况',
  `is_card` tinyint(1) DEFAULT NULL COMMENT '个贷审核信息 身份证 0未审核 1已审核',
  `is_income` tinyint(1) DEFAULT NULL COMMENT '个贷审核信息 收入状况 0未审核 1已审核',
  `is_credit` tinyint(1) DEFAULT NULL COMMENT '个贷审核信息 信用状况 0未审核 1已审核',
  `is_asset` tinyint(1) DEFAULT NULL COMMENT '个贷审核信息 资产状况 0未审核 1已审核',
  `is_vehicle` tinyint(1) DEFAULT NULL COMMENT '个贷审核信息 车辆状况 0未审核 1已审核',
  `is_driving_license` tinyint(1) DEFAULT NULL COMMENT '个贷审核信息 行驶证 0未审核 1已审核',
  `is_vehicle_registration` tinyint(1) DEFAULT NULL COMMENT '个贷审核信息 车辆登记证 0未审核 1已审核',
  `is_merry` tinyint(1) DEFAULT NULL COMMENT '个贷审核信息 婚姻状况 0未审核 1已审核',
  `is_work` tinyint(1) DEFAULT NULL COMMENT '个贷审核信息 工作状况 0未审核 1已审核',
  `is_account_book` tinyint(1) DEFAULT NULL COMMENT '个贷审核信息 户口本 0未审核 1已审核',
  `annual_income` varchar(30) DEFAULT NULL COMMENT '个人年收入:10万以内；10万以上',
  `overdue_report` varchar(30) DEFAULT NULL COMMENT '征信报告逾期情况:暂未提供；无；已处理',
  `debt_situation` varchar(30) DEFAULT NULL COMMENT '重大负债状况:无',
  `other_borrowed` varchar(30) DEFAULT NULL COMMENT '其他平台借款情况:无',
  `is_funds` varchar(30) DEFAULT NULL COMMENT '借款资金运用情况：不正常,正常',
  `is_managed` varchar(30) DEFAULT NULL COMMENT '借款人经营状况及财务状况：不正常,正常',
  `is_ability` varchar(30) DEFAULT NULL COMMENT '借款人还款能力变化情况：不正常,正常',
  `is_overdue` varchar(30) DEFAULT NULL COMMENT '借款人逾期情况：暂无,有',
  `is_complaint` varchar(30) DEFAULT NULL COMMENT '借款人涉诉情况：暂无,有',
  `is_punished` varchar(30) DEFAULT NULL COMMENT '借款人受行政处罚情况：暂无,有',
  `address` varchar(100) DEFAULT NULL COMMENT '借款人地址',
  PRIMARY KEY (`id`),
  KEY `idx_borrow_nid` (`borrow_nid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3287 DEFAULT CHARSET=utf8 COMMENT='标的个人信息表';

-- ----------------------------
-- Table structure for ht_borrow_project_repay
-- ----------------------------
DROP TABLE IF EXISTS `ht_borrow_project_repay`;
CREATE TABLE `ht_borrow_project_repay` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `borrow_class` varchar(255) DEFAULT NULL,
  `repay_method` varchar(50) DEFAULT NULL,
  `method_name` varchar(255) DEFAULT NULL,
  `del_flag` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '删除状态',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_group_id` varchar(50) DEFAULT NULL,
  `create_user_id` varchar(50) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_group_id` varchar(50) DEFAULT NULL,
  `update_user_id` varchar(50) DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1063 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_borrow_project_type
-- ----------------------------
DROP TABLE IF EXISTS `ht_borrow_project_type`;
CREATE TABLE `ht_borrow_project_type` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `borrow_project_type` varchar(10) DEFAULT NULL COMMENT '汇直投 汇消费',
  `borrow_cd` tinyint(1) unsigned DEFAULT NULL COMMENT '参数唯一标识',
  `borrow_name` varchar(100) DEFAULT NULL COMMENT '名称',
  `borrow_class` varchar(255) DEFAULT NULL COMMENT '项目编号',
  `invest_user_type` tinyint(1) unsigned DEFAULT NULL COMMENT '投资用户类型0:51老用户 1:新用户 2: 全部',
  `status` tinyint(1) unsigned DEFAULT NULL COMMENT '状态',
  `invest_start` varchar(60) DEFAULT NULL COMMENT '投资起始值',
  `invest_end` varchar(60) DEFAULT NULL COMMENT '投资最大值',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_user_id` int(10) unsigned DEFAULT '0' COMMENT '建立用户id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user_id` int(10) unsigned DEFAULT '0' COMMENT '建立用户id',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `increase_money` decimal(11,0) DEFAULT NULL COMMENT '递增金额',
  `interest_coupon` tinyint(2) unsigned DEFAULT NULL COMMENT '优惠券',
  `taste_money` tinyint(2) unsigned DEFAULT NULL COMMENT '体验金',
  `increase_interest_flag` tinyint(1) DEFAULT '0' COMMENT '是否加息标志位(0:否,1:是)',
  PRIMARY KEY (`id`),
  KEY `idx_borrow_cd` (`borrow_cd`),
  KEY `idx_borrow_project_type` (`borrow_project_type`),
  KEY `idx_borrow_class` (`borrow_class`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8 COMMENT='配置表';

-- ----------------------------
-- Table structure for ht_borrow_recover
-- ----------------------------
DROP TABLE IF EXISTS `ht_borrow_recover`;
CREATE TABLE `ht_borrow_recover` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `status` tinyint(2) unsigned DEFAULT '0' COMMENT '状态(初始:0,放款成功后更新成:1)',
  `user_id` int(11) DEFAULT '0' COMMENT '用户ID',
  `user_name` varchar(30) NOT NULL COMMENT '投资人用户名',
  `borrow_nid` varchar(50) DEFAULT '' COMMENT '标的编号',
  `nid` varchar(50) NOT NULL COMMENT '投资订单号(对应borrow_tender表的nid)',
  `auth_code` varchar(30) DEFAULT NULL COMMENT '投资人投标成功的授权号',
  `borrow_userid` int(11) DEFAULT '0' COMMENT '借款人用户ID',
  `borrow_user_name` varchar(30) NOT NULL COMMENT '借款人用户名',
  `tender_id` int(11) DEFAULT '0' COMMENT '投资ID(对应borrow_tender表里的ID字段)',
  `accede_order_id` varchar(50) DEFAULT NULL COMMENT '汇计划加入订单号',
  `recover_status` tinyint(2) DEFAULT NULL COMMENT '还款状态(只用于处理还款,展示请关联ht_borrow.status)(0:未还款,1:已还款,2:还款失败)',
  `recover_period` tinyint(2) unsigned DEFAULT NULL COMMENT '还款期数',
  `recover_time` int(11) DEFAULT NULL COMMENT '估计还款时间(分期标的是下一期应还时间)',
  `recover_yestime` int(11) DEFAULT NULL COMMENT '已经还款时间',
  `recover_account` decimal(11,2) DEFAULT '0.00' COMMENT '应还总额',
  `recover_interest` decimal(11,2) DEFAULT '0.00' COMMENT '应还利息',
  `recover_capital` decimal(11,2) DEFAULT '0.00' COMMENT '应还本金',
  `recover_account_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还总额',
  `recover_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还利息',
  `recover_capital_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还本金',
  `recover_account_wait` decimal(11,2) NOT NULL COMMENT '未收总额',
  `recover_capital_wait` decimal(11,2) NOT NULL COMMENT '未收本金',
  `recover_interest_wait` decimal(11,2) NOT NULL COMMENT '未收本息',
  `recover_type` varchar(50) NOT NULL COMMENT '还款流程(yes,late,wait)',
  `recover_service_fee` decimal(11,2) DEFAULT '0.00' COMMENT '放款服务费',
  `recover_fee_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已收管理费',
  `repay_charge_interest` decimal(11,2) DEFAULT '0.00' COMMENT '已还款提前还款利息',
  `repay_delay_interest` decimal(10,2) DEFAULT '0.00' COMMENT '已还款延期还款利息',
  `credit_manage_fee` decimal(10,2) DEFAULT '0.00' COMMENT '已收债转管理费',
  `repay_late_interest` decimal(11,2) DEFAULT '0.00' COMMENT '已还款逾期利息',
  `debt_status` tinyint(1) DEFAULT '0' COMMENT '债权是否结束状态(0:否,1:是)',
  `credit_status` tinyint(1) DEFAULT '0' COMMENT '债转状态 0初始 1承接停止 2完全承接',
  `recover_fee` decimal(11,2) NOT NULL COMMENT '账户管理费',
  `recover_late_fee` decimal(11,2) NOT NULL COMMENT '逾期费用收入',
  `advance_status` int(2) NOT NULL COMMENT '0:正常,1:提前,2:延期,3:逾期',
  `charge_days` int(11) DEFAULT '0' COMMENT '提前天数',
  `charge_interest` decimal(11,2) DEFAULT NULL COMMENT '提前减息(已加罚息)',
  `late_days` int(11) NOT NULL DEFAULT '0' COMMENT '逾期天数',
  `late_interest` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '逾期利息',
  `delay_days` int(11) DEFAULT '0' COMMENT '延期天数',
  `delay_interest` decimal(10,2) DEFAULT '0.00' COMMENT '延期利息',
  `delay_rate` decimal(10,8) DEFAULT '0.00000000' COMMENT '延期费率',
  `add_ip` varchar(20) DEFAULT NULL COMMENT 'create ip',
  `sendmail` tinyint(1) DEFAULT '0' COMMENT '0发送邮件，1已发送',
  `web` tinyint(1) DEFAULT '0' COMMENT '网站收支计算标识',
  `credit_amount` decimal(11,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '已转让本金',
  `credit_interest_amount` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已债转总利息（含垫付）',
  `credit_time` int(11) NOT NULL DEFAULT '0' COMMENT '债转时间',
  `repay_ordid` varchar(20) DEFAULT NULL COMMENT '还款订单号',
  `repay_orddate` varchar(10) DEFAULT NULL COMMENT '还款时间',
  `invite_user_name` varchar(100) DEFAULT NULL COMMENT '推荐人用户名(还款时更新)',
  `invite_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '推荐人用户id(还款时更新)',
  `invite_region_id` int(11) NOT NULL DEFAULT '0' COMMENT '一级部门id(还款时更新)',
  `invite_region_name` varchar(100) NOT NULL DEFAULT ' ' COMMENT '一级部门名称(还款时更新)',
  `invite_branch_id` int(11) NOT NULL DEFAULT '0' COMMENT '二级部门id(还款时更新)',
  `invite_branch_name` varchar(100) NOT NULL DEFAULT ' ' COMMENT '二级部门名称(还款时更新)',
  `invite_department_id` int(11) NOT NULL DEFAULT '0' COMMENT '三级部门id(还款时更新)',
  `invite_department_name` varchar(100) NOT NULL DEFAULT ' ' COMMENT '三级部门名称(还款时更新)',
  `tender_user_attribute` int(11) NOT NULL DEFAULT '0' COMMENT '投资人用户属性',
  `invite_user_attribute` int(11) NOT NULL DEFAULT '0' COMMENT '推荐人用户属性',
  `loan_batch_no` varchar(10) DEFAULT NULL COMMENT '放款批次号',
  `repay_batch_no` varchar(10) DEFAULT NULL COMMENT '还款批次号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `charge_penalty_interest` decimal(11,2) DEFAULT '0.00' COMMENT '提前还款罚息',
  `repay_charge_penalty_interest` decimal(11,2) DEFAULT '0.00' COMMENT '已还提前还款罚息',
  PRIMARY KEY (`id`),
  KEY `borrow_nid` (`borrow_nid`),
  KEY `nid` (`nid`),
  KEY `idx_userid_recover_yestime` (`user_id`,`recover_yestime`),
  KEY `idx_recover_time` (`recover_time`),
  KEY `idx_tender_id` (`tender_id`),
  KEY `idx_addtime` (`create_time`),
  KEY `idx_accede_order_id` (`accede_order_id`),
  KEY `idx_recover_yestime` (`recover_yestime`)
) ENGINE=InnoDB AUTO_INCREMENT=3096 DEFAULT CHARSET=utf8 COMMENT='标的放款记录（投资人） 总表';

-- ----------------------------
-- Table structure for ht_borrow_recover_plan
-- ----------------------------
DROP TABLE IF EXISTS `ht_borrow_recover_plan`;
CREATE TABLE `ht_borrow_recover_plan` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `status` tinyint(2) unsigned DEFAULT '0' COMMENT '状态(初始:0,放款成功后更新成:1)',
  `user_id` int(11) DEFAULT '0' COMMENT '用户ID',
  `user_name` varchar(30) NOT NULL COMMENT '投资人用户名',
  `borrow_nid` varchar(50) DEFAULT '' COMMENT '标的编号',
  `nid` varchar(50) NOT NULL COMMENT '投资订单号(对应borrow_tender表的nid)',
  `borrow_userid` int(11) DEFAULT '0' COMMENT '借款人用户ID',
  `borrow_user_name` varchar(30) NOT NULL COMMENT '借款人用户名',
  `tender_id` int(11) DEFAULT '0' COMMENT '投资ID(对应borrow_tender表里的ID字段)',
  `accede_order_id` varchar(50) DEFAULT NULL COMMENT '汇计划加入订单号',
  `recover_status` tinyint(2) DEFAULT NULL COMMENT '还款状态(0:未还款(advance_status=3逾期中),1:已还款,2:还款失败)',
  `recover_period` tinyint(2) unsigned DEFAULT NULL COMMENT '还款期数',
  `recover_time` int(11) DEFAULT NULL COMMENT '估计还款时间(分期标的是下一期应还时间)',
  `recover_yestime` varchar(50) DEFAULT NULL COMMENT '已经还款时间(当期实际还款时间)',
  `recover_account` decimal(11,2) DEFAULT '0.00' COMMENT '应还总额',
  `recover_interest` decimal(11,2) DEFAULT '0.00' COMMENT '应还利息',
  `recover_capital` decimal(11,2) DEFAULT '0.00' COMMENT '应还本金',
  `recover_account_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还总额',
  `recover_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还利息',
  `recover_capital_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还本金',
  `recover_account_wait` decimal(11,2) NOT NULL COMMENT '未收总额',
  `recover_capital_wait` decimal(11,2) NOT NULL COMMENT '未收本金',
  `recover_interest_wait` decimal(11,2) NOT NULL COMMENT '未收利息',
  `recover_type` varchar(50) NOT NULL COMMENT '还款流程(yes,late,wait)',
  `recover_fee` decimal(11,2) NOT NULL COMMENT '账户管理费',
  `recover_late_fee` decimal(11,2) NOT NULL COMMENT '逾期费用收入',
  `advance_status` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '0:正常,1:提前,2:延期,3:逾期',
  `charge_days` int(11) DEFAULT '0' COMMENT '提前天数',
  `charge_interest` decimal(11,2) DEFAULT NULL COMMENT '提前减息(已加罚息)',
  `late_days` int(11) NOT NULL DEFAULT '0' COMMENT '逾期天数',
  `late_interest` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '逾期利息',
  `delay_days` int(11) DEFAULT '0' COMMENT '延期天数',
  `delay_interest` decimal(10,2) DEFAULT '0.00' COMMENT '延期利息',
  `delay_rate` decimal(10,8) DEFAULT '0.00000000' COMMENT '延期利率',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `add_ip` varchar(20) DEFAULT NULL COMMENT 'create ip',
  `sendmail` tinyint(1) DEFAULT '0' COMMENT '0发送邮件，1已发送',
  `repay_order_id` varchar(50) DEFAULT NULL COMMENT '还款订单号',
  `repay_order_date` varchar(20) DEFAULT NULL COMMENT '还款订单日期',
  `invite_user_name` varchar(100) DEFAULT NULL COMMENT '推荐人用户名(还款时更新)',
  `invite_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '推荐人用户id(还款时更新)',
  `invite_region_id` int(11) NOT NULL DEFAULT '0' COMMENT '一级部门id(还款时更新)',
  `invite_region_name` varchar(100) NOT NULL DEFAULT ' ' COMMENT '一级部门名称(还款时更新)',
  `invite_branch_id` int(11) NOT NULL DEFAULT '0' COMMENT '二级部门id(还款时更新)',
  `invite_branch_name` varchar(100) NOT NULL DEFAULT ' ' COMMENT '二级部门名称(还款时更新)',
  `invite_department_id` int(11) NOT NULL DEFAULT '0' COMMENT '三级部门id(还款时更新)',
  `invite_department_name` varchar(100) NOT NULL DEFAULT ' ' COMMENT '三级部门名称(还款时更新)',
  `tender_user_attribute` int(11) NOT NULL DEFAULT '0' COMMENT '投资人用户属性',
  `invite_user_attribute` int(11) NOT NULL DEFAULT '0' COMMENT '推荐人用户属性',
  `auth_code` varchar(30) DEFAULT NULL COMMENT '投资人投标成功的授权号',
  `loan_batch_no` varchar(10) DEFAULT NULL COMMENT '放款批次号',
  `repay_batch_no` varchar(10) DEFAULT NULL COMMENT '还款批次号',
  `recover_fee_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已收管理费',
  `credit_status` tinyint(1) DEFAULT '0' COMMENT '债转状态 0初始 1承接停止 2完全承接',
  `repay_charge_interest` decimal(11,2) DEFAULT '0.00' COMMENT '已还款提前还款利息',
  `repay_delay_interest` decimal(10,2) DEFAULT '0.00' COMMENT '已还款延期还款利息',
  `repay_late_interest` decimal(11,2) DEFAULT '0.00' COMMENT '已还款逾期利息',
  `credit_amount` decimal(11,2) unsigned DEFAULT '0.00' COMMENT '已承接债转本金',
  `credit_interest_amount` decimal(11,2) DEFAULT '0.00' COMMENT '已承接垫付利息',
  `credit_manage_fee` decimal(10,2) DEFAULT '0.00' COMMENT '已收债转管理费',
  `debt_status` tinyint(1) DEFAULT '0' COMMENT '债权是否结束状态(0:否,1:是)',
  `credit_interest` decimal(11,2) DEFAULT '0.00' COMMENT '已承接利息',
  `charge_penalty_interest` decimal(11,2) DEFAULT '0.00' COMMENT '提前还款罚息',
  `repay_charge_penalty_interest` decimal(11,2) DEFAULT '0.00' COMMENT '已还提前还款罚息',
  PRIMARY KEY (`id`),
  KEY `period` (`borrow_nid`,`recover_status`,`recover_period`),
  KEY `idx_nid` (`nid`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_recover_time` (`recover_time`),
  KEY `idx_accede_order_id` (`accede_order_id`),
  KEY `idx_recover_yestime` (`recover_yestime`)
) ENGINE=InnoDB AUTO_INCREMENT=6731 DEFAULT CHARSET=utf8 COMMENT='标的放款记录分期（投资人）';

-- ----------------------------
-- Table structure for ht_borrow_repay
-- ----------------------------
DROP TABLE IF EXISTS `ht_borrow_repay`;
CREATE TABLE `ht_borrow_repay` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(11) DEFAULT '0' COMMENT '借款人用户ID',
  `user_name` varchar(30) NOT NULL COMMENT '借款人用户名',
  `borrow_nid` varchar(50) DEFAULT '0' COMMENT '标的编号',
  `nid` varchar(50) NOT NULL COMMENT '标识名(标的编号_借款人用户ID_1)放款时更新',
  `repay_user_id` int(11) DEFAULT '0' COMMENT '实际还款人（借款人、担保机构、保证金）的用户ID',
  `repay_type` varchar(50) DEFAULT NULL COMMENT '还款状态(wait,wait_yes)',
  `repay_fee` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '还款费用',
  `repay_action_time` int(11) DEFAULT NULL COMMENT '执行还款的时间',
  `repay_period` tinyint(2) unsigned DEFAULT '0' COMMENT '还款期数',
  `repay_time` int(11) unsigned DEFAULT '0' COMMENT '下期还款时间',
  `repay_yestime` int(11) unsigned DEFAULT '0' COMMENT '已经还款时间',
  `repay_account_all` decimal(11,2) NOT NULL COMMENT '还款总额，加上费用',
  `repay_account` decimal(11,2) DEFAULT '0.00' COMMENT '应还总额',
  `repay_interest` decimal(11,2) DEFAULT '0.00' COMMENT '应还利息',
  `repay_capital` decimal(11,2) DEFAULT '0.00' COMMENT '应还本金',
  `repay_account_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还总额',
  `late_repay_days` int(11) NOT NULL COMMENT '逾期的天数',
  `repay_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还利息',
  `repay_capital_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还本金',
  `repay_capital_wait` decimal(11,2) NOT NULL COMMENT '未还本金',
  `repay_interest_wait` decimal(11,2) NOT NULL COMMENT '未还利息',
  `charge_days` int(11) DEFAULT '0' COMMENT '提前还款天数',
  `charge_interest` decimal(11,2) DEFAULT NULL COMMENT '提前还款减息(已加罚息)',
  `late_days` int(11) NOT NULL DEFAULT '0' COMMENT '逾期天数',
  `late_interest` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '逾期利息',
  `delay_days` int(10) DEFAULT '0' COMMENT '延期天数',
  `delay_interest` decimal(10,2) DEFAULT '0.00' COMMENT '延期利息',
  `delay_remark` varchar(100) DEFAULT '0' COMMENT '延期备注说明',
  `repay_money_source` tinyint(1) unsigned DEFAULT NULL COMMENT '还款来源（1、借款人还款，2、机构垫付，3、保证金垫付）',
  `repay_sms_reminder` tinyint(1) unsigned DEFAULT '0' COMMENT '还款短信提醒(0:未发送提醒,1:还款前三天提醒,2:还款当天提醒)',
  `status` tinyint(2) unsigned DEFAULT '0' COMMENT '状态(初始:0,放款成功后更新成:1)',
  `auto_repay` tinyint(1) DEFAULT '0' COMMENT '是否自动还款 0:非自动还款 1:是自动还款',
  `advance_status` tinyint(2) unsigned DEFAULT '0' COMMENT '0:正常,1:提前,2:延期,3:逾期',
  `repay_status` tinyint(2) unsigned DEFAULT '0' COMMENT '还款状态(0:未还款,1:已还款)',
  `repay_username` varchar(30) DEFAULT NULL COMMENT '实际还款人（借款人、担保机构、保证金）的用户名',
  `add_ip` varchar(20) DEFAULT NULL COMMENT 'create ip',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `charge_penalty_interest` decimal(11,2) DEFAULT '0.00' COMMENT '提前还款罚息',
  `late_interest_wait` decimal(11,2) DEFAULT '0.00' COMMENT '逾期应还利息',
  PRIMARY KEY (`id`),
  KEY `borrow_nid` (`borrow_nid`),
  KEY `idx_repay_time` (`repay_time`),
  KEY `idx_repay_action_time` (`repay_action_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1563 DEFAULT CHARSET=utf8 COMMENT='标的还款记录（借款人）总表';

-- ----------------------------
-- Table structure for ht_borrow_repay_plan
-- ----------------------------
DROP TABLE IF EXISTS `ht_borrow_repay_plan`;
CREATE TABLE `ht_borrow_repay_plan` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT '0' COMMENT '借款人用户ID',
  `user_name` varchar(30) NOT NULL COMMENT '借款人用户名',
  `borrow_nid` varchar(50) DEFAULT '0' COMMENT '借款编号',
  `repay_user_id` int(11) DEFAULT '0' COMMENT '实际还款人（借款人、担保机构、保证金）的用户ID',
  `repay_username` varchar(30) DEFAULT NULL COMMENT '实际还款人（借款人、担保机构、保证金）的用户名',
  `nid` varchar(50) NOT NULL COMMENT '标识名(标的编号_借款人用户ID_1)放款时更新',
  `repay_type` varchar(50) DEFAULT NULL COMMENT '还款状态(wait,wait_yes)',
  `repay_fee` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '还款费用',
  `repay_action_time` varchar(50) DEFAULT NULL COMMENT '执行还款的时间',
  `repay_period` int(2) DEFAULT NULL COMMENT '还款期数',
  `repay_time` int(11) unsigned DEFAULT '0' COMMENT '下期还款时间',
  `repay_yestime` int(11) unsigned DEFAULT '0' COMMENT '已经还款时间',
  `repay_account_all` decimal(11,2) NOT NULL COMMENT '还款总额，加上费用',
  `repay_account` decimal(11,2) DEFAULT '0.00' COMMENT '应还总额',
  `repay_interest` decimal(11,2) DEFAULT '0.00' COMMENT '应还利息',
  `repay_capital` decimal(11,2) DEFAULT '0.00' COMMENT '应还本金',
  `repay_account_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还总额',
  `late_repay_days` int(11) NOT NULL COMMENT '逾期的天数',
  `repay_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还利息',
  `repay_capital_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还本金',
  `repay_capital_wait` decimal(11,2) NOT NULL COMMENT '未还本金',
  `repay_interest_wait` decimal(11,2) NOT NULL COMMENT '未还利息',
  `charge_days` int(11) DEFAULT '0' COMMENT '提前还款天数',
  `charge_interest` decimal(11,2) DEFAULT NULL COMMENT '提前还款减息(已加罚息)',
  `late_days` int(11) NOT NULL DEFAULT '0' COMMENT '逾期天数',
  `late_interest` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '逾期利息',
  `delay_days` int(10) DEFAULT '0' COMMENT '延期天数',
  `delay_interest` decimal(10,2) DEFAULT '0.00' COMMENT '延期利息',
  `delay_remark` varchar(100) DEFAULT '0' COMMENT '延期备注说明',
  `repay_money_source` tinyint(1) unsigned DEFAULT NULL COMMENT '还款来源（1、借款人还款，2、机构垫付，3、保证金垫付）',
  `repay_sms_reminder` tinyint(1) unsigned DEFAULT '0' COMMENT '还款短信提醒(0:未发送提醒,1:还款前三天提醒,2:还款当天提醒)',
  `auto_repay` tinyint(1) DEFAULT '0' COMMENT '是否自动还款 0:非自动还款 1:是自动还款',
  `status` tinyint(2) unsigned DEFAULT '0' COMMENT '状态(初始:0,放款成功后更新成:1)',
  `repay_status` tinyint(2) unsigned DEFAULT '0' COMMENT '还款状态(0:未还款,1:已还款)',
  `advance_status` tinyint(2) unsigned DEFAULT '0' COMMENT '0:正常,1:提前,2:延期,3:逾期',
  `add_ip` varchar(20) DEFAULT NULL COMMENT 'create ip',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `charge_penalty_interest` decimal(11,2) DEFAULT '0.00' COMMENT '提前还款罚息',
  `late_interest_wait` decimal(11,2) DEFAULT '0.00' COMMENT '逾期应还利息',
  PRIMARY KEY (`id`),
  KEY `period` (`borrow_nid`,`repay_period`)
) ENGINE=InnoDB AUTO_INCREMENT=4683 DEFAULT CHARSET=utf8 COMMENT='标的还款记录分期(借款人)';

-- ----------------------------
-- Table structure for ht_borrow_send_type
-- ----------------------------
DROP TABLE IF EXISTS `ht_borrow_send_type`;
CREATE TABLE `ht_borrow_send_type` (
  `send_cd` varchar(50) NOT NULL COMMENT '唯一标识(AUTO_BAIL,AUTO_FULL)',
  `send_name` varchar(255) DEFAULT NULL COMMENT '名称(自动发标时间间隔,自动复审时间间隔)',
  `after_time` int(11) DEFAULT NULL COMMENT '发表时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注说明',
  `del_flag` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '删除状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`send_cd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_borrow_style
-- ----------------------------
DROP TABLE IF EXISTS `ht_borrow_style`;
CREATE TABLE `ht_borrow_style` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nid` varchar(50) NOT NULL COMMENT '标示名',
  `status` int(11) NOT NULL COMMENT '是否启用',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `title` varchar(50) NOT NULL COMMENT '名称，可改',
  `contents` longtext NOT NULL COMMENT '算法公式',
  `remark` longtext NOT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_nid` (`nid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='标的还款方式';

-- ----------------------------
-- Table structure for ht_borrow_tender
-- ----------------------------
DROP TABLE IF EXISTS `ht_borrow_tender`;
CREATE TABLE `ht_borrow_tender` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT '0' COMMENT '用户ID',
  `user_name` varchar(30) NOT NULL DEFAULT '' COMMENT '投资人用户名',
  `borrow_user_id` int(11) NOT NULL COMMENT '借款人ID',
  `borrow_user_name` varchar(30) NOT NULL COMMENT '借款人用户名',
  `status` tinyint(2) unsigned DEFAULT '0' COMMENT '状态(0:初始,1:已放款,2:放款失败)',
  `borrow_nid` varchar(50) DEFAULT '0' COMMENT '标的编号',
  `nid` varchar(50) NOT NULL COMMENT '投资订单号',
  `accede_order_id` varchar(50) DEFAULT NULL COMMENT '汇计划加入订单号',
  `auth_code` varchar(30) DEFAULT NULL COMMENT '投资人投标成功的授权号',
  `account` decimal(11,2) DEFAULT '0.00' COMMENT '投资金额',
  `recover_full_status` tinyint(2) unsigned DEFAULT '0' COMMENT '满标状态(0:初始,1:满标)记录哪一笔是最后一笔满标',
  `recover_fee` decimal(11,2) NOT NULL COMMENT '管理费',
  `recover_account_all` decimal(11,2) DEFAULT '0.00' COMMENT '收款总额',
  `recover_account_interest` decimal(11,2) DEFAULT '0.00' COMMENT '收款总利息',
  `recover_account_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已收总额',
  `recover_account_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已收利息',
  `recover_account_capital_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已收本金',
  `recover_account_wait` decimal(11,2) DEFAULT '0.00' COMMENT '待收总额',
  `recover_account_interest_wait` decimal(11,2) DEFAULT '0.00' COMMENT '待收利息',
  `recover_account_capital_wait` decimal(11,2) DEFAULT '0.00' COMMENT '待收本金',
  `recover_times` int(10) DEFAULT '0' COMMENT '已收期数',
  `recover_advance_fee` decimal(11,2) NOT NULL COMMENT '提前还款费用',
  `recover_late_fee` decimal(11,2) NOT NULL COMMENT '逾期还款费用',
  `loan_amount` decimal(10,2) DEFAULT '0.00' COMMENT '放款金额',
  `loan_fee` decimal(10,2) DEFAULT '0.00' COMMENT '服务费',
  `add_ip` varchar(20) DEFAULT NULL COMMENT 'create ip',
  `client` tinyint(1) DEFAULT '0' COMMENT '客户端0PC，1微信2安卓APP，3IOS APP，4其他',
  `invite_user_name` varchar(100) DEFAULT NULL COMMENT '推荐人用户名(投资时)',
  `invite_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '推荐人用户id(投资时)',
  `invite_region_id` int(11) NOT NULL DEFAULT '0' COMMENT '一级部门id(投资时)',
  `invite_region_name` varchar(100) NOT NULL DEFAULT ' ' COMMENT '一级部门名称(投资时)',
  `invite_branch_id` int(11) NOT NULL DEFAULT '0' COMMENT '二级部门id(投资时)',
  `invite_branch_name` varchar(100) NOT NULL DEFAULT ' ' COMMENT '二级部门名称(投资时)',
  `invite_department_id` int(11) NOT NULL DEFAULT '0' COMMENT '三级部门id(投资时)',
  `invite_department_name` varchar(100) NOT NULL DEFAULT ' ' COMMENT '三级部门名称(投资时)',
  `tender_user_attribute` int(11) NOT NULL DEFAULT '0' COMMENT '投资人用户属性',
  `invite_user_attribute` int(11) NOT NULL DEFAULT '0' COMMENT '推荐人用户属性',
  `order_date` varchar(20) NOT NULL DEFAULT ' ' COMMENT '投资订单日期',
  `loan_order_date` varchar(20) DEFAULT NULL COMMENT '放款订单日期',
  `loan_ordid` varchar(20) NOT NULL DEFAULT ' ' COMMENT '放款订单号',
  `remark` varchar(300) DEFAULT NULL COMMENT '备注',
  `invest_type` tinyint(1) DEFAULT '0' COMMENT '投资类型 0手动投标 1预约投标 2自动投标',
  `tender_type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0投资1复投',
  `tender_from` varchar(8) NOT NULL DEFAULT 'hyjf' COMMENT '投资来源:默认-hyjf,wrb-风车理财',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `tender_user_utm_id` int(11) DEFAULT NULL COMMENT '渠道来源当时',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nid` (`nid`),
  KEY `user_id` (`user_id`),
  KEY `borrow_nid` (`borrow_nid`),
  KEY `IDX_addtime` (`create_time`) USING BTREE,
  KEY `idx_accede_order_id` (`accede_order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4204 DEFAULT CHARSET=utf8 COMMENT='标的投资详情表';

-- ----------------------------
-- Table structure for ht_borrow_tender_cpn
-- ----------------------------
DROP TABLE IF EXISTS `ht_borrow_tender_cpn`;
CREATE TABLE `ht_borrow_tender_cpn` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT '0' COMMENT '用户名称',
  `status` tinyint(2) unsigned DEFAULT '0' COMMENT '状态(0:初始,1:已放款,2:放款失败)',
  `borrow_nid` varchar(50) DEFAULT '0',
  `nid` varchar(50) NOT NULL COMMENT '投资订单号',
  `account_tender` decimal(11,2) DEFAULT '0.00',
  `account` decimal(11,2) DEFAULT '0.00',
  `change_status` tinyint(2) unsigned DEFAULT '0' COMMENT '债权转让',
  `change_userid` int(11) NOT NULL COMMENT '转让人',
  `change_period` int(11) NOT NULL COMMENT '债权转让期数',
  `tender_status` tinyint(2) unsigned DEFAULT '0' COMMENT '投资状态',
  `tender_nid` varchar(50) NOT NULL,
  `tender_award_account` decimal(11,2) NOT NULL COMMENT '投资奖励金额',
  `recover_full_status` tinyint(2) unsigned DEFAULT '0' COMMENT '满标状态(0:初始,1:满标)记录哪一笔是最后一笔满标',
  `recover_fee` decimal(11,2) NOT NULL COMMENT '回收的总费用',
  `recover_type` varchar(100) NOT NULL COMMENT '回收类型',
  `recover_account_all` decimal(11,2) DEFAULT '0.00' COMMENT '收款总额',
  `recover_account_interest` decimal(11,2) DEFAULT '0.00' COMMENT '收款总利息',
  `recover_account_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已收总额',
  `recover_account_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已收利息',
  `recover_account_capital_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已收本金',
  `recover_account_wait` decimal(11,2) DEFAULT '0.00' COMMENT '待收总额',
  `recover_account_interest_wait` decimal(11,2) DEFAULT '0.00' COMMENT '待收利息',
  `recover_account_capital_wait` decimal(11,2) DEFAULT '0.00' COMMENT '待收本金',
  `recover_times` int(10) DEFAULT '0' COMMENT '已收期数',
  `recover_advance_fee` decimal(11,2) NOT NULL COMMENT '提前还款费用',
  `recover_late_fee` decimal(11,2) NOT NULL COMMENT '逾期还款费用',
  `tender_award_fee` decimal(11,2) NOT NULL COMMENT '投资奖励增加',
  `loan_amount` decimal(10,2) DEFAULT '0.00' COMMENT '放款金额',
  `loan_fee` decimal(10,2) DEFAULT '0.00' COMMENT '服务费',
  `contents` varchar(250) NOT NULL,
  `auto_status` tinyint(2) unsigned DEFAULT '0' COMMENT '自动投标',
  `web_status` tinyint(2) unsigned DEFAULT '0' COMMENT '网站投标',
  `api_status` tinyint(1) DEFAULT '0' COMMENT '放款状态',
  `add_ip` varchar(20) DEFAULT NULL COMMENT 'create ip',
  `period_status` tinyint(1) NOT NULL DEFAULT '0',
  `isok` tinyint(1) DEFAULT '0',
  `is_report` tinyint(4) DEFAULT '0',
  `flag` tinyint(1) DEFAULT '0' COMMENT '专用标记',
  `activity_flag` tinyint(1) DEFAULT '0' COMMENT '活动专用标志',
  `client` tinyint(1) DEFAULT '0' COMMENT '客户端0PC，1微信2安卓APP，3IOS APP，4其他',
  `web` tinyint(1) NOT NULL DEFAULT '0' COMMENT '网站收支计算标识',
  `invite_user_name` varchar(100) DEFAULT ' ',
  `invite_branch_id` int(11) DEFAULT '0',
  `invite_branch_name` varchar(100) DEFAULT ' ',
  `invite_department_id` int(11) DEFAULT '0',
  `invite_department_name` varchar(100) DEFAULT ' ',
  `tender_user_attribute` int(11) DEFAULT '0',
  `invite_user_attribute` int(11) DEFAULT '0',
  `order_date` varchar(20) DEFAULT ' ',
  `loan_ordid` varchar(20) DEFAULT ' ',
  `invite_user_id` int(11) DEFAULT '0',
  `invite_region_id` int(11) DEFAULT '0',
  `invite_region_name` varchar(100) DEFAULT ' ',
  `tender_user_name` varchar(100) DEFAULT NULL COMMENT '投资人username',
  `tender_type` tinyint(1) unsigned DEFAULT '1' COMMENT '投资类别 1：直投类，2：汇添金',
  `remark` varchar(300) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nid` (`nid`),
  KEY `borrow_nid` (`borrow_nid`),
  KEY `addtime` (`create_time`),
  KEY `IDX_USER_ID_BORROW_NID` (`user_id`,`borrow_nid`)
) ENGINE=InnoDB AUTO_INCREMENT=1307 DEFAULT CHARSET=utf8 COMMENT='优惠券投资表';

-- ----------------------------
-- Table structure for ht_borrow_tender_tmp
-- ----------------------------
DROP TABLE IF EXISTS `ht_borrow_tender_tmp`;
CREATE TABLE `ht_borrow_tender_tmp` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT '0' COMMENT '用户ID',
  `user_name` varchar(30) NOT NULL DEFAULT '' COMMENT '投资用户名',
  `borrow_user_id` int(11) NOT NULL COMMENT '借款人用户ID',
  `borrow_user_name` varchar(30) NOT NULL COMMENT '借款人用户名',
  `status` tinyint(2) unsigned DEFAULT '0' COMMENT '状态(0:初始,1:已放款,2:放款失败)',
  `borrow_nid` varchar(50) DEFAULT '0' COMMENT '标的编号',
  `nid` varchar(50) NOT NULL COMMENT '投资订单号',
  `account` decimal(11,2) DEFAULT '0.00' COMMENT '投资金额',
  `recover_full_status` tinyint(2) unsigned DEFAULT '0' COMMENT '满标状态(0:初始,1:满标)记录哪一笔是最后一笔满标',
  `recover_fee` decimal(11,2) NOT NULL COMMENT '管理费',
  `recover_account_all` decimal(11,2) DEFAULT '0.00' COMMENT '收款总额',
  `recover_account_interest` decimal(11,2) DEFAULT '0.00' COMMENT '收款总利息',
  `recover_account_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已收总额',
  `recover_account_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已收利息',
  `recover_account_capital_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已收本金',
  `recover_account_wait` decimal(11,2) DEFAULT '0.00' COMMENT '待收总额',
  `recover_account_interest_wait` decimal(11,2) DEFAULT '0.00' COMMENT '待收利息',
  `recover_account_capital_wait` decimal(11,2) DEFAULT '0.00' COMMENT '待收本金',
  `recover_times` int(10) DEFAULT '0' COMMENT '已收期数',
  `recover_advance_fee` decimal(11,2) NOT NULL COMMENT '提前还款费用',
  `recover_late_fee` decimal(11,2) NOT NULL COMMENT '逾期还款费用',
  `loan_amount` decimal(10,2) DEFAULT '0.00' COMMENT '放款金额',
  `loan_fee` decimal(10,2) DEFAULT '0.00' COMMENT '服务费',
  `add_ip` varchar(15) DEFAULT NULL COMMENT 'create ip',
  `client` tinyint(1) DEFAULT '0' COMMENT '客户端0PC，1微信2安卓APP，3IOS APP，4其他',
  `invite_user_name` varchar(100) DEFAULT '' COMMENT '推荐人用户名(投资时)',
  `invite_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '推荐人用户id(投资时)',
  `invite_region_id` int(11) NOT NULL DEFAULT '0',
  `invite_region_name` varchar(100) NOT NULL DEFAULT '' COMMENT '一级部门id(投资时)',
  `invite_branch_id` int(11) NOT NULL DEFAULT '0' COMMENT '二级部门id(投资时)',
  `invite_branch_name` varchar(100) NOT NULL DEFAULT '' COMMENT '二级部门名称(投资时)',
  `invite_department_id` int(11) NOT NULL DEFAULT '0' COMMENT '三级部门id(投资时)',
  `invite_department_name` varchar(100) NOT NULL DEFAULT '' COMMENT '三级部门名称(投资时)',
  `tender_user_attribute` int(11) NOT NULL DEFAULT '0' COMMENT '投资人用户属性',
  `invite_user_attribute` int(11) NOT NULL DEFAULT '0' COMMENT '推荐人用户属性',
  `order_date` varchar(20) NOT NULL DEFAULT '' COMMENT '投资订单日期',
  `coupon_grant_id` int(11) DEFAULT NULL COMMENT '优惠券发放编号（coupon_user.id）',
  `is_bank_tender` int(2) DEFAULT NULL COMMENT '是否江西银行投资: 1 是',
  `tender_from` varchar(8) NOT NULL DEFAULT 'hyjf' COMMENT '投资来源:默认-hyjf,wrb-风车理财',
  `ret_code` varchar(20) DEFAULT NULL COMMENT '银行返回码',
  `ret_msg` varchar(200) DEFAULT NULL COMMENT '银行返回码描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nid` (`nid`),
  KEY `user_id` (`user_id`),
  KEY `borrow_nid` (`borrow_nid`)
) ENGINE=InnoDB AUTO_INCREMENT=2128 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_borrow_tender_tmpinfo
-- ----------------------------
DROP TABLE IF EXISTS `ht_borrow_tender_tmpinfo`;
CREATE TABLE `ht_borrow_tender_tmpinfo` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `ordid` varchar(20) NOT NULL,
  `tmp_array` varchar(254) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2658 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_borrow_user
-- ----------------------------
DROP TABLE IF EXISTS `ht_borrow_user`;
CREATE TABLE `ht_borrow_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `borrow_nid` varchar(50) NOT NULL COMMENT '标的编号',
  `username` varchar(50) NOT NULL COMMENT '企业名称',
  `city` varchar(20) DEFAULT NULL,
  `area` varchar(20) DEFAULT NULL,
  `currency_name` varchar(255) NOT NULL DEFAULT '' COMMENT '注册资本货币单位  元-美元',
  `reg_captial` varchar(20) DEFAULT NULL COMMENT '注册资本',
  `industry` varchar(100) DEFAULT '' COMMENT '行业',
  `litigation` varchar(100) DEFAULT '' COMMENT '涉诉',
  `cre_report` varchar(100) DEFAULT '' COMMENT '征信',
  `credit` int(13) DEFAULT NULL COMMENT '授信额度',
  `staff` int(4) DEFAULT NULL COMMENT '员工人数',
  `other_info` text COMMENT '其他资料',
  `com_reg_time` varchar(30) DEFAULT NULL COMMENT '企业注册时间',
  `legal_person` varchar(20) DEFAULT NULL COMMENT '法人',
  `social_credit_code` varchar(30) DEFAULT NULL COMMENT '统一社会信用代码',
  `regist_code` varchar(30) DEFAULT NULL COMMENT '注册号',
  `main_business` varchar(30) DEFAULT NULL COMMENT '主营业务',
  `overdue_times` varchar(10) DEFAULT NULL COMMENT '在平台逾期次数',
  `overdue_amount` varchar(15) DEFAULT NULL COMMENT '在平台逾期金额',
  `is_certificate` tinyint(1) DEFAULT NULL COMMENT '企贷审核信息 企业证件 0未审核 1已审核',
  `is_operation` tinyint(1) DEFAULT NULL COMMENT '企贷审核信息 经营状况 0未审核 1已审核',
  `is_finance` tinyint(1) DEFAULT NULL COMMENT '企贷审核信息 财务状况 0未审核 1已审核',
  `is_enterprise_creidt` tinyint(1) DEFAULT NULL COMMENT '企贷审核信息 企业信用 0未审核 1已审核',
  `is_legal_person` tinyint(1) DEFAULT NULL COMMENT '企贷审核信息 法人信息 0未审核 1已审核',
  `is_asset` tinyint(1) DEFAULT NULL COMMENT '企贷审核信息 资产状况 0未审核 1已审核',
  `is_purchase_contract` tinyint(1) DEFAULT NULL COMMENT '企贷审核信息 购销合同 0未审核 1已审核',
  `is_supply_contract` varchar(255) DEFAULT NULL COMMENT '企贷审核信息 供销合同 0未审核 1已审核',
  `overdue_report` varchar(30) DEFAULT NULL COMMENT '征信报告逾期情况:暂未提供；无；已处理',
  `debt_situation` varchar(30) DEFAULT NULL COMMENT '重大负债状况:无',
  `other_borrowed` varchar(30) DEFAULT NULL COMMENT '其他平台借款情况:无',
  `is_funds` varchar(30) DEFAULT NULL COMMENT '借款资金运用情况：不正常,正常',
  `is_managed` varchar(30) DEFAULT NULL COMMENT '借款人经营状况及财务状况：不正常,正常',
  `is_ability` varchar(30) DEFAULT NULL COMMENT '借款人还款能力变化情况：不正常,正常',
  `is_overdue` varchar(30) DEFAULT NULL COMMENT '借款人逾期情况：暂无,有',
  `is_complaint` varchar(30) DEFAULT NULL COMMENT '借款人涉诉情况：暂无,有',
  `is_punished` varchar(30) DEFAULT NULL COMMENT '借款人受行政处罚情况：暂无,有',
  `corporate_code` varchar(100) DEFAULT NULL COMMENT '企业组织机构代码',
  `registration_address` varchar(100) DEFAULT NULL COMMENT '企业注册地',
  PRIMARY KEY (`id`),
  KEY `idx_borrow_nid` (`borrow_nid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8 COMMENT='标的企业信息表';

-- ----------------------------
-- Table structure for ht_commission_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_commission_log`;
CREATE TABLE `ht_commission_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `spreads_user_id` int(11) NOT NULL COMMENT '推广人id',
  `nid` varchar(50) NOT NULL COMMENT '标示名',
  `type` varchar(50) NOT NULL COMMENT '类型',
  `spreads_type` varchar(100) NOT NULL COMMENT '推广类型',
  `account_type` varchar(100) NOT NULL COMMENT '资金类型',
  `scales` varchar(50) NOT NULL COMMENT '比例',
  `borrow_nid` varchar(50) NOT NULL COMMENT '借款id',
  `tender_id` int(11) NOT NULL COMMENT '投资id',
  `repay_id` int(11) NOT NULL COMMENT '还款id',
  `account_all` decimal(11,2) NOT NULL COMMENT '操作总金额本息',
  `account_capital` decimal(11,2) NOT NULL COMMENT '本金',
  `account_interest` decimal(11,2) NOT NULL COMMENT '利息',
  `account` decimal(11,2) NOT NULL COMMENT '金额',
  `remark` varchar(1000) NOT NULL COMMENT '备注',
  `create_ip` varchar(15) NOT NULL DEFAULT '',
  `pay_status` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '0:该提成未发放1:该提成已发放',
  `is_valid` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '0:该提成无效1:该提成有效',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8 COMMENT='推荐人提成发放日志表';

-- ----------------------------
-- Table structure for ht_consume
-- ----------------------------
DROP TABLE IF EXISTS `ht_consume`;
CREATE TABLE `ht_consume` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` decimal(11,2) DEFAULT NULL,
  `amount_real` decimal(11,2) DEFAULT NULL,
  `person_num` int(5) DEFAULT NULL,
  `status` tinyint(2) DEFAULT '0' COMMENT '处理状态  0：未处理 1：已处理',
  `time` varchar(20) DEFAULT NULL,
  `insert_time` varchar(20) DEFAULT NULL,
  `person_real` int(5) DEFAULT '0',
  `release` int(2) DEFAULT '0' COMMENT '打包状态 0:未打包 1：已打包',
  `insert_day` varchar(20) DEFAULT NULL,
  `consume_id` varchar(20) DEFAULT NULL COMMENT '资产包编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='汇消费业务';

-- ----------------------------
-- Table structure for ht_consume_list
-- ----------------------------
DROP TABLE IF EXISTS `ht_consume_list`;
CREATE TABLE `ht_consume_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contract_no` varchar(20) DEFAULT NULL,
  `person_name` varchar(20) DEFAULT NULL,
  `ident` varchar(30) DEFAULT NULL,
  `loan_date` varchar(20) DEFAULT NULL,
  `credit_amount` decimal(11,2) DEFAULT NULL,
  `init_pay` int(5) DEFAULT NULL,
  `instalment_num` varchar(5) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `ident_exp` varchar(20) DEFAULT NULL,
  `ident_auth` varchar(100) DEFAULT NULL,
  `company` varchar(100) DEFAULT NULL,
  `bank_name` varchar(100) DEFAULT NULL,
  `account_no` varchar(50) DEFAULT NULL,
  `income` decimal(11,2) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `insert_time` varchar(20) DEFAULT NULL,
  `status` tinyint(2) DEFAULT '0' COMMENT '状态  0：通过 1：不通过',
  `sex` varchar(2) DEFAULT NULL,
  `release` int(2) DEFAULT '0' COMMENT '打包状态 0:未打包 1：已打包',
  `insert_day` varchar(20) DEFAULT NULL,
  `consume_id` varchar(20) DEFAULT NULL COMMENT '资产包编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='汇消费明细';

-- ----------------------------
-- Table structure for ht_coupon_config
-- ----------------------------
DROP TABLE IF EXISTS `ht_coupon_config`;
CREATE TABLE `ht_coupon_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `coupon_code` varchar(50) NOT NULL COMMENT '优惠券编号，显示用',
  `coupon_name` varchar(50) DEFAULT NULL COMMENT '优惠券名称',
  `coupon_quota` decimal(11,2) DEFAULT NULL COMMENT '优惠券额度',
  `coupon_quantity` int(10) DEFAULT NULL COMMENT '发行数量',
  `coupon_profit_time` smallint(5) unsigned DEFAULT NULL COMMENT '收益期限，以天为单位',
  `expiration_type` tinyint(1) unsigned DEFAULT NULL COMMENT '优惠券有效期类别',
  `expiration_date` int(10) DEFAULT NULL COMMENT '优惠券有效期截止日',
  `expiration_length` tinyint(3) unsigned DEFAULT NULL COMMENT '优惠券有效期时长',
  `expiration_length_day` smallint(5) unsigned DEFAULT NULL COMMENT '固定期限时长（日），以日为单位的期限',
  `add_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '是否与本金投资共用，0：共用，1：单独使用',
  `coupon_system` varchar(30) DEFAULT NULL COMMENT '优惠券的使用平台0:全部，1：PC，2：微官网，3：Android，4：IOS',
  `coupon_type` tinyint(1) unsigned DEFAULT NULL COMMENT '优惠券的类别 1：体验金，2：加息券',
  `project_type` varchar(50) DEFAULT NULL COMMENT '优惠券的使用项目类别',
  `project_expiration_type` tinyint(1) unsigned DEFAULT NULL COMMENT '项目期限类别0:不限，1;等于，2：期限范围，3：大于等于，4：小于等于',
  `project_expiration_length` tinyint(3) unsigned DEFAULT NULL COMMENT '项目期限时长  以月为单位',
  `project_expiration_length_min` tinyint(3) unsigned DEFAULT NULL COMMENT '项目期限最短时长',
  `project_expiration_length_max` tinyint(3) unsigned DEFAULT NULL COMMENT '项目期限最长时长',
  `tender_quota_type` tinyint(1) unsigned DEFAULT NULL COMMENT '投资金额类别',
  `tender_quota` int(11) DEFAULT NULL COMMENT '投资金额',
  `tender_quota_min` int(11) DEFAULT NULL COMMENT '投资金额最小额度',
  `tender_quota_max` int(11) DEFAULT NULL COMMENT '投资金额最大额度',
  `content` varchar(300) DEFAULT NULL COMMENT '优惠券描述',
  `status` tinyint(1) unsigned DEFAULT '1' COMMENT '1：待审核，2：已发行，3：审核不通过',
  `audit_content` varchar(300) DEFAULT NULL,
  `audit_user` varchar(20) DEFAULT NULL,
  `audit_time` int(10) DEFAULT NULL,
  `repay_time_config` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '收益还款时间配置： 1 项目到期还款  2 收益期限到期还款',
  `del_flag` tinyint(1) DEFAULT NULL COMMENT '删除标识 0：未删除，1：已删除',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `coupon_code_index` (`coupon_code`)
) ENGINE=InnoDB AUTO_INCREMENT=516 DEFAULT CHARSET=utf8 COMMENT='优惠券配置';

-- ----------------------------
-- Table structure for ht_coupon_operation_history
-- ----------------------------
DROP TABLE IF EXISTS `ht_coupon_operation_history`;
CREATE TABLE `ht_coupon_operation_history` (
  `uuid` varchar(36) NOT NULL,
  `coupon_code` varchar(50) DEFAULT NULL,
  `operation_code` tinyint(1) unsigned DEFAULT NULL COMMENT '1：新增，2：修改，3：删除',
  `operation_content_from` text,
  `operation_content_to` text,
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优惠券操作履历';

-- ----------------------------
-- Table structure for ht_coupon_real_tender
-- ----------------------------
DROP TABLE IF EXISTS `ht_coupon_real_tender`;
CREATE TABLE `ht_coupon_real_tender` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coupon_tender_id` varchar(100) DEFAULT NULL COMMENT '优惠券投资订单编号',
  `real_tender_id` varchar(100) DEFAULT NULL COMMENT '真实投资订单编号',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `del_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标识',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `coupon_tender_id` (`coupon_tender_id`),
  KEY `idx_real_tender_id` (`real_tender_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1307 DEFAULT CHARSET=utf8 COMMENT='本金投资和优惠券投资关联表';

-- ----------------------------
-- Table structure for ht_coupon_recover
-- ----------------------------
DROP TABLE IF EXISTS `ht_coupon_recover`;
CREATE TABLE `ht_coupon_recover` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tender_id` varchar(100) DEFAULT NULL COMMENT '投资订单编号',
  `transfer_id` varchar(100) DEFAULT NULL COMMENT '转账订单编号，每次转账时生成',
  `recover_status` tinyint(1) unsigned DEFAULT NULL COMMENT '还款状态，0：未还款，1：已还款',
  `received_flg` tinyint(1) unsigned DEFAULT NULL COMMENT '收益领取状态，1：未回款，2：未领取，3：转账中,4：转账失败，5：已领取，6：已过期',
  `recover_period` int(2) DEFAULT '1' COMMENT '分期还款的期数，未分期的默认为：1',
  `transfer_time` int(10) DEFAULT NULL COMMENT '转账时间，手动领取收益时，调用汇付接口之前，需更新的时间',
  `recover_time` int(10) DEFAULT NULL COMMENT '应还款时间，放款时生成',
  `recover_yestime` int(10) DEFAULT NULL COMMENT '已还款时间，还款时生成，体验金的场合为用户的手动领取时间',
  `main_recover_yestime` int(10) DEFAULT NULL COMMENT '相关联的真实本金投资的还款时间，用于体验金收益过期判断',
  `recover_interest` decimal(11,2) DEFAULT '0.00' COMMENT '应还利息,放款时生成',
  `recover_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还利息',
  `recover_account` decimal(11,2) DEFAULT '0.00' COMMENT '应还本息',
  `recover_account_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还本息',
  `recover_capital` decimal(11,2) DEFAULT '0.00' COMMENT '应还本金',
  `recover_capital_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还本金',
  `current_recover_flg` tinyint(1) unsigned DEFAULT '0' COMMENT '分期付款的场合，1：还款期，0：非还款期',
  `recover_type` tinyint(1) unsigned DEFAULT '1' COMMENT '还款类别  1：直投类，2：汇添金',
  `notice_flg` tinyint(1) unsigned DEFAULT NULL COMMENT '通知用户标识，0：未通知，1：已通知',
  `exp_time` int(10) DEFAULT NULL COMMENT '收益过期时间',
  `del_flag` tinyint(1) unsigned DEFAULT NULL COMMENT '删除标识  0：未删除，1：已删除',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `tender_id` (`tender_id`),
  KEY `recover_time_idx` (`recover_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1010 DEFAULT CHARSET=utf8 COMMENT='优惠券还款';

-- ----------------------------
-- Table structure for ht_coupon_repay_monitor
-- ----------------------------
DROP TABLE IF EXISTS `ht_coupon_repay_monitor`;
CREATE TABLE `ht_coupon_repay_monitor` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `day` varchar(10) DEFAULT NULL COMMENT '日',
  `week` varchar(5) DEFAULT NULL COMMENT '星期',
  `interest_wait_total` decimal(11,2) DEFAULT NULL COMMENT '待收收益总额',
  `interest_yes_total` decimal(11,2) DEFAULT NULL COMMENT '已收收益总额',
  `del_flag` tinyint(1) unsigned DEFAULT NULL COMMENT '删除标识 0：未删除，1：已删除',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=179 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_coupon_tender
-- ----------------------------
DROP TABLE IF EXISTS `ht_coupon_tender`;
CREATE TABLE `ht_coupon_tender` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增编号',
  `coupon_grant_id` int(11) DEFAULT NULL COMMENT '优惠券发放编号（coupon_user.id）',
  `order_id` varchar(100) DEFAULT NULL COMMENT '投资订单编号',
  `attribute` tinyint(1) unsigned DEFAULT NULL COMMENT '用券时用户属性 0=>无主单 1=>有主单 2=>线下员工 3=>线上员工',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `del_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标识',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `coupon_grant_id` (`coupon_grant_id`),
  KEY `orderId` (`order_id`),
  KEY `add_time_idx` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1307 DEFAULT CHARSET=utf8 COMMENT='优惠券投资关联表';

-- ----------------------------
-- Table structure for ht_coupon_user
-- ----------------------------
DROP TABLE IF EXISTS `ht_coupon_user`;
CREATE TABLE `ht_coupon_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `coupon_code` varchar(50) DEFAULT NULL COMMENT '优惠券编号',
  `coupon_user_code` varchar(50) DEFAULT NULL COMMENT '优惠券用户编号',
  `coupon_group_code` varchar(36) DEFAULT NULL COMMENT '优惠券组编号',
  `user_id` int(11) DEFAULT NULL COMMENT '用户编号',
  `activity_id` int(11) DEFAULT NULL COMMENT '活动编号',
  `coupon_source` int(1) DEFAULT NULL COMMENT '1：手动发放，2：活动发放，3：vip礼包',
  `used_flag` tinyint(1) unsigned DEFAULT NULL COMMENT '优惠券使用标识 0：未使用，0：未使用，1：已使用，2：审核不通过，3：待审核，4：已失效',
  `read_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '0：未读，1：已读',
  `end_time` int(10) DEFAULT NULL COMMENT '使用截止时间',
  `content` varchar(300) DEFAULT NULL,
  `audit_content` varchar(300) DEFAULT NULL COMMENT '审核备注',
  `delete_content` varchar(300) DEFAULT NULL COMMENT '删除时备注',
  `audit_user` varchar(20) DEFAULT NULL COMMENT '审核用户',
  `audit_time` int(10) DEFAULT NULL COMMENT '审核时间',
  `attribute` tinyint(1) unsigned DEFAULT NULL COMMENT '用券时用户属性 0=>无主单 1=>有主单 2=>线下员工 3=>线上员工',
  `channel` varchar(30) DEFAULT '' COMMENT '注册渠道',
  `del_flag` tinyint(1) unsigned DEFAULT NULL COMMENT '删除标识 0：未删除，1：已删除',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `coupon_code` (`coupon_code`),
  KEY `userId` (`user_id`),
  KEY `coupon_user_code` (`coupon_user_code`),
  KEY `add_time_idx` (`create_time`),
  KEY `idx_coupon_source` (`coupon_source`)
) ENGINE=InnoDB AUTO_INCREMENT=10192 DEFAULT CHARSET=utf8 COMMENT='优惠券用户';

-- ----------------------------
-- Table structure for ht_credit_repay
-- ----------------------------
DROP TABLE IF EXISTS `ht_credit_repay`;
CREATE TABLE `ht_credit_repay` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '承接人用户ID',
  `user_name` varchar(30) NOT NULL COMMENT '承接人用户名',
  `credit_user_id` int(11) DEFAULT NULL COMMENT '出让人id',
  `credit_user_name` varchar(30) NOT NULL COMMENT '出让人用户名',
  `status` tinyint(2) unsigned DEFAULT '0' COMMENT '状态',
  `bid_nid` varchar(30) NOT NULL COMMENT '原标标号',
  `credit_nid` varchar(30) NOT NULL DEFAULT '0' COMMENT '债转标号',
  `credit_tender_nid` varchar(30) NOT NULL COMMENT '债转投标单号',
  `assign_nid` varchar(30) NOT NULL COMMENT '认购单号',
  `auth_code` varchar(30) DEFAULT NULL COMMENT '投资人投标成功的授权号',
  `assign_capital` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '应还本金',
  `assign_account` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '应还总额',
  `assign_interest` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '应还利息',
  `assign_interest_advance` decimal(11,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '垫付利息',
  `assign_price` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '购买价格',
  `assign_pay` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '支付金额',
  `assign_repay_account` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已还总额',
  `assign_repay_capital` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已还本金',
  `assign_repay_interest` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已还利息',
  `assign_repay_end_time` int(11) NOT NULL DEFAULT '0' COMMENT '最后还款日',
  `assign_repay_last_time` int(11) NOT NULL DEFAULT '0' COMMENT '上次还款时间',
  `assign_repay_next_time` int(11) NOT NULL DEFAULT '0' COMMENT '下次还款时间',
  `assign_repay_yes_time` int(11) NOT NULL DEFAULT '0' COMMENT '最终实际还款时间',
  `assign_repay_period` int(11) NOT NULL DEFAULT '1' COMMENT '还款期数',
  `assign_create_date` int(11) NOT NULL DEFAULT '0' COMMENT '认购日期',
  `client` int(11) DEFAULT NULL COMMENT '客户端',
  `recover_period` int(11) NOT NULL DEFAULT '1' COMMENT '原标还款期数',
  `manage_fee` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '管理费',
  `credit_repay_order_id` varchar(50) DEFAULT NULL COMMENT '债转还款订单号',
  `credit_repay_order_date` varchar(20) DEFAULT NULL COMMENT '债转还款订单日期',
  `advance_status` tinyint(2) unsigned DEFAULT '0' COMMENT '债转提前还款状态 0正常还款 1提前还款 2延期还款 3逾期还款',
  `charge_days` int(11) DEFAULT '0' COMMENT '提前天数',
  `charge_interest` decimal(11,2) DEFAULT NULL COMMENT '提前还款利息（负数）(已加罚息)',
  `delay_days` int(11) DEFAULT '0' COMMENT '延期天数',
  `delay_interest` decimal(10,2) DEFAULT '0.00' COMMENT '延期利息',
  `late_days` int(11) DEFAULT '0' COMMENT '逾期天数',
  `late_interest` decimal(11,2) DEFAULT '0.00' COMMENT '逾期利息',
  `debt_status` tinyint(1) DEFAULT '0' COMMENT '债权是否结束状态(0:否,1:是)',
  `add_ip` varchar(15) DEFAULT NULL COMMENT 'create ip',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `charge_penalty_interest` decimal(11,2) DEFAULT '0.00' COMMENT '提前还款罚息',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `credit_nid` (`credit_nid`),
  KEY `idx_bid_nid` (`bid_nid`),
  KEY `idx_assign_nid` (`assign_nid`)
) ENGINE=InnoDB AUTO_INCREMENT=305 DEFAULT CHARSET=utf8 COMMENT='债转还款表';

-- ----------------------------
-- Table structure for ht_credit_repay_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_credit_repay_log`;
CREATE TABLE `ht_credit_repay_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `credit_nid` int(11) unsigned NOT NULL DEFAULT '0',
  `error_code` text,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='债转还款日志表';

-- ----------------------------
-- Table structure for ht_credit_tender
-- ----------------------------
DROP TABLE IF EXISTS `ht_credit_tender`;
CREATE TABLE `ht_credit_tender` (
  `assign_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '承接人用户ID',
  `user_name` varchar(30) NOT NULL COMMENT '承接人用户名',
  `credit_user_id` int(11) DEFAULT NULL COMMENT '出让人用户ID',
  `credit_user_name` varchar(30) NOT NULL COMMENT '出让人用户名',
  `status` tinyint(2) unsigned DEFAULT '0' COMMENT '状态',
  `bid_nid` varchar(30) NOT NULL COMMENT '原标标号',
  `borrow_user_id` int(11) NOT NULL COMMENT '标的借款人用户ID',
  `borrow_user_name` varchar(30) NOT NULL COMMENT '标的借款人用户名',
  `credit_nid` varchar(30) NOT NULL DEFAULT '0' COMMENT '债转标号',
  `credit_tender_nid` varchar(30) NOT NULL COMMENT '债转投标单号',
  `assign_nid` varchar(30) NOT NULL COMMENT '认购单号',
  `auth_code` varchar(30) DEFAULT NULL COMMENT '投资人投标成功的授权号',
  `assign_capital` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '投资本金',
  `assign_account` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '回收总额',
  `assign_interest` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '债转利息',
  `assign_interest_advance` decimal(11,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '垫付利息',
  `assign_price` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '购买价格',
  `assign_pay` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '支付金额',
  `assign_repay_account` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已还总额',
  `assign_repay_capital` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已还本金',
  `assign_repay_interest` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已还利息',
  `assign_repay_end_time` int(11) NOT NULL DEFAULT '0' COMMENT '最后还款日',
  `assign_repay_last_time` int(11) NOT NULL DEFAULT '0' COMMENT '上次还款时间',
  `assign_repay_next_time` int(11) NOT NULL DEFAULT '0' COMMENT '下次还款时间',
  `assign_repay_yes_time` int(11) NOT NULL DEFAULT '0' COMMENT '最终实际还款时间',
  `assign_repay_period` int(11) NOT NULL DEFAULT '1' COMMENT '还款期数',
  `assign_create_date` int(11) NOT NULL DEFAULT '0' COMMENT '认购日期',
  `credit_fee` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '服务费',
  `invite_user_name` varchar(100) DEFAULT NULL COMMENT '承接人的推荐人用户名',
  `invite_user_attribute` tinyint(2) unsigned DEFAULT '0' COMMENT '承接人的推荐人用户属性',
  `invite_user_regionname` varchar(100) DEFAULT NULL COMMENT '承接人的推荐人部门信息',
  `invite_user_branchname` varchar(100) DEFAULT NULL COMMENT '承接人的推荐人部门信息',
  `invite_user_departmentname` varchar(100) DEFAULT NULL COMMENT '团队(投资时)',
  `invite_user_credit_name` varchar(100) DEFAULT NULL COMMENT '出让人的推荐人用户名',
  `invite_user_credit_attribute` tinyint(2) unsigned DEFAULT '0' COMMENT '出让人的推荐人属性',
  `invite_user_credit_regionname` varchar(100) DEFAULT NULL COMMENT '出让人的推荐人用户部门信息',
  `invite_user_credit_branchname` varchar(100) DEFAULT NULL COMMENT '出让人的推荐人用户部门信息',
  `invite_user_credit_departmentname` varchar(100) DEFAULT NULL COMMENT '出让人的推荐人用户部门信息',
  `client` int(11) DEFAULT NULL COMMENT '客户端',
  `recover_period` int(11) NOT NULL DEFAULT '0' COMMENT '已还款期数',
  `add_ip` varchar(15) DEFAULT NULL COMMENT 'create ip',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`assign_id`),
  UNIQUE KEY `assign_nid` (`assign_nid`),
  KEY `user_id` (`user_id`),
  KEY `credit_nid` (`credit_nid`),
  KEY `credit_tender_nid` (`credit_tender_nid`),
  KEY `idx_credit_user_id` (`credit_user_id`) USING BTREE,
  KEY `idx_add_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=296 DEFAULT CHARSET=utf8 COMMENT='债权投资表';

-- ----------------------------
-- Table structure for ht_credit_tender_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_credit_tender_log`;
CREATE TABLE `ht_credit_tender_log` (
  `assign_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户名称',
  `user_name` varchar(30) DEFAULT NULL COMMENT '承接人用户名',
  `credit_user_id` int(11) DEFAULT NULL COMMENT '出让人id',
  `credit_user_name` varchar(30) DEFAULT NULL COMMENT '出让人用户名',
  `bid_nid` varchar(30) NOT NULL COMMENT '原标标号',
  `borrow_user_id` int(11) DEFAULT NULL COMMENT '借款人用户ID',
  `borrow_user_name` varchar(30) DEFAULT NULL COMMENT '借款人用户名',
  `credit_nid` varchar(30) NOT NULL DEFAULT '0' COMMENT '债转标号',
  `credit_tender_nid` varchar(30) NOT NULL COMMENT '债转投标单号',
  `assign_nid` varchar(30) NOT NULL COMMENT '认购单号',
  `assign_capital` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '投资本金',
  `assign_account` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '回收总额',
  `assign_interest` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '债转利息',
  `assign_interest_advance` decimal(11,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '垫付利息',
  `assign_price` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '购买价格',
  `assign_pay` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '支付金额',
  `assign_repay_account` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已还总额',
  `assign_repay_capital` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已还本金',
  `assign_repay_interest` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已还利息',
  `assign_repay_end_time` int(11) NOT NULL DEFAULT '0' COMMENT '最后还款日',
  `assign_repay_last_time` int(11) NOT NULL DEFAULT '0' COMMENT '上次还款时间',
  `assign_repay_next_time` int(11) NOT NULL DEFAULT '0' COMMENT '下次还款时间',
  `assign_repay_yes_time` int(11) NOT NULL DEFAULT '0' COMMENT '最终实际还款时间',
  `assign_repay_period` int(11) NOT NULL DEFAULT '1' COMMENT '还款期数',
  `assign_create_date` int(11) NOT NULL DEFAULT '0' COMMENT '认购日期',
  `credit_fee` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '服务费',
  `client` int(11) DEFAULT NULL COMMENT '客户端',
  `status` tinyint(2) unsigned DEFAULT '0' COMMENT '状态',
  `log_order_id` varchar(30) DEFAULT NULL COMMENT '购买债权订单号',
  `tx_date` int(10) DEFAULT '0' COMMENT '交易日期',
  `tx_time` int(10) DEFAULT '0' COMMENT '交易时间',
  `seq_no` int(50) DEFAULT '0' COMMENT '交易流水号',
  `bank_seq_no` varchar(50) DEFAULT NULL COMMENT '交易日期+交易时间+交易流水号',
  `account_id` varchar(50) DEFAULT NULL COMMENT '银行电子账号',
  `add_ip` varchar(15) DEFAULT NULL COMMENT 'create ip',
  `ret_code` varchar(20) DEFAULT NULL COMMENT '银行返回码',
  `ret_msg` varchar(200) DEFAULT NULL COMMENT '银行返回码描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`assign_id`),
  UNIQUE KEY `assign_nid` (`assign_nid`),
  KEY `user_id` (`user_id`),
  KEY `credit_nid` (`credit_nid`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=453 DEFAULT CHARSET=utf8 COMMENT='债转投资日志表';

-- ----------------------------
-- Table structure for ht_debt_accede_commission
-- ----------------------------
DROP TABLE IF EXISTS `ht_debt_accede_commission`;
CREATE TABLE `ht_debt_accede_commission` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `plan_nid` varchar(50) NOT NULL COMMENT '计划编号',
  `plan_lock_period` int(10) DEFAULT NULL COMMENT '计划期限即计划锁定期限',
  `order_id` varchar(30) DEFAULT NULL COMMENT '充值订单号',
  `user_id` int(10) DEFAULT NULL COMMENT '获得提成的userId',
  `user_name` varchar(50) DEFAULT NULL COMMENT '提成人用户名',
  `is51` tinyint(1) DEFAULT '0' COMMENT '是否是51老用户(0:否,1:是)',
  `region_id` int(11) DEFAULT '0' COMMENT '地区ID',
  `region_name` varchar(30) DEFAULT NULL COMMENT '地区名',
  `branch_id` int(11) DEFAULT '0' COMMENT '分公司ID',
  `branch_name` varchar(30) DEFAULT NULL COMMENT '分公司名',
  `department_id` int(5) DEFAULT NULL COMMENT '获得提成的部门id',
  `department_name` varchar(30) DEFAULT NULL COMMENT '部门名',
  `accede_order_id` varchar(50) NOT NULL COMMENT '计划加入订单号',
  `accede_user_id` int(10) DEFAULT NULL COMMENT '加入用户userId',
  `accede_user_name` varchar(50) DEFAULT NULL COMMENT '加入用户名',
  `accede_department_id` int(5) DEFAULT NULL COMMENT '加入人的部门id',
  `accede_account` decimal(11,2) DEFAULT NULL COMMENT '加入金额',
  `accede_time` int(11) DEFAULT NULL COMMENT '加入时间',
  `commission` decimal(11,2) DEFAULT NULL COMMENT '提成',
  `status` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '0:未发放;1:已发放;100:删除',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `compute_time` int(11) DEFAULT NULL COMMENT '计算时间',
  `return_time` int(11) DEFAULT NULL COMMENT '返现时间',
  `return_user_id` int(10) DEFAULT NULL COMMENT '返现操作用户ID',
  `return_user_name` varchar(50) DEFAULT NULL COMMENT '返现操作用户名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='汇添金提成明细表';

-- ----------------------------
-- Table structure for ht_debt_account_list
-- ----------------------------
DROP TABLE IF EXISTS `ht_debt_account_list`;
CREATE TABLE `ht_debt_account_list` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `nid` varchar(100) NOT NULL COMMENT '交易凭证号',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `user_name` varchar(20) NOT NULL COMMENT '用户名',
  `referer_user_id` int(11) DEFAULT NULL COMMENT '推荐人用户userId',
  `referer_user_name` varchar(20) DEFAULT NULL COMMENT '推荐人用户名',
  `plan_nid` varchar(30) DEFAULT NULL COMMENT '计划nid',
  `plan_order_id` varchar(30) DEFAULT NULL COMMENT '计划订单号',
  `plan_order_balance` decimal(11,2) DEFAULT '0.00' COMMENT '计划订单余额',
  `plan_order_frost` decimal(11,2) DEFAULT '0.00' COMMENT '计划订单冻结金额',
  `plan_balance` decimal(11,2) DEFAULT '0.00' COMMENT '计划账户余额',
  `plan_frost` decimal(11,2) DEFAULT '0.00' COMMENT '计划账户冻结金额',
  `amount` decimal(11,2) DEFAULT '0.00' COMMENT '操作金额',
  `type` tinyint(1) DEFAULT NULL COMMENT '收支类型1收入2支出3冻结4解冻',
  `trade` varchar(50) DEFAULT NULL COMMENT '交易类型',
  `trade_code` varchar(50) DEFAULT NULL COMMENT '操作识别码 balance余额操作 frost冻结操作 await待收操作',
  `total` decimal(11,2) DEFAULT '0.00' COMMENT '资金总额(可用+冻结+待收)',
  `balance` decimal(11,2) DEFAULT '0.00' COMMENT '可用金额',
  `frost` decimal(11,2) DEFAULT '0.00' COMMENT '冻结金额',
  `account_wait` decimal(11,2) DEFAULT '0.00' COMMENT '待收总金额',
  `capital_wait` decimal(11,2) DEFAULT '0.00' COMMENT '待收本金',
  `interest_wait` decimal(11,2) DEFAULT '0.00' COMMENT '待还收益',
  `repay_wait` decimal(11,2) DEFAULT '0.00' COMMENT '待还金额',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `ip` varchar(15) DEFAULT NULL COMMENT '操作IP',
  `web` tinyint(1) DEFAULT '0' COMMENT '网站收支计算标识',
  `create_user_id` int(11) NOT NULL COMMENT '创建用户id',
  `create_user_name` varchar(20) NOT NULL COMMENT '创建用户名',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户名',
  `update_user_name` varchar(20) DEFAULT NULL COMMENT '更新用户名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `type` (`type`),
  KEY `trade` (`trade`),
  KEY `nid` (`nid`),
  KEY `create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='汇添金计划交易明细表（中间的资金操作）huiyingdai_account_list';

-- ----------------------------
-- Table structure for ht_debt_apicron
-- ----------------------------
DROP TABLE IF EXISTS `ht_debt_apicron`;
CREATE TABLE `ht_debt_apicron` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nid` varchar(50) DEFAULT NULL COMMENT '唯一标识',
  `plan_nid` varchar(30) DEFAULT NULL COMMENT '计划编号',
  `user_id` int(11) DEFAULT NULL COMMENT '借款人用户id',
  `user_name` varchar(20) DEFAULT NULL COMMENT '借款人用户名',
  `borrow_nid` varchar(20) DEFAULT NULL COMMENT '项目编号',
  `period_now` int(11) NOT NULL DEFAULT '1' COMMENT '当前还款期数',
  `data` text COMMENT '错误信息',
  `status` tinyint(1) DEFAULT '0' COMMENT '放款状态0未完成1已完成2放款执行中9放款失败',
  `api_type` tinyint(1) DEFAULT '0' COMMENT '0放款1还款2清算',
  `web_status` tinyint(1) DEFAULT '0' COMMENT '计算状态',
  `repay_status` tinyint(1) DEFAULT '0' COMMENT '还款状态0未完成1已完成',
  `credit_repay_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '债转还款状态',
  `liquidates_status` tinyint(1) DEFAULT NULL COMMENT '清算状态 0待清算 1已清算 9清算失败',
  `create_user_id` int(11) DEFAULT NULL COMMENT '创建用户id',
  `create_user_name` varchar(20) DEFAULT NULL COMMENT '创建用户名',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户名',
  `update_user_name` varchar(20) DEFAULT NULL COMMENT '更新用户名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `borrow_nid` (`borrow_nid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='汇添金专属借款API执行任务';

-- ----------------------------
-- Table structure for ht_debt_bail
-- ----------------------------
DROP TABLE IF EXISTS `ht_debt_bail`;
CREATE TABLE `ht_debt_bail` (
  `id` int(15) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `borrow_nid` varchar(50) DEFAULT NULL COMMENT '项目编号',
  `borrow_uid` int(11) DEFAULT '0',
  `operater_uid` int(11) DEFAULT '0' COMMENT '更新用户',
  `bail_num` decimal(15,2) DEFAULT '0.00' COMMENT '保证金',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='汇添金专属项目保证金';

-- ----------------------------
-- Table structure for ht_debt_borrow
-- ----------------------------
DROP TABLE IF EXISTS `ht_debt_borrow`;
CREATE TABLE `ht_debt_borrow` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT '0' COMMENT '用户名称',
  `applicant` varchar(20) DEFAULT NULL COMMENT '项目申请人',
  `repay_org_name` varchar(30) DEFAULT NULL COMMENT '担保机构用户名',
  `is_repay_org_flag` tinyint(1) DEFAULT '0' COMMENT '是否可用担保机构还款(0:否,1:是)',
  `repay_org_user_id` int(11) DEFAULT '0' COMMENT '担保机构用户ID',
  `name` varchar(255) DEFAULT NULL COMMENT '标题',
  `status` tinyint(2) unsigned DEFAULT '0' COMMENT '状态',
  `order` int(11) DEFAULT '0' COMMENT '排序',
  `borrow_pic` varchar(200) NOT NULL,
  `hits` int(11) DEFAULT '0' COMMENT '点击次数',
  `comment_count` int(11) NOT NULL DEFAULT '0',
  `litpic` varchar(255) DEFAULT NULL COMMENT '缩略图',
  `flag` varchar(50) DEFAULT NULL COMMENT '属性',
  `type` varchar(50) NOT NULL DEFAULT '1' COMMENT '1房贷2车贷',
  `view_type` varchar(50) NOT NULL,
  `add_ip` varchar(15) DEFAULT NULL COMMENT 'create ip',
  `amount_account` decimal(11,2) NOT NULL COMMENT '冻结的额度',
  `amount_type` varchar(100) NOT NULL COMMENT '额度类型',
  `cash_status` tinyint(2) unsigned NOT NULL,
  `account` decimal(11,2) DEFAULT '0.00' COMMENT '借贷总金额',
  `other_web_status` tinyint(2) unsigned NOT NULL,
  `account_contents` longtext NOT NULL COMMENT '财务信息',
  `borrow_type` varchar(100) NOT NULL COMMENT '借款类型',
  `borrow_password` varchar(100) NOT NULL COMMENT '借款密码',
  `borrow_flag` varchar(100) NOT NULL COMMENT '借款种类',
  `borrow_status` int(2) NOT NULL COMMENT '是否可以进行借款',
  `borrow_full_status` tinyint(2) unsigned NOT NULL COMMENT '满标审核状态',
  `borrow_nid` varchar(50) DEFAULT NULL COMMENT '借款的识别名',
  `borrow_pre_nid` int(7) unsigned NOT NULL DEFAULT '0',
  `borrow_account_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已借到的金额',
  `borrow_account_wait` decimal(11,2) NOT NULL DEFAULT '0.00',
  `borrow_account_scale` decimal(11,2) DEFAULT '0.00' COMMENT '借贷的完成率',
  `borrow_use` varchar(100) DEFAULT '0' COMMENT '用途',
  `borrow_style` varchar(100) DEFAULT '0' COMMENT '还款方式',
  `borrow_period` int(10) DEFAULT '0' COMMENT '借款期限',
  `borrow_period_roam` int(11) NOT NULL COMMENT '流转标的期数',
  `borrow_day` int(11) NOT NULL,
  `borrow_apr` decimal(10,2) DEFAULT '0.00' COMMENT '借款利率',
  `borrow_contents` text COMMENT '借款的详情',
  `borrow_frost_account` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '借款冻结金额',
  `borrow_frost_scale` varchar(50) NOT NULL COMMENT '冻结资金比例',
  `borrow_frost_second` decimal(11,2) NOT NULL COMMENT '秒标复审冻结',
  `borrow_valid_time` int(20) DEFAULT '0' COMMENT '借款有效时间',
  `borrow_success_time` int(20) DEFAULT '0' COMMENT '借款成功时间',
  `borrow_end_time` varchar(100) NOT NULL COMMENT '借款到期时间',
  `borrow_part_status` tinyint(2) unsigned NOT NULL COMMENT '是否部分借款',
  `borrow_upfiles` longtext NOT NULL COMMENT '上传的附件',
  `cancel_userid` int(11) NOT NULL COMMENT '撤销用户',
  `cancel_status` tinyint(2) unsigned NOT NULL COMMENT '是否撤销',
  `cancel_time` varchar(50) NOT NULL COMMENT '撤回时间',
  `cancel_remark` varchar(200) NOT NULL COMMENT '撤销理由',
  `cancel_contents` varchar(200) NOT NULL COMMENT '撤销管理备注',
  `tender_account_min` int(11) DEFAULT '0' COMMENT '最小的投资额',
  `tender_account_max` int(11) DEFAULT '0' COMMENT '最大的投资额',
  `tender_times` int(11) DEFAULT '0' COMMENT '投标的次数',
  `tender_last_time` varchar(50) NOT NULL COMMENT '最后投资时间',
  `repay_advance_status` tinyint(2) unsigned NOT NULL COMMENT '是否提前还款',
  `repay_advance_time` varchar(50) NOT NULL COMMENT '提前还款时间',
  `repay_advance_step` tinyint(2) unsigned NOT NULL COMMENT '提前还款阶段',
  `repay_account_all` decimal(11,2) DEFAULT '0.00' COMMENT '应还款总额',
  `repay_account_interest` decimal(11,2) DEFAULT '0.00' COMMENT '总还款利息',
  `repay_account_capital` decimal(11,2) DEFAULT '0.00' COMMENT '总还款本金',
  `repay_account_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还款总额',
  `repay_account_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还款利息',
  `repay_account_capital_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还款本金',
  `repay_account_wait` decimal(11,2) DEFAULT '0.00' COMMENT '未还款总额',
  `repay_account_interest_wait` decimal(11,2) DEFAULT '0.00' COMMENT '未还款利息',
  `repay_account_capital_wait` decimal(11,2) DEFAULT '0.00' COMMENT '未还款本金',
  `repay_account_times` int(5) DEFAULT '0' COMMENT '还款的次数',
  `repay_month_account` int(5) DEFAULT '0' COMMENT '每月还款金额',
  `repay_last_time` varchar(50) NOT NULL COMMENT '最后还款时间',
  `repay_each_time` varchar(250) DEFAULT '' COMMENT '每次还款的时间',
  `repay_next_time` int(20) DEFAULT '0' COMMENT '下一笔还款时间',
  `repay_next_account` decimal(11,2) DEFAULT '0.00' COMMENT '下一笔还款金额',
  `repay_times` int(11) NOT NULL COMMENT '还款次数',
  `repay_full_status` tinyint(2) unsigned NOT NULL COMMENT '是否已经还完',
  `repay_fee_normal` decimal(11,2) NOT NULL COMMENT '正常还款费用',
  `repay_fee_advance` decimal(11,2) NOT NULL COMMENT '提前还款费用',
  `repay_fee_late` decimal(11,2) NOT NULL COMMENT '逾期还款费用',
  `late_interest` decimal(11,2) NOT NULL COMMENT '逾期利息',
  `late_forfeit` decimal(11,2) NOT NULL COMMENT '逾期催缴费',
  `vouch_status` tinyint(2) unsigned NOT NULL COMMENT '是否是担保',
  `vouch_advance_status` tinyint(2) unsigned NOT NULL DEFAULT '0',
  `vouch_user_status` tinyint(2) unsigned NOT NULL COMMENT '担保人担保状态',
  `vouch_users` varchar(100) NOT NULL COMMENT '担保人列表',
  `vouch_account` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '总担保的金额',
  `vouch_account_yes` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已担保的金额',
  `vouch_account_wait` decimal(11,2) NOT NULL DEFAULT '0.00',
  `vouch_account_scale` decimal(11,0) NOT NULL DEFAULT '0' COMMENT '已担保的比例',
  `vouch_times` int(5) NOT NULL DEFAULT '0' COMMENT '担保次数',
  `vouch_award_status` int(2) NOT NULL DEFAULT '0' COMMENT '是否设置担保奖励',
  `vouch_award_scale` decimal(11,2) NOT NULL COMMENT '担保比例',
  `vouch_award_account` decimal(11,2) DEFAULT '0.00' COMMENT '总付出的担保奖励',
  `voucher_name` varchar(100) NOT NULL,
  `voucher_lianxi` varchar(100) NOT NULL,
  `voucher_att` varchar(100) NOT NULL,
  `vouchjg_name` varchar(100) NOT NULL,
  `vouchjg_lianxi` varchar(100) NOT NULL,
  `vouchjg_js` varchar(100) NOT NULL,
  `vouchjg_xy` varchar(100) NOT NULL,
  `fast_status` tinyint(2) unsigned NOT NULL,
  `vouchstatus` tinyint(2) unsigned NOT NULL,
  `group_status` tinyint(2) unsigned NOT NULL COMMENT '1圈内;0全见',
  `group_id` int(30) NOT NULL COMMENT '圈子编号',
  `award_status` tinyint(2) unsigned DEFAULT '0' COMMENT '是否奖励',
  `award_false` tinyint(2) unsigned DEFAULT '0' COMMENT '投资失败是否也奖励',
  `award_account` decimal(11,2) DEFAULT NULL COMMENT '奖励金额',
  `award_scale` decimal(11,2) DEFAULT NULL COMMENT '按比例奖励',
  `award_account_all` decimal(11,2) DEFAULT '0.00' COMMENT '投标奖励总额',
  `open_account` tinyint(2) unsigned DEFAULT '0' COMMENT '公开我的帐户资金情况',
  `open_borrow` tinyint(2) unsigned DEFAULT '0' COMMENT '公开我的借款资金情况',
  `open_tender` tinyint(2) unsigned DEFAULT '0' COMMENT '公开我的投标资金情况',
  `open_credit` tinyint(2) unsigned DEFAULT '0' COMMENT '公开我的信用额度情况',
  `comment_staus` tinyint(2) unsigned NOT NULL COMMENT '是否可以评论',
  `comment_times` int(11) NOT NULL COMMENT '评论次数',
  `comment_usertype` varchar(50) NOT NULL COMMENT '可评论的用户',
  `diya_contents` text NOT NULL,
  `borrow_pawn_app` varchar(50) NOT NULL,
  `borrow_pawn_app_url` varchar(100) NOT NULL,
  `borrow_pawn_auth` varchar(50) NOT NULL,
  `borrow_pawn_auth_url` varchar(100) NOT NULL,
  `borrow_pawn_formalities` varchar(50) NOT NULL,
  `borrow_pawn_formalities_url` varchar(100) NOT NULL,
  `borrow_pawn_type` varchar(50) NOT NULL,
  `borrow_pawn_time` varchar(50) NOT NULL,
  `borrow_pawn_description` text NOT NULL,
  `borrow_pawn_value` varchar(50) NOT NULL,
  `borrow_pawn_xin` varchar(50) NOT NULL,
  `order_top` varchar(50) NOT NULL COMMENT '置顶时间',
  `verify_userid` varchar(11) DEFAULT '0' COMMENT '审核人',
  `verify_time` varchar(50) DEFAULT '0' COMMENT '审核时间',
  `verify_remark` varchar(255) DEFAULT '',
  `verify_contents` varchar(250) NOT NULL COMMENT '审核备注',
  `verify_status` int(11) NOT NULL,
  `reverify_userid` varchar(11) DEFAULT '0' COMMENT '审核人',
  `reverify_time` varchar(50) DEFAULT '0' COMMENT '审核时间',
  `reverify_remark` varchar(255) DEFAULT '',
  `reverify_status` int(11) NOT NULL,
  `reverify_contents` varchar(250) NOT NULL COMMENT '审核复审标注',
  `upfiles_id` varchar(230) NOT NULL COMMENT ' 发标上传图片',
  `borrow_running_use` text COMMENT '资金运转-用途',
  `borrow_running_soruce` text COMMENT '资金运转-来源',
  `borrow_measures_instit` text COMMENT '风险控制措施-机构',
  `borrow_measures_mort` text COMMENT '风险控制措施-抵押物',
  `borrow_measures_mea` text COMMENT '险控制措施-措施',
  `borrow_analysis_policy` text COMMENT '政策及市场分析-政策支持',
  `borrow_analysis_market` text COMMENT '政策及市场分析-市场分析',
  `borrow_company` text COMMENT '企业背景',
  `borrow_company_scope` text COMMENT '企业信息-营业范围',
  `borrow_company_business` text COMMENT '企业信息-经营状况',
  `xmupfiles_id` varchar(300) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `dyupfiles_id` varchar(300) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `files` text COMMENT '项目资料',
  `guarantee_type` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '担保方式0抵押+担保  1抵押 2担保',
  `project_type` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '0汇保贷 1汇典贷 2汇小贷 3汇车贷 4新手标',
  `ontime` int(11) DEFAULT '0' COMMENT '定时发标',
  `service_fee_rate` varchar(10) NOT NULL DEFAULT '0.0002' COMMENT '服务费费率',
  `manage_fee_rate` varchar(13) DEFAULT '0.0001' COMMENT '管理费费率',
  `differential_rate` varchar(13) DEFAULT '0.0000' COMMENT '收益差率',
  `can_transaction_pc` varchar(3) DEFAULT NULL COMMENT '可投资平台_PC',
  `can_transaction_wei` varchar(3) DEFAULT NULL COMMENT '可投资平台_微网站',
  `can_transaction_ios` varchar(3) DEFAULT NULL COMMENT '可投资平台_IOS',
  `can_transaction_android` varchar(3) DEFAULT NULL COMMENT '可投资平台_Android',
  `operation_label` varchar(3) DEFAULT NULL COMMENT '运营标签',
  `borrow_company_instruction` text COMMENT '机构介绍',
  `borrow_operating_process` text COMMENT '操作流程',
  `company_or_personal` tinyint(1) unsigned DEFAULT NULL COMMENT '借款人信息 借款类型 1：公司 2：个人',
  `borrow_manager` varchar(100) DEFAULT NULL COMMENT '融资服务费',
  `borrow_service` varchar(100) DEFAULT NULL COMMENT '账户管理费',
  `borrow_manager_scale_end` varchar(255) DEFAULT '' COMMENT '账户管理费率下线',
  `borrow_full_time` int(11) DEFAULT NULL COMMENT '满标时间',
  `recover_last_time` int(11) DEFAULT NULL COMMENT '最后一笔的放款完成时间',
  `consume_id` varchar(20) DEFAULT NULL COMMENT '资产包编号',
  `verify_over_time` int(11) DEFAULT '0' COMMENT '初审通过时间',
  `borrow_user_name` varchar(100) DEFAULT NULL COMMENT '借款人人名',
  `disposal_price_estimate` varchar(50) DEFAULT '' COMMENT '售价预估    ',
  `disposal_period` varchar(50) DEFAULT '' COMMENT '处置周期    ',
  `disposal_channel` varchar(50) DEFAULT '' COMMENT '处置渠道    ',
  `disposal_result` varchar(2000) DEFAULT '' COMMENT '处置结果预案',
  `disposal_note` varchar(2000) DEFAULT '' COMMENT '备注说明    ',
  `disposal_project_name` varchar(100) DEFAULT '' COMMENT '项目名称    ',
  `disposal_project_type` varchar(100) DEFAULT '' COMMENT '项目类型    ',
  `disposal_area` varchar(200) DEFAULT '' COMMENT '所在地区    ',
  `disposal_predictive_value` varchar(20) DEFAULT '' COMMENT '预估价值    ',
  `disposal_ownership_category` varchar(20) DEFAULT '' COMMENT '权属类别    ',
  `disposal_asset_origin` varchar(20) DEFAULT '' COMMENT '资产成因    ',
  `disposal_attachment_info` varchar(2000) DEFAULT '' COMMENT '附件信息    ',
  `borrow_increase_money` decimal(10,0) DEFAULT NULL COMMENT '递增金额',
  `borrow_interest_coupon` tinyint(2) unsigned DEFAULT NULL COMMENT '优惠券',
  `borrow_taste_money` tinyint(2) unsigned DEFAULT NULL COMMENT '体验金',
  `bank_input_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '标的是否已录入银行托管 0未托管 1已托管 王坤添加',
  `booking_begin_time` int(11) DEFAULT NULL COMMENT '预约开始时间',
  `booking_end_time` int(11) DEFAULT NULL COMMENT '预约截止时间',
  `booking_status` tinyint(1) unsigned DEFAULT '0' COMMENT '预约状态 0初始 1预约中 2预约结束',
  `borrow_account_wait_appoint` decimal(20,2) DEFAULT '0.00' COMMENT '预约标等待预约金额',
  `borrow_account_scale_appoint` decimal(10,2) DEFAULT '0.00' COMMENT '预约进度',
  `borrow_plan_selected` tinyint(1) DEFAULT '0' COMMENT '是否已有计划关联:0:否,1:是',
  `borrow_repay_web_advance` decimal(10,2) DEFAULT '0.00' COMMENT '前台还款扣除的金额',
  `borrow_level` varchar(10) DEFAULT NULL COMMENT '借款人评级',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `type` (`type`),
  KEY `flag` (`flag`),
  KEY `name` (`name`),
  KEY `status` (`status`),
  KEY `borrow_apr` (`borrow_apr`),
  KEY `borrow_nid` (`borrow_nid`),
  KEY `project_type` (`project_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='汇添金专属项目';

-- ----------------------------
-- Table structure for ht_debt_car_info
-- ----------------------------
DROP TABLE IF EXISTS `ht_debt_car_info`;
CREATE TABLE `ht_debt_car_info` (
  `id` int(15) unsigned NOT NULL AUTO_INCREMENT,
  `brand` varchar(40) DEFAULT NULL COMMENT '品牌',
  `model` varchar(50) DEFAULT NULL COMMENT '型号',
  `carseries` varchar(50) DEFAULT NULL COMMENT '车系',
  `number` varchar(12) DEFAULT NULL COMMENT '车牌号',
  `color` varchar(16) DEFAULT NULL COMMENT '颜色',
  `year` varchar(12) DEFAULT NULL COMMENT '出厂年份',
  `place` varchar(60) DEFAULT NULL COMMENT '产地',
  `volume` varchar(16) DEFAULT NULL COMMENT '排量',
  `buytime` int(15) DEFAULT NULL COMMENT '购买日期',
  `is_safe` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '1有保险2无保险',
  `price` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '购买价',
  `toprice` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '评估价',
  `borrow_nid` varchar(50) DEFAULT NULL COMMENT '借款编号',
  `borrow_pre_nid` int(7) NOT NULL DEFAULT '0' COMMENT '借款预编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='汇添金专属项目车辆抵押信息';

-- ----------------------------
-- Table structure for ht_debt_commission_config
-- ----------------------------
DROP TABLE IF EXISTS `ht_debt_commission_config`;
CREATE TABLE `ht_debt_commission_config` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `commission_type` tinyint(1) unsigned NOT NULL COMMENT '提成类型:0:普通提成,1:超额提成',
  `user_type` tinyint(1) unsigned NOT NULL COMMENT '用户类型:0:线上员工',
  `rate` varchar(10) NOT NULL COMMENT '费率',
  `status` tinyint(1) unsigned NOT NULL COMMENT '状态 0关闭  1启用',
  `remark` varchar(500) DEFAULT NULL COMMENT '说明',
  `del_flag` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '删除状态 1:删除  0:未删除',
  `create_user_id` int(10) NOT NULL COMMENT '创建人',
  `create_user_name` varchar(20) NOT NULL DEFAULT '' COMMENT '创建人用户名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='计划提成配置表';

-- ----------------------------
-- Table structure for ht_debt_company_authen
-- ----------------------------
DROP TABLE IF EXISTS `ht_debt_company_authen`;
CREATE TABLE `ht_debt_company_authen` (
  `id` int(15) unsigned NOT NULL AUTO_INCREMENT,
  `authen_name` varchar(255) DEFAULT NULL COMMENT '认证项目名称',
  `authen_time` varchar(20) DEFAULT NULL COMMENT '认证时间',
  `authen_sort_key` int(2) DEFAULT NULL COMMENT '展示顺序',
  `borrow_nid` varchar(50) DEFAULT NULL COMMENT '借款编号',
  `borrow_pre_nid` int(7) DEFAULT '0' COMMENT '借款预编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='汇添金专属项目文件认证';

-- ----------------------------
-- Table structure for ht_debt_company_info
-- ----------------------------
DROP TABLE IF EXISTS `ht_debt_company_info`;
CREATE TABLE `ht_debt_company_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `borrow_nid` varchar(50) NOT NULL COMMENT '借款编号',
  `borrow_pre_nid` int(7) NOT NULL DEFAULT '0' COMMENT '借款预编号',
  `username` varchar(50) NOT NULL COMMENT '用户id',
  `province` varchar(20) DEFAULT NULL COMMENT '公司所在地:省',
  `city` varchar(20) DEFAULT NULL COMMENT '公司所在地:市',
  `area` varchar(20) DEFAULT NULL COMMENT '公司所在地:区',
  `reg_captial` int(11) NOT NULL COMMENT '注册资本',
  `industry` varchar(100) DEFAULT '' COMMENT '行业',
  `litigation` varchar(100) DEFAULT '' COMMENT '涉诉',
  `cre_report` varchar(100) DEFAULT '' COMMENT '征信',
  `credit` int(13) DEFAULT NULL COMMENT '信用额度',
  `staff` int(4) DEFAULT NULL COMMENT '员工人数',
  `other_info` text COMMENT '其他资料',
  `com_reg_time` varchar(30) DEFAULT NULL COMMENT '企业注册时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='汇添金专属项目企业信息';

-- ----------------------------
-- Table structure for ht_debt_credit
-- ----------------------------
DROP TABLE IF EXISTS `ht_debt_credit`;
CREATE TABLE `ht_debt_credit` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL COMMENT '债转用户userId',
  `user_name` varchar(20) NOT NULL COMMENT '债转用户用户名',
  `plan_nid` varchar(30) NOT NULL COMMENT '汇添金计划nid',
  `plan_order_id` varchar(30) NOT NULL COMMENT '汇添金加入订单号',
  `borrow_nid` varchar(30) NOT NULL COMMENT '汇天金专属标的标号',
  `borrow_name` varchar(255) NOT NULL COMMENT '汇天金专属标的标题',
  `borrow_apr` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '原标年化利率',
  `actual_apr` decimal(11,2) NOT NULL COMMENT '清算后债权实际年华收益率（清算时计算，可能会影响计划发布）',
  `invest_order_id` varchar(30) DEFAULT NULL COMMENT '原始标的投资订单号',
  `sell_order_id` varchar(30) NOT NULL COMMENT '原投资订单号',
  `credit_nid` varchar(30) NOT NULL COMMENT '债转编号nid',
  `credit_status` int(2) NOT NULL DEFAULT '0' COMMENT '转让状态，0.承接中，1.承接停止，2完全承接',
  `repay_status` int(2) DEFAULT '0' COMMENT '债转还款状态 0 未还款 1还款中 2还款完成',
  `is_liquidates` tinyint(1) DEFAULT '0' COMMENT '是否已清算(0:未清算,1:已清算)',
  `credit_order` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `hold_days` int(11) DEFAULT '0' COMMENT '持有天数',
  `remain_days` int(11) DEFAULT '0' COMMENT '剩余天数',
  `assign_period` int(11) DEFAULT '0' COMMENT '承接原项目所在期数',
  `liquidates_period` int(11) DEFAULT '0' COMMENT '清算时所在期数',
  `credit_period` int(11) NOT NULL DEFAULT '1' COMMENT '债转期数',
  `repay_period` int(11) NOT NULL DEFAULT '0' COMMENT '已还款期数',
  `credit_term` int(11) NOT NULL COMMENT '债转期限',
  `liquidation_fair_value` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '清算时公允价值',
  `liquidates_capital` decimal(11,2) DEFAULT '0.00' COMMENT '清算总本金',
  `credit_account` decimal(11,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '债转总额',
  `credit_capital` decimal(11,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '债转总本金',
  `credit_interest` decimal(11,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '债转总利息',
  `credit_interest_advance` decimal(11,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '垫付总利息',
  `credit_delay_interest` decimal(11,2) DEFAULT '0.00' COMMENT '清算时延期利息',
  `credit_late_interest` decimal(11,2) DEFAULT '0.00' COMMENT '清算时逾期利息',
  `credit_account_assigned` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已承接总金额（此金额为本金加利息与还款相关，同垫付利息无关）',
  `credit_capital_assigned` decimal(11,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '已承接本金',
  `credit_interest_assigned` decimal(11,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '已承接待还总利息',
  `credit_interest_advance_assigned` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已承接垫付总利息',
  `credit_delay_interest_assigned` decimal(11,2) DEFAULT '0.00' COMMENT '承接已垫付的延期利息',
  `credit_late_interest_assigned` decimal(11,2) DEFAULT '0.00' COMMENT '承接已垫付的逾期利息',
  `credit_account_wait` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '待承接总金额',
  `credit_capital_wait` decimal(11,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '待承接本金',
  `credit_interest_wait` decimal(11,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '待承接利息',
  `credit_interest_advance_wait` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已承接垫付总利息',
  `credit_discount` decimal(11,1) unsigned NOT NULL DEFAULT '0.0' COMMENT '折价率',
  `credit_income` decimal(11,2) unsigned NOT NULL COMMENT '总收入，本金+垫付利息',
  `credit_service_fee` decimal(11,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '服务费',
  `credit_price` decimal(11,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '出让价格',
  `repay_account` decimal(11,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '已还款总额',
  `repay_capital` decimal(11,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '已还本金',
  `repay_interest` decimal(11,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '已还利息',
  `repay_account_wait` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '待还款本息',
  `repay_capital_wait` decimal(11,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '待还款本金',
  `repay_interest_wait` decimal(11,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '待还款利息',
  `credit_repay_end_time` int(11) NOT NULL COMMENT '债转计划最后还款时间',
  `credit_repay_last_time` int(11) NOT NULL DEFAULT '0' COMMENT '上次还款时间',
  `credit_repay_next_time` int(11) NOT NULL DEFAULT '0' COMMENT '下次还款时间',
  `credit_repay_yes_time` int(11) NOT NULL DEFAULT '0' COMMENT '最终实际还款时间',
  `end_time` int(11) DEFAULT '0' COMMENT '债转结束时间（全部承接或者提前还款导致）',
  `assign_num` int(11) DEFAULT '0' COMMENT '承接次数',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标识 0 未删除 1已删除',
  `client` int(2) NOT NULL DEFAULT '0' COMMENT '债转发起客户端,0PC,1微官网,2Android,3iOS,4其他',
  `create_user_id` int(11) NOT NULL COMMENT '创建用户id',
  `create_user_name` varchar(20) NOT NULL COMMENT '创建用户名',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户id',
  `update_user_name` varchar(20) DEFAULT NULL COMMENT '更新用户名',
  `source_type` tinyint(1) DEFAULT '1' COMMENT '是否原始债权 0非原始 1原始',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `credit_nid` (`credit_nid`) COMMENT '债转id',
  KEY `bid_nid` (`borrow_nid`) COMMENT '项目原标nid',
  KEY `invest_nid` (`sell_order_id`) COMMENT '投资订单号',
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='清算数据债券表 等同于相应的borrow_credit表';

-- ----------------------------
-- Table structure for ht_debt_credit_repay
-- ----------------------------
DROP TABLE IF EXISTS `ht_debt_credit_repay`;
CREATE TABLE `ht_debt_credit_repay` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '承接用户id',
  `user_name` varchar(20) NOT NULL COMMENT '承接用户名',
  `credit_user_id` int(11) NOT NULL COMMENT '出让人id',
  `credit_user_name` varchar(20) NOT NULL COMMENT '出让人用户名',
  `invest_order_id` varchar(30) DEFAULT NULL COMMENT '原始标的投资订单号',
  `sell_order_id` varchar(30) NOT NULL COMMENT '债转原始订单号',
  `repay_status` int(2) NOT NULL DEFAULT '0' COMMENT '还款状态 0未还款 1已还款',
  `borrow_nid` varchar(30) NOT NULL COMMENT '原标标号',
  `repay_period` int(11) NOT NULL DEFAULT '1' COMMENT '原标还款期数',
  `credit_nid` varchar(30) NOT NULL COMMENT '债转标号',
  `assign_plan_nid` varchar(30) DEFAULT NULL COMMENT '承接计划nid',
  `assign_plan_order_id` varchar(30) DEFAULT NULL COMMENT '承接计划订单号',
  `assign_order_id` varchar(30) NOT NULL COMMENT '债转承接订单号',
  `repay_account` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '应还总额',
  `repay_capital` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '应还本金',
  `repay_interest` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '应还利息',
  `repay_account_wait` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '待还总额',
  `repay_capital_wait` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '待还本金',
  `repay_interest_wait` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '待还利息',
  `repay_account_yes` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已还总额',
  `repay_capital_yes` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已还本金',
  `repay_interest_yes` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已还利息',
  `assign_repay_end_time` int(11) NOT NULL DEFAULT '0' COMMENT '最后还款日',
  `assign_repay_last_time` int(11) NOT NULL DEFAULT '0' COMMENT '上次还款时间',
  `assign_repay_next_time` int(11) NOT NULL DEFAULT '0' COMMENT '下次还款时间',
  `assign_repay_yes_time` int(11) NOT NULL DEFAULT '0' COMMENT '最终实际还款时间',
  `assign_repay_time` int(11) DEFAULT '0' COMMENT '还款时间',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '是否删除 0未删除 1已删除',
  `assign_repay_period` int(11) NOT NULL DEFAULT '1' COMMENT '债权还款期数',
  `assign_create_date` int(11) NOT NULL DEFAULT '0' COMMENT '认购日期',
  `add_ip` varchar(20) DEFAULT NULL COMMENT 'create ip',
  `client` int(11) DEFAULT NULL COMMENT '承接客户端 0PC,1微官网,2Android,3iOS,4其他',
  `manage_fee` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '管理费',
  `liquidates_service_fee` decimal(11,2) DEFAULT '0.00' COMMENT '清算时收取的服务费',
  `unique_nid` varchar(60) NOT NULL DEFAULT '0' COMMENT '唯一nid',
  `credit_repay_order_id` varchar(50) DEFAULT NULL COMMENT '债转还款订单号',
  `credit_repay_order_date` varchar(20) DEFAULT NULL COMMENT '债转还款订单日期',
  `advance_status` int(2) NOT NULL COMMENT '是否提前还款 0正常还款 1提前还款 2延期还款 3逾期还款',
  `advance_days` int(11) DEFAULT '0' COMMENT '提前还款天数',
  `repay_advance_interest` decimal(11,2) DEFAULT '0.00' COMMENT '借款人提前还款利息',
  `late_days` int(11) DEFAULT '0' COMMENT '逾期天数',
  `repay_late_interest` decimal(11,2) DEFAULT '0.00' COMMENT '借款人还款逾期利息',
  `delay_days` int(11) DEFAULT '0' COMMENT '延期天数',
  `repay_delay_interest` decimal(11,2) DEFAULT '0.00' COMMENT '借款人还款延期利息',
  `receive_account_yes` decimal(11,2) DEFAULT '0.00' COMMENT '投资人收到的还款本息',
  `receive_capital_yes` decimal(11,2) DEFAULT '0.00' COMMENT '投资人收到的还款本金',
  `receive_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '投资人收到的还款利息',
  `receive_advance_interest` decimal(11,2) DEFAULT '0.00' COMMENT '投资人收取提前还款利息',
  `receive_late_interest` decimal(11,2) DEFAULT '0.00' COMMENT '投资人收取逾期利息',
  `receive_delay_interest` decimal(11,2) DEFAULT '0.00' COMMENT '投资人收取延期利息',
  `receive_advance_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '投资人已收取提前还款利息',
  `receive_late_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '投资人已收取逾期利息',
  `receive_delay_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '投资人已收取延期利息',
  `create_user_id` int(11) NOT NULL COMMENT '创建用户id',
  `create_user_name` varchar(20) NOT NULL COMMENT '创建用户名',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户名',
  `update_user_name` varchar(20) DEFAULT NULL COMMENT '更新用户名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_nid` (`unique_nid`) COMMENT '唯一nid',
  KEY `user_id` (`user_id`),
  KEY `credit_nid` (`credit_nid`),
  KEY `IDX_BID_NID_RECOVER_PERIOD` (`assign_order_id`,`borrow_nid`,`repay_period`),
  KEY `credit_user_id` (`credit_user_id`) COMMENT '出让人用户id',
  KEY `credit_tender_nid` (`sell_order_id`) COMMENT '原标投资订单号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='汇添金债权还款表，相当于borrow_credit_repay 表';

-- ----------------------------
-- Table structure for ht_debt_credit_tender
-- ----------------------------
DROP TABLE IF EXISTS `ht_debt_credit_tender`;
CREATE TABLE `ht_debt_credit_tender` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '承接用户userId',
  `user_name` varchar(20) NOT NULL COMMENT '承接用户名',
  `credit_user_id` int(11) NOT NULL COMMENT '出让人userId',
  `credit_user_name` varchar(20) DEFAULT NULL COMMENT '出让人用户名',
  `borrow_nid` varchar(30) NOT NULL COMMENT '原标标号',
  `repay_period` int(11) NOT NULL DEFAULT '0' COMMENT '原标的已还款期数',
  `credit_nid` varchar(30) NOT NULL COMMENT '债转标号',
  `invest_order_id` varchar(30) DEFAULT NULL COMMENT '原始标的投资订单号',
  `sell_order_id` varchar(30) NOT NULL COMMENT '原标投资订单号',
  `liquidates_plan_nid` varchar(30) NOT NULL COMMENT '清算债权计划编号',
  `liquidates_plan_order_id` varchar(30) NOT NULL COMMENT '清算债权计划计划加入订单号',
  `assign_plan_nid` varchar(30) NOT NULL COMMENT '承接的计划编号nid',
  `assign_plan_order_id` varchar(30) NOT NULL COMMENT '承接的计划订单号',
  `assign_order_id` varchar(30) NOT NULL COMMENT '承接订单号',
  `assign_order_date` varchar(20) NOT NULL COMMENT '承接日期',
  `assign_capital` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '承接本金',
  `assign_account` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '承接总额',
  `assign_interest` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '承接利息',
  `assign_repay_delay_interest` decimal(11,2) DEFAULT '0.00' COMMENT '承接的延期利息',
  `assign_repay_late_interest` decimal(11,2) DEFAULT '0.00' COMMENT '承接的逾期利息',
  `assign_interest_advance` decimal(11,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '承接垫付利息',
  `assign_price` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '购买价格',
  `assign_pay` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '支付金额',
  `repay_account_yes` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已还总额',
  `repay_capital_yes` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已还本金',
  `repay_interest_yes` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已还利息',
  `repay_account_wait` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '待还总额',
  `repay_capital_wait` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '待还本金',
  `repay_interest_wait` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '待还利息',
  `assign_repay_end_time` int(11) NOT NULL DEFAULT '0' COMMENT '最后还款日',
  `assign_repay_last_time` int(11) NOT NULL DEFAULT '0' COMMENT '上次还款时间',
  `assign_repay_next_time` int(11) NOT NULL DEFAULT '0' COMMENT '下次还款时间',
  `assign_repay_yes_time` int(11) NOT NULL DEFAULT '0' COMMENT '最终实际还款时间',
  `assign_repay_period` int(11) NOT NULL DEFAULT '1' COMMENT '债权承接期数',
  `del_flag` tinyint(1) DEFAULT '1' COMMENT '是否有效 0无效 1有效',
  `assign_type` tinyint(1) DEFAULT '0' COMMENT '承接类型 0 自动承接 1手动承接',
  `status` int(2) DEFAULT '0' COMMENT '状态 0未还款 1已还款',
  `assign_service_fee` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '服务费',
  `add_ip` varchar(20) DEFAULT NULL COMMENT 'create ip',
  `client` int(2) DEFAULT '0' COMMENT '客户端,0PC,1微官网,2Android,3iOS,4其他',
  `web` tinyint(4) NOT NULL DEFAULT '0' COMMENT '服务费收支',
  `create_user_id` int(11) NOT NULL COMMENT '创建用户id',
  `create_user_name` varchar(20) NOT NULL COMMENT '创建用户名',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户名',
  `update_user_name` varchar(20) DEFAULT NULL COMMENT '更新用户名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `assign_nid` (`assign_order_id`) COMMENT '承接订单号',
  KEY `credit_nid` (`credit_nid`) COMMENT '债转编号',
  KEY `credit_tender_nid` (`sell_order_id`) COMMENT '债转原标投资订单号',
  KEY `user_id` (`user_id`) COMMENT '承接用户id',
  KEY `credit_user_id` (`credit_user_id`) COMMENT '债转出让人用户id',
  KEY `bid_nid` (`borrow_nid`) COMMENT '原标标号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='汇添金债权承接表相当于borrow_credit_tender';

-- ----------------------------
-- Table structure for ht_debt_credit_tender_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_debt_credit_tender_log`;
CREATE TABLE `ht_debt_credit_tender_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '承接用户userId',
  `user_name` varchar(20) NOT NULL COMMENT '承接用户名',
  `credit_user_id` int(11) DEFAULT NULL COMMENT '出让人userId',
  `credit_user_name` varchar(20) DEFAULT NULL COMMENT '出让人用户名',
  `status` int(2) DEFAULT '0' COMMENT '状态 0初始 1成功 2失败',
  `borrow_nid` varchar(30) NOT NULL COMMENT '原标标号',
  `repay_period` int(11) NOT NULL DEFAULT '0' COMMENT '原标的已还款期数',
  `sell_order_id` varchar(30) NOT NULL COMMENT '原标投资订单号',
  `credit_nid` varchar(30) NOT NULL COMMENT '债转标号',
  `invest_order_id` varchar(30) DEFAULT NULL COMMENT '原始标的投资订单号',
  `liquidates_plan_nid` varchar(30) NOT NULL COMMENT '清算的计划nid',
  `liquidates_plan_order_id` varchar(30) NOT NULL COMMENT '清算的计划订单号',
  `assign_plan_nid` varchar(30) DEFAULT NULL COMMENT '承接计划nid',
  `assign_plan_order_id` varchar(30) DEFAULT NULL COMMENT '承接计划加入订单号',
  `assign_order_id` varchar(30) NOT NULL COMMENT '承接订单号',
  `assign_order_date` varchar(20) NOT NULL COMMENT '承接日期',
  `assign_capital` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '承接本金',
  `assign_account` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '承接总额',
  `assign_interest` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '承接利息',
  `assign_repay_delay_interest` decimal(11,2) DEFAULT '0.00' COMMENT '承接的延期利息',
  `assign_repay_late_interest` decimal(11,2) DEFAULT '0.00' COMMENT '承接的逾期利息',
  `assign_interest_advance` decimal(11,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '承接垫付利息',
  `assign_price` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '购买价格',
  `assign_pay` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '支付金额',
  `assign_repay_account` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已还总额',
  `assign_repay_capital` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已还本金',
  `assign_repay_interest` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已还利息',
  `assign_repay_end_time` int(11) NOT NULL DEFAULT '0' COMMENT '最后还款日',
  `assign_repay_last_time` int(11) NOT NULL DEFAULT '0' COMMENT '上次还款时间',
  `assign_repay_next_time` int(11) NOT NULL DEFAULT '0' COMMENT '下次还款时间',
  `assign_repay_yes_time` int(11) NOT NULL DEFAULT '0' COMMENT '最终实际还款时间',
  `assign_repay_period` int(11) NOT NULL DEFAULT '1' COMMENT '债权承接期数',
  `assign_service_fee` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '服务费',
  `assign_type` tinyint(1) DEFAULT '0' COMMENT '承接类型 0 自动承接 1手动承接',
  `add_ip` varchar(20) DEFAULT NULL COMMENT 'create ip',
  `client` int(11) DEFAULT NULL COMMENT '客户端',
  `create_user_id` int(11) NOT NULL COMMENT '创建用户userId',
  `create_user_name` varchar(20) NOT NULL COMMENT '创建用户名',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户userId',
  `update_user_name` varchar(20) DEFAULT NULL COMMENT '更新用户名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `assign_nid` (`assign_order_id`),
  KEY `credit_tender_nid` (`sell_order_id`) COMMENT '原标投资订单号',
  KEY `user_id` (`user_id`) COMMENT '承接用户id',
  KEY `credit_nid` (`credit_nid`) COMMENT '债转编号',
  KEY `credit_user_id` (`credit_user_id`) COMMENT '原标投资用户id',
  KEY `bid_nid` (`borrow_nid`) COMMENT '原标标号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='汇添金债权承接log表，相当于borrow_credit_tender表';

-- ----------------------------
-- Table structure for ht_debt_delete_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_debt_delete_log`;
CREATE TABLE `ht_debt_delete_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `borrow_nid` varchar(50) DEFAULT '' COMMENT '项目编号',
  `borrow_name` varchar(255) DEFAULT '' COMMENT '项目标题',
  `username` varchar(100) DEFAULT '' COMMENT '借款人人名',
  `account` varchar(20) DEFAULT '0' COMMENT '借贷总金额',
  `borrow_account_yes` varchar(11) DEFAULT '0' COMMENT '已借到的金额',
  `borrow_account_wait` varchar(11) DEFAULT '0' COMMENT '剩余金额',
  `borrow_account_scale` varchar(10) DEFAULT '0' COMMENT '借贷的完成率',
  `borrow_style` varchar(100) DEFAULT '' COMMENT '还款方式',
  `borrow_style_name` varchar(50) DEFAULT '' COMMENT '名称',
  `project_type` tinyint(1) DEFAULT '0' COMMENT '0汇保贷 1汇典贷 2汇小贷 3汇车贷 4新手标',
  `project_type_name` varchar(100) DEFAULT '' COMMENT '名称',
  `borrow_period` varchar(10) DEFAULT '' COMMENT '借款期限',
  `borrow_apr` varchar(11) DEFAULT '0' COMMENT '借款利率',
  `status` varchar(10) DEFAULT '' COMMENT '项目状态',
  `borrow_full_time` varchar(25) DEFAULT '' COMMENT '满标时间',
  `recover_last_time` varchar(25) DEFAULT '' COMMENT '放款完成时间',
  `bail_num` decimal(15,0) DEFAULT '0' COMMENT '保证金',
  `operater_uid` int(11) DEFAULT NULL COMMENT '操作人',
  `operater_user` varchar(50) DEFAULT '' COMMENT '操作人名称',
  `operater_time` int(11) DEFAULT NULL COMMENT '操作时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `name` (`borrow_name`),
  KEY `borrow_nid` (`borrow_nid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='汇添金专属项目删除历史';

-- ----------------------------
-- Table structure for ht_debt_detail
-- ----------------------------
DROP TABLE IF EXISTS `ht_debt_detail`;
CREATE TABLE `ht_debt_detail` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(11) NOT NULL COMMENT '投资用户userId',
  `user_name` varchar(20) NOT NULL COMMENT '投资用户名',
  `borrow_user_id` int(11) NOT NULL COMMENT '借款用户userId',
  `borrow_user_name` varchar(20) NOT NULL COMMENT '借款者用户名',
  `borrow_nid` varchar(50) NOT NULL COMMENT '项目标号',
  `borrow_name` varchar(255) NOT NULL DEFAULT '' COMMENT '原标标题',
  `borrow_apr` decimal(6,2) NOT NULL DEFAULT '0.00' COMMENT '原标年化利率',
  `borrow_period` int(10) NOT NULL DEFAULT '0' COMMENT '借款期限',
  `borrow_style` varchar(100) NOT NULL DEFAULT '' COMMENT '借款类型',
  `plan_nid` varchar(30) NOT NULL COMMENT '计划编号nid',
  `plan_order_id` varchar(30) NOT NULL COMMENT '计划加入订单号',
  `invest_order_id` varchar(30) DEFAULT '' COMMENT '原始标的投资订单号',
  `order_id` varchar(30) DEFAULT NULL COMMENT '计划债权订单号',
  `credit_nid` varchar(30) DEFAULT '' COMMENT '上次债转编号',
  `order_date` varchar(30) DEFAULT NULL COMMENT '计划订单日期',
  `order_type` tinyint(1) DEFAULT NULL COMMENT '订单类型 0 汇添金专属项目投资 1 债权承接',
  `source_type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否原始债权 0非原始 1原始',
  `account` decimal(11,2) DEFAULT '0.00' COMMENT '投资金额或者债权承接金额',
  `loan_time` int(11) NOT NULL DEFAULT '0' COMMENT '放款时间',
  `loan_capital` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '放款本金（应还本金）',
  `loan_interest` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '放款利息（应还利息）',
  `repay_period` tinyint(2) NOT NULL DEFAULT '1' COMMENT '还款期数',
  `repay_time` int(11) NOT NULL COMMENT '估计还款时间',
  `repay_action_time` int(11) NOT NULL COMMENT '实际还款时间',
  `repay_capital_yes` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已还本金',
  `repay_interest_yes` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已还利息',
  `repay_capital_wait` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '未收本金',
  `repay_interest_wait` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '未收利息',
  `repay_status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '还款状态 0未还款 1已还款',
  `manage_fee` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '账户管理费',
  `service_fee` decimal(11,2) DEFAULT '0.00' COMMENT '债权收取的服务费',
  `advance_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否提前还款 0正常还款 1提前还款 2延期还款 3逾期还款',
  `advance_days` int(11) NOT NULL DEFAULT '0' COMMENT '提前还款天数',
  `advance_interest` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '提前还款利息(负数）',
  `late_days` int(11) NOT NULL DEFAULT '0' COMMENT '逾期天数',
  `late_interest` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '逾期利息',
  `late_interest_assigned` decimal(11,2) DEFAULT '0.00' COMMENT '承接垫付的逾期利息',
  `delay_days` int(11) NOT NULL DEFAULT '0' COMMENT '延期天数',
  `delay_interest` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '延期利息',
  `delay_interest_assigned` decimal(11,2) DEFAULT '0.00' COMMENT '承接垫付的延期利息',
  `repay_order_id` varchar(20) DEFAULT NULL COMMENT '还款订单号',
  `repay_order_date` varchar(20) DEFAULT NULL COMMENT '还款订单日期',
  `expire_fair_value` decimal(11,2) DEFAULT '0.00' COMMENT '到期公允价值',
  `last_liquidation_time` int(11) DEFAULT '0' COMMENT '债权上次被清算时间',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '债权是否有效（0失效 1有效）',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '清算标识 0未清算 1清算',
  `client` tinyint(1) DEFAULT '0' COMMENT '客户端0PC，1微信2安卓APP，3IOS APP，4其他',
  `remark` varchar(300) DEFAULT NULL COMMENT '备注',
  `add_ip` varchar(20) DEFAULT NULL COMMENT 'create ip',
  `create_user_id` int(11) NOT NULL COMMENT '创建用户id',
  `create_user_name` varchar(20) NOT NULL COMMENT '创建用户名',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户userId',
  `update_user_name` varchar(20) DEFAULT NULL COMMENT '更新用户名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `plan_nid` (`plan_nid`),
  KEY `borrow_nid` (`borrow_nid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='债权详情表';

-- ----------------------------
-- Table structure for ht_debt_freeze
-- ----------------------------
DROP TABLE IF EXISTS `ht_debt_freeze`;
CREATE TABLE `ht_debt_freeze` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL COMMENT '用户userId',
  `user_name` varchar(20) NOT NULL COMMENT '用户名',
  `user_cust_id` varchar(20) NOT NULL COMMENT '用户汇付客户号',
  `plan_nid` varchar(30) NOT NULL COMMENT '计划编号',
  `plan_order_id` varchar(30) NOT NULL COMMENT '计划加入订单号',
  `borrow_nid` varchar(20) DEFAULT NULL COMMENT '项目编号',
  `order_id` varchar(20) NOT NULL COMMENT '冻结订单号',
  `order_date` varchar(20) NOT NULL COMMENT '冻结订单日期',
  `trx_id` varchar(20) NOT NULL COMMENT '冻结标识',
  `unfreeze_order_id` varchar(20) DEFAULT NULL COMMENT '解冻订单号',
  `unfreeze_order_date` varchar(20) DEFAULT NULL COMMENT '解冻订单日期',
  `amount` decimal(10,2) DEFAULT '0.00' COMMENT '冻结金额',
  `status` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '冻结订单状态 0冻结 1解冻',
  `freeze_type` tinyint(1) NOT NULL COMMENT '冻结类型 0投资冻结 1汇添金冻结 2债权承接冻结 3还款冻结',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '是否有效 0有效 1无效记录',
  `create_user_id` int(11) NOT NULL COMMENT '创建用户userId',
  `create_user_name` varchar(20) NOT NULL COMMENT '创建用户名',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户id',
  `update_user_name` varchar(20) DEFAULT NULL COMMENT '更新用户名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `ordid` (`order_id`),
  KEY `borrow_nid` (`borrow_nid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='汇添金计划冻结表';

-- ----------------------------
-- Table structure for ht_debt_freeze_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_debt_freeze_log`;
CREATE TABLE `ht_debt_freeze_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL COMMENT '用户userId',
  `user_name` varchar(20) NOT NULL COMMENT '用户名',
  `user_cust_id` varchar(20) NOT NULL COMMENT '用户汇付客户号',
  `plan_nid` varchar(30) NOT NULL COMMENT '计划编号',
  `plan_order_id` varchar(30) NOT NULL COMMENT '计划加入订单号',
  `borrow_nid` varchar(20) DEFAULT NULL COMMENT '项目编号',
  `order_id` varchar(20) NOT NULL COMMENT '冻结订单号',
  `order_date` varchar(20) NOT NULL COMMENT '冻结订单日期',
  `trx_id` varchar(20) DEFAULT NULL COMMENT '冻结标识',
  `amount` decimal(10,2) DEFAULT '0.00' COMMENT '冻结金额',
  `status` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '冻结操作状态  0初始 1成功',
  `freeze_type` tinyint(1) NOT NULL COMMENT '冻结类型 0投资冻结 ',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '是否有效 0有效 1无效记录',
  `create_user_id` int(11) NOT NULL COMMENT '创建用户userId',
  `create_user_name` varchar(20) NOT NULL COMMENT '创建用户名',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户userid',
  `update_user_name` varchar(20) DEFAULT NULL COMMENT '更新用户名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `ordid` (`order_id`),
  KEY `borrow_nid` (`borrow_nid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='汇添金计划冻结日志';

-- ----------------------------
-- Table structure for ht_debt_house_info
-- ----------------------------
DROP TABLE IF EXISTS `ht_debt_house_info`;
CREATE TABLE `ht_debt_house_info` (
  `id` int(15) unsigned NOT NULL AUTO_INCREMENT,
  `houses_type` varchar(2) DEFAULT NULL COMMENT '房产类型',
  `houses_location` varchar(255) DEFAULT NULL COMMENT '房产位置',
  `houses_area` varchar(255) DEFAULT NULL COMMENT '建筑面积',
  `houses_price` varchar(20) DEFAULT NULL COMMENT '市值',
  `houses_toprice` varchar(20) DEFAULT NULL COMMENT '抵押价值（元）',
  `borrow_nid` varchar(50) DEFAULT NULL COMMENT '借款编号',
  `borrow_pre_nid` int(7) DEFAULT '0' COMMENT '借款预编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='汇添金专属项目房屋抵押信息';

-- ----------------------------
-- Table structure for ht_debt_invest
-- ----------------------------
DROP TABLE IF EXISTS `ht_debt_invest`;
CREATE TABLE `ht_debt_invest` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户userId',
  `user_name` varchar(20) NOT NULL COMMENT '投资用户名',
  `user_attribute` int(11) NOT NULL DEFAULT '0' COMMENT '投资用户属性',
  `plan_nid` varchar(30) DEFAULT NULL COMMENT '计划nid',
  `plan_order_id` varchar(30) DEFAULT NULL COMMENT '计划订单号',
  `borrow_nid` varchar(50) NOT NULL COMMENT '项目编号',
  `order_id` varchar(100) NOT NULL COMMENT '投资订单号',
  `order_date` varchar(20) NOT NULL COMMENT '投资订单日期',
  `trx_id` varchar(20) NOT NULL DEFAULT '' COMMENT '冻结标识',
  `account` decimal(11,2) DEFAULT NULL COMMENT '投资金额',
  `loan_amount` decimal(11,2) DEFAULT '0.00' COMMENT '放款金额',
  `loan_fee` decimal(11,2) DEFAULT '0.00' COMMENT '服务费',
  `repay_account` decimal(11,2) DEFAULT '0.00' COMMENT '应还总额',
  `repay_capital` decimal(11,2) DEFAULT '0.00' COMMENT '应还本金',
  `repay_interest` decimal(11,2) DEFAULT '0.00' COMMENT '应还利息',
  `repay_account_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还总额',
  `repay_capital_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还本金',
  `repay_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还利息',
  `repay_account_wait` decimal(11,2) DEFAULT '0.00' COMMENT '待还总额',
  `repay_capital_wait` decimal(11,2) DEFAULT '0.00' COMMENT '待还本金',
  `repay_interest_wait` decimal(11,2) DEFAULT '0.00' COMMENT '待还利息',
  `invest_type` tinyint(1) DEFAULT '0' COMMENT '投资类型 0手动投标 1预约投标 2自动投标',
  `repay_times` int(10) DEFAULT '0' COMMENT '已还款次数',
  `invite_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '推荐人用户userId',
  `invite_user_name` varchar(100) DEFAULT NULL COMMENT '推荐人用户名',
  `invite_user_attribute` int(11) NOT NULL DEFAULT '0' COMMENT '推荐人用户属性',
  `invite_region_id` int(11) NOT NULL DEFAULT '0',
  `invite_region_name` varchar(100) NOT NULL DEFAULT ' ',
  `invite_branch_id` int(11) NOT NULL DEFAULT '0',
  `invite_branch_name` varchar(100) NOT NULL DEFAULT ' ',
  `invite_department_id` int(11) NOT NULL DEFAULT '0',
  `invite_department_name` varchar(100) NOT NULL DEFAULT ' ',
  `loan_order_id` varchar(20) DEFAULT '' COMMENT '放款订单号',
  `loan_order_date` varchar(20) DEFAULT '' COMMENT '放款日期',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '投资状态 0冻结成功 1放款成功 2还款中 3还款成功',
  `web` tinyint(1) NOT NULL DEFAULT '0' COMMENT '网站收支计算标识',
  `client` tinyint(1) DEFAULT '0' COMMENT '客户端0PC，1微信2安卓APP，3IOS APP，4其他',
  `remark` varchar(300) DEFAULT NULL COMMENT '备注',
  `add_ip` varchar(20) DEFAULT NULL COMMENT 'create ip',
  `create_user_id` int(11) NOT NULL COMMENT '创建用户id',
  `create_user_name` varchar(20) NOT NULL COMMENT '创建用户名',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户名',
  `update_user_name` varchar(20) DEFAULT NULL COMMENT '更新用户名',
  `recover_advance_fee` decimal(11,2) DEFAULT '0.00' COMMENT '提前还款费用',
  `recover_late_fee` decimal(11,2) DEFAULT '0.00' COMMENT '逾期还款费用',
  `tender_award_fee` decimal(11,2) DEFAULT '0.00' COMMENT '投资奖励增加',
  `contents` varchar(250) DEFAULT NULL,
  `recover_full_status` int(2) DEFAULT '0' COMMENT '是否已回收完成',
  `recover_fee` decimal(11,2) DEFAULT '0.00' COMMENT '放款服务费',
  `recover_type` varchar(100) DEFAULT NULL COMMENT '回收类型',
  `change_status` int(2) DEFAULT '0' COMMENT '债权转让',
  `change_period` int(11) DEFAULT '0' COMMENT '债权转让期数',
  `change_userid` int(11) DEFAULT '0' COMMENT '转让人',
  `tender_award_account` decimal(11,2) DEFAULT '0.00' COMMENT '投资奖励金额',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nid` (`order_id`),
  KEY `user_id` (`user_id`),
  KEY `borrow_nid` (`borrow_nid`),
  KEY `IDX_AD_US_BO` (`user_id`,`borrow_nid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='汇添金专属项目投资表，相当于borrow_tender表';

-- ----------------------------
-- Table structure for ht_debt_invest_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_debt_invest_log`;
CREATE TABLE `ht_debt_invest_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户userId',
  `user_name` varchar(20) NOT NULL COMMENT '投资用户名',
  `user_attribute` int(11) NOT NULL DEFAULT '0' COMMENT '投资用户属性',
  `plan_nid` varchar(30) NOT NULL COMMENT '计划nid',
  `plan_order_id` varchar(30) NOT NULL COMMENT '计划订单号',
  `borrow_nid` varchar(50) NOT NULL COMMENT '项目编号',
  `order_id` varchar(100) NOT NULL COMMENT '投资订单号',
  `account` decimal(11,2) DEFAULT '0.00' COMMENT '投资金额',
  `invest_type` tinyint(1) DEFAULT '0' COMMENT '投资类型 0手动投标 1预约投标 2自动投标',
  `status` tinyint(1) DEFAULT '0' COMMENT '投资日志表 0 初始 1成功 2失败',
  `client` tinyint(1) DEFAULT '0' COMMENT '客户端0PC，1微信2安卓APP，3IOS APP，4其他',
  `add_ip` varchar(20) DEFAULT NULL COMMENT 'create ip',
  `remark` varchar(300) DEFAULT NULL COMMENT '备注',
  `create_user_id` int(11) NOT NULL COMMENT '创建用户id',
  `create_user_name` varchar(20) NOT NULL COMMENT '创建用户名',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户名',
  `update_user_name` varchar(20) DEFAULT NULL COMMENT '更新用户名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nid` (`order_id`),
  KEY `user_id` (`user_id`),
  KEY `borrow_nid` (`borrow_nid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='汇添金专属项目投资临时表，相当于borrow_tender_tmp表';

-- ----------------------------
-- Table structure for ht_debt_loan
-- ----------------------------
DROP TABLE IF EXISTS `ht_debt_loan`;
CREATE TABLE `ht_debt_loan` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '投资用户id',
  `user_name` varchar(20) NOT NULL COMMENT '投资用户名',
  `borrow_user_id` int(11) DEFAULT '0' COMMENT '借款用户id',
  `borrow_user_name` varchar(20) DEFAULT NULL COMMENT '借款用户名',
  `borrow_nid` varchar(50) NOT NULL DEFAULT '' COMMENT '项目标的号',
  `plan_nid` varchar(30) NOT NULL COMMENT '计划nid',
  `plan_order_id` varchar(30) NOT NULL COMMENT '计划订单号',
  `invest_id` int(11) NOT NULL DEFAULT '0' COMMENT '投资id',
  `invest_order_id` varchar(100) NOT NULL COMMENT '投资订单号',
  `loan_account` decimal(11,2) DEFAULT '0.00' COMMENT '放款总本息（应还本息）',
  `loan_interest` decimal(11,2) DEFAULT '0.00' COMMENT '放款利息（应还利息）',
  `loan_capital` decimal(11,2) DEFAULT '0.00' COMMENT '放款本金（应还本金）',
  `receive_account_yes` decimal(11,2) DEFAULT '0.00' COMMENT '投资人收到的还款本息',
  `receive_capital_yes` decimal(11,2) DEFAULT '0.00' COMMENT '投资人收到的还款本金',
  `receive_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '投资人收到的还款利息',
  `remain_period` int(2) DEFAULT NULL COMMENT '剩余期数',
  `repay_period` int(2) DEFAULT NULL COMMENT '已还款期数',
  `repay_time` varchar(50) DEFAULT NULL COMMENT '估计还款时间',
  `repay_action_time` varchar(50) DEFAULT NULL COMMENT '实际还款时间',
  `repay_account_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还本息',
  `repay_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还利息',
  `repay_capital_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还本金',
  `repay_account_wait` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '待还本息',
  `repay_capital_wait` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '待还本金',
  `repay_interest_wait` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '待还利息',
  `repay_status` int(2) DEFAULT '0' COMMENT '还款状态 0未还款 1还款中 2已还款',
  `manage_fee` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '账户管理费',
  `loan_type` varchar(50) NOT NULL,
  `advance_status` int(2) NOT NULL COMMENT '是否提前还款 0正常还款 1提前还款 2延期还款 3逾期还款',
  `advance_days` int(11) DEFAULT '0' COMMENT '提前还款天数',
  `repay_advance_interest` decimal(11,2) DEFAULT '0.00' COMMENT '借款人应提前还款利息',
  `repay_advance_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '借款人已还款提前还款利息',
  `late_days` int(11) NOT NULL DEFAULT '0' COMMENT '逾期天数',
  `repay_late_interest` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '借款人应还款逾期利息',
  `repay_late_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '借款人已还款逾期利息',
  `delay_days` int(11) DEFAULT '0' COMMENT '延期天数',
  `repay_delay_interest` decimal(10,2) DEFAULT '0.00' COMMENT '借款人应还款延期利息',
  `repay_delay_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '借款人已还款延期利息',
  `receive_advance_interest` decimal(11,2) DEFAULT '0.00' COMMENT '投资人收取提前还款利息',
  `receive_late_interest` decimal(11,2) DEFAULT '0.00' COMMENT '投资人收取逾期利息',
  `receive_delay_interest` decimal(11,2) DEFAULT '0.00' COMMENT '投资人收取延期利息',
  `receive_advance_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '投资人已收取提前还款利息',
  `receive_late_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '投资人已收取逾期利息',
  `receive_delay_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '投资人已收取延期利息',
  `add_ip` varchar(20) DEFAULT NULL COMMENT 'create ip',
  `sendmail` tinyint(1) DEFAULT '0' COMMENT '0发送邮件，1已发送',
  `web` tinyint(1) DEFAULT '0' COMMENT '网站收支计算标识',
  `credit_amount` decimal(11,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '已债转金额',
  `credit_interest_amount` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已债转总利息（垫付）',
  `credit_time` int(11) NOT NULL DEFAULT '0' COMMENT '债转时间',
  `credit_status` tinyint(1) DEFAULT '0' COMMENT '债转状态:是否已债转(0:否,1:是)',
  `repay_order_id` varchar(20) DEFAULT NULL COMMENT '还款订单号',
  `repay_order_date` varchar(10) DEFAULT NULL COMMENT '还款订单日期',
  `create_user_id` int(11) NOT NULL COMMENT '创建用户userId',
  `create_user_name` varchar(20) NOT NULL COMMENT '创建用户名',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户userId',
  `update_user_name` varchar(20) DEFAULT NULL COMMENT '更新用户名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `borrow_nid` (`borrow_nid`),
  KEY `nid` (`invest_order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='汇添金专属项目放款总表，相当于borrow_recover表';

-- ----------------------------
-- Table structure for ht_debt_loan_detail
-- ----------------------------
DROP TABLE IF EXISTS `ht_debt_loan_detail`;
CREATE TABLE `ht_debt_loan_detail` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) DEFAULT '0' COMMENT '投资用户userId',
  `user_name` varchar(20) DEFAULT NULL COMMENT '投资用户名',
  `borrow_user_id` int(11) DEFAULT '0' COMMENT '借款用户userId',
  `borrow_user_name` varchar(20) DEFAULT NULL COMMENT '借款用户名',
  `borrow_nid` varchar(50) DEFAULT '' COMMENT '项目标的号',
  `plan_nid` varchar(30) DEFAULT NULL COMMENT '计划nid',
  `plan_order_id` varchar(30) DEFAULT NULL COMMENT '计划订单号',
  `invest_id` int(11) DEFAULT '0' COMMENT '投资id',
  `invest_order_id` varchar(100) NOT NULL COMMENT '投资订单号',
  `loan_account` decimal(11,2) DEFAULT '0.00' COMMENT '放款本息（应还本息）',
  `loan_interest` decimal(11,2) DEFAULT '0.00' COMMENT '放款利息（应还利息）',
  `loan_capital` decimal(11,2) DEFAULT '0.00' COMMENT '放款本金（应还本金）',
  `receive_account_yes` decimal(11,2) DEFAULT '0.00' COMMENT '投资人收到的还款本息',
  `receive_capital_yes` decimal(11,2) DEFAULT '0.00' COMMENT '投资人收到的还款本金',
  `receive_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '投资人收到的还款利息',
  `repay_period` int(2) DEFAULT NULL COMMENT '还款期数',
  `repay_time` varchar(50) DEFAULT NULL COMMENT '估计还款时间',
  `repay_action_time` varchar(50) DEFAULT NULL COMMENT '已经还款时间',
  `repay_account_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还本息',
  `repay_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还利息',
  `repay_capital_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还本金',
  `repay_account_wait` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '待还本息',
  `repay_capital_wait` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '待还本金',
  `repay_interest_wait` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '待还利息',
  `repay_status` int(2) DEFAULT '0' COMMENT '还款状态 0未还款 1还款中 2已还款',
  `manage_fee` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '账户管理费',
  `loan_type` varchar(50) NOT NULL COMMENT '放款类型（未使用）',
  `advance_status` int(2) NOT NULL COMMENT '是否提前还款 0正常还款 1提前还款 2延期还款 3逾期还款',
  `advance_days` int(11) DEFAULT '0' COMMENT '提前还款天数',
  `repay_advance_interest` decimal(11,2) DEFAULT '0.00' COMMENT '借款人应提前还款利息',
  `repay_advance_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '借款人已还款提前还款利息',
  `late_days` int(11) NOT NULL DEFAULT '0' COMMENT '逾期天数',
  `repay_late_interest` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '借款人应还款逾期利息',
  `repay_late_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '借款人已还款逾期利息',
  `delay_days` int(11) DEFAULT '0' COMMENT '延期天数',
  `repay_delay_interest` decimal(10,2) DEFAULT '0.00' COMMENT '借款人应还款延期利息',
  `repay_delay_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '借款人已还款延期利息',
  `receive_advance_interest` decimal(11,2) DEFAULT '0.00' COMMENT '投资人收取提前还款利息',
  `receive_late_interest` decimal(11,2) DEFAULT '0.00' COMMENT '投资人收取逾期利息',
  `receive_delay_interest` decimal(11,2) DEFAULT '0.00' COMMENT '投资人收取延期利息',
  `receive_advance_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '投资人已收取提前还款利息',
  `receive_late_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '投资人已收取逾期利息',
  `receive_delay_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '投资人已收取延期利息',
  `add_ip` varchar(20) DEFAULT NULL COMMENT 'create ip',
  `sendmail` tinyint(1) DEFAULT '0' COMMENT '0发送邮件，1已发送',
  `web` tinyint(1) DEFAULT '0' COMMENT '网站收支计算标识',
  `credit_amount` decimal(11,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '已债转金额',
  `credit_interest_amount` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已债转总利息（垫付）',
  `credit_time` int(11) NOT NULL DEFAULT '0' COMMENT '债转时间',
  `repay_order_id` varchar(20) DEFAULT NULL COMMENT '还款订单号',
  `repay_order_date` varchar(10) DEFAULT NULL COMMENT '还款订单日期',
  `create_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '创建用户id',
  `create_user_name` varchar(20) NOT NULL COMMENT '创建用户名',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户名',
  `update_user_name` varchar(20) DEFAULT NULL COMMENT '更新用户名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `borrow_nid` (`borrow_nid`),
  KEY `nid` (`invest_order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='汇添金专属项目放款详情表，相当于borrow_recover_plan表';

-- ----------------------------
-- Table structure for ht_debt_loan_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_debt_loan_log`;
CREATE TABLE `ht_debt_loan_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `nid` varchar(50) DEFAULT NULL COMMENT '订单号',
  `borrow_nid` varchar(20) DEFAULT NULL COMMENT '借款编号',
  `user_id` int(11) DEFAULT '0' COMMENT '用户ID',
  `status` int(2) DEFAULT '0' COMMENT '状态',
  `money` decimal(10,2) DEFAULT '0.00' COMMENT '金额',
  `fee` decimal(10,2) DEFAULT '0.00' COMMENT '服务费',
  `balance` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '实际到账余额',
  `remark` varchar(250) DEFAULT '' COMMENT '备注',
  `operator` varchar(20) DEFAULT NULL COMMENT '操作者',
  `add_ip` varchar(20) DEFAULT NULL COMMENT 'create ip',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `nid` (`nid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='汇添金专属项目放款日志';

-- ----------------------------
-- Table structure for ht_debt_plan
-- ----------------------------
DROP TABLE IF EXISTS `ht_debt_plan`;
CREATE TABLE `ht_debt_plan` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '计划id',
  `debt_plan_type` int(10) NOT NULL COMMENT '计划类型，关联计划类型表hyjf_debt_plan_config',
  `debt_plan_type_name` varchar(20) NOT NULL COMMENT '计划类型名称',
  `debt_plan_nid` varchar(20) NOT NULL COMMENT '计划编号',
  `debt_pre_plan_nid` int(7) unsigned NOT NULL DEFAULT '0' COMMENT '预发布计划编号',
  `debt_plan_name` varchar(50) NOT NULL COMMENT '计划名称',
  `debt_plan_money` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '计划金额',
  `debt_plan_money_yes` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '计划加入金额',
  `debt_plan_money_wait` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '计划余额',
  `debt_plan_account_scale` decimal(11,2) DEFAULT '0.00' COMMENT '计划完成率',
  `debt_plan_status` tinyint(2) unsigned DEFAULT '0' COMMENT '计划状态 0 发起中；1 待审核；2审核不通过；3待开放；4募集中；5锁定中；6清算中；7清算完成，8未还款，9还款中，10还款完成',
  `round_amount` decimal(10,2) DEFAULT '0.00' COMMENT '取整金额',
  `accede_times` int(10) DEFAULT '0' COMMENT '计划加入次数',
  `expect_apr` decimal(10,2) NOT NULL COMMENT '预期年化利率',
  `actual_apr` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '实际年化利率',
  `investment_scope` varchar(50) NOT NULL DEFAULT '' COMMENT '投资范围',
  `debt_quit_style` tinyint(2) unsigned DEFAULT '0' COMMENT '退出方式:0:到期退出',
  `debt_quit_period` int(5) NOT NULL DEFAULT '0' COMMENT '退出天数',
  `is_audits` varchar(10) NOT NULL DEFAULT '' COMMENT '是否立即审核:yes:立即提交审核,no:暂不提交审核',
  `plan_concept` longtext NOT NULL COMMENT '计划概念',
  `plan_principle` longtext NOT NULL COMMENT '计划原理',
  `safeguard_measures` longtext NOT NULL COMMENT '风控保障措施',
  `margin_measures` longtext NOT NULL COMMENT '风险保证金措施',
  `buy_begin_time` int(11) DEFAULT NULL COMMENT '申购开始时间',
  `buy_end_time` int(11) DEFAULT NULL COMMENT '申购结束时间',
  `buy_period_day` int(20) NOT NULL COMMENT '申购期限（天）',
  `buy_period_hour` int(20) NOT NULL COMMENT '申购期限（小时）',
  `buy_period` int(20) NOT NULL COMMENT '申购总期限（小时）',
  `debt_lock_period` tinyint(2) unsigned DEFAULT '0' COMMENT '锁定期(月)',
  `debt_min_investment` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '最低投资金额',
  `debt_investment_increment` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '投资增量',
  `debt_max_investment` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '最高投资金额',
  `debt_plan_balance` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '计划可用余额',
  `debt_plan_frost` decimal(20,2) DEFAULT '0.00' COMMENT '计划投资汇资产冻结金额',
  `full_expire_time` int(11) DEFAULT NULL COMMENT '满标/到期时间',
  `repay_account_all` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '应还款总额',
  `repay_account_interest` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '应还利息',
  `repay_account_capital` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '应还本金',
  `repay_account_yes` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '实还总额',
  `repay_account_interest_yes` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '实还利息',
  `repay_account_capital_yes` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '实还本金',
  `repay_account_wait` decimal(11,2) DEFAULT '0.00' COMMENT '未还款总额',
  `repay_account_interest_wait` decimal(11,2) DEFAULT '0.00' COMMENT '未还款利息',
  `repay_account_capital_wait` decimal(11,2) DEFAULT '0.00' COMMENT '未还款本金',
  `service_fee` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '服务费',
  `min_invest_number` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '最小投资笔数',
  `max_invest_number` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '最大投资笔数',
  `cycle_times` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '投资循环次数',
  `unable_cycle_times` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '无视规则投资次数',
  `invest_account_limit` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '投资金额界定',
  `min_surplus_invest_account` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '最低剩余投资金额',
  `liquidate_should_time` int(11) DEFAULT NULL COMMENT '应清算时间',
  `liquidate_fact_time` int(11) DEFAULT NULL COMMENT '实际清算时间',
  `repay_time` int(11) DEFAULT NULL COMMENT '还款时间',
  `audit_user_id` int(11) DEFAULT NULL COMMENT '审核人',
  `audit_time` int(11) DEFAULT NULL COMMENT '审核时间',
  `audit_remark` varchar(200) DEFAULT NULL COMMENT '审核备注',
  `plan_lock_time` int(11) DEFAULT NULL COMMENT '计划进入锁定期时间',
  `commission_status` tinyint(1) unsigned DEFAULT NULL COMMENT '提成状态:0:计算提成,1:提成明细',
  `commission_total` decimal(20,2) DEFAULT '0.00' COMMENT '提成总额',
  `liquidate_apr` decimal(6,2) DEFAULT '0.00' COMMENT '清算进度百分比',
  `coupon_config` varchar(10) DEFAULT '0' COMMENT '是否可用券：0 不可用 1 体验金 2 加息券 3 代金券',
  `liquidate_arrival_amount` decimal(20,2) DEFAULT '0.00' COMMENT '清算已经到账金额',
  `repay_time_last` int(11) DEFAULT NULL COMMENT '最迟还款日',
  `show_status` tinyint(1) DEFAULT '0' COMMENT 'web是否表示(汇添金测试用:0:表示,1:不表示)',
  `del_flag` tinyint(1) unsigned DEFAULT NULL COMMENT '删除标识 0：未删除，1：已删除',
  `create_user_id` int(11) NOT NULL COMMENT '发起人id',
  `create_user_name` varchar(20) NOT NULL COMMENT '创建人用户名',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新人id',
  `update_user_name` varchar(20) DEFAULT NULL COMMENT '修改人用户名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `debt_plan_nid` (`debt_plan_nid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='汇添金计划总表';

-- ----------------------------
-- Table structure for ht_debt_plan_accede
-- ----------------------------
DROP TABLE IF EXISTS `ht_debt_plan_accede`;
CREATE TABLE `ht_debt_plan_accede` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `plan_nid` varchar(20) NOT NULL COMMENT '计划nid',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `user_name` varchar(30) NOT NULL COMMENT '用户名',
  `user_attribute` int(11) NOT NULL DEFAULT '0' COMMENT '用户属性',
  `accede_order_id` varchar(50) NOT NULL COMMENT '计划加入订单号',
  `freeze_order_id` varchar(30) NOT NULL COMMENT '加入时的冻结订单号',
  `accede_account` decimal(11,2) NOT NULL COMMENT '计划加入总金额',
  `accede_balance` decimal(11,2) NOT NULL COMMENT '可用余额',
  `accede_frost` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '计划订单冻结金额（专指投资汇添金专属标的冻结）',
  `liquidates_credit_frost` decimal(11,2) DEFAULT '0.00' COMMENT '清算债权承接冻结的余额',
  `liquidates_apr` decimal(11,2) DEFAULT '0.00' COMMENT '清算进度',
  `liquidates_repay_frost` decimal(11,2) DEFAULT '0.00' COMMENT '清算还款冻结金额',
  `service_fee_rate` decimal(11,2) DEFAULT '0.00' COMMENT '服务费收取比例',
  `service_fee` decimal(11,2) DEFAULT '0.00' COMMENT '服务费',
  `expire_fair_value` decimal(11,2) DEFAULT '0.00' COMMENT '到期公允价值',
  `under_take_times` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '承接次数',
  `invest_max` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '最大投资金额（参考值）',
  `invest_min` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '最小投资金额（参考值）',
  `cycle_times` int(10) NOT NULL DEFAULT '0' COMMENT '遍历次数（已经尝试自动投资次数）',
  `repay_account` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '应还本息',
  `repay_capital` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '应还本金',
  `repay_interest` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '应还利息',
  `repay_account_wait` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '待还本息',
  `repay_capital_wait` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '待还本金',
  `repay_interest_wait` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '待还利息',
  `repay_account_yes` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已还本息',
  `repay_capital_yes` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已还本金',
  `repay_interest_yes` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已还利息',
  `invite_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '推荐人用户userId',
  `invite_user_name` varchar(100) DEFAULT NULL COMMENT '推荐人用户名',
  `invite_user_attribute` int(11) NOT NULL DEFAULT '0' COMMENT '推荐人用户属性',
  `invite_region_id` int(11) NOT NULL DEFAULT '0',
  `invite_region_name` varchar(100) NOT NULL DEFAULT ' ',
  `invite_branch_id` int(11) NOT NULL DEFAULT '0',
  `invite_branch_name` varchar(100) NOT NULL DEFAULT ' ',
  `invite_department_id` int(11) NOT NULL DEFAULT '0',
  `invite_department_name` varchar(100) NOT NULL DEFAULT ' ',
  `send_status` tinyint(1) DEFAULT NULL COMMENT '协议发送状态0未发送 1已发送',
  `calculation_status` tinyint(1) DEFAULT '0' COMMENT '是否需要计算到期公允价值状态(0:是,1否)',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '此笔加入是否已经完成 0投资中 1投资完成 2还款中 3还款完成',
  `reinvest_status` tinyint(1) DEFAULT '0' COMMENT '是否复投 0非复投 1复投',
  `repay_running_status` tinyint(1) DEFAULT '0' COMMENT '是否有其他项目在还款中 0否 1是',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '删除标识',
  `client` tinyint(1) DEFAULT NULL COMMENT '客户端 0PC，1微信，2安卓APP，3IOS，4其他',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` int(11) NOT NULL COMMENT '创建者id',
  `create_user_name` varchar(20) NOT NULL COMMENT '创建者用户名',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户id',
  `update_user_name` varchar(20) DEFAULT NULL COMMENT '更新用户',
  `invite_repay_user_id` int(11) DEFAULT '0' COMMENT '还款推荐人id',
  `invite_repay_user_attribute` int(11) DEFAULT '0' COMMENT '还款推荐人属性',
  `invite_repay_user_name` varchar(100) DEFAULT NULL COMMENT '推荐人用户名',
  `invite_repay_region_id` int(11) DEFAULT '0' COMMENT '还款推荐人部门id',
  `invite_repay_region_name` varchar(100) DEFAULT NULL COMMENT '还款推荐人部门名',
  `invite_repay_branch_id` int(11) DEFAULT '0' COMMENT '还款推荐人分部id',
  `invite_repay_branch_name` varchar(100) DEFAULT NULL COMMENT '还款推荐人分部名',
  `invite_repay_department_id` int(11) DEFAULT '0' COMMENT '还款推荐人所属部门id',
  `invite_repay_department_name` varchar(100) DEFAULT NULL COMMENT '还款推荐人所属部门名',
  PRIMARY KEY (`id`),
  UNIQUE KEY `accede_order_id` (`accede_order_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='汇添金计划加入明细表';

-- ----------------------------
-- Table structure for ht_debt_plan_borrow
-- ----------------------------
DROP TABLE IF EXISTS `ht_debt_plan_borrow`;
CREATE TABLE `ht_debt_plan_borrow` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `debt_plan_nid` varchar(20) NOT NULL COMMENT '计划编号',
  `borrow_nid` varchar(20) NOT NULL COMMENT '借款编号',
  `type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '关联类型，0专属项目，1转让债权',
  `add_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '标的添加类型 0手动添加 1自动添加',
  `del_flag` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '删除标志位 0未删除 1删除',
  `create_user_id` int(10) NOT NULL COMMENT '创建人',
  `create_user_name` varchar(20) DEFAULT NULL COMMENT '创建人用户名',
  `update_user_id` int(10) DEFAULT NULL COMMENT '修改人',
  `update_user_name` varchar(20) DEFAULT NULL COMMENT '修改人用户名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `invest_range_id` (`debt_plan_nid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='汇添金计划同专属项目关联表';

-- ----------------------------
-- Table structure for ht_debt_plan_config
-- ----------------------------
DROP TABLE IF EXISTS `ht_debt_plan_config`;
CREATE TABLE `ht_debt_plan_config` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `debt_plan_type` int(10) NOT NULL COMMENT '计划类型 与hyjf_debt_plan表debt_plan_type外键',
  `debt_plan_type_name` varchar(50) NOT NULL DEFAULT '' COMMENT '计划类型名称',
  `debt_plan_prefix` varchar(20) NOT NULL DEFAULT '' COMMENT '计划前缀',
  `debt_lock_period` int(2) NOT NULL COMMENT '锁定期 月',
  `debt_min_investment` decimal(10,2) NOT NULL COMMENT '最低投资金额',
  `debt_investment_increment` decimal(10,2) NOT NULL COMMENT '投资增量',
  `debt_max_investment` decimal(10,2) DEFAULT NULL COMMENT '最高投资金额',
  `debt_quit_style` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '退出方式:0:到期退出',
  `debt_quit_period` int(5) NOT NULL COMMENT '退出天数',
  `min_invest_number` int(2) NOT NULL DEFAULT '0' COMMENT '最小投资笔数',
  `max_invest_number` int(2) NOT NULL DEFAULT '0' COMMENT '最大投资笔数',
  `cycle_times` int(2) NOT NULL DEFAULT '0' COMMENT '投资循环次数',
  `unable_cycle_times` int(2) NOT NULL DEFAULT '0' COMMENT '无视规则投资次数',
  `invest_account_limit` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '汇添金专属资产最后一笔界定金额',
  `min_surplus_invest_account` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '最低剩余投资金额',
  `round_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '取整金额',
  `coupon_config` varchar(10) DEFAULT '0' COMMENT '是否可用券：0 不可用 1 体验金 2 加息券 3 代金券',
  `status` tinyint(1) unsigned NOT NULL COMMENT '状态 0关闭  1启用',
  `del_flag` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '删除状态 1:删除  0:未删除',
  `remark` longtext NOT NULL COMMENT '常见问题',
  `create_user_id` int(10) NOT NULL COMMENT '创建人',
  `create_user_name` varchar(20) NOT NULL DEFAULT '' COMMENT '创建人用户名',
  `update_user_id` int(10) DEFAULT NULL COMMENT '修改人',
  `update_user_name` varchar(20) DEFAULT NULL COMMENT '修改人用户名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `debt_plan_type` (`debt_plan_type`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='汇添金计划配置表';

-- ----------------------------
-- Table structure for ht_debt_plan_info_static
-- ----------------------------
DROP TABLE IF EXISTS `ht_debt_plan_info_static`;
CREATE TABLE `ht_debt_plan_info_static` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `wait_invest` decimal(10,2) DEFAULT NULL COMMENT '待成交资产-专属资产',
  `wait_credit` decimal(10,2) DEFAULT NULL COMMENT '待成交资产-债权转让',
  `yes_invest` int(10) DEFAULT NULL COMMENT '已成交资产数量-专属资产',
  `yes_credit` int(10) DEFAULT NULL COMMENT '已成交资产数量-债权转让',
  `wait_repay` decimal(10,2) DEFAULT NULL COMMENT '持有债权待还总额',
  `yes_repay` decimal(10,2) DEFAULT NULL COMMENT '持有债权已还总额',
  `plan_repay_wait` decimal(10,2) DEFAULT '0.00' COMMENT '应还总额',
  `plan_repay_yes` decimal(10,2) DEFAULT '0.00' COMMENT '已还总额',
  `plan_accede_all` decimal(10,2) DEFAULT '0.00' COMMENT '投资人加入总额',
  `expire_fair_value` decimal(10,2) DEFAULT '0.00' COMMENT '到期公允价值总额',
  `invest_period_one` int(10) DEFAULT NULL COMMENT '待成交资产-汇添金专属资产-期限分布-一月',
  `invest_period_two_four` int(10) DEFAULT NULL COMMENT '待成交资产-汇添金专属资产-期限分布-2-4月',
  `invest_period_five_eight` int(10) DEFAULT NULL COMMENT '待成交资产-汇添金专属资产-期限分布-5-8月',
  `invest_period_nine_twel` int(10) DEFAULT NULL COMMENT '待成交资产-汇添金专属资产-期限分布-9-12月',
  `invest_period_twel_tf` int(10) DEFAULT NULL COMMENT '待成交资产-汇添金专属资产-期限分布-12-24月',
  `invest_period_tf` int(10) DEFAULT NULL COMMENT '待成交资产-汇添金专属资产-期限分布-24-月',
  `credit_period_one` int(10) DEFAULT NULL COMMENT '待成交资产-债权转让-期限分布-一月',
  `credit_period_two_four` int(10) DEFAULT NULL COMMENT '待成交资产-债权转让-期限分布-2-4月',
  `credit_period_five_eight` int(10) DEFAULT NULL COMMENT '待成交资产-债权转让-期限分布-5-8月',
  `credit_period_nine_twel` int(10) DEFAULT NULL COMMENT '待成交资产-债权转让-期限分布-9-12月',
  `credit_period_twel_tf` int(10) DEFAULT NULL COMMENT '待成交资产-债权转让-期限分布-12-24月',
  `credit_period_tf` int(10) DEFAULT NULL COMMENT '待成交资产-债权转让-期限分布-24-月',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `data_date` varchar(20) DEFAULT NULL COMMENT '日期',
  `data_month` varchar(20) NOT NULL COMMENT '统计数据月份',
  `data_hour` varchar(20) NOT NULL COMMENT '统计数据小时',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='汇天利信息统计表';

-- ----------------------------
-- Table structure for ht_debt_plan_info_static_count
-- ----------------------------
DROP TABLE IF EXISTS `ht_debt_plan_info_static_count`;
CREATE TABLE `ht_debt_plan_info_static_count` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `accede_money_one` decimal(11,2) DEFAULT '0.00' COMMENT '加入金额分布-金额分布0-1万',
  `accede_money_two` decimal(11,2) DEFAULT '0.00' COMMENT '加入金额分布-金额分布1-2万',
  `accede_money_three` decimal(11,2) DEFAULT '0.00' COMMENT '加入金额分布-金额分布2-3万',
  `accede_money_four` decimal(11,2) DEFAULT '0.00' COMMENT '加入金额分布-金额分布3-4万',
  `accede_money_five` decimal(11,2) DEFAULT '0.00' COMMENT '加入金额分布-金额分布4-5万',
  `accede_money_five_up` decimal(11,2) DEFAULT '0.00' COMMENT '加入金额分布-金额分布5万以上',
  `accede_money_count_one` int(10) DEFAULT '0' COMMENT '加入金额分布-人次分布0-1万',
  `accede_money_count_two` int(10) DEFAULT '0' COMMENT '加入金额分布-人次分布1-2万',
  `accede_money_count_three` int(10) DEFAULT '0' COMMENT '加入金额分布-人次分布2-3万',
  `accede_money_count_four` int(10) DEFAULT '0' COMMENT '加入金额分布-人次分布3-4万',
  `accede_money_count_five` int(10) DEFAULT '0' COMMENT '加入金额分布-人次分布4-5万',
  `accede_money_count_five_up` int(10) DEFAULT '0' COMMENT '加入金额分布-人次分布5万以上',
  `accede_count_one` int(10) DEFAULT '0' COMMENT '加入次数分布-人次分布1次',
  `accede_count_two_four` int(10) DEFAULT '0' COMMENT '加入次数分布-人次分布2-4次',
  `accede_count_five_egiht` int(10) DEFAULT '0' COMMENT '加入次数分布-人次分布5-8次',
  `accede_count_nine_fifteen` int(10) DEFAULT '0' COMMENT '加入次数分布-人次分布9-15次',
  `accede_count_sixteen_thirty` int(10) DEFAULT '0' COMMENT '加入次数分布-人次分布16-30次',
  `accede_count_thirtyfirst_up` int(10) DEFAULT '0' COMMENT '加入次数分布-人次分布31次以上',
  `accede_client_money_pc` decimal(11,2) DEFAULT NULL COMMENT '平台加入金额-pc',
  `accede_client_money_wei` decimal(11,2) DEFAULT NULL COMMENT '平台加入金额-wei',
  `accede_client_money_ios` decimal(11,2) DEFAULT NULL COMMENT '平台加入金额-ios',
  `accede_client_money_android` decimal(11,2) DEFAULT NULL COMMENT '平台加入金额-android',
  `accede_client_count_pc` int(10) DEFAULT '0' COMMENT '平台加入人次-pc',
  `accede_client_count_wei` int(10) DEFAULT '0' COMMENT '平台加入人次-wei',
  `accede_client_count_ios` int(10) DEFAULT NULL COMMENT '平台加入人次-ios',
  `accede_client_count_android` int(10) DEFAULT NULL COMMENT '平台加入人次-android',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='汇天金信息统计表';

-- ----------------------------
-- Table structure for ht_debt_repay
-- ----------------------------
DROP TABLE IF EXISTS `ht_debt_repay`;
CREATE TABLE `ht_debt_repay` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '借款用户id',
  `user_name` varchar(20) NOT NULL COMMENT '借款用户名',
  `borrow_nid` varchar(50) NOT NULL DEFAULT '' COMMENT '项目编号',
  `plan_nid` varchar(30) DEFAULT NULL COMMENT '计划nid',
  `repay_nid` varchar(30) NOT NULL COMMENT '还款唯一标识',
  `manage_fee` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '还款费用',
  `liquidates_service_fee` decimal(11,2) DEFAULT '0.00' COMMENT '清算时收取的服务费',
  `loan_status` int(2) DEFAULT '0' COMMENT '放款状态 0放款中1已放款',
  `repay_status` int(2) DEFAULT '0' COMMENT '还款状态 0未还款 1还款中 2已还款',
  `remain_period` int(2) DEFAULT NULL COMMENT '剩余期数',
  `already_repay_period` int(2) DEFAULT '0' COMMENT '已还款期数',
  `repay_period` int(2) DEFAULT NULL COMMENT '已还款期数',
  `repay_time` varchar(50) DEFAULT NULL COMMENT '估计还款时间(下期还款时间)',
  `repay_action_time` varchar(50) DEFAULT NULL COMMENT '实际还款的时间',
  `repay_account_all` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '应还款总额，加上费用',
  `repay_account` decimal(11,2) DEFAULT '0.00' COMMENT '应还款本息',
  `repay_interest` decimal(11,2) DEFAULT '0.00' COMMENT '应还款利息',
  `repay_capital` decimal(11,2) DEFAULT '0.00' COMMENT '应还款本金',
  `repay_account_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还款本息',
  `repay_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还款利息',
  `repay_capital_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还款本金',
  `repay_account_wait` decimal(11,2) DEFAULT '0.00' COMMENT '待还款本息',
  `repay_capital_wait` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '待还款本金',
  `repay_interest_wait` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '待还利息',
  `advance_status` int(2) NOT NULL COMMENT '是否提前还款 0正常还款 1提前还款 2延期还款 3逾期还款',
  `advance_days` int(11) DEFAULT '0' COMMENT '提前还款天数',
  `advance_interest` decimal(11,2) DEFAULT '0.00' COMMENT '提前还款利息',
  `delay_days` int(10) DEFAULT '0' COMMENT '延期天数',
  `delay_interest` decimal(10,2) DEFAULT '0.00' COMMENT '延期利息',
  `late_days` int(11) NOT NULL DEFAULT '0' COMMENT '逾期天数',
  `late_interest` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '逾期利息',
  `delay_remark` varchar(100) DEFAULT NULL COMMENT '延期备注',
  `add_ip` varchar(20) DEFAULT NULL COMMENT 'create ip',
  `create_user_id` int(11) NOT NULL COMMENT '创建用户id',
  `create_user_name` varchar(20) NOT NULL COMMENT '创建用户名',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户名',
  `update_user_name` varchar(20) DEFAULT NULL COMMENT '更新用户名',
  `repay_money_source` tinyint(1) unsigned DEFAULT NULL COMMENT '还款来源（1、借款人还款，2、机构垫付，3、保证金垫付）',
  `repay_user_id` int(11) DEFAULT '0' COMMENT '实际还款人（借款人、担保机构、保证金）的用户ID',
  `repay_username` varchar(30) DEFAULT NULL COMMENT '实际还款人（借款人、担保机构、保证金）的用户名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `borrow_nid` (`borrow_nid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='汇添金专属标的还款总表，相当于borrow_repay表';

-- ----------------------------
-- Table structure for ht_debt_repay_detail
-- ----------------------------
DROP TABLE IF EXISTS `ht_debt_repay_detail`;
CREATE TABLE `ht_debt_repay_detail` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '借款用户id',
  `user_name` varchar(20) NOT NULL COMMENT '借款用户名',
  `borrow_nid` varchar(50) NOT NULL COMMENT '借款id',
  `plan_nid` varchar(30) DEFAULT NULL COMMENT '计划nid',
  `repay_nid` varchar(30) NOT NULL COMMENT '还款唯一标识',
  `manage_fee` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '还款费用',
  `liquidates_service_fee` decimal(11,2) DEFAULT '0.00' COMMENT '清算时收取的服务费',
  `loan_status` int(2) DEFAULT '0' COMMENT '放款状态 0放款中1已放款',
  `repay_status` int(2) DEFAULT '0' COMMENT '还款状态 0未还款 1还款中 2已还款',
  `repay_period` int(2) DEFAULT NULL COMMENT '还款期数',
  `repay_time` varchar(50) DEFAULT NULL COMMENT '估计还款时间(下期还款时间)',
  `repay_action_time` varchar(50) DEFAULT NULL COMMENT '实际还款的时间',
  `repay_account_all` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '应还款总额，加上费用',
  `repay_account` decimal(11,2) DEFAULT '0.00' COMMENT '应还款本息',
  `repay_interest` decimal(11,2) DEFAULT '0.00' COMMENT '应还款利息',
  `repay_capital` decimal(11,2) DEFAULT '0.00' COMMENT '应还款本金',
  `repay_account_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还款本息',
  `repay_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还款利息',
  `repay_capital_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还款本金',
  `repay_account_wait` decimal(11,2) DEFAULT '0.00' COMMENT '待还款本息',
  `repay_capital_wait` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '待还款本金',
  `repay_interest_wait` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '待还利息',
  `advance_status` int(2) NOT NULL COMMENT '是否提前还款 0正常还款 1提前还款 2延期还款 3逾期还款',
  `advance_days` int(11) DEFAULT '0' COMMENT '提前还款天数',
  `advance_interest` decimal(11,2) DEFAULT '0.00' COMMENT '提前还款利息',
  `delay_days` int(10) DEFAULT '0' COMMENT '延期天数',
  `delay_interest` decimal(10,2) DEFAULT '0.00' COMMENT '延期利息',
  `late_days` int(11) NOT NULL DEFAULT '0' COMMENT '逾期天数',
  `late_interest` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '逾期利息',
  `delay_remark` varchar(100) DEFAULT NULL COMMENT '延期备注',
  `add_ip` varchar(15) DEFAULT NULL COMMENT 'create ip',
  `create_user_id` int(11) NOT NULL COMMENT '创建用户id',
  `create_user_name` varchar(20) NOT NULL COMMENT '创建用户名',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户userId',
  `update_user_name` varchar(20) DEFAULT NULL COMMENT '更新用户名',
  `repay_money_source` tinyint(1) unsigned DEFAULT NULL COMMENT '还款来源（1、借款人还款，2、机构垫付，3、保证金垫付）',
  `repay_user_id` int(11) DEFAULT '0' COMMENT '实际还款人（借款人、担保机构、保证金）的用户ID',
  `repay_username` varchar(30) DEFAULT NULL COMMENT '实际还款人（借款人、担保机构、保证金）的用户名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `borrow_nid` (`borrow_nid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='汇添金还款详情表，相当于borrow_repay_plan表';

-- ----------------------------
-- Table structure for ht_debt_user_info
-- ----------------------------
DROP TABLE IF EXISTS `ht_debt_user_info`;
CREATE TABLE `ht_debt_user_info` (
  `id` int(15) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '名字',
  `sex` int(2) DEFAULT NULL COMMENT '1男2女',
  `old` int(4) DEFAULT NULL COMMENT '年龄',
  `merry` int(2) DEFAULT NULL COMMENT '1已婚2未婚',
  `pro` varchar(20) DEFAULT NULL COMMENT '工作地:省',
  `city` varchar(20) DEFAULT NULL COMMENT '工作地:城市',
  `industry` varchar(50) DEFAULT NULL COMMENT '行业',
  `credit` int(11) DEFAULT '0' COMMENT '个人授信额度',
  `size` varchar(50) DEFAULT NULL COMMENT '公司规模（人数）',
  `business` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '月营业额',
  `wtime` varchar(60) DEFAULT NULL COMMENT '在现单位工作的时间',
  `borrow_nid` varchar(50) DEFAULT NULL COMMENT '借款编号',
  `borrow_pre_nid` int(7) NOT NULL DEFAULT '0' COMMENT '借款预编号',
  `position` varchar(20) DEFAULT NULL COMMENT '岗位职业',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='汇添金专属项目个人信息表';

-- ----------------------------
-- Table structure for ht_evaluation_config
-- ----------------------------
DROP TABLE IF EXISTS `ht_evaluation_config`;
CREATE TABLE `ht_evaluation_config` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `debt_evaluation_type_check` tinyint(1) NOT NULL DEFAULT '0' COMMENT '散标债转出借者测评类型校验（1开启，0关闭）',
  `intellectual_eveluation_type_check` tinyint(1) NOT NULL DEFAULT '0' COMMENT '智投出借者测评类型校验（1开启，0关闭）',
  `dept_evaluation_money_check` tinyint(1) NOT NULL DEFAULT '0' COMMENT '散标债转单笔投资金额校验（1开启，0关闭）',
  `intellectual_evaluation_money_check` tinyint(1) NOT NULL DEFAULT '0' COMMENT '智投单笔投资金额校验（1开启，0关闭）',
  `dept_collection_evaluation_check` tinyint(1) NOT NULL DEFAULT '0' COMMENT '散标债转待收本金校验（1开启，0关闭）',
  `intellectual_collection_evaluation_check` tinyint(1) NOT NULL DEFAULT '0' COMMENT '智投待收本金校验（1开启，0关闭）',
  `investment_evaluation_check` tinyint(1) NOT NULL DEFAULT '0' COMMENT '投资时校验二期（1开启，0关闭）',
  `validity_evaluation_date` int(10) NOT NULL DEFAULT '360' COMMENT '测评有效期',
  `conservative_evaluation_single_money` decimal(11,2) DEFAULT NULL COMMENT '保守型单笔投资限额金额',
  `growup_evaluation_single_money` decimal(11,2) DEFAULT NULL COMMENT '成长型单笔投资限额金额',
  `steady_evaluation_single_money` decimal(11,2) DEFAULT NULL COMMENT '稳健型单笔投资限额金额',
  `enterprising_evaluation_singl_money` decimal(11,2) DEFAULT NULL COMMENT '进取型单笔投资限额金额',
  `conservative_evaluation_principal_money` decimal(11,2) DEFAULT NULL COMMENT '保守型待收本金限额金额',
  `growup_evaluation_principal_money` decimal(11,2) DEFAULT NULL COMMENT '成长型待收本金限额金额',
  `steady_evaluation_principal_money` decimal(11,2) DEFAULT NULL COMMENT '稳健型待收本金限额金额',
  `enterprising_evaluation_principal_money` decimal(11,2) DEFAULT NULL COMMENT '进取型待收本金限额金额',
  `bbb_evaluation_proposal` varchar(10) NOT NULL DEFAULT '' COMMENT 'BBB信用等级对应的建议出借者类型',
  `A_evaluation_proposal` varchar(10) NOT NULL DEFAULT '' COMMENT 'A信用等级对应的建议出借者类型',
  `AA0_evaluation_proposal` varchar(10) NOT NULL DEFAULT '' COMMENT 'AA-信用等级对应的建议出借者类型',
  `AA1_evaluation_proposal` varchar(10) NOT NULL DEFAULT '' COMMENT 'AA信用等级对应的建议出借者类型',
  `AA2_evaluation_proposal` varchar(10) NOT NULL DEFAULT '' COMMENT 'AA+信用等级对应的建议出借者类型',
  `AAA_evaluation_proposal` varchar(10) NOT NULL DEFAULT '' COMMENT 'AAA信用等级对应的建议出借者类型',
  `create_user` varchar(10) DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user` varchar(10) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='测评配置';

-- ----------------------------
-- Table structure for ht_evaluation_config_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_evaluation_config_log`;
CREATE TABLE `ht_evaluation_config_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `debt_evaluation_type_check` tinyint(1) NOT NULL DEFAULT '0' COMMENT '散标债转出借者测评类型校验（1开启，0关闭）',
  `intellectual_eveluation_type_check` tinyint(1) NOT NULL DEFAULT '0' COMMENT '智投出借者测评类型校验（1开启，0关闭）',
  `dept_evaluation_money_check` tinyint(1) NOT NULL DEFAULT '0' COMMENT '散标债转单笔投资金额校验（1开启，0关闭）',
  `intellectual_evaluation_money_check` tinyint(1) NOT NULL DEFAULT '0' COMMENT '智投单笔投资金额校验（1开启，0关闭）',
  `dept_collection_evaluation_check` tinyint(1) NOT NULL DEFAULT '0' COMMENT '散标债转待收本金校验（1开启，0关闭）',
  `intellectual_collection_evaluation_check` tinyint(1) NOT NULL DEFAULT '0' COMMENT '智投待收本金校验（1开启，0关闭）',
  `investment_evaluation_check` tinyint(1) DEFAULT '0' COMMENT '投资时校验二期（1开启，0关闭）',
  `validity_evaluation_date` int(10) NOT NULL DEFAULT '360' COMMENT '测评有效期',
  `conservative_evaluation_single_money` decimal(11,2) DEFAULT NULL COMMENT '保守型单笔投资限额金额',
  `growup_evaluation_single_money` decimal(11,2) DEFAULT NULL COMMENT '成长型单笔投资限额金额',
  `steady_evaluation_single_money` decimal(11,2) DEFAULT NULL COMMENT '稳健型单笔投资限额金额',
  `enterprising_evaluation_singl_money` decimal(11,2) DEFAULT NULL COMMENT '进取型单笔投资限额金额',
  `conservative_evaluation_principal_money` decimal(11,2) DEFAULT NULL COMMENT '保守型待收本金限额金额',
  `growup_evaluation_principal_money` decimal(11,2) DEFAULT NULL COMMENT '成长型待收本金限额金额',
  `steady_evaluation_principal_money` decimal(11,2) DEFAULT NULL COMMENT '稳健型待收本金限额金额',
  `enterprising_evaluation_principal_money` decimal(11,2) DEFAULT NULL COMMENT '进取型待收本金限额金额',
  `bbb_evaluation_proposal` varchar(10) NOT NULL DEFAULT '' COMMENT 'BBB信用等级对应的建议出借者类型',
  `A_evaluation_proposal` varchar(10) NOT NULL DEFAULT '' COMMENT 'A信用等级对应的建议出借者类型',
  `AA0_evaluation_proposal` varchar(10) NOT NULL DEFAULT '' COMMENT 'AA-信用等级对应的建议出借者类型',
  `AA1_evaluation_proposal` varchar(10) NOT NULL DEFAULT '' COMMENT 'AA信用等级对应的建议出借者类型',
  `AA2_evaluation_proposal` varchar(10) NOT NULL DEFAULT '' COMMENT 'AA+信用等级对应的建议出借者类型',
  `AAA_evaluation_proposal` varchar(10) NOT NULL DEFAULT '' COMMENT 'AAA信用等级对应的建议出借者类型',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态（1开关配置，2限额配置，3信用等级配置）',
  `ip` varchar(45) DEFAULT NULL COMMENT 'IP',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user` varchar(10) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  KEY `idx_update_user` (`update_user`),
  KEY `idx_create_time` (`create_time`),
  KEY `IDX_UP_CT` (`update_user`,`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8 COMMENT='测评配置log';

-- ----------------------------
-- Table structure for ht_eve_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_eve_log`;
CREATE TABLE `ht_eve_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `forcode` varchar(20) DEFAULT NULL COMMENT '发送方标识码',
  `seqno` int(11) DEFAULT NULL COMMENT '系统跟踪号',
  `cendt` int(11) DEFAULT NULL COMMENT '交易传输时间',
  `cardnbr` varchar(20) DEFAULT NULL COMMENT '主账号',
  `amount` decimal(16,2) DEFAULT NULL COMMENT '交易金额',
  `crflag` varchar(8) DEFAULT NULL COMMENT '交易金额符号--小于零等于C；大于零等于D',
  `msgtype` int(11) DEFAULT NULL COMMENT '消息类型--提现冲正交易是0420',
  `proccode` int(11) DEFAULT NULL COMMENT '交易类型码',
  `orderno` varchar(55) DEFAULT NULL COMMENT '订单号',
  `tranno` varchar(11) DEFAULT NULL COMMENT '内部交易流水号',
  `reserved` varchar(55) DEFAULT NULL COMMENT '内部保留域',
  `revind` int(11) DEFAULT NULL COMMENT '冲正、撤销标志 --1-已撤销/冲正空或0-正常交易',
  `create_day` varchar(55) DEFAULT NULL COMMENT '日期，记录导入数据所属日期',
  `transtype` int(11) DEFAULT NULL COMMENT '交易类型',
  `create_user_id` int(11) DEFAULT NULL COMMENT '创建用户',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户',
  `del_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标识',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_seqno` (`seqno`) USING BTREE,
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=522577 DEFAULT CHARSET=utf8 COMMENT='存款系统-红包账户-交易明细流水文件';

-- ----------------------------
-- Table structure for ht_fdd_templet
-- ----------------------------
DROP TABLE IF EXISTS `ht_fdd_templet`;
CREATE TABLE `ht_fdd_templet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `templet_id` varchar(12) NOT NULL COMMENT '模版编号',
  `protocol_type` tinyint(1) NOT NULL COMMENT '协议类型',
  `is_active` tinyint(1) NOT NULL COMMENT '启用状态',
  `ca_flag` tinyint(1) DEFAULT '0' COMMENT 'CA认证 0:初始,1:成功,2:失败',
  `certificate_time` int(11) DEFAULT NULL COMMENT '认证时间',
  `file_url` varchar(255) DEFAULT NULL COMMENT '文件存放地址',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_user_id` int(11) NOT NULL COMMENT '创建用户id',
  `create_user_name` varchar(20) NOT NULL COMMENT '创建用户名',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户名',
  `update_user_name` varchar(20) DEFAULT NULL COMMENT '更新用户名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `fdd_templet_unique` (`templet_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=139 DEFAULT CHARSET=utf8 COMMENT='法大大模板管理表';

-- ----------------------------
-- Table structure for ht_freeze_history
-- ----------------------------
DROP TABLE IF EXISTS `ht_freeze_history`;
CREATE TABLE `ht_freeze_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `trx_id` varchar(20) DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `freeze_user` varchar(20) DEFAULT '0',
  `freeze_time` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=538 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_freeze_list
-- ----------------------------
DROP TABLE IF EXISTS `ht_freeze_list`;
CREATE TABLE `ht_freeze_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `OrdId` varchar(20) DEFAULT NULL,
  `borrow_nid` varchar(20) DEFAULT NULL,
  `amount` decimal(10,2) DEFAULT '0.00',
  `RespCode` varchar(10) DEFAULT NULL,
  `UsrCustId` varchar(20) DEFAULT NULL,
  `TrxId` varchar(20) DEFAULT NULL,
  `xfrom` tinyint(1) DEFAULT '0',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `unfreeze_manual` tinyint(1) NOT NULL DEFAULT '0',
  `status` tinyint(1) unsigned DEFAULT '0' COMMENT '冻结标识  0冻结 1解冻',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `ordid` (`OrdId`),
  KEY `borrow_nid` (`borrow_nid`)
) ENGINE=InnoDB AUTO_INCREMENT=4245 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_hjh_accede
-- ----------------------------
DROP TABLE IF EXISTS `ht_hjh_accede`;
CREATE TABLE `ht_hjh_accede` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `accede_order_id` varchar(50) NOT NULL COMMENT '汇计划加入订单号',
  `plan_nid` varchar(30) NOT NULL COMMENT '计划nid',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `user_name` varchar(30) NOT NULL COMMENT '用户名',
  `user_attribute` int(11) NOT NULL DEFAULT '0' COMMENT '用户属性',
  `invite_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '推荐人用户userId',
  `invite_user_name` varchar(100) DEFAULT NULL COMMENT '推荐人用户名',
  `invite_user_attribute` int(11) DEFAULT NULL COMMENT '推荐人用户属性（投资时）',
  `invite_user_regionname` varchar(100) DEFAULT NULL COMMENT '分公司(投资时)',
  `invite_user_branchname` varchar(100) DEFAULT NULL COMMENT '部门(投资时)',
  `invite_user_departmentname` varchar(100) DEFAULT NULL COMMENT '团队(投资时)',
  `accede_account` decimal(11,2) NOT NULL COMMENT '加入金额',
  `already_invest` decimal(11,2) NOT NULL COMMENT '已投资金额',
  `client` tinyint(1) DEFAULT NULL COMMENT '客户端 0PC，1微信，2安卓APP，3IOS，4其他',
  `order_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT ' 0自动投标中 2自动投标成功 3锁定中 5退出中 7已退出 9自动投资异常',
  `count_interest_time` int(10) NOT NULL COMMENT '计息时间',
  `send_status` tinyint(1) DEFAULT NULL COMMENT '协议发送状态0未发送 1已发送',
  `lock_period` int(2) NOT NULL DEFAULT '0' COMMENT '锁定期',
  `commission_status` tinyint(1) DEFAULT NULL COMMENT '提成计算状态:0:未计算,1:已计算',
  `commission_count_time` int(10) DEFAULT NULL COMMENT '提成计算时间',
  `available_invest_account` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '可投金额',
  `end_date` date DEFAULT NULL COMMENT '计划订单结束日期(yyyyMMdd)',
  `credit_complete_flag` tinyint(1) DEFAULT '0' COMMENT '是否完成转让标识(0:未完成转让,1:已完成转让)',
  `frost_account` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '冻结金额（待用）',
  `wait_total` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '待收总额',
  `wait_captical` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '待收本金',
  `wait_interest` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '待收利息',
  `received_total` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '已收总额',
  `received_interest` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '已收利息',
  `received_capital` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '已收本金',
  `quit_time` int(10) DEFAULT NULL COMMENT '退出时间',
  `last_payment_time` int(10) NOT NULL COMMENT '最后回款时间',
  `acctual_payment_time` int(10) NOT NULL COMMENT '实际回款时间',
  `should_pay_total` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '应还总额',
  `should_pay_capital` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '应还本金',
  `should_pay_interest` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '应还利息',
  `expect_apr` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '加入时预期年化收益率(可能跟计划表不一致)',
  `fair_value` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '计划订单当前持有债权(每一小时计算一次)',
  `liquidation_fair_value` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '计划订单清算时债权价值',
  `actual_apr` decimal(10,4) NOT NULL DEFAULT '0.0000' COMMENT '实际年化收益率',
  `invest_counts` int(11) NOT NULL DEFAULT '0' COMMENT '投资笔数',
  `match_dates` tinyint(2) NOT NULL DEFAULT '0' COMMENT '匹配期',
  `lqd_service_fee` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '清算服务费(元)',
  `lqd_service_apr` decimal(11,8) NOT NULL DEFAULT '0.00000000' COMMENT '清算服务费率',
  `invest_service_apr` decimal(11,4) NOT NULL DEFAULT '0.0000' COMMENT '投资服务费率',
  `lqd_progress` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '清算进度',
  `create_user` int(11) NOT NULL COMMENT '创建人id',
  `update_user` int(11) DEFAULT NULL COMMENT '更新人id',
  `del_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标识',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `tender_user_utm_id` int(11) DEFAULT NULL COMMENT '渠道来源当时',
  PRIMARY KEY (`id`),
  UNIQUE KEY `accede_order_id` (`accede_order_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_end_date` (`end_date`),
  KEY `idx_plan_nid` (`plan_nid`),
  KEY `idx_order_status` (`order_status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1758 DEFAULT CHARSET=utf8 COMMENT='汇计划加入明细表';

-- ----------------------------
-- Table structure for ht_hjh_allocation_engine
-- ----------------------------
DROP TABLE IF EXISTS `ht_hjh_allocation_engine`;
CREATE TABLE `ht_hjh_allocation_engine` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '分配引擎表id',
  `plan_nid` varchar(20) NOT NULL COMMENT '计划编号',
  `plan_name` varchar(50) NOT NULL COMMENT '计划名称',
  `config_add_time` int(10) DEFAULT NULL COMMENT '计划专区(是否配置)添加时间',
  `config_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '计划专区(是否配置)状态 0：停用 1：启用',
  `label_id` int(11) NOT NULL DEFAULT '0' COMMENT '标签ID',
  `label_name` varchar(20) NOT NULL COMMENT '标签名称',
  `add_time` int(10) DEFAULT NULL COMMENT '标签添加时间',
  `label_sort` tinyint(1) NOT NULL DEFAULT '0' COMMENT '标签排序 默认值为0(最低)，优先级递增 1,2,3....',
  `transfer_time_sort` tinyint(1) DEFAULT NULL COMMENT '债转时间排序 0：按转让时间降序 1：按转让时间升序',
  `transfer_time_sort_priority` tinyint(1) DEFAULT NULL COMMENT '债转时间排序优先级',
  `apr_sort` tinyint(1) DEFAULT NULL COMMENT '年化收益率排序 0：从低到高 1：从高到低',
  `apr_sort_priority` tinyint(1) DEFAULT NULL COMMENT '年化收益率优先级',
  `actul_pay_sort` tinyint(1) DEFAULT NULL COMMENT '标的实际支付金额排序 0：从小到大 1：从大到小',
  `actul_pay_sort_priority` tinyint(1) DEFAULT NULL COMMENT '年化收益率优先级',
  `invest_progress_sort` tinyint(1) DEFAULT NULL COMMENT '投资进度排序 0：从小到大 1：从大到小',
  `invest_progress_sort_priority` tinyint(1) DEFAULT NULL COMMENT '投资进度优先级',
  `label_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '标签状态 0：停用 1：启用',
  `create_user` int(11) NOT NULL COMMENT '创建人id',
  `update_user` int(11) DEFAULT NULL COMMENT '更新人id',
  `del_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标识',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_label_id` (`label_id`)
) ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=utf8 COMMENT='标的分配引擎表';

-- ----------------------------
-- Table structure for ht_hjh_asset_borrowtype
-- ----------------------------
DROP TABLE IF EXISTS `ht_hjh_asset_borrowtype`;
CREATE TABLE `ht_hjh_asset_borrowtype` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `inst_code` varchar(20) NOT NULL COMMENT '机构编号',
  `asset_type` tinyint(2) NOT NULL DEFAULT '1' COMMENT '资产类型',
  `borrow_cd` tinyint(2) NOT NULL COMMENT '标的类型',
  `is_open` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否开启：0 未开启  1 已开启',
  `auto_add` tinyint(1) NOT NULL DEFAULT '1' COMMENT '自动录标: 0 否 1 是',
  `auto_record` tinyint(1) NOT NULL DEFAULT '1' COMMENT '自动备案：0 否 1 是',
  `auto_bail` tinyint(1) NOT NULL DEFAULT '0' COMMENT '自动保证金：0 否 1 是',
  `auto_audit` tinyint(1) NOT NULL DEFAULT '1' COMMENT '自动初审: 0 否 1 是',
  `auto_review` tinyint(1) NOT NULL DEFAULT '1' COMMENT '自动复审: 0 否 1 是',
  `auto_send_minutes` int(11) DEFAULT NULL COMMENT '自动发标时间间隔(分钟)',
  `auto_review_minutes` int(11) DEFAULT NULL COMMENT '自动复审时间间隔(分钟)',
  `applicant` varchar(20) DEFAULT NULL COMMENT '项目申请人',
  `repay_org_name` varchar(30) DEFAULT NULL COMMENT '垫付机构',
  `remark` varchar(50) DEFAULT '' COMMENT '说明',
  `create_user` int(11) NOT NULL COMMENT '添加人',
  `update_user` int(11) DEFAULT NULL COMMENT '更新用户',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_ht_hjh_asset_borrowtype_pk` (`inst_code`,`asset_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='资产与标的类型映射表';

-- ----------------------------
-- Table structure for ht_hjh_asset_risk_info
-- ----------------------------
DROP TABLE IF EXISTS `ht_hjh_asset_risk_info`;
CREATE TABLE `ht_hjh_asset_risk_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `asset_id` varchar(30) NOT NULL COMMENT '资产编号',
  `amazon_info` varchar(4000) DEFAULT NULL COMMENT '亚马逊店铺信息',
  `ebay_info` varchar(4000) DEFAULT NULL COMMENT '易贝店铺信息',
  `jingdong_info` varchar(4000) DEFAULT NULL COMMENT '京东店铺信息',
  `taobao_info` varchar(4000) DEFAULT NULL COMMENT '淘宝店铺信息',
  `tianmao_info` varchar(4000) DEFAULT NULL COMMENT '天猫店铺信息',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flg` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_asset_id` (`asset_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='汇计划第三方推送资产风控信息表';

-- ----------------------------
-- Table structure for ht_hjh_asset_type
-- ----------------------------
DROP TABLE IF EXISTS `ht_hjh_asset_type`;
CREATE TABLE `ht_hjh_asset_type` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `inst_code` varchar(20) NOT NULL COMMENT '机构编号',
  `asset_type` tinyint(2) DEFAULT NULL,
  `asset_type_name` varchar(30) NOT NULL COMMENT '资产类型名称',
  `status` tinyint(1) DEFAULT '0' COMMENT '状态(0:启用,1:关闭)',
  `create_user` int(11) DEFAULT NULL COMMENT '添加人',
  `update_user` int(11) DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标识',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_ht_hjh_asset_type_pk` (`inst_code`,`asset_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='资产来源的产品（资产）类型配置表';

-- ----------------------------
-- Table structure for ht_hjh_bail_config
-- ----------------------------
DROP TABLE IF EXISTS `ht_hjh_bail_config`;
CREATE TABLE `ht_hjh_bail_config` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `inst_code` varchar(20) DEFAULT NULL COMMENT '机构编号',
  `bail_tatol` decimal(13,2) DEFAULT '0.00' COMMENT '保证金金额',
  `bail_rate` int(11) DEFAULT '0' COMMENT '保证金比例',
  `timeStart` varchar(10) DEFAULT NULL COMMENT '授信周期开始',
  `timeEnd` varchar(10) DEFAULT NULL COMMENT '授信周期结束',
  `new_credit_line` decimal(13,2) DEFAULT '0.00' COMMENT '新增授信额度（元）',
  `loan_credit_line` decimal(13,2) DEFAULT '0.00' COMMENT '在贷授信额度（元）',
  `day_mark_line` decimal(13,2) DEFAULT '0.00' COMMENT '日推标额度（元）',
  `month_mark_line` decimal(13,2) DEFAULT '0.00' COMMENT '月推标额度（元）',
  `push_mark_line` decimal(15,2) DEFAULT '0.00' COMMENT '发标额度上限（元）',
  `loan_mark_line` decimal(13,2) DEFAULT '0.00' COMMENT '发标已发额度（元）',
  `remain_mark_line` decimal(13,2) DEFAULT '0.00' COMMENT '发标额度余额（元）',
  `repayed_capital` decimal(13,2) DEFAULT '0.00' COMMENT '已还本金',
  `his_loan_total` decimal(13,2) DEFAULT '0.00' COMMENT '历史标的放款总额',
  `cyc_loan_total` decimal(13,2) DEFAULT '0.00' COMMENT '周期内发标已发额度',
  `loan_balance` decimal(13,2) DEFAULT '0.00' COMMENT '在贷余额',
  `is_accumulate` tinyint(1) DEFAULT '0' COMMENT '未用额度是否累计 0：否 1：是',
  `remark` varchar(100) DEFAULT '' COMMENT '说明',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `del_flg` tinyint(1) DEFAULT '0' COMMENT '删除标识',
  PRIMARY KEY (`id`),
  KEY `idx_inst_code` (`inst_code`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COMMENT='现金贷资产方保证金信息配置';

-- ----------------------------
-- Table structure for ht_hjh_bail_config_info
-- ----------------------------
DROP TABLE IF EXISTS `ht_hjh_bail_config_info`;
CREATE TABLE `ht_hjh_bail_config_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `inst_code` varchar(20) DEFAULT NULL COMMENT '机构编号',
  `borrow_style` varchar(20) DEFAULT '0' COMMENT '还款类型：month 等额本息 end 按月计息，到期还本还息 endmonth 先息后本 endday 按天计息，到期还本息 principal 等额本金',
  `is_new_credit` tinyint(1) DEFAULT '0' COMMENT '新增授信校验 0：未勾选 1：勾选',
  `is_loan_credit` tinyint(1) DEFAULT '0' COMMENT '在贷余额授信校验 0：未勾选 1：勾选',
  `repay_capital_type` tinyint(1) DEFAULT '0' COMMENT '等额本息保证金的回滚方式 0：到期回滚 1：分期回滚 2：不回滚',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `del_flg` tinyint(1) DEFAULT '0' COMMENT '删除标识：机构未配置该还款方式的设1',
  PRIMARY KEY (`id`),
  KEY `idx_inst_code` (`inst_code`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8 COMMENT='现金贷资产方保证金回滚方式';

-- ----------------------------
-- Table structure for ht_hjh_bail_config_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_hjh_bail_config_log`;
CREATE TABLE `ht_hjh_bail_config_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `inst_code` varchar(20) DEFAULT NULL COMMENT '机构编号',
  `modify_column` varchar(20) DEFAULT '' COMMENT '修改字段',
  `before_value` varchar(50) DEFAULT '' COMMENT '修改前值',
  `after_value` varchar(50) DEFAULT '' COMMENT '修改后值',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=414 DEFAULT CHARSET=utf8 COMMENT='现金贷资产方保证金信息配置日志表';

-- ----------------------------
-- Table structure for ht_hjh_debt_credit
-- ----------------------------
DROP TABLE IF EXISTS `ht_hjh_debt_credit`;
CREATE TABLE `ht_hjh_debt_credit` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '债转用户ID',
  `user_name` varchar(20) DEFAULT NULL COMMENT '转让用户名',
  `plan_nid` varchar(30) NOT NULL DEFAULT '' COMMENT '计划编号',
  `plan_nid_new` varchar(30) DEFAULT '' COMMENT '新计划编号',
  `plan_order_id` varchar(30) DEFAULT '' COMMENT '计划加入订单号(旧计划)',
  `borrow_nid` varchar(30) DEFAULT '' COMMENT '借款编号',
  `borrow_name` varchar(255) DEFAULT '' COMMENT '标的名称',
  `borrow_apr` decimal(11,2) DEFAULT '0.00' COMMENT '原标年化利率',
  `borrow_style` varchar(50) DEFAULT '' COMMENT '原标还款方式',
  `borrow_period` tinyint(1) DEFAULT '0' COMMENT '原标项目期限',
  `inst_code` varchar(20) DEFAULT '' COMMENT '原标机构编号',
  `asset_type` tinyint(1) DEFAULT '0' COMMENT '原标资产类型',
  `project_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '原标项目类型(0汇保贷 1汇典贷 2汇小贷 3汇车贷 4新手标)',
  `actual_apr` decimal(11,2) DEFAULT '0.00' COMMENT '清算后债权实际年华收益率',
  `invest_order_id` varchar(30) NOT NULL DEFAULT '' COMMENT '原始标的投资订单号',
  `sell_order_id` varchar(30) NOT NULL DEFAULT '' COMMENT '原投资订单号',
  `credit_nid` varchar(30) NOT NULL DEFAULT '' COMMENT '债转编号',
  `credit_status` tinyint(1) DEFAULT '0' COMMENT '转让状态：0.承接中，1.部分承接，2完全承接，3承接终止',
  `repay_status` tinyint(1) DEFAULT '0' COMMENT '债转还款状态 0 未还款 1还款中 2还款完成',
  `is_liquidates` tinyint(1) DEFAULT '0' COMMENT '是否已清算(0:未清算,1:已清算)',
  `hold_days` int(11) DEFAULT '0' COMMENT '持有天数',
  `remain_days` int(11) DEFAULT '0' COMMENT '剩余天数',
  `during_days` int(11) DEFAULT '0' COMMENT '当前期计息天数',
  `assign_period` int(11) DEFAULT '0' COMMENT '承接原项目所在期数',
  `liquidates_period` int(11) DEFAULT '0' COMMENT '清算时所在期数',
  `credit_period` int(11) DEFAULT '0' COMMENT '债转期数',
  `repay_period` int(11) DEFAULT '0' COMMENT '已还款期数',
  `credit_term` int(11) DEFAULT '0' COMMENT '债转期限',
  `liquidation_fair_value` decimal(11,2) DEFAULT '0.00' COMMENT '清算时公允价值',
  `liquidates_capital` decimal(11,2) DEFAULT '0.00' COMMENT '清算总本金',
  `credit_account` decimal(11,2) DEFAULT '0.00' COMMENT '债转总额',
  `credit_capital` decimal(11,2) DEFAULT '0.00' COMMENT '债转总本金',
  `credit_interest` decimal(11,2) DEFAULT '0.00' COMMENT '债转总利息',
  `credit_interest_advance` decimal(11,2) DEFAULT '0.00' COMMENT '垫付总利息',
  `credit_delay_interest` decimal(11,2) DEFAULT '0.00' COMMENT '清算时延期利息',
  `credit_late_interest` decimal(11,2) DEFAULT '0.00' COMMENT '清算时逾期利息',
  `credit_account_assigned` decimal(11,2) DEFAULT '0.00' COMMENT '已承接总金额（此金额为本金加利息与还款相关，同垫付利息无关）',
  `credit_capital_assigned` decimal(11,2) DEFAULT '0.00' COMMENT '已承接本金',
  `credit_interest_assigned` decimal(11,2) DEFAULT '0.00' COMMENT '已承接待还总利息',
  `credit_interest_advance_assigned` decimal(11,2) DEFAULT '0.00' COMMENT '已承接垫付总利息',
  `credit_delay_interest_assigned` decimal(11,2) DEFAULT '0.00' COMMENT '承接已垫付的延期利息',
  `credit_late_interest_assigned` decimal(11,2) DEFAULT '0.00' COMMENT '承接已垫付的逾期利息',
  `credit_account_wait` decimal(11,2) DEFAULT '0.00' COMMENT '待承接总金额',
  `credit_capital_wait` decimal(11,2) DEFAULT '0.00' COMMENT '待承接本金',
  `credit_interest_wait` decimal(11,2) DEFAULT '0.00' COMMENT '待承接利息',
  `credit_interest_advance_wait` decimal(11,2) DEFAULT '0.00' COMMENT '已承接垫付总利息',
  `credit_income` decimal(11,2) DEFAULT '0.00' COMMENT '总收入，本金+垫付利息',
  `credit_service_fee` decimal(11,2) DEFAULT '0.00' COMMENT '服务费',
  `credit_price` decimal(11,2) DEFAULT '0.00' COMMENT '出让价格',
  `repay_account` decimal(11,2) DEFAULT '0.00' COMMENT '已还款总额',
  `repay_capital` decimal(11,2) DEFAULT '0.00' COMMENT '已还本金',
  `repay_interest` decimal(11,2) DEFAULT '0.00' COMMENT '已还利息',
  `repay_account_wait` decimal(11,2) DEFAULT '0.00' COMMENT '待还款本息',
  `repay_capital_wait` decimal(11,2) DEFAULT '0.00' COMMENT '待还款本金',
  `repay_interest_wait` decimal(11,2) DEFAULT '0.00' COMMENT '待还款利息',
  `credit_repay_end_time` int(11) DEFAULT '0' COMMENT '债转计划最后还款时间',
  `credit_repay_last_time` int(11) DEFAULT '0' COMMENT '上次还款时间',
  `credit_repay_next_time` int(11) DEFAULT '0' COMMENT '下次还款时间',
  `credit_repay_yes_time` int(11) DEFAULT '0' COMMENT '最终实际还款时间',
  `end_time` int(11) DEFAULT '0' COMMENT '债转结束时间（全部承接或者提前还款导致）',
  `assign_num` int(11) DEFAULT '0' COMMENT '承接次数',
  `client` tinyint(1) DEFAULT '0' COMMENT '债转发起客户端,0PC,1微官网,2Android,3iOS,4其他',
  `source_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否原始债权 0非原始 1原始',
  `credit_times` int(10) DEFAULT '0' COMMENT '债权出让次数',
  `is_late_credit` tinyint(1) DEFAULT '0' COMMENT '是否逾期之后的债权',
  `label_id` int(10) DEFAULT '0' COMMENT '标签ID',
  `label_name` varchar(50) DEFAULT '' COMMENT '标签名称',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标识 0 未删除 1已删除',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建用户id',
  `create_user_name` varchar(20) DEFAULT '' COMMENT '创建用户名',
  `update_user_id` int(11) DEFAULT '0' COMMENT '更新用户id',
  `update_user_name` varchar(20) DEFAULT NULL COMMENT '更新用户名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_credit_nid` (`credit_nid`),
  KEY `idx_plan_order_id` (`plan_order_id`),
  KEY `idx_invest_order_id` (`invest_order_id`),
  KEY `idx_borrow_nid` (`borrow_nid`)
) ENGINE=InnoDB AUTO_INCREMENT=32113 DEFAULT CHARSET=utf8 COMMENT='汇计划债转表';

-- ----------------------------
-- Table structure for ht_hjh_debt_credit_repay
-- ----------------------------
DROP TABLE IF EXISTS `ht_hjh_debt_credit_repay`;
CREATE TABLE `ht_hjh_debt_credit_repay` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '承接用户ID',
  `user_name` varchar(20) NOT NULL COMMENT '承接用户名',
  `credit_user_id` int(11) NOT NULL COMMENT '出让人用户ID',
  `credit_user_name` varchar(20) NOT NULL COMMENT '出让人用户名',
  `invest_order_id` varchar(30) DEFAULT NULL COMMENT '原始标的投资订单号',
  `sell_order_id` varchar(30) NOT NULL COMMENT '债转原始订单号',
  `repay_status` int(11) NOT NULL DEFAULT '0' COMMENT '还款状态 0未还款 1已还款',
  `borrow_nid` varchar(30) NOT NULL COMMENT '原标标号',
  `repay_period` int(11) NOT NULL DEFAULT '1' COMMENT '原标还款期数',
  `credit_nid` varchar(30) NOT NULL COMMENT '债转标号',
  `assign_plan_nid` varchar(30) DEFAULT NULL COMMENT '承接计划nid',
  `assign_plan_order_id` varchar(30) DEFAULT NULL COMMENT '承接计划订单号',
  `assign_order_id` varchar(30) NOT NULL COMMENT '债转承接订单号',
  `auth_code` varchar(30) NOT NULL DEFAULT '' COMMENT '投资人投标成功的授权号',
  `repay_account` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '应还总额',
  `repay_capital` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '应还本金',
  `repay_interest` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '应还利息',
  `repay_account_wait` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '待还总额',
  `repay_capital_wait` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '待还本金',
  `repay_interest_wait` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '待还利息',
  `repay_account_yes` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已还总额',
  `repay_capital_yes` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已还本金',
  `repay_interest_yes` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已还利息',
  `assign_repay_end_time` int(11) NOT NULL DEFAULT '0' COMMENT '最后还款日',
  `assign_repay_last_time` int(11) NOT NULL DEFAULT '0' COMMENT '上次还款时间',
  `assign_repay_next_time` int(11) NOT NULL DEFAULT '0' COMMENT '下次还款时间',
  `assign_repay_yes_time` int(11) NOT NULL DEFAULT '0' COMMENT '最终实际还款时间',
  `assign_repay_time` int(11) DEFAULT '0' COMMENT '还款时间',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '是否删除 0未删除 1已删除',
  `assign_repay_period` int(11) NOT NULL DEFAULT '1' COMMENT '债权还款期数',
  `assign_create_date` int(11) NOT NULL DEFAULT '0' COMMENT '认购日期',
  `client` int(11) DEFAULT '0' COMMENT '承接客户端 0PC,1微官网,2Android,3iOS,4其他',
  `manage_fee` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '管理费',
  `liquidates_service_fee` decimal(11,2) DEFAULT '0.00' COMMENT '清算时收取的服务费',
  `unique_nid` varchar(60) NOT NULL DEFAULT '0' COMMENT '唯一nid',
  `credit_repay_order_id` varchar(50) DEFAULT NULL COMMENT '债转还款订单号',
  `credit_repay_order_date` varchar(20) DEFAULT NULL COMMENT '债转还款订单日期',
  `advance_status` int(11) NOT NULL COMMENT '是否提前还款 0正常还款 1提前还款 2延期还款 3逾期还款',
  `advance_days` int(11) DEFAULT '0' COMMENT '提前还款天数',
  `repay_advance_interest` decimal(11,2) DEFAULT NULL COMMENT '借款人提前还款利息(已加罚息)',
  `late_days` int(11) DEFAULT '0' COMMENT '逾期天数',
  `repay_late_interest` decimal(11,2) DEFAULT '0.00' COMMENT '借款人还款逾期利息',
  `delay_days` int(11) DEFAULT '0' COMMENT '延期天数',
  `repay_delay_interest` decimal(11,2) DEFAULT '0.00' COMMENT '借款人还款延期利息',
  `receive_account_yes` decimal(11,2) DEFAULT '0.00' COMMENT '投资人收到的还款本息',
  `receive_capital_yes` decimal(11,2) DEFAULT '0.00' COMMENT '投资人收到的还款本金',
  `receive_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '投资人收到的还款利息',
  `receive_advance_interest` decimal(11,2) DEFAULT '0.00' COMMENT '投资人收取提前还款利息',
  `receive_late_interest` decimal(11,2) DEFAULT '0.00' COMMENT '投资人收取逾期利息',
  `receive_delay_interest` decimal(11,2) DEFAULT '0.00' COMMENT '投资人收取延期利息',
  `receive_advance_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '投资人已收取提前还款利息',
  `receive_late_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '投资人已收取逾期利息',
  `receive_delay_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '投资人已收取延期利息',
  `credit_times` int(10) DEFAULT '0' COMMENT '出让次数',
  `debt_status` tinyint(1) DEFAULT '0' COMMENT '债权是否结束状态(0:否,1:是)还款时用',
  `create_user_id` int(11) NOT NULL COMMENT '创建用户id',
  `create_user_name` varchar(20) NOT NULL COMMENT '创建用户名',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户名',
  `update_user_name` varchar(20) DEFAULT NULL COMMENT '更新用户名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `repay_advance_penalty_interest` decimal(11,2) DEFAULT '0.00' COMMENT '提前还款罚息',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_nid` (`unique_nid`) COMMENT '唯一nid',
  KEY `idx_invest_order_id` (`invest_order_id`),
  KEY `idx_borrow_nid` (`borrow_nid`),
  KEY `idx_assign_plan_order_id` (`assign_plan_order_id`),
  KEY `idx_assign_order_id` (`assign_order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1327 DEFAULT CHARSET=utf8 COMMENT='汇计划债转还款表';

-- ----------------------------
-- Table structure for ht_hjh_debt_credit_tender
-- ----------------------------
DROP TABLE IF EXISTS `ht_hjh_debt_credit_tender`;
CREATE TABLE `ht_hjh_debt_credit_tender` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '承接用户userId',
  `user_name` varchar(20) NOT NULL COMMENT '承接用户名',
  `credit_user_id` int(11) NOT NULL COMMENT '出让人userId',
  `credit_user_name` varchar(20) DEFAULT NULL COMMENT '出让人用户名',
  `borrow_nid` varchar(30) NOT NULL COMMENT '原标标号',
  `repay_period` int(11) NOT NULL DEFAULT '0' COMMENT '原标的已还款期数',
  `credit_nid` varchar(30) NOT NULL COMMENT '债转标号',
  `invest_order_id` varchar(30) DEFAULT NULL COMMENT '原始标的投资订单号',
  `sell_order_id` varchar(30) NOT NULL COMMENT '原标投资订单号',
  `liquidates_plan_nid` varchar(30) NOT NULL COMMENT '清算债权计划编号',
  `liquidates_plan_order_id` varchar(30) NOT NULL COMMENT '清算债权计划计划加入订单号',
  `assign_plan_nid` varchar(30) NOT NULL COMMENT '承接的计划编号nid',
  `assign_plan_order_id` varchar(30) NOT NULL COMMENT '承接的计划订单号',
  `assign_order_id` varchar(30) NOT NULL COMMENT '承接订单号',
  `assign_order_date` varchar(20) NOT NULL COMMENT '承接日期',
  `auth_code` varchar(30) NOT NULL DEFAULT '' COMMENT '投资人投标成功的授权号',
  `assign_capital` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '承接本金',
  `assign_account` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '承接总额',
  `assign_interest` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '承接利息',
  `assign_repay_delay_interest` decimal(11,2) DEFAULT '0.00' COMMENT '承接的延期利息',
  `assign_repay_late_interest` decimal(11,2) DEFAULT '0.00' COMMENT '承接的逾期利息',
  `assign_interest_advance` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '承接垫付利息',
  `assign_price` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '购买价格',
  `assign_pay` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '支付金额',
  `repay_account_yes` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已还总额',
  `repay_capital_yes` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已还本金',
  `repay_interest_yes` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已还利息',
  `repay_account_wait` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '待还总额',
  `repay_capital_wait` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '待还本金',
  `repay_interest_wait` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '待还利息',
  `assign_repay_end_time` int(11) NOT NULL DEFAULT '0' COMMENT '最后还款日',
  `assign_repay_last_time` int(11) NOT NULL DEFAULT '0' COMMENT '上次还款时间',
  `assign_repay_next_time` int(11) NOT NULL DEFAULT '0' COMMENT '下次还款时间',
  `assign_repay_yes_time` int(11) NOT NULL DEFAULT '0' COMMENT '最终实际还款时间',
  `assign_repay_period` int(11) NOT NULL DEFAULT '1' COMMENT '债权承接期数',
  `assign_type` tinyint(1) DEFAULT '0' COMMENT '承接类型 0 自动承接 1手动承接',
  `tender_type` tinyint(2) DEFAULT NULL COMMENT '投资类型 0投资1复投',
  `status` int(11) DEFAULT '0' COMMENT '状态 0未还款 1已还款',
  `assign_service_apr` decimal(11,8) NOT NULL DEFAULT '0.00000000' COMMENT '服务费率',
  `assign_service_fee` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '服务费=交易金额*服务费率',
  `client` int(11) DEFAULT '0' COMMENT '客户端,0PC,1微官网,2Android,3iOS,4其他',
  `del_flag` tinyint(1) DEFAULT '1' COMMENT '是否有效 0无效 1有效',
  `create_user_id` int(11) NOT NULL COMMENT '创建用户id',
  `create_user_name` varchar(20) NOT NULL COMMENT '创建用户名',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户名',
  `update_user_name` varchar(20) DEFAULT NULL COMMENT '更新用户名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `assign_nid` (`assign_order_id`) COMMENT '承接订单号',
  KEY `credit_nid` (`credit_nid`) COMMENT '债转编号',
  KEY `credit_tender_nid` (`sell_order_id`) COMMENT '债转原标投资订单号',
  KEY `user_id` (`user_id`) COMMENT '承接用户id',
  KEY `credit_user_id` (`credit_user_id`) COMMENT '债转出让人用户id',
  KEY `bid_nid` (`borrow_nid`) COMMENT '原标标号'
) ENGINE=InnoDB AUTO_INCREMENT=759 DEFAULT CHARSET=utf8 COMMENT='汇计划债转投资表';

-- ----------------------------
-- Table structure for ht_hjh_debt_credit_tender_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_hjh_debt_credit_tender_log`;
CREATE TABLE `ht_hjh_debt_credit_tender_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户名称',
  `user_name` varchar(20) NOT NULL COMMENT '承接用户名',
  `credit_user_id` int(11) DEFAULT NULL COMMENT '出让人id',
  `credit_user_name` varchar(20) DEFAULT NULL COMMENT '出让人用户名',
  `status` int(11) DEFAULT '0' COMMENT '状态 0初始 1成功 2失败',
  `borrow_nid` varchar(30) NOT NULL COMMENT '原标标号',
  `repay_period` int(11) NOT NULL DEFAULT '0' COMMENT '原标的已还款期数',
  `sell_order_id` varchar(30) NOT NULL COMMENT '原标投资订单号',
  `credit_nid` varchar(30) NOT NULL COMMENT '债转标号',
  `invest_order_id` varchar(30) DEFAULT NULL COMMENT '原始标的投资订单号',
  `liquidates_plan_nid` varchar(30) NOT NULL,
  `liquidates_plan_order_id` varchar(30) NOT NULL,
  `assign_plan_nid` varchar(30) DEFAULT NULL COMMENT '承接计划nid，同承接计划nid',
  `assign_plan_order_id` varchar(30) DEFAULT NULL COMMENT '计划订单号',
  `assign_order_id` varchar(30) NOT NULL COMMENT '认购单号',
  `assign_order_date` varchar(20) NOT NULL COMMENT '冻结订单日期',
  `assign_capital` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '投资本金',
  `assign_account` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '回收总额',
  `assign_interest` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '债转利息',
  `assign_repay_delay_interest` decimal(11,2) DEFAULT '0.00' COMMENT '承接的延期利息',
  `assign_repay_late_interest` decimal(11,2) DEFAULT '0.00' COMMENT '承接的逾期利息',
  `assign_interest_advance` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '垫付利息',
  `assign_price` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '购买价格',
  `assign_pay` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '支付金额',
  `assign_repay_account` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已还总额',
  `assign_repay_capital` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已还本金',
  `assign_repay_interest` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已还利息',
  `assign_repay_end_time` int(11) NOT NULL DEFAULT '0' COMMENT '最后还款日',
  `assign_repay_last_time` int(11) NOT NULL DEFAULT '0' COMMENT '上次还款时间',
  `assign_repay_next_time` int(11) NOT NULL DEFAULT '0' COMMENT '下次还款时间',
  `assign_repay_yes_time` int(11) NOT NULL DEFAULT '0' COMMENT '最终实际还款时间',
  `assign_repay_period` int(11) NOT NULL DEFAULT '1' COMMENT '债权承接期数',
  `assign_service_apr` decimal(11,8) DEFAULT '0.00000000' COMMENT '服务费率（清算1小时1变）',
  `assign_service_fee` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '服务费=交易金额*服务费率 ',
  `assign_type` tinyint(1) DEFAULT '0' COMMENT '承接类型 0 自动承接 1手动承接',
  `add_ip` varchar(20) DEFAULT NULL COMMENT 'create ip',
  `client` int(11) DEFAULT NULL COMMENT '客户端',
  `create_user_id` int(11) NOT NULL COMMENT '创建用户id',
  `create_user_name` varchar(20) NOT NULL COMMENT '创建用户名',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户名',
  `update_user_name` varchar(20) DEFAULT NULL COMMENT '更新用户名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `assign_nid` (`assign_order_id`),
  KEY `credit_tender_nid` (`sell_order_id`) USING BTREE COMMENT '原标投资订单号',
  KEY `user_id` (`user_id`) USING BTREE COMMENT '承接用户id',
  KEY `credit_nid` (`credit_nid`) USING BTREE COMMENT '债转编号',
  KEY `credit_user_id` (`credit_user_id`) USING BTREE COMMENT '原标投资用户id',
  KEY `bid_nid` (`borrow_nid`) USING BTREE COMMENT '原标标号'
) ENGINE=InnoDB AUTO_INCREMENT=809 DEFAULT CHARSET=utf8 COMMENT='汇计划债权承接log表，相当于borrow_credit_tender表';

-- ----------------------------
-- Table structure for ht_hjh_debt_detail
-- ----------------------------
DROP TABLE IF EXISTS `ht_hjh_debt_detail`;
CREATE TABLE `ht_hjh_debt_detail` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(11) NOT NULL COMMENT '投资用户编号',
  `user_name` varchar(20) NOT NULL COMMENT '投资者用户名',
  `borrow_user_id` int(11) NOT NULL COMMENT '借款用户编号',
  `borrow_user_name` varchar(20) NOT NULL COMMENT '借款者用户名',
  `borrow_nid` varchar(50) NOT NULL COMMENT '项目标的号',
  `borrow_name` varchar(255) NOT NULL COMMENT '原标标题',
  `borrow_apr` decimal(6,2) NOT NULL DEFAULT '0.00' COMMENT '原标年化利率',
  `borrow_period` int(10) NOT NULL DEFAULT '0' COMMENT '借款期限',
  `borrow_style` varchar(100) NOT NULL COMMENT '借款类型',
  `plan_nid` varchar(30) NOT NULL COMMENT '计划nid',
  `plan_order_id` varchar(30) NOT NULL COMMENT '计划订单号',
  `invest_order_id` varchar(30) DEFAULT NULL COMMENT '原始标的投资订单号',
  `order_id` varchar(30) DEFAULT NULL COMMENT '订单号(原始投资:投资订单号,承接债权:承接订单号)',
  `credit_nid` varchar(30) DEFAULT NULL COMMENT '上次债转编号',
  `order_date` varchar(30) DEFAULT NULL COMMENT '订单日期',
  `order_type` tinyint(1) DEFAULT NULL COMMENT '订单类型 0 汇添金专属项目投资 1 债权承接',
  `source_type` tinyint(1) DEFAULT '1' COMMENT '是否原始债权 0非原始 1原始',
  `account` decimal(11,2) DEFAULT '0.00' COMMENT '投资金额或者债权承接金额',
  `loan_time` int(11) NOT NULL,
  `loan_capital` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '放款本金（应还本金）',
  `loan_interest` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '放款利息（应还利息）',
  `repay_period` tinyint(2) NOT NULL DEFAULT '1' COMMENT '还款期数',
  `repay_time` int(11) NOT NULL COMMENT '估计还款时间',
  `repay_action_time` int(11) NOT NULL COMMENT '实际还款时间',
  `repay_capital_yes` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已还本金',
  `repay_interest_yes` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '已还利息',
  `repay_capital_wait` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '未收本金',
  `repay_interest_wait` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '未收利息',
  `repay_status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '还款状态 0未还款 1已还款',
  `manage_fee` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '账户管理费',
  `service_fee` decimal(11,2) DEFAULT '0.00' COMMENT '债权收取的服务费',
  `advance_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否提前还款 0正常还款 1提前还款 2延期还款 3逾期还款',
  `advance_days` int(11) NOT NULL DEFAULT '0' COMMENT '罚息天数',
  `advance_interest` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '罚息总额',
  `late_days` int(11) NOT NULL DEFAULT '0' COMMENT '逾期天数',
  `late_interest` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '逾期利息',
  `late_interest_assigned` decimal(11,2) DEFAULT '0.00' COMMENT '承接垫付的逾期利息',
  `delay_days` int(11) NOT NULL DEFAULT '0' COMMENT '延期天数',
  `delay_interest` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '延期利息',
  `delay_interest_assigned` decimal(11,2) DEFAULT '0.00' COMMENT '承接垫付的延期利息',
  `repay_order_id` varchar(20) DEFAULT NULL COMMENT '还款订单号',
  `repay_order_date` varchar(20) DEFAULT NULL,
  `expire_fair_value` decimal(11,2) DEFAULT '0.00' COMMENT '到期公允价值',
  `last_liquidation_time` int(11) DEFAULT NULL COMMENT '债权上次被清算时间',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '债权是否有效（0失效 1有效）',
  `client` tinyint(1) DEFAULT '0' COMMENT '客户端0PC，1微信2安卓APP，3IOS APP，4其他',
  `remark` varchar(300) DEFAULT NULL COMMENT '备注',
  `credit_times` int(10) DEFAULT '0' COMMENT '债权出让次数',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '清算标识 0未清算 1清算',
  `create_user_id` int(11) NOT NULL COMMENT '创建用户id',
  `create_user_name` varchar(20) NOT NULL COMMENT '创建用户名',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户名',
  `update_user_name` varchar(20) DEFAULT NULL COMMENT '更新用户名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `borrow_nid` (`borrow_nid`) USING BTREE,
  KEY `idx_user_id` (`user_id`),
  KEY `idx_plan_nid` (`plan_nid`),
  KEY `idx_plan_order_id` (`plan_order_id`),
  KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7880 DEFAULT CHARSET=utf8 COMMENT='债权详情表';

-- ----------------------------
-- Table structure for ht_hjh_inst_config
-- ----------------------------
DROP TABLE IF EXISTS `ht_hjh_inst_config`;
CREATE TABLE `ht_hjh_inst_config` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `inst_code` varchar(20) DEFAULT NULL COMMENT '机构编号',
  `inst_name` varchar(20) DEFAULT NULL COMMENT '机构名称',
  `inst_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '机构类别(0:借款机构,1:投资机构)',
  `capital_toplimit` decimal(12,2) DEFAULT '0.00' COMMENT '额度上限',
  `cooperative_agency` varchar(30) DEFAULT NULL COMMENT '担保公司',
  `commission_fee` decimal(12,2) NOT NULL DEFAULT '1.00' COMMENT '提现手续费',
  `repay_capital_type` tinyint(1) DEFAULT '0' COMMENT '等额本息保证金的回滚方式 0：到期回滚 1：分期回滚',
  `remark` varchar(100) DEFAULT '' COMMENT '备注说明',
  `del_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标识',
  `create_user` int(11) DEFAULT NULL COMMENT '添加人',
  `update_user` int(11) DEFAULT NULL COMMENT '更新用户',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_inst_code` (`inst_code`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COMMENT='现金贷资产方信息配置';

-- ----------------------------
-- Table structure for ht_hjh_label
-- ----------------------------
DROP TABLE IF EXISTS `ht_hjh_label`;
CREATE TABLE `ht_hjh_label` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `label_name` varchar(50) NOT NULL DEFAULT '' COMMENT '标签名称,最长不超过10个字符',
  `label_term_start` int(11) DEFAULT NULL COMMENT '期限开始时间',
  `label_term_end` int(11) DEFAULT NULL COMMENT '期限结束时间',
  `label_term_type` varchar(20) NOT NULL DEFAULT '日' COMMENT '期限类型--日、月',
  `label_apr_start` decimal(11,2) DEFAULT NULL COMMENT '标的实际利率开始范围',
  `label_apr_end` decimal(11,2) DEFAULT NULL COMMENT '标的实际利率的结束范围',
  `borrow_style` varchar(50) DEFAULT '' COMMENT '还款方式',
  `borrow_style_name` varchar(50) DEFAULT '' COMMENT '还款方式名称',
  `label_payment_account_start` decimal(10,2) DEFAULT NULL COMMENT '标的实际支付金额',
  `label_payment_account_end` decimal(10,2) DEFAULT NULL COMMENT '标的实际支付金额',
  `inst_code` varchar(50) DEFAULT '' COMMENT '资产来源,机构编号',
  `inst_name` varchar(50) DEFAULT '' COMMENT '资产来源,机构名称',
  `asset_type` tinyint(3) unsigned DEFAULT NULL COMMENT '机构产品类型',
  `asset_type_name` varchar(50) DEFAULT '' COMMENT '机构产品类型名称',
  `project_type` tinyint(3) DEFAULT NULL COMMENT '项目类型 0汇保贷 1汇典贷 2汇小贷 3汇车贷 4新手标',
  `project_type_name` varchar(50) DEFAULT '' COMMENT '项目类型名称',
  `is_credit` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '标的是否发生债转 0:否 1：是',
  `is_late` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '标的是否逾期  0:否 1：是',
  `credit_sum_max` int(11) DEFAULT NULL COMMENT '债转次数不超过',
  `push_time_start` time DEFAULT NULL COMMENT '推送时间开始范围',
  `push_time_end` time DEFAULT NULL COMMENT '推送时间结束范围',
  `remaining_days_start` int(11) DEFAULT NULL COMMENT '剩余天数开始范围',
  `remaining_days_end` int(11) DEFAULT NULL COMMENT '剩余天数结束范围',
  `label_state` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '启用状态 0：停用 1：启用',
  `create_user_id` int(11) DEFAULT NULL COMMENT '创建用户',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户',
  `del_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标识',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_inst_code` (`inst_code`)
) ENGINE=InnoDB AUTO_INCREMENT=217 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_hjh_plan
-- ----------------------------
DROP TABLE IF EXISTS `ht_hjh_plan`;
CREATE TABLE `ht_hjh_plan` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '计划id',
  `plan_nid` varchar(20) NOT NULL COMMENT '计划编号',
  `plan_name` varchar(50) NOT NULL COMMENT '计划名称',
  `lock_period` tinyint(2) NOT NULL DEFAULT '0' COMMENT '锁定期(天)',
  `is_month` tinyint(2) NOT NULL DEFAULT '0' COMMENT '默认0 天标，1 月标',
  `expect_apr` decimal(10,2) NOT NULL COMMENT '预期年化利率',
  `min_investment` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '最低加入金额',
  `max_investment` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '最高加入金额',
  `investment_increment` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '投资增量',
  `available_invest_account` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '计划可投金额，之前计划有总金额，现在只有剩余能投金额',
  `repay_wait_all` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '待还总额',
  `plan_invest_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '投资状态 0 全部；1 启用；2 关闭',
  `plan_display_status` tinyint(2) DEFAULT '1' COMMENT '显示状态字段 1显示 2 隐藏',
  `add_time` int(10) DEFAULT NULL COMMENT '添加时间',
  `borrow_style` varchar(100) DEFAULT '0' COMMENT '还款方式',
  `coupon_config` varchar(10) DEFAULT '0' COMMENT '是否可用券：0 不可用 1 体验金 2 加息券 3 代金券',
  `plan_concept` longtext NOT NULL COMMENT '计划介绍',
  `plan_principle` longtext NOT NULL COMMENT '计划原理',
  `safeguard_measures` longtext NOT NULL COMMENT '风控保障措施',
  `margin_measures` longtext NOT NULL COMMENT '风险保证金措施',
  `normal_questions` longtext NOT NULL COMMENT '常见问题',
  `join_total` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '累积加入总额',
  `plan_wait_captical` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '待还本金',
  `plan_wait_interest` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '待还利息',
  `repay_total` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '已还总额',
  `plan_repay_interest` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '已还利息',
  `plan_repay_capital` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '已还本金',
  `min_invest_counts` tinyint(2) NOT NULL DEFAULT '1' COMMENT '最小自动投资笔数',
  `invest_level` varchar(10) DEFAULT '稳健型' COMMENT '服务投资等级',
  `del_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标识',
  `create_user` int(11) NOT NULL COMMENT '创建人id',
  `update_user` int(11) DEFAULT NULL COMMENT '更新人id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `plan_nid` (`plan_nid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8 COMMENT='汇计划列表';

-- ----------------------------
-- Table structure for ht_hjh_plan_asset
-- ----------------------------
DROP TABLE IF EXISTS `ht_hjh_plan_asset`;
CREATE TABLE `ht_hjh_plan_asset` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `asset_id` varchar(30) NOT NULL COMMENT '资产编号',
  `inst_code` varchar(20) NOT NULL COMMENT '机构编号',
  `asset_type` tinyint(2) unsigned NOT NULL COMMENT '机构产品类型',
  `borrow_nid` varchar(50) DEFAULT NULL COMMENT '项目编号',
  `plan_nid` varchar(30) DEFAULT NULL COMMENT '计划编号',
  `user_name` varchar(30) DEFAULT NULL COMMENT '借款用户名',
  `user_id` int(11) NOT NULL COMMENT '借款用户id',
  `mobile` char(11) NOT NULL COMMENT '手机号',
  `account_id` varchar(20) DEFAULT NULL COMMENT '江西银行电子账号',
  `truename` varchar(20) DEFAULT NULL COMMENT '真实姓名',
  `idcard` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `account` decimal(11,0) NOT NULL COMMENT '借款金额',
  `is_month` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '默认0 天标，1 月标',
  `borrow_period` int(10) DEFAULT NULL COMMENT '借款期限',
  `borrow_style` varchar(50) DEFAULT NULL COMMENT '还款方式',
  `verify_status` tinyint(1) NOT NULL COMMENT '审核状态',
  `status` tinyint(2) NOT NULL COMMENT '项目状态',
  `recieve_time` int(10) DEFAULT NULL COMMENT '推送时间',
  `label_id` int(10) NOT NULL DEFAULT '0' COMMENT '标签ID',
  `label_name` varchar(50) DEFAULT NULL COMMENT '标签名字',
  `sex` tinyint(2) DEFAULT NULL COMMENT '性别',
  `age` tinyint(2) unsigned DEFAULT NULL COMMENT '年龄',
  `marriage` tinyint(2) unsigned DEFAULT NULL COMMENT '婚姻状况',
  `entrusted_flg` tinyint(1) NOT NULL DEFAULT '0' COMMENT '受托支付标志',
  `entrusted_user_id` int(11) DEFAULT NULL COMMENT '受托支付用户ID',
  `entrusted_user_name` varchar(30) DEFAULT NULL COMMENT '受托支付用户名',
  `entrusted_account_id` varchar(20) DEFAULT NULL COMMENT '受托支付电子账号',
  `work_city` varchar(100) DEFAULT NULL COMMENT '工作城市',
  `position` varchar(100) DEFAULT NULL COMMENT '岗位职业',
  `domicile` varchar(100) DEFAULT NULL COMMENT '户籍地',
  `credit_level` varchar(10) DEFAULT NULL COMMENT '信用评级',
  `useage` varchar(100) DEFAULT NULL COMMENT '借款用途',
  `monthly_income` varchar(30) DEFAULT NULL COMMENT '月薪收入',
  `first_payment` varchar(30) DEFAULT NULL COMMENT '第一还款来源',
  `second_payment` varchar(30) DEFAULT NULL COMMENT '第二还款来源',
  `cost_introdution` varchar(500) DEFAULT NULL COMMENT '费用说明',
  `overdue_times` varchar(10) DEFAULT NULL COMMENT '在平台逾期次数',
  `overdue_amount` varchar(15) DEFAULT NULL COMMENT '在平台逾期金额',
  `litigation` varchar(100) DEFAULT NULL COMMENT '涉诉情况',
  `asset_info` varchar(500) DEFAULT NULL COMMENT '项目信息',
  `annual_income` varchar(30) DEFAULT NULL COMMENT '个人年收入:10万以内；10万以上',
  `overdue_report` varchar(30) DEFAULT NULL COMMENT '征信报告逾期情况:暂未提供；无；已处理',
  `debt_situation` varchar(30) DEFAULT NULL COMMENT '重大负债状况:无',
  `other_borrowed` varchar(30) DEFAULT NULL COMMENT '其他平台借款情况:无',
  `is_funds` varchar(30) DEFAULT NULL COMMENT '借款资金运用情况：不正常,正常',
  `is_managed` varchar(30) DEFAULT NULL COMMENT '借款人经营状况及财务状况：不正常,正常',
  `is_ability` varchar(30) DEFAULT NULL COMMENT '借款人还款能力变化情况：不正常,正常',
  `is_overdue` varchar(30) DEFAULT NULL COMMENT '借款人逾期情况：暂无,有',
  `is_complaint` varchar(30) DEFAULT NULL COMMENT '借款人涉诉情况：暂无,有',
  `is_punished` varchar(30) DEFAULT NULL COMMENT '借款人受行政处罚情况：暂无,有',
  `create_user_id` int(11) DEFAULT NULL COMMENT '创建人id',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新人id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `address` varchar(100) DEFAULT NULL COMMENT '借款人地址',
  `corporate_code` varchar(100) DEFAULT NULL COMMENT '企业组织机构代码',
  `registration_address` varchar(100) DEFAULT NULL COMMENT '企业注册地',
  `borrow_type` tinyint(1) DEFAULT '0' COMMENT '借款类型 0:个人;1:企业',
  `borrow_company_name` varchar(50) DEFAULT NULL COMMENT '借款企业名称',
  `financial_situation` varchar(200) DEFAULT NULL COMMENT '财务状况',
  `legal_person` varchar(50) DEFAULT NULL COMMENT '法人',
  `registration_area` varchar(50) DEFAULT NULL COMMENT '注册地区',
  `registration_date` varchar(11) DEFAULT NULL COMMENT '注册时间',
  `main_business` varchar(50) DEFAULT NULL COMMENT '主营业务',
  `unified_social_credit_code` varchar(50) DEFAULT NULL COMMENT '统一社会信用代码',
  `registered_capital` varchar(50) DEFAULT NULL COMMENT '注册资本',
  `industry_involved` varchar(50) DEFAULT NULL COMMENT '所属行业',
  `asset_attributes` tinyint(1) DEFAULT NULL COMMENT '资产属性 1:抵押标 2:质押标 3:信用标 4:债权转让标 5:净值标',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_asset_id_uk` (`asset_id`) USING BTREE,
  KEY `status` (`status`),
  KEY `idx_borrow_nid` (`borrow_nid`)
) ENGINE=InnoDB AUTO_INCREMENT=1114 DEFAULT CHARSET=utf8 COMMENT='汇计划资产表';

-- ----------------------------
-- Table structure for ht_hjh_plan_borrow_tmp
-- ----------------------------
DROP TABLE IF EXISTS `ht_hjh_plan_borrow_tmp`;
CREATE TABLE `ht_hjh_plan_borrow_tmp` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `accede_order_id` varchar(50) NOT NULL COMMENT '汇计划加入订单号',
  `order_id` varchar(50) NOT NULL COMMENT '自动投标订单号',
  `plan_nid` varchar(30) NOT NULL COMMENT '计划nid',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `user_name` varchar(30) DEFAULT NULL COMMENT '用户名',
  `accede_account` decimal(11,2) DEFAULT NULL COMMENT '加入金额',
  `already_invest` decimal(11,2) DEFAULT NULL COMMENT '已投资金额',
  `asset_id` varchar(30) DEFAULT NULL COMMENT '资产编号',
  `inst_code` varchar(20) DEFAULT NULL COMMENT '机构编号',
  `asset_type` tinyint(2) DEFAULT NULL COMMENT '机构产品类型',
  `borrow_nid` varchar(50) NOT NULL COMMENT '项目编号',
  `borrow_account` decimal(11,2) NOT NULL COMMENT '借款金额',
  `account` decimal(11,2) NOT NULL COMMENT '投入金额',
  `borrow_period` int(10) DEFAULT NULL COMMENT '借款期限',
  `borrow_style` varchar(50) DEFAULT NULL COMMENT '还款方式',
  `borrow_type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '标的类型：0原始标的,1债转标的',
  `sell_user_id` int(11) DEFAULT NULL COMMENT '债转原用户id',
  `sell_order_id` varchar(50) DEFAULT NULL COMMENT '债转原投资订单号',
  `status` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '状态',
  `is_last` tinyint(1) unsigned NOT NULL COMMENT '是否标的的最后一笔投资/承接(0:非最后一笔；1:最后一笔)',
  `resp_code` varchar(20) DEFAULT NULL COMMENT '返回状态码',
  `resp_desc` varchar(500) DEFAULT NULL COMMENT '返回状态详细',
  `create_user_id` int(11) DEFAULT NULL COMMENT '创建人id',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新人id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_accede_order_id` (`accede_order_id`),
  KEY `idx_borrow_nid` (`borrow_nid`)
) ENGINE=InnoDB AUTO_INCREMENT=2833 DEFAULT CHARSET=utf8 COMMENT='汇计划自动投资临时表';

-- ----------------------------
-- Table structure for ht_hjh_region
-- ----------------------------
DROP TABLE IF EXISTS `ht_hjh_region`;
CREATE TABLE `ht_hjh_region` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '计划专区表id',
  `plan_nid` varchar(20) NOT NULL COMMENT '计划编号',
  `plan_name` varchar(50) NOT NULL COMMENT '计划名称',
  `config_add_time` int(10) DEFAULT NULL COMMENT '计划专区添加时间',
  `config_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '计划专区状态 0：停用 1：启用',
  `del_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标识',
  `create_user` int(11) NOT NULL COMMENT '创建人id',
  `update_user` int(11) DEFAULT NULL COMMENT '更新人id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `plan_nid` (`plan_nid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8 COMMENT='标的分配引擎表';

-- ----------------------------
-- Table structure for ht_hjh_repay
-- ----------------------------
DROP TABLE IF EXISTS `ht_hjh_repay`;
CREATE TABLE `ht_hjh_repay` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `accede_order_id` varchar(50) NOT NULL COMMENT '汇计划加入订单号',
  `plan_nid` varchar(30) NOT NULL COMMENT '计划nid',
  `lock_period` int(2) unsigned NOT NULL DEFAULT '0' COMMENT '锁定期(天)',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `user_name` varchar(30) NOT NULL COMMENT '用户名',
  `user_attribute` int(11) NOT NULL DEFAULT '0' COMMENT '用户属性',
  `accede_account` decimal(11,2) NOT NULL COMMENT '加入金额',
  `repay_interest` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '应还利息',
  `repay_capital` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '应还本金',
  `repay_status` tinyint(1) DEFAULT NULL COMMENT '回款状态 0 未回款，1 部分回款 2 已回款',
  `repay_already` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '已回款',
  `repay_wait` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '待回款',
  `repay_should` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '应还金额',
  `repay_actual` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '实还金额',
  `order_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '订单状态 0自动投标中 1锁定中 2退出中 3已退出',
  `repay_actual_time` int(10) DEFAULT NULL COMMENT '计划实际还款时间',
  `repay_should_time` int(10) DEFAULT NULL COMMENT '计划应还时间',
  `plan_repay_capital` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '已还本金',
  `plan_repay_interest` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '已还利息',
  `repay_total` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '已还总额',
  `plan_wait_captical` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '待还本金',
  `plan_wait_interest` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '待还利息',
  `wait_total` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '待还总额',
  `service_fee` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '服务费',
  `actual_revenue` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '实际收益(元)',
  `actual_pay_total` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '实际回款总额(元)',
  `del_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标识',
  `create_user` int(11) NOT NULL COMMENT '创建人id',
  `update_user` int(11) DEFAULT NULL COMMENT '更新人id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `accede_order_id` (`accede_order_id`),
  KEY `idx_plan_nid` (`plan_nid`)
) ENGINE=InnoDB AUTO_INCREMENT=1176 DEFAULT CHARSET=utf8 COMMENT='计划还款表';

-- ----------------------------
-- Table structure for ht_increase_interest_invest
-- ----------------------------
DROP TABLE IF EXISTS `ht_increase_interest_invest`;
CREATE TABLE `ht_increase_interest_invest` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT '0' COMMENT '投资用户ID',
  `invest_user_name` varchar(20) NOT NULL DEFAULT '投资用户名',
  `tender_id` int(11) NOT NULL DEFAULT '0' COMMENT '对应tender表里的id',
  `tender_nid` varchar(100) NOT NULL COMMENT '对应tender表里的nid',
  `borrow_nid` varchar(50) DEFAULT '0' COMMENT '借款编号',
  `borrow_apr` decimal(10,2) DEFAULT '0.00' COMMENT '借款利率',
  `borrow_extra_yield` decimal(10,2) DEFAULT '0.00' COMMENT '产品加息收益率（风险缓释金）',
  `borrow_period` int(10) NOT NULL DEFAULT '0' COMMENT '借款期限',
  `borrow_style` varchar(100) NOT NULL COMMENT '借款类型',
  `borrow_style_name` varchar(100) NOT NULL COMMENT '借款类型名称',
  `order_id` varchar(100) NOT NULL COMMENT '投资订单号',
  `order_date` varchar(20) NOT NULL DEFAULT '' COMMENT '投资订单日期',
  `account` decimal(11,2) DEFAULT '0.00' COMMENT '投资金额',
  `status` tinyint(2) unsigned NOT NULL COMMENT '投资状态',
  `loan_order_date` varchar(20) DEFAULT NULL COMMENT '放款订单日期',
  `loan_order_id` varchar(20) DEFAULT NULL COMMENT '放款订单号',
  `repay_interest` decimal(11,2) DEFAULT '0.00' COMMENT '收款总利息',
  `repay_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已收利息',
  `repay_interest_wait` decimal(11,2) DEFAULT '0.00' COMMENT '待收利息',
  `repay_times` int(10) DEFAULT '0' COMMENT '已还款次数',
  `loan_amount` decimal(10,2) DEFAULT '0.00' COMMENT '放款金额',
  `client` tinyint(1) DEFAULT '0' COMMENT '客户端0PC，1微信2安卓APP，3IOS APP，4其他',
  `add_ip` varchar(20) DEFAULT NULL COMMENT 'create ip',
  `web` tinyint(1) NOT NULL DEFAULT '0' COMMENT '网站收支计算标识',
  `remark` varchar(300) DEFAULT NULL COMMENT '备注',
  `invest_type` tinyint(1) DEFAULT '0' COMMENT '投资类型 0手动投标 1预约投标 2自动投标',
  `create_user_id` int(11) NOT NULL COMMENT '创建用户id',
  `create_user_name` varchar(20) NOT NULL COMMENT '创建用户名',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户名',
  `update_user_name` varchar(20) DEFAULT NULL COMMENT '更新用户名',
  `invite_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '推荐人用户id(投资时)',
  `invite_user_name` varchar(100) DEFAULT NULL COMMENT '推荐人用户名(投资时)',
  `invite_region_id` int(11) NOT NULL DEFAULT '0' COMMENT '一级部门id(投资时)',
  `invite_region_name` varchar(100) NOT NULL DEFAULT ' ' COMMENT '一级部门名称(投资时)',
  `invite_branch_id` int(11) NOT NULL DEFAULT '0' COMMENT '二级部门id(投资时)',
  `invite_branch_name` varchar(100) NOT NULL DEFAULT ' ' COMMENT '二级部门名称(投资时)',
  `invite_department_id` int(11) NOT NULL DEFAULT '0' COMMENT '三级部门id(投资时)',
  `invite_department_name` varchar(100) NOT NULL DEFAULT ' ' COMMENT '三级部门名称(投资时)',
  `tender_user_attribute` int(11) NOT NULL DEFAULT '0' COMMENT '投资人用户属性',
  `invite_user_attribute` int(11) NOT NULL DEFAULT '0' COMMENT '推荐人用户属性',
  `repay_time` int(10) DEFAULT '0' COMMENT '应还款时间',
  `repay_action_time` int(10) DEFAULT '0' COMMENT '实际还款时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_id` (`order_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=231 DEFAULT CHARSET=utf8 COMMENT='融通宝加息投资表';

-- ----------------------------
-- Table structure for ht_increase_interest_loan
-- ----------------------------
DROP TABLE IF EXISTS `ht_increase_interest_loan`;
CREATE TABLE `ht_increase_interest_loan` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '投资用户id',
  `user_name` varchar(20) NOT NULL COMMENT '投资用户名',
  `invest_id` int(11) NOT NULL DEFAULT '0' COMMENT '投资id',
  `invest_order_id` varchar(100) NOT NULL COMMENT '投资订单号',
  `invest_account` decimal(11,2) DEFAULT '0.00' COMMENT '投资金额',
  `borrow_user_id` int(11) DEFAULT '0' COMMENT '借款用户id',
  `borrow_user_name` varchar(20) DEFAULT NULL COMMENT '借款人用户名',
  `borrow_nid` varchar(50) NOT NULL DEFAULT '' COMMENT '项目编号',
  `borrow_apr` decimal(10,2) DEFAULT '0.00' COMMENT '借款利率',
  `borrow_extra_yield` decimal(10,2) DEFAULT '0.00' COMMENT '产品加息收益率（风险缓释金）',
  `borrow_period` int(10) NOT NULL DEFAULT '0' COMMENT '借款期限',
  `borrow_style` varchar(100) NOT NULL COMMENT '借款类型',
  `borrow_style_name` varchar(100) NOT NULL COMMENT '借款类型名称',
  `loan_interest` decimal(11,2) DEFAULT '0.00' COMMENT '放款利息（应还利息）',
  `remain_period` tinyint(2) unsigned DEFAULT NULL COMMENT '剩余期数',
  `repay_period` tinyint(2) unsigned DEFAULT NULL COMMENT '已还款期数',
  `repay_time` int(11) DEFAULT NULL COMMENT '估计还款时间',
  `repay_action_time` int(11) DEFAULT NULL COMMENT '已经还款时间',
  `repay_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还利息',
  `repay_interest_wait` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '待还利息',
  `repay_status` tinyint(2) unsigned DEFAULT NULL COMMENT '还款状态 0未还款 1还款中 2已还款',
  `add_ip` varchar(20) DEFAULT NULL COMMENT 'create ip',
  `web` tinyint(1) DEFAULT '0' COMMENT '网站收支计算标识',
  `repay_order_id` varchar(20) DEFAULT NULL COMMENT '还款订单号',
  `repay_order_date` varchar(10) DEFAULT NULL COMMENT '还款订单日期',
  `create_user_id` int(11) NOT NULL COMMENT '创建用户id',
  `create_user_name` varchar(20) NOT NULL COMMENT '创建用户名',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户名',
  `update_user_name` varchar(20) DEFAULT NULL COMMENT '更新用户名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `borrow_nid` (`borrow_nid`) USING BTREE,
  KEY `invest_order_id` (`invest_order_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=207 DEFAULT CHARSET=utf8 COMMENT='融通宝加息项目放款总表，相当于borrow_recover表';

-- ----------------------------
-- Table structure for ht_increase_interest_loan_detail
-- ----------------------------
DROP TABLE IF EXISTS `ht_increase_interest_loan_detail`;
CREATE TABLE `ht_increase_interest_loan_detail` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT '0' COMMENT '投资用户id',
  `user_name` varchar(20) DEFAULT NULL COMMENT '投资用户名',
  `borrow_user_id` int(11) DEFAULT '0' COMMENT '借款用户id',
  `borrow_user_name` varchar(20) DEFAULT NULL COMMENT '借款人用户名',
  `borrow_nid` varchar(50) DEFAULT '' COMMENT '项目编号',
  `borrow_apr` decimal(10,2) DEFAULT '0.00' COMMENT '借款利率',
  `borrow_extra_yield` decimal(10,2) DEFAULT '0.00' COMMENT '产品加息收益率（风险缓释金）',
  `borrow_period` int(10) NOT NULL DEFAULT '0' COMMENT '借款期限',
  `borrow_style` varchar(100) NOT NULL COMMENT '借款类型',
  `borrow_style_name` varchar(100) NOT NULL COMMENT '借款类型名称',
  `invest_id` int(11) DEFAULT '0' COMMENT '投资id',
  `invest_order_id` varchar(100) NOT NULL COMMENT '投资订单号',
  `invest_account` decimal(11,2) DEFAULT '0.00' COMMENT '投资金额',
  `loan_interest` decimal(11,2) DEFAULT '0.00' COMMENT '放款利息（应还利息）',
  `repay_period` tinyint(2) unsigned DEFAULT NULL COMMENT '已还款期数',
  `repay_time` int(11) DEFAULT NULL COMMENT '估计还款时间',
  `repay_action_time` int(11) DEFAULT NULL COMMENT '已经还款时间',
  `repay_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还利息',
  `repay_interest_wait` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '待还利息',
  `repay_status` tinyint(2) unsigned DEFAULT NULL COMMENT '还款状态 0未还款 1还款中 2已还款',
  `add_ip` varchar(20) DEFAULT NULL COMMENT 'create ip',
  `web` tinyint(1) DEFAULT '0' COMMENT '网站收支计算标识',
  `repay_order_id` varchar(20) DEFAULT NULL COMMENT '还款订单号',
  `repay_order_date` varchar(10) DEFAULT NULL COMMENT '还款订单日期',
  `create_user_id` int(11) NOT NULL COMMENT '创建用户id',
  `create_user_name` varchar(20) NOT NULL COMMENT '创建用户名',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户名',
  `update_user_name` varchar(20) DEFAULT NULL COMMENT '更新用户名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `borrow_nid` (`borrow_nid`) USING BTREE,
  KEY `invest_order_id` (`invest_order_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=367 DEFAULT CHARSET=utf8 COMMENT='融通宝加息项目放款详情表，相当于borrow_recover_plan表';

-- ----------------------------
-- Table structure for ht_increase_interest_repay
-- ----------------------------
DROP TABLE IF EXISTS `ht_increase_interest_repay`;
CREATE TABLE `ht_increase_interest_repay` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT '0' COMMENT '用户ID',
  `user_name` varchar(20) NOT NULL COMMENT '用户名',
  `invest_id` int(11) NOT NULL DEFAULT '0' COMMENT '投资id',
  `invest_order_id` varchar(100) NOT NULL COMMENT '投资订单号',
  `invest_account` decimal(11,2) DEFAULT '0.00' COMMENT '投资金额',
  `borrow_nid` varchar(50) DEFAULT '0' COMMENT '借款编号',
  `borrow_apr` decimal(10,2) DEFAULT '0.00' COMMENT '借款利率',
  `borrow_extra_yield` decimal(10,2) DEFAULT '0.00' COMMENT '产品加息收益率（风险缓释金）',
  `borrow_period` int(10) NOT NULL DEFAULT '0' COMMENT '借款期限',
  `borrow_style` varchar(100) NOT NULL COMMENT '借款类型',
  `borrow_style_name` varchar(100) NOT NULL COMMENT '借款类型名称',
  `borrow_account` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '借款金额',
  `order_id` varchar(100) NOT NULL COMMENT '转账订单号',
  `order_date` varchar(20) NOT NULL DEFAULT '' COMMENT '转账订单日期',
  `repay_status` tinyint(2) unsigned DEFAULT '0' COMMENT '还款状态 0未转账  1已转账 2转账中',
  `remain_period` tinyint(2) unsigned DEFAULT NULL COMMENT '剩余期数',
  `already_repay_period` tinyint(2) unsigned DEFAULT '0' COMMENT '已还款期数',
  `repay_period` tinyint(2) unsigned DEFAULT NULL COMMENT '还款期数',
  `repay_time` int(11) DEFAULT NULL COMMENT '估计还款时间',
  `repay_action_time` int(11) DEFAULT NULL COMMENT '已经还款时间',
  `repay_interest` decimal(11,2) DEFAULT '0.00' COMMENT '应还款利息',
  `repay_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还款利息',
  `repay_interest_wait` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '待还利息',
  `add_ip` varchar(20) DEFAULT NULL COMMENT 'create ip',
  `web` tinyint(1) DEFAULT '0' COMMENT '网站收支计算标识',
  `create_user_id` int(11) NOT NULL COMMENT '创建用户id',
  `create_user_name` varchar(20) NOT NULL COMMENT '创建用户名',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户名',
  `update_user_name` varchar(20) DEFAULT NULL COMMENT '更新用户名',
  `loan_action_time` int(10) DEFAULT NULL COMMENT '实际放款时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `borrow_nid` (`borrow_nid`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8 COMMENT='融通宝加息还款表';

-- ----------------------------
-- Table structure for ht_increase_interest_repay_detail
-- ----------------------------
DROP TABLE IF EXISTS `ht_increase_interest_repay_detail`;
CREATE TABLE `ht_increase_interest_repay_detail` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `user_name` varchar(20) NOT NULL COMMENT '用户名',
  `invest_id` int(11) NOT NULL DEFAULT '0' COMMENT '投资id',
  `invest_order_id` varchar(100) NOT NULL COMMENT '投资订单号',
  `invest_account` decimal(11,2) DEFAULT '0.00' COMMENT '投资金额',
  `borrow_nid` varchar(50) NOT NULL DEFAULT '0' COMMENT '借款id',
  `borrow_apr` decimal(10,2) DEFAULT '0.00' COMMENT '借款利率',
  `borrow_extra_yield` decimal(10,2) DEFAULT '0.00' COMMENT '产品加息收益率（风险缓释金）',
  `borrow_period` int(10) NOT NULL DEFAULT '0' COMMENT '借款期限',
  `borrow_style` varchar(100) NOT NULL COMMENT '借款类型',
  `borrow_style_name` varchar(100) NOT NULL COMMENT '借款类型名称',
  `order_id` varchar(100) NOT NULL COMMENT '转账订单号',
  `order_date` varchar(20) NOT NULL DEFAULT '' COMMENT '转账订单日期',
  `repay_status` tinyint(2) unsigned DEFAULT '0' COMMENT '还款状态 0未转账  1已转账 2转账中',
  `repay_period` tinyint(2) unsigned DEFAULT NULL COMMENT '还款期数',
  `repay_time` int(11) DEFAULT NULL COMMENT '估计还款时间(下期还款时间)',
  `repay_action_time` int(11) DEFAULT NULL COMMENT '执行还款的时间',
  `repay_interest` decimal(11,2) DEFAULT '0.00' COMMENT '应还款利息',
  `repay_interest_yes` decimal(11,2) DEFAULT '0.00' COMMENT '已还款利息',
  `repay_interest_wait` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '待还利息',
  `add_ip` varchar(20) DEFAULT NULL COMMENT 'create ip',
  `web` tinyint(1) DEFAULT '0' COMMENT '网站收支计算标识',
  `create_user_id` int(11) NOT NULL COMMENT '创建用户id',
  `create_user_name` varchar(20) NOT NULL COMMENT '创建用户名',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户名',
  `update_user_name` varchar(20) DEFAULT NULL COMMENT '更新用户名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `borrow_nid` (`borrow_nid`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8 COMMENT='融通宝加息还款详情表,相当于ht_borrow_repay_plan';

-- ----------------------------
-- Table structure for ht_manual_reverse
-- ----------------------------
DROP TABLE IF EXISTS `ht_manual_reverse`;
CREATE TABLE `ht_manual_reverse` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `seq_no` int(11) DEFAULT NULL COMMENT '原交易流水号',
  `bank_seq_no` varchar(50) DEFAULT NULL COMMENT '交易流水号',
  `tx_time` datetime DEFAULT NULL COMMENT '交易时间',
  `username` varchar(30) DEFAULT NULL COMMENT '用户名',
  `account_id` varchar(50) DEFAULT NULL COMMENT '电子账号',
  `is_bank` tinyint(1) DEFAULT NULL COMMENT '资金托管平台 0:汇付，1:江西银行',
  `type` tinyint(1) DEFAULT NULL COMMENT '收支类型 0收入 1支出',
  `trans_type` varchar(10) DEFAULT NULL COMMENT '交易类型',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '操作金额',
  `status` tinyint(1) DEFAULT NULL COMMENT '操作状态 0 成功 1失败',
  `create_user_id` int(11) DEFAULT NULL COMMENT '创建用户ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_merchant_account
-- ----------------------------
DROP TABLE IF EXISTS `ht_merchant_account`;
CREATE TABLE `ht_merchant_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sub_account_name` varchar(20) NOT NULL COMMENT '子账户名称',
  `sub_account_type` varchar(10) NOT NULL COMMENT '子账户类型',
  `sub_account_code` varchar(20) NOT NULL COMMENT '子账户代号',
  `transfer_into_flg` tinyint(1) DEFAULT '0' COMMENT '子账户转入:0 不支持 1 支持',
  `transfer_out_flg` tinyint(1) DEFAULT '0' COMMENT '子账户转出:0 不支持 1 支持',
  `balance_lower_limit` decimal(11,0) unsigned DEFAULT NULL COMMENT '余额下限(元)',
  `auto_transfer_out` tinyint(1) DEFAULT '0' COMMENT '自动转出:0 否 1 是',
  `auto_transfer_into` tinyint(1) DEFAULT '0' COMMENT '自动转出:0 否 1 是',
  `transfer_into_ratio` int(3) DEFAULT '0' COMMENT '转入比例',
  `account_balance` decimal(20,2) DEFAULT '0.00' COMMENT '账户余额',
  `available_balance` decimal(20,2) DEFAULT '0.00' COMMENT '可用余额',
  `frost` decimal(20,2) DEFAULT '0.00' COMMENT '冻结金额',
  `purpose` varchar(50) DEFAULT NULL COMMENT '用途',
  `sort` smallint(2) DEFAULT '0' COMMENT '排序',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='商户子账户配置表';

-- ----------------------------
-- Table structure for ht_merchant_transfer
-- ----------------------------
DROP TABLE IF EXISTS `ht_merchant_transfer`;
CREATE TABLE `ht_merchant_transfer` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_id` varchar(50) NOT NULL COMMENT '订单编号',
  `out_account_id` int(11) unsigned NOT NULL COMMENT '转出账户id',
  `out_account_code` varchar(30) NOT NULL COMMENT '转出账户代码',
  `out_account_name` varchar(30) NOT NULL COMMENT '转出账户',
  `in_account_id` int(11) unsigned DEFAULT NULL COMMENT '转入账户id',
  `in_account_code` varchar(30) DEFAULT NULL COMMENT '转入账户代码',
  `in_account_name` varchar(30) DEFAULT NULL COMMENT '转入账户',
  `transfer_amount` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '转账金额',
  `transfer_time` datetime DEFAULT NULL COMMENT '转账时间',
  `status` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '转让状态 0 待转账 1 转帐中 2 转账成功 3 转账失败',
  `transfer_type` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '转账类型 1自动转账 0手动转账',
  `remark` varchar(255) NOT NULL COMMENT '说明',
  `message` varchar(255) DEFAULT NULL COMMENT '失败原因',
  `create_user_id` int(11) unsigned NOT NULL COMMENT '创建者用户id',
  `create_user_name` varchar(30) DEFAULT NULL COMMENT '创建人',
  `update_user_id` int(11) unsigned DEFAULT NULL COMMENT '更新者用户id',
  `update_user_name` varchar(30) DEFAULT NULL COMMENT '更新者',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商户子账户的转账记录';

-- ----------------------------
-- Table structure for ht_nifa_contract_essence
-- ----------------------------
DROP TABLE IF EXISTS `ht_nifa_contract_essence`;
CREATE TABLE `ht_nifa_contract_essence` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `platform_no` varchar(20) DEFAULT NULL COMMENT '统一社会信用代码',
  `platform_name` varchar(99) DEFAULT NULL COMMENT '从业机构名称',
  `project_no` varchar(99) DEFAULT NULL COMMENT '项目编号',
  `contract_name` varchar(30) DEFAULT NULL COMMENT '合同名称',
  `contract_no` varchar(99) DEFAULT NULL COMMENT '合同编号',
  `contract_signer` varchar(99) DEFAULT NULL COMMENT '合同签署方',
  `contract_time` varchar(10) DEFAULT NULL COMMENT '合同签署日 格式为：YYYY-MM-DD',
  `borrower_type` tinyint(1) DEFAULT NULL COMMENT '借款人类型 01-自然人 02-机构',
  `borrower_cert_type` varchar(2) DEFAULT NULL COMMENT '借款人证件类型 0-身份证',
  `borrower_cert_no` varchar(20) DEFAULT NULL COMMENT '借款人证件号码 当借款人类型为01-自然人时，填写该字段',
  `borrower_name` varchar(30) DEFAULT NULL COMMENT '借款人姓名 当借款人类型为01-自然人时，填写该字段',
  `borrower_address` varchar(99) DEFAULT NULL COMMENT '借款人地址 当借款人类型为01-自然人时，填写该字段',
  `borrower_nacao_no` varchar(20) DEFAULT NULL COMMENT '借款人统一社会信用代码 当借款人类型为02-机构时，填写该字段',
  `borrower_orgcode_no` varchar(30) DEFAULT NULL COMMENT '借款人组织机构代码 当借款人类型为02-机构时，填写该字段',
  `borrower_company` varchar(30) DEFAULT NULL COMMENT '借款人名称 当借款人类型为02-机构时，填写该字段',
  `investor_type` tinyint(1) DEFAULT NULL COMMENT '出借人类型 01-自然人 02-机构',
  `investor_cert_type` varchar(2) DEFAULT NULL COMMENT '出借人证件类型 0-身份证',
  `investor_cert_no` varchar(20) DEFAULT NULL COMMENT '出借人证件号码',
  `investor_name` varchar(30) DEFAULT NULL COMMENT '出借人姓名',
  `investor_nacao_no` varchar(20) DEFAULT NULL COMMENT '出借人统一社会信用代码',
  `investor_orgcode_no` varchar(20) DEFAULT NULL COMMENT '出借人组织机构代码',
  `investor_company` varchar(30) DEFAULT NULL COMMENT '出借人名称',
  `invest_amount` varchar(20) DEFAULT NULL COMMENT '借款金额',
  `borrow_rate` varchar(10) DEFAULT NULL COMMENT '年化利率',
  `borrow_use` varchar(99) DEFAULT NULL COMMENT '借款用途',
  `borrow_use_limit` varchar(999) DEFAULT NULL COMMENT '借款用途限制',
  `loan_date` varchar(20) DEFAULT NULL COMMENT '借款放款日',
  `loan_date_basis` varchar(99) DEFAULT NULL COMMENT '借款放款日判断依据',
  `start_date` varchar(10) DEFAULT NULL COMMENT '起息日',
  `expiry_date` varchar(10) DEFAULT NULL COMMENT '到期日',
  `repay_type` int(11) DEFAULT NULL COMMENT '还款方式',
  `repay_formula` varchar(999) DEFAULT NULL COMMENT '还款方式含义及计算公式',
  `repay_date_rule` varchar(99) DEFAULT NULL COMMENT '还款规则',
  `repay_num` int(11) DEFAULT NULL COMMENT '还款期数',
  `repay_plan` varchar(3000) DEFAULT NULL COMMENT '还款计划',
  `overdue_repay_def` varchar(999) DEFAULT NULL COMMENT '逾期还款定义',
  `overdue_repay_resp` varchar(999) DEFAULT NULL COMMENT '逾期还款责任',
  `overdue_repay_proc` varchar(999) DEFAULT NULL COMMENT '逾期还款流程',
  `notice_address` varchar(999) DEFAULT NULL COMMENT '通知与送达',
  `contract_effective_date` varchar(10) DEFAULT NULL COMMENT '合同生效日',
  `contract_template_no` varchar(28) DEFAULT NULL COMMENT '合同模板编号',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_contract_no` (`contract_no`)
) ENGINE=InnoDB AUTO_INCREMENT=1381 DEFAULT CHARSET=utf8 COMMENT='合同要素信息';

-- ----------------------------
-- Table structure for ht_nifa_contract_status
-- ----------------------------
DROP TABLE IF EXISTS `ht_nifa_contract_status`;
CREATE TABLE `ht_nifa_contract_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `platform_no` varchar(20) DEFAULT NULL COMMENT '统一社会信用代码',
  `project_no` varchar(50) DEFAULT NULL COMMENT '项目编号',
  `contract_no` varchar(50) DEFAULT NULL COMMENT '合同编号',
  `contract_status` tinyint(1) DEFAULT NULL COMMENT '合同状态',
  `change_date` varchar(19) DEFAULT NULL COMMENT '更新日期 YYYY-MM-DD HH:mm:ss',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2766 DEFAULT CHARSET=utf8 COMMENT='合同状态变更';

-- ----------------------------
-- Table structure for ht_nifa_contract_template
-- ----------------------------
DROP TABLE IF EXISTS `ht_nifa_contract_template`;
CREATE TABLE `ht_nifa_contract_template` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `templet_nid` varchar(12) NOT NULL COMMENT '模版编号',
  `normal_definition` varchar(1000) DEFAULT NULL COMMENT '正常还款定义',
  `prepayment_definition` varchar(1000) DEFAULT NULL COMMENT '提前还款定义',
  `borrower_promises` varchar(1000) DEFAULT NULL COMMENT '借款人承诺与保证',
  `lender_promises` varchar(1000) DEFAULT NULL COMMENT '出借人承诺与保证',
  `borrower_obligation` varchar(1000) DEFAULT NULL COMMENT '借款人还款义务',
  `confidentiality` varchar(1000) DEFAULT NULL COMMENT '保密',
  `breach_contract` varchar(1000) DEFAULT NULL COMMENT '违约',
  `applicable_law` varchar(1000) DEFAULT NULL COMMENT '法律适用',
  `dispute_resolution` varchar(1000) DEFAULT NULL COMMENT '争议解决',
  `other_conditions` varchar(1000) DEFAULT NULL COMMENT '其他条款',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='合同模版约定条款表';

-- ----------------------------
-- Table structure for ht_nifa_field_definition
-- ----------------------------
DROP TABLE IF EXISTS `ht_nifa_field_definition`;
CREATE TABLE `ht_nifa_field_definition` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `borrowing_restrictions` varchar(255) NOT NULL COMMENT '借款用途限制',
  `judgments_based` varchar(255) DEFAULT NULL COMMENT '借款放款日判断依据',
  `repay_date_rule` varchar(255) DEFAULT NULL COMMENT '还款日规则说明',
  `overdue_definition` varchar(255) DEFAULT NULL COMMENT '逾期定义',
  `overdue_responsibility` varchar(255) DEFAULT NULL COMMENT '逾期还款责任',
  `overdue_process` varchar(255) DEFAULT NULL COMMENT '逾期还款流程',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='互金字段定义表';

-- ----------------------------
-- Table structure for ht_nifa_received_payments
-- ----------------------------
DROP TABLE IF EXISTS `ht_nifa_received_payments`;
CREATE TABLE `ht_nifa_received_payments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `platform_no` varchar(20) DEFAULT NULL COMMENT '统一社会信用代码',
  `project_no` varchar(50) DEFAULT NULL COMMENT '项目编号',
  `contract_no` varchar(50) DEFAULT NULL COMMENT '合同编号',
  `return_num` int(11) DEFAULT NULL COMMENT '回款期数',
  `return_date` varchar(10) DEFAULT NULL COMMENT '回款日期',
  `return_principal` varchar(10) DEFAULT NULL COMMENT '回款本金',
  `return_interest` varchar(10) DEFAULT NULL COMMENT '回款利息',
  `return_source` int(11) DEFAULT NULL COMMENT '回款来源',
  `return_situation` tinyint(1) DEFAULT NULL COMMENT '回款情况',
  `return_principal_rest` varchar(20) DEFAULT NULL COMMENT '剩余待回本金',
  `return_interest_rest` varchar(20) DEFAULT NULL COMMENT '剩余待回利息',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=735 DEFAULT CHARSET=utf8 COMMENT='出借人回款记录';

-- ----------------------------
-- Table structure for ht_nifa_repay_info
-- ----------------------------
DROP TABLE IF EXISTS `ht_nifa_repay_info`;
CREATE TABLE `ht_nifa_repay_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `platform_no` varchar(20) DEFAULT NULL COMMENT '统一社会信用代码',
  `project_no` varchar(50) DEFAULT NULL COMMENT '项目编号',
  `payment_num` int(11) DEFAULT NULL COMMENT '还款期数',
  `payment_date` varchar(10) DEFAULT NULL COMMENT '还款日期',
  `payment_principal` varchar(20) DEFAULT NULL COMMENT '还款本金',
  `payment_interest` varchar(20) DEFAULT NULL COMMENT '还款利息',
  `payment_source` int(11) DEFAULT NULL COMMENT '还款来源',
  `payment_situation` int(11) DEFAULT NULL COMMENT '还款情况',
  `payment_principal_rest` varchar(20) DEFAULT NULL COMMENT '剩余待还本金',
  `payment_interest_rest` varchar(20) DEFAULT NULL COMMENT '剩余待还利息',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=342 DEFAULT CHARSET=utf8 COMMENT='借款人项目还款记录';

-- ----------------------------
-- Table structure for ht_nifa_report_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_nifa_report_log`;
CREATE TABLE `ht_nifa_report_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `package_information` varchar(255) NOT NULL COMMENT '文件包信息',
  `upload_time` int(11) DEFAULT NULL COMMENT '上传时间',
  `history_date` varchar(10) DEFAULT '0' COMMENT '数据处理日期',
  `file_upload_status` tinyint(1) DEFAULT '0' COMMENT '文件上传状态 0：未处理 1：成功 2：失败',
  `feedback_result` tinyint(1) DEFAULT '0' COMMENT '文件解析反馈 0：未处理 1：成功 2：失败',
  `upload_name` varchar(50) DEFAULT NULL COMMENT '上传文件包名',
  `feedback_name` varchar(50) DEFAULT NULL COMMENT '反馈文件包名',
  `upload_path` varchar(255) DEFAULT NULL COMMENT '上传文件包路径',
  `feedback_path` varchar(255) DEFAULT NULL COMMENT '反馈文件包路径',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8 COMMENT='互金协会报送日志表';

-- ----------------------------
-- Table structure for ht_poundage
-- ----------------------------
DROP TABLE IF EXISTS `ht_poundage`;
CREATE TABLE `ht_poundage` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `ledger_id` int(11) DEFAULT NULL COMMENT '分账数据id',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '分账总金额',
  `quantity` int(11) DEFAULT NULL COMMENT '分账数量',
  `poundage_time` varchar(20) DEFAULT NULL COMMENT '分账时间段',
  `nid` varchar(20) DEFAULT NULL COMMENT '交易凭证号',
  `seq_no` varchar(20) DEFAULT NULL COMMENT '分账请求流水号',
  `tx_date` int(10) DEFAULT NULL COMMENT '银行订单日期',
  `tx_time` int(10) DEFAULT NULL COMMENT '银行订单时间',
  `status` tinyint(1) DEFAULT NULL COMMENT '分账状态  0：未审核    1：审核通过 2：分账成功  3：分账失败  4处理中',
  `add_time` int(11) DEFAULT NULL COMMENT '分账时间',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='手续费分账表';

-- ----------------------------
-- Table structure for ht_poundage_detail
-- ----------------------------
DROP TABLE IF EXISTS `ht_poundage_detail`;
CREATE TABLE `ht_poundage_detail` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `nid` varchar(30) DEFAULT '' COMMENT '订单号',
  `borrow_nid` varchar(30) DEFAULT '' COMMENT '项目编号',
  `borrow_type` varchar(20) DEFAULT '' COMMENT '项目类型',
  `usernname` varchar(30) DEFAULT '' COMMENT '投资人用户名',
  `poundage_type` varchar(20) DEFAULT NULL COMMENT '分账类型',
  `source` varchar(30) DEFAULT '' COMMENT '分账来源',
  `invite_region_name` varchar(30) DEFAULT '' COMMENT '推荐人公司',
  `invite_user_id` int(10) DEFAULT NULL COMMENT '推荐人id',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '分账金额',
  `ledger_time` int(11) DEFAULT '0' COMMENT '实际分账时间段',
  `ledger_id` int(11) DEFAULT NULL COMMENT '手续费分账配置id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `ledger_time` (`ledger_time`) USING BTREE,
  KEY `ledger_id` (`ledger_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='手续费分账明细表';

-- ----------------------------
-- Table structure for ht_poundage_exception
-- ----------------------------
DROP TABLE IF EXISTS `ht_poundage_exception`;
CREATE TABLE `ht_poundage_exception` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `ledger_amount` decimal(11,2) DEFAULT '0.00' COMMENT '分账金额',
  `poundage_id` int(10) NOT NULL,
  `ledger_id` int(10) NOT NULL COMMENT '手续费分账配置id',
  `payee_name` varchar(30) DEFAULT '' COMMENT '收款人用户名',
  `ledger_status` tinyint(1) DEFAULT '1' COMMENT '分账状态:0.未分账;1.已分账',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='手续费异常处理';

-- ----------------------------
-- Table structure for ht_poundage_ledger
-- ----------------------------
DROP TABLE IF EXISTS `ht_poundage_ledger`;
CREATE TABLE `ht_poundage_ledger` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `username` varchar(30) DEFAULT '' COMMENT '用户名',
  `truename` varchar(20) DEFAULT '' COMMENT '真实姓名',
  `account` varchar(30) DEFAULT '' COMMENT '电子账号',
  `type` tinyint(1) DEFAULT '0' COMMENT '分账类型   1:按投资人分账； 2:按借款人分账',
  `source` varchar(30) DEFAULT '' COMMENT '分账来源  0:全部； 1:服务费； 2:债转服务费； 3:管理费',
  `service_ratio` decimal(10,2) DEFAULT '0.00' COMMENT '服务费分账比例',
  `credit_ratio` decimal(10,2) DEFAULT '0.00' COMMENT '债转服务费分账比例',
  `manage_ratio` decimal(10,2) DEFAULT '0.00' COMMENT '管理费分账比例',
  `investor_company_id` int(10) DEFAULT NULL COMMENT '投资人分公司id',
  `investor_company` varchar(50) DEFAULT '' COMMENT '投资人分公司名称',
  `project_type` varchar(100) DEFAULT '' COMMENT '项目类型，可保存所有',
  `status` tinyint(1) DEFAULT '0' COMMENT '启用状态  0：禁用    1：启用',
  `explan` varchar(500) DEFAULT '' COMMENT '说明',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='手续费分账配置';

-- ----------------------------
-- Table structure for ht_product
-- ----------------------------
DROP TABLE IF EXISTS `ht_product`;
CREATE TABLE `ht_product` (
  `id` int(6) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `pnumber` varchar(30) NOT NULL COMMENT '编号',
  `interest_rate` decimal(10,3) NOT NULL DEFAULT '0.000' COMMENT '利率（年化）',
  `pupper` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '投资上限-投资人',
  `plower` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '投资下限-投资人',
  `total` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '已卖金额',
  `allpupper` decimal(10,2) DEFAULT '0.00' COMMENT '产品上限',
  `is_tender` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否可转入 0:可转入 1:不可转入',
  `is_redeem` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否可赎回 0:可赎回 1:不可赎回',
  `error_remark` varchar(100) NOT NULL COMMENT '不可操作注释',
  `pnumber_new` varchar(30) NOT NULL COMMENT '对公账户id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='理财产品表如，汇添利';

-- ----------------------------
-- Table structure for ht_product_error_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_product_error_log`;
CREATE TABLE `ht_product_error_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `order_id` varchar(30) NOT NULL COMMENT '订单号',
  `invest_time` int(11) NOT NULL COMMENT '时间',
  `remark` varchar(500) NOT NULL COMMENT '原因',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '操作金额',
  `date` varchar(20) DEFAULT NULL,
  `is_sms` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '0:此警报信息未发送  1：此警报信息已发送，不再重复发送',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='汇天利错误日志表-汇添利';

-- ----------------------------
-- Table structure for ht_product_info
-- ----------------------------
DROP TABLE IF EXISTS `ht_product_info`;
CREATE TABLE `ht_product_info` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `in_count` int(10) DEFAULT NULL COMMENT '转入用户数',
  `out_count` int(10) DEFAULT NULL COMMENT '转出用户数',
  `in_amount` decimal(10,2) DEFAULT NULL COMMENT '转入金额',
  `out_amount` decimal(10,2) DEFAULT NULL COMMENT '转出金额',
  `out_interest` decimal(16,8) DEFAULT NULL COMMENT '转出利息',
  `loan_balance` decimal(10,2) DEFAULT NULL COMMENT '资管公司账户余额',
  `invest_amount` decimal(10,2) DEFAULT NULL COMMENT '投资总金额',
  `data_date` varchar(12) DEFAULT NULL COMMENT '日期',
  `data_month` varchar(6) NOT NULL COMMENT '统计数据月份',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='汇天利信息统计表';

-- ----------------------------
-- Table structure for ht_product_interest
-- ----------------------------
DROP TABLE IF EXISTS `ht_product_interest`;
CREATE TABLE `ht_product_interest` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `order_id` varchar(30) NOT NULL COMMENT '订单号',
  `amount` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '计息金额',
  `interest_days` int(6) NOT NULL COMMENT '计息天数',
  `interest_rate` decimal(16,8) NOT NULL COMMENT '日利率',
  `interest` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '总利息',
  `interest_time` int(11) NOT NULL COMMENT '时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `idx_interest_time` (`interest_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='汇添利计息明细';

-- ----------------------------
-- Table structure for ht_product_list
-- ----------------------------
DROP TABLE IF EXISTS `ht_product_list`;
CREATE TABLE `ht_product_list` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `product_id` int(6) NOT NULL COMMENT '产品ID',
  `amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '投资金额',
  `invest_time` int(11) NOT NULL COMMENT '投资时间',
  `interest_time` int(11) NOT NULL COMMENT '计息时间',
  `valid_days` int(6) NOT NULL COMMENT '有效天数',
  `redeemed` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '已赎回金额',
  `rest_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '剩余金额',
  `referee` int(11) NOT NULL COMMENT '推荐人ID',
  `area` int(6) NOT NULL COMMENT '大区ID',
  `company` int(6) NOT NULL COMMENT '分公司ID',
  `department` int(6) NOT NULL COMMENT '部门ID',
  `status` tinyint(1) NOT NULL COMMENT '投资资金状态',
  `order_id` varchar(30) NOT NULL COMMENT '订单号',
  `order_date` varchar(8) NOT NULL COMMENT '订单日期',
  `interest` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '利息',
  `client` tinyint(1) DEFAULT '0' COMMENT '客户端0PC，1微信2安卓APP，3IOS APP，4其他',
  `balance` decimal(10,2) DEFAULT '0.00' COMMENT '当前汇天利账户可用本金',
  `loans_id` varchar(100) DEFAULT NULL COMMENT '放款id',
  `loans_date` varchar(8) DEFAULT NULL COMMENT '放款order_date',
  `is_new` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否为对公账户投资 0：否 1：是',
  `invest_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '投资状态：0成功，1未付款，2失败    默认0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_ORDER_ID` (`order_id`),
  KEY `user_id` (`user_id`),
  KEY `invest_status` (`invest_status`),
  KEY `idx_invest_time` (`invest_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='理财产品购买明细-汇添利';

-- ----------------------------
-- Table structure for ht_product_list_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_product_list_log`;
CREATE TABLE `ht_product_list_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `product_id` int(6) NOT NULL COMMENT '产品ID',
  `amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '投资金额',
  `invest_time` int(11) NOT NULL COMMENT '投资时间',
  `interest_time` int(11) NOT NULL COMMENT '计息时间',
  `valid_days` int(6) NOT NULL COMMENT '有效天数',
  `redeemed` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '已赎回金额',
  `rest_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '剩余金额',
  `referee` int(11) NOT NULL COMMENT '推荐人ID',
  `area` int(6) NOT NULL COMMENT '大区ID',
  `company` int(6) NOT NULL COMMENT '分公司ID',
  `department` int(6) NOT NULL COMMENT '部门ID',
  `status` tinyint(1) NOT NULL COMMENT '投资资金状态',
  `order_id` varchar(30) NOT NULL COMMENT '订单号',
  `order_date` varchar(8) NOT NULL COMMENT '订单日期',
  `interest` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '利息',
  `tender_status` varchar(30) NOT NULL DEFAULT '初始投资',
  `client` tinyint(1) DEFAULT '0' COMMENT '客户端0PC，1微信2安卓APP，3IOS APP，4其他',
  `is_new` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否为对公账户投资 0：否 1：是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='理财产品购买明细-汇添利';

-- ----------------------------
-- Table structure for ht_product_redeem
-- ----------------------------
DROP TABLE IF EXISTS `ht_product_redeem`;
CREATE TABLE `ht_product_redeem` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `order_id` varchar(30) NOT NULL COMMENT '订单号',
  `amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '赎回金额',
  `redeem_time` int(11) NOT NULL COMMENT '时间',
  `interest` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '利息',
  `total` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '总额',
  `referee` int(11) NOT NULL COMMENT '推荐人ID',
  `area` int(6) NOT NULL COMMENT '大区ID',
  `company` int(6) NOT NULL COMMENT '分公司ID',
  `department` int(6) NOT NULL COMMENT '部门ID',
  `remark` varchar(20) DEFAULT NULL,
  `client` tinyint(1) NOT NULL DEFAULT '0',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '赎回状态：0成功，1失败  默认0',
  `comments` varchar(100) DEFAULT NULL COMMENT '备注信息（如失败信息）',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='理财产品赎回明细表-汇添利';

-- ----------------------------
-- Table structure for ht_product_redeem_day
-- ----------------------------
DROP TABLE IF EXISTS `ht_product_redeem_day`;
CREATE TABLE `ht_product_redeem_day` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `amount` decimal(10,2) NOT NULL COMMENT '用户投资总额',
  `interest` decimal(16,8) NOT NULL COMMENT '总收益',
  `invest_time` int(11) NOT NULL COMMENT '插入时间',
  `interest_all` decimal(16,8) DEFAULT '0.00000000' COMMENT '累计收益',
  `flag` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for ht_product_redeem_fail
-- ----------------------------
DROP TABLE IF EXISTS `ht_product_redeem_fail`;
CREATE TABLE `ht_product_redeem_fail` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `list_id` varchar(30) NOT NULL COMMENT '赎回订单ID',
  `order_id` varchar(30) NOT NULL COMMENT '订单号',
  `amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '赎回金额',
  `redeem_time` int(11) NOT NULL COMMENT '时间',
  `interest` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '利息',
  `total` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '总额',
  `referee` int(11) NOT NULL COMMENT '推荐人ID',
  `area` int(6) NOT NULL COMMENT '大区ID',
  `company` int(6) NOT NULL COMMENT '分公司ID',
  `department` int(6) NOT NULL COMMENT '部门ID',
  `remark` varchar(100) DEFAULT NULL,
  `amount_all` decimal(10,2) DEFAULT '0.00' COMMENT '赎回操作金额',
  `error` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='理财产品赎回明细表-汇添利';

-- ----------------------------
-- Table structure for ht_product_redeem_list
-- ----------------------------
DROP TABLE IF EXISTS `ht_product_redeem_list`;
CREATE TABLE `ht_product_redeem_list` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `list_id` varchar(30) NOT NULL COMMENT '赎回订单ID',
  `order_id` varchar(30) NOT NULL COMMENT '订单号',
  `amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '赎回金额',
  `redeem_time` int(11) NOT NULL COMMENT '时间',
  `interest` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '利息',
  `total` decimal(16,8) NOT NULL DEFAULT '0.00000000' COMMENT '总额',
  `referee` int(11) NOT NULL COMMENT '推荐人ID',
  `area` int(6) NOT NULL COMMENT '大区ID',
  `company` int(6) NOT NULL COMMENT '分公司ID',
  `department` int(6) NOT NULL COMMENT '部门ID',
  `client` tinyint(1) NOT NULL DEFAULT '0',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '赎回状态：0成功，1失败  默认0',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注信息（如失败信息）',
  PRIMARY KEY (`id`),
  KEY `list_id` (`list_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='理财产品赎回明细表-汇添利';

-- ----------------------------
-- Table structure for ht_product_reward_list
-- ----------------------------
DROP TABLE IF EXISTS `ht_product_reward_list`;
CREATE TABLE `ht_product_reward_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '推荐人user_id',
  `total_interest` decimal(12,2) DEFAULT '0.00' COMMENT '汇天利用户总收益',
  `reward` decimal(8,2) DEFAULT '0.00' COMMENT '推荐人提成金额',
  `status` tinyint(2) DEFAULT '0' COMMENT '发放状态:0未发放，1已发放',
  `give_time` int(11) DEFAULT NULL COMMENT '发放时间',
  `act_month` varchar(10) DEFAULT NULL COMMENT '活动月份',
  `remark` varchar(40) DEFAULT NULL COMMENT '提成说明',
  `order_id` varchar(16) DEFAULT NULL COMMENT '唯一标识',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_product_user
-- ----------------------------
DROP TABLE IF EXISTS `ht_product_user`;
CREATE TABLE `ht_product_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  `total` decimal(10,2) DEFAULT '0.00',
  `valid_days` int(11) DEFAULT '0',
  `redeemed` decimal(10,2) DEFAULT '0.00' COMMENT '赎回金额',
  `rest_amount` decimal(10,2) DEFAULT '0.00' COMMENT '投资金额',
  `interest` decimal(10,2) DEFAULT '0.00' COMMENT '利息',
  `rest_amount_check` decimal(10,2) DEFAULT '0.00' COMMENT '检查',
  `redeemed_check` decimal(10,2) DEFAULT '0.00' COMMENT '检查',
  `status` tinyint(1) DEFAULT '0' COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_protocol_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_protocol_log`;
CREATE TABLE `ht_protocol_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `protocol_id` varchar(32) NOT NULL COMMENT '协议id',
  `protocol_name` varchar(50) NOT NULL COMMENT '协议模板名称',
  `version_number` varchar(15) NOT NULL COMMENT '版本号',
  `operation` tinyint(4) NOT NULL DEFAULT '0' COMMENT '操作（0.创建1.修改2.删除）',
  `delete_user` int(11) DEFAULT '0' COMMENT '删除人id',
  `create_user` int(11) DEFAULT '0' COMMENT '创建人id',
  `update_user` int(11) DEFAULT '0' COMMENT '修改人id',
  `delete_time` timestamp NULL DEFAULT NULL COMMENT '删除时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=340 DEFAULT CHARSET=utf8 COMMENT='对协议操作进行记录';

-- ----------------------------
-- Table structure for ht_protocol_template
-- ----------------------------
DROP TABLE IF EXISTS `ht_protocol_template`;
CREATE TABLE `ht_protocol_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `protocol_id` varchar(32) NOT NULL COMMENT '协议id',
  `protocol_name` varchar(50) NOT NULL COMMENT '协议模板名称',
  `display_name` varchar(50) NOT NULL COMMENT '前台显示名称',
  `protocol_type` varchar(50) NOT NULL COMMENT '协议类型',
  `version_number` varchar(32) NOT NULL COMMENT '协议版本号(当前协议使用的版本号)',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态(0.协议不显示1.协议显示)',
  `protocol_url` varchar(50) NOT NULL COMMENT '协议路径(当前协议显示路径)',
  `img_url` varchar(120) DEFAULT '' COMMENT 'pdf图片路径地址',
  `remarks` varchar(120) DEFAULT NULL COMMENT '备注(当前协议)',
  `is_show` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否在帮助中心显示（0不显示，1显示）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8 COMMENT='对协议模板进行记录';

-- ----------------------------
-- Table structure for ht_protocol_version
-- ----------------------------
DROP TABLE IF EXISTS `ht_protocol_version`;
CREATE TABLE `ht_protocol_version` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `protocol_id` varchar(32) NOT NULL COMMENT '协议id',
  `version_number` varchar(32) NOT NULL COMMENT '协议版本号',
  `protocol_name` varchar(50) NOT NULL COMMENT '协议文件名称',
  `protocol_url` varchar(50) NOT NULL COMMENT '协议路径',
  `display_name` varchar(100) NOT NULL COMMENT '前台展示名称',
  `remarks` varchar(120) DEFAULT NULL COMMENT '备注',
  `display_flag` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '是否启用(0.版本不启用1.版本启用2.版本废弃)',
  `create_user` int(11) DEFAULT '0' COMMENT '创建人id',
  `update_user` int(11) DEFAULT '0' COMMENT '修改人id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=196 DEFAULT CHARSET=utf8 COMMENT='对协议版本历史进行记录';

-- ----------------------------
-- Table structure for ht_push_money
-- ----------------------------
DROP TABLE IF EXISTS `ht_push_money`;
CREATE TABLE `ht_push_money` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '提成设置表：主键id',
  `type` varchar(20) NOT NULL COMMENT '用户类型：属于什么用户',
  `project_type` tinyint(1) DEFAULT '1' COMMENT '产品类型：1 汇直投 2 汇计划',
  `reward_send` tinyint(1) DEFAULT '1' COMMENT '是否发放提成：0 不发放 1 发放',
  `day_tender` varchar(20) NOT NULL COMMENT '天标',
  `month_tender` varchar(20) NOT NULL COMMENT '月标',
  `remark` text NOT NULL COMMENT '备注说明',
  `create_by` int(11) DEFAULT NULL,
  `update_by` int(11) DEFAULT NULL COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_project_type` (`project_type`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

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
-- Table structure for ht_r_user
-- ----------------------------
DROP TABLE IF EXISTS `ht_r_user`;
CREATE TABLE `ht_r_user` (
  `user_id` int(11) unsigned NOT NULL,
  `username` varchar(30) DEFAULT '' COMMENT '用户名',
  `mobile` char(11) DEFAULT '' COMMENT '手机号',
  `bank_mobile` char(11) DEFAULT '' COMMENT '银行预留手机号',
  `role_id` tinyint(4) unsigned DEFAULT '1' COMMENT '用户角色1投资人2借款人3担保机构',
  `truename` varchar(30) DEFAULT '' COMMENT '真实姓名',
  `attribute` int(11) NOT NULL DEFAULT '0' COMMENT '用户属性 0=>无主单 1=>有主单 2=>线下员工 3=>线上员工',
  `spreads_user_id` int(11) unsigned DEFAULT '0',
  `user_type` tinyint(1) unsigned DEFAULT '0' COMMENT '用户类型 0普通用户 1企业用户',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`),
  KEY `idx_spreads_user_id` (`spreads_user_id`),
  KEY `idx_crete_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户冗余表';

-- ----------------------------
-- Table structure for ht_repayment_plan
-- ----------------------------
DROP TABLE IF EXISTS `ht_repayment_plan`;
CREATE TABLE `ht_repayment_plan` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL COMMENT '用户id',
  `borrow_nid` varchar(50) DEFAULT '' COMMENT '投资订单号(ht_borrow_recover 的nid)',
  `accede_order_id` varchar(50) DEFAULT '' COMMENT '汇计划加入订单号',
  `customer_name` varchar(20) DEFAULT NULL COMMENT '坐席姓名',
  `customer_group` tinyint(1) DEFAULT '0' COMMENT '坐席分组 0:其他,1:新客组,2:老客组,3:惠众',
  `money` decimal(16,2) DEFAULT NULL COMMENT '待回款金额',
  `repayment_time` date DEFAULT NULL COMMENT '待回款时间,精确到日 yyyy-mm-dd',
  `status` tinyint(1) DEFAULT '1' COMMENT '是否有效 1:有效,2:无效',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `repayment_plan_create_time` (`create_time`),
  KEY `customer_name` (`customer_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1372 DEFAULT CHARSET=utf8 COMMENT='坐席每日待回款金额表';

-- ----------------------------
-- Table structure for ht_sponsor_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_sponsor_log`;
CREATE TABLE `ht_sponsor_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `borrow_nid` varchar(20) NOT NULL COMMENT '借款编号',
  `old_sponsor_id` int(11) DEFAULT NULL COMMENT '原始担保人id',
  `old_sponsor` varchar(20) DEFAULT NULL COMMENT '原始担保人username',
  `new_sponsor_id` int(11) DEFAULT NULL COMMENT '新担保人id',
  `new_sponsor` varchar(20) DEFAULT NULL COMMENT '新担保人username',
  `status` tinyint(1) DEFAULT '0' COMMENT '0初始1修改成功2修改失败',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '0初始1关闭2删除',
  `create_user_name` varchar(20) DEFAULT NULL COMMENT '创建用户名',
  `update_user_name` varchar(20) DEFAULT NULL COMMENT '更新用户名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_stzh_white_list
-- ----------------------------
DROP TABLE IF EXISTS `ht_stzh_white_list`;
CREATE TABLE `ht_stzh_white_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '机构/个人userid',
  `user_name` varchar(30) DEFAULT NULL COMMENT '机构/个人 用户名',
  `account_id` varchar(20) DEFAULT NULL COMMENT '电子账号',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号',
  `customer_name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `st_user_id` int(11) DEFAULT NULL COMMENT '受托支付收款人userid',
  `st_user_name` varchar(30) DEFAULT NULL COMMENT '受托支付收款人用户名',
  `st_account_id` varchar(20) DEFAULT NULL COMMENT '收款人电子账号',
  `st_mobile` varchar(11) DEFAULT NULL COMMENT '收款人  手机号',
  `st_customer_name` varchar(50) DEFAULT NULL COMMENT '收款人名称/姓名',
  `approval_name` varchar(20) DEFAULT NULL COMMENT '审批人',
  `approval_time` varchar(20) DEFAULT NULL COMMENT '审批时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `inst_code` varchar(20) DEFAULT NULL COMMENT '机构编号',
  `inst_name` varchar(50) DEFAULT NULL COMMENT '机构名称',
  `state` tinyint(2) unsigned DEFAULT NULL COMMENT '状态 1启用  0禁用',
  `del_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标识',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '修改人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_sub_commission
-- ----------------------------
DROP TABLE IF EXISTS `ht_sub_commission`;
CREATE TABLE `ht_sub_commission` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_id` varchar(30) DEFAULT NULL COMMENT '订单号',
  `account_id` varchar(30) DEFAULT NULL COMMENT '转出用户电子账户号',
  `receive_user_id` int(11) DEFAULT NULL COMMENT '收款方用户ID',
  `receive_user_name` varchar(30) DEFAULT NULL COMMENT '收款用户名',
  `receive_account_id` varchar(30) DEFAULT NULL COMMENT '收款方电子账户号',
  `account` decimal(16,2) DEFAULT '0.00' COMMENT '转账金额',
  `truename` varchar(30) DEFAULT '' COMMENT '转入姓名',
  `trade_status` tinyint(1) DEFAULT NULL COMMENT '交易状态(0:初始 1:成功 2:失败 )',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  `error_msg` varchar(50) DEFAULT NULL COMMENT '错误信息',
  `tx_date` int(10) DEFAULT NULL COMMENT '交易日期',
  `tx_time` int(10) DEFAULT NULL COMMENT '交易时间',
  `seq_no` varchar(15) DEFAULT NULL COMMENT '交易流水号',
  `create_user_id` int(11) DEFAULT NULL COMMENT '创建用户ID',
  `create_user_name` varchar(30) DEFAULT NULL COMMENT '创建用户名',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户ID',
  `update_user_name` varchar(30) DEFAULT NULL COMMENT '更新用户名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COMMENT='账户分佣记录表';

-- ----------------------------
-- Table structure for ht_sub_commission_list_config
-- ----------------------------
DROP TABLE IF EXISTS `ht_sub_commission_list_config`;
CREATE TABLE `ht_sub_commission_list_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT '0' COMMENT '用户id',
  `truename` char(30) DEFAULT '' COMMENT '姓名',
  `username` char(30) DEFAULT '' COMMENT '用户名',
  `role_name` char(30) DEFAULT '' COMMENT '用户角色',
  `user_type` char(30) DEFAULT '' COMMENT '用户类型',
  `bank_open_account` char(30) DEFAULT '' COMMENT '银行开户状态',
  `account` char(20) DEFAULT '' COMMENT '江西银行电子账号',
  `status` tinyint(1) DEFAULT '0' COMMENT '用户状态 0-启用 1-禁用',
  `remark` varchar(255) DEFAULT '' COMMENT '说明',
  `create_user_id` int(11) DEFAULT NULL COMMENT '创建用户id',
  `create_user_name` varchar(20) DEFAULT NULL COMMENT '创建用户名',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户名',
  `update_user_name` varchar(20) DEFAULT NULL COMMENT '更新用户名',
  `cooperate_num` varchar(20) DEFAULT NULL COMMENT '合作机构编号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='账户分佣记录表';

-- ----------------------------
-- Table structure for ht_tender_agreement
-- ----------------------------
DROP TABLE IF EXISTS `ht_tender_agreement`;
CREATE TABLE `ht_tender_agreement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '投资用户ID',
  `user_name` varchar(30) NOT NULL DEFAULT '' COMMENT '投资用户名',
  `borrow_nid` varchar(50) NOT NULL DEFAULT '' COMMENT '标的编号',
  `tender_type` tinyint(1) NOT NULL COMMENT '投资类型:0:原始投资,1:承接债权,2:加入计划,3:计划承接',
  `tender_nid` varchar(50) NOT NULL COMMENT '投资订单号或承接订单号',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '合同状态 0:初始,1:生成成功,2,签署成功,3,下载成功,4,已归档',
  `sign_status` varchar(4) NOT NULL DEFAULT '' COMMENT '签署状态',
  `sign_desc` varchar(100) NOT NULL DEFAULT '' COMMENT '签署描述',
  `archive_status` varchar(4) NOT NULL DEFAULT '' COMMENT '归档状态',
  `archive_desc` varchar(100) NOT NULL DEFAULT '' COMMENT '归档描述',
  `contract_number` varchar(50) NOT NULL DEFAULT '' COMMENT '合同编号',
  `contract_name` varchar(50) NOT NULL DEFAULT '' COMMENT '合同名称',
  `templet_id` varchar(12) NOT NULL DEFAULT '0' COMMENT '模板编号',
  `contract_create_time` int(11) NOT NULL DEFAULT '0' COMMENT '合同生成时间',
  `contract_sign_time` int(11) NOT NULL DEFAULT '0' COMMENT '合同签署时间',
  `contract_download_time` int(11) NOT NULL DEFAULT '0' COMMENT '合同下载时间',
  `contract_archive_time` int(11) NOT NULL DEFAULT '0' COMMENT '合同归档时间',
  `download_url` varchar(500) NOT NULL DEFAULT '' COMMENT '合同下载地址',
  `viewpdf_url` varchar(500) NOT NULL DEFAULT '' COMMENT '合同查看地址',
  `img_url` varchar(200) NOT NULL DEFAULT '' COMMENT '脱敏图片存放地址',
  `pdf_url` varchar(500) NOT NULL DEFAULT '' COMMENT '本地pdf文件路径',
  `create_user_id` int(11) NOT NULL COMMENT '创建用户id',
  `create_user_name` varchar(20) NOT NULL COMMENT '创建用户名',
  `update_user_id` int(11) DEFAULT NULL COMMENT '更新用户名',
  `update_user_name` varchar(20) DEFAULT NULL COMMENT '更新用户名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `user_name` (`user_name`) USING BTREE,
  KEY `borrow_nid` (`borrow_nid`) USING BTREE,
  KEY `tender_nid` (`tender_nid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4737 DEFAULT CHARSET=utf8 COMMENT='用户投资协议记录表';

-- ----------------------------
-- Table structure for ht_tender_back_history
-- ----------------------------
DROP TABLE IF EXISTS `ht_tender_back_history`;
CREATE TABLE `ht_tender_back_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` tinyint(1) unsigned DEFAULT NULL COMMENT '撤销方 0：运营方 1：投资方',
  `borrow_nid` varchar(20) DEFAULT NULL COMMENT '借款编号',
  `borrow_name` varchar(255) DEFAULT NULL COMMENT '借款标题',
  `account` varchar(255) DEFAULT NULL COMMENT '借款金额',
  `account_yes` varchar(255) DEFAULT NULL COMMENT '借到金额',
  `user_name` varchar(20) DEFAULT NULL COMMENT '投资用户名',
  `amount` varchar(255) DEFAULT NULL COMMENT '撤销金额',
  `ord_id` varchar(50) DEFAULT NULL COMMENT '投资订单号',
  `trx_id` varchar(20) DEFAULT NULL COMMENT '解冻订单号',
  `notes` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_user` varchar(20) DEFAULT '' COMMENT '操作用户',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_tender_commission
-- ----------------------------
DROP TABLE IF EXISTS `ht_tender_commission`;
CREATE TABLE `ht_tender_commission` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `borrow_nid` varchar(50) CHARACTER SET utf8 NOT NULL,
  `tender_type` tinyint(1) DEFAULT '1' COMMENT '投资类别 1：直投类，2：汇计划',
  `ordid` varchar(30) DEFAULT NULL COMMENT '充值订单号',
  `tender_id` int(11) DEFAULT '0',
  `user_id` int(10) DEFAULT NULL COMMENT '获得提成的uid',
  `department_id` int(5) DEFAULT NULL COMMENT '获得提成的部门id',
  `tender_user_id` int(10) DEFAULT NULL COMMENT '投资人的uid',
  `tender_department_id` int(5) DEFAULT NULL COMMENT '投资人的部门id',
  `account_tender` decimal(11,2) DEFAULT NULL COMMENT '投资金额',
  `tender_time` int(11) DEFAULT NULL COMMENT '投资时间',
  `send_time` int(10) DEFAULT NULL COMMENT '提成发放时间',
  `commission` decimal(11,2) DEFAULT NULL COMMENT '提成',
  `status` int(2) NOT NULL DEFAULT '0' COMMENT '0:未发放;1:已发放;100:删除',
  `remark` varchar(100) DEFAULT NULL,
  `compute_time` int(11) DEFAULT NULL COMMENT '计算时间',
  `region_id` int(11) DEFAULT '0' COMMENT '地区ID',
  `branch_id` int(11) DEFAULT '0' COMMENT '分公司ID',
  `region_name` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '地区名',
  `branch_name` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '分公司名',
  `department_name` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '部门名',
  `account_id` varchar(20) DEFAULT '' COMMENT '电子账号',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '返现时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`) USING BTREE,
  KEY `idx_tender_id` (`tender_id`),
  KEY `idx_borrow_nid` (`borrow_nid`)
) ENGINE=InnoDB AUTO_INCREMENT=176 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for ht_transfer_exception_log
-- ----------------------------
DROP TABLE IF EXISTS `ht_transfer_exception_log`;
CREATE TABLE `ht_transfer_exception_log` (
  `uuid` varchar(36) NOT NULL,
  `order_id` varchar(20) DEFAULT NULL COMMENT '订单编号',
  `recover_id` int(11) DEFAULT NULL COMMENT '投资编号',
  `content` text COMMENT '请求内容',
  `result` text COMMENT '返回结果',
  `type` tinyint(1) unsigned DEFAULT NULL COMMENT '0:体验金收益回款，1：加息券收益回款，2：其他',
  `status` tinyint(1) unsigned DEFAULT NULL COMMENT '0：成功，1：失败',
  `respcode` varchar(10) DEFAULT NULL COMMENT '汇付返回码',
  `trans_amt` decimal(11,2) DEFAULT NULL COMMENT '交易金额',
  `account_id` varchar(20) NOT NULL COMMENT '用户客户号',
  `seq_no` varchar(15) DEFAULT NULL COMMENT '交易流水号',
  `cus_id` varchar(20) DEFAULT NULL COMMENT '入账商户号',
  `acct_id` varchar(20) DEFAULT NULL COMMENT '入账子账户号',
  `user_id` int(11) DEFAULT NULL COMMENT '用户编号',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `del_flag` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标识',
  `create_user` varchar(20) DEFAULT '' COMMENT '发行者用户id',
  `update_user` varchar(20) DEFAULT NULL COMMENT '更新者',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='转账异常';

-- ----------------------------
-- Table structure for ht_under_line_recharge
-- ----------------------------
DROP TABLE IF EXISTS `ht_under_line_recharge`;
CREATE TABLE `ht_under_line_recharge` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `code` char(8) NOT NULL COMMENT '交易类型',
  `explain` varchar(30) DEFAULT NULL COMMENT '交易类型说明',
  `create_user_id` int(11) NOT NULL COMMENT '操作人',
  `create_user_name` varchar(30) NOT NULL COMMENT '添加者用户名',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态:0,启用;1,禁用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COMMENT='线下充值类型配置';

-- ----------------------------
-- Table structure for ht_user_operate_list
-- ----------------------------
DROP TABLE IF EXISTS `ht_user_operate_list`;
CREATE TABLE `ht_user_operate_list` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL COMMENT '用户id',
  `user_name` varchar(30) DEFAULT NULL COMMENT '用户名',
  `current_owner` varchar(20) DEFAULT NULL COMMENT '当前拥有人(坐席姓名)',
  `customer_group` tinyint(1) DEFAULT '0' COMMENT '坐席分组 0:其他,1:新客组,2:老客组,3:惠众',
  `money` decimal(16,2) DEFAULT NULL COMMENT '金额',
  `year_money` decimal(16,2) DEFAULT NULL COMMENT '年化金额',
  `balance` decimal(16,2) DEFAULT NULL COMMENT '站岗资金',
  `operating` tinyint(1) DEFAULT '0' COMMENT '用户行为 1:投资,2:充值,3:回款,4提现',
  `status` tinyint(1) DEFAULT '1' COMMENT '是否有效 1:有效,2:无效',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `operate_list_create_time` (`create_time`),
  KEY `customer_group_operating` (`customer_group`,`operating`)
) ENGINE=InnoDB AUTO_INCREMENT=572 DEFAULT CHARSET=utf8 COMMENT='运营部用户资金明细';

-- ----------------------------
-- Table structure for ht_user_transfer
-- ----------------------------
DROP TABLE IF EXISTS `ht_user_transfer`;
CREATE TABLE `ht_user_transfer` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_id` varchar(50) NOT NULL COMMENT '订单编号',
  `out_user_id` int(11) unsigned NOT NULL COMMENT '转出账户id',
  `out_user_name` varchar(30) NOT NULL COMMENT '转出账户',
  `in_user_id` int(11) unsigned DEFAULT NULL COMMENT '转入账户id',
  `in_user_name` varchar(30) DEFAULT NULL COMMENT '转入账户',
  `transfer_amount` decimal(20,2) NOT NULL DEFAULT '0.00' COMMENT '转账金额',
  `transfer_url` varchar(255) NOT NULL COMMENT '转账链接',
  `transfer_time` datetime DEFAULT NULL COMMENT '转账时间',
  `status` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '转让状态 0 待转账 1 转帐中 2 转账成功 3 转账失败',
  `transfer_type` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '转账类型 0 用户转给平台',
  `remark` varchar(255) NOT NULL COMMENT '转账说明',
  `create_user_name` varchar(30) DEFAULT NULL COMMENT '创建人',
  `reconciliation_id` varchar(50) DEFAULT NULL COMMENT '充值手续费对账标识',
  `create_user_id` int(11) unsigned NOT NULL COMMENT '创建者用户id',
  `update_user_id` int(11) unsigned DEFAULT NULL COMMENT '更新者用户id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ht_wkcd_borrow
-- ----------------------------
DROP TABLE IF EXISTS `ht_wkcd_borrow`;
CREATE TABLE `ht_wkcd_borrow` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `wkcd_id` varchar(40) NOT NULL COMMENT '微可唯一标识',
  `user_id` int(11) NOT NULL COMMENT '用户名',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `truename` varchar(255) DEFAULT NULL COMMENT '借款人姓名',
  `mobile` char(11) DEFAULT NULL COMMENT '手机号',
  `borrow_amount` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '借款金额',
  `car_no` char(7) DEFAULT NULL COMMENT '车牌',
  `car_type` varchar(50) DEFAULT NULL COMMENT '车型',
  `car_shop` varchar(50) DEFAULT NULL COMMENT '所属门店',
  `wkcd_repay_type` varchar(100) DEFAULT NULL COMMENT '微可还款方式',
  `wkcd_borrow_period` int(11) DEFAULT NULL COMMENT '借款周期（月）',
  `wkcd_status` varchar(50) DEFAULT NULL COMMENT '微可审核状态 ',
  `ht_status` int(11) NOT NULL DEFAULT '0' COMMENT '汇盈审核状态  0未审核/1已审核-通过/2已审核-不通过',
  `check_desc` varchar(500) DEFAULT NULL COMMENT '审批意见',
  `check_user` int(11) DEFAULT NULL COMMENT 'hyjf审核人id',
  `check_time` int(10) DEFAULT NULL COMMENT '汇盈审核时间',
  `borrow_nid` varchar(50) DEFAULT NULL COMMENT '对应项目编号',
  `apr` decimal(10,2) DEFAULT NULL COMMENT '年化率',
  `sync` tinyint(2) unsigned DEFAULT NULL COMMENT '0：实鑫车贷没有记录还款计划  1：实鑫车贷已经记录还款计划',
  `create_user` int(11) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `wkcdid_index` (`wkcd_id`),
  KEY `username_index` (`user_name`),
  KEY `hyjfstatus_index` (`ht_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微可车贷资产信息表';
